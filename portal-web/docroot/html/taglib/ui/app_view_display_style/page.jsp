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

<%@ include file="/html/taglib/init.jsp" %>

<%
String displayStyle = (String)request.getAttribute("liferay-ui:app-view-display-style:displayStyle");
String[] displayViews = (String[])request.getAttribute("liferay-ui:app-view-display-style:displayStyles");
String eventName = (String)request.getAttribute("liferay-ui:app-view-display-style:eventName");
Map<String, String> requestParams = (Map<String, String>)request.getAttribute("liferay-ui:app-view-display-style:requestParams");
PortletURL displayStyleUrl = (PortletURL)request.getAttribute("liferay-ui:app-view-display-style:displayStyleUrl");
%>

<c:if test="<%= (displayViews.length > 1) && (displayStyleUrl != null) %>">
	<span class="display-style-buttons-container" id="<portlet:namespace />displayStyleButtonsContainer">
		<div class="display-style-buttons" id="<portlet:namespace />displayStyleButtons">
			<aui:nav-item anchorCssClass="btn btn-default" dropdown="<%= true %>" iconCssClass='<%= "icon-" + _getIcon(displayStyle) %>'>

				<%
				for (String dataStyle : displayViews) {
					if (displayStyleUrl != null) {
						displayStyleUrl.setParameter("displayStyle", dataStyle);
					}
				%>

					<aui:nav-item
						href='<%= (displayStyleUrl == null) ? "javascript:;" : displayStyleUrl.toString() %>'
						iconCssClass='<%= "icon-" + _getIcon(dataStyle) %>'
						label="<%= dataStyle %>"
					/>

				<%
				}
				%>

			</aui:nav-item>
		</div>
	</span>
</c:if>

<c:if test="<%= (displayViews.length > 1) && (displayStyleUrl == null) %>">
	<span class="display-style-buttons-container" id="<portlet:namespace />displayStyleButtonsContainer">
		<div class="display-style-buttons" id="<portlet:namespace />displayStyleButtons">
			<aui:nav-item anchorCssClass="btn btn-default" dropdown="<%= true %>" iconCssClass='<%= "icon-" + _getIcon(displayStyle) %>'>

				<%
				for (int i = 0; i < displayViews.length; i++) {
					String dataStyle = displayViews[i];

					Map<String, Object> data = new HashMap<String, Object>();

					data.put("displayStyle", dataStyle);
				%>

					<aui:nav-item
						anchorData="<%= data %>"
						href="javascript:;"
						iconCssClass='<%= "icon-" + _getIcon(dataStyle) %>'
						label="<%= dataStyle %>"
					/>

				<%
				}
				%>

			</aui:nav-item>
		</div>
	</span>

	<aui:script use="aui-base">
		function changeDisplayStyle(displayStyle) {
			var config = {};

			<%
			if (requestParams != null) {
				Set<String> requestParamNames = requestParams.keySet();

				for (String requestParamName : requestParamNames) {
					String requestParamValue = requestParams.get(requestParamName);
			%>

					config['<portlet:namespace /><%= requestParamName %>'] = '<%= HtmlUtil.escapeJS(requestParamValue) %>';

			<%
				}
			}
			%>

			config['<portlet:namespace />displayStyle'] = displayStyle;
			config['<portlet:namespace />saveDisplayStyle'] = true;

			Liferay.fire(
				'<portlet:namespace />dataRequest',
				{
					requestParams: config,
					src: Liferay.DL_ENTRIES_PAGINATOR
				}
			);
		}

		var displayStyleButtonsMenu = A.one('#<portlet:namespace />displayStyleButtons .dropdown-menu');

		if (displayStyleButtonsMenu) {
			displayStyleButtonsMenu.delegate(
				'click',
				function(event) {
					var displayStyle = event.currentTarget.attr('data-displayStyle');

					if (<%= requestParams != null %>) {
						changeDisplayStyle(displayStyle);
					}
					else if (<%= eventName != null %>) {
						Liferay.fire(
							'<%= eventName %>',
							{
								displayStyle: displayStyle
							}
						);
					}
				},
				'li > a'
			);
		}
	</aui:script>
</c:if>

<%!
private String _getIcon(String displayStyle) {
	String displayStyleIcon = displayStyle;

	if (displayStyle.equals("descriptive")) {
		displayStyleIcon = "th-list";
	}
	else if (displayStyle.equals("icon")) {
		displayStyleIcon = "th-large";
	}
	else if (displayStyle.equals("list")) {
		displayStyleIcon = "align-justify";
	}

	return displayStyleIcon;
}
%>