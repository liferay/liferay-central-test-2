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

<%@ page import="com.liferay.portlet.social.model.SocialActivity" %>
<%@ page import="com.liferay.portlet.social.model.SocialActivityFeedEntry" %>
<%@ page import="com.liferay.portlet.social.service.SocialActivityInterpreterLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.social.service.SocialActivityLocalServiceUtil" %>

<%
String className = (String)request.getAttribute("liferay-ui:social-activities:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:social-activities:classPK"));
List<SocialActivity> activities = (List<SocialActivity>)request.getAttribute("liferay-ui:social-activities:activities");

if (activities == null) {
	activities = SocialActivityLocalServiceUtil.getActivities(0, className, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
}

DateFormat dateFormatDate = new SimpleDateFormat("MMMM d", locale);

if (timeZone != null) {
	dateFormatDate.setTimeZone(timeZone);
}

DateFormat timeFormatDate = DateFormats.getTime(locale, timeZone);
%>

<div class="taglib-social-activities">

	<%
	boolean firstDaySeparator = true;

	Date now = new Date();

	int daysBetween = -1;

	for (SocialActivity activity : activities) {
		SocialActivityFeedEntry activityFeedEntry = SocialActivityInterpreterLocalServiceUtil.interpret(activity, themeDisplay);

		if (activityFeedEntry != null) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), activityFeedEntry.getPortletId());

			int curDaysBetween = DateUtil.getDaysBetween(activity.getCreateDate(), now, timeZone);
	%>

			<c:if test="<%= curDaysBetween > daysBetween %>">

				<%
				daysBetween = curDaysBetween;
				%>

				<div class="<%= firstDaySeparator ? "first-" : "" %>day-separator">
					<c:choose>
						<c:when test="<%= curDaysBetween == 0 %>">
							<liferay-ui:message key="today" />
						</c:when>
						<c:when test="<%= curDaysBetween == 1 %>">
							<liferay-ui:message key="yesterday" />
						</c:when>
						<c:otherwise>
							<%= dateFormatDate.format(activity.getCreateDate()) %>
						</c:otherwise>
					</c:choose>
				</div>

				<%
				firstDaySeparator = false;
				%>

			</c:if>

			<div class="activity-separator"><!-- --></div>

			<table class="lfr-table">
			<tr>
				<td>
					<img src="<%= portlet.getContextPath() + portlet.getIcon() %>" />
				</td>
				<td>
					<%= activityFeedEntry.getTitle() %>

					<%= timeFormatDate.format(activity.getCreateDate()) %>
				</td>
			<tr>
				<td></td>
				<td>
					<%= activityFeedEntry.getBody() %>
				</td>
			</tr>
			</table>

	<%
		}
	}
	%>

</div>