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
MBMessageDisplay messageDisplay = (MBMessageDisplay)request.getAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE);

MBTopic topic = messageDisplay.getTopic();
MBMessage message = messageDisplay.getMessage();

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
<input name="<portlet:namespace />breadcrumbsTopicId" type="hidden" value="<%= topic.getTopicId() %>">
<input name="<portlet:namespace />breadcrumbsMessageId" type="hidden" value="<%= message.getMessageId() %>">
<input name="<portlet:namespace />topicIds" type="hidden" value="<%= topic.getTopicId() %>">
<input name="<portlet:namespace />threadId" type="hidden" value="<%= message.getThreadId() %>">

<%= MBUtil.getBreadcrumbs(null, topic, message, pageContext, renderResponse) %>

<br><br>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
<tr>
	<td>
		<c:if test="<%= previousMessage != null %>">
			<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/view_message" /><portlet:param name="topicId" value="<%= topic.getTopicId() %>" /><portlet:param name="messageId" value="<%= previousMessage.getMessageId() %>" /></portlet:renderURL>">
		</c:if>

		&laquo; <%= LanguageUtil.get(pageContext, "previous-message") %>

		<c:if test="<%= previousMessage != null %>">
			</a>
		</c:if>

		|

		<c:if test="<%= nextMessage != null %>">
			<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/view_message" /><portlet:param name="topicId" value="<%= topic.getTopicId() %>" /><portlet:param name="messageId" value="<%= nextMessage.getMessageId() %>" /></portlet:renderURL>">
		</c:if>

		<%= LanguageUtil.get(pageContext, "next-message") %> &raquo;

		<c:if test="<%= nextMessage != null %>">
			</a>
		</c:if>
	</td>
	<td>
		<c:if test="<%= previousThread != null %>">
			<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/view_message" /><portlet:param name="topicId" value="<%= topic.getTopicId() %>" /><portlet:param name="messageId" value="<%= previousThread.getRootMessageId() %>" /></portlet:renderURL>">
		</c:if>

		&laquo; <%= LanguageUtil.get(pageContext, "previous-thread") %>

		<c:if test="<%= !isFirstThread %>">
			</a>
		</c:if>

		|

		<c:if test="<%= nextThread != null %>">
			<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/view_message" /><portlet:param name="topicId" value="<%= topic.getTopicId() %>" /><portlet:param name="messageId" value="<%= nextThread.getRootMessageId() %>" /></portlet:renderURL>">
		</c:if>

		<%= LanguageUtil.get(pageContext, "next-thread") %> &raquo;

		<c:if test="<%= isLastThread %>">
			</a>
		</c:if>
	</td>
	<td align="right">
		<c:if test="<%= MBTopicPermission.contains(permissionChecker, topic, ActionKeys.ADD_MESSAGE) %>">
			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL">
						<portlet:param name="struts_action" value="/message_boards/edit_message" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="topicId" value="<%= topic.getTopicId() %>" />
						<portlet:param name="threadId" value="<%= message.getThreadId() %>" />
						<portlet:param name="parentMessageId" value="<%= message.getMessageId() %>" />
					</portlet:renderURL>

					<liferay-ui:icon image="reply" url="<%= portletURL %>" />

					<a href="<%= portletURL.toString() %>"><%= LanguageUtil.get(pageContext, "reply") %></a>
				</td>
				<td style="padding-left: 15px;"></td>
				<td>
					<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL">
						<portlet:param name="struts_action" value="/message_boards/edit_message" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="topicId" value="<%= topic.getTopicId() %>" />
					</portlet:renderURL>

					<liferay-ui:icon image="post" message="post-new-thread" url="<%= portletURL %>" />

					<a href="<%= portletURL.toString() %>"><%= LanguageUtil.get(pageContext, "post-new-thread") %></a>
				</td>
			</tr>
			</table>
		</c:if>
	</td>
</tr>
</table>

<br>

<span style="font-size: small;"><b>
<%= message.getSubject() %>
</b></span><br>

<%= LanguageUtil.get(pageContext, "by") %>

<c:choose>
	<c:when test="<%= message.isAnonymous() %>">
		<%= LanguageUtil.get(pageContext, "anonymous") %>
	</c:when>
	<c:otherwise>
		<%= PortalUtil.getUserName(message.getUserId(), message.getUserName(), request) %>
	</c:otherwise>
</c:choose>

, <%= LanguageUtil.get(pageContext, "on") %> <%= dateFormatDateTime.format(message.getModifiedDate()) %>

