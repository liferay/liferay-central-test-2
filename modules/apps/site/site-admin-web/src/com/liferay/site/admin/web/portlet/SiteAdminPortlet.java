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

package com.liferay.site.admin.web.portlet;

import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.portal.DuplicateGroupException;
import com.liferay.portal.GroupFriendlyURLException;
import com.liferay.portal.GroupInheritContentException;
import com.liferay.portal.GroupKeyException;
import com.liferay.portal.GroupParentException;
import com.liferay.portal.LayoutSetVirtualHostException;
import com.liferay.portal.LocaleException;
import com.liferay.portal.NoSuchBackgroundTaskException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.PendingBackgroundTaskException;
import com.liferay.portal.RemoteOptionsException;
import com.liferay.portal.RequiredGroupException;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.liveusers.LiveUsers;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.MembershipRequest;
import com.liferay.portal.model.MembershipRequestConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.Team;
import com.liferay.portal.security.auth.AuthException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.auth.RemoteAuthException;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeServiceUtil;
import com.liferay.portal.service.LayoutSetServiceUtil;
import com.liferay.portal.service.MembershipRequestLocalServiceUtil;
import com.liferay.portal.service.MembershipRequestServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.exportimport.RemoteExportException;
import com.liferay.portlet.exportimport.staging.StagingUtil;
import com.liferay.portlet.sites.util.Sites;
import com.liferay.portlet.sites.util.SitesUtil;
import com.liferay.site.admin.web.constants.SiteAdminPortletKeys;
import com.liferay.site.admin.web.upgrade.SiteAdminWebUpgrade;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-site-admin",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/site_admin.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Sites Admin",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + SiteAdminPortletKeys.SITE_ADMIN,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class SiteAdminPortlet extends MVCPortlet {

	public void activate(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		updateActive(actionRequest, true);
	}

	public void deactivate(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		updateActive(actionRequest, false);
	}

	public void deleteBackgroundTask(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long backgroundTaskId = ParamUtil.getLong(
			actionRequest, "backgroundTaskId");

		BackgroundTaskManagerUtil.deleteBackgroundTask(backgroundTaskId);
	}

	public void deleteGroups(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] deleteGroupIds = null;

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		if (groupId > 0) {
			deleteGroupIds = new long[] {groupId};
		}
		else {
			deleteGroupIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteGroupIds"), 0L);
		}

		for (long deleteGroupId : deleteGroupIds) {
			GroupServiceUtil.deleteGroup(deleteGroupId);

			LiveUsers.deleteGroup(themeDisplay.getCompanyId(), deleteGroupId);
		}
	}

	public void editGroup(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Throwable {

		Callable<Group> groupCallable = new GroupCallable(actionRequest);

		Group group = TransactionInvokerUtil.invoke(
			_transactionAttribute, groupCallable);

		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");

		PortletURL siteAdministrationURL = PortalUtil.getControlPanelPortletURL(
			actionRequest, group, SiteAdminPortletKeys.SITE_SETTINGS, 0,
			PortletRequest.RENDER_PHASE);

		siteAdministrationURL.setParameter(
			"redirect", siteAdministrationURL.toString());

		if (liveGroupId <= 0) {
			hideDefaultSuccessMessage(actionRequest);

			MultiSessionMessages.add(
				actionRequest,
				SiteAdminPortletKeys.SITE_SETTINGS + "requestProcessed");
		}

		actionRequest.setAttribute(
			WebKeys.REDIRECT, siteAdministrationURL.toString());

		sendRedirect(actionRequest, actionResponse);
	}

	public void editGroupAssignments(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		long[] removeUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeUserIds"), 0L);

		removeUserIds = filterRemoveUserIds(groupId, removeUserIds);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		UserServiceUtil.unsetGroupUsers(groupId, removeUserIds, serviceContext);

		LiveUsers.leaveGroup(
			themeDisplay.getCompanyId(), groupId, removeUserIds);
	}

	/**
	 * Resets the number of failed merge attempts for the site template, which
	 * is accessed by retrieving the layout set prototype ID. Once the counter
	 * is reset, the modified site template is merged back into its linked site,
	 * which is accessed by retrieving the group ID and private layout set.
	 *
	 * <p>
	 * If the number of failed merge attempts is not equal to zero after the
	 * merge, an error key is submitted to {@link SessionErrors}.
	 * </p>
	 *
	 * @param  actionRequest the portlet request used to retrieve parameters
	 * @throws Exception if an exception occurred
	 */
	public void resetMergeFailCountAndMerge(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long layoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "layoutSetPrototypeId");

		LayoutSetPrototype layoutSetPrototype =
			LayoutSetPrototypeServiceUtil.getLayoutSetPrototype(
				layoutSetPrototypeId);

		SitesUtil.setMergeFailCount(layoutSetPrototype, 0);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		boolean privateLayoutSet = ParamUtil.getBoolean(
			actionRequest, "privateLayoutSet");

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			groupId, privateLayoutSet);

		SitesUtil.resetPrototype(layoutSet);

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		SitesUtil.mergeLayoutSetPrototypeLayouts(group, layoutSet);

		layoutSetPrototype =
			LayoutSetPrototypeServiceUtil.getLayoutSetPrototype(
				layoutSetPrototypeId);

		if (SitesUtil.getMergeFailCount(layoutSetPrototype) > 0) {
			SessionErrors.add(actionRequest, "resetMergeFailCountAndMerge");
		}
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(
			_panelAppRegistry, _panelCategoryRegistry);

		renderRequest.setAttribute(
			ApplicationListWebKeys.PANEL_CATEGORY_HELPER, panelCategoryHelper);

		if (SessionErrors.contains(
				renderRequest, NoSuchBackgroundTaskException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchGroupException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, PrincipalException.getNestedClasses())) {

			include("/error.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	protected long[] filterRemoveUserIds(long groupId, long[] userIds)
		throws Exception {

		Set<Long> filteredUserIds = new HashSet<>(userIds.length);

		for (long userId : userIds) {
			if (UserLocalServiceUtil.hasGroupUser(groupId, userId)) {
				filteredUserIds.add(userId);
			}
		}

		return ArrayUtil.toArray(
			filteredUserIds.toArray(new Long[filteredUserIds.size()]));
	}

	protected Group getLiveGroup(PortletRequest portletRequest)
		throws PortalException {

		long liveGroupId = ParamUtil.getLong(portletRequest, "liveGroupId");

		if (liveGroupId > 0) {
			return GroupLocalServiceUtil.getGroup(liveGroupId);
		}

		return null;
	}

	protected long getRefererGroupId(ThemeDisplay themeDisplay)
		throws Exception {

		long refererGroupId = 0;

		try {
			Layout refererLayout = LayoutLocalServiceUtil.getLayout(
				themeDisplay.getRefererPlid());

			refererGroupId = refererLayout.getGroupId();
		}
		catch (NoSuchLayoutException nsle) {
		}

		return refererGroupId;
	}

	protected List<Role> getRoles(PortletRequest portletRequest)
		throws Exception {

		List<Role> roles = new ArrayList<>();

		long[] siteRolesRoleIds = StringUtil.split(
			ParamUtil.getString(portletRequest, "siteRolesRoleIds"), 0L);

		for (long siteRolesRoleId : siteRolesRoleIds) {
			if (siteRolesRoleId == 0) {
				continue;
			}

			Role role = RoleLocalServiceUtil.getRole(siteRolesRoleId);

			roles.add(role);
		}

		return roles;
	}

	protected List<Team> getTeams(PortletRequest portletRequest)
		throws Exception {

		List<Team> teams = new ArrayList<>();

		long[] teamsTeamIds = ArrayUtil.unique(
				StringUtil.split(
						ParamUtil.getString(portletRequest, "teamsTeamIds"), 0L)
		);

		for (long teamsTeamId : teamsTeamIds) {
			if (teamsTeamId == 0) {
				continue;
			}

			Team team = TeamLocalServiceUtil.getTeam(teamsTeamId);

			teams.add(team);
		}

		return teams;
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof AssetCategoryException ||
			cause instanceof AssetTagException ||
			cause instanceof AuthException ||
			cause instanceof DuplicateGroupException ||
			cause instanceof GroupFriendlyURLException ||
			cause instanceof GroupInheritContentException ||
			cause instanceof GroupKeyException ||
			cause instanceof GroupParentException ||
			cause instanceof LayoutSetVirtualHostException ||
			cause instanceof LocaleException ||
			cause instanceof NoSuchBackgroundTaskException ||
			cause instanceof PendingBackgroundTaskException ||
			cause instanceof RemoteAuthException ||
			cause instanceof RemoteExportException ||
			cause instanceof RemoteOptionsException ||
			cause instanceof RequiredGroupException ||
			cause instanceof SystemException ||
			super.isSessionErrorException(cause)) {

			return true;
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setPanelAppRegistry(PanelAppRegistry panelAppRegistry) {
		_panelAppRegistry = panelAppRegistry;
	}

	@Reference(unbind = "-")
	protected void setPanelCategoryRegistry(
		PanelCategoryRegistry panelCategoryRegistry) {

		_panelCategoryRegistry = panelCategoryRegistry;
	}

	@Reference(unbind = "-")
	protected void setSiteAdminWebUpgrade(
		SiteAdminWebUpgrade siteAdminWebUpgrade) {
	}

	protected void updateActive(ActionRequest actionRequest, boolean active)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		if ((groupId == themeDisplay.getDoAsGroupId()) ||
			(groupId == themeDisplay.getScopeGroupId()) ||
			(groupId == getRefererGroupId(themeDisplay))) {

			throw new RequiredGroupException.MustNotDeleteCurrentGroup(groupId);
		}

		Group group = GroupServiceUtil.getGroup(groupId);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Group.class.getName(), actionRequest);

		GroupServiceUtil.updateGroup(
			groupId, group.getParentGroupId(), group.getNameMap(),
			group.getDescriptionMap(), group.getType(),
			group.isManualMembership(), group.getMembershipRestriction(),
			group.getFriendlyURL(), group.isInheritContent(), active,
			serviceContext);
	}

	protected Group updateGroup(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long userId = PortalUtil.getUserId(actionRequest);

		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");

		long parentGroupId = ParamUtil.getLong(
			actionRequest, "parentGroupSearchContainerPrimaryKeys",
			GroupConstants.DEFAULT_PARENT_GROUP_ID);
		Map<Locale, String> nameMap = null;
		Map<Locale, String> descriptionMap = null;
		int type = 0;
		String friendlyURL = null;
		boolean inheritContent = false;
		boolean active = false;
		boolean manualMembership = true;

		int membershipRestriction =
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION;

		boolean actionRequestMembershipRestriction = ParamUtil.getBoolean(
			actionRequest, "membershipRestriction");

		if (actionRequestMembershipRestriction &&
			(parentGroupId != GroupConstants.DEFAULT_PARENT_GROUP_ID)) {

			membershipRestriction =
				GroupConstants.MEMBERSHIP_RESTRICTION_TO_PARENT_SITE_MEMBERS;
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Group.class.getName(), actionRequest);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		Group liveGroup = null;

		if (liveGroupId <= 0) {

			// Add group

			nameMap = LocalizationUtil.getLocalizationMap(
				actionRequest, "name");
			descriptionMap = LocalizationUtil.getLocalizationMap(
				actionRequest, "description");
			type = ParamUtil.getInteger(actionRequest, "type");
			friendlyURL = ParamUtil.getString(actionRequest, "friendlyURL");
			manualMembership = ParamUtil.getBoolean(
				actionRequest, "manualMembership");
			inheritContent = ParamUtil.getBoolean(
				actionRequest, "inheritContent");
			active = ParamUtil.getBoolean(actionRequest, "active");

			liveGroup = GroupServiceUtil.addGroup(
				parentGroupId, GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap,
				descriptionMap, type, manualMembership, membershipRestriction,
				friendlyURL, true, inheritContent, active, serviceContext);

			LiveUsers.joinGroup(
				themeDisplay.getCompanyId(), liveGroup.getGroupId(), userId);
		}
		else {

			// Update group

			liveGroup = GroupLocalServiceUtil.getGroup(liveGroupId);

			nameMap = LocalizationUtil.getLocalizationMap(
				actionRequest, "name", liveGroup.getNameMap());
			descriptionMap = LocalizationUtil.getLocalizationMap(
				actionRequest, "description", liveGroup.getDescriptionMap());
			type = ParamUtil.getInteger(
				actionRequest, "type", liveGroup.getType());
			manualMembership = ParamUtil.getBoolean(
				actionRequest, "manualMembership",
				liveGroup.isManualMembership());
			friendlyURL = ParamUtil.getString(
				actionRequest, "friendlyURL", liveGroup.getFriendlyURL());
			inheritContent = ParamUtil.getBoolean(
				actionRequest, "inheritContent", liveGroup.getInheritContent());
			active = ParamUtil.getBoolean(
				actionRequest, "active", liveGroup.getActive());

			liveGroup = GroupServiceUtil.updateGroup(
				liveGroupId, parentGroupId, nameMap, descriptionMap, type,
				manualMembership, membershipRestriction, friendlyURL,
				inheritContent, active, serviceContext);

			if (type == GroupConstants.TYPE_SITE_OPEN) {
				List<MembershipRequest> membershipRequests =
					MembershipRequestLocalServiceUtil.search(
						liveGroupId, MembershipRequestConstants.STATUS_PENDING,
						QueryUtil.ALL_POS, QueryUtil.ALL_POS);

				for (MembershipRequest membershipRequest : membershipRequests) {
					MembershipRequestServiceUtil.updateStatus(
						membershipRequest.getMembershipRequestId(),
						themeDisplay.translate(
							"your-membership-has-been-approved"),
						MembershipRequestConstants.STATUS_APPROVED,
						serviceContext);

					LiveUsers.joinGroup(
						themeDisplay.getCompanyId(),
						membershipRequest.getGroupId(),
						new long[] {membershipRequest.getUserId()});
				}
			}
		}

		// Settings

		UnicodeProperties typeSettingsProperties =
			liveGroup.getTypeSettingsProperties();

		String customJspServletContextName = ParamUtil.getString(
			actionRequest, "customJspServletContextName",
			typeSettingsProperties.getProperty("customJspServletContextName"));

		typeSettingsProperties.setProperty(
			"customJspServletContextName", customJspServletContextName);

		typeSettingsProperties.setProperty(
			"defaultSiteRoleIds",
			ListUtil.toString(
				getRoles(actionRequest), Role.ROLE_ID_ACCESSOR,
				StringPool.COMMA));
		typeSettingsProperties.setProperty(
			"defaultTeamIds",
			ListUtil.toString(
				getTeams(actionRequest), Team.TEAM_ID_ACCESSOR,
				StringPool.COMMA));

		String[] analyticsTypes = PrefsPropsUtil.getStringArray(
			themeDisplay.getCompanyId(), PropsKeys.ADMIN_ANALYTICS_TYPES,
			StringPool.NEW_LINE);

		for (String analyticsType : analyticsTypes) {
			if (StringUtil.equalsIgnoreCase(analyticsType, "google")) {
				String googleAnalyticsId = ParamUtil.getString(
					actionRequest, "googleAnalyticsId",
					typeSettingsProperties.getProperty("googleAnalyticsId"));

				typeSettingsProperties.setProperty(
					"googleAnalyticsId", googleAnalyticsId);
			}
			else {
				String analyticsScript = ParamUtil.getString(
					actionRequest, Sites.ANALYTICS_PREFIX + analyticsType,
					typeSettingsProperties.getProperty(analyticsType));

				typeSettingsProperties.setProperty(
					Sites.ANALYTICS_PREFIX + analyticsType, analyticsScript);
			}
		}

		boolean trashEnabled = ParamUtil.getBoolean(
			actionRequest, "trashEnabled",
			GetterUtil.getBoolean(
				typeSettingsProperties.getProperty("trashEnabled"), true));

		typeSettingsProperties.setProperty(
			"trashEnabled", String.valueOf(trashEnabled));

		int trashEntriesMaxAgeCompany = PrefsPropsUtil.getInteger(
			themeDisplay.getCompanyId(), PropsKeys.TRASH_ENTRIES_MAX_AGE);

		double trashEntriesMaxAgeGroup = ParamUtil.getDouble(
			actionRequest, "trashEntriesMaxAge");

		if (trashEntriesMaxAgeGroup > 0) {
			trashEntriesMaxAgeGroup *= 1440;
		}
		else {
			trashEntriesMaxAgeGroup = GetterUtil.getInteger(
				typeSettingsProperties.getProperty("trashEntriesMaxAge"),
				trashEntriesMaxAgeCompany);
		}

		if (trashEntriesMaxAgeGroup != trashEntriesMaxAgeCompany) {
			typeSettingsProperties.setProperty(
				"trashEntriesMaxAge",
				String.valueOf(GetterUtil.getInteger(trashEntriesMaxAgeGroup)));
		}
		else {
			typeSettingsProperties.remove("trashEntriesMaxAge");
		}

		int contentSharingWithChildrenEnabled = ParamUtil.getInteger(
			actionRequest, "contentSharingWithChildrenEnabled",
			GetterUtil.getInteger(
				typeSettingsProperties.getProperty(
					"contentSharingWithChildrenEnabled"),
				Sites.CONTENT_SHARING_WITH_CHILDREN_DEFAULT_VALUE));

		typeSettingsProperties.setProperty(
			"contentSharingWithChildrenEnabled",
			String.valueOf(contentSharingWithChildrenEnabled));

		UnicodeProperties formTypeSettingsProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "TypeSettingsProperties--");

		typeSettingsProperties.putAll(formTypeSettingsProperties);

		// Virtual hosts

		LayoutSet publicLayoutSet = liveGroup.getPublicLayoutSet();

		String publicVirtualHost = ParamUtil.getString(
			actionRequest, "publicVirtualHost",
			publicLayoutSet.getVirtualHostname());

		LayoutSetServiceUtil.updateVirtualHost(
			liveGroup.getGroupId(), false, publicVirtualHost);

		LayoutSet privateLayoutSet = liveGroup.getPrivateLayoutSet();

		String privateVirtualHost = ParamUtil.getString(
			actionRequest, "privateVirtualHost",
			privateLayoutSet.getVirtualHostname());

		LayoutSetServiceUtil.updateVirtualHost(
			liveGroup.getGroupId(), true, privateVirtualHost);

		// Staging

		if (liveGroup.hasStagingGroup()) {
			Group stagingGroup = liveGroup.getStagingGroup();

			friendlyURL = ParamUtil.getString(
				actionRequest, "stagingFriendlyURL",
				stagingGroup.getFriendlyURL());

			GroupServiceUtil.updateFriendlyURL(
				stagingGroup.getGroupId(), friendlyURL);

			LayoutSet stagingPublicLayoutSet =
				stagingGroup.getPublicLayoutSet();

			publicVirtualHost = ParamUtil.getString(
				actionRequest, "stagingPublicVirtualHost",
				stagingPublicLayoutSet.getVirtualHostname());

			LayoutSetServiceUtil.updateVirtualHost(
				stagingGroup.getGroupId(), false, publicVirtualHost);

			LayoutSet stagingPrivateLayoutSet =
				stagingGroup.getPrivateLayoutSet();

			privateVirtualHost = ParamUtil.getString(
				actionRequest, "stagingPrivateVirtualHost",
				stagingPrivateLayoutSet.getVirtualHostname());

			LayoutSetServiceUtil.updateVirtualHost(
				stagingGroup.getGroupId(), true, privateVirtualHost);

			GroupServiceUtil.updateGroup(
				stagingGroup.getGroupId(), typeSettingsProperties.toString());
		}

		liveGroup = GroupServiceUtil.updateGroup(
			liveGroup.getGroupId(), typeSettingsProperties.toString());

		// Layout set prototypes

		long privateLayoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "privateLayoutSetPrototypeId");
		long publicLayoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "publicLayoutSetPrototypeId");

		boolean privateLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
			actionRequest, "privateLayoutSetPrototypeLinkEnabled",
			privateLayoutSet.isLayoutSetPrototypeLinkEnabled());
		boolean publicLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
			actionRequest, "publicLayoutSetPrototypeLinkEnabled",
			publicLayoutSet.isLayoutSetPrototypeLinkEnabled());

		if ((privateLayoutSetPrototypeId == 0) &&
			(publicLayoutSetPrototypeId == 0) &&
			!privateLayoutSetPrototypeLinkEnabled &&
			!publicLayoutSetPrototypeLinkEnabled) {

			long layoutSetPrototypeId = ParamUtil.getLong(
				actionRequest, "layoutSetPrototypeId");
			int layoutSetVisibility = ParamUtil.getInteger(
				actionRequest, "layoutSetVisibility");
			boolean layoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
				actionRequest, "layoutSetPrototypeLinkEnabled",
				(layoutSetPrototypeId > 0));

			if (layoutSetVisibility == _LAYOUT_SET_VISIBILITY_PRIVATE) {
				privateLayoutSetPrototypeId = layoutSetPrototypeId;

				privateLayoutSetPrototypeLinkEnabled =
					layoutSetPrototypeLinkEnabled;
			}
			else {
				publicLayoutSetPrototypeId = layoutSetPrototypeId;

				publicLayoutSetPrototypeLinkEnabled =
					layoutSetPrototypeLinkEnabled;
			}
		}

		if (!liveGroup.isStaged() || liveGroup.isStagedRemotely()) {
			SitesUtil.updateLayoutSetPrototypesLinks(
				liveGroup, publicLayoutSetPrototypeId,
				privateLayoutSetPrototypeId,
				publicLayoutSetPrototypeLinkEnabled,
				privateLayoutSetPrototypeLinkEnabled);
		}
		else {
			SitesUtil.updateLayoutSetPrototypesLinks(
				liveGroup.getStagingGroup(), publicLayoutSetPrototypeId,
				privateLayoutSetPrototypeId,
				publicLayoutSetPrototypeLinkEnabled,
				privateLayoutSetPrototypeLinkEnabled);
		}

		// Staging

		if (!privateLayoutSet.isLayoutSetPrototypeLinkActive() &&
			!publicLayoutSet.isLayoutSetPrototypeLinkActive()) {

			StagingUtil.updateStaging(actionRequest, liveGroup);
		}

		return liveGroup;
	}

	private static final int _LAYOUT_SET_VISIBILITY_PRIVATE = 1;

	private static final TransactionAttribute _transactionAttribute =
		TransactionAttribute.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	private PanelAppRegistry _panelAppRegistry;
	private PanelCategoryRegistry _panelCategoryRegistry;

	private class GroupCallable implements Callable<Group> {

		@Override
		public Group call() throws Exception {
			return updateGroup(_actionRequest);
		}

		private GroupCallable(ActionRequest actionRequest) {
			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}