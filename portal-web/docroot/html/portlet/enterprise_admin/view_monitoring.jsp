<%
/**
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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2");

String portletURLString = (String)request.getAttribute("view.jsp-portletURLString");
%>

<c:choose>
	<c:when test="<%= PropsValues.LIVE_USERS_ENABLED && PropsValues.SESSION_TRACKER_MEMORY_ENABLED %>">
		<liferay-ui:tabs
			names="live-sessions"
			param="tabs2"
			url="<%= portletURLString %>"
		/>

		<liferay-ui:search-container
			headerNames="session-id,user-id,name,screen-name,last-request,num-of-hits"
			emptyResultsMessage="there-are-no-live-sessions"
		>

			<%
			Map<String, UserTracker> sessionUsers = LiveUsers.getSessionUsers(company.getCompanyId());

			List<UserTracker> userTrackers = new ArrayList<UserTracker>(sessionUsers.values());

			userTrackers = ListUtil.sort(userTrackers, new UserTrackerModifiedDateComparator());
			%>

			<liferay-ui:search-container-results
				results="<%= ListUtil.subList(userTrackers, searchContainer.getStart(), searchContainer.getEnd()) %>"
				total="<%= userTrackers.size() %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.model.UserTracker"
				escapedModel="<%= false %>"
				keyProperty="userTrackerId"
				modelVar="userTracker"
			>
				<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="rowURL">
					<portlet:param name="struts_action" value="/enterprise_admin/edit_session" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="sessionId" value="<%= userTracker.getSessionId() %>" />
				</portlet:renderURL>

				<%
				User user2 = null;

				try {
					user2 = UserLocalServiceUtil.getUserById(userTracker.getUserId());
				}
				catch (NoSuchUserException nsue) {
				}
				%>

				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					name="session-id"
					property="sessionId"
				/>

				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					name="user-id"
					property="userId"
				/>

				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					name="user-id"
					value='<%= ((user2 != null) ? user2.getFullName() : LanguageUtil.get(pageContext, "not-available")) %>'
				/>

				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					name="screen-name"
					value='<%= ((user2 != null) ? user2.getScreenName() : LanguageUtil.get(pageContext, "not-available")) %>'
				/>

				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					name="last-request"
					value="<%= dateFormatDateTime.format(userTracker.getModifiedDate()) %>"
				/>

				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					name="num-of-hits"
					property="hits"
				/>

			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</c:when>
	<c:when test="<%= !PropsValues.LIVE_USERS_ENABLED %>">
		<%= LanguageUtil.format(pageContext, "display-of-live-session-data-is-disabled", PropsKeys.LIVE_USERS_ENABLED) %>
	</c:when>
	<c:otherwise>
		<%= LanguageUtil.format(pageContext, "display-of-live-session-data-is-disabled", PropsKeys.SESSION_TRACKER_MEMORY_ENABLED) %>
	</c:otherwise>
</c:choose>