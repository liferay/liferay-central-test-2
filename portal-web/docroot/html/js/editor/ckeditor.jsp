<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HttpUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.PropertiesParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.StringBundler" %>
<%@ page import="com.liferay.portal.kernel.util.StringPool" %>
<%@ page import="com.liferay.portal.kernel.util.TextFormatter" %>
<%@ page import="com.liferay.portal.kernel.util.UnicodeProperties" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>

<%@ page import="java.util.Map" %>

<%
long plid = ParamUtil.getLong(request, "p_l_id");
String portletId = ParamUtil.getString(request, "p_p_id");
String mainPath = ParamUtil.getString(request, "p_main_path");
String doAsUserId = ParamUtil.getString(request, "doAsUserId");
String doAsGroupId = ParamUtil.getString(request, "doAsGroupId");
String toolbarSet = ParamUtil.getString(request, "toolbarSet", "liferay");
String initMethod =	ParamUtil.getString(request, "initMethod", DEFAULT_INIT_METHOD);
String onChangeMethod = ParamUtil.getString(request, "onChangeMethod");
String cssPath = ParamUtil.getString(request, "cssPath");
String cssClasses = ParamUtil.getString(request, "cssClasses");
String languageId = ParamUtil.getString(request, "languageId");

UnicodeProperties properties = PropertiesParamUtil.getProperties(request, "config--");

StringBundler configParamsSB = new StringBundler();

for (Map.Entry<String, String> property : properties.entrySet()) {
	configParamsSB.append(StringPool.AMPERSAND);
	configParamsSB.append(property.getKey());
	configParamsSB.append(StringPool.EQUAL);
	configParamsSB.append(HttpUtil.encodeURL(property.getValue()));
}

String ckEditorConfigFileName = ParamUtil.getString(request, "ckEditorConfigFileName", "ckconfig.jsp");
%>

<html>

<head>
	<style type="text/css">
		table.cke_dialog {
			position: absolute !important;
		}
	</style>

	<script type="text/javascript" src="ckeditor/ckeditor.js"></script>

	<script type="text/javascript">
		function getCkData() {
			var data = CKEDITOR.instances.CKEditor1.getData();

			if (CKEDITOR.env.gecko && (CKEDITOR.tools.trim(data) == '<br />')) {
				data = '';
			}

			return data;
		}

		function getHTML() {
			return getCkData();
		}

		function setHTML(value) {
			CKEDITOR.instances.CKEditor1.setData(value);
		}

		function getText() {
			return getCkData();
		}

		<%
		if (Validator.isNotNull(onChangeMethod)) {
		%>

			function onChangeCallback() {
				var ckEditor = CKEDITOR.instances.CKEditor1;
				var dirty = ckEditor.checkDirty();

				if (dirty) {
					parent.<%= HtmlUtil.escape(onChangeMethod) %>(getText());

					ckEditor.resetDirty();
				}
			}

		<%
		}
		%>
	</script>
</head>

<body>

<textarea id="CKEditor1" name="CKEditor1"></textarea>

<script type="text/javascript">
	(function() {

		<%
		String connectorURL = HttpUtil.encodeURL(mainPath + "/portal/fckeditor?p_l_id=" + plid + "&p_p_id=" + HttpUtil.encodeURL(portletId) + "&doAsUserId=" + HttpUtil.encodeURL(doAsUserId) + "&doAsGroupId=" + HttpUtil.encodeURL(doAsGroupId));
		%>

		CKEDITOR.replace(
			'CKEditor1',
			{
				customConfig: '<%= PortalUtil.getPathContext() %>/html/js/editor/ckeditor/<%= ckEditorConfigFileName %>?p_l_id=<%= plid %>&p_p_id=<%= HttpUtil.encodeURL(portletId) %>&p_main_path=<%= HttpUtil.encodeURL(mainPath) %>&doAsUserId=<%= HttpUtil.encodeURL(doAsUserId) %>&doAsGroupId=<%= HttpUtil.encodeURL(doAsGroupId) %>&cssPath=<%= HttpUtil.encodeURL(cssPath) %>&cssClasses=<%= HttpUtil.encodeURL(cssClasses) %>&languageId=<%= HttpUtil.encodeURL(languageId) %><%= configParamsSB.toString() %>',
				filebrowserBrowseUrl: '<%= PortalUtil.getPathContext() %>/html/js/editor/ckeditor/editor/filemanager/browser/liferay/browser.html?Connector=<%= connectorURL %>',
				filebrowserUploadUrl: null,
				toolbar: '<%= TextFormatter.format(HtmlUtil.escape(toolbarSet), TextFormatter.M) %>'
			}
		);

		var ckEditor = CKEDITOR.instances.CKEditor1;

		ckEditor.on(
			'instanceReady',
			function() {
				ckEditor.setData(parent.<%= HtmlUtil.escape(initMethod) %>());

				<%
				if (Validator.isNotNull(onChangeMethod)) {
				%>

					setInterval(
						function() {
							try {
								onChangeCallback();
							}
							catch(e) {
							}
						},
						300
					);

				<%
				}
				%>
			}
		);
	})();
</script>

</body>

</html>

<%!
public static final String DEFAULT_INIT_METHOD = "initEditor";
%>