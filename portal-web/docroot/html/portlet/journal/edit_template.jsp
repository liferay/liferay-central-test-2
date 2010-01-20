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
String redirect = ParamUtil.getString(request, "redirect");

String originalRedirect = ParamUtil.getString(request, "originalRedirect", StringPool.BLANK);

if (originalRedirect.equals(StringPool.BLANK)) {
	originalRedirect = redirect;
}
else {
	redirect = originalRedirect;
}

JournalTemplate template = (JournalTemplate)request.getAttribute(WebKeys.JOURNAL_TEMPLATE);

long groupId = BeanParamUtil.getLong(template, request, "groupId", scopeGroupId);

Group group = GroupLocalServiceUtil.getGroup(groupId);

String templateId = BeanParamUtil.getString(template, request, "templateId");
String newTemplateId = ParamUtil.getString(request, "newTemplateId");

String structureId = BeanParamUtil.getString(template, request, "structureId");

String structureName = StringPool.BLANK;

if (Validator.isNotNull(structureId)) {
	try {
		JournalStructure structure = JournalStructureLocalServiceUtil.getStructure(groupId, structureId);

		structureName = structure.getName();
	}
	catch (NoSuchStructureException nsse) {
	}
}

String xslContent = request.getParameter("xslContent");

String xsl = xslContent;

if (xslContent != null) {
	xsl = JS.decodeURIComponent(xsl);
}
else {
	xsl = BeanParamUtil.getString(template, request, "xsl");
}

String langType = BeanParamUtil.getString(template, request, "langType", JournalTemplateConstants.LANG_TYPE_VM);

boolean cacheable = BeanParamUtil.getBoolean(template, request, "cacheable");

if (template == null) {
	cacheable = true;
}

boolean smallImage = BeanParamUtil.getBoolean(template, request, "smallImage");
String smallImageURL = BeanParamUtil.getString(template, request, "smallImageURL");
%>

<aui:script>
	function <portlet:namespace />downloadTemplateContent() {
		document.<portlet:namespace />fm2.action = "<%= themeDisplay.getPathMain() %>/journal/get_template_content";
		document.<portlet:namespace />fm2.target = "_self";
		document.<portlet:namespace />fm2.xslContent.value = document.<portlet:namespace />fm1.<portlet:namespace />xslContent.value;
		document.<portlet:namespace />fm2.formatXsl.value = document.<portlet:namespace />fm1.<portlet:namespace />formatXsl.value;
		document.<portlet:namespace />fm2.langType.value = document.<portlet:namespace />fm1.<portlet:namespace />langType.value;
		document.<portlet:namespace />fm2.submit();
	}

	function <portlet:namespace />removeStructure() {
		document.<portlet:namespace />fm1.<portlet:namespace />structureId.value = "";

		var nameEl = document.getElementById("<portlet:namespace />structureName");

		nameEl.href = "#";
		nameEl.innerHTML = "";

		document.getElementById("<portlet:namespace />removeStructureButton").disabled = true;
	}

	function <portlet:namespace />saveAndContinueTemplate() {
		document.<portlet:namespace />fm1.<portlet:namespace />saveAndContinue.value = "1";
		<portlet:namespace />saveTemplate();
	}

	function <portlet:namespace />saveTemplate() {
		document.<portlet:namespace />fm1.<portlet:namespace /><%= Constants.CMD %>.value = "<%= template == null ? Constants.ADD : Constants.UPDATE %>";

		<c:if test="<%= template == null %>">
			document.<portlet:namespace />fm1.<portlet:namespace />templateId.value = document.<portlet:namespace />fm1.<portlet:namespace />newTemplateId.value;
		</c:if>

		submitForm(document.<portlet:namespace />fm1);
	}

	function <portlet:namespace />selectStructure(structureId, structureName) {
		document.<portlet:namespace />fm1.<portlet:namespace />structureId.value = structureId;

		var nameEl = document.getElementById("<portlet:namespace />structureName");

		nameEl.href = "<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/journal/edit_structure" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /></portlet:renderURL>&<portlet:namespace />structureId=" + structureId;
		nameEl.innerHTML = structureName + "&nbsp;";

		document.getElementById("<portlet:namespace />removeStructureButton").disabled = false;
	}
</aui:script>

