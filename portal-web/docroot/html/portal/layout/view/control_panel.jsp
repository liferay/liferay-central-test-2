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

<%@ include file="/html/portal/init.jsp" %>

<%
String ppid = ParamUtil.getString(request, "p_p_id");

if (ppid.equals(PortletKeys.PORTLET_CONFIGURATION)) {
	ppid = ParamUtil.getString(request, PortalUtil.getPortletNamespace(ppid) + "portletResource");
}

if (ppid.equals(PortletKeys.EXPANDO)) {
	String modelResource = ParamUtil.getString(request, PortalUtil.getPortletNamespace(ppid) + "modelResource");

	if (modelResource.equals(User.class.getName())) {
		ppid = PortletKeys.ENTERPRISE_ADMIN_USERS;
	}
	else if (modelResource.equals(Organization.class.getName())) {
		ppid = PortletKeys.ENTERPRISE_ADMIN_ORGANIZATIONS;
	}
}

String category = PortalUtil.getControlPanelCategory(themeDisplay.getCompanyId(), ppid);
%>

<c:if test="<%= !themeDisplay.isStateExclusive() && !themeDisplay.isStatePopUp() %>">

	<%
	String panelCategory = StringPool.BLANK;

	if (category.equals(PortletCategoryKeys.CONTENT)) {
		panelCategory = "panel-manage-content";
	}
	else if (category.equals(PortletCategoryKeys.MY)) {
		panelCategory = "panel-manage-my";
	}
	else if (category.equals(PortletCategoryKeys.PORTAL)) {
		panelCategory = "panel-manage-portal";
	}
	else if (category.equals(PortletCategoryKeys.SERVER)) {
		panelCategory = "panel-manage-server";
	}
	else {
		panelCategory = "panel-manage-frontpage";
	}

	Group currentGroup = themeDisplay.getScopeGroup();
	%>

	<div id="content-wrapper">
		<table class="lfr-ctrl-panel <%= panelCategory %>">
		<tr>
			<td class="panel-page-menu" valign="top">
				<liferay-portlet:runtime portletName="87" />
			</td>
			<td class="panel-page-content <%= (!layoutTypePortlet.hasStateMax()) ? "panel-page-frontpage" : "panel-page-application" %>" valign="top">
				<table class="panel-page-content-menu">
				<tr>
					<td>
						<c:choose>
							<c:when test="<%= category.equals(PortletCategoryKeys.CONTENT) %>">
								<h2><liferay-ui:message key="content-for" /> <a href="javascript: ;" class="lfr-group-selector"><%= currentGroup.isUser() ? LanguageUtil.get(pageContext, "my-community") : currentGroup.getDescriptiveName() %></a></h2>

								<div class="lfr-panel-container lfr-floating-container" id="myPanel">

									<%
									List<Group> manageableGroups = GroupServiceUtil.getManageableGroups(themeDisplay.getUserId(), ActionKeys.MANAGE_LAYOUTS);
									List<Organization> manageableOrganizations = OrganizationServiceUtil.getManageableOrganizations(themeDisplay.getUserId(), ActionKeys.MANAGE_LAYOUTS);
									%>

									<c:if test="<%= !manageableGroups.isEmpty() %>">
										<div class="lfr-panel lfr-collapsible">
											<div class="lfr-panel-titlebar lfr-has-button">
												<h3 class="lfr-panel-title"><span><liferay-ui:message key="communities" /></span></h3>

												<a href="javascript: ;" class="lfr-panel-button"></a>
											</div>

											<div class="lfr-panel-content">
												<ul>

													<%
													for (int i = 0; i < manageableGroups.size(); i++) {
														Group group = manageableGroups.get(i);
													%>

														<c:if test="<%= (i != 0) && (i % 7 == 0 ) %>">
															</ul>
															<ul>
														</c:if>

														<li>
															<a href="<%= HttpUtil.setParameter(PortalUtil.getCurrentURL(request), "doAsGroupId", group.getGroupId()) %>"><%= group.isUser() ? LanguageUtil.get(pageContext, "my-community") : group.getDescriptiveName() %></a>
														</li>

													<%
													}
													%>

												</ul>
											</div>
										</div>
									</c:if>

									<c:if test="<%= !manageableOrganizations.isEmpty() %>">
										<div class="lfr-panel lfr-collapsible">
											<div class="lfr-panel-titlebar lfr-has-button">
												<h3 class="lfr-panel-title"><span><liferay-ui:message key="organizations" /></span></h3>

												<a href="javascript: ;" class="lfr-panel-button"></a>
											</div>

											<div class="lfr-panel-content">
												<ul>

													<%
													for (int i = 0; i < manageableOrganizations.size(); i++) {
														Organization organization = manageableOrganizations.get(i);
													%>

														<c:if test="<%= (i != 0) && (i % 7 == 0 ) %>">
															</ul>
															<ul>
														</c:if>

														<li>
															<a href="<%= HttpUtil.setParameter(PortalUtil.getCurrentURL(request), "doAsGroupId", organization.getGroup().getGroupId()) %>"><%= organization.getName() %></a>
														</li>

													<%
													}
													%>

												</ul>
											</div>
										</div>
									</c:if>
								</div>
							</c:when>
							<c:when test="<%= category.equals(PortletCategoryKeys.PORTAL) %>">
								<h2>
									<liferay-ui:message key="portal" />

									<c:if test="<%= CompanyLocalServiceUtil.getCompaniesCount() > 1 %>">
										<%= company.getName() %>
									</c:if>
								</h2>
							</c:when>
							<c:otherwise>

								<%
								String title = "control-panel";

								if (Validator.isNotNull(category)) {
									category = "category." + category;
								}
								%>

								<h2><liferay-ui:message key="<%= title %>" /></h2>
							</c:otherwise>
						</c:choose>
					</td>
					<td align="right">

						<%
						String refererGroupDescriptiveName = null;
						String backURL = null;

						if (themeDisplay.getRefererPlid() > 0) {
							Layout refererLayout = LayoutLocalServiceUtil.getLayout(themeDisplay.getRefererPlid());

							Group refererGroup = refererLayout.getGroup();

							refererGroupDescriptiveName = refererGroup.getDescriptiveName();

							if (refererGroup.isUser()) {
								refererGroupDescriptiveName = LanguageUtil.get(pageContext, "my-community");
							}

							backURL = PortalUtil.getLayoutURL(refererLayout, themeDisplay);
						}
						else {
							refererGroupDescriptiveName = GroupConstants.GUEST;
							backURL = themeDisplay.getURLPortal() + PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING + "/guest";

							if (Validator.isNotNull(themeDisplay.getDoAsUserId())) {
								backURL = HttpUtil.addParameter(backURL, "doAsUserId", themeDisplay.getDoAsUserId());
							}

							if (Validator.isNotNull(themeDisplay.getDoAsUserLanguageId())) {

								backURL = HttpUtil.addParameter(backURL, "doAsUserLanguageId", themeDisplay.getDoAsUserLanguageId());
							}
						}
						%>

						<div>
							<a class="portlet-icon-back" href="<%= backURL %>"><%= LanguageUtil.format(pageContext, "back-to-x", refererGroupDescriptiveName) %></a>
						</div>

					</td>
				</tr>
				</table>
