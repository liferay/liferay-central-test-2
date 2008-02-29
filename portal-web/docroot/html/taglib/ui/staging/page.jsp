<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

<%@ page import="com.liferay.portlet.tasks.NoSuchProposalException"%>
<%@ page import="com.liferay.portlet.tasks.model.TasksProposal"%>
<%@ page import="com.liferay.portlet.tasks.service.TasksProposalLocalServiceUtil"%>
<%@ page import="com.liferay.portlet.tasks.util.TasksUtil"%>

<c:if test="<%= themeDisplay.isShowStagingIcon() %>">

	<%
	Group group = layout.getGroup();

	Group stagingGroup = null;

	Group liveGroup = null;

	if (group.isStagingGroup()) {
		stagingGroup = group;
		liveGroup = group.getLiveGroup();
	}
	else {
		stagingGroup = group.getStagingGroup();
		liveGroup = group;
	}

	Layout stagedLayout = LayoutLocalServiceUtil.getLayout(stagingGroup.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId());

	String friendlyURL = null;

	boolean hasManageLayoutsPermission = GroupPermissionUtil.contains(permissionChecker, liveGroup.getGroupId(), ActionKeys.MANAGE_LAYOUTS);

	Properties groupTypeSettings = liveGroup.getTypeSettingsProperties();

	boolean managedStaging = PropertiesParamUtil.getBoolean(groupTypeSettings, request, GroupImpl.MANAGED_STAGING);
	%>

	<ul>
		<c:choose>
			<c:when test="<%= group.isStagingGroup() %>">

				<%
				boolean layoutProposed = false;

				try {
					Layout liveLayout = LayoutLocalServiceUtil.getLayout(liveGroup.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId());

					friendlyURL = PortalUtil.getLayoutFriendlyURL(liveLayout, themeDisplay);
				}
				catch (Exception e) {
				}

				if (managedStaging) {
					TasksProposal proposal = null;

					try {
						proposal = TasksProposalLocalServiceUtil.getProposal(Layout.class.getName(), layout.getPlid());
					}
					catch (NoSuchProposalException nspe) {
					}

					if (proposal != null) {
						layoutProposed = true;
					}
				}
				%>

				<c:if test="<%= Validator.isNotNull(friendlyURL) %>">
					<li class="page-settings">
						<a href="<%= friendlyURL %>"><liferay-ui:message key="view-live-page" /></a>
					</li>
				</c:if>

				<c:if test="<%= hasManageLayoutsPermission %>">
					<c:choose>
						<c:when test="<%= managedStaging %>">
							<c:if test="<%= !layoutProposed %>">
								<%
								PortletURL proposePublicationURL = new PortletURLImpl(request, PortletKeys.LAYOUT_MANAGEMENT, layout.getPlid(), PortletRequest.ACTION_PHASE);

								proposePublicationURL.setWindowState(WindowState.MAXIMIZED);
								proposePublicationURL.setPortletMode(PortletMode.VIEW);
								proposePublicationURL.setParameter("struts_action", "/layout_management/edit_proposal");
								proposePublicationURL.setParameter("redirect", currentURL);
								proposePublicationURL.setParameter(Constants.CMD, Constants.ADD);
								proposePublicationURL.setParameter("groupId", String.valueOf(liveGroup.getGroupId()));
								proposePublicationURL.setParameter("liveGroupId", String.valueOf(liveGroup.getGroupId()));
								proposePublicationURL.setParameter("selPlid", String.valueOf(layout.getPlid()));

								String[] approvalRoleNames = TasksUtil.getApprovalRoleNames(company.getCompanyId(), liveGroup.getGroupId());

								JSONArray jsonReviewers = new JSONArray();

								Role reviewerRole = RoleLocalServiceUtil.getRole(company.getCompanyId(), approvalRoleNames[0]);

								LinkedHashMap userParams = new LinkedHashMap();
								userParams.put("usersGroups", new Long(liveGroup.getGroupId()));
								userParams.put("userGroupRole", new Long[] {new Long(liveGroup.getGroupId()), new Long(reviewerRole.getRoleId())});
								List<User> reviewers = UserLocalServiceUtil.search(company.getCompanyId(), null, null, userParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

								for (User reviewerUser : reviewers) {
									JSONObject jsonReviewer = new JSONObject();
									jsonReviewer.put("userId", reviewerUser.getUserId());
									jsonReviewer.put("fullName", reviewerUser.getFullName());

									jsonReviewers.put(jsonReviewer);
								}

								if (jsonReviewers.length() <= 0) {
									Role ownerRole = RoleLocalServiceUtil.getRole(company.getCompanyId(), "Community Owner");

									userParams = new LinkedHashMap();
									userParams.put("usersGroups", new Long(liveGroup.getGroupId()));
									userParams.put("userGroupRole", new Long[] {new Long(liveGroup.getGroupId()), new Long(ownerRole.getRoleId())});
									reviewers = UserLocalServiceUtil.search(company.getCompanyId(), null, null, userParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

									for (User reviewerUser : reviewers) {
										JSONObject jsonReviewer = new JSONObject();
										jsonReviewer.put("userId", reviewerUser.getUserId());
										jsonReviewer.put("fullName", reviewerUser.getFullName());

										jsonReviewers.put(jsonReviewer);
									}
								}
								%>

								<li class="page-settings">
									<a href="javascript: Liferay.LayoutExporter.proposeLayout({url: '<%= proposePublicationURL.toString() %>', namespace: '_<%= PortletKeys.LAYOUT_MANAGEMENT %>_', reviewers: <%= jsonReviewers.toString().replace('"','\'') %>, title: '<%= LanguageUtil.get(pageContext, "proposal-description") %>'});"><liferay-ui:message key="propose-publication" /></a>
								</li>
							</c:if>
						</c:when>
						<c:when test="<%= themeDisplay.getURLPublishToLive() != null %>">
							<li class="page-settings <%= (!managedStaging ? "group-end" : "") %>">
								<a href="javascript: Liferay.LayoutExporter.publishToLive({url: '<%= themeDisplay.getURLPublishToLive().toString() %>', messageId: 'publish-to-live'});"><liferay-ui:message key="publish-to-live" /></a>
							</li>
						</c:when>
					</c:choose>
				</c:if>
			</c:when>
			<c:otherwise>

				<%
				try {
					friendlyURL = PortalUtil.getLayoutFriendlyURL(stagedLayout, themeDisplay);
				}
				catch (Exception e) {
				}
				%>

				<c:if test="<%= Validator.isNotNull(friendlyURL) %>">
					<li class="page-settings <%= (!managedStaging ? "group-end" : "") %>">
						<a href="<%= friendlyURL %>"><liferay-ui:message key="view-staged-page" /></a>
					</li>
				</c:if>
			</c:otherwise>
		</c:choose>

		<c:if test="<%= managedStaging %>">
			<li class="page-settings group-end">
				<%
				PortletURL proposalsURL = new PortletURLImpl(request, PortletKeys.LAYOUT_MANAGEMENT, stagedLayout.getPlid(), PortletRequest.RENDER_PHASE);

				proposalsURL.setWindowState(WindowState.MAXIMIZED);
				proposalsURL.setPortletMode(PortletMode.VIEW);
				proposalsURL.setParameter("struts_action", "/layout_management/edit_pages");
				proposalsURL.setParameter("redirect", currentURL);
				proposalsURL.setParameter("groupId", String.valueOf(liveGroup.getGroupId()));
				proposalsURL.setParameter("tabs2", "proposals");
				%>
				<a href="<%= proposalsURL.toString() %>"><liferay-ui:message key="proposals" /></a>
			</li>
		</c:if>

		<%
		PortletURL portletURL = new PortletURLImpl(request, PortletKeys.MY_PLACES, plid.longValue(), PortletRequest.ACTION_PHASE);

		portletURL.setWindowState(WindowState.NORMAL);
		portletURL.setPortletMode(PortletMode.VIEW);

		portletURL.setParameter("struts_action", "/my_places/view");

		boolean organizationCommunity = stagingGroup.isOrganization();
		boolean regularCommunity = stagingGroup.isCommunity();
		int publicLayoutsPageCount = stagingGroup.getPublicLayoutsPageCount();
		int privateLayoutsPageCount = stagingGroup.getPrivateLayoutsPageCount();

		Organization organization = null;

		boolean showPublicPlace = true;

		if (publicLayoutsPageCount == 0) {
			if (organizationCommunity) {
				showPublicPlace = PropsValues.MY_PLACES_SHOW_ORGANIZATION_PUBLIC_SITES_WITH_NO_LAYOUTS;
			}
			else if (regularCommunity) {
				showPublicPlace = PropsValues.MY_PLACES_SHOW_COMMUNITY_PUBLIC_SITES_WITH_NO_LAYOUTS;
			}
		}

		boolean showPrivatePlace = true;

		if (privateLayoutsPageCount == 0) {
			if (organizationCommunity) {
				showPrivatePlace = PropsValues.MY_PLACES_SHOW_ORGANIZATION_PRIVATE_SITES_WITH_NO_LAYOUTS;
			}
			else if (regularCommunity) {
				showPrivatePlace = PropsValues.MY_PLACES_SHOW_COMMUNITY_PRIVATE_SITES_WITH_NO_LAYOUTS;
			}
		}
		%>

		<c:if test="<%= showPublicPlace || showPrivatePlace %>">

			<%
			boolean selectedCommunity = false;

 				if (layout != null) {
  				if (layout.getGroupId() == stagingGroup.getGroupId()) {
					selectedCommunity = true;
				}
  			}
			%>

			<%
			portletURL.setParameter("groupId", String.valueOf(stagingGroup.getGroupId()));
			portletURL.setParameter("privateLayout", Boolean.FALSE.toString());

			boolean selectedPlace = false;

			if (layout != null) {
				selectedPlace = !layout.isPrivateLayout() && (layout.getGroupId() == stagingGroup.getGroupId());
			}
			%>

			<c:if test="<%= showPublicPlace %>">
				<li class="public <%= selectedPlace ? "current" : "" %>">
					<a href="<%= publicLayoutsPageCount > 0 ? "javascript: submitForm(document.hrefFm, '" + portletURL.toString() + "');" : "javascript: ;" %>"

					><liferay-ui:message key="public-pages" /> <span class="page-count">(<%= publicLayoutsPageCount %>)</span></a>
				</li>
			</c:if>

			<%
			portletURL.setParameter("groupId", String.valueOf(stagingGroup.getGroupId()));
			portletURL.setParameter("privateLayout", Boolean.TRUE.toString());

			selectedPlace = false;

			if (layout != null) {
				selectedPlace = layout.isPrivateLayout() && (layout.getGroupId() == stagingGroup.getGroupId());
			}
			%>

			<c:if test="<%= showPrivatePlace %>">
				<li class="private <%= selectedPlace ? "current" : "" %>">
					<a href="<%= privateLayoutsPageCount > 0 ? "javascript: submitForm(document.hrefFm, '" + portletURL.toString() + "');" : "javascript: ;" %>"

					><liferay-ui:message key="private-pages" /> <span class="page-count">(<%= privateLayoutsPageCount %>)</span></a>
				</li>
			</c:if>
		</c:if>
	</ul>
</c:if>