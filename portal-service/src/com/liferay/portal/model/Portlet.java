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

package com.liferay.portal.model;


/**
 * <a href="Portlet.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the Portlet table in the
 * database.
 * </p>
 *
 * <p>
 * Customize {@link com.liferay.portal.model.impl.PortletImpl} and rerun the
 * ServiceBuilder to generate the new methods.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletModel
 * @see       com.liferay.portal.model.impl.PortletImpl
 * @see       com.liferay.portal.model.impl.PortletModelImpl
 * @generated
 */
public interface Portlet extends PortletModel {
	public java.lang.String getRootPortletId();

	public java.lang.String getInstanceId();

	public java.lang.String getPluginId();

	public java.lang.String getPluginType();

	public com.liferay.portal.kernel.plugin.PluginPackage getPluginPackage();

	public void setPluginPackage(
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage);

	public com.liferay.portal.model.PluginSetting getDefaultPluginSetting();

	public void setDefaultPluginSetting(
		com.liferay.portal.model.PluginSetting pluginSetting);

	public long getTimestamp();

	public void setTimestamp(long timestamp);

	public java.lang.String getIcon();

	public void setIcon(java.lang.String icon);

	public java.lang.String getVirtualPath();

	public void setVirtualPath(java.lang.String virtualPath);

	public java.lang.String getStrutsPath();

	public void setStrutsPath(java.lang.String strutsPath);

	public java.lang.String getPortletName();

	public void setPortletName(java.lang.String portletName);

	public java.lang.String getDisplayName();

	public void setDisplayName(java.lang.String displayName);

	public java.lang.String getPortletClass();

	public void setPortletClass(java.lang.String portletClass);

	public java.lang.String getConfigurationActionClass();

	public void setConfigurationActionClass(
		java.lang.String configurationActionClass);

	public com.liferay.portal.kernel.portlet.ConfigurationAction getConfigurationActionInstance();

	public java.lang.String getIndexerClass();

	public void setIndexerClass(java.lang.String indexerClass);

	public com.liferay.portal.kernel.search.Indexer getIndexerInstance();

	public java.lang.String getOpenSearchClass();

	public void setOpenSearchClass(java.lang.String openSearchClass);

	public com.liferay.portal.kernel.search.OpenSearch getOpenSearchInstance();

	public java.lang.String getSchedulerClass();

	public void setSchedulerClass(java.lang.String schedulerClass);

	public com.liferay.portal.kernel.job.Scheduler getSchedulerInstance();

	public void addSchedulerEntry(
		com.liferay.portal.kernel.scheduler.SchedulerEntry schedulerEntry);

	public java.util.List<com.liferay.portal.kernel.scheduler.SchedulerEntry> getSchedulerEntries();

	public void setSchedulerEntries(
		java.util.List<com.liferay.portal.kernel.scheduler.SchedulerEntry> schedulerEntries);

	public java.lang.String getPortletURLClass();

	public void setPortletURLClass(java.lang.String portletURLClass);

	public java.lang.String getFriendlyURLMapperClass();

	public void setFriendlyURLMapperClass(
		java.lang.String friendlyURLMapperClass);

	public com.liferay.portal.kernel.portlet.FriendlyURLMapper getFriendlyURLMapperInstance();

	public java.lang.String getURLEncoderClass();

	public void setURLEncoderClass(java.lang.String urlEncoderClass);

	public com.liferay.portal.kernel.servlet.URLEncoder getURLEncoderInstance();

	public java.lang.String getPortletDataHandlerClass();

	public void setPortletDataHandlerClass(
		java.lang.String portletDataHandlerClass);

	public com.liferay.portal.lar.PortletDataHandler getPortletDataHandlerInstance();

	public com.liferay.portal.kernel.portlet.PortletLayoutListener getPortletLayoutListener();

	public java.lang.String getPortletLayoutListenerClass();

	public void setPortletLayoutListenerClass(
		java.lang.String portletLayoutListenerClass);

