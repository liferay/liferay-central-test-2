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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portlet.asset.model.AssetCategory" %>
<%@ page import="com.liferay.portlet.asset.model.AssetCategoryVocabulary" %>
<%@ page import="com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.asset.service.AssetCategoryVocabularyServiceUtil" %>

<%
String className = (String)request.getAttribute("liferay-ui:asset_categories_summary:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:asset_categories_summary:classPK"));
LiferayPortletURL portletURL = (LiferayPortletURL)request.getAttribute("liferay-ui:asset_categories_summary:portletURL");

List<AssetCategoryVocabulary> vocabularies = AssetCategoryVocabularyServiceUtil.getGroupCategoryVocabularies(scopeGroupId);
List<AssetCategory> entries = AssetCategoryLocalServiceUtil.getCategories(className, classPK);
%>

<%
for (AssetCategoryVocabulary vocabulary : vocabularies) {
	String vocabularyName = vocabulary.getName();

	List<AssetCategory> categories = _filterCategories(entries, vocabulary);
%>

	<c:if test="<%= categories.size() > 0 %>">
		<div class="taglib-asset-categories-summary">
			<%= vocabularyName %>:

			<c:choose>
				<c:when test="<%= portletURL != null %>">

					<%
					for (AssetCategory category : categories) {
						portletURL.setParameter("categoryId", String.valueOf(category.getCategoryId()));
					%>

						<a class="asset-category" href="<%= portletURL.toString() %>"><%= _buildCategoryPath(category) %></a>

					<%
					}
					%>

				</c:when>
				<c:otherwise>

					<%
					for (AssetCategory category : categories) {
					%>

						<span class="asset-category">
							<%= _buildCategoryPath(category) %>
						</span>

					<%
					}
					%>

				</c:otherwise>
			</c:choose>
		</div>
	</c:if>
<%
}
%>

<%!
private String _buildCategoryPath(AssetCategory category) throws PortalException, SystemException {
	StringBuilder sb = new StringBuilder();

	for (AssetCategory curCategory : category.getAncestors()) {
		sb.append(curCategory.getName());
		sb.append(" &raquo; ");
	}

	sb.append(category.getName());

	return sb.toString();
}

private List<AssetCategory> _filterCategories(List<AssetCategory> categories, AssetCategoryVocabulary vocabulary) {
	List<AssetCategory> filteredCategories = new ArrayList<AssetCategory>();

	for (AssetCategory category : categories) {
		if (category.getVocabularyId() == vocabulary.getCategoryVocabularyId()) {
			filteredCategories.add(category);
		}
	}

	return filteredCategories;
}
%>