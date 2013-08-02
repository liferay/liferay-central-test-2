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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.security.pacl.NotPrivileged;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.velocity.VelocityContext;
import com.liferay.portal.kernel.velocity.VelocityEngine;
import com.liferay.portal.kernel.velocity.VelocityVariablesUtil;
import com.liferay.portal.template.TemplateControlContext;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.Writer;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.Template;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.ResourceManager;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;

/**
 * @author Raymond Augé
 */
@DoPrivileged
public class VelocityEngineImpl implements VelocityEngine {

	public VelocityEngineImpl() {
	}

	@Override
	public void clearClassLoader(ClassLoader classLoader) {
		_classLoaderToolsContextsMap.remove(classLoader);
	}

	@Override
	public void flushTemplate(String velocityTemplateId) {
		StringResourceRepository stringResourceRepository =
			StringResourceLoader.getRepository();

		if (stringResourceRepository != null) {
			stringResourceRepository.removeStringResource(velocityTemplateId);
		}

		LiferayResourceCacheUtil.remove(
			_getResourceCacheKey(velocityTemplateId));
	}

	@NotPrivileged
	@Override
	public VelocityContext getEmptyContext() {
		return new VelocityContextImpl();
	}

	@NotPrivileged
	@Override
	public VelocityContext getRestrictedToolsContext() {
		return _getToolsContext(_RESTRICTED);
	}

	@NotPrivileged
	@Override
	public VelocityContext getStandardToolsContext() {
		return _getToolsContext(_STANDARD);
	}

	public TemplateControlContext getTemplateControlContext() {
		return _pacl.getTemplateControlContext();
	}

	@NotPrivileged
	@Override
	public VelocityContext getWrappedClassLoaderToolsContext() {
		return new VelocityContextImpl(_getToolsContext(_STANDARD));
	}

	@NotPrivileged
	@Override
	public VelocityContext getWrappedRestrictedToolsContext() {
		return new VelocityContextImpl(_getToolsContext(_RESTRICTED));
	}

	@NotPrivileged
	@Override
	public VelocityContext getWrappedStandardToolsContext() {
		return new VelocityContextImpl(_getToolsContext(_STANDARD));
	}

	@Override
	public void init() throws Exception {
		if (_velocityEngine != null) {
			return;
		}

		_velocityEngine = new org.apache.velocity.app.VelocityEngine();

		LiferayResourceLoader.setVelocityResourceListeners(
			PropsValues.VELOCITY_ENGINE_RESOURCE_LISTENERS);

		ExtendedProperties extendedProperties = new FastExtendedProperties();

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.EVENTHANDLER_METHODEXCEPTION,
			LiferayMethodExceptionEventHandler.class.getName());

		extendedProperties.setProperty(
			RuntimeConstants.INTROSPECTOR_RESTRICT_CLASSES,
			StringUtil.merge(PropsValues.VELOCITY_ENGINE_RESTRICTED_CLASSES));

		extendedProperties.setProperty(
			RuntimeConstants.INTROSPECTOR_RESTRICT_PACKAGES,
			StringUtil.merge(PropsValues.VELOCITY_ENGINE_RESTRICTED_PACKAGES));

		extendedProperties.setProperty(_RESOURCE_LOADER, "string,servlet");

		extendedProperties.setProperty(
			"string." + _RESOURCE_LOADER + ".cache",
			String.valueOf(
				PropsValues.VELOCITY_ENGINE_RESOURCE_MANAGER_CACHE_ENABLED));

		extendedProperties.setProperty(
			"string." + _RESOURCE_LOADER + ".class",
			StringResourceLoader.class.getName());

		extendedProperties.setProperty(
			"string." + _RESOURCE_LOADER + ".repository.class",
			StringResourceRepositoryImpl.class.getName());

		extendedProperties.setProperty(
			"servlet." + _RESOURCE_LOADER + ".cache",
			String.valueOf(
				PropsValues.VELOCITY_ENGINE_RESOURCE_MANAGER_CACHE_ENABLED));

