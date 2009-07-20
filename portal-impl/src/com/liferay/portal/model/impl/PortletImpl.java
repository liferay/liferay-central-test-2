/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.job.Scheduler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.OpenSearch;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.lar.PortletDataHandler;
import com.liferay.portal.model.Plugin;
import com.liferay.portal.model.PluginSetting;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletFilter;
import com.liferay.portal.model.PortletInfo;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.webdav.WebDAVStorage;
import com.liferay.portlet.ControlPanelEntry;
import com.liferay.portlet.PortletBagImpl;
import com.liferay.portlet.PortletQNameUtil;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialRequestInterpreter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

public class PortletImpl extends PortletModelImpl implements Portlet {

	public PortletImpl() {
	}

	public PortletImpl(long companyId, String portletId) {
		setCompanyId(companyId);
		setPortletId(portletId);
		setStrutsPath(portletId);
		setActive(true);
		_headerPortalCss = new ArrayList<String>();
		_headerPortletCss = new ArrayList<String>();
		_headerPortalJavaScript = new ArrayList<String>();
		_headerPortletJavaScript = new ArrayList<String>();
		_footerPortalCss = new ArrayList<String>();
		_footerPortletCss = new ArrayList<String>();
		_footerPortalJavaScript = new ArrayList<String>();
		_footerPortletJavaScript = new ArrayList<String>();
		_unlinkedRoles = new HashSet<String>();
		_roleMappers = new LinkedHashMap<String, String>();
		_initParams = new HashMap<String, String>();
		_portletModes = new HashMap<String, Set<String>>();
		_windowStates = new HashMap<String, Set<String>>();
		_supportedLocales = new HashSet<String>();
		_portletFilters = new LinkedHashMap<String, PortletFilter>();
		_processingEvents = new HashSet<QName>();
		_publishingEvents = new HashSet<QName>();
		_publicRenderParameters = new HashSet<PublicRenderParameter>();
	}

	public PortletImpl(
		String portletId, PluginPackage pluginPackage,
		PluginSetting pluginSetting, long companyId, long timestamp,
		String icon, String virtualPath, String strutsPath, String portletName,
		String displayName, String portletClass,
		String configurationActionClass, String indexerClass,
		String openSearchClass, String schedulerClass, String portletURLClass,
		String friendlyURLMapperClass, String urlEncoderClass,
		String portletDataHandlerClass, String portletLayoutListenerClass,
		String pollerProcessorClass, String popMessageListenerClass,
		String socialActivityInterpreterClass,
		String socialRequestInterpreterClass, String webDAVStorageToken,
		String webDAVStorageClass, String controlPanelEntryCategory,
		double controlPanelEntryWeight, String controlPanelClass,
		String defaultPreferences, String preferencesValidator,
		boolean preferencesCompanyWide, boolean preferencesUniquePerLayout,
		boolean preferencesOwnedByGroup, boolean useDefaultTemplate,
		boolean showPortletAccessDenied, boolean showPortletInactive,
		boolean actionURLRedirect, boolean restoreCurrentView,
		boolean maximizeEdit, boolean maximizeHelp, boolean popUpPrint,
		boolean layoutCacheable, boolean instanceable, boolean scopeable,
		String userPrincipalStrategy, boolean privateRequestAttributes,
		boolean privateSessionAttributes, int renderWeight, boolean ajaxable,
		List<String> headerPortalCss, List<String> headerPortletCss,
		List<String> headerPortalJavaScript,
		List<String> headerPortletJavaScript, List<String> footerPortalCss,
		List<String> footerPortletCss, List<String> footerPortalJavaScript,
		List<String> footerPortletJavaScript, String cssClassWrapper,
		String facebookIntegration, boolean addDefaultResource, String roles,
		Set<String> unlinkedRoles, Map<String, String> roleMappers,
		boolean system, boolean active, boolean include,
		Map<String, String> initParams, Integer expCache,
		Map<String, Set<String>> portletModes,
		Map<String, Set<String>> windowStates, Set<String> supportedLocales,
		String resourceBundle, PortletInfo portletInfo,
		Map<String, PortletFilter> portletFilters, Set<QName> processingEvents,
		Set<QName> publishingEvents,
		Set<PublicRenderParameter> publicRenderParameters,
		PortletApp portletApp) {

		setPortletId(portletId);
		_pluginPackage = pluginPackage;
		_defaultPluginSetting = pluginSetting;
		setCompanyId(companyId);
		_timestamp = timestamp;
		_icon = icon;
		_virtualPath = virtualPath;
		_strutsPath = strutsPath;
		_portletName = portletName;
		_displayName = displayName;
		_portletClass = portletClass;
		_configurationActionClass = configurationActionClass;
		_indexerClass = indexerClass;
		_openSearchClass = openSearchClass;
		_schedulerClass = schedulerClass;
		_portletURLClass = portletURLClass;
		_friendlyURLMapperClass = friendlyURLMapperClass;
		_urlEncoderClass = urlEncoderClass;
		_portletDataHandlerClass = portletDataHandlerClass;
		_portletLayoutListenerClass = portletLayoutListenerClass;
		_pollerProcessorClass = pollerProcessorClass;
		_popMessageListenerClass = popMessageListenerClass;
		_socialActivityInterpreterClass = socialActivityInterpreterClass;
		_socialRequestInterpreterClass = socialRequestInterpreterClass;
		_webDAVStorageToken = webDAVStorageToken;
		_webDAVStorageClass = webDAVStorageClass;
		_controlPanelEntryCategory = controlPanelEntryCategory;
		_controlPanelEntryWeight = controlPanelEntryWeight;
		_controlPanelEntryClass = controlPanelClass;
		_defaultPreferences = defaultPreferences;
		_preferencesValidator = preferencesValidator;
		_preferencesCompanyWide = preferencesCompanyWide;
		_preferencesUniquePerLayout = preferencesUniquePerLayout;
		_preferencesOwnedByGroup = preferencesOwnedByGroup;
		_useDefaultTemplate = useDefaultTemplate;
		_showPortletAccessDenied = showPortletAccessDenied;
		_showPortletInactive = showPortletInactive;
		_actionURLRedirect = actionURLRedirect;
		_restoreCurrentView = restoreCurrentView;
		_maximizeEdit = maximizeEdit;
		_maximizeHelp = maximizeHelp;
		_popUpPrint = popUpPrint;
		_layoutCacheable = layoutCacheable;
		_instanceable = instanceable;
		_scopeable = scopeable;
		_userPrincipalStrategy = userPrincipalStrategy;
		_privateRequestAttributes = privateRequestAttributes;
		_privateSessionAttributes = privateSessionAttributes;
		_renderWeight = renderWeight;
		_ajaxable = ajaxable;
		_headerPortalCss = headerPortalCss;
		_headerPortletCss = headerPortletCss;
		_headerPortalJavaScript = headerPortalJavaScript;
		_headerPortletJavaScript = headerPortletJavaScript;
		_footerPortalCss = footerPortalCss;
		_footerPortletCss = footerPortletCss;
		_footerPortalJavaScript = footerPortalJavaScript;
		_footerPortletJavaScript = footerPortletJavaScript;
		_cssClassWrapper = cssClassWrapper;
		_facebookIntegration = facebookIntegration;
		_scopeable = scopeable;
		_addDefaultResource = addDefaultResource;
		setRoles(roles);
		_unlinkedRoles = unlinkedRoles;
		_roleMappers = roleMappers;
		_system = system;
		setActive(active);
		_include = include;
		_initParams = initParams;
		_expCache = expCache;
		_portletModes = portletModes;
		_windowStates = windowStates;
		_supportedLocales = supportedLocales;
		_resourceBundle = resourceBundle;
		_portletInfo = portletInfo;
		_portletFilters = portletFilters;
		setProcessingEvents(processingEvents);
		setPublishingEvents(publishingEvents);
		setPublicRenderParameters(publicRenderParameters);
		_portletApp = portletApp;

		if (_instanceable) {
			_clonedInstances = new Hashtable<String, Portlet>();
		}
	}

