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

<%@ include file="/html/portlet/recent_bloggers/init.jsp" %>

<%
String organizationName = StringPool.BLANK;

Organization organization = null;

if (organizationId > 0) {
	organization = OrganizationLocalServiceUtil.getOrganization(organizationId);

	organizationName = organization.getName();
}
%>

<script type="text/javascript">
	function <portlet:namespace />removeOrganization() {
		document.<portlet:namespace />fm.<portlet:namespace />organizationId.value = "";

		var nameEl = document.getElementById("<portlet:namespace />organizationName");

		nameEl.innerHTML = "";

		document.getElementById("<portlet:namespace />removeOrganizationButton").disabled = true;
	}

	function <portlet:namespace />selectOrganization(organizationId, name) {
		document.<portlet:namespace />fm.<portlet:namespace />organizationId.value = organizationId;

		var nameEl = document.getElementById("<portlet:namespace />organizationName");

		nameEl.innerHTML = name + "&nbsp;";

		document.getElementById("<portlet:namespace />removeOrganizationButton").disabled = false;
	}
</script>

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
<input name="<portlet:namespace />organizationId" type="hidden" value="<%= organizationId %>" />

<table class="liferay-table">
<tr>
	<td>
		<liferay-ui:message key="organization" />
	</td>
	<td>
		<span id="<portlet:namespace />organizationName"><%= organizationName %></span>

		<input type="button" value="<liferay-ui:message key="select" />" onClick="var organizationWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/portlet_configuration/select_organization" /><portlet:param name="tabs1" value="<%= "organizations" %>" /><portlet:param name="type" value="<%= String.valueOf(OrganizationImpl.TYPE_REGULAR) %>" /></portlet:renderURL>', 'organization', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=no,status=no,toolbar=no,width=680'); void(''); organizationWindow.focus();" />

		<input <%= (organizationId <= 0) ? "disabled" : "" %> id="<portlet:namespace />removeOrganizationButton" type="button" value="<liferay-ui:message key="remove" />" onClick="<portlet:namespace />removeOrganization();">
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="display-style" />
	</td>
	<td>
		<select name="<portlet:namespace />displayStyle">
			<option <%= (displayStyle.equals("user-name-and-image")) ? "selected" : "" %> value="user-name-and-image"><liferay-ui:message key="user-name-and-image"/></option>
			<option <%= (displayStyle.equals("user-name")) ? "selected" : "" %> value="user-name"><liferay-ui:message key="user-name"/></option>
		</select>
	</td>
</tr>
</table>

<br />

<input type="button" value="<liferay-ui:message key="save" />" onClick="submitForm(document.<portlet:namespace />fm);" />

</form>