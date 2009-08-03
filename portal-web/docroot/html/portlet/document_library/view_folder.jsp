<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

DLFolder folder = (DLFolder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId");

long parentFolderId = BeanParamUtil.getLong(folder, request, "parentFolderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
%>

<table class="lfr-table">

<tr>
	<td class="lfr-label">
		<liferay-ui:message key="parent-folder" />
	</td>
	<td>

		<%
		String parentFolderName = "";

		try {
			DLFolder parentFolder = DLFolderLocalServiceUtil.getFolder(parentFolderId);

			parentFolderName = parentFolder.getName();
		}
		catch (NoSuchFolderException nscce) {
		}
		%>

		<%= parentFolderName %>
	</td>
</tr>
<tr>
	<td colspan="2">
		<br />
	</td>
</tr>

<tr>
	<td class="lfr-label">
		<liferay-ui:message key="name" />
	</td>
	<td>
		<%= folder.getName() %>
	</td>
</tr>
<tr>
	<td class="lfr-label">
		<liferay-ui:message key="description" />
	</td>
	<td>
		<%= folder.getDescription() %>
	</td>
</tr>

<liferay-ui:custom-attributes-available className="<%= DLFolder.class.getName() %>">
	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<liferay-ui:custom-attribute-list
				className="<%= DLFolder.class.getName() %>"
				classPK="<%= (folder != null) ? folder.getFolderId() : 0 %>"
				editable="<%= false %>"
				label="<%= true %>"
			/>
		</td>
	</tr>
</liferay-ui:custom-attributes-available>

<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="webdav-url" />
	</td>
	<td>

		<%
		StringBuffer sb = new StringBuffer();

		if (folder != null) {
			DLFolder curFolder = folder;

			while (true) {
				sb.insert(0, WebDAVUtil.encodeURL(curFolder.getName()));
				sb.insert(0, StringPool.SLASH);

				if (curFolder.getParentFolderId() == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
					break;
				}
				else {
					curFolder = DLFolderLocalServiceUtil.getFolder(curFolder.getParentFolderId());
				}
			}
		}

		Group group = layout.getGroup();
		%>

		<liferay-ui:input-resource
			url='<%= themeDisplay.getPortalURL() + "/tunnel-web/secure/webdav/" + company.getWebId() + group.getFriendlyURL() + "/document_library" + sb.toString() %>'
		/>
	</td>
</tr>

</table>

<%
	DLUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);
%>