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

<%@ include file="/facets/init.jsp" %>

<%
com.liferay.portal.search.web.internal.facet.display.context.AssetTagsSearchFacetDisplayContext assetTagsSearchFacetDisplayContext = new com.liferay.portal.search.web.internal.facet.display.context.AssetTagsSearchFacetDisplayContext(facet, fieldParam, dataJSONObject.getString("displayStyle", "cloud"), dataJSONObject.getInt("frequencyThreshold"), dataJSONObject.getInt("maxTerms", 10), dataJSONObject.getBoolean("showAssetCount", true));
%>

<c:choose>
	<c:when test="<%= assetTagsSearchFacetDisplayContext.isRenderNothing() %>">
		<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(assetTagsSearchFacetDisplayContext.getFieldParamInputName()) %>" type="hidden" value="<%= assetTagsSearchFacetDisplayContext.getFieldParamInputValue() %>" />
	</c:when>
	<c:otherwise>
		<div class="panel panel-default">
			<div class="panel-heading">
				<div class="panel-title">
					<liferay-ui:message key="tags" />
				</div>
			</div>

			<div class="panel-body">
				<div class="asset-tags <%= cssClass %>" data-facetFieldName="<%= HtmlUtil.escapeAttribute(facet.getFieldId()) %>" id="<%= randomNamespace %>facet">
					<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(assetTagsSearchFacetDisplayContext.getFieldParamInputName()) %>" type="hidden" value="<%= assetTagsSearchFacetDisplayContext.getFieldParamInputValue() %>" />

					<ul class="<%= assetTagsSearchFacetDisplayContext.isCloudWithCount() ? "tag-cloud" : "tag-list" %> list-unstyled">
						<li class="default facet-value">
							<a class="<%= assetTagsSearchFacetDisplayContext.isNothingSelected() ? "text-primary" : "text-default" %>" data-value="" href="javascript:;"><liferay-ui:message key="<%= HtmlUtil.escape(facetConfiguration.getLabel()) %>" /></a>
						</li>

						<%
						java.util.List<com.liferay.portal.search.web.internal.facet.display.context.AssetTagsSearchFacetTermDisplayContext> assetTagsSearchFacetTermDisplayContexts = assetTagsSearchFacetDisplayContext.getTermDisplayContexts();

						for (com.liferay.portal.search.web.internal.facet.display.context.AssetTagsSearchFacetTermDisplayContext assetTagsSearchFacetTermDisplayContext : assetTagsSearchFacetTermDisplayContexts) {
						%>

							<li class="facet-value tag-popularity-<%= assetTagsSearchFacetTermDisplayContext.getPopularity() %>">
								<a class="<%= assetTagsSearchFacetTermDisplayContext.isSelected() ? "text-primary" : "text-default" %>" data-value="<%= HtmlUtil.escapeAttribute(assetTagsSearchFacetTermDisplayContext.getValue()) %>" href="javascript:;">
									<%= HtmlUtil.escape(assetTagsSearchFacetTermDisplayContext.getDisplayName()) %>

									<c:if test="<%= assetTagsSearchFacetTermDisplayContext.isShowFrequency() %>">
										<span class="frequency">(<%= assetTagsSearchFacetTermDisplayContext.getFrequency() %>)</span>
									</c:if>
								</a>
							</li>

						<%
						}
						%>

					</ul>
				</div>
			</div>
		</div>
	</c:otherwise>
</c:choose>