	public String getRootPortletId() {
		return PortletConstants.getRootPortletId(getPortletId());
	}

	public String getInstanceId() {
		return PortletConstants.getInstanceId(getPortletId());
	}

	public String getPluginId() {
		return getRootPortletId();
	}

	public String getPluginType() {
		return Plugin.TYPE_PORTLET;
	}

	public PluginPackage getPluginPackage() {
		return _pluginPackage;
	}

	public void setPluginPackage(PluginPackage pluginPackage) {
		_pluginPackage = pluginPackage;
	}

	public PluginSetting getDefaultPluginSetting() {
		return _defaultPluginSetting;
	}

	public void setDefaultPluginSetting(PluginSetting pluginSetting) {
		_defaultPluginSetting = pluginSetting;
	}

	public long getTimestamp() {
		return _timestamp;
	}

	public void setTimestamp(long timestamp) {
		_timestamp = timestamp;
	}

	public String getIcon() {
		return _icon;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public String getVirtualPath() {
		return _virtualPath;
	}

	public void setVirtualPath(String virtualPath) {
		if (_portletApp.isWARFile() && Validator.isNull(virtualPath)) {
			virtualPath = PropsValues.PORTLET_VIRTUAL_PATH;
		}

		_virtualPath = virtualPath;
	}

	public String getStrutsPath() {
		return _strutsPath;
	}

	public void setStrutsPath(String strutsPath) {
		_strutsPath = strutsPath;
	}

	public String getPortletName() {
		return _portletName;
	}

	public void setPortletName(String portletName) {
		_portletName = portletName;
	}

	public String getDisplayName() {
		return _displayName;
	}

	public void setDisplayName(String displayName) {
		_displayName = displayName;
	}

	public String getPortletClass() {
		return _portletClass;
	}

	public void setPortletClass(String portletClass) {
		_portletClass = portletClass;
	}

	public String getConfigurationActionClass() {
		return _configurationActionClass;
	}

	public void setConfigurationActionClass(String configurationActionClass) {
		_configurationActionClass = configurationActionClass;
	}

	public ConfigurationAction getConfigurationActionInstance() {
		if (Validator.isNull(getConfigurationActionClass())) {
			return null;
		}

		if (_portletApp.isWARFile()) {
			PortletBag portletBag = PortletBagPool.get(getRootPortletId());

			return portletBag.getConfigurationActionInstance();
		}

		return (ConfigurationAction)InstancePool.get(
			getConfigurationActionClass());
	}

	public String getIndexerClass() {
		return _indexerClass;
	}

	public void setIndexerClass(String indexerClass) {
		_indexerClass = indexerClass;
	}

	public Indexer getIndexerInstance() {
		if (Validator.isNull(getIndexerClass())) {
			return null;
		}

		if (_portletApp.isWARFile()) {
			PortletBag portletBag = PortletBagPool.get(getRootPortletId());

			return portletBag.getIndexerInstance();
		}

		return (Indexer)InstancePool.get(getIndexerClass());
	}

	public String getOpenSearchClass() {
		return _openSearchClass;
	}

	public void setOpenSearchClass(String openSearchClass) {
		_openSearchClass = openSearchClass;
	}

	public OpenSearch getOpenSearchInstance() {
		if (Validator.isNull(getOpenSearchClass())) {
			return null;
		}

		if (_portletApp.isWARFile()) {
			PortletBag portletBag = PortletBagPool.get(getRootPortletId());

			return portletBag.getOpenSearchInstance();
		}

		return (OpenSearch)InstancePool.get(getOpenSearchClass());
	}

	public String getSchedulerClass() {
		return _schedulerClass;
	}

	public void setSchedulerClass(String schedulerClass) {
		_schedulerClass = schedulerClass;
	}

	public Scheduler getSchedulerInstance() {
		if (Validator.isNull(getSchedulerClass())) {
			return null;
		}

		if (_portletApp.isWARFile()) {
			PortletBag portletBag = PortletBagPool.get(getRootPortletId());

			return portletBag.getSchedulerInstance();
		}

		return (Scheduler)InstancePool.get(getSchedulerClass());
	}

	public String getPortletURLClass() {
		return _portletURLClass;
	}

	public void setPortletURLClass(String portletURLClass) {
		_portletURLClass = portletURLClass;
	}

	public String getFriendlyURLMapperClass() {
		return _friendlyURLMapperClass;
	}

	public void setFriendlyURLMapperClass(String friendlyURLMapperClass) {
		_friendlyURLMapperClass = friendlyURLMapperClass;
	}

	public FriendlyURLMapper getFriendlyURLMapperInstance() {
		if (Validator.isNull(getFriendlyURLMapperClass())) {
			return null;
		}

		if (_portletApp.isWARFile()) {
			PortletBag portletBag = PortletBagPool.get(getRootPortletId());

			return portletBag.getFriendlyURLMapperInstance();
		}

		return (FriendlyURLMapper)InstancePool.get(getFriendlyURLMapperClass());
	}

	public String getURLEncoderClass() {
		return _urlEncoderClass;
	}

	public void setURLEncoderClass(String urlEncoderClass) {
		_urlEncoderClass = urlEncoderClass;
	}

	public URLEncoder getURLEncoderInstance() {
		if (Validator.isNull(getURLEncoderClass())) {
			return null;
		}

		if (_portletApp.isWARFile()) {
			PortletBag portletBag = PortletBagPool.get(getRootPortletId());

			return portletBag.getURLEncoderInstance();
		}

		return (URLEncoder)InstancePool.get(getURLEncoderClass());
	}

	public String getPortletDataHandlerClass() {
		return _portletDataHandlerClass;
	}

	public void setPortletDataHandlerClass(String portletDataHandlerClass) {
		_portletDataHandlerClass = portletDataHandlerClass;
	}

	public PortletDataHandler getPortletDataHandlerInstance() {
		if (Validator.isNull(getPortletDataHandlerClass())) {
			return null;
		}

		if (_portletApp.isWARFile()) {
			PortletBagImpl portletBagImpl = (PortletBagImpl)PortletBagPool.get(
				getRootPortletId());

			return portletBagImpl.getPortletDataHandlerInstance();
		}

		return (PortletDataHandler)InstancePool.get(
			getPortletDataHandlerClass());
	}

	public PortletLayoutListener getPortletLayoutListener() {
		if (Validator.isNull(getPortletLayoutListenerClass())) {
			return null;
		}

		return (PortletLayoutListener)InstancePool.get(
			getPortletLayoutListenerClass());
	}

	public String getPortletLayoutListenerClass() {
		return _portletLayoutListenerClass;
	}

	public void setPortletLayoutListenerClass(
		String portletLayoutListenerClass) {

		_portletLayoutListenerClass = portletLayoutListenerClass;
	}

	public PortletLayoutListener getPortletLayoutListenerInstance() {
		if (Validator.isNull(getPortletLayoutListenerClass())) {
			return null;
		}

		if (_portletApp.isWARFile()) {
			PortletBag portletBag = PortletBagPool.get(getRootPortletId());

			return portletBag.getPortletLayoutListenerInstance();
		}

		return (PortletLayoutListener)InstancePool.get(
			getPortletLayoutListenerClass());
	}

	public String getPollerProcessorClass() {
		return _pollerProcessorClass;
	}

	public void setPollerProcessorClass(String pollerProcessorClass) {
		_pollerProcessorClass = pollerProcessorClass;
	}

	public PollerProcessor getPollerProcessorInstance() {
		if (Validator.isNull(getPollerProcessorClass())) {
			return null;
		}

		if (_portletApp.isWARFile()) {
			PortletBag portletBag = PortletBagPool.get(getRootPortletId());

			return portletBag.getPollerProcessorInstance();
		}

		return (PollerProcessor)InstancePool.get(getPollerProcessorClass());
	}

	public String getPopMessageListenerClass() {
		return _popMessageListenerClass;
	}

	public void setPopMessageListenerClass(String popMessageListenerClass) {
		_popMessageListenerClass = popMessageListenerClass;
	}

	public MessageListener getPopMessageListenerInstance() {
		if (Validator.isNull(getPopMessageListenerClass())) {
			return null;
		}

		if (_portletApp.isWARFile()) {
			PortletBag portletBag = PortletBagPool.get(getRootPortletId());

			return portletBag.getPopMessageListenerInstance();
		}

		return (MessageListener)InstancePool.get(getPopMessageListenerClass());
	}

	public String getSocialActivityInterpreterClass() {
		return _socialActivityInterpreterClass;
	}

	public void setSocialActivityInterpreterClass(
		String socialActivityInterpreterClass) {

		_socialActivityInterpreterClass = socialActivityInterpreterClass;
	}

	public SocialActivityInterpreter getSocialActivityInterpreterInstance() {
		if (Validator.isNull(getSocialActivityInterpreterClass())) {
			return null;
		}

		if (_portletApp.isWARFile()) {
			PortletBagImpl portletBagImpl = (PortletBagImpl)PortletBagPool.get(
				getRootPortletId());

			return portletBagImpl.getSocialActivityInterpreterInstance();
		}

		return (SocialActivityInterpreter)InstancePool.get(
			getSocialActivityInterpreterClass());
	}

	public String getSocialRequestInterpreterClass() {
		return _socialRequestInterpreterClass;
	}

	public void setSocialRequestInterpreterClass(
		String socialRequestInterpreterClass) {

		_socialRequestInterpreterClass = socialRequestInterpreterClass;
	}

	public SocialRequestInterpreter getSocialRequestInterpreterInstance() {
		if (Validator.isNull(getSocialRequestInterpreterClass())) {
			return null;
		}

		if (_portletApp.isWARFile()) {
			PortletBagImpl portletBagImpl = (PortletBagImpl)PortletBagPool.get(
				getRootPortletId());

			return portletBagImpl.getSocialRequestInterpreterInstance();
		}

		return (SocialRequestInterpreter)InstancePool.get(
			getSocialRequestInterpreterClass());
	}

	public String getWebDAVStorageToken() {
		return _webDAVStorageToken;
	}

	public void setWebDAVStorageToken(String webDAVStorageToken) {
		_webDAVStorageToken = webDAVStorageToken;
	}

	public String getWebDAVStorageClass() {
		return _webDAVStorageClass;
	}

	public void setWebDAVStorageClass(String webDAVStorageClass) {
		_webDAVStorageClass = webDAVStorageClass;
	}

	public WebDAVStorage getWebDAVStorageInstance() {
		if (Validator.isNull(getWebDAVStorageClass())) {
			return null;
		}

		if (_portletApp.isWARFile()) {
			PortletBagImpl portletBagImpl = (PortletBagImpl)PortletBagPool.get(
				getRootPortletId());

			return portletBagImpl.getWebDAVStorageInstance();
		}

		return (WebDAVStorage)InstancePool.get(
			getWebDAVStorageClass());
	}

	public String getControlPanelEntryCategory() {
		return _controlPanelEntryCategory;
	}

	public void setControlPanelEntryCategory(String controlPanelEntryCategory) {
		_controlPanelEntryCategory = controlPanelEntryCategory;
	}

	public double getControlPanelEntryWeight() {
		return _controlPanelEntryWeight;
	}

	public void setControlPanelEntryWeight(double controlPanelEntryWeight) {
		_controlPanelEntryWeight = controlPanelEntryWeight;
	}

	public String getControlPanelEntryClass() {
		return _controlPanelEntryClass;
	}

	public void setControlPanelEntryClass(String controlPanelEntryClass) {
		_controlPanelEntryClass = controlPanelEntryClass;
	}

	public ControlPanelEntry getControlPanelEntryInstance() {
		if (Validator.isNull(getControlPanelEntryClass())) {
			return null;
		}

		if (_portletApp.isWARFile()) {
			PortletBagImpl portletBagImpl = (PortletBagImpl)PortletBagPool.get(
				getRootPortletId());

			return portletBagImpl.getControlPanelEntryInstance();
		}

		return (ControlPanelEntry)InstancePool.get(getControlPanelEntryClass());
	}

	public String getDefaultPreferences() {
		if (Validator.isNull(_defaultPreferences)) {
			return PortletConstants.DEFAULT_PREFERENCES;
		}
		else {
			return _defaultPreferences;
		}
	}

	public void setDefaultPreferences(String defaultPreferences) {
		_defaultPreferences = defaultPreferences;
	}

	public String getPreferencesValidator() {
		return _preferencesValidator;
	}

	public void setPreferencesValidator(String preferencesValidator) {
		if (preferencesValidator != null) {

			// Trim this because XDoclet generates preferences validators with
			// extra white spaces

			_preferencesValidator = preferencesValidator.trim();
		}
		else {
			_preferencesValidator = null;
		}
	}

	public boolean getPreferencesCompanyWide() {
		return _preferencesCompanyWide;
	}

	public boolean isPreferencesCompanyWide() {
		return _preferencesCompanyWide;
	}

	public void setPreferencesCompanyWide(boolean preferencesCompanyWide) {
		_preferencesCompanyWide = preferencesCompanyWide;
	}

	public boolean getPreferencesUniquePerLayout() {
		return _preferencesUniquePerLayout;
	}

	public boolean isPreferencesUniquePerLayout() {
		return _preferencesUniquePerLayout;
	}

	public void setPreferencesUniquePerLayout(
		boolean preferencesUniquePerLayout) {

		_preferencesUniquePerLayout = preferencesUniquePerLayout;
	}

	public boolean getPreferencesOwnedByGroup() {
		return _preferencesOwnedByGroup;
	}

	public boolean isPreferencesOwnedByGroup() {
		return _preferencesOwnedByGroup;
	}

	public void setPreferencesOwnedByGroup(boolean preferencesOwnedByGroup) {
		_preferencesOwnedByGroup = preferencesOwnedByGroup;
	}

	public boolean getUseDefaultTemplate() {
		return _useDefaultTemplate;
	}

	public boolean isUseDefaultTemplate() {
		return _useDefaultTemplate;
	}

	public void setUseDefaultTemplate(boolean useDefaultTemplate) {
		_useDefaultTemplate = useDefaultTemplate;
	}

	public boolean getShowPortletAccessDenied() {
		return _showPortletAccessDenied;
	}

	public boolean isShowPortletAccessDenied() {
		return _showPortletAccessDenied;
	}

	public void setShowPortletAccessDenied(boolean showPortletAccessDenied) {
		_showPortletAccessDenied = showPortletAccessDenied;
	}

	public boolean getShowPortletInactive() {
		return _showPortletInactive;
	}

	public boolean isShowPortletInactive() {
		return _showPortletInactive;
	}

	public void setShowPortletInactive(boolean showPortletInactive) {
		_showPortletInactive = showPortletInactive;
	}

	public boolean getActionURLRedirect() {
		return _actionURLRedirect;
	}

	public boolean isActionURLRedirect() {
		return _actionURLRedirect;
	}

	public void setActionURLRedirect(boolean actionURLRedirect) {
		_actionURLRedirect = actionURLRedirect;
	}

	public boolean getRestoreCurrentView() {
		return _restoreCurrentView;
	}

	public boolean isRestoreCurrentView() {
		return _restoreCurrentView;
	}

	public void setRestoreCurrentView(boolean restoreCurrentView) {
		_restoreCurrentView = restoreCurrentView;
	}

	public boolean getMaximizeEdit() {
		return _maximizeEdit;
	}

	public boolean isMaximizeEdit() {
		return _maximizeEdit;
	}

	public void setMaximizeEdit(boolean maximizeEdit) {
		_maximizeEdit = maximizeEdit;
	}

	public boolean getMaximizeHelp() {
		return _maximizeHelp;
	}

	public boolean isMaximizeHelp() {
		return _maximizeHelp;
	}

	public void setMaximizeHelp(boolean maximizeHelp) {
		_maximizeHelp = maximizeHelp;
	}

	public boolean getPopUpPrint() {
		return _popUpPrint;
	}

	public boolean isPopUpPrint() {
		return _popUpPrint;
	}

	public void setPopUpPrint(boolean popUpPrint) {
		_popUpPrint = popUpPrint;
	}

	public boolean getLayoutCacheable() {
		return _layoutCacheable;
	}

	public boolean isLayoutCacheable() {
		return _layoutCacheable;
	}

	public void setLayoutCacheable(boolean layoutCacheable) {
		_layoutCacheable = layoutCacheable;
	}

	public boolean getInstanceable() {
		return _instanceable;
	}

	public boolean isInstanceable() {
		return _instanceable;
	}

	public void setInstanceable(boolean instanceable) {
		_instanceable = instanceable;
	}

	public boolean getScopeable() {
		return _scopeable;
	}

	public boolean isScopeable() {
		return _scopeable;
	}

	public void setScopeable(boolean scopeable) {
		_scopeable = scopeable;
	}

	public String getUserPrincipalStrategy() {
		return _userPrincipalStrategy;
	}

	public void setUserPrincipalStrategy(String userPrincipalStrategy) {
		if (Validator.isNotNull(userPrincipalStrategy)) {
			_userPrincipalStrategy = userPrincipalStrategy;
		}
	}

	public boolean getPrivateRequestAttributes() {
		return _privateRequestAttributes;
	}

	public boolean isPrivateRequestAttributes() {
		return _privateRequestAttributes;
	}

	public void setPrivateRequestAttributes(boolean privateRequestAttributes) {
		_privateRequestAttributes = privateRequestAttributes;
	}

	public boolean getPrivateSessionAttributes() {
		return _privateSessionAttributes;
	}

	public boolean isPrivateSessionAttributes() {
		return _privateSessionAttributes;
	}

	public void setPrivateSessionAttributes(boolean privateSessionAttributes) {
		_privateSessionAttributes = privateSessionAttributes;
	}

	public int getRenderWeight() {
		return _renderWeight;
	}

	public void setRenderWeight(int renderWeight) {
		_renderWeight = renderWeight;
	}

	public boolean getAjaxable() {
		return _ajaxable;
	}

	public boolean isAjaxable() {
		return _ajaxable;
	}

	public void setAjaxable(boolean ajaxable) {
		_ajaxable = ajaxable;
	}

	public List<String> getHeaderPortalCss() {
		return _headerPortalCss;
	}

	public void setHeaderPortalCss(List<String> headerPortalCss) {
		_headerPortalCss = headerPortalCss;
	}

	public List<String> getHeaderPortletCss() {
		return _headerPortletCss;
	}

	public void setHeaderPortletCss(List<String> headerPortletCss) {
		_headerPortletCss = headerPortletCss;
	}

	public List<String> getHeaderPortalJavaScript() {
		return _headerPortalJavaScript;
	}

	public void setHeaderPortalJavaScript(List<String> headerPortalJavaScript) {
		_headerPortalJavaScript = headerPortalJavaScript;
	}

	public List<String> getHeaderPortletJavaScript() {
		return _headerPortletJavaScript;
	}

	public void setHeaderPortletJavaScript(
		List<String> headerPortletJavaScript) {

		_headerPortletJavaScript = headerPortletJavaScript;
	}

	public List<String> getFooterPortalCss() {
		return _footerPortalCss;
	}

	public void setFooterPortalCss(List<String> footerPortalCss) {
		_footerPortalCss = footerPortalCss;
	}

	public List<String> getFooterPortletCss() {
		return _footerPortletCss;
	}

	public void setFooterPortletCss(List<String> footerPortletCss) {
		_footerPortletCss = footerPortletCss;
	}

	public List<String> getFooterPortalJavaScript() {
		return _footerPortalJavaScript;
	}

	public void setFooterPortalJavaScript(List<String> footerPortalJavaScript) {
		_footerPortalJavaScript = footerPortalJavaScript;
	}

	public List<String> getFooterPortletJavaScript() {
		return _footerPortletJavaScript;
	}

	public void setFooterPortletJavaScript(
		List<String> footerPortletJavaScript) {

		_footerPortletJavaScript = footerPortletJavaScript;
	}

	public String getCssClassWrapper() {
		return _cssClassWrapper;
	}

	public void setCssClassWrapper(String cssClassWrapper) {
		_cssClassWrapper = cssClassWrapper;
	}

	public String getFacebookIntegration() {
		return _facebookIntegration;
	}

	public void setFacebookIntegration(String facebookIntegration) {
		if (Validator.isNotNull(facebookIntegration)) {
			_facebookIntegration = facebookIntegration;
		}
	}

	public boolean getAddDefaultResource() {
		return _addDefaultResource;
	}

	public boolean isAddDefaultResource() {
		return _addDefaultResource;
	}

	public void setAddDefaultResource(boolean addDefaultResource) {
		_addDefaultResource = addDefaultResource;
	}

	public void setRoles(String roles) {
		_rolesArray = StringUtil.split(roles);

		super.setRoles(roles);
	}

	public String[] getRolesArray() {
		return _rolesArray;
	}

	public void setRolesArray(String[] rolesArray) {
		_rolesArray = rolesArray;

		super.setRoles(StringUtil.merge(rolesArray));
	}

	public Set<String> getUnlinkedRoles() {
		return _unlinkedRoles;
	}

	public void setUnlinkedRoles(Set<String> unlinkedRoles) {
		_unlinkedRoles = unlinkedRoles;
	}

	public Map<String, String> getRoleMappers() {
		return _roleMappers;
	}

	public void setRoleMappers(Map<String, String> roleMappers) {
		_roleMappers = roleMappers;
	}

	public void linkRoles() {
		List<String> linkedRoles = new ArrayList<String>();

		Iterator<String> itr = _unlinkedRoles.iterator();

		while (itr.hasNext()) {
			String unlinkedRole = itr.next();

			String roleLink = _roleMappers.get(unlinkedRole);

			if (Validator.isNotNull(roleLink)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Linking role for portlet [" + getPortletId() +
							"] with role-name [" + unlinkedRole +
								"] to role-link [" + roleLink + "]");
				}

				linkedRoles.add(roleLink);
			}
			else {
				_log.error(
					"Unable to link role for portlet [" + getPortletId() +
						"] with role-name [" + unlinkedRole +
							"] because role-link is null");
			}
		}

		String[] array = linkedRoles.toArray(new String[linkedRoles.size()]);

		Arrays.sort(array);

		setRolesArray(array);
	}

