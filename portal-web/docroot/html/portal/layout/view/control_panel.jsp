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

<%@ include file="/html/portal/init.jsp" %>

<%
String ppid = ParamUtil.getString(request, "p_p_id");

if (ppid.equals(PortletKeys.PORTLET_CONFIGURATION)) {
	String portletResource = ParamUtil.getString(request, PortalUtil.getPortletNamespace(ppid) + "portletResource");

	if (Validator.isNull(portletResource)) {
		portletResource = ParamUtil.getString(request, "portletResource");
	}

	if (Validator.isNotNull(portletResource)) {
		ppid = portletResource;
	}
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

if (ppid.equals(PortletKeys.PLUGIN_INSTALLER)) {
	ppid = PortletKeys.ADMIN_PLUGINS;
}

String category = PortalUtil.getControlPanelCategory(ppid, themeDisplay);

List<Layout> scopeLayouts = new ArrayList<Layout>();

boolean denyAccess = false;

if (Validator.isNotNull(ppid)) {
	Portlet portlet = PortletLocalServiceUtil.getPortletById(ppid);

	if ((portlet == null) ||
		(!portlet.isSystem() && !_hasPortlet(ppid, category, themeDisplay))) {

		denyAccess = true;
	}
}
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

	Layout scopeLayout = null;
	Group curGroup = themeDisplay.getScopeGroup();

	if (curGroup.isLayout()){
		scopeLayout = LayoutLocalServiceUtil.getLayout(curGroup.getClassPK());
		curGroup = scopeLayout.getGroup();
	}
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

								<%
								String curGroupLabel = LanguageUtil.get(pageContext, "default");

								List<Layout> curGroupLayouts = new ArrayList<Layout>();

								curGroupLayouts.addAll(LayoutLocalServiceUtil.getLayouts(curGroup.getGroupId(), false));
								curGroupLayouts.addAll(LayoutLocalServiceUtil.getLayouts(curGroup.getGroupId(), true));

								for (Layout curGroupLayout : curGroupLayouts) {
									if (curGroupLayout.hasScopeGroup()) {
										scopeLayouts.add(curGroupLayout);
									}
								}
								%>

								<h2>
									<liferay-ui:message key="content-for" /> <a href="javascript: ;" class="lfr-group-selector"><%= curGroup.isUser() ? LanguageUtil.get(pageContext, "my-community") : HtmlUtil.escape(curGroup.getDescriptiveName()) %></a>

									<c:if test="<%= !scopeLayouts.isEmpty() %>">
										<span class="nobr lfr-title-scope-selector">
											<liferay-ui:message key="with-scope" /> <a href="javascript: ;" class="lfr-scope-selector"><%= (scopeLayout == null) ? curGroupLabel : scopeLayout.getName(locale) %></a>
										</span>
									</c:if>
								</h2>

								<liferay-ui:panel-floating-container id="groupSelectorPanel" trigger=".lfr-group-selector" paging="<%= true %>">

									<%
									List<Group> manageableGroups = GroupServiceUtil.getManageableGroups(ActionKeys.MANAGE_LAYOUTS, PropsValues.CONTROL_PANEL_NAVIGATION_MAX_COMMUNITIES);
									List<Organization> manageableOrganizations = OrganizationServiceUtil.getManageableOrganizations(ActionKeys.MANAGE_LAYOUTS, PropsValues.CONTROL_PANEL_NAVIGATION_MAX_ORGANIZATIONS);
									%>

									<c:if test="<%= !manageableGroups.isEmpty() %>">
										<liferay-ui:panel id="communityPanel" title='<%= LanguageUtil.get(pageContext, "communities") %>' collapsible="<%= true %>" persistState="<%= true %>" extended="<%= true %>">
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
										</liferay-ui:panel>
									</c:if>

									<c:if test="<%= !manageableOrganizations.isEmpty() %>">
										<liferay-ui:panel id="organizationsPanel" title='<%= LanguageUtil.get(pageContext, "organizations") %>' collapsible="<%= true %>" persistState="<%= true %>" extended="<%= true %>">
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
														<a href="<%= HttpUtil.setParameter(PortalUtil.getCurrentURL(request), "doAsGroupId", organization.getGroup().getGroupId()) %>"><%= HtmlUtil.escape(organization.getName()) %></a>
													</li>

												<%
												}
												%>

											</ul>
										</liferay-ui:panel>
									</c:if>
								</liferay-ui:panel-floating-container>

								<c:if test="<%= !scopeLayouts.isEmpty() %>">
									<liferay-ui:panel-floating-container id="scopePanel" trigger=".lfr-scope-selector">
										<liferay-ui:panel id="" title="">
											<ul>
												<li>
													<a href="<%= HttpUtil.setParameter(PortalUtil.getCurrentURL(request), "doAsGroupId", curGroup.getGroupId()) %>"><%= curGroupLabel %></a>
												</li>

												<%
												for (Layout curScopeLayout : scopeLayouts) {
												%>

													<li>
														<a href="<%= HttpUtil.setParameter(PortalUtil.getCurrentURL(request), "doAsGroupId", curScopeLayout.getScopeGroup().getGroupId()) %>"><%= HtmlUtil.escape(curScopeLayout.getName(locale)) %></a>
													</li>

												<%
												}
												%>

											</ul>
										</liferay-ui:panel>
									</liferay-ui:panel-floating-container>
								</c:if>
							</c:when>
							<c:when test="<%= category.equals(PortletCategoryKeys.PORTAL) %>">
								<h2>
									<liferay-ui:message key="portal" />

									<c:if test="<%= CompanyLocalServiceUtil.getCompaniesCount(false) > 1 %>">
										<%= company.getName() %>
									</c:if>
								</h2>
							</c:when>
							<c:otherwise>

								<%
								String title = category;

								if (category.equals(PortletCategoryKeys.MY)) {
									title = user.getFullName();
								}

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
							List<Group> myPlaces = user.getMyPlaces(1);

							if (myPlaces.isEmpty()) {
								refererGroupDescriptiveName = GroupConstants.GUEST;
								backURL = themeDisplay.getURLHome();
							}
							else {
								Group myPlace = myPlaces.get(0);

								refererGroupDescriptiveName = myPlace.getDescriptiveName();

								PortletURL portletURL = new PortletURLImpl(request, PortletKeys.MY_PLACES, plid, PortletRequest.ACTION_PHASE);

								portletURL.setWindowState(WindowState.NORMAL);
								portletURL.setPortletMode(PortletMode.VIEW);

								portletURL.setParameter("struts_action", "/my_places/view");

								portletURL.setParameter("groupId", String.valueOf(myPlace.getGroupId()));

								if (myPlace.getPublicLayoutsPageCount() > 0) {
									portletURL.setParameter("privateLayout", "0");
								}
								else {
									portletURL.setParameter("privateLayout", "1");
								}

								backURL = portletURL.toString();
							}

							if (Validator.isNotNull(themeDisplay.getDoAsUserId())) {
								backURL = HttpUtil.addParameter(backURL, "doAsUserId", themeDisplay.getDoAsUserId());
							}

							if (Validator.isNotNull(themeDisplay.getDoAsUserLanguageId())) {
								backURL = HttpUtil.addParameter(backURL, "doAsUserLanguageId", themeDisplay.getDoAsUserLanguageId());
							}
						}
						%>

						<div>
							<span class="nobr">
								<a class="portlet-icon-back" href="<%= backURL %>"><%= LanguageUtil.format(pageContext, "back-to-x", HtmlUtil.escape(refererGroupDescriptiveName)) %></a>
							</span>
						</div>

					</td>
				</tr>
				</table>
