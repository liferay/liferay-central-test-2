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
%>

<portlet:actionURL var="editCategoryURL">
	<portlet:param name="struts_action" value="/asset_category_admin/edit_category" />
</portlet:actionURL>

<aui:form action='<%=editCategoryURL%>' cssClass="update-category-form" method="get" name="fm1">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= category == null ? Constants.ADD : Constants.UPDATE %>" />

	<aui:model-context bean="<%= category %>" model="<%= AssetCategory.class %>" />

	<aui:fieldset>
		<div>
			<div class="add-category-layer asset-category-layer">
				<aui:input type="hidden" name="vocabularyId" />
				<aui:input type="hidden" name="parentCategoryId" />

				<aui:input label="name" name="title" cssClass="category-name"/>

				<aui:input name="description" />

				<aui:select label="to-vocabulary" name="vocabulary-select-list" inputCssClass="vocabulary-select-list">

				<%
				for (AssetVocabulary vocabulary : vocabularies) {
				%>

					<aui:option label="<%= vocabulary.getTitle(locale) %>" selected="<%= vocabulary.getVocabularyId() == vocabularyId %>" value="<%= vocabulary.getVocabularyId() %>" />

				<%
				}
				%>

				</aui:select>

				<c:choose>
					<c:when test="<%= category == null %>">
						<aui:field-wrapper cssClass="category-permissions-actions" label="permissions">
							<liferay-ui:input-permissions
								modelName="<%= AssetCategory.class.getName() %>"
							/>
						</aui:field-wrapper>
					</c:when>
				</c:choose>

				<aui:button-row>
					<aui:button type="submit" />

					<aui:button cssClass="close-panel" type="cancel" value="close" />
				</aui:button-row>
			</div>
		</div>
	</aui:fieldset>
</aui:form>