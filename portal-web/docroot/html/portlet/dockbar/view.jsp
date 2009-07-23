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

<%@ include file="/html/portlet/dockbar/init.jsp" %>

<%
Group group = null;

if (layout != null) {
	group = layout.getGroup();
}
%>

<div class="dockbar" id="dockbar" rel="<portlet:namespace />">
	<ul class="aui-toolbar">
		<li class="pin-dockbar">
			<a href="javascript:;"><img alt="" src="<%= themeDisplay.getPathThemeImages() %>/spacer.png" /></a>
		</li>

		<c:if test="<%= (group != null) && (!group.hasStagingGroup() || group.isStagingGroup()) && LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.UPDATE) %>">
			<li class="add-content has-submenu" id="<portlet:namespace />addContent">
				<a class="menu-button" href="javascript:;">
					<span>
						<liferay-ui:message key="add" />
					</span>
				</a>

				<div class="add-content-container menu-container" id="<portlet:namespace />addContentContainer">
					<ul>
						<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.MANAGE_LAYOUTS) && !group.isLayoutPrototype() %>">
							<li class="first add-page">
								<a href="javascript:;" id="addPage">
									<liferay-ui:message key="page" />
								</a>
							</li>
						</c:if>

						<c:if test="<%= !themeDisplay.isStateMaximized() %>">
							<li class="add-application">
								<a href="javascript:;" id="<portlet:namespace />addApplication">
									<liferay-ui:message key="application" />
								</a>
							</li>
							<li class="last common-items">
								<h4><liferay-ui:message key="common-items" /></h4>

								<ul>
									<li class="first">
										<a href="javascript:;" class="app-shortcut" rel="56">
											<%= PortalUtil.getPortletTitle("56", themeDisplay.getCompanyId(), locale) %>
										</a>
									</li>
									<li>
										<a href="javascript:;" class="app-shortcut" rel="73">
											<%= PortalUtil.getPortletTitle("73", themeDisplay.getCompanyId(), locale) %>
										</a>
									</li>
									<li>
										<a href="javascript:;" class="app-shortcut" rel="71">
											<%= PortalUtil.getPortletTitle("71", themeDisplay.getCompanyId(), locale) %>
										</a>
									</li>
									<li class="last">
										<a href="javascript:;" class="app-shortcut" rel="85">
											<%= PortalUtil.getPortletTitle("85", themeDisplay.getCompanyId(), locale) %>
										</a>
									</li>
								</ul>
							</li>
						</c:if>
					</ul>
				</div>
			</li>
		</c:if>

		<c:if test="<%= themeDisplay.isShowControlPanelIcon() || themeDisplay.isShowPageSettingsIcon() || themeDisplay.isShowLayoutTemplatesIcon() %>">
			<li class="manage-content has-submenu" id="<portlet:namespace />manageContent">
				<a class="menu-button" href="javascript:;">
					<span>
						<liferay-ui:message key="settings" />
					</span>
				</a>

				<div class="manage-content-container menu-container" id="<portlet:namespace />manageContentContainer">
					<ul>
						<c:if test="<%= themeDisplay.isShowControlPanelIcon() %>">
							<li id="<portlet:namespace />controlPanel">
								<a href="<%= themeDisplay.getURLControlPanel() %>">
									<liferay-ui:message key="control-panel" />
								</a>
							</li>
						</c:if>

						<c:if test="<%= themeDisplay.isShowPageSettingsIcon() %>">
							<li class="first">
								<a href="<%= HtmlUtil.escape(themeDisplay.getURLPageSettings().toString()) %>">
									<liferay-ui:message key='<%= group.isLayoutPrototype()? "manage-page" : "manage-pages" %>' />
								</a>
							</li>
						</c:if>

						<c:if test="<%= themeDisplay.isShowLayoutTemplatesIcon() %>">
							<li class="last">
								<a href="javascript:;" id="pageTemplate">
									<liferay-ui:message key="layout" />
								</a>
							</li>
						</c:if>
					</ul>
				</div>
			</li>
		</c:if>

		<c:if test="<%= themeDisplay.isShowStagingIcon() %>">
			<li class="staging-options has-submenu" id="<portlet:namespace />staging">
				<a class="menu-button" href="javascript:;">
					<span>
						<liferay-ui:message key="staging" />
					</span>
				</a>

				<div class="staging-container menu-container" id="<portlet:namespace />stagingContainer">
					<liferay-ui:staging />
				</div>
			</li>
		</c:if>

		<li class="aui-toolbar-separator">
			<span></span>
		</li>

		<c:if test="<%= themeDisplay.isSignedIn() %>">
			<li class="toggle-controls" id="<portlet:namespace />toggleControls">
				<a href="javascript:;">
					<liferay-ui:message key="toggle-edit-controls" />
				</a>
			</li>
		</c:if>
	</ul>

	<ul class="aui-toolbar user-toolbar">
		<c:if test="<%= user.hasMyPlaces() %>">
			<li class="my-places has-submenu" id="<portlet:namespace />myPlaces">
				<a class="menu-button" href="javascript:;">
					<span>
						<liferay-ui:message key="go-to" />
					</span>
				</a>

				<div class="my-places-container menu-container" id="<portlet:namespace />myPlacesContainer">
					<liferay-ui:my-places />
				</div>
			</li>
		</c:if>

		<li class="aui-toolbar-separator">
			<span></span>
		</li>

		<li class="user-avatar">
			<a href="<%= HtmlUtil.escape(themeDisplay.getURLMyAccount().toString()) %>"><img alt="<%= user.getFullName() %>" src="<%= themeDisplay.getPathImage() %>/user_<%= user.isFemale() ? "female" : "male" %>_portrait?img_id=<%= user.getPortraitId() %>&t=<%= ImageServletTokenUtil.getToken(user.getPortraitId()) %>" /></a> <a href="<%= HtmlUtil.escape(themeDisplay.getURLMyAccount().toString()) %>"><%= user.getFullName() %></a>

			<c:if test="<%= themeDisplay.isShowSignOutIcon() %>">
				<span class="sign-out">(<a href="<%= themeDisplay.getURLSignOut() %>"><liferay-ui:message key="sign-out" /></a>)</span>
			</c:if>
		</li>
	</ul>

	<div class="dockbar-messages" id="<portlet:namespace />dockbarMessages">
		<div class="aui-header"></div>

		<div class="aui-body"></div>

		<div class="aui-footer"></div>
	</div>

	<%
	List<LayoutPrototype> layoutPrototypes = LayoutPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);
	%>

	<c:if test="<%= !layoutPrototypes.isEmpty() %>">
		<div id="layoutPrototypeTemplate" class="aui-html-template">
			<ul>

				<%
				for (LayoutPrototype layoutPrototype : layoutPrototypes) {
				%>
					<li>
						<label>
							<a href="javascript:;">
								<input name="template" type="radio" value="<%= layoutPrototype.getLayoutPrototypeId() %>" /> <%= layoutPrototype.getName(user.getLanguageId()) %>
							</a>
						</label>
					</li>
				<%
				}
				%>

			</ul>
		</div>
	</c:if>
</div>