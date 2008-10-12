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

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
String tabs4 = (String)request.getAttribute("edit_pages.jsp-tab4");

long groupId = ((Long)request.getAttribute("edit_pages.jsp-groupId")).longValue();
long liveGroupId = ((Long)request.getAttribute("edit_pages.jsp-liveGroupId")).longValue();
long selPlid = ((Long)request.getAttribute("edit_pages.jsp-selPlid")).longValue();
boolean privateLayout = ((Boolean)request.getAttribute("edit_pages.jsp-privateLayout")).booleanValue();
Layout selLayout = (Layout)request.getAttribute("edit_pages.jsp-selLayout");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_pages.jsp-portletURL");
%>

<script type="text/javascript">
	jQuery(
		function () {
			var panel = jQuery('.lfr-panel');

			if (panel.is('.lfr-collapsible')) {
				panel.find('.lfr-panel-titlebar').click(
					function(event) {
						panel.find('.lfr-panel-content').toggle();
						panel.toggleClass('lfr-collapsed');

						var panelId = panel.attr('id');

						if (panelId) {
							var state = 'closed';
							var data = {};

							if (!panel.is('.lfr-collapsed')) {
								state = 'open';
							}

							data[panelId] = state;
							jQuery.ajax(
								{
									url: themeDisplay.getPathMain() + '/portal/session_click',
									data: data
								}
							);
						}
					}
				)
			}
		}
	);
</script>

<liferay-ui:tabs
	names="regular-browsers,mobile-devices"
	param="tabs4"
	url='<%= portletURL.toString() + "&" + renderResponse.getNamespace() + "selPlid=" + selPlid %>'
/>

