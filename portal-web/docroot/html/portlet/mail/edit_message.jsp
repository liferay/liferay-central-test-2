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
String to = "";
String cc = "";
String bcc = "";
String subject = "";
String body = "";
%>

<script type="text/javascript">
	var <portlet:namespace />fileIndex = 0;

	function <portlet:namespace />addAttachment(remoteFile, contentPath) {
		var table = document.getElementById("<portlet:namespace />files");

		var newRow = table.insertRow(table.rows.length - 1);

		newRow.id = "<portlet:namespace />file" + <portlet:namespace />fileIndex;

		if (remoteFile == null) {
			var browser = createElement("input", "<portlet:namespace />attachment" + <portlet:namespace />fileIndex);

			browser.type = "file";
			browser.size = "30";

			var spacer = document.createElement("span");

			spacer.innerHTML = "&nbsp;&nbsp;&nbsp;";

			var del = document.createElement("a");

			del.href = "javascript: <portlet:namespace />removeAttachment('" + newRow.id + "');";
			del.innerHTML = "[<%= LanguageUtil.get(pageContext, "remove") %>]";

			newRow.insertCell(0).appendChild(browser);
			newRow.insertCell(1).appendChild(spacer);
			newRow.insertCell(2).appendChild(del);
		}
		else {
			var checkbox = createElement("input", "<portlet:namespace />remoteAttachment" + remoteFile);

			checkbox.type = "checkbox";
			checkbox.checked = true;
			checkbox.value = contentPath;

			var filename = document.createElement("span");

			var href = "<%= themeDisplay.getPathMain() %>/mail/get_attachment?fileName=" + remoteFile + "&contentPath=" + contentPath;
			
			filename.innerHTML = '<span class="font-small"><a href="' + href + '">' + remoteFile + '</a></span>';

			newRow.insertCell(0);

			newRow.cells[0].appendChild(checkbox);
			newRow.cells[0].appendChild(filename);
			newRow.cells[0].colSpan = 3;
		}

		<portlet:namespace />fileIndex++;
	}

	function <portlet:namespace />completeMessage(send) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = send ? "<%= Constants.SEND %>" : "<%= Constants.SAVE %>";
		document.<portlet:namespace />fm.<portlet:namespace />body.value = parent.<portlet:namespace />editor.getHTML();
		submitForm(document.<portlet:namespace />fm);
	}

	function initEditor() {
		return "<%= UnicodeFormatter.toString(body) %>";
	}

	function <portlet:namespace />removeAttachment(id) {
		var delRow = document.getElementById(id);

		document.getElementById("<portlet:namespace />files").deleteRow(delRow.rowIndex);
	}
</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mail/complete_message" /></portlet:actionURL>" enctype="multipart/form-data" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />body" type="hidden" value="" />

<liferay-ui:error exception="<%= ContentException.class %>" message="please-enter-valid-content" />
<liferay-ui:error exception="<%= RecipientException.class %>" message="please-enter-a-valid-email-address" />

<table border="0" cellpadding="0" cellspacing="0">
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "to") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<input class="form-text" name="<portlet:namespace />to" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="text" value="<%= to %>">
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "cc") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<input class="form-text" name="<portlet:namespace />cc" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="text" value="<%= cc %>">
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "bcc") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<input class="form-text" name="<portlet:namespace />bcc" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="text" value="<%= bcc %>">
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "subject") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<input class="form-text" name="<portlet:namespace />subject" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="text" value="<%= subject %>">
	</td>
</tr>
</table>

<br>

<iframe frameborder="0" height="400" id="<portlet:namespace />editor" name="<portlet:namespace />editor" scrolling="no" src="<%= themeDisplay.getPathJavaScript() %>/editor/editor.jsp?p_l_id=<%= plid %>&editorImpl=<%= PropsUtil.get(EDITOR_WYSIWYG_IMPL_KEY) %>" width="640"></iframe>

<br><br>

<table border="0" cellpadding="0" cellspacing="0">
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "attachments") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<table cellpadding="0" cellspacing="0" border="0" id="<portlet:namespace />files">
		<tr>
			<td>
				<input type="button" class="portlet-form-button" value='<%= LanguageUtil.get(pageContext, "add-attachment") %>' onclick="<portlet:namespace />addAttachment()" />
			</td>
		</tr>
		</table>
	</td>
</tr>
</table>

<br>

<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "send") %>' onClick="<portlet:namespace />completeMessage(true);">

<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "save") %>' onclick="<portlet:namespace />completeMessage(false);">

<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="self.location = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mail/view" /></portlet:renderURL>';">

</form>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.mail.edit_message.jsp";
%>

<%--
<%
String editorUrl = themeDisplay.getPathJavaScript() + "/editor/editor.jsp?p_l_id=" + plid + "&editorImpl=" + PropsUtil.get(EDITOR_WYSIWYG_IMPL_KEY) + "&initMethod=initEditor";

String [] recipients = (String [])request.getAttribute(WebKeys.MAIL_RECIPIENTS);
String tos = "";
String ccs = "";
String bccs = "";
if (recipients != null && recipients.length == 3) {
	tos = recipients[0];
	ccs = recipients[1];
	bccs = recipients[2];
}
String subject = GetterUtil.getString((String)request.getAttribute(WebKeys.MAIL_SUBJECT));
String content = GetterUtil.getString((String)request.getAttribute(WebKeys.MAIL_MESSAGE));
List remoteAttachments = (List)request.getAttribute(WebKeys.MAIL_ATTACHMENTS);
Long draftId = (Long)request.getAttribute(WebKeys.MAIL_DRAFT_ID);

<input name="<portlet:namespace />draftId" type="hidden" value="<%= draftId != null ? draftId : new Long(-1L) %>" />
<c:if test="<%= remoteAttachments != null %>">
	<script type="text/javascript">
	<%
	for (Iterator itr = remoteAttachments.iterator(); itr.hasNext(); ) {
		RemoteMailAttachment rma = (RemoteMailAttachment)itr.next();
	%>
		<portlet:namespace />addAttachment("<%= rma.getFilename() %>", "<%= rma.getContentPath() %>");
	<%
	}
	%>
	</script>
</c:if>--%>