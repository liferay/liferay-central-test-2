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

<c:if test="<%= themeDisplay.isIncludeCalendarJs() %>">
	<script type="text/javascript">
		jQuery.datepicker.setDefaults(
			{
				clearText: '<%= UnicodeLanguageUtil.get(pageContext, "clear") %>',
				clearStatus: '<%= UnicodeLanguageUtil.get(pageContext, "erase-the-current-date") %>',
				closeText: '<%= UnicodeLanguageUtil.get(pageContext, "close") %>',
				closeStatus: '<%= UnicodeLanguageUtil.get(pageContext, "cancel") %>',
				prevText: '&#x3c;<%= UnicodeLanguageUtil.get(pageContext, "previous") %>',
				prevStatus: '<%= UnicodeLanguageUtil.get(pageContext, "previous") %>',
				nextText: '<%= UnicodeLanguageUtil.get(pageContext, "next") %>&#x3e;',
				nextStatus: '<%= UnicodeLanguageUtil.get(pageContext, "next") %>',
				currentText: '<%= UnicodeLanguageUtil.get(pageContext, "today") %>',
				currentStatus: '<%= UnicodeLanguageUtil.get(pageContext, "current-month") %>',
				monthNames: <%= JS.toScript(CalendarUtil.getMonths(locale)) %>,
				monthNamesShort: <%= JS.toScript(CalendarUtil.getMonths(locale, "MMM")) %>,
				monthStatus: '<%= UnicodeLanguageUtil.get(pageContext, "show-a-different-month") %>',
				yearStatus: '<%= UnicodeLanguageUtil.get(pageContext, "show-a-different-year") %>',
				weekHeader: '<%= UnicodeLanguageUtil.get(pageContext, "week-abbreviation") %>',
				weekStatus: '<%= UnicodeLanguageUtil.get(pageContext, "wekk-of-the-year") %>',
				dayNames: <%= JS.toScript(CalendarUtil.getDays(locale)) %>,
				dayNamesShort: <%= JS.toScript(CalendarUtil.getDays(locale, "EEE")) %>,

				<%
				String[] calendarDays = new String[CalendarUtil.DAYS_ABBREVIATION.length];

				Calendar cal = CalendarFactoryUtil.getCalendar(timeZone, locale);

				for (int i = 0; i < CalendarUtil.DAYS_ABBREVIATION.length; i++) {
					calendarDays[i] = LanguageUtil.get(pageContext, CalendarUtil.DAYS_ABBREVIATION[i]);
				}
				%>

				dayNamesMin: <%= JS.toScript(calendarDays) %>,
				dayStatus: '',
				dateStatus: '',
				dateFormat: 'mm/dd/yy',
				firstDay: <%= (cal.getFirstDayOfWeek() - 1) % 7 %>,
				initStatus: '<%= UnicodeLanguageUtil.get(pageContext, "select-date") %>',
				isRTL: ('<liferay-ui:message key="lang.dir" />' === 'rtl')
			}
		);
	</script>
</c:if>

<c:if test="<%= themeDisplay.isIncludePortletCssJs() %>">
	<script src="<%= themeDisplay.getPathJavaScript() %>/liferay/portlet_css_packed.js?bn=<%= ReleaseInfo.getBuildNumber() %>" type="text/javascript"></script>
</c:if>

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

			if (!footerPortalCssPaths.contains(footerPortalCssPath) && !themeDisplay.isIncludedJs(footerPortalCssPath)) {
				footerPortalCssPaths.add(footerPortalCssPath);
	%>

				<link href="<%= footerPortalCssPath %>?t=<%= portlet.getTimestamp() %>" rel="stylesheet" type="text/css" />

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

				if (footerPortletCssPath.endsWith(".jsp")) {
					footerPortletCssPath += "?themeId=" + themeDisplay.getTheme().getThemeId() + "&amp;colorSchemeId=" + themeDisplay.getColorScheme().getColorSchemeId() + "&amp;t=" + portlet.getTimestamp();
				}
				else {
					footerPortletCssPath += "?t=" + portlet.getTimestamp();
				}
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

			if (!footerPortalJavaScriptPaths.contains(footerPortalJavaScriptPath)) {
				footerPortalJavaScriptPaths.add(footerPortalJavaScriptPath);
	%>

				<script src="<%= footerPortalJavaScriptPath %>?t=<%= portlet.getTimestamp() %>" type="text/javascript"></script>

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
				if (!PropsValues.JAVASCRIPT_FAST_LOAD && footerPortletJavaScriptPath.endsWith("packed.js")) {
					StringBuilder sb = new StringBuilder();

					sb.append(footerPortletJavaScriptPath.substring(0, footerPortletJavaScriptPath.length() - 9));
					sb.append("unpacked.js");

					footerPortletJavaScriptPath = sb.toString();
				}

				footerPortletJavaScriptPaths.add(footerPortletJavaScriptPath);
	%>

				<script src="<%= footerPortletJavaScriptPath %>?t=<%= portlet.getTimestamp() %>" type="text/javascript"></script>

	<%
			}
		}
	}
	%>

</c:if>

<%
if (layout != null) {
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
}
%>

<%@ include file="/html/common/themes/session_timeout.jspf" %>

<liferay-util:include page="/html/common/themes/bottom-ext.jsp" />