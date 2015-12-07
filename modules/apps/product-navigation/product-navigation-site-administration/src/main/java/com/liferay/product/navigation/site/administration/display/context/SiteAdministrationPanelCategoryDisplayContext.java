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

import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.exportimport.staging.StagingUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * @author Julio Camarero
 */
public class SiteAdministrationPanelCategoryDisplayContext {

	public SiteAdministrationPanelCategoryDisplayContext(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortalException {

		_portletRequest = portletRequest;
		_portletResponse = portletResponse;

		_panelCategoryHelper =
			(PanelCategoryHelper)_portletRequest.getAttribute(
				ApplicationListWebKeys.PANEL_CATEGORY_HELPER);
		_themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Group getGroup() {
		if (_group != null) {
			return _group;
		}

		_group = _themeDisplay.getScopeGroup();

		return _group;
	}

	public String getGroupName() throws PortalException {
		if (Validator.isNotNull(_groupName)) {
			return _groupName;
		}

		Group group = getGroup();

		_groupName = group.getDescriptiveName(_themeDisplay.getLocale());

		return _groupName;
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

		Group group = getGroup();

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

		Group group = getGroup();

		if (Validator.isNotNull(portletId) &&
			PortletPermissionUtil.hasControlPanelAccessPermission(
				_themeDisplay.getPermissionChecker(), group.getGroupId(),
				portletId)) {

			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				_portletRequest, Group.class.getName(),
				PortletProvider.Action.MANAGE);

			_manageSitesURL = portletURL.toString();
		}

		return _manageSitesURL;
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

	public boolean isShowStagingInfo() throws PortalException {
		if (_showStagingInfo != null) {
			return _showStagingInfo.booleanValue();
		}

		Group group = getGroup();

		_showStagingInfo = false;

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

	protected String getGroupURL(Group group, boolean privateLayout) {
		String groupDisplayURL = group.getDisplayURL(
			_themeDisplay, privateLayout);

		if (Validator.isNotNull(groupDisplayURL)) {
			return groupDisplayURL;
		}

		return getGroupAdministrationURL(group);
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

	private Group _group;
	private String _groupName;
	private String _liveGroupURL;
	private String _logoURL;
	private String _manageSitesURL;
	private final PanelCategoryHelper _panelCategoryHelper;
	private final PortletRequest _portletRequest;
	private final PortletResponse _portletResponse;
	private Boolean _showStagingInfo = null;
	private String _stagingGroupURL;
	private String _stagingLabel;
	private final ThemeDisplay _themeDisplay;

}