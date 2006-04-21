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
DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, locale);
df.setTimeZone(timeZone);

Folder[] moveToFolders = MailUtil.getMoveToFolders(request);

MimeMessage msg = (MimeMessage)request.getAttribute(WebKeys.MAIL_MESSAGE);
InternetAddress[] from = (InternetAddress[])msg.getFrom();

Folder folder = msg.getFolder();
String folderName = folder.getName();

String msgDiv = (String)request.getAttribute(WebKeys.MAIL_MESSAGE_DIV);
if (Validator.isNull(msgDiv)) {
	msgDiv = "1";
}

String msgPrevious = (String)request.getAttribute("msg_previous");
String msgNext = (String)request.getAttribute("msg_next");

List attachments = (List)request.getAttribute(WebKeys.MAIL_ATTACHMENTS);

// Address Book URL

String addressBookLayoutId = PortalUtil.getLayoutIdWithPortletId(layouts, PortletKeys.ADDRESS_BOOK, layoutId);

PortletURL addressBookURL = new PortletURLImpl(request, PortletKeys.ADDRESS_BOOK, addressBookLayoutId, true);

addressBookURL.setWindowState(WindowState.MAXIMIZED);
addressBookURL.setPortletMode(PortletMode.VIEW);

// Mail URL

PortletURL mailURL = renderResponse.createRenderURL();

mailURL.setParameter("struts_action", "/mail/view_message");
mailURL.setParameter("folder_name", folderName);
mailURL.setParameter("msg_num", Integer.toString(msg.getMessageNumber()));

String mailURLToString = mailURL.toString();
%>

<form method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />redirect" type="hidden" value="">

<%
PortletURL redirectURL = renderResponse.createRenderURL();

String nextMsgNum = (String)request.getAttribute("next_msg_num_after_delete");

if (Validator.isNotNull(nextMsgNum) && !nextMsgNum.equals("-1")) {
	redirectURL.setParameter("struts_action", "/mail/view_message");
	redirectURL.setParameter("folder_name", folderName);
	redirectURL.setParameter("msg_num", nextMsgNum);
}
else {
	redirectURL.setParameter("struts_action", "/mail/view_folder");
	redirectURL.setParameter("folder_name", folderName);
}

String redirectURLToString = redirectURL.toString();
%>

