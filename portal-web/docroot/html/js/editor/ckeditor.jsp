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

StringBundler configParamsSB = new StringBundler();

Map<String, String> configParams = (Map<String, String>)request.getAttribute("liferay-ui:input-editor:configParams");

if (configParams != null) {
	for (Map.Entry<String, String> configParam : configParams.entrySet()) {
		configParamsSB.append(StringPool.AMPERSAND);
		configParamsSB.append(configParam.getKey());
		configParamsSB.append(StringPool.EQUAL);
		configParamsSB.append(HttpUtil.encodeURL(configParam.getValue()));
	}
}

String ckEditorConfigFileName = ParamUtil.getString(request, "ckEditorConfigFileName", "ckconfig.jsp");

boolean useCustomDataProcessor = false;

if (!ckEditorConfigFileName.equals("ckconfig.jsp")) {
	useCustomDataProcessor = true;
}
%>

<liferay-util:html-top outputKey="ckeditor">
	<style type="text/css">
		table.cke_dialog {
			position: absolute !important;
		}
	</style>

	<script src='<%= PortalUtil.getPathContext() + "/html/js/editor/ckeditor/ckeditor.js" %>' type="text/javascript"></script>
</liferay-util:html-top>

<script type="text/javascript">
	window['<%= name %>'] = {
		getCkData: function() {
			var data = CKEDITOR.instances['<%= name %>'].getData();

			if (CKEDITOR.env.gecko && (CKEDITOR.tools.trim(data) == '<br />')) {
				data = '';
			}

			return data;
		},

	getHTML: function() {
		return window['<%= name %>'].getCkData();
	},

	getText: function() {
		return window['<%= name %>'].getCkData();
	},

	setHtml: function(value) {
		CKEDITOR.instances['<%= name %>'].setData(value);
	}

	<%
	if (Validator.isNotNull(onChangeMethod)) {
	%>

		,onChangeCallback: function () {
			var ckEditor = CKEDITOR.instances['<%= name %>'];
			var dirty = ckEditor.checkDirty();

			if (dirty) {
				<%= HtmlUtil.escape(onChangeMethod) %>(window['<%= name %>'].getText());

				ckEditor.resetDirty();
			}
		}

	<%
	}
	%>

	};
</script>

<div class="<%= cssClass %>">
	<textarea id='<%= name %>' name='<%= name %>'></textarea>
</div>

<script type="text/javascript">
	(function() {
		function setData() {
			ckEditor.setData(<%= HtmlUtil.escape(initMethod) %>);
		}

		<%
		String connectorURL = HttpUtil.encodeURL(mainPath + "/portal/fckeditor?p_l_id=" + plid + "&p_p_id=" + HttpUtil.encodeURL(portletId) + "&doAsUserId=" + HttpUtil.encodeURL(doAsUserId) + "&doAsGroupId=" + HttpUtil.encodeURL(String.valueOf(doAsGroupId)));
		%>

		CKEDITOR.replace(
			'<%= name %>',
			{
				customConfig: '<%= PortalUtil.getPathContext() %>/html/js/editor/ckeditor/<%= ckEditorConfigFileName %>?p_l_id=<%= plid %>&p_p_id=<%= HttpUtil.encodeURL(portletId) %>&p_main_path=<%= HttpUtil.encodeURL(mainPath) %>&doAsUserId=<%= HttpUtil.encodeURL(doAsUserId) %>&doAsGroupId=<%= HttpUtil.encodeURL(String.valueOf(doAsGroupId)) %>&cssPath=<%= HttpUtil.encodeURL(cssPath) %>&cssClasses=<%= HttpUtil.encodeURL(cssClasses) %>&languageId=<%= HttpUtil.encodeURL(languageId) %><%= configParamsSB.toString() %>',
				filebrowserBrowseUrl: '<%= PortalUtil.getPathContext() %>/html/js/editor/ckeditor/editor/filemanager/browser/liferay/browser.html?Connector=<%= connectorURL %>',
				filebrowserUploadUrl: null,
				toolbar: '<%= TextFormatter.format(HtmlUtil.escape(toolbarSet), TextFormatter.M) %>'
			}
		);

		var ckEditor = CKEDITOR.instances['<%= name %>'];

		var customDataProcessorLoaded = false;

		<%
		if (useCustomDataProcessor) {
		%>

			ckEditor.on(
				'customDataProcessorLoaded',
				function() {
					customDataProcessorLoaded = true;

					if (instanceReady) {
						setData();
					}
				}
			);

		<%
		}
		%>

		var instanceReady = false;

		ckEditor.on(
			'instanceReady',
			function() {
				<%
				if (useCustomDataProcessor) {
				%>

					instanceReady = true;

					if (customDataProcessorLoaded) {
						setData();
					}

				<%
				}
				else {
				%>

					setData();

				<%
				}

				if (Validator.isNotNull(onChangeMethod)) {

				%>

					setInterval(
						function() {
							try {
								window['<%= name %>'].onChangeCallback();
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