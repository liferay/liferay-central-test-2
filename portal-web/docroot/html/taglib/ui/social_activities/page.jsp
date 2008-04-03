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

List<SocialActivity> activities = SocialActivityLocalServiceUtil.getActivities(className, classPK);

DateFormat dateFormatDateTime = DateFormats.getDateTime(locale, timeZone);
%>

<liferay-ui:toggle-area
	id='<%= "toggle_id_taglib_ui_activities_" + className + "_" + classPK %>'
	showMessage='<%= LanguageUtil.get(pageContext, "show-activities") + " &raquo;" %>'
	hideMessage='<%= "&laquo; " + LanguageUtil.get(pageContext, "hide-activities") %>'
	defaultShowContent="<%= false %>"
>
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
		<c:when test="<%= activities.size() > 0 %>">

			<%
			for (int i = 0; i < activities.size(); i++) {
				SocialActivity activity = activities.get(i);

				SocialActivityFeedEntry activityFeedEntry = SocialActivityInterpreterLocalServiceUtil.interpret(activity, themeDisplay);

				if (activityFeedEntry != null) {
					String cssClass = "portlet-section-body";

					if ((i % 2) == 0) {
						cssClass = "portlet-section-alternate";
					}
			%>

					<tr class="<%= cssClass %>">
						<td>
							<%= activityFeedEntry.getTitle() %>
						</td>
						<td>
							<%= dateFormatDateTime.format(activity.getCreateDate()) %>
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