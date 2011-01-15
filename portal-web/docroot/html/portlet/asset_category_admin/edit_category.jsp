<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/html/portlet/asset_category_admin/init.jsp" %>

<%
AssetCategory category = (AssetCategory)request.getAttribute(WebKeys.ASSET_CATEGORY);

List<AssetVocabulary> vocabularies = (List<AssetVocabulary>)request.getAttribute(WebKeys.ASSET_VOCABULARIES);

long vocabularyId = ParamUtil.getLong(request, "vocabularyId");

int[] propertiesIndexes = null;

List<AssetCategoryProperty> properties = Collections.emptyList();

String propertiesIndexesParam = ParamUtil.getString(request, "propertiesIndexes");

if (Validator.isNotNull(propertiesIndexesParam)) {
	properties = new ArrayList<AssetCategoryProperty>();

	propertiesIndexes = StringUtil.split(propertiesIndexesParam, 0);

	for (int propertiesIndex : propertiesIndexes) {
		properties.add(new AssetCategoryPropertyImpl());
	}
}
else {
	if (category != null) {

		properties = AssetCategoryPropertyServiceUtil.getCategoryProperties(category.getCategoryId());

		propertiesIndexes = new int[properties.size()];


		for (int i = 0; i < properties.size(); i++) {
			propertiesIndexes[i] = i;
		}
	}

	if (properties.isEmpty()) {
		properties = new ArrayList<AssetCategoryProperty>();

		properties.add(new AssetCategoryPropertyImpl());

		propertiesIndexes = new int[] {0};
	}

	if (propertiesIndexes == null) {
		propertiesIndexes = new int[0];
	}
}

String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_asset_category_admin_edit_category");
String formName = randomNamespace + "_fm";
%>

<portlet:actionURL var="editCategoryURL">
	<portlet:param name="struts_action" value="/asset_category_admin/edit_category" />
</portlet:actionURL>

<aui:form action='<%=editCategoryURL%>' cssClass="update-category-form" method="get" name="<%= formName %>">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= category == null ? Constants.ADD : Constants.UPDATE %>" />

	<aui:model-context bean="<%= category %>" model="<%= AssetCategory.class %>" />

	<aui:fieldset>
		<div>
			<div class="add-category-layer asset-category-layer">
				<aui:input type="hidden" name="categoryId" value="<%= category != null ? category.getCategoryId() : StringPool.BLANK %>" />
				<aui:input type="hidden" name="parentCategoryId" value="<%= category != null ? category.getParentCategoryId() : StringPool.BLANK %>" />

				<aui:input label="name" name="title" cssClass="category-name"/>

				<aui:input name="description" />

				<aui:select label="to-vocabulary" name="vocabularyId" inputCssClass="vocabulary-select-list">

					<%
					for (AssetVocabulary vocabulary : vocabularies) {
					%>

						<aui:option label="<%= vocabulary.getTitle(locale) %>" selected="<%= vocabulary.getVocabularyId() == vocabularyId %>" value="<%= vocabulary.getVocabularyId() %>" />

					<%
					}
					%>

				</aui:select>

				<liferay-ui:panel-container extended="<%= false %>" id="assetCategoryPanelContainer" persistState="<%= true %>">
					<c:if test="<%= category == null %>">
						<liferay-ui:panel collapsible="<%= true %>" cssClass="category-permissions-actions" defaultState="open" extended="<%= true %>" id="assetCategoryPermissionsPanel" persistState="<%= true %>" title="permissions">
							<liferay-ui:input-permissions
								modelName="<%= AssetCategory.class.getName() %>"
							/>
						</liferay-ui:panel>
					</c:if>

					<liferay-ui:panel collapsible="<%= true %>" defaultState="closed" extended="<%= true %>" helpMessage="properties-are-a-way-to-add-more-detailed-information-to-a-specific-category" id="assetCategoryPropertiesPanel" persistState="<%= true %>" title="properties">
						<aui:fieldset cssClass="category-properties" id="categoryProperties">

							<%
							for (int i = 0; i < propertiesIndexes.length; i++) {
								int propertiesIndex = propertiesIndexes[i];

								AssetCategoryProperty property = properties.get(i);
							%>

								<aui:model-context bean="<%= property %>" model="<%= AssetCategoryProperty.class %>" />

								<div class="lfr-form-row lfr-form-row-inline">
									<div class="row-fields">
										<aui:input fieldParam='<%= "key" + propertiesIndex %>' name="key" />

										<aui:input fieldParam='<%= "value" + propertiesIndex %>' name="value" />
									</div>
								</div>

							<%
							}
							%>

							<aui:input name="categoryPropertiesIndexes" type="hidden" value="<%= StringUtil.merge(propertiesIndexes) %>" />
						</aui:fieldset>
					</liferay-ui:panel>
				</liferay-ui:panel-container>

				<aui:button-row>
					<aui:button type="submit" />

					<c:if test="<%= category != null && permissionChecker.hasPermission(scopeGroupId, AssetCategory.class.getName(), category.getCategoryId(), ActionKeys.DELETE)%>">
						<aui:button value="delete" id="category-delete-button" />
					</c:if>

					<c:if test="<%= category != null && permissionChecker.hasPermission(scopeGroupId, AssetCategory.class.getName(), category.getCategoryId(), ActionKeys.PERMISSIONS) %>" >
						<liferay-security:permissionsURL
							modelResource="<%= AssetCategory.class.getName() %>"
							modelResourceDescription="<%= category.getTitle(locale) %>"
							resourcePrimKey="<%= String.valueOf(category.getCategoryId()) %>"
							var="permissionsURL"
							windowState="pop_up"
						/>

						<aui:button value="permissions" id="category-change-permissions" data-url="<%= permissionsURL %>" />
					</c:if>

					<aui:button cssClass="close-panel" type="cancel" value="close" />
				</aui:button-row>
			</div>
		</div>
	</aui:fieldset>
</aui:form>

<aui:script use="liferay-auto-fields">
	var autoFields = new Liferay.AutoFields(
		{
			contentBox: 'fieldset#categoryProperties',
			fieldIndexes: '<portlet:namespace />categoryPropertiesIndexes'
		}
	).render();

	var propertiesTrigger = A.one('fieldset#categoryProperties');

	if (propertiesTrigger) {
		propertiesTrigger.setData('autoFieldsInstance', autoFields);
	}
</aui:script>