<input name="<portlet:namespace />folder_name" type="hidden" value="<%= folderName %>">
<input name="<portlet:namespace />folder_name_2" type="hidden" value="">
<input name="<portlet:namespace />msg_num" type="hidden" value="<%= msg.getMessageNumber() %>">
<input name="<portlet:namespace />msg_numbers" type="hidden" value="<%= msg.getMessageNumber() %>">

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value="<%= (MailUtil.isDefaultFolder(folderName)) ? LanguageUtil.get(pageContext, folderName) : folderName %>" />

	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "reply") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace />cmd.value = 'reply'; submitForm(document.<portlet:namespace />fm, '<portlet:renderURL><portlet:param name="struts_action" value="/mail/compose_message" /></portlet:renderURL>');">

			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "reply-all") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace />cmd.value = 'reply_all'; submitForm(document.<portlet:namespace />fm, '<portlet:renderURL><portlet:param name="struts_action" value="/mail/compose_message" /></portlet:renderURL>');">

			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "forward") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace />cmd.value = 'forward'; submitForm(document.<portlet:namespace />fm, '<portlet:renderURL><portlet:param name="struts_action" value="/mail/compose_message" /></portlet:renderURL>');">

			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "delete") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<%= redirectURLToString %>'; submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/mail/delete_message" /></portlet:actionURL>');">

			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "block") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<%= redirectURLToString %>'; submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/mail/block_sender" /></portlet:actionURL>');">

			<!--
			<script type="text/javascript">
				if (printCheck()) {
					document.write("<input type=\"button\" value=\"<%= LanguageUtil.get(pageContext, "print") %>\" onClick=\"printWindow()\">");
				}
			</script>
			-->

			<select name="<portlet:namespace />mail_f_l_1" onChange="if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-move-selected-messages-to") %> ' + document.<portlet:namespace />fm.<portlet:namespace />mail_f_l_1[document.<portlet:namespace />fm.<portlet:namespace />mail_f_l_1.selectedIndex].text + '?')) { document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<%= redirectURLToString %>'; document.<portlet:namespace />fm.<portlet:namespace />folder_name_2.value = document.<portlet:namespace />fm.<portlet:namespace />mail_f_l_1.options[document.<portlet:namespace />fm.<portlet:namespace />mail_f_l_1.options.selectedIndex].value; submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/mail/move_message" /></portlet:actionURL>'); } else { this.selectedIndex = 0; }">
				<option value=""><%= LanguageUtil.get(pageContext, "move-to") %>...</option>

				<%
				for (int i = 0; i < moveToFolders.length; i++) {
					Folder moveToFolder = moveToFolders[i];
				%>

					<c:if test="<%= !folderName.equals(moveToFolder.getName()) %>">
						<option value="<%= moveToFolder.getName() %>"><%= LanguageUtil.get(pageContext, moveToFolder.getName()) %></option>
					</c:if>

				<%
				}

				for (int i = 0; i < extraFolders.length; i++) {
					Folder extraFolder = MailUtil.getFolder(request, extraFolders[i]);
				%>

					<c:if test="<%= !folderName.equals(extraFolder.getName()) %>">
						<option value="<%= extraFolder.getName() %>"><%= extraFolder.getName() %></option>
					</c:if>

				<%
				}
				%>

			</select>
		</td>
		<td align="right">
			<font class="portlet-font" style="font-size: x-small;">

			<c:if test="<%= msgPrevious != null %>">
				<a href="<portlet:renderURL><portlet:param name="struts_action" value="/mail/view_message" /><portlet:param name="folder_name" value="<%= folderName %>" /><portlet:param name="msg_num" value="<%= msgPrevious %>" /></portlet:renderURL>"><%= LanguageUtil.get(pageContext, "previous") %></a>
			</c:if>

			<c:if test="<%= (msgPrevious != null) && (msgNext != null) %>">
				-
			</c:if>

			<c:if test="<%= msgNext != null %>">
				<a href="<portlet:renderURL><portlet:param name="struts_action" value="/mail/view_message" /><portlet:param name="folder_name" value="<%= folderName %>" /><portlet:param name="msg_num" value="<%= msgNext %>" /></portlet:renderURL>"><%= LanguageUtil.get(pageContext, "next") %></a>
			</c:if>

			</font>
		</td>
	</tr>
	</table>

	<br>

	<table border="0" cellpadding="0" cellspacing="0" width="100%">

	<c:if test="<%= messageHeaders != 0 %>">
		<tr>
			<td width="1%">
				<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "from") %>:</b></font>
			</td>
			<td width="10"><img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="10"></td>
			<td width="99%">
				<font class="portlet-font" style="font-size: x-small;">

				<c:if test="<%= from[0].getPersonal() == null %>">
					<%= _format(from[0], addressBookURL, mailURLToString) %>
				</c:if>

				<c:if test="<%= from[0].getPersonal() != null %>">
					<%= from[0].getPersonal() %> &#60;<%= _format(from[0], addressBookURL, mailURLToString) %>&#62;
				</c:if>

				</font>
			</td>
		</tr>

		<%
		InternetAddress[] toRecipients = (InternetAddress[])msg.getRecipients(Message.RecipientType.TO);
		%>

		<c:if test="<%= toRecipients != null %>">
			<tr>
				<td nowrap valign="top" width="1%">
					<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "to") %>:</b></font>

					<c:if test="<%= toRecipients.length > messageRecipientsLimit %>">
						<img border="0" height="9" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/arrows/01_plus.gif" title="<%= LanguageUtil.get(pageContext, "show") %>" vspace="0" width="9"
							onClick="
								if (this.src.indexOf('arrows/01_plus.gif') != -1) {
									document.getElementById('msg_to').style.display = '';
									this.src = '<%= themeDisplay.getPathThemeImage() %>/arrows/01_minus.gif';
									this.title = '<%= UnicodeLanguageUtil.get(pageContext, "hide") %>';
								}
								else {
									document.getElementById('msg_to').style.display = 'none';
									this.src = '<%= themeDisplay.getPathThemeImage() %>/arrows/01_plus.gif';
									this.title = '<%= UnicodeLanguageUtil.get(pageContext, "show") %>';
								}"
						>
					</c:if>
				</td>
				<td></td>
				<td id="msg_to" <%= (toRecipients.length > messageRecipientsLimit) ? "style=\"display: none;\"" : "" %>>
					<font class="portlet-font" style="font-size: x-small;">

					<%
					for (int i = 0; i < toRecipients.length; i++) {
						InternetAddress recipient = (InternetAddress)toRecipients[i];
					%>

						<c:if test="<%= recipient.getPersonal() == null %>">
							<%= _format(recipient, addressBookURL, mailURLToString) %><%= (i != (toRecipients.length - 1)) ? ",&nbsp;" : "" %>
						</c:if>

						<c:if test="<%= recipient.getPersonal() != null %>">
							<%= recipient.getPersonal() %> &#60;<%= _format(recipient, addressBookURL, mailURLToString) %>&#62;<%= (i != (toRecipients.length - 1)) ? ",&nbsp;" : "" %>
						</c:if>

					<%
					}
					%>

					</font>
				</td>
			</tr>
		</c:if>

		<%
		InternetAddress[] ccRecipients = (InternetAddress[])msg.getRecipients(Message.RecipientType.CC);
		%>

		<c:if test="<%= ccRecipients != null %>">
			<tr>
				<td nowrap valign="top">
					<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "cc") %>:</b></font>

					<c:if test="<%= ccRecipients.length > messageRecipientsLimit %>">
						<img border="0" height="9" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/arrows/01_plus.gif" title="<%= LanguageUtil.get(pageContext, "show") %>" vspace="0" width="9"
							onClick="
								if (this.src.indexOf('arrows/01_plus.gif') != -1) {
									document.getElementById('msg_cc').style.display = '';
									this.src = '<%= themeDisplay.getPathThemeImage() %>/arrows/01_minus.gif';
									this.title = '<%= UnicodeLanguageUtil.get(pageContext, "hide") %>';
								}
								else {
									document.getElementById('msg_cc').style.display = 'none';
									this.src = '<%= themeDisplay.getPathThemeImage() %>/arrows/01_plus.gif';
									this.title = '<%= UnicodeLanguageUtil.get(pageContext, "show") %>';
								}"
						>
					</c:if>
				</td>
				<td></td>
				<td id="msg_cc" <%= (ccRecipients.length > messageRecipientsLimit) ? "style=\"display: none;\"" : "" %>>
					<font class="portlet-font" style="font-size: x-small;">

					<%
					for (int i = 0; i < ccRecipients.length; i++) {
						InternetAddress recipient = (InternetAddress)ccRecipients[i];
					%>

						<c:if test="<%= recipient.getPersonal() == null %>">
							<%= _format(recipient, addressBookURL, mailURLToString) %><%= (i != (ccRecipients.length - 1)) ? ",&nbsp;" : "" %>
						</c:if>

						<c:if test="<%= recipient.getPersonal() != null %>">
							<%= recipient.getPersonal() %> &#60;<%= _format(recipient, addressBookURL, mailURLToString) %>&#62;<%= (i != (ccRecipients.length - 1)) ? ",&nbsp;" : "" %>
						</c:if>

					<%
					}
					%>

					</font>
				</td>
			</tr>
		</c:if>

		<tr>
			<td>
				<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "subject") %>:</b></font>
			</td>
			<td width="10">
				&nbsp;
			</td>
			<td>
				<font class="portlet-font" style="font-size: x-small;"><%= msg.getSubject() %></font>
			</td>
		</tr>
		<tr>
			<td>
				<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "date") %>:</b></font>
			</td>
			<td width="10">
				&nbsp;
			</td>
			<td>
				<font class="portlet-font" style="font-size: x-small;">

				<c:if test="<%= msg.getReceivedDate() != null %>">
					<%= df.format(msg.getReceivedDate()) %>
				</c:if>

				</font>
			</td>
		</tr>
	</c:if>

	<c:if test="<%= (messageHeaders == 2) || (messageHeaders == 3) %>">
		<tr>
			<td colspan="2">
				&nbsp;
			</td>
			<td>
				<font class="portlet-font" style="font-size: xx-small;">

				<%
				Enumeration e = msg.getAllHeaderLines();

				while (e.hasMoreElements()) {
				%>

					<%= e.nextElement() %><br>

				<%
				}
				%>

				</font>
			</td>
		</tr>
	</c:if>

	<tr>
		<td colspan="3">
			<br>

