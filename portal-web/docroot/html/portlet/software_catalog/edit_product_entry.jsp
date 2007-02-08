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

SCProductEntry productEntry = (SCProductEntry) request.getAttribute(WebKeys.SOFTWARE_CATALOG_PRODUCT_ENTRY);

long productEntryId = BeanParamUtil.getLong(productEntry, request, "productEntryId");

String type = BeanParamUtil.getString(productEntry, request, "type");

Set licenseIds = CollectionFactory.getHashSet();

if ((productEntry != null) && (request.getParameterValues("licenses") == null)) {
	Iterator itr = productEntry.getLicenses().iterator();

	while (itr.hasNext()) {
		SCLicense license = (SCLicense) itr.next();

		licenseIds.add(new Long(license.getLicenseId()));
	}
}
else {
	long[] licenses = ParamUtil.getLongValues(request, "licenses");

	for (int i = 0; i < licenses.length; i++) {
		licenseIds.add(new Long(licenses[i]));
	}
}
%>

<script type="text/javascript">
	function <portlet:namespace />saveProductEntry() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= productEntry == null ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}

</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/software_catalog/edit_product_entry" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveProductEntry(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>">
<input name="<portlet:namespace />productEntryId" type="hidden" value="<%= productEntryId %>">

<liferay-ui:tabs names="product" />

<table border="0" cellpadding="0" cellspacing="0">
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "name") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<liferay-ui:input-field model="<%= SCProductEntry.class %>" bean="<%= productEntry %>" field="name" />
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "type") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<select name="<portlet:namespace/>type">
			<option <%= type.equals("portlet") ? "selected" : "" %> value="portlet"><%= LanguageUtil.get(pageContext, "portlet") %></option>
			<option <%= type.equals("theme") ? "selected" : "" %> value="theme"><%= LanguageUtil.get(pageContext, "theme") %></option>
			<option <%= type.equals("layout") ? "selected" : "" %> value="layout"><%= LanguageUtil.get(pageContext, "layout") %></option>
			<option <%= type.equals("extension") ? "selected" : "" %> value="extension"><%= LanguageUtil.get(pageContext, "extension") %></option>
		</select>
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "licenses") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<select name="<portlet:namespace/>licenses" multiple="true">
			<optgroup label="<%= LanguageUtil.get(pageContext, "recommended-licenses") %>">

				<%
				Iterator itr = SCLicenseLocalServiceUtil.getLicenses(true, true).iterator();

				while (itr.hasNext()) {
					SCLicense license = (SCLicense) itr.next();
				%>

					<option <%= licenseIds.contains(new Long(license.getLicenseId())) ? "selected" : "" %> value="<%= license.getPrimaryKey() %>"><%= license.getName() %></option>

				<%
				}
				%>

			</optgroup>

			<optgroup label="<%= LanguageUtil.get(pageContext, "other-licenses") %>">

				<%
				itr = SCLicenseLocalServiceUtil.getLicenses(true, false).iterator();

				while (itr.hasNext()) {
					SCLicense license = (SCLicense) itr.next();
				%>

					<option <%= licenseIds.contains(new Long(license.getLicenseId())) ? "selected" : "" %> value="<%= license.getPrimaryKey() %>"><%= license.getName() %></option>

				<%
				}
				%>

			</optgroup>
		</select>
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "page-url") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<liferay-ui:input-field model="<%= SCProductEntry.class %>" bean="<%= productEntry %>" field="pageURL" />
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "short-description") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<liferay-ui:input-field model="<%= SCProductEntry.class %>" bean="<%= productEntry %>" field="shortDescription" />
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "long-description") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<liferay-ui:input-field model="<%= SCProductEntry.class %>" bean="<%= productEntry %>" field="longDescription" />
	</td>
</tr>

<c:if test="<%= productEntry == null %>">
	<tr>
		<td colspan="3">
			<br>
		</td>
	</tr>
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "permissions") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<liferay-ui:input-permissions
				modelName="<%= SCProductEntry.class.getName() %>"
			/>
		</td>
	</tr>
</c:if>

</table>

<br>

<liferay-ui:tabs names="plugin-repository" />

<table border="0" cellpadding="0" cellspacing="0">
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "group-id") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<liferay-ui:input-field model="<%= SCProductEntry.class %>" bean="<%= productEntry %>" field="repoGroupId" />
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "artifact-id") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<liferay-ui:input-field model="<%= SCProductEntry.class %>" bean="<%= productEntry %>" field="repoArtifactId" />
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