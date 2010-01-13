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

<%@ page import="com.liferay.taglib.aui.ScriptData" %>

<%-- Portal JavaScript --%>

<c:if test="<%= themeDisplay.isIncludePortletCssJs() %>">

	<%
	long javaScriptLastModified = ServletContextUtil.getLastModified(application, "/html/js", true);
	%>

	<script src="<%= HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, themeDisplay.getPathJavaScript() + "/liferay/portlet_css.js", javaScriptLastModified)) %>" type="text/javascript"></script>
</c:if>

<%-- Portlet CSS References --%>

<%
List<Portlet> portlets = (List<Portlet>)request.getAttribute(WebKeys.LAYOUT_PORTLETS);
%>

<c:if test="<%= portlets != null %>">

	<%
	Set<String> footerPortalCssSet = new LinkedHashSet<String>();

	for (Portlet portlet : portlets) {
		for (String footerPortalCss : portlet.getFooterPortalCss()) {
			if (!HttpUtil.hasProtocol(footerPortalCss)) {
				footerPortalCss = PortalUtil.getStaticResourceURL(request, request.getContextPath() + footerPortalCss, portlet.getTimestamp());
			}

			if (!footerPortalCssSet.contains(footerPortalCss)) {
				footerPortalCssSet.add(footerPortalCss);
	%>

				<link href="<%= HtmlUtil.escape(footerPortalCss) %>" rel="stylesheet" type="text/css" />

	<%
			}
		}
	}

	Set<String> footerPortletCssSet = new LinkedHashSet<String>();

	for (Portlet portlet : portlets) {
		for (String footerPortletCss : portlet.getFooterPortletCss()) {
			if (!HttpUtil.hasProtocol(footerPortletCss)) {
				footerPortletCss = PortalUtil.getStaticResourceURL(request, portlet.getContextPath() + footerPortletCss, portlet.getTimestamp());
			}

			if (!footerPortletCssSet.contains(footerPortletCss)) {
				footerPortletCssSet.add(footerPortletCss);
	%>

				<link href="<%= HtmlUtil.escape(footerPortletCss) %>" rel="stylesheet" type="text/css" />

	<%
			}
		}
	}
	%>

</c:if>

<%-- Portlet JavaScript References --%>

<c:if test="<%= portlets != null %>">

	<%
	Set<String> footerPortalJavaScriptSet = new LinkedHashSet<String>();

	for (Portlet portlet : portlets) {
		for (String footerPortalJavaScript : portlet.getFooterPortalJavaScript()) {
			if (!HttpUtil.hasProtocol(footerPortalJavaScript)) {
				footerPortalJavaScript = PortalUtil.getStaticResourceURL(request, request.getContextPath() + footerPortalJavaScript, portlet.getTimestamp());
			}

			if (!footerPortalJavaScriptSet.contains(footerPortalJavaScript) && !themeDisplay.isIncludedJs(footerPortalJavaScript)) {
				footerPortalJavaScriptSet.add(footerPortalJavaScript);
	%>

				<script src="<%= HtmlUtil.escape(footerPortalJavaScript) %>" type="text/javascript"></script>

	<%
			}
		}
	}

	Set<String> footerPortletJavaScriptSet = new LinkedHashSet<String>();

	for (Portlet portlet : portlets) {
		for (String footerPortletJavaScript : portlet.getFooterPortletJavaScript()) {
			if (!HttpUtil.hasProtocol(footerPortletJavaScript)) {
				footerPortletJavaScript = PortalUtil.getStaticResourceURL(request, portlet.getContextPath() + footerPortletJavaScript, portlet.getTimestamp());
			}

			if (!footerPortletJavaScriptSet.contains(footerPortletJavaScript)) {
				footerPortletJavaScriptSet.add(footerPortletJavaScript);
	%>

				<script src="<%= HtmlUtil.escape(footerPortletJavaScript) %>" type="text/javascript"></script>

	<%
			}
		}
	}
	%>

</c:if>

<%
ScriptData scriptData = (ScriptData)request.getAttribute(WebKeys.AUI_SCRIPT_DATA);
%>

<c:if test="<%= scriptData != null %>">
	<script type="text/javascript">
		// <![CDATA[
			<%= scriptData.getRawSB().toString() %>

			<%
			String callback = scriptData.getCallbackSB().toString();
			%>

			<c:if test="<%= Validator.isNotNull(callback) %>">

				<%
				Set<String> useSet = scriptData.getUseSet();

				StringBundler useSB = new StringBundler(useSet.size() * 4);

				for (String use : useSet) {
					useSB.append(StringPool.APOSTROPHE);
					useSB.append(use);
					useSB.append(StringPool.APOSTROPHE);
					useSB.append(StringPool.COMMA_AND_SPACE);
				}
				%>

				AUI().use(
					<%= useSB.toString() %>
					function(A) {
						<%= callback %>
					}
				);
			</c:if>
		// ]]>
	</script>
</c:if>

<%-- Raw Text --%>

<%
StringBuilder pageBottomSB = (StringBuilder)request.getAttribute(WebKeys.PAGE_BOTTOM);
%>

<c:if test="<%= pageBottomSB != null %>">
	<%= pageBottomSB.toString() %>
</c:if>

<%-- Theme JavaScript --%>

<script src="<%= HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, themeDisplay.getPathThemeJavaScript() + "/javascript.js")) %>" type="text/javascript"></script>

<c:if test="<%= layout != null %>">

	<%-- User Inputted Layout JavaScript --%>

	<%
	UnicodeProperties typeSettings = layout.getTypeSettingsProperties();
	%>

	<script type="text/javascript">
		// <![CDATA[
			<%= GetterUtil.getString(typeSettings.getProperty("javascript-1")) %>
			<%= GetterUtil.getString(typeSettings.getProperty("javascript-2")) %>
			<%= GetterUtil.getString(typeSettings.getProperty("javascript-3")) %>
		// ]]>
	</script>

	<%-- Google Analytics --%>

	<%
	UnicodeProperties groupTypeSettings = layout.getGroup().getTypeSettingsProperties();

	String googleAnalyticsId = groupTypeSettings.getProperty("googleAnalyticsId");

	if (Validator.isNotNull(googleAnalyticsId)) {
	%>

		<script type="text/javascript">
			var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");

			document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
		</script>

		<script type="text/javascript">
			var pageTracker = _gat._getTracker("<%= googleAnalyticsId %>");

			pageTracker._trackPageview();
		</script>

	<%
	}
	%>

</c:if>

<%@ include file="/html/common/themes/session_timeout.jspf" %>

<c:if test="<%= PropsValues.MONITORING_PORTAL_REQUEST %>">
	<%@ include file="/html/common/themes/bottom_monitoring.jspf" %>
</c:if>

<liferay-util:include page="/html/common/themes/bottom-ext.jsp" />