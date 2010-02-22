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

<%@ include file="/html/taglib/ui/asset_tags_navigation/init.jsp" %>

<%
long classNameId = GetterUtil.getLong((String)request.getAttribute("liferay-ui:asset-tags-navigation:classNameId"));
String displayStyle = (String)request.getAttribute("liferay-ui:asset-tags-navigation:displayStyle");
boolean hidePortletWhenEmpty = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:asset-tags-navigation:hidePortletWhenEmpty"));
boolean showAssetCount = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:asset-tags-navigation:showAssetCount"));
boolean showZeroAssetCount = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:asset-tags-navigation:showZeroAssetCount"));

String tag = ParamUtil.getString(request, "tag");

PortletURL portletURL = renderResponse.createRenderURL();

String tagsNavigation = _buildTagsNavigation(scopeGroupId, tag, portletURL, classNameId, displayStyle, showAssetCount, showZeroAssetCount);

if (Validator.isNotNull(tagsNavigation)) {
%>

	<liferay-ui:panel-container cssClass="taglib-asset-tags-navigation" extended="<%= true %>" persistState="<%= true %>">
		<%= tagsNavigation %>
	</liferay-ui:panel-container>

<%
}
else {
	if (hidePortletWhenEmpty) {
		renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
	}
%>

	<div class="portlet-msg-info">
		<liferay-ui:message key="there-are-no-tags" />
	</div>

<%
}
%>

<%!
private String _buildTagsNavigation(long groupId, String selectedTagName, PortletURL portletURL, long classNameId, String displayStyle, boolean showAssetCount, boolean showZeroAssetCount) throws Exception {
	List<AssetTag> tags = AssetTagServiceUtil.getGroupTags(groupId);

	if (tags.isEmpty()) {
		return null;
	}

	StringBundler sb = new StringBundler();

	sb.append("<ul class=\"");

	if (showAssetCount && displayStyle.equals("cloud")) {
		sb.append("tag-cloud");
	}
	else {
		sb.append("tag-list");
	}

	sb.append("\">");

	int maxCount = 1;
	int minCount = 1;

	if (showAssetCount && displayStyle.equals("cloud")) {
		for (AssetTag tag : tags) {
			int count = tag.getAssetCount();

			if (!showZeroAssetCount && (count == 0)) {
				continue;
			}

			maxCount = Math.max(maxCount, count);
			minCount = Math.min(minCount, count);
		}
	}

	double multiplier = 1;

	if (maxCount != minCount) {
		multiplier = (double)5 / (maxCount - minCount);
	}

	for (AssetTag tag : tags) {
		String tagName = tag.getName();

		int count = tag.getAssetCount();

		if (classNameId > 0) {
			AssetTagStats tagStats = AssetTagStatsLocalServiceUtil.getTagStats(tag.getTagId(), classNameId);

			count = tagStats.getAssetCount();
		}

		int popularity = (int)(1 + ((maxCount - (maxCount - (count - minCount))) * multiplier));

		if (!showZeroAssetCount && (count == 0)) {
			continue;
		}

		sb.append("<li class=\"tag-popularity-");
		sb.append(popularity);
		sb.append("\"><span>");

		if (tagName.equals(selectedTagName)) {
			sb.append("<strong>");
			sb.append(tagName);
			sb.append("</strong>");

			if (showAssetCount) {
				sb.append("<span class=\"tag-asset-count\">");
				sb.append(StringPool.SPACE);
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(count);
				sb.append(StringPool.CLOSE_PARENTHESIS);
				sb.append("</span>");
			}
		}
		else {
			portletURL.setParameter("tag", tag.getName());

			sb.append("<a href=\"");
			sb.append(portletURL.toString());
			sb.append("\">");
			sb.append(tagName);

			if (showAssetCount) {
				sb.append("<span class=\"tag-asset-count\">");
				sb.append(StringPool.SPACE);
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(count);
				sb.append(StringPool.CLOSE_PARENTHESIS);
				sb.append("</span>");
			}

			sb.append("</a></span>");
		}

		sb.append("</span></li>");
	}

	sb.append("</ul><br style=\"clear: both;\" />");

	return sb.toString();
}
%>