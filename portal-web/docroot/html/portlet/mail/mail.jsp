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

<%@ include file="/html/portlet/mail/init.jsp" %>

<%
String mailBgColor = "#F4F5EB";
String mailLineColor = "#B3B6B0";
%>

<style>
	.portlet-mail-toolbar {
		margin-bottom: 5px;
	}

	.portlet-mail-toolbar td {
		background-color: <%= colorScheme.getPortletBg() %>;
		border: 1px solid <%= mailLineColor %>;
		cursor: pointer;
		padding-left: 10px;
		padding-right: 10px;
	}
</style>

<c:choose>
	<c:when test="<%= renderRequest.getWindowState().equals(WindowState.NORMAL) %>">
		<table border="0" cellpadding="2" cellspacing="2" class="portlet-mail-toolbar font-small" id="portlet-mail-main-toolbar">
		<tr>
			<td nowrap onClick="location.href = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" />';">
				<img align="absmiddle" src="<%= themeDisplay.getPathThemeImage() %>/mail/check_mail.gif" /> <%= LanguageUtil.get(pageContext, "check-mail") %>
			</td>
			<td nowrap onClick="location.href = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mail/edit_message" /></portlet:renderURL>';">
				<img align="absmiddle" src="<%= themeDisplay.getPathThemeImage() %>/mail/compose.gif" /> <%= LanguageUtil.get(pageContext, "new") %>
			</td>
		</tr>
		</table>

		<%
		MailUtil.setFolder(request.getSession(), MailUtil.MAIL_INBOX_NAME);

		MailFolder folder = MailUtil.getFolder(request.getSession());
		%>

		<%= LanguageUtil.get(pageContext, "unread-messages") %>: <%= folder.getUnreadMessageCount() %>

		<br><br>

		<%
		int count = 0;

		Set envelopes = MailUtil.getEnvelopes(request.getSession(), new DateComparator(false));

		Iterator itr = envelopes.iterator();

		while (itr.hasNext() && (count < 10)) {
			MailEnvelope mailEnvelope = (MailEnvelope)itr.next();

			String recipient = GetterUtil.getString(
				mailEnvelope.getRecipient(), StringPool.NBSP);

			String subject = GetterUtil.getString(
				mailEnvelope.getSubject(), StringPool.NBSP);

			if (!mailEnvelope.isRead()) {
		%>

				<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="messageId" value="<%= String.valueOf(mailEnvelope.getMessageId()) %>" /><portlet:param name="folderId" value="<%= MailUtil.MAIL_INBOX_NAME %>" /></portlet:renderURL>">
				<span style="font-weight: bold"><%= recipient %></span> - <%= subject %><br />
				</a>

		<%
				count++;
			}
		}
		%>

	</c:when>
	<c:otherwise>
		<script src="<%= themeDisplay.getPathJavaScript() %>/portlet/mail.js" type="text/javascript"></script>

		<style type="text/css">

			#p_p_body_<%= PortletKeys.MAIL %> {
				background-color: <%= mailBgColor %>;
			}

			#portlet-mail-drag-indicator {
				color: <%= colorScheme.getLayoutBg() %>;
				font-weight: bold;
				padding: 2px 5px 2px 15px;
				position: absolute;
				background-color: <%= colorScheme.getLayoutTabSelectedText() %>;
				cursor: pointer;
			}

			#portlet-mail-folder-pane ul {
				margin: 5px 0 5px 5px;
				overflow: hidden;
			}

			.portlet-mail-folder-selected {
				background-color: <%= colorScheme.getLayoutTabBg() %>;
			}

			#portlet-mail-folder-pane ul li {
				padding: 2px 2px 2px 5px;
				margin: 1px 5px 1px 0;
				white-space: nowrap;
			}

			#portlet-mail-folder-pane-td {
				border: 1px solid <%= mailLineColor %>;
			}

			#portlet-mail-folder-pane {
				overflow: hidden;
				width: 100px;
			}

			#portlet-mail-msgs-pane {
				width: 100%;
			}

			.portlet-mail-msgs-pane-td {
				border: 1px solid <%= mailLineColor %>;
			}

			#portlet-mail-handle {
				background-color: <%= mailBgColor %>;
			}

			#portlet-mail-handle div {
				width: 5px;
				height: 1px;
				font-size: 0;
			}

			#portlet-mail-msgs-preview{
				overflow: hidden;
			}

			#portlet-mail-msgs-preview-pane {
				height: 200px;
				width: 100%;
				<c:choose>
					<c:when test="<%= BrowserSniffer.is_ie(request) %>">
						overflow: hidden;
						overflow-y: auto;
					</c:when>
					<c:when test="<%= BrowserSniffer.is_safari(request) %>">
						overflow: auto;
					</c:when>
					<c:otherwise>
						overflow: -moz-scrollbars-vertical;
					</c:otherwise>
				</c:choose>
			}

			#portlet-mail-msgs-preview-pane table {
				cursor: pointer;
			}

			#portlet-mail-msgs-handle {
				background-color: <%= mailBgColor %>;
				font-size: 0;
				height: 5px;
			}

			#portlet-mail-msg-header {
				background-color: <%= colorScheme.getLayoutTabBg() %>;
				overflow: hidden;
			}

			#portlet-mail-msg-header-div {
				padding: 5px;
			}

			#portlet-mail-msg-detailed-pane {
				overflow: hidden;
			}

			#portlet-mail-msg-frame-div {
				margin-left: 0px;
			}

			#portlet-mail-msg-detailed-frame {
				height: auto;
			}

			.portlet-mail-title-text span {
				padding-right: 5px;
			}

			#portlet-mail-msgs-title-state {
				overflow: hidden;
				width: 24px;
			}

			#portlet-mail-msgs-title-state div {
				padding: 2px 0 2px 5px;
			}

			#portlet-mail-msgs-title-from {
				overflow: hidden;
				width: 16px;
			}

			#portlet-mail-msgs-title-from div {
				padding: 2px 0 2px 5px;
			}

			#portlet-mail-msgs-title-from {
				overflow: hidden;
				width: 125px;
			}

			#portlet-mail-msgs-title-from div {
				padding: 2px 0 2px 5px;
			}

			#portlet-mail-msgs-title-subject {
				overflow: hidden;
				width: 250px;
			}

			#portlet-mail-msgs-title-subject div {
				padding: 2px 0 2px 5px;
			}

			#portlet-mail-msgs-title-received {
				overflow: hidden;
				width: 125px;
			}

			#portlet-mail-msgs-title-received div {
				padding: 2px 0 2px 5px;
			}

			#portlet-mail-msgs-title-size {
				overflow: hidden;
				<c:choose>
					<c:when test="<%= BrowserSniffer.is_safari(request) %>">
						width: 125px;
					</c:when>
					<c:otherwise>
						width: 100%;
					</c:otherwise>
				</c:choose>
			}

			#portlet-mail-msgs-title-size div {
				padding: 2px 0 2px 5px;
			}

			.portlet-mail-msgs-title {
				background-color: <%= colorScheme.getLayoutTabBg() %>;
			}

			#portlet-mail-msgs-from-handle {
			}

			#portlet-mail-msgs-subject-handle {
			}

			#portlet-mail-msgs-received-handle {
			}

			.portlet-mail-msgs-title-handle {
				font-size: 0;
				height: 1px;
				width: 3px;
			}

			#portlet-mail-msgs-state {
				overflow: hidden;
				width: 24px;
			}

			#portlet-mail-msgs-state div {
				height: 16px;
				overflow: hidden;
				padding-left: 5px;
				position: relative;
				width: 24px;
			}

			#portlet-mail-msgs-from {
				overflow: hidden;
				width: 125px;
			}

			#portlet-mail-msgs-from div {
				height: 16px;
				overflow: hidden;
				padding-left: 5px;
				position: relative;
				width: 800px;
			}

			#portlet-mail-msgs-subject {
				overflow: hidden;
				width: 250px;
			}

			#portlet-mail-msgs-subject div {
				height: 16px;
				overflow: hidden;
				padding-left: 5px;
				position: relative;
				width: 800px;
			}

			#portlet-mail-msgs-received {
				overflow: hidden;
				width: 125px;
			}

			#portlet-mail-msgs-received div {
				height: 16px;
				overflow: hidden;
				padding-left: 5px;
				position: relative;
				width: 800px;
			}

			#portlet-mail-msgs-size {
				overflow: hidden;
				<c:choose>
					<c:when test="<%= BrowserSniffer.is_safari(request) %>">
						width: 125px;
					</c:when>
					<c:otherwise>
						width: 100%;
					</c:otherwise>
				</c:choose>
			}

			#portlet-mail-msgs-size div {
				height: 16px;
				overflow: hidden;
				padding-left: 5px;
				position: relative;
				width: 800px;
			}

			#portlet-mail-bottom-handle {
				border-bottom: 1px solid <%= mailLineColor %>;
			}

			#portlet-mail-bottom-handle div {
				cursor: n-resize;
				font-size: 0;
				height: 5px;
			}
		</style>

		<table border="0" cellpadding="2" cellspacing="2" class="portlet-mail-toolbar font-small" id="portlet-mail-main-toolbar">
		<tr>
			<td nowrap onClick="location.href = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mail/edit_message" /></portlet:renderURL>';">
				<img align="absmiddle" src="<%= themeDisplay.getPathThemeImage() %>/mail/compose.gif" /> <%= LanguageUtil.get(pageContext, "new") %>
			</td>
			<td nowrap onClick="Mail.clearPreview(); Mail.getPreview();">
				<img align="absmiddle" src="<%= themeDisplay.getPathThemeImage() %>/mail/check_mail.gif" /> <%= LanguageUtil.get(pageContext, "check-mail") %>
			</td>
			<td nowrap onClick="Mail.submitCompose('reply', document.<portlet:namespace />fm);">
				<img align="absmiddle" src="<%= themeDisplay.getPathThemeImage() %>/mail/reply.gif" /> <%= LanguageUtil.get(pageContext, "reply") %>
			</td>
			<td nowrap onClick="Mail.submitCompose('replyAll', document.<portlet:namespace />fm);">
				<img align="absmiddle" src="<%= themeDisplay.getPathThemeImage() %>/mail/reply_all.gif" /> <%= LanguageUtil.get(pageContext, "reply-all") %>
			</td>
			<td nowrap onClick="Mail.submitCompose('forward', document.<portlet:namespace />fm);">
				<img align="absmiddle" src="<%= themeDisplay.getPathThemeImage() %>/mail/forward.gif" /> <%= LanguageUtil.get(pageContext, "forward") %>
			</td>
			<td nowrap onClick="Mail.deleteSelectedMessages();">
				<img align="absmiddle" src="<%= themeDisplay.getPathThemeImage() %>/mail/delete.gif" /> <%= LanguageUtil.get(pageContext, "delete") %>
			</td>
			<td nowrap onClick="Mail.print();">
				<img align="absmiddle" src="<%= themeDisplay.getPathThemeImage() %>/mail/print.gif" /> <%= LanguageUtil.get(pageContext, "print") %>
			</td>
		</tr>
		</table>

		<table border="0" cellpadding="3" cellspacing="2" class="portlet-mail-toolbar font-small" id="portlet-mail-drafts-toolbar" style="display: none;">
		<tr>
			<td nowrap onClick="location.href='<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mail/edit_message" /></portlet:renderURL>'">
				<img align="absmiddle" src="<%= themeDisplay.getPathThemeImage() %>/mail/compose.gif" /> <%= LanguageUtil.get(pageContext, "new") %>
			</td>
			<td nowrap onClick="Mail.submitCompose('edit', document.<portlet:namespace />fm)">
				<img align="absmiddle" src="<%= themeDisplay.getPathThemeImage() %>/mail/edit_draft.gif" /> <%= LanguageUtil.get(pageContext, "edit") %>
			</td>
			<td nowrap onClick="Mail.deleteSelectedMessages()">
				<img align="absmiddle" src="<%= themeDisplay.getPathThemeImage() %>/mail/delete.gif" /> <%= LanguageUtil.get(pageContext, "delete") %>
			</td>
		</tr>
		</table>
		</div>

		<form action="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mail/edit_message" /></portlet:renderURL>" method="post" name="<portlet:namespace />fm">
			<input name="<portlet:namespace /><%= Constants.CMD %>" id="portlet-mail-compose-action" type="hidden" />
			<input name="<portlet:namespace />messageId" id="portlet-mail-message-id" type="hidden" />
			<input name="<portlet:namespace />folderId" id="portlet-mail-folder-id" type="hidden" />
		</form>

		<table border="0" cellpadding="0" cellspacing="0" style="background-color: <%= colorScheme.getPortletBg() %>;">
		<tr>
			<td id="portlet-mail-folder-pane-td" valign="top">
				<div id="portlet-mail-folder-pane">
					<div style="padding-top: 50px; text-align: center;">
						<img src="<%= themeDisplay.getPathThemeImage() %>/progress_bar/loading_animation.gif" />
					</div>
				</div>
			</td>
			<td id="portlet-mail-handle" style="cursor: e-resize;">
				<div style="font-size: 0; height: 30px; width: 5px;"></div>
			</td>
			<td valign="top" width="100%">
				<div id="portlet-mail-msgs-pane">
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td class="portlet-mail-msgs-pane-td" width="100%">
							<div id="portlet-mail-msgs-preview">
								<table id="portlet-mail-msgs-preview-pane-title" border="0" cellpadding="0" cellspacing="0" <%= BrowserSniffer.is_safari(request) ? "" : "width=\"100%\"" %>>
								<tr>
									<td class="portlet-mail-msgs-title">
										<div id="portlet-mail-msgs-title-state">
											<div class="portlet-mail-title-text">
												<img src="<%= themeDisplay.getPathThemeImage() %>/mail/read.gif" />
											</div>
										</div>
									</td>
									<td>
										<div class="portlet-mail-msgs-title-handle"></div>
									</td>
									<td class="portlet-mail-msgs-title">
										<div id="portlet-mail-msgs-title-from">
											<div class="portlet-mail-title-text">
												<span><%= LanguageUtil.get(pageContext, "from") %></span><span style="display: none;"><%= LanguageUtil.get(pageContext, "to") %></span>
											</div>
										</div>
									</td>
									<td id="portlet-mail-msgs-from-handle" style="cursor: e-resize;">
										<div class="portlet-mail-msgs-title-handle"></div>
									</td>
									<td class="portlet-mail-msgs-title">
										<div id="portlet-mail-msgs-title-subject">
											<div class="portlet-mail-title-text">
												<span><%= LanguageUtil.get(pageContext, "subject") %></span>
											</div>
										</div>
									</td>
									<td id="portlet-mail-msgs-subject-handle" style="cursor: e-resize;">
										<div class="portlet-mail-msgs-title-handle"></div>
									</td>
									<td class="portlet-mail-msgs-title">
										<div id="portlet-mail-msgs-title-received">
											<div class="portlet-mail-title-text">
												<span><%= LanguageUtil.get(pageContext, "date") %></span>
											</div>
										</div>
									</td>
									<td id="portlet-mail-msgs-received-handle" style="cursor: e-resize;">
										<div class="portlet-mail-msgs-title-handle"></div>
									</td>
									<td class="portlet-mail-msgs-title" <%= BrowserSniffer.is_safari(request) ? "" : "width=\"90%\"" %>>
										<div id="portlet-mail-msgs-title-size">
											<div class="portlet-mail-title-text">
												<span><%= LanguageUtil.get(pageContext, "size") %></span>
											</div>
										</div>
									</td>
								</tr>
								</table>

								<div id="portlet-mail-msgs-preview-pane">
									<table border="0" cellpadding="0" cellspacing="0" <%= BrowserSniffer.is_safari(request) ? "" : "width=\"100%\"" %>>
									<tr>
										<td valign="top">
											<div id="portlet-mail-msgs-state">
											</div>
										</td>
										<td valign="top">
											<div class="portlet-mail-msgs-title-handle"></div>
										</td>
										<td valign="top">
											<div id="portlet-mail-msgs-from">
											</div>
										</td>
										<td valign="top">
											<div class="portlet-mail-msgs-title-handle"></div>
										</td>
										<td valign="top">
											<div id="portlet-mail-msgs-subject">
											</div>
										</td>
										<td valign="top">
											<div class="portlet-mail-msgs-title-handle"></div>
										</td>
										<td valign="top">
											<div id="portlet-mail-msgs-received">
											</div>
										</td>
										<td valign="top">
											<div class="portlet-mail-msgs-title-handle"></div>
										</td>
										<td valign="top" <%= BrowserSniffer.is_safari(request) ? "" : "width=\"90%\"" %>>
											<div id="portlet-mail-msgs-size">
											</div>
										</td>
									</tr>
									</table>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div id="portlet-mail-msgs-handle" style="cursor: n-resize;">
							</div>
						</td>
					</tr>
					<tr>
						<td class="portlet-mail-msgs-pane-td" width="100%">
							<div id="portlet-mail-msg-header">
								<div id="portlet-mail-msg-header-div"></div>
							</div>
							<div id="portlet-mail-msg-detailed-pane">
								<div id="portlet-mail-msg-frame-div">
									<iframe frameborder="0" id="portlet-mail-msg-detailed-frame" src="" width="100%"></iframe>
								</div>
							</div>
						</td>
					</tr>
					</table>
				</div>
			</td>
		</tr>
		</table>

		<div id="portlet-mail-bottom-handle"><div></div></div>

		<script type="text/javascript">

			<%
			String messageIdParam = ParamUtil.getString(request, "messageId", null);

			try {
				String folderId = MailUtil.getFolderName(request.getSession());
				long messageId = MailUtil.getMessageId(request.getSession());
			%>

				Mail.currentFolderId = "<%= folderId %>";
				Mail.currentMessageId = <%= (messageIdParam != null) ? messageIdParam : (messageId + "") %>;

			<%
			}
			catch (Exception e) {
			}
			%>

			Mail.highlightColor = "<%= colorScheme.getPortletMenuBg() %>";
			Mail.colorSelected = "<%= colorScheme.getLayoutTabBg() %>";

			Mail.init();
		</script>
	</c:otherwise>
</c:choose>