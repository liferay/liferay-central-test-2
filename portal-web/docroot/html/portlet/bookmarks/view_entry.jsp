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
BookmarksEntry entry = (BookmarksEntry)request.getAttribute(WebKeys.BOOKMARKS_ENTRY);

entry = entry.toEscapedModel();

long entryId = entry.getEntryId();

request.setAttribute("view_entry.jsp-entry", entry);
%>

<liferay-util:include page="/html/portlet/bookmarks/top_links.jsp" />

<aui:layout>
	<aui:column columnWidth="<%= 75 %>" cssClass="entry-column entry-column-first" first="<%= true %>">
		<h3 class="entry-title"><%= entry.getName() %></h3>

		<div class="entry-categories">
			<liferay-ui:asset-categories-summary
				className="<%= BookmarksEntry.class.getName() %>"
				classPK="<%= entryId %>"
			/>
		</div>

		<div class="entry-tags">
			<liferay-ui:asset-tags-summary
				className="<%= BookmarksEntry.class.getName() %>"
				classPK="<%= entryId %>"
				message="tags"
			/>
		</div>

		<div class="entry-url">
			<a href="<%= themeDisplay.getPathMain() %>/bookmarks/open_entry?entryId=<%= entry.getEntryId() %>"><%= entry.getUrl() %></a>
		</div>

		<div class="entry-description">
			<%= entry.getComments() %>
		</div>

		<div class="custom-attributes">
			<liferay-ui:custom-attributes-available className="<%= BookmarksEntry.class.getName() %>">
				<liferay-ui:custom-attribute-list
					className="<%= BookmarksEntry.class.getName() %>"
					classPK="<%= (entry != null) ? entry.getEntryId() : 0 %>"
					editable="<%= false %>"
					label="<%= true %>"
				/>
			</liferay-ui:custom-attributes-available>
		</div>

		<div class="entry-author">
			<%= LanguageUtil.format(pageContext, "created-by-x", PortalUtil.getUserName(entry.getUserId(), themeDisplay.getScopeGroupName())) %>
		</div>

		<div class="entry-date">
			<%= dateFormatDate.format(entry.getCreateDate()) %>
		</div>

		<div class="entry-downloads">
			<%= entry.getVisits() %> <liferay-ui:message key="visits" />
		</div>

		<div class="entry-ratings">
			<liferay-ui:ratings
				className="<%= BookmarksEntry.class.getName() %>"
				classPK="<%= entryId %>"
			/>
		</div>
	</aui:column>

	<aui:column columnWidth="<%= 25 %>" cssClass="detail-column detail-column-last" last="<%= true %>">
		<div class="entry-download">
			<liferay-ui:icon
				image="../file_system/large/bookmark"
				message="download"
				url="<%= entry.getUrl() %>"
				cssClass="entry-avatar"
			/>

			<div class="entry-name">
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