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

List<Portlet> portlets = new ArrayList<Portlet>();

for (String portletId : PropsValues.DOCKBAR_ADD_PORTLETS) {
	Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);

	if (portlet.isInclude() && portlet.isActive() && portlet.hasAddPortletPermission(user.getUserId())) {
		portlets.add(portlet);
	}
}
%>

<div class="dockbar" id="dockbar" rel="<portlet:namespace />">
	<ul class="aui-toolbar">
		<li class="pin-dockbar">
			<a href="javascript:;"><img alt='<liferay-ui:message key="pin-the-dockbar" />' src="<%= themeDisplay.getPathThemeImages() %>/spacer.png" /></a>
		</li>

		<c:if test="<%= (group != null) && (!group.hasStagingGroup() || group.isStagingGroup()) && LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.UPDATE) %>">
			<li class="add-content has-submenu" id="<portlet:namespace />addContent">
				<a class="menu-button" href="javascript:;">
					<span>
						<liferay-ui:message key="add" />
					</span>
				</a>

				<div class="aui-menu add-content-menu aui-contextoverlay-hidden" id="<portlet:namespace />addContentContainer">
					<div class="aui-menu-content">
						<ul>
							<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.MANAGE_LAYOUTS) && !group.isLayoutPrototype() %>">
								<li class="first add-page">
									<a href="javascript:;" id="addPage">
										<liferay-ui:message key="page" />
									</a>
								</li>
							</c:if>

							<c:if test="<%= !themeDisplay.isStateMaximized() %>">
								<li class="last common-items">
									<div class="aui-menugroup">
										<div class="aui-menugroup-content">
											<h4><liferay-ui:message key="applications" /></h4>

											<ul>

												<%
												for (int i = 0; i < portlets.size(); i++) {
													Portlet portlet = portlets.get(i);
												%>

													<li class="<%= (i == 0) ? "first" : "" %>">
														<a class="app-shortcut" href="javascript:;" rel="<%= portlet.getPortletId() %>">
															<liferay-portlet:icon-portlet portlet="<%= portlet %>" />

															<%= PortalUtil.getPortletTitle(portlet.getPortletId(), locale) %>
														</a>
													</li>

												<%
												}
												%>

												<li class="add-application last more-applications">
													<a href="javascript:;" id="<portlet:namespace />addApplication">
														<liferay-ui:message key="more" />&hellip;
													</a>
												</li>
											</ul>
										</div>
									</div>
								</li>
							</c:if>
						</ul>
					</div>
				</div>
			</li>
		</c:if>

		<c:if test="<%= themeDisplay.isShowControlPanelIcon() || themeDisplay.isShowPageSettingsIcon() || themeDisplay.isShowLayoutTemplatesIcon() %>">
			<li class="manage-content has-submenu" id="<portlet:namespace />manageContent">
				<a class="menu-button" href="javascript:;">
					<span>
						<liferay-ui:message key="manage" />
					</span>
				</a>

				<div class="aui-menu manage-content-menu aui-contextoverlay-hidden" id="<portlet:namespace />manageContentContainer">
					<div class="aui-menu-content">
						<ul>
							<c:if test="<%= themeDisplay.isShowPageSettingsIcon() %>">
								<li class="first manage-page">
									<a href="<%= HtmlUtil.escape(themeDisplay.getURLPageSettings().toString()) %>">
										<liferay-ui:message key="page" />
									</a>
								</li>
							</c:if>

							<c:if test="<%= themeDisplay.isShowLayoutTemplatesIcon() %>">
								<li class="page-layout">
									<a href="javascript:;" id="pageTemplate">
										<liferay-ui:message key="page-layout" />
									</a>
								</li>
							</c:if>

							<c:if test="<%= themeDisplay.isShowPageSettingsIcon() && !group.isLayoutPrototype() %>">
								<li class="sitemap">
									<a href="<%= HtmlUtil.escape(HttpUtil.setParameter(themeDisplay.getURLPageSettings().toString(), "selPlid", "-1")) %>">
										<liferay-ui:message key="sitemap" />
									</a>
								</li>
							</c:if>

							<c:if test="<%= themeDisplay.isShowPageSettingsIcon() && !group.isLayoutPrototype() %>">
								<li class="settings">
									<a href="<%= HtmlUtil.escape(HttpUtil.setParameter(themeDisplay.getURLPageSettings().toString(), "tabs1", "settings")) %>">
										<liferay-ui:message key="settings" />
									</a>
								</li>
							</c:if>

							<c:if test="<%= themeDisplay.isShowControlPanelIcon() %>">
								<li class="control-panel last" id="<portlet:namespace />controlPanel">
									<a href="<%= themeDisplay.getURLControlPanel() %>">
										<liferay-ui:message key="control-panel" />
									</a>
								</li>
							</c:if>
						</ul>
					</div>
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

				<div class="aui-menu staging-menu aui-contextoverlay-hidden" id="<portlet:namespace />stagingContainer">
					<div class="aui-menu-content">
						<liferay-ui:staging />
					</div>
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

				<div class="aui-menu my-places-menu aui-contextoverlay-hidden" id="<portlet:namespace />myPlacesContainer">
					<div class="aui-menu-content">
						<liferay-ui:my-places />
					</div>
				</div>
			</li>
		</c:if>

		<li class="aui-toolbar-separator">
			<span></span>
		</li>

		<li class="user-avatar <%= themeDisplay.isImpersonated() ? "impersonating-user has-submenu" : "" %>" id="<portlet:namespace />userAvatar">
			<span class="user-links <%= themeDisplay.isImpersonated() ? "menu-button": "" %>">
				<a href="<%= HtmlUtil.escape(themeDisplay.getURLMyAccount().toString()) %>"><img alt="<%= user.getFullName() %>" src="<%= themeDisplay.getPathImage() %>/user_<%= user.isFemale() ? "female" : "male" %>_portrait?img_id=<%= user.getPortraitId() %>&t=<%= ImageServletTokenUtil.getToken(user.getPortraitId()) %>" /></a> <a href="<%= HtmlUtil.escape(themeDisplay.getURLMyAccount().toString()) %>"><%= user.getFullName() %></a>

				<c:if test="<%= themeDisplay.isShowSignOutIcon() %>">
					<span class="sign-out">(<a href="<%= themeDisplay.getURLSignOut() %>"><liferay-ui:message key="sign-out" /></a>)</span>
				</c:if>
			</span>

			<c:if test="<%= themeDisplay.isImpersonated() %>">
				<div class="aui-menu impersonation-menu aui-contextoverlay-hidden" id="<portlet:namespace />userOptionsContainer">
					<div class="aui-menu-content">
						<div class="notice-message portlet-msg-info">
							<c:choose>
								<c:when test="<%= themeDisplay.isSignedIn() %>">
									<%= LanguageUtil.format(pageContext, "you-are-impersonating-x", new Object[] {user.getFullName()}) %>
								</c:when>
								<c:otherwise>
									<liferay-ui:message key="you-are-impersonating-the-guest-user" />
								</c:otherwise>
							</c:choose>
						</div>

						<ul>
							<li>
								<a href="<%= PortalUtil.getLayoutURL(layout, themeDisplay, false) %>"><liferay-ui:message key="be-yourself-again" /> (<%= realUser.getFullName() %>)</a>
							</li>

							<%
							Locale realUserLocale = realUser.getLocale();
							Locale userLocale = user.getLocale();
							%>

							<c:if test="<%= !realUserLocale.equals(userLocale) %>">

								<%
								String doAsUserLanguageId = null;
								String changeLanguageMessage = null;

								if (locale.getLanguage().equals(realUserLocale.getLanguage()) && locale.getCountry().equals(realUserLocale.getCountry())) {
									doAsUserLanguageId = userLocale.getLanguage() + "_" + userLocale.getCountry();
									changeLanguageMessage = LanguageUtil.format(realUserLocale, "use-x's-preferred-language-(x)", new String[] {user.getFullName(), userLocale.getDisplayLanguage(realUserLocale)});
								}
								else {
									doAsUserLanguageId = realUserLocale.getLanguage() + "_" + realUserLocale.getCountry();
									changeLanguageMessage = LanguageUtil.format(realUserLocale, "use-your-preferred-language-(x)", realUserLocale.getDisplayLanguage(realUserLocale));
								}
								%>

								<li class="current-user-language">
									<a href="<%= HttpUtil.setParameter(PortalUtil.getCurrentURL(request), "doAsUserLanguageId", doAsUserLanguageId) %>"><%= changeLanguageMessage %></a>
								</li>
							</c:if>
						</ul>
					</div>
				</div>
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