<br><br>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
<tr>
	<td>
		<%= message.getBody() %>

		<br><br>

		<c:if test="<%= message.isAttachments() %>">
			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="top">
					<b><%= LanguageUtil.get(pageContext, "attachments") %>:</b>
				</td>
				<td style="padding-left: 10px;"></td>
				<td>

					<%
					String[] fileNames = null;

					try {
						fileNames = DLServiceUtil.getFileNames(company.getCompanyId(), Company.SYSTEM, message.getAttachmentsDir());
					}
					catch (NoSuchDirectoryException nsde) {
					}

					for (int i = 0; (fileNames != null) && (i < fileNames.length); i++) {
						String fileName = FileUtil.getShortFileName(fileNames[i]);
						long fileSize = DLServiceUtil.getFileSize(company.getCompanyId(), Company.SYSTEM, fileNames[i]);
					%>

						<a href="<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/message_boards/get_message_attachment" /><portlet:param name="topicId" value="<%= topic.getTopicId() %>" /><portlet:param name="messageId" value="<%= message.getMessageId() %>" /><portlet:param name="attachment" value="<%= fileName %>" /></portlet:actionURL>"><%= fileName %></a> (<%= TextFormatter.formatKB(fileSize, locale) %>k)&nbsp;&nbsp;&nbsp;

					<%
					}
					%>

				</td>
			</tr>
			</table>

			<br>
		</c:if>
	</td>
</tr>
</table>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
<tr>
	<td>
		<input class="form-text" name="<portlet:namespace />keywords" size="30" type="text">

		<input class="portlet-form-button" type="submit" value="<%= LanguageUtil.get(pageContext, "search-messages") %>">
	</td>
	<td align="right">

		<%
		MBMessage parentMessage = messageDisplay.getParentMessage();
		%>

		<c:if test="<%= parentMessage != null %>">

			<%
			PortletURL parentMessageURL = renderResponse.createRenderURL();

			parentMessageURL.setWindowState(WindowState.MAXIMIZED);

			parentMessageURL.setParameter("struts_action", "/message_boards/view_message");
			parentMessageURL.setParameter("topicId", parentMessage.getTopicId());
			parentMessageURL.setParameter("messageId", parentMessage.getMessageId());

			String author = parentMessage.isAnonymous() ? LanguageUtil.get(pageContext, "anonymous") : PortalUtil.getUserName(parentMessage.getUserId(), parentMessage.getUserName());
			%>

			<span style="font-size: xx-small;">
			<%= LanguageUtil.format(pageContext, "posted-as-a-reply-to", new Object[] {parentMessageURL.toString(), parentMessage.getMessageId(), author})  %>
			</span>
		</c:if>
	</td>
</tr>
</table>

<br>

<table border="0" cellpadding="4" cellspacing="0" width="100%">
<tr>
	<td class="portlet-section-header">
		<table border="0" cellpadding="4" cellspacing="0" width="100%">
		<tr class="portlet-section-header" style="font-size: x-small;">
			<td colspan="10">
				<b><%= LanguageUtil.get(pageContext, "message-thread") %></b> [ <a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/view_message" /><portlet:param name="topicId" value="<%= topic.getTopicId() %>" /><portlet:param name="messageId" value="<%= message.getMessageId() %>" /><portlet:param name="threadView" value="<%= Boolean.toString(!threadView) %>" /></portlet:renderURL>"><%= threadView ? LanguageUtil.get(pageContext, "hide") : LanguageUtil.get(pageContext, "view") %></a> ]
			</td>
		</tr>

		<%
		if (threadView) {
			TreeWalker treeWalker = messageDisplay.getTreeWalker();
		%>

			<tr class="portlet-section-header" style="font-size: x-small; font-weight: bold;">
				<td colspan="2">
					<%= LanguageUtil.get(pageContext, "subject") %>
				</td>
				<td></td>
				<td>
					<%= LanguageUtil.get(pageContext, "author") %>
				</td>
				<td></td>
				<td>
					<%= LanguageUtil.get(pageContext, "date") %>
				</td>
				<td></td>
				<td>
					<img src="<%= themeDisplay.getPathThemeImage() %>/common/clip.gif">
				</td>
				<td></td>
				<td></td>
			</tr>

			<%
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER, treeWalker);
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_SEL_MESSAGE, message);
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE, treeWalker.getRoot());
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_LAST_NODE, new Boolean(false));
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_DEPTH, new Integer(0));
			%>

			<liferay-util:include page="/html/portlet/message_boards/view_message_thread.jsp" />

		<%
		}
		%>

		</table>
	</td>
</tr>
</table>

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />keywords.focus();
</script>