		extendedProperties.setProperty(
			"servlet." + _RESOURCE_LOADER + ".class",
			LiferayResourceLoader.class.getName());

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.RESOURCE_MANAGER_CLASS,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_RESOURCE_MANAGER));

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.RESOURCE_MANAGER_CACHE_CLASS,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_RESOURCE_MANAGER_CACHE));

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.VM_LIBRARY,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_VELOCIMACRO_LIBRARY));

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.VM_LIBRARY_AUTORELOAD,
			String.valueOf(
				!PropsValues.VELOCITY_ENGINE_RESOURCE_MANAGER_CACHE_ENABLED));

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.
				VM_PERM_ALLOW_INLINE_REPLACE_GLOBAL,
			String.valueOf(
				!PropsValues.VELOCITY_ENGINE_RESOURCE_MANAGER_CACHE_ENABLED));

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.RUNTIME_LOG_LOGSYSTEM_CLASS,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_LOGGER));

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.RUNTIME_LOG_LOGSYSTEM +
				".log4j.category",
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_LOGGER_CATEGORY));

		_velocityEngine.setExtendedProperties(extendedProperties);

		_velocityEngine.init();

		_restrictedToolsContext = new VelocityContextImpl();

		VelocityVariablesUtil.insertHelperUtilities(
			_restrictedToolsContext,
			PropsValues.JOURNAL_TEMPLATE_VELOCITY_RESTRICTED_VARIABLES);

		_standardToolsContext = new VelocityContextImpl();

		VelocityVariablesUtil.insertHelperUtilities(
			_standardToolsContext, null);
	}

	@NotPrivileged
	@Override
	public boolean mergeTemplate(
			String velocityTemplateId, String velocityTemplateContent,
			VelocityContext velocityContext, Writer writer)
		throws Exception {

		Template template = AccessController.doPrivileged(
			new DoGetTemplatePrivilegedAction(
				velocityTemplateId, velocityTemplateContent, StringPool.UTF8));

		VelocityContextImpl velocityContextImpl =
			(VelocityContextImpl)velocityContext;

		template.merge(velocityContextImpl.getWrappedVelocityContext(), writer);

		return true;
	}

	@NotPrivileged
	@Override
	public boolean mergeTemplate(
			String velocityTemplateId, VelocityContext velocityContext,
			Writer writer)
		throws Exception {

		return mergeTemplate(velocityTemplateId, null, velocityContext, writer);
	}

	@NotPrivileged
	@Override
	public boolean resourceExists(String resource) {
		return _velocityEngine.resourceExists(resource);
	}

	private VelocityContextImpl _doGetToolsContext(
		ClassLoader classLoader, String templateContextType) {

		Map<String, VelocityContextImpl> toolsContextMap =
			_classLoaderToolsContextsMap.get(classLoader);

		if (toolsContextMap == null) {
			toolsContextMap =
				new ConcurrentHashMap<String, VelocityContextImpl>();

			_classLoaderToolsContextsMap.put(classLoader, toolsContextMap);
		}

		VelocityContextImpl velocityContextImpl = toolsContextMap.get(
			templateContextType);

		if (velocityContextImpl != null) {
			return velocityContextImpl;
		}

		velocityContextImpl = new VelocityContextImpl();

		if (_RESTRICTED.equals(templateContextType)) {
			VelocityVariablesUtil.insertHelperUtilities(
				velocityContextImpl,
				PropsValues.JOURNAL_TEMPLATE_VELOCITY_RESTRICTED_VARIABLES);
		}
		else {
			VelocityVariablesUtil.insertHelperUtilities(
				velocityContextImpl, null);
		}

		toolsContextMap.put(templateContextType, velocityContextImpl);

		return velocityContextImpl;
	}

	private String _getResourceCacheKey(String velocityTemplateId) {
		return _RESOURCE_TEMPLATE_NAME_SPACE.concat(velocityTemplateId);
	}

	private VelocityContextImpl _getToolsContext(String templateContextType) {
		TemplateControlContext templateControlContext =
			getTemplateControlContext();

		AccessControlContext accessControlContext =
			templateControlContext.getAccessControlContext();
		ClassLoader classLoader = templateControlContext.getClassLoader();

		if (accessControlContext == null) {
			return _doGetToolsContext(classLoader, templateContextType);
		}

		return AccessController.doPrivileged(
			new DoGetToolsContextPrivilegedAction(
				classLoader, templateContextType),
			accessControlContext);
	}

	private static final String _RESOURCE_LOADER =
		org.apache.velocity.app.VelocityEngine.RESOURCE_LOADER;

	private static final String _RESOURCE_TEMPLATE_NAME_SPACE = String.valueOf(
		ResourceManager.RESOURCE_TEMPLATE);

	private static final String _RESTRICTED = "RESTRICTED";

	private static final String _STANDARD = "STANDARD";

	private static Log _log = LogFactoryUtil.getLog(VelocityEngineImpl.class);

	private static PACL _pacl = new NoPACL();

	private Map<ClassLoader, Map<String, VelocityContextImpl>>
		_classLoaderToolsContextsMap = new ConcurrentHashMap
			<ClassLoader, Map<String, VelocityContextImpl>>();
	private VelocityContextImpl _restrictedToolsContext;
	private VelocityContextImpl _standardToolsContext;
	private org.apache.velocity.app.VelocityEngine _velocityEngine;

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
			String velocityTemplateId, String velocityTemplateContent,
			String encoding) {

			_velocityTemplateId = velocityTemplateId;
			_velocityTemplateContent = velocityTemplateContent;
			_encoding = encoding;
		}

		public Template run() throws Exception {
			if (Validator.isNotNull(_velocityTemplateContent)) {
				LiferayResourceCacheUtil.remove(
					_getResourceCacheKey(_velocityTemplateId));

				StringResourceRepository stringResourceRepository =
					StringResourceLoader.getRepository();

				stringResourceRepository.putStringResource(
					_velocityTemplateId, _velocityTemplateContent);

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Added " + _velocityTemplateId +
							" to the Velocity template repository");
				}
			}

			return _velocityEngine.getTemplate(_velocityTemplateId, _encoding);
		}

		private String _encoding;
		private String _velocityTemplateContent;
		private String _velocityTemplateId;

	}

	private class DoGetToolsContextPrivilegedAction
		implements PrivilegedAction<VelocityContextImpl> {

		public DoGetToolsContextPrivilegedAction(
			ClassLoader classLoader, String templateContextType) {

			_classLoader = classLoader;
			_templateContextType = templateContextType;
		}

		public VelocityContextImpl run() {
			return _doGetToolsContext(_classLoader, _templateContextType);
		}

		private ClassLoader _classLoader;
		private String _templateContextType;

	}

}