<c:choose>
	<c:when test='<%= tabs4.equals("regular-browsers") %>'>

		<%
		Theme selTheme = null;
		ColorScheme selColorScheme = null;

		if (selLayout != null) {
			selTheme = selLayout.getTheme();
			selColorScheme = selLayout.getColorScheme();
		}
		else {
			LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(groupId, privateLayout);

			selTheme = layoutSet.getTheme();
			selColorScheme = layoutSet.getColorScheme();
		}
		%>

		<c:if test="<%= selLayout != null %>">
			<table class="lfr-table">
			<tr>
				<td class="lfr-label">
					<%= LanguageUtil.get(pageContext, "use-the-general-look-and-feel-for-the-" + (privateLayout ? "public" : "private") + "-pages") %>
				</td>
				<td>
					<select name="<portlet:namespace />hidden" onChange="if (this.value == 1) { <portlet:namespace />updateLookAndFeel('', ''); } else { <portlet:namespace />updateLookAndFeel('<%= selTheme.getThemeId() %>', '<%= selColorScheme.getColorSchemeId() %>'); }">
						<option <%= (selLayout.isInheritLookAndFeel()) ? "selected" : "" %> value="1"><liferay-ui:message key="yes" /></option>
						<option <%= (!selLayout.isInheritLookAndFeel()) ? "selected" : "" %> value="0"><liferay-ui:message key="no" /></option>
					</select>
				</td>
			</table>

			<br />
		</c:if>

		<liferay-ui:tabs
			names="themes,css"
			param="tabs5"
			refresh="<%= false %>"
		>
			<liferay-ui:section>
				<%
				List themes = ThemeLocalServiceUtil.getThemes(company.getCompanyId(), liveGroupId, user.getUserId(), false);
				PluginPackage selPluginPackage = selTheme.getPluginPackage();
				List colorSchemes = selTheme.getColorSchemes();
				%>

				<input name="<portlet:namespace />themeId" type="hidden" value="<%= selTheme.getThemeId() %>" />
				<input name="<portlet:namespace />colorSchemeId" type="hidden" value="<%= selColorScheme.getColorSchemeId() %>" />

				<div class="lfr-theme-list">
					<div class="float-container lfr-current-theme">
						<h3><liferay-ui:message key="current-theme" /></h3>
							<img alt="<%= selTheme.getName() %>" class="theme-screenshot" src="<%= selTheme.getContextPath() %><%= selTheme.getImagesPath() %>/thumbnail.png" title="<%= selTheme.getName() %>" />
							<div class="theme-details">
								<h4 class="theme-title"><%= selTheme.getName() %></h4>
								<dl>
									<c:if test="<%= Validator.isNotNull(selPluginPackage.getShortDescription()) %>">
										<dt><liferay-ui:message key="description" /></dt>
										<dd><%= selPluginPackage.getShortDescription() %></dd>
									</c:if>
									<c:if test="<%= Validator.isNotNull(selPluginPackage.getAuthor()) %>">
										<dt><liferay-ui:message key="author" /></dt>
										<dd><a href="<%= selPluginPackage.getPageURL() %>"><%= selPluginPackage.getAuthor() %></a></dd>
									</c:if>
								</dl>

								<c:if test="<%= colorSchemes.size() > 0 %>">

									<%
										String panelCssClass = "";
										String panelId = renderResponse.getNamespace() + "colorSchemes";
										String panelViewState = GetterUtil.getString(SessionClicks.get(request, panelId, null), "closed");

										if (panelViewState.equalsIgnoreCase("closed")) {
											panelCssClass = "lfr-collapsed";
										}
									 %>

									<div class="lfr-panel lfr-collapsible <%= panelCssClass %>" id="<%= panelId %>">
										<h4 class="lfr-panel-titlebar">
											<span class="lfr-panel-title">
												<%= LanguageUtil.format(pageContext, "color-schemes-x", colorSchemes.size()) %>
											</span>
										</h4>
										<div class="lfr-panel-content">
											<ul class="lfr-component lfr-theme-list">
											<%
												Iterator<ColorScheme> itr = colorSchemes.iterator();

												while (itr.hasNext()) {
													ColorScheme currentColorScheme = itr.next();
													String cssClass = "";

													if (selColorScheme.getColorSchemeId().equals(currentColorScheme.getColorSchemeId())) {
														cssClass = "selected-color-scheme";
													}
												%>
													<li class="<%= cssClass %>">
														<a class="theme-entry" href="javascript: ;" onclick="<portlet:namespace />updateLookAndFeel('<%= selTheme.getThemeId() %>', '<%= currentColorScheme.getColorSchemeId() %>', '');">
															<span class="theme-title"><%= currentColorScheme.getName() %></span>
															<img alt="<%= currentColorScheme.getName() %>" class="theme-thumbnail" class="" src="<%= selTheme.getContextPath() %><%= selColorScheme.getColorSchemeThumbnailPath() %>/thumbnail.png" title="<%= currentColorScheme.getName() %>" />
														</a>
													</li>
												<%
												}
											 %>
											</ul>
										</div>
									</div>
								</c:if>
							</div>
					</div>

					<div class="float-container lfr-available-themes">
						<h3>
							<span class="header-title">
								<%= LanguageUtil.format(pageContext, "available-themes-x", (themes.size() - 1)) %>
							</span>
							<span class="download-themes">
								<a href="javascript: ;">
									<liferay-ui:message key="download-more" />
								</a>
							</span>
						</h3>
						<c:if test="<%= themes.size() > 1 %>">
							<ul class="lfr-component lfr-theme-list">
								<%
									Iterator<Theme> itr = themes.iterator();

									while (itr.hasNext()) {
										Theme currentTheme = itr.next();

										if (!selTheme.getThemeId().equals(currentTheme.getThemeId())) {
									%>
										<li>
											<a class="theme-entry" href="javascript: ;" onclick="<portlet:namespace />updateLookAndFeel('<%= currentTheme.getThemeId() %>', '');">
												<span class="theme-title"><%= currentTheme.getName() %></span>
												<img alt="<%= currentTheme.getName() %>" class="theme-thumbnail" src="<%= currentTheme.getContextPath() %><%= currentTheme.getImagesPath() %>/thumbnail.png" title="<%= currentTheme.getName() %>" />
											</a>
										</li>
									<%
										}
									}
								 %>
							</ul>
						</c:if>
					</div>
				</div>
			</liferay-ui:section>
			<liferay-ui:section>

				<%
				String selColorSchemeId = StringPool.BLANK;

				if (selTheme.hasColorSchemes()) {
					selColorSchemeId = selColorScheme.getColorSchemeId();
				}

				String cssText = null;

				if ((selLayout != null) && !selLayout.isInheritLookAndFeel()) {
					cssText = selLayout.getCssText();
				}
				else {
					LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(groupId, privateLayout);

					cssText = layoutSet.getCss();
				}
				%>

				<liferay-ui:message key="insert-custom-css-that-will-loaded-after-the-theme" />

				<br /><br />

				<textarea class="lfr-textarea" name="<portlet:namespace />css"><%= cssText %></textarea>

				<br /><br />

				<input type="button" value="<liferay-ui:message key="save" />" onclick="<portlet:namespace />updateLookAndFeel('<%= selTheme.getThemeId() %>', '<%= selColorSchemeId %>', '<%= sectionParam %>', '<%= sectionName %>');" />
			</liferay-ui:section>
		</liferay-ui:tabs>
	</c:when>
	<c:when test='<%= tabs4.equals("mobile-devices") %>'>

		<%
		Theme selTheme = null;
		ColorScheme selColorScheme = null;

		if (selLayout != null) {
			selTheme = selLayout.getWapTheme();
			selColorScheme = selLayout.getWapColorScheme();
		}
		else {
			LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(groupId, privateLayout);

			selTheme = layoutSet.getWapTheme();
			selColorScheme = layoutSet.getWapColorScheme();
		}
		%>

		<c:if test="<%= selLayout != null %>">
			<table class="lfr-table">
			<tr>
				<td class="lfr-label">
					<%= LanguageUtil.get(pageContext, "use-the-general-look-and-feel-for-the-" + (privateLayout ? "public" : "private") + "-pages") %>
				</td>
				<td>
					<select name="<portlet:namespace />hidden" onChange="if (this.value == 1) { <portlet:namespace />updateLookAndFeel('', ''); } else { <portlet:namespace />updateLookAndFeel('<%= selTheme.getThemeId() %>', '<%= selColorScheme.getColorSchemeId() %>'); }">
						<option <%= (selLayout.isInheritWapLookAndFeel()) ? "selected" : "" %> value="1"><liferay-ui:message key="yes" /></option>
						<option <%= (!selLayout.isInheritWapLookAndFeel()) ? "selected" : "" %> value="0"><liferay-ui:message key="no" /></option>
					</select>
				</td>
			</table>

			<br />
		</c:if>

		<liferay-ui:tabs names="themes" />

		<%
		List themes = ThemeLocalServiceUtil.getThemes(company.getCompanyId(), liveGroupId, user.getUserId(), true);
		List colorSchemes = selTheme.getColorSchemes();
		PluginPackage selPluginPackage = selTheme.getPluginPackage();
		%>

		<input name="<portlet:namespace />themeId" type="hidden" value="<%= selTheme.getThemeId() %>" />
		<input name="<portlet:namespace />colorSchemeId" type="hidden" value="<%= selColorScheme.getColorSchemeId() %>" />

		<div class="lfr-theme-list">
			<div class="float-container lfr-current-theme">
				<h3><liferay-ui:message key="current-theme" /></h3>
					<img alt="<%= selTheme.getName() %>" class="theme-screenshot" src="<%= selTheme.getContextPath() %><%= selTheme.getImagesPath() %>/thumbnail.png" title="<%= selTheme.getName() %>" />
					<div class="theme-details">
						<h4 class="theme-title"><%= selTheme.getName() %></h4>
						<dl>
							<c:if test="<%= Validator.isNotNull(selPluginPackage.getShortDescription()) %>">
								<dt><liferay-ui:message key="description" /></dt>
								<dd><%= selPluginPackage.getShortDescription() %></dd>
							</c:if>
							<c:if test="<%= Validator.isNotNull(selPluginPackage.getAuthor()) %>">
								<dt><liferay-ui:message key="author" /></dt>
								<dd><a href="<%= selPluginPackage.getPageURL() %>"><%= selPluginPackage.getAuthor() %></a></dd>
							</c:if>
						</dl>

						<c:if test="<%= colorSchemes.size() > 0 %>">

							<%
								String panelCssClass = "";
								String panelId = renderResponse.getNamespace() + "colorSchemes";
								String panelViewState = GetterUtil.getString(SessionClicks.get(request, panelId, null), "closed");

								if (panelViewState.equalsIgnoreCase("closed")) {
									panelCssClass = "lfr-collapsed";
								}
							 %>

							<div class="lfr-panel lfr-collapsible <%= panelCssClass %>" id="<%= panelId %>">
								<h4 class="lfr-panel-titlebar">
									<span class="lfr-panel-title">
										<%= LanguageUtil.format(pageContext, "color-schemes-x", colorSchemes.size()) %>
									</span>
								</h4>
								<div class="lfr-panel-content">
									<ul class="lfr-component lfr-theme-list">
									<%
										Iterator<ColorScheme> itr = colorSchemes.iterator();

										while (itr.hasNext()) {
											ColorScheme currentColorScheme = itr.next();
											String cssClass = "";

											if (selColorScheme.getColorSchemeId().equals(currentColorScheme.getColorSchemeId())) {
												cssClass = "selected-color-scheme";
											}
										%>
											<li class="<%= cssClass %>">
												<a class="theme-entry" href="javascript: ;" onclick="<portlet:namespace />updateLookAndFeel('<%= selTheme.getThemeId() %>', '<%= currentColorScheme.getColorSchemeId() %>', '');">
													<span class="theme-title"><%= currentColorScheme.getName() %></span>
													<img alt="<%= currentColorScheme.getName() %>" class="theme-thumbnail" class="" src="<%= selTheme.getContextPath() %><%= selColorScheme.getColorSchemeThumbnailPath() %>/thumbnail.png" title="<%= currentColorScheme.getName() %>" />
												</a>
											</li>
										<%
										}
									 %>
									</ul>
								</div>
							</div>
						</c:if>
					</div>
			</div>

			<div class="float-container lfr-available-themes">
				<h3>
					<span class="header-title">
						<%= LanguageUtil.format(pageContext, "available-themes-x", (themes.size() - 1)) %>
					</span>
					<span class="download-themes">
						<a href="javascript: ;">
							<liferay-ui:message key="download-more" />
						</a>
					</span>
				</h3>

				<c:if test="<%= themes.size() > 1 %>">
					<ul class="lfr-component lfr-theme-list">
						<%
							Iterator<Theme> itr = themes.iterator();

							while (itr.hasNext()) {
								Theme currentTheme = itr.next();

								if (!selTheme.getThemeId().equals(currentTheme.getThemeId())) {
							%>
								<li>
									<a class="theme-entry" href="javascript: ;" onclick="<portlet:namespace />updateLookAndFeel('<%= currentTheme.getThemeId() %>', '');">
										<span class="theme-title"><%= currentTheme.getName() %></span>
										<img alt="<%= currentTheme.getName() %>" class="theme-thumbnail" src="<%= currentTheme.getContextPath() %><%= currentTheme.getImagesPath() %>/thumbnail.png" title="<%= currentTheme.getName() %>" />
									</a>
								</li>
							<%
								}
							}
						 %>
					</ul>
				</c:if>
			</div>
		</div>
	</c:when>
</c:choose>