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

<%@ page import="com.liferay.portal.security.permission.ResourceActionsUtil" %>

<%
String formName = namespace + request.getAttribute("liferay-ui:input-permissions:formName");
String modelName = (String)request.getAttribute("liferay-ui:input-permissions:modelName");
String uid = namespace + System.currentTimeMillis();
%>

<c:choose>
	<c:when test="<%= user.getDefaultUser() %>">
		<liferay-ui:message key="not-available" />
	</c:when>
	<c:when test="<%= modelName != null %>">

		<%
		Group group = layout.getGroup();

		List communityPermissions = ListUtil.fromArray(request.getParameterValues("communityPermissions"));
		List guestPermissions = ListUtil.fromArray(request.getParameterValues("guestPermissions"));

		List supportedActions = (List)request.getAttribute("liferay-ui:input-permissions:supportedActions");
		List communityDefaultActions = (List)request.getAttribute("liferay-ui:input-permissions:communityDefaultActions");
		List guestDefaultActions = (List)request.getAttribute("liferay-ui:input-permissions:guestDefaultActions");
		List guestUnsupportedActions = (List)request.getAttribute("liferay-ui:input-permissions:guestUnsupportedActions");

		boolean submitted = (request.getParameter("communityPermissions") != null);

		boolean inputPermissionsShowConfigure = ParamUtil.getBoolean(request, "inputPermissionsShowConfigure");
		boolean inputPermissionsShowMore = ParamUtil.getBoolean(request, "inputPermissionsShowMore");
		%>

		<table class="lfr-table" id="<%= uid %>inputPermissionsTable" style="display: <%= inputPermissionsShowConfigure ? "" : "none" %>;">
		<tr>
			<th style="text-align: right;">
				<liferay-ui:message key="action" />
			</th>

			<c:choose>
				<c:when test="<%= group.isCommunity() %>">
					<th style="text-align: center;">
						<liferay-ui:message key="community" />
					</th>
				</c:when>
				<c:when test="<%= group.isOrganization() %>">
					<th style="text-align: center;">
						<liferay-ui:message key="organization" />
					</th>
				</c:when>
			</c:choose>

			<th style="text-align: center;">
				<liferay-ui:message key="guest" />
			</th>
		</tr>

		<%
		for (int i = 0; i < supportedActions.size(); i++) {
			String action = (String)supportedActions.get(i);

			boolean communityChecked = communityDefaultActions.contains(action);
			boolean guestChecked = guestDefaultActions.contains(action);
			boolean guestDisabled = guestUnsupportedActions.contains(action);

			if (submitted) {
				communityChecked = communityPermissions.contains(action);
				guestChecked = guestPermissions.contains(action);
			}

			if (guestDisabled) {
				guestChecked = false;
			}

			boolean showAction = false;

			if (inputPermissionsShowMore || communityDefaultActions.contains(action) || guestDefaultActions.contains(action)) {
				showAction = true;
			}
		%>

			<tr id="<%= uid %>inputPermissionsAction<%= action %>" style="display: <%= showAction ? "" : "none" %>;">
				<td style="text-align: right;">
					<%= ResourceActionsUtil.getAction(pageContext, action) %>
				</td>

				<c:if test="<%= group.isCommunity() || group.isOrganization() %>">
					<td style="text-align: center;">
						<input <%= communityChecked ? "checked" : "" %> name="<%= namespace %>communityPermissions" type="checkbox" value="<%= action %>">
					</td>
				</c:if>

				<td style="text-align: right;">
					<input <%= guestChecked ? "checked" : "" %> <%= guestDisabled ? "disabled" : "" %> name="<%= namespace %>guestPermissions" type="checkbox" value="<%= action %>">
				</td>
			</tr>

		<%
		}
		%>

		</table>

		<input id="<%= uid %>inputPermissionsShowConfigure" name="<%= namespace %>inputPermissionsShowConfigure" type="hidden" value="<%= inputPermissionsShowConfigure %>" />
		<input id="<%= uid %>inputPermissionsShowMore" name="<%= namespace %>inputPermissionsShowMore" type="hidden" value="<%= inputPermissionsShowMore %>" />

		<div id="<%= uid %>inputPermissionsConfigureLink" style="display: <%= inputPermissionsShowConfigure ? "none" : "" %>;">
		<a href="javascript: <%= uid %>inputPermissionsConfigure();"><liferay-ui:message key="configure" /> &raquo;</a>
		</div>

		<div id="<%= uid %>inputPermissionsMoreLink" style="display: <%= !inputPermissionsShowConfigure || inputPermissionsShowMore ? "none" : "" %>;">
		<a href="javascript: <%= uid %>inputPermissionsMore();"><liferay-ui:message key="more" /> &raquo;</a>
		</div>

		<script type="text/javascript">
			function <%= uid %>inputPermissionsConfigure() {
				document.getElementById("<%= uid %>inputPermissionsTable").style.display = "";
				document.getElementById("<%= uid %>inputPermissionsMoreLink").style.display = "";

				<%
				for (int i = 0; i < supportedActions.size(); i++) {
					String action = (String)supportedActions.get(i);

					if (communityDefaultActions.contains(action) || guestDefaultActions.contains(action)) {
				%>

						document.getElementById("<%= uid %>inputPermissionsAction<%= action %>").style.display = "";

				<%
					}
				}
				%>

				document.getElementById("<%= uid %>inputPermissionsConfigureLink").style.display = "none";
				document.getElementById("<%= uid %>inputPermissionsShowConfigure").value = "true";
			}

			function <%= uid %>inputPermissionsMore() {

				<%
				for (int i = 0; i < supportedActions.size(); i++) {
					String action = (String)supportedActions.get(i);

					if (!communityDefaultActions.contains(action) && !guestDefaultActions.contains(action)) {
				%>

						document.getElementById("<%= uid %>inputPermissionsAction<%= action %>").style.display = "";

				<%
					}
				}
				%>

				document.getElementById("<%= uid %>inputPermissionsMoreLink").style.display = "none";
				document.getElementById("<%= uid %>inputPermissionsShowMore").value = "true";
			}
		</script>
	</c:when>
	<c:otherwise>

		<%
		boolean addCommunityPermissions = ParamUtil.getBoolean(request, "addCommunityPermissions", true);
		boolean addGuestPermissions = ParamUtil.getBoolean(request, "addGuestPermissions", true);
		%>

		<input name="<%= namespace %>addCommunityPermissions" type="hidden" value="<%= addCommunityPermissions %>" />
		<input name="<%= namespace %>addGuestPermissions" type="hidden" value="<%= addGuestPermissions %>" />

		<input <%= addCommunityPermissions ? "checked" : "" %> name="<%= namespace %>addCommunityPermissionsBox" type="checkbox" onClick="document.<%= formName %>.<%= uid %>addCommunityPermissions.value = this.checked; <%= uid %>checkCommunityAndGuestPermissions();"> <liferay-ui:message key="assign-default-permissions-to-community" /><br />
		<input <%= addGuestPermissions ? "checked" : "" %> name="<%= namespace %>addGuestPermissionsBox" type="checkbox" onClick="document.<%= formName %>.<%= uid %>addGuestPermissions.value = this.checked; <%= uid %>checkCommunityAndGuestPermissions();"> <liferay-ui:message key="assign-default-permissions-to-guest" /><br />
		<input <%= !addCommunityPermissions && !addGuestPermissions ? "checked" : "" %> name="<%= namespace %>addUserPermissionsBox" type="checkbox" onClick="document.<%= formName %>.<%= uid %>addCommunityPermissions.value = !this.checked; document.<%= formName %>.<%= uid %>addGuestPermissions.value = !this.checked; <%= uid %>checkUserPermissions();"> <liferay-ui:message key="only-assign-permissions-to-me" />

		<script type="text/javascript">
			function <%= uid %>checkCommunityAndGuestPermissions() {
				if (document.<%= formName %>.<%= uid %>addCommunityPermissionsBox.checked ||
					document.<%= formName %>.<%= uid %>addGuestPermissionsBox.checked) {

					document.<%= formName %>.<%= uid %>addUserPermissionsBox.checked = false;
				}
				else if (!document.<%= formName %>.<%= uid %>addCommunityPermissionsBox.checked &&
						 !document.<%= formName %>.<%= uid %>addGuestPermissionsBox.checked) {

					document.<%= formName %>.<%= uid %>addUserPermissionsBox.checked = true;
				}
			}

			function <%= uid %>checkUserPermissions() {
				if (document.<%= formName %>.<%= uid %>addUserPermissionsBox.checked) {
					document.<%= formName %>.<%= uid %>addCommunityPermissionsBox.checked = false;
					document.<%= formName %>.<%= uid %>addGuestPermissionsBox.checked = false;
				}
				else {
					document.<%= formName %>.<%= uid %>addCommunityPermissionsBox.checked = true;
					document.<%= formName %>.<%= uid %>addGuestPermissionsBox.checked = true;
				}
			}
		</script>
	</c:otherwise>
</c:choose>