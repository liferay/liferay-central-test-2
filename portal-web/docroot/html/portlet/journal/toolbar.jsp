<%--
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
--%>

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String strutsAction = ParamUtil.getString(request, "struts_action");
%>

<aui:nav-bar>
	<aui:nav>
		<aui:nav-item cssClass="hide" dropdown="<%= true %>" id="actionsButtonContainer" label="actions">

			<%
			String taglibURL = "javascript:" + renderResponse.getNamespace() + "deleteEntries();";
			%>

			<aui:nav-item href="<%= taglibURL %>" iconClass='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "icon-trash" : "icon-remove" %>' label='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "move-to-the-recycle-bin" : "delete" %>' />

			<%
			taglibURL = "javascript:Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.EXPIRE + "'});";
			%>

			<aui:nav-item href="<%= taglibURL %>" label="expire" />

			<%
			taglibURL = "javascript:Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.MOVE + "'});";
			%>

			<aui:nav-item href="<%= taglibURL %>" label="move" />
		</aui:nav-item>

		<liferay-util:include page="/html/portlet/journal/add_button.jsp" />

		<liferay-util:include page="/html/portlet/journal/sort_button.jsp" />

		<c:if test="<%= !user.isDefaultUser() %>">
			<aui:nav-item dropdown="<%= true %>" label="manage">

				<%
				String taglibURL = "javascript:" + renderResponse.getNamespace() + "openStructuresView()";
				%>

				<aui:nav-item href="<%= taglibURL %>" label="structures" />

				<%
				taglibURL = "javascript:" + renderResponse.getNamespace() + "openTemplatesView()";
				%>

				<aui:nav-item href="<%= taglibURL %>" label="templates" />

				<%
				taglibURL = "javascript:" + renderResponse.getNamespace() + "openFeedsView()";
				%>

				<c:if test="<%= PortalUtil.isRSSFeedsEnabled() %>">
					<aui:nav-item href="<%= taglibURL %>" label="feeds" />
				</c:if>
			</aui:nav-item>
		</c:if>
	</aui:nav>

	<div class="pull-right">
		<span class="pull-left display-style-buttons-container" id="<portlet:namespace />displayStyleButtonsContainer">
			<c:if test='<%= !strutsAction.equals("/journal/search") %>'>
				<liferay-util:include page="/html/portlet/journal/display_style_buttons.jsp" />
			</c:if>
		</span>

		<aui:nav-bar-search file="/html/portlet/journal/article_search.jsp" />
	</div>
</aui:nav-bar>

<aui:script>
	<c:if test="<%= PortalUtil.isRSSFeedsEnabled() %>">
		function <portlet:namespace />openFeedsView() {
			Liferay.Util.openWindow(
				{
					id: '<portlet:namespace />openFeedsView',
					title: '<%= UnicodeLanguageUtil.get(pageContext, "feeds") %>',
					uri: '<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/journal/view_feeds" /></liferay-portlet:renderURL>'
				}
			);
		}
	</c:if>

	<%
	Portlet portlet = PortletLocalServiceUtil.getPortletById(portletDisplay.getId());
	%>

	function <portlet:namespace />deleteEntries() {
		if (<%= TrashUtil.isTrashEnabled(scopeGroupId) %> || confirm(' <%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			Liferay.fire(
				'<%= renderResponse.getNamespace() %>editEntry',
				{
					action: '<%= TrashUtil.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>'
				}
			);
		}
	}

	function <portlet:namespace />openStructuresView() {
		Liferay.Util.openDDMPortlet(
			{
				basePortletURL: '<%= PortletURLFactoryUtil.create(request, PortletKeys.DYNAMIC_DATA_MAPPING, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>',
				dialog: {
					destroyOnHide: true
				},
				refererPortletName: '<%= PortletKeys.JOURNAL %>',
				refererWebDAVToken: '<%= portlet.getWebDAVStorageToken() %>',
				showGlobalScope: 'false',
				showManageTemplates: 'true',
				title: '<%= UnicodeLanguageUtil.get(pageContext, "structures") %>'
			}
		);
	}

	function <portlet:namespace />openTemplatesView() {
		Liferay.Util.openDDMPortlet(
			{
				basePortletURL: '<%= PortletURLFactoryUtil.create(request, PortletKeys.DYNAMIC_DATA_MAPPING, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>',
				classNameId: '<%= PortalUtil.getClassNameId(DDMStructure.class) %>',
				dialog: {
					destroyOnHide: true
				},
				groupId: <%= scopeGroupId %>,
				refererPortletName: '<%= PortletKeys.JOURNAL %>',
				refererWebDAVToken: '<%= portlet.getWebDAVStorageToken() %>',
				struts_action: '/dynamic_data_mapping/view_template',
				title: '<%= UnicodeLanguageUtil.get(pageContext, "templates") %>'
			}
		);
	}
</aui:script>