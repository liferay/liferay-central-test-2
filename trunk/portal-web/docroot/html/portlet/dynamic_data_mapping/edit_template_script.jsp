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

<%@ include file="/html/portlet/dynamic_data_mapping/init.jsp" %>

<%
String language = ParamUtil.getString(request, "language");

String editorType = ParamUtil.getString(request, "editorType");

if (Validator.isNotNull(editorType)) {
	portalPreferences.setValue(PortletKeys.DYNAMIC_DATA_MAPPING, "editor-type", editorType);
}
else {
	editorType = portalPreferences.getValue(PortletKeys.DYNAMIC_DATA_MAPPING, "editor-type", "html");
}

boolean useEditorCodepress = editorType.equals("codepress");

String defaultContent = ContentUtil.get(PropsUtil.get(PropsKeys.DYNAMIC_DATA_MAPPING_TEMPLATE_LANGUAGE_CONTENT, new Filter(language)));
%>

<aui:form method="post" name="editorForm">
	<aui:fieldset>
		<aui:select name="editorType" onChange='<%= renderResponse.getNamespace() + "updateEditorType();" %>'>
			<aui:option label="plain" value="1" />
			<aui:option label="rich" selected="<%= useEditorCodepress %>" value="0" />
		</aui:select>

		<c:choose>
			<c:when test="<%= useEditorCodepress %>">
				 <aui:input cssClass="lfr-template-editor" inputCssClass="codepress html" label="" name="scriptContent" type="textarea" wrap="off" />
			</c:when>
			<c:otherwise>
				<aui:input cssClass="lfr-template-editor" inputCssClass="lfr-textarea" label="" name="scriptContent" onKeyDown="Liferay.Util.checkTab(this); Liferay.Util.disableEsc();" type="textarea" wrap="off" />
			</c:otherwise>
		</c:choose>
	</aui:fieldset>

	<aui:button-row>
		<aui:button onClick='<%= renderResponse.getNamespace() + "updateTemplateScript();" %>' value="update" />

		<c:if test="<%= !useEditorCodepress %>">
			<aui:button onClick='<%= "Liferay.Util.selectAndCopy(document." + renderResponse.getNamespace() + "editorForm." + renderResponse.getNamespace() + "scriptContent);" %>' value="select-and-copy" />
		</c:if>

		<aui:button type="cancel" />
	</aui:button-row>
</aui:form>

<c:if test="<%= useEditorCodepress %>">
	<script src="<%= themeDisplay.getPathContext() %>/html/js/editor/codepress/codepress.js" type="text/javascript"></script>
</c:if>

<aui:script>
	function <portlet:namespace />getEditorContent() {
		var openerAUI = Liferay.Util.getOpener().AUI();

		var scriptContent = openerAUI.one('input[name=<portlet:namespace />scriptContent]');

		if (scriptContent) {
			var content = decodeURIComponent(scriptContent.val());
		}

		if (!content) {
			content = "<%= UnicodeFormatter.toString(defaultContent) %>";
		}

		return content;
	}

	Liferay.provide(
		window,
		'<portlet:namespace />updateEditorType',
		function() {
			var A = AUI();

			<%
			String newEditorType = "codepress";

			if (useEditorCodepress) {
				newEditorType = "html";
			}
			%>

			Liferay.Util.switchEditor(
				{
					textarea: '<portlet:namespace />scriptContent',
					uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/dynamic_data_mapping/edit_template_script" /><portlet:param name="language" value="<%= language %>" /><portlet:param name="editorType" value="<%= newEditorType %>" /></portlet:renderURL>'
				}
			);
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />updateTemplateScript',
		function() {
			var openingWindow = Liferay.Util.getOpener();

			var openerAUI = openingWindow.AUI();

			var scriptContent = openerAUI.one('input[name=<portlet:namespace />scriptContent]');
			var content = '';

			<c:choose>
				<c:when test="<%= useEditorCodepress %>">
					content = <portlet:namespace />scriptContent.getCode();
				</c:when>
				<c:otherwise>
					content = document.<portlet:namespace />editorForm.<portlet:namespace />scriptContent.value;
				</c:otherwise>
			</c:choose>

			scriptContent.val(encodeURIComponent(content));

			var dialog = Liferay.Util.getWindow();

			if (dialog) {
				dialog.close();
			}
		},
		['aui-dialog']
	);

	Liferay.Util.resizeTextarea('<portlet:namespace />scriptContent', <%= useEditorCodepress %>, true);
</aui:script>

<aui:script use="aui-base">
	var textarea = '#<portlet:namespace />scriptContent';

	if (<%= useEditorCodepress %>) {
		textarea = '#<portlet:namespace />scriptContent_cp';
	}

	A.on(
		'available',
		function(event) {
			A.one(textarea).val(<portlet:namespace />getEditorContent());
		},
		textarea
	);
</aui:script>