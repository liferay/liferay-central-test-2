<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
--%>

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "public-pages");
String tabs2 = ParamUtil.getString(request, "tabs2");
String tabs3 = ParamUtil.getString(request, "tabs3");
String tabs4 = ParamUtil.getString(request, "tabs4");

String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

if (portletName.equals(PortletKeys.LAYOUTS_ADMIN) || portletName.equals(PortletKeys.MY_ACCOUNT)) {
	portletDisplay.setURLBack(backURL);
}

if (portletName.equals(PortletKeys.LAYOUTS_ADMIN) && tabs1.equals("settings")) {
	renderResponse.setTitle(LanguageUtil.get(pageContext, "settings"));
}

Group selGroup = (Group)request.getAttribute(WebKeys.GROUP);

Group liveGroup = null;
Group stagingGroup = null;

if (selGroup.isStagingGroup()) {
	liveGroup = selGroup.getLiveGroup();
	stagingGroup = selGroup;
}
else {
	liveGroup = selGroup;

	if (selGroup.hasStagingGroup()) {
		stagingGroup = selGroup.getStagingGroup();
	}
}

Group group = null;

if (stagingGroup != null) {
	group = stagingGroup;
}
else {
	group = liveGroup;
}

long groupId = liveGroup.getGroupId();

if (group != null) {
	groupId = group.getGroupId();
}

long liveGroupId = liveGroup.getGroupId();

long stagingGroupId = 0;

if (stagingGroup != null) {
	stagingGroupId = stagingGroup.getGroupId();
}

long selPlid = ParamUtil.getLong(request, "selPlid", LayoutConstants.DEFAULT_PLID);

UnicodeProperties groupTypeSettings = null;

if (group != null) {
	groupTypeSettings = group.getTypeSettingsProperties();
}
else {
	groupTypeSettings = new UnicodeProperties();
}

UnicodeProperties liveGroupTypeSettings = liveGroup.getTypeSettingsProperties();

Layout selLayout = null;

try {
	if (selPlid != LayoutConstants.DEFAULT_PLID) {
		selLayout = LayoutLocalServiceUtil.getLayout(selPlid);
	}
}
catch (NoSuchLayoutException nsle) {
}

if (Validator.isNull(tabs2) && !tabs1.equals("settings")) {
	tabs2 = "pages";
}

if (tabs1.endsWith("-pages") && !tabs2.equals("pages") && !tabs2.equals("look-and-feel") && !tabs2.equals("proposals")) {
	tabs2 = "pages";
}
else if (tabs1.equals("settings") && !tabs2.equals("virtual-host") && !tabs2.equals("logo") && !tabs2.equals("sitemap") && !tabs2.equals("monitoring") && !tabs2.equals("merge-pages") && !tabs2.equals("staging")) {
	tabs2 = "virtual-host";
}

if ((selLayout == null) && tabs2.equals("pages")) {
	tabs3 = "children";
}

if (tabs2.equals("pages") && !tabs3.equals("look-and-feel") && (!tabs3.equals("children") || ((selLayout != null) && !PortalUtil.isLayoutParentable(selLayout)))) {
	tabs3 = "page";
}

if (!tabs2.equals("export-import") && (tabs2.equals("look-and-feel") || tabs3.equals("look-and-feel"))) {
	if (!tabs4.equals("regular-browsers") && !tabs4.equals("mobile-devices")) {
		tabs4 = "regular-browsers";
	}
}

