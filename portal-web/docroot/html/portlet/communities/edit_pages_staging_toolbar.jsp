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

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
Group liveGroup = (Group)request.getAttribute("edit_pages.jsp-liveGroup");

long groupId = ((Long)request.getAttribute("edit_pages.jsp-groupId")).longValue();
long liveGroupId = ((Long)request.getAttribute("edit_pages.jsp-liveGroupId")).longValue();

PortletURL portletURL = (PortletURL)request.getAttribute("edit_pages.jsp-portletURL");

long selPlid = ParamUtil.getLong(request, "selPlid", LayoutConstants.DEFAULT_PLID);

PortletURL publishToLiveURL = renderResponse.createRenderURL();

publishToLiveURL.setWindowState(LiferayWindowState.EXCLUSIVE);
publishToLiveURL.setPortletMode(PortletMode.VIEW);

publishToLiveURL.setParameter("struts_action", "/communities/publish_pages");
publishToLiveURL.setParameter(Constants.CMD, "publish_to_live");
publishToLiveURL.setParameter("pagesRedirect", portletURL.toString() + "&" + renderResponse.getNamespace() + "selPlid=" + selPlid);
publishToLiveURL.setParameter("groupId", String.valueOf(groupId));
publishToLiveURL.setParameter("selPlid", String.valueOf(selPlid));

PortletURL publishToRemoteURL = PortletURLUtil.clone(publishToLiveURL, renderResponse);

publishToRemoteURL.setParameter(Constants.CMD, "publish_to_remote");
%>

<liferay-ui:error exception="<%= RemoteExportException.class %>">

	<%
	RemoteExportException ree = (RemoteExportException)errorException;
	%>

	<c:if test="<%= ree.getType() == RemoteExportException.BAD_CONNECTION %>">
		<%= LanguageUtil.format(pageContext, "could-not-connect-to-address-x.-please-verify-that-the-specified-port-is-correct-and-that-the-remote-server-is-configured-to-accept-requests-from-this-server", "<em>" + ree.getURL() + "</em>") %>
	</c:if>
	<c:if test="<%= ree.getType() == RemoteExportException.NO_GROUP %>">
		<%= LanguageUtil.format(pageContext, "remote-group-with-id-x-does-not-exist", ree.getGroupId()) %>
	</c:if>
	<c:if test="<%= ree.getType() == RemoteExportException.NO_LAYOUTS %>">
		<liferay-ui:message key="no-pages-are-selected-for-export" />
	</c:if>
</liferay-ui:error>

<div class="portlet-msg-alert">
	<liferay-ui:message key="the-staging-environment-is-activated-changes-have-to-be-published-to-make-them-available-to-end-users" />

	<div id="stagingToobar"></div>
</div>