<%= _displayContents(msg, colorScheme, pageContext) %>

			<%= _displayImages(msg, folderName, Integer.toString(msg.getMessageNumber()), themeDisplay.getPathMain()) %>
		</td>
	</tr>
	</table>

	<br>

	<c:if test="<%= (attachments != null) && (attachments.size() > 0) %>">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td>
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

							<a href="<%= themeDisplay.getPathMain() %>/mail/save_part?strip=0&folder_name=<%= msg.getFolder().getName() %>&msg_num=<%= msg.getMessageNumber() %>&msg_part=<%= attachment.getBodyPartId() %>" <%= (!attachment.hasDefaultFileName()) ? "target=\"_blank\"" : "" %>><%= attachment.getFileName() %></a>&nbsp;(<%= TextFormatter.formatKB(attachment.getSize(), locale) %>k)&nbsp;&nbsp;

						<%
						}
						%>

						</font>
					</td>
				</tr>
				</table>
			</td>
		</tr>
		</table>

		<br>
	</c:if>

	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "reply") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace />cmd.value = 'reply'; submitForm(document.<portlet:namespace />fm, '<portlet:renderURL><portlet:param name="struts_action" value="/mail/compose_message" /></portlet:renderURL>');">

			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "reply-all") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace />cmd.value = 'reply_all'; submitForm(document.<portlet:namespace />fm, '<portlet:renderURL><portlet:param name="struts_action" value="/mail/compose_message" /></portlet:renderURL>');">

			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "forward") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace />cmd.value = 'forward'; submitForm(document.<portlet:namespace />fm, '<portlet:renderURL><portlet:param name="struts_action" value="/mail/compose_message" /></portlet:renderURL>');">

			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "delete") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<%= redirectURLToString %>'; submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/mail/delete_message" /></portlet:actionURL>');">

			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "block") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<%= redirectURLToString %>'; submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/mail/block_sender" /></portlet:actionURL>');">

			<!--
			<script type="text/javascript">
				if (printCheck()) {
					document.write("<input type=\"button\" value=\"<%= LanguageUtil.get(pageContext, "print") %>\" onClick=\"printWindow()\">");
				}
			</script>
			-->

			<select name="<portlet:namespace />mail_f_l_2" onChange="if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-move-selected-messages-to") %> ' + document.<portlet:namespace />fm.<portlet:namespace />mail_f_l_2[document.<portlet:namespace />fm.<portlet:namespace />mail_f_l_2.selectedIndex].text + '?')) { document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<%= redirectURLToString %>'; document.<portlet:namespace />fm.<portlet:namespace />folder_name_2.value = document.<portlet:namespace />fm.<portlet:namespace />mail_f_l_2.options[document.<portlet:namespace />fm.<portlet:namespace />mail_f_l_2.options.selectedIndex].value; submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/mail/move_message" /></portlet:actionURL>'); } else { this.selectedIndex = 0; }">
				<option value=""><%= LanguageUtil.get(pageContext, "move-to") %>...</option>

				<%
				for (int i = 0; i < moveToFolders.length; i++) {
					Folder moveToFolder = moveToFolders[i];
				%>

					<c:if test="<%= !folderName.equals(moveToFolder.getName()) %>">
						<option value="<%= moveToFolder.getName() %>"><%= LanguageUtil.get(pageContext, moveToFolder.getName()) %></option>
					</c:if>

				<%
				}

				for (int i = 0; i < extraFolders.length; i++) {
					Folder extraFolder = MailUtil.getFolder(request, extraFolders[i]);
				%>

					<c:if test="<%= !folderName.equals(extraFolder.getName()) %>">
						<option value="<%= extraFolder.getName() %>"><%= extraFolder.getName() %></option>
					</c:if>

				<%
				}
				%>

			</select>
		</td>
		<td align="right">
			<font class="portlet-font" style="font-size: x-small;">

			<c:if test="<%= msgPrevious != null %>">
				<a href="<portlet:renderURL><portlet:param name="struts_action" value="/mail/view_message" /><portlet:param name="folder_name" value="<%= folderName %>" /><portlet:param name="msg_num" value="<%= msgPrevious %>" /></portlet:renderURL>"><%= LanguageUtil.get(pageContext, "previous") %></a>
			</c:if>

			<c:if test="<%= (msgPrevious != null) && (msgNext != null) %>">
				-
			</c:if>

			<c:if test="<%= msgNext != null %>">
				<a href="<portlet:renderURL><portlet:param name="struts_action" value="/mail/view_message" /><portlet:param name="folder_name" value="<%= folderName %>" /><portlet:param name="msg_num" value="<%= msgNext %>" /></portlet:renderURL>"><%= LanguageUtil.get(pageContext, "next") %></a>
			</c:if>

			</font>
		</td>
	</tr>
	</table>
