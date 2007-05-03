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

<%@ include file="/html/portlet/message_boards/css.jspf" %>

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

PortalPreferences prefs = PortletPreferencesFactory.getPortalPreferences(request);

String threadView = ParamUtil.getString(request, "threadView");

if (Validator.isNotNull(threadView)) {
	prefs.setValue(PortletKeys.MESSAGE_BOARDS, "thread-view", threadView);
}
else {
	threadView = prefs.getValue(PortletKeys.MESSAGE_BOARDS, "thread-view", "combination");
}
%>

<script type="text/javascript">
	function <portlet:namespace />scrollIntoView(messageId) {
		document.getElementById("<portlet:namespace />messageScroll" + messageId).scrollIntoView(true);
	}
</script>

<form action="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/search" /></portlet:renderURL>" method="post" name="<portlet:namespace />fm" onSubmit="submitForm(this); return false;">
<input name="<portlet:namespace />breadcrumbsCategoryId" type="hidden" value="<%= category.getCategoryId() %>">
<input name="<portlet:namespace />breadcrumbsMessageId" type="hidden" value="<%= message.getMessageId() %>">
<input name="<portlet:namespace />threadId" type="hidden" value="<%= message.getThreadId() %>">

<liferay-util:include page="/html/portlet/message_boards/tabs1.jsp" />

<table cellpadding="0" cellspacing="0" width="100%">
<tr>
	<td width="99%">
		<div class="breadcrumbs">
			<%= MBUtil.getBreadcrumbs(null, message, pageContext, renderRequest, renderResponse) %>
		</div>
	</td>
	<td>

		<%
		currentURLObj.setParameter("threadView", "combination");
		%>

		<liferay-ui:icon
			image="../message_boards/thread_view_combination"
			message="combination-view"
			url="<%= currentURLObj.toString() %>"
		/>
	</td>
	<td>

		<%
		currentURLObj.setParameter("threadView", "flat");
		%>

		<liferay-ui:icon
			image="../message_boards/thread_view_flat"
			message="flat-view"
			url="<%= currentURLObj.toString() %>"
		/>
	</td>
	<td>

		<%
		currentURLObj.setParameter("threadView", "tree");
		%>

		<liferay-ui:icon
			image="../message_boards/thread_view_tree"
			message="tree-view"
			url="<%= currentURLObj.toString() %>"
		/>
	</td>
</tr>
</table>

<div class="message-board-thread-controls">
	<div class="message-board-thread-navigation">
		<bean:message key="threads" />

		[

		<c:if test="<%= previousThread != null %>">
			<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/view_message" /><portlet:param name="messageId" value="<%= previousThread.getRootMessageId() %>" /></portlet:renderURL>">
		</c:if>

		<bean:message key="previous" />

		<c:if test="<%= previousThread != null %>">
			</a>
		</c:if>

		|

		<c:if test="<%= nextThread != null %>">
			<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/view_message" /><portlet:param name="messageId" value="<%= nextThread.getRootMessageId() %>" /></portlet:renderURL>">
		</c:if>

		<bean:message key="next" />

		<c:if test="<%= nextThread != null %>">
			</a>
		</c:if>

		]
	</div>
	<div class="message-board-thread-actions">
		<table class="liferay-table">
		<tr>
			<c:if test="<%= MBCategoryPermission.contains(permissionChecker, category, ActionKeys.ADD_MESSAGE) %>">
				<td>
					<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="addMessageURL">
						<portlet:param name="struts_action" value="/message_boards/edit_message" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="categoryId" value="<%= category.getCategoryId() %>" />
					</portlet:renderURL>

					<liferay-ui:icon image="post" message="post-new-thread" url="<%= addMessageURL %>" />

					<a href="<%= addMessageURL.toString() %>"><bean:message key="post-new-thread" /></a>
				</td>
			</c:if>

			<c:if test="<%= MBMessagePermission.contains(permissionChecker, message, ActionKeys.SUBSCRIBE) %>">
				<td>
					<c:choose>
						<c:when test="<%= SubscriptionLocalServiceUtil.isSubscribed(user.getCompanyId(), user.getUserId(), MBThread.class.getName(), GetterUtil.getLong(message.getThreadId())) %>">
							<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="unsubscribeURL">
								<portlet:param name="struts_action" value="/message_boards/edit_message" />
								<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="messageId" value="<%= message.getMessageId() %>" />
							</portlet:actionURL>

							<liferay-ui:icon image="unsubscribe" url="<%= unsubscribeURL %>" />

							<a href="<%= unsubscribeURL.toString() %>"><bean:message key="unsubscribe" /></a>
						</c:when>
						<c:otherwise>
							<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="subscribeURL">
								<portlet:param name="struts_action" value="/message_boards/edit_message" />
								<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="messageId" value="<%= message.getMessageId() %>" />
							</portlet:actionURL>

							<liferay-ui:icon image="subscribe" url="<%= subscribeURL %>" />

							<a href="<%= subscribeURL.toString() %>"><bean:message key="subscribe" /></a>
						</c:otherwise>
					</c:choose>
				</td>
			</c:if>
		</tr>
		</table>
	</div>
	<div class="message-board-clear"></div>
</div>

<div class="portlet-section-header message-board-title">
	<%= message.getSubject() %>
</div>

<div>

	<%
	MBTreeWalker treeWalker = messageDisplay.getTreeWalker();

	List messages = new ArrayList();

	messages.addAll(treeWalker.getMessages());

	Collections.sort(messages, new MessageCreateDateComparator(true));
	%>

	<div class="message-board-message-scroll" id="<portlet:namespace />messageScroll0"></div>

	<c:if test='<%= threadView.equals("combination") && (messages.size() > 1) %>'>
		<liferay-ui:toggle
			id="toggle_id_message_boards_view_message_thread"
			defaultOn="true"
		/>

		<table border="0" cellpadding="1" cellspacing="0" class="toggle_id_message_boards_view_message_thread" id="toggle_id_message_boards_view_message_thread" width="100%" style="display: <liferay-ui:toggle-value id="toggle_id_message_boards_view_message_thread" />;">

		<%
		request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER, treeWalker);
		request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_SEL_MESSAGE, message);
		request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE, treeWalker.getRoot());
		request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CATEGORY, category);
		request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_LAST_NODE, new Boolean(false));
		request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_DEPTH, new Integer(0));

		%>

		<liferay-util:include page="/html/portlet/message_boards/view_thread_shortcut.jsp" />

		</table>
	</c:if>

	<c:choose>
		<c:when test='<%= threadView.equals("tree") %>'>

			<%
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER, treeWalker);
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_SEL_MESSAGE, message);
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE, treeWalker.getRoot());
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CATEGORY, category);
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_LAST_NODE, new Boolean(false));
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_DEPTH, new Integer(0));
			%>

			<liferay-util:include page="/html/portlet/message_boards/view_thread_tree.jsp" />
		</c:when>
		<c:otherwise>
			<%@ include file="/html/portlet/message_boards/view_thread_flat.jspf" %>
		</c:otherwise>
	</c:choose>
</div>

</form>

<%
MBMessageFlagLocalServiceUtil.addReadFlags(messages, themeDisplay.getUserId());
%>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.message_boards.view_message.jsp");
%>