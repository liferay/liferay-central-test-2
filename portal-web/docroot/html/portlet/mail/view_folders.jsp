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
Folder[] defaultFolders = MailUtil.getDefaultFolders(request);
%>

<form method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace />redirect" type="hidden" value="<portlet:renderURL><portlet:param name="struts_action" value="/mail/view_folders" /></portlet:renderURL>">
<input name="<portlet:namespace />folder_name" type="hidden" value="">

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "folders") %>' />

	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
			<input class="portlet-form-button" name="<portlet:namespace />add_folder_btn" type="button" value='<%= LanguageUtil.get(pageContext, "add") %>' onClick="self.location = '<portlet:actionURL><portlet:param name="struts_action" value="/mail/add_folder" /></portlet:actionURL>';">

			<input class="portlet-form-button" name="<portlet:namespace />edit_folder_btn" type="button" value='<%= LanguageUtil.get(pageContext, "edit") %>' onClick="folderSelectedValue = getSelectedRadioValue(document.<portlet:namespace />fm.<portlet:namespace />folder_name_col); if ((folderSelectedValue != '') && (folderSelectedValue != '<%= MailUtil.MAIL_INBOX_NAME %>') && (folderSelectedValue != '<%= MailUtil.MAIL_JUNK_NAME %>') && (folderSelectedValue != '<%= MailUtil.MAIL_SENT_NAME %>') && (folderSelectedValue != '<%= MailUtil.MAIL_DRAFTS_NAME %>') && (folderSelectedValue != '<%= MailUtil.MAIL_TRASH_NAME %>')) { document.<portlet:namespace />fm.<portlet:namespace />folder_name.value = folderSelectedValue; submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/mail/edit_folder" /></portlet:actionURL>'); }">

			<input class="portlet-form-button" name="<portlet:namespace />delete_folder_btn" type="button" value='<%= LanguageUtil.get(pageContext, "delete") %>' onClick="folderSelectedValue = getSelectedRadioValue(document.<portlet:namespace />fm.<portlet:namespace />folder_name_col); if ((folderSelectedValue != '') && (folderSelectedValue != '<%= MailUtil.MAIL_INBOX_NAME %>') && (folderSelectedValue != '<%= MailUtil.MAIL_JUNK_NAME %>') && (folderSelectedValue != '<%= MailUtil.MAIL_SENT_NAME %>') && (folderSelectedValue != '<%= MailUtil.MAIL_DRAFTS_NAME %>') && (folderSelectedValue != '<%= MailUtil.MAIL_TRASH_NAME %>')) { document.<portlet:namespace />fm.<portlet:namespace />folder_name.value = folderSelectedValue; if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-selected-folder") %>')) { submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/mail/delete_folder" /></portlet:actionURL>'); } }">

			<input class="portlet-form-button" name="<portlet:namespace />empty_folder_btn" type="button" value='<%= LanguageUtil.get(pageContext, "empty-folder") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace />folder_name.value = getSelectedRadioValue(document.<portlet:namespace />fm.<portlet:namespace />folder_name_col); if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-empty-the-contents-of-the-selected-folder") %>')) { submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/mail/empty_folder" /></portlet:actionURL>'); }">

			<input class="portlet-form-button" name="<portlet:namespace />empty_trash_btn" type="button" value='<%= LanguageUtil.get(pageContext, "empty-trash") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace />folder_name.value = '<%= MailUtil.MAIL_TRASH_NAME %>'; if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-empty-the-trash") %>')) { submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/mail/empty_folder" /></portlet:actionURL>'); }">
		</td>
	</tr>
	</table>

	<br>

	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
			&nbsp;
		</td>
		<td width="15%">
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "folder") %></b></font>
		</td>
		<td align="center" width="25%">
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "messages") %></b></font>
		</td>
		<td align="center" width="25%">
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "new") %></b></font>
		</td>
		<td align="center" width="25%">
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "size") %></b></font>
		</td>
	</tr>

	<%
	for (int i = 0; i < defaultFolders.length; i++) {
		Folder defaultFolder = defaultFolders[i];
	%>

		<tr>
			<td align="center">
				<input <%= (i == 0) ? "checked" : "" %> name="<portlet:namespace />folder_name_col" type="radio" value="<%= defaultFolder.getName() %>" onClick="disableFields(mailButtons);">
			</td>
			<td>
				<font class="portlet-font" style="font-size: x-small;">
				<a href="<portlet:renderURL><portlet:param name="struts_action" value="/mail/view_folder" /><portlet:param name="folder_name" value="<%= defaultFolder.getName() %>" /></portlet:renderURL>"><%= LanguageUtil.get(pageContext, defaultFolder.getName()) %></a>
				</font>
			</td>
			<td align="center">
				<font class="portlet-font" style="font-size: x-small;"><%= defaultFolder.getMessageCount() %></font>
			</td>
			<td align="center">
				<font class="portlet-font" style="font-size: x-small;"><%= defaultFolder.getUnreadMessageCount() %></font>
			</td>
			<td align="center">
				<font class="portlet-font" style="font-size: x-small;"><%= TextFormatter.formatKB(MailUtil.getSize(defaultFolder.getMessages()), locale) %>k</font>
			</td>
		</tr>

	<%
	}
	%>

	<c:if test="<%= extraFolders.length > 0 %>">
		<tr>
			<td colspan="5">
				<br>
			</td>
		</tr>
	</c:if>

	<%
	for (int i = 0; i < extraFolders.length; i++) {
		Folder extraFolder = MailUtil.getFolder(request, extraFolders[i]);
	%>

		<tr>
			<td align="center">
				<input name="<portlet:namespace />folder_name_col" type="radio" value="<%= extraFolder.getName() %>" onClick="enableFields(mailButtons);">
			</td>
			<td>
				<font class="portlet-font" style="font-size: x-small;">
				<a href="<portlet:renderURL><portlet:param name="struts_action" value="/mail/view_folder" /><portlet:param name="folder_name" value="<%= extraFolder.getName() %>" /></portlet:renderURL>"><%= extraFolder.getName() %></a>
				</font>
			</td>
			<td align="center">
				<font class="portlet-font" style="font-size: x-small;"><%= extraFolder.getMessageCount() %></font>
			</td>
			<td align="center">
				<font class="portlet-font" style="font-size: x-small;"><%= extraFolder.getUnreadMessageCount() %></font>
			</td>
			<td align="center">
				<font class="portlet-font" style="font-size: x-small;"><%= TextFormatter.formatKB(MailUtil.getSize(extraFolder.getMessages()), locale) %>k</font>
			</td>
		</tr>

	<%
	}
	%>

	</table>