</liferay-ui:box>

</form>

<%!
private String _displayContent(Part p, ColorScheme colorScheme, PageContext pageContext) {
	StringBuffer sb = new StringBuffer();

	try {
		Content c = MailUtil.getContent(p);

		if (c == null) {
			sb.append("<font class=\"portlet-msg-error\" style=\"font-size: x-small;\">");
			sb.append(LanguageUtil.get(pageContext, "the-format-of-this-email-is-incomplete-and-cannot-be-displayed-properly"));
			sb.append("</font>");
		}
		else if (c.getType().equals(Constants.TEXT_PLAIN)) {
			sb.append("<font color=\"").append(colorScheme.getPortletFont()).append("\">\n");
			sb.append("<pre>\n");
			sb.append(MailUtil.formatPlainText(c.getText()));
			sb.append("</pre>\n");
		}
		else {
			sb.append(c.getText());
		}
	}
	catch (Exception e) {
		e.printStackTrace();
	}

	return sb.toString();
}

private String _displayContents(Part p, ColorScheme colorScheme, PageContext pageContext) {
	StringBuffer sb = new StringBuffer();

	try {
		if (!p.isMimeType("multipart/*")) {
			sb.append(_displayContent(p, colorScheme, pageContext));
		}
		else if (p.isMimeType("multipart/alternative")) {
			Multipart mp = (Multipart)p.getContent();
			for (int i = 1; i < mp.getCount(); i++) {
				Part curPart = mp.getBodyPart(i);

				if (curPart.isMimeType("multipart/*")) {
					sb.append(_displayContents(curPart, colorScheme, pageContext));
					sb.append("<br>\n");
				}
				else if (curPart.getFileName() == null) {
					sb.append(_displayContent(curPart, colorScheme, pageContext));
					sb.append("<br>\n");
				}
			}

		}
		else {
			Multipart mp = (Multipart)p.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				Part curPart = mp.getBodyPart(i);

				if (curPart.isMimeType("multipart/*")) {
					sb.append(_displayContents(curPart, colorScheme, pageContext));
					sb.append("<br>\n");
				}
				else if (curPart.getFileName() == null) {
					sb.append(_displayContent(curPart, colorScheme, pageContext));
					sb.append("<br>\n");
				}
			}
		}
	}
	catch (Exception e) {
		_log.error(e);
	}

	return sb.toString();
}

