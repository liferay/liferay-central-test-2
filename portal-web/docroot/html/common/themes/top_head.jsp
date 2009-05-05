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

<%@ include file="/html/common/init.jsp" %>

<%@ include file="/html/common/themes/top_meta.jspf" %>
<%@ include file="/html/common/themes/top_meta-ext.jsp" %>

<link rel="Shortcut Icon" href="<%= themeDisplay.getPathThemeImages() %>/<%= PropsValues.THEME_SHORTCUT_ICON %>" />

<%-- Portal CSS --%>

<link href="<%= HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNHost() + themeDisplay.getPathContext() + "/html/portal/css.jsp")) %>" rel="stylesheet" type="text/css" />

<%
List<Portlet> portlets = null;

if (layout != null) {
	String type = layout.getType();

	if (type.equals(LayoutConstants.TYPE_PORTLET)) {
		portlets = layoutTypePortlet.getAllPortlets();

		if (themeDisplay.isStateMaximized()) {
			String ppid = ParamUtil.getString(request, "p_p_id");

			if (Validator.isNotNull(ppid)) {
				Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), ppid);

				if (!portlets.contains(portlet)) {
					portlets.add(portlet);
				}
			}
		}
	}
	else if (type.equals(LayoutConstants.TYPE_CONTROL_PANEL) || type.equals(LayoutConstants.TYPE_PANEL)) {
		portlets = new ArrayList<Portlet>();

		String ppid = ParamUtil.getString(request, "p_p_id");

		if (ppid.equals(PortletKeys.PORTLET_CONFIGURATION)) {
			ppid = ParamUtil.getString(request, PortalUtil.getPortletNamespace(ppid) + "portletResource");
		}

		if (Validator.isNotNull(ppid)) {
			portlets.add(PortletLocalServiceUtil.getPortletById(company.getCompanyId(), ppid));
		}
	}

	request.setAttribute(WebKeys.LAYOUT_PORTLETS, portlets);
}
%>

<%-- Portlet CSS References --%>

<c:if test="<%= portlets != null %>">

	<%
	Set<String> headerPortalCssPaths = new LinkedHashSet<String>();

	for (Portlet portlet : portlets) {
		List<String> headerPortalCssList = portlet.getHeaderPortalCss();

		for (String headerPortalCss : headerPortalCssList) {
			String headerPortalCssPath = request.getContextPath() + headerPortalCss;

			if (!headerPortalCssPaths.contains(headerPortalCssPath)) {
				headerPortalCssPaths.add(headerPortalCssPath);

				headerPortalCssPath = HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, headerPortalCssPath, portlet.getTimestamp()));
	%>

				<link href="<%= headerPortalCssPath %>" rel="stylesheet" type="text/css" />

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

				headerPortletCssPath = HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, headerPortletCssPath, portlet.getTimestamp()));
	%>

				<link href="<%= headerPortletCssPath %>" rel="stylesheet" type="text/css" />

	<%
			}
		}
	}
	%>

</c:if>

<%-- Portal JavaScript References --%>

<%@ include file="/html/common/themes/top_js.jspf" %>
<%@ include file="/html/common/themes/top_js-ext.jspf" %>

<%-- Portlet JavaScript References --%>

<c:if test="<%= portlets != null %>">

	<%
	Set<String> headerPortalJavaScriptPaths = new LinkedHashSet<String>();

	for (Portlet portlet : portlets) {
		List<String> headerPortalJavaScriptList = portlet.getHeaderPortalJavaScript();

		for (String headerPortalJavaScript : headerPortalJavaScriptList) {
			String headerPortalJavaScriptPath = request.getContextPath() + headerPortalJavaScript;

			if (!headerPortalJavaScriptPaths.contains(headerPortalJavaScriptPath) && !themeDisplay.isIncludedJs(headerPortalJavaScriptPath)) {
				headerPortalJavaScriptPaths.add(headerPortalJavaScriptPath);

				headerPortalJavaScriptPath = HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, headerPortalJavaScriptPath, portlet.getTimestamp()));
	%>

				<script src="<%= headerPortalJavaScriptPath %>" type="text/javascript"></script>

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

				headerPortletJavaScriptPath = HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, headerPortletJavaScriptPath, portlet.getTimestamp()));
	%>

				<script src="<%= headerPortletJavaScriptPath %>" type="text/javascript"></script>

	<%
			}
		}
	}
	%>

</c:if>

<%-- Raw Text --%>

<%
List<String> markupHeaders = (List<String>)request.getAttribute(MimeResponse.MARKUP_HEAD_ELEMENT);

if (markupHeaders != null) {
	for (String markupHeader : markupHeaders) {
%>

		<%= markupHeader %>

<%
	}
}

StringBuilder pageTopSB = (StringBuilder)request.getAttribute(WebKeys.PAGE_TOP);
%>

<c:if test="<%= pageTopSB != null %>">
	<%= pageTopSB.toString() %>
</c:if>

<%-- Theme CSS --%>

<link href="<%= HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, themeDisplay.getPathThemeCss() + "/main.css")) %>" rel="stylesheet" type="text/css" />

<style type="text/css">
	/* <![CDATA[ */
		<c:if test="<%= !themeDisplay.getCompanyLogo().equals(StringPool.BLANK) %>">
			#banner .logo a {
				background: url(<%= HtmlUtil.escape(themeDisplay.getCompanyLogo()) %>) no-repeat;
				display: block;
				font-size: 0;
				height: <%= themeDisplay.getCompanyLogoHeight() %>px;
				text-indent: -9999em;
				width: <%= themeDisplay.getCompanyLogoWidth() %>px;
			}
		</c:if>

		<c:if test="<%= BrowserSnifferUtil.isIe(request) && (BrowserSnifferUtil.getMajorVersion(request) < 7) %>">
			img, .png {
				position: relative;
				behavior: expression(
					(this.runtimeStyle.behavior = "none") &&
					(
						this.pngSet || (this.src && this.src.toLowerCase().indexOf('spacer.png') > -1) ?
							this.pngSet = true :
								(
									this.nodeName == "IMG" &&
									(
										(this.src.toLowerCase().indexOf('.png') > -1) ||
										(this.className && ([''].concat(this.className.split(' ')).concat(['']).join('|').indexOf('|png|')) > -1)
									) ?
										(
											this.runtimeStyle.backgroundImage = "none",
											this.runtimeStyle.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + this.src + "', sizingMethod='image')",
											this.src = "<%= themeDisplay.getPathThemeImages() %>/spacer.png"
										) :
											(
											   (
													(this.currentStyle.backgroundImage.toLowerCase().indexOf('.png') > -1) ||
													(this.className && ([''].concat(this.className.split(' ')).concat(['']).join('|').indexOf('|png|')) > -1)
												) ?
													(
															this.origBg = this.origBg ?
																this.origBg :
																this.currentStyle.backgroundImage.toString().replace('url("','').replace('")',''),
																this.runtimeStyle.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + this.origBg + "', sizingMethod='crop')",
																this.runtimeStyle.backgroundImage = "none"
													) :
														''
											)
								),
								this.pngSet = true
					)
				);
			}
		</c:if>
	/* ]]> */
</style>

<%-- User Inputted Layout CSS --%>

<c:if test="<%= (layout != null) && Validator.isNotNull(layout.getCssText()) %>">
	<style type="text/css">
		<%= layout.getCssText() %>
	</style>
</c:if>

<%-- User Inputted Portlet CSS --%>

<c:if test="<%= portlets != null %>">
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

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.common.themes.top_head.jsp");
%>