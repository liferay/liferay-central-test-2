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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
Layout selLayout = (Layout)request.getAttribute("edit_pages.jsp-selLayout");

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<liferay-ui:error-marker key="errorSection" value="proposals" />

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

<h3><liferay-ui:message key="proposals" /></h3>

<%
long liveGroupId = ((Long)request.getAttribute("edit_pages.jsp-liveGroupId")).longValue();

PortletURL portletURL = (PortletURL)request.getAttribute("edit_pages.jsp-portletURL");

List<String> headerNames = new ArrayList<String>();

headerNames.add("name");
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

	rowURL.setParameter("struts_action", "/layouts_admin/edit_proposal");
	rowURL.setParameter("redirect", currentURL);
	rowURL.setParameter("groupId", String.valueOf(proposal.getGroupId()));
	rowURL.setParameter("proposalId", String.valueOf(proposal.getProposalId()));

	// Name

	row.addText(proposal.getName().concat("<br />").concat(proposal.getDescription()), rowURL);

	// ID

	row.addText(proposal.getClassPK(), rowURL);

	// User

	row.addText(HtmlUtil.escape(PortalUtil.getUserName(proposal.getUserId(), proposal.getUserName())), rowURL);

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

	row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/layouts_admin/proposal_action.jsp");

	// Add result row

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />