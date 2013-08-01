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

<%@ include file="/html/taglib/init.jsp" %>

<%
String portletURL = (String)request.getAttribute("liferay-ui:trash-undo:portletURL");
String redirect = GetterUtil.getString((String)request.getAttribute("liferay-ui:trash-undo:redirect"), currentURL);

if (SessionMessages.contains(portletRequest, portletDisplay.getId() + SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA)) {
	Map<String, String[]> data = (HashMap<String, String[]>)SessionMessages.get(portletRequest, portletDisplay.getId() + SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA);

	if (data != null) {
		int trashedEntriesCount = 0;

		Set<String> keys = data.keySet();

		for (String key : keys) {
			String[] primaryKeys = data.get(key);

			trashedEntriesCount += primaryKeys.length;
		}
%>

		<div class="alert alert-success taglib-trash-undo">
			<aui:form action="<%= portletURL %>" name="undoForm">
				<liferay-util:buffer var="trashLink">
					<c:choose>
						<c:when test="<%= themeDisplay.isShowSiteAdministrationIcon() %>">
							<liferay-portlet:renderURL plid="<%= PortalUtil.getControlPanelPlid(company.getCompanyId()) %>" portletName="<%= PortletKeys.TRASH %>" varImpl="trashURL" windowState="<%= WindowState.NORMAL.toString() %>">
								<portlet:param name="struts_action" value="/trash/view" />
							</liferay-portlet:renderURL>

							<%
							String trashURLString = HttpUtil.setParameter(trashURL.toString(), "doAsGroupId", String.valueOf(themeDisplay.getScopeGroupId()));

							if (Validator.isNull(themeDisplay.getControlPanelCategory())) {
								trashURLString = HttpUtil.setParameter(trashURLString, "controlPanelCategory", "current_site");
							}
							%>

							<aui:a href="<%= trashURLString %>" label="the-recycle-bin" />
						</c:when>
						<c:otherwise>
							<liferay-ui:message key="the-recycle-bin" />
						</c:otherwise>
					</c:choose>
				</liferay-util:buffer>

				<liferay-ui:message arguments="<%= new Object[] {trashLink, trashedEntriesCount} %>" key='<%= trashedEntriesCount > 1 ? "x-items-were-moved-to-x" : "the-selected-item-was-moved-to-x" %>' />

				<a class="trash-undo-link" href="javascript:;" id="<%= namespace %>undo"><liferay-ui:message key="undo" /></a>

				<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

				<%
				for (String key : keys) {
					String[] primaryKeys = data.get(key);
				%>

					<aui:input name="<%= key %>" type="hidden" value="<%= StringUtil.merge(primaryKeys) %>" />

				<%
				}
				%>

				<aui:button cssClass="trash-undo-button" type="submit" value="undo" />
			</aui:form>
		</div>

		<aui:script use="aui-base">
			var undoLink = A.one('#<%= namespace %>undo');

			if (undoLink) {
				undoLink.on(
					'click',
					function(event) {
						submitForm(document.<%= namespace %>undoForm);
					}
				);
			}
		</aui:script>

<%
	}
}
%>