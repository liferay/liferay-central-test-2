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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
String topLink = ParamUtil.getString(request, "topLink", "message-boards-home");

String redirect = ParamUtil.getString(request, "redirect");

MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = BeanParamUtil.getLong(category, request, "mbCategoryId", MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

MBCategoryDisplay categoryDisplay = new MBCategoryDisplayImpl(scopeGroupId, categoryId);

Set<Long> categorySubscriptionClassPKs = null;
Set<Long> threadSubscriptionClassPKs = null;

if (themeDisplay.isSignedIn()) {
	List<Subscription> categorySubscriptions = SubscriptionLocalServiceUtil.getUserSubscriptions(user.getUserId(), MBCategory.class.getName());

	categorySubscriptionClassPKs = new HashSet<Long>(categorySubscriptions.size());

	for (Subscription subscription : categorySubscriptions) {
		categorySubscriptionClassPKs.add(subscription.getClassPK());
	}

	threadSubscriptionClassPKs = new HashSet<Long>();

	List<Subscription> threadSubscriptions = SubscriptionLocalServiceUtil.getUserSubscriptions(user.getUserId(), MBThread.class.getName());

	threadSubscriptionClassPKs = new HashSet<Long>(threadSubscriptions.size());

	for (Subscription subscription : threadSubscriptions) {
		threadSubscriptionClassPKs.add(subscription.getClassPK());
	}
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/message_boards/view");
portletURL.setParameter("topLink", topLink);
portletURL.setParameter("mbCategoryId", String.valueOf(categoryId));

request.setAttribute("view.jsp-viewCategory", Boolean.TRUE.toString());
%>

<liferay-util:include page="/html/portlet/message_boards/top_links.jsp" />

<c:choose>
	<c:when test='<%= topLink.equals("message-boards-home") %>'>
		<c:if test="<%= category == null %>">
			<div class="category-subscriptions">
				<div class="category-subscription-types">
					<liferay-ui:icon image="rss" label="<%= true %>" method="get" target="_blank" url='<%= themeDisplay.getPortalURL() + themeDisplay.getPathMain() + "/message_boards/rss?p_l_id=" + plid + "&mbCategoryId=" + scopeGroupId + rssURLParams %>' />

					<c:if test="<%= MBPermission.contains(permissionChecker, scopeGroupId, ActionKeys.SUBSCRIBE) %>">
						<c:choose>
							<c:when test="<%= SubscriptionLocalServiceUtil.isSubscribed(user.getCompanyId(), user.getUserId(), MBCategory.class.getName(), scopeGroupId) %>">
								<portlet:actionURL var="unsubscribeURL">
									<portlet:param name="struts_action" value="/message_boards/edit_category" />
									<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
									<portlet:param name="redirect" value="<%= currentURL %>" />
									<portlet:param name="mbCategoryId" value="<%= String.valueOf(MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) %>" />
								</portlet:actionURL>

								<liferay-ui:icon image="unsubscribe" label="<%= true %>" url="<%= unsubscribeURL %>" />
							</c:when>
							<c:otherwise>
								<portlet:actionURL var="subscribeURL">
									<portlet:param name="struts_action" value="/message_boards/edit_category" />
									<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
									<portlet:param name="redirect" value="<%= currentURL %>" />
									<portlet:param name="mbCategoryId" value="<%= String.valueOf(MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) %>" />
								</portlet:actionURL>

								<liferay-ui:icon image="subscribe" label="<%= true %>" url="<%= subscribeURL %>" />
							</c:otherwise>
						</c:choose>
					</c:if>
				</div>
			</div>
		</c:if>

		<%
		boolean showAddCategoryButton = MBCategoryPermission.contains(permissionChecker, scopeGroupId, categoryId, ActionKeys.ADD_CATEGORY);
		boolean showAddMessageButton = MBCategoryPermission.contains(permissionChecker, scopeGroupId, categoryId, ActionKeys.ADD_MESSAGE);
		boolean showPermissionsButton = GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS);

		if (showAddMessageButton && !themeDisplay.isSignedIn()) {
			if (!allowAnonymousPosting) {
				showAddMessageButton = false;
			}
		}
		%>

		<c:if test="<%= showAddCategoryButton || showAddMessageButton || showPermissionsButton %>">
			<div class="category-buttons">
				<c:if test="<%= showAddCategoryButton %>">
					<portlet:renderURL var="editCategoryURL">
						<portlet:param name="struts_action" value="/message_boards/edit_category" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="parentCategoryId" value="<%= String.valueOf(categoryId) %>" />
					</portlet:renderURL>

					<aui:button onClick='<%= editCategoryURL %>' value='<%= (category == null) ? "add-category" : "add-subcategory" %>' />
				</c:if>

				<c:if test="<%= showAddMessageButton %>">
					<portlet:renderURL var="editMessageURL">
						<portlet:param name="struts_action" value="/message_boards/edit_message" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="mbCategoryId" value="<%= String.valueOf(categoryId) %>" />
					</portlet:renderURL>

					<aui:button onClick='<%= editMessageURL %>' value="post-new-thread" />
				</c:if>

				<c:if test="<%= showPermissionsButton %>">

					<%
					String modelResource = "com.liferay.portlet.messageboards";
					String modelResourceDescription = themeDisplay.getScopeGroupName();
					String resourcePrimKey = String.valueOf(scopeGroupId);

					if (category != null) {
						modelResource = MBCategory.class.getName();
						modelResourceDescription = category.getName();
						resourcePrimKey = String.valueOf(category.getCategoryId());
					}
					%>

					<liferay-security:permissionsURL
						modelResource="<%= modelResource %>"
						modelResourceDescription="<%= HtmlUtil.escape(modelResourceDescription) %>"
						resourcePrimKey="<%= resourcePrimKey %>"
						var="permissionsURL"
					/>

					<aui:button onClick="<%= permissionsURL %>" value="permissions" />
				</c:if>
			</div>
		</c:if>

		<liferay-ui:panel-container cssClass="message-boards-panels" extended="<%= false %>" id="messageBoardsPanelContainer" persistState="<%= true %>">

			<%
			int totalCategories = MBCategoryServiceUtil.getCategoriesCount(scopeGroupId, categoryId);
			%>

			<c:if test="<%= totalCategories > 0 %>">
				<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="messageBoardsCategoriesPanel" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, category != null ? "subcategories" : "categories") %>'>

					<liferay-ui:search-container
						curParam="cur1"
						deltaConfigurable="<%= false %>"
						headerNames="<%= "category,categories,threads,posts" %>"
						iteratorURL="<%= portletURL %>"
					>
						<liferay-ui:search-container-results
							results="<%= MBCategoryServiceUtil.getCategories(scopeGroupId, categoryId, searchContainer.getStart(), searchContainer.getEnd()) %>"
							total="<%= totalCategories %>"
						/>

						<liferay-ui:search-container-row
							className="com.liferay.portlet.messageboards.model.MBCategory"
							escapedModel="<%= true %>"
							keyProperty="categoryId"
							modelVar="curCategory"
						>

							<liferay-ui:search-container-row-parameter name="categorySubscriptionClassPKs" value="<%= categorySubscriptionClassPKs %>" />

							<liferay-portlet:renderURL varImpl="rowURL">
								<portlet:param name="struts_action" value="/message_boards/view" />
								<portlet:param name="mbCategoryId" value="<%= String.valueOf(curCategory.getCategoryId()) %>" />
							</liferay-portlet:renderURL>

							<%@ include file="/html/portlet/message_boards/category_columns.jspf" %>

						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator />
					</liferay-ui:search-container>

				</liferay-ui:panel>
			</c:if>

			<%
			List<String> headerNames = new ArrayList<String>();

			headerNames.add("thread");
			headerNames.add("status");
			headerNames.add("started-by");
			headerNames.add("posts");
			headerNames.add("views");
			headerNames.add("last-post");
			headerNames.add(StringPool.BLANK);

			SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur2", SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

			int total = MBThreadLocalServiceUtil.getThreadsCount(scopeGroupId, categoryId, WorkflowConstants.STATUS_APPROVED);

			searchContainer.setTotal(total);

			List results = MBThreadLocalServiceUtil.getThreads(scopeGroupId, categoryId, WorkflowConstants.STATUS_APPROVED, searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);

			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				MBThread thread = (MBThread)results.get(i);

				MBMessage message = MBMessageLocalServiceUtil.getMessage(thread.getRootMessageId());

				message = message.toEscapedModel();

				boolean readThread = MBMessageFlagLocalServiceUtil.hasReadFlag(themeDisplay.getUserId(), thread);

				ResultRow row = new ResultRow(new Object[] {message, threadSubscriptionClassPKs}, thread.getThreadId(), i, !readThread);

				row.setRestricted(!MBMessagePermission.contains(permissionChecker, message, ActionKeys.VIEW));

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("struts_action", "/message_boards/view_message");
				rowURL.setParameter("messageId", String.valueOf(message.getMessageId()));

				// Thread

				StringBundler sb = new StringBundler();

				String[] threadPriority = MBUtil.getThreadPriority(preferences, themeDisplay.getLanguageId(), thread.getPriority(), themeDisplay);

				if ((threadPriority != null) && (thread.getPriority() > 0)) {
					sb.append("<img align=\"left\" alt=\"");
					sb.append(threadPriority[0]);
					sb.append("\" border=\"0\" src=\"");
					sb.append(threadPriority[1]);
					sb.append("\" title=\"");
					sb.append(threadPriority[0]);
					sb.append("\" />");
				}

				if (thread.isLocked()) {
					sb.append("<img align=\"left\" alt=\"");
					sb.append(LanguageUtil.get(pageContext, "thread-locked"));
					sb.append("\" border=\"0\" src=\"");
					sb.append(themeDisplay.getPathThemeImages() + "/common/lock.png");
					sb.append("\" title=\"");
					sb.append(LanguageUtil.get(pageContext, "thread-locked"));
					sb.append("\" />");
				}

				sb.append(message.getSubject());

				row.addText(sb.toString(), rowURL);

				// Status

				sb.setIndex(0);

				if (MBMessageFlagLocalServiceUtil.hasQuestionFlag(message.getMessageId())) {
					sb.append(LanguageUtil.get(pageContext, "waiting-for-an-answer"));
				}
				if (MBMessageFlagLocalServiceUtil.hasAnswerFlag(message.getMessageId())) {
					sb.append(LanguageUtil.get(pageContext, "resolved"));
				}

				row.addText(sb.toString(), rowURL);

				// Started by

				if (message.isAnonymous()) {
					row.addText(LanguageUtil.get(pageContext, "anonymous"), rowURL);
				}
				else {
					row.addText(HtmlUtil.escape(PortalUtil.getUserName(message.getUserId(), message.getUserName())), rowURL);
				}

				// Number of posts

				row.addText(String.valueOf(thread.getMessageCount()), rowURL);

				// Number of views

				row.addText(String.valueOf(thread.getViewCount()), rowURL);

				// Last post

				if (thread.getLastPostDate() == null) {
					row.addText(LanguageUtil.get(pageContext, "none"), rowURL);
				}
				else {
					sb.setIndex(0);

					sb.append(LanguageUtil.get(pageContext, "date"));
					sb.append(": ");
					sb.append(dateFormatDateTime.format(thread.getLastPostDate()));

					String lastPostByUserName = HtmlUtil.escape(PortalUtil.getUserName(thread.getLastPostByUserId(), StringPool.BLANK));

					if (Validator.isNotNull(lastPostByUserName)) {
						sb.append("<br />");
						sb.append(LanguageUtil.get(pageContext, "by"));
						sb.append(": ");
						sb.append(lastPostByUserName);
					}

					row.addText(sb.toString(), rowURL);
				}

				// Action

				row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/message_boards/message_action.jsp");

				// Add result row

				resultRows.add(row);
			}
			%>

			<liferay-ui:panel collapsible="<%= true %>" cssClass="threads-panel" extended="<%= true %>" id="messageBoardsThreadsPanel" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "threads") %>'>
				<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
			</liferay-ui:panel>
		</liferay-ui:panel-container>

		<%
		if (category != null) {
			PortalUtil.setPageSubtitle(category.getName(), request);
			PortalUtil.setPageDescription(category.getDescription(), request);

			MBUtil.addPortletBreadcrumbEntries(category, request, renderResponse);
		}
		%>

	</c:when>
	<c:when test='<%= topLink.equals("my-posts") || topLink.equals("my-subscriptions") || topLink.equals("recent-posts") %>'>

		<%
		long groupThreadsUserId = ParamUtil.getLong(request, "groupThreadsUserId");

		if ((topLink.equals("my-posts") || topLink.equals("my-subscriptions")) && themeDisplay.isSignedIn()) {
			groupThreadsUserId = user.getUserId();
		}

		if (groupThreadsUserId > 0) {
			portletURL.setParameter("groupThreadsUserId", String.valueOf(groupThreadsUserId));
		}
		%>

		<c:if test='<%= topLink.equals("recent-posts") && (groupThreadsUserId > 0) %>'>
			<div class="portlet-msg-info">
				<liferay-ui:message key="filter-by-user" />: <%= HtmlUtil.escape(PortalUtil.getUserName(groupThreadsUserId, StringPool.BLANK)) %>
			</div>
		</c:if>

		<c:if test='<%= topLink.equals("my-subscriptions") %>'>

			<%
			List<String> headerNames = new ArrayList<String>();

			headerNames.add("category");
			headerNames.add("categories");
			headerNames.add("threads");
			headerNames.add("posts");
			headerNames.add(StringPool.BLANK);

			SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur1", SearchContainer.DEFAULT_DELTA, portletURL, headerNames, "you-are-not-subscribed-to-any-categories");

			int total = MBCategoryServiceUtil.getSubscribedCategoriesCount(scopeGroupId, user.getUserId());

			searchContainer.setTotal(total);

			List results = MBCategoryServiceUtil.getSubscribedCategories(scopeGroupId, user.getUserId(), searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);

			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				MBCategory curCategory = (MBCategory)results.get(i);

				curCategory = curCategory.toEscapedModel();

				ResultRow row = new ResultRow(curCategory, curCategory.getCategoryId(), i);

				row.setParameter("categorySubscriptionClassPKs", categorySubscriptionClassPKs);

				boolean restricted = !MBCategoryPermission.contains(permissionChecker, curCategory, ActionKeys.VIEW);

				row.setRestricted(restricted);

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("struts_action", "/message_boards/view");
				rowURL.setParameter("mbCategoryId", String.valueOf(curCategory.getCategoryId()));

				// Name and description

				StringBundler sb = new StringBundler(8);

				if (!restricted) {
					sb.append("<a href=\"");
					sb.append(rowURL);
					sb.append("\">");
				}

				sb.append("<strong>");
				sb.append(curCategory.getName());
				sb.append("</strong>");

				if (Validator.isNotNull(curCategory.getDescription())) {
					sb.append("<br />");
					sb.append(curCategory.getDescription());
				}

				row.addText(sb.toString());

				// Statistics

				int categoriesCount = categoryDisplay.getSubcategoriesCount(curCategory);
				int threadsCount = categoryDisplay.getSubcategoriesThreadsCount(curCategory);
				int messagesCount = categoryDisplay.getSubcategoriesMessagesCount(curCategory);

				row.addText(String.valueOf(categoriesCount), rowURL);
				row.addText(String.valueOf(threadsCount), rowURL);
				row.addText(String.valueOf(messagesCount), rowURL);

				// Action

				if (restricted) {
					row.addText(StringPool.BLANK);
				}
				else {
					row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/message_boards/category_action.jsp");
				}

				// Add result row

				resultRows.add(row);
			}
			%>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
		</c:if>

		<%
		List<String> headerNames = new ArrayList<String>();

		headerNames.add("thread");
		headerNames.add("started-by");
		headerNames.add("posts");
		headerNames.add("views");
		headerNames.add("last-post");
		headerNames.add(StringPool.BLANK);

		String emptyResultsMessage = null;

		if (topLink.equals("my-posts")) {
			emptyResultsMessage = "you-do-not-have-any-posts";
		}
		else if (topLink.equals("my-subscriptions")) {
			emptyResultsMessage = "you-are-not-subscribed-to-any-threads";
		}
		else if (topLink.equals("recent-posts")) {
			emptyResultsMessage = "there-are-no-recent-posts";
		}

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, emptyResultsMessage);

		List results = null;

		if (topLink.equals("my-posts")) {
			int total = MBThreadLocalServiceUtil.getGroupThreadsCount(scopeGroupId, groupThreadsUserId, WorkflowConstants.STATUS_APPROVED);

			searchContainer.setTotal(total);

			results = MBThreadLocalServiceUtil.getGroupThreads(scopeGroupId, groupThreadsUserId, WorkflowConstants.STATUS_APPROVED, searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);
		}
		else if (topLink.equals("my-subscriptions")) {
			int total = MBThreadLocalServiceUtil.getGroupThreadsCount(scopeGroupId, groupThreadsUserId, WorkflowConstants.STATUS_APPROVED, true);

			searchContainer.setTotal(total);

			results = MBThreadLocalServiceUtil.getGroupThreads(scopeGroupId, groupThreadsUserId, WorkflowConstants.STATUS_APPROVED, true, searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);
		}
		else if (topLink.equals("recent-posts")) {
			int total = MBThreadLocalServiceUtil.getGroupThreadsCount(scopeGroupId, groupThreadsUserId, WorkflowConstants.STATUS_APPROVED, false, false);

			searchContainer.setTotal(total);

			results = MBThreadLocalServiceUtil.getGroupThreads(scopeGroupId, groupThreadsUserId, WorkflowConstants.STATUS_APPROVED, false, false, searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);
		}

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			MBThread thread = (MBThread)results.get(i);

			MBMessage message = MBMessageLocalServiceUtil.getMessage(thread.getRootMessageId());

			message = message.toEscapedModel();

			boolean readThread = MBMessageFlagLocalServiceUtil.hasReadFlag(themeDisplay.getUserId(), thread);

			ResultRow row = new ResultRow(new Object[] {message, threadSubscriptionClassPKs}, thread.getThreadId(), i, !readThread);

			row.setRestricted(!MBMessagePermission.contains(permissionChecker, message, ActionKeys.VIEW));

			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("struts_action", "/message_boards/view_message");
			rowURL.setParameter("messageId", String.valueOf(message.getMessageId()));

			// Thread

			StringBundler sb = new StringBundler();

			String[] threadPriority = MBUtil.getThreadPriority(preferences, themeDisplay.getLanguageId(), thread.getPriority(), themeDisplay);

			if ((threadPriority != null) && (thread.getPriority() > 0)) {
				sb.append("<img align=\"left\" alt=\"");
				sb.append(threadPriority[0]);
				sb.append("\" border=\"0\" src=\"");
				sb.append(threadPriority[1]);
				sb.append("\" title=\"");
				sb.append(threadPriority[0]);
				sb.append("\" />");
			}

			sb.append(message.getSubject());

			row.addText(sb.toString(), rowURL);

			// Started by

			if (message.isAnonymous()) {
				row.addText(LanguageUtil.get(pageContext, "anonymous"), rowURL);
			}
			else {
				row.addText(HtmlUtil.escape(PortalUtil.getUserName(message.getUserId(), message.getUserName())), rowURL);
			}

			// Number of posts

			row.addText(String.valueOf(thread.getMessageCount()), rowURL);

			// Number of views

			row.addText(String.valueOf(thread.getViewCount()), rowURL);

			// Last post

			if (thread.getLastPostDate() == null) {
				row.addText(LanguageUtil.get(pageContext, "none"), rowURL);
			}
			else {
				sb.setIndex(0);

				sb.append(LanguageUtil.get(pageContext, "date"));
				sb.append(": ");
				sb.append(dateFormatDateTime.format(thread.getLastPostDate()));

				String lastPostByUserName = HtmlUtil.escape(PortalUtil.getUserName(thread.getLastPostByUserId(), StringPool.BLANK));

				if (Validator.isNotNull(lastPostByUserName)) {
					sb.append("<br />");
					sb.append(LanguageUtil.get(pageContext, "by"));
					sb.append(": ");
					sb.append(lastPostByUserName);
				}

				row.addText(sb.toString(), rowURL);
			}

			// Action

			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/message_boards/message_action.jsp");

			// Add result row

			resultRows.add(row);
		}
		%>

		<c:if test='<%= topLink.equals("my-subscriptions") %>'>
			<br />
		</c:if>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<c:if test='<%= topLink.equals("recent-posts") %>'>

			<%
			String rssURL = themeDisplay.getPortalURL() + themeDisplay.getPathMain() + "/message_boards/rss?p_l_id=" + plid + "&groupId=" + scopeGroupId;

			if (groupThreadsUserId > 0) {
				rssURL += "&userId=" + groupThreadsUserId;
			}

			rssURL += rssURLParams;
			%>

			<br />

			<table class="lfr-table">
			<tr>
				<td>
					<liferay-ui:icon
						image="rss"
						label="<%= true %>"
						message="subscribe-to-recent-posts"
						method="get"
						target="_blank"
						url="<%= rssURL %>"
					/>
				</td>
			</tr>
			</table>
		</c:if>

		<%
		PortalUtil.setPageSubtitle(LanguageUtil.get(pageContext, StringUtil.replace(topLink, StringPool.UNDERLINE, StringPool.DASH)), request);
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, TextFormatter.format(topLink, TextFormatter.O)), portletURL.toString());
		%>

	</c:when>
	<c:when test='<%= topLink.equals("statistics") %>'>
		<liferay-ui:panel-container cssClass="statistics-panel" extended="<%= false %>" id="messageBoardsStatisticsPanelContainer" persistState="<%= true %>">
			<liferay-ui:panel collapsible="<%= true %>" cssClass="statistics-panel-content" extended="<%= true %>" id="messageBoardsGeneralStatisticsPanel" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "general") %>'>
				<dl>
					<dt>
						<liferay-ui:message key="num-of-categories" />:
					</dt>
					<dd>
						<%= numberFormat.format(categoryDisplay.getAllCategoriesCount()) %>
					</dd>
					<dt>
						<liferay-ui:message key="num-of-posts" />:
					</dt>
					<dd>
						<%= numberFormat.format(MBMessageLocalServiceUtil.getGroupMessagesCount(scopeGroupId, WorkflowConstants.STATUS_APPROVED)) %>
					</dd>
					<dt>
						<liferay-ui:message key="num-of-participants" />:
					</dt>
					<dd>
						<%= numberFormat.format(MBStatsUserLocalServiceUtil.getStatsUsersByGroupIdCount(scopeGroupId)) %>
					</dd>
				</dl>
			</liferay-ui:panel>

			<liferay-ui:panel collapsible="<%= true %>" cssClass="statistics-panel-content" extended="<%= true %>" id="messageBoardsTopPostersPanel" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "top-posters") %>'>

				<%
				SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, null, "there-are-no-top-posters");

				int total = MBStatsUserLocalServiceUtil.getStatsUsersByGroupIdCount(scopeGroupId);

				searchContainer.setTotal(total);

				List results = MBStatsUserLocalServiceUtil.getStatsUsersByGroupId(scopeGroupId, searchContainer.getStart(), searchContainer.getEnd());

				searchContainer.setResults(results);

				List resultRows = searchContainer.getResultRows();

				for (int i = 0; i < results.size(); i++) {
					MBStatsUser statsUser = (MBStatsUser)results.get(i);

					ResultRow row = new ResultRow(statsUser, statsUser.getStatsUserId(), i);

					// User display

					row.addJSP("/html/portlet/message_boards/top_posters_user_display.jsp");

					// Add result row

					resultRows.add(row);
				}
				%>

				<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
			</liferay-ui:panel>
		</liferay-ui:panel-container>

		<%
		PortalUtil.setPageSubtitle(LanguageUtil.get(pageContext, StringUtil.replace(topLink, StringPool.UNDERLINE, StringPool.DASH)), request);
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, TextFormatter.format(topLink, TextFormatter.O)), portletURL.toString());
		%>

	</c:when>
	<c:when test='<%= topLink.equals("banned-users") %>'>

		<%
		List<String> headerNames = new ArrayList<String>();

		headerNames.add("banned-user");
		headerNames.add("banned-by");
		headerNames.add("ban-date");

		if (PropsValues.MESSAGE_BOARDS_EXPIRE_BAN_INTERVAL > 0) {
			headerNames.add("unban-date");
		}

		headerNames.add(StringPool.BLANK);

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, "there-are-no-banned-users");

		int total = MBBanLocalServiceUtil.getBansCount(scopeGroupId);

		searchContainer.setTotal(total);

		List results = MBBanLocalServiceUtil.getBans(scopeGroupId, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			MBBan ban = (MBBan)results.get(i);

			ResultRow row = new ResultRow(ban, ban.getBanId(), i);

			// Banned user

			row.addText(HtmlUtil.escape(PortalUtil.getUserName(ban.getBanUserId(), StringPool.BLANK)));

			// Banned by

			row.addText(HtmlUtil.escape(PortalUtil.getUserName(ban.getUserId(), StringPool.BLANK)));

			// Ban date

			row.addText(dateFormatDateTime.format(ban.getCreateDate()));

			// Unban date

			if (PropsValues.MESSAGE_BOARDS_EXPIRE_BAN_INTERVAL > 0) {
				row.addText(dateFormatDateTime.format(MBUtil.getUnbanDate(ban, PropsValues.MESSAGE_BOARDS_EXPIRE_BAN_INTERVAL)));
			}

			// Action

			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/message_boards/ban_user_action.jsp");

			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<%
		PortalUtil.setPageSubtitle(LanguageUtil.get(pageContext, StringUtil.replace(topLink, StringPool.UNDERLINE, StringPool.DASH)), request);
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, TextFormatter.format(topLink, TextFormatter.O)), portletURL.toString());
		%>

	</c:when>
</c:choose>