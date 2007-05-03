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

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

Group group = (Group)request.getAttribute(WebKeys.GROUP);

long groupId = BeanParamUtil.getLong(group, request, "groupId");

String type = BeanParamUtil.getString(group, request, "type");
String friendlyURL = BeanParamUtil.getString(group, request, "friendlyURL");
%>

<script type="text/javascript">
	function <portlet:namespace />saveGroup() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= group == null ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/communities/edit_community" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveGroup(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>" />
<input name="<portlet:namespace />groupId" type="hidden" value="<%= groupId %>" />
<input name="<portlet:namespace />friendlyURL" type="hidden" value="<%= friendlyURL %>" />

<liferay-ui:tabs names="community" />

<liferay-ui:error exception="<%= DuplicateGroupException.class %>" message="please-enter-a-unique-name" />
<liferay-ui:error exception="<%= GroupNameException.class %>" message="please-enter-a-valid-name" />
<liferay-ui:error exception="<%= RequiredGroupException.class %>" message="old-group-name-is-a-required-system-group" />

<table class="liferay-table">
<tr>
	<td>
		<bean:message key="name" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= Group.class %>" bean="<%= group %>" field="name" />
	</td>
</tr>
<tr>
	<td>
		<bean:message key="description" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= Group.class %>" bean="<%= group %>" field="description" />
	</td>
</tr>
<tr>
	<td>
		<bean:message key="open" />
	</td>
	<td>
		<input <%= type.equals(GroupImpl.TYPE_COMMUNITY_OPEN) ? "checked" : "" %> name="<portlet:namespace />type" value="<%= GroupImpl.TYPE_COMMUNITY_OPEN %>" type="checkbox">
	</td>
</tr>
<tr>
	<td>
		<bean:message key="active" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= Group.class %>" bean="<%= group %>" field="active" defaultValue="<%= Boolean.TRUE %>" />
	</td>
</tr>
</table>

<br />

<input type="submit" value="<bean:message key="save" />" />

<input type="button" value="<bean:message key="cancel" />" onClick="self.location = '<%= redirect %>';" />

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />name.focus();
</script>