/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.sites.util;

import com.liferay.portal.RequiredLayoutException;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.impl.VirtualLayout;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.LayoutSetServiceUtil;
import com.liferay.portal.service.LockLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.impl.LayoutLocalServiceVirtualLayoutsAdvice;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.service.permission.PortalPermissionUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.service.persistence.LayoutSetUtil;
import com.liferay.portal.service.persistence.LayoutUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.LayoutSettings;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletPreferencesImpl;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Raymond Augé
 * @author Ryan Park
 * @author Zsolt Berentey
 */
public class SitesUtil {

	public static final String LAST_MERGE_TIME = "last-merge-time";

	public static final String LAST_RESET_TIME = "last-reset-time";

	public static final String LAYOUT_UPDATEABLE = "layoutUpdateable";

	public static final String MERGE_FAIL_COUNT = "merge-fail-count";

	public static final String MERGE_FAIL_FRIENDLY_URL_LAYOUTS =
		"merge-fail-friendly-url-layouts";

	public static void addMergeFailFriendlyURLLayout(Layout layout)
		throws PortalException, SystemException {

		LayoutSet layoutSet = layout.getLayoutSet();

		layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			layoutSet.getGroupId(), layoutSet.isPrivateLayout());

		UnicodeProperties settingsProperties =
			layoutSet.getSettingsProperties();

		String mergeFailFriendlyURLLayouts =
			settingsProperties.getProperty(
				MERGE_FAIL_FRIENDLY_URL_LAYOUTS, StringPool.BLANK);

		mergeFailFriendlyURLLayouts = StringUtil.add(
			mergeFailFriendlyURLLayouts, layout.getUuid());

		settingsProperties.setProperty(
			MERGE_FAIL_FRIENDLY_URL_LAYOUTS, mergeFailFriendlyURLLayouts);

