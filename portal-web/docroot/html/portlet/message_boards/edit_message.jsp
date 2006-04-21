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

if (Validator.isNotNull(threadId) && Validator.isNull(subject)) {
	subject = "RE: ";

	try {
		MBMessage parentMessage = MBMessageLocalServiceUtil.getMessage(topicId, parentMessageId);

		subject += parentMessage.getSubject();
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

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/edit_message" /></portlet:actionURL>" enctype="multipart/form-data" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveMessage(); return false";>
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>">
<input name="<portlet:namespace />messageRedirect" type="hidden" value="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/view_message" /><portlet:param name="topicId" value="<%= topicId %>" /></portlet:renderURL>&<portlet:namespace />messageId=">
<input name="<portlet:namespace />topicId" type="hidden" value="<%= topicId %>">
<input name="<portlet:namespace />messageId" type="hidden" value="<%= messageId %>">
<input name="<portlet:namespace />threadId" type="hidden" value="<%= threadId %>">
<input name="<portlet:namespace />parentMessageId" type="hidden" value="<%= parentMessageId %>">
<input name="<portlet:namespace />attachments" type="hidden" value="<%= attachments %>">

<liferay-ui:tabs names="message" />

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
		<iframe frameborder="0" height="400" id="<portlet:namespace />editor" name="<portlet:namespace />editor" scrolling="no" src="<%= themeDisplay.getPathJavaScript() %>/editor/editor.jsp?editor_impl=<%= PropsUtil.get(EDITOR_WYSIWYG_IMPL_KEY) %>" width="640"></iframe>

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
		<liferay-ui:input-select param="anonymous" />
	</td>
</tr>

<c:if test="<%= message == null %>">
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

<input class="portlet-form-button" type="submit" value='<%= LanguageUtil.get(pageContext, (message != null) ? "update" : ((Validator.isNull(threadId) ? "post-new-thread" : "reply"))) %>'>

<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, ((attachments) ? "remove" : "attach") + "-files") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace />body.value = parent.<portlet:namespace />editor.getHTML(); document.<portlet:namespace />fm.<portlet:namespace />attachments.value = '<%= !attachments %>'; submitForm(document.<portlet:namespace />fm);">

<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="self.location = '<%= redirect %>';">

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />subject.focus();
</script>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.message_boards.edit_message.jsp";
%>