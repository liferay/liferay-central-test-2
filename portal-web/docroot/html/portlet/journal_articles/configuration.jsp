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

String redirect = ParamUtil.getString(request, "redirect");

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

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm1">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value='<%= portletURL.toString() + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur %>' />
	<aui:input name="structureId" type="hidden" value="<%= structureId %>" />

	<aui:fieldset>
		<aui:select label="community" name="groupId" onChange='<%= "submitForm(document." + renderResponse.getNamespace() + "fm1);" %>'>

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

				<aui:option label="<%= groupName %>" selected="<%= groupId == group.getGroupId() %>" value="<%= group.getGroupId() %>" />

			<%
			}
			%>

		</aui:select>

		<aui:select label="web-content-type" name="type" onChange='<%= renderResponse.getNamespace() + "saveConfiguration();" %>'>
			<aui:option value="" />

			<%
			for (int i = 0; i < JournalArticleConstants.TYPES.length; i++) {
			%>

				<aui:option label="<%= JournalArticleConstants.TYPES[i] %>" selected="<%= type.equals(JournalArticleConstants.TYPES[i]) %>" />

			<%
			}
			%>

		</aui:select>

		<aui:select label="display-url" name="pageURL" onChange='<%= renderResponse.getNamespace() + "saveConfiguration();" %>'>
			<aui:option label="maximized" selected='<%= pageURL.equals("maximized") %>' />
			<aui:option label="normal" selected='<%= pageURL.equals("normal") %>' />
			<aui:option label="pop-up" selected='<%= pageURL.equals("popUp") %>' />
		</aui:select>

		<aui:select label="display-per-page" name="pageDelta" onChange='<%= renderResponse.getNamespace() + "saveConfiguration();" %>'>

			<%
			String[] pageDeltaValues = PropsUtil.getArray(PropsKeys.JOURNAL_ARTICLES_PAGE_DELTA_VALUES);

			for (int i = 0; i < pageDeltaValues.length; i++) {
			%>

				<aui:option label="<%= pageDeltaValues[i] %>" selected="<%= pageDelta == GetterUtil.getInteger(pageDeltaValues[i]) %>" />

			<%
			}
			%>

		</aui:select>

		<aui:select label="order-by-column" name="orderByCol" onChange='<%= renderResponse.getNamespace() + "saveConfiguration();" %>'>
			<aui:option label="display-date" selected='<%= orderByCol.equals("display-date") %>' />
			<aui:option label="create-date" selected='<%= orderByCol.equals("create-date") %>' />
			<aui:option label="modified-date" selected='<%= orderByCol.equals("modified-date") %>' />
			<aui:option label="title" selected='<%= orderByCol.equals("title") %>' />
			<aui:option label="id" selected='<%= orderByCol.equals("id") %>' />
		</aui:select>

		<aui:select label="order-by-type" name="orderByType" onChange='<%= renderResponse.getNamespace() + "saveConfiguration();" %>'>
			<aui:option label="ascending" selected='<%= orderByType.equals("asc") %>' />
			<aui:option label="descending" selected='<%= orderByType.equals("desc") %>' />
		</aui:select>
	</aui:fieldset>

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
</aui:form>

<aui:form action="<%= configurationURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="" />
	<aui:input name="redirect" type="hidden" value='<%= portletURL.toString() + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur %>' />

	<aui:fieldset>
		<aui:legend label="structures" />

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
	</aui:fieldset>

	<aui:button-row>
		<aui:button name="saveButton" type="submit" value="save" />

		<aui:button name="cancelButton" onClick="<%= redirect %>" type="button" value="cancel" />
	</aui:button-row>
</aui:form>