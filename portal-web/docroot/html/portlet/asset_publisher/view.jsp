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

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
if (mergeUrlTags) {
	String[] compilerTagNames = (String[])request.getAttribute(WebKeys.TAGS_COMPILER_ENTRIES);

	Set<String> layoutTagNames = AssetUtil.getLayoutTagNames(request);

	if (!layoutTagNames.isEmpty()) {
		compilerTagNames = ArrayUtil.append(compilerTagNames, layoutTagNames.toArray(new String[layoutTagNames.size()]));
	}

	String titleEntry = null;

	if ((compilerTagNames != null) && (compilerTagNames.length > 0)) {
		String[] newAssetTagNames = ArrayUtil.append(assetTagNames, compilerTagNames);

		assetTagNames = newAssetTagNames;

		titleEntry = compilerTagNames[compilerTagNames.length - 1];
	}

	String portletTitle = HtmlUtil.unescape(portletDisplay.getTitle());

	portletTitle = AssetUtil.substituteTagPropertyVariables(scopeGroupId, titleEntry, portletTitle);

	renderResponse.setTitle(portletTitle);
}

assetTagNames = ArrayUtil.distinct(assetTagNames, new StringComparator());

for (String curAssetTagName : assetTagNames) {
	try {
		AssetTag assetTag = AssetTagLocalServiceUtil.getTag(scopeGroupId, curAssetTagName);

		AssetTagProperty journalTemplateIdProperty = AssetTagPropertyLocalServiceUtil.getTagProperty(assetTag.getTagId(), "journal-template-id");

		String journalTemplateId = journalTemplateIdProperty.getValue();

		request.setAttribute(WebKeys.JOURNAL_TEMPLATE_ID, journalTemplateId);

		break;
	}
	catch (NoSuchTagException nste) {
	}
	catch (NoSuchTagPropertyException nstpe) {
	}
}

if (enableTagBasedNavigation && selectionStyle.equals("manual") && (assetTagNames.length > 0)) {
	selectionStyle = "dynamic";
}

String portletId = portletDisplay.getId();
%>

<%@ include file="/html/portlet/asset_publisher/add_asset.jspf" %>

<%
if (showQueryLogic) {
	StringBuilder tagsText = new StringBuilder();

	if (assetTagNames.length > 0) {
		tagsText.append("( ");
	}

	for (int i = 0; i < assetTagNames.length; i++) {
		if ((i + 1) == assetTagNames.length) {
			tagsText.append(assetTagNames[i]);
			tagsText.append(" )");
		}
		else {
			tagsText.append(assetTagNames[i]);

			if (andOperator) {
				tagsText.append(" AND ");
			}
			else {
				tagsText.append(" OR ");
			}
		}
	}

	if ((assetTagNames.length > 0) && (notAssetTagNames.length > 0)) {
		tagsText.append(" AND NOT ( ");
	}

	for (int i = 0; i < notAssetTagNames.length; i++) {
		if ((i + 1) == notAssetTagNames.length) {
			tagsText.append(notAssetTagNames[i]);
			tagsText.append(" )");
		}
		else {
			tagsText.append(notAssetTagNames[i]);
			tagsText.append(" OR ");
		}
	}
%>

	<liferay-ui:message key="tags" />:

	<%= tagsText %>

	<div class="separator"><!-- --></div>

<%
}

// Display content

PortletURL portletURL = renderResponse.createRenderURL();

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, delta, portletURL, null, null);
%>

<c:if test="<%= assetCategoryId > 0 %>">
	<h1 class="asset-categorization-title">
		<%= LanguageUtil.format(pageContext, "content-with-x-x", new String[] {assetVocabularyName, assetCategoryName}) %>
	</h1>
</c:if>

<c:if test="<%= Validator.isNotNull(assetTagName) %>">
	<h1 class="asset-categorization-title">
		<%= LanguageUtil.format(pageContext, "content-with-tag-x", assetTagName) %>
	</h1>
</c:if>

<c:choose>
	<c:when test='<%= selectionStyle.equals("dynamic") %>'>
		<%@ include file="/html/portlet/asset_publisher/view_dynamic_list.jspf" %>
	</c:when>
	<c:when test='<%= selectionStyle.equals("manual") %>'>
		<%@ include file="/html/portlet/asset_publisher/view_manual.jspf" %>
	</c:when>
</c:choose>

<c:if test='<%= !paginationType.equals("none") && (searchContainer.getTotal() > searchContainer.getResults().size()) %>'>
	<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" type="<%= paginationType %>" />
</c:if>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.asset_publisher.view.jsp");
%>