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

<%@ include file="/html/taglib/init.jsp" %>

<%
IntegerWrapper iconListIconCount = (IntegerWrapper)request.getAttribute("liferay-ui:icon-list:icon-count");

if (iconListIconCount != null) {
	iconListIconCount.increment();
}

boolean iconListShowWhenSingleIcon = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:icon-list:showWhenSingleIcon"));

Boolean iconListSingleIcon = (Boolean)request.getAttribute("liferay-ui:icon-list:single-icon");

IntegerWrapper iconMenuIconCount = (IntegerWrapper)request.getAttribute("liferay-ui:icon-menu:icon-count");

if (iconMenuIconCount != null) {
	iconMenuIconCount.increment();
}

Boolean iconMenuSingleIcon = (Boolean)request.getAttribute("liferay-ui:icon-menu:single-icon");

boolean iconMenuShowWhenSingleIcon = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:icon-menu:showWhenSingleIcon"));

String image = (String)request.getAttribute("liferay-ui:icon:image");
String imageHover = (String)request.getAttribute("liferay-ui:icon:imageHover");

boolean auiImage = (image != null) && image.startsWith(_AUI_PATH);

String message = (String)request.getAttribute("liferay-ui:icon:message");

if (message == null) {
	message = StringUtil.replace(image, StringPool.UNDERLINE, StringPool.DASH);
}

String src = (String)request.getAttribute("liferay-ui:icon:src");
String srcHover = (String)request.getAttribute("liferay-ui:icon:srcHover");

if (Validator.isNull(src)) {
	if (auiImage) {
		src = themeDisplay.getPathThemeImages() + "/spacer.png";
	}
	else {
		src = themeDisplay.getPathThemeImages() + "/common/" + image + ".png";
	}
}

if (Validator.isNull(srcHover) && Validator.isNotNull(imageHover)) {
	srcHover = themeDisplay.getPathThemeImages() + "/common/" + imageHover + ".png";
}

String url = GetterUtil.getString((String)request.getAttribute("liferay-ui:icon:url"));

url = HtmlUtil.escape(url);

String method = (String)request.getAttribute("liferay-ui:icon:method");

if (Validator.isNull(method)) {
	method = "post";
}

String target = GetterUtil.getString((String)request.getAttribute("liferay-ui:icon:target"));
boolean label = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:icon:label"));
String lang = GetterUtil.getString((String)request.getAttribute("liferay-ui:icon:lang"));
boolean toolTip = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:icon:toolTip"));
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:icon:cssClass"));

if ((iconListIconCount != null) || (iconListSingleIcon != null)) {
	label = true;
}

if ((iconMenuIconCount != null) || (iconMenuSingleIcon != null)) {
	label = true;
}
String details = null;

if (label) {
	details = " alt=\"\"";
}
else {
	details = " alt=\"" + LanguageUtil.get(pageContext, message) + "\"";

	if (toolTip) {
		details += " onmouseover=\"Liferay.Portal.ToolTip.show(this, '" + UnicodeLanguageUtil.get(pageContext, message) + "')\"";
	}
	else {
		details += " title=\"" + LanguageUtil.get(pageContext, message) + "\"";
	}
}
%>

<%!
private static String _AUI_PATH = "../aui/";
%>