	public boolean hasRoleWithName(String roleName) {
		if ((_rolesArray == null) || (_rolesArray.length == 0)) {
			return false;
		}

		for (int i = 0; i < _rolesArray.length; i++) {
			if (_rolesArray[i].equalsIgnoreCase(roleName)) {
				return true;
			}
		}

		return false;
	}

	public boolean hasAddPortletPermission(long userId) {
		try {
			if ((_rolesArray == null) || (_rolesArray.length == 0)) {
				return true;
			}
			else if (RoleLocalServiceUtil.hasUserRoles(
						userId, getCompanyId(), _rolesArray, true)) {

				return true;
			}
			else if (RoleLocalServiceUtil.hasUserRole(
						userId, getCompanyId(), RoleConstants.ADMINISTRATOR,
						true)) {

				return true;
			}
			else {
				User user = UserLocalServiceUtil.getUserById(userId);

				if (user.isDefaultUser() &&
					hasRoleWithName(RoleConstants.GUEST)) {

					return true;
				}
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		return false;
	}

	public boolean getSystem() {
		return _system;
	}

	public boolean isSystem() {
		return _system;
	}

	public void setSystem(boolean system) {
		_system = system;
	}

	public boolean getInclude() {
		return _include;
	}

	public boolean isInclude() {
		return _include;
	}

	public void setInclude(boolean include) {
		_include = include;
	}

	public Map<String, String> getInitParams() {
		return _initParams;
	}

	public void setInitParams(Map<String, String> initParams) {
		_initParams = initParams;
	}

	public Integer getExpCache() {
		return _expCache;
	}

	public void setExpCache(Integer expCache) {
		_expCache = expCache;
	}

	public Map<String, Set<String>> getPortletModes() {
		return _portletModes;
	}

	public void setPortletModes(Map<String, Set<String>> portletModes) {
		_portletModes = portletModes;
	}

	public boolean hasPortletMode(String mimeType, PortletMode portletMode) {
		if (mimeType == null) {
			mimeType = ContentTypes.TEXT_HTML;
		}

		Set<String> mimeTypePortletModes = _portletModes.get(mimeType);

		if (mimeTypePortletModes == null) {
			return false;
		}

		if (mimeTypePortletModes.contains(portletMode.toString())) {
			return true;
		}
		else {
			return false;
		}
	}

	public Set<String> getAllPortletModes() {
		Set<String> allPortletModes = new TreeSet<String>();

		Iterator<Map.Entry <String, Set<String>>> itr1 =
			_portletModes.entrySet().iterator();

		while (itr1.hasNext()) {
			Map.Entry<String, Set<String>> entry = itr1.next();

			Set<String> mimeTypePortletModes = entry.getValue();

			Iterator<String> itr2 = mimeTypePortletModes.iterator();

			while (itr2.hasNext()) {
				String portletMode = itr2.next();

				allPortletModes.add(portletMode);
			}
		}

		return allPortletModes;
	}

	public boolean hasMultipleMimeTypes() {
		if (_portletModes.size() > 1) {
			return true;
		}
		else {
			return false;
		}
	}

	public Map<String, Set<String>> getWindowStates() {
		return _windowStates;
	}

	public void setWindowStates(Map<String, Set<String>> windowStates) {
		_windowStates = windowStates;
	}

	public boolean hasWindowState(String mimeType, WindowState windowState) {
		if (mimeType == null) {
			mimeType = ContentTypes.TEXT_HTML;
		}

		Set<String> mimeTypeWindowStates = _windowStates.get(mimeType);

		if (mimeTypeWindowStates == null) {
			return false;
		}

		if (mimeTypeWindowStates.contains(windowState.toString())) {
			return true;
		}
		else {
			return false;
		}
	}

	public Set<String> getAllWindowStates() {
		Set<String> allWindowStates = new TreeSet<String>();

		Iterator<Map.Entry <String, Set<String>>> itr1 =
			_windowStates.entrySet().iterator();

		while (itr1.hasNext()) {
			Map.Entry<String, Set<String>> entry = itr1.next();

			Set<String> mimeTypeWindowStates = entry.getValue();

			Iterator<String> itr2 = mimeTypeWindowStates.iterator();

			while (itr2.hasNext()) {
				String windowState = itr2.next();

				allWindowStates.add(windowState);
			}
		}

		return allWindowStates;
	}

	public Set<String> getSupportedLocales() {
		return _supportedLocales;
	}

	public void setSupportedLocales(Set<String> supportedLocales) {
		_supportedLocales = supportedLocales;
	}

	public String getResourceBundle() {
		return _resourceBundle;
	}

	public void setResourceBundle(String resourceBundle) {
		_resourceBundle = resourceBundle;
	}

	public PortletInfo getPortletInfo() {
		return _portletInfo;
	}

	public void setPortletInfo(PortletInfo portletInfo) {
		_portletInfo = portletInfo;
	}

	public Map<String, PortletFilter> getPortletFilters() {
		return _portletFilters;
	}

	public void setPortletFilters(Map<String, PortletFilter> portletFilters) {
		_portletFilters = portletFilters;
	}

	public void addProcessingEvent(QName processingEvent) {
		_processingEvents.add(processingEvent);
		_processingEventsByQName.put(
			PortletQNameUtil.getKey(processingEvent), processingEvent);
	}

	public QName getProcessingEvent(String uri, String localPart) {
		return _processingEventsByQName.get(
			PortletQNameUtil.getKey(uri, localPart));
	}

	public Set<QName> getProcessingEvents() {
		return _processingEvents;
	}

	public void setProcessingEvents(Set<QName> processingEvents) {
		for (QName processingEvent : processingEvents) {
			addProcessingEvent(processingEvent);
		}
	}

	public void addPublishingEvent(QName publishingEvent) {
		_publishingEvents.add(publishingEvent);
	}

	public Set<QName> getPublishingEvents() {
		return _publishingEvents;
	}

	public void setPublishingEvents(Set<QName> publishingEvents) {
		for (QName publishingEvent : publishingEvents) {
			addPublishingEvent(publishingEvent);
		}
	}

	public void addPublicRenderParameter(
		PublicRenderParameter publicRenderParameter) {

		_publicRenderParameters.add(publicRenderParameter);
		_publicRenderParametersByIdentifier.put(
			publicRenderParameter.getIdentifier(), publicRenderParameter);
		_publicRenderParametersByQName.put(
			PortletQNameUtil.getKey(publicRenderParameter.getQName()),
			publicRenderParameter);
	}

	public PublicRenderParameter getPublicRenderParameter(String identifier) {
		return _publicRenderParametersByIdentifier.get(identifier);
	}

	public PublicRenderParameter getPublicRenderParameter(
		String uri, String localPart) {

		return _publicRenderParametersByQName.get(
			PortletQNameUtil.getKey(uri, localPart));
	}

	public Set<PublicRenderParameter> getPublicRenderParameters() {
		return _publicRenderParameters;
	}

	public void setPublicRenderParameters(
		Set<PublicRenderParameter> publicRenderParameters) {

		for (PublicRenderParameter publicRenderParameter :
				publicRenderParameters) {

			addPublicRenderParameter(publicRenderParameter);
		}
	}

	public String getContextPath() {
		String virtualPath = getVirtualPath();

		if (Validator.isNotNull(virtualPath)) {
			return virtualPath;
		}

		if (_portletApp.isWARFile()) {
			StringBuilder sb = new StringBuilder();

			sb.append(StringPool.SLASH);
			sb.append(_portletApp.getServletContextName());

			return sb.toString();
		}
		else {
			return PortalUtil.getPathContext();
		}
	}

	public PortletApp getPortletApp() {
		return _portletApp;
	}

	public void setPortletApp(PortletApp portletApp) {
		_portletApp = portletApp;
	}

	public Portlet getClonedInstance(String portletId) {
		if (_clonedInstances == null) {

			// LEP-528

			return null;
		}

		Portlet clonedInstance = _clonedInstances.get(portletId);

		if (clonedInstance == null) {
			clonedInstance = (Portlet)clone();

			clonedInstance.setPortletId(portletId);

			// Disable caching of cloned instances until we can figure out how
			// to elegantly refresh the cache when the portlet is dynamically
			// updated by the user. For example, the user might change the
			// portlet from one column to the next. Cloned instances that are
			// cached would not see the new change. We can then also cache
			// static portlet instances.

			//_clonedInstances.put(portletId, clonedInstance);
		}

		return clonedInstance;
	}

	public boolean getStatic() {
		return _staticPortlet;
	}

	public boolean isStatic() {
		return _staticPortlet;
	}

	public void setStatic(boolean staticPortlet) {
		_staticPortlet = staticPortlet;
	}

	public boolean getStaticStart() {
		return _staticPortletStart;
	}

	public boolean isStaticStart() {
		return _staticPortletStart;
	}

	public void setStaticStart(boolean staticPortletStart) {
		_staticPortletStart = staticPortletStart;
	}

	public boolean getStaticEnd() {
		return !_staticPortletStart;
	}

	public boolean isStaticEnd() {
		return !_staticPortletStart;
	}

	public boolean getUndeployedPortlet() {
		return _undeployedPortlet;
	}

	public boolean isUndeployedPortlet() {
		return _undeployedPortlet;
	}

	public void setUndeployedPortlet(boolean undeployedPortlet) {
		_undeployedPortlet = undeployedPortlet;
	}

	public Object clone() {
		Portlet portlet = new PortletImpl(
			getPortletId(), getPluginPackage(), getDefaultPluginSetting(),
			getCompanyId(), getTimestamp(), getIcon(), getVirtualPath(),
			getStrutsPath(), getPortletName(), getDisplayName(),
			getPortletClass(), getConfigurationActionClass(), getIndexerClass(),
			getOpenSearchClass(), getSchedulerClass(), getPortletURLClass(),
			getFriendlyURLMapperClass(), getURLEncoderClass(),
			getPortletDataHandlerClass(), getPortletLayoutListenerClass(),
			getPollerProcessorClass(), getPopMessageListenerClass(),
			getSocialActivityInterpreterClass(),
			getSocialRequestInterpreterClass(), getWebDAVStorageToken(),
			getWebDAVStorageClass(), getControlPanelEntryCategory(),
			getControlPanelEntryWeight(), getControlPanelEntryClass(),
			getDefaultPreferences(), getPreferencesValidator(),
			isPreferencesCompanyWide(), isPreferencesUniquePerLayout(),
			isPreferencesOwnedByGroup(), isUseDefaultTemplate(),
			isShowPortletAccessDenied(), isShowPortletInactive(),
			isActionURLRedirect(), isRestoreCurrentView(), isMaximizeEdit(),
			isMaximizeHelp(), isPopUpPrint(), isLayoutCacheable(),
			isInstanceable(), isScopeable(), getUserPrincipalStrategy(),
			isPrivateRequestAttributes(), isPrivateSessionAttributes(),
			getRenderWeight(), isAjaxable(), getHeaderPortalCss(),
			getHeaderPortletCss(), getHeaderPortalJavaScript(),
			getHeaderPortletJavaScript(), getFooterPortalCss(),
			getFooterPortletCss(), getFooterPortalJavaScript(),
			getFooterPortletJavaScript(), getCssClassWrapper(),
			getFacebookIntegration(), isAddDefaultResource(), getRoles(),
			getUnlinkedRoles(), getRoleMappers(), isSystem(), isActive(),
			isInclude(), getInitParams(), getExpCache(), getPortletModes(),
			getWindowStates(), getSupportedLocales(), getResourceBundle(),
			getPortletInfo(), getPortletFilters(), getProcessingEvents(),
			getPublishingEvents(), getPublicRenderParameters(),
			getPortletApp());

		portlet.setId(getId());

		return portlet;
	}

	public int compareTo(Portlet portlet) {
		return getPortletId().compareTo(portlet.getPortletId());
	}

	public boolean equals(Object obj) {
		Portlet portlet = (Portlet)obj;

		return getPortletId().equals(portlet.getPortletId());
	}

	private static Log _log = LogFactoryUtil.getLog(PortletImpl.class);

	private PluginPackage _pluginPackage;

	private PluginSetting _defaultPluginSetting;

	private long _timestamp;

	private String _icon;

	private String _virtualPath;

	private String _strutsPath;

	private String _portletName;

	private String _displayName;

	private String _portletClass;

	private String _configurationActionClass;

	private String _indexerClass;

	private String _openSearchClass;

	private String _schedulerClass;

	private String _portletURLClass;

	private String _friendlyURLMapperClass;

	private String _urlEncoderClass;

	private String _portletDataHandlerClass;

	private String _portletLayoutListenerClass;

	private String _pollerProcessorClass;

	private String _popMessageListenerClass;

	private String _socialActivityInterpreterClass;

	private String _socialRequestInterpreterClass;

	private String _webDAVStorageToken;

	private String _webDAVStorageClass;

	private String _defaultPreferences;

	private String _preferencesValidator;

	private boolean _preferencesCompanyWide;

	private boolean _preferencesUniquePerLayout = true;

	private boolean _preferencesOwnedByGroup = true;

	private String _controlPanelEntryCategory;

	private double _controlPanelEntryWeight = 100;

	private String _controlPanelEntryClass;

	private boolean _useDefaultTemplate = true;

	private boolean _showPortletAccessDenied =
		PropsValues.LAYOUT_SHOW_PORTLET_ACCESS_DENIED;

	private boolean _showPortletInactive =
		PropsValues.LAYOUT_SHOW_PORTLET_INACTIVE;

	private boolean _actionURLRedirect;

	private boolean _restoreCurrentView = true;

	private boolean _maximizeEdit;

	private boolean _maximizeHelp;

	private boolean _popUpPrint = true;

	private boolean _layoutCacheable;

	private boolean _instanceable;

	private boolean _scopeable;

	private String _userPrincipalStrategy =
		PortletConstants.USER_PRINCIPAL_STRATEGY_USER_ID;

	private boolean _privateRequestAttributes = true;

	private boolean _privateSessionAttributes = true;

	private int _renderWeight = 1;

	private boolean _ajaxable = true;

	private List<String> _headerPortalCss;

	private List<String> _headerPortletCss;

	private List<String> _headerPortalJavaScript;

	private List<String> _headerPortletJavaScript;

	private List<String> _footerPortalCss;

	private List<String> _footerPortletCss;

	private List<String> _footerPortalJavaScript;

	private List<String> _footerPortletJavaScript;

	private String _cssClassWrapper = StringPool.BLANK;

	private String _facebookIntegration =
		PortletConstants.FACEBOOK_INTEGRATION_IFRAME;

	private boolean _addDefaultResource;

	private String[] _rolesArray;

	private Set<String> _unlinkedRoles;

	private Map<String, String> _roleMappers;

	private boolean _system;

	private boolean _include = true;

	private Map<String, String> _initParams;

	private Integer _expCache;

	private Map<String, Set<String>> _portletModes;

	private Map<String, Set<String>> _windowStates;

	private Set<String> _supportedLocales;

	private String _resourceBundle;

	private PortletInfo _portletInfo;

	private Map<String, PortletFilter> _portletFilters;

	private Set<QName> _processingEvents = new HashSet<QName>();

	private Map<String, QName> _processingEventsByQName =
		new HashMap<String, QName>();

	private Set<QName> _publishingEvents = new HashSet<QName>();

	private Set<PublicRenderParameter> _publicRenderParameters =
		new HashSet<PublicRenderParameter>();

	private Map<String, PublicRenderParameter>
		_publicRenderParametersByIdentifier =
			new HashMap<String, PublicRenderParameter>();

	private Map<String, PublicRenderParameter>
		_publicRenderParametersByQName =
			new HashMap<String, PublicRenderParameter>();

	private PortletApp _portletApp;

	private Map<String, Portlet> _clonedInstances;

	private boolean _staticPortlet;

	private boolean _staticPortletStart;

	private boolean _undeployedPortlet = false;

}