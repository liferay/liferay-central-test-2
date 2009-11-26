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

<%@ include file="/html/portlet/journal_articles/init.jsp" %>

<%
String cur = ParamUtil.getString(request, "cur");

groupId = ParamUtil.getLong(request, "groupId", groupId);

if (Validator.isNotNull(structureId)) {
	try {
		JournalStructureLocalServiceUtil.getStructure(groupId, structureId);
	}
	catch (NoSuchStructureException nsse) {
		structureId = StringPool.BLANK;

		preferences.setValue("structure-id", structureId);

		preferences.store();
	}
}
%>

<liferay-portlet:renderURL portletConfiguration="true" varImpl="portletURL" />

<script type="text/javascript">
	function <portlet:namespace />removeStructure() {
		document.<portlet:namespace />fm1.<portlet:namespace />structureId.value = "";
		submitForm(document.<portlet:namespace />fm1);
	}

	function <portlet:namespace />saveConfiguration() {
		AUI().use(
			'io',
			function(A) {
				var form = A.get('#<portlet:namespace />fm1');

				var uri = form.getAttribute('action');

				A.io(
					uri,
					{
						form: {
							id: form
						},
						method: 'POST'
					}
				);
			}
		);
	}

	function <portlet:namespace />selectStructure(structureId) {
		document.<portlet:namespace />fm1.<portlet:namespace />structureId.value = structureId;
		submitForm(document.<portlet:namespace />fm1);
	}
</script>

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" id="<portlet:namespace />fm1" method="post" name="<portlet:namespace />fm1">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= portletURL.toString() %>&<portlet:namespace />cur=<%= cur %>" />
<input name="<portlet:namespace />structureId" type="hidden" value="<%= HtmlUtil.escapeAttribute(structureId) %>" />

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="community" />
	</td>
	<td>
		<select name="<portlet:namespace />groupId" onChange="submitForm(document.<portlet:namespace />fm1);">

			<%
			List<Group> myPlaces = user.getMyPlaces();

			for (int i = 0; i < myPlaces.size(); i++) {
				Group group = myPlaces.get(i);

				group = group.toEscapedModel();

				String groupName = group.getDescriptiveName();

				if (group.isUser()) {
					groupName = LanguageUtil.get(pageContext, "my-community");
				}
			%>

				<option <%= groupId == group.getGroupId() ? "selected" : "" %> value="<%= group.getGroupId() %>"><%= HtmlUtil.escape(groupName) %></option>

			<%
			}
			%>

		</select>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="web-content-type" />
	</td>
	<td>
		<select name="<portlet:namespace />type" onChange="<portlet:namespace />saveConfiguration();">
			<option value=""></option>

			<%
			for (int i = 0; i < JournalArticleConstants.TYPES.length; i++) {
			%>

				<option <%= type.equals(JournalArticleConstants.TYPES[i]) ? "selected" : "" %> value="<%= JournalArticleConstants.TYPES[i] %>"><%= LanguageUtil.get(pageContext, JournalArticleConstants.TYPES[i]) %></option>

			<%
			}
			%>

		</select>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="display-url" />
	</td>
	<td>
		<select name="<portlet:namespace />pageURL" onChange="<portlet:namespace />saveConfiguration();">
			<option <%= pageURL.equals("maximized") ? "selected" : "" %> value="maximized"><liferay-ui:message key="maximized" /></option>
			<option <%= pageURL.equals("normal") ? "selected" : "" %> value="normal"><liferay-ui:message key="normal" /></option>
			<option <%= pageURL.equals("popUp") ? "selected" : "" %> value="popUp"><liferay-ui:message key="pop-up" /></option>
		</select>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="display-per-page" />
	</td>
	<td>
		<select name="<portlet:namespace />pageDelta" onChange="<portlet:namespace />saveConfiguration();">

			<%
			String[] pageDeltaValues = PropsUtil.getArray(PropsKeys.JOURNAL_ARTICLES_PAGE_DELTA_VALUES);

			for (int i = 0; i < pageDeltaValues.length; i++) {
			%>

				<option <%= (pageDelta == GetterUtil.getInteger(pageDeltaValues[i])) ? "selected" : "" %> value="<%= pageDeltaValues[i] %>"><%= pageDeltaValues[i] %></option>

			<%
			}
			%>

		</select>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="order-by-column" />
	</td>
	<td>
		<select name="<portlet:namespace />orderByCol" onChange="<portlet:namespace />saveConfiguration();">
			<option <%= orderByCol.equals("display-date") ? "selected" : "" %> value="display-date"><liferay-ui:message key="display-date" /></option>
			<option <%= orderByCol.equals("create-date") ? "selected" : "" %> value="create-date"><liferay-ui:message key="create-date" /></option>
			<option <%= orderByCol.equals("modified-date") ? "selected" : "" %> value="modified-date"><liferay-ui:message key="modified-date" /></option>
			<option <%= orderByCol.equals("title") ? "selected" : "" %> value="title"><liferay-ui:message key="web-content-title" /></option>
			<option <%= orderByCol.equals("id") ? "selected" : "" %> value="id"><liferay-ui:message key="id" /></option>
		</select>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="order-by-type" />
	</td>
	<td>
		<select name="<portlet:namespace />orderByType" onChange="<portlet:namespace />saveConfiguration();">
			<option <%= orderByType.equals("asc") ? "selected" : "" %> value="asc"><liferay-ui:message key="ascending" /></option>
			<option <%= orderByType.equals("desc") ? "selected" : "" %> value="desc"><liferay-ui:message key="descending" /></option>
		</select>
	</td>
