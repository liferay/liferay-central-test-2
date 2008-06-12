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
	<script type="text/javascript">
		jQuery.datepicker.setDefaults(
			{
				clearText: '<liferay-ui:message key="clear" />',
				clearStatus: '<liferay-ui:message key="erase-the-current-date" />',
				closeText: '<liferay-ui:message key="close" />',
				closeStatus: '<liferay-ui:message key="cancel" />',
				prevText: '&#x3c;<liferay-ui:message key="previous" />',
				prevStatus: '<liferay-ui:message key="previous" />',
				nextText: '<liferay-ui:message key="next" />&#x3e;',
				nextStatus: '<liferay-ui:message key="next" />',
				currentText: '<liferay-ui:message key="today" />',
				currentStatus: '<liferay-ui:message key="current-month" />',
				monthNames: <%= JS.toScript(CalendarUtil.getMonths(locale)) %>,
				monthNamesShort: <%= JS.toScript(CalendarUtil.getMonths(locale, "MMM")) %>,
				monthStatus: '<liferay-ui:message key="show-a-different-month" />',
				yearStatus: '<liferay-ui:message key="show-a-different-year" />',
				weekHeader: '<liferay-ui:message key="week-abbreviation" />',
				weekStatus: '<liferay-ui:message key="week-of-the-year" />',
				dayNames: <%= JS.toScript(CalendarUtil.getDays(locale, "EEEE")) %>,

				<%
				String[] calendarDays = CalendarUtil.getDays(locale, "EEEE");
				%>

				dayNamesShort: <%= JS.toScript(calendarDays) %>,

				<%
				Calendar cal = CalendarFactoryUtil.getCalendar(timeZone, locale);

				for (int i = 0; i < calendarDays.length; i++) {
					String day = calendarDays[i];

					int daysIndex = (cal.getFirstDayOfWeek() + i - 1) % 7;

					calendarDays[i] = LanguageUtil.get(pageContext, CalendarUtil.DAYS_ABBREVIATION[daysIndex]);
				}
				%>

				dayNamesMin: <%= JS.toScript(calendarDays) %>,
				dayStatus: '',
				dateStatus: '',
				dateFormat: 'mm/dd/yy',
				firstDay: <%= (cal.getFirstDayOfWeek() - 1) % 7 %>,
				initStatus: '<liferay-ui:message key="select-date" />',
				isRTL: ('<liferay-ui:message key="lang.dir" />' === 'rtl')
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