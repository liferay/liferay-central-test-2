<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
%>

<%@ include file="/html/taglib/init.jsp" %>

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

				<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, liveGroup.getGroupId(), ActionKeys.MANAGE_LAYOUTS) || LayoutPermissionUtil.contains(permissionChecker, layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(), ActionKeys.UPDATE) %>">

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

								userParams.put("userGroupRole", new Long[] {new Long(liveGroup.getGroupId()), new Long(role.getRoleId())});

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
									<a href="javascript:Liferay.LayoutExporter.proposeLayout({url: '<%= proposePublicationURL.toString() %>', namespace: '<%= PortalUtil.getPortletNamespace(PortletKeys.LAYOUT_MANAGEMENT) %>', reviewers: <%= StringUtil.replace(jsonReviewers.toString(), '"', '\'') %>, title: '<liferay-ui:message key="proposal-description" />'});"><liferay-ui:message key="propose-publication" /></a>
								</li>
							</c:if>
						</c:when>
						<c:when test="<%= themeDisplay.getURLPublishToLive() != null %>">
							<li class="page-settings">
								<a href="javascript:Liferay.LayoutExporter.publishToLive({url: '<%= themeDisplay.getURLPublishToLive().toString() %>', title: '<%= UnicodeLanguageUtil.get(pageContext, "publish-to-live") %>'});"><liferay-ui:message key="publish-to-live" /></a>
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