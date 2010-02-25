<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/image_gallery/init.jsp" %>

<%
long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");
%>

<liferay-ui:search-container
	curParam="cur1"
	headerNames="folder,num-of-folders,num-of-images"
	iteratorURL="<%= portletURL %>"
>
	<liferay-ui:search-container-results
		results="<%= IGFolderLocalServiceUtil.getFolders(scopeGroupId, folderId, searchContainer.getStart(), searchContainer.getEnd()) %>"
		total="<%= IGFolderLocalServiceUtil.getFoldersCount(scopeGroupId, folderId) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portlet.imagegallery.model.IGFolder"
		escapedModel="<%= true %>"
		keyProperty="folderId"
		modelVar="curFolder"
	>
		<liferay-portlet:renderURL varImpl="rowURL">
			<portlet:param name="struts_action" value="/image_gallery/view" />
			<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:search-container-column-text
			buffer="buffer"
			name="folder"
		>

			<%
			buffer.append("<a href=\"");
			buffer.append(rowURL);
			buffer.append("\">");
			buffer.append("<img alt=\"");
			buffer.append(LanguageUtil.get(pageContext, "folder"));
			buffer.append("\" class=\"label-icon\" src=\"");
			buffer.append(themeDisplay.getPathThemeImages());
			buffer.append("/common/folder.png\">");
			buffer.append("<strong>");
			buffer.append(curFolder.getName());
			buffer.append("</strong>");

			if (Validator.isNotNull(curFolder.getDescription())) {
				buffer.append("<br />");
				buffer.append(curFolder.getDescription());
			}

			buffer.append("</a>");

			List subfolders = IGFolderLocalServiceUtil.getFolders(scopeGroupId, curFolder.getFolderId(), 0, 5);

			if (!subfolders.isEmpty()) {
				int subfoldersCount = IGFolderLocalServiceUtil.getFoldersCount(scopeGroupId, curFolder.getFolderId());

				buffer.append("<br /><u>");
				buffer.append(LanguageUtil.get(pageContext, "subfolders"));
				buffer.append("</u>: ");

				for (int j = 0; j < subfolders.size(); j++) {
					IGFolder subfolder = (IGFolder)subfolders.get(j);

					subfolder = subfolder.toEscapedModel();

					rowURL.setParameter("folderId", String.valueOf(subfolder.getFolderId()));

					buffer.append("<a href=\"");
					buffer.append(rowURL);
					buffer.append("\">");
					buffer.append(subfolder.getName());
					buffer.append("</a>");

					if ((j + 1) < subfolders.size()) {
						buffer.append(", ");
					}
				}

				if (subfoldersCount > subfolders.size()) {
					rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));

					buffer.append(", <a href=\"");
					buffer.append(rowURL);
					buffer.append("\">");
					buffer.append(LanguageUtil.get(pageContext, "more"));
					buffer.append(" &raquo;");
					buffer.append("</a>");
				}

				rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));
			}
			%>

		</liferay-ui:search-container-column-text>

		<%
		List subfolderIds = new ArrayList();

		subfolderIds.add(new Long(curFolder.getFolderId()));

		IGFolderLocalServiceUtil.getSubfolderIds(subfolderIds, scopeGroupId, curFolder.getFolderId());

		int subFoldersCount = subfolderIds.size() - 1;
		int subEntriesCount = IGImageLocalServiceUtil.getFoldersImagesCount(scopeGroupId, subfolderIds);
		%>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="num-of-folders"
			value="<%= String.valueOf(subFoldersCount) %>"
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="num-of-entries"
			value="<%= String.valueOf(subEntriesCount) %>"
		/>

		<liferay-ui:search-container-column-jsp
			align="right"
			path="/html/portlet/image_gallery/folder_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>