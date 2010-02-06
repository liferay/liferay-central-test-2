<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/init.jsp" %>

<%
PortletPreferences preferences = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

String initUrl = preferences.getValue("initUrl", StringPool.BLANK);
String scope = preferences.getValue("scope", StringPool.BLANK);
String proxyHost = preferences.getValue("proxyHost", StringPool.BLANK);
String proxyPort = preferences.getValue("proxyPort", StringPool.BLANK);
String proxyAuthentication = preferences.getValue("proxyAuthentication", StringPool.BLANK);
String proxyAuthenticationUsername = preferences.getValue("proxyAuthenticationUsername", StringPool.BLANK);
String proxyAuthenticationPassword = preferences.getValue("proxyAuthenticationPassword", StringPool.BLANK);
String proxyAuthenticationHost = preferences.getValue("proxyAuthenticationHost", StringPool.BLANK);
String proxyAuthenticationDomain = preferences.getValue("proxyAuthenticationDomain", StringPool.BLANK);
String stylesheet = preferences.getValue("stylesheet", StringPool.BLANK);
%>