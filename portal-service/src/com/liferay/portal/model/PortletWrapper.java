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

package com.liferay.portal.model;

/**
 * <p>
 * This class is a wrapper for {@link Portlet}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Portlet
 * @generated
 */
public class PortletWrapper implements Portlet {
	public PortletWrapper(Portlet portlet) {
		_portlet = portlet;
	}

	public long getPrimaryKey() {
		return _portlet.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_portlet.setPrimaryKey(pk);
	}

	public long getId() {
		return _portlet.getId();
	}

	public void setId(long id) {
		_portlet.setId(id);
	}

	public long getCompanyId() {
		return _portlet.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_portlet.setCompanyId(companyId);
	}

	public java.lang.String getPortletId() {
		return _portlet.getPortletId();
	}

	public void setPortletId(java.lang.String portletId) {
		_portlet.setPortletId(portletId);
	}

	public java.lang.String getRoles() {
		return _portlet.getRoles();
	}

	public void setRoles(java.lang.String roles) {
		_portlet.setRoles(roles);
	}

	public boolean getActive() {
		return _portlet.getActive();
	}

	public boolean isActive() {
		return _portlet.isActive();
	}

	public void setActive(boolean active) {
		_portlet.setActive(active);
	}

	public com.liferay.portal.model.Portlet toEscapedModel() {
		return _portlet.toEscapedModel();
	}

	public boolean isNew() {
		return _portlet.isNew();
	}

	public void setNew(boolean n) {
		_portlet.setNew(n);
	}

	public boolean isCachedModel() {
		return _portlet.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_portlet.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _portlet.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_portlet.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _portlet.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _portlet.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_portlet.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _portlet.clone();
	}

	public int compareTo(com.liferay.portal.model.Portlet portlet) {
		return _portlet.compareTo(portlet);
	}

	public int hashCode() {
		return _portlet.hashCode();
	}

	public java.lang.String toString() {
		return _portlet.toString();
	}

	public java.lang.String toXmlString() {
		return _portlet.toXmlString();
	}

	public java.lang.String getRootPortletId() {
		return _portlet.getRootPortletId();
	}

	public java.lang.String getInstanceId() {
		return _portlet.getInstanceId();
	}

	public java.lang.String getPluginId() {
		return _portlet.getPluginId();
	}

	public java.lang.String getPluginType() {
		return _portlet.getPluginType();
	}

	public com.liferay.portal.kernel.plugin.PluginPackage getPluginPackage() {
		return _portlet.getPluginPackage();
	}

	public void setPluginPackage(
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		_portlet.setPluginPackage(pluginPackage);
	}

	public com.liferay.portal.model.PluginSetting getDefaultPluginSetting() {
		return _portlet.getDefaultPluginSetting();
	}

	public void setDefaultPluginSetting(
		com.liferay.portal.model.PluginSetting pluginSetting) {
		_portlet.setDefaultPluginSetting(pluginSetting);
	}

	public long getTimestamp() {
		return _portlet.getTimestamp();
	}

	public void setTimestamp(long timestamp) {
		_portlet.setTimestamp(timestamp);
	}

	public java.lang.String getIcon() {
		return _portlet.getIcon();
	}

	public void setIcon(java.lang.String icon) {
		_portlet.setIcon(icon);
	}

	public java.lang.String getVirtualPath() {
		return _portlet.getVirtualPath();
	}

	public void setVirtualPath(java.lang.String virtualPath) {
		_portlet.setVirtualPath(virtualPath);
	}

	public java.lang.String getStrutsPath() {
		return _portlet.getStrutsPath();
	}

	public void setStrutsPath(java.lang.String strutsPath) {
		_portlet.setStrutsPath(strutsPath);
	}

	public java.lang.String getPortletName() {
		return _portlet.getPortletName();
	}

	public void setPortletName(java.lang.String portletName) {
		_portlet.setPortletName(portletName);
	}

	public java.lang.String getDisplayName() {
		return _portlet.getDisplayName();
	}

	public void setDisplayName(java.lang.String displayName) {
		_portlet.setDisplayName(displayName);
	}

	public java.lang.String getPortletClass() {
		return _portlet.getPortletClass();
	}

