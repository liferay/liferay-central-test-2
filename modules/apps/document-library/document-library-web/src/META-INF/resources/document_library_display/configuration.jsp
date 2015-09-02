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

<%@ include file="/document_library_display/init.jsp" %>

<%
DLPortletInstanceSettingsHelper dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(dlDisplayRequestHelper);
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />
	<aui:input name="preferences--rootFolderId--" type="hidden" value="<%= rootFolderId %>" />
	<aui:input name="preferences--folderColumns--" type="hidden" />
	<aui:input name="preferences--fileEntryColumns--" type="hidden" />

	<liferay-ui:error key="rootFolderIdInvalid" message="please-enter-a-valid-root-folder" />

	<liferay-ui:panel-container extended="<%= true %>" id="documentLibrarySettingsPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="documentLibraryDisplay" persistState="<%= true %>" title="display-settings">
			<aui:input id="showActions" label="show-actions" name="preferences--showActions--" type="checkbox" value="<%= dlPortletInstanceSettings.isShowActions() %>" />

			<aui:input label="show-folder-menu" name="preferences--showFolderMenu--" type="checkbox" value="<%= dlPortletInstanceSettings.isShowFolderMenu() %>" />

			<aui:input label="show-navigation-links" name="preferences--showTabs--" type="checkbox" value="<%= dlPortletInstanceSettings.isShowTabs() %>" />

			<aui:input label="show-search" name="preferences--showFoldersSearch--" type="checkbox" value="<%= dlPortletInstanceSettings.isShowFoldersSearch() %>" />
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="documentLibraryFoldersListingPanel" persistState="<%= true %>" title="folders-listing">
			<aui:fieldset>
				<div class="form-group">
					<aui:input label="root-folder" name="rootFolderName" type="resource" value="<%= rootFolderName %>" />

					<aui:button name="openFolderSelectorButton" value="select" />

					<%
					String taglibRemoveFolder = "Liferay.Util.removeEntitySelection('rootFolderId', 'rootFolderName', this, '" + renderResponse.getNamespace() + "');";
					%>

					<aui:button disabled="<%= rootFolderId <= 0 %>" name="removeFolderButton" onClick="<%= taglibRemoveFolder %>" value="remove" />
				</div>

				<aui:input name="preferences--showSubfolders--" type="checkbox" value="<%= dlPortletInstanceSettings.isShowSubfolders() %>" />

				<aui:input name="preferences--foldersPerPage--" size="2" type="text" value="<%= dlPortletInstanceSettings.getFoldersPerPage() %>" />

				<aui:field-wrapper label="show-columns">
					<liferay-ui:input-move-boxes
						leftBoxName="currentFolderColumns"
						leftList="<%= dlPortletInstanceSettingsHelper.getCurrentFolderColumns() %>"
						leftReorder="true"
						leftTitle="current"
						rightBoxName="availableFolderColumns"
						rightList="<%= dlPortletInstanceSettingsHelper.getAvailableFolderColumns() %>"
						rightTitle="available"
					/>
				</aui:field-wrapper>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="documentLibraryDocumentsListingPanel" persistState="<%= true %>" title="documents-listing">
			<aui:fieldset>
				<aui:input label="documents-per-page" name="preferences--fileEntriesPerPage--" size="2" type="text" value="<%= dlPortletInstanceSettings.getFileEntriesPerPage() %>" />

				<aui:field-wrapper label="show-columns">
					<liferay-ui:input-move-boxes
						leftBoxName="currentFileEntryColumns"
						leftList="<%= dlPortletInstanceSettingsHelper.getCurrentFileEntryColumns() %>"
						leftReorder="true"
						leftTitle="current"
						rightBoxName="availableFileEntryColumns"
						rightList="<%= dlPortletInstanceSettingsHelper.getAvailableFileEntryColumns() %>"
						rightTitle="available"
					/>
				</aui:field-wrapper>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="documentLibraryDocumentsRatingsPanel" persistState="<%= true %>" title="ratings">
			<aui:input name="preferences--enableRatings--" type="checkbox" value="<%= dlPortletInstanceSettings.isEnableRatings() %>" />
			<aui:input name="preferences--enableCommentRatings--" type="checkbox" value="<%= dlPortletInstanceSettings.isEnableCommentRatings() %>" />
		</liferay-ui:panel>
	</liferay-ui:panel-container>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script sandbox="<%= true %>">
	$('#<portlet:namespace />openFolderSelectorButton').on(
		'click',
		function(event) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						destroyOnHide: true,
						modal: true,
						width: 600
					},
					id: '_<%= HtmlUtil.escapeJS(portletResource) %>_selectFolder',
					title: '<liferay-ui:message arguments="folder" key="select-x" />',

					<liferay-portlet:renderURL portletName="<%= portletResource %>" var="selectFolderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
						<portlet:param name="mvcRenderCommandName" value="/document_library/select_folder" />
						<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
						<portlet:param name="ignoreRootFolder" value="<%= Boolean.TRUE.toString() %>" />
					</liferay-portlet:renderURL>

					uri: '<%= selectFolderURL.toString() %>'
				},
				function(event) {
					var folderData = {
						idString: 'rootFolderId',
						idValue: event.folderid,
						nameString: 'rootFolderName',
						nameValue: event.foldername
					};

					Liferay.Util.selectFolder(folderData, '<portlet:namespace />');
				}
			);
		}
	);

	var showActionsInput = $('#<portlet:namespace />showActions');

	showActionsInput.on(
		'change',
		function(event) {
			var currentColumns = $('#<portlet:namespace />currentFileEntryColumns, #<portlet:namespace />currentFolderColumns');

			if (showActionsInput.prop('checked')) {
				currentColumns.append('<option value="action"><%= UnicodeLanguageUtil.get(request, "action") %></option>');
			}
			else {
				var allColumns = currentColumns.add('#<portlet:namespace />availableFileEntryColumns, #<portlet:namespace />availableFolderColumns');

				allColumns.find('option[value="action"]').remove();
			}
		}
	);
</aui:script>

<aui:script>
	function <portlet:namespace />saveConfiguration() {
		var Util = Liferay.Util;

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('folderColumns').val(Util.listSelect(form.fm('currentFolderColumns')));
		form.fm('fileEntryColumns').val(Util.listSelect(form.fm('currentFileEntryColumns')));

		submitForm(form);
	}
</aui:script>

<c:if test="<%= SessionMessages.contains(renderRequest, portletDisplay.getId() + SessionMessages.KEY_SUFFIX_UPDATED_CONFIGURATION) %>">
	<aui:script position="inline" use="aui-base">
		var valueMap = {};

		var foldersPerPageInput = A.one('#<portlet:namespace />foldersPerPage');

		if (foldersPerPageInput) {
			valueMap.delta1 = foldersPerPageInput.val();
		}

		var fileEntriesPerPageInput = A.one('#<portlet:namespace />fileEntriesPerPage');

		if (fileEntriesPerPageInput) {
			valueMap.delta2 = fileEntriesPerPageInput.val();
		}

		var portlet = Liferay.Util.getTop().AUI().one('#p_p_id<%= HtmlUtil.escapeJS(PortalUtil.getPortletNamespace(portletResource)) %>');

		portlet.refreshURL = portlet.refreshURL.replace(
			/(cur\d{1}|delta[12])(=|%3D)[^%&]+/g,
			function(match, param, equals) {
				return param + equals + (valueMap[param] || 1);
			}
		);
	</aui:script>
</c:if>