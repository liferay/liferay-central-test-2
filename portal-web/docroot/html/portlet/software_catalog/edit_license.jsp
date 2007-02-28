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

<%@ include file="/html/portlet/software_catalog/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

SCLicense license = (SCLicense)request.getAttribute(WebKeys.SOFTWARE_CATALOG_LICENSE);

long licenseId = BeanParamUtil.getLong(license, request, "licenseId");

boolean openSource = BeanParamUtil.getBoolean(license, request, "openSource", false);
boolean active = BeanParamUtil.getBoolean(license, request, "active", false);
boolean recommended = BeanParamUtil.getBoolean(license, request, "recommended", false);
%>

<script type="text/javascript">
	function <portlet:namespace />saveLicense() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= license == null ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}

</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/software_catalog/edit_license" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveLicense(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>">
<input name="<portlet:namespace />licenseId" type="hidden" value="<%= licenseId %>">

<liferay-ui:tabs names="license" />

<liferay-ui:error exception="<%= LicenseNameException.class %>" message="please-select-at-least-one-framework-version" />

<table border="0" cellpadding="0" cellspacing="0">

<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "name") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<liferay-ui:input-field model="<%= SCLicense.class %>" bean="<%= license %>" field="name" />
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "url") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<liferay-ui:input-field model="<%= SCLicense.class %>" bean="<%= license %>" field="url" />
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "open-source") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<liferay-ui:input-checkbox param="openSource" defaultValue="<%= openSource %>" />
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "active") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<liferay-ui:input-checkbox param="active" defaultValue="<%= active %>" />
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "recommended") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<liferay-ui:input-checkbox param="recommended" defaultValue="<%= recommended %>" />
	</td>
</tr>
</table>

<br>

<input class="portlet-form-button" type="submit" value='<%= LanguageUtil.get(pageContext, "save") %>'>

<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="self.location = '<%= redirect %>';">

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />name.focus();
</script>