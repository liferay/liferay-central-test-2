<%--
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
--%>

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
String closeRedirect = ParamUtil.getString(request, "closeRedirect");

Group selGroup = (Group)request.getAttribute(WebKeys.GROUP);

Group group = layoutsAdminDisplayContext.getGroup();
Group liveGroup = layoutsAdminDisplayContext.getLiveGroup();
long groupId = layoutsAdminDisplayContext.getGroupId();
long liveGroupId = layoutsAdminDisplayContext.getLiveGroupId();
boolean privateLayout = layoutsAdminDisplayContext.isPrivateLayout();
UnicodeProperties liveGroupTypeSettings = liveGroup.getTypeSettingsProperties();
LayoutSet selLayoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

String rootNodeName = layoutsAdminDisplayContext.getRootNodeName();

PortletURL redirectURL = layoutsAdminDisplayContext.getRedirectURL();

int pagesCount = 0;

if (selGroup.isLayoutSetPrototype()) {
	privateLayout = true;
}

if (privateLayout) {
	if (group != null) {
		pagesCount = group.getPrivateLayoutsPageCount();
	}
}
else {
	if (group != null) {
		pagesCount = group.getPublicLayoutsPageCount();
	}
}

String[] mainSections = PropsValues.LAYOUT_SET_FORM_UPDATE;

if (!company.isSiteLogo()) {
	mainSections = ArrayUtil.remove(mainSections, "logo");
}

if (group.isGuest()) {
	mainSections = ArrayUtil.remove(mainSections, "advanced");
}

String[][] categorySections = {mainSections};

boolean hasExportImportLayoutsPermission = GroupPermissionUtil.contains(permissionChecker, liveGroup, ActionKeys.EXPORT_IMPORT_LAYOUTS);

boolean hasAddPageLayoutsPermission = GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.ADD_LAYOUT);

boolean hasViewPagesPermission = (pagesCount > 0) && (liveGroup.isStaged() || selGroup.isLayoutSetPrototype() || selGroup.isStagingGroup() || portletName.equals(PortletKeys.MY_SITES) || portletName.equals(PortletKeys.GROUP_PAGES) || portletName.equals(PortletKeys.SITES_ADMIN) || portletName.equals(PortletKeys.USERS_ADMIN));
%>

<aui:nav-bar>
	<aui:nav cssClass="navbar-nav">
		<c:if test="<%= hasViewPagesPermission %>">
			<liferay-portlet:actionURL plid="<%= layoutsAdminDisplayContext.getSelPlid() %>" portletName="<%= PortletKeys.SITE_REDIRECTOR %>" var="viewPagesURL">
				<portlet:param name="struts_action" value="/my_sites/view" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
				<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
			</liferay-portlet:actionURL>

			<aui:nav-item href="<%= viewPagesURL %>" iconCssClass="icon-file" label="view-pages" target="_blank" />
		</c:if>
		<c:if test="<%= hasAddPageLayoutsPermission %>">
			<portlet:renderURL var="addPagesURL">
				<portlet:param name="struts_action" value="/layouts_admin/add_layout" />
				<portlet:param name="tabs1" value="<%= layoutsAdminDisplayContext.getTabs1() %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
				<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= addPagesURL %>" iconCssClass="icon-plus" label="add-page" />
		</c:if>
		<c:if test="<%= hasExportImportLayoutsPermission %>">
			<portlet:renderURL var="exportPagesURL">
				<portlet:param name="struts_action" value="/layouts_admin/export_layouts" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EXPORT %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
				<portlet:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
				<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
				<portlet:param name="rootNodeName" value="<%= rootNodeName %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= exportPagesURL %>" iconCssClass="icon-arrow-down" label="export" />

			<portlet:renderURL var="importPagesURL">
				<portlet:param name="struts_action" value="/layouts_admin/import_layouts" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.VALIDATE %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
				<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
				<portlet:param name="rootNodeName" value="<%= rootNodeName %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= importPagesURL %>" iconCssClass="icon-arrow-up" label="import" />
		</c:if>
	</aui:nav>
</aui:nav-bar>

<c:if test="<%= liveGroup.isStaged() %>">
	<%@ include file="/html/portlet/layouts_admin/error_auth_exception.jspf" %>

	<%@ include file="/html/portlet/layouts_admin/error_remote_export_exception.jspf" %>

	<div class="alert alert-warning">
		<liferay-ui:message key="the-staging-environment-is-activated-changes-have-to-be-published-to-make-them-available-to-end-users" />
	</div>
</c:if>

<portlet:actionURL var="editLayoutSetURL">
	<portlet:param name="struts_action" value="/layouts_admin/edit_layout_set" />
</portlet:actionURL>

<aui:form action="<%= editLayoutSetURL %>" cssClass="edit-layoutset-form" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveLayoutset();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirectURL.toString() %>" />
	<aui:input name="closeRedirect" type="hidden" value="<%= closeRedirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="liveGroupId" type="hidden" value="<%= liveGroupId %>" />
	<aui:input name="stagingGroupId" type="hidden" value="<%= layoutsAdminDisplayContext.getStagingGroupId() %>" />
	<aui:input name="selPlid" type="hidden" value="<%= layoutsAdminDisplayContext.getSelPlid() %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= privateLayout %>" />
	<aui:input name="layoutSetId" type="hidden" value="<%= selLayoutSet.getLayoutSetId() %>" />
	<aui:input name="<%= PortletDataHandlerKeys.SELECTED_LAYOUTS %>" type="hidden" />

	<liferay-ui:form-navigator
		categoryNames="<%= _CATEGORY_NAMES %>"
		categorySections="<%= categorySections %>"
		jspPath="/html/portlet/layouts_admin/layout_set/"
		showButtons="<%= GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.MANAGE_LAYOUTS) && SitesUtil.isLayoutSetPrototypeUpdateable(selLayoutSet) %>"
	/>
</aui:form>

<aui:script>
	function <portlet:namespace />saveLayoutset(action) {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.prop('encoding', 'multipart/form-data');

		form.fm('<%= Constants.CMD %>').val(action ? action : 'update');

		submitForm(form);
	}
</aui:script>

<%!
private static final String[] _CATEGORY_NAMES = {""};
%>