	public com.liferay.portal.kernel.portlet.PortletLayoutListener getPortletLayoutListenerInstance();

	public java.lang.String getPollerProcessorClass();

	public void setPollerProcessorClass(java.lang.String pollerProcessorClass);

	public com.liferay.portal.kernel.poller.PollerProcessor getPollerProcessorInstance();

	public java.lang.String getPopMessageListenerClass();

	public void setPopMessageListenerClass(
		java.lang.String popMessageListenerClass);

	public com.liferay.portal.kernel.pop.MessageListener getPopMessageListenerInstance();

	public java.lang.String getSocialActivityInterpreterClass();

	public void setSocialActivityInterpreterClass(
		java.lang.String socialActivityInterpreterClass);

	public com.liferay.portlet.social.model.SocialActivityInterpreter getSocialActivityInterpreterInstance();

	public java.lang.String getSocialRequestInterpreterClass();

	public void setSocialRequestInterpreterClass(
		java.lang.String socialRequestInterpreterClass);

	public com.liferay.portlet.social.model.SocialRequestInterpreter getSocialRequestInterpreterInstance();

	public java.lang.String getWebDAVStorageToken();

	public void setWebDAVStorageToken(java.lang.String webDAVStorageToken);

	public java.lang.String getWebDAVStorageClass();

	public void setWebDAVStorageClass(java.lang.String webDAVStorageClass);

	public com.liferay.portal.webdav.WebDAVStorage getWebDAVStorageInstance();

	public java.lang.String getControlPanelEntryCategory();

	public void setControlPanelEntryCategory(
		java.lang.String controlPanelEntryCategory);

	public double getControlPanelEntryWeight();

	public void setControlPanelEntryWeight(double controlPanelEntryWeight);

	public java.lang.String getControlPanelEntryClass();

	public void setControlPanelEntryClass(
		java.lang.String controlPanelEntryClass);

	public com.liferay.portlet.ControlPanelEntry getControlPanelEntryInstance();

	public java.util.List<String> getAssetRendererFactoryClasses();

	public void setAssetRendererFactoryClasses(
		java.util.List<String> assetRendererFactoryClasses);

	public java.util.List<com.liferay.portlet.asset.model.AssetRendererFactory> getAssetRendererFactoryInstances();

	public java.util.List<String> getCustomAttributesDisplayClasses();

	public void setCustomAttributesDisplayClasses(
		java.util.List<String> customAttributesDisplayClasses);

	public java.util.List<com.liferay.portlet.expando.model.CustomAttributesDisplay> getCustomAttributesDisplayInstances();

	public java.util.List<String> getWorkflowHandlerClasses();

	public void setWorkflowHandlerClasses(
		java.util.List<String> workflowHandlerClasses);

	public java.util.List<com.liferay.portal.kernel.workflow.WorkflowHandler> getWorkflowHandlerInstances();

	public java.lang.String getDefaultPreferences();

	public void setDefaultPreferences(java.lang.String defaultPreferences);

	public java.lang.String getPreferencesValidator();

	public void setPreferencesValidator(java.lang.String preferencesValidator);

	public boolean getPreferencesCompanyWide();

	public boolean isPreferencesCompanyWide();

	public void setPreferencesCompanyWide(boolean preferencesCompanyWide);

	public boolean getPreferencesUniquePerLayout();

	public boolean isPreferencesUniquePerLayout();

	public void setPreferencesUniquePerLayout(
		boolean preferencesUniquePerLayout);

	public boolean getPreferencesOwnedByGroup();

	public boolean isPreferencesOwnedByGroup();

	public void setPreferencesOwnedByGroup(boolean preferencesOwnedByGroup);

	public boolean getUseDefaultTemplate();

	public boolean isUseDefaultTemplate();

	public void setUseDefaultTemplate(boolean useDefaultTemplate);

	public boolean getShowPortletAccessDenied();

	public boolean isShowPortletAccessDenied();

	public void setShowPortletAccessDenied(boolean showPortletAccessDenied);

