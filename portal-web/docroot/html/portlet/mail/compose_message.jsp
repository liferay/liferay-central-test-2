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

if (msgModel == null) {
	msgModel = new com.liferay.portlet.mail.model.Message(request, prefs);

	if (Validator.isNotNull(signature)) {
		String msgModelBody = null;

		if (msgModel.isHTMLFormat()) {
			msgModelBody = "<br><br>" + StringUtil.replace(signature, "\n", "<br>");
		}
		else {
			msgModelBody = "\n\n\n" + signature;
		}

		msgModel.setBody(msgModelBody);
	}

	session.setAttribute(WebKeys.MAIL_MESSAGE_MODEL, msgModel);
}

List attachments = (List)request.getAttribute(WebKeys.MAIL_ATTACHMENTS);
%>

<script language="JavaScript" event="onLoad" for="window">
	document.<portlet:namespace />fm.<portlet:namespace />msg_to.focus();
</script>

<script type="text/javascript">
	function initEditor() {
		return '<%= UnicodeFormatter.toString(msgModel.getBody()) %>';
	}

	function <portlet:namespace />submitMailForm(action) {
		<c:if test="<%= msgModel.isHTMLFormat() %>">
			if (document.<portlet:namespace />fm.<portlet:namespace />html_cb_1.checked) {
				document.<portlet:namespace />fm.<portlet:namespace />msg_body.value = parent.<portlet:namespace />editor.getHTML();
			}
			else {
				document.<portlet:namespace />fm.<portlet:namespace />msg_body.value = parent.<portlet:namespace />editor.getText();
			}
		</c:if>

		submitForm(document.<portlet:namespace />fm, action);
	}
</script>

