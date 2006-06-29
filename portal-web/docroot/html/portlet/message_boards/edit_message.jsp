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
String redirect = ParamUtil.getString(request, "redirect");

MBMessage message = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE);

String topicId = BeanParamUtil.getString(message, request, "topicId");
String messageId = BeanParamUtil.getString(message, request, "messageId");

String threadId = BeanParamUtil.getString(message, request, "threadId");
String parentMessageId = BeanParamUtil.getString(message, request, "parentMessageId", MBMessage.DEFAULT_PARENT_MESSAGE_ID);

String subject = BeanParamUtil.getString(message, request, "subject");

MBMessage parentMessage = null;

if (Validator.isNotNull(threadId) && Validator.isNull(subject)) {
	try {
		parentMessage = MBMessageLocalServiceUtil.getMessage(topicId, parentMessageId);

		if (parentMessage.getSubject().startsWith("RE: ")) {
			subject = parentMessage.getSubject();
		}
		else {
			subject = "RE: " + parentMessage.getSubject();
		}
	}
	catch (Exception e) {
	}
}

String body = BeanParamUtil.getString(message, request, "body");
boolean attachments = BeanParamUtil.getBoolean(message, request, "attachments");
%>

<script type="text/javascript">
	function initEditor() {
		return "<%= UnicodeFormatter.toString(body) %>";
	}

	function <portlet:namespace />saveMessage() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= message == null ? Constants.ADD : Constants.UPDATE %>";
		document.<portlet:namespace />fm.<portlet:namespace />body.value = parent.<portlet:namespace />editor.getHTML();
		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/edit_message" /></portlet:actionURL>" enctype="multipart/form-data" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveMessage(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>">
<input name="<portlet:namespace />messageRedirect" type="hidden" value="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/view_message" /><portlet:param name="topicId" value="<%= topicId %>" /></portlet:renderURL>&<portlet:namespace />messageId=">
<input name="<portlet:namespace />topicId" type="hidden" value="<%= topicId %>">
<input name="<portlet:namespace />messageId" type="hidden" value="<%= messageId %>">
<input name="<portlet:namespace />threadId" type="hidden" value="<%= threadId %>">
<input name="<portlet:namespace />parentMessageId" type="hidden" value="<%= parentMessageId %>">
<input name="<portlet:namespace />attachments" type="hidden" value="<%= attachments %>">

<liferay-ui:tabs names="message" />

<liferay-ui:error exception="<%= CaptchaException.class %>" message="text-verification-failed" />
<liferay-ui:error exception="<%= MessageBodyException.class %>" message="please-enter-a-valid-message" />
<liferay-ui:error exception="<%= MessageSubjectException.class %>" message="please-enter-a-valid-subject" />

<liferay-ui:error exception="<%= FileNameException.class %>">

	<%
	String[] fileExtensions = PropsUtil.getArray(PropsUtil.DL_FILE_EXTENSIONS);
	%>

	<%= LanguageUtil.get(pageContext, "document-names-must-end-with-one-of-the-following-extensions") %> <%= StringUtil.merge(fileExtensions, ", ") %>.
</liferay-ui:error>

<liferay-ui:error exception="<%= FileSizeException.class %>" message="please-enter-a-file-with-a-valid-file-size" />

<%
String breadcrumbsMessageId = parentMessageId;

if (Validator.isNull(threadId)) {
	breadcrumbsMessageId = messageId;
}

if (message != null) {
	breadcrumbsMessageId = message.getMessageId();
}
%>

<%= MBUtil.getBreadcrumbs(null, topicId, breadcrumbsMessageId, pageContext, renderResponse) %>

<br><br>

<table border="0" cellpadding="0" cellspacing="0">
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "subject") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<liferay-ui:input-field model="<%= MBMessage.class %>" field="subject" defaultValue="<%= subject %>" />
	</td>
</tr>
<tr>
	<td colspan="3">
		<br>
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "body") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<iframe frameborder="0" height="400" id="<portlet:namespace />editor" name="<portlet:namespace />editor" scrolling="no" src="<%= themeDisplay.getPathJavaScript() %>/editor/editor.jsp?p_l_id=<%= plid %>&editorImpl=<%= PropsUtil.get(EDITOR_WYSIWYG_IMPL_KEY) %>" width="640"></iframe>

		<input name="<portlet:namespace />body" type="hidden" value="">
	</td>
