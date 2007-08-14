<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String langType = ParamUtil.getString(request, "langType");

String defaultContent = null;

if (langType.equals(JournalTemplateImpl.LANG_TYPE_XSL)) {
	defaultContent = ContentUtil.get("com/liferay/portlet/journal/dependencies/template.xsl");
}
else if (langType.equals(JournalTemplateImpl.LANG_TYPE_CSS)) {
	defaultContent = ContentUtil.get("com/liferay/portlet/journal/dependencies/template.css");
}
else {
	defaultContent = ContentUtil.get("com/liferay/portlet/journal/dependencies/template.vm");
}
%>

<script src="<%= themeDisplay.getPathContext() %>/html/js/editor/codepress/codepress.js" type="text/javascript"></script>

<script type="text/javascript">

	function getEditorContent() {
		var content = decodeURIComponent(opener.document.<portlet:namespace />fm.<portlet:namespace />xslContent.value);

		if (content == "") {
			content = "<%= UnicodeFormatter.toString(defaultContent) %>";
		}

		return content;
	}

	function <portlet:namespace />updateTemplateXsl() {
		opener.document.<portlet:namespace />fm.<portlet:namespace />xslContent.value = encodeURIComponent(<portlet:namespace />xslContent.getCode());

		self.close();
	}
</script>

<form method="post" name="<portlet:namespace />fm">

<textarea class="codepress html" id="<portlet:namespace />xslContent" name="<portlet:namespace />xslContent" wrap="off"></textarea>

<br /><br />

<input type="button" value="<liferay-ui:message key="update" />" onClick="<portlet:namespace />updateTemplateXsl();" />

<input type="button" value="<liferay-ui:message key="select-and-copy" />" onClick="Liferay.Util.selectAndCopy(document.<portlet:namespace />fm.<portlet:namespace />xslContent);" />

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="self.close();" />

</form>
<script type="text/javascript">
	jQuery(
		function() {
			document.<portlet:namespace />fm.<portlet:namespace />xslContent.value = getEditorContent();
			Liferay.Util.resizeTextarea('<portlet:namespace />xslContent');
		}
	);
</script>