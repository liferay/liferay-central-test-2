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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String editorType = ParamUtil.getString(request, "editorType");

if (Validator.isNotNull(editorType)) {
	portalPrefs.setValue(PortletKeys.JOURNAL, "editor-type", editorType);
}
else {
	editorType = portalPrefs.getValue(PortletKeys.JOURNAL, "editor-type", "html");
}

boolean useEditorCodepress = editorType.equals("codepress");
%>

<script type="text/javascript">
	function getEditorContent() {
		return <portlet:namespace />getXsd();
	}

	function <portlet:namespace />updateEditorType() {

		<%
		String newEditorType = "codepress";

		if (useEditorCodepress) {
			newEditorType = "html";
		}
		%>

		var editorForm = AUI().one(document.<portlet:namespace />editorForm);

		if (editorForm) {
			var popup = editorForm.ancestor('.aui-widget-bd');

			if (popup) {
				popup = popup.getDOM();
			}
		}

		Liferay.Util.switchEditor(
			{
				popup: popup,
				textarea: '<portlet:namespace />xsdContent',
				url: '<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/journal/edit_structure_xsd" /><portlet:param name="editorType" value="<%= newEditorType %>" /></portlet:renderURL>'
			}
		);
	}

	function <portlet:namespace />updateStructureXsd() {
		document.<portlet:namespace />fm1.scroll.value = "<portlet:namespace />xsd";

		var xsdContent = AUI().one('input[name=<portlet:namespace />xsd]');
		var content = '';

		<c:choose>
			<c:when test="<%= useEditorCodepress %>">
				if (xsdContent) {
					content = <portlet:namespace />xsdContent.getCode();
				}
			</c:when>
			<c:otherwise>
				content = document.<portlet:namespace />editorForm.<portlet:namespace />xsdContent.value;
			</c:otherwise>
		</c:choose>

		xsdContent.attr('value', content);

		AUI().DialogManager.closeByChild(document.<portlet:namespace />editorForm);

		submitForm(document.<portlet:namespace />fm1);
	}
</script>

<aui:form method="post" name="editorForm">
	<aui:fieldset>
		<aui:select name="editorType" onChange='<%= renderResponse.getNamespace() + "updateEditorType();" %>'>
			<aui:option label="plain" value="1" />
			<aui:option label="rich" selected="<%= useEditorCodepress %>" value="0" />
		</aui:select>
	</aui:fieldset>

	<c:choose>
		<c:when test="<%= useEditorCodepress %>">
			<aui:input name="xsdContent" type="textarea" wrap="off" />
		</c:when>
		<c:otherwise>
			<aui:input name="xsdContent" type="textarea" onKeyDown="Liferay.Util.checkTab(this); Liferay.Util.disableEsc();" wrap="off" />
		</c:otherwise>
	</c:choose>

	<aui:button-row>
		<aui:button onClick='<%= renderResponse.getNamespace() + "updateStructureXsd();" %>' type="button" value="update" />

		<c:if test="<%= !useEditorCodepress %>">
			<aui:button onClick='<%= "Liferay.Util.selectAndCopy(document." + renderResponse.getNamespace() + "editorForm." + renderResponse.getNamespace() + "xsdContent);" %>' type="button" value="select-and-copy" />
		</c:if>

		<aui:button onClick="AUI().DialogManager.closeByChild(this);" type="button" value="cancel" />
	</aui:button-row>
</aui:form>

<script type="text/javascript">
	AUI().ready(
		function() {
			document.<portlet:namespace />editorForm.<portlet:namespace />xsdContent.value = getEditorContent();

			Liferay.Util.resizeTextarea('<portlet:namespace />xsdContent', <%= useEditorCodepress %>, true);
		}
	);
</script>

<c:if test="<%= useEditorCodepress %>">
	<script src="<%= themeDisplay.getPathContext() %>/html/js/editor/codepress/codepress.js" type="text/javascript"></script>
</c:if>