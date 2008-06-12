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

<c:if test="<%= themeDisplay.isIncludeCalendarJs() %>">
<%
Calendar selCal = CalendarFactoryUtil.getCalendar(timeZone, locale);
%>
	<script type="text/javascript">
		jQuery.datepicker.setDefaults(
			{
				clearText: '<liferay-ui:message key="clear" />', // Display text for clear link
				clearStatus: '<liferay-ui:message key="erase-the-current-date" />', // Status text for clear link
				closeText: '<liferay-ui:message key="close" />', // Display text for close link
				closeStatus: '<liferay-ui:message key="cancel" />', // Status text for close link
				prevText: '&#x3c;<liferay-ui:message key="previous" />', // Display text for previous month link
				prevStatus: '<liferay-ui:message key="previous" />', // Status text for previous month link
				nextText: '<liferay-ui:message key="next" />&#x3e;', // Display text for next month link
				nextStatus: '<liferay-ui:message key="next" />', // Status text for next month link
				currentText: '<liferay-ui:message key="today" />', // Display text for current month link
				currentStatus: '<liferay-ui:message key="current-month" />', // Status text for current month link
				<%
				String[] calendarMonths = CalendarUtil.getMonths(locale);
				%>
				monthNames: <%= JS.toScript(calendarMonths) %>, // Names of months for drop-down and formatting
				<%
				calendarMonths = CalendarUtil.getMonths(locale, "MMM");
				%>
				monthNamesShort: <%= JS.toScript(calendarMonths) %>, // For formatting

				monthStatus: '<liferay-ui:message key="show-a-different-month" />', // Status text for selecting a month
				yearStatus: '<liferay-ui:message key="show-a-different-year" />', // Status text for selecting a year
				weekHeader: '<liferay-ui:message key="week-abbreviation" />', // Header for the week of the year column
				weekStatus: '<liferay-ui:message key="week-of-the-year" />', // Status text for the week of the year column

				<%
				String[] calendarDays = CalendarUtil.getDays(locale, "EEEE");
				%>
				dayNames: <%= JS.toScript(calendarDays) %>, // For formatting
				<%
				calendarDays = CalendarUtil.getDays(locale, "EEE");
				%>
				dayNamesShort: <%= JS.toScript(calendarMonths) %>, // For formatting
				<%
				int i = 0;
				for (String day : calendarDays) {
					int daysIndex = (selCal.getFirstDayOfWeek() + i - 1) % 7;

					calendarDays[i] = LanguageUtil.get(pageContext, CalendarUtil.DAYS_ABBREVIATION[daysIndex]);
					i++;
				}
				%>
				dayNamesMin: <%= JS.toScript(calendarDays) %>, // Column headings for days starting at Sunday

				dayStatus: '', // Status text for the day of the week selection (Set DD as first week day)
				dateStatus: '', // Status text for the date selection (Select DD, M d)
				dateFormat: 'mm/dd/yy', // See format options on parseDate
				firstDay: <%= (selCal.getFirstDayOfWeek() - 1) % 7 %>, // The first day of the week, Sun = 0, Mon = 1, ...
				initStatus: '<liferay-ui:message key="select-date" />', // Initial Status text on opening
				isRTL: ('<liferay-ui:message key="lang.dir" />' === 'rtl') // True if right-to-left language, false if left-to-right
			}
		);
	</script>
</c:if>

<c:if test="<%= themeDisplay.isIncludePortletCssJs() %>">
	<script src="<%= themeDisplay.getPathJavaScript() %>/liferay/portlet_css_packed.js?bn=<%= ReleaseInfo.getBuildNumber() %>" type="text/javascript"></script>
</c:if>

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
	Properties groupTypeSettings = layout.getGroup().getTypeSettingsProperties();

	String googleAnalyticsId = groupTypeSettings.getProperty("googleAnalyticsId");

	if (Validator.isNotNull(googleAnalyticsId)) {
%>

		<script type="text/javascript">
			var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");

			document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
		</script>

		<script type="text/javascript">
			var pageTracker = _gat._getTracker("<%= googleAnalyticsId %>");

			pageTracker._initData();
			pageTracker._trackPageview();
		</script>

<%
	}
}
%>

<c:if test="<%= PropsValues.WEB_SERVER_DISPLAY_NODE %>">
	<div class="portlet-msg-info">
		<liferay-ui:message key="node" />: <%= PortalUtil.getComputerName() %>
	</div>
</c:if>

<form action="" method="post" name="hrefFm"></form>

<liferay-util:include page="/html/common/themes/bottom-ext.jsp" />