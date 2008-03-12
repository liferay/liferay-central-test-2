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

import com.germinus.easyconf.Filter;

import com.liferay.portal.LayoutFriendlyURLException;
import com.liferay.portal.LayoutHiddenException;
import com.liferay.portal.LayoutNameException;
import com.liferay.portal.LayoutParentLayoutIdException;
import com.liferay.portal.LayoutSetVirtualHostException;
import com.liferay.portal.LayoutTypeException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.PortalException;
import com.liferay.portal.RequiredLayoutException;
import com.liferay.portal.SystemException;
import com.liferay.portal.events.EventsProcessor;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.LayoutSetServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.impl.ThemeLocalUtil;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.communities.util.CommunitiesUtil;
import com.liferay.portlet.tasks.NoSuchProposalException;
import com.liferay.util.FileUtil;
import com.liferay.util.servlet.SessionErrors;
import com.liferay.util.servlet.UploadException;
import com.liferay.util.servlet.UploadPortletRequest;

import java.io.ByteArrayInputStream;
import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Properties;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
				copyFromLive(req);
			}
			else if (cmd.equals("display_order")) {
				updateDisplayOrder(req);
			}
			else if (cmd.equals("logo")) {
				updateLogo(req);
			}
			else if (cmd.equals("look_and_feel")) {
				updateLookAndFeel(req);
			}
			else if (cmd.equals("merge_pages")) {
				updateMergePages(req);
			}
			else if (cmd.equals("monitoring")) {
				updateMonitoring(req);
			}
			else if (cmd.equals("publish_to_live")) {
				publishToLive(req);
			}
			else if (cmd.equals("staging")) {
				updateStaging(req);
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

	protected void checkPermissions(PortletRequest req) throws Exception {

		// LEP-850

		ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		long groupId = ParamUtil.getLong(req, "groupId");

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

			if (!groupUser.isLayoutsRequired()) {
				throw new PrincipalException();
			}
		}
	}

	protected void copyFromLive(ActionRequest req) throws Exception {
		String tabs2 = ParamUtil.getString(req, "tabs2");

		long stagingGroupId = ParamUtil.getLong(req, "stagingGroupId");

		Group stagingGroup = GroupLocalServiceUtil.getGroup(stagingGroupId);

		boolean privateLayout = true;

		if (tabs2.equals("public-pages")) {
			privateLayout = false;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Copying live to staging for group " +
					stagingGroup.getLiveGroupId());
		}

		Map parameterMap = getStagingParameters();

		String scope = ParamUtil.getString(req, "scope");

		if (scope.equals("all-pages")) {
			copyLayouts(
				stagingGroup.getLiveGroupId(), stagingGroup.getGroupId(),
				privateLayout, parameterMap);
		}
		else if (scope.equals("selected-pages")) {
			Map layoutIdMap = new LinkedHashMap();

			long[] rowIds = ParamUtil.getLongValues(req, "rowIds");

			for (int i = 0; i < rowIds.length; i++) {
				long selPlid = rowIds[i];
				boolean includeChildren = ParamUtil.getBoolean(
					req, "includeChildren_" + selPlid);

				layoutIdMap.put(
					new Long(selPlid), new Boolean(includeChildren));
			}

			publishLayouts(
				layoutIdMap, stagingGroup.getLiveGroupId(),
				stagingGroup.getGroupId(), privateLayout, parameterMap);
		}
	}

	protected void copyLayouts(
			long sourceGroupId, long targetGroupId, boolean privateLayout,
			Map parameterMap)
		throws Exception {

		byte[] data = LayoutServiceUtil.exportLayouts(
			sourceGroupId, privateLayout, parameterMap);

		ByteArrayInputStream bais = new ByteArrayInputStream(data);

		LayoutServiceUtil.importLayouts(
			targetGroupId, privateLayout, parameterMap, bais);
	}

	protected void copyPreferences(
			ActionRequest req, Layout layout, Layout copyLayout)
		throws Exception {

		long companyId = layout.getCompanyId();

		LayoutTypePortlet copyLayoutTypePortlet =
			(LayoutTypePortlet)copyLayout.getLayoutType();

		List copyPortletIds = copyLayoutTypePortlet.getPortletIds();

		for (int i = 0; i < copyPortletIds.size(); i++) {
			String copyPortletId = (String)copyPortletIds.get(i);

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

	protected List getMissingParents(Layout layout, long liveGroupId)
		throws PortalException, SystemException {

		List missingParents = new ArrayList();

		long parentLayoutId = layout.getParentLayoutId();

		while (parentLayoutId > 0) {
			try {
				LayoutLocalServiceUtil.getLayout(
					liveGroupId, layout.isPrivateLayout(), parentLayoutId);

				// If one parent is found all others are assumed to exist

				break;
			}
			catch (NoSuchLayoutException nsle) {
				Layout parent = LayoutLocalServiceUtil.getLayout(
					layout.getGroupId(), layout.isPrivateLayout(),
					parentLayoutId);

				missingParents.add(parent);

				parentLayoutId = parent.getParentLayoutId();
			}
		}

		return missingParents;
	}

	protected Map getPublishToLiveParameters(ActionRequest req) {
		Map parameterMap = new HashMap(req.getParameterMap());

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL, Boolean.FALSE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.THEME, Boolean.FALSE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			Boolean.FALSE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			PortletDataHandlerKeys.DATA_STRATEGY_MIRROR);
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			UserIdStrategy.CURRENT_USER_ID);

		return parameterMap;
	}

	protected Map getStagingParameters() {
		Map parameterMap = new HashMap();

		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS, Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.USER_PERMISSIONS, Boolean.FALSE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA, Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL, Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP, Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
			Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.THEME, Boolean.FALSE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			Boolean.FALSE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			PortletDataHandlerKeys.DATA_STRATEGY_MIRROR);
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			UserIdStrategy.CURRENT_USER_ID);

		return parameterMap;
	}

	protected Properties getTypeSettingsProperties(ActionRequest req) {
		Properties typeSettingsProperties = new Properties();

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

	protected void publishLayout(
			long plid, long liveGroupId, boolean includeChildren)
		throws Exception {

		Map parameterMap = getStagingParameters();

		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			Boolean.FALSE.toString());

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		List layouts = new ArrayList();

		layouts.add(layout);

		layouts.addAll(getMissingParents(layout, liveGroupId));

		if (includeChildren) {
			layouts.addAll(layout.getAllChildren());
		}

		Iterator itr = layouts.iterator();

		long[] layoutIds = new long[layouts.size()];

		for (int i = 0; itr.hasNext(); i++) {
			Layout curLayout = (Layout)itr.next();

			layoutIds[i] = curLayout.getLayoutId();
		}

		byte[] data = LayoutServiceUtil.exportLayouts(
			layout.getGroupId(), layout.isPrivateLayout(), layoutIds,
			parameterMap);

		ByteArrayInputStream bais = new ByteArrayInputStream(data);

		LayoutServiceUtil.importLayouts(
			liveGroupId, layout.isPrivateLayout(), parameterMap, bais);
	}

	protected void publishLayouts(
			Map layoutIdMap, long stagingGroupId, long liveGroupId,
			boolean privateLayout, Map parameterMap)
		throws Exception {

		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			Boolean.FALSE.toString());

		List layouts = new ArrayList();

		Iterator itr1 = layoutIdMap.entrySet().iterator();

		while (itr1.hasNext()) {
			Entry entry = (Entry)itr1.next();

			long plid = ((Long)entry.getKey()).longValue();
			boolean includeChildren =
				((Boolean)entry.getValue()).booleanValue();

			Layout layout = LayoutLocalServiceUtil.getLayout(plid);

			if (!layouts.contains(layout)) {
				layouts.add(layout);
			}

			Iterator itr2 = getMissingParents(layout, liveGroupId).iterator();

			while (itr2.hasNext()) {
				Layout parentLayout = (Layout)itr2.next();

				if (!layouts.contains(parentLayout)) {
					layouts.add(parentLayout);
				}
			}

			if (includeChildren) {
				itr2 = layout.getAllChildren().iterator();

				while (itr2.hasNext()) {
					Layout childLayout = (Layout)itr2.next();

					if (!layouts.contains(childLayout)) {
						layouts.add(childLayout);
					}
				}
			}
		}

		itr1 = layouts.iterator();

		long[] layoutIds = new long[layouts.size()];

		for (int i = 0; itr1.hasNext(); i++) {
			Layout curLayout = (Layout)itr1.next();

			layoutIds[i] = curLayout.getLayoutId();
		}

		byte[] data = LayoutServiceUtil.exportLayouts(
			stagingGroupId, privateLayout, layoutIds, parameterMap);

		ByteArrayInputStream bais = new ByteArrayInputStream(data);

		LayoutServiceUtil.importLayouts(
			liveGroupId, privateLayout, parameterMap, bais);
	}

	protected void publishToLive(ActionRequest req) throws Exception {
		String tabs2 = ParamUtil.getString(req, "tabs2");

		long stagingGroupId = ParamUtil.getLong(req, "stagingGroupId");

		Group stagingGroup = GroupLocalServiceUtil.getGroup(stagingGroupId);

		boolean privateLayout = true;

		if (tabs2.equals("public-pages")) {
			privateLayout = false;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Copying staging to live for group " +
					stagingGroup.getLiveGroupId());
		}

		String scope = ParamUtil.getString(req, "scope");

		Map parameterMap = getPublishToLiveParameters(req);

		if (scope.equals("all-pages")) {
			copyLayouts(
				stagingGroup.getGroupId(), stagingGroup.getLiveGroupId(),
				privateLayout, parameterMap);
		}
		else if (scope.equals("selected-pages")) {
			Map layoutIdMap = new LinkedHashMap();

			long[] rowIds = ParamUtil.getLongValues(req, "rowIds");

			for (int i = 0; i < rowIds.length; i++) {
				long selPlid = rowIds[i];
				boolean includeChildren = ParamUtil.getBoolean(
					req, "includeChildren_" + selPlid);

				layoutIdMap.put(
					new Long(selPlid), new Boolean(includeChildren));
			}

			publishLayouts(
				layoutIdMap, stagingGroup.getGroupId(),
				stagingGroup.getLiveGroupId(), privateLayout, parameterMap);
		}
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

		Locale[] locales = LanguageUtil.getAvailableLocales();

		Map localeNamesMap = new HashMap();
		Map localeTitlesMap = new HashMap();

		for (int i = 0; i < locales.length; i++) {
			String languageId = LocaleUtil.toLanguageId(locales[i]);

			localeNamesMap.put(
				locales[i],
				ParamUtil.getString(uploadReq, "name_" + languageId));
			localeTitlesMap.put(
				locales[i],
				ParamUtil.getString(uploadReq, "title_" + languageId));
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
				Layout layout = LayoutServiceUtil.addLayout(
					groupId, privateLayout, parentLayoutId, localeNamesMap,
					localeTitlesMap, description, type, hidden, friendlyURL);

				if (type.equals(LayoutImpl.TYPE_PORTLET)) {
					LayoutTypePortlet layoutTypePortlet =
						(LayoutTypePortlet)layout.getLayoutType();

					if (Validator.isNull(
							layoutTypePortlet.getLayoutTemplateId())) {

						layoutTypePortlet.setLayoutTemplateId(
							0, PropsValues.LAYOUT_DEFAULT_TEMPLATE_ID, false);

						LayoutServiceUtil.updateLayout(
							layout.getGroupId(), layout.isPrivateLayout(),
							layout.getLayoutId(), layout.getTypeSettings());
					}
				}
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

			if (type.equals(LayoutImpl.TYPE_PORTLET)) {
				if ((copyLayoutId > 0) &&
					(copyLayoutId != layout.getLayoutId())) {

					try {
						Layout copyLayout = LayoutLocalServiceUtil.getLayout(
							groupId, privateLayout, copyLayoutId);

						if (copyLayout.getType().equals(
								LayoutImpl.TYPE_PORTLET)) {

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
			}
			else {
				layout.setTypeSettingsProperties(formTypeSettingsProperties);

				LayoutServiceUtil.updateLayout(
					groupId, privateLayout, layoutId, layout.getTypeSettings());
			}

			HttpServletResponse httpRes = (HttpServletResponse)
				((ActionResponseImpl)res).getHttpServletResponse();

			String[] eventClasses = StringUtil.split(
				PropsUtil.getComponentProperties().getString(
					PropsUtil.LAYOUT_CONFIGURATION_ACTION_UPDATE,
					Filter.by(type)));

			EventsProcessor.process(eventClasses, uploadReq, httpRes);
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

		updateLookAndFeel(
			companyId, stagingGroupId, privateLayout, layoutId, themeId,
			colorSchemeId, css, wapTheme);
	}

	protected void updateLookAndFeel(
			long companyId, long groupId, boolean privateLayout, long layoutId,
			String themeId, String colorSchemeId, String css, boolean wapTheme)
		throws Exception {

		if (Validator.isNotNull(themeId) && Validator.isNull(colorSchemeId)) {
			ColorScheme colorScheme = ThemeLocalUtil.getColorScheme(
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

	protected void updateMergePages(ActionRequest req) throws Exception {
		long liveGroupId = ParamUtil.getLong(req, "liveGroupId");
		long stagingGroupId = ParamUtil.getLong(req, "stagingGroupId");

		boolean mergeGuestPublicPages = ParamUtil.getBoolean(
			req, "mergeGuestPublicPages");

		updateMergePages(liveGroupId, mergeGuestPublicPages);

		updateMergePages(stagingGroupId, mergeGuestPublicPages);
	}

	protected void updateMergePages(long groupId, boolean mergeGuestPublicPages)
		throws Exception {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		Properties props = group.getTypeSettingsProperties();

		props.setProperty(
			"mergeGuestPublicPages", String.valueOf(mergeGuestPublicPages));

		GroupServiceUtil.updateGroup(groupId, group.getTypeSettings());
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

	protected void updateStaging(ActionRequest req) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		long liveGroupId = ParamUtil.getLong(req, "liveGroupId");

		if (!GroupPermissionUtil.contains(
				permissionChecker, liveGroupId, ActionKeys.MANAGE_STAGING)) {

			throw new PrincipalException();
		}

		long stagingGroupId = ParamUtil.getLong(req, "stagingGroupId");

		boolean stagingEnabled = ParamUtil.getBoolean(req, "stagingEnabled");
		boolean workflowEnabled = ParamUtil.getBoolean(req, "workflowEnabled");

		if ((stagingGroupId > 0) && !stagingEnabled) {
			GroupServiceUtil.deleteGroup(stagingGroupId);

			GroupServiceUtil.updateWorkflow(liveGroupId, false, 0, null);
		}
		else if ((stagingGroupId == 0) && stagingEnabled) {
			Group liveGroup = GroupServiceUtil.getGroup(liveGroupId);

			Group stagingGroup = GroupServiceUtil.addGroup(
				liveGroup.getGroupId(), liveGroup.getName() + " (Staging)",
				liveGroup.getDescription(), GroupImpl.TYPE_COMMUNITY_PRIVATE,
				null, liveGroup.isActive());

			if (liveGroup.hasPrivateLayouts()) {
				Map parameterMap = getStagingParameters();

				copyLayouts(
					liveGroup.getGroupId(), stagingGroup.getGroupId(), true,
					parameterMap);
			}

			if (liveGroup.hasPublicLayouts()) {
				Map parameterMap = getStagingParameters();

				copyLayouts(
					liveGroup.getGroupId(), stagingGroup.getGroupId(), false,
					parameterMap);
			}
		}
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

	private static Log _log = LogFactory.getLog(EditPagesAction.class);

}