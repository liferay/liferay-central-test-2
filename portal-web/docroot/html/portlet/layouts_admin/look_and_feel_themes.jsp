<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
List<Theme> themes = (List<Theme>)request.getAttribute("edit_pages.jsp-themes");
List<ColorScheme> colorSchemes = (List<ColorScheme>)request.getAttribute("edit_pages.jsp-colorSchemes");

Theme selTheme = (Theme)request.getAttribute("edit_pages.jsp-selTheme");
ColorScheme selColorScheme = (ColorScheme)request.getAttribute("edit_pages.jsp-selColorScheme");

PluginPackage selPluginPackage = selTheme.getPluginPackage();
String device = (String)request.getAttribute("edit_pages.jsp-device");

boolean editable = (Boolean)request.getAttribute("edit_pages.jsp-editable");
%>

<div class="lfr-theme-list">
	<div class="float-container lfr-current-theme">
		<h3><liferay-ui:message key="current-theme" /></h3>

		<div>
			<img alt="<%= selTheme.getName() %>" class="theme-screenshot" onclick="document.getElementById('<portlet:namespace /><%= device %>SelTheme').checked = true;" src="<%= selTheme.getStaticResourcePath() %><%= selTheme.getImagesPath() %>/thumbnail.png" title="<%= selTheme.getName() %>" />

			<div class="theme-details">
				<c:choose>
					<c:when test="<%= editable %>">
						<aui:input checked="<%= true %>" cssClass="theme-title" id='<%= device + "SelTheme" %>' label="<%= selTheme.getName() %>" name='<%= device + "ThemeId" %>' type="radio" value="<%= selTheme.getThemeId() %>" />
					</c:when>
					<c:otherwise>
						<div class="theme-title"><%= selTheme.getName() %></div>
					</c:otherwise>
				</c:choose>

				<dl>
					<c:if test="<%= Validator.isNotNull(selPluginPackage.getShortDescription()) %>">
						<c:choose>
							<c:when test="<%= editable %>">
								<dt>
									<liferay-ui:message key="description" />
								</dt>
								<dd>
									<%= selPluginPackage.getShortDescription() %>
								</dd>
							</c:when>
							<c:otherwise>
								<%= selPluginPackage.getShortDescription() %>
							</c:otherwise>
						</c:choose>
					</c:if>

					<c:if test="<%= editable && Validator.isNotNull(selPluginPackage.getAuthor()) %>">
						<dt>
							<liferay-ui:message key="author" />
						</dt>
						<dd>
							<a href="<%= selPluginPackage.getPageURL() %>"><%= selPluginPackage.getAuthor() %></a>
						</dd>
					</c:if>

					<c:if test="<%= !editable && !colorSchemes.isEmpty() && Validator.isNotNull(selColorScheme) %>">
						<dt class="current-color-scheme">
							<liferay-ui:message key="color-scheme" />
						</dt>
						<dd>
							<%= selColorScheme.getName() %>
						</dd>
					</c:if>
				</dl>
			</div>
		</div>

		<c:if test="<%= editable && !colorSchemes.isEmpty() %>">
			<div class="color-schemes">
				<liferay-ui:panel-container extended="<%= true %>" id="communitiesColorSchemesPanelContainer" persistState="<%= true %>">
					<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" id="communitiesColorSchemesPanel" persistState="<%= true %>" title='<%= LanguageUtil.format(pageContext, "color-schemes-x", colorSchemes.size()) %>'>
						<div class="lfr-component lfr-theme-list">

							<%
							for (int i = 0; i < colorSchemes.size(); i++) {
								ColorScheme curColorScheme = colorSchemes.get(i);

								String cssClass = StringPool.BLANK;

								if (selColorScheme.getColorSchemeId().equals(curColorScheme.getColorSchemeId())) {
									cssClass = "selected-color-scheme";
								}
							%>

							<div class="<%= cssClass %> theme-entry">
								<img alt="" class="theme-thumbnail modify-link" onclick="document.getElementById('<portlet:namespace /><%= device %>ColorSchemeId<%= i %>').checked = true;" src="<%= selTheme.getStaticResourcePath() %><%= curColorScheme.getColorSchemeThumbnailPath() %>/thumbnail.png" title="<%= curColorScheme.getName() %>" />

								<aui:input cssClass="theme-title" checked="<%= selColorScheme.getColorSchemeId().equals(curColorScheme.getColorSchemeId()) %>" id='<%= device + "ColorSchemeId" + i %>' label="<%= curColorScheme.getName() %>" name='<%= device + "ColorSchemeId" %>' type="radio" value="<%= curColorScheme.getColorSchemeId() %>" />
							</div>

							<%
							}
							%>

						</div>
					</liferay-ui:panel>
				</liferay-ui:panel-container>
			</div>
		</c:if>
	</div>

	<c:if test="<%= editable %>">
		<div class="float-container lfr-available-themes">
			<h3>
				<span class="header-title">
					<%= LanguageUtil.format(pageContext, "available-themes-x", (themes.size() - 1)) %>
				</span>

				<c:if test="<%= permissionChecker.isOmniadmin() && PrefsPropsUtil.getBoolean(PropsKeys.AUTO_DEPLOY_ENABLED, PropsValues.AUTO_DEPLOY_ENABLED) %>">

					<%
					PortletURL installPluginsURL = ((RenderResponseImpl)renderResponse).createRenderURL(PortletKeys.PLUGIN_INSTALLER);

					installPluginsURL.setParameter("struts_action", "/plugin_installer/view");
					installPluginsURL.setParameter("backURL", currentURL);
					installPluginsURL.setParameter("tabs2", "theme-plugins");
					%>

					<span class="install-themes">
						<a href="<%= installPluginsURL %>"><liferay-ui:message key="install-more" /></a>
					</span>
				</c:if>
			</h3>

			<c:if test="<%= themes.size() > 1 %>">
				<ul class="lfr-component lfr-theme-list">

					<%
					for (int i = 0; i < themes.size(); i++) {
						Theme curTheme = themes.get(i);

						if (!selTheme.getThemeId().equals(curTheme.getThemeId())) {
					%>

							<li>
								<div class="theme-entry">
									<img alt="" class="theme-thumbnail modify-link" onclick="document.getElementById('<portlet:namespace /><%= device %>ThemeId<%= i %>').checked = true;" src="<%= curTheme.getStaticResourcePath() %><%= curTheme.getImagesPath() %>/thumbnail.png" title="<%= curTheme.getName() %>" />

									<aui:input cssClass="theme-title" id='<%= device + "ThemeId" + i %>' label="<%= curTheme.getName() %>" name='<%= device + "ThemeId" %>' type="radio" value="<%= curTheme.getThemeId() %>" />
								</div>
							</li>

					<%
						}
					}
					%>

				</ul>
			</c:if>
		</div>
	</c:if>
</div>