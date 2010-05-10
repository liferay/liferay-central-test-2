<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
%>

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
List results = (List)request.getAttribute("view.jsp-results");

int assetEntryIndex = ((Integer)request.getAttribute("view.jsp-assetEntryIndex")).intValue();

AssetEntry assetEntry = (AssetEntry)request.getAttribute("view.jsp-assetEntry");
AssetRendererFactory assetRendererFactory = (AssetRendererFactory)request.getAttribute("view.jsp-assetRendererFactory");
AssetRenderer assetRenderer = (AssetRenderer)request.getAttribute("view.jsp-assetRenderer");

String title = (String)request.getAttribute("view.jsp-title");

if (Validator.isNull(title)) {
	title = assetRenderer.getTitle();
}

boolean show = ((Boolean)request.getAttribute("view.jsp-show")).booleanValue();

request.setAttribute("view.jsp-showIconLabel", false);

PortletURL viewFullContentURL = renderResponse.createRenderURL();

viewFullContentURL.setParameter("struts_action", "/asset_publisher/view_content");
viewFullContentURL.setParameter("assetEntryId", String.valueOf(assetEntry.getEntryId()));
viewFullContentURL.setParameter("type", assetRendererFactory.getType());

if (Validator.isNotNull(assetRenderer.getUrlTitle())) {
	viewFullContentURL.setParameter("urlTitle", assetRenderer.getUrlTitle());

	if (assetRenderer.getGroupId() != scopeGroupId) {
		viewFullContentURL.setParameter("groupId", String.valueOf(assetRenderer.getGroupId()));
	}
}

String viewURL = viewInContext ? assetRenderer.getURLViewInContext((LiferayPortletRequest)renderRequest, (LiferayPortletResponse)renderResponse, viewFullContentURL.toString()) : viewFullContentURL.toString();

viewURL = _checkViewURL(viewURL, currentURL, themeDisplay);
%>

	<c:if test="<%= assetEntryIndex == 0 %>">
		<ul class="title-list">
	</c:if>

	<c:if test="<%= show %>">
		<li class="title-list <%= assetRendererFactory.getType() %>">
			<c:choose>
				<c:when test="<%= assetRenderer.hasViewPermission(permissionChecker) && Validator.isNotNull(viewURL) %>">
					<a href="<%= viewURL %>"><%= title %></a>
				</c:when>
				<c:otherwise>
					<%= title %>
				</c:otherwise>
			</c:choose>

			<liferay-util:include page="/html/portlet/asset_publisher/asset_actions.jsp" />

			<c:if test="<%= assetRenderer.hasViewPermission(permissionChecker) %>">
				<div class="asset-metadata">
					<%@ include file="/html/portlet/asset_publisher/asset_metadata.jspf" %>
				</div>
			</c:if>
		</li>
	</c:if>

	<c:if test="<%= (assetEntryIndex + 1) == results.size() %>">
		</ul>
	</c:if>