<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
PortletURL portletURL = renderResponse.createRenderURL();

List<String> headerNames = new ArrayList<String>();

headerNames.add("resource");
headerNames.add("custom-fields");

List<CustomAttributesDisplay> customAttributesDisplays = PortletLocalServiceUtil.getCustomAttributesDisplays();
%>

<liferay-ui:search-container
	emptyResultsMessage='<%= LanguageUtil.get(pageContext, "custom-fields-are-not-enabled-for-any-resource") %>'
	iteratorURL="<%= portletURL %>"
>
	<liferay-ui:search-container-results
		results="<%= ListUtil.subList(customAttributesDisplays, searchContainer.getStart(), searchContainer.getEnd()) %>"
		total="<%= customAttributesDisplays.size() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portlet.expando.model.CustomAttributesDisplay"
		modelVar="customAttributesDisplay"
		stringKey="<%= true %>"
	>
		<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="rowURL">
			<portlet:param name="struts_action" value="/expando/view_attributes" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="modelResource" value="<%= customAttributesDisplay.getClassName() %>" />
		</portlet:renderURL>

		<liferay-ui:search-container-row-parameter
			name="customAttributesDisplay"
			value="<%= customAttributesDisplay %>"
		/>

		<liferay-ui:search-container-column-text
			buffer="buffer"
			href="<%= rowURL %>"
			name="resource"
		>

			<%
			buffer.append("<img align=\"left\" border=\"0\" src=\"");
			buffer.append(customAttributesDisplay.getIconPath(themeDisplay));
			buffer.append("\" style=\"margin-right: 5px\">");
			buffer.append("<strong>");
			buffer.append(LanguageUtil.get(pageContext, "model.resource." + customAttributesDisplay.getClassName()));
			buffer.append("</strong>");
			%>

		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			buffer="buffer"
			href="<%= rowURL %>"
			name="custom-fields"
		>

			<%
			ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(customAttributesDisplay.getClassName());

			List<String> attributeNames = Collections.list(expandoBridge.getAttributeNames());

			buffer.append(StringUtil.merge(attributeNames, ", "));
			%>

		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-jsp
			align="right"
			path="/html/portlet/expando/resource_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator paginate="<%= false %>" />
</liferay-ui:search-container>

<%!
private static final String[] _CUSTOM_ATTRIBUTES_RESOURCES = {
	BlogsEntry.class.getName(),
	BookmarksEntry.class.getName(),
	BookmarksFolder.class.getName(),
	CalEvent.class.getName(),
	DLFileEntry.class.getName(),
	DLFolder.class.getName(),
	IGFolder.class.getName(),
	IGImage.class.getName(),
	JournalArticle.class.getName(),
	Layout.class.getName(),
	MBCategory.class.getName(),
	MBMessage.class.getName(),
	Organization.class.getName(),
	User.class.getName(),
	WikiPage.class.getName()
};
%>