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

folder = folder.toEscapedModel();

List<Long> subfolderIds = new ArrayList<Long>();

subfolderIds.add(new Long(folder.getFolderId()));

DLFolderLocalServiceUtil.getSubfolderIds(subfolderIds, scopeGroupId, folder.getFolderId());

int foldersCount = subfolderIds.size() - 1;
int fileEntriesCount = DLFolderLocalServiceUtil.getFileEntriesAndFileShortcutsCount(subfolderIds);

request.setAttribute("view_folder.jsp-folder", folder);
%>

<aui:column columnWidth="<%= 75 %>" cssClass="file-entry-column file-entry-column-first" first="<%= true %>">
	<h3><%= folder.getName() %></h3>

	<div class="folder-description">
		<%= folder.getDescription() %>
	</div>

	<div class="folder-date">
		<%= LanguageUtil.format(pageContext, "last-updated-x", dateFormatDateTime.format(folder.getModifiedDate())) %>
	</div>

	<div class="folder-subfolders">
		<%= foldersCount %> <liferay-ui:message key="subfolders" />
	</div>

	<div class="folder-file-entries">
		<%= fileEntriesCount %> <liferay-ui:message key="files" />
	</div>

	<div class="custom-attributes">
		<liferay-ui:custom-attributes-available className="<%= DLFolder.class.getName() %>">
			<liferay-ui:custom-attribute-list
				className="<%= DLFolder.class.getName() %>"
				classPK="<%= (folder != null) ? folder.getFolderId() : 0 %>"
				editable="<%= false %>"
				label="<%= true %>"
			/>
		</liferay-ui:custom-attributes-available>
	</div>

	<div class="folder-field">
		<label><liferay-ui:message key="webdav-url" /></label>

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
	</div>
</aui:column>

<aui:column columnWidth="<%= 25 %>" cssClass="file-entry-column file-entry-column-last" last="<%= true %>">
	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="viewFolderURL">
		<portlet:param name="struts_action" value="/document_library/view" />
		<portlet:param name="folderId" value="<%= String.valueOf(folder.getFolderId()) %>" />
	</portlet:renderURL>

	<div class="folder-icon">
		<liferay-ui:icon
			image='<%= "../document_library/folder" + (((foldersCount + fileEntriesCount) > 0) ? "_full" : StringPool.BLANK) %>'
			cssClass="folder-avatar"
			message='open'
			url='<%= viewFolderURL %>'
		/>

		<div class="folder-name">
			<a href="<%= viewFolderURL %>">
				<%= folder.getName() %>
			</a>
		</div>
	</div>

	<liferay-util:include page="/html/portlet/document_library/folder_action.jsp" />
</aui:column>

<%
DLUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);
%>