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

<%@ include file="/html/portlet/tags_admin/init.jsp" %>

<form id="<portlet:namespace />fm">

<table class="vocabulary-container">
<tr>
	<td colspan="3">
		<div id="vocabulary-search-bar">
			<input id="vocabulary-search-input" type="text" value="" />

			<select class="vocabulary-select-search" id="vocabulary-select-search">
				<option value="vocabularies"><liferay-ui:message key="tag-sets" /></option>
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
				<c:if test="<%= TagsPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_VOCABULARY) %>">
					<input class="add-vocabulary-btn" id="add-vocabulary-btn" name="add-vocabulary-btn" type="button"  value="<liferay-ui:message key="add-tag-set" />">
				</c:if>

				<c:if test="<%= TagsPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_ENTRY) %>">
					<input class="add-entry-btn" id="add-entry-btn" name="add-entry-btn" type="button"  value="<liferay-ui:message key="add-tag" />">
				</c:if>

				<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS) %>">
					<liferay-security:permissionsURL
						modelResource="com.liferay.portlet.tags"
						modelResourceDescription="<%= HtmlUtil.escape(themeDisplay.getScopeGroupName()) %>"
						resourcePrimKey="<%= String.valueOf(scopeGroupId) %>"
						var="permissionsURL"
					/>

					<input type="button" value="<liferay-ui:message key="permissions" />" onClick="location.href = '<%= permissionsURL %>';" />
				</c:if>
				<div class="exp-overlay entry-toolbar-section">
					<div class="exp-header">
						<span class="knob"></span>
					</div>
					<div class="panel-content">
						<div class="exp-ctrl-holder">
							<label class="vocabulary-label" for="vocabulary-entry-name">
								<liferay-ui:message key="name" />
							</label>

							<input class="vocabulary-entry-name" name="vocabulary-entry-name" type="text" value="" />
						</div>

						<div class="exp-ctrl-holder">
							<label for="vocabulary-select-list">
								<liferay-ui:message key="to-vocabulary" />
							</label>

							<select class="vocabulary-select-list" name="vocabulary-select-list"></select>
						</div>

						<div class="entry-permissions-actions">
							<liferay-ui:input-permissions
								modelName="<%= TagsEntry.class.getName() %>"
							/>
						</div>

						<div class="exp-button-holder">
							<input class="entry-save-button" type="button" value="<liferay-ui:message key="save" />" />

							<input class="close-panel" type="button" value="<liferay-ui:message key="close" />" />
						</div>
					</div>
				</div>

				<div class="exp-overlay vocabulary-toolbar-section">
					<div class="exp-header">
						<span class="knob"></span>
					</div>
					<div class="panel-content">
						<div class="exp-ctrl-holder">
							<label for="vocabulary-name">
								<liferay-ui:message key="add-tag-set" />
							</label>

							<input class="vocabulary-name" name="vocabulary-name" type="text" value="" />
						</div>

						<div class="vocabulary-permissions-actions">
							<liferay-ui:input-permissions
								modelName="<%= TagsVocabulary.class.getName() %>"
							/>
						</div>

						<div class="exp-button-holder">
							<input class="vocabulary-save-button" type="button" value="<liferay-ui:message key="save" />" />

							<input class="close-panel" type="button" value="<liferay-ui:message key="close" />" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</td>
</tr>
<tr class="vocabulary-content">
	<td class="vocabulary-list-container">
		<div class="results-header"><liferay-ui:message key="tag-sets" /></div>
		<div class="vocabulary-list lfr-component"></div>
	</td>
	<td class="vocabulary-entries-container">
		<div class="results-header">
			<liferay-ui:message key="entries" />
		</div>

		<div class="lfr-message-response" id="vocabulary-entry-messages" style="display: none;"></div>

		<div class="vocabulary-entries lfr-component"></div>
	</td>
	<td class="vocabulary-edit-entry">
		<div class="results-header"><liferay-ui:message key="edit-entry" /></div>
		<div class="vocabulary-edit">
			<div class="vocabulary-close">
				<span>
					<liferay-ui:icon image="close" />
				</span>
			</div>

			<div class="vocabulary-label">
				<liferay-ui:message key="name" />:
			</div>

			<input class="entry-name" name="entry-name" type="text" />

			<br /><br />

			<div class="vocabulary-properties">
				<liferay-ui:message key="properties" />:

				<liferay-ui:icon-help message="properties-are-a-way-to-add-more-detailed-information-to-a-specific-tag-or-category" />

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

				<input class="permissions-entries-btn" type="button" value="<liferay-ui:message key="edit-tag-permissions" />" />
			</div>
		</div>
	</td>
</tr>
<tr>
	<td colspan="3">
		<div class="vocabulary-footer">
			<input class="vocabulary-delete-list-button" type="button" value="<liferay-ui:message key="delete-tag-set" />" />

			<input class="permissions-vocabulary-btn" type="button" value="<liferay-ui:message key="edit-tag-set-permissions" />" />
		</div>
	</td>
</tr>
</table>

</form>

<script>
	jQuery(
		function() {
			new Liferay.Portlet.TagsAdmin('<%= portletDisplay.getId() %>');
		}
	);
</script>