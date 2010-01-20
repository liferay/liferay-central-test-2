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

<%@ include file="/html/portlet/expando/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String modelResource = ParamUtil.getString(request, "modelResource");
String modelResourceName = ResourceActionsUtil.getModelResource(pageContext, modelResource);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/expando/view_attributes");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("modelResource", modelResource);
%>

<aui:script>
	function <portlet:namespace />addExpando() {
		submitForm(document.hrefFm, '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/expando/edit_expando" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="modelResource" value="<%= modelResource %>" /></portlet:renderURL>');
	}
</aui:script>

<div>
	<liferay-ui:message key="edit-custom-fields-for" />: <aui:a href="<%= HtmlUtil.escape(PortalUtil.escapeRedirect(redirect)) %>"><%= modelResourceName %></aui:a>
</div>

<br />

<liferay-ui:tabs
	names="custom-fields"
	backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
/>

<%
ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(modelResource);

List<String> attributeNames = Collections.list(expandoBridge.getAttributeNames());
%>

<liferay-ui:search-container
	emptyResultsMessage='<%= LanguageUtil.format(pageContext, "no-custom-fields-are-defined-for-x", modelResourceName) %>'
	iteratorURL="<%= portletURL %>"
>
	<liferay-ui:search-container-results
		total="<%= attributeNames.size() %>"
		results="<%= attributeNames %>"
	/>

	<liferay-ui:search-container-row
		className="java.lang.String"
		modelVar="name"
		stringKey="<%= true %>"
	>

		<%
		int type = expandoBridge.getAttributeType(name);

		ExpandoColumn expandoColumn = ExpandoColumnLocalServiceUtil.getDefaultTableColumn(modelResource, name);
		%>

		<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="rowURL">
			<portlet:param name="struts_action" value="/expando/edit_expando" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="columnId" value="<%= String.valueOf(expandoColumn.getColumnId()) %>" />
			<portlet:param name="modelResource" value="<%= modelResource %>" />
		</portlet:renderURL>

		<liferay-ui:search-container-row-parameter
			name="expandoColumn"
			value="<%= expandoColumn %>"
		/>

		<liferay-ui:search-container-row-parameter
			name="modelResource"
			value="<%= modelResource %>"
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			buffer="buffer"
			name="name"
		>

			<%
			String localizedName = LanguageUtil.get(pageContext, name);

			if (name.equals(localizedName)) {
				localizedName = TextFormatter.format(name, TextFormatter.J);
			}

			buffer.append(HtmlUtil.escape(localizedName));
			%>

		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="key"
			value="<%= HtmlUtil.escape(name) %>"
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="type"
			value="<%= LanguageUtil.get(pageContext, ExpandoColumnConstants.getTypeLabel(type)) %>"
		/>

		<liferay-ui:search-container-column-text
			buffer="buffer"
			href="<%= rowURL %>"
			name="default-value"
		>

			<%
			if (type == ExpandoColumnConstants.BOOLEAN) {
				buffer.append((Boolean)expandoBridge.getAttributeDefault(name));
			}
			else if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
				buffer.append(StringUtil.merge((boolean[])expandoBridge.getAttributeDefault(name), StringPool.COMMA_AND_SPACE));
			}
			else if (type == ExpandoColumnConstants.DATE) {
				buffer.append(dateFormatDateTime.format((Date)expandoBridge.getAttributeDefault(name)));
			}
			else if (type == ExpandoColumnConstants.DATE_ARRAY) {
				Date[] dates = (Date[])expandoBridge.getAttributeDefault(name);

				for (int i = 0; i < dates.length; i++) {
					if (i != 0) {
						buffer.append(StringPool.COMMA_AND_SPACE);
					}

					buffer.append(dateFormatDateTime.format(dates[i]));
				}
			}
			else if (type == ExpandoColumnConstants.DOUBLE) {
				buffer.append((Double)expandoBridge.getAttributeDefault(name));
			}
			else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
				buffer.append(StringUtil.merge((double[])expandoBridge.getAttributeDefault(name), StringPool.COMMA_AND_SPACE));
			}
			else if (type == ExpandoColumnConstants.FLOAT) {
				buffer.append((Float)expandoBridge.getAttributeDefault(name));
			}
			else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
				buffer.append(StringUtil.merge((float[])expandoBridge.getAttributeDefault(name), StringPool.COMMA_AND_SPACE));
			}
			else if (type == ExpandoColumnConstants.INTEGER) {
				buffer.append((Integer)expandoBridge.getAttributeDefault(name));
			}
			else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
				buffer.append(StringUtil.merge((int[])expandoBridge.getAttributeDefault(name), StringPool.COMMA_AND_SPACE));
			}
			else if (type == ExpandoColumnConstants.LONG) {
				buffer.append((Long)expandoBridge.getAttributeDefault(name));
			}
			else if (type == ExpandoColumnConstants.LONG_ARRAY) {
				buffer.append(StringUtil.merge((long[])expandoBridge.getAttributeDefault(name), StringPool.COMMA_AND_SPACE));
			}
			else if (type == ExpandoColumnConstants.SHORT) {
				buffer.append((Short)expandoBridge.getAttributeDefault(name));
			}
			else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
				buffer.append(StringUtil.merge((short[])expandoBridge.getAttributeDefault(name), StringPool.COMMA_AND_SPACE));
			}
			else if (type == ExpandoColumnConstants.STRING_ARRAY) {
				buffer.append(HtmlUtil.escape(StringUtil.merge((String[])expandoBridge.getAttributeDefault(name), StringPool.COMMA_AND_SPACE)));
			}
			else {
				buffer.append(HtmlUtil.escape((String)expandoBridge.getAttributeDefault(name)));
			}
			%>

		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-jsp
			align="right"
			path="/html/portlet/expando/expando_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_EXPANDO) %>">
		<aui:button-row>
			<aui:button onClick='<%= renderResponse.getNamespace() + "addExpando();" %>' value="add-custom-field" />
		</aui:button-row>

		<br />
	</c:if>

	<liferay-ui:search-iterator paginate="<%= false %>" />
</liferay-ui:search-container>

<%
PortalUtil.addPortletBreadcrumbEntry(request, modelResourceName, portletURL.toString());
%>