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

<%@ include file="/html/portlet/workflow/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "definitions");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/workflow/view");
portletURL.setParameter("tabs1", tabs1);
%>

<form action="<%= portletURL.toString() %>" method="post" name="<portlet:namespace />fm">

<liferay-ui:tabs
	names="definitions,instances,tasks"
	url="<%= portletURL.toString() %>"
/>

<c:choose>
	<c:when test='<%= tabs1.equals("definitions") %>'>

		<%
		DefinitionSearch searchContainer = new DefinitionSearch(renderRequest, portletURL);
		%>

		<liferay-ui:search-form
			page="/html/portlet/workflow/definition_search.jsp"
			searchContainer="<%= searchContainer %>"
		/>

		<c:if test="<%= renderRequest.getWindowState().equals(WindowState.MAXIMIZED) %>">

			<%
			DefinitionSearchTerms searchTerms = (DefinitionSearchTerms)searchContainer.getSearchTerms();

			int total = WorkflowComponentServiceUtil.getDefinitionsCount(searchTerms.getDefinitionId(), searchTerms.getName());

			searchContainer.setTotal(total);

			List results = WorkflowComponentServiceUtil.getDefinitions(searchTerms.getDefinitionId(), searchTerms.getName(), searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);
			%>

			<br><div class="beta-separator"></div><br>

			<%--<c:if test="<%= PortletPermission.contains(permissionChecker, plid, PortletKeys.JOURNAL, ActionKeys.ADD_TEMPLATE) %>">
				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "add") %>' onClick="self.location = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/journal/edit_template" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>';">
			</c:if>

			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "delete") %>' onClick="<portlet:namespace />deleteDefinitions();">

			<br><br>--%>

			<%
			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				WorkflowDefinition definition = (WorkflowDefinition)results.get(i);

				String definitionId = String.valueOf(definition.getDefinitionId());

				ResultRow row = new ResultRow(definition, definitionId, i);

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setWindowState(WindowState.MAXIMIZED);

				rowURL.setParameter("struts_action", "/workflow/edit_definition");
				//rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("definitionId", definitionId);

				row.setParameter("rowHREF", rowURL.toString());

				// Definition id

				row.addText(definitionId, rowURL);

				// Name

				row.addText(definition.getName(), rowURL);

				// Type

				row.addText(GetterUtil.getString(definition.getType()), rowURL);

				// Version

				row.addText(String.valueOf(definition.getVersion()), rowURL);

				// Add result row

				resultRows.add(row);
			}
			%>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

			<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
		</c:if>
	</c:when>
	<c:when test='<%= tabs1.equals("instances") %>'>
	</c:when>
	<c:when test='<%= tabs1.equals("tasks") %>'>
	</c:when>
</c:choose>

</form>