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
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_flash_page") + StringPool.UNDERLINE;

String align = (String)request.getAttribute("liferay-ui:flash:align");
String allowScriptAccess = (String)request.getAttribute("liferay-ui:flash:allowScriptAccess");
String base = (String)request.getAttribute("liferay-ui:flash:base");
String bgcolor = (String)request.getAttribute("liferay-ui:flash:bgcolor");
String devicefont = (String)request.getAttribute("liferay-ui:flash:devicefont");
String flashvars = (String)request.getAttribute("liferay-ui:flash:flashvars");
String height = (String)request.getAttribute("liferay-ui:flash:height");
String id = (String)request.getAttribute("liferay-ui:flash:id");
String loop = (String)request.getAttribute("liferay-ui:flash:loop");
String menu = (String)request.getAttribute("liferay-ui:flash:menu");
String movie = (String)request.getAttribute("liferay-ui:flash:movie");
String play = (String)request.getAttribute("liferay-ui:flash:play");
String quality = (String)request.getAttribute("liferay-ui:flash:quality");
String salign = (String)request.getAttribute("liferay-ui:flash:salign");
String scale = (String)request.getAttribute("liferay-ui:flash:scale");
String swliveconnect = (String)request.getAttribute("liferay-ui:flash:swliveconnect");
String version = (String)request.getAttribute("liferay-ui:flash:version");
String width = (String)request.getAttribute("liferay-ui:flash:width");
String wmode = (String)request.getAttribute("liferay-ui:flash:wmode");
%>

<div id="<%= randomNamespace %>flashcontent" style="height: <%= height %>; width: <%= width %>;"></div>

<aui:script>
	var <%= randomNamespace %>swfObj = new SWFObject("<%= movie %>", "<%= id %>", "<%= width %>", "<%= height %>", "<%= version %>", "<%= bgcolor %>");

	<%= randomNamespace %>swfObj.addParam("allowScriptAccess", "<%= allowScriptAccess %>");
	<%= randomNamespace %>swfObj.addParam("base", "<%= base %>");
	<%= randomNamespace %>swfObj.addParam("devicefont", "<%= devicefont %>");
	<%= randomNamespace %>swfObj.addParam("flashvars", "<%= flashvars %>");
	<%= randomNamespace %>swfObj.addParam("loop", "<%= loop %>");
	<%= randomNamespace %>swfObj.addParam("menu", "<%= menu %>");
	<%= randomNamespace %>swfObj.addParam("play", "<%= play %>");
	<%= randomNamespace %>swfObj.addParam("quality", "<%= quality %>");
	<%= randomNamespace %>swfObj.addParam("salign", "<%= salign %>");
	<%= randomNamespace %>swfObj.addParam("scale", "<%= scale %>");
	<%= randomNamespace %>swfObj.addParam("swliveconnect", "<%= swliveconnect %>");
	<%= randomNamespace %>swfObj.addParam("wmode", "<%= wmode %>");

	<%= randomNamespace %>swfObj.write("<%= randomNamespace %>flashcontent");
</aui:script>