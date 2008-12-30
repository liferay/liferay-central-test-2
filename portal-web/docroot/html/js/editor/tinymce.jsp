<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

<%@ page import="com.liferay.portal.kernel.servlet.BrowserSnifferUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ContentTypes" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HttpUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="com.liferay.portal.util.PropsKeys" %>
<%@ page import="com.liferay.portal.util.PropsUtil" %>

<%
long plid = ParamUtil.getLong(request, "p_l_id");
String mainPath = ParamUtil.getString(request, "p_main_path");
String doAsUserId = ParamUtil.getString(request, "doAsUserId");
String connectorURL = HttpUtil.encodeURL(mainPath + "/portal/fckeditor?p_l_id=" + plid + "&doAsUserId=" + HttpUtil.encodeURL(doAsUserId));

String initMethod = ParamUtil.get(request, "initMethod", DEFAULT_INIT_METHOD);
String onChangeMethod = ParamUtil.getString(request, "onChangeMethod");
%>

<html>
<head>
	<title>Editor</title>
	<script src="tiny_mce/tiny_mce.js" type="text/javascript"></script>
	<script type="text/javascript">
		var onChangeCallbackCounter = 0;

		tinyMCE.init({
			mode : "textareas",
			theme : "advanced",
			extended_valid_elements : "a[name|href|target|title|onclick],img[class|src|border=0|alt|title|hspace|vspace|width|height|align|onmouseover|onmouseout|name],hr[class|width|size|noshade],font[face|size|color|style],span[class|align|style]",
			file_browser_callback : "fileBrowserCallback",
			init_instance_callback : "initInstanceCallback",
			onchange_callback : "onChangeCallback",
			plugins : "table,advhr,advimage,advlink,iespell,preview,media,searchreplace,print,contextmenu",
			relative_urls : false,
			theme_advanced_buttons1_add_before : "fontselect,fontsizeselect,forecolor,backcolor,separator",
			theme_advanced_buttons2_add : "separator,media,advhr,separator,preview,print",
			theme_advanced_buttons2_add_before: "cut,copy,paste,search,replace",
			theme_advanced_buttons3_add_before : "tablecontrols,separator",
			theme_advanced_disable : "formatselect,styleselect,help",
			theme_advanced_toolbar_align : "left",
			theme_advanced_toolbar_location : "top"
		});

		function fileBrowserCallback(field_name, url, type, win) {
			var type = type.toLowerCase();

			if (type != "image") {
				return false;
			}

			var basepath = "";

			if (document.location.protocol == "file:") {
				basepath = decodeURIComponent(document.location.pathname.substr(1));
				basepath = basepath.replace(/\\/gi, "/");

				var sFullProtocol = document.location.href.match(/^(file\:\/{2,3})/)[1];

				if (<%= BrowserSnifferUtil.isOpera(request) %>) {
					sFullProtocol += "localhost/";
				}

				basepath = sFullProtocol + basepath.substring(0, basepath.lastIndexOf("/") + 1);
			}
			else {
				basepath = document.location.protocol + "//" + document.location.host + document.location.pathname.substring(0, document.location.pathname.lastIndexOf("/") + 1);
			}

			var connector = basepath + "fckeditor/editor/filemanager/browser/liferay/browser.html?Connector=<%= connectorURL %>&Type=Image";

			var height = 0;
			var width = 0;

			try {
				height = screen.height * 0.7;
				width = screen.width * 0.7;
			}
			catch (e) {
				height = 600;
				width = 800;
			}

			window.SetUrl = function(fileUrl) {
				win.document.forms[0].elements[field_name].value = fileUrl;
				win.focus();
			}

			tinyMCE.activeEditor.windowManager.open({
				file : connector,
				width : width,
				height : height,
				resizable : "yes",
				close_previous : "no"
			});

			return false;
		}

		function getHTML() {
			return tinyMCE.activeEditor.getContent();
		}

		function init(value) {
			setHTML(decodeURIComponent(value));
		}

		function initInstanceCallback() {
			init(parent.<%= initMethod %>());
		}

		function onChangeCallback(tinyMCE) {

			// This purposely ignores the first callback event because each call
			// to setContent triggers an undo level which fires the callback
			// when no changes have yet been made.

			// setContent is not really the correct way of initializing this
			// editor with content. The content should be placed statically
			// (from the editor's perspective) within the textarea. This is a
			// problem from the portal's perspective because it's passing the
			// content via a javascript method (initMethod).

			if (onChangeCallbackCounter > 0) {

				<%
				if (Validator.isNotNull(onChangeMethod)) {
				%>

					parent.<%= HtmlUtil.escape(onChangeMethod) %>(getHTML());

				<%
				}
				%>

			}

			onChangeCallbackCounter++;
		}

		function setHTML(value) {
			tinyMCE.activeEditor.setContent(value);
		}
	</script>
</head>

<body leftmargin="0" marginheight="0" marginwidth="0" rightmargin="0" topmargin="0">

<textarea id="textArea" name="textArea" style="height: 100%; width: 100%;"></textarea>

</body>

</html>

<%!
public static final String DEFAULT_INIT_METHOD = "initEditor";
%>