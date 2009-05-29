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

<%@ include file="/html/portlet/asset_category_admin/init.jsp" %>

<form id="<portlet:namespace />fm">

<table class="category-vocabulary-container">
<tr>
	<td colspan="3">
		<div class="category-vocabulary-toolbar">
			<span id="category-vocabulary-search-bar">
				<input id="category-vocabulary-search-input" type="text" value="" />

				<select class="category-vocabulary-select-search" id="category-vocabulary-select-search">
					<option value="categories" selected><liferay-ui:message key="categories" /></option>
					<option value="vocabularies"><liferay-ui:message key="vocabularies" /></option>
				</select>

				<input id="category-vocabulary-search-button" type="button" value="<liferay-ui:message key="search" />" />
			</span>

			<span class="category-vocabulary-actions">
				<c:if test="<%= AssetPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_CATEGORY_VOCABULARY) %>">
					<input class="add-category-vocabulary-btn" id="add-category-vocabulary-btn" name="add-category-vocabulary-btn" type="button"  value="<liferay-ui:message key="add-vocabulary" />">
				</c:if>

				<c:if test="<%= AssetPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_CATEGORY) %>">
					<input class="add-category-btn" id="add-category-btn" name="add-category-btn" type="button"  value="<liferay-ui:message key="add-category" />">
				</c:if>

				<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS) %>">
					<liferay-security:permissionsURL
						modelResource="com.liferay.portlet.asset"
						modelResourceDescription="<%= themeDisplay.getScopeGroupName() %>"
						resourcePrimKey="<%= String.valueOf(scopeGroupId) %>"
						var="permissionsURL"
					/>

					<input type="button" value="<liferay-ui:message key="permissions" />" onClick="location.href = '<%= permissionsURL %>';" />
				</c:if>

				<div class="exp-overlay category-toolbar-section">
					<div class="exp-header">
						<span class="knob"></span>
					</div>

					<div class="panel-content">
						<div class="ctrl-holder">
							<label class="category-vocabulary-label" for="category-vocabulary-category-name">
								<liferay-ui:message key="name" />
							</label>

							<input class="category-vocabulary-category-name" name="category-vocabulary-category-name" type="text" value="" />
						</div>

						<div class="ctrl-holder">
							<label for="category-vocabulary-select-list">
								<liferay-ui:message key="to-vocabulary" />
							</label>

							<select class="category-vocabulary-select-list" name="category-vocabulary-select-list"></select>
						</div>

						<div class="category-permissions-actions">
							<liferay-ui:input-permissions
								modelName="<%= AssetCategory.class.getName() %>"
							/>
						</div>

						<div class="button-holder">
							<input class="category-save-button" type="button" value="<liferay-ui:message key="save" />" />

							<input class="close-panel" type="button" value="<liferay-ui:message key="close" />" />
						</div>
					</div>
				</div>

				<div class="exp-overlay category-vocabulary-toolbar-section">
					<div class="exp-header">
						<span class="knob"></span>
					</div>

					<div class="panel-content">
						<div class="ctrl-holder">
							<label for="category-vocabulary-name">
								<liferay-ui:message key="add-vocabulary" />
							</label>

							<input class="category-vocabulary-name" name="category-vocabulary-name" type="text" value="" />
						</div>

						<div class="category-vocabulary-permissions-actions">
							<liferay-ui:input-permissions
								modelName="<%= AssetCategoryVocabulary.class.getName() %>"
							/>
						</div>

						<div class="button-holder">
							<input class="category-vocabulary-save-button" type="button" value="<liferay-ui:message key="save" />" />

							<input class="close-panel" type="button" value="<liferay-ui:message key="close" />" />
						</div>
					</div>
				</div>
			</span>
		</div>
	</td>
</tr>
<tr class="category-vocabulary-content">
	<td class="category-vocabulary-list-container">
		<div class="results-header"><liferay-ui:message key="vocabularies" /></div>

		<div class="category-vocabulary-list lfr-component"></div>
	</td>
	<td class="category-vocabulary-categories-container">
		<div class="results-header">
			<liferay-ui:message key="categories" />
		</div>

		<div class="lfr-message-response" id="category-vocabulary-category-messages" style="display: none;"></div>

		<div class="category-vocabulary-categories lfr-component"></div>
	</td>
	<td class="category-vocabulary-edit-category">
		<div class="results-header"><liferay-ui:message key="edit-category" /></div>

		<div class="category-vocabulary-edit">
			<div class="category-vocabulary-close">
				<span>
					<liferay-ui:icon image="close" />
				</span>
			</div>

			<div class="category-vocabulary-label">
				<liferay-ui:message key="name" />:
			</div>

			<input class="category-name" name="category-name" type="text" />

			<br /><br />

			<div class="category-vocabulary-properties">
				<liferay-ui:message key="properties" />:

				<liferay-ui:icon-help message="properties-are-a-way-to-add-more-detailed-information-to-a-specific-tag-or-category" />

				<div class="category-vocabulary-property-row">
					<input class="category-property-key" type="text" />

					<input class="category-property-value" type="text" />

					<span class="add-category-property"><liferay-ui:icon image="add" /></span>

					<span class="delete-category-property"><liferay-ui:icon image="delete" /></span>
				</div>

				<br />

				<input class="category-vocabulary-save-properties" type="button" value="<liferay-ui:message key="save" />" />

				<input class="category-vocabulary-close" type="button" value="<liferay-ui:message key="close" />" />

				<input class="category-vocabulary-delete-categories-button" type="button" value="<liferay-ui:message key="delete" />" />

				<input class="permissions-categories-btn" type="button" value="<liferay-ui:message key="edit-category-permissions" />" />
			</div>
		</div>
	</td>
</tr>
<tr>
	<td colspan="3">
		<div class="category-vocabulary-footer">
			<input class="category-vocabulary-delete-list-button" type="button" value="<liferay-ui:message key="delete-vocabulary" />" />

			<input class="permissions-category-vocabulary-btn" type="button" value="<liferay-ui:message key="edit-vocabulary-permissions" />" />
		</div>
	</td>
</tr>
</table>

</form>

<script>
	jQuery(
		function() {
			new Liferay.Portlet.AssetCategoryAdmin('<%= portletDisplay.getId() %>');
		}
	);
</script>