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

<%@ include file="/html/common/init.jsp" %>

<%@ include file="/html/common/themes/top_meta.jspf" %>
<%@ include file="/html/common/themes/top_meta-ext.jsp" %>

<%@ page import="com.liferay.portlet.journal.NoSuchTemplateException" %>
<%@ page import="com.liferay.portlet.journal.model.JournalTemplate" %>
<%@ page import="com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil" %>

<link rel="Shortcut Icon" href="<%= themeDisplay.getPathThemeImages() %>/<%= PropsValues.THEME_SHORTCUT_ICON %>" />

<link href="<%= themeDisplay.getCDNHost() %><%= themeDisplay.getPathMain() %>/portal/css_cached?themeId=<%= themeDisplay.getTheme().getThemeId() %>&amp;colorSchemeId=<%= themeDisplay.getColorScheme().getColorSchemeId() %>&amp;t=<%= theme.getTimestamp() %>" type="text/css" rel="stylesheet" />

<%
List<Portlet> portlets = null;

if ((layout != null) && layout.getType().equals(LayoutConstants.TYPE_PORTLET)) {
	portlets = layoutTypePortlet.getAllPortlets();

	if (layoutTypePortlet.hasStateMaxPortletId(PortletKeys.PORTLET_CONFIGURATION)) {
		portlets.add(PortletLocalServiceUtil.getPortletById(company.getCompanyId(), PortletKeys.PORTLET_CONFIGURATION));
	}
}
%>

<c:if test="<%= portlets != null %>">

	<%
	Set<String> headerPortalCssPaths = new LinkedHashSet<String>();

	for (Portlet portlet : portlets) {
		List<String> headerPortalCssList = portlet.getHeaderPortalCss();

		for (String headerPortalCss : headerPortalCssList) {
			String headerPortalCssPath = request.getContextPath() + headerPortalCss;

			if (!headerPortalCssPaths.contains(headerPortalCssPath)) {
				headerPortalCssPaths.add(headerPortalCssPath);
	%>

				<link href="<%= headerPortalCssPath %>?t=<%= portlet.getTimestamp() %>" rel="stylesheet" type="text/css" />

	<%
			}
		}
	}

	Set<String> headerPortletCssPaths = new LinkedHashSet<String>();

	for (Portlet portlet : portlets) {
		List<String> headerPortletCssList = portlet.getHeaderPortletCss();

		for (String headerPortletCss : headerPortletCssList) {
			String headerPortletCssPath = portlet.getContextPath() + headerPortletCss;

			if (!headerPortletCssPaths.contains(headerPortletCssPath)) {
				headerPortletCssPaths.add(headerPortletCssPath);

				if (headerPortletCssPath.endsWith(".jsp")) {
					headerPortletCssPath += "?themeId=" + themeDisplay.getTheme().getThemeId() + "&amp;colorSchemeId=" + themeDisplay.getColorScheme().getColorSchemeId() + "&amp;t=" + portlet.getTimestamp();
				}
				else {
					headerPortletCssPath += "?t=" + portlet.getTimestamp();
				}
	%>

				<link href="<%= headerPortletCssPath %>" rel="stylesheet" type="text/css" />

	<%
			}
		}
	}
	%>

	<style type="text/css">

		<%
		for (Portlet portlet : portlets) {
			PortletPreferences portletSetup = PortletPreferencesFactoryUtil.getLayoutPortletSetup(layout, portlet.getPortletId());

			String portletSetupCss = portletSetup.getValue("portlet-setup-css", StringPool.BLANK);
		%>

			<c:if test="<%= Validator.isNotNull(portletSetupCss) %>">

				<%
				try {
				%>

					<%@ include file="/html/common/themes/portlet_css.jspf" %>

				<%
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(e.getMessage());
					}
				}
				%>

			</c:if>

		<%
		}
		%>

	</style>
</c:if>

<c:if test="<%= (layout != null) && Validator.isNotNull(layout.getCssText()) %>">
	<style type="text/css">
		<%= layout.getCssText() %>
	</style>
</c:if>

<%@ include file="/html/common/themes/top_js.jspf" %>
<%@ include file="/html/common/themes/top_js-ext.jspf" %>

<c:if test="<%= portlets != null %>">

	<%
	Set<String> headerPortalJavaScriptPaths = new LinkedHashSet<String>();

	for (Portlet portlet : portlets) {
		List<String> headerPortalJavaScriptList = portlet.getHeaderPortalJavaScript();

		for (String headerPortalJavaScript : headerPortalJavaScriptList) {
			String headerPortalJavaScriptPath = request.getContextPath() + headerPortalJavaScript;

			if (!headerPortalJavaScriptPaths.contains(headerPortalJavaScriptPath) && !themeDisplay.isIncludedJs(headerPortalJavaScriptPath)) {
				headerPortalJavaScriptPaths.add(headerPortalJavaScriptPath);
	%>

				<script src="<%= headerPortalJavaScriptPath %>?t=<%= portlet.getTimestamp() %>" type="text/javascript"></script>

	<%
			}
		}
	}

	Set<String> headerPortletJavaScriptPaths = new LinkedHashSet<String>();

	for (Portlet portlet : portlets) {
		List<String> headerPortletJavaScriptList = portlet.getHeaderPortletJavaScript();

		for (String headerPortletJavaScript : headerPortletJavaScriptList) {
			String headerPortletJavaScriptPath = portlet.getContextPath() + headerPortletJavaScript;

			if (!headerPortletJavaScriptPaths.contains(headerPortletJavaScriptPath)) {
				headerPortletJavaScriptPaths.add(headerPortletJavaScriptPath);
	%>

				<script src="<%= headerPortletJavaScriptPath %>?t=<%= portlet.getTimestamp() %>" type="text/javascript"></script>

	<%
			}
		}
	}
	%>

</c:if>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.common.themes.top_head.jsp");
%>