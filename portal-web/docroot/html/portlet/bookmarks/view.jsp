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

<%@ include file="/html/portlet/bookmarks/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "folders");

BookmarksFolder folder = (BookmarksFolder)request.getAttribute(WebKeys.BOOKMARKS_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", BookmarksFolderImpl.DEFAULT_PARENT_FOLDER_ID);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/bookmarks/view");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("folderId", String.valueOf(folderId));
%>

<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" varImpl="searchURL"><portlet:param name="struts_action" value="/bookmarks/search" /></liferay-portlet:renderURL>

<form action="<%= searchURL %>" method="get" name="<portlet:namespace />fm1" onSubmit="submitForm(this); return false;">
<liferay-portlet:renderURLParams varImpl="searchURL" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(currentURL) %>" />
<input name="<portlet:namespace />breadcrumbsFolderId" type="hidden" value="<%= folderId %>" />
<input name="<portlet:namespace />searchFolderIds" type="hidden" value="<%= folderId %>" />

<liferay-ui:tabs
	names="folders,my-entries,recent-entries"
	url="<%= portletURL.toString() %>"
/>

<c:choose>
	<c:when test='<%= tabs1.equals("folders") %>'>

		<liferay-ui:search-container
			curParam="cur1"
			headerNames="folder,num-of-folders,num-of-entries"
			iteratorURL="<%= portletURL %>"
		>
			<liferay-ui:search-container-results
				results="<%= BookmarksFolderLocalServiceUtil.getFolders(scopeGroupId, folderId, searchContainer.getStart(), searchContainer.getEnd()) %>"
				total="<%= BookmarksFolderLocalServiceUtil.getFoldersCount(scopeGroupId, folderId) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portlet.bookmarks.model.BookmarksFolder"
				escapedModel="<%= true %>"
				keyProperty="folderId"
				modelVar="curFolder"
			>
				<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" varImpl="rowURL">
					<portlet:param name="struts_action" value="/bookmarks/view" />
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
					buffer.append("<b>");
					buffer.append(curFolder.getName());
					buffer.append("</b>");

					if (Validator.isNotNull(curFolder.getDescription())) {
						buffer.append("<br />");
						buffer.append(curFolder.getDescription());
					}

					buffer.append("</a>");

					List subfolders = BookmarksFolderLocalServiceUtil.getFolders(scopeGroupId, curFolder.getFolderId(), 0, 5);

					if (subfolders.size() > 0) {
						int subfoldersCount = BookmarksFolderLocalServiceUtil.getFoldersCount(scopeGroupId, curFolder.getFolderId());

						buffer.append("<br /><u>");
						buffer.append(LanguageUtil.get(pageContext, "subfolders"));
						buffer.append("</u>: ");

						for (int j = 0; j < subfolders.size(); j++) {
							BookmarksFolder subfolder = (BookmarksFolder)subfolders.get(j);

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

				BookmarksFolderLocalServiceUtil.getSubfolderIds(subfolderIds, scopeGroupId, curFolder.getFolderId());

				int foldersCount = subfolderIds.size() - 1;
				int entriesCount = BookmarksEntryLocalServiceUtil.getFoldersEntriesCount(subfolderIds);
				%>

				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					name="num-of-folders"
					value="<%= String.valueOf(foldersCount) %>"
				/>

				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					name="num-of-entries"
					value="<%= String.valueOf(entriesCount) %>"
				/>

				<liferay-ui:search-container-column-jsp
					align="right"
					path="/html/portlet/bookmarks/folder_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<%
			boolean showAddFolderButton = BookmarksFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_FOLDER);
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
						String modelResource = "com.liferay.portlet.bookmarks";
						String modelResourceDescription = themeDisplay.getScopeGroupName();
						String resourcePrimKey = String.valueOf(scopeGroupId);

						if (folder != null) {
							modelResource = BookmarksFolder.class.getName();
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

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>

		</form>

		<script type="text/javascript">
			function <portlet:namespace />addFolder() {
				var url = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/bookmarks/edit_folder" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="parentFolderId" value="<%= String.valueOf(folderId) %>" /></portlet:renderURL>';

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
			<br />

			<form action="<%= searchURL %>" method="get" name="<portlet:namespace />fm2" onSubmit="submitForm(this); return false;">
			<liferay-portlet:renderURLParams varImpl="searchURL" />
			<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(currentURL) %>" />
			<input name="<portlet:namespace />breadcrumbsFolderId" type="hidden" value="<%= folderId %>" />
			<input name="<portlet:namespace />searchFolderId" type="hidden" value="<%= folderId %>" />

			<liferay-ui:tabs names="entries" />

			<%
			String orderByCol = ParamUtil.getString(request, "orderByCol");
			String orderByType = ParamUtil.getString(request, "orderByType");

			if (Validator.isNotNull(orderByCol) && Validator.isNotNull(orderByType)) {
				portalPrefs.setValue(PortletKeys.BOOKMARKS, "entries-order-by-col", orderByCol);
				portalPrefs.setValue(PortletKeys.BOOKMARKS, "entries-order-by-type", orderByType);
			}
			else {
				orderByCol = portalPrefs.getValue(PortletKeys.BOOKMARKS, "entries-order-by-col", "name");
				orderByType = portalPrefs.getValue(PortletKeys.BOOKMARKS, "entries-order-by-type", "asc");
			}

			OrderByComparator orderByComparator = BookmarksUtil.getEntriesOrderByComparator(orderByCol, orderByType);
			%>

			<liferay-ui:search-container
				curParam="cur2"
				headerNames="name,url,visits,priority,modified-date"
				iteratorURL="<%= portletURL %>"
				orderByCol="<%= orderByCol %>"
				orderByType="<%= orderByType %>"
			>
				<liferay-ui:search-container-results
					results="<%= BookmarksEntryLocalServiceUtil.getEntries(folder.getFolderId(), searchContainer.getStart(), searchContainer.getEnd(), orderByComparator) %>"
					total="<%= BookmarksEntryLocalServiceUtil.getEntriesCount(folder.getFolderId()) %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.portlet.bookmarks.model.BookmarksEntry"
					escapedModel="<%= true %>"
					keyProperty="entryId"
					modelVar="entry"
				>

					<%
					StringBuilder sb = new StringBuilder();

					sb.append(themeDisplay.getPathMain());
					sb.append("/bookmarks/open_entry?entryId=");
					sb.append(entry.getEntryId());

					String rowHREF = sb.toString();
					%>

					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="entry"
						orderable="<%= true %>"
						orderableProperty="name"
						property="name"
						target="_blank"
						title="<%= entry.getComments() %>"
					/>

					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="url"
						orderable="<%= true %>"
						property="url"
						target="_blank"
						title="<%= entry.getComments() %>"
					/>

					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="visits"
						orderable="<%= true %>"
						property="visits"
						target="_blank"
						title="<%= entry.getComments() %>"
					/>

					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="priority"
						orderable="<%= true %>"
						property="priority"
						target="_blank"
						title="<%= entry.getComments() %>"
					/>

					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="modified-date"
						orderable="<%= true %>"
						target="_blank"
						title="<%= entry.getComments() %>"
						value="<%= dateFormatDate.format(entry.getModifiedDate()) %>"
					/>

					<liferay-ui:search-container-column-jsp
						align="right"
						path="/html/portlet/bookmarks/entry_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<%
				boolean showAddEntryButton = BookmarksFolderPermission.contains(permissionChecker, folder, ActionKeys.ADD_ENTRY);
				boolean showSearch = (results.size() > 0);
				%>

				<c:if test="<%= showAddEntryButton || showSearch %>">
					<div>
						<c:if test="<%= showSearch %>">
							<label for="<portlet:namespace />keywords2"><liferay-ui:message key="search" /></label>

							<input id="<portlet:namespace />keywords2" name="<portlet:namespace />keywords" size="30" type="text" />

							<input type="submit" value="<liferay-ui:message key="search-this-folder" />" />
						</c:if>

						<c:if test="<%= showAddEntryButton %>">
							<input type="button" value="<liferay-ui:message key="add-entry" />" onClick="<portlet:namespace />addEntry();" />
						</c:if>
					</div>

					<br />
				</c:if>

				<liferay-ui:search-iterator />
			</liferay-ui:search-container>

			</form>

			<script type="text/javascript">
				function <portlet:namespace />addEntry() {
					var url = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/bookmarks/edit_entry" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" /></portlet:renderURL>';

					if (document.<portlet:namespace />fm2.<portlet:namespace />keywords) {
						url += '&<portlet:namespace />name=' + document.<portlet:namespace />fm2.<portlet:namespace />keywords.value;
					}

					submitForm(document.hrefFm, url);
				}

				<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
					Liferay.Util.focusFormField(document.<portlet:namespace />fm2.<portlet:namespace />keywords);
					Liferay.Util.focusFormField(document.<portlet:namespace />fm1.<portlet:namespace />keywords);
				</c:if>
			</script>

			<%
			BookmarksUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);

			PortalUtil.setPageSubtitle(folder.getName(), request);
			PortalUtil.setPageDescription(folder.getDescription(), request);
			%>

		</c:if>
	</c:when>
	<c:when test='<%= tabs1.equals("my-entries") || tabs1.equals("recent-entries") %>'>
		<liferay-ui:search-container
			headerNames="folder,num-of-folders,num-of-entries"
			iteratorURL="<%= portletURL %>"
		>

			<%
			long groupEntriesUserId = 0;

			if (tabs1.equals("my-entries") && themeDisplay.isSignedIn()) {
				groupEntriesUserId = user.getUserId();
			}
			%>

			<liferay-ui:search-container-results
				results="<%= BookmarksEntryLocalServiceUtil.getGroupEntries(scopeGroupId, groupEntriesUserId, searchContainer.getStart(), searchContainer.getEnd()) %>"
				total="<%= BookmarksEntryLocalServiceUtil.getGroupEntriesCount(scopeGroupId, groupEntriesUserId) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portlet.bookmarks.model.BookmarksEntry"
				escapedModel="<%= true %>"
				keyProperty="entryId"
				modelVar="entry"
			>

				<%
				StringBuilder sb = new StringBuilder();

				sb.append(themeDisplay.getPathMain());
				sb.append("/bookmarks/open_entry?entryId=");
				sb.append(entry.getEntryId());

				String rowHREF = sb.toString();
				%>

				<liferay-ui:search-container-column-text
					href="<%= rowHREF %>"
					name="entry"
					property="name"
					target="_blank"
					title="<%= entry.getComments() %>"
				/>

				<liferay-ui:search-container-column-text
					href="<%= rowHREF %>"
					name="url"
					property="url"
					target="_blank"
					title="<%= entry.getComments() %>"
				/>

				<liferay-ui:search-container-column-text
					href="<%= rowHREF %>"
					name="visits"
					property="visits"
					target="_blank"
					title="<%= entry.getComments() %>"
				/>

				<liferay-ui:search-container-column-jsp
					align="right"
					path="/html/portlet/bookmarks/entry_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>

		</form>

		<%
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, tabs1), currentURL);

		PortalUtil.setPageSubtitle(LanguageUtil.get(pageContext, StringUtil.replace(tabs1, StringPool.UNDERLINE, StringPool.DASH)), request);
		%>

	</c:when>
</c:choose>