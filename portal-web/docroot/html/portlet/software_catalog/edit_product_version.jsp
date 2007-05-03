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
SCProductVersion productVersion = (SCProductVersion) request.getAttribute(WebKeys.SOFTWARE_CATALOG_PRODUCT_VERSION);

long productEntryId = productEntry.getProductEntryId();
long productVersionId = BeanParamUtil.getLong(productVersion, request, "productVersionId");

Set frameworkVersionIds = CollectionFactory.getHashSet();

if ((productVersion != null) && (request.getParameterValues("frameworkVersions") == null)) {
	Iterator itr = productVersion.getFrameworkVersions().iterator();

	while (itr.hasNext()) {
		SCFrameworkVersion frameworkVersion = (SCFrameworkVersion) itr.next();

		frameworkVersionIds.add(new Long(frameworkVersion.getFrameworkVersionId()));
	}
}
else {
	long[] frameworkVersionIdsArray = ParamUtil.getLongValues(request, "frameworkVersions");

	for (int i = 0; i < frameworkVersionIdsArray.length; i++) {
		frameworkVersionIds.add(new Long(frameworkVersionIdsArray[i]));
	}
}

PortletURL editProductEntryURL = renderResponse.createRenderURL();

editProductEntryURL.setWindowState(WindowState.MAXIMIZED);

editProductEntryURL.setParameter("struts_action", "/software_catalog/edit_product_entry");
editProductEntryURL.setParameter("redirect", redirect);
editProductEntryURL.setParameter("productEntryId", String.valueOf(productEntryId));

%>

<script type="text/javascript">
	function <portlet:namespace />saveEntry() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= productVersion == null ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}

</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/software_catalog/edit_product_version" /><portlet:param name="productEntryId" value="<%= Long.toString(productEntryId) %>" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveEntry(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>" />
<input name="<portlet:namespace />productVersionId" type="hidden" value="<%= productVersionId %>" />

<liferay-ui:tabs names="product-entry" />

<liferay-ui:error exception="<%= ProductVersionNameException.class %>" message="please-enter-a-valid-version-name" />
<liferay-ui:error exception="<%= ProductVersionChangeLogException.class %>" message="please-enter-a-valid-change-log" />
<liferay-ui:error exception="<%= ProductVersionDownloadURLException.class %>" message="please-enter-at-least-one-of-direct-download-url-or-download-page-url" />
<liferay-ui:error exception="<%= ProductVersionFrameworkVersionException.class %>" message="please-select-at-least-one-framework-version" />

<h3><%=LanguageUtil.get(pageContext, "product-version-for-product") + " " + productEntry.getName()%></h3>

<fieldset>
	<legend><%=LanguageUtil.get(pageContext, "main-fields")%></legend>

	<table class="liferay-table">
	<tr>
		<td>
			<bean:message key="version-name" />
		</td>
		<td>
			<liferay-ui:input-field model="<%= SCProductVersion.class %>" bean="<%= productVersion %>" field="version" />
		</td>
	</tr>
	<tr>
		<td>
			<bean:message key="changeLog" />
		</td>
		<td>
			<liferay-ui:input-field model="<%= SCProductVersion.class %>" bean="<%= productVersion %>" field="changeLog" />
		</td>
	</tr>
	<tr>
		<td>
			<bean:message key="supported-framework-versions" />
		</td>
		<td>
			<select name="<portlet:namespace/>frameworkVersions" multiple="true">
				<%
				Iterator frameworkVersionsIt = SCFrameworkVersionServiceUtil.getFrameworkVersions(portletGroupId.longValue(), true).iterator();
				while (frameworkVersionsIt.hasNext()) {
					SCFrameworkVersion frameworkVersion = (SCFrameworkVersion) frameworkVersionsIt.next();
				%>
						<option <%= (frameworkVersionIds.contains(new Long(frameworkVersion.getFrameworkVersionId()))) ? "selected" : "" %>
							value="<%= frameworkVersion.getFrameworkVersionId() %>"><%= frameworkVersion.getName() %></option>
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

	<table class="liferay-table">
	<tr>
		<td>
			<bean:message key="download-page-url" />
		</td>
		<td>
			<liferay-ui:input-field model="<%= SCProductVersion.class %>" bean="<%= productVersion %>" field="downloadPageURL" />
		</td>
	</tr>
		<tr>
			<td>
				<bean:message key="direct-download-url" /> (<bean:message key="recommended" />)
			</td>
			<td>
				<liferay-ui:input-field model="<%= SCProductVersion.class %>" bean="<%= productVersion %>" field="directDownloadURL" />
			</td>
		</tr>
	<tr>
		<td>
			<bean:message key="include-artifact-in-repository" />
		</td>

		<% if (Validator.isNotNull(productEntry.getRepoArtifactId()) && Validator.isNotNull(productEntry.getRepoArtifactId())) { %>
			<td>
				<select name="<portlet:namespace/>repoStoreArtifact" size="1">
					<option <%= ((productVersion != null) && (!productVersion.getRepoStoreArtifact())) ? "selected" : "" %> value="false"><bean:message key="no" /></option>
					<option <%= ((productVersion != null) && (productVersion.getRepoStoreArtifact())) ? "selected" : "" %> value="true"><bean:message key="yes" /></option>
				</select>
			</td>
		<% } else { %>
			<td>
				<a href="<%=editProductEntryURL%>"><bean:message key="please-specify-the-group-id-and-artifact-id-to-be-able-to-add-versions-to-our-repository" /></a>
			</td>
		<% } %>
	</tr>

	</table>
</fieldset>

<div class="form-buttons">
	<input type="submit" value="<bean:message key="save" />" />
	<input type="button" value="<bean:message key="cancel" />" onClick="self.location = '<%= redirect %>';" />
</div>

</form>

<script type="text/javascript">
	function <portlet:namespace />showRepoStoreArtifact() {
		if (document.<portlet:namespace />fm.<portlet:namespace />directDownloadURL.value == '') {
			document.<portlet:namespace />fm.<portlet:namespace />repoStoreArtifact.disabled = true;
			document.<portlet:namespace />fm.<portlet:namespace />repoStoreArtifact.options[0].selected = true;
		} else {
			document.<portlet:namespace />fm.<portlet:namespace />repoStoreArtifact.disabled = false;
		}
	}
	document.<portlet:namespace />fm.<portlet:namespace />version.focus();
	document.<portlet:namespace />fm.<portlet:namespace />directDownloadURL.onkeyup = <portlet:namespace />showRepoStoreArtifact;
	<portlet:namespace />showRepoStoreArtifact();

</script>