<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
--%>

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = MBUtil.getCategoryId(request, category);

MBCategoryDisplay categoryDisplay = (MBCategoryDisplay)request.getAttribute("view.jsp-categoryDisplay");

Set<Long> categorySubscriptionClassPKs = (Set<Long>)request.getAttribute("view.jsp-categorySubscriptionClassPKs");
Set<Long> threadSubscriptionClassPKs = (Set<Long>)request.getAttribute("view.jsp-threadSubscriptionClassPKs");

PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");
%>

<liferay-ui:panel-container cssClass="message-boards-panels" extended="<%= false %>" id="messageBoardsPanelContainer" persistState="<%= true %>">

	<%
	int categoriesCount = MBCategoryServiceUtil.getCategoriesCount(scopeGroupId, categoryId);
	%>

	<c:if test="<%= categoriesCount > 0 %>">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="messageBoardsCategoriesPanel" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, (category != null) ? "subcategories" : "categories") %>'>
			<liferay-ui:search-container
				curParam="cur1"
				deltaConfigurable="<%= false %>"
				headerNames="category,categories,threads,posts"
				iteratorURL="<%= portletURL %>"
			>
				<liferay-ui:search-container-results
					results="<%= MBCategoryServiceUtil.getCategories(scopeGroupId, categoryId, searchContainer.getStart(), searchContainer.getEnd()) %>"
					total="<%= categoriesCount %>"
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

	<liferay-ui:panel collapsible="<%= true %>" cssClass="threads-panel" extended="<%= true %>" id="messageBoardsThreadsPanel" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "threads") %>'>
		<liferay-ui:search-container
			curParam="cur2"
			emptyResultsMessage="there-are-no-threads-in-this-category"
			headerNames="thread,flag,started-by,posts,views,last-post"
			iteratorURL="<%= portletURL %>"
		>
			<liferay-ui:search-container-results
				results="<%= MBThreadServiceUtil.getThreads(scopeGroupId, categoryId, WorkflowConstants.STATUS_APPROVED, searchContainer.getStart(), searchContainer.getEnd()) %>"
				total="<%= MBThreadServiceUtil.getThreadsCount(scopeGroupId, categoryId, WorkflowConstants.STATUS_APPROVED) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portlet.messageboards.model.MBThread"
				keyProperty="threadId"
				modelVar="thread"
			>

				<%
				MBMessage message = null;

				try {
					message = MBMessageLocalServiceUtil.getMessage(thread.getRootMessageId());
				}
				catch (NoSuchMessageException nsme) {
					_log.error("Thread requires missing root message id " + thread.getRootMessageId());

					continue;
				}

				message = message.toEscapedModel();

				boolean readThread = MBMessageFlagLocalServiceUtil.hasReadFlag(themeDisplay.getUserId(), thread);

				row.setBold(!readThread);
				row.setObject(new Object[] {message, threadSubscriptionClassPKs});
				row.setRestricted(!MBMessagePermission.contains(permissionChecker, message, ActionKeys.VIEW));
				%>

				<liferay-portlet:renderURL varImpl="rowURL">
					<portlet:param name="struts_action" value="/message_boards/view_message" />
					<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
				</liferay-portlet:renderURL>

				<liferay-ui:search-container-column-text
					buffer="buffer"
					href="<%= rowURL %>"
					name="thread"
				>

					<%
					String[] threadPriority = MBUtil.getThreadPriority(preferences, themeDisplay.getLanguageId(), thread.getPriority(), themeDisplay);

					if ((threadPriority != null) && (thread.getPriority() > 0)) {
						buffer.append("<img class=\"thread-priority\" alt=\"");
						buffer.append(threadPriority[0]);
						buffer.append("\" src=\"");
						buffer.append(threadPriority[1]);
						buffer.append("\" title=\"");
						buffer.append(threadPriority[0]);
						buffer.append("\" />");
					}

					if (thread.isLocked()) {
						buffer.append("<img class=\"thread-priority\" alt=\"");
						buffer.append(LanguageUtil.get(pageContext, "thread-locked"));
						buffer.append("\" src=\"");
						buffer.append(themeDisplay.getPathThemeImages() + "/common/lock.png");
						buffer.append("\" title=\"");
						buffer.append(LanguageUtil.get(pageContext, "thread-locked"));
						buffer.append("\" />");
					}

					buffer.append(message.getSubject());
					%>

				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					buffer="buffer"
					href="<%= rowURL %>"
					name="flag"
				>

					<%
					if (MBMessageFlagLocalServiceUtil.hasAnswerFlag(message.getMessageId())) {
						buffer.append(LanguageUtil.get(pageContext, "resolved"));
					}
					else if (MBMessageFlagLocalServiceUtil.hasQuestionFlag(message.getMessageId())) {
						buffer.append(LanguageUtil.get(pageContext, "waiting-for-an-answer"));
					}
					%>

				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					name="started-by"
					value='<%= message.isAnonymous() ? LanguageUtil.get(pageContext, "anonymous") : HtmlUtil.escape(PortalUtil.getUserName(message.getUserId(), message.getUserName())) %>'
				/>

				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					name="posts"
					value="<%= String.valueOf(thread.getMessageCount()) %>"
				/>

				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					name="views"
					value="<%= String.valueOf(thread.getViewCount()) %>"
				/>

				<liferay-ui:search-container-column-text
					buffer="buffer"
					href="<%= rowURL %>"
					name="last-post"
				>

					<%
					if (thread.getLastPostDate() == null) {
						buffer.append(LanguageUtil.get(pageContext, "none"));
					}
					else {
						buffer.append(LanguageUtil.get(pageContext, "date"));
						buffer.append(": ");
						buffer.append(dateFormatDateTime.format(thread.getLastPostDate()));

						String lastPostByUserName = HtmlUtil.escape(PortalUtil.getUserName(thread.getLastPostByUserId(), StringPool.BLANK));

						if (Validator.isNotNull(lastPostByUserName)) {
							buffer.append("<br />");
							buffer.append(LanguageUtil.get(pageContext, "by"));
							buffer.append(": ");
							buffer.append(lastPostByUserName);
						}
					}
					%>

				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-jsp
					align="right"
					path="/html/portlet/message_boards/message_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</liferay-ui:panel>
</liferay-ui:panel-container>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.message_boards.view_category_default_jsp");
%>