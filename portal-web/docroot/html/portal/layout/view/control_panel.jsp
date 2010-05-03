<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
		String strutsAction = ParamUtil.getString(request, PortalUtil.getPortletNamespace(ppid) + "struts_action");

		if (!strutsAction.startsWith("/portlet_configuration/")) {
			ppid = portletResource;
		}
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

Portlet portlet = null;

boolean denyAccess = false;

if (Validator.isNotNull(ppid)) {
	portlet = PortletLocalServiceUtil.getPortletById(ppid);

	if ((portlet == null) ||
		(!portlet.isSystem() && !_hasPortlet(ppid, category, themeDisplay))) {

		denyAccess = true;
	}
}

request.setAttribute("control_panel.jsp-ppid", ppid);
%>

<c:choose>
	<c:when test="<%= !themeDisplay.isStateExclusive() && !themeDisplay.isStatePopUp() %>">

		<%
		String panelBodyCssClass = "panel-page-body";
		String panelCategory = "lfr-ctrl-panel";
		String categoryTitle = Validator.isNotNull(category) ? LanguageUtil.get(pageContext, "category." + category) : StringPool.BLANK;

		if (!layoutTypePortlet.hasStateMax()) {
			panelBodyCssClass += " panel-page-frontpage";
		}
		else {
			panelBodyCssClass += " panel-page-application";
		}

		if (category.equals(PortletCategoryKeys.CONTENT)) {
			panelCategory += " panel-manage-content";
		}
		else if (category.equals(PortletCategoryKeys.MY)) {
			panelCategory += " panel-manage-my";
			categoryTitle = user.getFullName();
		}
		else if (category.equals(PortletCategoryKeys.PORTAL)) {
			panelCategory += " panel-manage-portal";

			if (CompanyLocalServiceUtil.getCompaniesCount(false) > 1) {
				categoryTitle += " " + company.getName();
			}
		}
		else if (category.equals(PortletCategoryKeys.SERVER)) {
			panelCategory += " panel-manage-server";
		}
		else if (category.equals(PortletCategoryKeys.WORKFLOW)) {
			panelCategory += " panel-manage-workflow";
		}
		else {
			panelCategory += " panel-manage-frontpage";
		}

		Layout scopeLayout = null;
		Group curGroup = themeDisplay.getScopeGroup();

		if (curGroup.isLayout()) {
			scopeLayout = LayoutLocalServiceUtil.getLayout(curGroup.getClassPK());
			curGroup = scopeLayout.getGroup();
		}

		PortalUtil.addPortletBreadcrumbEntry(request, categoryTitle, null);
		%>

		<div id="content-wrapper">
			<aui:layout cssClass="<%= panelCategory %>">
				<aui:column columnWidth="<%= 25 %>" cssClass="panel-page-menu" first="<%= true %>">
					<liferay-portlet:runtime portletName="87" />
				</aui:column>

				<aui:column columnWidth="<%= 75 %>" cssClass="<%= panelBodyCssClass %>" last="<%= true %>">
					<aui:layout cssClass="panel-page-body-menu">
						<aui:column columnWidth="<%= 75 %>" first="<%= true %>">
							<span id="mainContent"></span>

							<c:choose>
								<c:when test="<%= category.equals(PortletCategoryKeys.CONTENT) %>">

									<%
									String curGroupName = null;

									if (curGroup.isCompany()) {
										curGroupName = LanguageUtil.get(pageContext, "global");
									}
									else if (curGroup.isUser() && (curGroup.getClassPK() == user.getUserId())) {
										curGroupName = LanguageUtil.get(pageContext, "my-community");
									}
									else {
										curGroupName = curGroup.getDescriptiveName();
									}

									PortalUtil.addPortletBreadcrumbEntry(request, curGroupName, null);

									String curGroupLabel = null;

									if (scopeLayout == null) {
										curGroupLabel = LanguageUtil.get(pageContext, "default");
									}
									else {
										curGroupLabel = scopeLayout.getName(locale);
										PortalUtil.addPortletBreadcrumbEntry(request, curGroupLabel, null);
									}

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
										<liferay-ui:message key="content-for" />

										<a href="javascript:;" class="lfr-group-selector"><%= HtmlUtil.escape(curGroupName) %></a>

										<c:if test="<%= !scopeLayouts.isEmpty() %>">
											<span class="nobr lfr-title-scope-selector">
												<liferay-ui:message key="with-scope" /> <a href="javascript:;" class="lfr-scope-selector"><%= curGroupLabel %></a>
											</span>
										</c:if>
									</h2>

									<liferay-ui:panel-floating-container paging="<%= true %>" trigger=".lfr-group-selector">
										<%
										List<Group> manageableGroups = GroupServiceUtil.getManageableGroups(ActionKeys.MANAGE_LAYOUTS, PropsValues.CONTROL_PANEL_NAVIGATION_MAX_COMMUNITIES);
										List<Organization> manageableOrganizations = OrganizationServiceUtil.getManageableOrganizations(ActionKeys.MANAGE_LAYOUTS, PropsValues.CONTROL_PANEL_NAVIGATION_MAX_ORGANIZATIONS);
										%>

										<c:if test="<%= !manageableGroups.isEmpty() %>">
											<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="communitiesPanel" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "communities") %>'>
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
															<a href="<%= HttpUtil.setParameter(PortalUtil.getCurrentURL(request), "doAsGroupId", group.getGroupId()) %>"><%= (group.isUser() && (group.getClassPK() == user.getUserId())) ? LanguageUtil.get(pageContext, "my-community") : HtmlUtil.escape(group.getDescriptiveName()) %></a>
														</li>

													<%
													}
													%>

												</ul>
											</liferay-ui:panel>
										</c:if>

										<c:if test="<%= !manageableOrganizations.isEmpty() %>">
											<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="communitiesPanel" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "organizations") %>'>
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

										<%
										boolean showGlobal = permissionChecker.isCompanyAdmin();
										boolean showMyCommunity = user.getGroup().hasPrivateLayouts() || user.getGroup().hasPublicLayouts();
										%>

										<c:if test="<%= showGlobal || showMyCommunity %>">
											<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="sharedPanel" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "other[plural]") %>'>
												<ul>
													<c:if test="<%= showGlobal %>">
														<li>
															<a href="<%= HttpUtil.setParameter(PortalUtil.getCurrentURL(request), "doAsGroupId", themeDisplay.getCompanyGroupId()) %>"><liferay-ui:message key="global" /></a>
														</li>
													</c:if>
													<c:if test="<%= showMyCommunity %>">
														<li>
															<a href="<%= HttpUtil.setParameter(PortalUtil.getCurrentURL(request), "doAsGroupId", user.getGroup().getGroupId()) %>"><liferay-ui:message key="my-community" /></a>
														</li>
													</c:if>
												</ul>
											</liferay-ui:panel>
										</c:if>
									</liferay-ui:panel-floating-container>

									<c:if test="<%= !scopeLayouts.isEmpty() %>">
										<liferay-ui:panel-floating-container trigger=".lfr-scope-selector">
											<liferay-ui:panel title="">
												<ul>
													<li>
														<a href="<%= HttpUtil.setParameter(PortalUtil.getCurrentURL(request), "doAsGroupId", curGroup.getGroupId()) %>"><liferay-ui:message key="default" /></a>
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
											<%= HtmlUtil.escape(company.getName()) %>
										</c:if>
									</h2>
								</c:when>
								<c:otherwise>

									<%
									String title = category;

									if (category.equals(PortletCategoryKeys.MY)) {
										title = HtmlUtil.escape(user.getFullName());
									}

									if (Validator.isNotNull(category)) {
										category = "category." + category;
									}
									%>

									<h2><liferay-ui:message key="<%= title %>" /></h2>
								</c:otherwise>
							</c:choose>
						</aui:column>

						<aui:column columnWidth="<%= 25 %>" last="<%= true %>">

							<%
							String refererGroupDescriptiveName = null;
							String backURL = null;

							if (themeDisplay.getRefererPlid() > 0) {
								Layout refererLayout = LayoutLocalServiceUtil.getLayout(themeDisplay.getRefererPlid());

								Group refererGroup = refererLayout.getGroup();

								refererGroupDescriptiveName = refererGroup.getDescriptiveName();

								if (refererGroup.isUser() && (refererGroup.getClassPK() == user.getUserId())) {
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

							<span class="nobr">
								<a class="portlet-icon-back" href="<%= PortalUtil.escapeRedirect(backURL) %>"><%= LanguageUtil.format(pageContext, "back-to-x", HtmlUtil.escape(refererGroupDescriptiveName)) %></a>
							</span>
						</aui:column>
					</aui:layout>

					<%@ include file="/html/portal/layout/view/panel_content.jspf" %>
				</aui:column>
			</aui:layout>
		</div>
	</c:when>
	<c:otherwise>
		<%@ include file="/html/portal/layout/view/panel_content.jspf" %>
	</c:otherwise>
</c:choose>

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