	public boolean getShowPortletInactive();

	public boolean isShowPortletInactive();

	public void setShowPortletInactive(boolean showPortletInactive);

	public boolean getActionURLRedirect();

	public boolean isActionURLRedirect();

	public void setActionURLRedirect(boolean actionURLRedirect);

	public boolean getRestoreCurrentView();

	public boolean isRestoreCurrentView();

	public void setRestoreCurrentView(boolean restoreCurrentView);

	public boolean getMaximizeEdit();

	public boolean isMaximizeEdit();

	public void setMaximizeEdit(boolean maximizeEdit);

	public boolean getMaximizeHelp();

	public boolean isMaximizeHelp();

	public void setMaximizeHelp(boolean maximizeHelp);

	public boolean getPopUpPrint();

	public boolean isPopUpPrint();

	public void setPopUpPrint(boolean popUpPrint);

	public boolean getLayoutCacheable();

	public boolean isLayoutCacheable();

	public void setLayoutCacheable(boolean layoutCacheable);

	public boolean getInstanceable();

	public boolean isInstanceable();

	public void setInstanceable(boolean instanceable);

	public boolean getScopeable();

	public boolean isScopeable();

	public void setScopeable(boolean scopeable);

	public java.lang.String getUserPrincipalStrategy();

	public void setUserPrincipalStrategy(java.lang.String userPrincipalStrategy);

	public boolean getPrivateRequestAttributes();

	public boolean isPrivateRequestAttributes();

	public void setPrivateRequestAttributes(boolean privateRequestAttributes);

	public boolean getPrivateSessionAttributes();

	public boolean isPrivateSessionAttributes();

	public void setPrivateSessionAttributes(boolean privateSessionAttributes);

	public int getRenderWeight();

	public void setRenderWeight(int renderWeight);

	public boolean getAjaxable();

	public boolean isAjaxable();

	public void setAjaxable(boolean ajaxable);

	public java.util.List<String> getHeaderPortalCss();

	public void setHeaderPortalCss(java.util.List<String> headerPortalCss);

	public java.util.List<String> getHeaderPortletCss();

	public void setHeaderPortletCss(java.util.List<String> headerPortletCss);

	public java.util.List<String> getHeaderPortalJavaScript();

	public void setHeaderPortalJavaScript(
		java.util.List<String> headerPortalJavaScript);

	public java.util.List<String> getHeaderPortletJavaScript();

	public void setHeaderPortletJavaScript(
		java.util.List<String> headerPortletJavaScript);

	public java.util.List<String> getFooterPortalCss();

	public void setFooterPortalCss(java.util.List<String> footerPortalCss);

	public java.util.List<String> getFooterPortletCss();

	public void setFooterPortletCss(java.util.List<String> footerPortletCss);

	public java.util.List<String> getFooterPortalJavaScript();

	public void setFooterPortalJavaScript(
		java.util.List<String> footerPortalJavaScript);

	public java.util.List<String> getFooterPortletJavaScript();

	public void setFooterPortletJavaScript(
		java.util.List<String> footerPortletJavaScript);

	public java.lang.String getCssClassWrapper();

	public void setCssClassWrapper(java.lang.String cssClassWrapper);

	public java.lang.String getFacebookIntegration();

	public void setFacebookIntegration(java.lang.String facebookIntegration);

	public boolean getAddDefaultResource();

	public boolean isAddDefaultResource();

	public void setAddDefaultResource(boolean addDefaultResource);

	public void setRoles(java.lang.String roles);

	public java.lang.String[] getRolesArray();

	public void setRolesArray(java.lang.String[] rolesArray);

	public java.util.Set<String> getUnlinkedRoles();

	public void setUnlinkedRoles(java.util.Set<String> unlinkedRoles);

	public java.util.Map<String, String> getRoleMappers();

	public void setRoleMappers(java.util.Map<String, String> roleMappers);

	public void linkRoles();

	public boolean hasRoleWithName(java.lang.String roleName);

