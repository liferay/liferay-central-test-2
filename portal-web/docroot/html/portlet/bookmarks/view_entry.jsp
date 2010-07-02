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

<%@ include file="/html/portlet/bookmarks/init.jsp" %>

<%
BookmarksEntry entry = (BookmarksEntry)request.getAttribute(WebKeys.BOOKMARKS_ENTRY);

entry = entry.toEscapedModel();

long entryId = entry.getEntryId();

BookmarksFolder folder = entry.getFolder();

request.setAttribute("view_entry.jsp-entry", entry);
%>

<liferay-util:include page="/html/portlet/bookmarks/top_links.jsp" />

<c:if test="<%= folder != null %>">
	<portlet:renderURL var="backURL">
		<portlet:param name="struts_action" value="/bookmarks/view" />
		<portlet:param name="folderId" value="<%= String.valueOf(folder.getFolderId()) %>" />
	</portlet:renderURL>

	<%
	String folderName = LanguageUtil.get(pageContext, "bookmarks-home");

	if (Validator.isNotNull(folder.getName())) {
		folderName = folder.getName();
	}
	%>

	<liferay-ui:header
		backLabel='<%= "&laquo; " + LanguageUtil.format(pageContext, "back-to-x", HtmlUtil.escape(folderName)) %>'
		backURL="<%= backURL.toString() %>"
		title="<%= entry.getName() %>"
	/>
</c:if>

<aui:layout>
	<aui:column columnWidth="<%= 75 %>" cssClass="asset-column asset-column-details" first="<%= true %>">
		<div class="asset-categories">
			<liferay-ui:asset-categories-summary
				className="<%= BookmarksEntry.class.getName() %>"
				classPK="<%= entryId %>"
			/>
		</div>

		<div class="asset-tags">
			<liferay-ui:asset-tags-summary
				className="<%= BookmarksEntry.class.getName() %>"
				classPK="<%= entryId %>"
				message="tags"
			/>
		</div>

		<div class="asset-url">
			<a href="<%= themeDisplay.getPathMain() %>/bookmarks/open_entry?entryId=<%= entry.getEntryId() %>"><%= entry.getUrl() %></a>
		</div>

		<div class="asset-description">
			<%= entry.getComments() %>
		</div>

		<liferay-ui:custom-attributes-available className="<%= BookmarksEntry.class.getName() %>">
			<liferay-ui:custom-attribute-list
				className="<%= BookmarksEntry.class.getName() %>"
				classPK="<%= (entry != null) ? entry.getEntryId() : 0 %>"
				editable="<%= false %>"
				label="<%= true %>"
			/>
		</liferay-ui:custom-attributes-available>

		<div class="asset-metadata">
			<div class="asset-icon asset-author">
				<%= LanguageUtil.format(pageContext, "created-by-x", HtmlUtil.escape(PortalUtil.getUserName(entry.getUserId(), themeDisplay.getScopeGroupName()))) %>
			</div>

			<div class="asset-icon asset-date">
				<%= dateFormatDate.format(entry.getCreateDate()) %>
			</div>

			<div class="asset-icon asset-downloads last">
				<%= entry.getVisits() %> <liferay-ui:message key="visits" />
			</div>
		</div>

		<div class="asset-ratings">
			<liferay-ui:ratings
				className="<%= BookmarksEntry.class.getName() %>"
				classPK="<%= entryId %>"
			/>
		</div>
	</aui:column>

	<aui:column columnWidth="<%= 25 %>" cssClass="asset-column asset-column-actions" last="<%= true %>">
		<div class="asset-summary">
			<liferay-ui:icon
				cssClass="asset-avatar"
				image="../file_system/large/bookmark"
				message="download"
				url="<%= entry.getUrl() %>"
			/>

			<div class="asset-name">
				<a href="<%= entry.getUrl() %>">
					<%= entry.getName() %>
				</a>
			</div>
		</div>

		<%
		request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
		%>

		<liferay-util:include page="/html/portlet/bookmarks/entry_action.jsp" />
	</aui:column>
</aui:layout>

<%
BookmarksUtil.addPortletBreadcrumbEntries(entry, request, renderResponse);
%>