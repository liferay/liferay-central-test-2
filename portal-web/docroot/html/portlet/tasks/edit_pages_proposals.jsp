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
 */%>

<%@ include file="/html/portlet/tasks/init.jsp" %>

<%
Group group = (Group)request.getAttribute("edit_pages.jsp-group");
long groupId = ((Long)request.getAttribute("edit_pages.jsp-groupId")).longValue();
long liveGroupId = ((Long)request.getAttribute("edit_pages.jsp-liveGroupId")).longValue();
boolean privateLayout = ((Boolean)request.getAttribute("edit_pages.jsp-privateLayout")).booleanValue();
Layout selLayout = (Layout)request.getAttribute("edit_pages.jsp-selLayout");
PortletURL portletURL = (PortletURL)request.getAttribute("edit_pages.jsp-portletURL");

String currentLanguageId = LanguageUtil.getLanguageId(request);
Locale currentLocale = LocaleUtil.fromLanguageId(currentLanguageId);
Locale defaultLocale = LocaleUtil.getDefault();
String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

Locale[] locales = LanguageUtil.getAvailableLocales();

String type = BeanParamUtil.getString(selLayout, request, "type");
String friendlyURL = BeanParamUtil.getString(selLayout, request, "friendlyURL");

boolean isStagingManager = false;			// STAGING MANAGER
boolean isEditor = false;					// EDITOR
boolean isProducer = false;					// PRODUCER
boolean isReviewer = false;					// REVIEWER

if (GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.MANAGE_STAGING)) {
	isStagingManager = true;
}

if (GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.PUBLISH_STAGING)) {
	isEditor = true;
}

if (GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.ASSIGN_REVIEWER)) {
	isProducer = true;
}

if (GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.APPROVE_PROPOSAL)) {
	isReviewer = true;
}
%>

<%
List headerNames = new ArrayList();

headerNames.add("name");
headerNames.add("type");
headerNames.add("user");
//headerNames.add("published-date");
headerNames.add("due-date");
headerNames.add("status");
headerNames.add(StringPool.BLANK);

SearchContainer searchContainer = new SearchContainer(renderRequest, new DisplayTerms(renderRequest), new DisplayTerms(renderRequest), SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, "no-proposals-were-found");

int total = 0;

List results = null;

if (isStagingManager || isProducer || isEditor) {
	// Show all proposals.

	total = TasksProposalLocalServiceUtil.getProposalsCount(company.getCompanyId(), liveGroupId);

	results = TasksProposalLocalServiceUtil.getProposals(company.getCompanyId(), liveGroupId, searchContainer.getStart(), searchContainer.getEnd());
}
else if (isReviewer) {
	// Show only proposals assigned to me for review.

	total = TasksProposalLocalServiceUtil.getReviewersProposalsCount(company.getCompanyId(), liveGroupId, user.getUserId());

	results = TasksProposalLocalServiceUtil.getReviewersProposals(company.getCompanyId(), liveGroupId, user.getUserId(), searchContainer.getStart(), searchContainer.getEnd(), null);
}
else {
	// Show only my proposals.

	total = TasksProposalLocalServiceUtil.getUsersProposalsCount(company.getCompanyId(), liveGroupId, user.getUserId());

	results = TasksProposalLocalServiceUtil.getUsersProposals(company.getCompanyId(), liveGroupId, user.getUserId(), searchContainer.getStart(), searchContainer.getEnd());
}

searchContainer.setTotal(total);

searchContainer.setResults(results);

List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	TasksProposal proposal = (TasksProposal)results.get(i);

	proposal = proposal.toEscapedModel();

	ResultRow row = new ResultRow(proposal, proposal.getProposalId(), i);

	PortletURL rowURL = renderResponse.createRenderURL();

	rowURL.setWindowState(WindowState.MAXIMIZED);

	rowURL.setParameter("struts_action", "/communities/edit_proposal");
	rowURL.setParameter("redirect", currentURL);
	rowURL.setParameter("groupId", String.valueOf(proposal.getGroupId()));
	rowURL.setParameter("proposalId", String.valueOf(proposal.getProposalId()));

	StringMaker sm = new StringMaker();

	// Name

	sm.append("<a href=\"");
	sm.append(rowURL.toString());
	sm.append("\">");
	sm.append(proposal.getName(locale));
	sm.append("</a><br/>");
	sm.append(proposal.getDescription());

	row.addText(sm.toString());

	// Type

	String className = PortalUtil.getClassName(proposal.getClassNameId());

	row.addText(SearchEntry.DEFAULT_ALIGN, "top", LanguageUtil.get(pageContext, "model.resource." + className), rowURL);

	// User

	row.addText(SearchEntry.DEFAULT_ALIGN, "top", proposal.getUserName(), rowURL);

	// DueDate

	String formattedDate = StringPool.BLANK;

	if (proposal.getDueDate() != null) {
		formattedDate = dateFormatDate.format(proposal.getDueDate());
	}

	row.addText(SearchEntry.DEFAULT_ALIGN, "top", formattedDate, rowURL);

	// Status

	row.addText(SearchEntry.DEFAULT_ALIGN, "top", LanguageUtil.format(pageContext, proposal.getCurrentStatus(), new Integer(proposal.getCurrentStage())), rowURL);

	// Action

	row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/communities/proposal_action.jsp");

	// Add result row

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />