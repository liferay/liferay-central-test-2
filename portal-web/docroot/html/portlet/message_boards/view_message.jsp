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

<%@ include file="/html/portlet/message_boards/css.jsp" %>

<%
MBMessageDisplay messageDisplay = (MBMessageDisplay)request.getAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE);

MBMessage message = messageDisplay.getMessage();

MBCategory category = messageDisplay.getCategory();

MBMessage previousMessage = messageDisplay.getPreviousMessage();
MBMessage nextMessage = messageDisplay.getNextMessage();

MBMessage firstMessage = messageDisplay.getFirstMessage();
MBMessage lastMessage = messageDisplay.getLastMessage();

boolean isFirstMessage = messageDisplay.isFirstMessage();
boolean isLastMessage = messageDisplay.isLastMessage();

MBThread previousThread = messageDisplay.getPreviousThread();
MBThread nextThread = messageDisplay.getNextThread();

MBThread firstThread = messageDisplay.getFirstThread();
MBThread lastThread = messageDisplay.getLastThread();

boolean isFirstThread = messageDisplay.isFirstThread();
boolean isLastThread = messageDisplay.isLastThread();

boolean threadView = ParamUtil.get(request, "threadView", true);
%>

<form action="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/search" /></portlet:renderURL>" method="post" name="<portlet:namespace />fm" onSubmit="submitForm(this); return false;">
<input name="<portlet:namespace />breadcrumbsCategoryId" type="hidden" value="<%= category.getCategoryId() %>">
<input name="<portlet:namespace />breadcrumbsMessageId" type="hidden" value="<%= message.getMessageId() %>">
<input name="<portlet:namespace />threadId" type="hidden" value="<%= message.getThreadId() %>">

<liferay-util:include page="/html/portlet/message_boards/tabs1.jsp" />

<%= MBUtil.getBreadcrumbs(null, message, pageContext, renderResponse, false) %>

<br><br>

<div style="background-color: <%= colorScheme.getLayoutTabBg() %>; border: 1px solid <%= colorScheme.getPortletFontDim() %>; margin-bottom: 5px; padding: 3px 5px;">
	<span style="float: left;">
		<%= LanguageUtil.get(pageContext, "threads") %>

		[

		<c:if test="<%= previousThread != null %>">
			<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/view_message" /><portlet:param name="messageId" value="<%= previousThread.getRootMessageId() %>" /></portlet:renderURL>">
		</c:if>

		<%= LanguageUtil.get(pageContext, "previous") %>

		<c:if test="<%= previousThread != null %>">
			</a>
		</c:if>

		|

		<c:if test="<%= nextThread != null %>">
			<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/view_message" /><portlet:param name="messageId" value="<%= nextThread.getRootMessageId() %>" /></portlet:renderURL>">
		</c:if>

		<%= LanguageUtil.get(pageContext, "next") %>

		<c:if test="<%= nextThread != null %>">
			</a>
		</c:if>

		]
	</span>
	<span style="float: right;">
		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<c:if test="<%= MBCategoryPermission.contains(permissionChecker, category, ActionKeys.ADD_MESSAGE) %>">
				<td>
					<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL">
						<portlet:param name="struts_action" value="/message_boards/edit_message" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="categoryId" value="<%= category.getCategoryId() %>" />
					</portlet:renderURL>

					<liferay-ui:icon image="post" message="post-new-thread" url="<%= portletURL %>" />

					<a href="<%= portletURL.toString() %>"><%= LanguageUtil.get(pageContext, "post-new-thread") %></a>
				</td>
			</c:if>

			<c:if test="<%= MBMessagePermission.contains(permissionChecker, message, ActionKeys.SUBSCRIBE) %>">
				<td style="padding-left: 15px;"></td>
				<td>
					<c:choose>
						<c:when test="<%= SubscriptionLocalServiceUtil.isSubscribed(user.getCompanyId(), user.getUserId(), MBThread.class.getName(), message.getThreadId()) %>">
							<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL">
								<portlet:param name="struts_action" value="/message_boards/edit_message" />
								<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="messageId" value="<%= message.getMessageId() %>" />
							</portlet:actionURL>

							<liferay-ui:icon image="unsubscribe" url="<%= portletURL %>" />

							<a href="<%= portletURL.toString() %>"><%= LanguageUtil.get(pageContext, "unsubscribe") %></a>
						</c:when>
						<c:otherwise>
							<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL">
								<portlet:param name="struts_action" value="/message_boards/edit_message" />
								<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="messageId" value="<%= message.getMessageId() %>" />
							</portlet:actionURL>

							<liferay-ui:icon image="subscribe" url="<%= portletURL %>" />

							<a href="<%= portletURL.toString() %>"><%= LanguageUtil.get(pageContext, "subscribe") %></a>
						</c:otherwise>
					</c:choose>
				</td>
			</c:if>
		</tr>
		</table>
	</span>
	<div style="clear: both;"></div>
</div>

<div class="portlet-section-header" style="border:1px solid <%= colorScheme.getPortletFontDim() %>; font-size: large; font-weight: normal; padding: 3px 5px;">
	<%= message.getSubject() %>
</div>

<div>

	<%
	TreeWalker treeWalker = messageDisplay.getTreeWalker();

	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER, treeWalker);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_SEL_MESSAGE, message);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE, treeWalker.getRoot());
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CATEGORY, category);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_LAST_NODE, new Boolean(false));
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_DEPTH, new Integer(0));
	%>

	<liferay-util:include page="/html/portlet/message_boards/view_message_thread.jsp" />
</div>

</form>