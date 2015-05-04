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

<%@ include file="/init.jsp" %>

<%
long folderId = GetterUtil.getLong((String)liferayPortletRequest.getAttribute("view.jsp-folderId"));

PortletURL portletURL = liferayPortletResponse.createRenderURL();

portletURL.setParameter("folderId", String.valueOf(folderId));

String keywords = ParamUtil.getString(request, "keywords");

boolean advancedSearch = ParamUtil.getBoolean(liferayPortletRequest, ArticleDisplayTerms.ADVANCED_SEARCH);

boolean isSearch = Validator.isNull(keywords) && !advancedSearch;
%>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm1">
	<aui:nav-bar>
		<aui:nav collapsible="<%= true %>" cssClass="nav-display-style-buttons navbar-nav" icon="th-list" id="displayStyleButtons">

			<c:if test="<%= !isSearch %>">
				<liferay-util:include page="/display_style_buttons.jsp" servletContext="<%= application %>" />
			</c:if>
		</aui:nav>

		<aui:nav cssClass="navbar-nav" id="toolbarContainer">
			<aui:nav-item cssClass="hide" dropdown="<%= true %>" id="actionsButtonContainer" label="actions">

				<%
				String taglibURL = "javascript:Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: 'expireEntries'}); void(0);";
				%>

				<aui:nav-item href="<%= taglibURL %>" iconCssClass="icon-time" label="expire" />

				<%
				taglibURL = "javascript:Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: 'moveEntries'}); void(0);";
				%>

				<aui:nav-item href="<%= taglibURL %>" iconCssClass="icon-move" label="move" />

				<%
				taglibURL = "javascript:" + renderResponse.getNamespace() + "deleteEntries();";
				%>

				<aui:nav-item cssClass="item-remove" href="<%= taglibURL %>" iconCssClass='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "icon-trash" : "icon-remove" %>' label='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "move-to-the-recycle-bin" : "delete" %>' />
			</aui:nav-item>

			<liferay-util:include page="/add_button.jsp" servletContext="<%= application %>" />

			<c:if test="<%= !isSearch %>">
				<liferay-util:include page="/sort_button.jsp" servletContext="<%= application %>" />
			</c:if>

			<c:if test="<%= !user.isDefaultUser() %>">
				<aui:nav-item dropdown="<%= true %>" label="manage">

					<%
					String taglibURL = "javascript:" + renderResponse.getNamespace() + "openStructuresView()";
					%>

					<aui:nav-item href="<%= taglibURL %>" iconCssClass="icon-th-large" label="structures" />

					<%
					taglibURL = "javascript:" + renderResponse.getNamespace() + "openTemplatesView()";
					%>

					<aui:nav-item href="<%= taglibURL %>" iconCssClass="icon-list-alt" label="templates" />

					<%
					taglibURL = "javascript:" + renderResponse.getNamespace() + "openFeedsView()";
					%>

					<c:if test="<%= PortalUtil.isRSSFeedsEnabled() %>">
						<aui:nav-item href="<%= taglibURL %>" iconCssClass="icon-rss" label="feeds" />
					</c:if>
				</aui:nav-item>
			</c:if>
		</aui:nav>

		<aui:nav-bar-search>
			<liferay-util:include page="/article_search.jsp" servletContext="<%= application %>" />
		</aui:nav-bar-search>
	</aui:nav-bar>
</aui:form>

<aui:script>
	<c:if test="<%= PortalUtil.isRSSFeedsEnabled() %>">
		function <portlet:namespace />openFeedsView() {
			Liferay.Util.openWindow(
				{
					id: '<portlet:namespace />openFeedsView',
					title: '<%= UnicodeLanguageUtil.get(request, "feeds") %>',
					uri: '<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/view_feeds.jsp" /></liferay-portlet:renderURL>'
				}
			);
		}
	</c:if>

	<%
	Portlet portlet = PortletLocalServiceUtil.getPortletById(portletDisplay.getId());
	%>

	function <portlet:namespace />deleteEntries() {
		if (<%= TrashUtil.isTrashEnabled(scopeGroupId) %> || confirm(' <%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			Liferay.fire(
				'<%= renderResponse.getNamespace() %>editEntry',
				{
					action: '<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "moveEntriesToTrash" : "deleteEntries" %>'
				}
			);
		}
	}

	function <portlet:namespace />openStructuresView() {
		Liferay.Util.openDDMPortlet(
			{
				basePortletURL: '<%= PortletURLFactoryUtil.create(request, PortletKeys.DYNAMIC_DATA_MAPPING, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>',
				dialog: {
					destroyOnHide: true,
					on: {
						visibleChange: function(event) {
							if (!event.newVal) {
								Liferay.Portlet.refresh('#p_p_id_' + '<%= portletDisplay.getId() %>' + '_');
							}
						}
					}
				},
				groupId: <%= scopeGroupId %>,
				refererPortletName: '<%= JournalPortletKeys.JOURNAL %>',
				refererWebDAVToken: '<%= portlet.getWebDAVStorageToken() %>',
				showAncestorScopes: true,
				showManageTemplates: true,
				title: '<%= UnicodeLanguageUtil.get(request, "structures") %>'
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
				refererPortletName: '<%= JournalPortletKeys.JOURNAL %>',
				refererWebDAVToken: '<%= portlet.getWebDAVStorageToken() %>',
				showAncestorScopes: true,
				showHeader: false,
				resourceClassNameId: '<%= PortalUtil.getClassNameId(JournalArticle.class) %>',
				struts_action: '/dynamic_data_mapping/view_template',
				title: '<%= UnicodeLanguageUtil.get(request, "templates") %>'
			}
		);
	}
</aui:script>