</c:if>

<%
if (themeDisplay.isStateExclusive() || themeDisplay.isStatePopUp() || layoutTypePortlet.hasStateMax()) {
	String content = null;

	if (themeDisplay.isStateExclusive()) {
		content = LayoutTemplateLocalServiceUtil.getContent("exclusive", true, theme.getThemeId());
	}
	else if (themeDisplay.isStatePopUp()) {
		content = LayoutTemplateLocalServiceUtil.getContent("pop_up", true, theme.getThemeId());
	}
	else {
		ppid = StringUtil.split(layoutTypePortlet.getStateMax())[0];

		content = LayoutTemplateLocalServiceUtil.getContent("max", true, theme.getThemeId());
	}
%>

	<%= RuntimePortletUtil.processTemplate(application, request, response, pageContext, ppid, content) %>

<%
}
else {
	String description = StringPool.BLANK;

	if (Validator.isNull(description)) {
		description = LanguageUtil.get(pageContext, "please-select-a-tool-from-the-left-menu");
	}
%>

	<div class="portlet-msg-info">
		<%= description %>
	</div>

<%
}
%>

<c:if test="<%= !themeDisplay.isStateExclusive() && !themeDisplay.isStatePopUp() %>">
			</td>
		</tr>
		</table>
	</div>
</c:if>

<style type="text/css">
	.lfr-group-selector {
		background: url(<%= themeDisplay.getPathThemeImages()%>/arrows/05_down.png) no-repeat 100% 50%;
		padding: 2px;
		padding-right: 20px;
	}
</style>

<script>
	jQuery(
		function () {
			new Liferay.FloatingPanel(
				{
				container: '#myPanel',
				trigger: '.lfr-group-selector',
				paging: true
				}
			);
		}
	);
</script>