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

<%@ include file="/html/taglib/init.jsp" %>

<c:if test="<%= themeDisplay.isShowStagingIcon() %>">

	<%
	Group group = themeDisplay.getScopeGroup();

	if (themeDisplay.getScopeGroup().isLayout()) {
		group = layout.getGroup();
	}

	String publishNowDialogTitle = "publish-to-live-now";
	String publishScheduleDialogTitle = "schedule-publication-to-live";

	Group liveGroup = null;
	Group stagingGroup = null;

	if (group.isStagingGroup()) {
		liveGroup = group.getLiveGroup();
		stagingGroup = group;
	}
	else if (group.isStaged()) {
		if (group.isStagedRemotely()) {
			liveGroup = group;
			stagingGroup = null;

			publishNowDialogTitle = "publish-to-remote-live-now";
			publishScheduleDialogTitle = "schedule-publication-to-remote-live";
		}
		else {
			liveGroup = group;
			stagingGroup = group.getStagingGroup();
		}
	}
	%>

	<c:if test="<%= liveGroup.isStaged() %>">
		<ul>
			<c:choose>
				<c:when test="<%= !liveGroup.isStagedRemotely() && group.isStagingGroup() %>">

					<%
					String friendlyURL = null;

					try {
						Layout liveLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(layout.getUuid(), liveGroup.getGroupId());

						friendlyURL = PortalUtil.getLayoutFriendlyURL(liveLayout, themeDisplay);
						friendlyURL = PortalUtil.addPreservedParameters(themeDisplay, friendlyURL);
					}
					catch (Exception e) {
					}
					%>

					<c:if test="<%= Validator.isNotNull(friendlyURL) %>">
						<li class="page-settings">
							<a href="<%= friendlyURL %>"><liferay-ui:message key="view-live-page" /></a>
						</li>
					</c:if>
				</c:when>
				<c:when test="<%= !liveGroup.isStagedRemotely() && !group.isStagingGroup() %>">

					<%
					String friendlyURL = null;

					try {
						Layout stagedLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(layout.getUuid(), stagingGroup.getGroupId());

						friendlyURL = PortalUtil.getLayoutFriendlyURL(stagedLayout, themeDisplay);
						friendlyURL = PortalUtil.addPreservedParameters(themeDisplay, friendlyURL);
					}
					catch (Exception e) {
					}
					%>

					<c:if test="<%= Validator.isNotNull(friendlyURL) %>">
						<li class="page-settings">
							<a href="<%= friendlyURL %>"><liferay-ui:message key="view-staged-page" /></a>
						</li>
					</c:if>
				</c:when>
			</c:choose>

			<c:if test="<%= (liveGroup.isStagedRemotely() || group.isStagingGroup()) && (themeDisplay.getURLPublishToLive() != null) && GroupPermissionUtil.contains(permissionChecker, liveGroup.getGroupId(), ActionKeys.PUBLISH_STAGING) %>">

				<%
				PortletURL publishToLiveURL = themeDisplay.getURLPublishToLive();
				%>

				<li class="page-settings">
					<a href="javascript:Liferay.LayoutExporter.publishToLive({title: '<%= UnicodeLanguageUtil.get(pageContext, publishNowDialogTitle) %>', url: '<%= publishToLiveURL.toString() %>'});"><liferay-ui:message key="<%= publishNowDialogTitle %>" /></a>
				</li>

				<%
				publishToLiveURL.setParameter("schedule", String.valueOf(true));
				%>

				<li class="page-settings">
					<a href="javascript:Liferay.LayoutExporter.publishToLive({title: '<%= UnicodeLanguageUtil.get(pageContext, publishScheduleDialogTitle) %>', url: '<%= publishToLiveURL.toString() %>'});"><liferay-ui:message key="<%= publishScheduleDialogTitle %>" /></a>
				</li>
			</c:if>
		</ul>
	</c:if>
</c:if>