	public void setPortletClass(java.lang.String portletClass) {
		_portlet.setPortletClass(portletClass);
	}

	public java.lang.String getConfigurationActionClass() {
		return _portlet.getConfigurationActionClass();
	}

	public void setConfigurationActionClass(
		java.lang.String configurationActionClass) {
		_portlet.setConfigurationActionClass(configurationActionClass);
	}

	public com.liferay.portal.kernel.portlet.ConfigurationAction getConfigurationActionInstance() {
		return _portlet.getConfigurationActionInstance();
	}

	public java.lang.String getIndexerClass() {
		return _portlet.getIndexerClass();
	}

	public void setIndexerClass(java.lang.String indexerClass) {
		_portlet.setIndexerClass(indexerClass);
	}

	public com.liferay.portal.kernel.search.Indexer getIndexerInstance() {
		return _portlet.getIndexerInstance();
	}

	public java.lang.String getOpenSearchClass() {
		return _portlet.getOpenSearchClass();
	}

	public void setOpenSearchClass(java.lang.String openSearchClass) {
		_portlet.setOpenSearchClass(openSearchClass);
	}

	public com.liferay.portal.kernel.search.OpenSearch getOpenSearchInstance() {
		return _portlet.getOpenSearchInstance();
	}

	public void addSchedulerEntry(
		com.liferay.portal.kernel.scheduler.SchedulerEntry schedulerEntry) {
		_portlet.addSchedulerEntry(schedulerEntry);
	}

	public java.util.List<com.liferay.portal.kernel.scheduler.SchedulerEntry> getSchedulerEntries() {
		return _portlet.getSchedulerEntries();
	}

	public void setSchedulerEntries(
		java.util.List<com.liferay.portal.kernel.scheduler.SchedulerEntry> schedulerEntries) {
		_portlet.setSchedulerEntries(schedulerEntries);
	}

	public java.lang.String getPortletURLClass() {
		return _portlet.getPortletURLClass();
	}

	public void setPortletURLClass(java.lang.String portletURLClass) {
		_portlet.setPortletURLClass(portletURLClass);
	}

	public java.lang.String getFriendlyURLMapperClass() {
		return _portlet.getFriendlyURLMapperClass();
	}

	public void setFriendlyURLMapperClass(
		java.lang.String friendlyURLMapperClass) {
		_portlet.setFriendlyURLMapperClass(friendlyURLMapperClass);
	}

	public com.liferay.portal.kernel.portlet.FriendlyURLMapper getFriendlyURLMapperInstance() {
		return _portlet.getFriendlyURLMapperInstance();
	}

	public java.lang.String getFriendlyURLMapping() {
		return _portlet.getFriendlyURLMapping();
	}

	public void setFriendlyURLMapping(java.lang.String friendlyURLMapping) {
		_portlet.setFriendlyURLMapping(friendlyURLMapping);
	}

	public java.lang.String getFriendlyURLRoutes() {
		return _portlet.getFriendlyURLRoutes();
	}

	public void setFriendlyURLRoutes(java.lang.String friendlyURLRoutes) {
		_portlet.setFriendlyURLRoutes(friendlyURLRoutes);
	}

	public java.lang.String getURLEncoderClass() {
		return _portlet.getURLEncoderClass();
	}

	public void setURLEncoderClass(java.lang.String urlEncoderClass) {
		_portlet.setURLEncoderClass(urlEncoderClass);
	}

	public com.liferay.portal.kernel.servlet.URLEncoder getURLEncoderInstance() {
		return _portlet.getURLEncoderInstance();
	}

	public java.lang.String getPortletDataHandlerClass() {
		return _portlet.getPortletDataHandlerClass();
	}

	public void setPortletDataHandlerClass(
		java.lang.String portletDataHandlerClass) {
		_portlet.setPortletDataHandlerClass(portletDataHandlerClass);
	}

	public com.liferay.portal.kernel.lar.PortletDataHandler getPortletDataHandlerInstance() {
		return _portlet.getPortletDataHandlerInstance();
	}

	public java.lang.String getPortletLayoutListenerClass() {
		return _portlet.getPortletLayoutListenerClass();
	}

