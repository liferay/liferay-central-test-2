<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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

StringBundler sb = new StringBundler();

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