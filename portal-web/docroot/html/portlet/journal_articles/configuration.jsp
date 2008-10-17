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

<%@ include file="/html/portlet/journal_articles/init.jsp" %>

<%
groupId = ParamUtil.getLong(request, "groupId", groupId);
PortletURL portletURL = renderResponse.createRenderURL();
%>

<script type="text/javascript">
	function <portlet:namespace />selectStructure(structureId) {
		document.<portlet:namespace />fm.<portlet:namespace />structureId.value = structureId;
		submitForm(document.<portlet:namespace />fm);
	}
</script>

<c:if test='<%= "structure".equals(filterArticlesBy) && Validator.isNotNull(structureId) %>'>
	<div class="portlet-msg-info">
		<liferay-ui:message key="displaying-articles-of-structure" />: <%= structureId %>
	</div>
</c:if>

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
<input name="<portlet:namespace />structureId" type="hidden" value="<%= structureId %>" />

<liferay-ui:error exception="<%= NoSuchGroupException.class %>" message="the-community-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchStructureException.class %>" message="the-structure-could-not-be-found" />

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="community" />
	</td>
	<td>
		<select name="<portlet:namespace />groupId">
			<option value=""></option>

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

				<option <%= groupId == group.getGroupId() ? "selected" : "" %> value="<%= group.getGroupId() %>"><%= groupName %></option>

			<%
			}
			%>

		</select>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="article-type" />
	</td>
	<td>
		<select name="<portlet:namespace />type">
			<option value=""></option>

			<%
			for (int i = 0; i < JournalArticleImpl.TYPES.length; i++) {
			%>

				<option <%= type.equals(JournalArticleImpl.TYPES[i]) ? "selected" : "" %> value="<%= JournalArticleImpl.TYPES[i] %>"><%= LanguageUtil.get(pageContext, JournalArticleImpl.TYPES[i]) %></option>

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
		<select name="<portlet:namespace />pageURL">
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
		<select name="<portlet:namespace />pageDelta">

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
		<select name="<portlet:namespace />orderByCol">
			<option <%= orderByCol.equals("display-date") ? "selected" : "" %> value="display-date"><liferay-ui:message key="display-date" /></option>
			<option <%= orderByCol.equals("create-date") ? "selected" : "" %> value="create-date"><liferay-ui:message key="create-date" /></option>
			<option <%= orderByCol.equals("modified-date") ? "selected" : "" %> value="modified-date"><liferay-ui:message key="modified-date" /></option>
			<option <%= orderByCol.equals("title") ? "selected" : "" %> value="title"><liferay-ui:message key="article-title" /></option>
			<option <%= orderByCol.equals("id") ? "selected" : "" %> value="id"><liferay-ui:message key="id" /></option>
		</select>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="order-by-type" />
	</td>
	<td>
		<select name="<portlet:namespace />orderByType">
			<option <%= orderByType.equals("asc") ? "selected" : "" %> value="asc"><liferay-ui:message key="ascending" /></option>
			<option <%= orderByType.equals("desc") ? "selected" : "" %> value="desc"><liferay-ui:message key="descending" /></option>
		</select>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="filter-articles-by" />
	</td>
	<td>
		<select name="<portlet:namespace />filterArticlesBy">
			<option <%= filterArticlesBy.equals("article-type") ? "selected" : "" %> value="article-type"><liferay-ui:message key="article-type" /></option>
			<option <%= filterArticlesBy.equals("structure") ? "selected" : "" %> value="structure"><liferay-ui:message key="structure" /></option>
		</select>
	</td>
</tr>
</table>

<br />

<input type="button" value="<liferay-ui:message key="save" />" onClick="submitForm(document.<portlet:namespace />fm);" />

<div class="separator"><!-- --></div>

<input name="<portlet:namespace />groupId" type="hidden" value="" />

<%
StructureSearch searchContainer = new StructureSearch(renderRequest, portletURL);

List headerNames = searchContainer.getHeaderNames();

headerNames.add(StringPool.BLANK);

searchContainer.setRowChecker(new RowChecker(renderResponse));
%>

<liferay-ui:search-form
	page="/html/portlet/journal/structure_search.jsp"
	searchContainer="<%= searchContainer %>"
/>

<%
StructureSearchTerms searchTerms = (StructureSearchTerms)searchContainer.getSearchTerms();
%>

<%@ include file="/html/portlet/journal/structure_search_results.jspf" %>

<div class="separator"><!-- --></div>

<%
List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	JournalStructure structure = (JournalStructure)results.get(i);

	structure = structure.toEscapedModel();

	ResultRow row = new ResultRow(structure, structure.getStructureId(), i);

	StringBuilder href = new StringBuilder();

	href.append("javascript: ");
	href.append(renderResponse.getNamespace());
	href.append("selectStructure('");
	href.append(structure.getStructureId());
	href.append("');");

	String rowHREF = href.toString();

	// Structure id

	row.addText(structure.getStructureId(), rowHREF);

	// Name and description

	StringBuilder sb = new StringBuilder();

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