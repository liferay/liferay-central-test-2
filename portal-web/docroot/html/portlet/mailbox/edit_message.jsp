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

<%@ include file="/html/portlet/init.jsp" %>
<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.journal.edit_article_content_xsd_el.jsp";
%>
<%
String editorUrl = themeDisplay.getPathJavaScript() + "/editor/editor.jsp?p_l_id=" + plid + "&editorImpl=" + PropsUtil.get(EDITOR_WYSIWYG_IMPL_KEY) + "&initMethod=initEditor";
String content = "test";
Long messageId = (Long)request.getAttribute("messageId");
%>

<script type="text/javascript">
	function initEditor() {
		return "<%= UnicodeFormatter.toString(content) %>";
	}

	function <portlet:namespace />saveDraft() {
		inputs = document.<portlet:namespace />fm.getElementsByTagName("input");
		var body = parent.<portlet:namespace />editor.getHTML();
		var queryString = "cmd=saveDraft&body=" + body;
		
		if (<portlet:namespace />messageId > 0) {
			queryString = "&messageId=" + <portlet:namespace />messageId;
		}
		
		for (var i = 0; i < inputs.length; i++) {
			var input = inputs[i];
			var inputName
	
			if (input.name && input.name.length > 0 &&
				input.value && input.value.length > 0) {
				inputName = input.name.replace(/^<portlet:namespace />/,"");
				
				queryString += "&" + inputName + "=" + input.value;
			}
		}
		alert(queryString);
		loadPage("<%= themeDisplay.getPathMain() %>/mailbox/action", queryString, <portlet:namespace />saveDraftReturn);
	}
	
	function <portlet:namespace />saveDraftReturn(xmlHttpReq) {
		var message = eval("(" + xmlHttpReq.responseText + ")");
		<portlet:namespace />messageId = message.id;
	}
	
	function <portlet:namespace />sendMessage() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.SEND %>";
		document.<portlet:namespace />fm.<portlet:namespace />body.value = parent.<portlet:namespace />editor.getHTML();
		submitForm(document.<portlet:namespace />fm);
	}

	var <portlet:namespace />file_index = 0;
	<c:if test="<%= messageId != null %>">
		var <portlet:namespace />messageId = <%= messageId.toString() %>;
	</c:if>
	<c:if test="<%= messageId == null %>">
		var <portlet:namespace />messageId = -1;
	</c:if>

	function <portlet:namespace />addAttachment() {
		var table = document.getElementById("<portlet:namespace />attachments");
		var newrow = table.insertRow(table.rows.length - 1);
		newrow.id = "<portlet:namespace />file" + <portlet:namespace />file_index;

		var browser = createElement("input", "<portlet:namespace />attachment" + <portlet:namespace />file_index);
		browser.type = "file";
		browser.size = "30";

		var spacer = document.createElement("span");
		spacer.innerHTML = "&nbsp; &nbsp;";

		var del = document.createElement("a");
		del.href = "javascript:<portlet:namespace />removeAttachment('" + newrow.id + "')";
		del.innerHTML = "[<%= LanguageUtil.get(pageContext, "remove") %>]";

		newrow.insertCell(0).appendChild(browser);
		newrow.insertCell(1).appendChild(spacer);
		newrow.insertCell(2).appendChild(del);

		<portlet:namespace />file_index++;
	}

	function <portlet:namespace />removeAttachment(id) {
		var delrow = document.getElementById(id);
		document.getElementById("<portlet:namespace />attachments").deleteRow(delrow.rowIndex);
	}
</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mailbox/send_message" /></portlet:actionURL>" enctype="multipart/form-data" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveArticle(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />body" type="hidden" value="" />
<input name="<portlet:namespace />messageId" type="hidden" value="<%= messageId %>" />

	<table cellpadding="0" cellspacing="2" border="0">
	<tr>
		<td><b><%= LanguageUtil.get(pageContext, "to") %>:</b></td>
		<td><input class="portlet-form-input-field" name="<portlet:namespace />tos" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" /></td>
	</tr>
	<tr>
		<td><b><%= LanguageUtil.get(pageContext, "cc") %>:</b></td>
		<td><input class="portlet-form-input-field" name="<portlet:namespace />ccs" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" /></td>
	</tr>
	<tr>
		<td><b><%= LanguageUtil.get(pageContext, "bcc") %>:</b></td>
		<td><input class="portlet-form-input-field" name="<portlet:namespace />bccs" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" /></td>
	</tr>
	<tr>
		<td><b><%= LanguageUtil.get(pageContext, "subject") %>:</b></td>
		<td><input class="portlet-form-input-field" name="<portlet:namespace />subject" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" /></td>
	</tr>
	<tr>
		<td valign="top"><b><%= LanguageUtil.get(pageContext, "attachments") %>:</b></td>
		<td>
			<table cellpadding="0" cellspacing="2" border="0" id="<portlet:namespace />attachments" style="margin-left: -2px; margin-top: -2px;">
				<tr>
					<td colspan="3">
						<input type="button" class="portlet-form-button" value='<%= LanguageUtil.get(pageContext, "add-attachment") %>' onclick="<portlet:namespace />addAttachment()" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
	</table>

	<br />

	<div>
		<input type="button" class="portlet-form-button" value='<%= LanguageUtil.get(pageContext, "send") %>' onclick="<portlet:namespace />sendMessage()" />
		<input type="button" class="portlet-form-button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onclick='self.location="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mailbox/view" /></portlet:renderURL>"' />
		<input type="button" class="portlet-form-button" value='<%= LanguageUtil.get(pageContext, "save") %>' onclick="<portlet:namespace />saveDraft()" />
	</div>

	<br />

	<iframe frameborder="0" height="250" id="<portlet:namespace />editor" name="<portlet:namespace />editor" scrolling="no" src="<%= editorUrl %>" width="100%"></iframe>

</form>