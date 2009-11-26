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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
String uploadProgressId = PwdGenerator.getPassword(PwdGenerator.KEY3, 4);
String importProgressId = PwdGenerator.getPassword(PwdGenerator.KEY3, 4);

String tabs2 = ParamUtil.getString(request, "tabs2");

String redirect = ParamUtil.getString(request, "redirect");

WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);

long nodeId = BeanParamUtil.getLong(node, request, "nodeId");

String[] importers = PropsValues.WIKI_IMPORTERS;

if (Validator.isNull(tabs2)) {
	tabs2 = importers[0];
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/wiki/import_pages");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("nodeId", String.valueOf(nodeId));
%>

<script type="text/javascript">
	function <portlet:namespace />importPages() {
		<%= uploadProgressId %>.startProgress();
		<%= importProgressId %>.startProgress();

		submitForm(document.<portlet:namespace />fm);
	}
</script>

<portlet:actionURL var="importPagesURL">
	<portlet:param name="struts_action" value="/wiki/import_pages" />
</portlet:actionURL>

<aui:form action="<%= importPagesURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "importPages(); return false;" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="importProgressId" type="hidden" value="<%= importProgressId %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="nodeId" type="hidden" value="<%= nodeId %>" />
	<aui:input name="importer" type="hidden" value="<%= tabs2 %>" />

	<liferay-ui:tabs
		names="import-pages"
		backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
	/>

	<liferay-ui:tabs
		names="<%= StringUtil.merge(importers) %>"
		param="tabs2"
		url="<%= portletURL.toString() %>"
	/>

	<liferay-ui:error exception="<%= ImportFilesException.class %>" message="please-provide-all-mandatory-files-and-make-sure-the-file-types-are-valid" />
	<liferay-ui:error exception="<%= NoSuchNodeException.class %>" message="the-node-could-not-be-found" />

	<liferay-util:include page="<%= PropsUtil.get(PropsKeys.WIKI_IMPORTERS_PAGE, new Filter(tabs2)) %>" />

	<aui:button-row>
		<aui:button name="saveButton" type="submit" value="import" />

		<aui:button name="cancelButton" onClick="<%= redirect %>" type="button" value="cancel" />
	</aui:button-row>
</aui:form>

<liferay-ui:upload-progress
	id="<%= uploadProgressId %>"
	message="uploading"
	redirect="<%= HtmlUtil.escape(PortalUtil.escapeRedirect(redirect)) %>"
/>

<liferay-ui:upload-progress
	id="<%= importProgressId %>"
	message="importing"
	redirect="<%= HtmlUtil.escape(PortalUtil.escapeRedirect(redirect)) %>"
/>