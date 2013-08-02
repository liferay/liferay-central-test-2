/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.freemarker;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.freemarker.FreeMarkerContext;
import com.liferay.portal.kernel.freemarker.FreeMarkerEngine;
import com.liferay.portal.kernel.freemarker.FreeMarkerVariablesUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.security.pacl.NotPrivileged;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.template.TemplateControlContext;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateCache;
import freemarker.cache.TemplateLoader;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;
import java.io.Writer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import java.security.AccessController;
import java.security.PrivilegedExceptionAction;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mika Koivisto
 * @author Raymond Augé
 */
@DoPrivileged
public class FreeMarkerEngineImpl implements FreeMarkerEngine {

	@Override
	public void clearClassLoader(ClassLoader classLoader) {
		_classLoaderToolsContextsMap.remove(classLoader);
	}

	@Override
	public void flushTemplate(String freeMarkerTemplateId) {
		if (_configuration == null) {
			return;
		}

		if (_stringTemplateLoader != null) {
			_stringTemplateLoader.removeTemplate(freeMarkerTemplateId);
		}

		PortalCache portalCache = LiferayCacheStorage.getPortalCache();

		portalCache.remove(_getResourceCacheKey(freeMarkerTemplateId));
	}

	public TemplateControlContext getTemplateControlContext() {
		return _pacl.getTemplateControlContext();
	}

	@NotPrivileged
	@Override
	public FreeMarkerContext getWrappedClassLoaderToolsContext() {
		return new FreeMarkerContextImpl(_doGetToolsContext(_STANDARD));
	}

	@NotPrivileged
	@Override
	public FreeMarkerContext getWrappedRestrictedToolsContext() {
		return new FreeMarkerContextImpl(_doGetToolsContext(_RESTRICTED));
	}

	@NotPrivileged
	@Override
	public FreeMarkerContext getWrappedStandardToolsContext() {
		return new FreeMarkerContextImpl(_doGetToolsContext(_STANDARD));
	}

	@Override
	public void init() throws Exception {
		if (_configuration != null) {
			return;
		}

		LiferayTemplateLoader liferayTemplateLoader =
			new LiferayTemplateLoader();

		liferayTemplateLoader.setTemplateLoaders(
			PropsValues.FREEMARKER_ENGINE_TEMPLATE_LOADERS);

		_stringTemplateLoader = new StringTemplateLoader();

		MultiTemplateLoader multiTemplateLoader =
			new MultiTemplateLoader(
				new TemplateLoader[] {
					new ClassTemplateLoader(getClass(), StringPool.SLASH),
					_stringTemplateLoader, liferayTemplateLoader
				});

		_configuration = new Configuration();

		_configuration.setDefaultEncoding(StringPool.UTF8);
		_configuration.setLocalizedLookup(
			PropsValues.FREEMARKER_ENGINE_LOCALIZED_LOOKUP);
		_configuration.setNewBuiltinClassResolver(
			new LiferayTemplateClassResolver());
		_configuration.setObjectWrapper(new LiferayObjectWrapper());
		_configuration.setSetting(
			"auto_import", PropsValues.FREEMARKER_ENGINE_MACRO_LIBRARY);
		_configuration.setSetting(
			"cache_storage", PropsValues.FREEMARKER_ENGINE_CACHE_STORAGE);
		_configuration.setSetting(
			"template_exception_handler",
			PropsValues.FREEMARKER_ENGINE_TEMPLATE_EXCEPTION_HANDLER);
		_configuration.setTemplateLoader(multiTemplateLoader);
		_configuration.setTemplateUpdateDelay(
			PropsValues.FREEMARKER_ENGINE_MODIFICATION_CHECK_INTERVAL);

		try {

			// This must take place after setting properties above otherwise the
			// cache is reset to the original implementation

			Field field = ReflectionUtil.getDeclaredField(
				Configuration.class, "cache");

			TemplateCache templateCache = (TemplateCache)field.get(
				_configuration);

			templateCache = new LiferayTemplateCache(templateCache);

			field.set(_configuration, templateCache);
		}
		catch (Exception e) {
			throw new Exception("Unable to Initialize Freemarker manager");
		}

		_encoding = _configuration.getEncoding(_configuration.getLocale());
		_locale = _configuration.getLocale();

		ClassLoader classLoader = TemplateCache.class.getClassLoader();

		Class<?> templateKeyClass = classLoader.loadClass(
			TemplateCache.class.getName().concat("$TemplateKey"));

		_templateKeyConstructor = templateKeyClass.getDeclaredConstructor(
			String.class, Locale.class, String.class, boolean.class);

		_templateKeyConstructor.setAccessible(true);
	}

	@NotPrivileged
	@Override
	public boolean mergeTemplate(
			String freeMarkerTemplateId, FreeMarkerContext freeMarkerContext,
			Writer writer)
		throws Exception {

		return mergeTemplate(
			freeMarkerTemplateId, null, freeMarkerContext, writer);
	}