<aui:script use="aui-toolbar">
	var stagingToolbar = new A.Toolbar(
		{
			activeState: false,
			children: [

		<c:choose>
			<c:when test="<%= liveGroup.isWorkflowEnabled() %>">
				<c:if test="<%= selPlid > 0 %>">

					<%
					TasksProposal proposal = null;

					try {
						proposal = TasksProposalLocalServiceUtil.getProposal(Layout.class.getName(), String.valueOf(selPlid));
					}
					catch (NoSuchProposalException nspe) {
					}
					%>

					<c:if test="<%= proposal == null %>">
						<portlet:actionURL var="proposalURL">
							<portlet:param name="struts_action" value="/communities/edit_proposal" />
							<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="pagesRedirect" value='<%= portletURL.toString() + "&" + renderResponse.getNamespace() + "selPlid=" + selPlid %>' />
							<portlet:param name="groupId" value="<%= String.valueOf(liveGroupId) %>" />
							<portlet:param name="className" value="<%= Layout.class.getName() %>" />
							<portlet:param name="classPK" value="<%= String.valueOf(selPlid) %>" />
						</portlet:actionURL>

						<%
						int workflowStages = ParamUtil.getInteger(request, "workflowStages", liveGroup.getWorkflowStages());
						String[] workflowRoleNames = StringUtil.split(ParamUtil.getString(request, "workflowRoleNames", liveGroup.getWorkflowRoleNames()));

						JSONArray jsonReviewers = JSONFactoryUtil.createJSONArray();

						Role role = RoleLocalServiceUtil.getRole(company.getCompanyId(), workflowRoleNames[0]);

						LinkedHashMap userParams = new LinkedHashMap();

						if (liveGroup.isOrganization()) {
							userParams.put("usersOrgs", new Long(liveGroup.getOrganizationId()));
						}
						else {
							userParams.put("usersGroups", new Long(liveGroupId));
						}

						userParams.put("userGroupRole", new Long[] {new Long(liveGroupId), new Long(role.getRoleId())});

						List<User> reviewers = UserLocalServiceUtil.search(company.getCompanyId(), null, null, userParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS, (OrderByComparator)null);

						if (reviewers.isEmpty()) {
							if (liveGroup.isCommunity()) {
								role = RoleLocalServiceUtil.getRole(company.getCompanyId(), RoleConstants.COMMUNITY_OWNER);
							}
							else {
								role = RoleLocalServiceUtil.getRole(company.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);
							}

							userParams.put("userGroupRole", new Long[] {new Long(liveGroupId), new Long(role.getRoleId())});

							reviewers = UserLocalServiceUtil.search(company.getCompanyId(), null, null, userParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS, (OrderByComparator)null);
						}

						for (User reviewer : reviewers) {
							JSONObject jsonReviewer = JSONFactoryUtil.createJSONObject();

							jsonReviewer.put("userId", reviewer.getUserId());
							jsonReviewer.put("fullName", reviewer.getFullName());

							jsonReviewers.put(jsonReviewer);
						}
						%>

						{
							label: '<liferay-ui:message key="propose-publication" />',
							icon: 'clipboard',
							handler: function (event) {
								Liferay.LayoutExporter.proposeLayout(
									{
										url: '<%= proposalURL.toString().replace('"','\'') %>',
										namespace: '<portlet:namespace />',
										reviewers: <%= StringUtil.replace(jsonReviewers.toString(), '"', '\'') %>,
										title: '<liferay-ui:message key="propose-publication" />'
									}
								);
							}
						},

					</c:if>
				</c:if>

				<c:if test="<%= liveGroup.isStagedRemotely() && GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.MANAGE_STAGING) %>">
					{
						label: '<liferay-ui:message key="publish-to-remote-live" />',
						icon: 'arrowreturnthick-1-t',
						handler: function (event) {
							Liferay.LayoutExporter.publishToLive(
								{
									title: '<liferay-ui:message key="publish-to-remote-live" />',
									url: '<%= publishToRemoteURL %>'
								}
							);
						}
					},
				</c:if>
			</c:when>
			<c:otherwise>
				<c:if test="<%= !liveGroup.isStagedRemotely() %>">
					{
						label: '<liferay-ui:message key="publish-to-live-now" />',
						icon: 'arrowreturnthick-1-t',
						handler: function (event) {
							Liferay.LayoutExporter.publishToLive(
								{
									title: '<liferay-ui:message key="publish-to-live-now" />',
									url: '<%= publishToLiveURL %>'
								}
							);
						}
					},

					<%
					publishToLiveURL.setParameter("schedule", String.valueOf(true));
					%>

					{
						label: '<liferay-ui:message key="schedule-publication-to-live" />',
						icon: 'clock',
						handler: function (event) {
							Liferay.LayoutExporter.publishToLive(
								{
									title: '<liferay-ui:message key="schedule-publication-to-live" />',
									url: '<%= publishToLiveURL %>'
								}
							);
						}
					},
				</c:if>

				<c:if test="<%= liveGroup.isStagedRemotely() %>">
					{
						label: '<liferay-ui:message key="publish-to-remote-live-now" />',
						icon: 'arrowreturnthick-1-t',
						handler: function (event) {
							Liferay.LayoutExporter.publishToLive(
								{
									title: '<liferay-ui:message key="publish-to-remote-live-now" />',
									url: '<%= publishToRemoteURL %>'
								}
							);
						}
					},

					<%
					publishToRemoteURL.setParameter("schedule", String.valueOf(true));
					%>

					{
						label: '<liferay-ui:message key="schedule-publication-to-remote-live" />',
						icon: 'clock',
						handler: function (event) {
							Liferay.LayoutExporter.publishToLive(
								{
									title: '<liferay-ui:message key="schedule-publication-to-remote-live" />',
									url: '<%= publishToRemoteURL %>'
								}
							);
						}
					},
				</c:if>
			</c:otherwise>
		</c:choose>

		<c:if test="<%= !liveGroup.isStagedRemotely() %>">
			<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>" var="importLayoutsURL">
				<portlet:param name="struts_action" value="/communities/publish_pages" />
				<portlet:param name="<%= Constants.CMD %>" value="copy_from_live" />
				<portlet:param name="pagesRedirect" value='<%= portletURL.toString() + "&" + renderResponse.getNamespace() + "selPlid=" + selPlid %>' />
				<portlet:param name="groupId" value="<%= String.valueOf(liveGroupId) %>" />
			</portlet:renderURL>

			{
				label: '<liferay-ui:message key="copy-from-live" />',
				icon: 'copy',
				handler: function (event) {
					Liferay.LayoutExporter.publishToLive(
						{
							title: '<liferay-ui:message key="copy-from-live" />',
							url: '<%= importLayoutsURL %>'
						}
					);
				}
			}

		</c:if>


			]
		}
	).render('#stagingToobar');
</aui:script>