</tr>

<c:if test="<%= attachments %>">
	<tr>
		<td colspan="3">
			<br>
		</td>
	</tr>

	<%
	for (int i = 1; i <= 5; i++) {
	%>

		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "file") %> <%= i %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<input class="form-text" name="<portlet:namespace />msgFile<%= i %>" size="70" type="file">
			</td>
		</tr>

	<%
	}
	%>

</c:if>

<c:if test="<%= message == null %>">
	<tr>
		<td colspan="3">
			<br>
		</td>
	</tr>
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "anonymous") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<liferay-ui:input-checkbox param="anonymous" />
		</td>
	</tr>
	<tr>
		<td colspan="3">
			<br>
		</td>
	</tr>
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "permissions") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<liferay-ui:input-permissions />
		</td>
	</tr>
</c:if>

</table>

<br>

<c:if test="<%= message == null %>">
	<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>" var="captchaURL">
		<portlet:param name="struts_action" value="/message_boards/captcha" />
	</portlet:actionURL>

	<liferay-ui:captcha url="<%= captchaURL %>" />
</c:if>

<input class="portlet-form-button" type="submit" value='<%= LanguageUtil.get(pageContext, (message != null) ? "update" : ((Validator.isNull(threadId) ? "post-new-thread" : "reply"))) %>'>

<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, ((attachments) ? "remove" : "attach") + "-files") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace />body.value = parent.<portlet:namespace />editor.getHTML(); document.<portlet:namespace />fm.<portlet:namespace />attachments.value = '<%= !attachments %>'; submitForm(document.<portlet:namespace />fm);">

<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="self.location = '<%= redirect %>';">

</form>

<c:if test="<%= parentMessage != null %>">
<br>
<%= LanguageUtil.get(pageContext, "replying-to") %>

<div style="margin: 5px 0px 0px 0px; border: 1px solid <%= colorScheme.getPortletFontDim() %>; <%= BrowserSniffer.is_ie(request) ? "width: 100%;" : "" %>">
	<table cellpadding="0" cellspacing="0" style="table-layout: fixed;" width="100%">
	<tr>
		<td class="portlet-section-body" rowspan="2" style="border-right: 1px solid <%= colorScheme.getPortletFontDim() %>; vertical-align: top;" width="100">
			<div class="message-board-thread-left" style="padding:5px;">
				<c:choose>
					<c:when test="<%= parentMessage.isAnonymous() %>">
						<%= LanguageUtil.get(pageContext, "anonymous") %>
					</c:when>
					<c:otherwise>
						<b><%= PortalUtil.getUserName(parentMessage.getUserId(), parentMessage.getUserName(), request) %></b><br>

						<span style="font-size: xx-small;">

							<%
							try {
								User user2 = UserLocalServiceUtil.getUserById(parentMessage.getUserId());
								int posts = MBStatsUserLocalServiceUtil.getStatsUser(portletGroupId, parentMessage.getUserId()).getMessageCount();
								String rank = MBUtil.getRank(portletSetup, posts);
							%>

								<img src="<%= themeDisplay.getPathImage() %>/user_portrait?img_id=<%= parentMessage.getUserId() %>" style="margin:10px 0px; width:75%;"><br>

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
		<td class="portlet-section-body" valign="top">
			<div class="message-board-thread-top" style="border-bottom: 1px solid <%= colorScheme.getPortletFontDim() %>; padding: 3px 5px;">
				<div style="float: left;">
					<span>
						<b><%= StringUtil.shorten(parentMessage.getSubject(), 50) %></b>
					</span>
					<span style="font-size: xx-small;">
						|

						<%= dateFormatDateTime.format(parentMessage.getModifiedDate()) %>
					</span>
				</div>

				<div style="clear: both;"></div>
			</div>
			<div class="message-board-thread-body" style="padding: 15px;">
				<%= parentMessage.getBody() %>

				<c:if test="<%= parentMessage.isAttachments() %>">
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
								fileNames = DLServiceUtil.getFileNames(company.getCompanyId(), Company.SYSTEM, parentMessage.getAttachmentsDir());
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
	</table>
</div>
</c:if>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />subject.focus();
</script>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.message_boards.edit_message.jsp";
%>