<form method="post" name="<portlet:namespace />fm2">
<input name="xslContent" type="hidden" value="" />
<input name="formatXsl" type="hidden" value="" />
<input name="langType" type="hidden" value="" />
</form>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/journal/edit_template" /></portlet:actionURL>" enctype="multipart/form-data" method="post" name="<portlet:namespace />fm1" onSubmit="<portlet:namespace />saveTemplate(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(redirect) %>" />
<input name="<portlet:namespace />originalRedirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(originalRedirect) %>" />
<input name="<portlet:namespace />groupId" type="hidden" value="<%= groupId %>" />
<input name="<portlet:namespace />templateId" type="hidden" value="<%= HtmlUtil.escapeAttribute(templateId) %>" />
<input name="<portlet:namespace />xslContent" type="hidden" value="<%= JS.encodeURIComponent(xsl) %>" />
<input name="<portlet:namespace />saveAndContinue" type="hidden" value="" />

<liferay-ui:tabs
	names="template"
	backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
/>

<liferay-ui:error exception="<%= DuplicateTemplateIdException.class %>" message="please-enter-a-unique-id" />
<liferay-ui:error exception="<%= TemplateDescriptionException.class %>" message="please-enter-a-valid-description" />
<liferay-ui:error exception="<%= TemplateIdException.class %>" message="please-enter-a-valid-id" />
<liferay-ui:error exception="<%= TemplateNameException.class %>" message="please-enter-a-valid-name" />

<liferay-ui:error exception="<%= TemplateSmallImageNameException.class %>">

	<%
	String[] imageExtensions = PrefsPropsUtil.getStringArray(PropsKeys.JOURNAL_IMAGE_EXTENSIONS, ",");
	%>

	<liferay-ui:message key="image-names-must-end-with-one-of-the-following-extensions" /> <%= StringUtil.merge(imageExtensions, StringPool.COMMA) %>.
</liferay-ui:error>


<liferay-ui:error exception="<%= TemplateSmallImageSizeException.class %>" message="please-enter-a-file-with-a-valid-file-size" />
<liferay-ui:error exception="<%= TemplateXslException.class %>" message="please-enter-a-valid-script-template" />

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="id" />
	</td>
	<td>
		<c:choose>
			<c:when test="<%= PropsValues.JOURNAL_TEMPLATE_FORCE_AUTOGENERATE_ID %>">
				<c:choose>
					<c:when test="<%= template == null %>">
						<liferay-ui:message key="autogenerate-id" />

						<input name="<portlet:namespace />newTemplateId" type="hidden" value="" />
						<input name="<portlet:namespace />autoTemplateId" type="hidden" value="true" />
					</c:when>
					<c:otherwise>
						<%= templateId %>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<table class="lfr-table">
				<tr>
					<td>
						<c:choose>
							<c:when test="<%= template == null %>">
								<liferay-ui:input-field model="<%= JournalTemplate.class %>" bean="<%= template %>" field="templateId" fieldParam="newTemplateId" defaultValue="<%= newTemplateId %>" />
							</c:when>
							<c:otherwise>
								<%= templateId %>
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:if test="<%= template == null %>">
							<liferay-ui:input-checkbox param="autoTemplateId" />

							<liferay-ui:message key="autogenerate-id" />
						</c:if>
					</td>
				</tr>
				</table>
			</c:otherwise>
		</c:choose>
	</td>
</tr>
<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="name" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= JournalTemplate.class %>" bean="<%= template %>" field="name" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="description" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= JournalTemplate.class %>" bean="<%= template %>" field="description" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="cacheable" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= JournalTemplate.class %>" bean="<%= template %>" field="cacheable" defaultValue="<%= new Boolean(cacheable) %>" />

		<liferay-ui:icon-help message="journal-template-cacheable-help" />
	</td>
</tr>

<c:if test="<%= template != null %>">
	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="url" />
		</td>
		<td>
			<liferay-ui:input-resource
				url='<%= themeDisplay.getPortalURL() + themeDisplay.getPathMain() + "/journal/get_template?groupId=" + groupId + "&templateId=" + templateId %>'
			/>
		</td>
	</tr>

	<c:if test="<%= portletDisplay.isWebDAVEnabled() %>">
		<tr>
			<td>
				<liferay-ui:message key="webdav-url" />
			</td>
			<td>
				<liferay-ui:input-resource
					url='<%= themeDisplay.getPortalURL() + "/tunnel-web/secure/webdav/" + company.getWebId() + group.getFriendlyURL() + "/journal/Templates/" + templateId %>'
				/>
			</td>
		</tr>
	</c:if>
