<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
%>

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String moduleId = ParamUtil.getString(request, "moduleId");
String pluginId = ParamUtil.getString(request, "pluginId");
String pluginType = ParamUtil.getString(request, "pluginType");

PluginSetting pluginSetting = PluginSettingLocalServiceUtil.getPluginSetting(company.getCompanyId(), pluginId, pluginType);

boolean active = pluginSetting.isActive();
String[] rolesArray = pluginSetting.getRolesArray();

if (pluginType.equals(Plugin.TYPE_PORTLET)) {
	String portletId = pluginId;

	Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletId);

	active = portlet.isActive();
	rolesArray = portlet.getRolesArray();
}
%>

<portlet:actionURL var="editPluginURL">
	<portlet:param name="struts_action" value="/enterprise_admin/edit_plugin" />
</portlet:actionURL>

<aui:form action="<%= editPluginURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="pluginId" type="hidden" value="<%= pluginId %>" />
	<aui:input name="pluginType" type="hidden" value="<%= pluginType %>" />

	<liferay-ui:tabs
		names="plugin"
		backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
	/>

	<aui:fieldset>
		<aui:field-wrapper label="module-id">
			<%= moduleId %>
		</aui:field-wrapper>

		<aui:field-wrapper label="plugin-id">
			<%= HtmlUtil.escape(pluginId) %>
		</aui:field-wrapper>

		<aui:input disabled="<%= pluginId.equals(PortletKeys.ENTERPRISE_ADMIN) %>" inlineLabel="left" name="active" type="checkbox" value="<%= active %>" />

		<c:if test="<%= !pluginType.equals(Plugin.TYPE_PORTLET) %>">
			<aui:input cssClass="lfr-textarea-container" helpMessage="enter-one-role-name-per-line-a-user-must-belong-to-one-of-these-roles-in-order-to-add-this-plugin-to-a-page" name="roles" type="textarea" value='<%= StringUtil.merge(rolesArray, "\n") %>' />
		</c:if>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>