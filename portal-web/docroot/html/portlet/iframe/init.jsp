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

<%@ page import="com.liferay.portlet.iframe.util.IFrameUtil" %>

<%
PortletPreferences preferences = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

String src = preferences.getValue("src", StringPool.BLANK);
boolean relative = GetterUtil.getBoolean(preferences.getValue("relative", StringPool.BLANK));

boolean auth = GetterUtil.getBoolean(preferences.getValue("auth", StringPool.BLANK));
String authType = preferences.getValue("auth-type", StringPool.BLANK);
String formMethod = preferences.getValue("form-method", StringPool.BLANK);
String userName = preferences.getValue("user-name", StringPool.BLANK);
String userNameField = preferences.getValue("user-name-field", StringPool.BLANK);
String password = preferences.getValue("password", StringPool.BLANK);
String passwordField = preferences.getValue("password-field", StringPool.BLANK);
String hiddenVariables = preferences.getValue("hidden-variables", StringPool.BLANK);

String alt = preferences.getValue("alt", StringPool.BLANK);
String border = preferences.getValue("border", "0");
String bordercolor = preferences.getValue("bordercolor", "#000000");
String frameborder = preferences.getValue("frameborder", "0");
String heightMaximized = preferences.getValue("height-maximized", "600");
String heightNormal = preferences.getValue("height-normal", "300");
String hspace = preferences.getValue("hspace", "0");
String longdesc = preferences.getValue("longdesc", StringPool.BLANK);
String scrolling = preferences.getValue("scrolling", "auto");
String vspace = preferences.getValue("vspace", "0");
String width = preferences.getValue("width", "100%");

List<String> iframeVariables = new ArrayList<String>();

Enumeration<String> enu = request.getParameterNames();

while (enu.hasMoreElements()) {
	String name = enu.nextElement();

	if (name.startsWith(_IFRAME_PREFIX)) {
		iframeVariables.add(name.substring(_IFRAME_PREFIX.length()).concat(StringPool.EQUAL).concat(request.getParameter(name)));
	}
}
%>

<%!
private static final String _IFRAME_PREFIX = "iframe_";
%>