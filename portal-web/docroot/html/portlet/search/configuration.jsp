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

<%@ include file="/html/portlet/search/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2");
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-ui:tabs
		formName="fm"
		names="display-settings,spell-check-settings,other-settings"
		param="tabs2"
		refresh="<%= false %>"
	>
		<liferay-ui:section>
			<%@ include file="/html/portlet/search/display_settings.jspf" %>
		</liferay-ui:section>

		<liferay-ui:section>
			<%@ include file="/html/portlet/search/spell_check_settings.jspf" %>
		</liferay-ui:section>

		<liferay-ui:section>
			<%@ include file="/html/portlet/search/other_settings.jspf" %>
		</liferay-ui:section>
	</liferay-ui:tabs>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>