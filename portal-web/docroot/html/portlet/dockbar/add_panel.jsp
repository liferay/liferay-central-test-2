<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
--%>

<%@ include file="/html/portlet/dockbar/init.jsp" %>

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() %>">
		<c:if test="<%= layout != null %>">

			<%
			Group group = layout.getGroup();

			boolean hasLayoutAddPermission = false;

			if (layout.getParentLayoutId() == LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
				hasLayoutAddPermission = GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.ADD_LAYOUT);
			}
			else {
				hasLayoutAddPermission = LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.ADD_LAYOUT);
			}

			boolean hasLayoutCustomizePermission = LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.CUSTOMIZE);
			boolean hasLayoutUpdatePermission = LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.UPDATE);
			%>

			<c:if test="<%= !group.isControlPanel() && !group.isUserPersonalPanel() && (hasLayoutAddPermission || hasLayoutUpdatePermission || (layoutTypePortlet.isCustomizable() && layoutTypePortlet.isCustomizedView() && hasLayoutCustomizePermission)) %>">
				<div class="add-content-menu" id="<portlet:namespace />addPanelContainer">
					<aui:button cssClass="close" name="closePanelAdd" value="&times;" />

					<%
					String[] tabs1Names = new String[0];

					boolean stateMaximized = ParamUtil.getBoolean(request, "stateMaximized");

					boolean hasAddContentAndApplicationsPermission = !stateMaximized && layout.isTypePortlet() && !layout.isLayoutPrototypeLinkActive() && (hasLayoutUpdatePermission || (layoutTypePortlet.isCustomizable() && layoutTypePortlet.isCustomizedView() && hasLayoutCustomizePermission));

					if (hasAddContentAndApplicationsPermission) {
						tabs1Names = ArrayUtil.append(tabs1Names, "content,applications");
					}

					if (hasLayoutAddPermission) {
						tabs1Names = ArrayUtil.append(tabs1Names, "page");
					}

					String selectedTab = GetterUtil.getString(SessionClicks.get(request, "liferay_addpanel_tab", "content"));

					if (stateMaximized) {
						selectedTab = "page";
					}
					%>

					<h1><liferay-ui:message key="add" /></h1>

					<liferay-ui:tabs
						names="<%= StringUtil.merge(tabs1Names) %>"
						refresh="<%= false %>"
						type="pills"
						value="<%= selectedTab %>"
					>
						<c:if test="<%= hasAddContentAndApplicationsPermission %>">
							<liferay-ui:section>
								<liferay-util:include page="/html/portlet/dockbar/add_content.jsp" />
							</liferay-ui:section>

							<liferay-ui:section>
								<liferay-util:include page="/html/portlet/dockbar/add_application.jsp" />
							</liferay-ui:section>
						</c:if>

						<c:if test="<%= hasLayoutAddPermission && themeDisplay.isShowSiteAdministrationIcon() %>">

							<%
							long selPlid= ParamUtil.getLong(request, "selPlid", LayoutConstants.DEFAULT_PLID);
							%>

							<liferay-ui:section>
								<liferay-portlet:renderURL plid="<%= PortalUtil.getControlPanelPlid(company.getCompanyId()) %>" portletName="<%= PortletKeys.GROUP_PAGES %>" varImpl="newPageURL" windowState="<%= WindowState.NORMAL.toString() %>">
									<portlet:param name="struts_action" value="/group_pages/edit_layouts" />
									<portlet:param name="tabs1" value='<%= layout.isPrivateLayout() ? "private-pages" : "public-pages" %>' />
									<portlet:param name="groupId" value="<%= String.valueOf(groupDisplayContextHelper.getLiveGroupId()) %>" />
									<portlet:param name="selPlid" value="<%= String.valueOf(selPlid) %>" />
									<portlet:param name="treeId" value="layoutsTree" />
									<portlet:param name="viewLayout" value="true" />
								</liferay-portlet:renderURL>

								<%
								String newPageURLString = HttpUtil.setParameter(newPageURL.toString(), "controlPanelCategory", "current_site");

								newPageURLString = HttpUtil.setParameter(newPageURLString, "doAsGroupId", String.valueOf(groupDisplayContextHelper.getLiveGroupId()));
								newPageURLString = HttpUtil.setParameter(newPageURLString, "refererPlid", String.valueOf(selPlid));
								%>

								<aui:button-row>
									<aui:button href="<%= newPageURLString %>"  primary="<%= true %>" value="new-page" />
								</aui:button-row>
							</liferay-ui:section>
						</c:if>
					</liferay-ui:tabs>

					<span class="added-message hide" id="<portlet:namespace />addedMessage">
						<span class="alert-success message">
							<liferay-ui:icon iconCssClass="icon-ok-sign" /> <span id="<portlet:namespace />portletName"></span> <liferay-ui:message key="added" />

							<a class="content-link" href="javascript:;" id="<portlet:namespace />contentLink"><liferay-ui:message key="skip-to-content" /></a>
						</span>
					</span>
				</div>
			</c:if>
		</c:if>
	</c:when>
	<c:otherwise>
		<liferay-ui:message key="please-sign-in-to-continue" />
	</c:otherwise>
</c:choose>

<aui:script use="liferay-dockbar">
	A.one('#<portlet:namespace />closePanelAdd').on('click', Liferay.Dockbar.toggleAddPanel, Liferay.Dockbar);
</aui:script>