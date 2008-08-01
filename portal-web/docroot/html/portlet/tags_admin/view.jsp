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

<div id="ui-tags-messages" class="lfr-message-response" style="display: none;"></div>

<div class="ui-tags">

	<table class="ui-tags-container">

		<tr>
			<td colspan="3">
				<div class="ui-tags-search-bar">
					<input id="<portlet:namespace />searchInput" type="text" value="" size="50" />
					<input id="<portlet:namespace />searchButton" type="button" value="<liferay-ui:message key="search" />" />
				</div>
			</td>
		</tr>

		<tr class="ui-tags-toolbar">
			<td>
				<div class="ui-tags-buttons">
					<span class="button selected tags-sets">Tags sets</span>
					<span class="button categories">Categories</span>
				</div>
			</td>
			<td colspan="2">
				<div class="ui-tags-actions">
					<b class="ui-tags-label">Add tag</b>
					<input name="ui-tags-entry-name" class="ui-tags-entry-name" type="text" value="" />
					<select name="ui-tags-select-list" class="ui-tags-select-list">
						<option value="new">(new)</option>
					</select>
					<input name="ui-tags-vocabulary-name" class="ui-tags-vocabulary-name" type="text" value="" />
					<input class="ui-tags-save-entry" type="button" value="<liferay-ui:message key="save" />" />
				</div>
			</td>
		</tr>

		<tr class="ui-tags-content">
			<td>
				<div class="ui-tags-vocabulary-list"></div>
			</td>
			<td>
				<div id="ui-tags-entry-messages" class="lfr-message-response" style="display: none;"></div>
				<div class="ui-tags-vocabulary-entries"></div>
			</td>
			<td>
				<div class="ui-tags-vocabulary-edit">
					<div class="ui-tags-close"><span><liferay-ui:icon image="close" /></span></div>
					<div class="ui-tags-label">Tag name:</div>
					<input name="entry-name" class="entry-name" type="text" size="40" />
					<input class="ui-tags-delete-entries-button" type="button" value="<liferay-ui:message key="delete" />" />
					<br /><br />
					<div class="ui-tags-properties">
						Properties:
						<div class="ui-tags-property-line">
							<input class="property-key" type="text" />
							<input class="property-value" type="text" />
							<liferay-ui:icon image="add" />
							<liferay-ui:icon image="delete" />
						</div>
						<br />
						<input class="ui-tags-save-properties" type="button" value="<liferay-ui:message key="save" />" />
						<input class="ui-tags-close" type="button" value="<liferay-ui:message key="close" />" />
					</div>
				</div>
			</td>
		</tr>

		<tr>
			<td colspan="3">
				<div class="ui-tags-footer">
					<input class="ui-tags-delete-list-button" type="button" value="<liferay-ui:message key="delete" /> vocabulary" />
				</div>
			</td>
		</tr>

	</table>
</div>
</form>

<script>
jQuery(function() {
	var <portlet:namespace />tagsAdmin = new Liferay.Portlet.TagsAdmin({

	});
});
</script>