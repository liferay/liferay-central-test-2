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

package com.liferay.product.navigation.site.administration.display.context;

import com.liferay.application.list.GroupProvider;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.exportimport.staging.StagingUtil;
import com.liferay.product.navigation.product.menu.web.display.context.ProductMenuDisplayContext;
import com.liferay.product.navigation.site.administration.application.list.SiteAdministrationPanelCategory;
import com.liferay.product.navigation.site.administration.constants.SiteAdministrationWebKeys;
import com.liferay.site.util.RecentGroupManager;

import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Julio Camarero
 */
public class SiteAdministrationPanelCategoryDisplayContext {

	public SiteAdministrationPanelCategoryDisplayContext(
			PortletRequest portletRequest, PortletResponse portletResponse,
			Group group)
		throws PortalException {

		_portletRequest = portletRequest;
		_portletResponse = portletResponse;

		if (group != null) {
			_group = group;
		}

		_groupProvider = (GroupProvider)portletRequest.getAttribute(
			SiteAdministrationWebKeys.GROUP_PROVIDER);
		_panelCategory = (PanelCategory)_portletRequest.getAttribute(
			ApplicationListWebKeys.PANEL_CATEGORY);
		_panelCategoryHelper =
			(PanelCategoryHelper)_portletRequest.getAttribute(
				ApplicationListWebKeys.PANEL_CATEGORY_HELPER);
		_recentGroupManager = (RecentGroupManager)portletRequest.getAttribute(
			SiteAdministrationWebKeys.RECENT_GROUP_MANAGER);
		_themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Group getGroup() {
		if (_group != null) {
			return _group;
		}

		_group = _themeDisplay.getScopeGroup();

		if (!_group.isControlPanel()) {
			updateLatentGroup(_group.getGroupId());

			return _group;
		}

		_group = _groupProvider.getGroup(
			PortalUtil.getHttpServletRequest(_portletRequest));

		return _group;
	}

	public String getGroupName() throws PortalException {
		if (_groupName != null) {
			return _groupName;
		}

		Group group = getGroup();

		if (group == null) {
			_groupName = StringPool.BLANK;
		}
		else {
			if (group.isUser()) {
				if (group.getClassPK() == _themeDisplay.getUserId()) {
					_groupName = LanguageUtil.get(
						_themeDisplay.getRequest(), "my-site");
				}
				else {
					User user = UserLocalServiceUtil.getUser(
						group.getClassPK());

					_groupName = LanguageUtil.format(
						getResourceBundle(), "x-site", user.getFullName());
				}
			}
			else {
				_groupName = group.getDescriptiveName(
					_themeDisplay.getLocale());
			}
		}

		return _groupName;
	}

	public String getGroupURL() {
		if (_groupURL != null) {
			return _groupURL;
		}

		_groupURL = StringPool.BLANK;

		Group group = getGroup();

		return getGroupURL(group);
	}

	public String getGroupURL(boolean privateLayout) {
		if (_groupURL != null) {
			return _groupURL;
		}

		_groupURL = StringPool.BLANK;

		Group group = getGroup();

		return getGroupURL(group, privateLayout);
	}

	public String getLiveGroupURL() {
		if (_liveGroupURL != null) {
			return _liveGroupURL;
		}

		_liveGroupURL = StringPool.BLANK;

		Group group = getGroup();

		if (group.isStagingGroup()) {
			if (group.isStagedRemotely()) {
				_liveGroupURL = StagingUtil.buildRemoteURL(
					group.getTypeSettingsProperties());
			}
			else {
				Group liveGroup = StagingUtil.getLiveGroup(group.getGroupId());

				if (liveGroup != null) {
					Layout layout = _themeDisplay.getLayout();

					_liveGroupURL = getGroupURL(
						liveGroup, layout.isPrivateLayout());
				}
			}
		}

		return _liveGroupURL;
	}

	public String getLogoURL() {
		if (Validator.isNotNull(_logoURL)) {
			return _logoURL;
		}

		_logoURL = StringPool.BLANK;

		Group group = getGroup();

		if (group == null) {
			return _logoURL;
		}

		_logoURL = group.getLogoURL(_themeDisplay, false);

		return _logoURL;
	}

	public String getManageSitesURL() throws PortalException {
		if (_manageSitesURL != null) {
			return _manageSitesURL;
		}

		_manageSitesURL = StringPool.BLANK;

		String portletId = PortletProviderUtil.getPortletId(
			Group.class.getName(), PortletProvider.Action.MANAGE);

		if (Validator.isNotNull(portletId) &&
			PortletPermissionUtil.hasControlPanelAccessPermission(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(), portletId)) {

			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				_portletRequest, Group.class.getName(),
				PortletProvider.Action.MANAGE);

			_manageSitesURL = portletURL.toString();
		}

		return _manageSitesURL;
	}

