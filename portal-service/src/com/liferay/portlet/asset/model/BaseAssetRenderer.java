/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.provider.AddPortletProvider;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.Date;
import java.util.Locale;

import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

/**
 * @author Jorge Ferrer
 * @author Sergio González
 */
public abstract class BaseAssetRenderer implements AssetRenderer {

	@Override
	public String getAddToPagePortletId() throws Exception {
		AddPortletProvider addPortletProvider = _serviceTrackerMap.getService(
			getClassName());

		if (addPortletProvider != null) {
			return addPortletProvider.getPortletId();
		}

		return PortletKeys.ASSET_PUBLISHER;
	}

	public AssetRendererFactory getAssetRendererFactory() {
		if (_assetRendererFactory != null) {
			return _assetRendererFactory;
		}

		_assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				getClassName());

		return _assetRendererFactory;
	}

	@Override
	public int getAssetRendererType() {
		return _assetRendererType;
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _AVAILABLE_LANGUAGE_IDS;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getAvailableLanguageIds}
	 */
	@Deprecated
	@Override
	public String[] getAvailableLocales() {
		return getAvailableLanguageIds();
	}

	@Override
	public DDMFieldReader getDDMFieldReader() {
		return _nullDDMFieldReader;
	}

	@Override
	public String getDiscussionPath() {
		return null;
	}

	@Override
	public Date getDisplayDate() {
		return null;
	}

	@Override
	@SuppressWarnings("unused")
	public String getIconCssClass() throws PortalException {
		return getAssetRendererFactory().getIconCssClass();
	}

	@Override
	public String getIconPath(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return getIconPath(themeDisplay);
	}

	@Override
	public String getNewName(String oldName, String token) {
		return TrashUtil.getNewName(oldName, token);
	}

	@Override
	public String getPreviewPath(
			PortletRequest portletRequest, PortletResponse PortletResponse)
		throws Exception {

		return "/html/portlet/asset_publisher/display/preview.jsp";
	}

	@Override
	public String getSearchSummary(Locale locale) {
		return getSummary(null, null);
	}

	@Override
	public String getSummary() {
		return getSummary(null, null);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getSummary(PortletRequest,
	 *             PortletResponse)}
	 */
	@Deprecated
	@Override
	public String getSummary(Locale locale) {
		return getSummary(null, null);
	}

	@Override
	public String[] getSupportedConversions() {
		return null;
	}

	@Override
	public String getThumbnailPath(PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getPathThemeImages() +
			"/file_system/large/default.png";
	}

	@Override
	public String getURLDownload(ThemeDisplay themeDisplay) {
		return null;
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		return null;
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState, PortletURL redirectURL)
		throws Exception {

		LiferayPortletURL editPortletURL = (LiferayPortletURL)getURLEdit(
			liferayPortletRequest, liferayPortletResponse);

		if (editPortletURL == null) {
			return null;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getScopeGroup();

		if (group.isLayout()) {
			Layout layout = themeDisplay.getLayout();

			group = layout.getGroup();
		}

		if (group.hasStagingGroup()) {
			return null;
		}

		editPortletURL.setDoAsGroupId(getGroupId());

		editPortletURL.setParameter("redirect", redirectURL.toString());

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String portletResource = ParamUtil.getString(
			liferayPortletRequest, "portletResource", portletDisplay.getId());

		if (Validator.isNotNull(portletResource)) {
			editPortletURL.setParameter(
				"referringPortletResource", portletResource);
		}
		else {
			editPortletURL.setParameter(
				"referringPortletResource", portletDisplay.getId());
		}

		editPortletURL.setPortletMode(PortletMode.VIEW);
		editPortletURL.setRefererPlid(themeDisplay.getPlid());
		editPortletURL.setWindowState(windowState);

		return editPortletURL;
	}

	@Override
	public PortletURL getURLExport(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		return null;
	}

	@Override
	public String getURLImagePreview(PortletRequest portletRequest)
		throws Exception {

		return getThumbnailPath(portletRequest);
	}

	@Override
	public String getUrlTitle() {
		return null;
	}

	@Override
	public PortletURL getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws Exception {

		return null;
	}

	@Override
	public PortletURL getURLViewDiffs(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		return null;
	}

	@Override
	public String getURLViewInContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect)
		throws Exception {

		return null;
	}

	@Override
	public String getViewInContextMessage() {
		return "view-in-context";
	}

	@Override
	@SuppressWarnings("unused")
	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return false;
	}

	@Override
	@SuppressWarnings("unused")
	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return true;
	}

	@Override
	public boolean isConvertible() {
		return false;
	}

	@Override
	public boolean isDisplayable() {
		return true;
	}

	@Override
	public boolean isLocalizable() {
		return false;
	}

	@Override
	public boolean isPreviewInContext() {
		return false;
	}

	@Override
	public boolean isPrintable() {
		return false;
	}

	public String renderActions(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		return null;
	}

	@Override
	public void setAddToPagePreferences(
			PortletPreferences portletPreferences, String portletId,
			ThemeDisplay themeDisplay)
		throws Exception {

		AddPortletProvider addPortletProvider = _serviceTrackerMap.getService(
			getClassName());

		if (addPortletProvider != null) {
			addPortletProvider.setPortletPreferences(
				portletPreferences, portletId, getClassName(), getClassPK(),
				themeDisplay);

			return;
		}

		portletPreferences.setValue("displayStyle", "full-content");
		portletPreferences.setValue(
			"emailAssetEntryAddedEnabled", Boolean.FALSE.toString());
		portletPreferences.setValue("selectionStyle", "manual");
		portletPreferences.setValue(
			"showAddContentButton", Boolean.FALSE.toString());

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			getClassName(), getClassPK());

		AssetPublisherUtil.addSelection(
			themeDisplay, portletPreferences, portletId,
			assetEntry.getEntryId(), -1, assetEntry.getClassName());
	}

	public void setAssetRendererType(int assetRendererType) {
		_assetRendererType = assetRendererType;
	}

	protected long getControlPanelPlid(
			LiferayPortletRequest liferayPortletRequest)
		throws PortalException {

		return PortalUtil.getControlPanelPlid(liferayPortletRequest);
	}

	protected long getControlPanelPlid(ThemeDisplay themeDisplay)
		throws PortalException {

		return PortalUtil.getControlPanelPlid(themeDisplay.getCompanyId());
	}

	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/page.png";
	}

	protected Locale getLocale(PortletRequest portletRequest) {
		if (portletRequest != null) {
			return portletRequest.getLocale();
		}

		return LocaleUtil.getMostRelevantLocale();
	}

	protected String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest, String noSuchEntryRedirect,
		String path, String primaryKeyParameterName,
		long primaryKeyParameterValue) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		StringBundler sb = new StringBundler(11);

		sb.append(themeDisplay.getPortalURL());
		sb.append(themeDisplay.getPathMain());
		sb.append(path);
		sb.append("?p_l_id=");
		sb.append(themeDisplay.getPlid());
		sb.append("&noSuchEntryRedirect=");
		sb.append(HttpUtil.encodeURL(noSuchEntryRedirect));
		sb.append(StringPool.AMPERSAND);
		sb.append(primaryKeyParameterName);
		sb.append(StringPool.EQUAL);
		sb.append(primaryKeyParameterValue);

		return PortalUtil.addPreservedParameters(themeDisplay, sb.toString());
	}

	private static final String[] _AVAILABLE_LANGUAGE_IDS = new String[0];

	private static final DDMFieldReader _nullDDMFieldReader =
		new NullDDMFieldReader();
	private static final ServiceTrackerMap<String, AddPortletProvider>
		_serviceTrackerMap = ServiceTrackerCollections.singleValueMap(
			AddPortletProvider.class, "model.class.name");

	static {
		_serviceTrackerMap.open();
	}

	private AssetRendererFactory _assetRendererFactory;
	private int _assetRendererType = AssetRendererFactory.TYPE_LATEST_APPROVED;

	private static final class NullDDMFieldReader implements DDMFieldReader {

		@Override
		public Fields getFields() {
			return new Fields();
		}

		@Override
		public Fields getFields(String ddmType) {
			return getFields();
		}

	}

}