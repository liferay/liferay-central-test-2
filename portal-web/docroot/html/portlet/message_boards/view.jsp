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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "categories");
String tabs2 = ParamUtil.getString(request, "tabs2", "general");

MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = BeanParamUtil.getLong(category, request, "categoryId", MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID);

List categoryIds = new ArrayList();

categoryIds.add(new Long(categoryId));

MBCategoryLocalServiceUtil.getSubcategoryIds(categoryIds, portletGroupId.longValue(), categoryId);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/message_boards/view");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("categoryId", String.valueOf(categoryId));
%>

<liferay-util:include page="/html/portlet/message_boards/tabs1.jsp" />

<c:choose>
	<c:when test='<%= tabs1.equals("categories") %>'>
		<form action="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/search" /></portlet:renderURL>" method="post" name="<portlet:namespace />fm1" onSubmit="submitForm(this); return false;">
		<input name="<portlet:namespace />breadcrumbsCategoryId" type="hidden" value="<%= categoryId %>" />
		<input name="<portlet:namespace />categoryIds" type="hidden" value="<%= StringUtil.merge(categoryIds) %>" />

		<c:if test="<%= category != null %>">
			<div class="breadcrumbs">
				<%= MBUtil.getBreadcrumbs(category, null, pageContext, renderRequest, renderResponse) %>
			</div>
		</c:if>

		<%
		List headerNames = new ArrayList();

		headerNames.add("category");
		headerNames.add("categories");
		headerNames.add("threads");
		headerNames.add("posts");
		headerNames.add(StringPool.BLANK);

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur1", SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

		int total = MBCategoryLocalServiceUtil.getCategoriesCount(portletGroupId.longValue(), categoryId);

		searchContainer.setTotal(total);

		List results = MBCategoryLocalServiceUtil.getCategories(portletGroupId.longValue(), categoryId, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			MBCategory curCategory = (MBCategory)results.get(i);

			ResultRow row = new ResultRow(curCategory, curCategory.getCategoryId(), i);
			
			boolean restricted = !MBCategoryPermission.contains(permissionChecker, curCategory, ActionKeys.VIEW);

			row.setRestricted(restricted);

			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setWindowState(WindowState.MAXIMIZED);

			rowURL.setParameter("struts_action", "/message_boards/view");
			rowURL.setParameter("categoryId", String.valueOf(curCategory.getCategoryId()));

			// Name and description

			StringMaker sm = new StringMaker();

			sm.append("<a href=\"");
			sm.append(restricted?"":rowURL);
			sm.append("\"><b>");
			sm.append(curCategory.getName());
			sm.append("</b>");

			if (Validator.isNotNull(curCategory.getDescription())) {
				sm.append("<br />");
				sm.append("<span style=\"font-size: xx-small;\">");
				sm.append(curCategory.getDescription());
				sm.append("</span>");
			}

			sm.append("</a>");
			
			if (!restricted) {
				List subcategories = MBCategoryLocalServiceUtil.getCategories(portletGroupId.longValue(), curCategory.getCategoryId(), 0, 5);

				if (subcategories.size() > 0) {
					sm.append("<br />");
					sm.append("<span style=\"font-size: xx-small; font-weight: bold;\"><u>");
					sm.append(LanguageUtil.get(pageContext, "subcategories"));
					sm.append("</u>: ");

					for (int j = 0; j < subcategories.size(); j++) {
						MBCategory subcategory = (MBCategory)subcategories.get(j);

						rowURL.setParameter("categoryId", String.valueOf(subcategory.getCategoryId()));

						sm.append("<a href=\"");
						sm.append(rowURL);
						sm.append("\">");
						sm.append(subcategory.getName());
						sm.append("</a>");

						if ((j + 1) < subcategories.size()) {
							sm.append(", ");
						}
					}

					rowURL.setParameter("categoryId", String.valueOf(curCategory.getCategoryId()));

					sm.append("</span>");
				}
			}

			row.addText(sm.toString());

			// Statistics

			List subcategoryIds = new ArrayList();

			subcategoryIds.add(new Long(curCategory.getCategoryId()));

			MBCategoryLocalServiceUtil.getSubcategoryIds(subcategoryIds, portletGroupId.longValue(), curCategory.getCategoryId());

			int categoriesCount = subcategoryIds.size() - 1;
			int threadsCount = MBThreadLocalServiceUtil.getCategoriesThreadsCount(subcategoryIds);
			int messagesCount = MBMessageLocalServiceUtil.getCategoriesMessagesCount(subcategoryIds);

			row.addText(String.valueOf(categoriesCount), restricted?"":rowURL.toString());
			row.addText(String.valueOf(threadsCount), restricted?"":rowURL.toString());
			row.addText(String.valueOf(messagesCount), restricted?"":rowURL.toString());

			// Action

			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/message_boards/category_action.jsp");

			// Add result row

			resultRows.add(row);
		}

		boolean showAddCategoryButton = MBCategoryPermission.contains(permissionChecker, plid.longValue(), categoryId, ActionKeys.ADD_CATEGORY);
		%>

		<c:if test="<%= showAddCategoryButton || (results.size() > 0) %>">
			<table class="liferay-table">
			<tr>
				<c:if test="<%= showAddCategoryButton %>">
					<td>
						<input type="button" value="<liferay-ui:message key="add-category" />" onClick="self.location = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/edit_category" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="parentCategoryId" value="<%= String.valueOf(categoryId) %>" /></portlet:renderURL>';" />
					</td>
				</c:if>

				<c:if test="<%= results.size() > 0 %>">
					<td>
						<input name="<portlet:namespace />keywords" size="30" type="text" />

						<input type="submit" value="<liferay-ui:message key="search-categories" />" />
					</td>
				</c:if>
			</tr>
			</table>

			<c:if test="<%= results.size() > 0 %>">
				<br />
			</c:if>
		</c:if>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />

		<c:if test="<%= (category != null) && (showAddCategoryButton || (results.size() > 0)) %>">
			<br />
		</c:if>

		</form>

		<script type="text/javascript">
			if (document.<portlet:namespace />fm1.<portlet:namespace />keywords) {
				document.<portlet:namespace />fm1.<portlet:namespace />keywords.focus();
			}
		</script>

		<c:if test="<%= category != null %>">
			<form action="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/search" /></portlet:renderURL>" method="post" name="<portlet:namespace />fm2" onSubmit="submitForm(this); return false;">
			<input name="<portlet:namespace />breadcrumbsCategoryId" type="hidden" value="<%= categoryId %>" />
			<input name="<portlet:namespace />categoryIds" type="hidden" value="<%= categoryId %>" />

			<liferay-ui:tabs names="threads" />

			<%
			headerNames.clear();

			headerNames.add("thread");
			headerNames.add("started-by");
			headerNames.add("posts");
			headerNames.add("views");
			headerNames.add("last-post");
			headerNames.add(StringPool.BLANK);

			searchContainer = new SearchContainer(renderRequest, null, null, "cur2", SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

			total = MBThreadLocalServiceUtil.getThreadsCount(categoryId);

			searchContainer.setTotal(total);

			results = MBThreadLocalServiceUtil.getThreads(categoryId, searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);

			resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				MBThread thread = (MBThread)results.get(i);

				MBMessage message = MBMessageLocalServiceUtil.getMessage(thread.getRootMessageId());
				boolean readThread = MBThreadLocalServiceUtil.hasReadThread(themeDisplay.getUserId(), thread.getThreadId());

				ResultRow row = new ResultRow(message, thread.getThreadId(), i, !readThread);
				
				row.setRestricted(!MBMessagePermission.contains(permissionChecker, message, ActionKeys.VIEW));

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setWindowState(WindowState.MAXIMIZED);

				rowURL.setParameter("struts_action", "/message_boards/view_message");
				rowURL.setParameter("messageId", String.valueOf(message.getMessageId()));

				// Thread

				StringMaker sm = new StringMaker();

				String[] threadPriority = MBUtil.getThreadPriority(portletSetup, thread.getPriority(), themeDisplay);

				if ((threadPriority != null) && (thread.getPriority() > 0)) {
					sm.append("<img align=\"left\" alt=\"");
					sm.append(threadPriority[0]);
					sm.append("\" border=\"0\" src=\"");
					sm.append(threadPriority[1]);
					sm.append("\" title=\"");
					sm.append(threadPriority[0]);
					sm.append("\" >");
				}

				sm.append(message.getSubject());

				row.addText(sm.toString(), rowURL);

				// Started by

				if (message.isAnonymous()) {
					row.addText(LanguageUtil.get(pageContext, "anonymous"), rowURL);
				}
				else {
					row.addText(PortalUtil.getUserName(message.getUserId(), message.getUserName()), rowURL);
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
					sm = new StringMaker();

					sm.append("<span style=\"font-size: xx-small; white-space: nowrap;\">");

					sm.append(LanguageUtil.get(pageContext, "date"));
					sm.append(": ");
					sm.append(dateFormatDateTime.format(thread.getLastPostDate()));

					try {
						User user2 = UserLocalServiceUtil.getUserById(thread.getLastPostByUserId());

						sm.append("<br />");
						sm.append(LanguageUtil.get(pageContext, "by"));
						sm.append(": ");
						sm.append(user2.getFullName());
					}
					catch (NoSuchUserException nsue) {
					}

					sm.append("</span>");

					row.addText(sm.toString(), rowURL);
				}

				// Action

				row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/message_boards/message_action.jsp");

				// Add result row

				resultRows.add(row);
			}

			boolean showAddMessageButton = MBCategoryPermission.contains(permissionChecker, category, ActionKeys.ADD_MESSAGE);
			%>

			<c:if test="<%= showAddMessageButton || (results.size() > 0) %>">
				<table class="liferay-table">
				<tr>
					<c:if test="<%= showAddMessageButton %>">
						<td>
							<input type="button" value="<liferay-ui:message key="post-new-thread" />" onClick="self.location = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/edit_message" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="categoryId" value="<%= String.valueOf(categoryId) %>" /></portlet:renderURL>';" />
						</td>
					</c:if>

					<c:if test="<%= results.size() > 0 %>">
						<td>
							<input name="<portlet:namespace />keywords" size="30" type="text" />

							<input type="submit" value="<liferay-ui:message key="search-threads" />" />
						</td>
					</c:if>
				</tr>
				</table>

				<c:if test="<%= results.size() > 0 %>">
					<br />
				</c:if>
			</c:if>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

			<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />

			</form>

			<script type="text/javascript">
				if (document.<portlet:namespace />fm1.<portlet:namespace />keywords) {
					document.<portlet:namespace />fm1.<portlet:namespace />keywords.focus();
				}
				else if (document.<portlet:namespace />fm2.<portlet:namespace />keywords) {
					document.<portlet:namespace />fm2.<portlet:namespace />keywords.focus();
				}
			</script>
		</c:if>
	</c:when>
	<c:when test='<%= tabs1.equals("my-posts") || tabs1.equals("my-subscriptions") || tabs1.equals("recent-posts") %>'>

		<%
		long groupThreadsUserId = 0;

		if ((tabs1.equals("my-posts") || tabs1.equals("my-subscriptions")) && themeDisplay.isSignedIn()) {
			groupThreadsUserId = user.getUserId();
		}

		List headerNames = new ArrayList();

		headerNames.add("thread");
		headerNames.add("started-by");
		headerNames.add("posts");
		headerNames.add("views");
		headerNames.add("last-post");
		headerNames.add(StringPool.BLANK);

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

		List results = null;

		if (tabs1.equals("my-subscriptions")) {
			int total = MBThreadLocalServiceUtil.getGroupThreadsCount(portletGroupId.longValue(), groupThreadsUserId, true);

			searchContainer.setTotal(total);

			results = MBThreadLocalServiceUtil.getGroupThreads(portletGroupId.longValue(), groupThreadsUserId, true, searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);
		}
		else {
			int total = MBThreadLocalServiceUtil.getGroupThreadsCount(portletGroupId.longValue(), groupThreadsUserId);

			searchContainer.setTotal(total);

			results = MBThreadLocalServiceUtil.getGroupThreads(portletGroupId.longValue(), groupThreadsUserId, searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);
		}

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			MBThread thread = (MBThread)results.get(i);

			MBMessage message = MBMessageLocalServiceUtil.getMessage(thread.getRootMessageId());
			boolean readThread = MBThreadLocalServiceUtil.hasReadThread(themeDisplay.getUserId(), thread.getThreadId());

			ResultRow row = new ResultRow(message, thread.getThreadId(), i, !readThread);

			row.setRestricted(!MBMessagePermission.contains(permissionChecker, message, ActionKeys.VIEW));

			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setWindowState(WindowState.MAXIMIZED);

			rowURL.setParameter("struts_action", "/message_boards/view_message");
			rowURL.setParameter("messageId", String.valueOf(message.getMessageId()));

			// Thread

			StringMaker sm = new StringMaker();

			String[] threadPriority = MBUtil.getThreadPriority(portletSetup, thread.getPriority(), themeDisplay);

			if ((threadPriority != null) && (thread.getPriority() > 0)) {
				sm.append("<img align=\"left\" alt=\"");
				sm.append(threadPriority[0]);
				sm.append("\" border=\"0\" src=\"");
				sm.append(threadPriority[1]);
				sm.append("\" title=\"");
				sm.append(threadPriority[0]);
				sm.append("\" >");
			}

			sm.append(message.getSubject());

			row.addText(sm.toString(), rowURL);

			// Started by

			if (message.isAnonymous()) {
				row.addText(LanguageUtil.get(pageContext, "anonymous"), rowURL);
			}
			else {
				row.addText(PortalUtil.getUserName(message.getUserId(), message.getUserName()), rowURL);
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
				sm = new StringMaker();

				sm.append("<span style=\"font-size: xx-small; white-space: nowrap;\">");

				sm.append(LanguageUtil.get(pageContext, "date"));
				sm.append(": ");
				sm.append(dateFormatDateTime.format(thread.getLastPostDate()));

				try {
					User user2 = UserLocalServiceUtil.getUserById(thread.getLastPostByUserId());

					sm.append("<br />");
					sm.append(LanguageUtil.get(pageContext, "by"));
					sm.append(": ");
					sm.append(user2.getFullName());
				}
				catch (NoSuchUserException nsue) {
				}

				sm.append("</span>");

				row.addText(sm.toString(), rowURL);
			}

			// Action

			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/message_boards/message_action.jsp");

			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
	</c:when>
	<c:when test='<%= tabs1.equals("statistics") %>'>
		<liferay-ui:tabs
			names="general,top-posters"
			param="tabs2"
			url="<%= portletURL.toString() %>"
		/>

		<c:choose>
			<c:when test='<%= tabs2.equals("general") %>'>
				<liferay-ui:message key="num-of-categories" />: <%= numberFormat.format(MBCategoryLocalServiceUtil.getCategoriesCount(portletGroupId.longValue())) %><br />
				<liferay-ui:message key="num-of-posts" />: <%= numberFormat.format(MBMessageLocalServiceUtil.getGroupMessagesCount(portletGroupId.longValue())) %><br />
				<liferay-ui:message key="num-of-participants" />: <%= numberFormat.format(MBStatsUserLocalServiceUtil.getStatsUsersCount(portletGroupId.longValue())) %>
			</c:when>
			<c:when test='<%= tabs2.equals("top-posters") %>'>

				<%
				List headerNames = new ArrayList();

				headerNames.add("name");
				headerNames.add("posts");
				headerNames.add("join-date");
				headerNames.add("last-post-date");

				SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

				int total = MBStatsUserLocalServiceUtil.getStatsUsersCount(portletGroupId.longValue());

				searchContainer.setTotal(total);

				List results = MBStatsUserLocalServiceUtil.getStatsUsers(portletGroupId.longValue(), searchContainer.getStart(), searchContainer.getEnd());

				searchContainer.setResults(results);

				List resultRows = searchContainer.getResultRows();

				for (int i = 0; i < results.size(); i++) {
					MBStatsUser statsUser = (MBStatsUser)results.get(i);

					ResultRow row = new ResultRow(statsUser, statsUser.getStatsUserId(), i);

					PortletURL rowURL = null;

					String fullName = null;
					Date createDate = null;

					try {
						User user2 = UserLocalServiceUtil.getUserById(statsUser.getUserId());

						rowURL = new PortletURLImpl(request, PortletKeys.DIRECTORY, plid.longValue(), false);

						rowURL.setWindowState(WindowState.MAXIMIZED);
						rowURL.setPortletMode(PortletMode.VIEW);

						rowURL.setParameter("struts_action", "/directory/edit_user");
						rowURL.setParameter("p_u_i_d", String.valueOf(user2.getUserId()));

						fullName = user2.getFullName();
						createDate = user2.getCreateDate();
					}
					catch (NoSuchUserException nsue) {
					}

					// Name

					row.addText(fullName, rowURL);

					// Number of posts

					row.addText(String.valueOf(statsUser.getMessageCount()), rowURL);

					// Join date

					if (createDate == null) {
						row.addText(LanguageUtil.get(pageContext, "not-available"), rowURL);
					}
					else {
						row.addText(dateFormatDateTime.format(createDate), rowURL);
					}

					// Last post date

					if (statsUser.getLastPostDate() == null) {
						row.addText(LanguageUtil.get(pageContext, "not-available"), rowURL);
					}
					else {
						row.addText(dateFormatDateTime.format(statsUser.getLastPostDate()), rowURL);
					}

					// Add result row

					resultRows.add(row);
				}
				%>

				<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

				<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
			</c:when>
		</c:choose>
	</c:when>
	<c:when test='<%= tabs1.equals("banned-users") %>'>

		<%
		int expireInterval = GetterUtil.getInteger(PropsUtil.get(PropsUtil.MESSAGE_BOARDS_EXPIRE_BAN_INTERVAL));

		List headerNames = new ArrayList();

		headerNames.add("name");
		headerNames.add("ban-date");

		if (expireInterval > 0) {
			headerNames.add("unban-date");
		}

		headerNames.add(StringPool.BLANK);

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

		int total = MBBanLocalServiceUtil.getBansCount(portletGroupId.longValue());

		searchContainer.setTotal(total);

		List results = MBBanLocalServiceUtil.getBans(portletGroupId.longValue(), searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			MBBan ban = (MBBan)results.get(i);

			ResultRow row = new ResultRow(ban, ban.getBanId(), i);

			// Name

			row.addText(PortalUtil.getUserName(ban.getBanUserId(), StringPool.BLANK));

			// Ban Date

			row.addText(dateFormatDateTime.format(ban.getCreateDate()));

			// Unban Date

			if (expireInterval > 0) {
				row.addText(dateFormatDateTime.format(MBUtil.getUnbanDate(ban, expireInterval)));
			}

			// Action

			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/message_boards/ban_user_action.jsp");

			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
	</c:when>
</c:choose>