	public List<Group> getMySites() throws PortalException {
		if (_mySites != null) {
			return _mySites;
		}

		User user = _themeDisplay.getUser();

		_mySites = user.getMySiteGroups(
			new String[] {
				Company.class.getName(), Group.class.getName(),
				Organization.class.getName()
			},
			PropsValues.MY_SITES_MAX_ELEMENTS);

		return _mySites;
	}

	public int getNotificationsCount() {
		if (_notificationsCount != null) {
			return _notificationsCount.intValue();
		}

		_notificationsCount = 0;

		Group group = getGroup();

		if (group == null) {
			return _notificationsCount;
		}

		SiteAdministrationPanelCategory siteAdministrationPanelCategory =
			(SiteAdministrationPanelCategory)_portletRequest.getAttribute(
				ApplicationListWebKeys.PANEL_CATEGORY);

		_notificationsCount = _panelCategoryHelper.getNotificationsCount(
			siteAdministrationPanelCategory.getKey(),
			_themeDisplay.getPermissionChecker(), getGroup(),
			_themeDisplay.getUser());

		return _notificationsCount;
	}

	public PanelCategory getPanelCategory() {
		return _panelCategory;
	}

	public String getStagingGroupURL() {
		if (_stagingGroupURL != null) {
			return _stagingGroupURL;
		}

		_stagingGroupURL = StringPool.BLANK;

		Group group = getGroup();

		if (!group.isStagedRemotely() && group.hasStagingGroup()) {
			Group stagingGroup = StagingUtil.getStagingGroup(
				group.getGroupId());

			if (stagingGroup != null) {
				Layout layout = _themeDisplay.getLayout();

				_stagingGroupURL = getGroupURL(
					stagingGroup, layout.isPrivateLayout());
			}
		}

		return _stagingGroupURL;
	}

	public String getStagingLabel() throws PortalException {
		if (_stagingLabel != null) {
			return _stagingLabel;
		}

		Group group = getGroup();

		_stagingLabel = StringPool.BLANK;

		if (isShowStagingInfo()) {
			if (group.isStagingGroup()) {
				_stagingLabel = "staging";
			}
			else if (group.hasStagingGroup()) {
				_stagingLabel = "live";
			}
		}

		return _stagingLabel;
	}

	public boolean isCollapsedPanel() throws PortalException {
		if (_collapsedPanel != null) {
			return _collapsedPanel;
		}

		ProductMenuDisplayContext productMenuDisplayContext =
			new ProductMenuDisplayContext(_portletRequest, _portletResponse);

		_collapsedPanel = Validator.equals(
			_panelCategory.getKey(),
			productMenuDisplayContext.getRootPanelCategoryKey());

		return _collapsedPanel;
	}

	public boolean isDisplaySiteLink() {
		Group group = getGroup();

		if (group.hasPrivateLayouts() || group.hasPublicLayouts()) {
			return true;
		}

		return false;
	}

	public boolean isSelectedSite() {
		if (_selectedSite != null) {
			return _selectedSite.booleanValue();
		}

		_selectedSite = false;

		Group group = getGroup();

		if (group == null) {
			return false;
		}

		Layout layout = _themeDisplay.getLayout();

		if (layout != null) {
			if (layout.getGroupId() == group.getGroupId()) {
				_selectedSite = true;
			}
			else if (group.hasStagingGroup()) {
				Group stagingGroup = group.getStagingGroup();

				if (layout.getGroupId() == stagingGroup.getGroupId()) {
					_selectedSite = true;
				}
			}
		}

		return _selectedSite;
	}

