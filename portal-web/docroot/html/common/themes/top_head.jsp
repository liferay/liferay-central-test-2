<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

<link rel="Shortcut Icon" href="<%= themeDisplay.getPathThemeImages() %>/liferay.ico" />

<link href="<%= themeDisplay.getPathMain() %>/portal/css_cached?themeId=<%= themeDisplay.getTheme().getThemeId() %>&colorSchemeId=<%= themeDisplay.getColorScheme().getColorSchemeId() %>" type="text/css" rel="stylesheet" />
<link href="<%= themeDisplay.getPathJavaScript() %>/calendar/skins/aqua/theme.css" rel="stylesheet" type="text/css" />

<%
try {
	JournalTemplate template = JournalTemplateLocalServiceUtil.getTemplate(portletGroupId.longValue(), "CSS-TEMPLATE");
%>

	<link rel="stylesheet" type="text/css" href="<%= themeDisplay.getPathMain() %>/journal/get_template?groupId=<%= portletGroupId.longValue() %>&templateId=CSS-TEMPLATE" />

<%
}
catch (NoSuchTemplateException nste) {
}

List portlets = null;

if ((layout != null) && layout.getType().equals(LayoutImpl.TYPE_PORTLET)) {
	portlets = layoutTypePortlet.getPortlets();
}
%>

<c:if test="<%= portlets != null %>">

	<%
	Set headerCssPortlets = CollectionFactory.getHashSet();
	Set headerCssPaths = CollectionFactory.getHashSet();

	for (int i = 0; i < portlets.size(); i++) {
		Portlet portlet = (Portlet)portlets.get(i);

		if (!headerCssPortlets.contains(portlet.getRootPortletId())) {
			headerCssPortlets.add(portlet.getRootPortletId());

			List headerCssList = portlet.getHeaderCss();

			for (int j = 0; j < headerCssList.size(); j++) {
				String headerCss = (String)headerCssList.get(j);

				String headerCssPath = portlet.getContextPath() + headerCss;

				if (!headerCssPaths.contains(headerCssPath)) {
					headerCssPaths.add(headerCssPath);
	%>

					<link href="<%= headerCssPath %>" rel="stylesheet" type="text/css" />

	<%
				}
			}
		}
	}
	%>

	<style type="text/css">

		<%
		for (int i = 0; i < portlets.size(); i++) {
			Portlet portlet = (Portlet)portlets.get(i);

			PortletPreferences portletSetup = PortletPreferencesFactory.getPortletSetup(request, portlet.getPortletId(), true, true);

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
<%@ include file="/html/common/themes/top_js-ext.jsp" %>

<c:if test="<%= portlets != null %>">

	<%
	Set headerJavaScriptPortlets = CollectionFactory.getHashSet();
	Set headerJavaScriptPaths = CollectionFactory.getHashSet();

	for (int i = 0; i < portlets.size(); i++) {
		Portlet portlet = (Portlet)portlets.get(i);

		if (!headerJavaScriptPortlets.contains(portlet.getRootPortletId())) {
			headerJavaScriptPortlets.add(portlet.getRootPortletId());

			List headerJavaScriptList = portlet.getHeaderJavaScript();

			for (int j = 0; j < headerJavaScriptList.size(); j++) {
				String headerJavaScript = (String)headerJavaScriptList.get(j);

				String headerJavaScriptPath = portlet.getContextPath() + headerJavaScript;

				if (!headerJavaScriptPaths.contains(headerJavaScriptPath)) {
					headerJavaScriptPaths.add(headerJavaScriptPath);
	%>

					<script src="<%= headerJavaScriptPath %>" type="text/javascript"></script>

	<%
				}
			}
		}
	}
	%>

</c:if>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.common.themes.top_head.jsp");
%>