<%/**
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

<%@ include file="/html/portlet/tasks/init.jsp" %>

<%
TasksProposal proposal = (TasksProposal)request.getAttribute(WebKeys.TASKS_PROPOSAL);

long proposalId = BeanParamUtil.getLong(proposal, request, "proposalId");

long groupId = BeanParamUtil.getLong(proposal, request, "groupId");

long classNameId = BeanParamUtil.getLong(proposal, request, "classNameId");

long classPK = BeanParamUtil.getLong(proposal, request, "classPK");

String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

Group selGroup = (Group)request.getAttribute(WebKeys.GROUP);

selGroup = selGroup.toEscapedModel();

Group liveGroup = null;
Group stagingGroup = null;

if (selGroup.isStagingGroup()) {
	liveGroup = selGroup.getLiveGroup();
	stagingGroup = selGroup;
}
else {
	liveGroup = selGroup;

	if (selGroup.hasStagingGroup()) {
		stagingGroup = selGroup.getStagingGroup();
	}
}

long liveGroupId = liveGroup.getGroupId();

long stagingGroupId = 0;

if (stagingGroup != null) {
	stagingGroupId = stagingGroup.getGroupId();
}

String name = StringPool.BLANK;
String type = StringPool.BLANK;
String className = StringPool.BLANK;

if (classNameId > 0) {
	className = PortalUtil.getClassName(classNameId);
}

int numberOfApprovalStages = TasksUtil.getNumberOfApprovalStages(company.getCompanyId(), liveGroupId);

String[] approvalRoleNames = TasksUtil.getApprovalRoleNames(company.getCompanyId(), liveGroupId);

int currentApprovalStage = 1;
TasksReview review = null;
long reviewId = 0;

boolean isLastStageReviewer = false;
boolean isStageOneReviewer = false;
boolean isReviewer = false;

if (proposal != null) {
	name = proposal.getName(locale);
	type = LanguageUtil.get(pageContext, "model.resource." + className);

	try {
		review = TasksReviewLocalServiceUtil.getReview(proposalId, user.getUserId());

		reviewId = review.getReviewId();
	}
	catch (NoSuchReviewException nsre) {
	}
}

if (review != null) {
	if (review.getStage() == 1) {
		isStageOneReviewer = true;
		isReviewer = true;
	}
	else if (review.getStage() == numberOfApprovalStages) {
		isLastStageReviewer = true;
		isReviewer = true;
	}
	else {
		isReviewer = true;
	}
}

Calendar dueDate = CalendarFactoryUtil.getCalendar(timeZone, locale);

dueDate.add(Calendar.MONTH, 9);

if (proposal != null) {
	if (proposal.getDueDate() != null) {
		dueDate.setTime(proposal.getDueDate());
	}
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/communities/edit_proposal");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("proposalId", String.valueOf(proposalId));
portletURL.setParameter("groupId", String.valueOf(selGroup.getGroupId()));
%>

<script type="text/javascript">
	function <portlet:namespace />rejectProposal(rejected) {
		if (rejected && confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-reject-this-proposal") %>')) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "reject_review";
			document.<portlet:namespace />fm.<portlet:namespace />rejected.value = rejected;
			submitForm(document.<portlet:namespace />fm);
		}
		if (!rejected && confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-approve-this-proposal") %>')) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "reject_review";
			document.<portlet:namespace />fm.<portlet:namespace />rejected.value = rejected;
			submitForm(document.<portlet:namespace />fm);
		}
	}

	function <portlet:namespace />publishProposal() {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-publish-this-proposal") %>')) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "publish_proposal";
			document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<%= backURL %>';
			submitForm(document.<portlet:namespace />fm);
		}
	}

	function <portlet:namespace />savePropsal(options) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.UPDATE %>';

		if (options) {
			var stage = options.stage;
			var currentReviewers = options.currentReviewers;
			var availableReviewers = options.availableReviewers;

			document.<portlet:namespace />fm.<portlet:namespace />stage.value = stage;
			document.<portlet:namespace />fm.<portlet:namespace />reviewerIds.value = currentReviewers;
			document.<portlet:namespace />fm.<portlet:namespace />removeReviewerIds.value = availableReviewers;
		}

		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/communities/edit_proposal" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />savePropsal(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />groupId" type="hidden" value="<%= groupId %>" />
<input name="<portlet:namespace />liveGroupId" type="hidden" value="<%= liveGroupId %>" />
<input name="<portlet:namespace />stagingGroupId" type="hidden" value="<%= stagingGroupId %>" />
<input name="<portlet:namespace />proposalId" type="hidden" value="<%= proposalId %>" />
<input name="<portlet:namespace />rejected" type="hidden" value="" />
<input name="<portlet:namespace />pagesRedirect" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= currentURL %>" />
<input name="<portlet:namespace />reviewerIds" type="hidden" value="" />
<input name="<portlet:namespace />removeReviewerIds" type="hidden" value="" />
<input name="<portlet:namespace />stage" type="hidden" value="<%= currentApprovalStage %>" />

<liferay-ui:tabs
	names="proposal"
	url="<%= portletURL.toString() %>"
	backURL="<%= redirect %>"
/>

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="name" />
	</td>
	<td>
		<%= name %>
 	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="type" />
	</td>
	<td>
		<%= type %>
	</td>
</tr>

<c:if test="<%= proposal != null %>">
	<tr>
		<td>
			<liferay-ui:message key="status" />
		</td>
		<td>
			<%= LanguageUtil.format(pageContext, proposal.getCurrentStatus(), new Integer(proposal.getCurrentStage())) %>
		</td>
	</tr>
</c:if>

<tr>
	<td colspan="2">
		<br/>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="description" />
	</td>
	<td>
		<c:choose>
			<c:when test="<%= TasksProposalPermission.contains(permissionChecker, proposalId, ActionKeys.UPDATE) %>">
				<liferay-ui:input-field model="<%= TasksProposal.class %>" bean="<%= proposal %>" field="description" />
			</c:when>
			<c:otherwise>
				<%= BeanParamUtil.getString(proposal, request, "description") %>
			</c:otherwise>
		</c:choose>
	</td>
</tr>
<tr>
	<td colspan="2">
		<br/>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="due-date" />
	</td>
	<td>
		<c:choose>
			<c:when test="<%= TasksProposalPermission.contains(permissionChecker, proposalId, ActionKeys.UPDATE) %>">
				<liferay-ui:input-field model="<%= TasksProposal.class %>" bean="<%= proposal %>" field="dueDate" defaultValue="<%= dueDate %>" />
			</c:when>
			<c:otherwise>
				<%= dateFormatDate.format(dueDate.getTime()) %>
			</c:otherwise>
		</c:choose>
	</td>
</tr>
</table

<c:if test="<%= proposal != null %>">
	<br/>

	<liferay-ui:tabs names="reviewers" />

	<%
	List headerNames = new ArrayList();

	headerNames.add("user");
	headerNames.add("stage");
	headerNames.add("status");
	headerNames.add("review-date");

	SearchContainer searchContainer = new SearchContainer(renderRequest, new DisplayTerms(renderRequest), new DisplayTerms(renderRequest), SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, "no-reviewers-were-found");

	List<TasksReview> results = TasksReviewLocalServiceUtil.getReviews(proposal.getProposalId());

	int total = results.size();

	searchContainer.setTotal(total);

	searchContainer.setResults(results);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		TasksReview curReview = (TasksReview)results.get(i);

		curReview = curReview.toEscapedModel();

		ResultRow row = new ResultRow(curReview, curReview.getReviewId(), i);

		// User

		row.addText(curReview.getUserName());

		// Stage

		row.addText(String.valueOf(curReview.getStage()));

		// Status

		String status = "not-reviewed";

		if (curReview.isCompleted()) {
			status = (curReview.isRejected() ? "rejected" : "approved");
		}

		row.addText(LanguageUtil.get(pageContext, status));

		// ReviewedDate

		String formattedDate = StringPool.BLANK;

		if (curReview.isCompleted()) {
			formattedDate = dateFormatDate.format(curReview.getModifiedDate());
		}

		row.addText(formattedDate);

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

	<c:if test="<%= isStageOneReviewer && GroupPermissionUtil.contains(permissionChecker, groupId, ActionKeys.ASSIGN_REVIEWER) %>">
		<br/>

		<liferay-ui:toggle-area showKey="show-assign-reviewers" hideKey="hide-assign-reviewers">

			<table class="lfr-table">
			<%
			for (int i = currentApprovalStage; i < numberOfApprovalStages; i++) {
			%>
			<tr>
				<td>
					<br />
				</td>
			</tr>
			<tr>
				<td>
					<liferay-ui:message key="stage" /> <%= (i + 1) %>
				</td>
				<td>

					<%
					// Left list

					List leftList = new ArrayList();

					results = TasksReviewLocalServiceUtil.getReviews(proposal.getProposalId(), i + 1);

					for (TasksReview curReview : results) {
						leftList.add(new KeyValuePair(String.valueOf(curReview.getUserId()), curReview.getUserName()));
					}

					Collections.sort(leftList, new KeyValuePairComparator(false, true));

					// Right list

					List rightList = new ArrayList();

					Role reviewerRole = RoleLocalServiceUtil.getRole(company.getCompanyId(), approvalRoleNames[i]);

					LinkedHashMap userParams = new LinkedHashMap();
					userParams.put("usersGroups", new Long(liveGroup.getGroupId()));
					userParams.put("userGroupRole", new Long[] {new Long(liveGroup.getGroupId()), new Long(reviewerRole.getRoleId())});
					List<User> reviewers = UserLocalServiceUtil.search(company.getCompanyId(), null, null, userParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

					for (User user2 : reviewers) {
						KeyValuePair kvp = new KeyValuePair(String.valueOf(user2.getUserId()), user2.getFullName());

						if (!leftList.contains(kvp)) {
							rightList.add(kvp);
						}
					}

					Collections.sort(rightList, new KeyValuePairComparator(false, true));
					%>

					<liferay-ui:input-move-boxes
						formName="fm"
						leftTitle="current"
						rightTitle="available"
						leftBoxName='<%= "current_reviewers" + (i + 1) %>'
						rightBoxName='<%= "available_reviewers" + (i + 1) %>'
						leftList="<%= leftList %>"
						rightList="<%= rightList %>"
					/>
				</td>
				<td>
					<input type="button" value='<liferay-ui:message key="assign" />' onClick='<portlet:namespace />savePropsal({stage: <%= (i + 1) %>, currentReviewers: Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace /><%= "current_reviewers" + (i + 1) %>), availableReviewers: Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace /><%= "available_reviewers" + (i + 1) %>)});' />
				</td>
			</tr>
			<%
			}
			%>
			</table>

		</liferay-ui:toggle-area>
	</c:if>

	<br /><br />

	<liferay-ui:activities className="<%= TasksProposal.class.getName() %>" classPK="<%= proposalId %>" />

</c:if>

<br/><br/>

<c:if test="<%= TasksProposalPermission.contains(permissionChecker, proposalId, ActionKeys.UPDATE) %>">
	<input type="submit" value="<liferay-ui:message key="save" />" />
</c:if>

<c:if test="<%= proposal != null %>" >
	<%
	Layout proposedLayout = LayoutLocalServiceUtil.getLayout(proposal.getClassPK());

	String previewURL = PortalUtil.getLayoutFriendlyURL(proposedLayout, themeDisplay);
	%>

	<input type="button" value="<liferay-ui:message key="preview" />" onClick="window.open('<%= previewURL %>', 'previewWindow');" />
</c:if>

<c:if test="<%= review != null && (isReviewer) %>">
	<c:if test="<%= isLastStageReviewer && (GroupPermissionUtil.contains(permissionChecker, groupId, ActionKeys.MANAGE_STAGING) || GroupPermissionUtil.contains(permissionChecker, groupId, ActionKeys.PUBLISH_STAGING)) %>">
		<input type="button" value="<liferay-ui:message key="publish" />"  onClick="<portlet:namespace />publishProposal();" />
	</c:if>

	<c:choose>
		<c:when test="<%= review.isCompleted() %>">
			<c:if test="<%= !review.isRejected() %>">
				<input type="button" value="<liferay-ui:message key="reject" />"  onClick="<portlet:namespace />rejectProposal(true);" />
			</c:if>
			<c:if test="<%= review.isRejected() %>">
				<input type="button" value="<liferay-ui:message key="approve" />"  onClick="<portlet:namespace />rejectProposal(false);" />
			</c:if>
		</c:when>
		<c:otherwise>
			<input type="button" value="<liferay-ui:message key="reject" />"  onClick="<portlet:namespace />rejectProposal(true);" />
			<input type="button" value="<liferay-ui:message key="approve" />"  onClick="<portlet:namespace />rejectProposal(false);" />
		</c:otherwise>
	</c:choose>
</c:if>

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="self.location = '<%= redirect %>';" />

</form>

<c:if test="<%= proposal != null %>">
	<div class="separator"><!-- --></div>

	<liferay-ui:message key="comments" />

	<br /><br />

	<portlet:actionURL var="discussionURL">
		<portlet:param name="struts_action" value="/communities/edit_proposal_discussion" />
	</portlet:actionURL>

	<liferay-ui:discussion
		formAction="<%= discussionURL %>"
		formName="fm1"
		className="<%= TasksProposal.class.getName() %>"
		classPK="<%= proposal.getProposalId() %>"
		userId="<%= proposal.getUserId() %>"
		subject="<%= proposal.getName(locale) %>"
		redirect="<%= currentURL %>"
	/>
</c:if>