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

<%@ include file="/html/portlet/bookmarks/init.jsp" %>

<%
String topLink = ParamUtil.getString(request, "topLink", "bookmarks-home");

BookmarksFolder folder = (BookmarksFolder)request.getAttribute(WebKeys.BOOKMARKS_FOLDER);

long defaultFolderId = GetterUtil.getLong(preferences.getValue("rootFolderId", StringPool.BLANK), BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", defaultFolderId);

if ((folder == null) && (defaultFolderId != BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	try {
		folder = BookmarksFolderLocalServiceUtil.getFolder(folderId);
	}
	catch (NoSuchFolderException nsfe) {
		folderId = BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

int foldersCount = BookmarksFolderLocalServiceUtil.getFoldersCount(scopeGroupId, folderId);
int entriesCount = BookmarksEntryLocalServiceUtil.getEntriesCount(scopeGroupId, folderId);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/bookmarks/view");
portletURL.setParameter("topLink", topLink);
portletURL.setParameter("folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-folder", folder);

request.setAttribute("view.jsp-folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-viewFolder", Boolean.TRUE.toString());
%>

<liferay-util:include page="/html/portlet/bookmarks/top_links.jsp" />

<c:choose>
	<c:when test='<%= topLink.equals("bookmarks-home") %>'>
		<aui:layout>
			<c:if test="<%= folder != null %>">
				<h3 class="folder-title"><%= folder.getName() %></h3>
			</c:if>

			<aui:column columnWidth="<%= 75 %>" cssClass="folder-column folder-column-first" first="<%= true %>">
				<liferay-ui:panel-container extended="<%= false %>" id="bookmarksPanelContainer" persistState="<%= true %>">
					<c:if test="<%= folder != null %>">
						<div class="folder-description">
							<%= folder.getDescription() %>
						</div>

						<div class="folder-metadata">
							<div class="folder-date">
								<%= LanguageUtil.format(pageContext, "last-updated-x", dateFormatDate.format(folder.getModifiedDate())) %>
							</div>

							<div class="folder-subfolders">
								<%= foldersCount %> <liferay-ui:message key="subfolders" />
							</div>

							<div class="folder-entries">
								<%= entriesCount %> <liferay-ui:message key="entries" />
							</div>
						</div>

						<div class="custom-attributes">
							<liferay-ui:custom-attributes-available className="<%= BookmarksFolder.class.getName() %>">
								<liferay-ui:custom-attribute-list
									className="<%= BookmarksFolder.class.getName() %>"
									classPK="<%= (folder != null) ? folder.getFolderId() : 0 %>"
									editable="<%= false %>"
									label="<%= true %>"
								/>
							</liferay-ui:custom-attributes-available>
						</div>
					</c:if>

					<c:if test="<%= foldersCount > 0 %>">
						<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="subFoldersPanel" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, folder != null ? "subfolders" : "folders") %>'>
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
									<liferay-portlet:renderURL varImpl="rowURL">
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
										buffer.append("<strong>");
										buffer.append(curFolder.getName());
										buffer.append("</strong>");

										if (Validator.isNotNull(curFolder.getDescription())) {
											buffer.append("<br />");
											buffer.append(curFolder.getDescription());
										}

										buffer.append("</a>");

										List subfolders = BookmarksFolderLocalServiceUtil.getFolders(scopeGroupId, curFolder.getFolderId(), 0, 5);

										if (!subfolders.isEmpty()) {
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

									int subFoldersCount = subfolderIds.size() - 1;
									int subEntriesCount = BookmarksEntryLocalServiceUtil.getFoldersEntriesCount(scopeGroupId, subfolderIds);
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
										path="/html/portlet/bookmarks/folder_action.jsp"
									/>
								</liferay-ui:search-container-row>

								<liferay-ui:search-iterator />
							</liferay-ui:search-container>
						</liferay-ui:panel>
					</c:if>

					<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="entriesPanel" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "bookmarks") %>'>

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
							headerNames="name,url,visits,modified-date"
							iteratorURL="<%= portletURL %>"
							orderByCol="<%= orderByCol %>"
							orderByType="<%= orderByType %>"
						>
							<liferay-ui:search-container-results
								results="<%= BookmarksEntryLocalServiceUtil.getEntries(scopeGroupId, folderId, searchContainer.getStart(), searchContainer.getEnd(), orderByComparator) %>"
								total="<%= BookmarksEntryLocalServiceUtil.getEntriesCount(scopeGroupId, folderId) %>"
							/>

							<liferay-ui:search-container-row
								className="com.liferay.portlet.bookmarks.model.BookmarksEntry"
								escapedModel="<%= true %>"
								keyProperty="entryId"
								modelVar="entry"
							>

								<%
								String rowHREF = null;
								String target = null;

								if (BookmarksEntryPermission.contains(permissionChecker, entry, ActionKeys.VIEW)) {
									PortletURL tempRowURL = renderResponse.createRenderURL();

									tempRowURL.setParameter("struts_action", "/bookmarks/view_entry");
									tempRowURL.setParameter("entryId", String.valueOf(entry.getEntryId()));
									tempRowURL.setParameter("redirect", currentURL);

									rowHREF = tempRowURL.toString();
								}
								else {
									rowHREF = themeDisplay.getPathMain().concat("/bookmarks/open_entry?entryId=").concat(String.valueOf(entry.getEntryId()));

									target = "_blank";
								}
								%>

								<liferay-ui:search-container-column-text
									href="<%= rowHREF %>"
									name="name"
									orderable="<%= true %>"
									orderableProperty="name"
									property="name"
									target="<%= target %>"
									title="<%= entry.getComments() %>"
								/>

								<liferay-ui:search-container-column-text
									href="<%= rowHREF %>"
									name="url"
									orderable="<%= true %>"
									property="url"
									target="<%= target %>"
									title="<%= entry.getComments() %>"
								/>

								<liferay-ui:search-container-column-text
									href="<%= rowHREF %>"
									name="visits"
									orderable="<%= true %>"
									property="visits"
									target="<%= target %>"
									title="<%= entry.getComments() %>"
								/>

								<liferay-ui:search-container-column-text
									href="<%= rowHREF %>"
									name="modified-date"
									orderable="<%= true %>"
									target="<%= target %>"
									title="<%= entry.getComments() %>"
									value="<%= dateFormatDate.format(entry.getModifiedDate()) %>"
								/>

								<liferay-ui:search-container-column-jsp
									align="right"
									path="/html/portlet/bookmarks/entry_action.jsp"
								/>
							</liferay-ui:search-container-row>

							<liferay-ui:search-iterator />
						</liferay-ui:search-container>
					</liferay-ui:panel>
				</liferay-ui:panel-container>
			</aui:column>

			<aui:column columnWidth="<%= 25 %>" cssClass="detail-column detail-column-last" last="<%= true %>">
				<div class="folder-icon">
					<liferay-ui:icon
						image='<%= "../file_system/large/" + (((foldersCount + entriesCount) > 0) ? "folder_full_bookmark" : "folder_empty") %>'
						cssClass="folder-avatar"
						message='<%= folder != null ? folder.getName() : LanguageUtil.get(pageContext, "bookmarks-home") %>'
					/>

					<div class="folder-name">
						<h4><%= folder != null ? folder.getName() : LanguageUtil.get(pageContext, "bookmarks-home") %></h4>
					</div>
				</div>

				<%
				request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
				%>

				<liferay-util:include page="/html/portlet/bookmarks/folder_action.jsp" />
			</aui:column>
		</aui:layout>

		<%
		if (folder != null) {
			BookmarksUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);

			if (portletName.equals(PortletKeys.BOOKMARKS)) {
				PortalUtil.setPageSubtitle(folder.getName(), request);
				PortalUtil.setPageDescription(folder.getDescription(), request);
			}
		}
		%>

	</c:when>
	<c:when test='<%= topLink.equals("my-entries") || topLink.equals("recent-entries") %>'>
		<aui:layout>
			<h3 class="folder-title"><liferay-ui:message key="<%= topLink %>" /></h3>

			<liferay-ui:search-container
				headerNames="folder,num-of-folders,num-of-entries"
				iteratorURL="<%= portletURL %>"
			>

				<%
				long groupEntriesUserId = 0;

				if (topLink.equals("my-entries") && themeDisplay.isSignedIn()) {
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
					String rowHREF = themeDisplay.getPathMain().concat("/bookmarks/open_entry?entryId=").concat(String.valueOf(entry.getEntryId()));
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
		</aui:layout>

		<%
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, topLink), currentURL);

		PortalUtil.setPageSubtitle(LanguageUtil.get(pageContext, StringUtil.replace(topLink, StringPool.UNDERLINE, StringPool.DASH)), request);
		%>

	</c:when>
</c:choose>