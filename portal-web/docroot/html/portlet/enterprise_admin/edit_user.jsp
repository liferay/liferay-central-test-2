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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
themeDisplay.setIncludeServiceJs(true);

String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

User selUser = PortalUtil.getSelectedUser(request);

Contact selContact = null;

if (selUser != null) {
	selContact = selUser.getContact();
}

PasswordPolicy passwordPolicy = null;

if (selUser == null) {
	passwordPolicy = PasswordPolicyLocalServiceUtil.getDefaultPasswordPolicy(company.getCompanyId());
}
else {
	passwordPolicy = selUser.getPasswordPolicy();
}

String groupIds = ParamUtil.getString(request, "groupsSearchContainerPrimaryKeys");

List<Group> groups = Collections.EMPTY_LIST;

if (Validator.isNotNull(groupIds)) {
	long[] groupIdsArray = StringUtil.split(groupIds, 0L);

	groups = GroupLocalServiceUtil.getGroups(groupIdsArray);
}
else if (selUser != null) {
	groups = selUser.getGroups();
}

String organizationIds = ParamUtil.getString(request, "organizationsSearchContainerPrimaryKeys");

List<Organization> organizations = Collections.EMPTY_LIST;

if (Validator.isNotNull(organizationIds)) {
	long[] organizationIdsArray = StringUtil.split(organizationIds, 0L);

	organizations = OrganizationLocalServiceUtil.getOrganizations(organizationIdsArray);
}
else if (selUser != null) {
	organizations = selUser.getOrganizations();
}

String roleIds = ParamUtil.getString(request, "rolesSearchContainerPrimaryKeys");

List<Role> roles = Collections.EMPTY_LIST;

if (Validator.isNotNull(roleIds)) {
	long[] roleIdsArray = StringUtil.split(roleIds, 0L);

	roles = RoleLocalServiceUtil.getRoles(roleIdsArray);
}
else if (selUser != null) {
	roles = selUser.getRoles();
}

String[] mainSections = PropsValues.USERS_FORM_ADD_MAIN;
String[] identificationSections = PropsValues.USERS_FORM_ADD_IDENTIFICATION;
String[] miscellaneousSections = PropsValues.USERS_FORM_ADD_MISCELLANEOUS;

if (selUser != null) {
	mainSections = PropsValues.USERS_FORM_UPDATE_MAIN;
	identificationSections = PropsValues.USERS_FORM_UPDATE_IDENTIFICATION;
	miscellaneousSections = PropsValues.USERS_FORM_UPDATE_MISCELLANEOUS;
}

String[] allSections = ArrayUtil.append(mainSections, ArrayUtil.append(identificationSections, miscellaneousSections));

String[][] categorySections = {mainSections, identificationSections, miscellaneousSections};

String curSection = mainSections[0];
%>

<script type="text/javascript">
	function <portlet:namespace />createURL(href, value, onclick) {
		return '<a href="' + href + '"' + (onclick ? ' onclick="' + onclick + '" ' : '') + '>' + value + '</a>';
	};

	function <portlet:namespace />saveUser() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= (selUser == null) ? Constants.ADD : Constants.UPDATE %>";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_user" /></portlet:renderURL>&<portlet:namespace />backURL=<%= HttpUtil.encodeURL(backURL) %>&<portlet:namespace />p_u_i_d=";
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_user" /></portlet:actionURL>");
	}
</script>

<c:choose>
	<c:when test="<%= portletName.equals(PortletKeys.MY_ACCOUNT) %>">
		<liferay-util:include page="/html/portlet/my_account/tabs1.jsp">
			<liferay-util:param name="tabs1" value="profile" />
		</liferay-util:include>
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/html/portlet/enterprise_admin/user/toolbar.jsp">
			<liferay-util:param name="toolbarItem" value='<%= (selUser == null) ? "add" : "view-all" %>' />
		</liferay-util:include>
	</c:otherwise>
</c:choose>

<form class="uni-form" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="" />
<input name="<portlet:namespace />backURL" type="hidden" value="<%= HtmlUtil.escape(backURL) %>" />
<input name="<portlet:namespace />p_u_i_d" type="hidden" value="<%= (selUser != null) ? selUser.getUserId() : 0 %>" />

<div id="<portlet:namespace />sectionsContainer">
	<table class="user-table" width="100%">
	<tr>
		<td>

			<%
			request.setAttribute("user.selUser", selUser);
			request.setAttribute("user.selContact", selContact);
			request.setAttribute("user.passwordPolicy", passwordPolicy);
			request.setAttribute("user.groups", groups);
			request.setAttribute("user.organizations", organizations);
			request.setAttribute("user.roles", roles);

			request.setAttribute("common.className", Contact.class.getName());

			if (selContact != null) {
				request.setAttribute("common.classPK", selContact.getContactId());
			}

			for (String section : allSections) {
				String sectionId = _getSectionId(section);
				String sectionJsp = "/html/portlet/enterprise_admin/user/" + _getSectionJsp(section) + ".jsp";
			%>

				<div class="form-section <%= curSection.equals(section)? "selected" : StringPool.BLANK %>" id="<%= sectionId %>">
					<liferay-util:include page="<%= sectionJsp %>" />
				</div>

			<%
			}
			%>

			<div class="lfr-component form-navigation">
				<div class="user-info">
					<p class="float-container">
						<c:if test="<%= selUser != null %>">
							<img alt="<%= selUser.getFullName() %>" class="avatar" src="<%= themeDisplay.getPathImage() %>/user_<%= selUser.isFemale() ? "female" : "male" %>_portrait?img_id=<%= selUser.getPortraitId() %>&t=<%= ImageServletTokenUtil.getToken(selUser.getPortraitId()) %>" />

							<span><%= selUser.getFullName() %></span>
						</c:if>
					</p>
				</div>

				<%
				String[] categoryNames = _CATEGORY_NAMES;
				%>

				<%@ include file="/html/portlet/enterprise_admin/categories_navigation.jspf" %>

				<div class="button-holder">
					<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveUser();" />

					<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(backURL) %>';" />
				</div>

				<c:if test="<%= (selUser != null) && (passwordPolicy != null) && selUser.getLockout() %>">
					<div class="button-holder">
						<div class="portlet-msg-alert"><liferay-ui:message key="this-user-account-has-been-locked-due-to-excessive-failed-login-attempts" /></div>

						<input type="button" value="<liferay-ui:message key="unlock" />" onClick="<portlet:namespace />saveUser('unlock');" />
					</div>
				</c:if>
			</div>
		</td>
	</tr>
	</table>
</div>

</form>

<%
if (selUser != null) {
	PortalUtil.setPageSubtitle(selUser.getFullName(), request);
}
%>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<script type="text/javascript">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />screenName);
	</script>
</c:if>

<%!
private static String[] _CATEGORY_NAMES = {"user-information", "identification", "miscellaneous"};
%>