	public boolean hasAddPortletPermission(long userId);

	public boolean getSystem();

	public boolean isSystem();

	public void setSystem(boolean system);

	public boolean getInclude();

	public boolean isInclude();

	public void setInclude(boolean include);

	public java.util.Map<String, String> getInitParams();

	public void setInitParams(java.util.Map<String, String> initParams);

	public java.lang.Integer getExpCache();

	public void setExpCache(java.lang.Integer expCache);

	public java.util.Map<String, java.util.Set<String>> getPortletModes();

	public void setPortletModes(
		java.util.Map<String, java.util.Set<String>> portletModes);

	public boolean hasPortletMode(java.lang.String mimeType,
		javax.portlet.PortletMode portletMode);

	public java.util.Set<String> getAllPortletModes();

	public boolean hasMultipleMimeTypes();

	public java.util.Map<String, java.util.Set<String>> getWindowStates();

	public void setWindowStates(
		java.util.Map<String, java.util.Set<String>> windowStates);

	public boolean hasWindowState(java.lang.String mimeType,
		javax.portlet.WindowState windowState);

	public java.util.Set<String> getAllWindowStates();

	public java.util.Set<String> getSupportedLocales();

	public void setSupportedLocales(java.util.Set<String> supportedLocales);

	public java.lang.String getResourceBundle();

	public void setResourceBundle(java.lang.String resourceBundle);

	public com.liferay.portal.model.PortletInfo getPortletInfo();

	public void setPortletInfo(com.liferay.portal.model.PortletInfo portletInfo);

	public java.util.Map<String, com.liferay.portal.model.PortletFilter> getPortletFilters();

	public void setPortletFilters(
		java.util.Map<String, com.liferay.portal.model.PortletFilter> portletFilters);

	public void addProcessingEvent(
		com.liferay.portal.kernel.xml.QName processingEvent);

	public com.liferay.portal.kernel.xml.QName getProcessingEvent(
		java.lang.String uri, java.lang.String localPart);

	public java.util.Set<com.liferay.portal.kernel.xml.QName> getProcessingEvents();

	public void setProcessingEvents(
		java.util.Set<com.liferay.portal.kernel.xml.QName> processingEvents);

	public void addPublishingEvent(
		com.liferay.portal.kernel.xml.QName publishingEvent);

	public java.util.Set<com.liferay.portal.kernel.xml.QName> getPublishingEvents();

	public void setPublishingEvents(
		java.util.Set<com.liferay.portal.kernel.xml.QName> publishingEvents);

	public void addPublicRenderParameter(
		com.liferay.portal.model.PublicRenderParameter publicRenderParameter);

	public com.liferay.portal.model.PublicRenderParameter getPublicRenderParameter(
		java.lang.String identifier);

	public com.liferay.portal.model.PublicRenderParameter getPublicRenderParameter(
		java.lang.String uri, java.lang.String localPart);

	public java.util.Set<com.liferay.portal.model.PublicRenderParameter> getPublicRenderParameters();

	public void setPublicRenderParameters(
		java.util.Set<com.liferay.portal.model.PublicRenderParameter> publicRenderParameters);

	public java.lang.String getContextPath();

	public com.liferay.portal.model.PortletApp getPortletApp();

	public void setPortletApp(com.liferay.portal.model.PortletApp portletApp);

	public com.liferay.portal.model.Portlet getClonedInstance(
		java.lang.String portletId);

	public boolean getStatic();

	public boolean isStatic();

	public void setStatic(boolean staticPortlet);

	public boolean getStaticStart();

	public boolean isStaticStart();

	public void setStaticStart(boolean staticPortletStart);

	public boolean getStaticEnd();

	public boolean isStaticEnd();

	public boolean getUndeployedPortlet();

	public boolean isUndeployedPortlet();

	public void setUndeployedPortlet(boolean undeployedPortlet);

	public java.lang.Object clone();

	public int compareTo(com.liferay.portal.model.Portlet portlet);

	public boolean equals(java.lang.Object obj);
}