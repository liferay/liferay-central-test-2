<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

<table class="vocabulary-container">
<tr>
	<td colspan="3">
		<div class="vocabulary-toolbar">
			<span id="vocabulary-search-bar">
				<input id="vocabulary-search-input" type="text" value="" />

				<select class="vocabulary-select-search" id="vocabulary-select-search">
					<option value="categories" selected><liferay-ui:message key="categories" /></option>
					<option value="vocabularies"><liferay-ui:message key="vocabularies" /></option>
				</select>

				<input id="vocabulary-search-button" type="button" value="<liferay-ui:message key="search" />" />
			</span>

			<span class="vocabulary-actions">
				<c:if test="<%= AssetPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_VOCABULARY) %>">
					<input class="add-vocabulary-button" id="add-vocabulary-button" name="add-vocabulary-button" type="button" value="<liferay-ui:message key="add-vocabulary" />">
				</c:if>

				<c:if test="<%= AssetPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_CATEGORY) %>">
					<input class="add-category-button" id="add-category-button" name="add-category-button" type="button" value="<liferay-ui:message key="add-category" />">
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

				<div class="asset-category-layer-wrapper">
					<div class="add-category-layer asset-category-layer">
						<span class="aui-field">
							<span class="aui-field-content">
								<label class="vocabulary-label" for="vocabulary-category-name">
									<liferay-ui:message key="name" />
								</label>

								<input class="vocabulary-category-name" name="vocabulary-category-name" type="text" value="" />
							</span>
						</span>

						<span class="aui-field">
							<span class="aui-field-content">
								<label for="vocabulary-select-list">
									<liferay-ui:message key="to-vocabulary" />
								</label>

								<select class="vocabulary-select-list" name="vocabulary-select-list"></select>
							</span>
						</span>

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

				<div class="asset-category-layer-wrapper">
					<div class="add-vocabulary-layer asset-category-layer">
						<span class="aui-field">
							<span class="aui-field-content">
								<label for="vocabulary-name">
									<liferay-ui:message key="add-vocabulary" />
								</label>

								<input class="vocabulary-name" name="vocabulary-name" type="text" value="" />
							</span>
						</span>

						<div class="vocabulary-permissions-actions">
							<liferay-ui:input-permissions
								modelName="<%= AssetVocabulary.class.getName() %>"
							/>
						</div>

						<div class="button-holder">
							<input class="vocabulary-save-button" type="button" value="<liferay-ui:message key="save" />" />

							<input class="close-panel" type="button" value="<liferay-ui:message key="close" />" />
						</div>
					</div>
				</div>
			</span>
		</div>
	</td>
</tr>
<tr class="vocabulary-content">
	<td class="vocabulary-list-container">
		<div class="results-header"><liferay-ui:message key="vocabularies" /></div>

		<div class="vocabulary-list lfr-component"></div>
	</td>
	<td class="vocabulary-categories-container">
		<div class="results-header">
			<liferay-ui:message key="categories" />
		</div>

		<div class="lfr-message-response" id="vocabulary-category-messages" style="display: none;"></div>

		<div class="vocabulary-categories"></div>
	</td>
	<td class="aui-helper-hidden vocabulary-edit-category">
		<div class="results-header"><liferay-ui:message key="edit-category" /></div>

		<div class="vocabulary-edit">
			<div class="vocabulary-close">
				<span>
					<liferay-ui:icon image="close" />
				</span>
			</div>

			<div class="vocabulary-label">
				<liferay-ui:message key="name" />:
			</div>

			<input class="category-name" name="category-name" type="text" />

			<br /><br />

			<div class="vocabulary-properties">
				<liferay-ui:message key="properties" />:

				<liferay-ui:icon-help message="properties-are-a-way-to-add-more-detailed-information-to-a-specific-category" />

				<div class="aui-helper-hidden vocabulary-property-row">
					<input class="category-property-key" type="text" />

					<input class="category-property-value" type="text" />

					<span class="add-category-property"><liferay-ui:icon image="add" /></span>

					<span class="delete-category-property"><liferay-ui:icon image="delete" /></span>
				</div>

				<br />

				<input class="vocabulary-save-category-properties" type="button" value="<liferay-ui:message key="save" />" />

				<input class="vocabulary-close" type="button" value="<liferay-ui:message key="close" />" />

				<input class="vocabulary-delete-categories-button" type="button" value="<liferay-ui:message key="delete" />" />

				<input class="permissions-categories-button" type="button" value="<liferay-ui:message key="edit-category-permissions" />" />
			</div>
		</div>
	</td>
</tr>
<tr>
	<td colspan="3">
		<div class="vocabulary-footer">
			<input class="vocabulary-delete-list-button" type="button" value="<liferay-ui:message key="delete-vocabulary" />" />

			<input class="permissions-vocabulary-button" type="button" value="<liferay-ui:message key="edit-vocabulary-permissions" />" />
		</div>
	</td>
</tr>
</table>

</form>

<aui:script use="liferay-category-admin">
	new Liferay.Portlet.AssetCategoryAdmin('<%= portletDisplay.getId() %>');
</aui:script>