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

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/software_catalog/edit_product_entry" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" enctype="multipart/form-data" onSubmit="<portlet:namespace />saveProductEntry(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>" />
<input name="<portlet:namespace />productEntryId" type="hidden" value="<%= productEntryId %>" />

<liferay-ui:tabs names="product" />

<liferay-ui:error exception="<%= ProductEntryNameException.class %>" message="please-enter-a-valid-name" />
<liferay-ui:error exception="<%= ProductEntryTypeException.class %>" message="please-select-a-valid-type" />
<liferay-ui:error exception="<%= ProductEntryShortDescriptionException.class %>" message="please-enter-a-valid-short-description" />
<liferay-ui:error exception="<%= ProductEntryLicenseException.class %>" message="please-select-at-least-one-license" />
<liferay-ui:error exception="<%= ProductEntryImagesException.class %>" message="please-enter-a valid-main-screenshot" />
<liferay-ui:error exception="<%= ImageSizeException.class %>" message="please-enter-a-file-with-a-valid-file-size" />

<table class="liferay-table">
<tr>
	<td>
		<bean:message key="name" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= SCProductEntry.class %>" bean="<%= productEntry %>" field="name" />
	</td>
</tr>
<tr>
	<td>
		<bean:message key="type" />
	</td>
	<td>
		<select name="<portlet:namespace/>type">
			<option <%= type.equals("portlet") ? "selected" : "" %> value="portlet"><bean:message key="portlet" /></option>
			<option <%= type.equals("theme") ? "selected" : "" %> value="theme"><bean:message key="theme" /></option>
			<option <%= type.equals("layout") ? "selected" : "" %> value="layout"><bean:message key="layout" /></option>
			<option <%= type.equals("extension") ? "selected" : "" %> value="extension"><bean:message key="extension" /></option>
		</select>
	</td>
</tr>
<tr>
	<td>
		<bean:message key="licenses" />
	</td>
	<td>
		<select name="<portlet:namespace/>licenses" multiple="true">
			<optgroup label="<bean:message key="recommended-licenses" />">

				<%
				Iterator itr = SCLicenseLocalServiceUtil.getLicenses(true, true).iterator();

				while (itr.hasNext()) {
					SCLicense license = (SCLicense) itr.next();
				%>

					<option <%= licenseIds.contains(new Long(license.getLicenseId())) ? "selected" : "" %> value="<%= license.getLicenseId() %>"><%= license.getName() %></option>

				<%
				}
				%>

			</optgroup>

			<optgroup label="<bean:message key="other-licenses" />">

				<%
				itr = SCLicenseLocalServiceUtil.getLicenses(true, false).iterator();

				while (itr.hasNext()) {
					SCLicense license = (SCLicense) itr.next();
				%>

					<option <%= licenseIds.contains(new Long(license.getLicenseId())) ? "selected" : "" %> value="<%= license.getLicenseId() %>"><%= license.getName() %></option>

				<%
				}
				%>

			</optgroup>
		</select>
	</td>
</tr>
<tr>
	<td>
		<bean:message key="page-url" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= SCProductEntry.class %>" bean="<%= productEntry %>" field="pageURL" />
	</td>
</tr>
<tr>
	<td>
		<bean:message key="short-description" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= SCProductEntry.class %>" bean="<%= productEntry %>" field="shortDescription" />
	</td>
</tr>
<tr>
	<td>
		<bean:message key="long-description" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= SCProductEntry.class %>" bean="<%= productEntry %>" field="longDescription" />
	</td>
</tr>
<tr>
	<td>
		<bean:message key="main-screenshot" />
	</td>
	<td>
		<%
		String mainImageName = SCProductEntryImpl.MAIN_IMAGE_NAME;
		%>
		<input name="<portlet:namespace />screenshot_update_<%=mainImageName%>" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="file" />
		<%
		if (productEntry != null) {
			String imageId = productEntry.getImageId(mainImageName);
			if (imageId != null) {
				String imageURL = themeDisplay.getPathImage() + "/software_catalog?img_id=" + imageId + "&small=1";
		%>
				<br clear="all"/>
				<img src="<%=imageURL%>"/>
		<%
			}
		}

		%>
	</td>
</tr>
<tr>
	<td>
		<bean:message key="secondary-screenshots" />
	</td>
	<td>
		<%
		for (int i = 1; i < 5; i++) {
			String imageName = Integer.toString(i);
		%>
 			<br clear="all"/>
			<input name="<portlet:namespace />screenshot_update_<%=imageName%>" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="file" />
		<%
			if (productEntry != null) {
				String imageId = productEntry.getImageId(imageName);
				if (imageId != null) {
					String imageURL = themeDisplay.getPathImage() + "/software_catalog?img_id=" + imageId + "&small=1";
		%>
					<br clear="all"/>
					<img src="<%=imageURL%>" width="100"/>
					<input type="checkbox" name="<portlet:namespace />screenshot_delete_<%=imageName%>"/> <%= LanguageUtil.get(pageContext, "delete")%>
		<%
				}
			}
		}
		%>
	</td>
</tr>

<c:if test="<%= productEntry == null %>">
	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
	<tr>
		<td>
			<bean:message key="permissions" />
		</td>
		<td>
			<liferay-ui:input-permissions
				modelName="<%= SCProductEntry.class.getName() %>"
			/>
		</td>
	</tr>
</c:if>

</table>

<br />

<liferay-ui:tabs names="plugin-repository" />

<table class="liferay-table">
<tr>
	<td>
		<bean:message key="group-id" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= SCProductEntry.class %>" bean="<%= productEntry %>" field="repoGroupId" />
	</td>
</tr>
<tr>
	<td>
		<bean:message key="artifact-id" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= SCProductEntry.class %>" bean="<%= productEntry %>" field="repoArtifactId" />
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