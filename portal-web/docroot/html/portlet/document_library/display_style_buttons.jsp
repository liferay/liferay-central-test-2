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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId");

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PortletKeys.DOCUMENT_LIBRARY, "display-style", "icon");
}

String keywords = ParamUtil.getString(request, "keywords");
%>

<aui:script use="aui-dialog,aui-dialog-iframe">
	var buttonRow = A.one('#<portlet:namespace />displayStyleToolbar');

	var displayStyleToolbar = new A.Toolbar(
		{
			activeState: true,
			boundingBox: buttonRow,
			children: [
				{

					<portlet:resourceURL var="iconDisplayStyle">
						<c:choose>
							<c:when test="<%= Validator.isNull(keywords) %>">
								<portlet:param name="struts_action" value="/document_library/view" />
								<portlet:param name="viewDisplayStyleButtons" value="<%= Boolean.TRUE.toString() %>" />
								<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
							</c:when>
							<c:otherwise>
								<portlet:param name="struts_action" value="/document_library/search" />
								<portlet:param name="keywords" value="<%= keywords %>" />
							</c:otherwise>
						</c:choose>

						<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
						<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryTypeId) %>" />
						<portlet:param name="displayStyle" value="icon" />
						<portlet:param name="saveDisplayStyle" value="<%= Boolean.TRUE.toString() %>" />
					</portlet:resourceURL>

					handler: function(event) {
						updateDisplayStyle('<%= iconDisplayStyle.toString() %>', 0);
					},
					icon: 'display-icon'
				},
				{

					<portlet:resourceURL var="descriptiveDisplayStyle">
						<c:choose>
							<c:when test="<%= Validator.isNull(keywords) %>">
								<portlet:param name="struts_action" value="/document_library/view" />
								<portlet:param name="viewDisplayStyleButtons" value="<%= Boolean.TRUE.toString() %>" />
								<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
						</c:when>
						<c:otherwise>
								<portlet:param name="struts_action" value="/document_library/search" />
								<portlet:param name="keywords" value="<%= keywords %>" />
							</c:otherwise>
						</c:choose>

						<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
						<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryTypeId) %>" />
						<portlet:param name="displayStyle" value="descriptive" />
						<portlet:param name="saveDisplayStyle" value="<%= Boolean.TRUE.toString() %>" />
					</portlet:resourceURL>

					handler: function(event) {
						updateDisplayStyle('<%= descriptiveDisplayStyle.toString() %>', 1);
					},
					icon: 'display-descriptive'
				},
				{

					<portlet:resourceURL var="listDisplayStyle">
						<c:choose>
							<c:when test="<%= Validator.isNull(keywords) %>">
								<portlet:param name="struts_action" value="/document_library/view" />
								<portlet:param name="viewDisplayStyleButtons" value="<%= Boolean.TRUE.toString() %>" />
								<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
							</c:when>
							<c:otherwise>
								<portlet:param name="struts_action" value="/document_library/search" />
								<portlet:param name="keywords" value="<%= keywords %>" />
							</c:otherwise>
						</c:choose>

						<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
						<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryTypeId) %>" />
						<portlet:param name="displayStyle" value="list" />
						<portlet:param name="saveDisplayStyle" value="<%= Boolean.TRUE.toString() %>" />
					</portlet:resourceURL>

					handler: function(event) {
						updateDisplayStyle('<%= listDisplayStyle.toString() %>', 2);
					},
					icon: 'display-list'
				}
			]
		}
	).render();

	<c:choose>
		<c:when test='<%= displayStyle.equals("icon") %>'>
			var index = 0;
		</c:when>
		<c:when test='<%= displayStyle.equals("descriptive") %>'>
			var index = 1;
		</c:when>
		<c:when test='<%= displayStyle.equals("list") %>'>
			var index = 2;
		</c:when>
	</c:choose>

	displayStyleToolbar.item(index).StateInteraction.set('active', true);

	buttonRow.setData('displayStyleToolbar', displayStyleToolbar);

	var entriesContainer = A.one('#<portlet:namespace />documentContainer');

	var updateDisplayStyle = function(url, index) {
		entriesContainer.plug(A.LoadingMask);

		entriesContainer.loadingmask.toggle();

		A.io.request(
			url,
			{
				after: {
					success: function(event, id, obj) {
						entriesContainer.unplug(A.LoadingMask);

						A.one('#<portlet:namespace />displayStyleToolbar').empty();

						displayStyleToolbar.item(0).StateInteraction.set('active', false);
						displayStyleToolbar.item(1).StateInteraction.set('active', false);
						displayStyleToolbar.item(2).StateInteraction.set('active', false);

						displayStyleToolbar.item(index).StateInteraction.set('active', true);

						var responseData = this.get('responseData');

						var content = A.Node.create(responseData);

						var displayStyleButtonsContainer = A.one('#<portlet:namespace />displayStyleButtonsContainer');
						var displayStyleButtons = content.one('#<portlet:namespace />displayStyleButtons');

						displayStyleButtonsContainer.plug(A.Plugin.ParseContent);
						displayStyleButtonsContainer.setContent(displayStyleButtons);

						var entries = content.one('#<portlet:namespace />entries');

						entriesContainer.setContent(entries);
					}
				}
			}
		);
	}
</aui:script>