<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/ui/asset_categories_selector/init.jsp" %>

<%
themeDisplay.setIncludeServiceJs(true);

String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_asset_categories_selector_page") + StringPool.UNDERLINE;

String className = (String)request.getAttribute("liferay-ui:asset-categories-selector:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:asset-categories-selector:classPK"));
String hiddenInput = (String)request.getAttribute("liferay-ui:asset-categories-selector:hiddenInput");
String curCategoryIds = GetterUtil.getString((String)request.getAttribute("liferay-ui:asset-categories-selector:curCategoryIds"), "");
String curCategoryNames = StringPool.BLANK;

long classNameId = PortalUtil.getClassNameId(className);

List<AssetVocabulary> groupVocabularies = AssetVocabularyLocalServiceUtil.getGroupVocabularies(scopeGroupId, false);

for (AssetVocabulary vocabulary : groupVocabularies) {
	UnicodeProperties settingsProperties = vocabulary.getSettingsProperties();

	long[] selectedClassNameIds = StringUtil.split(settingsProperties.getProperty("selectedClassNameIds"), 0L);

	if (selectedClassNameIds.length == 0) {
		continue;
	}

	if ((selectedClassNameIds[0] != AssetCategoryConstants.ALL_CLASS_NAME_IDS) && !ArrayUtil.contains(selectedClassNameIds, classNameId)) {
		continue;
	}
%>

	<span class="aui-field-content">
		<label class="aui-field-label" id="<%= namespace %>assetCategoriesLabel_<%= vocabulary.getVocabularyId() %>">

			<%= vocabulary.getTitle(locale) %>

			<c:if test="<%= vocabulary.isRequired(classNameId) %>">
				<span class="aui-label-required">(<liferay-ui:message key="required" />)</span>
			</c:if>
		</label>

		<div class="lfr-tags-selector-content" id="<%= namespace + randomNamespace %>assetCategoriesSelector_<%= vocabulary.getVocabularyId() %>">
			<aui:input name="<%= hiddenInput + StringPool.UNDERLINE + vocabulary.getVocabularyId() %>" type="hidden" />
		</div>
	</span>

<%
	if (Validator.isNotNull(className) && (classPK > 0)) {
		List<AssetCategory> categories = AssetCategoryServiceUtil.getCategories(className, classPK);

		curCategoryIds = ListUtil.toString(categories, "categoryId");
		curCategoryNames = ListUtil.toString(categories, "name");
	}

	String curCategoryIdsParam = request.getParameter(hiddenInput + StringPool.UNDERLINE + vocabulary.getVocabularyId());

	if (curCategoryIdsParam != null) {
		curCategoryIds = curCategoryIdsParam;
		curCategoryNames = StringPool.BLANK;
	}

	if (Validator.isNotNull(curCategoryIds)) {
		long[] curCategoryIdsArray = GetterUtil.getLongValues(StringUtil.split(curCategoryIds));

		curCategoryIdsArray = _filterCategoryIds(vocabulary.getVocabularyId(), curCategoryIdsArray);

		if (curCategoryIdsArray.length == 0) {
			curCategoryIds = StringPool.BLANK;
			curCategoryNames = StringPool.BLANK;
		}
		else {
			StringBundler sb = new StringBundler(curCategoryIdsArray.length * 2);
			for (long curCategoryId : curCategoryIdsArray) {
				AssetCategory category = AssetCategoryServiceUtil.getCategory(curCategoryId);
				
				category = category.toEscapedModel();
				
				sb.append(category.getName());
				sb.append(StringPool.COMMA);
			}

			sb.setIndex(sb.index() - 1);

			curCategoryIds = StringUtil.merge(curCategoryIdsArray);
			curCategoryNames = sb.toString();
		}
	}
%>

	<aui:script use="liferay-asset-categories-selector">
		new Liferay.AssetCategoriesSelector(
			{
				className: '<%= className %>',
				contentBox: '#<%= namespace + randomNamespace %>assetCategoriesSelector_<%= vocabulary.getVocabularyId() %>',
				curEntries: '<%= HtmlUtil.escapeJS(curCategoryNames) %>',
				curEntryIds: '<%= curCategoryIds %>',
				hiddenInput: '#<%= namespace + hiddenInput + StringPool.UNDERLINE + vocabulary.getVocabularyId() %>',
				instanceVar: '<%= namespace + randomNamespace %>',
				labelNode: '#<%= namespace %>assetCategoriesLabel_<%= vocabulary.getVocabularyId() %>',
				portalModelResource: <%= Validator.isNotNull(className) && (ResourceActionsUtil.isPortalModelResource(className) || className.equals(Group.class.getName())) %>,
				vocabularyIds: '<%= String.valueOf(vocabulary.getVocabularyId()) %>',
			}
		).render();
	</aui:script>

<%
}
%>

<%!
private long[] _filterCategoryIds(long vocabularyId, long[] categoryIds) throws PortalException, SystemException {
	List<Long> filteredCategoryIds = new ArrayList<Long>();

	for (long categoryId : categoryIds) {
		AssetCategory category = AssetCategoryServiceUtil.getCategory(categoryId);

		if (category.getVocabularyId() == vocabularyId) {
			filteredCategoryIds.add(category.getCategoryId());
		}
	}

	return ArrayUtil.toArray(filteredCategoryIds.toArray(new Long[filteredCategoryIds.size()]));
}
%>