</tr>
</table>

<br />

<div class="portlet-msg-info">
	<c:choose>
		<c:when test="<%= Validator.isNull(structureId) %>">
			<liferay-ui:message key="select-a-structure-to-filter-the-web-content-list-by-a-structure" />
		</c:when>
		<c:otherwise>
			<%= LanguageUtil.format(pageContext, "filter-web-content-list-by-structure-x", structureId) %> <a href="javascript:<portlet:namespace />removeStructure();"><liferay-ui:message key="click-to-remove-filter" /></a>
		</c:otherwise>
	</c:choose>
</div>

</form>

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= portletURL.toString() %>&<portlet:namespace />cur=<%= cur %>" />

<liferay-ui:tabs names="structures" />

<%
DynamicRenderRequest dynamicRenderRequest = new DynamicRenderRequest(renderRequest);

dynamicRenderRequest.setParameter("groupId", String.valueOf(groupId));

StructureSearch searchContainer = new StructureSearch(dynamicRenderRequest, portletURL);
%>

<liferay-ui:search-form
	page="/html/portlet/journal/structure_search.jsp"
	searchContainer="<%= searchContainer %>"
/>

<br />

<%
StructureSearchTerms searchTerms = (StructureSearchTerms)searchContainer.getSearchTerms();
%>

<%@ include file="/html/portlet/journal/structure_search_results.jspf" %>

<%
List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	JournalStructure structure = (JournalStructure)results.get(i);

	structure = structure.toEscapedModel();

	ResultRow row = new ResultRow(structure, structure.getStructureId(), i);

	StringBuilder sb = new StringBuilder();

	sb.append("javascript:");
	sb.append(renderResponse.getNamespace());
	sb.append("selectStructure('");
	sb.append(structure.getStructureId());
	sb.append("');");

	String rowHREF = sb.toString();

	// Structure id

	row.addText(structure.getStructureId(), rowHREF);

	// Name and description

	sb = new StringBuilder();

	sb.append(structure.getName());

	if (Validator.isNotNull(structure.getDescription())) {
		sb.append("<br />");
		sb.append(structure.getDescription());
	}

	row.addText(sb.toString(), rowHREF);

	// Add result row

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

</form>