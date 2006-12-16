<%
/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/software_repository/init.jsp" %>

<%
	String redirect = ParamUtil.getString(request, "redirect");

	SRProductEntry productEntry = (SRProductEntry) request.getAttribute(WebKeys.SOFTWARE_REPOSITORY_PRODUCT_ENTRY);

	long productEntryId = BeanParamUtil.getLong(productEntry, request, "productEntryId");
%>

<script type="text/javascript">
	function <portlet:namespace />saveEntry() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= productEntry == null ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}

</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/software_repository/edit_product_entry" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveEntry(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>">
<input name="<portlet:namespace />productEntryId" type="hidden" value="<%= productEntryId %>">

<liferay-ui:tabs names="product-entry" />

<%--<liferay-ui:error exception="<%= EntryURLException.class %>" message="please-enter-a-valid-url" />--%>
<fieldset>
	<legend><%=LanguageUtil.get(pageContext, "main-fields")%></legend>
	<table border="0" cellpadding="0" cellspacing="0">

	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "name") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<liferay-ui:input-field model="<%= SRProductEntry.class %>" bean="<%= productEntry %>" field="name" />
		</td>
	</tr>
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "type") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<select name="<portlet:namespace/>type">
				<option <%= ((productEntry != null) && productEntry.getType().equals("portlet")) ? "selected" : "" %> value="portlet"><%= LanguageUtil.get(pageContext, "portlet") %></option>
				<option <%= ((productEntry != null) && productEntry.getType().equals("theme")) ? "selected" : "" %> value="theme"><%= LanguageUtil.get(pageContext, "theme") %></option>
				<option <%= ((productEntry != null) && productEntry.getType().equals("layout")) ? "selected" : "" %> value="layout"><%= LanguageUtil.get(pageContext, "layout") %></option>
				<option <%= ((productEntry != null) && productEntry.getType().equals("extension")) ? "selected" : "" %> value="extension"><%= LanguageUtil.get(pageContext, "extension") %></option>
			</select>
		</td>
	</tr>
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "license") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<select name="<portlet:namespace/>licenses" multiple="true">
				<optgroup label="<%=LanguageUtil.get(pageContext, "recommended-licenses")%>">
				<%
					Iterator recommendedVersionsIt = SRLicenseServiceUtil.getLicenses(true, true).iterator();
					while (recommendedVersionsIt.hasNext()) {
						SRLicense version = (SRLicense) recommendedVersionsIt.next();
				%>
						<option <%= ((productEntry != null) && (productEntry.getLicenseIds().contains(new Long(version.getLicenseId())))) ? "selected" : "" %>
							value="<%= Long.toString(version.getPrimaryKey()) %>"><%= version.getName() %></option>
				<%
					}
				%>
				</optgroup>
				<optgroup label="<%=LanguageUtil.get(pageContext, "other-licenses")%>">
				<%
					Iterator versionsIt = SRLicenseServiceUtil.getLicenses(true, false).iterator();
					while (versionsIt.hasNext()) {
						SRLicense version = (SRLicense) versionsIt.next();
				%>
					<option <%= ((productEntry != null) && (productEntry.getLicenseIds().contains(new Long(version.getLicenseId())))) ? "selected" : "" %>
							value="<%= Long.toString(version.getPrimaryKey()) %>"><%= version.getName() %></option>
				<%
					}
				%>
				</optgroup>
			</select>
		</td>
	</tr>
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "product-entry-page-url") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<liferay-ui:input-field model="<%= SRProductEntry.class %>" bean="<%= productEntry %>" field="pageURL" />
		</td>
	</tr>
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "short-description") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<liferay-ui:input-field model="<%= SRProductEntry.class %>" bean="<%= productEntry %>" field="shortDescription" />
		</td>
	</tr>
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "long-description") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<liferay-ui:input-field model="<%= SRProductEntry.class %>" bean="<%= productEntry %>" field="longDescription" />
		</td>
	</tr>

	</table>
</fieldset>
<fieldset>
	<legend><%=LanguageUtil.get(pageContext, "repository-fields")%></legend>
	<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "artifact-id") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<liferay-ui:input-field model="<%= SRProductEntry.class %>" bean="<%= productEntry %>" field="repoArtifactId" />
			</td>
		</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "group-id") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<liferay-ui:input-field model="<%= SRProductEntry.class %>" bean="<%= productEntry %>" field="repoGroupId" />
			</td>
		</tr>

	</table>
</fieldset>

<br>

<input class="portlet-form-button" type="submit" value='<%= LanguageUtil.get(pageContext, "save") %>'>

<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="self.location = '<%= redirect %>';">

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />name.focus();
</script>