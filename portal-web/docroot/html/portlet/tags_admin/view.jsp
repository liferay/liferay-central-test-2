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

<table class="vocabulary-container">
<tr>
	<td colspan="3">
		<div id="vocabulary-search-bar">
			<input id="vocabulary-search-input" type="text" value="" />

			<select class="vocabulary-select-search" id="vocabulary-select-search">
				<option value="vocabularies"><liferay-ui:message key="sets" /></option>
				<option value="entries"><liferay-ui:message key="entries" /></option>
			</select>

			<input id="vocabulary-search-button" type="button" value="<liferay-ui:message key="search" />" />
		</div>
	</td>
</tr>
<tr>
	<td colspan="3">
		<div class="vocabulary-toolbar">
			<div class="vocabulary-buttons">
				<span class="button selected tags-sets"><liferay-ui:message key="tag-sets" /></span>

				<span class="button categories"><liferay-ui:message key="categories" /></span>
			</div>

			<div class="vocabulary-actions">
				<strong class="vocabulary-label"><liferay-ui:message key="add-tag" /></strong>

				<input class="vocabulary-entry-name" name="vocabulary-entry-name" type="text" value="" />

				<select class="vocabulary-select-list" name="vocabulary-select-list">
					<option value="new">(new)</option>
				</select>

				<input class="vocabulary-name" name="vocabulary-name" type="text" value="" />

				<input class="vocabulary-save-entry" type="button" value="<liferay-ui:message key="save" />" />
			</div>
		</div>
	</td>
</tr>
<tr class="vocabulary-content">
	<td class="vocabulary-list-container">
		<div class="results-header"><liferay-ui:message key="sets" /></div>
		<div class="vocabulary-list lfr-component"></div>
	</td>
	<td class="vocabulary-entries-container">
		<div class="results-header"><liferay-ui:message key="entries" /></div>
		<div class="lfr-message-response" id="vocabulary-entry-messages" style="display: none;"></div>

		<div class="vocabulary-entries lfr-component"></div>
	</td>
	<td class="vocabulary-edit-entry">
		<div class="results-header"><liferay-ui:message key="edit-entry" /></div>
		<div class="vocabulary-edit">
			<div class="vocabulary-close"><span><liferay-ui:icon image="close" /></span></div>

			<div class="vocabulary-label"><liferay-ui:message key="tag-name" />:</div>

			<input class="entry-name" name="entry-name" type="text" />

			<br /><br />

			<div class="vocabulary-properties">
				<liferay-ui:message key="properties" />:

				<div class="vocabulary-property-row">
					<input class="property-key" type="text" />

					<input class="property-value" type="text" />

					<span class="add-property"><liferay-ui:icon image="add" /></span>

					<span class="delete-property"><liferay-ui:icon image="delete" /></span>
				</div>

				<br />

				<input class="vocabulary-save-properties" type="button" value="<liferay-ui:message key="save" />" />

				<input class="vocabulary-close" type="button" value="<liferay-ui:message key="close" />" />
				
				<input class="vocabulary-delete-entries-button" type="button" value="<liferay-ui:message key="delete" />" />
			</div>
		</div>
	</td>
</tr>
<tr>
	<td colspan="3">
		<div class="vocabulary-footer">
			<input class="vocabulary-delete-list-button" type="button" value="<liferay-ui:message key="delete-vocabulary" />" />
		</div>
	</td>
</tr>
</table>

</form>

<script>
	jQuery(
		function() {
			new Liferay.Portlet.TagsAdmin();
		}
	);
</script>