<%
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

String categoryId = BeanParamUtil.getString(category, request, "categoryId", MBCategory.DEFAULT_PARENT_CATEGORY_ID);

List categoryIds = new ArrayList();

MBCategoryLocalServiceUtil.getSubcategoryIds(categoryIds, portletGroupId, categoryId);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/message_boards/view");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("categoryId", categoryId);
%>

<liferay-ui:tabs
	names="categories,statistics"
	url="<%= portletURL.toString() %>"
/>

<c:choose>
	<c:when test='<%= tabs1.equals("categories") %>'>
		<form action="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/search" /></portlet:renderURL>" method="post" name="<portlet:namespace />fm1" onSubmit="submitForm(this); return false;">
		<input name="<portlet:namespace />breadcrumbsCategoryId" type="hidden" value="<%= categoryId %>">
		<input name="<portlet:namespace />categoryIds" type="hidden" value="<%= StringUtil.merge(categoryIds) %>">

		<c:if test="<%= category != null %>">
			<%= MBUtil.getBreadcrumbs(category, null, null, pageContext, renderResponse) %>

			<br><br>
		</c:if>

		<%
		List headerNames = new ArrayList();

		headerNames.add("category");
		headerNames.add("num-of-categories");
		headerNames.add("num-of-topics");
		headerNames.add("num-of-posts");
		headerNames.add(StringPool.BLANK);

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur1", SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

		int total = MBCategoryLocalServiceUtil.getCategoriesCount(portletGroupId, categoryId);

		searchContainer.setTotal(total);

		List results = MBCategoryLocalServiceUtil.getCategories(portletGroupId, categoryId, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			MBCategory curCategory = (MBCategory)results.get(i);

			ResultRow row = new ResultRow(curCategory, curCategory.getPrimaryKey().toString(), i);

			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setWindowState(WindowState.MAXIMIZED);

			rowURL.setParameter("struts_action", "/message_boards/view");
			rowURL.setParameter("categoryId", curCategory.getCategoryId());

			// Name and description

			StringBuffer sb = new StringBuffer();

			sb.append(curCategory.getName());

			if (Validator.isNotNull(curCategory.getDescription())) {
				sb.append("<br>");
				sb.append("<span style=\"font-size: xx-small;\">");
				sb.append(curCategory.getDescription());
				sb.append("</span>");
			}

			row.addText(sb.toString(), rowURL);

			// Statistics

			List subcategoryIds = new ArrayList();

			subcategoryIds.add(curCategory.getCategoryId());

			MBCategoryLocalServiceUtil.getSubcategoryIds(subcategoryIds, portletGroupId, curCategory.getCategoryId());

			int categoriesCount = subcategoryIds.size() - 1;
			int topicsCount = MBTopicLocalServiceUtil.getCategoriesTopicsCount(subcategoryIds);
			int messagesCount = MBMessageLocalServiceUtil.getCategoriesMessagesCount(subcategoryIds);

			row.addText(Integer.toString(categoriesCount), rowURL);
			row.addText(Integer.toString(topicsCount), rowURL);
			row.addText(Integer.toString(messagesCount), rowURL);

			// Action

			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/message_boards/category_action.jsp");

			// Add result row

			resultRows.add(row);
		}

		boolean showAddCategoryButton = MBCategoryPermission.contains(permissionChecker, plid, categoryId, ActionKeys.ADD_CATEGORY);
		%>

		<c:if test="<%= showAddCategoryButton || (results.size() > 0) %>">
			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<c:if test="<%= showAddCategoryButton %>">
					<td>
						<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "add-category") %>' onClick="self.location = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/edit_category" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="parentCategoryId" value="<%= categoryId %>" /></portlet:renderURL>';">
					</td>
					<td style="padding-left: 30px;"></td>
				</c:if>

				<c:if test="<%= results.size() > 0 %>">
					<td>
						<input class="form-text" name="<portlet:namespace />keywords" size="30" type="text">

						<input class="portlet-form-button" type="submit" value="<%= LanguageUtil.get(pageContext, "search-categories") %>">
					</td>
				</c:if>
			</tr>
			</table>

			<c:if test="<%= results.size() > 0 %>">
				<br>
			</c:if>
		</c:if>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />

		<c:if test="<%= category != null %>">
			<br>
		</c:if>

		</form>

		<script type="text/javascript">
			if (document.<portlet:namespace />fm1.<portlet:namespace />keywords) {
				document.<portlet:namespace />fm1.<portlet:namespace />keywords.focus();
			}
		</script>

		<c:if test="<%= category != null %>">
			<form action="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/search" /></portlet:renderURL>" method="post" name="<portlet:namespace />fm2" onSubmit="submitForm(this); return false;">
			<input name="<portlet:namespace />breadcrumbsCategoryId" type="hidden" value="<%= categoryId %>">
			<input name="<portlet:namespace />categoryIds" type="hidden" value="<%= categoryId %>">

			<liferay-ui:tabs names="topics" />

			<%
			headerNames.clear();

			headerNames.add("topic");
			headerNames.add("num-of-threads");
			headerNames.add(LanguageUtil.get(pageContext, "num-of-posts") + " <span style=\"font-size: xx-small; font-weight: normal;\">(" + LanguageUtil.get(pageContext, "new-over-total") + ")</span>");
			headerNames.add("last-post-date");
			headerNames.add(StringPool.BLANK);

			searchContainer = new SearchContainer(renderRequest, null, null, "cur2", SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

			total = MBTopicLocalServiceUtil.getTopicsCount(categoryId);

			searchContainer.setTotal(total);

			results = MBTopicLocalServiceUtil.getTopics(categoryId, searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);

			resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				MBTopic topic = (MBTopic)results.get(i);

				ResultRow row = new ResultRow(topic, topic.getPrimaryKey().toString(), i);

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setWindowState(WindowState.MAXIMIZED);

				rowURL.setParameter("struts_action", "/message_boards/view_topic");
				rowURL.setParameter("topicId", topic.getTopicId());

				// Name and description

				StringBuffer sb = new StringBuffer();

				sb.append(topic.getName());

				if (Validator.isNotNull(topic.getDescription())) {
					sb.append("<br>");
					sb.append("<span style=\"font-size: xx-small;\">");
					sb.append(topic.getDescription());
					sb.append("</span>");
				}

				row.addText(sb.toString(), rowURL);

				// Statistics

				int threadsCount = MBThreadLocalServiceUtil.getThreadsCount(topic.getTopicId());

				int messagesTotalCount = MBMessageLocalServiceUtil.getTopicMessagesCount(topic.getTopicId());
				int messagesReadCount = MBMessageLocalServiceUtil.getReadMessagesCount(topic.getTopicId(), user.getUserId());
				int messagesUnreadCount = messagesTotalCount - messagesReadCount;

				row.addText(Integer.toString(threadsCount), rowURL);
				row.addText(messagesUnreadCount + "/" + messagesTotalCount, rowURL);

				// Last post date

				if (topic.getLastPostDate() == null) {
					row.addText(LanguageUtil.get(pageContext, "never"), rowURL);
				}
				else {
					row.addText(dateFormatDateTime.format(topic.getLastPostDate()), rowURL);
				}

				// Action

				row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/message_boards/topic_action.jsp");

				// Add result row

				resultRows.add(row);
			}

			boolean showAddTopicButton = MBCategoryPermission.contains(permissionChecker, category, ActionKeys.ADD_TOPIC);
			%>

			<c:if test="<%= showAddTopicButton || (results.size() > 0) %>">
				<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<c:if test="<%= showAddTopicButton %>">
						<td>
							<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "add-topic") %>' onClick="self.location = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/edit_topic" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="categoryId" value="<%= categoryId %>" /></portlet:renderURL>';">
						</td>
						<td style="padding-left: 30px;"></td>
					</c:if>

					<c:if test="<%= results.size() > 0 %>">
						<td>
							<input class="form-text" name="<portlet:namespace />keywords" size="30" type="text">

							<input class="portlet-form-button" type="submit" value="<%= LanguageUtil.get(pageContext, "search-topics") %>">
						</td>
					</c:if>
				</tr>
				</table>

				<c:if test="<%= results.size() > 0 %>">
					<br>
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
	<c:when test='<%= tabs1.equals("statistics") %>'>
		<liferay-ui:tabs
			names="general,top-posters"
			param="tabs2"
			url="<%= portletURL.toString() %>"
		/>

		<c:choose>
			<c:when test='<%= tabs2.equals("general") %>'>
				<%= LanguageUtil.get(pageContext, "num-of-categories") %>: <%= numberFormat.format(MBCategoryLocalServiceUtil.getCategoriesCount(portletGroupId)) %><br>
				<%= LanguageUtil.get(pageContext, "num-of-topics") %>: <%= numberFormat.format(MBTopicLocalServiceUtil.getGroupTopicsCount(portletGroupId)) %><br>
				<%= LanguageUtil.get(pageContext, "num-of-posts") %>: <%= numberFormat.format(MBMessageLocalServiceUtil.getGroupMessagesCount(portletGroupId)) %><br>
				<%= LanguageUtil.get(pageContext, "num-of-participants") %>: <%= numberFormat.format(MBStatsUserLocalServiceUtil.getStatsUsersCount(portletGroupId)) %>
			</c:when>
			<c:when test='<%= tabs2.equals("top-posters") %>'>

				<%
				List headerNames = new ArrayList();

				headerNames.add("name");
				headerNames.add("num-of-posts");
				headerNames.add("join-date");
				headerNames.add("last-post-date");

				SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

				int total = MBStatsUserLocalServiceUtil.getStatsUsersCount(portletGroupId);

				searchContainer.setTotal(total);

				List results = MBStatsUserLocalServiceUtil.getStatsUsers(portletGroupId, searchContainer.getStart(), searchContainer.getEnd());

				searchContainer.setResults(results);

				List resultRows = searchContainer.getResultRows();

				for (int i = 0; i < results.size(); i++) {
					MBStatsUser statsUser = (MBStatsUser)results.get(i);

					ResultRow row = new ResultRow(statsUser, statsUser.getPrimaryKey().toString(), i);

					PortletURL rowURL = null;

					String fullName = null;
					Date createDate = null;

					try {
						User user2 = UserLocalServiceUtil.getUserById(statsUser.getUserId());

						rowURL = new PortletURLImpl(request, PortletKeys.DIRECTORY, plid, false);

						rowURL.setWindowState(WindowState.MAXIMIZED);
						rowURL.setPortletMode(PortletMode.VIEW);

						rowURL.setParameter("struts_action", "/directory/edit_user");
						rowURL.setParameter("p_u_e_a", user2.getEmailAddress());

						fullName = user2.getFullName();
						createDate = user2.getCreateDate();
					}
					catch (NoSuchUserException nsue) {
					}

					// Name

					row.addText(fullName, rowURL);

					// Number of posts

					row.addText(Integer.toString(statsUser.getMessageCount()), rowURL);

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
</c:choose>