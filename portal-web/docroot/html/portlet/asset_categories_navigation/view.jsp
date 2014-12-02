<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/asset_categories_navigation/init.jsp" %>

<%
List<AssetVocabulary> ddmTemplateAssetVocabularies = new ArrayList<AssetVocabulary>();

if (allAssetVocabularies) {
	ddmTemplateAssetVocabularies = assetVocabularies;
}
else {
	for (long assetVocabularyId : assetVocabularyIds) {
		try {
			ddmTemplateAssetVocabularies.add(AssetVocabularyServiceUtil.getVocabulary(assetVocabularyId));
		}
		catch (NoSuchVocabularyException nsve) {
		}
	}
}
%>

<liferay-ui:ddm-template-renderer displayStyle="<%= displayStyle %>" displayStyleGroupId="<%= displayStyleGroupId %>" entries="<%= ddmTemplateAssetVocabularies %>">
	<c:choose>
		<c:when test="<%= allAssetVocabularies %>">
			<liferay-ui:asset-categories-navigation
				hidePortletWhenEmpty="<%= true %>"
			/>
		</c:when>
		<c:otherwise>
			<liferay-ui:asset-categories-navigation
				hidePortletWhenEmpty="<%= true %>"
				vocabularyIds="<%= assetVocabularyIds %>"
			/>
		</c:otherwise>
	</c:choose>
</liferay-ui:ddm-template-renderer>