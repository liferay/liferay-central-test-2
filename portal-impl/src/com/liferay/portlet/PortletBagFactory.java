/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet;

import com.liferay.portal.dao.shard.ShardPollerProcessorWrapper;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.OpenSearch;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MethodInvoker;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.xmlrpc.Method;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.poller.PollerProcessorUtil;
import com.liferay.portal.pop.POPServerUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.xmlrpc.XmlRpcServlet;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.expando.model.CustomAttributesDisplay;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialRequestInterpreter;
import com.liferay.portlet.social.model.impl.SocialActivityInterpreterImpl;
import com.liferay.portlet.social.model.impl.SocialRequestInterpreterImpl;
import com.liferay.portlet.social.service.SocialActivityInterpreterLocalServiceUtil;
import com.liferay.portlet.social.service.SocialRequestInterpreterLocalServiceUtil;
import com.liferay.util.portlet.PortletProps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.portlet.PreferencesValidator;

import javax.servlet.ServletContext;

/**
 * <a href="PortletBagFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Ivica Cardic
 * @author Raymond Augé
 */
public class PortletBagFactory {

	public static PortletBag create(
			Portlet portlet, ServletContext servletContext,
			ClassLoader portletClassLoader)
		throws Exception {

		PortletApp portletApp = portlet.getPortletApp();

		if (!portletApp.isWARFile()) {
			String contextPath = PortalUtil.getPathContext();

			servletContext = ServletContextPool.get(contextPath);

			portletClassLoader = PortalClassLoaderUtil.getClassLoader();
		}

		Class<?> portletClass = null;

		try {
			portletClass = portletClassLoader.loadClass(
				portlet.getPortletClass());
		}
		catch (Throwable e) {
			_log.error(e, e);

			PortletLocalServiceUtil.destroyPortlet(portlet);

			return null;
		}

		javax.portlet.Portlet portletInstance =
			(javax.portlet.Portlet)portletClass.newInstance();

		ConfigurationAction configurationActionInstance = null;

		if (Validator.isNotNull(portlet.getConfigurationActionClass())) {
			configurationActionInstance = (ConfigurationAction)newInstance(
				portletClassLoader, ConfigurationAction.class,
				portlet.getConfigurationActionClass());
		}

		Indexer indexerInstance = null;

		if (Validator.isNotNull(portlet.getIndexerClass())) {
			indexerInstance = (Indexer)newInstance(
				portletClassLoader, Indexer.class, portlet.getIndexerClass());

			IndexerRegistryUtil.register(indexerInstance);
		}

		OpenSearch openSearchInstance = null;

		if (Validator.isNotNull(portlet.getOpenSearchClass())) {
			openSearchInstance = (OpenSearch)newInstance(
				portletClassLoader, OpenSearch.class,
				portlet.getOpenSearchClass());
		}

		initSchedulers(portlet, portletClassLoader);

		FriendlyURLMapper friendlyURLMapperInstance = null;

		if (Validator.isNotNull(portlet.getFriendlyURLMapperClass())) {
			friendlyURLMapperInstance = (FriendlyURLMapper)newInstance(
				portletClassLoader, FriendlyURLMapper.class,
				portlet.getFriendlyURLMapperClass());
		}

		URLEncoder urlEncoderInstance = null;

		if (Validator.isNotNull(portlet.getURLEncoderClass())) {
			urlEncoderInstance = (URLEncoder)newInstance(
				portletClassLoader, URLEncoder.class,
				portlet.getURLEncoderClass());
		}

		PortletDataHandler portletDataHandlerInstance = null;

		if (Validator.isNotNull(portlet.getPortletDataHandlerClass())) {
			portletDataHandlerInstance = (PortletDataHandler)newInstance(
				portletClassLoader, PortletDataHandler.class,
				portlet.getPortletDataHandlerClass());
		}

		PortletLayoutListener portletLayoutListenerInstance = null;

		if (Validator.isNotNull(portlet.getPortletLayoutListenerClass())) {
			portletLayoutListenerInstance = (PortletLayoutListener)newInstance(
				portletClassLoader, PortletLayoutListener.class,
				portlet.getPortletLayoutListenerClass());
		}

		PollerProcessor pollerProcessorInstance = null;

		if (Validator.isNotNull(portlet.getPollerProcessorClass())) {
			pollerProcessorInstance = (PollerProcessor)newInstance(
				portletClassLoader, PollerProcessor.class,
				portlet.getPollerProcessorClass());

			PollerProcessorUtil.addPollerProcessor(
				portlet.getPortletId(),
				new ShardPollerProcessorWrapper(pollerProcessorInstance));
		}

		com.liferay.portal.kernel.pop.MessageListener
			popMessageListenerInstance = null;

		if (Validator.isNotNull(portlet.getPopMessageListenerClass())) {
			popMessageListenerInstance =
				(com.liferay.portal.kernel.pop.MessageListener)newInstance(
					portletClassLoader,
					com.liferay.portal.kernel.pop.MessageListener.class,
					portlet.getPopMessageListenerClass());

			POPServerUtil.addListener(popMessageListenerInstance);
		}

		SocialActivityInterpreter socialActivityInterpreterInstance = null;

		if (Validator.isNotNull(portlet.getSocialActivityInterpreterClass())) {
			socialActivityInterpreterInstance =
				(SocialActivityInterpreter)newInstance(
					portletClassLoader, SocialActivityInterpreter.class,
					portlet.getSocialActivityInterpreterClass());

			socialActivityInterpreterInstance =
				new SocialActivityInterpreterImpl(
					portlet.getPortletId(), socialActivityInterpreterInstance);

			SocialActivityInterpreterLocalServiceUtil.addActivityInterpreter(
				socialActivityInterpreterInstance);
		}

		SocialRequestInterpreter socialRequestInterpreterInstance = null;

		if (Validator.isNotNull(portlet.getSocialRequestInterpreterClass())) {
			socialRequestInterpreterInstance =
				(SocialRequestInterpreter)newInstance(
					portletClassLoader, SocialRequestInterpreter.class,
					portlet.getSocialRequestInterpreterClass());

			socialRequestInterpreterInstance = new SocialRequestInterpreterImpl(
				portlet.getPortletId(), socialRequestInterpreterInstance);

			SocialRequestInterpreterLocalServiceUtil.addRequestInterpreter(
				socialRequestInterpreterInstance);
		}

		WebDAVStorage webDAVStorageInstance = null;

		if (Validator.isNotNull(portlet.getWebDAVStorageClass())) {
			webDAVStorageInstance = (WebDAVStorage)newInstance(
				portletClassLoader, WebDAVStorage.class,
				portlet.getWebDAVStorageClass());

			webDAVStorageInstance.setToken(portlet.getWebDAVStorageToken());

			WebDAVUtil.addStorage(webDAVStorageInstance);
		}

		Method xmlRpcMethodInstance = null;

		if (Validator.isNotNull(portlet.getXmlRpcMethodClass())) {
			xmlRpcMethodInstance = (Method)newInstance(
				portletClassLoader, Method.class,
				portlet.getXmlRpcMethodClass());

			XmlRpcServlet.registerMethod(xmlRpcMethodInstance);
		}

		ControlPanelEntry controlPanelEntryInstance = null;

		if (Validator.isNotNull(portlet.getControlPanelEntryClass())) {
			controlPanelEntryInstance = (ControlPanelEntry)newInstance(
				portletClassLoader, ControlPanelEntry.class,
				portlet.getControlPanelEntryClass());
		}

		List<AssetRendererFactory> assetRendererFactoryInstances =
			new ArrayList<AssetRendererFactory>();

		for (String assetRendererFactoryClass :
				portlet.getAssetRendererFactoryClasses()) {

			AssetRendererFactory assetRendererFactoryInstance =
				(AssetRendererFactory)newInstance(
					portletClassLoader, AssetRendererFactory.class,
					assetRendererFactoryClass);

			assetRendererFactoryInstance.setClassNameId(
				PortalUtil.getClassNameId(
					assetRendererFactoryInstance.getClassName()));
			assetRendererFactoryInstance.setPortletId(portlet.getPortletId());

			assetRendererFactoryInstances.add(assetRendererFactoryInstance);

			AssetRendererFactoryRegistryUtil.register(
				assetRendererFactoryInstance);
		}

		List<CustomAttributesDisplay> customAttributesDisplayInstances =
			new ArrayList<CustomAttributesDisplay>();

		for (String customAttributesDisplayClass :
				portlet.getCustomAttributesDisplayClasses()) {

			CustomAttributesDisplay customAttributesDisplayInstance =
				(CustomAttributesDisplay)newInstance(
					portletClassLoader, CustomAttributesDisplay.class,
					customAttributesDisplayClass);

			customAttributesDisplayInstance.setClassNameId(
				PortalUtil.getClassNameId(
					customAttributesDisplayInstance.getClassName()));
			customAttributesDisplayInstance.setPortletId(
				portlet.getPortletId());

			customAttributesDisplayInstances.add(
				customAttributesDisplayInstance);
		}

		List<WorkflowHandler> workflowHandlerInstances =
			new ArrayList<WorkflowHandler>();

		for (String workflowHandlerClass :
				portlet.getWorkflowHandlerClasses()) {

			WorkflowHandler workflowHandlerInstance =
				(WorkflowHandler)newInstance(
					portletClassLoader, WorkflowHandler.class,
					workflowHandlerClass);

			workflowHandlerInstances.add(workflowHandlerInstance);

			WorkflowHandlerRegistryUtil.register(workflowHandlerInstance);
		}

		PreferencesValidator preferencesValidatorInstance = null;

		if (Validator.isNotNull(portlet.getPreferencesValidator())) {
			preferencesValidatorInstance = (PreferencesValidator)newInstance(
				portletClassLoader, PreferencesValidator.class,
				portlet.getPreferencesValidator());

			try {
				if (PropsValues.PREFERENCE_VALIDATE_ON_STARTUP) {
					preferencesValidatorInstance.validate(
						PortletPreferencesSerializer.fromDefaultXML(
							portlet.getDefaultPreferences()));
				}
			}
			catch (Exception e) {
				_log.warn(
					"Portlet with the name " + portlet.getPortletId() +
						" does not have valid default preferences");
			}
		}

		Map<String, ResourceBundle> resourceBundles = null;

		if (Validator.isNotNull(portlet.getResourceBundle())) {
			resourceBundles = new HashMap<String, ResourceBundle>();

			initResourceBundle(
				resourceBundles, portlet, portletClassLoader,
				LocaleUtil.getDefault());

			String[] supportedLocales = portlet.getSupportedLocales().toArray(
				new String[0]);

			if (supportedLocales.length == 0) {
				supportedLocales = PropsValues.LOCALES;
			}

			for (String supportedLocale : supportedLocales) {
				Locale locale = LocaleUtil.fromLanguageId(supportedLocale);

				initResourceBundle(
					resourceBundles, portlet, portletClassLoader, locale);
			}
		}

		PortletBag portletBag = new PortletBagImpl(
			portlet.getPortletId(), servletContext, portletInstance,
			configurationActionInstance, indexerInstance, openSearchInstance,
			friendlyURLMapperInstance, urlEncoderInstance,
			portletDataHandlerInstance, portletLayoutListenerInstance,
			pollerProcessorInstance, popMessageListenerInstance,
			socialActivityInterpreterInstance, socialRequestInterpreterInstance,
			webDAVStorageInstance, xmlRpcMethodInstance,
			controlPanelEntryInstance, assetRendererFactoryInstances,
			customAttributesDisplayInstances, workflowHandlerInstances,
			preferencesValidatorInstance, resourceBundles);

		PortletBagPool.put(portlet.getPortletId(), portletBag);

		try {
			PortletInstanceFactoryUtil.create(portlet, servletContext);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return portletBag;
	}

	protected static void initResourceBundle(
		Map<String, ResourceBundle> resourceBundles, Portlet portlet,
		ClassLoader portletClassLoader, Locale locale) {

		try {
			ResourceBundle resourceBundle = ResourceBundle.getBundle(
				portlet.getResourceBundle(), locale, portletClassLoader);

			resourceBundles.put(
				LocaleUtil.toLanguageId(locale), resourceBundle);
		}
		catch (MissingResourceException mre) {
			_log.warn(mre.getMessage());
		}
	}

	protected static void initScheduler(
			SchedulerEntry schedulerEntry, ClassLoader portletClassLoader)
		throws Exception {

		String propertyKey = schedulerEntry.getPropertyKey();

		if (Validator.isNotNull(propertyKey)) {
			String triggerValue = null;

			Thread currentThread = Thread.currentThread();

			ClassLoader contextClassLoader =
				currentThread.getContextClassLoader();

			try {
				currentThread.setContextClassLoader(portletClassLoader);

				MethodWrapper methodWrapper = new MethodWrapper(
					PortletProps.class.getName(), "get", propertyKey);

				triggerValue = (String)MethodInvoker.invoke(
					methodWrapper, false);
			}
			finally {
				currentThread.setContextClassLoader(contextClassLoader);
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Scheduler property key " + propertyKey +
						" has trigger value " + triggerValue);
			}

			if (Validator.isNull(triggerValue)) {
				throw new SchedulerException(
					"Property key " + propertyKey + " requires a value");
			}

			schedulerEntry.setTriggerValue(triggerValue);
		}

		SchedulerEngineUtil.schedule(schedulerEntry, portletClassLoader);
	}

	protected static void initSchedulers(
			Portlet portlet, ClassLoader portletClassLoader)
		throws Exception {

		if (!PropsValues.SCHEDULER_ENABLED){
			return;
		}

		List<SchedulerEntry> schedulerEntries = portlet.getSchedulerEntries();

		if ((schedulerEntries == null) || schedulerEntries.isEmpty()) {
			return;
		}

		for (SchedulerEntry schedulerEntry : schedulerEntries) {
			initScheduler(schedulerEntry, portletClassLoader);
		}
	}

	protected static Object newInstance(
			ClassLoader portletClassLoader, Class<?> interfaceClass,
			String implClassName)
		throws Exception {

		return ProxyFactory.newInstance(
			portletClassLoader, interfaceClass, implClassName);
	}

	protected static Object newInstance(
			ClassLoader portletClassLoader, Class<?>[] interfaceClasses,
			String implClassName)
		throws Exception {

		return ProxyFactory.newInstance(
			portletClassLoader, interfaceClasses, implClassName);
	}

	private static Log _log = LogFactoryUtil.getLog(PortletBagFactory.class);

}