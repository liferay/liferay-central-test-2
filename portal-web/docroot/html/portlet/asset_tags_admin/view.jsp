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

<%@ include file="/html/portlet/asset_tags_admin/init.jsp" %>

<form id="<portlet:namespace />fm">

<table class="tags-admin-container">
<tr>
	<td colspan="3">
		<div class="tags-admin-toolbar">
			<span id="tags-admin-search-bar">
				<input id="tags-admin-search-input" type="text" value="" />

				<input id="tags-admin-search-button" type="button" value="<liferay-ui:message key="search" />" />
			</span>

			<span class="tags-admin-actions">
				<c:if test="<%= AssetPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_TAG) %>">
					<input class="add-tag-button" id="add-tag-button" name="add-tag-button" type="button" value="<liferay-ui:message key="add-tag" />">
				</c:if>

				<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS) %>">
					<liferay-security:permissionsURL
						modelResource="com.liferay.portlet.asset"
						modelResourceDescription="<%= HtmlUtil.escape(themeDisplay.getScopeGroupName()) %>"
						resourcePrimKey="<%= String.valueOf(scopeGroupId) %>"
						var="permissionsURL"
					/>

					<input type="button" value="<liferay-ui:message key="permissions" />" onClick="location.href = '<%= permissionsURL %>';" />
				</c:if>

				<div class="add-tag-layer-wrapper">
					<div class="add-tag-layer">
						<div class="aui-ctrl-holder">
							<label class="tag-label" for="new-tag-name">
								<liferay-ui:message key="name" />
							</label>

							<input class="new-tag-name" id="new-tag-name" name="new-tag-name" type="text" value="" />
						</div>

						<div class="tag-permissions-actions">
							<liferay-ui:input-permissions
								modelName="<%= AssetTag.class.getName() %>"
							/>
						</div>

						<div class="aui-button-holder">
							<input class="tag-save-button" type="button" value="<liferay-ui:message key="save" />" />

							<input class="close-panel" type="button" value="<liferay-ui:message key="close" />" />
						</div>
					</div>
				</div>
			</span>
		</div>
	</td>
</tr>
<tr class="tags-admin-content">
	<td class="tag-container">
		<div class="results-header">
			<liferay-ui:message key="tags" />
		</div>

		<div class="lfr-message-response" id="tag-messages" style="display: none;"></div>

		<div class="tags lfr-component"></div>
	</td>
	<td class="tag-edit-container">
		<div class="results-header"><liferay-ui:message key="edit-tag" /></div>
		<div class="tag-edit">
			<div class="tag-close">
				<span>
					<liferay-ui:icon image="close" />
				</span>
			</div>

			<div class="tag-label">
				<liferay-ui:message key="name" />:
			</div>

			<input class="tag-name" name="tag-name" type="text" />

			<br /><br />

			<div class="tag-properties">
				<liferay-ui:message key="properties" />:

				<liferay-ui:icon-help message="properties-are-a-way-to-add-more-detailed-information-to-a-specific-tag" />

				<div class="tag-property-row">
					<input class="property-key" type="text" />

					<input class="property-value" type="text" />

					<span class="add-property"><liferay-ui:icon image="add" /></span>

					<span class="delete-property"><liferay-ui:icon image="delete" /></span>
				</div>

				<br />

				<input class="tag-save-properties" type="button" value="<liferay-ui:message key="save" />" />

				<input class="tag-close" type="button" value="<liferay-ui:message key="close" />" />

				<input class="tag-delete-button" type="button" value="<liferay-ui:message key="delete" />" />

				<input class="tag-permissions-button" type="button" value="<liferay-ui:message key="edit-tag-permissions" />" />
			</div>
		</div>
	</td>
</tr>
</table>

</form>

<script type="text/javascript">
	AUI().ready(
		'liferay-tags-admin',
		function(A) {
			new Liferay.Portlet.AssetTagsAdmin('<%= portletDisplay.getId() %>');
		}
	);
</script>