<form method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace />redirect" type="hidden" value="">

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "compose") %>' />

	<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="top">
					<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "to") %>:</b></font>
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td>
							<input class="form-text" name="<portlet:namespace />msg_to" size="50" tabindex="1" type="text" value="<%= Html.formatTo(msgModel.getTo()) %>">
						</td>
					</tr>
					<tr>
						<td>
							<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "cc") %>:</b></font>
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td>
							<input class="form-text" name="<portlet:namespace />msg_cc" size="50" tabindex="2" type="text" value="<%= Html.formatTo(msgModel.getCc()) %>">
						</td>
					</tr>
					<tr>
						<td>
							<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "bcc") %>:</b></font>
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td>
							<input class="form-text" name="<portlet:namespace />msg_bcc" size="50" tabindex="3" type="text" value="<%= Html.formatTo(msgModel.getBcc()) %>">
						</td>
					</tr>
					<tr>
						<td>
							<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "subject") %>:</b></font>
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td>
							<input class="form-text" name="<portlet:namespace />msg_sub" size="50" tabindex="4" type="text" value="<%= msgModel.getSubject() %>"
								<c:if test="<%= BrowserSniffer.is_ie(request) %>">
									onKeyDown="
										if (window.event.keyCode == 9) {
											if (window.event.shiftKey) {
												document.<portlet:namespace />fm.<portlet:namespace />msg_bcc.focus();
											}
											else {
												<c:if test="<%= !msgModel.isHTMLFormat() %>">
													document.<portlet:namespace />fm.<portlet:namespace />msg_body.focus();
												</c:if>

												<c:if test="<%= msgModel.isHTMLFormat() %>">
													parent.<portlet:namespace />editor.focus();
												</c:if>
											}

											window.event.cancelBubble = true;
											window.event.returnValue = false;

											return false;
										}

										"
									</c:if>
							>
						</td>
					</tr>
					</table>
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td valign="top">
					<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "select-recipients") %>' onClick="<portlet:namespace />submitMailForm('<portlet:actionURL><portlet:param name="struts_action" value="/mail/select_recipients" /></portlet:actionURL>');">
						</td>
					</tr>
					<tr>
						<td><img border="0" height="3" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
					</tr>
					<tr>
						<td>
							<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "attachments") %>' onClick="<portlet:namespace />submitMailForm('<portlet:renderURL><portlet:param name="struts_action" value="/mail/attachments" /></portlet:renderURL>');"><br>
						</td>
					</tr>
					<tr>
						<td>
							<br>

							<font class="portlet-font" style="font-size: xx-small;">
							<%= LanguageUtil.format(pageContext, "to-email-joebloggs", company.getMx(), false) %>
							</font>
						</td>
					</tr>
					</table>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	<tr class="gamma">
		<td>
			<br>

			<c:if test="<%= (attachments != null) && (attachments.size() > 0) %>">
				<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td valign="top">
						<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "attachments") %>:</b></font>
					</td>
					<td width="10">
						&nbsp;
					</td>
					<td>
						<font class="portlet-font" style="font-size: x-small;">

						<%
						for (int i = 0; i < attachments.size(); i++) {
							Attachment attachment = (Attachment)attachments.get(i);
						%>

							<%= attachment.getFileName() %>&nbsp;(<%= TextFormatter.formatKB(attachment.getSize(), locale) %>k)&nbsp;&nbsp;

						<%
						}
						%>

						</font>
					</td>
				</tr>
				</table>

				<br>
			</c:if>

			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "send") %>' onClick="<portlet:namespace />submitMailForm('<portlet:actionURL><portlet:param name="struts_action" value="/mail/send_message" /></portlet:actionURL>');">

					<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "save-draft") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:renderURL><portlet:param name="struts_action" value="/mail/view_folder" /></portlet:renderURL>'; <portlet:namespace />submitMailForm('<portlet:actionURL><portlet:param name="struts_action" value="/mail/save_draft" /></portlet:actionURL>');">

					<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:renderURL><portlet:param name="struts_action" value="/mail/view_folder" /></portlet:renderURL>'; <portlet:namespace />submitMailForm('<portlet:actionURL><portlet:param name="struts_action" value="/mail/cancel_message" /></portlet:actionURL>');">
				</td>
				<td align="right">
					<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<font class="portlet-font" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "return-receipt") %></font>

							<input <%= (msgModel.isReturnReceipt()) ? "checked" : "" %> name="<portlet:namespace />msg_return_receipt_1" type="checkbox"
								onClick="
									if (is_rtf) {
										document.<portlet:namespace />fm.<portlet:namespace />msg_return_receipt_2.checked = this.checked;

										if (this.checked) {
											alert('<%= UnicodeLanguageUtil.get(pageContext, "you-will-be-sent-an-email-notification-when-each-of-the-recipients-of-this-email-have-opened-to-read-this-email") %>');

											if (!document.<portlet:namespace />fm.<portlet:namespace />html_cb_1.checked) {
												if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "this-functionality-will-only-work-when-you-send-rich-text-formatted-email.-do-you-want-to-enable-rich-text-formatting") %>')) {
													document.<portlet:namespace />fm.<portlet:namespace />html_cb_1.click();
												}
												else {
													document.<portlet:namespace />fm.<portlet:namespace />msg_return_receipt_1.checked = false;
													document.<portlet:namespace />fm.<portlet:namespace />msg_return_receipt_2.checked = false;
												}
											}
										}
									}
									else {
										alert('<%= UnicodeLanguageUtil.get(pageContext, "please-upgrade-your-browser-to-use-this-feature") %>');
									}"
							>
						</td>
						<td width="30">
							&nbsp;
						</td>
						<td>
							<font class="portlet-font" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "rich-text-format") %></font>

							<input <%= (msgModel.isHTMLFormat()) ? "checked" : "" %> name="<portlet:namespace />html_cb_1" type="checkbox"
								onClick="
									if (is_rtf) {
										document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:renderURL><portlet:param name="struts_action" value="/mail/compose_message" /></portlet:renderURL>';

										if (this.checked == false) {
											if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "changing-from-rich-text-format-to-plain-text-requires-removing-all-current-formatting") %>')) {
												document.<portlet:namespace />fm.<portlet:namespace />html_cb_2.checked = false;
												<portlet:namespace />submitMailForm('<portlet:actionURL><portlet:param name="struts_action" value="/mail/set_html_format" /></portlet:actionURL>');
											}
											else {
												this.checked = true;
											}
										}
										else {
											document.<portlet:namespace />fm.<portlet:namespace />html_cb_2.checked = true;
											<portlet:namespace />submitMailForm('<portlet:actionURL><portlet:param name="struts_action" value="/mail/set_html_format" /></portlet:actionURL>');
										}
									}
									else {
										alert('<%= UnicodeLanguageUtil.get(pageContext, "please-upgrade-your-browser-to-use-this-feature") %>');
										document.<portlet:namespace />fm.<portlet:namespace />html_cb_1.checked = false;
										document.<portlet:namespace />fm.<portlet:namespace />html_cb_2.checked = false;
									}"
							>
						</td>
					</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2"><img border="0" height="4" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
			</tr>
			<tr>
				<td colspan="2">
					<c:if test="<%= !msgModel.isHTMLFormat() %>">
						<textarea class="form-text" cols="80" id="<portlet:namespace />msg_body" name="<portlet:namespace />msg_body" rows="20" tabindex="5" wrap="soft" onKeyDown="checkTab(this); disableEsc();"><%= msgModel.getBody() %></textarea>
					</c:if>

					<c:if test="<%= msgModel.isHTMLFormat() %>">
						<input name="<portlet:namespace />msg_body" type="hidden" value="">

						<iframe frameborder="0" height="400" id="<portlet:namespace />editor" name="<portlet:namespace />editor" tabindex="5" scrolling="no" src="<%= themeDisplay.getPathJavaScript() %>/editor/editor.jsp?p_l_id=<%= plid %>&editorImpl=<%= PropsUtil.get(EDITOR_WYSIWYG_IMPL_KEY) %>" width="700"></iframe>
					</c:if>
				</td>
			</tr>
			<tr>
				<td colspan="2"><img border="0" height="4" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
			</tr>
			<tr>
				<td>
					<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "send") %>' onClick="<portlet:namespace />submitMailForm('<portlet:actionURL><portlet:param name="struts_action" value="/mail/send_message" /></portlet:actionURL>');">

					<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "save-draft") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:renderURL><portlet:param name="struts_action" value="/mail/view_folder" /></portlet:renderURL>'; <portlet:namespace />submitMailForm('<portlet:actionURL><portlet:param name="struts_action" value="/mail/save_draft" /></portlet:actionURL>');">

					<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:renderURL><portlet:param name="struts_action" value="/mail/view_folder" /></portlet:renderURL>'; <portlet:namespace />submitMailForm('<portlet:actionURL><portlet:param name="struts_action" value="/mail/cancel_message" /></portlet:actionURL>');">
				</td>
				<td align="right">
					<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<font class="portlet-font" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "return-receipt") %></font>

							<input <%= (msgModel.isReturnReceipt()) ? "checked" : "" %> name="<portlet:namespace />msg_return_receipt_2" type="checkbox"
								onClick="
									if (is_rtf) {
										document.<portlet:namespace />fm.<portlet:namespace />msg_return_receipt_1.checked = this.checked;

										if (this.checked) {
											alert('<%= UnicodeLanguageUtil.get(pageContext, "you-will-be-sent-an-email-notification-when-each-of-the-recipients-of-this-email-have-opened-to-read-this-email") %>');

											if (!document.<portlet:namespace />fm.<portlet:namespace />html_cb_1.checked) {
												if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "this-functionality-will-only-work-when-you-send-rich-text-formatted-email.-do-you-want-to-enable-rich-text-formatting") %>')) {
													document.<portlet:namespace />fm.<portlet:namespace />html_cb_1.click();
												}
												else {
													document.<portlet:namespace />fm.<portlet:namespace />msg_return_receipt_1.checked = false;
													document.<portlet:namespace />fm.<portlet:namespace />msg_return_receipt_2.checked = false;
												}
											}
										}
									}
									else {
										alert('<%= UnicodeLanguageUtil.get(pageContext, "please-upgrade-your-browser-to-use-this-feature") %>');
									}"
							>
						</td>
						<td width="30">
							&nbsp;
						</td>
						<td>
							<font class="portlet-font" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "rich-text-format") %></font>

							<input <%= (msgModel.isHTMLFormat()) ? "checked" : "" %> name="<portlet:namespace />html_cb_2" type="checkbox"
								onClick="
									if (is_rtf) {
										document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:renderURL><portlet:param name="struts_action" value="/mail/compose_message" /></portlet:renderURL>';

										if (this.checked == false) {
											if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "changing-from-rich-text-format-to-plain-text-requires-removing-all-current-formatting") %>')) {
												document.<portlet:namespace />fm.<portlet:namespace />html_cb_1.checked = false;
												<portlet:namespace />submitMailForm('<portlet:actionURL><portlet:param name="struts_action" value="/mail/set_html_format" /></portlet:actionURL>');
											}
											else {
												this.checked = true;
											}
										}
										else {
											document.<portlet:namespace />fm.<portlet:namespace />html_cb_1.checked = true;
											<portlet:namespace />submitMailForm('<portlet:actionURL><portlet:param name="struts_action" value="/mail/set_html_format" /></portlet:actionURL>');
										}
									}
									else {
										alert('<%= UnicodeLanguageUtil.get(pageContext, "please-upgrade-your-browser-to-use-this-feature") %>');
										document.<portlet:namespace />fm.<portlet:namespace />html_cb_1.checked = false;
										document.<portlet:namespace />fm.<portlet:namespace />html_cb_2.checked = false;
									}"
							>
						</td>
					</tr>
					</table>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	</table>
</liferay-ui:box>

</form>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.mail.compose_message.jsp";
%>