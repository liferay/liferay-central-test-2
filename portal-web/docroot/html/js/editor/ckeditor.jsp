<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HttpUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>

<%
long plid = ParamUtil.getLong(request, "p_l_id");
String mainPath = ParamUtil.getString(request, "p_main_path");
String doAsUserId = ParamUtil.getString(request, "doAsUserId");
String initMethod =	ParamUtil.getString(request, "initMethod", DEFAULT_INIT_METHOD);
String onChangeMethod = ParamUtil.getString(request, "onChangeMethod");
String toolbarSet = ParamUtil.getString(request, "toolbarSet", "liferay");
String cssPath = ParamUtil.getString(request, "cssPath");
String cssClasses = ParamUtil.getString(request, "cssClasses");
%>


<%@ page import="com.liferay.util.TextFormatter" %><html>
	<head>
		<script type="text/javascript" src="ckeditor/ckeditor.js"></script>

		<script type="text/javascript">
			window.onload = function() {
				initCkArea();
			}

			function initCkArea(){
				var textArea = document.getElementById("CKEditor1");
				var ckEditor = CKEDITOR.instances.CKEditor1;

				textArea.value = parent.<%= HtmlUtil.escape(initMethod) %>();

				<%
				String formatedToolbar = TextFormatter.format(HtmlUtil.escape(toolbarSet), TextFormatter.M);
				%>

				CKEDITOR.config.toolbar = '<%= formatedToolbar %>';
			}

			function getHTML() {
				return CKEDITOR.instances.CKEditor1.getData();
			}

			function getText() {
				return CKEDITOR.instances.CKEditor1.getData();
			}
		</script>
	</head>

	<body>
		<textarea id="CKEditor1" name="CKEditor1"></textarea>

		<script type="text/javascript">

			<%
			String connectorURL = HttpUtil.encodeURL(mainPath + "/portal/fckeditor?p_l_id=" + plid + "&doAsUserId=" + HttpUtil.encodeURL(doAsUserId));
			%>

			CKEDITOR.replace('CKEditor1',{
				filebrowserBrowseUrl : '/html/js/editor/ckeditor/editor/filemanager/browser/liferay/browser.html?Connector=<%= connectorURL %>',
				filebrowserUploadUrl : '/html/js/editor/ckeditor/editor/filemanager/browser/liferay/frmupload.html?Connector=<%= connectorURL %>'
				})
		</script>
	</body>
</html>

<%!
public static final String DEFAULT_INIT_METHOD = "initEditor";
%>
