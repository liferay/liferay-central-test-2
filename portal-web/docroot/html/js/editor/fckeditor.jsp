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

<%@ page import="com.liferay.util.ParamUtil" %>

<%
String plid = ParamUtil.getString(request, "p_l_id");
String initMethod = ParamUtil.getString(request, "initMethod", DEFAULT_INIT_METHOD);

// To upgrade FCKEditor, download the latest version and unzip it to fckeditor.
// Add custom configuration to fckeditor/fckconfig.jsp. Copy
// fckeditor/editor/filemanager/browser/default to
// fckeditor/editor/filemanager/browser/liferay. Modify browser.html,
// frmresourceslist.html, frmresourcetype.html, and frmupload.html.

%>

<html>

<head>
	<title>Editor</title>
	<script type="text/javascript" src="fckeditor/fckeditor.js"></script>
	<script type="text/javascript">
		function getHTML() {
			return (document.getElementById("FCKeditor1___Frame").contentWindow.document.getElementById("eEditorArea")).contentWindow.document.body.innerHTML;
		}

		function getText() {
			return (document.getElementById("FCKeditor1___Frame").contentWindow.document.getElementById("eEditorArea")).contentWindow.document.body.innerText;
		}
	</script>
</head>

<body leftmargin="0" marginheight="0" marginwidth="0" rightmargin="0" topmargin="0">

<script type="text/javascript">
	var fckEditor = new FCKeditor("FCKeditor1");

	fckEditor.Config["CustomConfigurationsPath"] = "<%= request.getContextPath() %>/html/js/editor/fckeditor/fckconfig.jsp?p_l_id=<%= plid %>";

	fckEditor.BasePath = "fckeditor/";
	fckEditor.Value = parent.<%= initMethod %>();
	fckEditor.Width = "100%";
	fckEditor.Height = "100%";
	fckEditor.ToolbarSet = "Liferay";

	fckEditor.Create();
</script>

</body>

</html>

<%!
public static final String DEFAULT_INIT_METHOD = "initEditor";
%>