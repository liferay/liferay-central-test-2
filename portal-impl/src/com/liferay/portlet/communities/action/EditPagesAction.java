/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.communities.action;

import com.liferay.portal.LayoutFriendlyURLException;
import com.liferay.portal.LayoutHiddenException;
import com.liferay.portal.LayoutNameException;
import com.liferay.portal.LayoutParentLayoutIdException;
import com.liferay.portal.LayoutSetVirtualHostException;
import com.liferay.portal.LayoutTypeException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.RemoteExportException;
import com.liferay.portal.RequiredLayoutException;
import com.liferay.portal.events.EventsProcessor;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.SafeProperties;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.LayoutSetServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.ThemeLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.communities.util.CommunitiesUtil;
import com.liferay.portlet.communities.util.StagingUtil;
import com.liferay.portlet.tasks.NoSuchProposalException;
import com.liferay.util.servlet.UploadException;

import java.io.File;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditPagesAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class EditPagesAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		try {
			checkPermissions(req);
		}
		catch (PrincipalException pe) {
			return;
		}

		String cmd = ParamUtil.getString(req, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateLayout(req, res);
			}
			else if (cmd.equals(Constants.DELETE)) {
				CommunitiesUtil.deleteLayout(req, res);
			}
			else if (cmd.equals("copy_from_live")) {
				StagingUtil.copyFromLive(req);
			}
			else if (cmd.equals("display_order")) {
				updateDisplayOrder(req);
			}
			else if (cmd.equals("export_remotely")) {
				StagingUtil.exportRemotely(req);
			}
			else if (cmd.equals("logo")) {
				updateLogo(req);
			}
			else if (cmd.equals("look_and_feel")) {
				updateLookAndFeel(req);
			}
			else if (cmd.equals("monitoring")) {
				updateMonitoring(req);
			}
			else if (cmd.equals("publish_to_live")) {
				StagingUtil.publishToLive(req);
			}
			else if (cmd.equals("schedule_publish_to_live")) {
				StagingUtil.schedulePublishToLive(req);
			}
			else if (cmd.equals("staging")) {
				StagingUtil.updateStaging(req);
			}
			else if (cmd.equals("unschedule_publish_to_live")) {
				StagingUtil.unschedulePublishToLive(req);
			}
			else if (cmd.equals("unschedule_remote_export")) {
				StagingUtil.unscheduleRemoteExport(req);
			}
			else if (cmd.equals("virtual_host")) {
				updateVirtualHost(req);
			}
			else if (cmd.equals("workflow")) {
				updateWorkflow(req);
			}

			String redirect = ParamUtil.getString(req, "pagesRedirect");

			sendRedirect(req, res, redirect);
		}
		catch (Exception e) {
			if (e instanceof NoSuchLayoutException ||
				e instanceof NoSuchProposalException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				setForward(req, "portlet.communities.error");
			}
			else if (e instanceof LayoutFriendlyURLException ||
					 e instanceof LayoutHiddenException ||
					 e instanceof LayoutNameException ||
					 e instanceof LayoutParentLayoutIdException ||
					 e instanceof LayoutSetVirtualHostException ||
					 e instanceof LayoutTypeException ||
					 e instanceof RemoteExportException ||
					 e instanceof RequiredLayoutException ||
					 e instanceof UploadException) {

				if (e instanceof LayoutFriendlyURLException) {
					SessionErrors.add(
						req, LayoutFriendlyURLException.class.getName(), e);
				}
				else {
					SessionErrors.add(req, e.getClass().getName(), e);
				}
			}
			else {
				throw e;
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {

		try {
			checkPermissions(req);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(req, PrincipalException.class.getName());

			return mapping.findForward("portlet.communities.error");
		}

		try {
			ActionUtil.getGroup(req);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				return mapping.findForward("portlet.communities.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(req, "portlet.communities.edit_pages"));
	}

	public void serveResource(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ResourceRequest req, ResourceResponse res)
		throws Exception {

		String path =
			"/html/portlet/communities/scheduled_publish_to_live_events.jsp";

		PortletRequestDispatcher prd =
			config.getPortletContext().getRequestDispatcher(path);

		prd.include(req, res);
	}

	protected void checkPermissions(PortletRequest req) throws Exception {

		// LEP-850

		ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		long groupId = ParamUtil.getLong(req, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(req, "privateLayout");

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.isCommunity()) {
			if (!GroupPermissionUtil.contains(
					permissionChecker, group.getGroupId(),
					ActionKeys.APPROVE_PROPOSAL) &&
				!GroupPermissionUtil.contains(
					permissionChecker, group.getGroupId(),
					ActionKeys.MANAGE_LAYOUTS)) {

				throw new PrincipalException();
			}
		}
		else if (group.isOrganization()) {
			long organizationId = group.getClassPK();

			if (!OrganizationPermissionUtil.contains(
					permissionChecker, organizationId,
					ActionKeys.APPROVE_PROPOSAL) &&
				!OrganizationPermissionUtil.contains(
					permissionChecker, organizationId,
					ActionKeys.MANAGE_LAYOUTS)) {

				throw new PrincipalException();
			}
		}
		else if (group.isUser()) {
			long groupUserId = group.getClassPK();

			User groupUser = UserLocalServiceUtil.getUserById(groupUserId);

			long[] organizationIds = groupUser.getOrganizationIds();

			UserPermissionUtil.check(
				permissionChecker, groupUserId, organizationIds,
				ActionKeys.UPDATE);

			if ((privateLayout &&
				 !PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_MODIFIABLE) ||
				(!privateLayout &&
				 !PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_MODIFIABLE)) {

				throw new PrincipalException();
			}
		}
	}

	protected void copyPreferences(
			ActionRequest req, Layout layout, Layout copyLayout)
		throws Exception {

		long companyId = layout.getCompanyId();

		LayoutTypePortlet copyLayoutTypePortlet =
			(LayoutTypePortlet)copyLayout.getLayoutType();

		List<String> copyPortletIds = copyLayoutTypePortlet.getPortletIds();

		for (String copyPortletId : copyPortletIds) {
			HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(req);

			// Copy preference

			PortletPreferencesIds portletPreferencesIds =
				PortletPreferencesFactoryUtil.getPortletPreferencesIds(
					httpReq, layout, copyPortletId);

			PortletPreferencesLocalServiceUtil.getPreferences(
				portletPreferencesIds);

			PortletPreferencesIds copyPortletPreferencesIds =
				PortletPreferencesFactoryUtil.getPortletPreferencesIds(
					httpReq, copyLayout, copyPortletId);

			PortletPreferences copyPrefs =
				PortletPreferencesLocalServiceUtil.getPreferences(
					copyPortletPreferencesIds);

			PortletPreferencesLocalServiceUtil.updatePreferences(
				portletPreferencesIds.getOwnerId(),
				portletPreferencesIds.getOwnerType(),
				portletPreferencesIds.getPlid(),
				portletPreferencesIds.getPortletId(), copyPrefs);

			// Copy portlet setup

			PortletPreferencesLocalServiceUtil.getPreferences(
				companyId, PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
				copyPortletId);

			copyPrefs =
				PortletPreferencesLocalServiceUtil.getPreferences(
					companyId, PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, copyLayout.getPlid(),
					copyPortletId);

			PortletPreferencesLocalServiceUtil.updatePreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
				copyPortletId, copyPrefs);
		}
	}

	protected Properties getTypeSettingsProperties(ActionRequest req) {
		Properties typeSettingsProperties = new SafeProperties();

		String prefix = "TypeSettingsProperties(";

		for (String paramName: req.getParameterMap().keySet()) {
			if (paramName.startsWith(prefix)) {
				String key = paramName.substring(
					prefix.length(), paramName.length() - 1);

				typeSettingsProperties.setProperty(
					key, req.getParameter(paramName));
			}
		}

		return typeSettingsProperties;
	}

	protected void updateDisplayOrder(ActionRequest req) throws Exception {
		long groupId = ParamUtil.getLong(req, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(req, "privateLayout");
		long parentLayoutId = ParamUtil.getLong(req, "parentLayoutId");
		long[] layoutIds = StringUtil.split(
			ParamUtil.getString(req, "layoutIds"), 0L);

		LayoutServiceUtil.setLayouts(
			groupId, privateLayout, parentLayoutId, layoutIds);
	}

	protected void updateLayout(ActionRequest req, ActionResponse res)
		throws Exception {

		UploadPortletRequest uploadReq = PortalUtil.getUploadPortletRequest(
			req);

		String cmd = ParamUtil.getString(uploadReq, Constants.CMD);

		long groupId = ParamUtil.getLong(req, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(req, "privateLayout");
		long layoutId = ParamUtil.getLong(req, "layoutId");
		long parentLayoutId = ParamUtil.getLong(uploadReq, "parentLayoutId");
		String description = ParamUtil.getString(uploadReq, "description");
		String type = ParamUtil.getString(uploadReq, "type");
		boolean hidden = ParamUtil.getBoolean(uploadReq, "hidden");
		String friendlyURL = ParamUtil.getString(uploadReq, "friendlyURL");
		boolean iconImage = ParamUtil.getBoolean(uploadReq, "iconImage");
		byte[] iconBytes = FileUtil.getBytes(
			uploadReq.getFile("iconFileName"));

		boolean inheritFromParentLayoutId = ParamUtil.getBoolean(
			uploadReq, "inheritFromParentLayoutId");

		long copyLayoutId = ParamUtil.getLong(uploadReq, "copyLayoutId");

		long junctionPlid = ParamUtil.getLong(uploadReq, "junctionPlid");

		Locale[] locales = LanguageUtil.getAvailableLocales();

		Map<Locale, String> localeNamesMap = new HashMap<Locale, String>();
		Map<Locale, String> localeTitlesMap = new HashMap<Locale, String>();

		for (Locale locale : locales) {
			String languageId = LocaleUtil.toLanguageId(locale);

			localeNamesMap.put(
				locale, ParamUtil.getString(uploadReq, "name_" + languageId));
			localeTitlesMap.put(
				locale, ParamUtil.getString(uploadReq, "title_" + languageId));
		}

		if (cmd.equals(Constants.ADD)) {

			// Add layout

			if (inheritFromParentLayoutId && (parentLayoutId > 0)) {
				Layout parentLayout = LayoutLocalServiceUtil.getLayout(
					groupId, privateLayout, parentLayoutId);

				Layout layout = LayoutServiceUtil.addLayout(
					groupId, privateLayout, parentLayoutId, localeNamesMap,
					localeTitlesMap, description, parentLayout.getType(),
					parentLayout.isHidden(), friendlyURL);

				LayoutServiceUtil.updateLayout(
					layout.getGroupId(), layout.isPrivateLayout(),
					layout.getLayoutId(), parentLayout.getTypeSettings());
			}
			else {
				LayoutServiceUtil.addLayout(
					groupId, privateLayout, parentLayoutId, localeNamesMap,
					localeTitlesMap, description, type, hidden, friendlyURL);
			}
		}
		else {

			// Update layout

			Layout layout = LayoutLocalServiceUtil.getLayout(
				groupId, privateLayout, layoutId);

			layout = LayoutServiceUtil.updateLayout(
				groupId, privateLayout, layoutId, layout.getParentLayoutId(),
				localeNamesMap, localeTitlesMap, description, type, hidden,
				friendlyURL, Boolean.valueOf(iconImage), iconBytes);

			Properties formTypeSettingsProperties = getTypeSettingsProperties(
				req);

			if (type.equals(LayoutConstants.TYPE_PORTLET)) {
				if ((copyLayoutId > 0) &&
					(copyLayoutId != layout.getLayoutId())) {

					try {
						Layout copyLayout = LayoutLocalServiceUtil.getLayout(
							groupId, privateLayout, copyLayoutId);

						if (copyLayout.getType().equals(
								LayoutConstants.TYPE_PORTLET)) {

							LayoutServiceUtil.updateLayout(
								groupId, privateLayout, layoutId,
								copyLayout.getTypeSettings());

							copyPreferences(req, layout, copyLayout);
						}
					}
					catch (NoSuchLayoutException nsle) {
					}
				}
				else {
					Properties layoutTypeSettingsProperties =
						layout.getTypeSettingsProperties();

					for (Object property: formTypeSettingsProperties.keySet()) {
						layoutTypeSettingsProperties.put(
							property, formTypeSettingsProperties.get(property));
					}

					LayoutServiceUtil.updateLayout(
						groupId, privateLayout, layoutId,
						layout.getTypeSettings());
				}

				LayoutServiceUtil.updateJunctionPlid(
					layout.getPlid(), junctionPlid);
			}
			else {
				layout.setTypeSettingsProperties(formTypeSettingsProperties);

				LayoutServiceUtil.updateLayout(
					groupId, privateLayout, layoutId, layout.getTypeSettings());
			}

			HttpServletResponse httpRes = PortalUtil.getHttpServletResponse(
				res);

			String[] eventClasses = StringUtil.split(
				PropsUtil.get(
					PropsKeys.LAYOUT_CONFIGURATION_ACTION_UPDATE,
					new Filter(type)));

			EventsProcessor.process(
				PropsKeys.LAYOUT_CONFIGURATION_ACTION_UPDATE, eventClasses,
				uploadReq, httpRes);
		}
	}

	protected void updateLogo(ActionRequest req) throws Exception {
		UploadPortletRequest uploadReq = PortalUtil.getUploadPortletRequest(
			req);

		long liveGroupId = ParamUtil.getLong(req, "liveGroupId");
		long stagingGroupId = ParamUtil.getLong(req, "stagingGroupId");

		boolean privateLayout = ParamUtil.getBoolean(req, "privateLayout");
		boolean logo = ParamUtil.getBoolean(req, "logo");

		File file = uploadReq.getFile("logoFileName");
		byte[] bytes = FileUtil.getBytes(file);

		if (logo && ((bytes == null) || (bytes.length == 0))) {
			throw new UploadException();
		}

		LayoutSetServiceUtil.updateLogo(liveGroupId, privateLayout, logo, file);

		if (stagingGroupId > 0) {
			LayoutSetServiceUtil.updateLogo(
				stagingGroupId, privateLayout, logo, file);
		}
	}

	protected void updateLookAndFeel(ActionRequest req) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(
			WebKeys.THEME_DISPLAY);

		long companyId = themeDisplay.getCompanyId();

		long liveGroupId = ParamUtil.getLong(req, "liveGroupId");
		long stagingGroupId = ParamUtil.getLong(req, "stagingGroupId");

		boolean privateLayout = ParamUtil.getBoolean(req, "privateLayout");
		long layoutId = ParamUtil.getLong(req, "layoutId");
		String themeId = ParamUtil.getString(req, "themeId");
		String colorSchemeId = ParamUtil.getString(req, "colorSchemeId");
		String css = ParamUtil.getString(req, "css");
		boolean wapTheme = ParamUtil.getBoolean(req, "wapTheme");

		updateLookAndFeel(
			companyId, liveGroupId, privateLayout, layoutId, themeId,
			colorSchemeId, css, wapTheme);

		if (stagingGroupId > 0) {
			updateLookAndFeel(
				companyId, stagingGroupId, privateLayout, layoutId, themeId,
				colorSchemeId, css, wapTheme);
		}
	}

	protected void updateLookAndFeel(
			long companyId, long groupId, boolean privateLayout, long layoutId,
			String themeId, String colorSchemeId, String css, boolean wapTheme)
		throws Exception {

		if (Validator.isNotNull(themeId) && Validator.isNull(colorSchemeId)) {
			ColorScheme colorScheme = ThemeLocalServiceUtil.getColorScheme(
				companyId, themeId, colorSchemeId, wapTheme);

			colorSchemeId = colorScheme.getColorSchemeId();
		}

		if (layoutId <= 0) {
			LayoutSetServiceUtil.updateLookAndFeel(
				groupId, privateLayout, themeId, colorSchemeId, css, wapTheme);
		}
		else {
			LayoutServiceUtil.updateLookAndFeel(
				groupId, privateLayout, layoutId, themeId, colorSchemeId, css,
				wapTheme);
		}
	}

	protected void updateMonitoring(ActionRequest req) throws Exception {
		long liveGroupId = ParamUtil.getLong(req, "liveGroupId");

		String googleAnalyticsId = ParamUtil.getString(
			req, "googleAnalyticsId");

		Group liveGroup = GroupLocalServiceUtil.getGroup(liveGroupId);

		Properties props = liveGroup.getTypeSettingsProperties();

		props.setProperty("googleAnalyticsId", googleAnalyticsId);

		GroupServiceUtil.updateGroup(liveGroupId, liveGroup.getTypeSettings());
	}

	protected void updateVirtualHost(ActionRequest req) throws Exception {
		long liveGroupId = ParamUtil.getLong(req, "liveGroupId");

		String publicVirtualHost = ParamUtil.getString(
			req, "publicVirtualHost");
		String privateVirtualHost = ParamUtil.getString(
			req, "privateVirtualHost");
		String friendlyURL = ParamUtil.getString(req, "friendlyURL");

		LayoutSetServiceUtil.updateVirtualHost(
			liveGroupId, false, publicVirtualHost);

		LayoutSetServiceUtil.updateVirtualHost(
			liveGroupId, true, privateVirtualHost);

		GroupServiceUtil.updateFriendlyURL(liveGroupId, friendlyURL);
	}

	protected void updateWorkflow(ActionRequest req) throws Exception {
		long liveGroupId = ParamUtil.getLong(req, "liveGroupId");

		boolean workflowEnabled = ParamUtil.getBoolean(req, "workflowEnabled");
		int workflowStages = ParamUtil.getInteger(req, "workflowStages");

		StringMaker sm = new StringMaker();

		for (int i = 1; i <= workflowStages; i++) {
			String workflowRoleName = ParamUtil.getString(
				req, "workflowRoleName_" + i);

			sm.append(workflowRoleName);

			if ((i + 1) <= workflowStages) {
				sm.append(",");
			}
		}

		String workflowRoleNames = sm.toString();

		GroupServiceUtil.updateWorkflow(
			liveGroupId, workflowEnabled, workflowStages, workflowRoleNames);
	}

}