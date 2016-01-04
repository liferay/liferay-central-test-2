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

<%@ include file="/message_boards/init.jsp" %>

<%
String topLink = ParamUtil.getString(request, "topLink", "message-boards-home");

PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");

long categoryId = GetterUtil.getLong(request.getAttribute("view.jsp-categoryId"));

MBCategoryDisplay categoryDisplay = new MBCategoryDisplayImpl(scopeGroupId, categoryId);

String displayStyle = GetterUtil.getString(request.getAttribute("view.jsp-displayStyle"));
%>

<div class="container-fluid-1280">
	<liferay-ui:panel-container cssClass="statistics-panel" extended="<%= false %>" id="messageBoardsStatisticsPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" cssClass="statistics-panel-content" extended="<%= true %>" id="messageBoardsGeneralStatisticsPanel" persistState="<%= true %>" title="general">
			<dl>
				<dt>
					<liferay-ui:message key="num-of-categories" />:
				</dt>
				<dd>
					<%= numberFormat.format(categoryDisplay.getAllCategoriesCount()) %>
				</dd>
				<dt>
					<liferay-ui:message key="num-of-posts" />:
				</dt>
				<dd>
					<%= numberFormat.format(MBStatsUserLocalServiceUtil.getMessageCountByGroupId(scopeGroupId)) %>
				</dd>
				<dt>
					<liferay-ui:message key="num-of-participants" />:
				</dt>
				<dd>
					<%= numberFormat.format(MBStatsUserLocalServiceUtil.getStatsUsersByGroupIdCount(scopeGroupId)) %>
				</dd>
			</dl>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" cssClass="statistics-panel-content" extended="<%= true %>" id="messageBoardsTopPostersPanel" persistState="<%= true %>" title="top-posters">
			<liferay-ui:search-container
				emptyResultsMessage="there-are-no-top-posters"
				iteratorURL="<%= portletURL %>"
				total="<%= MBStatsUserLocalServiceUtil.getStatsUsersByGroupIdCount(scopeGroupId) %>"
			>
				<liferay-ui:search-container-results
					results="<%= MBStatsUserLocalServiceUtil.getStatsUsersByGroupId(scopeGroupId, searchContainer.getStart(), searchContainer.getEnd()) %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.portlet.messageboards.model.MBStatsUser"
					keyProperty="statsUserId"
					modelVar="statsUser"
				>
					<liferay-ui:search-container-column-jsp
						path="/message_boards/top_posters_user_display.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
			</liferay-ui:search-container>
		</liferay-ui:panel>
	</liferay-ui:panel-container>
</div>

<%
PortalUtil.setPageSubtitle(LanguageUtil.get(request, StringUtil.replace(topLink, StringPool.UNDERLINE, StringPool.DASH)), request);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, TextFormatter.format(topLink, TextFormatter.O)), portletURL.toString());
%>