	@NotPrivileged
	@Override
	public boolean mergeTemplate(
			String freeMarkerTemplateId, String freemarkerTemplateContent,
			FreeMarkerContext freeMarkerContext, Writer writer)
		throws Exception {

		Template template = AccessController.doPrivileged(
			new DoGetTemplatePrivilegedAction(
				freeMarkerTemplateId, freemarkerTemplateContent,
				StringPool.UTF8));

		FreeMarkerContextImpl freeMarkerContextImpl =
			(FreeMarkerContextImpl)freeMarkerContext;

		template.process(freeMarkerContextImpl.getWrappedContext(), writer);

		return true;
	}

	@NotPrivileged
	@Override
	public boolean resourceExists(String resource) {
		try {
			Template template = _configuration.getTemplate(resource);

			if (template != null) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioe, ioe);
			}

			return false;
		}
	}

	private FreeMarkerContextImpl _doGetToolsContext(
		String templateContextType) {

		TemplateControlContext templateControlContext =
			getTemplateControlContext();

		ClassLoader classLoader = templateControlContext.getClassLoader();

		Map<String, FreeMarkerContextImpl> toolsContextMap =
			_classLoaderToolsContextsMap.get(classLoader);

		if (toolsContextMap == null) {
			toolsContextMap =
				new ConcurrentHashMap<String, FreeMarkerContextImpl>();

			_classLoaderToolsContextsMap.put(classLoader, toolsContextMap);
		}

		FreeMarkerContextImpl freeMarkerContextImpl = toolsContextMap.get(
			templateContextType);

		if (freeMarkerContextImpl != null) {
			return freeMarkerContextImpl;
		}

		freeMarkerContextImpl = new FreeMarkerContextImpl();

		if (_RESTRICTED.equals(templateContextType)) {
			FreeMarkerVariablesUtil.insertHelperUtilities(
				freeMarkerContextImpl,
				PropsValues.JOURNAL_TEMPLATE_FREEMARKER_RESTRICTED_VARIABLES);
		}
		else {
			FreeMarkerVariablesUtil.insertHelperUtilities(
				freeMarkerContextImpl, null);
		}

		toolsContextMap.put(templateContextType, freeMarkerContextImpl);

		return freeMarkerContextImpl;
	}

	private String _getResourceCacheKey(String freeMarkerTemplateId) {
		try {
			Object object = _templateKeyConstructor.newInstance(
				freeMarkerTemplateId, _locale, _encoding, Boolean.TRUE);

			return object.toString();
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Failed to build FreeMarker internal resource cache key for " +
					"template id " + freeMarkerTemplateId, e);
		}
	}

	private static final String _RESTRICTED = "RESTRICTED";

	private static final String _STANDARD = "STANDARD";

	private static Log _log = LogFactoryUtil.getLog(FreeMarkerEngineImpl.class);

	private static PACL _pacl = new NoPACL();

	private Map<ClassLoader, Map<String, FreeMarkerContextImpl>>
		_classLoaderToolsContextsMap = new ConcurrentHashMap
			<ClassLoader, Map<String, FreeMarkerContextImpl>>();
	private Configuration _configuration;
	private String _encoding;
	private Locale _locale;
	private StringTemplateLoader _stringTemplateLoader;
	private Constructor<?> _templateKeyConstructor;

	private static class NoPACL implements PACL {

		public TemplateControlContext getTemplateControlContext() {
			ClassLoader contextClassLoader =
				ClassLoaderUtil.getContextClassLoader();

			return new TemplateControlContext(null, contextClassLoader);
		}

	}

	public static interface PACL {

		public TemplateControlContext getTemplateControlContext();

	}

	private class DoGetTemplatePrivilegedAction
		implements PrivilegedExceptionAction<Template> {

		public DoGetTemplatePrivilegedAction(
			String freeMarkerTemplateId, String freemarkerTemplateContent,
			String encoding) {

			_freemarkerTemplateContent = freemarkerTemplateContent;
			_freeMarkerTemplateId = freeMarkerTemplateId;
			_encoding = encoding;
		}

		public Template run() throws Exception {
			if (Validator.isNotNull(_freemarkerTemplateContent)) {
				PortalCache portalCache = LiferayCacheStorage.getPortalCache();

				portalCache.remove(_getResourceCacheKey(_freeMarkerTemplateId));

				_stringTemplateLoader.putTemplate(
					_freeMarkerTemplateId, _freemarkerTemplateContent);

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Added " + _freeMarkerTemplateId +
							" to the string based FreeMarker template " +
								"repository");
				}
			}

			return _configuration.getTemplate(_freeMarkerTemplateId, _encoding);
		}

		private String _encoding;
		private String _freemarkerTemplateContent;
		private String _freeMarkerTemplateId;

	}

}