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

<%@ include file="/html/portlet/alfresco_content/init.jsp" %>

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>">
<input name="<portlet:namespace />uuid" type="hidden" value="">

<table border="0" cellpadding="0" cellspacing="0">
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "url") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<input class="form-text" name="<portlet:namespace />url" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="text" value="<%= url %>">
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "user-id") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<input class="form-text" name="<portlet:namespace />userId" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="text" value="<%= userId %>">
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "password") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<input class="form-text" name="<portlet:namespace />password" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="text" value="<%= password %>">
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "maximize-links") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<liferay-ui:input-checkbox param="maximizeLinks" defaultValue="<%= maximizeLinks %>" />
	</td>
</tr>
</table>

<br>

<input class="portlet-form-button" type="button" value="<bean:message key="save" />" onClick="submitForm(document.<portlet:namespace />fm);"><br>

<%
String nodeName = null;

try {
	Node node = AlfrescoContentUtil.getNode(url, userId, password, uuid);

	nodeName = AlfrescoContentUtil.getNamedValue(node.getProperties(), org.alfresco.webservice.util.Constants.PROP_NAME);
}
catch (Exception e) {
	_log.warn(e.getMessage());
}
%>

<c:if test="<%= Validator.isNotNull(nodeName) %>">
	<br>

	<%= LanguageUtil.get(pageContext, "displaying-content") %>: <%= nodeName %><br>
</c:if>

<br><div class="beta-separator"></div><br>

<%
SearchContainer searchContainer = new SearchContainer();

List headerNames = new ArrayList();

headerNames.add("content");

searchContainer.setHeaderNames(headerNames);
searchContainer.setEmptyResultsMessage("no-alfresco-content-was-found");

ResultSetRow[] results = new ResultSetRow[0];

try {
	String selUuid = ParamUtil.getString(request, "uuid");

	if (selUuid.equals(uuid)) {
		selUuid = StringPool.BLANK;
	}

	results = AlfrescoContentUtil.getNodes(url, userId, password, selUuid);

	if (results == null) {
		results = new ResultSetRow[0];
	}
}
catch (Exception e) {
	_log.warn(e.getMessage());
}

int total = results.length;

searchContainer.setTotal(total);

List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.length; i++) {
	ResultSetRow resultSetRow = results[i];

	ResultSetRowNode node = resultSetRow.getNode();

	ResultRow row = new ResultRow(node, node.getId(), i);

    NamedValue[] namedValues = resultSetRow.getColumns();

	StringBuffer sb = new StringBuffer();

	sb.append("javascript: ");

	String propContent = AlfrescoContentUtil.getNamedValue(namedValues, org.alfresco.webservice.util.Constants.PROP_CONTENT);

	if (propContent == null) {
		sb.append("document.");
		sb.append(renderResponse.getNamespace());
		sb.append("fm.");
		sb.append(renderResponse.getNamespace());
		sb.append(Constants.CMD);
		sb.append(".value = ''; ");
	}

	sb.append("document.");
	sb.append(renderResponse.getNamespace());
	sb.append("fm.");
	sb.append(renderResponse.getNamespace());
	sb.append("uuid.value = '");
	sb.append(node.getId());
	sb.append("'; ");
	sb.append("submitForm(document.");
	sb.append(renderResponse.getNamespace());
	sb.append("fm);");

	String rowHREF = sb.toString();

	// Name

	row.addText(AlfrescoContentUtil.getNamedValue(namedValues, org.alfresco.webservice.util.Constants.PROP_NAME), rowHREF);

	// Add result row

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />url.focus();
</script>

<%!
private static Log _log = LogFactory.getLog("portal-web.docroot.html.portlet.alfresco_content.edit_configuration.jsp");
%>