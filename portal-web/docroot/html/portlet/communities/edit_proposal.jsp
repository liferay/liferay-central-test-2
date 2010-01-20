<%/**
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

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

Group group = (Group)request.getAttribute(WebKeys.GROUP);

Group stagingGroup = group.getStagingGroup();

long groupId = group.getGroupId();

long stagingGroupId = stagingGroup.getGroupId();

int workflowStages = group.getWorkflowStages();
String[] workflowRoleNames = StringUtil.split(group.getWorkflowRoleNames());

TasksProposal proposal = (TasksProposal)request.getAttribute(WebKeys.TASKS_PROPOSAL);

long proposalId = proposal.getProposalId();

String className = PortalUtil.getClassName(proposal.getClassNameId());
String classPK = proposal.getClassPK();

Calendar dueDate = CalendarFactoryUtil.getCalendar(timeZone, locale);

dueDate.add(Calendar.MONTH, 9);

if (proposal.getDueDate() != null) {
	dueDate.setTime(proposal.getDueDate());
}

TasksReview review = null;

try {
	review = TasksReviewLocalServiceUtil.getReview(user.getUserId(), proposalId);
}
catch (NoSuchReviewException nsre) {
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/communities/edit_proposal");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("groupId", String.valueOf(groupId));
portletURL.setParameter("proposalId", String.valueOf(proposalId));
%>

<aui:script>
	function <portlet:namespace />approveProposal() {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-approve-this-proposal") %>')) {
			document.<portlet:namespace />fm1.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.APPROVE %>";
			submitForm(document.<portlet:namespace />fm1);
		}
	}

	function <portlet:namespace />rejectProposal() {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-reject-this-proposal") %>')) {
			document.<portlet:namespace />fm1.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.REJECT %>";
			submitForm(document.<portlet:namespace />fm1);
		}
	}

	function <portlet:namespace />saveProposal() {
		document.<portlet:namespace />fm1.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.UPDATE %>';

		<%
		for (int i = 2; i <= workflowStages; i++) {
			String workflowRoleName = workflowRoleNames[i - 1];
		%>

			document.<portlet:namespace />fm1.<portlet:namespace />reviewUserIds_<%= i %>.value = Liferay.Util.listSelect(document.<portlet:namespace />fm1.<portlet:namespace />current_reviewers_<%= i %>);

		<%
		}
		%>

		submitForm(document.<portlet:namespace />fm1);
	}
</aui:script>

<portlet:actionURL var="editProposalURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
	<portlet:param name="struts_action" value="/communities/edit_proposal" />
</portlet:actionURL>

<aui:form action="<%= editProposalURL %>" method="post" name="fm1" onSubmit='<%= renderResponse.getNamespace() + "saveProposal(); return false;" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="proposalId" type="hidden" value="<%= proposalId %>" />

	<%
	for (int i = 2; i <= workflowStages; i++) {
		String workflowRoleName = workflowRoleNames[i - 1];
	%>

		<aui:input name="reviewUserIds_<%= i %>" type="hidden" />

	<%
	}
	%>

	<liferay-ui:tabs
		names="proposal"
		url="<%= portletURL.toString() %>"
		backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
	/>

	<liferay-ui:error exception="<%= DuplicateReviewUserIdException.class %>" message="users-cannot-be-assigned-to-more-than-one-stage" />

	<aui:model-context bean="<%= proposal %>" model="<%= TasksProposal.class %>" />

	<aui:fieldset>
		<aui:field-wrapper label="user">
			<%= PortalUtil.getUserName(proposal.getUserId(), proposal.getUserName()) %>
		</aui:field-wrapper>

		<aui:field-wrapper label="name">
			<%= proposal.getName() %>
		</aui:field-wrapper>

		<aui:field-wrapper label="type">
			<%= LanguageUtil.get(pageContext, "model.resource." + className) %>
		</aui:field-wrapper>

		<aui:field-wrapper label="id">
			<%= classPK %>
		</aui:field-wrapper>

		<aui:field-wrapper label="status">
			<%= proposal.getStatus(locale) %>
		</aui:field-wrapper>

		<aui:input name="description" />

		<aui:input formName="fm1" name="dueDate" value="<%= dueDate %>" />
	</aui:fieldset>

	<c:if test="<%= (review != null) && (review.getStage() == 1) && GroupPermissionUtil.contains(permissionChecker, groupId, ActionKeys.ASSIGN_REVIEWER) %>">
		<br />

		<liferay-ui:toggle-area
			id="toggle_id_communities_edit_proposal_reviewers"
			showMessage='<%= LanguageUtil.get(pageContext, "show-assign-reviewers") + " &raquo;" %>'
			hideMessage='<%= "&laquo; " + LanguageUtil.get(pageContext, "hide-assign-reviewers") %>'
		>
			<table class="lfr-table">

			<%
			for (int i = 2; i <= workflowStages; i++) {
				String workflowRoleName = workflowRoleNames[i - 1];
			%>

				<tr>
					<td colspan="3">
						<br />
					</td>
				</tr>
				<tr>
					<td>
						<liferay-ui:message key="stage" /> <%= i + 1 %>:

						<%= workflowRoleName %>
					</td>
					<td>

						<%

						// Left list

						List leftList = new ArrayList();

						String reviewUserIdsParam = request.getParameter("reviewUserIds_" + i);

						if (reviewUserIdsParam == null) {
							List<TasksReview> reviews = TasksReviewLocalServiceUtil.getReviews(proposal.getProposalId(), i);

							for (TasksReview curReview : reviews) {
								leftList.add(new KeyValuePair(String.valueOf(curReview.getUserId()), PortalUtil.getUserName(curReview.getUserId(), curReview.getUserName())));
							}
						}
						else {
							long[] reviewUserIds = StringUtil.split(reviewUserIdsParam, 0L);

							for (long reviewUserId : reviewUserIds) {
								User reviewUser = UserLocalServiceUtil.getUserById(reviewUserId);

								leftList.add(new KeyValuePair(String.valueOf(reviewUser.getUserId()), PortalUtil.getUserName(reviewUser.getUserId(), reviewUser.getFullName())));
							}
						}

						leftList = ListUtil.sort(leftList, new KeyValuePairComparator(false, true));

						// Right list

						List rightList = new ArrayList();

						Role role = RoleLocalServiceUtil.getRole(company.getCompanyId(), workflowRoleName);

						LinkedHashMap userParams = new LinkedHashMap();

						if (group.isOrganization()) {
							userParams.put("usersOrgs", new Long(group.getClassPK()));
						}
						else {
							userParams.put("usersGroups", new Long(groupId));
						}

						userParams.put("userGroupRole", new Long[] {new Long(groupId), new Long(role.getRoleId())});

						List<User> reviewers = UserLocalServiceUtil.search(company.getCompanyId(), null, null, userParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS, (OrderByComparator)null);

						for (User reviewer : reviewers) {
							KeyValuePair kvp = new KeyValuePair(String.valueOf(reviewer.getUserId()), reviewer.getFullName());

							if (!leftList.contains(kvp)) {
								rightList.add(kvp);
							}
						}

						rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
						%>

						<liferay-ui:input-move-boxes
							formName="fm1"
							leftTitle="current"
							rightTitle="available"
							leftBoxName='<%= "current_reviewers_" + i %>'
							rightBoxName='<%= "available_reviewers_" + i %>'
							leftList="<%= leftList %>"
							rightList="<%= rightList %>"
						/>
					</td>
				</tr>

			<%
			}
			%>

			</table>
		</liferay-ui:toggle-area>
	</c:if>

	<br />

	<liferay-ui:toggle-area
		id="toggle_id_communities_edit_proposal_activities"
		showMessage='<%= LanguageUtil.get(pageContext, "show-activities") + " &raquo;" %>'
		hideMessage='<%= "&laquo; " + LanguageUtil.get(pageContext, "hide-activities") %>'
		defaultShowContent="<%= false %>"
	>
		<br />

		<liferay-ui:social-activities
			className="<%= TasksProposal.class.getName() %>"
			classPK="<%= proposalId %>"
		/>
	</liferay-ui:toggle-area>

	<br />

	<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, groupId, ActionKeys.ASSIGN_REVIEWER) || GroupPermissionUtil.contains(permissionChecker, groupId, ActionKeys.MANAGE_STAGING) || GroupPermissionUtil.contains(permissionChecker, groupId, ActionKeys.PUBLISH_STAGING) || TasksProposalPermission.contains(permissionChecker, proposalId, ActionKeys.UPDATE) %>">
		<aui:button type="submit" />
	</c:if>

	<%
	PortletURL publishToLiveURL = renderResponse.createRenderURL();

	publishToLiveURL.setWindowState(LiferayWindowState.EXCLUSIVE);
	publishToLiveURL.setPortletMode(PortletMode.VIEW);

	publishToLiveURL.setParameter("pagesRedirect", redirect);
	publishToLiveURL.setParameter("groupId", String.valueOf(stagingGroupId));
	publishToLiveURL.setParameter("proposalId", String.valueOf(proposal.getProposalId()));

	long proposedLayoutPlid = LayoutConstants.DEFAULT_PLID;

	Layout proposedLayout = null;

	if (className.equals(Layout.class.getName())) {
		publishToLiveURL.setParameter("struts_action", "/communities/export_pages");

		proposedLayoutPlid = GetterUtil.getLong(proposal.getClassPK());

		proposedLayout = LayoutLocalServiceUtil.getLayout(proposedLayoutPlid);

		publishToLiveURL.setParameter("tabs2", proposedLayout.isPrivateLayout() ? "private-pages" : "public-pages");
		publishToLiveURL.setParameter("selPlid", String.valueOf(proposedLayoutPlid));
	}
	else if (className.equals(Portlet.class.getName())) {
		publishToLiveURL.setParameter("struts_action", "/communities/publish_portlet");

		proposedLayoutPlid = GetterUtil.getLong(classPK.substring(0, classPK.indexOf(PortletConstants.LAYOUT_SEPARATOR)));

		proposedLayout = LayoutLocalServiceUtil.getLayout(proposedLayoutPlid);
	}
	%>

	<aui:button onClick="window.open('<%= PortalUtil.getLayoutFriendlyURL(proposedLayout, themeDisplay) %>');" value="preview" />

	<c:choose>
		<c:when test="<%= review != null %>">
			<c:if test="<%= ((review.getStage() == workflowStages) && GroupPermissionUtil.contains(permissionChecker, groupId, ActionKeys.PUBLISH_STAGING)) || GroupPermissionUtil.contains(permissionChecker, groupId, ActionKeys.MANAGE_STAGING) %>">

				<%
				String taglibPublishToLiveURL = "Liferay.LayoutExporter.publishToLive({url: '" + publishToLiveURL.toString() + "', title: '" + UnicodeLanguageUtil.get(pageContext, "publish-to-live") + "'});";
				%>

				<aui:button onClick="<%= taglibPublishToLiveURL %>" value="publish-to-live" />
			</c:if>

			<c:choose>
				<c:when test="<%= review.isCompleted() %>">
					<c:if test="<%= review.isRejected() %>">
						<aui:button onClick='<%= renderResponse.getNamespace() + "approveProposal();" %>' value="approve" />
					</c:if>

					<c:if test="<%= !review.isRejected() %>">
						<aui:button onClick='<%= renderResponse.getNamespace() + "rejectProposal();" %>' value="reject" />
					</c:if>
				</c:when>
				<c:otherwise>
					<aui:button onClick='<%= renderResponse.getNamespace() + "approveProposal();" %>' value="approve" />

					<aui:button onClick='<%= renderResponse.getNamespace() + "rejectProposal();" %>' value="reject" />
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:when test="<%= (review == null) && GroupPermissionUtil.contains(permissionChecker, groupId, ActionKeys.MANAGE_STAGING) %>">

			<%
			String taglibPublishToLiveURL = "Liferay.LayoutExporter.publishToLive({url: '" + publishToLiveURL.toString() + "', title: '" + UnicodeLanguageUtil.get(pageContext, "publish-to-live") + "'});";
			%>

			<aui:button onClick="<%= taglibPublishToLiveURL %>" value="publish-to-live" />
		</c:when>
	</c:choose>

	<aui:button onClick="<%= redirect %>" type="cancel" />
</aui:form>

<br />

<liferay-ui:tabs names="reviewers" />

<%
List<String> headerNames = new ArrayList<String>();

headerNames.add("user");
headerNames.add("stage");
headerNames.add("status");
headerNames.add("review-date");

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, "no-reviewers-were-found");

List<TasksReview> results = TasksReviewLocalServiceUtil.getReviews(proposal.getProposalId());

results = ListUtil.sort(results, new ReviewUserNameComparator(true));

int total = results.size();

searchContainer.setTotal(total);
searchContainer.setResults(results);

List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	TasksReview curReview = (TasksReview)results.get(i);

	curReview = curReview.toEscapedModel();

	ResultRow row = new ResultRow(curReview, curReview.getReviewId(), i);

	// User

	row.addText(PortalUtil.getUserName(curReview.getUserId(), curReview.getUserName()));

	// Stage

	row.addText(String.valueOf(curReview.getStage() + 1));

	// Status

	String status = "not-reviewed";

	if (curReview.isCompleted()) {
		status = curReview.isRejected() ? "rejected" : "approved";
	}

	row.addText(LanguageUtil.get(pageContext, status));

	// Review date

	if (curReview.isCompleted()) {
		row.addText(dateFormatDateTime.format(curReview.getModifiedDate()));
	}
	else {
		row.addText(StringPool.BLANK);
	}

	// Add result row

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

<br />

<liferay-ui:tabs names="comments" />

<portlet:actionURL var="discussionURL">
	<portlet:param name="struts_action" value="/communities/edit_proposal_discussion" />
</portlet:actionURL>

<liferay-ui:discussion
	formAction="<%= discussionURL %>"
	formName="fm2"
	className="<%= TasksProposal.class.getName() %>"
	classPK="<%= proposal.getProposalId() %>"
	userId="<%= proposal.getUserId() %>"
	subject="<%= proposal.getName() %>"
	redirect="<%= currentURL %>"
/>