		LayoutSetLocalServiceUtil.updateLayoutSet(layoutSet, false);
	}

	public static void addPortletBreadcrumbEntries(
			Group group, String pagesName, PortletURL redirectURL,
			HttpServletRequest request, RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			com.liferay.portal.kernel.util.WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String portletName = portletDisplay.getPortletName();

		if ((renderResponse == null) ||
			portletName.equals(PortletKeys.GROUP_PAGES) ||
			portletName.equals(PortletKeys.MY_PAGES)) {

			return;
		}

		Locale locale = themeDisplay.getLocale();

		if (group.isLayoutPrototype()) {
			PortalUtil.addPortletBreadcrumbEntry(
				request, LanguageUtil.get(locale, "page-template"), null);

			PortalUtil.addPortletBreadcrumbEntry(
				request, group.getDescriptiveName(), redirectURL.toString());
		}
		else {
			PortalUtil.addPortletBreadcrumbEntry(
				request, group.getDescriptiveName(), null);
		}

		if (!group.isLayoutPrototype()) {
			PortalUtil.addPortletBreadcrumbEntry(
				request, LanguageUtil.get(locale, pagesName),
				redirectURL.toString());
		}
	}

	public static void applyLayoutPrototype(
			LayoutPrototype layoutPrototype, Layout targetLayout,
			boolean linkEnabled)
		throws Exception {

		Layout layoutPrototypeLayout = layoutPrototype.getLayout();

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		serviceContext.setAttribute("layoutPrototypeLinkEnabled", linkEnabled);
		serviceContext.setAttribute(
			"layoutPrototypeUuid", layoutPrototype.getUuid());

		targetLayout = LayoutLocalServiceUtil.updateLayout(
			targetLayout.getGroupId(), targetLayout.isPrivateLayout(),
			targetLayout.getLayoutId(), targetLayout.getParentLayoutId(),
			targetLayout.getNameMap(), targetLayout.getTitleMap(),
			targetLayout.getDescriptionMap(), targetLayout.getKeywordsMap(),
			targetLayout.getRobotsMap(), layoutPrototypeLayout.getType(),
			targetLayout.getHidden(), targetLayout.getFriendlyURL(),
			targetLayout.getIconImage(), null, serviceContext);

		targetLayout = LayoutLocalServiceUtil.updateLayout(
			targetLayout.getGroupId(), targetLayout.isPrivateLayout(),
			targetLayout.getLayoutId(),
			layoutPrototypeLayout.getTypeSettings());

		copyPortletPermissions(targetLayout, layoutPrototypeLayout);

		copyPortletSetups(layoutPrototypeLayout, targetLayout);

		copyLookAndFeel(targetLayout, layoutPrototypeLayout);

		targetLayout = LayoutLocalServiceUtil.getLayout(targetLayout.getPlid());

		UnicodeProperties typeSettingsProperties =
			targetLayout.getTypeSettingsProperties();

		typeSettingsProperties.setProperty(
			LAST_MERGE_TIME,
			String.valueOf(targetLayout.getModifiedDate().getTime()));

		LayoutLocalServiceUtil.updateLayout(targetLayout, false);

		UnicodeProperties prototypeTypeSettingsProperties =
			layoutPrototypeLayout.getTypeSettingsProperties();

		prototypeTypeSettingsProperties.setProperty(MERGE_FAIL_COUNT, "0");

		LayoutLocalServiceUtil.updateLayout(layoutPrototypeLayout, false);
	}

	public static void copyLayout(
			long userId, Layout sourceLayout, Layout targetLayout,
			ServiceContext serviceContext)
		throws Exception {

		Map<String, String[]> parameterMap = getLayoutSetPrototypeParameters(
			serviceContext);

		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.FALSE.toString()});

		File file = LayoutLocalServiceUtil.exportLayoutsAsFile(
			sourceLayout.getGroupId(), sourceLayout.isPrivateLayout(),
			new long[] {sourceLayout.getLayoutId()}, parameterMap, null, null);

		try {
			LayoutLocalServiceUtil.importLayouts(
				userId, targetLayout.getGroupId(),
				targetLayout.isPrivateLayout(), parameterMap, file);
		}
		finally {
			file.delete();
		}
	}

	public static void copyLookAndFeel(Layout targetLayout, Layout sourceLayout)
		throws Exception {

		LayoutLocalServiceUtil.updateLookAndFeel(
			targetLayout.getGroupId(), targetLayout.isPrivateLayout(),
			targetLayout.getLayoutId(), sourceLayout.getThemeId(),
			sourceLayout.getColorSchemeId(), sourceLayout.getCss(), false);

		LayoutLocalServiceUtil.updateLookAndFeel(
			targetLayout.getGroupId(), targetLayout.isPrivateLayout(),
			targetLayout.getLayoutId(), sourceLayout.getWapThemeId(),
			sourceLayout.getWapColorSchemeId(), sourceLayout.getCss(), true);
	}

	public static void copyPortletPermissions(
			Layout targetLayout, Layout sourceLayout)
		throws Exception {

		long companyId = targetLayout.getCompanyId();

		List<Role> roles = RoleLocalServiceUtil.getRoles(companyId);

		LayoutTypePortlet sourceLayoutTypePortlet =
			(LayoutTypePortlet)sourceLayout.getLayoutType();

		List<String> sourcePortletIds = sourceLayoutTypePortlet.getPortletIds();

		for (String sourcePortletId : sourcePortletIds) {
			String resourceName = PortletConstants.getRootPortletId(
				sourcePortletId);

			String sourceResourcePrimKey = PortletPermissionUtil.getPrimaryKey(
				sourceLayout.getPlid(), sourcePortletId);

			String targetResourcePrimKey = PortletPermissionUtil.getPrimaryKey(
				targetLayout.getPlid(), sourcePortletId);

			List<String> actionIds =
				ResourceActionsUtil.getPortletResourceActions(resourceName);

			for (Role role : roles) {
				String roleName = role.getName();

				if (roleName.equals(RoleConstants.ADMINISTRATOR) ||
					(targetLayout.isPrivateLayout() &&
					 roleName.equals(RoleConstants.GUEST))) {

					continue;
				}

				List<String> actions =
					ResourcePermissionLocalServiceUtil.
						getAvailableResourcePermissionActionIds(
							companyId, resourceName,
							ResourceConstants.SCOPE_INDIVIDUAL,
							sourceResourcePrimKey, role.getRoleId(), actionIds);

				ResourcePermissionLocalServiceUtil.setResourcePermissions(
					companyId, resourceName, ResourceConstants.SCOPE_INDIVIDUAL,
					targetResourcePrimKey, role.getRoleId(),
					actions.toArray(new String[actions.size()]));
			}
		}
	}

	public static void copyPortletSetups(
			Layout sourceLayout, Layout targetLayout)
		throws Exception {

		LayoutTypePortlet sourceLayoutTypePortlet =
			(LayoutTypePortlet)sourceLayout.getLayoutType();

		List<String> sourcePortletIds = sourceLayoutTypePortlet.getPortletIds();

		for (String sourcePortletId : sourcePortletIds) {
			PortletPreferences sourcePreferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					sourceLayout, sourcePortletId, null);

			PortletPreferencesImpl sourcePreferencesImpl =
				(PortletPreferencesImpl)sourcePreferences;

			PortletPreferences targetPreferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					targetLayout, sourcePortletId, null);

			PortletPreferencesImpl targetPreferencesImpl =
				(PortletPreferencesImpl)targetPreferences;

			PortletPreferencesLocalServiceUtil.updatePreferences(
				targetPreferencesImpl.getOwnerId(),
				targetPreferencesImpl.getOwnerType(),
				targetPreferencesImpl.getPlid(), sourcePortletId,
				sourcePreferences);

			if ((sourcePreferencesImpl.getOwnerId() !=
					PortletKeys.PREFS_OWNER_ID_DEFAULT) &&
				(sourcePreferencesImpl.getOwnerType() !=
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT)) {

				sourcePreferences =
					PortletPreferencesFactoryUtil.getLayoutPortletSetup(
						sourceLayout, sourcePortletId);

				sourcePreferencesImpl =
					(PortletPreferencesImpl)sourcePreferences;

				targetPreferences =
					PortletPreferencesFactoryUtil.getLayoutPortletSetup(
						targetLayout, sourcePortletId);

				targetPreferencesImpl =
					(PortletPreferencesImpl)targetPreferences;

				PortletPreferencesLocalServiceUtil.updatePreferences(
					targetPreferencesImpl.getOwnerId(),
					targetPreferencesImpl.getOwnerType(),
					targetPreferencesImpl.getPlid(), sourcePortletId,
					sourcePreferences);
			}

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			updateLayoutScopes(
				serviceContext.getUserId(), sourceLayout, targetLayout,
				sourcePreferences, targetPreferences, sourcePortletId,
				serviceContext.getLanguageId());
		}
	}

	public static void copyTypeSettings(Group sourceGroup, Group targetGroup)
		throws Exception {

		GroupServiceUtil.updateGroup(
			targetGroup.getGroupId(), sourceGroup.getTypeSettings());
	}

	public static Object[] deleteLayout(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			actionResponse);

		return deleteLayout(request, response);
	}

	public static Object[] deleteLayout(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		long plid = ParamUtil.getLong(request, "plid");

		long groupId = ParamUtil.getLong(request, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");
		long layoutId = ParamUtil.getLong(request, "layoutId");

		Layout layout = null;

		if (plid <= 0) {
			layout = LayoutLocalServiceUtil.getLayout(
				groupId, privateLayout, layoutId);
		}
		else {
			layout = LayoutLocalServiceUtil.getLayout(plid);

			groupId = layout.getGroupId();
			privateLayout = layout.isPrivateLayout();
			layoutId = layout.getLayoutId();
		}

		Group group = layout.getGroup();
		String oldFriendlyURL = layout.getFriendlyURL();

		if (group.isStagingGroup() &&
			!GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.MANAGE_STAGING) &&
			!GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.PUBLISH_STAGING)) {

			throw new PrincipalException();
		}

		if (LayoutPermissionUtil.contains(
				permissionChecker, layout, ActionKeys.DELETE)) {

			LayoutSettings layoutSettings = LayoutSettings.getInstance(layout);

			EventsProcessorUtil.process(
				PropsKeys.LAYOUT_CONFIGURATION_ACTION_DELETE,
				layoutSettings.getConfigurationActionDelete(), request,
				response);
		}

		LayoutSet layoutSet = layout.getLayoutSet();

		if (group.isGuest() && (layoutSet.getPageCount() == 1)) {
			throw new RequiredLayoutException(
				RequiredLayoutException.AT_LEAST_ONE);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			request);

		LayoutServiceUtil.deleteLayout(
			groupId, privateLayout, layoutId, serviceContext);

		long newPlid = layout.getParentPlid();

		if (newPlid <= 0) {
			Layout firstLayout = LayoutLocalServiceUtil.fetchFirstLayout(
				layoutSet.getGroupId(), layoutSet.getPrivateLayout(),
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			if (firstLayout != null) {
				newPlid = firstLayout.getPlid();
			}
		}

		return new Object[] {group, oldFriendlyURL, newPlid};
	}

	public static void deleteLayout(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			renderResponse);

		deleteLayout(request, response);
	}

	public static File exportLayoutSetPrototype(
			LayoutSetPrototype layoutSetPrototype,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		LayoutSet layoutSet = layoutSetPrototype.getLayoutSet();

		Map<String, String[]> parameterMap = getLayoutSetPrototypeParameters(
			serviceContext);

		return LayoutLocalServiceUtil.exportLayoutsAsFile(
			layoutSet.getGroupId(), layoutSet.isPrivateLayout(), null,
			parameterMap, null, null);
	}

	public static Layout getLayoutSetPrototypeLayout(Layout layout) {
		try {
			LayoutSet layoutSet = layout.getLayoutSet();

			if (!layoutSet.isLayoutSetPrototypeLinkActive()) {
				return null;
			}

			LayoutSetPrototype layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypeByUuid(
					layoutSet.getLayoutSetPrototypeUuid());

			Group group = layoutSetPrototype.getGroup();

			return LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				layout.getSourcePrototypeLayoutUuid(), group.getGroupId(),
				true);
		}
		catch (Exception e) {
			_log.error(
				"Unable to fetch the the layout set prototype's layout", e);
		}

		return null;
	}

	public static Map<String, String[]> getLayoutSetPrototypeParameters(
		ServiceContext serviceContext) {

		Map<String, String[]> parameterMap =
			new LinkedHashMap<String, String[]>();

		parameterMap.put(
			PortletDataHandlerKeys.CATEGORIES,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.LAYOUT_SET_SETTINGS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			new String[] {
				PortletDataHandlerKeys.
					LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE
			});
		parameterMap.put(
			PortletDataHandlerKeys.LOGO,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PERFORM_DIRECT_BINARY_IMPORT,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.THEME,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.THEME_REFERENCE,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {UserIdStrategy.CURRENT_USER_ID});
		parameterMap.put(
			PortletDataHandlerKeys.USER_PERMISSIONS,
			new String[] {Boolean.FALSE.toString()});

		return parameterMap;
	}

	public static List<Layout> getMergeFailFriendlyURLLayouts(
			LayoutSet layoutSet)
		throws PortalException, SystemException {

		if (layoutSet == null) {
			return Collections.emptyList();
		}

		UnicodeProperties settingsProperties =
			layoutSet.getSettingsProperties();

		String uuids = settingsProperties.getProperty(
			MERGE_FAIL_FRIENDLY_URL_LAYOUTS);

		if (Validator.isNotNull(uuids)) {
			List<Layout> layouts = new ArrayList<Layout>();

			for (String uuid : StringUtil.split(uuids)) {
				Layout layout =
					LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
						uuid, layoutSet.getGroupId(),
						layoutSet.isPrivateLayout());

				layouts.add(layout);
			}

			return layouts;
		}

		return Collections.emptyList();
	}

	public static void importLayoutSetPrototype(
			LayoutSetPrototype layoutSetPrototype, InputStream inputStream,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		LayoutSet layoutSet = layoutSetPrototype.getLayoutSet();

		Map<String, String[]> parameterMap = getLayoutSetPrototypeParameters(
			serviceContext);

		setLayoutSetPrototypeLinkEnabledParameter(
			parameterMap, layoutSet, serviceContext);

		LayoutServiceUtil.importLayouts(
			layoutSet.getGroupId(), layoutSet.isPrivateLayout(), parameterMap,
			inputStream);
	}

	public static boolean isLayoutDeleteable(Layout layout) {
		try {
			if (layout instanceof VirtualLayout) {
				return false;
			}

			if (Validator.isNull(layout.getSourcePrototypeLayoutUuid())) {
				return true;
			}

			LayoutSet layoutSet = layout.getLayoutSet();

			if (!layoutSet.isLayoutSetPrototypeLinkActive()) {
				return true;
			}

			if (LayoutLocalServiceUtil.hasLayoutSetPrototypeLayout(
					layoutSet.getLayoutSetPrototypeUuid(),
					layout.getSourcePrototypeLayoutUuid())) {

				return false;
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return true;
	}

	public static boolean isLayoutModifiedSinceLastMerge(Layout layout) {
		if ((layout == null) ||
			Validator.isNull(layout.getSourcePrototypeLayoutUuid()) ||
			layout.isLayoutPrototypeLinkActive() ||
			!isLayoutUpdateable(layout)) {

			return false;
		}

		long lastMergeTime = GetterUtil.getLong(
			layout.getTypeSettingsProperty(LAST_MERGE_TIME));

		Date existingLayoutModifiedDate = layout.getModifiedDate();

		if ((existingLayoutModifiedDate != null) &&
			(existingLayoutModifiedDate.getTime() > lastMergeTime)) {

			return true;
		}

		return false;
	}

	public static boolean isLayoutSetMergeable(Group group, LayoutSet layoutSet)
		throws PortalException, SystemException {

		if (!layoutSet.isLayoutSetPrototypeLinkActive() ||
			group.isLayoutPrototype() || group.isLayoutSetPrototype()) {

			return false;
		}

		UnicodeProperties settingsProperties =
			layoutSet.getSettingsProperties();

		long lastMergeTime = GetterUtil.getLong(
			settingsProperties.getProperty(LAST_MERGE_TIME));

		LayoutSetPrototype layoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypeByUuid(
				layoutSet.getLayoutSetPrototypeUuid());

		Date modifiedDate = layoutSetPrototype.getModifiedDate();

		if (lastMergeTime >= modifiedDate.getTime()) {
			return false;
		}

		LayoutSet layoutSetPrototypeLayoutSet =
			layoutSetPrototype.getLayoutSet();

		UnicodeProperties layoutSetPrototypeSettingsProperties =
			layoutSetPrototypeLayoutSet.getSettingsProperties();

		int mergeFailCount = GetterUtil.getInteger(
			layoutSetPrototypeSettingsProperties.getProperty(MERGE_FAIL_COUNT));

		if (mergeFailCount >
				PropsValues.LAYOUT_SET_PROTOTYPE_MERGE_FAIL_THRESHOLD) {

			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(6);

				sb.append("Merge not performed because the fail threshold ");
				sb.append("was reached for layoutSetPrototypeId ");
				sb.append(layoutSetPrototype.getLayoutSetPrototypeId());
				sb.append(" and layoutId ");
				sb.append(layoutSetPrototypeLayoutSet.getLayoutSetId());
				sb.append(". Update the count in the database to try again.");

				_log.warn(sb.toString());
			}

			return false;
		}

		return true;
	}

	public static boolean isLayoutSetPrototypeUpdateable(LayoutSet layoutSet) {
		if (!layoutSet.isLayoutSetPrototypeLinkActive()) {
			return true;
		}

		try {
			LayoutSetPrototype layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypeByUuid(
					layoutSet.getLayoutSetPrototypeUuid());

			String layoutsUpdateable = layoutSetPrototype.getSettingsProperty(
				"layoutsUpdateable");

			if (Validator.isNotNull(layoutsUpdateable)) {
				return GetterUtil.getBoolean(layoutsUpdateable, true);
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return true;
	}

	public static boolean isLayoutSortable(Layout layout) {
		return isLayoutDeleteable(layout);
	}

	public static boolean isLayoutUpdateable(Layout layout) {
		try {
			if (layout instanceof VirtualLayout) {
				return false;
			}

			if (Validator.isNull(layout.getLayoutPrototypeUuid()) &&
				Validator.isNull(layout.getSourcePrototypeLayoutUuid())) {

				return true;
			}

			LayoutSet layoutSet = layout.getLayoutSet();

			if (layoutSet.isLayoutSetPrototypeLinkActive()) {
				boolean layoutSetPrototypeUpdateable =
					isLayoutSetPrototypeUpdateable(layoutSet);

				if (!layoutSetPrototypeUpdateable) {
					return false;
				}

				Layout layoutSetPrototypeLayout = getLayoutSetPrototypeLayout(
					layout);

				String layoutUpdateable =
					layoutSetPrototypeLayout.getTypeSettingsProperty(
						LAYOUT_UPDATEABLE);

				if (Validator.isNull(layoutUpdateable)) {
					return true;
				}

				return GetterUtil.getBoolean(layoutUpdateable);
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return true;
	}

	public static boolean isUserGroupLayoutSetViewable(
			PermissionChecker permissionChecker, Group userGroupGroup)
		throws PortalException, SystemException {

		if (!userGroupGroup.isUserGroup()) {
			return false;
		}

		if (GroupPermissionUtil.contains(
				permissionChecker, userGroupGroup.getGroupId(),
				ActionKeys.VIEW)) {

			return true;
		}

		UserGroup userGroup = UserGroupLocalServiceUtil.getUserGroup(
			userGroupGroup.getClassPK());

		if (UserLocalServiceUtil.hasUserGroupUser(
				userGroup.getUserGroupId(), permissionChecker.getUserId())) {

			return true;
		}
		else {
			return false;
		}
	}

	public static void mergeLayoutPrototypeLayout(Group group, Layout layout)
		throws Exception {

		String sourcePrototypeLayoutUuid =
			layout.getSourcePrototypeLayoutUuid();

		if (Validator.isNull(sourcePrototypeLayoutUuid)) {
			doMergeLayoutPrototypeLayout(group, layout);

			return;
		}

		LayoutSet layoutSet = layout.getLayoutSet();

		long layoutSetPrototypeId = layoutSet.getLayoutSetPrototypeId();

		if (layoutSetPrototypeId > 0) {
			Group layoutSetPrototypeGroup =
				GroupLocalServiceUtil.getLayoutSetPrototypeGroup(
					layout.getCompanyId(), layoutSetPrototypeId);

			Layout sourcePrototypeLayout =
				LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
					sourcePrototypeLayoutUuid,
					layoutSetPrototypeGroup.getGroupId(), true);

			doMergeLayoutPrototypeLayout(
				layoutSetPrototypeGroup, sourcePrototypeLayout);
		}

		doMergeLayoutPrototypeLayout(group, layout);
	}

	/**
	 * @deprecated {@link #mergeLayoutPrototypeLayout(Group, Layout)}
	 */
	public static void mergeLayoutProtypeLayout(Group group, Layout layout)
		throws Exception {

		mergeLayoutPrototypeLayout(group, layout);
	}

	public static void mergeLayoutSetPrototypeLayouts(
			Group group, LayoutSet layoutSet)
		throws Exception {

		if (!layoutSet.isLayoutSetPrototypeLinkActive() ||
			group.isLayoutPrototype() || group.isLayoutSetPrototype()) {

			return;
		}

		UnicodeProperties settingsProperties =
			layoutSet.getSettingsProperties();

		long lastMergeTime = GetterUtil.getLong(
			settingsProperties.getProperty(LAST_MERGE_TIME));

		LayoutSetPrototype layoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypeByUuid(
				layoutSet.getLayoutSetPrototypeUuid());

		Date modifiedDate = layoutSetPrototype.getModifiedDate();

		if (lastMergeTime >= modifiedDate.getTime()) {
			return;
		}

		LayoutSet layoutSetPrototypeLayoutSet =
			layoutSetPrototype.getLayoutSet();

		UnicodeProperties layoutSetPrototypeSettingsProperties =
			layoutSetPrototypeLayoutSet.getSettingsProperties();

		int mergeFailCount = GetterUtil.getInteger(
			layoutSetPrototypeSettingsProperties.getProperty(MERGE_FAIL_COUNT));

		if (mergeFailCount >
				PropsValues.LAYOUT_SET_PROTOTYPE_MERGE_FAIL_THRESHOLD) {

			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(6);

				sb.append("Merge not performed because the fail threshold ");
				sb.append("was reached for layoutSetPrototypeId ");
				sb.append(layoutSetPrototype.getLayoutSetPrototypeId());
				sb.append(" and layoutId ");
				sb.append(layoutSetPrototypeLayoutSet.getLayoutSetId());
				sb.append(". Update the count in the database to try again.");

				_log.warn(sb.toString());
			}

			return;
		}

		String owner = PortalUUIDUtil.generate();

		try {
			Lock lock = LockLocalServiceUtil.lock(
				LayoutLocalServiceVirtualLayoutsAdvice.class.getName(),
				String.valueOf(layoutSet.getLayoutSetId()), owner, false);

			// Double deep check

			if (!owner.equals(lock.getOwner())) {
				Date createDate = lock.getCreateDate();

				if ((System.currentTimeMillis() - createDate.getTime()) >=
						PropsValues.LAYOUT_SET_PROTOTYPE_MERGE_LOCK_MAX_TIME) {

					// Acquire lock if the lock is older than the lock max time

					lock = LockLocalServiceUtil.lock(
						LayoutLocalServiceVirtualLayoutsAdvice.class.getName(),
						String.valueOf(layoutSet.getLayoutSetId()),
						lock.getOwner(), owner, false);

					// Check if acquiring the lock succeeded or if another
					// process has the lock

					if (!owner.equals(lock.getOwner())) {
						return;
					}
				}
				else {
					return;
				}
			}
		}
		catch (Exception e) {
			return;
		}

		try {
			boolean importData = true;

			long lastResetTime = GetterUtil.getLong(
				settingsProperties.getProperty(LAST_RESET_TIME));

			if ((lastMergeTime > 0) || (lastResetTime > 0)) {
				importData = false;
			}

			Map<String, String[]> parameterMap =
				getLayoutSetPrototypesParameters(importData);

			removeMergeFailFriendlyURLLayouts(layoutSet);

			importLayoutSetPrototype(
				layoutSetPrototype, layoutSet.getGroupId(),
				layoutSet.isPrivateLayout(), parameterMap, importData);
		}
		catch (Exception e) {
			_log.error(e, e);

			layoutSetPrototypeSettingsProperties.setProperty(
				MERGE_FAIL_COUNT, String.valueOf(++mergeFailCount));

			// Invoke updateImpl so that we do not trigger the listeners

			LayoutSetUtil.updateImpl(layoutSetPrototypeLayoutSet, false);
		}
		finally {
			LockLocalServiceUtil.unlock(
				LayoutLocalServiceVirtualLayoutsAdvice.class.getName(),
				String.valueOf(layoutSet.getLayoutSetId()), owner, false);
		}
	}

	/**
	 * @deprecated {@link #mergeLayoutSetPrototypeLayouts(Group, LayoutSet)}
	 */
	public static void mergeLayoutSetProtypeLayouts(
			Group group, LayoutSet layoutSet)
		throws Exception {

		mergeLayoutSetPrototypeLayouts(group, layoutSet);
	}

	public static void removeMergeFailFriendlyURLLayouts(LayoutSet layoutSet)
		throws PortalException, SystemException {

		UnicodeProperties settingsProperties =
			layoutSet.getSettingsProperties();

		settingsProperties.remove(MERGE_FAIL_FRIENDLY_URL_LAYOUTS);

		LayoutSetLocalServiceUtil.updateLayoutSet(layoutSet, false);
	}

	public static void resetPrototype(Layout layout)
		throws PortalException, SystemException {

		layout.setModifiedDate(null);

		LayoutLocalServiceUtil.updateLayout(layout, false);

		LayoutSet layoutSet = layout.getLayoutSet();
		UnicodeProperties settingsProperties =
			layoutSet.getSettingsProperties();

		settingsProperties.remove(LAST_MERGE_TIME);

		settingsProperties.setProperty(
			LAST_RESET_TIME, String.valueOf(System.currentTimeMillis()));

		LayoutSetLocalServiceUtil.updateLayoutSet(layoutSet, false);
	}

	public static void updateLayoutScopes(
			long userId, Layout sourceLayout, Layout targetLayout,
			PortletPreferences sourcePreferences,
			PortletPreferences targetPreferences, String sourcePortletId,
			String languageId)
		throws Exception {

		String scopeType = GetterUtil.getString(
			sourcePreferences.getValue("lfrScopeType", null));

		if (Validator.isNull(scopeType) || !scopeType.equals("layout")) {
			return;
		}

		Layout targetScopeLayout =
			LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
				targetLayout.getUuid(), targetLayout.getGroupId(),
				targetLayout.isPrivateLayout());

		if (!targetScopeLayout.hasScopeGroup()) {
			GroupLocalServiceUtil.addGroup(
				userId, Layout.class.getName(), targetLayout.getPlid(),
				targetLayout.getName(languageId), null, 0, null, false, true,
				null);
		}

		String portletTitle = PortalUtil.getPortletTitle(
			sourcePortletId, languageId);

		String newPortletTitle = PortalUtil.getNewPortletTitle(
			portletTitle, String.valueOf(sourceLayout.getLayoutId()),
			targetLayout.getName(languageId));

		targetPreferences.setValue(
			"groupId", String.valueOf(targetLayout.getGroupId()));
		targetPreferences.setValue("lfrScopeType", "layout");
		targetPreferences.setValue(
			"lfrScopeLayoutUuid", targetLayout.getUuid());
		targetPreferences.setValue(
			"portletSetupTitle_" + languageId, newPortletTitle);
		targetPreferences.setValue(
			"portletSetupUseCustomTitle", Boolean.TRUE.toString());

		targetPreferences.store();
	}

	public static void updateLayoutSetPrototypesLinks(
			Group group, long publicLayoutSetPrototypeId,
			long privateLayoutSetPrototypeId,
			boolean publicLayoutSetPrototypeLinkEnabled,
			boolean privateLayoutSetPrototypeLinkEnabled)
		throws Exception {

		updateLayoutSetPrototypeLink(
			group.getGroupId(), true, privateLayoutSetPrototypeId,
			privateLayoutSetPrototypeLinkEnabled);
		updateLayoutSetPrototypeLink(
			group.getGroupId(), false, publicLayoutSetPrototypeId,
			publicLayoutSetPrototypeLinkEnabled);
	}

	protected static void doMergeLayoutPrototypeLayout(
			Group group, Layout layout)
		throws Exception {

		if (!layout.isLayoutPrototypeLinkActive() ||
			group.isLayoutPrototype() || group.hasStagingGroup()) {

			return;
		}

		long lastMergeTime = GetterUtil.getLong(
			layout.getTypeSettingsProperty(LAST_MERGE_TIME));

		LayoutPrototype layoutPrototype =
			LayoutPrototypeLocalServiceUtil.getLayoutPrototypeByUuid(
				layout.getLayoutPrototypeUuid());

		Layout layoutPrototypeLayout = layoutPrototype.getLayout();

		Date modifiedDate = layoutPrototypeLayout.getModifiedDate();

		if (lastMergeTime >= modifiedDate.getTime()) {
			return;
		}

		UnicodeProperties prototypeTypeSettingsProperties =
			layoutPrototypeLayout.getTypeSettingsProperties();

		int mergeFailCount = GetterUtil.getInteger(
			prototypeTypeSettingsProperties.getProperty(MERGE_FAIL_COUNT));

		if (mergeFailCount >
				PropsValues.LAYOUT_PROTOTYPE_MERGE_FAIL_THRESHOLD) {

			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(6);

				sb.append("Merge not performed because the fail threshold ");
				sb.append("was reached for layoutPrototypeId ");
				sb.append(layoutPrototype.getLayoutPrototypeId());
				sb.append(" and layoutId ");
				sb.append(layoutPrototypeLayout.getLayoutId());
				sb.append(". Update the count in the database to try again.");

				_log.warn(sb.toString());
			}

			return;
		}

		String owner = PortalUUIDUtil.generate();

		try {
			Lock lock = LockLocalServiceUtil.lock(
				LayoutLocalServiceVirtualLayoutsAdvice.class.getName(),
				String.valueOf(layout.getPlid()), owner, false);

			if (!owner.equals(lock.getOwner())) {
				Date createDate = lock.getCreateDate();

				if ((System.currentTimeMillis() - createDate.getTime()) >=
						PropsValues.LAYOUT_PROTOTYPE_MERGE_LOCK_MAX_TIME) {

					// Acquire lock if the lock is older than the lock max time

					lock = LockLocalServiceUtil.lock(
						LayoutLocalServiceVirtualLayoutsAdvice.class.getName(),
						String.valueOf(layout.getPlid()), lock.getOwner(),
						owner, false);

					// Check if acquiring the lock succeeded or if another
					// process has the lock

					if (!owner.equals(lock.getOwner())) {
						return;
					}
				}
				else {
					return;
				}
			}
		}
		catch (Exception e) {
			return;
		}

		try {
			SitesUtil.applyLayoutPrototype(layoutPrototype, layout, true);
		}
		catch (Exception e) {
			_log.error(e, e);

			prototypeTypeSettingsProperties.setProperty(
				MERGE_FAIL_COUNT, String.valueOf(++mergeFailCount));

			// Invoke updateImpl so that we do not trigger the listeners

			LayoutUtil.updateImpl(layoutPrototypeLayout, false);
		}
		finally {
			LockLocalServiceUtil.unlock(
				LayoutLocalServiceVirtualLayoutsAdvice.class.getName(),
				String.valueOf(layout.getPlid()), owner, false);
		}
	}

	protected static Map<String, String[]> getLayoutSetPrototypesParameters(
		boolean importData) {

		Map<String, String[]> parameterMap =
			new LinkedHashMap<String, String[]>();

		parameterMap.put(
			PortletDataHandlerKeys.CATEGORIES,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.IGNORE_LAST_PUBLISH_DATE,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.LAYOUT_SET_SETTINGS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			new String[] {
				PortletDataHandlerKeys.
					LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE
			});
		parameterMap.put(
			PortletDataHandlerKeys.LOGO,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.THEME,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.THEME_REFERENCE,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {UserIdStrategy.CURRENT_USER_ID});

		if (importData) {
			parameterMap.put(
				PortletDataHandlerKeys.DATA_STRATEGY,
				new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR});
			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA,
				new String[] {Boolean.TRUE.toString()});
			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA_ALL,
				new String[] {Boolean.TRUE.toString()});
		}
		else {
			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA,
				new String[] {Boolean.FALSE.toString()});
			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA_ALL,
				new String[] {Boolean.FALSE.toString()});
		}

		return parameterMap;
	}

	protected static void importLayoutSetPrototype(
			LayoutSetPrototype layoutSetPrototype, long groupId,
			boolean privateLayout, Map<String, String[]> parameterMap,
			boolean importData)
		throws PortalException, SystemException {

		File file = null;

		StringBundler sb = new StringBundler(importData ? 4 : 3);

		sb.append(_TEMP_DIR);
		sb.append(layoutSetPrototype.getUuid());

		if (importData) {
			sb.append("-data");
		}

		sb.append(".lar");

		File cacheFile = new File(sb.toString());

		if (cacheFile.exists()) {
			Date modifiedDate = layoutSetPrototype.getModifiedDate();

			if (cacheFile.lastModified() >= modifiedDate.getTime()) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Using cached layout set prototype LAR file " +
							cacheFile.getAbsolutePath());
				}

				file = cacheFile;
			}
		}

		boolean newFile = false;

		if (file == null) {
			Group layoutSetPrototypeGroup = layoutSetPrototype.getGroup();

			file = LayoutLocalServiceUtil.exportLayoutsAsFile(
				layoutSetPrototypeGroup.getGroupId(), true, null, parameterMap,
				null, null);

			newFile = true;
		}

		long userId = UserLocalServiceUtil.getDefaultUserId(
			layoutSetPrototype.getCompanyId());

		LayoutLocalServiceUtil.importLayouts(
			userId, groupId, privateLayout, parameterMap, file);

		if (newFile) {
			try {
				FileUtil.copyFile(file, cacheFile);

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Copied " + file.getAbsolutePath() + " to " +
							cacheFile.getAbsolutePath());
				}
			}
			catch (Exception e) {
				_log.error(
					"Unable to copy file " + file.getAbsolutePath() + " to " +
						cacheFile.getAbsolutePath(),
					e);
			}
		}
	}

	protected static void setLayoutSetPrototypeLinkEnabledParameter(
		Map<String, String[]> parameterMap, LayoutSet targetLayoutSet,
		ServiceContext serviceContext) {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((permissionChecker == null) ||
			!PortalPermissionUtil.contains(
				permissionChecker, ActionKeys.UNLINK_LAYOUT_SET_PROTOTYPE)) {

			return;
		}

		if (targetLayoutSet.isPrivateLayout()) {
			boolean privateLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
				serviceContext, "privateLayoutSetPrototypeLinkEnabled", true);

			if (!privateLayoutSetPrototypeLinkEnabled) {
				privateLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
					serviceContext, "layoutSetPrototypeLinkEnabled");
			}

			parameterMap.put(
				PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED,
				new String[] {
					String.valueOf(privateLayoutSetPrototypeLinkEnabled)
				});
		}
		else {
			boolean publicLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
				serviceContext, "publicLayoutSetPrototypeLinkEnabled");

			if (!publicLayoutSetPrototypeLinkEnabled) {
				publicLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
					serviceContext, "layoutSetPrototypeLinkEnabled", true);
			}

			parameterMap.put(
				PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED,
				new String[] {
					String.valueOf(publicLayoutSetPrototypeLinkEnabled)
				});
		}
	}

	protected static void updateLayoutSetPrototypeLink(
			long groupId, boolean privateLayout, long layoutSetPrototypeId,
			boolean layoutSetPrototypeLinkEnabled)
		throws Exception {

		String layoutSetPrototypeUuid = null;

		if (layoutSetPrototypeId > 0) {
			LayoutSetPrototype layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.fetchLayoutSetPrototype(
					layoutSetPrototypeId);

			if (layoutSetPrototype != null) {
				layoutSetPrototypeUuid = layoutSetPrototype.getUuid();

				// Merge without enabling the link

				if (!layoutSetPrototypeLinkEnabled &&
					(layoutSetPrototypeId > 0)) {

					Map<String, String[]> parameterMap =
						getLayoutSetPrototypesParameters(true);

					importLayoutSetPrototype(
						layoutSetPrototype, groupId, privateLayout,
						parameterMap, true);
				}
			}
		}

		LayoutSetServiceUtil.updateLayoutSetPrototypeLinkEnabled(
			groupId, privateLayout, layoutSetPrototypeLinkEnabled,
			layoutSetPrototypeUuid);

		LayoutLocalServiceUtil.updatePriorities(groupId, privateLayout);
	}

	private static final String _TEMP_DIR =
		SystemProperties.get(SystemProperties.TMP_DIR) +
			"/liferay/layout_set_prototype/";

	private static Log _log = LogFactoryUtil.getLog(SitesUtil.class);

}