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

<%@ include file="/html/portlet/image_gallery/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "folders");

IGFolder folder = (IGFolder)request.getAttribute(WebKeys.IMAGE_GALLERY_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", IGFolderImpl.DEFAULT_PARENT_FOLDER_ID);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/image_gallery/view");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("folderId", String.valueOf(folderId));

List scores = null;
%>

<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" varImpl="searchURL"><portlet:param name="struts_action" value="/image_gallery/search" /></liferay-portlet:renderURL>

<form action="<%= searchURL %>" method="get" name="<portlet:namespace />fm1" onSubmit="submitForm(this); return false;">
<liferay-portlet:renderURLParams varImpl="searchURL" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(currentURL) %>" />
<input name="<portlet:namespace />breadcrumbsFolderId" type="hidden" value="<%= folderId %>" />
<input name="<portlet:namespace />searchFolderIds" type="hidden" value="<%= folderId %>" />

<%
String tabs1Names = "folders";

if (themeDisplay.isSignedIn()) {
	tabs1Names += ",my-images";
}

tabs1Names += ",recent-images";
%>

<liferay-ui:tabs
	names="<%= tabs1Names %>"
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
		List<String> headerNames = new ArrayList<String>();

		headerNames.add("folder");
		headerNames.add("num-of-folders");
		headerNames.add("num-of-images");
		headerNames.add(StringPool.BLANK);

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur1", SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

		int total = IGFolderLocalServiceUtil.getFoldersCount(scopeGroupId, folderId);

		searchContainer.setTotal(total);

		List results = IGFolderLocalServiceUtil.getFolders(scopeGroupId, folderId, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			IGFolder curFolder = (IGFolder)results.get(i);

			curFolder = curFolder.toEscapedModel();

			ResultRow row = new ResultRow(curFolder, curFolder.getFolderId(), i);

			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setWindowState(WindowState.MAXIMIZED);

			rowURL.setParameter("struts_action", "/image_gallery/view");
			rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));

			// Name and description

			StringBuilder sb = new StringBuilder();

			sb.append("<a href=\"");
			sb.append(rowURL);
			sb.append("\">");
			sb.append("<img align=\"left\" border=\"0\" src=\"");
			sb.append(themeDisplay.getPathThemeImages());
			sb.append("/common/folder.png\">");
			sb.append("<b>");
			sb.append(curFolder.getName());
			sb.append("</b>");

			if (Validator.isNotNull(curFolder.getDescription())) {
				sb.append("<br />");
				sb.append(curFolder.getDescription());
			}

			sb.append("</a>");

			List subfolders = IGFolderLocalServiceUtil.getFolders(scopeGroupId, curFolder.getFolderId(), 0, 5);

			if (subfolders.size() > 0) {
				int subfoldersCount = IGFolderLocalServiceUtil.getFoldersCount(scopeGroupId, curFolder.getFolderId());

				sb.append("<br /><u>");
				sb.append(LanguageUtil.get(pageContext, "subfolders"));
				sb.append("</u>: ");

				for (int j = 0; j < subfolders.size(); j++) {
					IGFolder subfolder = (IGFolder)subfolders.get(j);

					subfolder = subfolder.toEscapedModel();

					rowURL.setParameter("folderId", String.valueOf(subfolder.getFolderId()));

					sb.append("<a href=\"");
					sb.append(rowURL);
					sb.append("\">");
					sb.append(subfolder.getName());
					sb.append("</a>");

					if ((j + 1) < subfolders.size()) {
						sb.append(", ");
					}
				}

				if (subfoldersCount > subfolders.size()) {
					rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));

					sb.append(", <a href=\"");
					sb.append(rowURL);
					sb.append("\">");
					sb.append(LanguageUtil.get(pageContext, "more"));
					sb.append(" &raquo;");
					sb.append("</a>");
				}

				rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));
			}

			row.addText(sb.toString());

			// Statistics

			List subfolderIds = new ArrayList();

			subfolderIds.add(new Long(curFolder.getFolderId()));

			IGFolderLocalServiceUtil.getSubfolderIds(subfolderIds, scopeGroupId, curFolder.getFolderId());

			int foldersCount = subfolderIds.size() - 1;
			int imagesCount = IGImageLocalServiceUtil.getFoldersImagesCount(subfolderIds);

			row.addText(String.valueOf(foldersCount), rowURL);
			row.addText(String.valueOf(imagesCount), rowURL);

			// Action

			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/image_gallery/folder_action.jsp");

			// Add result row

			resultRows.add(row);
		}

		boolean showAddFolderButton = IGFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_FOLDER);
		boolean showPermissionsButton = GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS);
		boolean showSearch = (results.size() > 0);
		%>

		<c:if test="<%= showAddFolderButton || showPermissionsButton || showSearch %>">
			<div>
				<c:if test="<%= showSearch %>">
					<label for="<portlet:namespace />keywords1"><liferay-ui:message key="search" /></label>

					<input id="<portlet:namespace />keywords1" name="<portlet:namespace />keywords" size="30" type="text" />

					<input type="submit" value="<liferay-ui:message key="search-folders" />" />
				</c:if>

				<c:if test="<%= showAddFolderButton %>">
					<input type="button" value="<liferay-ui:message key='<%= (folder == null) ? "add-folder" : "add-subfolder" %>' />" onClick="<portlet:namespace />addFolder();" />
				</c:if>

				<c:if test="<%= showPermissionsButton %>">

					<%
					String modelResource = "com.liferay.portlet.imagegallery";
					String modelResourceDescription = themeDisplay.getScopeGroupName();
					String resourcePrimKey = String.valueOf(scopeGroupId);

					if (folder != null) {
						modelResource = IGFolder.class.getName();
						modelResourceDescription = folder.getName();
						resourcePrimKey = String.valueOf(folder.getFolderId());
					}
					%>

					<liferay-security:permissionsURL
						modelResource="<%= modelResource %>"
						modelResourceDescription="<%= HtmlUtil.escape(modelResourceDescription) %>"
						resourcePrimKey="<%= resourcePrimKey %>"
						var="permissionsURL"
					/>

					<input type="button" value="<liferay-ui:message key="permissions" />" onClick="location.href = '<%= permissionsURL %>';" />
				</c:if>
			</div>

			<br />
		</c:if>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		</form>

		<script type="text/javascript">
			function <portlet:namespace />addFolder() {
				var url = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/image_gallery/edit_folder" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="parentFolderId" value="<%= String.valueOf(folderId) %>" /></portlet:renderURL>';

				if (document.<portlet:namespace />fm1.<portlet:namespace />keywords) {
					url += '&<portlet:namespace />name=' + document.<portlet:namespace />fm1.<portlet:namespace />keywords.value;
				}

				submitForm(document.hrefFm, url);
			}

			<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
				Liferay.Util.focusFormField(document.<portlet:namespace />fm1.<portlet:namespace />keywords);
			</c:if>
		</script>

		<c:if test="<%= folder != null %>">
			<script type="text/javascript">
				function <portlet:namespace />viewSlideShow() {
					var slideShowWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/image_gallery/view_slide_show" /><portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" /></portlet:renderURL>', 'slideShow', 'directories=no,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no');
					slideShowWindow.focus();
				}
			</script>

			<br />

			<form action="<%= searchURL %>" method="get" name="<portlet:namespace />fm2" onSubmit="submitForm(this); return false;">
			<liferay-portlet:renderURLParams varImpl="searchURL" />
			<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(currentURL) %>" />
			<input name="<portlet:namespace />breadcrumbsFolderId" type="hidden" value="<%= folderId %>" />
			<input name="<portlet:namespace />searchFolderId" type="hidden" value="<%= folderId %>" />

			<liferay-ui:tabs names="images" />

			<%
			searchContainer = new SearchContainer(renderRequest, null, null, "cur2", SearchContainer.DEFAULT_DELTA, portletURL, null, null);

			total = IGImageLocalServiceUtil.getImagesCount(folder.getFolderId());

			searchContainer.setTotal(total);

			results = IGImageLocalServiceUtil.getImages(folder.getFolderId(), searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);

			boolean showAddImageButton = IGFolderPermission.contains(permissionChecker, folder, ActionKeys.ADD_IMAGE);
			showSearch = (results.size() > 0);
			%>

			<c:if test="<%= showAddImageButton || showSearch %>">
				<div>
					<c:if test="<%= showSearch %>">
						<label for="<portlet:namespace />keywords2"><liferay-ui:message key="search" /></label>

						<input id="<portlet:namespace />keywords2" name="<portlet:namespace />keywords" size="30" type="text" />

						<input type="submit" value="<liferay-ui:message key="search-this-folder" />" />
					</c:if>

					<c:if test="<%= showAddImageButton %>">
						<input type="button" value="<liferay-ui:message key="add-image" />" onClick="location.href = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/image_gallery/edit_image" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" /></portlet:renderURL>';" />
					</c:if>

					<c:if test="<%= showSearch %>">
						<input type="button" value="<liferay-ui:message key="view-slide-show" />" onClick="<portlet:namespace />viewSlideShow();" />
					</c:if>
				</div>

				<br />
			</c:if>

			<%@ include file="/html/portlet/image_gallery/view_images.jspf" %>

			</form>

			<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
				<script type="text/javascript">
					Liferay.Util.focusFormField(document.<portlet:namespace />fm2.<portlet:namespace />keywords);
					Liferay.Util.focusFormField(document.<portlet:namespace />fm1.<portlet:namespace />keywords);
				</script>
			</c:if>
		</c:if>

		<br />

		<%
		StringBuffer sb = new StringBuffer();

		if (folder != null) {
			IGFolder curFolder = folder;

			while (true) {
				sb.insert(0, WebDAVUtil.encodeURL(curFolder.getName()));
				sb.insert(0, StringPool.SLASH);

				if (curFolder.getParentFolderId() == IGFolderImpl.DEFAULT_PARENT_FOLDER_ID) {
					break;
				}
				else {
					curFolder = IGFolderLocalServiceUtil.getFolder(curFolder.getParentFolderId());
				}
			}

			PortalUtil.setPageSubtitle(folder.getName(), request);
			PortalUtil.setPageDescription(folder.getDescription(), request);
		}
		%>

		<liferay-ui:webdav path='<%= "/image_gallery" + sb.toString() %>' />
	</c:when>
	<c:when test='<%= tabs1.equals("my-images") || tabs1.equals("recent-images") %>'>

		<%
		long groupImagesUserId = 0;

		if (tabs1.equals("my-images") && themeDisplay.isSignedIn()) {
			groupImagesUserId = user.getUserId();
		}

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, null, null);

		int total = IGImageLocalServiceUtil.getGroupImagesCount(scopeGroupId, groupImagesUserId);

		searchContainer.setTotal(total);

		List results = IGImageLocalServiceUtil.getGroupImages(scopeGroupId, groupImagesUserId, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);
		%>

		<%@ include file="/html/portlet/image_gallery/view_images.jspf" %>

		</form>

		<%
		PortalUtil.setPageSubtitle(LanguageUtil.get(pageContext, tabs1), request);
		%>

	</c:when>
</c:choose>