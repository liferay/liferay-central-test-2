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
PortletURL portletURL = renderResponse.createRenderURL();

List<String> headerNames = new ArrayList<String>();

headerNames.add("resource");
headerNames.add("custom-attributes");

List<String> modelResources = ListUtil.fromArray(_CUSTOM_ATTRIBUTES_RESOURCES);
%>

<liferay-ui:search-container
	emptyResultsMessage='<%= LanguageUtil.get(pageContext, "custom-attributes-are-not-enabled-for-any-resource") %>'
	iteratorURL="<%= portletURL %>"
>
	<liferay-ui:search-container-results
		results="<%= ListUtil.subList(modelResources, searchContainer.getStart(), searchContainer.getEnd()) %>"
		total="<%= modelResources.size() %>"
	/>

	<liferay-ui:search-container-row
		className="java.lang.String"
		modelVar="modelResource"
		stringKey="<%= true %>"
	>

		<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="rowURL">
			<portlet:param name="struts_action" value="/expando/view_attributes" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="modelResource" value="<%= modelResource %>" />
		</portlet:renderURL>

		<liferay-ui:search-container-row-parameter
			name="modelResource"
			value="<%= modelResource %>"
		/>

		<liferay-ui:search-container-column-text
			buffer="buffer"
			href="<%= rowURL %>"
			name="resource"
		>

			<%
			buffer.append("<img align=\"left\" border=\"0\" src=\"");
			buffer.append(themeDisplay.getPathThemeImages());
			buffer.append(_getIconPath(modelResource));
			buffer.append("\" style=\"margin-right: 5px\">");
			buffer.append("<strong>");
			buffer.append(LanguageUtil.get(pageContext, "model.resource." + modelResource));
			buffer.append("</strong>");
			%>

		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			buffer="buffer"
			href="<%= rowURL %>"
			name="custom-attributes"
		>

			<%
			ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(modelResource);

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

private String _getIconPath(String modelResource) {
	if (modelResource.equals(BlogsEntry.class.getName())) {
		return "/common/page.png";
	}
	else if (modelResource.equals(BookmarksEntry.class.getName())) {
		return "/ratings/star_hover.png";
	}
	else if (modelResource.equals(BookmarksFolder.class.getName())) {
		return "/common/folder.png";
	}
	else if (modelResource.equals(CalEvent.class.getName())) {
		return "/common/date.png";
	}
	else if (modelResource.equals(DLFileEntry.class.getName())) {
		return "/document_library/page.png";
	}
	else if (modelResource.equals(DLFolder.class.getName())) {
		return "/common/folder.png";
	}
	else if (modelResource.equals(IGFolder.class.getName())) {
		return "/common/folder.png";
	}
	else if (modelResource.equals(IGImage.class.getName())) {
		return "/document_library/bmp.png";
	}
	else if (modelResource.equals(JournalArticle.class.getName())) {
		return "/common/history.png";
	}
	else if (modelResource.equals(Layout.class.getName())) {
		return "/common/page.png";
	}
	else if (modelResource.equals(MBCategory.class.getName())) {
		return "/common/folder.png";
	}
	else if (modelResource.equals(MBMessage.class.getName())) {
		return "/common/conversation.png";
	}
	else if (modelResource.equals(Organization.class.getName())) {
		return "/common/organization_icon.png";
	}
	else if (modelResource.equals(User.class.getName())) {
		return "/common/user_icon.png";
	}
	else if (modelResource.equals(WikiPage.class.getName())) {
		return "/common/pages.png";
	}
	else {
		return "/common/page.png";
	}
}
%>