</c:if>

<%
if (!denyAccess && (themeDisplay.isStateExclusive() || themeDisplay.isStatePopUp() || layoutTypePortlet.hasStateMax())) {
	String velocityTemplateId = null;

	String content = null;

	if (themeDisplay.isStateExclusive()) {
		velocityTemplateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "exclusive";

		content = LayoutTemplateLocalServiceUtil.getContent("exclusive", true, theme.getThemeId());
	}
	else if (themeDisplay.isStatePopUp()) {
		velocityTemplateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "pop_up";

		content = LayoutTemplateLocalServiceUtil.getContent("pop_up", true, theme.getThemeId());
	}
	else {
		ppid = StringUtil.split(layoutTypePortlet.getStateMax())[0];

		velocityTemplateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "max";

		content = LayoutTemplateLocalServiceUtil.getContent("max", true, theme.getThemeId());
	}
%>

	<%= RuntimePortletUtil.processTemplate(application, request, response, pageContext, ppid, velocityTemplateId, content) %>

<%
}
else {
	String description = StringPool.BLANK;

	String className = "portlet-msg-info";

	if (denyAccess) {
		description = LanguageUtil.get(pageContext, "you-do-not-have-enough-permissions-to-access-this-application");

		className = "portlet-msg-error";
	}

	if (Validator.isNull(description)) {
		description = LanguageUtil.get(pageContext, "please-select-a-tool-from-the-left-menu");
	}
%>

	<div class="<%= className %>">
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

<%@ include file="/html/portal/layout/view/common.jspf" %>

<%!
private static boolean _hasPortlet(String portletId, String category, ThemeDisplay themeDisplay) throws Exception {
	List<Portlet> portlets = PortalUtil.getControlPanelPortlets(category, themeDisplay);

	for (Portlet portlet : portlets) {
		if (portlet.getPortletId().equals(portletId)) {
			return true;
		}
	}

	return false;
}
%>