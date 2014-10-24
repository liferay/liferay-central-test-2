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

<%@ include file="/html/portlet/journal_articles/init.jsp" %>

<%
int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

groupId = ParamUtil.getLong(request, "groupId", groupId);
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="true" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm1">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value='<%= configurationRenderURL + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur %>' />
	<aui:input name="preferences--ddmStructureKey--" type="hidden" value="<%= ddmStructureKey %>" />

	<liferay-ui:panel-container extended="<%= true %>" id="journalArticlesSettingsPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="journalArticlesFilterPanel" persistState="<%= true %>" title="filter">
			<aui:fieldset>
				<aui:select label="site" name="preferences--groupId--">
					<aui:option label="global" selected="<%= groupId == themeDisplay.getCompanyGroupId() %>" value="<%= themeDisplay.getCompanyGroupId() %>" />

					<%
					List<Group> mySiteGroups = user.getMySiteGroups();

					for (int i = 0; i < mySiteGroups.size(); i++) {
						Group group = mySiteGroups.get(i);

						String groupDescriptiveName = HtmlUtil.escape(group.getDescriptiveName(locale));

						if (group.isUser()) {
							groupDescriptiveName = LanguageUtil.get(request, "my-site");
						}
					%>

						<aui:option label="<%= groupDescriptiveName %>" selected="<%= groupId == group.getGroupId() %>" value="<%= group.getGroupId() %>" />

					<%
					}
					%>

				</aui:select>

				<%
				String ddmStructureName = StringPool.BLANK;
				String ddmStructureDescription = StringPool.BLANK;

				if (ddmStructure != null) {
					ddmStructureName = HtmlUtil.escape(ddmStructure.getName(locale));
					ddmStructureDescription = HtmlUtil.escape(ddmStructure.getDescription(locale));
				}
				else {
					ddmStructureName = LanguageUtil.get(request, "any");
				}

				if (Validator.isNotNull(ddmStructureDescription)) {
					ddmStructureName = ddmStructureName + " (" + ddmStructureDescription+ ")";
				}
				%>

				<div class="form-group">
					<aui:input name="structure" type="resource" value="<%= ddmStructureName %>" />

					<aui:button onClick='<%= renderResponse.getNamespace() + "openStructureSelector();" %>' value="select" />

					<aui:button name="removeStructureButton" onClick='<%= renderResponse.getNamespace() + "removeStructure();" %>' value="remove" />
				</div>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="journalArticlesDisplaySettings" persistState="<%= true %>" title="display-settings">
			<aui:fieldset>
				<aui:select label="display-url" name="preferences--pageUrl--" value="<%= pageUrl %>">
					<aui:option label="maximized" />
					<aui:option label="normal" />
					<aui:option label="pop-up" />
					<aui:option label="view-in-context" value="viewInContext" />
				</aui:select>

				<aui:select label="display-per-page" name="preferences--pageDelta--">

					<%
					for (int pageDeltaValue : PropsValues.JOURNAL_ARTICLES_PAGE_DELTA_VALUES) {
					%>

						<aui:option label="<%= pageDeltaValue %>" selected="<%= pageDelta == pageDeltaValue %>" />

					<%
					}
					%>

				</aui:select>

				<aui:field-wrapper label="order-by-column">
					<aui:select inlineField="<%= true %>" label="" name="preferences--orderByCol--" title="order-by-column" value="<%= orderByCol %>">
						<aui:option label="display-date" />
						<aui:option label="create-date" />
						<aui:option label="modified-date" />
						<aui:option label="title" />
						<aui:option label="id" />
					</aui:select>

					<aui:select label="" name="preferences--orderByType--" title="order-by-type" value="<%= orderByType %>">
						<aui:option label="ascending" value="asc" />
						<aui:option label="descending" value="desc" />
					</aui:select>
				</aui:field-wrapper>
			</aui:fieldset>
		</liferay-ui:panel>
	</liferay-ui:panel-container>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />openStructureSelector() {
		Liferay.Util.openDDMPortlet(
			{
				basePortletURL: '<%= PortletURLFactoryUtil.create(request, PortletKeys.DYNAMIC_DATA_MAPPING, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>',
				classPK: <%= (ddmStructure != null) ? ddmStructure.getPrimaryKey() : 0 %>,
				dialog: {
					destroyOnHide: true
				},
				eventName: '<portlet:namespace />selectStructure',
				groupId: <%= groupId %>,
				refererPortletName: '<%= PortletKeys.JOURNAL %>',
				showAncestorScopes: true,
				struts_action: '/dynamic_data_mapping/select_structure',
				title: '<%= UnicodeLanguageUtil.get(request, "structures") %>'
			},
			function(event) {
				var A = AUI();

				document.<portlet:namespace />fm1.<portlet:namespace />ddmStructureKey.value = event.ddmstructurekey;

				A.one('#<portlet:namespace />structure').val(event.name + ' (' + event.ddmstructureid + ')');
			}
		);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />removeStructure',
		function() {
			var A = AUI();

			document.<portlet:namespace />fm1.<portlet:namespace />ddmStructureKey.value = '';

			A.one('#<portlet:namespace />structure').val('<%= UnicodeLanguageUtil.get(request, "any") %>');
		},
		['aui-base']
	);
</aui:script>