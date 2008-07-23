<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/tags_admin/init.jsp" %>

<form id="<portlet:namespace />fm">

<table width="100%">
<tr>
<td align="right" colspan="3">
	<liferay-ui:message key="tag-value" />

	<input id="<portlet:namespace />addEntryNameInput" type="text" />

	<liferay-ui:message key="vocabulary" />

	<input id="<portlet:namespace />addEntryVocabularyInput" type="text" value="" />

	<input id="<portlet:namespace />addEntryButton" type="button" value="<liferay-ui:message key="add" />" />
</td>
</tr>
<tr>
<td valign="top">
<fieldset id="<portlet:namespace />Vocabularies">
	<legend><liferay-ui:message key="vocabularies" /></legend>

	<div class="ui-tags" id="<portlet:namespace />listVocabulariesDiv" /></div>

	<br />

	<div>
		<b><liferay-ui:message key="new-vocabulary" /></b>
		<br />
		<liferay-ui:message key="name" />
		<input id="<portlet:namespace />addVocabularyNameInput" type="text" />
		<br />
		<liferay-ui:message key="folksonomy" /><input id="<portlet:namespace />addVocabularyFolksonomyCheck" type="checkbox" />
		<br />
		<input id="<portlet:namespace />addVocabularyButton" type="button" value="<liferay-ui:message key="add" />" />
	</div>
</fieldset>
</td>

<td valign="top">
<fieldset id="<portlet:namespace />editVocabularyFields">
	<legend><liferay-ui:message key="edit-vocabulary" /></legend>


	<div class="ui-tags" id="<portlet:namespace />vocabularyTagsDiv" /></div>

	<br /><br />

	<liferay-ui:message key="vocabulary-name" />
	<input id="<portlet:namespace />editVocabularyNameInput" type="text" />

	<br />
	<liferay-ui:message key="folksonomy" /><input id="<portlet:namespace />editVocabularyFolksonomyCheck" type="checkbox" value="" disabled />
	<br /><br />
	<input id="<portlet:namespace />updateVocabularyButton" type="button" value="<liferay-ui:message key="save" />" />

	<input id="<portlet:namespace />deleteVocabularyButton" type="button" value="<liferay-ui:message key="delete" />" />
</fieldset>
</td>

<!--
<fieldset id="<portlet:namespace />searchEntriesFields">
	<legend><liferay-ui:message key="search-tag" /></legend>

	<liferay-ui:message key="enter-text-below-to-refine-the-list-of-tags" />

	<br /><br />

	<input id="<portlet:namespace />keywordsInput" type="text" size="40" />

	<span id="<portlet:namespace />searchPropertiesSpan" style="padding-left: 10px;"></span>

	<br /><br />

	<liferay-ui:message key="click-on-any-tag-to-edit-it" />

	<div class="ui-tags" id="<portlet:namespace />searchResultsDiv" /></div>
</fieldset>
 -->
<td valign="top">
<fieldset id="<portlet:namespace />editEntryFields">
	<legend><liferay-ui:message key="edit-tag" /></legend>

	<liferay-ui:message key="tag-value" />

	<input id="<portlet:namespace />editEntryNameInput" type="text" value="" />

	<%--<input id="<portlet:namespace />updateEntryButton" type="button" value="<liferay-ui:message key="copy" />" />--%>

	<br />

	<liferay-ui:message key="tag-vocabulary" />

	<input id="<portlet:namespace />editEntryVocabularyInput" type="text" value="" />

	<br />

	<div id="<portlet:namespace />editEntryParentDiv" style="padding-left: 10px;">
		<liferay-ui:message key="tag-parent" />

		<input id="<portlet:namespace />editEntryParentInput" type="text" value="" />

		<br /><br />
	</div>

	<liferay-ui:message key="properties" />

	<table border="0" cellpadding="0" cellspacing="0" id="<portlet:namespace />propertiesTable"></table>

	<input id="<portlet:namespace />addPropertyButton" type="button" value="<liferay-ui:message key="add-property" />" />

	<input id="<portlet:namespace />updateEntryButton" type="button" value="<liferay-ui:message key="save" />" />

	<input id="<portlet:namespace />deleteEntryButton" type="button" value="<liferay-ui:message key="delete" />" />

	<input id="<portlet:namespace />cancelEditEntryButton" type="button" value="<liferay-ui:message key="cancel" />" />
</fieldset>
</td>
</tr>
</table>
</form>

<script type="text/javascript">
	var <portlet:namespace />tagsAdmin = new Liferay.Portlet.TagsAdmin(
		{
			instanceVar: "<portlet:namespace />tagsAdmin",
			addCategoryNameInput: "<portlet:namespace />addCategoryNameInput",
			addEntryButton: "<portlet:namespace />addEntryButton",
			addEntryNameInput: "<portlet:namespace />addEntryNameInput",
			addEntryVocabularyInput: "<portlet:namespace />addEntryVocabularyInput",
			addPropertyButton: "<portlet:namespace />addPropertyButton",
			addToCategorySpan: "<portlet:namespace />addToCategorySpan",
			addVocabularyButton: "<portlet:namespace />addVocabularyButton",
			addVocabularyFolksonomyCheck: "<portlet:namespace />addVocabularyFolksonomyCheck",
			addVocabularyNameInput: "<portlet:namespace />addVocabularyNameInput",
			cancelEditEntryButton: "<portlet:namespace />cancelEditEntryButton",
			cancelEditVocabularyButton: "<portlet:namespace />cancelEditVocabularyButton",
			deleteEntryButton: "<portlet:namespace />deleteEntryButton",
			deleteVocabularyButton: "<portlet:namespace />deleteVocabularyButton",
			editEntryFields: "<portlet:namespace />editEntryFields",
			editEntryNameInput: "<portlet:namespace />editEntryNameInput",
			editEntryParentInput: "<portlet:namespace />editEntryParentInput",
			editEntryParentDiv: "<portlet:namespace />editEntryParentDiv",
			editEntryVocabularyInput: "<portlet:namespace />editEntryVocabularyInput",
			editVocabularyFields: "<portlet:namespace />editVocabularyFields",
			editVocabularyFolksonomyCheck: "<portlet:namespace />editVocabularyFolksonomyCheck",
			editVocabularyNameInput: "<portlet:namespace />editVocabularyNameInput",
			form: "<portlet:namespace />fm",
			keywordsInput: "<portlet:namespace />keywordsInput",
			listVocabulariesDiv: "<portlet:namespace />listVocabulariesDiv",
			propertiesTable: "<portlet:namespace />propertiesTable",
			searchPropertiesSpan: "<portlet:namespace />searchPropertiesSpan",
			searchResultsDiv: "<portlet:namespace />searchResultsDiv",
			updateEntryButton: "<portlet:namespace />updateEntryButton",
			updateVocabularyButton: "<portlet:namespace />updateVocabularyButton",
			vocabularyTagsDiv: "<portlet:namespace />vocabularyTagsDiv"
		}
	);
</script>