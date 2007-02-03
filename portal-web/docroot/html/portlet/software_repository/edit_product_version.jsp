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

<%@ include file="/html/portlet/software_repository/init.jsp" %>

<%
	String redirect = ParamUtil.getString(request, "redirect");

	SRProductEntry productEntry = (SRProductEntry) request.getAttribute(WebKeys.SOFTWARE_REPOSITORY_PRODUCT_ENTRY);
	SRProductVersion productVersion = (SRProductVersion) request.getAttribute(WebKeys.SOFTWARE_REPOSITORY_PRODUCT_VERSION);

	long productEntryId = BeanParamUtil.getLong(productEntry, request, "productEntryId");
	long productVersionId = BeanParamUtil.getLong(productVersion, request, "productVersionId");
%>

<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editProductEntryURL">
	<portlet:param name="struts_action" value="/software_repository/edit_product_entry" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="productEntryId" value="<%= String.valueOf(productEntry.getProductEntryId()) %>" />
</portlet:renderURL>

<script type="text/javascript">
	function <portlet:namespace />saveEntry() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= productVersion == null ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}

</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/software_repository/edit_product_version" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveEntry(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>">
<input name="<portlet:namespace />productEntryId" type="hidden" value="<%= productEntryId %>">
<input name="<portlet:namespace />productVersionId" type="hidden" value="<%= productVersionId %>">

<liferay-ui:tabs names="product-entry" />

<%--<liferay-ui:error exception="<%= EntryURLException.class %>" message="please-enter-a-valid-url" />--%>

<h3><%=LanguageUtil.get(pageContext, "product-version-for-product") + " " + productEntry.getName()%></h3>

<fieldset>
	<legend><%=LanguageUtil.get(pageContext, "main-fields")%></legend>
	<table border="0" cellpadding="0" cellspacing="0">

	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "version") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<liferay-ui:input-field model="<%= SRProductVersion.class %>" bean="<%= productVersion %>" field="version" />
		</td>
	</tr>
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "changeLog") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<liferay-ui:input-field model="<%= SRProductVersion.class %>" bean="<%= productVersion %>" field="changeLog" />
		</td>
	</tr>
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "supported-framework-versions") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<select name="<portlet:namespace/>frameworkVersions" multiple="true">
				<%
					Iterator frameworkVersionsIt = SRFrameworkVersionServiceUtil.getFrameworkVersions(portletGroupId.longValue(), true).iterator();
					while (frameworkVersionsIt.hasNext()) {
						SRFrameworkVersion frameworkVersion = (SRFrameworkVersion) frameworkVersionsIt.next();
				%>
						<option <%= ((productVersion != null) && (productVersion.getFrameworkVersionIds().contains(new Long(frameworkVersion.getFrameworkVersionId())))) ? "selected" : "" %>
							value="<%= String.valueOf(frameworkVersion.getPrimaryKey()) %>"><%= frameworkVersion.getName() %></option>
				<%
					}
				%>
			</select>
		</td>
	</tr>
	</table>
</fieldset>
<fieldset class="repository-fields">
	<legend><%=LanguageUtil.get(pageContext, "repository-fields")%></legend>
	<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "download-page-url") %>
		</td>
		<td style="padding-left: 10px;"></td>	<td>
			<liferay-ui:input-field model="<%= SRProductVersion.class %>" bean="<%= productVersion %>" field="downloadPageURL" />
		</td>
	</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "direct-download-url") %> (<%= LanguageUtil.get(pageContext, "recommended") %>)
			</td>
			<td style="padding-left: 10px;"></td>	<td>
				<liferay-ui:input-field model="<%= SRProductVersion.class %>" bean="<%= productVersion %>" field="directDownloadURL" />
			</td>
		</tr>
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "add-artifact-to-this-repository") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<% if (Validator.isNotNull(productEntry.getRepoArtifactId()) && Validator.isNotNull(productEntry.getRepoArtifactId())) { %>
			<td>
				<select name="<portlet:namespace/>repoAllowAddArtifact" size="1">
					<option <%= ((productVersion != null) && (productVersion.getRepoStoreArtifact())) ? "selected" : "" %>><%= LanguageUtil.get(pageContext, "yes") %></option>
					<option <%= ((productVersion != null) && (!productVersion.getRepoStoreArtifact())) ? "selected" : "" %>><%= LanguageUtil.get(pageContext, "no") %></option>
				</select>
			</td>
		<% } else { %>
			<td>
				<a href="<%=editProductEntryURL%>"><%= LanguageUtil.get(pageContext, "please-specify-the-group-id-and-artifact-id-to-be-able-to-add-versions-to-our-repository") %></a>
			</td>
		<% } %>
	</tr>

	</table>
</fieldset>

<div class="form-buttons">
	<input class="portlet-form-button" type="submit" value='<%= LanguageUtil.get(pageContext, "save") %>'>
	<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="self.location = '<%= redirect %>';">
</div>

</form>

<script type="text/javascript">
	function <portlet:namespace />showAddArtifactToRepo() {
		if (document.<portlet:namespace />fm.<portlet:namespace />directDownloadURL.value == '') {
			document.<portlet:namespace />fm.<portlet:namespace />repoAllowAddArtifact.disabled = true;
		} else {
			document.<portlet:namespace />fm.<portlet:namespace />repoAllowAddArtifact.disabled = false;
		}
	}
	document.<portlet:namespace />fm.<portlet:namespace />version.focus();
	document.<portlet:namespace />fm.<portlet:namespace />directDownloadURL.onchange = <portlet:namespace />showAddArtifactToRepo;
	<portlet:namespace />showAddArtifactToRepo();

</script>