	public boolean isShowSiteAdministration() throws PortalException {
		Group group = getGroup();

		if (group == null) {
			return false;
		}

		if (GroupPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), group,
				ActionKeys.VIEW_SITE_ADMINISTRATION)) {

			return true;
		}

		return false;
	}

	public boolean isShowSiteSelector() throws PortalException {
		List<Group> mySites = getMySites();

		if (mySites.isEmpty()) {
			return false;
		}

		return true;
	}

	public boolean isShowStagingInfo() throws PortalException {
		if (_showStagingInfo != null) {
			return _showStagingInfo.booleanValue();
		}

		_showStagingInfo = false;

		Group group = getGroup();

		if (group == null) {
			return _showStagingInfo;
		}

		if (!group.isStaged() && !group.isStagingGroup()) {
			return _showStagingInfo;
		}

		if (!hasStagingPermission()) {
			return _showStagingInfo;
		}

		_showStagingInfo = true;

		return _showStagingInfo;
	}

	protected String getGroupAdministrationURL(Group group) {
		PortletURL groupAdministrationURL = null;

		if (_panelCategoryHelper == null) {
			return null;
		}

		String portletId = _panelCategoryHelper.getFirstPortletId(
			PanelCategoryKeys.SITE_ADMINISTRATION,
			_themeDisplay.getPermissionChecker(), group);

		if (Validator.isNotNull(portletId)) {
			groupAdministrationURL = PortalUtil.getControlPanelPortletURL(
				_portletRequest, group, portletId, 0, 0,
				PortletRequest.RENDER_PHASE);

			if (groupAdministrationURL != null) {
				return groupAdministrationURL.toString();
			}
		}

		return null;
	}

	protected String getGroupURL(Group group) {
		String groupDisplayURL = group.getDisplayURL(_themeDisplay, false);

		if (Validator.isNotNull(groupDisplayURL)) {
			return groupDisplayURL;
		}

		groupDisplayURL = group.getDisplayURL(_themeDisplay, true);

		if (Validator.isNotNull(groupDisplayURL)) {
			return groupDisplayURL;
		}

		return getGroupAdministrationURL(group);
	}

	protected String getGroupURL(Group group, boolean privateLayout) {
		String groupDisplayURL = group.getDisplayURL(
			_themeDisplay, privateLayout);

		if (Validator.isNotNull(groupDisplayURL)) {
			return groupDisplayURL;
		}

		return getGroupAdministrationURL(group);
	}

	protected ResourceBundle getResourceBundle() {
		return ResourceBundleUtil.getBundle(
			"content.Language", _themeDisplay.getLocale(), getClass());
	}

	protected HttpSession getSession() {
		HttpServletRequest request = PortalUtil.getOriginalServletRequest(
			PortalUtil.getHttpServletRequest(_portletRequest));

		return request.getSession();
	}

	protected boolean hasStagingPermission() throws PortalException {
		if (!GroupPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), getGroup(),
				ActionKeys.MANAGE_STAGING)) {

			return false;
		}

		if (!GroupPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), getGroup(),
				ActionKeys.PUBLISH_STAGING)) {

			return false;
		}

		if (!GroupPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), getGroup(),
				ActionKeys.VIEW_STAGING)) {

			return false;
		}

		return true;
	}

	protected void updateLatentGroup(long groupId) {
		if (groupId <= 0) {
			return;
		}

		_recentGroupManager.addRecentGroup(
			PortalUtil.getHttpServletRequest(_portletRequest), groupId);

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			_portletRequest);

		Group latentGroup = _groupProvider.getGroup(request);

		if ((latentGroup == null) || (groupId != latentGroup.getGroupId())) {
			_groupProvider.setGroup(request, _group);
		}
	}

	private Boolean _collapsedPanel;
	private Group _group;
	private String _groupName;
	private final GroupProvider _groupProvider;
	private String _groupURL;
	private String _liveGroupURL;
	private String _logoURL;
	private String _manageSitesURL;
	private List<Group> _mySites;
	private Integer _notificationsCount;
	private final PanelCategory _panelCategory;
	private final PanelCategoryHelper _panelCategoryHelper;
	private final PortletRequest _portletRequest;
	private final PortletResponse _portletResponse;
	private final RecentGroupManager _recentGroupManager;
	private Boolean _selectedSite;
	private Boolean _showStagingInfo = null;
	private String _stagingGroupURL;
	private String _stagingLabel;
	private final ThemeDisplay _themeDisplay;

}