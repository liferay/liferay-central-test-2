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
String randomNamespace = PwdGenerator.getPassword(PwdGenerator.KEY3, 4) + StringPool.UNDERLINE;

String formName = randomNamespace + request.getAttribute("liferay-ui:input-permissions:formName");
String modelName = (String)request.getAttribute("liferay-ui:input-permissions:modelName");
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

		<table class="lfr-table" id="<%= randomNamespace %>inputPermissionsTable" style="display: <%= inputPermissionsShowConfigure ? "" : "none" %>;">
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

			<tr id="<%= randomNamespace %>inputPermissionsAction<%= action %>" style="display: <%= showAction ? "" : "none" %>;">
				<td style="text-align: right;">
					<%= ResourceActionsUtil.getAction(pageContext, action) %>
				</td>

				<c:if test="<%= group.isCommunity() || group.isOrganization() %>">
					<td style="text-align: center;">
						<input <%= communityChecked ? "checked" : "" %> name="<%= randomNamespace %>communityPermissions" type="checkbox" value="<%= action %>">
					</td>
				</c:if>

				<td style="text-align: right;">
					<input <%= guestChecked ? "checked" : "" %> <%= guestDisabled ? "disabled" : "" %> name="<%= randomNamespace %>guestPermissions" type="checkbox" value="<%= action %>">
				</td>
			</tr>

		<%
		}
		%>

		</table>

		<input id="<%= randomNamespace %>inputPermissionsShowConfigure" name="<%= randomNamespace %>inputPermissionsShowConfigure" type="hidden" value="<%= inputPermissionsShowConfigure %>" />
		<input id="<%= randomNamespace %>inputPermissionsShowMore" name="<%= randomNamespace %>inputPermissionsShowMore" type="hidden" value="<%= inputPermissionsShowMore %>" />

		<div id="<%= randomNamespace %>inputPermissionsConfigureLink" style="display: <%= inputPermissionsShowConfigure ? "none" : "" %>;">
		<a href="javascript: <%= randomNamespace %>inputPermissionsConfigure();"><liferay-ui:message key="configure" /> &raquo;</a>
		</div>

		<div id="<%= randomNamespace %>inputPermissionsMoreLink" style="display: <%= !inputPermissionsShowConfigure || inputPermissionsShowMore ? "none" : "" %>;">
		<a href="javascript: <%= randomNamespace %>inputPermissionsMore();"><liferay-ui:message key="more" /> &raquo;</a>
		</div>

		<script type="text/javascript">
			function <%= randomNamespace %>inputPermissionsConfigure() {
				document.getElementById("<%= randomNamespace %>inputPermissionsTable").style.display = "";
				document.getElementById("<%= randomNamespace %>inputPermissionsMoreLink").style.display = "";

				<%
				for (int i = 0; i < supportedActions.size(); i++) {
					String action = (String)supportedActions.get(i);

					if (communityDefaultActions.contains(action) || guestDefaultActions.contains(action)) {
				%>

						document.getElementById("<%= randomNamespace %>inputPermissionsAction<%= action %>").style.display = "";

				<%
					}
				}
				%>

				document.getElementById("<%= randomNamespace %>inputPermissionsConfigureLink").style.display = "none";
				document.getElementById("<%= randomNamespace %>inputPermissionsShowConfigure").value = "true";
			}

			function <%= randomNamespace %>inputPermissionsMore() {

				<%
				for (int i = 0; i < supportedActions.size(); i++) {
					String action = (String)supportedActions.get(i);

					if (!communityDefaultActions.contains(action) && !guestDefaultActions.contains(action)) {
				%>

						document.getElementById("<%= randomNamespace %>inputPermissionsAction<%= action %>").style.display = "";

				<%
					}
				}
				%>

				document.getElementById("<%= randomNamespace %>inputPermissionsMoreLink").style.display = "none";
				document.getElementById("<%= randomNamespace %>inputPermissionsShowMore").value = "true";
			}
		</script>
	</c:when>
	<c:otherwise>

		<%
		boolean addCommunityPermissions = ParamUtil.getBoolean(request, "addCommunityPermissions", true);
		boolean addGuestPermissions = ParamUtil.getBoolean(request, "addGuestPermissions", true);
		%>

		<input name="<%= randomNamespace %>addCommunityPermissions" type="hidden" value="<%= addCommunityPermissions %>" />
		<input name="<%= randomNamespace %>addGuestPermissions" type="hidden" value="<%= addGuestPermissions %>" />

		<input <%= addCommunityPermissions ? "checked" : "" %> name="<%= randomNamespace %>addCommunityPermissionsBox" type="checkbox" onClick="document.<%= formName %>.<%= randomNamespace %>addCommunityPermissions.value = this.checked; <%= randomNamespace %>checkCommunityAndGuestPermissions();"> <liferay-ui:message key="assign-default-permissions-to-community" /><br />
		<input <%= addGuestPermissions ? "checked" : "" %> name="<%= randomNamespace %>addGuestPermissionsBox" type="checkbox" onClick="document.<%= formName %>.<%= randomNamespace %>addGuestPermissions.value = this.checked; <%= randomNamespace %>checkCommunityAndGuestPermissions();"> <liferay-ui:message key="assign-default-permissions-to-guest" /><br />
		<input <%= !addCommunityPermissions && !addGuestPermissions ? "checked" : "" %> name="<%= randomNamespace %>addUserPermissionsBox" type="checkbox" onClick="document.<%= formName %>.<%= randomNamespace %>addCommunityPermissions.value = !this.checked; document.<%= formName %>.<%= randomNamespace %>addGuestPermissions.value = !this.checked; <%= randomNamespace %>checkUserPermissions();"> <liferay-ui:message key="only-assign-permissions-to-me" />

		<script type="text/javascript">
			function <%= randomNamespace %>checkCommunityAndGuestPermissions() {
				if (document.<%= formName %>.<%= randomNamespace %>addCommunityPermissionsBox.checked ||
					document.<%= formName %>.<%= randomNamespace %>addGuestPermissionsBox.checked) {

					document.<%= formName %>.<%= randomNamespace %>addUserPermissionsBox.checked = false;
				}
				else if (!document.<%= formName %>.<%= randomNamespace %>addCommunityPermissionsBox.checked &&
						 !document.<%= formName %>.<%= randomNamespace %>addGuestPermissionsBox.checked) {

					document.<%= formName %>.<%= randomNamespace %>addUserPermissionsBox.checked = true;
				}
			}

			function <%= randomNamespace %>checkUserPermissions() {
				if (document.<%= formName %>.<%= randomNamespace %>addUserPermissionsBox.checked) {
					document.<%= formName %>.<%= randomNamespace %>addCommunityPermissionsBox.checked = false;
					document.<%= formName %>.<%= randomNamespace %>addGuestPermissionsBox.checked = false;
				}
				else {
					document.<%= formName %>.<%= randomNamespace %>addCommunityPermissionsBox.checked = true;
					document.<%= formName %>.<%= randomNamespace %>addGuestPermissionsBox.checked = true;
				}
			}
		</script>
	</c:otherwise>
</c:choose>