long parentLayoutId = BeanParamUtil.getLong(selLayout, request, "parentLayoutId", LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

Organization organization = null;
User selUser = null;
UserGroup userGroup = null;

if (liveGroup.isOrganization()) {
	organization = OrganizationLocalServiceUtil.getOrganization(liveGroup.getOrganizationId());
}
else if (liveGroup.isUser()) {
	selUser = UserLocalServiceUtil.getUserById(liveGroup.getClassPK());
}
else if (liveGroup.isUserGroup()) {
	userGroup = UserGroupLocalServiceUtil.getUserGroup(liveGroup.getClassPK());
}

String tabs1Names = "public-pages,private-pages";

boolean privateLayout = tabs1.equals("private-pages");

if (liveGroup.isUser()) {
	boolean hasPowerUserRole = RoleLocalServiceUtil.hasUserRole(selUser.getUserId(), company.getCompanyId(), RoleConstants.POWER_USER, true);

	boolean privateLayoutsModifiable = PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_MODIFIABLE && (!PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_POWER_USER_REQUIRED || hasPowerUserRole);
	boolean publicLayoutsModifiable = PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_MODIFIABLE && (!PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_POWER_USER_REQUIRED || hasPowerUserRole);

	if (privateLayoutsModifiable && publicLayoutsModifiable) {
		tabs1Names = "public-pages,private-pages";
	}
	else if (privateLayoutsModifiable) {
		tabs1Names = "private-pages";
	}
	else if (publicLayoutsModifiable) {
		tabs1Names = "public-pages";
	}

	if (!publicLayoutsModifiable && privateLayoutsModifiable && !privateLayout) {
		tabs1 = "private-pages";

		privateLayout = true;
	}
}

if (selGroup.isLayoutSetPrototype()) {
	privateLayout = true;
}

LayoutLister layoutLister = new LayoutLister();

String rootNodeName = liveGroup.getDescriptiveName();

if (liveGroup.isOrganization()) {
	rootNodeName = organization.getName();
}
else if (liveGroup.isUser()) {
	rootNodeName = selUser.getFullName();
}
else if (liveGroup.isUserGroup()) {
	rootNodeName = userGroup.getName();
}
else if (selGroup.isLayoutSetPrototype()) {
	LayoutSetPrototype layoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(selGroup.getClassPK());

	rootNodeName = layoutSetPrototype.getName(user.getLanguageId());
}

LayoutView layoutView = layoutLister.getLayoutView(groupId, privateLayout, rootNodeName, locale);

List layoutList = layoutView.getList();

request.setAttribute(WebKeys.LAYOUT_LISTER_LIST, layoutList);

boolean workflowEnabled = liveGroup.isWorkflowEnabled();
int workflowStages = ParamUtil.getInteger(request, "workflowStages", liveGroup.getWorkflowStages());
String[] workflowRoleNames = StringUtil.split(ParamUtil.getString(request, "workflowRoleNames", liveGroup.getWorkflowRoleNames()));

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/layouts_admin/edit_pages");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("tabs3", tabs3);
//portletURL.setParameter("tabs4", tabs4);
portletURL.setParameter("redirect", redirect);

if (portletName.equals(PortletKeys.LAYOUTS_ADMIN) || portletName.equals(PortletKeys.MY_ACCOUNT)) {
	portletURL.setParameter("backURL", backURL);
}

portletURL.setParameter("groupId", String.valueOf(liveGroupId));

if (!portletName.equals(PortletKeys.GROUP_PAGES) && !portletName.equals(PortletKeys.MY_PAGES)) {
	if (organization != null) {
		EnterpriseAdminUtil.addPortletBreadcrumbEntries(organization, request, renderResponse);
	}
	else {
		PortalUtil.addPortletBreadcrumbEntry(request, group.getDescriptiveName(), null);
	}

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "manage-pages"), currentURL);
}

request.setAttribute("edit_pages.jsp-tab4", tabs4);

request.setAttribute("edit_pages.jsp-liveGroup", liveGroup);
request.setAttribute("edit_pages.jsp-stagingGroup", stagingGroup);
request.setAttribute("edit_pages.jsp-group", group);
request.setAttribute("edit_pages.jsp-selGroup", selGroup);
request.setAttribute("edit_pages.jsp-groupId", new Long(groupId));
request.setAttribute("edit_pages.jsp-stagingGroupId", new Long(stagingGroupId));
request.setAttribute("edit_pages.jsp-liveGroupId", new Long(liveGroupId));
request.setAttribute("edit_pages.jsp-selPlid", new Long(selPlid));
request.setAttribute("edit_pages.jsp-privateLayout", new Boolean(privateLayout));
request.setAttribute("edit_pages.jsp-groupTypeSettings", groupTypeSettings);
request.setAttribute("edit_pages.jsp-liveGroupTypeSettings", liveGroupTypeSettings);
request.setAttribute("edit_pages.jsp-selLayout", selLayout);

request.setAttribute("edit_pages.jsp-rootNodeName", rootNodeName);

request.setAttribute("edit_pages.jsp-layoutList", layoutList);

request.setAttribute("edit_pages.jsp-workflowEnabled", new Boolean(workflowEnabled));
request.setAttribute("edit_pages.jsp-workflowStages", new Integer(workflowStages));
request.setAttribute("edit_pages.jsp-workflowRoleNames", workflowRoleNames);

request.setAttribute("edit_pages.jsp-portletURL", portletURL);
%>

<c:if test="<%= portletName.equals(PortletKeys.COMMUNITIES) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_COMMUNITIES) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_ORGANIZATIONS) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_USER_GROUPS) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_USERS) || portletName.equals(PortletKeys.GROUP_PAGES) || portletName.equals(PortletKeys.MY_PAGES) %>">
	<c:if test="<%= portletName.equals(PortletKeys.COMMUNITIES) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_COMMUNITIES) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_ORGANIZATIONS) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_USER_GROUPS) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_USERS) %>">
		<liferay-ui:header
			backURL="<%= backURL %>"
			title="<%= liveGroup.getDescriptiveName() %>"
		/>
	</c:if>

	<%
	String tabs1URL = portletURL.toString();

	if (liveGroup.isUser()) {
		PortletURL userTabs1URL = renderResponse.createRenderURL();

		userTabs1URL.setParameter("struts_action", "/my_pages/edit_pages");
		userTabs1URL.setParameter("tabs1", tabs1);
		userTabs1URL.setParameter("backURL", backURL);
		userTabs1URL.setParameter("groupId", String.valueOf(liveGroupId));

		tabs1URL = userTabs1URL.toString();
	}
	%>

	<liferay-ui:tabs
		names="<%= tabs1Names %>"
		param="tabs1"
		value="<%= tabs1 %>"
		url="<%= tabs1URL %>"
	/>

	<%
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, TextFormatter.format(tabs1, TextFormatter.O)), currentURL);
	%>
</c:if>

<liferay-util:include page="/html/portlet/layouts_admin/edit_pages.jsp" />