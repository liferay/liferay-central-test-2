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
String tabs1 = ParamUtil.getString(request, "tabs1", "folders");

DLFolder folder = (DLFolder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long defaultFolderId = GetterUtil.getLong(preferences.getValue("rootFolderId", StringPool.BLANK), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", defaultFolderId);

if ((folder == null) && (defaultFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	try {
		folder = DLFolderLocalServiceUtil.getFolder(folderId);
	}
	catch (NoSuchFolderException nsfe) {
		folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

int status = StatusConstants.APPROVED;

if (permissionChecker.isCompanyAdmin() || permissionChecker.isCommunityAdmin(scopeGroupId)) {
	status = StatusConstants.ANY;
}

int foldersCount = DLFolderLocalServiceUtil.getFoldersCount(scopeGroupId, folderId);
int fileEntriesCount = DLFolderLocalServiceUtil.getFileEntriesAndFileShortcutsCount(scopeGroupId, folderId, status);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/document_library/view");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-folder", folder);
request.setAttribute("view.jsp-folderId", folderId);
%>

<c:if test="<%= folder == null %>">
	<liferay-util:include page="/html/portlet/document_library/tabs1.jsp" />
</c:if>

<c:choose>
	<c:when test='<%= tabs1.equals("folders") %>'>
		<aui:layout>
			<c:if test="<%= folder != null %>">
				<h3 class="folder-title"><%= folder.getName() %></h3>
			</c:if>

			<aui:column columnWidth="<%= 75 %>" cssClass="folder-column folder-column-first" first="<%= true %>">
				<liferay-ui:panel-container id="documentLibraryPanels" extended="<%= Boolean.FALSE %>" persistState="<%= true %>">
					<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" varImpl="searchURL">
						<portlet:param name="struts_action" value="/document_library/search" />
					</liferay-portlet:renderURL>

					<aui:form action="<%= searchURL %>" method="get" name="fm" onSubmit="submitForm(this); return false;">
						<liferay-portlet:renderURLParams varImpl="searchURL" />
						<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
						<aui:input name="breadcrumbsFolderId" type="hidden" value="<%= folderId %>" />
						<aui:input name="searchFolderIds" type="hidden" value="<%= folderId %>" />

						<c:if test="<%= showSubfolders %>">
							<c:if test="<%= showFoldersSearch %>">
								<div class="folder-search">
									<aui:input cssClass="input-text-search" id="keywords1" label="" name="keywords" size="30" type="text" />

									<aui:button type="submit" value="search" />
								</div>
							</c:if>

							<c:if test="<%= folder != null %>">
								<div class="folder-description">
									<%= folder.getDescription() %>
								</div>

								<div class="folder-metadata">
									<div class="folder-date">
										<%= LanguageUtil.format(pageContext, "last-updated-x", dateFormatDateTime.format(folder.getModifiedDate())) %>
									</div>

									<div class="folder-subfolders">
										<%= foldersCount %> <liferay-ui:message key="subfolders" />
									</div>

									<div class="folder-file-entries">
										<%= fileEntriesCount %> <liferay-ui:message key="documents" />
									</div>
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
							</c:if>

							<c:if test="<%= foldersCount > 0 %>">
								<liferay-ui:panel id="subFoldersPanel" title='<%= LanguageUtil.get(pageContext, folder != null ? "subfolders" : "folders") %>' collapsible="<%= true %>" persistState="<%= true %>" extended="<%= true %>">
									<liferay-ui:search-container
										curParam="cur1"
										delta="<%= foldersPerPage %>"
										headerNames="<%= StringUtil.merge(folderColumns) %>"
										iteratorURL="<%= portletURL %>"
									>
										<liferay-ui:search-container-results
											results="<%= DLFolderLocalServiceUtil.getFolders(scopeGroupId, folderId, searchContainer.getStart(), searchContainer.getEnd()) %>"
											total="<%= DLFolderLocalServiceUtil.getFoldersCount(scopeGroupId, folderId) %>"
										/>

										<liferay-ui:search-container-row
											className="com.liferay.portlet.documentlibrary.model.DLFolder"
											escapedModel="<%= true %>"
											keyProperty="folderId"
											modelVar="curFolder"
										>
											<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" varImpl="rowURL">
												<portlet:param name="struts_action" value="/document_library/view" />
												<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
											</liferay-portlet:renderURL>

											<%@ include file="/html/portlet/document_library/folder_columns.jspf" %>
										</liferay-ui:search-container-row>

										<liferay-ui:search-iterator />
									</liferay-ui:search-container>
								</liferay-ui:panel>
							</c:if>
						</c:if>
					</aui:form>

					<script type="text/javascript">
						<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
							Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />keywords);
						</c:if>
					</script>

					<c:if test="<%= showSubfolders %>">
						<br />
					</c:if>

					<c:choose>
						<c:when test="<%= showSubfolders && showTabs %>">
							<liferay-ui:panel id="documentsPanel" title='<%= LanguageUtil.get(pageContext, "documents") %>' collapsible="<%= true %>" persistState="<%= true %>" extended="<%= true %>">
								<%@ include file="/html/portlet/document_library/view_file_entries.jspf" %>
							</liferay-ui:panel>
						</c:when>
						<c:otherwise>
							<%@ include file="/html/portlet/document_library/view_file_entries.jspf" %>
						</c:otherwise>
					</c:choose>
				</liferay-ui:panel-container>
			</aui:column>

			<aui:column columnWidth="<%= 25 %>" cssClass="detail-column detail-column-last" last="<%= true %>">
				<div class="folder-icon">
					<liferay-ui:icon
						image='<%= "../document_library/folder" + (((foldersCount + fileEntriesCount) > 0) ? "_full" : StringPool.BLANK) %>'
						cssClass="folder-avatar"
						message='<%= folder != null ? folder.getName() : LanguageUtil.get(pageContext, "document-home") %>'
					/>

					<div class="folder-name">
						<h4><%= folder != null ? folder.getName() : LanguageUtil.get(pageContext, "document-home") %></h4>
					</div>
				</div>

				<%
				request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
				%>

				<liferay-util:include page="/html/portlet/document_library/folder_action.jsp" />
			</aui:column>
		</aui:layout>

		<%
		if (folder != null) {
			DLUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);

			if (portletName.equals(PortletKeys.DOCUMENT_LIBRARY)) {
				PortalUtil.setPageSubtitle(folder.getName(), request);
				PortalUtil.setPageDescription(folder.getDescription(), request);
			}
		}
		%>

	</c:when>
	<c:when test='<%= tabs1.equals("my-documents") || tabs1.equals("recent-documents") %>'>

		<%
		long groupFileEntriesUserId = 0;

		if (tabs1.equals("my-documents") && themeDisplay.isSignedIn()) {
			groupFileEntriesUserId = user.getUserId();
		}
		%>

		<liferay-ui:search-container
			delta="<%= fileEntriesPerPage %>"
			iteratorURL="<%= portletURL %>"
		>
			<liferay-ui:search-container-results
				results="<%= DLFileEntryLocalServiceUtil.getGroupFileEntries(scopeGroupId, groupFileEntriesUserId, searchContainer.getStart(), searchContainer.getEnd()) %>"
				total="<%= DLFileEntryLocalServiceUtil.getGroupFileEntriesCount(scopeGroupId, groupFileEntriesUserId) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portlet.documentlibrary.model.DLFileEntry"
				escapedModel="<%= true %>"
				keyProperty="fileEntryId"
				modelVar="fileEntry"
			>

				<%
				String rowHREF = null;

				if (DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.VIEW)) {
					rowHREF = themeDisplay.getPathMain() + "/document_library/get_file?p_l_id=" + themeDisplay.getPlid() + "&groupId=" + themeDisplay.getScopeGroupId() + "&folderId=" + fileEntry.getFolderId() + "&title=" + HttpUtil.encodeURL(HtmlUtil.unescape(fileEntry.getTitle()));
				}
				%>

				<%@ include file="/html/portlet/document_library/file_entry_columns.jspf" %>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>

		<%
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, tabs1), currentURL);

		PortalUtil.setPageSubtitle(LanguageUtil.get(pageContext, tabs1), request);
		%>

	</c:when>
</c:choose>