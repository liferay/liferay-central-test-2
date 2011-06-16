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

long documentTypeId = ParamUtil.getLong(request, "documentTypeId");

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PortletKeys.DOCUMENT_LIBRARY, "display-style", "icon");
}

String keywords = ParamUtil.getString(request, "keywords");
%>

<aui:script use="aui-dialog,aui-dialog-iframe">
	var buttonRow = A.one('#<portlet:namespace />displayStyleToolbar');

	function onButtonClick(displayStyle) {
		var config = {
			'<portlet:namespace />struts_action': '<%= Validator.isNull(keywords) ? "/document_library/view" : "/document_library/search" %>',
			'<portlet:namespace />displayStyle': displayStyle,
			'<portlet:namespace />documentTypeId': '<%= String.valueOf(documentTypeId) %>',
			'<portlet:namespace />folderId': '<%= String.valueOf(folderId) %>',
			'<portlet:namespace />saveDisplayStyle': <%= Boolean.TRUE.toString() %>
		};

		if (<%= Validator.isNull(keywords) %>) {
			config['<portlet:namespace />viewEntries'] = <%= Boolean.TRUE.toString() %>;
		}
		else {
			config['<portlet:namespace />keywords'] = '<%= keywords %>';
		}

		updateDisplayStyle(config);
	}

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
						<portlet:param name="documentTypeId" value="<%= String.valueOf(documentTypeId) %>" />
						<portlet:param name="displayStyle" value="icon" />
						<portlet:param name="saveDisplayStyle" value="<%= Boolean.TRUE.toString() %>" />
					</portlet:resourceURL>

					handler: function(event) {
						onButtonClick('icon');
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
						<portlet:param name="documentTypeId" value="<%= String.valueOf(documentTypeId) %>" />
						<portlet:param name="displayStyle" value="descriptive" />
						<portlet:param name="saveDisplayStyle" value="<%= Boolean.TRUE.toString() %>" />
					</portlet:resourceURL>

					handler: function(event) {
						onButtonClick('descriptive');
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
						<portlet:param name="documentTypeId" value="<%= String.valueOf(documentTypeId) %>" />
						<portlet:param name="displayStyle" value="list" />
						<portlet:param name="saveDisplayStyle" value="<%= Boolean.TRUE.toString() %>" />
					</portlet:resourceURL>

					handler: function(event) {
						onButtonClick('list');
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

	var documentLibraryContainer = A.one('#<portlet:namespace />documentLibraryContainer');

	var entriesContainer = A.one('#<portlet:namespace />documentContainer');

	Liferay.on(
		'<portlet:namespace />dataRetrieveSuccess',
		function(event) {
			var data = event.data;

			if (event.callbackParams) {
				var handler = event.callbackParams['<portlet:namespace />handler'];

				if (handler === 'changeDisplayStyle') {
					handleDisplayStyle(event);
				}
			}
		}
	);

	function handleDisplayStyle(event) {
		documentLibraryContainer.loadingmask.hide();

		var responseData = event.responseData;

		var content = A.Node.create(responseData);

		entriesContainer.setContent(content);
	}

	function updateDisplayStyle(config) {
		displayStyleToolbar.item(0).StateInteraction.set('active', false);
		displayStyleToolbar.item(1).StateInteraction.set('active', false);
		displayStyleToolbar.item(2).StateInteraction.set('active', false);

		if ( config['<portlet:namespace />displayStyle'] === 'icon') {
			index = 0;
		}
		else if ( config['<portlet:namespace />displayStyle'] === 'descriptive') {
			index = 1;
		}
		else if ( config['<portlet:namespace />displayStyle'] === 'list') {
			index = 2;
		}

		displayStyleToolbar.item(index).StateInteraction.set('active', true);

		Liferay.fire(
			'<portlet:namespace />dataRequest',
			{
				requestParams: config,
				callbackParams: {
					'<portlet:namespace />handler': 'changeDisplayStyle'
				}
			}
		);
	}
</aui:script>