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

<%@ include file="/html/taglib/ui/asset_categories_navigation/init.jsp" %>

<%
boolean hidePortletWhenEmpty = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:asset-tags-navigation:hidePortletWhenEmpty"));
long[] vocabularyIds = (long[])request.getAttribute("liferay-ui:asset-tags-navigation:vocabularyIds");

long categoryId = ParamUtil.getLong(request, "categoryId");

List<AssetVocabulary> vocabularies = null;

if (vocabularyIds == null) {
	vocabularies = AssetVocabularyServiceUtil.getGroupVocabularies(scopeGroupId);
}
else {
	vocabularies = new ArrayList<AssetVocabulary>();

	for (long vocabularyId : vocabularyIds) {
		try {
			vocabularies.add(AssetVocabularyServiceUtil.getVocabulary(vocabularyId));
		}
		catch (NoSuchVocabularyException nsve) {
		}
	}
}

PortletURL portletURL = renderResponse.createRenderURL();
%>

<liferay-ui:panel-container cssClass="taglib-asset-categories-navigation" extended="<%= true %>" id='<%= namespace + "taglibAssetCategoriesNavigation" %>' persistState="<%= true %>">

	<%
	for (int i = 0; i < vocabularies.size(); i++) {
		AssetVocabulary vocabulary = vocabularies.get(i);

		String vocabularyNavigation = _buildVocabularyNavigation(vocabulary, categoryId, portletURL);

		if (Validator.isNotNull(vocabularyNavigation)) {
			hidePortletWhenEmpty = false;
	%>

			<liferay-ui:panel collapsible="<%= false %>" extended="<%= true %>" id='<%= namespace + "taglibAssetCategoriesNavigation" + i %>' persistState="<%= true %>" title="<%= vocabulary.getName() %>">
				<%= vocabularyNavigation %>
			</liferay-ui:panel>

	<%
		}
	}
	%>

</liferay-ui:panel-container>

<%
if (hidePortletWhenEmpty) {
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
%>

	<div class="portlet-msg-info">
		<liferay-ui:message key="there-are-no-categories" />
	</div>

<%
}
%>

<script type="text/javascript">
	AUI().ready(
		'tree-view',
		function(A) {
			var treeViews = A.all('#<%= namespace %>taglibAssetCategoriesNavigation .lfr-asset-category-list-container');

			treeViews.each(
				function(treeEl) {
					new A.TreeView(
						{
							boundingBox: treeEl,
							contentBox: treeEl.one('.lfr-asset-category-list'),
							type: 'normal'
						}
					)
					.render();
				}
			);
		}
	);
</script>

<%!
private void _buildCategoriesNavigation(List<AssetCategory> categories, long curCategoryId, PortletURL portletURL, StringBuilder sb) throws Exception {
	for (AssetCategory category : categories) {
		long categoryId = category.getCategoryId();
		String name = category.getName();

		List<AssetCategory> categoriesChildren = AssetCategoryServiceUtil.getChildCategories(category.getCategoryId());

		sb.append("<li><span>");

		if (categoryId == curCategoryId) {
			sb.append("<strong>");
			sb.append(name);
			sb.append("</strong>");
		}
		else {
			portletURL.setParameter("categoryId", String.valueOf(categoryId));

			sb.append("<a href=\"");
			sb.append(portletURL.toString());
			sb.append("\">");
			sb.append(name);
			sb.append("</a>");
		}

		sb.append("</span>");

		if (!categoriesChildren.isEmpty()) {
			sb.append("<ul>");

			_buildCategoriesNavigation(categoriesChildren, curCategoryId, portletURL, sb);

			sb.append("</ul>");
		}

		sb.append("</li>");
	}
}

private String _buildVocabularyNavigation(AssetVocabulary vocabulary, long categoryId, PortletURL portletURL) throws Exception {
	List<AssetCategory> categories = AssetCategoryServiceUtil.getVocabularyRootCategories(vocabulary.getVocabularyId());

	if (categories.isEmpty()) {
		return null;
	}

	StringBuilder sb = new StringBuilder();

	sb.append("<div class=\"lfr-asset-category-list-container\"><ul class=\"lfr-asset-category-list\">");

	_buildCategoriesNavigation(categories, categoryId, portletURL, sb);

	sb.append("</ul></div>");

	return sb.toString();
}
%>