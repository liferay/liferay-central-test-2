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

<%@ include file="/html/taglib/ui/asset_links/init.jsp" %>

<%
String className = GetterUtil.getString((String)request.getAttribute("liferay-ui:asset-links:className"));
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:asset-links:classPK"));

List<AssetLink> assetLinks = new ArrayList<AssetLink>();
AssetEntry assetEntry = null;

if (classPK > 0) {
	assetEntry = AssetEntryLocalServiceUtil.getEntry(className, classPK);
	assetLinks = AssetLinkLocalServiceUtil.getLinks(assetEntry.getEntryId(), AssetLinkConstants.TYPE_RELATED);
}
%>

<c:if test="<%= assetLinks.size() > 0 %>" >
	<div class="taglib-asset-links">
		<h2 class="asset-links-title"><liferay-ui:message key="related-assets" />:</h2>

		<ul class="asset-links-list">

			<%
			String assetEntryTitle = StringPool.BLANK;

			for (AssetLink assetLink : assetLinks) {
				AssetEntry linkedAssetEntry = null;

				if (assetLink.getEntryId1() == assetEntry.getEntryId()) {
					linkedAssetEntry = AssetEntryServiceUtil.getEntry(assetLink.getEntryId2());
				}
				else {
					linkedAssetEntry = AssetEntryServiceUtil.getEntry(assetLink.getEntryId1());
				}

				AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(ClassNameLocalServiceUtil.getClassName(linkedAssetEntry.getClassNameId()).getClassName());

				AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(linkedAssetEntry.getClassPK());

				assetEntryTitle = assetRenderer.getTitle(locale);

				String urlViewInContext = assetRenderer.getURLViewInContext((LiferayPortletRequest)portletRequest, (LiferayPortletResponse)portletResponse, "viewFullContentURLString");
			%>

				<li class="asset-links-list-item">
					<liferay-ui:icon
						label="<%= true %>"
						message="<%= assetEntryTitle %>"
						src="<%= assetRenderer.getIconPath(portletRequest) %>"
						url="<%= urlViewInContext %>"
					/>
				</li>

			<%
			}
			%>

		</ul>
	</div>
</c:if>
