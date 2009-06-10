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

<%@ include file="/html/taglib/ui/asset_tags_navigation/init.jsp" %>

<%
String tag = ParamUtil.getString(renderRequest, "tag");

PortletURL portletURL = renderResponse.createRenderURL();
%>

<liferay-ui:panel-container id='<%= namespace + "taglibAssetTagsNavigation" %>' extended="<%= Boolean.TRUE %>" persistState="<%= true %>" cssClass="taglib-asset-tags-navigation">

	<%= _buildTagsNavigation(scopeGroupId, tag, portletURL) %>

</liferay-ui:panel-container>

<%!
private String _buildTagsNavigation(long groupId, String selectedTagName, PortletURL portletURL) throws Exception {
	StringBuilder sb = new StringBuilder();

	sb.append("<ul class=\"tag-cloud\">");

	List<AssetTag> tags = AssetTagServiceUtil.getGroupTags(groupId);

	for (AssetTag entry : tags) {
		String entryName = entry.getName();

		sb.append("<li>");
		sb.append("<span>");

		if (entryName.equals(selectedTagName)) {
			sb.append("<b>");
			sb.append(entryName);
			sb.append("</b>");
		}
		else {
			portletURL.setParameter("tag", entry.getName());

			sb.append("<a href=\"");
			sb.append(portletURL.toString());
			sb.append("\">");
			sb.append(entryName);
			sb.append("</a>");
		}

		sb.append("</span>");

		sb.append("</li>");
	}

	sb.append("</ul>");
	sb.append("<br style=\"clear: both;\" />");

	return sb.toString();
}
%>