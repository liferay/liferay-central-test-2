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

<div style="margin: 5px 0px 0px <%= depth * 10 %>px; border: 1px solid <%= colorScheme.getPortletFontDim() %>; <%= BrowserSniffer.is_ie(request) ? "width: 100%;" : "" %>">
	<table cellpadding="0" cellspacing="0" style="table-layout: fixed;" width="100%">
	<tr>
		<td class="<%= className %>" rowspan="2" style="border-right: 1px solid <%= colorScheme.getPortletFontDim() %>; vertical-align: top;" width="100">
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
								User user2 = UserLocalServiceUtil.getUserById(message.getUserId());
								int posts = MBStatsUserLocalServiceUtil.getStatsUser(portletGroupId, message.getUserId()).getMessageCount();
								String rank = MBUtil.getRank(portletSetup, posts);
							%>

								<img src="<%= themeDisplay.getPathImage() %>/user_portrait?img_id=<%= message.getUserId() %>" style="margin:10px 0px; width:75%;"><br>

								<%= LanguageUtil.get(pageContext, "rank") %>: <%= rank %><br>
								<%= LanguageUtil.get(pageContext, "posts") %>: <%= posts %><br>
								<%= LanguageUtil.get(pageContext, "joined") %>: <%= dateFormatDate.format(user2.getCreateDate()) %>

							<%
							}
							catch (NoSuchStatsUserException nssue) {
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
							parentMessage = MBMessageLocalServiceUtil.getMessage(message.getParentMessageId());
						}
						catch (Exception e) {}
						%>

						<c:if test="<%= parentMessage != null %>">

							<%
							PortletURL parentMessageURL = renderResponse.createRenderURL();

							parentMessageURL.setWindowState(WindowState.MAXIMIZED);

							parentMessageURL.setParameter("struts_action", "/message_boards/view_message");
							parentMessageURL.setParameter("messageId", parentMessage.getMessageId());

							String author = parentMessage.isAnonymous() ? LanguageUtil.get(pageContext, "anonymous") : PortalUtil.getUserName(parentMessage.getUserId(), parentMessage.getUserName());
							%>

							<%= LanguageUtil.format(pageContext, "posted-as-a-reply-to", author) %>
						</c:if>
					</span>
				</div>

				<c:if test="<%= editable && MBCategoryPermission.contains(permissionChecker, category, ActionKeys.ADD_MESSAGE) %>">
					<div style="float: right;">
						<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="replyURL">
									<portlet:param name="struts_action" value="/message_boards/edit_message" />
									<portlet:param name="redirect" value="<%= currentURL %>" />
									<portlet:param name="categoryId" value="<%= message.getCategoryId() %>" />
									<portlet:param name="threadId" value="<%= message.getThreadId() %>" />
									<portlet:param name="parentMessageId" value="<%= message.getMessageId() %>" />
								</portlet:renderURL>

								<liferay-ui:icon image="reply" url="<%= replyURL %>" />

								<a href="<%= replyURL.toString() %>"><%= LanguageUtil.get(pageContext, "reply") %></a>
							</td>
							<td style="padding-left: 15px;"></td>
							<td>
								<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="quoteURL">
									<portlet:param name="struts_action" value="/message_boards/edit_message" />
									<portlet:param name="redirect" value="<%= currentURL %>" />
									<portlet:param name="categoryId" value="<%= message.getCategoryId() %>" />
									<portlet:param name="threadId" value="<%= message.getThreadId() %>" />
									<portlet:param name="parentMessageId" value="<%= message.getMessageId() %>" />
									<portlet:param name="quote" value="true" />
								</portlet:renderURL>

								<liferay-ui:icon image="quote" url="<%= quoteURL %>" />

								<a href="<%= quoteURL.toString() %>"><%= LanguageUtil.get(pageContext, "reply-with-quote") %></a>
							</td>
						</tr>
						</table>
					</div>
				</c:if>

				<div style="clear: both;"></div>
			</div>
			<div class="message-board-thread-body" style="padding: 15px;">
				<%= message.getBody(true) %>

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

								<a href="<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/message_boards/get_message_attachment" /><portlet:param name="messageId" value="<%= message.getMessageId() %>" /><portlet:param name="attachment" value="<%= fileName %>" /></portlet:actionURL>"><%= fileName %></a> (<%= TextFormatter.formatKB(fileSize, locale) %>k)&nbsp;&nbsp;&nbsp;

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

	<c:if test="<%= editable %>">
		<tr>
			<td class="<%= className %>" valign="bottom" width="90%">
				<div class="message-board-thread-bottom" style="padding:3px 5px; text-align:right;">
					<c:if test="<%= MBMessagePermission.contains(permissionChecker, message, ActionKeys.UPDATE) %>">
						<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL">
							<portlet:param name="struts_action" value="/message_boards/edit_message" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
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
						PortletURL categoryURL = renderResponse.createRenderURL();

						categoryURL.setWindowState(WindowState.MAXIMIZED);

						categoryURL.setParameter("struts_action", "/message_boards/view");
						categoryURL.setParameter("categoryId", message.getCategoryId());
						%>

						<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL">
							<portlet:param name="struts_action" value="/message_boards/edit_message" />
							<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
							<portlet:param name="redirect" value="<%= categoryURL.toString() %>" />
							<portlet:param name="messageId" value="<%= message.getMessageId() %>" />
						</portlet:actionURL>

						<liferay-ui:icon-delete url="<%= portletURL %>" />
					</c:if>
				</div>
			</td>
		</tr>
	</c:if>

	</table>
</div>