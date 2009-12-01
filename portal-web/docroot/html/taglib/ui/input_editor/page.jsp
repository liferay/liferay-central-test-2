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
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:cssClass"));
String name = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:name"), namespace + "editor");

String editorImpl = (String)request.getAttribute("liferay-ui:input-editor:editorImpl");

if (Validator.isNull(editorImpl)) {
	editorImpl = PropsValues.EDITOR_WYSIWYG_DEFAULT;
}
else {
	editorImpl = PropsUtil.get(editorImpl);
}

String toolbarSet = (String)request.getAttribute("liferay-ui:input-editor:toolbarSet");
String initMethod = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:initMethod"), namespace + "initEditor");
String onChangeMethod = (String)request.getAttribute("liferay-ui:input-editor:onChangeMethod");
String height = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:height"), "400");
String width = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:width"), "640");

StringBuilder sb = new StringBuilder();

sb.append(themeDisplay.getPathContext());
sb.append("/html/js/editor/editor.jsp?p_l_id=");
sb.append(plid);
sb.append("&p_main_path=");
sb.append(HttpUtil.encodeURL(themeDisplay.getPathMain()));
sb.append("&doAsUserId=");

String doAsUserId = themeDisplay.getDoAsUserId();

if (Validator.isNull(doAsUserId)) {
	doAsUserId = Encryptor.encrypt(company.getKeyObj(), String.valueOf(themeDisplay.getUserId()));
}

sb.append(HttpUtil.encodeURL(doAsUserId));

sb.append("&editorImpl=");
sb.append(editorImpl);

if (Validator.isNotNull(toolbarSet)) {
	sb.append("&toolbarSet=");
	sb.append(toolbarSet);
}

if (Validator.isNotNull(initMethod)) {
	sb.append("&initMethod=");
	sb.append(initMethod);
}

if (Validator.isNotNull(onChangeMethod)) {
	sb.append("&onChangeMethod=");
	sb.append(onChangeMethod);
}

sb.append("&cssPath=");
sb.append(HttpUtil.encodeURL(themeDisplay.getPathThemeCss()));

String cssClasses = "portlet ";

Portlet portlet = (Portlet)request.getAttribute(WebKeys.RENDER_PORTLET);

if (portlet != null) {
	cssClasses += portlet.getCssClassWrapper();
}

sb.append("&cssClasses=");
sb.append(HttpUtil.encodeURL(cssClasses));

String editorURL = sb.toString();
%>

<iframe <%= Validator.isNotNull(cssClass) ? "class=\"" + cssClass + "\"" : StringPool.BLANK %> frameborder="0" height="<%= height %>" id="<%= name %>" name="<%= name %>" scrolling="no" src="<%= editorURL %>" width="<%= width %>"></iframe>