</liferay-ui:box>

</form>

<script type="text/javascript">
	var mailButtons = new Array();
	mailButtons[0] = document.<portlet:namespace />fm.<portlet:namespace />edit_folder_btn;
	mailButtons[1] = document.<portlet:namespace />fm.<portlet:namespace />delete_folder_btn;

	folderSelectedValue = getSelectedRadioValue(document.<portlet:namespace />fm.<portlet:namespace />folder_name_col);

	if (folderSelectedValue == "") {
		for (var i = 0; i < document.<portlet:namespace />fm.<portlet:namespace />folder_name_col.length; i++) {
			document.<portlet:namespace />fm.<portlet:namespace />folder_name_col[i].checked = false;
		}

	}

	if ((folderSelectedValue == "") ||
		(folderSelectedValue == "<%= MailUtil.MAIL_INBOX_NAME %>") ||
		(folderSelectedValue == "<%= MailUtil.MAIL_JUNK_NAME %>") ||
		(folderSelectedValue == "<%= MailUtil.MAIL_SENT_NAME %>") ||
		(folderSelectedValue == "<%= MailUtil.MAIL_DRAFTS_NAME %>") ||
		(folderSelectedValue == "<%= MailUtil.MAIL_TRASH_NAME %>")) {

		disableFields(mailButtons);
	}
</script>