private String _displayImages(Part p, String folderName, String msgNumber, String mainPath) {
	StringBuffer sb = new StringBuffer();

	try {
		if (p.isMimeType("multipart/*")) {
			Multipart mp = (Multipart)p.getContent();

			for (int i = 0; i < mp.getCount(); i++) {
				BodyPart bp = mp.getBodyPart(i);

				if ((bp.isMimeType("image/gif")) || (bp.isMimeType("image/jpeg")) || (bp.isMimeType("image/pjpeg"))) {
					sb.append("<img src=\"");
					sb.append(mainPath);
					sb.append("/mail/save_part?strip=0&folder_name=").append(folderName);
					sb.append("&msg_num=").append(msgNumber);
					sb.append("&msg_part=").append(i);
					sb.append("\">");
					sb.append("<br><br>\n");
				}
			}
		}
	}
	catch (Exception e) {
		_log.error(e);
	}

	return sb.toString();
}

private String _format(InternetAddress ia, PortletURL addressBookURL, String mailURLToString) {
	if (ia == null) {
		return "";
	}

	addressBookURL.setParameters(new HashMap());

	addressBookURL.setParameter("struts_action", "/address_book/edit_contact");
	addressBookURL.setParameter("mail_redirect", mailURLToString);
	addressBookURL.setParameter("contact_email_address", ia.getAddress());

	if (ia.getPersonal() != null) {
		addressBookURL.setParameter("contact_first_name", ia.getPersonal());
	}

	StringBuffer sb = new StringBuffer();

	sb.append("<a class=\"gamma\" href=\"");
	sb.append(addressBookURL.toString());
	sb.append("\">").append(ia.getAddress()).append("</a>");

	return sb.toString();
}

private static Log _log = LogFactory.getLog("portal-web.docroot.html.portlet.mail.view_message.jsp");
%>