</c:if>

<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="structure" />
	</td>
	<td>
		<input name="<portlet:namespace />structureId" type="hidden" value="<%= structureId %>" />

		<c:choose>
			<c:when test="<%= (template == null) || (Validator.isNotNull(structureId)) %>">
				<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/journal/edit_structure" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /><portlet:param name="structureId" value="<%= structureId %>" /></portlet:renderURL>" id="<portlet:namespace />structureName">
				<%= structureName %></a>
			</c:when>
			<c:otherwise>
				<a id="<portlet:namespace />structureName"></a>
			</c:otherwise>
		</c:choose>

		<c:if test="<%= (template == null) || (Validator.isNull(template.getStructureId())) %>">
			<input type="button" value="<liferay-ui:message key="select" />" onClick="var structureWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/journal/select_structure" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /></portlet:renderURL>', 'structure', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680'); void(''); structureWindow.focus();" />

			<input <%= Validator.isNull(structureId) ? "disabled" : "" %> id="<portlet:namespace />removeStructureButton" type="button" value="<liferay-ui:message key="remove" />" onClick="<portlet:namespace />removeStructure();">
		</c:if>
	</td>
</tr>
<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="language-type" />
	</td>
	<td>
		<select name="<portlet:namespace />langType">

			<%
			for (int i = 0; i < JournalTemplateConstants.LANG_TYPES.length; i++) {
			%>

				<option <%= langType.equals(JournalTemplateConstants.LANG_TYPES[i]) ? "selected" : "" %> value="<%= JournalTemplateConstants.LANG_TYPES[i] %>"><%= JournalTemplateConstants.LANG_TYPES[i].toUpperCase() %></option>

			<%
			}
			%>

		</select>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="script" />
	</td>
	<td>
		<input class="lfr-input-text" name="<portlet:namespace />xsl" type="file" />

		<input id="<portlet:namespace />editorButton" type="button" value="<liferay-ui:message key="launch-editor" />" />

		<c:if test="<%= template != null %>">
			<input type="button" value="<liferay-ui:message key="download" />" onClick="<portlet:namespace />downloadTemplateContent();" />
		</c:if>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="format-script" />
	</td>
	<td>
		<liferay-ui:input-checkbox param="formatXsl" />
	</td>
</tr>
<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="small-image-url" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= JournalTemplate.class %>" bean="<%= template %>" field="smallImageURL" />
	</td>
</tr>
<tr>
	<td>
		<span style="font-size: xx-small;">-- <%= LanguageUtil.get(pageContext, "or").toUpperCase() %> --</span> <liferay-ui:message key="small-image" />
	</td>
	<td>
		<input class="lfr-input-text" name="<portlet:namespace />smallFile" type="file" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="use-small-image" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= JournalTemplate.class %>" bean="<%= template %>" field="smallImage" />
	</td>
</tr>

<c:if test="<%= template == null %>">
	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="permissions" />
		</td>
		<td>
			<liferay-ui:input-permissions
				modelName="<%= JournalTemplate.class.getName() %>"
			/>
		</td>
	</tr>
</c:if>

</table>

<br />

<input type="submit" value="<liferay-ui:message key="save" />" />

<input name="save-and-continue" type="button" value="<liferay-ui:message key="save-and-continue" />" onClick="<portlet:namespace />saveAndContinueTemplate();" />

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(PortalUtil.escapeRedirect(redirect)) %>';" />

</form>

<aui:script>
	Liferay.Util.inlineEditor(
		{
			url: '<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/journal/edit_template_xsl" /></portlet:renderURL>&<portlet:namespace />langType=' + document.<portlet:namespace />fm1.<portlet:namespace />langType.value,
			button: '#<portlet:namespace />editorButton',
			textarea: '<portlet:namespace />xslContent'
		}
	);

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		<c:choose>
			<c:when test="<%= PropsValues.JOURNAL_TEMPLATE_FORCE_AUTOGENERATE_ID %>">
				Liferay.Util.focusFormField(document.<portlet:namespace />fm1.<portlet:namespace />name);
			</c:when>
			<c:otherwise>
				Liferay.Util.focusFormField(document.<portlet:namespace />fm1.<portlet:namespace /><%= (template == null) ? "newTemplateId" : "name" %>);
			</c:otherwise>
		</c:choose>
	</c:if>
</aui:script>