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

<%@ include file="/html/taglib/init.jsp" %>

<%
String portletId = portletDisplay.getRootPortletId();

String mainPath = themeDisplay.getPathMain();
String doAsUserId = themeDisplay.getDoAsUserId();

if (Validator.isNull(doAsUserId)) {
	doAsUserId = Encryptor.encrypt(company.getKeyObj(), String.valueOf(themeDisplay.getUserId()));
}

long doAsGroupId = themeDisplay.getDoAsGroupId();
String cssPath = themeDisplay.getPathThemeCss();
String languageId = LocaleUtil.toLanguageId(locale);

String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:cssClass"));
String cssClasses = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:cssClasses"));
String name = namespace + GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:name"));
String toolbarSet = (String)request.getAttribute("liferay-ui:input-editor:toolbarSet");
String initMethod = (String)request.getAttribute("liferay-ui:input-editor:initMethod");
String onChangeMethod = (String)request.getAttribute("liferay-ui:input-editor:onChangeMethod");

if (Validator.isNotNull(initMethod)) {
	initMethod = namespace + initMethod;
}

if (Validator.isNotNull(onChangeMethod)) {
	onChangeMethod = namespace + onChangeMethod;
}

UnicodeProperties properties = PropertiesParamUtil.getProperties(request, "config--");

StringBundler configParamsSB = new StringBundler();

for (Map.Entry<String, String> property : properties.entrySet()) {
	configParamsSB.append(StringPool.AMPERSAND);
	configParamsSB.append(property.getKey());
	configParamsSB.append(StringPool.EQUAL);
	configParamsSB.append(property.getValue());
}

// To upgrade FCKEditor, download the latest version and unzip it to fckeditor.
// Add custom configuration to fckeditor/fckconfig.jsp. Copy
// fckeditor/editor/filemanager/browser/default to
// fckeditor/editor/filemanager/browser/liferay. Modify browser.html,
// frmresourceslist.html, frmresourcetype.html, and frmupload.html.

%>

<liferay-util:html-top outputKey="fckeditor">
	<script src='<%= PortalUtil.getPathContext() + "/html/js/editor/fckeditor/fckeditor.js" %>' type="text/javascript"></script>
</liferay-util:html-top>

<script type="text/javascript">
	function getHTML() {
		return FCKeditorAPI.GetInstance("FCKeditor1").GetXHTML();
	}

	function getText() {
		return FCKeditorAPI.GetInstance("FCKeditor1").GetXHTML();
	}

	function initFckArea() {

		// LEP-3563

		if (!window.frameElement || (!document.all && window.frameElement.clientWidth == 0)) {

			// This is hack since FCKEditor doesn't initialize properly in
			// Gecko if the editor is hidden.

			setTimeout('initFckArea();',250);
		}
		else {
			var textArea = document.getElementById("FCKeditor1");

			textArea.value = parent.<%= HtmlUtil.escape(initMethod) %>();

			var fckEditor = new FCKeditor("FCKeditor1");

			fckEditor.Config["CustomConfigurationsPath"] = "<%= PortalUtil.getPathContext() %>/html/js/editor/fckeditor/fckconfig.jsp?p_l_id=<%= plid %>&p_p_id=<%= HttpUtil.encodeURL(portletId) %>&p_main_path=<%= HttpUtil.encodeURL(mainPath) %>&doAsUserId=<%= HttpUtil.encodeURL(doAsUserId) %>&doAsGroupId=<%= HttpUtil.encodeURL(doAsGroupId) %>&cssPath=<%= HttpUtil.encodeURL(cssPath) %>&cssClasses=<%= HttpUtil.encodeURL(cssClasses) %>&languageId=<%= HttpUtil.encodeURL(languageId) %><%= configParamsSB.toString() %>";

			fckEditor.BasePath = "fckeditor/";
			fckEditor.Width = "100%";
			fckEditor.Height = "100%";
			fckEditor.ToolbarSet = '<%= HtmlUtil.escape(toolbarSet) %>';

			fckEditor.ReplaceTextarea();

			// LEP-5707

			var ua = navigator.userAgent, isFirefox2andBelow = false;
			var agent = /(Firefox)\/(.+)/.exec(ua);

			if (agent && agent.length && (agent.length == 3)) {
				if (parseInt(agent[2]) && parseInt(agent[2]) < 3) {
					isFirefox2andBelow = true;
				}
			}

			if (isFirefox2andBelow) {
				var fckInstanceName = fckEditor.InstanceName;
				var fckIframe = document.getElementById(fckInstanceName + '___Frame');

				var interval = setInterval(
					function() {
						var iframe = fckIframe.contentDocument.getElementsByTagName('iframe');

						if (iframe.length) {
							iframe = iframe[0];

							iframe.onload = function(event) {
								clearInterval(interval);
								parent.stop();
							};
						}
					},
					500);
			}
		}

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
	}

	function onChangeCallback() {

		<%
		if (Validator.isNotNull(onChangeMethod)) {
		%>

			var dirty = FCKeditorAPI.GetInstance("FCKeditor1").IsDirty();

			if (dirty) {
				parent.<%= HtmlUtil.escape(onChangeMethod) %>(getText());

				FCKeditorAPI.GetInstance("FCKeditor1").ResetIsDirty();
			}

		<%
		}
		%>

	}

	function setHTML(value) {
		FCKeditorAPI.GetInstance("FCKeditor1").SetHTML(value);
	}

	window.onload = function() {
		initFckArea();
	}
</script>

<div class="<%= cssClass %>">
	<textarea id="<%= name %>" name="<%= name %>" style="display: none"></textarea>
</div>