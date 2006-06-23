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
TreeWalker treeWalker = (TreeWalker)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER);
MBMessage selMessage = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_SEL_MESSAGE);
MBMessage message = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE);
MBTopic topic = (MBTopic)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_TOPIC);
boolean lastNode = ((Boolean)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_LAST_NODE)).booleanValue();
int depth = ((Integer)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_DEPTH)).intValue();

String className = "portlet-section-alternate";
String classHoverName = "portlet-section-alternate-hover";

if (treeWalker.isOdd()) {
	className = "portlet-section-body";
	classHoverName = "portlet-section-body-hover";
}
%>

<div style="margin: 5px 0px 0px <%= depth * 10 %>px; border: 1px solid <%= colorScheme.getPortletFontDim() %>; <%= BrowserSniffer.is_ie(request) ? "width: 100%;" : "" %>">
	<table cellpadding="0" cellspacing="0" style="table-layout: fixed;" width="100%">
	<tr>
		<td class="<%= className %>" rowspan="2" style="border-right: 1px solid <%= colorScheme.getPortletFontDim() %>;" width="100">
			<div class="message-board-thread-left" style="padding:5px;">
				<c:choose>
					<c:when test="<%= message.isAnonymous() %>">
						<%= LanguageUtil.get(pageContext, "anonymous") %>
					</c:when>
					<c:otherwise>
						<b><%= PortalUtil.getUserName(message.getUserId(), message.getUserName(), request) %></b><br>

						<span style="font-size: xx-small;">

							<%
							try {
								User messageUser = UserLocalServiceUtil.getUserById(message.getUserId());
							%>

								<img src="<%= themeDisplay.getPathImage() %>/user_portrait?img_id=<%= message.getUserId() %>" style="margin:10px 0px; width:75%;"><br>

								<%= LanguageUtil.get(pageContext, "posts") %>: <%= MBStatsUserLocalServiceUtil.getStatsUser(portletGroupId, message.getUserId()).getMessageCount() %><br>
								<%= LanguageUtil.get(pageContext, "created") %>: <%= dateFormatDate.format(messageUser.getCreateDate()) %><br>
								<%= LanguageUtil.get(pageContext, "organization") %>: <%= messageUser.getOrganization().getName() %><br>
								<%= LanguageUtil.get(pageContext, "location") %>: <%= messageUser.getLocation().getName() %>

							<%
							}
							catch (NoSuchUserException nsue) {
							}
							%>

						</span>
					</c:otherwise>
				</c:choose>
			</div>
		</td>
		<td class="<%= className %>" valign="top">
			<div class="message-board-thread-top" style="border-bottom: 1px solid <%= colorScheme.getPortletFontDim() %>; padding: 3px 5px;">
				<div style="float: left;">
					<span>
						<b><%= StringUtil.shorten(message.getSubject(), 50) %></b>
					</span>
					<span style="font-size: xx-small;">
						|

						<%= dateFormatDateTime.format(message.getModifiedDate()) %>

						<%
						MBMessage parentMessage = null;

						try {
							parentMessage = MBMessageLocalServiceUtil.getMessage(message.getTopicId(), message.getParentMessageId());
						}
						catch (Exception e) {}
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

							<%= LanguageUtil.format(pageContext, "posted-as-a-reply-to", author) %>
						</c:if>
					</span>
				</div>

				<c:if test="<%= MBTopicPermission.contains(permissionChecker, topic, ActionKeys.ADD_MESSAGE) %>">
					<div style="float: right;">
						<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL">
							<portlet:param name="struts_action" value="/message_boards/edit_message" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="topicId" value="<%= message.getTopicId() %>" />
							<portlet:param name="threadId" value="<%= message.getThreadId() %>" />
							<portlet:param name="parentMessageId" value="<%= message.getMessageId() %>" />
						</portlet:renderURL>

						<liferay-ui:icon image="reply" url="<%= portletURL %>" />

						<a href="<%= portletURL.toString() %>"><%= LanguageUtil.get(pageContext, "reply") %></a>
					</div>
				</c:if>

				<div style="clear: both;"></div>
			</div>
			<div class="message-board-thread-body" style="padding: 15px;">
				<%= message.getBody() %>

				<c:if test="<%= message.isAttachments() %>">
					<br><br>

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

								<a href="<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/message_boards/get_message_attachment" /><portlet:param name="topicId" value="<%= message.getTopicId() %>" /><portlet:param name="messageId" value="<%= message.getMessageId() %>" /><portlet:param name="attachment" value="<%= fileName %>" /></portlet:actionURL>"><%= fileName %></a> (<%= TextFormatter.formatKB(fileSize, locale) %>k)&nbsp;&nbsp;&nbsp;

							<%
							}
							%>

						</td>
					</tr>
					</table>
				</c:if>
			</div>
		</td>
	</tr>
	<tr>
		<td class="<%= className %>" valign="bottom" width="90%">
			<div class="message-board-thread-bottom" style="padding:3px 5px; text-align:right;">
				<c:if test="<%= MBMessagePermission.contains(permissionChecker, message, ActionKeys.UPDATE) %>">
					<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL">
						<portlet:param name="struts_action" value="/message_boards/edit_message" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="topicId" value="<%= message.getTopicId() %>" />
						<portlet:param name="messageId" value="<%= message.getMessageId() %>" />
					</portlet:renderURL>

					<liferay-ui:icon image="edit" url="<%= portletURL %>" />
				</c:if>

				<c:if test="<%= MBMessagePermission.contains(permissionChecker, message, ActionKeys.PERMISSIONS) %>">
					<liferay-security:permissionsURL
						modelResource="<%= MBMessage.class.getName() %>"
						modelResourceDescription="<%= message.getSubject() %>"
						resourcePrimKey="<%= message.getPrimaryKey().toString() %>"
						var="portletURL"
					/>

					<liferay-ui:icon image="permissions" url="<%= portletURL %>" />
				</c:if>

				<c:if test="<%= MBMessagePermission.contains(permissionChecker, message, ActionKeys.DELETE) %>">

					<%
					PortletURL topicURL = renderResponse.createRenderURL();

					topicURL.setWindowState(WindowState.MAXIMIZED);

					topicURL.setParameter("struts_action", "/message_boards/view_topic");
					topicURL.setParameter("topicId", message.getTopicId());
					%>

					<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL">
						<portlet:param name="struts_action" value="/message_boards/edit_message" />
						<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
						<portlet:param name="redirect" value="<%= topicURL.toString() %>" />
						<portlet:param name="topicId" value="<%= message.getTopicId() %>" />
						<portlet:param name="messageId" value="<%= message.getMessageId() %>" />
					</portlet:actionURL>

					<liferay-ui:icon-delete url="<%= portletURL %>" />
				</c:if>
			</div>
		</td>
	</tr>
	</table>
</div>

<%
List messages = treeWalker.getMessages();
int[] range = treeWalker.getChildrenRange(message);

depth++;

for (int i = range[0]; i < range[1]; i++) {
	MBMessage curMessage = (MBMessage)messages.get(i);

	boolean lastChildNode = false;

	if ((i + 1) == range[1]) {
		lastChildNode = true;
	}

	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER, treeWalker);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_SEL_MESSAGE, selMessage);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE, curMessage);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_TOPIC, topic);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_LAST_NODE, new Boolean(lastChildNode));
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_DEPTH, new Integer(depth));
%>

	<liferay-util:include page="/html/portlet/message_boards/view_message_thread.jsp" />

<%
}
%>