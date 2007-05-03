<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/admin/init.jsp" %>

<%
	String redirect = ParamUtil.getString(request, "redirect");

	String pluginId = ParamUtil.getString(request, "pluginId");
	String pluginType = ParamUtil.getString(request, "pluginType");

	PluginSetting pluginSetting = PluginSettingLocalServiceUtil.getPluginSetting(company.getCompanyId(), pluginId, pluginType);
%>

<script type="text/javascript">
	function <portlet:namespace />savePluginSetting() {
		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/admin/edit_plugin_setting" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />savePortlet(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>" />
<input name="<portlet:namespace />pluginId" type="hidden" value="<%= pluginId %>" />
<input name="<portlet:namespace />pluginType" type="hidden" value="<%= pluginType %>" />

<liferay-ui:tabs names="plugin-setting" />

<table class="liferay-table">
<tr>
	<td>
		<bean:message key="id" />
	</td>
	<td>
		<%= pluginId %>
	</td>
</tr>
<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td>
		<bean:message key="active" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= PluginSetting.class %>" bean="<%= pluginSetting %>" field="active" />
	</td>
</tr>
</table>

<br />

<bean:message key="enter-one-role-name-per-line-a-user-must-belong-to-one-of-these-roles-in-order-to-add-this-plugin-to-a-page" />

<br /><br />

<textarea name="<portlet:namespace />roles" style="height: <%= ModelHintsDefaults.TEXTAREA_DISPLAY_HEIGHT %>px; width: <%= ModelHintsDefaults.TEXTAREA_DISPLAY_WIDTH %>px;"><%= StringUtil.merge(pluginSetting.getRolesArray(), "\n") %></textarea>

<br /><br />

<input type="submit" value="<bean:message key="save" />" />

<input type="button" value="<bean:message key="cancel" />" onClick="self.location = '<%= redirect %>';" />

</form>