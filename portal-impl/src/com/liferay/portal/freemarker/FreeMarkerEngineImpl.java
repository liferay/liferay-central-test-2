/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.lang.PortalSecurityManagerThreadLocal;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.security.pacl.PACLPolicy;
import com.liferay.portal.security.pacl.PACLPolicyManager;
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

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mika Koivisto
 * @author Raymond Augé
 */
public class FreeMarkerEngineImpl implements FreeMarkerEngine {

	public void clearClassLoader(ClassLoader classLoader) {
		_classLoaderFreeMarkerContexts.remove(classLoader);
	}

	public void flushTemplate(String freeMarkerTemplateId) {
		if (_stringTemplateLoader != null) {
			_stringTemplateLoader.removeTemplate(freeMarkerTemplateId);
		}

		PortalCache portalCache = LiferayCacheStorage.getPortalCache();

		portalCache.remove(_getResourceCacheKey(freeMarkerTemplateId));
	}

	public FreeMarkerContext getWrappedClassLoaderToolsContext() {

		// This context will have all of its utilities initialized within the
		// class loader of the current thread

		ClassLoader contextClassLoader =
			PACLClassLoaderUtil.getContextClassLoader();

		PACLPolicy threadLocalPACLPolicy =
			PortalSecurityManagerThreadLocal.getPACLPolicy();

		PACLPolicy contextClassLoaderPACLPolicy =
			PACLPolicyManager.getPACLPolicy(contextClassLoader);

		try {
			PortalSecurityManagerThreadLocal.setPACLPolicy(
				contextClassLoaderPACLPolicy);

			FreeMarkerContextImpl classLoaderContext =
				_classLoaderFreeMarkerContexts.get(contextClassLoader);

			if (classLoaderContext == null) {
				classLoaderContext = new FreeMarkerContextImpl();

				FreeMarkerVariablesUtil.insertHelperUtilities(
					classLoaderContext, null);

				_classLoaderFreeMarkerContexts.put(
					contextClassLoader, classLoaderContext);
			}

			return new PACLFreeMarkerContextImpl(
				classLoaderContext.getWrappedContext(),
				contextClassLoaderPACLPolicy);
		}
		finally {
			PortalSecurityManagerThreadLocal.setPACLPolicy(
				threadLocalPACLPolicy);
		}
	}

	public FreeMarkerContext getWrappedRestrictedToolsContext() {
		return new FreeMarkerContextImpl(
			_restrictedToolsContext.getWrappedContext());
	}

	public FreeMarkerContext getWrappedStandardToolsContext() {
		return new FreeMarkerContextImpl(
			_standardToolsContext.getWrappedContext());
	}

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

		_encoding = _configuration.getEncoding(_locale);
		_locale = _configuration.getLocale();

		_restrictedToolsContext = new FreeMarkerContextImpl();

		FreeMarkerVariablesUtil.insertHelperUtilities(
			_restrictedToolsContext,
			PropsValues.JOURNAL_TEMPLATE_FREEMARKER_RESTRICTED_VARIABLES);

		_standardToolsContext = new FreeMarkerContextImpl();

		FreeMarkerVariablesUtil.insertHelperUtilities(
			_standardToolsContext, null);

		ClassLoader classLoader = TemplateCache.class.getClassLoader();

		Class<?> templateKeyClass = classLoader.loadClass(
			TemplateCache.class.getName().concat("$TemplateKey"));

		_templateKeyConstructor = templateKeyClass.getDeclaredConstructor(
			String.class, Locale.class, String.class, boolean.class);

		_templateKeyConstructor.setAccessible(true);
	}

	public boolean mergeTemplate(
			String freeMarkerTemplateId, FreeMarkerContext freeMarkerContext,
			Writer writer)
		throws Exception {

		return mergeTemplate(
			freeMarkerTemplateId, null, freeMarkerContext, writer);
	}

	public boolean mergeTemplate(
			String freeMarkerTemplateId, String freemarkerTemplateContent,
			FreeMarkerContext freeMarkerContext, Writer writer)
		throws Exception {

		if (Validator.isNotNull(freemarkerTemplateContent)) {
			PortalCache portalCache = LiferayCacheStorage.getPortalCache();

			portalCache.remove(_getResourceCacheKey(freeMarkerTemplateId));

			_stringTemplateLoader.putTemplate(
				freeMarkerTemplateId, freemarkerTemplateContent);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Added " + freeMarkerTemplateId +
						" to the string based FreeMarker template repository");
			}
		}

		FreeMarkerContextImpl freeMarkerContextImpl =
			(FreeMarkerContextImpl)freeMarkerContext;

		PACLPolicy threadLocalPACLPolicy =
			PortalSecurityManagerThreadLocal.getPACLPolicy();

		try {
			if (freeMarkerContextImpl instanceof PACLFreeMarkerContextImpl) {
				PACLFreeMarkerContextImpl paclContextImpl =
					(PACLFreeMarkerContextImpl)freeMarkerContextImpl;

				PortalSecurityManagerThreadLocal.setPACLPolicy(
					paclContextImpl.getPaclPolicy());
			}

			Template template = _configuration.getTemplate(
				freeMarkerTemplateId, StringPool.UTF8);

			template.process(freeMarkerContextImpl.getWrappedContext(), writer);
		}
		finally {
			if (freeMarkerContextImpl instanceof PACLFreeMarkerContextImpl) {
				PortalSecurityManagerThreadLocal.setPACLPolicy(
					threadLocalPACLPolicy);
			}
		}

		return true;
	}

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

	private static Log _log = LogFactoryUtil.getLog(FreeMarkerEngineImpl.class);

	private Map<ClassLoader, FreeMarkerContextImpl>
		_classLoaderFreeMarkerContexts =
			new ConcurrentHashMap<ClassLoader, FreeMarkerContextImpl>();
	private Configuration _configuration;
	private String _encoding;
	private Locale _locale;
	private FreeMarkerContextImpl _restrictedToolsContext;
	private FreeMarkerContextImpl _standardToolsContext;
	private StringTemplateLoader _stringTemplateLoader;
	private Constructor<?> _templateKeyConstructor;

}