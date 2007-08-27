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

<script type="text/javascript">
	function getEditorContent() {
		return opener.<portlet:namespace />getXsd();
	}

	function <portlet:namespace />updateStructureXsd() {
		opener.document.<portlet:namespace />fm.scroll.value = "<portlet:namespace />xsd";
		opener.document.<portlet:namespace />fm.<portlet:namespace />xsd.value = <portlet:namespace />xsdContent.getCode();

		submitForm(opener.document.<portlet:namespace />fm);

		self.close();
	}
</script>

<form method="post" name="<portlet:namespace />fm">

<textarea class="codepress html" id="<portlet:namespace />xsdContent" name="<portlet:namespace />xsdContent" wrap="off"></textarea>

<br /><br />

<input type="button" value="<liferay-ui:message key="update" />" onClick="<portlet:namespace />updateStructureXsd();" />

<input type="button" value="<liferay-ui:message key="select-and-copy" />" onClick="Liferay.Util.selectAndCopy(document.<portlet:namespace />fm.<portlet:namespace />xsdContent);" />

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="self.close();" />

</form>

<script type="text/javascript">
	jQuery(
		function() {
			document.<portlet:namespace />fm.<portlet:namespace />xsdContent.value = getEditorContent();

			Liferay.Util.resizeTextarea('<portlet:namespace />xsdContent');
		}
	);
</script>

<script src="<%= themeDisplay.getPathContext() %>/html/js/editor/codepress/codepress.js" type="text/javascript"></script>