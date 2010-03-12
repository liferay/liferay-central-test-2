<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
%>

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String strutsAction = ParamUtil.getString(request, "struts_action");

String tabs2 = ParamUtil.getString(request, "tabs2", "version-history");

String redirect = ParamUtil.getString(request, "redirect");

DLFileShortcut fileShortcut = (DLFileShortcut)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_SHORTCUT);

fileShortcut = fileShortcut.toEscapedModel();

long fileShortcutId = fileShortcut.getFileShortcutId();
long toFolderId = fileShortcut.getToFolderId();
String toName = fileShortcut.getToName();

Group toGroup = null;
DLFolder toFolder = null;
DLFileEntry toFileEntry = null;

if (Validator.isNotNull(toName)) {
	try {
		toFileEntry = DLFileEntryLocalServiceUtil.getFileEntry(scopeGroupId, toFolderId, toName);
		toGroup = GroupLocalServiceUtil.getGroup(toFileEntry.getGroupId());

		if (toFolderId > 0) {
			toFolder = DLFolderLocalServiceUtil.getFolder(toFolderId);
		}
	}
	catch (Exception e) {
	}
}
else if (toFolderId > 0) {
	try {
		toFolder = DLFolderLocalServiceUtil.getFolder(toFolderId);
		toGroup = GroupLocalServiceUtil.getGroup(toFolder.getGroupId());
	}
	catch (Exception e) {
	}
}

toFileEntry = toFileEntry.toEscapedModel();

String extension = null;

if (Validator.isNotNull(toFileEntry.getName())) {
	extension = FileUtil.getExtension(toFileEntry.getTitle());
}

String[] conversions = new String[0];

if (PrefsPropsUtil.getBoolean(PropsKeys.OPENOFFICE_SERVER_ENABLED, PropsValues.OPENOFFICE_SERVER_ENABLED)) {
	conversions = (String[])DocumentConversionUtil.getConversions(extension);
}

Lock lock = null;
Boolean isLocked = Boolean.FALSE;
Boolean hasLock = Boolean.FALSE;

