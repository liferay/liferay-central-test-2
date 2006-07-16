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
%>

<script type="text/javascript">
	function initEditor() {
		return "<%= UnicodeFormatter.toString(content) %>";
	}
	
	function <portlet:namespace />sendMessage() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.SEND %>";
		document.<portlet:namespace />fm.<portlet:namespace />body.value = parent.<portlet:namespace />editor.getHTML();
		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mailbox/edit_message" /></portlet:actionURL>" enctype="multipart/form-data" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveArticle(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />body" type="hidden" value="" />

	<table cellpadding="0" cellspacing="2" border="0">
	<tr>
		<td><b><%= LanguageUtil.get(pageContext, "to") %>:</b></td>
		<td><input class="portlet-form-input-field" name="<portlet:namespace />tos" /></td>
	</tr>
	<tr>	
		<td><b><%= LanguageUtil.get(pageContext, "cc") %>:</b></td>
		<td><input class="portlet-form-input-field" name="<portlet:namespace />ccs" /></td>
	</tr>
	<tr>
		<td><b><%= LanguageUtil.get(pageContext, "bcc") %>:</b></td>
		<td><input class="portlet-form-input-field" name="<portlet:namespace />bccs" /></td>
	</tr>
	<tr>
		<td><b><%= LanguageUtil.get(pageContext, "subject") %>:</b></td>
		<td><input class="portlet-form-input-field" name="<portlet:namespace />subject" /></td>
	</tr>
	<tr>
		<td><b><%= LanguageUtil.get(pageContext, "attachments") %>:</b></td>
		<td><input class="portlet-form-input-field" type="file" name="<portlet:namespace />attachment_0" /></td>
	</tr>
	</table>

	<input type="button" class="portlet-form-button" value="<%= LanguageUtil.get(pageContext, "send") %>" onclick="<portlet:namespace />sendMessage()" /><br />

	<iframe frameborder="0" height="250" id="<portlet:namespace />editor" name="<portlet:namespace />editor" scrolling="no" src="<%= editorUrl %>" width="100%"></iframe>
</form>
	