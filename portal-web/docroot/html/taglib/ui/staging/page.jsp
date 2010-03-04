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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portal.model.RoleConstants" %>
<%@ page import="com.liferay.portlet.tasks.NoSuchProposalException" %>
<%@ page import="com.liferay.portlet.tasks.model.TasksProposal" %>
<%@ page import="com.liferay.portlet.tasks.service.TasksProposalLocalServiceUtil" %>

<c:if test="<%= themeDisplay.isShowStagingIcon() %>">

	<%
	Group group = themeDisplay.getScopeGroup();

	if (themeDisplay.getScopeGroup().isLayout()) {
		group = layout.getGroup();
	}

	Group liveGroup = null;
	Group stagingGroup = null;

	if (group.isStagingGroup()) {
		liveGroup = group.getLiveGroup();
		stagingGroup = group;
	}
	else {
		liveGroup = group;
		stagingGroup = group.getStagingGroup();
	}

	boolean workflowEnabled = liveGroup.isWorkflowEnabled();
	%>

	<ul>
		<c:choose>
			<c:when test="<%= group.isStagingGroup() %>">

				<%
				String friendlyURL = null;

				try {
					Layout liveLayout = LayoutLocalServiceUtil.getLayout(liveGroup.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId());

					friendlyURL = PortalUtil.getLayoutFriendlyURL(liveLayout, themeDisplay);
				}
				catch (Exception e) {
				}
				%>

				<c:if test="<%= Validator.isNotNull(friendlyURL) %>">
					<li class="page-settings">
						<a href="<%= friendlyURL %>"><liferay-ui:message key="view-live-page" /></a>
					</li>
				</c:if>

				<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, liveGroup.getGroupId(), ActionKeys.MANAGE_LAYOUTS) || GroupPermissionUtil.contains(permissionChecker, liveGroup.getGroupId(), ActionKeys.PUBLISH_STAGING) || LayoutPermissionUtil.contains(permissionChecker, layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(), ActionKeys.UPDATE) %>">

					<%
					TasksProposal proposal = null;

					if (workflowEnabled) {
						try {
							proposal = TasksProposalLocalServiceUtil.getProposal(Layout.class.getName(), String.valueOf(layout.getPlid()));
						}
						catch (NoSuchProposalException nspe) {
						}
					}
					%>

					<c:choose>
						<c:when test="<%= workflowEnabled %>">
							<c:if test="<%= proposal == null %>">

								<%
								PortletURL proposePublicationURL = new PortletURLImpl(request, PortletKeys.LAYOUT_MANAGEMENT, layout.getPlid(), PortletRequest.ACTION_PHASE);

								proposePublicationURL.setWindowState(WindowState.MAXIMIZED);
								proposePublicationURL.setPortletMode(PortletMode.VIEW);

								proposePublicationURL.setParameter("struts_action", "/layout_management/edit_proposal");
								proposePublicationURL.setParameter(Constants.CMD, Constants.ADD);
								proposePublicationURL.setParameter("redirect", currentURL);
								proposePublicationURL.setParameter("groupId", String.valueOf(liveGroup.getGroupId()));
								proposePublicationURL.setParameter("className", Layout.class.getName());
								proposePublicationURL.setParameter("classPK", String.valueOf(layout.getPlid()));

								String[] workflowRoleNames = StringUtil.split(liveGroup.getWorkflowRoleNames());

								JSONArray jsonReviewers = JSONFactoryUtil.createJSONArray();

								Role role = RoleLocalServiceUtil.getRole(company.getCompanyId(), workflowRoleNames[0]);

								LinkedHashMap userParams = new LinkedHashMap();

								if (liveGroup.isOrganization()) {
									userParams.put("usersOrgs", new Long(liveGroup.getClassPK()));
								}
								else {
									userParams.put("usersGroups", new Long(liveGroup.getGroupId()));
								}

								String name = role.getName();

								if (!name.equals(RoleConstants.COMMUNITY_MEMBER) && !name.equals(RoleConstants.ORGANIZATION_MEMBER)) {
									userParams.put("userGroupRole", new Long[] {new Long(liveGroup.getGroupId()), new Long(role.getRoleId())});
								}

								List<User> reviewers = UserLocalServiceUtil.search(company.getCompanyId(), null, null, userParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS, (OrderByComparator)null);

								if (reviewers.isEmpty()) {
									if (liveGroup.isCommunity()) {
										role = RoleLocalServiceUtil.getRole(company.getCompanyId(), RoleConstants.COMMUNITY_OWNER);
									}
									else {
										role = RoleLocalServiceUtil.getRole(company.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);
									}

									userParams.put("userGroupRole", new Long[] {new Long(liveGroup.getGroupId()), new Long(role.getRoleId())});

									reviewers = UserLocalServiceUtil.search(company.getCompanyId(), null, null, userParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS, (OrderByComparator)null);
								}

								for (User reviewer : reviewers) {
									JSONObject jsonReviewer = JSONFactoryUtil.createJSONObject();

									jsonReviewer.put("userId", reviewer.getUserId());
									jsonReviewer.put("fullName", reviewer.getFullName());

									jsonReviewers.put(jsonReviewer);
								}
								%>

								<li class="page-settings">
									<a href="javascript:Liferay.LayoutExporter.proposeLayout({namespace: '<%= PortalUtil.getPortletNamespace(PortletKeys.LAYOUT_MANAGEMENT) %>', reviewers: <%= StringUtil.replace(jsonReviewers.toString(), '"', '\'') %>, title: '<liferay-ui:message key="proposal-description" />', url: '<%= proposePublicationURL.toString() %>'});"><liferay-ui:message key="propose-publication" /></a>
								</li>
							</c:if>
						</c:when>
						<c:when test="<%= themeDisplay.getURLPublishToLive() != null %>">
							<li class="page-settings">
								<a href="javascript:Liferay.LayoutExporter.publishToLive({title: '<%= UnicodeLanguageUtil.get(pageContext, "publish-to-live") %>', url: '<%= themeDisplay.getURLPublishToLive().toString() %>'});"><liferay-ui:message key="publish-to-live" /></a>
							</li>
						</c:when>
					</c:choose>
				</c:if>
			</c:when>
			<c:otherwise>

				<%
				String friendlyURL = null;

				try {
					Layout stagedLayout = LayoutLocalServiceUtil.getLayout(stagingGroup.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId());

					friendlyURL = PortalUtil.getLayoutFriendlyURL(stagedLayout, themeDisplay);
				}
				catch (Exception e) {
				}
				%>

				<c:if test="<%= Validator.isNotNull(friendlyURL) %>">
					<li class="page-settings">
						<a href="<%= friendlyURL %>"><liferay-ui:message key="view-staged-page" /></a>
					</li>
				</c:if>
			</c:otherwise>
		</c:choose>

		<c:if test="<%= workflowEnabled %>">
			<li class="page-settings">

				<%
				Layout stagedLayout = LayoutLocalServiceUtil.getLayout(stagingGroup.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId());

				PortletURL viewProposalsURL = new PortletURLImpl(request, PortletKeys.LAYOUT_MANAGEMENT, stagedLayout.getPlid(), PortletRequest.RENDER_PHASE);

				viewProposalsURL.setWindowState(WindowState.MAXIMIZED);
				viewProposalsURL.setPortletMode(PortletMode.VIEW);

				viewProposalsURL.setParameter("struts_action", "/layout_management/edit_pages");
				viewProposalsURL.setParameter("tabs2", "proposals");
				viewProposalsURL.setParameter("redirect", currentURL);
				viewProposalsURL.setParameter("groupId", String.valueOf(liveGroup.getGroupId()));
				%>

				<a href="<%= viewProposalsURL.toString() %>"><liferay-ui:message key="view-proposals" /></a>
			</li>
		</c:if>
	</ul>
</c:if>