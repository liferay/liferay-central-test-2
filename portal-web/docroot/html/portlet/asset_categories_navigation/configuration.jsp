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

<%@ include file="/html/portlet/asset_categories_navigation/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
%>

<script type="text/javascript">
	function <portlet:namespace />saveConfiguration() {
		if (document.<portlet:namespace />fm.<portlet:namespace />assetVocabularyIds) {
			document.<portlet:namespace />fm.<portlet:namespace />assetVocabularyIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentAssetVocabularyIds);
		}

		submitForm(document.<portlet:namespace />fm);
	}

	AUI().ready(
		function () {
			Liferay.Util.toggleSelectBox('<portlet:namespace />allAssetVocabularies', 'false', '<portlet:namespace />assetVocabulariesBoxes');
		}
	);
</script>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "saveConfiguration(); return false;" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<aui:fieldset>
		<liferay-ui:message key="vocabularies" />

		<select name="<portlet:namespace />allAssetVocabularies" id="<portlet:namespace />allAssetVocabularies">
			<option <%= allAssetVocabularies ? "selected" : StringPool.BLANK %> value="<%= true %>"><liferay-ui:message key="all" /></option>
			<option <%= !allAssetVocabularies ? "selected" : StringPool.BLANK %> value="<%= false %>"><liferay-ui:message key="filter[action]" />...</option>
		</select>

		<input name="<portlet:namespace />assetVocabularyIds" type="hidden" value="" />

		<%
		Set<Long> availableAssetVocabularyIdsSet = SetUtil.fromArray(availableAssetVocabularyIds);

		// Left list

		List<KeyValuePair> typesLeftList = new ArrayList<KeyValuePair>();

		for (long vocabularyId : assetVocabularyIds) {
			AssetVocabulary vocabulary = AssetVocabularyLocalServiceUtil.getVocabulary(vocabularyId);

			typesLeftList.add(new KeyValuePair(String.valueOf(vocabularyId), vocabulary.getName()));
		}

		// Right list

		List<KeyValuePair> typesRightList = new ArrayList<KeyValuePair>();

		Arrays.sort(assetVocabularyIds);

		for (long vocabularyId : availableAssetVocabularyIdsSet) {
			if (Arrays.binarySearch(assetVocabularyIds, vocabularyId) < 0) {
				AssetVocabulary vocabulary = AssetVocabularyLocalServiceUtil.getVocabulary(vocabularyId);

				typesRightList.add(new KeyValuePair(String.valueOf(vocabularyId), vocabulary.getName()));
			}
		}

		typesRightList = ListUtil.sort(typesRightList, new KeyValuePairComparator(false, true));
		%>

		<div class="<%= allAssetVocabularies ? "aui-helper-hidden" : "" %>" id="<portlet:namespace />assetVocabulariesBoxes">
			<liferay-ui:input-move-boxes
				formName="fm"
				leftTitle="current"
				rightTitle="available"
				leftBoxName="currentAssetVocabularyIds"
				rightBoxName="availableAssetVocabularyIds"
				leftReorder="true"
				leftList="<%= typesLeftList %>"
				rightList="<%= typesRightList %>"
			/>
		</div>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>