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

<%@ include file="/html/taglib/ui/asset_categories_selector/init.jsp" %>

<%
themeDisplay.setIncludeServiceJs(true);

String randomNamespace = DeterminateKeyGenerator.generate("taglib_ui_asset_categories_selector_page") + StringPool.UNDERLINE;

String className = (String)request.getAttribute("liferay-ui:asset-categories-selector:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:asset-categories-selector:classPK"));
String hiddenInput = (String)request.getAttribute("liferay-ui:asset-categories-selector:hiddenInput");
String curCategoryIds = GetterUtil.getString((String)request.getAttribute("liferay-ui:asset-categories-selector:curCategoryIds"), "");
String curCategoryNames = StringPool.BLANK;

if (Validator.isNotNull(className) && (classPK > 0)) {
	List<AssetCategory> categories = AssetCategoryServiceUtil.getCategories(className, classPK);

	curCategoryIds = ListUtil.toString(categories, "categoryId");
	curCategoryNames = ListUtil.toString(categories, "name");
}

String curCategoryIdsParam = request.getParameter(hiddenInput);

if (curCategoryIdsParam != null) {
	curCategoryIds = curCategoryIdsParam;
}

if (Validator.isNotNull(curCategoryIds)) {
	StringBuilder sb = new StringBuilder();

	long[] curCategoryIdsArray = GetterUtil.getLongValues(StringUtil.split(curCategoryIds));

	for (long curCategoryId : curCategoryIdsArray) {
		AssetCategory category = AssetCategoryServiceUtil.getCategory(curCategoryId);

		sb.append(category.getName());
		sb.append(StringPool.COMMA);
	}

	curCategoryNames = sb.substring(0, Math.max(0, sb.length() - 1));
}
%>

<div class="lfr-tags-selector-content" id="<%= namespace + randomNamespace %>assetCategoriesSelector">
	<aui:input name="<%= hiddenInput %>" type="hidden" />
</div>

<script type="text/javascript">
	AUI().ready(
		'liferay-categories-selector',
		function() {
			new Liferay.AssetCategoriesSelector(
				{
					contentBox: '#<%= namespace + randomNamespace %>assetCategoriesSelector',
					curEntries: '<%= curCategoryNames %>',
					curEntryIds: '<%= curCategoryIds %>',
					hiddenInput: '#<%= namespace + hiddenInput %>',
					instanceVar: '<%= namespace + randomNamespace %>'
				}
			).render();
		}
	);
</script>