	public void setPortletLayoutListenerClass(
		java.lang.String portletLayoutListenerClass) {
		_portlet.setPortletLayoutListenerClass(portletLayoutListenerClass);
	}

	public com.liferay.portal.kernel.portlet.PortletLayoutListener getPortletLayoutListenerInstance() {
		return _portlet.getPortletLayoutListenerInstance();
	}

	public java.lang.String getPollerProcessorClass() {
		return _portlet.getPollerProcessorClass();
	}

	public void setPollerProcessorClass(java.lang.String pollerProcessorClass) {
		_portlet.setPollerProcessorClass(pollerProcessorClass);
	}

	public com.liferay.portal.kernel.poller.PollerProcessor getPollerProcessorInstance() {
		return _portlet.getPollerProcessorInstance();
	}

	public java.lang.String getPopMessageListenerClass() {
		return _portlet.getPopMessageListenerClass();
	}

	public void setPopMessageListenerClass(
		java.lang.String popMessageListenerClass) {
		_portlet.setPopMessageListenerClass(popMessageListenerClass);
	}

	public com.liferay.portal.kernel.pop.MessageListener getPopMessageListenerInstance() {
		return _portlet.getPopMessageListenerInstance();
	}

	public java.lang.String getSocialActivityInterpreterClass() {
		return _portlet.getSocialActivityInterpreterClass();
	}

	public void setSocialActivityInterpreterClass(
		java.lang.String socialActivityInterpreterClass) {
		_portlet.setSocialActivityInterpreterClass(socialActivityInterpreterClass);
	}

	public com.liferay.portlet.social.model.SocialActivityInterpreter getSocialActivityInterpreterInstance() {
		return _portlet.getSocialActivityInterpreterInstance();
	}

	public java.lang.String getSocialRequestInterpreterClass() {
		return _portlet.getSocialRequestInterpreterClass();
	}

	public void setSocialRequestInterpreterClass(
		java.lang.String socialRequestInterpreterClass) {
		_portlet.setSocialRequestInterpreterClass(socialRequestInterpreterClass);
	}

	public com.liferay.portlet.social.model.SocialRequestInterpreter getSocialRequestInterpreterInstance() {
		return _portlet.getSocialRequestInterpreterInstance();
	}

	public java.lang.String getWebDAVStorageToken() {
		return _portlet.getWebDAVStorageToken();
	}

	public void setWebDAVStorageToken(java.lang.String webDAVStorageToken) {
		_portlet.setWebDAVStorageToken(webDAVStorageToken);
	}

	public java.lang.String getWebDAVStorageClass() {
		return _portlet.getWebDAVStorageClass();
	}

	public void setWebDAVStorageClass(java.lang.String webDAVStorageClass) {
		_portlet.setWebDAVStorageClass(webDAVStorageClass);
	}

	public com.liferay.portal.kernel.webdav.WebDAVStorage getWebDAVStorageInstance() {
		return _portlet.getWebDAVStorageInstance();
	}

	public java.lang.String getXmlRpcMethodClass() {
		return _portlet.getXmlRpcMethodClass();
	}

	public void setXmlRpcMethodClass(java.lang.String xmlRpcMethodClass) {
		_portlet.setXmlRpcMethodClass(xmlRpcMethodClass);
	}

	public com.liferay.portal.kernel.xmlrpc.Method getXmlRpcMethodInstance() {
		return _portlet.getXmlRpcMethodInstance();
	}

	public java.lang.String getControlPanelEntryCategory() {
		return _portlet.getControlPanelEntryCategory();
	}

	public void setControlPanelEntryCategory(
		java.lang.String controlPanelEntryCategory) {
		_portlet.setControlPanelEntryCategory(controlPanelEntryCategory);
	}

	public double getControlPanelEntryWeight() {
		return _portlet.getControlPanelEntryWeight();
	}

	public void setControlPanelEntryWeight(double controlPanelEntryWeight) {
		_portlet.setControlPanelEntryWeight(controlPanelEntryWeight);
	}

	public java.lang.String getControlPanelEntryClass() {
		return _portlet.getControlPanelEntryClass();
	}

