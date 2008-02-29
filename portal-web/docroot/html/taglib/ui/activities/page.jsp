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

<%@ page import="com.liferay.portal.util.ActivityTrackerInterpreterUtil" %>

<%
String className = (String)request.getAttribute("liferay-ui:activities:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:activities:classPK"));

List<ActivityTracker> activityTrackers = ActivityTrackerLocalServiceUtil.getObjectActivityTrackers(className, classPK);

DateFormat dateFormatDateTime = DateFormats.getDateTime(locale, timeZone);
%>

<liferay-ui:toggle-area showKey="show-activities" hideKey="hide-activities">

	<br />

	<table class="taglib-search-iterator">
	<tr class="portlet-section-header">
		<th class="col-1" width="80%">
			<liferay-ui:message key="activity" />
		</th>
		<th class="col-2" width="20%">
			<liferay-ui:message key="date" />
		</th>
	</tr>

	<c:choose>
		<c:when test="<%= activityTrackers.size() > 0 %>">

			<%
			for (int i = 0; i < activityTrackers.size(); i++) {
				ActivityTracker activityTracker = activityTrackers.get(i);

				ActivityFeedEntry activityFeedEntry = ActivityTrackerInterpreterUtil.interpret(activityTracker, themeDisplay);

				if (activityFeedEntry != null) {
					String cssClass = "portlet-section-body";

					if (i % 2 == 0) {
						cssClass = "portlet-section-alternate";
					}
			%>

					<tr class="<%= cssClass %>">
						<td>
							<%= activityFeedEntry.getTitle() %>
						</td>
						<td>
							<%= dateFormatDateTime.format(activityTracker.getCreateDate()) %>
						</td>
					</tr>
					<tr class="<%= cssClass %>">
						<td colspan="2">
							<%= activityFeedEntry.getBody() %>
						</td>
					</tr>

			<%
				}
			}
			%>

		</c:when>
		<c:otherwise>
			<tr class="portlet-section-body">
				<td colspan="2">
					<liferay-ui:message key="no-activities-were-found" />
				</td>
			</tr>
		</c:otherwise>
	</c:choose>

	</table>
</liferay-ui:toggle-area>