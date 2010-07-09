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

import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.OpenSearch;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.xmlrpc.Method;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.expando.model.CustomAttributesDisplay;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialRequestInterpreter;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.Portlet;
import javax.portlet.PreferencesValidator;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class PortletBagImpl implements PortletBag {

	public PortletBagImpl(
		String portletName, ServletContext servletContext,
		Portlet portletInstance,
		ConfigurationAction configurationActionInstance,
		Indexer indexerInstance, OpenSearch openSearchInstance,
		FriendlyURLMapper friendlyURLMapperInstance,
		URLEncoder urlEncoderInstance,
		PortletDataHandler portletDataHandlerInstance,
		PortletLayoutListener portletLayoutListenerInstance,
		PollerProcessor pollerProcessorInstance,
		MessageListener popMessageListenerInstance,
		SocialActivityInterpreter socialActivityInterpreterInstance,
		SocialRequestInterpreter socialRequestInterpreterInstance,
		WebDAVStorage webDAVStorageInstance, Method xmlRpcMethodInstance,
		ControlPanelEntry controlPanelEntryInstance,
		List<AssetRendererFactory> assetRendererFactoryInstances,
		List<CustomAttributesDisplay> customAttributesDisplayInstances,
		List<WorkflowHandler> workflowHandlerInstances,
		PreferencesValidator preferencesValidatorInstance,
		Map<String, ResourceBundle> resourceBundles) {

		_portletName = portletName;
		_servletContext = servletContext;
		_portletInstance = portletInstance;
		_configurationActionInstance = configurationActionInstance;
		_indexerInstance = indexerInstance;
		_openSearchInstance = openSearchInstance;
		_friendlyURLMapperInstance = friendlyURLMapperInstance;
		_urlEncoderInstance = urlEncoderInstance;
		_portletDataHandlerInstance = portletDataHandlerInstance;
		_portletLayoutListenerInstance = portletLayoutListenerInstance;
		_pollerProcessorInstance = pollerProcessorInstance;
		_popMessageListenerInstance = popMessageListenerInstance;
		_socialActivityInterpreterInstance = socialActivityInterpreterInstance;
		_socialRequestInterpreterInstance = socialRequestInterpreterInstance;
		_webDAVStorageInstance = webDAVStorageInstance;
		_xmlRpcMethodInstance = xmlRpcMethodInstance;
		_controlPanelEntryInstance = controlPanelEntryInstance;
		_assetRendererFactoryInstances = assetRendererFactoryInstances;
		_customAttributesDisplayInstances = customAttributesDisplayInstances;
		_workflowHandlerInstances = workflowHandlerInstances;
		_preferencesValidatorInstance = preferencesValidatorInstance;
		_resourceBundles = resourceBundles;
	}

	public Object clone() {
		return new PortletBagImpl(
			getPortletName(), getServletContext(), getPortletInstance(),
			getConfigurationActionInstance(), getIndexerInstance(),
			getOpenSearchInstance(), getFriendlyURLMapperInstance(),
			getURLEncoderInstance(), getPortletDataHandlerInstance(),
			getPortletLayoutListenerInstance(), getPollerProcessorInstance(),
			getPopMessageListenerInstance(),
			getSocialActivityInterpreterInstance(),
			getSocialRequestInterpreterInstance(), getWebDAVStorageInstance(),
			getXmlRpcMethodInstance(), getControlPanelEntryInstance(),
			getAssetRendererFactoryInstances(),
			getCustomAttributesDisplayInstances(),
			getWorkflowHandlerInstances(), getPreferencesValidatorInstance(),
			getResourceBundles());
	}

	public List<AssetRendererFactory> getAssetRendererFactoryInstances() {
		return _assetRendererFactoryInstances;
	}

	public ConfigurationAction getConfigurationActionInstance() {
		return _configurationActionInstance;
	}

	public ControlPanelEntry getControlPanelEntryInstance() {
		return _controlPanelEntryInstance;
	}

	public List<CustomAttributesDisplay> getCustomAttributesDisplayInstances() {
		return _customAttributesDisplayInstances;
	}

	public FriendlyURLMapper getFriendlyURLMapperInstance() {
		return _friendlyURLMapperInstance;
	}

	public Indexer getIndexerInstance() {
		return _indexerInstance;
	}

	public OpenSearch getOpenSearchInstance() {
		return _openSearchInstance;
	}

	public PollerProcessor getPollerProcessorInstance() {
		return _pollerProcessorInstance;
	}

	public MessageListener getPopMessageListenerInstance() {
		return _popMessageListenerInstance;
	}

	public PortletDataHandler getPortletDataHandlerInstance() {
		return _portletDataHandlerInstance;
	}

	public Portlet getPortletInstance() {
		return _portletInstance;
	}

	public PortletLayoutListener getPortletLayoutListenerInstance() {
		return _portletLayoutListenerInstance;
	}

	public String getPortletName() {
		return _portletName;
	}

	public PreferencesValidator getPreferencesValidatorInstance() {
		return _preferencesValidatorInstance;
	}

	public ResourceBundle getResourceBundle(Locale locale) {
		ResourceBundle resourceBundle = _resourceBundles.get(
			LocaleUtil.toLanguageId(locale));

		if (resourceBundle == null) {
			resourceBundle = _resourceBundles.get(locale.getLanguage());

			if (resourceBundle == null) {
				resourceBundle = _resourceBundles.get(
					LocaleUtil.toLanguageId(LocaleUtil.getDefault()));
			}
		}

		return resourceBundle;
	}

	public Map<String, ResourceBundle> getResourceBundles() {
		return _resourceBundles;
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	public SocialActivityInterpreter getSocialActivityInterpreterInstance() {
		return _socialActivityInterpreterInstance;
	}

	public SocialRequestInterpreter getSocialRequestInterpreterInstance() {
		return _socialRequestInterpreterInstance;
	}

	public URLEncoder getURLEncoderInstance() {
		return _urlEncoderInstance;
	}

	public WebDAVStorage getWebDAVStorageInstance() {
		return _webDAVStorageInstance;
	}

	public List<WorkflowHandler> getWorkflowHandlerInstances() {
		return _workflowHandlerInstances;
	}

	public Method getXmlRpcMethodInstance() {
		return _xmlRpcMethodInstance;
	}

	public void setPortletInstance(Portlet portletInstance) {
		_portletInstance = portletInstance;
	}

	public void setPortletName(String portletName) {
		_portletName = portletName;
	}

	private List<AssetRendererFactory> _assetRendererFactoryInstances;
	private ConfigurationAction _configurationActionInstance;
	private ControlPanelEntry _controlPanelEntryInstance;
	private List<CustomAttributesDisplay> _customAttributesDisplayInstances;
	private FriendlyURLMapper _friendlyURLMapperInstance;
	private Indexer _indexerInstance;
	private OpenSearch _openSearchInstance;
	private PollerProcessor _pollerProcessorInstance;
	private MessageListener _popMessageListenerInstance;
	private PortletDataHandler _portletDataHandlerInstance;
	private Portlet _portletInstance;
	private PortletLayoutListener _portletLayoutListenerInstance;
	private String _portletName;
	private PreferencesValidator _preferencesValidatorInstance;
	private Map<String, ResourceBundle> _resourceBundles;
	private ServletContext _servletContext;
	private SocialActivityInterpreter _socialActivityInterpreterInstance;
	private SocialRequestInterpreter _socialRequestInterpreterInstance;
	private URLEncoder _urlEncoderInstance;
	private WebDAVStorage _webDAVStorageInstance;
	private Method _xmlRpcMethodInstance;
	private List<WorkflowHandler> _workflowHandlerInstances;

}