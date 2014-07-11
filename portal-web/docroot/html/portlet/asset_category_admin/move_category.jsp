<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/html/portlet/asset_category_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long categoryId = ParamUtil.getLong(request, "categoryId");

long vocabularyId = ParamUtil.getLong(request, "vocabularyId");

AssetCategory category = AssetCategoryLocalServiceUtil.fetchCategory(categoryId);

AssetCategory parentCategory = category.getParentCategory();

List<AssetVocabulary> vocabularies = AssetVocabularyServiceUtil.getGroupVocabularies(scopeGroupId);
%>

<liferay-ui:header
	title='<%= LanguageUtil.format(request, "move-x", category.getTitle(locale)) %>'
/>

<portlet:actionURL var="editCategoryURL">
	<portlet:param name="struts_action" value="/asset_category_admin/edit_category" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</portlet:actionURL>

<aui:form action="<%= editCategoryURL %>" name="fm" onSubmit="event.preventDefault();">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.MOVE %>" />
	<aui:input name="categoryId" type="hidden" value="<%= categoryId %>" />

	<aui:select label="to-vocabulary" name="vocabularyId">

		<%
		for (AssetVocabulary vocabulary : vocabularies) {
		%>

			<aui:option label="<%= HtmlUtil.escape(vocabulary.getTitle(locale)) %>" selected="<%= vocabulary.getVocabularyId() == vocabularyId %>" value="<%= vocabulary.getVocabularyId() %>" />

		<%
		}
		%>

	</aui:select>

	<span><liferay-ui:message key="and-to-category" /></span>

	<div class="lfr-tags-selector-content">
		<aui:input name="parentCategoryId" type="hidden" />

		<%
		for (AssetVocabulary curVocabulary : vocabularies) {
		%>

			<div class="<%= (curVocabulary.getVocabularyId() == vocabularyId) ? StringPool.BLANK : "hide" %> asset-category-selector" id="<portlet:namespace />assetCategoriesSelector<%= curVocabulary.getVocabularyId() %>"></div>

		<%
		}
		%>

	</div>

	<aui:button-row>
		<aui:button type="submit" value="move" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script use="liferay-asset-categories-selector">
	var assetCategoriesSelector;

	<%
	for (AssetVocabulary curVocabulary : vocabularies) {
	%>

		assetCategoriesSelector = new Liferay.AssetCategoriesSelector(
			{
				contentBox: '#<portlet:namespace />assetCategoriesSelector<%= curVocabulary.getVocabularyId() %>',

				<c:if test="<%= ((curVocabulary.getVocabularyId() == vocabularyId) && (parentCategory != null)) %>">
					curEntries: '<%= parentCategory.getTitle(locale) %>',
					curEntryIds: '<%= parentCategory.getCategoryId() %>',
				</c:if>

				hiddenInput: '#<portlet:namespace />parentCategoryId',
				singleSelect: <%= true %>,
				instanceVar: '<portlet:namespace />',
				label: '<%= UnicodeLanguageUtil.format(request, "select-x", curVocabulary.getTitle(locale), false) %>',
				vocabularyGroupIds: '<%= scopeGroupId %>',
				vocabularyIds: '<%= curVocabulary.getVocabularyId() %>'
			}
		).render();

	<%
	}
	%>

	var parentCategoryId = A.one('#<portlet:namespace />parentCategoryId');

	var previousSelector = A.one('#<portlet:namespace />assetCategoriesSelector<%= vocabularyId %>');

	A.one('#<portlet:namespace />vocabularyId').on(
		'change',
		function(event) {
			A.all('.asset-category-selector').hide();

			previousSelector.attr('data-selectedId', parentCategoryId.val());

			previousSelector = A.one('#<portlet:namespace />assetCategoriesSelector' + event.currentTarget.val());

			previousSelector.show();

			parentCategoryId.val(previousSelector.attr('data-selectedId') || '');
		}
	);

	A.one('#<portlet:namespace />fm').on(
		'submit',
		function(event) {
			if (parentCategoryId.val() != document.<portlet:namespace />fm.<portlet:namespace />categoryId.value) {
				submitForm(document.<portlet:namespace />fm);
			}
			else {
				alert('<liferay-ui:message key="the-category-x-and-parent-category-should-not-be-the-same" arguments="<%= category.getTitle(locale) %>" />');
			}
		}
	);
</aui:script>