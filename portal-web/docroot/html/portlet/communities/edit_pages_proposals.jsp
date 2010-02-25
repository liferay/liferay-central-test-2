<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
long liveGroupId = ((Long)request.getAttribute("edit_pages.jsp-liveGroupId")).longValue();

PortletURL portletURL = (PortletURL)request.getAttribute("edit_pages.jsp-portletURL");

List<String> headerNames = new ArrayList<String>();

headerNames.add("name");
headerNames.add("type");
headerNames.add("id");
headerNames.add("user");
headerNames.add("due-date");
headerNames.add("status");
headerNames.add(StringPool.BLANK);

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, "no-proposals-were-found");

int total = 0;
List<TasksProposal> results = null;

if (GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.ASSIGN_REVIEWER) || GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.MANAGE_STAGING) || GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.PUBLISH_STAGING)) {
	total = TasksProposalLocalServiceUtil.getProposalsCount(liveGroupId);
	results = TasksProposalLocalServiceUtil.getProposals(liveGroupId, searchContainer.getStart(), searchContainer.getEnd());
}
else if (GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.APPROVE_PROPOSAL)) {
	total = TasksProposalLocalServiceUtil.getReviewProposalsCount(liveGroupId, user.getUserId());
	results = TasksProposalLocalServiceUtil.getReviewProposals(liveGroupId, user.getUserId(), searchContainer.getStart(), searchContainer.getEnd());
}
else {
	total = TasksProposalLocalServiceUtil.getUserProposalsCount(liveGroupId, user.getUserId());
	results = TasksProposalLocalServiceUtil.getUserProposals(liveGroupId, user.getUserId(), searchContainer.getStart(), searchContainer.getEnd());
}

searchContainer.setTotal(total);
searchContainer.setResults(results);

List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	TasksProposal proposal = results.get(i);

	proposal = proposal.toEscapedModel();

	ResultRow row = new ResultRow(proposal, proposal.getProposalId(), i);

	PortletURL rowURL = renderResponse.createRenderURL();

	rowURL.setParameter("struts_action", "/communities/edit_proposal");
	rowURL.setParameter("redirect", currentURL);
	rowURL.setParameter("groupId", String.valueOf(proposal.getGroupId()));
	rowURL.setParameter("proposalId", String.valueOf(proposal.getProposalId()));

	// Name

	row.addText(proposal.getName().concat("<br />").concat(proposal.getDescription()), rowURL);

	// Type

	String className = PortalUtil.getClassName(proposal.getClassNameId());

	row.addText(LanguageUtil.get(pageContext, "model.resource." + className), rowURL);

	// ID

	row.addText(proposal.getClassPK(), rowURL);

	// User

	row.addText(PortalUtil.getUserName(proposal.getUserId(), proposal.getUserName()), rowURL);

	// Due date

	if (proposal.getDueDate() == null) {
		row.addText(LanguageUtil.get(pageContext, "never"), rowURL);
	}
	else {
		row.addText(dateFormatDateTime.format(proposal.getDueDate()), rowURL);
	}

	// Status

	row.addText(proposal.getStatus(locale), rowURL);

	// Action

	row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/communities/proposal_action.jsp");

	// Add result row

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />