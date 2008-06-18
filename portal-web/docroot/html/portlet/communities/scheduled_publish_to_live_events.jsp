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

<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<%@ page import="com.liferay.portlet.communities.util.StagingUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.scheduler.SchedulerEngineUtil" %>
<%@ page import="com.liferay.portal.kernel.scheduler.messaging.SchedulerRequest" %>

<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>

<%
long liveGroupId = ParamUtil.getLong(request, "groupId");
String namespace = ParamUtil.getString(request, "namespace");

Collection<SchedulerRequest> schedulerRequests = SchedulerEngineUtil.getScheduledJobs(StagingUtil.getSchedulerGroupName(liveGroupId));

Iterator itr = schedulerRequests.iterator();
int i = 0;
%>

<div class="results-grid">
	<table class="taglib-search-iterator">
	<tbody>
		<tr class="portlet-section-header results-header">
			<th class="col-1"><liferay-ui:message key="description" /></th>
			<th class="col-2" />
		</tr>
	<%
	if (schedulerRequests.size() == 0) {
	%>
		<tr class="portlet-section-body results-row">
			<td align="center" colspan="2"> <liferay-ui:message key="there-are-no-scheduled-events" /> </td>
		</tr>
	<%
	}

	while (itr.hasNext()) {
		i++;

		String style = "class=\"portlet-section-body results-row\" onmouseover=\"this.className = \'portlet-section-body-hover results-row hover\';\" onmouseout=\"this.className = \'portlet-section-body results-row\';\"";

		if ((i % 2) == 0) {
			style = "class=\"portlet-section-alternate results-row alt\" onmouseover=\"this.className = \'portlet-section-alternate-hover results-row alt hover\';\" onmouseout=\"this.className = \'portlet-section-alternate results-row alt\';\"";
		}

		SchedulerRequest schedulerRequest = (SchedulerRequest)itr.next();
	%>
		<tr <%= style %> >
			<td align="left" class="col-1" valign="middle"> <%= schedulerRequest.getDescription() %> </td>
			<td align="left" class="col-2" valign="middle"><a href="javascript: <%= namespace %>unschedulePublishToLive('<%= schedulerRequest.getJobName() %>');"><liferay-ui:message key="delete" /></a></td>
		</tr>
	<%
	}
	%>
	</tbody>
	</table>
</div>