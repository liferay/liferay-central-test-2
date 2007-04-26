<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

<c:choose>
	<c:when test="<%= GetterUtil.getBoolean(PropsUtil.get(PropsUtil.SESSION_TRACKER_MEMORY_ENABLED)) %>">
		<liferay-ui:tabs
			names="live-sessions"
			param="tabs2"
			url="<%= portletURL.toString() %>"
		/>

		<%
		SearchContainer searchContainer = new SearchContainer();

		List headerNames = new ArrayList();

		headerNames.add("session-id");
		headerNames.add("user-id");
		headerNames.add("name");
		headerNames.add("email-address");
		headerNames.add("last-request");
		headerNames.add("num-of-hits");

		searchContainer.setHeaderNames(headerNames);

		List results = new ArrayList();

		Iterator itr = LiveUsers.getSessionUsers().entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry entry = (Map.Entry)itr.next();

			results.add(entry.getValue());
		}

		Collections.sort(results, new UserTrackerModifiedDateComparator());

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			UserTracker userTracker = (UserTracker)results.get(i);

			ResultRow row = new ResultRow(userTracker, userTracker.getPrimaryKey().toString(), i);

			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setWindowState(WindowState.MAXIMIZED);

			rowURL.setParameter("struts_action", "/admin/edit_session");
			rowURL.setParameter("redirect", currentURL);
			rowURL.setParameter("userTrackerId", userTracker.getUserTrackerId());

			User user2 = null;

			try {
				user2 = UserLocalServiceUtil.getUserById(userTracker.getUserId());
			}
			catch (NoSuchUserException nsue) {
			}

			// Session ID

			row.addText(userTracker.getUserTrackerId(), rowURL);

			// User ID

			row.addText(String.valueOf(userTracker.getUserId()), rowURL);

			// Name

			row.addText(((user2 != null) ? user2.getFullName() : LanguageUtil.get(pageContext, "not-available")), rowURL);

			// Email Address

			row.addText(((user2 != null) ? user2.getEmailAddress() : LanguageUtil.get(pageContext, "not-available")), rowURL);

			// Last Request

			row.addText(dateFormatDateTime.format(userTracker.getModifiedDate()), rowURL);

			// # of Hits

			row.addText(String.valueOf(userTracker.getHits()), rowURL);

			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
	</c:when>
	<c:otherwise>
		<%= LanguageUtil.format(pageContext, "display-of-live-session-data-is-disabled", PropsUtil.SESSION_TRACKER_MEMORY_ENABLED) %>
	</c:otherwise>
</c:choose>