	public void setControlPanelEntryClass(
		java.lang.String controlPanelEntryClass) {
		_portlet.setControlPanelEntryClass(controlPanelEntryClass);
	}

	public com.liferay.portlet.ControlPanelEntry getControlPanelEntryInstance() {
		return _portlet.getControlPanelEntryInstance();
	}

	public java.util.List<java.lang.String> getAssetRendererFactoryClasses() {
		return _portlet.getAssetRendererFactoryClasses();
	}

	public void setAssetRendererFactoryClasses(
		java.util.List<java.lang.String> assetRendererFactoryClasses) {
		_portlet.setAssetRendererFactoryClasses(assetRendererFactoryClasses);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetRendererFactory> getAssetRendererFactoryInstances() {
		return _portlet.getAssetRendererFactoryInstances();
	}

	public java.util.List<java.lang.String> getCustomAttributesDisplayClasses() {
		return _portlet.getCustomAttributesDisplayClasses();
	}

	public void setCustomAttributesDisplayClasses(
		java.util.List<java.lang.String> customAttributesDisplayClasses) {
		_portlet.setCustomAttributesDisplayClasses(customAttributesDisplayClasses);
	}

	public java.util.List<com.liferay.portlet.expando.model.CustomAttributesDisplay> getCustomAttributesDisplayInstances() {
		return _portlet.getCustomAttributesDisplayInstances();
	}

	public java.util.List<java.lang.String> getWorkflowHandlerClasses() {
		return _portlet.getWorkflowHandlerClasses();
	}

	public void setWorkflowHandlerClasses(
		java.util.List<java.lang.String> workflowHandlerClasses) {
		_portlet.setWorkflowHandlerClasses(workflowHandlerClasses);
	}

	public java.util.List<com.liferay.portal.kernel.workflow.WorkflowHandler> getWorkflowHandlerInstances() {
		return _portlet.getWorkflowHandlerInstances();
	}

	public java.lang.String getDefaultPreferences() {
		return _portlet.getDefaultPreferences();
	}

	public void setDefaultPreferences(java.lang.String defaultPreferences) {
		_portlet.setDefaultPreferences(defaultPreferences);
	}

	public java.lang.String getPreferencesValidator() {
		return _portlet.getPreferencesValidator();
	}

	public void setPreferencesValidator(java.lang.String preferencesValidator) {
		_portlet.setPreferencesValidator(preferencesValidator);
	}

	public boolean getPreferencesCompanyWide() {
		return _portlet.getPreferencesCompanyWide();
	}

	public boolean isPreferencesCompanyWide() {
		return _portlet.isPreferencesCompanyWide();
	}

	public void setPreferencesCompanyWide(boolean preferencesCompanyWide) {
		_portlet.setPreferencesCompanyWide(preferencesCompanyWide);
	}

	public boolean getPreferencesUniquePerLayout() {
		return _portlet.getPreferencesUniquePerLayout();
	}

	public boolean isPreferencesUniquePerLayout() {
		return _portlet.isPreferencesUniquePerLayout();
	}

	public void setPreferencesUniquePerLayout(
		boolean preferencesUniquePerLayout) {
		_portlet.setPreferencesUniquePerLayout(preferencesUniquePerLayout);
	}

	public boolean getPreferencesOwnedByGroup() {
		return _portlet.getPreferencesOwnedByGroup();
	}

	public boolean isPreferencesOwnedByGroup() {
		return _portlet.isPreferencesOwnedByGroup();
	}

	public void setPreferencesOwnedByGroup(boolean preferencesOwnedByGroup) {
		_portlet.setPreferencesOwnedByGroup(preferencesOwnedByGroup);
	}

	public boolean getUseDefaultTemplate() {
		return _portlet.getUseDefaultTemplate();
	}

	public boolean isUseDefaultTemplate() {
		return _portlet.isUseDefaultTemplate();
	}

	public void setUseDefaultTemplate(boolean useDefaultTemplate) {
		_portlet.setUseDefaultTemplate(useDefaultTemplate);
	}

	public boolean getShowPortletAccessDenied() {
		return _portlet.getShowPortletAccessDenied();
	}

	public boolean isShowPortletAccessDenied() {
		return _portlet.isShowPortletAccessDenied();
	}

	public void setShowPortletAccessDenied(boolean showPortletAccessDenied) {
		_portlet.setShowPortletAccessDenied(showPortletAccessDenied);
	}

	public boolean getShowPortletInactive() {
		return _portlet.getShowPortletInactive();
	}

	public boolean isShowPortletInactive() {
		return _portlet.isShowPortletInactive();
	}

	public void setShowPortletInactive(boolean showPortletInactive) {
		_portlet.setShowPortletInactive(showPortletInactive);
	}

	public boolean getActionURLRedirect() {
		return _portlet.getActionURLRedirect();
	}

	public boolean isActionURLRedirect() {
		return _portlet.isActionURLRedirect();
	}

	public void setActionURLRedirect(boolean actionURLRedirect) {
		_portlet.setActionURLRedirect(actionURLRedirect);
	}

	public boolean getRestoreCurrentView() {
		return _portlet.getRestoreCurrentView();
	}

	public boolean isRestoreCurrentView() {
		return _portlet.isRestoreCurrentView();
	}

	public void setRestoreCurrentView(boolean restoreCurrentView) {
		_portlet.setRestoreCurrentView(restoreCurrentView);
	}

	public boolean getMaximizeEdit() {
		return _portlet.getMaximizeEdit();
	}

	public boolean isMaximizeEdit() {
		return _portlet.isMaximizeEdit();
	}

	public void setMaximizeEdit(boolean maximizeEdit) {
		_portlet.setMaximizeEdit(maximizeEdit);
	}

	public boolean getMaximizeHelp() {
		return _portlet.getMaximizeHelp();
	}

	public boolean isMaximizeHelp() {
		return _portlet.isMaximizeHelp();
	}

	public void setMaximizeHelp(boolean maximizeHelp) {
		_portlet.setMaximizeHelp(maximizeHelp);
	}

	public boolean getPopUpPrint() {
		return _portlet.getPopUpPrint();
	}

	public boolean isPopUpPrint() {
		return _portlet.isPopUpPrint();
	}

	public void setPopUpPrint(boolean popUpPrint) {
		_portlet.setPopUpPrint(popUpPrint);
	}

	public boolean getLayoutCacheable() {
		return _portlet.getLayoutCacheable();
	}

	public boolean isLayoutCacheable() {
		return _portlet.isLayoutCacheable();
	}

	public void setLayoutCacheable(boolean layoutCacheable) {
		_portlet.setLayoutCacheable(layoutCacheable);
	}

	public boolean getInstanceable() {
		return _portlet.getInstanceable();
	}

	public boolean isInstanceable() {
		return _portlet.isInstanceable();
	}

	public void setInstanceable(boolean instanceable) {
		_portlet.setInstanceable(instanceable);
	}

	public boolean getRemoteable() {
		return _portlet.getRemoteable();
	}

	public boolean isRemoteable() {
		return _portlet.isRemoteable();
	}

	public void setRemoteable(boolean remoteable) {
		_portlet.setRemoteable(remoteable);
	}

	public boolean getScopeable() {
		return _portlet.getScopeable();
	}

	public boolean isScopeable() {
		return _portlet.isScopeable();
	}

	public void setScopeable(boolean scopeable) {
		_portlet.setScopeable(scopeable);
	}

	public java.lang.String getUserPrincipalStrategy() {
		return _portlet.getUserPrincipalStrategy();
	}

	public void setUserPrincipalStrategy(java.lang.String userPrincipalStrategy) {
		_portlet.setUserPrincipalStrategy(userPrincipalStrategy);
	}

	public boolean getPrivateRequestAttributes() {
		return _portlet.getPrivateRequestAttributes();
	}

	public boolean isPrivateRequestAttributes() {
		return _portlet.isPrivateRequestAttributes();
	}

	public void setPrivateRequestAttributes(boolean privateRequestAttributes) {
		_portlet.setPrivateRequestAttributes(privateRequestAttributes);
	}

	public boolean getPrivateSessionAttributes() {
		return _portlet.getPrivateSessionAttributes();
	}

	public boolean isPrivateSessionAttributes() {
		return _portlet.isPrivateSessionAttributes();
	}

	public void setPrivateSessionAttributes(boolean privateSessionAttributes) {
		_portlet.setPrivateSessionAttributes(privateSessionAttributes);
	}

	public int getRenderWeight() {
		return _portlet.getRenderWeight();
	}

	public void setRenderWeight(int renderWeight) {
		_portlet.setRenderWeight(renderWeight);
	}

	public boolean getAjaxable() {
		return _portlet.getAjaxable();
	}

	public boolean isAjaxable() {
		return _portlet.isAjaxable();
	}

	public void setAjaxable(boolean ajaxable) {
		_portlet.setAjaxable(ajaxable);
	}

	public java.util.List<java.lang.String> getHeaderPortalCss() {
		return _portlet.getHeaderPortalCss();
	}

	public void setHeaderPortalCss(
		java.util.List<java.lang.String> headerPortalCss) {
		_portlet.setHeaderPortalCss(headerPortalCss);
	}

	public java.util.List<java.lang.String> getHeaderPortletCss() {
		return _portlet.getHeaderPortletCss();
	}

	public void setHeaderPortletCss(
		java.util.List<java.lang.String> headerPortletCss) {
		_portlet.setHeaderPortletCss(headerPortletCss);
	}

	public java.util.List<java.lang.String> getHeaderPortalJavaScript() {
		return _portlet.getHeaderPortalJavaScript();
	}

	public void setHeaderPortalJavaScript(
		java.util.List<java.lang.String> headerPortalJavaScript) {
		_portlet.setHeaderPortalJavaScript(headerPortalJavaScript);
	}

	public java.util.List<java.lang.String> getHeaderPortletJavaScript() {
		return _portlet.getHeaderPortletJavaScript();
	}

	public void setHeaderPortletJavaScript(
		java.util.List<java.lang.String> headerPortletJavaScript) {
		_portlet.setHeaderPortletJavaScript(headerPortletJavaScript);
	}

	public java.util.List<java.lang.String> getFooterPortalCss() {
		return _portlet.getFooterPortalCss();
	}

	public void setFooterPortalCss(
		java.util.List<java.lang.String> footerPortalCss) {
		_portlet.setFooterPortalCss(footerPortalCss);
	}

	public java.util.List<java.lang.String> getFooterPortletCss() {
		return _portlet.getFooterPortletCss();
	}

	public void setFooterPortletCss(
		java.util.List<java.lang.String> footerPortletCss) {
		_portlet.setFooterPortletCss(footerPortletCss);
	}

	public java.util.List<java.lang.String> getFooterPortalJavaScript() {
		return _portlet.getFooterPortalJavaScript();
	}

	public void setFooterPortalJavaScript(
		java.util.List<java.lang.String> footerPortalJavaScript) {
		_portlet.setFooterPortalJavaScript(footerPortalJavaScript);
	}

	public java.util.List<java.lang.String> getFooterPortletJavaScript() {
		return _portlet.getFooterPortletJavaScript();
	}

	public void setFooterPortletJavaScript(
		java.util.List<java.lang.String> footerPortletJavaScript) {
		_portlet.setFooterPortletJavaScript(footerPortletJavaScript);
	}

	public java.lang.String getCssClassWrapper() {
		return _portlet.getCssClassWrapper();
	}

	public void setCssClassWrapper(java.lang.String cssClassWrapper) {
		_portlet.setCssClassWrapper(cssClassWrapper);
	}

	public java.lang.String getFacebookIntegration() {
		return _portlet.getFacebookIntegration();
	}

	public void setFacebookIntegration(java.lang.String facebookIntegration) {
		_portlet.setFacebookIntegration(facebookIntegration);
	}

	public boolean getAddDefaultResource() {
		return _portlet.getAddDefaultResource();
	}

	public boolean isAddDefaultResource() {
		return _portlet.isAddDefaultResource();
	}

	public void setAddDefaultResource(boolean addDefaultResource) {
		_portlet.setAddDefaultResource(addDefaultResource);
	}

	public java.lang.String[] getRolesArray() {
		return _portlet.getRolesArray();
	}

	public void setRolesArray(java.lang.String[] rolesArray) {
		_portlet.setRolesArray(rolesArray);
	}

	public java.util.Set<java.lang.String> getUnlinkedRoles() {
		return _portlet.getUnlinkedRoles();
	}

	public void setUnlinkedRoles(java.util.Set<java.lang.String> unlinkedRoles) {
		_portlet.setUnlinkedRoles(unlinkedRoles);
	}

	public java.util.Map<java.lang.String, java.lang.String> getRoleMappers() {
		return _portlet.getRoleMappers();
	}

	public void setRoleMappers(
		java.util.Map<java.lang.String, java.lang.String> roleMappers) {
		_portlet.setRoleMappers(roleMappers);
	}

	public void linkRoles() {
		_portlet.linkRoles();
	}

	public boolean hasRoleWithName(java.lang.String roleName) {
		return _portlet.hasRoleWithName(roleName);
	}

	public boolean hasAddPortletPermission(long userId) {
		return _portlet.hasAddPortletPermission(userId);
	}

	public boolean getSystem() {
		return _portlet.getSystem();
	}

	public boolean isSystem() {
		return _portlet.isSystem();
	}

	public void setSystem(boolean system) {
		_portlet.setSystem(system);
	}

	public boolean getInclude() {
		return _portlet.getInclude();
	}

	public boolean isInclude() {
		return _portlet.isInclude();
	}

	public void setInclude(boolean include) {
		_portlet.setInclude(include);
	}

	public java.util.Map<java.lang.String, java.lang.String> getInitParams() {
		return _portlet.getInitParams();
	}

	public void setInitParams(
		java.util.Map<java.lang.String, java.lang.String> initParams) {
		_portlet.setInitParams(initParams);
	}

	public java.lang.Integer getExpCache() {
		return _portlet.getExpCache();
	}

	public void setExpCache(java.lang.Integer expCache) {
		_portlet.setExpCache(expCache);
	}

	public java.util.Map<java.lang.String, java.util.Set<java.lang.String>> getPortletModes() {
		return _portlet.getPortletModes();
	}

	public void setPortletModes(
		java.util.Map<java.lang.String, java.util.Set<java.lang.String>> portletModes) {
		_portlet.setPortletModes(portletModes);
	}

	public boolean hasPortletMode(java.lang.String mimeType,
		javax.portlet.PortletMode portletMode) {
		return _portlet.hasPortletMode(mimeType, portletMode);
	}

	public java.util.Set<java.lang.String> getAllPortletModes() {
		return _portlet.getAllPortletModes();
	}

	public boolean hasMultipleMimeTypes() {
		return _portlet.hasMultipleMimeTypes();
	}

	public java.util.Map<java.lang.String, java.util.Set<java.lang.String>> getWindowStates() {
		return _portlet.getWindowStates();
	}

	public void setWindowStates(
		java.util.Map<java.lang.String, java.util.Set<java.lang.String>> windowStates) {
		_portlet.setWindowStates(windowStates);
	}

	public boolean hasWindowState(java.lang.String mimeType,
		javax.portlet.WindowState windowState) {
		return _portlet.hasWindowState(mimeType, windowState);
	}

	public java.util.Set<java.lang.String> getAllWindowStates() {
		return _portlet.getAllWindowStates();
	}

	public java.util.Set<java.lang.String> getSupportedLocales() {
		return _portlet.getSupportedLocales();
	}

	public void setSupportedLocales(
		java.util.Set<java.lang.String> supportedLocales) {
		_portlet.setSupportedLocales(supportedLocales);
	}

	public java.lang.String getResourceBundle() {
		return _portlet.getResourceBundle();
	}

	public void setResourceBundle(java.lang.String resourceBundle) {
		_portlet.setResourceBundle(resourceBundle);
	}

	public com.liferay.portal.model.PortletInfo getPortletInfo() {
		return _portlet.getPortletInfo();
	}

	public void setPortletInfo(com.liferay.portal.model.PortletInfo portletInfo) {
		_portlet.setPortletInfo(portletInfo);
	}

	public java.util.Map<java.lang.String, com.liferay.portal.model.PortletFilter> getPortletFilters() {
		return _portlet.getPortletFilters();
	}

	public void setPortletFilters(
		java.util.Map<java.lang.String, com.liferay.portal.model.PortletFilter> portletFilters) {
		_portlet.setPortletFilters(portletFilters);
	}

	public void addProcessingEvent(
		com.liferay.portal.kernel.xml.QName processingEvent) {
		_portlet.addProcessingEvent(processingEvent);
	}

	public com.liferay.portal.kernel.xml.QName getProcessingEvent(
		java.lang.String uri, java.lang.String localPart) {
		return _portlet.getProcessingEvent(uri, localPart);
	}

	public java.util.Set<com.liferay.portal.kernel.xml.QName> getProcessingEvents() {
		return _portlet.getProcessingEvents();
	}

	public void setProcessingEvents(
		java.util.Set<com.liferay.portal.kernel.xml.QName> processingEvents) {
		_portlet.setProcessingEvents(processingEvents);
	}

	public void addPublishingEvent(
		com.liferay.portal.kernel.xml.QName publishingEvent) {
		_portlet.addPublishingEvent(publishingEvent);
	}

	public java.util.Set<com.liferay.portal.kernel.xml.QName> getPublishingEvents() {
		return _portlet.getPublishingEvents();
	}

	public void setPublishingEvents(
		java.util.Set<com.liferay.portal.kernel.xml.QName> publishingEvents) {
		_portlet.setPublishingEvents(publishingEvents);
	}

	public void addPublicRenderParameter(
		com.liferay.portal.model.PublicRenderParameter publicRenderParameter) {
		_portlet.addPublicRenderParameter(publicRenderParameter);
	}

	public com.liferay.portal.model.PublicRenderParameter getPublicRenderParameter(
		java.lang.String identifier) {
		return _portlet.getPublicRenderParameter(identifier);
	}

	public com.liferay.portal.model.PublicRenderParameter getPublicRenderParameter(
		java.lang.String uri, java.lang.String localPart) {
		return _portlet.getPublicRenderParameter(uri, localPart);
	}

	public java.util.Set<com.liferay.portal.model.PublicRenderParameter> getPublicRenderParameters() {
		return _portlet.getPublicRenderParameters();
	}

	public void setPublicRenderParameters(
		java.util.Set<com.liferay.portal.model.PublicRenderParameter> publicRenderParameters) {
		_portlet.setPublicRenderParameters(publicRenderParameters);
	}

	public java.lang.String getContextPath() {
		return _portlet.getContextPath();
	}

	public com.liferay.portal.model.PortletApp getPortletApp() {
		return _portlet.getPortletApp();
	}

	public void setPortletApp(com.liferay.portal.model.PortletApp portletApp) {
		_portlet.setPortletApp(portletApp);
	}

	public com.liferay.portal.model.Portlet getClonedInstance(
		java.lang.String portletId) {
		return _portlet.getClonedInstance(portletId);
	}

	public boolean getStatic() {
		return _portlet.getStatic();
	}

	public boolean isStatic() {
		return _portlet.isStatic();
	}

	public void setStatic(boolean staticPortlet) {
		_portlet.setStatic(staticPortlet);
	}

	public boolean getStaticStart() {
		return _portlet.getStaticStart();
	}

	public boolean isStaticStart() {
		return _portlet.isStaticStart();
	}

	public void setStaticStart(boolean staticPortletStart) {
		_portlet.setStaticStart(staticPortletStart);
	}

	public boolean getStaticEnd() {
		return _portlet.getStaticEnd();
	}

	public boolean isStaticEnd() {
		return _portlet.isStaticEnd();
	}

	public boolean getUndeployedPortlet() {
		return _portlet.getUndeployedPortlet();
	}

	public boolean isUndeployedPortlet() {
		return _portlet.isUndeployedPortlet();
	}

	public void setUndeployedPortlet(boolean undeployedPortlet) {
		_portlet.setUndeployedPortlet(undeployedPortlet);
	}

	public boolean equals(java.lang.Object obj) {
		return _portlet.equals(obj);
	}

	public Portlet getWrappedPortlet() {
		return _portlet;
	}

	private Portlet _portlet;
}