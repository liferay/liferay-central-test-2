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

<%@ include file="/html/portlet/image_gallery/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "folders");

IGFolder folder = (IGFolder)request.getAttribute(WebKeys.IMAGE_GALLERY_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", IGFolderImpl.DEFAULT_PARENT_FOLDER_ID);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/image_gallery/view");
portletURL.setParameter("folderId", String.valueOf(folderId));
%>

<form method="post" name="<portlet:namespace />">

<liferay-ui:tabs
	names="folders,my-images,recent-images"
	url="<%= portletURL.toString() %>"
/>

<c:choose>
	<c:when test='<%= tabs1.equals("folders") %>'>
		<c:if test="<%= folder != null %>">
			<div class="breadcrumbs">
				<%= IGUtil.getBreadcrumbs(folder, null, pageContext, renderRequest, renderResponse) %>
			</div>
		</c:if>

		<%
		List headerNames = new ArrayList();

		headerNames.add("folder");
		headerNames.add("num-of-folders");
		headerNames.add("num-of-images");
		headerNames.add(StringPool.BLANK);

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur1", SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

		int total = IGFolderLocalServiceUtil.getFoldersCount(portletGroupId.longValue(), folderId);

		searchContainer.setTotal(total);

		List results = IGFolderLocalServiceUtil.getFolders(portletGroupId.longValue(), folderId, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			IGFolder curFolder = (IGFolder)results.get(i);

			ResultRow row = new ResultRow(curFolder, curFolder.getFolderId(), i);

			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setWindowState(WindowState.MAXIMIZED);

			rowURL.setParameter("struts_action", "/image_gallery/view");
			rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));

			// Name and description

			StringMaker sm = new StringMaker();

			sm.append("<a href=\"");
			sm.append(rowURL);
			sm.append("\">");
			sm.append("<img align=\"left\" border=\"0\" src=\"");
			sm.append(themeDisplay.getPathThemeImages());
			sm.append("/common/folder.png\">");
			sm.append("<b>");
			sm.append(curFolder.getName());
			sm.append("</b>");

			if (Validator.isNotNull(curFolder.getDescription())) {
				sm.append("<br />");
				sm.append("<span style=\"font-size: xx-small;\">");
				sm.append(curFolder.getDescription());
				sm.append("</span>");
			}

			sm.append("</a>");

			List subfolders = IGFolderLocalServiceUtil.getFolders(portletGroupId.longValue(), curFolder.getFolderId(), 0, 5);

			if (subfolders.size() > 0) {
				sm.append("<br />");
				sm.append("<span style=\"font-size: xx-small; font-weight: bold;\"><u>");
				sm.append(LanguageUtil.get(pageContext, "subfolders"));
				sm.append("</u>: ");

				for (int j = 0; j < subfolders.size(); j++) {
					IGFolder subfolder = (IGFolder)subfolders.get(j);

					rowURL.setParameter("folderId", String.valueOf(subfolder.getFolderId()));

					sm.append("<a href=\"");
					sm.append(rowURL);
					sm.append("\">");
					sm.append(subfolder.getName());
					sm.append("</a>");

					if ((j + 1) < subfolders.size()) {
						sm.append(", ");
					}
				}

				rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));

				sm.append("</span>");
			}

			row.addText(sm.toString());

			// Statistics

			List subfolderIds = new ArrayList();

			subfolderIds.add(new Long(curFolder.getFolderId()));

			IGFolderLocalServiceUtil.getSubfolderIds(subfolderIds, portletGroupId.longValue(), curFolder.getFolderId());

			int foldersCount = subfolderIds.size() - 1;
			int imagesCount = IGImageLocalServiceUtil.getFoldersImagesCount(subfolderIds);

			row.addText(Integer.toString(foldersCount), rowURL);
			row.addText(Integer.toString(imagesCount), rowURL);

			// Action

			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/image_gallery/folder_action.jsp");

			// Add result row

			resultRows.add(row);
		}
		%>

		<c:if test="<%= IGFolderPermission.contains(permissionChecker, plid, folderId, ActionKeys.ADD_FOLDER) %>">
			<input type="button" value='<%= LanguageUtil.get(pageContext, "add-folder") %>' onClick="self.location = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/image_gallery/edit_folder" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="parentFolderId" value="<%= String.valueOf(folderId) %>" /></portlet:renderURL>';"><br />

			<c:if test="<%= results.size() > 0 %>">
				<br />
			</c:if>
		</c:if>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />

		<c:if test="<%= folder != null %>">
			<br />

			<liferay-ui:tabs names="images" />

			<%
			headerNames.clear();

			headerNames.add("thumbnail");
			headerNames.add("height");
			headerNames.add("width");
			headerNames.add("size");
			headerNames.add(StringPool.BLANK);

			searchContainer = new SearchContainer(renderRequest, null, null, "cur2", SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

			total = IGImageLocalServiceUtil.getImagesCount(folder.getFolderId());

			searchContainer.setTotal(total);

			results = IGImageLocalServiceUtil.getImages(folder.getFolderId(), searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);

			resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				IGImage image = (IGImage)results.get(i);

				ResultRow row = new ResultRow(image, image.getImageId(), i);

				// Thumbnail

				row.addJSP("/html/portlet/image_gallery/image_thumbnail.jsp");

				// Statistics

				row.addText(Integer.toString(image.getHeight()));
				row.addText(Integer.toString(image.getWidth()));
				row.addText(TextFormatter.formatKB(image.getSize(), locale) + "k");

				// Action

				row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/image_gallery/image_action.jsp");

				// Add result row

				resultRows.add(row);
			}

			boolean showAddImageButton = IGFolderPermission.contains(permissionChecker, folder, ActionKeys.ADD_IMAGE);
			%>

			<c:if test="<%= showAddImageButton || (results.size() > 0) %>">
				<c:if test="<%= showAddImageButton %>">
					<input type="button" value='<%= LanguageUtil.get(pageContext, "add-image") %>' onClick="self.location = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/image_gallery/edit_image" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" /></portlet:renderURL>';">
				</c:if>

				<c:if test="<%= results.size() > 0 %>">
					<input type="button" value='<%= LanguageUtil.get(pageContext, "view-slide-show") %>' onClick="var slideShowWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/image_gallery/view_slide_show" /><portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" /></portlet:renderURL>', 'slideShow', 'directories=no,location=no,menubar=no,resizable=yes,scrollbars=no,status=no,toolbar=no'); void(''); slideShowWindow.focus();">
				</c:if>

				<c:if test="<%= results.size() > 0 %>">
					<br /><br />
				</c:if>
			</c:if>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

			<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
		</c:if>

		</form>
	</c:when>
	<c:when test='<%= tabs1.equals("my-images") || tabs1.equals("recent-images") %>'>

		<%
		long groupImagesUserId = 0;

		if (tabs1.equals("my-images") && themeDisplay.isSignedIn()) {
			groupImagesUserId = user.getUserId();
		}

		List headerNames = new ArrayList();

		headerNames.add("thumbnail");
		headerNames.add("height");
		headerNames.add("width");
		headerNames.add("size");
		headerNames.add(StringPool.BLANK);

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

		int total = IGImageLocalServiceUtil.getGroupImagesCount(portletGroupId.longValue(), groupImagesUserId);

		searchContainer.setTotal(total);

		List results = IGImageLocalServiceUtil.getGroupImages(portletGroupId.longValue(), groupImagesUserId, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			IGImage image = (IGImage)results.get(i);

			ResultRow row = new ResultRow(image, image.getImageId(), i);

			// Thumbnail

			row.addJSP("/html/portlet/image_gallery/image_thumbnail.jsp");

			// Statistics

			row.addText(Integer.toString(image.getHeight()));
			row.addText(Integer.toString(image.getWidth()));
			row.addText(TextFormatter.formatKB(image.getSize(), locale) + "k");

			// Action

			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/image_gallery/image_action.jsp");

			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
	</c:when>
</c:choose>