try {
	lock = LockLocalServiceUtil.getLock(DLFileEntry.class.getName(), DLUtil.getLockId(toFileEntry.getGroupId(), toFileEntry.getFolderId(), toFileEntry.getName()));

	isLocked = Boolean.TRUE;

	if (lock.getUserId() == user.getUserId()) {
		hasLock = Boolean.TRUE;
	}
}
catch (Exception e) {
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", strutsAction);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("fileShortcutId", String.valueOf(fileShortcutId));

request.setAttribute("view_file_shortcut.jsp-fileShortcut", fileShortcut);
%>

<liferay-util:include page="/html/portlet/document_library/top_links.jsp" />

<aui:layout>
	<aui:column columnWidth="<%= 75 %>" cssClass="file-entry-column file-entry-column-first" first="<%= true %>">

		<%
		String versionText = LanguageUtil.format(pageContext, "version-x", toFileEntry.getVersion());

		if (Validator.isNull(toFileEntry.getVersion())) {
			versionText = LanguageUtil.get(pageContext, "not-approved");
		}
		%>

		<h3 class="file-entry-title"><%= LanguageUtil.format(pageContext, "shortcut-to-x", toFileEntry.getTitle() + " (" + versionText + ")") %></h3>

		<div class="file-entry-categories">
			<liferay-ui:asset-categories-summary
				className="<%= DLFileEntry.class.getName() %>"
				classPK="<%= toFileEntry.getFileEntryId() %>"
			/>
		</div>

		<div class="file-entry-tags">
			<liferay-ui:asset-tags-summary
				className="<%= DLFileEntry.class.getName() %>"
				classPK="<%= toFileEntry.getFileEntryId() %>"
				message="tags"
			/>
		</div>

		<div class="file-entry-description">
			<%= toFileEntry.getDescription() %>
		</div>

		<div class="custom-attributes">
			<liferay-ui:custom-attributes-available className="<%= DLFileEntry.class.getName() %>">
				<liferay-ui:custom-attribute-list
					className="<%= DLFileEntry.class.getName() %>"
					classPK="<%= (toFileEntry != null) ? toFileEntry.getFileEntryId() : 0 %>"
					editable="<%= false %>"
					label="<%= true %>"
				/>
			</liferay-ui:custom-attributes-available>
		</div>

		<div class="file-entry-author">
			<%= LanguageUtil.format(pageContext, "last-updated-by-x", HtmlUtil.escape(PortalUtil.getUserName(toFileEntry.getUserId(), toFileEntry.getUserName()))) %>
		</div>

		<div class="file-entry-date">
			<%= dateFormatDateTime.format(toFileEntry.getModifiedDate()) %>
		</div>

		<div class="file-entry-downloads">
			<%= toFileEntry.getReadCount() %> <liferay-ui:message key="downloads" />
		</div>

		<div class="file-entry-ratings">
			<liferay-ui:ratings
				className="<%= DLFileEntry.class.getName() %>"
				classPK="<%= toFileEntry.getFileEntryId() %>"
			/>
		</div>

		<div class="file-entry-field">
			<label><liferay-ui:message key="url" /></label>

			<liferay-ui:input-resource
				url='<%= themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/document/" + fileShortcutId %>'
			/>
		</div>

		<div class="file-entry-field">
			<label><liferay-ui:message key="community" /></label>

			<%= HtmlUtil.escape(toGroup.getDescriptiveName()) %>
		</div>
	</aui:column>

	<aui:column columnWidth="<%= 25 %>" cssClass="detail-column detail-column-last" last="<%= true %>">
		<img alt="" class="shortcut-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_link.png">

		<c:if test="<%= isLocked %>">
			<img alt="" class="locked-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_lock.png">
		</c:if>

		<div class="file-entry-download">
			<liferay-ui:icon
				cssClass="file-entry-avatar"
				image='<%= "../file_system/large/" + DLUtil.getGenericName(extension) %>'
				message="download"
				url='<%= themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/document/" + fileShortcutId %>'
			/>

			<div class="file-entry-name">
				<a href="<%= themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/document/" + fileShortcutId %>">
					<%= toFileEntry.getTitle() %>
				</a>
			</div>

			<c:if test="<%= conversions.length > 0 %>">
				<div class="file-entry-field file-entry-conversions">
					<label><liferay-ui:message key="other-available-formats" /></label>

					<%
					for (int i = 0; i < conversions.length; i++) {
						String conversion = conversions[i];
					%>

						<liferay-ui:icon
							image='<%= "../file_system/small/" + conversion %>'
							label="<%= true %>"
							message="<%= conversion.toUpperCase() %>"
							url='<%= themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/document/" + themeDisplay.getScopeGroupId() + StringPool.SLASH + toFolder.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(toFileEntry.getTitle()) + "?targetExtension=" + conversion %>'
						/>

					<%
					}
					%>

				</div>
			</c:if>
		</div>

		<liferay-util:include page="/html/portlet/document_library/file_entry_action.jsp" />
	</aui:column>
</aui:layout>

<div class="file-entry-panels">
	<liferay-ui:panel-container extended="<%= false %>" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" cssClass="version-history" extended="<%= true %>" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "version-history") %>'>

			<%
			boolean showNonApprovedDocuments = false;

			if ((user.getUserId() == fileShortcut.getUserId()) || permissionChecker.isCompanyAdmin() || permissionChecker.isCommunityAdmin(scopeGroupId)) {
				showNonApprovedDocuments = true;
			}

			SearchContainer searchContainer = new SearchContainer();

			List<String> headerNames = new ArrayList<String>();

			headerNames.add("version");
			headerNames.add("date");
			headerNames.add("size");

			if (showNonApprovedDocuments) {
				headerNames.add("status");
			}

			headerNames.add(StringPool.BLANK);

			searchContainer.setHeaderNames(headerNames);

			int status = StatusConstants.APPROVED;

			if (showNonApprovedDocuments) {
				status = StatusConstants.ANY;
			}

			List results = DLFileVersionLocalServiceUtil.getFileVersions(scopeGroupId, toFileEntry.getFolderId(), toFileEntry.getName(), status);
			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				DLFileVersion fileVersion = (DLFileVersion)results.get(i);

				ResultRow row = new ResultRow(new Object[] {toFileEntry, fileVersion, conversions, portletURL, isLocked, hasLock}, fileVersion.getFileVersionId(), i);

				StringBundler sb = new StringBundler(6);

				sb.append(themeDisplay.getPortalURL());
				sb.append(themeDisplay.getPathContext());
				sb.append("/document/");
				sb.append(fileShortcutId);
				sb.append("?version=");
				sb.append(String.valueOf(fileVersion.getVersion()));

				String rowHREF = sb.toString();

				// Statistics

				row.addText(String.valueOf(fileVersion.getVersion()), rowHREF);
				row.addText(dateFormatDateTime.format(fileVersion.getCreateDate()), rowHREF);
				row.addText(TextFormatter.formatKB(fileVersion.getSize(), locale) + "k", rowHREF);

				// Status

				if (showNonApprovedDocuments) {
					row.addText(LanguageUtil.get(pageContext, (fileVersion.getStatus() == StatusConstants.APPROVED)? "approved" : "not-approved"));
				}

				// Action

				row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/document_library/file_version_action.jsp");

				// Add result row

				resultRows.add(row);
			}
			%>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= false %>" />
		</liferay-ui:panel>

		<c:if test="<%= PropsValues.DL_FILE_ENTRY_COMMENTS_ENABLED && DLFileShortcutPermission.contains(permissionChecker, fileShortcut, ActionKeys.ADD_DISCUSSION) %>">
			<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "comments") %>'>
				<portlet:actionURL var="discussionURL">
					<portlet:param name="struts_action" value="/document_library/edit_file_entry_discussion" />
				</portlet:actionURL>

				<liferay-ui:discussion
					className="<%= DLFileEntry.class.getName() %>"
					classPK="<%= toFileEntry.getFileEntryId() %>"
					formAction="<%= discussionURL %>"
					formName="fm2"
					ratingsEnabled="<%= enableCommentRatings %>"
					redirect="<%= currentURL %>"
					subject="<%= toFileEntry.getTitle() %>"
					userId="<%= toFileEntry.getUserId() %>"
				/>
			</liferay-ui:panel>
		</c:if>
	</liferay-ui:panel-container>
</div>

<%
DLUtil.addPortletBreadcrumbEntries(fileShortcut, request, renderResponse);
%>