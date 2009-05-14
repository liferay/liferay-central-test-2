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
String cmd = ParamUtil.getString(request, Constants.CMD);

tabs1 = "roles";
String tabs2 = ParamUtil.getString(request, "tabs2", "current");

String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

Role role = (Role)request.getAttribute(WebKeys.ROLE);

boolean showModelResources = ParamUtil.getBoolean(request, "showModelResources", true);
String portletResource = ParamUtil.getString(request, "portletResource");

String portletResourceLabel = null;

if (Validator.isNotNull(portletResource)) {
	Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);

	if (portlet.getPortletId().equals(PortletKeys.PORTAL)) {
		portletResourceLabel = LanguageUtil.get(pageContext, "general");
	}
	else {
		portletResourceLabel = PortalUtil.getPortletTitle(portlet, application, locale);
	}
}

List modelResources = null;

if (Validator.isNotNull(portletResource)) {
	modelResources = ResourceActionsUtil.getPortletModelResources(portletResource);
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/enterprise_admin/edit_role_permissions");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("roleId", String.valueOf(role.getRoleId()));
portletURL.setParameter("portletResource", portletResource);

PortletURL permissionsSummaryURL = renderResponse.createRenderURL();

permissionsSummaryURL.setWindowState(WindowState.MAXIMIZED);

permissionsSummaryURL.setParameter("struts_action", "/enterprise_admin/edit_role_permissions");
permissionsSummaryURL.setParameter(Constants.CMD, Constants.VIEW);
permissionsSummaryURL.setParameter("tabs1", "roles");
permissionsSummaryURL.setParameter("roleId", String.valueOf(role.getRoleId()));

PortletURL editPermissionsURL = renderResponse.createRenderURL();

editPermissionsURL.setWindowState(WindowState.MAXIMIZED);

editPermissionsURL.setParameter("struts_action", "/enterprise_admin/edit_role_permissions");
editPermissionsURL.setParameter(Constants.CMD, Constants.EDIT);
editPermissionsURL.setParameter("tabs1", "roles");
editPermissionsURL.setParameter("roleId", String.valueOf(role.getRoleId()));
editPermissionsURL.setParameter("redirect", permissionsSummaryURL.toString());
%>

<script type="text/javascript">
	function <portlet:namespace />addPermissions(field) {
		var addPermissionsURL = field.value;

		if (addPermissionsURL == '') {
			addPermissionsURL = '<%= permissionsSummaryURL %>';
		}

		location.href = addPermissionsURL;
	}

	function <portlet:namespace />removeGroup(pos, target) {
		var selectedGroupIds = document.<portlet:namespace />fm['<portlet:namespace />groupIds' + target].value.split(",");
		var selectedGroupNames = document.<portlet:namespace />fm['<portlet:namespace />groupNames' + target].value.split("@@");

		selectedGroupIds.splice(pos, 1);
		selectedGroupNames.splice(pos, 1);

		<portlet:namespace />updateGroups(selectedGroupIds, selectedGroupNames, target);
	}

	function <portlet:namespace />selectGroup(groupId, name, target) {
		var selectedGroupIds = [];
		var selectedGroupIdsField = document.<portlet:namespace />fm['<portlet:namespace />groupIds' + target].value;

		if (selectedGroupIdsField != "") {
			selectedGroupIds = selectedGroupIdsField.split(",");
		}

		var selectedGroupNames = [];
		var selectedGroupNamesField = document.<portlet:namespace />fm['<portlet:namespace />groupNames' + target].value;

		if (selectedGroupNamesField != "") {
			selectedGroupNames = selectedGroupNamesField.split("@@");
		}

		if (!Liferay.Util.contains(selectedGroupIds, groupId)) {
			selectedGroupIds.push(groupId);
			selectedGroupNames.push(name);
		}

		<portlet:namespace />updateGroups(selectedGroupIds, selectedGroupNames, target);
	}

	function <portlet:namespace />updateActions() {
		var selectedTargets = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");

		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "actions";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<%= portletURL.toString() %>";
		document.<portlet:namespace />fm.<portlet:namespace />selectedTargets.value = selectedTargets;
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />updateGroups(selectedGroupIds, selectedGroupNames, target) {
		document.<portlet:namespace />fm['<portlet:namespace />groupIds' + target].value = selectedGroupIds.join(',');
		document.<portlet:namespace />fm['<portlet:namespace />groupNames' + target].value = selectedGroupNames.join('@@');

		var nameEl = document.getElementById("<portlet:namespace />groupHTML" + target);

		var groupsHTML = '';

		for (var i = 0; i < selectedGroupIds.length; i++) {
			var id = selectedGroupIds[i];
			var name = selectedGroupNames[i];

			groupsHTML += '<span class="ui-scope">' + name + '<a class="ui-scope-delete" href="javascript: <portlet:namespace />removeGroup(' + i + ', \'' + target + '\' );"><span>x</span></a></span>';
		}

		if (groupsHTML == '') {
			groupsHTML = '<%= LanguageUtil.get(pageContext, "portal") %>';
		}

		nameEl.innerHTML = groupsHTML;
	}
</script>

<liferay-util:include page="/html/portlet/enterprise_admin/role/toolbar.jsp">
	<liferay-util:param name="toolbarItem" value='<%= (role == null) ? "add" : "view-all" %>' />
	<liferay-util:param name="backURL" value="<%= backURL %>" />
</liferay-util:include>

<liferay-util:include page="/html/portlet/enterprise_admin/edit_role_tabs.jsp">
	<liferay-util:param name="tabs1" value="define-permissions" />
</liferay-util:include>

<liferay-ui:success key="permissionDeleted" message="the-permission-was-deleted" />
<liferay-ui:success key="permissionsUpdated" message="the-role-permissions-were-updated" />

<%@ include file="/html/portlet/enterprise_admin/edit_role_permissions_navigation.jspf" %>

<c:choose>
	<c:when test="<%= cmd.equals(Constants.VIEW) %>">

		<liferay-util:include page="/html/portlet/enterprise_admin/edit_role_permissions_summary.jsp" />

	</c:when>
	<c:otherwise>
		<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_role_permissions" /></portlet:actionURL>" id="<portlet:namespace />fm" method="post" name="<portlet:namespace />fm">
		<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
		<input name="<portlet:namespace />tabs2" type="hidden" value="<%= HtmlUtil.escape(tabs2) %>" />
		<input name="<portlet:namespace />redirect" type="hidden" value="" />
		<input name="<portlet:namespace />roleId" type="hidden" value="<%= role.getRoleId() %>" />
		<input name="<portlet:namespace />portletResource" type="hidden" value="<%= HtmlUtil.escape(portletResource) %>" />
		<input name="<portlet:namespace />modelResources" type="hidden" value='<%= (modelResources == null) ? "" : StringUtil.merge(modelResources) %>' />
		<input name="<portlet:namespace />selectedTargets" type="hidden" value="" />

		<c:choose>
			<c:when test="<%= !showModelResources %>">
				<h3><%= portletResourceLabel %></h3>

				<%
					request.setAttribute("edit_role_permissions.jsp-curPortletResource", portletResource);
				%>

				<liferay-util:include page="/html/portlet/enterprise_admin/edit_role_permissions_resource.jsp" />
			</c:when>
			<c:when test="<%= (modelResources != null) && (modelResources.size() > 0) %>">

				<%
				modelResources = ListUtil.sort(modelResources, new ModelResourceComparator(company.getCompanyId(), locale));

				for (int i = 0; i < modelResources.size(); i++) {
					String curModelResource = (String)modelResources.get(i);

					String curModelResourceName = ResourceActionsUtil.getModelResource(pageContext, curModelResource);
					%>

					<h3><%= curModelResourceName %></h3>

					<%
					request.removeAttribute("edit_role_permissions.jsp-curPortletResource");
					request.setAttribute("edit_role_permissions.jsp-curModelResource", curModelResource);
					request.setAttribute("edit_role_permissions.jsp-curModelResourceName", curModelResourceName);
					%>

					<liferay-util:include page="/html/portlet/enterprise_admin/edit_role_permissions_resource.jsp" />

				<%
				}
				%>
			</c:when>
		</c:choose>

		<br />

		<input type="button" value="<liferay-ui:message key="save" />" onclick="<portlet:namespace />updateActions();" />

		<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />

		</form>

		<script type="text/javascript">
			jQuery(
				function() {
					var form = jQuery("#<portlet:namespace />fm");

					var allBox = form.find("input[name=<portlet:namespace />actionAllBox]");
					var inputs = form.find("input[type=checkbox]").not(allBox);

					var inputsCount = inputs.length;

					if (inputs.not(":checked").length == 0) {
						allBox.attr("checked", true);
					}

					allBox.click(
						function() {
							var allBoxChecked = this.checked;

							if (allBoxChecked) {
								var uncheckedInputs = inputs.not(":checked");

								uncheckedInputs.trigger("click");
							}
							else {
								var checkedInputs = inputs.filter(":checked");

								checkedInputs.trigger("click");
							}

							allBox.attr("checked", allBoxChecked);
						}
					);

					inputs.click(
						function() {
							var uncheckedCount = inputs.not(":checked").length;

							if (this.checked) {
								if (uncheckedCount == 0) {
									allBox.attr("checked", true);
								}
							}
							else {
								if (inputsCount > uncheckedCount) {
									allBox.attr("checked", false);
								}
							}
						}
					);
				}
			);
		</script>
	</c:otherwise>
</c:choose>