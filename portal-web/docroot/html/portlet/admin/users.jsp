<%
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

<liferay-ui:tabs
	names="live-sessions,default-groups-and-roles,reserved-users,mail-host-names,emails"
	param="tabs2"
	url="<%= portletURL.toString() %>"
/>

<c:choose>
	<c:when test='<%= tabs2.equals("default-groups-and-roles") %>'>
		<%= LanguageUtil.get(pageContext, "enter-the-default-group-names-per-line-that-are-associated-with-newly-created-users") %>

		<br><br>

		<textarea class="form-text" cols="<%= ModelHintsDefaults.TEXTAREA_DISPLAY_WIDTH %>" name="<portlet:namespace />defaultGroupNames"  rows="<%= ModelHintsDefaults.TEXTAREA_DISPLAY_HEIGHT %>"><%= StringUtil.merge(AdminUtil.getDefaultGroupNames(company.getCompanyId()), "\n") %></textarea>

		<br><br>

		<%= LanguageUtil.get(pageContext, "enter-the-default-role-names-per-line-that-are-associated-with-newly-created-users") %>

		<br><br>

		<textarea class="form-text" cols="<%= ModelHintsDefaults.TEXTAREA_DISPLAY_WIDTH %>" name="<portlet:namespace />defaultRoleNames"  rows="<%= ModelHintsDefaults.TEXTAREA_DISPLAY_HEIGHT %>"><%= StringUtil.merge(AdminUtil.getDefaultRoleNames(company.getCompanyId()), "\n") %></textarea>

		<br><br>

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "save") %>' onClick="<portlet:namespace />saveUsers('updateDefaultGroupsAndRoles');">
	</c:when>
	<c:when test='<%= tabs2.equals("reserved-users") %>'>
		<%= LanguageUtil.get(pageContext, "enter-one-user-id-per-line-to-reserve-the-user-id") %>

		<br><br>

		<textarea class="form-text" cols="<%= ModelHintsDefaults.TEXTAREA_DISPLAY_WIDTH %>" name="<portlet:namespace />reservedUserIds"  rows="<%= ModelHintsDefaults.TEXTAREA_DISPLAY_HEIGHT %>"><%= StringUtil.merge(AdminUtil.getReservedUserIds(company.getCompanyId()), "\n") %></textarea>

		<br><br>

		<%= LanguageUtil.get(pageContext, "enter-one-user-email-address-per-line-to-reserve-the-user-email-address") %>

		<br><br>

		<textarea class="form-text" cols="<%= ModelHintsDefaults.TEXTAREA_DISPLAY_WIDTH %>" name="<portlet:namespace />reservedEmailAddresses"  rows="<%= ModelHintsDefaults.TEXTAREA_DISPLAY_HEIGHT %>"><%= StringUtil.merge(AdminUtil.getReservedEmailAddresses(company.getCompanyId()), "\n") %></textarea>

		<br><br>

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "save") %>' onClick="<portlet:namespace />saveUsers('updateReservedUsers');">
	</c:when>
	<c:when test='<%= tabs2.equals("mail-host-names") %>'>
		<%= LanguageUtil.format(pageContext, "enter-one-mail-host-name-per-line-for-all-additional-mail-host-names-besides-x", company.getMx(), false) %>

		<br><br>

		<textarea class="form-text" cols="<%= ModelHintsDefaults.TEXTAREA_DISPLAY_WIDTH %>" name="<portlet:namespace />mailHostNames"  rows="<%= ModelHintsDefaults.TEXTAREA_DISPLAY_HEIGHT %>"><%= StringUtil.merge(AdminUtil.getMailHostNames(company.getCompanyId()), "\n") %></textarea>

		<br><br>

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "save") %>' onClick="<portlet:namespace />saveUsers('updateMailHostNames');">
	</c:when>
	<c:when test='<%= tabs2.equals("emails") %>'>
		<script type="text/javascript">

			<%
			String emailFromName = ParamUtil.getString(request, "emailFromName", AdminUtil.getEmailFromName(company.getCompanyId()));
			String emailFromAddress = ParamUtil.getString(request, "emailFromAddress", AdminUtil.getEmailFromAddress(company.getCompanyId()));

			String emailUserAddedSubject = ParamUtil.getString(request, "emailUserAddedSubject", AdminUtil.getEmailUserAddedSubject(company.getCompanyId()));
			String emailUserAddedBody = ParamUtil.getString(request, "emailUserAddedBody", AdminUtil.getEmailUserAddedBody(company.getCompanyId()));

			String emailPasswordSentSubject = ParamUtil.getString(request, "emailPasswordSentSubject", AdminUtil.getEmailPasswordSentSubject(company.getCompanyId()));
			String emailPasswordSentBody = ParamUtil.getString(request, "emailPasswordSentBody", AdminUtil.getEmailPasswordSentBody(company.getCompanyId()));

			String editorParam = "";
			String editorContent = "";

			if (tabs3.equals("user-added-email")) {
				editorParam = "emailUserAddedBody";
				editorContent = emailUserAddedBody;
			}
			else if (tabs3.equals("password-sent-email")) {
				editorParam = "emailPasswordSentBody";
				editorContent = emailPasswordSentBody;
			}
			%>

			function initEditor() {
				return "<%= UnicodeFormatter.toString(editorContent) %>";
			}

			function <portlet:namespace />saveEmails() {
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "updateEmails";

				<c:if test='<%= tabs3.endsWith("-email") %>'>
					document.<portlet:namespace />fm.<portlet:namespace /><%= editorParam %>.value = parent.<portlet:namespace />editor.getHTML();
				</c:if>

				submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/admin/edit_users" /></portlet:actionURL>");
			}
		</script>

		<liferay-ui:tabs
			names="email-from,user-added-email,password-sent-email"
			param="tabs3"
			url="<%= portletURL.toString() %>"
		/>

		<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
		<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
		<liferay-ui:error key="emailPasswordSentBody" message="please-enter-a-valid-body" />
		<liferay-ui:error key="emailPasswordSentSubject" message="please-enter-a-valid-subject" />
		<liferay-ui:error key="emailUserAddedBody" message="please-enter-a-valid-body" />
		<liferay-ui:error key="emailUserAddedSubject" message="please-enter-a-valid-subject" />

		<c:choose>
			<c:when test='<%= tabs3.endsWith("-email") %>'>
				<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<%= LanguageUtil.get(pageContext, "enabled") %>
					</td>
					<td style="padding-left: 10px;"></td>
					<td>
						<c:choose>
							<c:when test='<%= tabs3.equals("user-added-email") %>'>
								<liferay-ui:input-checkbox param="emailUserAddedEnabled" defaultValue="<%= AdminUtil.getEmailUserAddedEnabled(company.getCompanyId()) %>" />
							</c:when>
							<c:when test='<%= tabs3.equals("password-sent-email") %>'>
								<liferay-ui:input-checkbox param="emailPasswordSentEnabled" defaultValue="<%= AdminUtil.getEmailPasswordSentEnabled(company.getCompanyId()) %>" />
							</c:when>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<br>
					</td>
				</tr>
				<tr>
					<td>
						<%= LanguageUtil.get(pageContext, "subject") %>
					</td>
					<td style="padding-left: 10px;"></td>
					<td>
						<c:choose>
							<c:when test='<%= tabs3.equals("user-added-email") %>'>
								<input class="form-text" name="<portlet:namespace />emailUserAddedSubject" size="<%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>" type="text" value="<%= emailUserAddedSubject %>">
							</c:when>
							<c:when test='<%= tabs3.equals("password-sent-email") %>'>
								<input class="form-text" name="<portlet:namespace />emailPasswordSentSubject" size="<%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>" type="text" value="<%= emailPasswordSentSubject %>">
							</c:when>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<br>
					</td>
				</tr>
				<tr>
					<td>
						<%= LanguageUtil.get(pageContext, "body") %>
					</td>
					<td style="padding-left: 10px;"></td>
					<td>
						<iframe frameborder="0" height="400" id="<portlet:namespace />editor" name="<portlet:namespace />editor" scrolling="no" src="<%= themeDisplay.getPathJavaScript() %>/editor/editor.jsp?p_l_id=<%= plid %>&editorImpl=<%= PropsUtil.get(EDITOR_WYSIWYG_IMPL_KEY) %>" width="640"></iframe>

						<input name="<portlet:namespace /><%= editorParam %>" type="hidden" value="">
					</td>
				</tr>
				</table>

				<br>

				<b><%= LanguageUtil.get(pageContext, "definition-of-terms") %></b>

				<br><br>

				<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<b>[$FROM_ADDRESS$]</b>
					</td>
					<td style="padding-left: 10px;"></td>
					<td>
						<%= emailFromAddress %>
					</td>
				</tr>
				<tr>
					<td>
						<b>[$FROM_NAME$]</b>
					</td>
					<td style="padding-left: 10px;"></td>
					<td>
						<%= emailFromName %>
					</td>
				</tr>
				<tr>
					<td>
						<b>[$PORTAL_URL$]</b>
					</td>
					<td style="padding-left: 10px;"></td>
					<td>
						<%= company.getPortalURL() %>
					</td>
				</tr>

				<c:if test='<%= tabs3.equals("password-sent-email") %>'>
					<tr>
						<td>
							<b>[$REMOTE_ADDRESS$]</b>
						</td>
						<td style="padding-left: 10px;"></td>
						<td>
							The browser's remote address
						</td>
					</tr>
					<tr>
						<td>
							<b>[$REMOTE_HOST$]</b>
						</td>
						<td style="padding-left: 10px;"></td>
						<td>
							The browser's remote host
						</td>
					</tr>
				</c:if>

				<tr>
					<td>
						<b>[$TO_ADDRESS$]</b>
					</td>
					<td style="padding-left: 10px;"></td>
					<td>
						The address of the email recipient
					</td>
				</tr>
				<tr>
					<td>
						<b>[$TO_NAME$]</b>
					</td>
					<td style="padding-left: 10px;"></td>
					<td>
						The name of the email recipient
					</td>
				</tr>

				<c:if test='<%= tabs3.equals("password-sent-email") %>'>
					<tr>
						<td>
							<b>[$USER_AGENT$]</b>
						</td>
						<td style="padding-left: 10px;"></td>
						<td>
							The browser's user agent
						</td>
					</tr>
				</c:if>

				<tr>
					<td>
						<b>[$USER_ID$]</b>
					</td>
					<td style="padding-left: 10px;"></td>
					<td>
						The user ID
					</td>
				</tr>
				<tr>
					<td>
						<b>[$USER_PASSWORD$]</b>
					</td>
					<td style="padding-left: 10px;"></td>
					<td>
						The user password
					</td>
				</tr>
				</table>
			</c:when>
			<c:otherwise>
				<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<%= LanguageUtil.get(pageContext, "name") %>
					</td>
					<td style="padding-left: 10px;"></td>
					<td>
						<input class="form-text" name="<portlet:namespace />emailFromName" size="<%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>" type="text" value="<%= emailFromName %>">
					</td>
				</tr>
				<tr>
					<td>
						<%= LanguageUtil.get(pageContext, "address") %>
					</td>
					<td style="padding-left: 10px;"></td>
					<td>
						<input class="form-text" name="<portlet:namespace />emailFromAddress" size="<%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>" type="text" value="<%= emailFromAddress %>">
					</td>
				</tr>
				</table>
			</c:otherwise>
		</c:choose>

		<br>

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "save") %>' onClick="<portlet:namespace />saveEmails();">
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="<%= GetterUtil.getBoolean(PropsUtil.get(PropsUtil.SESSION_TRACKER_MEMORY_ENABLED)) %>">

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

				Map currentUsers = (Map)WebAppPool.get(company.getCompanyId(), WebKeys.CURRENT_USERS);

				Map.Entry[] currentUsersArray = (Map.Entry[])currentUsers.entrySet().toArray(new Map.Entry[0]);

				List results = new ArrayList();

				for (int i = 0; i < currentUsersArray.length; i++) {
					Map.Entry mapEntry = currentUsersArray[i];

					results.add(mapEntry.getValue());
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

					row.addText(userTracker.getUserId(), rowURL);

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
	</c:otherwise>
</c:choose>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.admin.users.jsp";
%>