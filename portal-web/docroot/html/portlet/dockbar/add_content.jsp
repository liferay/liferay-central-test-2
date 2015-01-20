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

<%@ include file="/html/portlet/dockbar/init.jsp" %>

<%
int deltaDefault = GetterUtil.getInteger(SessionClicks.get(request, "liferay_addpanel_numitems", "10"));

int delta = ParamUtil.getInteger(request, "delta", deltaDefault);

String displayStyleDefault = GetterUtil.getString(SessionClicks.get(request, "liferay_addpanel_displaystyle", "descriptive"));

String displayStyle = ParamUtil.getString(request, "displayStyle", displayStyleDefault);
%>

<portlet:resourceURL var="updateContentListURL">
	<portlet:param name="struts_action" value="/dockbar/view" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:resourceURL>

<aui:form action="<%= updateContentListURL %>" name="addContentForm" onSubmit="event.preventDefault();">
	<div class="content-search">
		<aui:input cssClass="search-query" inlineField="<%= true %>" label="" name="searchContent" type="text" />

		<aui:select inlineField="<%= true %>" label="" name="numItems" title="number-of-items-to-display">

			<%
			for (int curDelta : PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) {
				if (curDelta > SearchContainer.MAX_DELTA) {
					continue;
				}
			%>

				<aui:option label="<%= curDelta %>" selected="<%= delta == curDelta %>" />

			<%
			}
			%>

		</aui:select>
	</div>

	<aui:nav-bar>
		<aui:nav collapsible="<%= true %>" cssClass="nav-display-style-buttons navbar-nav" icon="th-list" id="displayStyleButtons">
			<liferay-ui:app-view-display-style
				displayStyle="<%= displayStyle %>"
				displayStyles="<%= _DISPLAY_VIEWS %>"
				eventName='<%= "AddContent:changeDisplayStyle" %>'
			/>
		</aui:nav>

		<span class="add-content-button">

			<%
			PortletURL redirectURL = liferayPortletResponse.createLiferayPortletURL(themeDisplay.getPlid(), portletDisplay.getId(), PortletRequest.RENDER_PHASE, false);

			redirectURL.setParameter("struts_action", "/dockbar/add_content_redirect");
			redirectURL.setWindowState(LiferayWindowState.POP_UP);
			%>

			<liferay-ui:asset-add-button
				redirect="<%= redirectURL.toString() %>"
			/>
		</span>
	</aui:nav-bar>

	<div id="<portlet:namespace />entriesContainer">
		<liferay-util:include page="/html/portlet/dockbar/view_resources.jsp" />
	</div>
</aui:form>

<aui:script use="liferay-dockbar-add-content">
	var Dockbar = Liferay.Dockbar;

	var searchContent = A.one('#<portlet:namespace />searchContent');

	var addContent = new Dockbar.AddContent(
		{
			displayStyle: '<%= HtmlUtil.escapeJS(displayStyle) %>',
			focusItem: searchContent,
			inputNode: searchContent,
			namespace: '<portlet:namespace />',
			selected: !A.one('#<portlet:namespace />addContentForm').ancestor().hasClass('hide')
		}
	);

	if (Dockbar.PortletDragDrop) {
		addContent.plug(
			Dockbar.PortletDragDrop,
			{
				on: {
					dragEnd: function(event) {
						addContent.addPortlet(
							event.portletNode,
							{
								item: event.appendNode
							}
						);
					}
				},
				srcNode: '#<portlet:namespace />entriesContainer'
			}
		);
	}

	Liferay.component('<portlet:namespace />addContent', addContent);
</aui:script>

<%!
private static final String[] _DISPLAY_VIEWS = {"icon", "descriptive", "list"};
%>