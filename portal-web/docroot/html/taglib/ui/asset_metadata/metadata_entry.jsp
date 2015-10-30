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

<%@ include file="/html/taglib/ui/asset_metadata/init.jsp" %>

<%
AssetEntry assetEntry = (AssetEntry)request.getAttribute("liferay-ui:asset-metadata:assetEntry");
AssetRenderer<?> assetRenderer = (AssetRenderer<?>)request.getAttribute("liferay-ui:asset-metadata:assetRenderer");
boolean filterByMetadata = GetterUtil.getBoolean(request.getAttribute("liferay-ui:asset-metadata:filterByMetadata"));
String metadataField = (String)request.getAttribute("liferay-ui:asset-metadata:metadataField");

String iconCssClass = StringPool.BLANK;
String label = LanguageUtil.get(request, metadataField);
String metadataFieldCssClass = "metadata-" + metadataField;
boolean showLabel = true;
String value = null;

if (metadataField.equals("author")) {
	showLabel = false;
	value = "author";
}
else if (metadataField.equals("categories")) {
	List<AssetCategory> assetCategories = assetEntry.getCategories();

	showLabel = false;

	if (!assetCategories.isEmpty()) {
		value = "categories";
	}
}
else if (metadataField.equals("create-date")) {
	iconCssClass = "icon-calendar";
	value = dateFormatDate.format(assetEntry.getCreateDate());
}
else if (metadataField.equals("expiration-date")) {
	iconCssClass = "icon-calendar";

	if (assetEntry.getExpirationDate() == null) {
		value = StringPool.BLANK;
	}
	else {
		value = dateFormatDate.format(assetEntry.getExpirationDate());
	}
}
else if (metadataField.equals("modified-date")) {
	iconCssClass = "icon-calendar";
	value = dateFormatDate.format(assetEntry.getModifiedDate());
}
else if (metadataField.equals("priority")) {
	iconCssClass = "icon-long-arrow-up";
	value = LanguageUtil.get(request, "priority") + StringPool.COLON + StringPool.SPACE + assetEntry.getPriority();
}
else if (metadataField.equals("publish-date")) {
	iconCssClass = "icon-calendar";

	if (assetEntry.getPublishDate() == null) {
		value = StringPool.BLANK;
	}
	else {
		value = dateFormatDate.format(assetEntry.getPublishDate());
	}
}
else if (metadataField.equals("tags")) {
	List<AssetTag> assetTags = assetEntry.getTags();

	showLabel = false;

	if (!assetTags.isEmpty()) {
		value = "tags";
	}
}
else if (metadataField.equals("view-count")) {
	int viewCount = assetEntry.getViewCount();

	value = viewCount + StringPool.SPACE + LanguageUtil.get(request, (viewCount == 1) ? "view" : "views");
}
%>

<c:choose>
	<c:when test='<%= Validator.equals(value, "author") %>'>

		<%
		User assetRendererUser = UserLocalServiceUtil.getUser(assetRenderer.getUserId());

		String displayDate = StringPool.BLANK;

		if (assetEntry.getPublishDate() != null) {
			displayDate = LanguageUtil.format(request, "x-ago", LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - assetEntry.getPublishDate().getTime(), true), false);
		}
		else if (assetEntry.getModifiedDate() != null) {
			displayDate = LanguageUtil.format(request, "x-ago", LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - assetEntry.getModifiedDate().getTime(), true), false);
		}
		%>

		<div class="metadata-author">
			<div class="asset-avatar">
				<liferay-ui:user-portrait
					userId="<%= assetRendererUser.getUserId() %>"
				/>
			</div>

			<div class="asset-user-info">
				<span class="user-info"><%= assetRendererUser.getFullName() %></span>

				<span class="date-info"><%= displayDate %></span>
			</div>
		</div>
	</c:when>
	<c:when test="<%= Validator.isNotNull(value) %>">
		<aui:column cssClass="help-block">
			<dt class="metadata-entry-label <%= showLabel ? StringPool.BLANK : "hide" %>"><%= label %></dt>

			<dd class="metadata-entry <%= metadataFieldCssClass %> <%= iconCssClass %>">
				<c:choose>
					<c:when test='<%= value.equals("categories") %>'>
						<liferay-ui:asset-categories-summary
							className="<%= assetEntry.getClassName() %>"
							classPK="<%= assetEntry.getClassPK () %>"
							portletURL="<%= filterByMetadata ? renderResponse.createRenderURL() : null %>"
						/>
					</c:when>
					<c:when test='<%= value.equals("tags") %>'>
						<liferay-ui:asset-tags-summary
							className="<%= assetEntry.getClassName() %>"
							classPK="<%= assetEntry.getClassPK () %>"
							portletURL="<%= filterByMetadata ? renderResponse.createRenderURL() : null %>"
						/>
					</c:when>
					<c:otherwise>
						<%= value %>
					</c:otherwise>
				</c:choose>
			</dd>
		</aui:column>
	</c:when>
</c:choose>