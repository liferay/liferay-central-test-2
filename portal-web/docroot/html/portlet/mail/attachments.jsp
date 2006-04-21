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
com.liferay.portlet.mail.model.Message msgModel = (com.liferay.portlet.mail.model.Message)session.getAttribute(WebKeys.MAIL_MESSAGE_MODEL);

List attachments = (List)request.getAttribute(WebKeys.MAIL_ATTACHMENTS);

Double totalSize =(Double)request.getAttribute(WebKeys.MAIL_ATTACHMENT_TOTAL_SIZE);
if (totalSize == null) {
	totalSize = new Double(0);
}

Boolean overLimit = (Boolean)session.getAttribute(WebKeys.MAIL_ATTACHMENT_OVER_LIMIT);
if (overLimit == null) {
	overLimit = Boolean.FALSE;
}
else {
	session.setAttribute(WebKeys.MAIL_ATTACHMENT_OVER_LIMIT, null);
}
%>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/mail/add_attachment" /></portlet:actionURL>" enctype="multipart/form-data" method="post" name="<portlet:namespace />fm" onSubmit="if (typeof document.<portlet:namespace />fm.<portlet:namespace />attachments != 'undefined') { document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:renderURL><portlet:param name="struts_action" value="/mail/compose_message" /></portlet:renderURL>'; document.<portlet:namespace />fm.<portlet:namespace />attachments_list.value = listSelect(document.<portlet:namespace />fm.<portlet:namespace />attachments); submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/mail/reorder_attachments" /></portlet:actionURL>'); } else { self.location = '<portlet:renderURL><portlet:param name="struts_action" value="/mail/compose_message" /></portlet:renderURL>'; } return false;">
<input name="<portlet:namespace />redirect" type="hidden" value="">
<input name="<portlet:namespace />attachments_list" type="hidden" value="">
<c:if test="<%= msgModel.isReturnReceipt() %>">
	<input name="<portlet:namespace />msg_return_receipt_1" type="hidden" value="on">
</c:if>
<c:if test="<%= msgModel.isHTMLFormat() %>">
	<input name="<portlet:namespace />html_cb_1" type="hidden" value="on">
</c:if>

<c:if test="<%= overLimit.booleanValue() %>">
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
			<font class="portlet-msg-error" style="font-size: xx-small;">
			<%= LanguageUtil.format(pageContext, "the-total-size-of-all-files-attached-to-a-message-may-not-exceed-x-k", TextFormatter.formatKB(MailUtil.ATTACHMENTS_MAX_SIZE, locale), false) %>
			</font>
		</td>
	</tr>
	</table>

	<br>
</c:if>

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "attachments") %>' />

	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td valign="top">
			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><%= LanguageUtil.get(pageContext, "please-be-patient-while-uploading-your-file") %></font><br>
					<input class="form-text" name="<portlet:namespace />file_name" size="50" type="file">
				</td>
			</tr>
			<tr>
				<td align="center">
					<br>

					<input class="portlet-form-button" name="<portlet:namespace />attach_btn" type="button" value="<bean:message key="attach-to-message" />" onClick="<liferay-ui:upload-progress /> document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:actionURL><portlet:param name="struts_action" value="/mail/attachments" /><portlet:param name="html_cb_1" value='<%= (msgModel.isHTMLFormat()) ? "on" : "" %>' /></portlet:actionURL>'; if (typeof document.<portlet:namespace />fm.<portlet:namespace />attachments != 'undefined') { document.<portlet:namespace />fm.<portlet:namespace />attachments_list.value = listSelect(document.<portlet:namespace />fm.<portlet:namespace />attachments); } submitForm(document.<portlet:namespace />fm);">

					<input class="portlet-form-button" type="submit" value="<%= LanguageUtil.get(pageContext, "finished") %>">
				</td>
			</tr>
			</table>
		</td>
		<td width="30">
			<img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="30">
		</td>
		<td valign="top">
			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "attachments") %>:</b></font>
				</td>
			</tr>
			<tr>
				<td>
					<table border="0" cellpadding="0" cellspacing="2">
					<tr>
						<td>
							<c:if test="<%= (attachments != null) && (attachments.size() > 0) %>">
								<select name="<portlet:namespace />attachments" size="5">

								<%
								for (int i = 0; i < attachments.size(); i++) {
									Attachment attachment = (Attachment)attachments.get(i);
								%>

									<option value="<%= attachment.getBodyPartId() %>"><%= attachment.getFileName() %>&nbsp;(<%= TextFormatter.formatKB(attachment.getSize(), locale) %>k)</option>

								<%
								}
								%>

								</select>
							</c:if>

							<c:if test="<%= (attachments == null) || (attachments.size() == 0) %>">
								<select name="<portlet:namespace />attachments" size="5">
								</select>
							</c:if>
						</td>
						<td valign="top">
							<a href="javascript: reorder(document.<portlet:namespace />fm.<portlet:namespace />attachments, 0);"><img border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/arrows/02_up.gif" vspace="2" width="16"></a><br>
							<a href="javascript: reorder(document.<portlet:namespace />fm.<portlet:namespace />attachments, 1);"><img border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/arrows/02_down.gif" vspace="2" width="16"></a><br>
							<a href="javascript: removeItem(document.<portlet:namespace />fm.<portlet:namespace />attachments);"><img border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/arrows/02_x.gif" vspace="2" width="16"></a><br>
						</td>
					</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><%= LanguageUtil.get(pageContext, "total-size") %> = <b><%= TextFormatter.formatKB(totalSize.doubleValue(), locale) %>k</b> (<%= TextFormatter.formatKB(MailUtil.ATTACHMENTS_MAX_SIZE, locale) %>k <%= LanguageUtil.get(pageContext, "maximum") %>)</font>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	</table>
</liferay-ui:box>

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />file_name.focus();
</script>