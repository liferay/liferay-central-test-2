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

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
long liveGroupId = ParamUtil.getLong(request, "groupId");

SearchContainer searchContainer = new SearchContainer();

List headerNames = new ArrayList();

headerNames.add("description");
headerNames.add(StringPool.BLANK);

searchContainer.setHeaderNames(headerNames);
searchContainer.setEmptyResultsMessage("there-are-no-scheduled-events");

List<SchedulerRequest> results = SchedulerEngineUtil.getScheduledJobs(StagingUtil.getSchedulerGroupName(liveGroupId, false));
List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	SchedulerRequest schedulerRequest = results.get(i);

	ResultRow row = new ResultRow(schedulerRequest, schedulerRequest.hashCode(), i);

	// Description

	row.addText(schedulerRequest.getDescription());

	// Action

	StringMaker sm = new StringMaker();

	sm.append("<a href=\"javascript: ");
	sm.append(portletDisplay.getNamespace());
	sm.append("unschedulePublishToLive('");
	sm.append(schedulerRequest.getJobName());
	sm.append("');\">");
	sm.append(LanguageUtil.get(pageContext, "delete"));
	sm.append("</a>");

	row.addText(sm.toString());

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= false %>" />