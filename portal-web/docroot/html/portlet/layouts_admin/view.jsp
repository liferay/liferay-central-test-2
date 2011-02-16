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

String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

if (portletName.equals(PortletKeys.LAYOUTS_ADMIN) || portletName.equals(PortletKeys.MY_ACCOUNT)) {
	portletDisplay.setURLBack(backURL);
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
long refererPlid = ParamUtil.getLong(request, "refererPlid", LayoutConstants.DEFAULT_PLID);
long layoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;

UnicodeProperties groupTypeSettings = null;

if (group != null) {
	groupTypeSettings = group.getTypeSettingsProperties();
}
else {
	groupTypeSettings = new UnicodeProperties();
}

UnicodeProperties liveGroupTypeSettings = liveGroup.getTypeSettingsProperties();

boolean privateLayout = tabs1.equals("private-pages");

Layout selLayout = null;

try {
	if (selPlid != LayoutConstants.DEFAULT_PLID) {
		selLayout = LayoutLocalServiceUtil.getLayout(selPlid);

		layoutId = selLayout.getLayoutId();
		privateLayout = selLayout.isPrivateLayout();
	}
}
catch (NoSuchLayoutException nsle) {
}

Layout refererLayout = null;

try {
	if (refererPlid != LayoutConstants.DEFAULT_PLID) {
		refererLayout = LayoutLocalServiceUtil.getLayout(refererPlid);
	}
}
catch (NoSuchLayoutException nsle) {
}

if (selLayout != null) {
	layoutId = selLayout.getLayoutId();
}

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

int publicPagesCount = group.getPublicLayoutsPageCount();
int privatePagesCount = group.getPrivateLayoutsPageCount();

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

String pagesName = "pages";

if (privateLayout && publicPagesCount > 0) {
	pagesName = "private-pages";
}
else if (!privateLayout && privatePagesCount > 0) {
	pagesName = "public-pages";
}

rootNodeName = LanguageUtil.get(pageContext, pagesName) + " (" + rootNodeName + ")";

LayoutView layoutView = layoutLister.getLayoutView(groupId, privateLayout, rootNodeName, locale);

List layoutList = layoutView.getList();

request.setAttribute(WebKeys.LAYOUT_LISTER_LIST, layoutList);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/layouts_admin/edit_layouts");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("redirect", redirect);

if (portletName.equals(PortletKeys.LAYOUTS_ADMIN) || portletName.equals(PortletKeys.MY_ACCOUNT)) {
	portletURL.setParameter("backURL", backURL);
}

portletURL.setParameter("groupId", String.valueOf(liveGroupId));

if (!portletName.equals(PortletKeys.GROUP_PAGES) && !portletName.equals(PortletKeys.MY_PAGES)) {
	if (organization != null) {
		EnterpriseAdminUtil.addPortletBreadcrumbEntries(organization, request, renderResponse);
	}
	else if (group.isLayoutPrototype()) {
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "page-template"), null);

		PortalUtil.addPortletBreadcrumbEntry(request, group.getDescriptiveName(), currentURL);
	}
	else {
		PortalUtil.addPortletBreadcrumbEntry(request, group.getDescriptiveName(), null);
	}

	if (!group.isLayoutPrototype()) {
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, pagesName), portletURL.toString());
	}
}

request.setAttribute("edit_pages.jsp-group", group);
request.setAttribute("edit_pages.jsp-selGroup", selGroup);
request.setAttribute("edit_pages.jsp-liveGroup", liveGroup);
request.setAttribute("edit_pages.jsp-stagingGroup", stagingGroup);

request.setAttribute("edit_pages.jsp-groupId", new Long(groupId));
request.setAttribute("edit_pages.jsp-liveGroupId", new Long(liveGroupId));
request.setAttribute("edit_pages.jsp-stagingGroupId", new Long(stagingGroupId));

request.setAttribute("edit_pages.jsp-selPlid", new Long(selPlid));
request.setAttribute("edit_pages.jsp-layoutId", new Long(layoutId));
request.setAttribute("edit_pages.jsp-selLayout", selLayout);
request.setAttribute("edit_pages.jsp-privateLayout", new Boolean(privateLayout));
request.setAttribute("edit_pages.jsp-groupTypeSettings", groupTypeSettings);
request.setAttribute("edit_pages.jsp-liveGroupTypeSettings", liveGroupTypeSettings);

request.setAttribute("edit_pages.jsp-rootNodeName", rootNodeName);

request.setAttribute("edit_pages.jsp-layoutList", layoutList);

request.setAttribute("edit_pages.jsp-portletURL", portletURL);
%>

<c:choose>
	<c:when test="<%= portletName.equals(PortletKeys.COMMUNITIES) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_COMMUNITIES) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_ORGANIZATIONS) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_USER_GROUPS) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_USERS) || portletName.equals(PortletKeys.GROUP_PAGES) || portletName.equals(PortletKeys.MY_PAGES) %>">
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

			userTabs1URL.setParameter("struts_action", "/my_pages/edit_layouts");
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

		if(selLayout != null && !group.isLayoutPrototype()) {
			PortalUtil.addPortletBreadcrumbEntry(request, selLayout.getName(locale), currentURL);
		}
		%>
	</c:when>
	<c:otherwise>

		<%
		if(selLayout != null && !group.isLayoutPrototype()) {
			PortalUtil.addPortletBreadcrumbEntry(request, selLayout.getName(locale), currentURL);
		}
		%>

		<div class="layout-breadcrumb">
			<liferay-ui:breadcrumb displayStyle="horizontal" showPortletBreadcrumb="<%= true %>" showGuestGroup="<%= false %>" showLayout="<%= false %>" showParentGroups="<%= false %>" />
		</div>
	</c:otherwise>
</c:choose>

<%
if(selLayout != null && !group.isLayoutPrototype()) {
	PortalUtil.addPortletBreadcrumbEntry(request, selLayout.getName(locale), currentURL);
}
%>



<aui:layout cssClass="manage-view">
	<c:if test="<%= !group.isLayoutPrototype() %>">
		<aui:column columnWidth="25" cssClass="manage-sitemap">
			<div class="header-row">
				<div class="header-row-content"> </div>
			</div>

			<liferay-util:include page="/html/portlet/layouts_admin/tree_js.jsp">
				<liferay-util:param name="treeId" value="layoutsTree" />
			</liferay-util:include>
		</aui:column>
	</c:if>

	<aui:column columnWidth="<%= group.isLayoutPrototype() ? 100 : 75 %>" cssClass="manage-layout">
		<c:choose>
			<c:when test="<%= selPlid > 0 %>">
				<liferay-util:include page="/html/portlet/layouts_admin/edit_layout.jsp" />
			</c:when>
			<c:otherwise>
				<liferay-util:include page="/html/portlet/layouts_admin/edit_layoutset.jsp" />
			</c:otherwise>
		</c:choose>
	</aui:column>
</aui:layout>