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
String randomNamespace = PwdGenerator.getPassword(PwdGenerator.KEY3, 4) + StringPool.UNDERLINE;

int index = ParamUtil.getInteger(request, "index", GetterUtil.getInteger((String)request.getAttribute("configuration.jsp-index")));
int queryLogicIndex = GetterUtil.getInteger((String)request.getAttribute("configuration.jsp-queryLogicIndex"));

boolean queryContains = true;
boolean queryAndOperator = false;
String queryName = "assetTags";
String queryValues = null;

if (queryLogicIndex > 0) {
	queryContains = PrefsParamUtil.getBoolean(preferences, request, "queryContains" + queryLogicIndex, true);
	queryAndOperator = PrefsParamUtil.getBoolean(preferences, request, "queryAndOperator" + queryLogicIndex);
	queryName = PrefsParamUtil.getString(preferences, request, "queryName" + queryLogicIndex, "assetTags");
	queryValues = StringUtil.merge(preferences.getValues("queryValues" + queryLogicIndex , new String[0]));

	if (Validator.equals(queryName, "assetTags")) {
		queryValues = ParamUtil.getString(request, "queryTagNames" + queryLogicIndex, queryValues);
	}
	else {
		queryValues = ParamUtil.getString(request, "queryCategoryIds" + queryLogicIndex, queryValues);
	}
}
%>

<aui:select inlineField="<%= true %>" label="" name='<%= "queryContains" + index %>'>
	<aui:option label="contains" selected="<%= queryContains %>" value="true" />
	<aui:option label="does-not-contain" selected="<%= !queryContains %>" value="false" />
</aui:select>

<aui:select inlineField="<%= true %>" label="" name='<%= "queryAndOperator" + index %>'>
	<aui:option label="all" selected="<%= queryAndOperator %>" value="true" />
	<aui:option label="any" selected="<%= !queryAndOperator %>" value="false" />
</aui:select>

<aui:select cssClass="asset-query-name" id='<%= randomNamespace + "selector" %>' inlineLabel="left" label="of-the-following" name='<%= "queryName" + index %>'>
	<aui:option label="tags" selected='<%= Validator.equals(queryName, "assetTags") %>' value="assetTags" />
	<aui:option label="categories" selected='<%= Validator.equals(queryName, "assetCategories") %>' value="assetCategories" />
</aui:select>

<div class="aui-ctrl-holder tags-selector <%= Validator.equals(queryName, "assetTags") ? StringPool.BLANK : "aui-helper-hidden" %>">
	<liferay-ui:asset-tags-selector
		hiddenInput='<%= "queryTagNames" + index %>'
		curTags='<%= Validator.equals(queryName, "assetTags") ? queryValues : null %>'
		focus="<%= false %>"
	/>
</div>

<div class="aui-ctrl-holder categories-selector <%= Validator.equals(queryName, "assetCategories") ? StringPool.BLANK : "aui-helper-hidden" %>">
	<liferay-ui:asset-categories-selector
		hiddenInput='<%= "queryCategoryIds" + index %>'
		curCategoryIds='<%= Validator.equals(queryName, "assetCategories") ? queryValues : null %>'
		focus="<%= false %>"
	/>
</div>

<script type="text/javascript">
	AUI().ready(
		function (A) {
			var select = A.one('#<portlet:namespace /><%= randomNamespace %>selector');

			if (select) {
				var row = select.ancestor('.aui-autorow');

				if (row) {
					select.on(
						'change',
						function(event) {
							var tagsSelector = row.one('.tags-selector');
							var categoriesSelector = row.one('.categories-selector');

							if (select.val() == 'assetTags') {
								if (tagsSelector) {
									tagsSelector.show();
								}

								if (categoriesSelector) {
									categoriesSelector.hide();
								}
							}
							else {
								if (tagsSelector) {
									tagsSelector.hide();
								}

								if (categoriesSelector) {
									categoriesSelector.show();
								}
							}
						}
					);
				}
			}
		}
	);
</script>