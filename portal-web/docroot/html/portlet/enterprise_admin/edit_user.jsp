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

long classPK=0;

if (selContact != null) {
	classPK = selContact.getContactId();
}

String className = Contact.class.getName();

//Organizations

String organizationIds = ParamUtil.getString(request, "organizationsSearchContainerPrimaryKeys", null);

List organizations = new ArrayList();

if (Validator.isNotNull(organizationIds)) {
	long[] organizationIdsArray = StringUtil.split(organizationIds, 0L);

	organizations = OrganizationLocalServiceUtil.getOrganizations(organizationIdsArray);
	organizations = EnterpriseAdminUtil.filterOrganizations(permissionChecker, organizations);
}
else if (selUser != null) {
	organizations = selUser.getOrganizations();
}

//Communities

LinkedHashMap groupParams = new LinkedHashMap();

if (selUser != null) {
	groupParams.put("usersGroups", new Long(selUser.getUserId()));
}

List communities = GroupLocalServiceUtil.search(company.getCompanyId(), null, null, groupParams, 0, 50, new GroupNameComparator(true));
communities = EnterpriseAdminUtil.filterCommunities(permissionChecker, communities);

// Roles

List<Role> regularRoles = null;

if (selUser == null) {
	regularRoles = new ArrayList<Role>();
}
else {
	regularRoles = RoleLocalServiceUtil.getUserRoles(selUser.getUserId());
	regularRoles = EnterpriseAdminUtil.filterRoles(permissionChecker, regularRoles);
}

// Form Sections

String[] mainSections = PropsValues.USERS_FORM_ADD_MAIN;
String[] identificationSections = PropsValues.USERS_FORM_ADD_IDENTIFICATION;
String[] miscellaneousSections = PropsValues.USERS_FORM_ADD_MISCELLANEOUS;

if (selUser != null) {
	mainSections = PropsValues.USERS_FORM_UPDATE_MAIN;
	identificationSections = PropsValues.USERS_FORM_UPDATE_IDENTIFICATION;
	miscellaneousSections = PropsValues.USERS_FORM_UPDATE_MISCELLANEOUS;
}

String[] tempSections = new String[mainSections.length + identificationSections.length];
String[] allSections = new String[mainSections.length + identificationSections.length + miscellaneousSections.length];
ArrayUtil.combine(mainSections, identificationSections, tempSections);
ArrayUtil.combine(tempSections, miscellaneousSections, allSections);

String[][] categorySections = {mainSections, identificationSections, miscellaneousSections};

String curSection = mainSections[0];
%>

<script type="text/javascript">
	function <portlet:namespace />saveUser(cmd) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = cmd;

		var redirect = "<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_user" /></portlet:renderURL>";

		redirect += "&<portlet:namespace />backURL=<%= HttpUtil.encodeURL(backURL) %>&<portlet:namespace />p_u_i_d=";

		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_user" /></portlet:actionURL>");
	}

	function <portlet:namespace />createURL(href, value, onClick) {
		var anchorText = '<a href="' + href + '"' + (onClick ? ' onclick="' + onClick + '" ' : '') + '>' + value + '</a>';

		return anchorText;
	};
</script>

<liferay-util:include page="/html/portlet/enterprise_admin/user/toolbar.jsp">
	<liferay-util:param name="toolbar-item" value='<%= (selUser == null) ? "add-user" : "view-users" %>' />
</liferay-util:include>

<form class="uni-form" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />

<input name="<portlet:namespace />redirect" type="hidden" value="" />
<input name="<portlet:namespace />backURL" type="hidden" value="<%= HtmlUtil.escape(backURL) %>" />
<input name="<portlet:namespace />p_u_i_d" type="hidden" value='<%= (selUser != null) ? selUser.getUserId() : 0 %>' />

