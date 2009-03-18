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

<%-- Portal JavaScript --%>

<c:if test="<%= themeDisplay.isIncludeCalendarJs() %>">

	<%
	String[] calendarDays = new String[CalendarUtil.DAYS_ABBREVIATION.length];

	Calendar cal = CalendarFactoryUtil.getCalendar(timeZone, locale);

	for (int i = 0; i < CalendarUtil.DAYS_ABBREVIATION.length; i++) {
		calendarDays[i] = LanguageUtil.get(pageContext, CalendarUtil.DAYS_ABBREVIATION[i]);
	}
	%>

	<script type="text/javascript">
		// <![CDATA[
			(function() {
				var defaultConfig = YAHOO.widget.Calendar._DEFAULT_CONFIG;

				defaultConfig.MONTHS_LONG.value = <%= JS.toScript(CalendarUtil.getMonths(locale)) %>;
				defaultConfig.MONTHS_SHORT.value = <%= JS.toScript(CalendarUtil.getMonths(locale, "MMM")) %>;

				defaultConfig.WEEKDAYS_LONG.value = <%= JS.toScript(CalendarUtil.getDays(locale)) %>;
				defaultConfig.WEEKDAYS_MEDIUM.value = <%= JS.toScript(CalendarUtil.getDays(locale, "EEE")) %>;
				defaultConfig.WEEKDAYS_SHORT.value = <%= JS.toScript(CalendarUtil.getDays(locale, "EE")) %>;
				defaultConfig.WEEKDAYS_1CHAR.value = <%= JS.toScript(calendarDays) %>;

				defaultConfig.START_WEEKDAY.value = <%= (cal.getFirstDayOfWeek() - 1) % 7 %>;

				defaultConfig.NAV.value = true;

				defaultConfig.STRINGS.value = {
					close: '<%= UnicodeLanguageUtil.get(pageContext, "close") %>',
					nextMonth: '<%= UnicodeLanguageUtil.get(pageContext, "next") %>',
					previousMonth: '<%= UnicodeLanguageUtil.get(pageContext, "previous") %>'
				};
			})();
		// ]]>
	</script>
</c:if>

<c:if test="<%= themeDisplay.isIncludePortletCssJs() %>">

	<%
	long javaScriptLastModified = ServletContextUtil.getLastModified(application, "/html/js", true);
	%>

	<script src="<%= HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, themeDisplay.getPathJavaScript() + "/liferay/portlet_css.js", javaScriptLastModified)) %>" type="text/javascript"></script>
</c:if>

<%-- Portlet CSS and JavaScript References --%>

<%
List<Portlet> portlets = (List<Portlet>)request.getAttribute(WebKeys.LAYOUT_PORTLETS);
%>

<c:if test="<%= portlets != null %>">

	<%
	Set<String> footerPortalCssPaths = new LinkedHashSet<String>();

	for (Portlet portlet : portlets) {
		List<String> footerPortalCssList = portlet.getFooterPortalCss();

		for (String footerPortalCss : footerPortalCssList) {
			String footerPortalCssPath = request.getContextPath() + footerPortalCss;

			if (!footerPortalCssPaths.contains(footerPortalCssPath)) {
				footerPortalCssPaths.add(footerPortalCssPath);

				footerPortalCssPath = HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, footerPortalCssPath, portlet.getTimestamp()));
	%>

				<link href="<%= footerPortalCssPath %>" rel="stylesheet" type="text/css" />

	<%
			}
		}
	}

	Set<String> footerPortletCssPaths = new LinkedHashSet<String>();

	for (Portlet portlet : portlets) {
		List<String> footerPortletCssList = portlet.getFooterPortletCss();

		for (String footerPortletCss : footerPortletCssList) {
			String footerPortletCssPath = portlet.getContextPath() + footerPortletCss;

			if (!footerPortletCssPaths.contains(footerPortletCssPath)) {
				footerPortletCssPaths.add(footerPortletCssPath);

				footerPortletCssPath = HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, footerPortletCssPath, portlet.getTimestamp()));
	%>

				<link href="<%= footerPortletCssPath %>" rel="stylesheet" type="text/css" />

	<%
			}
		}
	}

	Set<String> footerPortalJavaScriptPaths = new LinkedHashSet<String>();

	for (Portlet portlet : portlets) {
		List<String> footerPortalJavaScriptList = portlet.getFooterPortalJavaScript();

		for (String footerPortalJavaScript : footerPortalJavaScriptList) {
			String footerPortalJavaScriptPath = request.getContextPath() + footerPortalJavaScript;

			if (!footerPortalJavaScriptPaths.contains(footerPortalJavaScriptPath) && !themeDisplay.isIncludedJs(footerPortalJavaScriptPath)) {
				footerPortalJavaScriptPaths.add(footerPortalJavaScriptPath);

				footerPortalJavaScriptPath = HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, footerPortalJavaScriptPath, portlet.getTimestamp()));
	%>

				<script src="<%= footerPortalJavaScriptPath %>" type="text/javascript"></script>

	<%
			}
		}
	}

	Set<String> footerPortletJavaScriptPaths = new LinkedHashSet<String>();

	for (Portlet portlet : portlets) {
		List<String> footerPortletJavaScriptList = portlet.getFooterPortletJavaScript();

		for (String footerPortletJavaScript : footerPortletJavaScriptList) {
			String footerPortletJavaScriptPath = portlet.getContextPath() + footerPortletJavaScript;

			if (!footerPortletJavaScriptPaths.contains(footerPortletJavaScriptPath)) {
				footerPortletJavaScriptPaths.add(footerPortletJavaScriptPath);

				footerPortletJavaScriptPath = HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, footerPortletJavaScriptPath, portlet.getTimestamp()));
	%>

				<script src="<%= footerPortletJavaScriptPath %>" type="text/javascript"></script>

	<%
			}
		}
	}
	%>

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
			<%= typeSettings.getProperty("javascript-1") %>
			<%= typeSettings.getProperty("javascript-2") %>
			<%= typeSettings.getProperty("javascript-3") %>
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

<liferay-util:include page="/html/common/themes/bottom-ext.jsp" />