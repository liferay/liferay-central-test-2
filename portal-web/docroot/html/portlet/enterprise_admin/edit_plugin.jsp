<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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