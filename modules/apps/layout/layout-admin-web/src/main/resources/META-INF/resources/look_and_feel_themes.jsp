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

<%@ include file="/init.jsp" %>

<%
Layout selLayout = layoutsAdminDisplayContext.getSelLayout();
LayoutSet selLayoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

List<Theme> themes = (List<Theme>)request.getAttribute("edit_pages.jsp-themes");
List<ColorScheme> colorSchemes = (List<ColorScheme>)request.getAttribute("edit_pages.jsp-colorSchemes");
Theme selTheme = (Theme)request.getAttribute("edit_pages.jsp-selTheme");
ColorScheme selColorScheme = (ColorScheme)request.getAttribute("edit_pages.jsp-selColorScheme");
String device = (String)request.getAttribute("edit_pages.jsp-device");
boolean editable = (Boolean)request.getAttribute("edit_pages.jsp-editable");

Map<String, ThemeSetting> configurableSettings = selTheme.getConfigurableSettings();
%>

<div class="lfr-theme-list">
	<div class="float-container lfr-current-theme" id="<%= editable ? device : StringPool.BLANK %>LookAndFeel">
		<legend><liferay-ui:message key="current-theme" /></legend>

		<div>
			<img alt="<%= HtmlUtil.escapeAttribute(selTheme.getName()) %>" class="img-thumbnail theme-screenshot" onclick="<portlet:namespace /><%= device %>selectTheme('SelTheme', false);" src="<%= themeDisplay.getCDNBaseURL() %><%= HtmlUtil.escapeAttribute(selTheme.getStaticResourcePath()) %><%= HtmlUtil.escapeAttribute(selTheme.getImagesPath()) %>/thumbnail.png" title="<%= HtmlUtil.escapeAttribute(selTheme.getName()) %>" />

			<div class="theme-details">
				<c:choose>
					<c:when test="<%= editable %>">
						<aui:input checked="<%= true %>" cssClass="selected-theme theme-title" id='<%= device + "SelTheme" %>' label="<%= HtmlUtil.escape(selTheme.getName()) %>" name='<%= device + "ThemeId" %>' type="radio" value="<%= selTheme.getThemeId() %>" />
					</c:when>
					<c:otherwise>
						<div class="selected-theme theme-title"><%= HtmlUtil.escape(selTheme.getName()) %></div>
					</c:otherwise>
				</c:choose>

				<dl class="theme-fields">

					<%
					PluginPackage selPluginPackage = selTheme.getPluginPackage();
					%>

					<c:if test="<%= (selPluginPackage != null) && Validator.isNotNull(selPluginPackage.getShortDescription()) %>">
						<dt>
							<liferay-ui:message key="description" />
						</dt>
						<dd>
							<%= HtmlUtil.escape(selPluginPackage.getShortDescription()) %>
						</dd>
					</c:if>

					<c:if test="<%= editable && (selPluginPackage != null) && Validator.isNotNull(selPluginPackage.getAuthor()) %>">
						<dt>
							<liferay-ui:message key="author" />
						</dt>
						<dd>
							<a href="<%= HtmlUtil.escapeHREF(selPluginPackage.getPageURL()) %>"><%= HtmlUtil.escape(selPluginPackage.getAuthor()) %></a>
						</dd>
					</c:if>

					<c:if test="<%= !editable && !colorSchemes.isEmpty() && Validator.isNotNull(selColorScheme) %>">
						<dt class="current-color-scheme">
							<liferay-ui:message key="color-scheme" />
						</dt>
						<dd>
							<%= HtmlUtil.escape(selColorScheme.getName()) %>
						</dd>
					</c:if>

					<c:if test="<%= !editable && !configurableSettings.isEmpty() %>">

						<%
						for (String name : configurableSettings.keySet()) {
						%>

							<dt class="theme-setting">
								<liferay-ui:message key="<%= HtmlUtil.escape(name) %>" />
							</dt>
							<dd>
								<%= HtmlUtil.escape(selLayoutSet.getThemeSetting(name, device)) %>
							</dd>

						<%
						}
						%>

					</c:if>
				</dl>
			</div>
		</div>

		<c:if test="<%= editable %>">
			<c:if test="<%= !colorSchemes.isEmpty() || !configurableSettings.isEmpty() %>">
				<c:if test="<%= !colorSchemes.isEmpty() %>">
					<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" id='<%= device + "layoutsAdminLookAndFeelColorsPanel" %>' persistState="<%= true %>" title='<%= LanguageUtil.format(request, "color-schemes-x", colorSchemes.size()) %>'>
						<aui:fieldset cssCclass="color-schemes">
							<div class="lfr-theme-list list-unstyled">

								<%
								for (int i = 0; i < colorSchemes.size(); i++) {
									ColorScheme curColorScheme = colorSchemes.get(i);

									String cssClass = StringPool.BLANK;

									if (selColorScheme.getColorSchemeId().equals(curColorScheme.getColorSchemeId())) {
										cssClass = "selected-color-scheme";
									}
								%>

								<div class="<%= cssClass %> theme-entry">
									<img alt="" class="modify-link theme-thumbnail" onclick="<portlet:namespace /><%= device %>selectColorScheme('#<portlet:namespace /><%= device %>ColorSchemeId<%= i %>');" src="<%= themeDisplay.getCDNBaseURL() %><%= HtmlUtil.escapeAttribute(selTheme.getStaticResourcePath()) %><%= HtmlUtil.escapeAttribute(curColorScheme.getColorSchemeThumbnailPath()) %>/thumbnail.png" title="<%= HtmlUtil.escapeAttribute(curColorScheme.getName()) %>" />

									<aui:input checked="<%= selColorScheme.getColorSchemeId().equals(curColorScheme.getColorSchemeId()) %>" cssClass="theme-title" id='<%= device + "ColorSchemeId" + i %>' label="<%= HtmlUtil.escape(curColorScheme.getName()) %>" name='<%= device + "ColorSchemeId" %>' type="radio" value="<%= curColorScheme.getColorSchemeId() %>" />
								</div>

								<%
								}
								%>

							</div>
						</aui:fieldset>
					</liferay-ui:panel>
				</c:if>

				<c:if test="<%= !configurableSettings.isEmpty() %>">
					<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" id='<%= device + "layoutsAdminLookAndFeelSettingsPanel" %>' persistState="<%= true %>" title="settings">
						<aui:fieldset>

							<%
							for (String name : configurableSettings.keySet()) {
								ThemeSetting themeSetting = configurableSettings.get(name);

								String type = GetterUtil.getString(themeSetting.getType(), "text");
								String value = StringPool.BLANK;

								if (selLayout != null) {
									value = selLayout.getThemeSetting(name, device);
								}
								else {
									value = selLayoutSet.getThemeSetting(name, device);
								}

								String propertyName = HtmlUtil.escapeAttribute(device + "ThemeSettingsProperties--" + name + StringPool.DOUBLE_DASH);
							%>

								<c:choose>
									<c:when test='<%= type.equals("checkbox") || type.equals("text") || type.equals("textarea") %>'>
										<aui:input label="<%= HtmlUtil.escape(name) %>" name="<%= propertyName %>" type="<%= type %>" value="<%= value %>" />
									</c:when>
									<c:when test='<%= type.equals("select") %>'>
										<aui:select label="<%= HtmlUtil.escape(name) %>" name="<%= propertyName %>">

											<%
											for (String option : themeSetting.getOptions()) {
											%>

												<aui:option label="<%= HtmlUtil.escape(option) %>" selected="<%= option.equals(value) %>" />

											<%
											}
											%>

										</aui:select>
									</c:when>
								</c:choose>

								<c:if test="<%= Validator.isNotNull(themeSetting.getScript()) %>">
									<aui:script position="inline">
										<%= StringUtil.replace(themeSetting.getScript(), "[@NAMESPACE@]", liferayPortletResponse.getNamespace()) %>
									</aui:script>
								</c:if>

							<%
							}
							%>

						</aui:fieldset>
					</liferay-ui:panel>
				</c:if>
			</c:if>
		</c:if>
	</div>

	<c:if test="<%= editable %>">
		<div class="float-container lfr-available-themes" id="<%= device %>availableThemes">
			<legend>
				<span class="header-title">
					<liferay-ui:message arguments="<%= themes.size() - 1 %>" key="available-themes-x" translateArguments="<%= false %>" />
				</span>

				<c:if test="<%= permissionChecker.isOmniadmin() && PortletLocalServiceUtil.hasPortlet(themeDisplay.getCompanyId(), PortletKeys.MARKETPLACE_STORE) && PrefsPropsUtil.getBoolean(PropsKeys.AUTO_DEPLOY_ENABLED, PropsValues.AUTO_DEPLOY_ENABLED) %>">

					<%
					PortletURL marketplaceURL = PortalUtil.getControlPanelPortletURL(request, PortletKeys.MARKETPLACE_STORE, 0, PortletRequest.RENDER_PHASE);
					%>

					<liferay-ui:icon
						cssClass="manage-layout-set-branches-link pull-right"
						iconCssClass="icon-inbox"
						id="installMore"
						label="<%= true %>"
						linkCssClass="btn btn-default"
						message="install-more"
						url="<%= marketplaceURL.toString() %>"
					/>
				</c:if>
			</legend>

			<c:if test="<%= themes.size() > 1 %>">
				<ul class="lfr-theme-list list-unstyled">

					<%
					for (int i = 0; i < themes.size(); i++) {
						Theme curTheme = themes.get(i);

						if (!selTheme.getThemeId().equals(curTheme.getThemeId())) {
					%>

							<li>
								<div class="theme-entry">
									<img alt="" class="modify-link theme-thumbnail" onclick="<portlet:namespace /><%= device %>selectTheme('ThemeId<%= i %>', true);" src="<%= themeDisplay.getCDNBaseURL() %><%= HtmlUtil.escapeAttribute(curTheme.getStaticResourcePath()) %><%= HtmlUtil.escapeAttribute(curTheme.getImagesPath()) %>/thumbnail.png" title="<%= HtmlUtil.escapeAttribute(curTheme.getName()) %>" />

									<aui:input cssClass="theme-title" id='<%= device + "ThemeId" + i %>' label="<%= HtmlUtil.escape(curTheme.getName()) %>" name='<%= device + "ThemeId" %>' type="radio" value="<%= curTheme.getThemeId() %>" />
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

<c:if test="<%= editable %>">
	<aui:script sandbox="<%= true %>">
		var colorSchemePanel = $('#<%= device %>layoutsAdminLookAndFeelColorsPanel');

		var toggleDisabled = function(disabled) {
			colorSchemePanel.find('input[name=<portlet:namespace /><%= device %>ColorSchemeId]').prop('disabled', disabled);
		};

		if (colorSchemePanel.length) {
			$('#<%= device %>availableThemes').find('input[name=<portlet:namespace /><%= device %>ThemeId]').on(
				'change',
				function() {
					toggleDisabled(true);
				}
			);

			$('#<%= device %>LookAndFeel').find('#<portlet:namespace /><%= device %>SelTheme').on(
				'change',
				function() {
					toggleDisabled(false);
				}
			);
		}
	</aui:script>

	<aui:script>
		function <portlet:namespace /><%= device %>selectColorScheme(id) {
			var colorSchemeInput = AUI.$(id);

			if (!colorSchemeInput.prop('disabled')) {
				colorSchemeInput.prop('checked', true);
			}
		}

		function <portlet:namespace /><%= device %>selectTheme(themeId, colorSchemesDisabled) {
			var $ = AUI.$;

			$('#<portlet:namespace /><%= device %>' + themeId).prop('checked', true);

			$('#<%= device %>layoutsAdminLookAndFeelColorsPanel').find('input[name=<portlet:namespace /><%= device %>ColorSchemeId]').prop('disabled', colorSchemesDisabled);
		}
	</aui:script>
</c:if>