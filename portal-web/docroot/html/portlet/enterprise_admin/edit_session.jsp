<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
String redirect = ParamUtil.getString(request, "redirect");

String sessionId = ParamUtil.getString(request, "sessionId");

UserTracker userTracker = LiveUsers.getUserTracker(company.getCompanyId(), sessionId);

List<UserTrackerPath> paths = userTracker.getPaths();
int numHits = userTracker.getHits();

userTracker = userTracker.toEscapedModel();
%>

<portlet:actionURL var="editSessionURL">
	<portlet:param name="struts_action" value="/enterprise_admin/edit_session" />
</portlet:actionURL>

<aui:form action="<%= editSessionURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="sessionId" type="hidden" value="<%= sessionId %>" />

	<liferay-ui:tabs
		names="live-session"
		backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
	/>

	<c:choose>
		<c:when test="<%= userTracker == null %>">
			<liferay-ui:message key="session-id-not-found" />

			<br /><br />

			<aui:button onClick="<%= redirect %>" type="cancel" />
		</c:when>
		<c:otherwise>

			<%
			User user2 = null;

			try {
				user2 = UserLocalServiceUtil.getUserById(userTracker.getUserId());
			}
			catch (NoSuchUserException nsue) {
			}

			boolean userSessionAlive = false;
			%>

			<aui:fieldset>
				<aui:field-wrapper label="session-id">
					<%= HtmlUtil.escape(sessionId) %>
				</aui:field-wrapper>

				<aui:field-wrapper label="user-id">
					<%= userTracker.getUserId() %>
				</aui:field-wrapper>

				<aui:field-wrapper label="name">
					<%= (user2 != null) ? user2.getFullName() : LanguageUtil.get(pageContext, "not-available") %>
				</aui:field-wrapper>

				<aui:field-wrapper label="email-address">
					<%= (user2 != null) ? user2.getEmailAddress() : LanguageUtil.get(pageContext, "not-available") %>
				</aui:field-wrapper>

				<aui:field-wrapper label="last-request">
					<%= dateFormatDateTime.format(userTracker.getModifiedDate()) %>
				</aui:field-wrapper>

				<aui:field-wrapper label="num-of-hits">
					<%= numHits %>
				</aui:field-wrapper>

				<aui:field-wrapper label="browser-os-type">
					<%= userTracker.getUserAgent() %>
				</aui:field-wrapper>

				<aui:field-wrapper label="remote-host-ip">
					<%= userTracker.getRemoteAddr() %> / <%= userTracker.getRemoteHost() %>
				</aui:field-wrapper>

				<liferay-ui:panel-container extended="<%= true %>" id="sessionHistoryPanelContainer" persistState="<%= true %>">
					<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" id="sessionAccessedURLsPanels" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "accessed-urls") %>'>
						<table border="0" cellpadding="4" cellspacing="0" width="100%">

							<%
							for (int i = 0; i < paths.size(); i++) {
								UserTrackerPath userTrackerPath = paths.get(i);

								String className = "portlet-section-body results-row";
								String classHoverName = "portlet-section-body-hover results-row hover";

								if (MathUtil.isEven(i)) {
									className = "portlet-section-alternate results-row alt";
									classHoverName = "portlet-section-alternate-hover results-row alt hover";
								}
							%>

								<tr class="<%= className %>" style="font-size: xx-small;" onMouseEnter="this.className = '<%= classHoverName %>';" onMouseLeave="this.className = '<%= className %>';">
									<td class="lfr-top">
										<%= StringUtil.replace(userTrackerPath.getPath(), "&", "& ") %>
									</td>
									<td class="lfr-top" nowrap>
										<%= dateFormatDateTime.format(userTrackerPath.getPathDate()) %>
									</td>
								</tr>

							<%
							}
							%>

							</table>
					</liferay-ui:panel>

					<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" id="sessionAttributesPanel" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "session-attributes") %>'>
						<table border="0" cellpadding="4" cellspacing="0" width="100%">

							<%
							userSessionAlive = true;

							HttpSession userSession = PortalSessionContext.get(sessionId);

							if (userSession != null) {
								try {
									int counter = 0;

									Set sortedAttrNames = new TreeSet();

									Enumeration enu = userSession.getAttributeNames();

									while (enu.hasMoreElements()) {
										String attrName = (String)enu.nextElement();

										sortedAttrNames.add(attrName);
									}

									Iterator itr = sortedAttrNames.iterator();

									while (itr.hasNext()) {
										String attrName = (String)itr.next();

										String className = "portlet-section-body results-row";
										String classHoverName = "portlet-section-body-hover results-row hover";

										if (MathUtil.isEven(counter++)) {
											className = "portlet-section-alternate results-row alt";
											classHoverName = "portlet-section-alternate-hover results-row alt hover";
										}
							%>

										<tr class="<%= className %>" style="font-size: xx-small;" onMouseEnter="this.className = '<%= classHoverName %>';" onMouseLeave="this.className = '<%= className %>';">
											<td class="lfr-top">
												<%= attrName %>
											</td>
										</tr>

							<%
									}
								}
								catch (Exception e) {
									userSessionAlive = false;

									e.printStackTrace();
								}
							}
							%>

							</table>
					</liferay-ui:panel>
				</liferay-ui:panel-container>
			</aui:fieldset>

			<aui:button-row>
				<c:if test="<%= userSessionAlive && !session.getId().equals(sessionId) %>">
					<aui:button type="submit" value="kill-session" />
				</c:if>

				<aui:button onClick="<%= redirect %>" type="cancel" />
			</aui:button-row>
		</c:otherwise>
	</c:choose>
</aui:form>