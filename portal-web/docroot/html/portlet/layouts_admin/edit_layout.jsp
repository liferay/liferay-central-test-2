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
Group selGroup = (Group)request.getAttribute(WebKeys.GROUP);

Group group = (Group)request.getAttribute("edit_pages.jsp-group");
Group liveGroup = (Group)request.getAttribute("edit_pages.jsp-liveGroup");

long groupId = ((Long)request.getAttribute("edit_pages.jsp-groupId")).longValue();
long liveGroupId = ((Long)request.getAttribute("edit_pages.jsp-liveGroupId")).longValue();
long stagingGroupId = ((Long)request.getAttribute("edit_pages.jsp-liveGroupId")).longValue();

Layout selLayout = (Layout)request.getAttribute("edit_pages.jsp-selLayout");
long selPlid = ((Long)request.getAttribute("edit_pages.jsp-selPlid")).longValue();
long layoutId = ((Long)request.getAttribute("edit_pages.jsp-layoutId")).longValue();
boolean privateLayout = ((Boolean)request.getAttribute("edit_pages.jsp-privateLayout")).booleanValue();

PortletURL portletURL = (PortletURL)request.getAttribute("edit_pages.jsp-portletURL");
portletURL.setParameter("selPlid", String.valueOf(selPlid));

long refererPlid = ParamUtil.getLong(request, "refererPlid", LayoutConstants.DEFAULT_PLID);

//Sections

String[] mainSections = PropsValues.LAYOUT_FORM_UPDATE;

String[][] categorySections = {mainSections};
%>

<div class="header-row title">
	<div class="header-row-content">
		<aui:button-row cssClass="edit-toolbar" id='<%= renderResponse.getNamespace() + "layoutToolbar" %>'>
		</aui:button-row>
	</div>
</div>

<portlet:actionURL var="editLayoutURL">
	<portlet:param name="struts_action" value="/manage_pages/edit_layouts" />
</portlet:actionURL>

<aui:form action="<%= editLayoutURL %>" cssClass="edit-layout-form"  enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveLayout();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="liveGroupId" type="hidden" value="<%= liveGroupId %>" />
	<aui:input name="stagingGroupId" type="hidden" value="<%= stagingGroupId %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= privateLayout %>" />
	<aui:input name="layoutId" type="hidden" value="<%= layoutId %>" />
	<aui:input name="selPlid" type="hidden" value="<%= selPlid %>" />
	<aui:input name="<%= PortletDataHandlerKeys.SELECTED_LAYOUTS %>" type="hidden" />

	<c:if test="<%= !group.isLayoutPrototype() && (selLayout != null) %>">
		<c:if test="<%= liveGroup.isStaged() %>">
			<liferay-util:include page="/html/portlet/layouts_admin/staging_toolbar.jsp" />
		</c:if>

		<liferay-security:permissionsURL
			modelResource="<%= Layout.class.getName() %>"
			modelResourceDescription="<%= selLayout.getName(locale) %>"
			resourcePrimKey="<%= String.valueOf(selLayout.getPlid()) %>"
			var="permissionURL"
		/>

		<aui:script use="aui-dialog,aui-dialog-iframe">
			var buttonRow = A.one('#<portlet:namespace />layoutToolbar');

			var permissionPopUp = null;
			var popUp = null;

			var layoutToolbar = new A.Toolbar(
				{
					activeState: false,
					boundingBox: buttonRow,
					children: [
						{
							handler: function(event) {
								if (!popUp) {
									var content = A.one('#<portlet:namespace />addLayout');

									popUp = new A.Dialog(
										{
											bodyContent: content.show(),
											centered: true,
											title: '<liferay-ui:message key="add-child-page" />',
											modal: true,
											width: 500
										}
									).render();
								}

								popUp.show();
							},
							icon: 'circle-plus',
							label: '<liferay-ui:message key="add-child-page" />'
						},
						{
							handler: function(event) {
								if (!permissionPopUp) {
									permissionPopUp = new A.Dialog(
										{
											centered: true,
											modal: true,
											title: '<liferay-ui:message key="permissions" />',
											width: 700
										}
									).plug(
										A.Plugin.DialogIframe,
										{
											after: {
												load: Liferay.Util.afterIframeLoaded
											},
											uri: '<%= permissionURL %>'
										}
									).render();
								}
								else {
									permissionPopUp.iframe.node.get('contentWindow.location').reload(true);
								}

								permissionPopUp.show();
								permissionPopUp.centered();

							},
							icon: 'key',
							label: '<liferay-ui:message key="permissions" />'
						},
						{
							handler: function(event) {
								<c:choose>
									<c:when test="<%= (selPlid == themeDisplay.getPlid()) || (selPlid == refererPlid) %>">
										alert('<%= UnicodeLanguageUtil.get(pageContext, "you-cannot-delete-this-page-because-you-are-currently-accessing-this-page") %>');
									</c:when>
									<c:otherwise>
										if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-selected-page") %>')) {
											document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
											document.<portlet:namespace />fm.<portlet:namespace />pagesRedirect.value = "<%= portletURL.toString() %>&<portlet:namespace />selPlid=<%= selLayout.getParentPlid() %>";
											submitForm(document.<portlet:namespace />fm);
										}
									</c:otherwise>
								</c:choose>
							},
							icon: 'circle-minus',
							label: '<liferay-ui:message key="delete" />'
						}
					]
				}
			).render();

			buttonRow.setData('layoutToolbar', layoutToolbar);
		</aui:script>
	</c:if>

	<liferay-ui:form-navigator
		categoryNames="<%= _CATEGORY_NAMES %>"
		categorySections="<%= categorySections %>"
		jspPath="/html/portlet/layouts_admin/layout/"
	/>
</aui:form>

<liferay-util:html-top>
	<liferay-util:include page="/html/portlet/layouts_admin/add_layout.jsp" />
</liferay-util:html-top>

<aui:script>
	function <portlet:namespace />saveLayout(action) {
		if (action) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = action;
		}
		else {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'update';
		}

		document.<portlet:namespace />fm.<portlet:namespace />redirect.value += Liferay.Util.getHistoryParam('<portlet:namespace />');;

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<%!
private static String[] _CATEGORY_NAMES = {""};
%>