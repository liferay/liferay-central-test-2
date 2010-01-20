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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portal.security.permission.ResourceActionsUtil" %>

<%
String randomNamespace = DeterminateKeyGenerator.generate("taglib_ui_input_permissions_page") + StringPool.UNDERLINE;

String formName = namespace + request.getAttribute("liferay-ui:input-permissions:formName");
String modelName = (String)request.getAttribute("liferay-ui:input-permissions:modelName");
%>

<c:choose>
	<c:when test="<%= user.getDefaultUser() %>">
		<liferay-ui:message key="not-available" />
	</c:when>
	<c:when test="<%= modelName != null %>">

		<%
		Group group = themeDisplay.getScopeGroup();
		Group layoutGroup = layout.getGroup();

		List communityPermissions = ListUtil.fromArray(request.getParameterValues("communityPermissions"));
		List guestPermissions = ListUtil.fromArray(request.getParameterValues("guestPermissions"));

		List supportedActions = (List)request.getAttribute("liferay-ui:input-permissions:supportedActions");
		List communityDefaultActions = (List)request.getAttribute("liferay-ui:input-permissions:communityDefaultActions");
		List guestDefaultActions = (List)request.getAttribute("liferay-ui:input-permissions:guestDefaultActions");
		List guestUnsupportedActions = (List)request.getAttribute("liferay-ui:input-permissions:guestUnsupportedActions");

		boolean submitted = (request.getParameter("communityPermissions") != null);

		boolean inputPermissionsShowConfigure = ParamUtil.getBoolean(request, "inputPermissionsShowConfigure");
		boolean inputPermissionsShowMore = ParamUtil.getBoolean(request, "inputPermissionsShowMore");

		boolean inputPermissionsPublicChecked = false;

		if (layoutGroup.getName().equals(GroupConstants.CONTROL_PANEL)) {
			if (!group.hasPrivateLayouts() && guestDefaultActions.contains(ActionKeys.VIEW)) {
				inputPermissionsPublicChecked = true;
			}
		}
		else if (layout.isPublicLayout() && guestDefaultActions.contains(ActionKeys.VIEW)) {
			inputPermissionsPublicChecked = true;
		}
		%>

		<input id="<%= randomNamespace %>inputPermissionsShowConfigure" name="<%= namespace %>inputPermissionsShowConfigure" type="hidden" value="<%= inputPermissionsShowConfigure %>" />
		<input id="<%= randomNamespace %>inputPermissionsShowMore" name="<%= namespace %>inputPermissionsShowMore" type="hidden" value="<%= inputPermissionsShowMore %>" />

		<div class="<%= inputPermissionsShowConfigure ? "aui-helper-hidden" : "" %>">
			<label class="inline-label" for="<%= namespace %>inputPermissionsPublic"><input <%= inputPermissionsPublicChecked ? "checked" : "" %> id="<%= namespace %>inputPermissionsPublic" name="<%= namespace %>inputPermissionsPublic" onclick="<%= randomNamespace %>updatePermissionsGuestView();" type="checkbox" /> <liferay-ui:message key="public" /> <liferay-ui:icon-help message="input-permissions-public-help" /></label>

			<a href="javascript:<%= randomNamespace %>inputPermissionsConfigure();" id="<%= randomNamespace %>inputPermissionsConfigureLink" style="margin-left: 10px;"><liferay-ui:message key="configure" /> &raquo;</a> <liferay-ui:icon-help message="input-permissions-configure-help" /></label>

			<a class="aui-helper-hidden" href="javascript:<%= randomNamespace %>inputPermissionsDismiss();" id="<%= randomNamespace %>inputPermissionsDismissLink" style="margin-left: 10px;">&laquo; <liferay-ui:message key="dismiss" /></a>
		</div>

		<table class="lfr-table <%= inputPermissionsShowConfigure ? "" : "aui-helper-hidden" %>" id="<%= randomNamespace %>inputPermissionsTable">
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
			boolean guestChecked = inputPermissionsPublicChecked && guestDefaultActions.contains(action);
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

			<tr class="<%= showAction ? "" : "aui-helper-hidden" %>" id="<%= randomNamespace %>inputPermissionsAction<%= action %>">
				<td style="text-align: right;">
					<%= ResourceActionsUtil.getAction(pageContext, action) %>
				</td>

				<c:if test="<%= group.isCommunity() || group.isOrganization() %>">
					<td style="text-align: center;">
						<label class="hidden-label" for="<%= namespace %>communityPermissions"><%= LanguageUtil.format(pageContext, "give-x-permission-to-community-members", ResourceActionsUtil.getAction(pageContext, action)) %></label>

						<input <%= communityChecked ? "checked" : "" %> id="<%= namespace %>communityPermissions" name="<%= namespace %>communityPermissions" type="checkbox" value="<%= action %>">
					</td>
				</c:if>

				<td style="text-align: right;">
					<label class="hidden-label" for="<%= namespace %>guestPermissions"><%= LanguageUtil.format(pageContext, "give-x-permission-to-guest-members", ResourceActionsUtil.getAction(pageContext, action)) %></label>

					<input <%= guestChecked ? "checked" : "" %> <%= guestDisabled ? "disabled" : "" %> id="<%= namespace %>guestPermissions" name="<%= namespace %>guestPermissions" <%= action.equals(ActionKeys.VIEW) ? "onclick=\"" + randomNamespace + "updatePermissionsPublic();\"" : "" %> type="checkbox" value="<%= action %>">
				</td>
			</tr>

		<%
		}
		%>

		</table>

		<div class="<%= !inputPermissionsShowConfigure || inputPermissionsShowMore ? "aui-helper-hidden" : "" %>" id="<%= randomNamespace %>inputPermissionsMoreLink">
			<a href="javascript:<%= randomNamespace %>inputPermissionsMore();"><liferay-ui:message key="more" /> &raquo;</a>
		</div>

		<aui:script>
			function <%= randomNamespace %>inputPermissionsConfigure() {
				<%= randomNamespace %>updatePermissionsGuestView();

				AUI().one("#<%= randomNamespace %>inputPermissionsDismissLink").show();
				AUI().one("#<%= randomNamespace %>inputPermissionsTable").show();

				if (AUI().one("#<%= randomNamespace %>inputPermissionsShowMore").val() != "true") {
					AUI().one("#<%= randomNamespace %>inputPermissionsMoreLink").show();
				}

				<%
				for (int i = 0; i < supportedActions.size(); i++) {
					String action = (String)supportedActions.get(i);

					if (communityDefaultActions.contains(action) || guestDefaultActions.contains(action)) {
				%>

						AUI().one("#<%= randomNamespace %>inputPermissionsAction<%= action %>").show();

				<%
					}
				}
				%>

				AUI().one("#<%= randomNamespace %>inputPermissionsConfigureLink").hide();
				AUI().one("#<%= randomNamespace %>inputPermissionsShowConfigure").val("true");
			}

			function <%= randomNamespace %>inputPermissionsDismiss() {
				<%= randomNamespace %>updatePermissionsGuestView();

				AUI().one("#<%= randomNamespace %>inputPermissionsConfigureLink").show();
				AUI().one("#<%= randomNamespace %>inputPermissionsTable").hide();
				AUI().one("#<%= randomNamespace %>inputPermissionsMoreLink").hide();

				<%
				for (int i = 0; i < supportedActions.size(); i++) {
					String action = (String)supportedActions.get(i);

					if (communityDefaultActions.contains(action) || guestDefaultActions.contains(action)) {
				%>

						AUI().one("#<%= randomNamespace %>inputPermissionsAction<%= action %>").hide();

				<%
					}
				}
				%>

				AUI().one("#<%= randomNamespace %>inputPermissionsDismissLink").hide();
				AUI().one("#<%= randomNamespace %>inputPermissionsShowConfigure").val("false");
			}

			function <%= randomNamespace %>inputPermissionsMore() {

				<%
				for (int i = 0; i < supportedActions.size(); i++) {
					String action = (String)supportedActions.get(i);

					if (!communityDefaultActions.contains(action) && !guestDefaultActions.contains(action)) {
				%>

						AUI().one("#<%= randomNamespace %>inputPermissionsAction<%= action %>").show();

				<%
					}
				}
				%>

				AUI().one("#<%= randomNamespace %>inputPermissionsMoreLink").hide();
				AUI().one("#<%= randomNamespace %>inputPermissionsShowMore").val("true");
			}

			function <%= randomNamespace %>updatePermissionsCommunity(value) {
				var publicCheckbox = AUI().one("#<%= namespace %>inputPermissionsPublic");
				var guestViewCheckbox = AUI().one('input[name="<%= namespace %>guestPermissions"][value="' + value + '"]');

				if (guestViewCheckbox) {
					guestViewCheckbox.set("checked", publicCheckbox.get("checked"));
				}
			}

			function <%= randomNamespace %>updatePermissionsGuestView() {
				var publicCheckbox = AUI().one("#<%= namespace %>inputPermissionsPublic");
				var guestViewCheckbox = AUI().one('input[name="<%= namespace %>guestPermissions"][value="VIEW"]');

				if (guestViewCheckbox) {
					guestViewCheckbox.set("checked", publicCheckbox.get("checked"));
				}
			}

			function <%= randomNamespace %>updatePermissionsPublic() {
				var publicCheckbox = AUI().one("#<%= namespace %>inputPermissionsPublic");
				var guestViewCheckbox = AUI().one('input[name="<%= namespace %>guestPermissions"][value="VIEW"]');

				if (publicCheckbox) {
					publicCheckbox.set("checked", guestViewCheckbox.get("checked"));
				}
			}
		</aui:script>
	</c:when>
	<c:otherwise>

		<%
		boolean addCommunityPermissions = ParamUtil.getBoolean(request, "addCommunityPermissions", true);
		boolean addGuestPermissions = ParamUtil.getBoolean(request, "addGuestPermissions", true);
		%>

		<input name="<%= namespace %>addCommunityPermissions" type="hidden" value="<%= addCommunityPermissions %>" />
		<input name="<%= namespace %>addGuestPermissions" type="hidden" value="<%= addGuestPermissions %>" />

		<input <%= addCommunityPermissions ? "checked" : "" %> name="<%= namespace %>addCommunityPermissionsBox" type="checkbox" onClick="document.<%= formName %>.<%= namespace %>addCommunityPermissions.value = this.checked; <%= namespace %>checkCommunityAndGuestPermissions();"> <liferay-ui:message key="assign-default-permissions-to-community" /><br />
		<input <%= addGuestPermissions ? "checked" : "" %> name="<%= namespace %>addGuestPermissionsBox" type="checkbox" onClick="document.<%= formName %>.<%= namespace %>addGuestPermissions.value = this.checked; <%= namespace %>checkCommunityAndGuestPermissions();"> <liferay-ui:message key="assign-default-permissions-to-guest" /><br />
		<input <%= !addCommunityPermissions && !addGuestPermissions ? "checked" : "" %> name="<%= namespace %>addUserPermissionsBox" type="checkbox" onClick="document.<%= formName %>.<%= namespace %>addCommunityPermissions.value = !this.checked; document.<%= formName %>.<%= namespace %>addGuestPermissions.value = !this.checked; <%= namespace %>checkUserPermissions();"> <liferay-ui:message key="only-assign-permissions-to-me" />

		<aui:script>
			function <%= namespace %>checkCommunityAndGuestPermissions() {
				if (document.<%= formName %>.<%= namespace %>addCommunityPermissionsBox.checked ||
					document.<%= formName %>.<%= namespace %>addGuestPermissionsBox.checked) {

					document.<%= formName %>.<%= namespace %>addUserPermissionsBox.checked = false;
				}
				else if (!document.<%= formName %>.<%= namespace %>addCommunityPermissionsBox.checked &&
						 !document.<%= formName %>.<%= namespace %>addGuestPermissionsBox.checked) {

					document.<%= formName %>.<%= namespace %>addUserPermissionsBox.checked = true;
				}
			}

			function <%= namespace %>checkUserPermissions() {
				if (document.<%= formName %>.<%= namespace %>addUserPermissionsBox.checked) {
					document.<%= formName %>.<%= namespace %>addCommunityPermissionsBox.checked = false;
					document.<%= formName %>.<%= namespace %>addGuestPermissionsBox.checked = false;
				}
				else {
					document.<%= formName %>.<%= namespace %>addCommunityPermissionsBox.checked = true;
					document.<%= formName %>.<%= namespace %>addGuestPermissionsBox.checked = true;
				}
			}
		</aui:script>
	</c:otherwise>
</c:choose>