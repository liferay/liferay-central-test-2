<%
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

<script type="text/javascript">

	<%
	int sessionTimeout = GetterUtil.getInteger(PropsUtil.get(PropsUtil.SESSION_TIMEOUT));
	int sessionTimeoutWarning = GetterUtil.getInteger(PropsUtil.get(PropsUtil.SESSION_TIMEOUT_WARNING));
	int timeoutDiff = (sessionTimeout - sessionTimeoutWarning) * (int)Time.MINUTE;

	Calendar sessionTimeoutCal = new GregorianCalendar(timeZone);

	sessionTimeoutCal.add(Calendar.MILLISECOND, sessionTimeout * (int)Time.MINUTE);
	%>

	<c:if test="<%= (themeDisplay.isSignedIn()) && (sessionTimeoutWarning > 0) && (timeoutDiff > 0) %>">
		setTimeout("openSessionWarning()", <%= timeoutDiff %>);
	</c:if>

	function extendSession() {
		loadPage("<%= themeDisplay.getPathMain() %>/portal/extend_session");

		setTimeout("openSessionWarning()", <%= timeoutDiff %>);
	}

	function openSessionWarning() {
		Alerts.fireMessageBox(true, "<font class=\"bg\" size=\"2\"><span class=\"bg-neg-alert\" id=\"session_warning_text\"><%= LanguageUtil.format(pageContext, "warning-due-to-inactivity-your-session-will-expire", new Object[] {new Integer(sessionTimeoutWarning), DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, locale).format(Time.getDate(sessionTimeoutCal)), timeZone.getDisplayName(false, TimeZone.SHORT, locale), new Integer(sessionTimeout)}, false) %></span></font>", "extendSession();", "");
	}
</script>