<div id="user">
	<table class="user-table" width="100%">
		<tr>
			<td>
				<%
				request.setAttribute("user.selUser",selUser);
				request.setAttribute("user.selContact", selContact);
				request.setAttribute("common.className", className);

				request.setAttribute("user.passwordPolicy", passwordPolicy);
				request.setAttribute("user.organizations", organizations);
				request.setAttribute("user.communities", communities);
				request.setAttribute("user.regularRoles", regularRoles);

				List<Website> websites = null;

				if (classPK <= 0) {
					websites = Collections.EMPTY_LIST;
				}
				else {
					websites = WebsiteServiceUtil.getWebsites(className, classPK);
				}

				request.setAttribute("common.websites", websites);
				%>

				<%
				for (String section : allSections) {
					String sectionId = EnterpriseAdminUtil.getIdName(section);
					String jspPath = "/html/portlet/enterprise_admin/user/" + EnterpriseAdminUtil.getJspName(section) + ".jsp";
				%>
					<div class="form-section <%= curSection.equals(section)? "selected" : StringPool.BLANK %>" id="<%= sectionId %>">
						<liferay-util:include page="<%= jspPath %>" />
					</div>
				<%
				}
				%>

				<div class="lfr-component form-navigation">

					<div class="user-info">
						<p class="float-container">
							<c:if test="<%= selUser != null %>">
								<img alt="<liferay-ui:message key="avatar" />" class="avatar" src='<%= themeDisplay.getPathImage() %>/user_<%= selUser.isFemale() ? "female" : "male" %>_portrait?img_id=<%= selUser.getPortraitId() %>&t=<%= ImageServletTokenUtil.getToken(selUser.getPortraitId()) %>' width="34" />

								<liferay-ui:message key="editing-user" /> <span><%= selUser.getFullName() %></span>
							</c:if>
						</p>
					</div>

					<%
					for (int i = 0; i < _CATEGORY_NAMES.length; i++) {
						String category = _CATEGORY_NAMES[i];
						String[] sections = categorySections[i];

						if (sections.length > 0) {
					%>
							<div class="menu-group">
								<h3><liferay-ui:message key="<%= category %>" /></h3>

								<ul>

									<%
									String errorSection = (String)request.getAttribute("user.errorSection");

									for (String section : sections) {
										String sectionId = EnterpriseAdminUtil.getIdName(section);

										boolean error = false;

										if (sectionId.equals(errorSection)) {
											error = true;

											curSection = section;
										}

										String cssClass = StringPool.BLANK;

										if (curSection.equals(section)) {
											cssClass += "selected";
										}

										if (error) {
											cssClass += " section-error";
										}
									%>

										<li class="<%= cssClass %>">
											<a href="#<%= sectionId %>" id='<%= sectionId %>Link'>

											<liferay-ui:message key="<%= section %>" />

											<span class="modified-notice"> (<liferay-ui:message key="modified" />) </span>

											<c:if test="<%= error %>">
												<span class="error-notice"> (<liferay-ui:message key="error" />) </span>
											</c:if>

											</a>
										</li>

									<%
									}
									%>
								</ul>
							</div>
					<%
						}
					}
					%>

					<div class="button-holder">
						<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveUser('<%= selUser == null ? Constants.ADD : Constants.UPDATE %>');" />  &nbsp;

						<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HttpUtil.encodeURL(backURL) %>';" /><br />
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

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<script type="text/javascript">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />screenName);
	</script>
</c:if>

<script type="text/javascript">
	jQuery(
		function () {
			var <portlet:namespace />formNavigator = new Liferay.EnterpriseAdmin.FormNavigator(
				{
					container: '#user'
				}
			);

			<%
			String errorSection = (String)request.getAttribute("user.errorSection");
			%>

			<c:if test="<%= Validator.isNotNull(errorSection) %>">
				<portlet:namespace />formNavigator._revealSection('#<%= errorSection %>', '');
			</c:if>
		}
	);
</script>

<%!
private static String[] _CATEGORY_NAMES = {"main-user-info", "identification", "miscellaneous"};
%>