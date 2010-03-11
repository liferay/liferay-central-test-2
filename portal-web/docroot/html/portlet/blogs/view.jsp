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

<%@ include file="/html/portlet/blogs/init.jsp" %>

<%
String topLink = ParamUtil.getString(request, "topLink", "blogs-home");

request.setAttribute("view.jsp-viewBlogs", Boolean.TRUE.toString());
%>

<liferay-util:include page="/html/portlet/blogs/top_links.jsp" />

<c:choose>
	<c:when test='<%= topLink.equals("comments") && GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.UPDATE_DISCUSSION) %>'>
		<%
		String tabs1 = ParamUtil.getString(request, "tabs1", "all");

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("struts_action", "/blogs/view");
		portletURL.setParameter("topLink", "comments");
		%>

		<liferay-ui:tabs
			names="all,approved,pending,spam"
			url="<%= portletURL.toString() %>"
		/>

		<c:if test='<%= tabs1.equals(\"spam\") %>'>
			<div class="portlet-msg-info">
				<liferay-ui:message key="comments-marked-as-spam-will-automatically-be-deleted-in-14-days" />
			</div>
		</c:if>
		<%
		int status = StatusConstants.ANY;

		if (tabs1.equals("approved")) {
			status = StatusConstants.APPROVED;
		}
		else if (tabs1.equals("pending")) {
			status = StatusConstants.PENDING;
		}
		else if (tabs1.equals("spam")) {
			status = StatusConstants.DENIED;
		}

		List<String> headerNames = new ArrayList<String>();

		headerNames.add("user-name");
		headerNames.add("comments");
		headerNames.add("status");
		headerNames.add("blog-entry");
		headerNames.add(StringPool.BLANK);

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

		int count = BlogsEntryLocalServiceUtil.getDiscussionMessagesCount(scopeGroupId, status);

		searchContainer.setTotal(count);

		List results = BlogsEntryLocalServiceUtil.getDiscussionMessages(scopeGroupId, status, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			MBMessage message = (MBMessage)results.get(i);

			BlogsEntry entry = BlogsEntryLocalServiceUtil.getBlogsEntry(message.getClassPK());

			ResultRow row = new ResultRow(message, message.getMessageId(), i);

			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("struts_action", "/blogs/view_entry");
			rowURL.setParameter("urlTitle", HttpUtil.encodeURL(entry.getUrlTitle()));

			// User Name

			User user1 = UserLocalServiceUtil.getUser(message.getUserId());

			String userURL = null;
			String userName = message.getUserName();

			if (!user1.isDefaultUser()) {
				userURL = user1.getDisplayURL(themeDisplay);
			}

			if (Validator.isNull(userName)) {
				userName = LanguageUtil.get(pageContext, "anonymous");
			}

			row.addText(userName, userURL);

			// Comment

			row.addText(message.getBody(true));

			// Status

			String statusMsg = LanguageUtil.get(pageContext, "spam");

			if (message.getStatus() == StatusConstants.APPROVED) {
				statusMsg = LanguageUtil.get(pageContext, "approved");
			}
			else if (message.getStatus() == StatusConstants.PENDING) {
				statusMsg = LanguageUtil.get(pageContext, "pending");
			}

			row.addText(statusMsg, rowURL);

			// Blog Entry

			row.addText(entry.getTitle(), rowURL);

			// Actions

			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/blogs/discussion_action.jsp");

			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
	</c:when>
	<c:otherwise>
		<%
		String redirect = currentURL;

		long categoryId = ParamUtil.getLong(request, "categoryId");

		String categoryName = null;
		String vocabularyName = null;

		if (categoryId != 0) {
			AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getAssetCategory(categoryId);

			categoryName = assetCategory.getName();

			AssetVocabulary assetVocabulary = AssetVocabularyLocalServiceUtil.getAssetVocabulary(assetCategory.getVocabularyId());

			vocabularyName = assetVocabulary.getName();
		}

		String tagName = ParamUtil.getString(request, "tag");

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("struts_action", "/blogs/view");
		%>

		<liferay-portlet:renderURL varImpl="searchURL"><portlet:param name="struts_action" value="/blogs/search" /></liferay-portlet:renderURL>

		<aui:form action="<%= searchURL %>" method="get" name="fm1">
			<liferay-portlet:renderURLParams varImpl="searchURL" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="groupId" type="hidden" value="<%= String.valueOf(scopeGroupId) %>" />

			<%
			SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, pageDelta, portletURL, null, null);

			searchContainer.setDelta(pageDelta);
			searchContainer.setDeltaConfigurable(false);

			AssetEntryQuery assetEntryQuery = new AssetEntryQuery(BlogsEntry.class.getName(), searchContainer);

			assetEntryQuery.setExcludeZeroViewCount(false);

			if (BlogsPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_ENTRY)) {
				assetEntryQuery.setVisible(null);
			}
			else {
				assetEntryQuery.setVisible(true);
			}

			int total = AssetEntryLocalServiceUtil.getEntriesCount(assetEntryQuery);

			searchContainer.setTotal(total);

			List<AssetEntry> results = AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);

			searchContainer.setResults(results);
			%>

			<%@ include file="/html/portlet/blogs/view_entries.jspf" %>
		</aui:form>

		<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
			<aui:script>
				Liferay.Util.focusFormField(document.<portlet:namespace />fm1.<portlet:namespace />keywords);
			</aui:script>
		</c:if>
	</c:otherwise>
</c:choose>