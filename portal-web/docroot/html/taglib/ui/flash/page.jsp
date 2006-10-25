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

<%@ include file="/html/taglib/init.jsp" %>

<%
String align = (String)request.getAttribute("liferay-ui:flash:align");
String allowScriptAccess = (String)request.getAttribute("liferay-ui:flash:allowScriptAccess");
String base = (String)request.getAttribute("liferay-ui:flash:base");
String bgcolor = (String)request.getAttribute("liferay-ui:flash:bgcolor");
String devicefont = (String)request.getAttribute("liferay-ui:flash:devicefont");
String flashvars = (String)request.getAttribute("liferay-ui:flash:flashvars");
String height = (String)request.getAttribute("liferay-ui:flash:height");
String loop = (String)request.getAttribute("liferay-ui:flash:loop");
String menu = (String)request.getAttribute("liferay-ui:flash:menu");
String movie = (String)request.getAttribute("liferay-ui:flash:movie");
String play = (String)request.getAttribute("liferay-ui:flash:play");
String quality = (String)request.getAttribute("liferay-ui:flash:quality");
String salign = (String)request.getAttribute("liferay-ui:flash:salign");
String scale = (String)request.getAttribute("liferay-ui:flash:scale");
String swliveconnect = (String)request.getAttribute("liferay-ui:flash:swliveconnect");
String width = (String)request.getAttribute("liferay-ui:flash:width");
String wmode = (String)request.getAttribute("liferay-ui:flash:wmode");
%>

<object align="<%= align %>" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0" height="<%= height %>" width="<%= width %>">
	<param name="allowScriptAccess" value="<%= allowScriptAccess %>" />
	<param name="base" value="<%= base %>" />
	<param name="bgcolor" value="<%= bgcolor %>" />
	<param name="devicefont" value="<%= devicefont %>" />
	<param name="flashvars" value="<%= flashvars %>" />
	<param name="loop" value="<%= loop %>" />
	<param name="menu" value="<%= menu %>" />
	<param name="movie" value="<%= movie %>" />
	<param name="play" value="<%= play %>" />
	<param name="quality" value="<%= quality %>" />
	<param name="salign" value="<%= salign %>" />
	<param name="scale" value="<%= scale %>" />
	<param name="swliveconnect" value="<%= swliveconnect %>" />
	<param name="wmode" value="<%= wmode %>" />

	<embed
		align="<%= align %>"
		allowScriptAccess="<%= allowScriptAccess %>"
		base="<%= base %>"
		bgcolor="<%= bgcolor %>"
		devicefont="<%= devicefont %>"
		flashvars="<%= flashvars %>"
		height="<%= height %>"
		loop="<%= loop %>"
		menu="<%= menu %>"
		play="<%= play %>"
		pluginspage="http://www.macromedia.com/go/getflashplayer"
		quality="<%= quality %>"
		salign="<%= salign %>"
		scale="<%= scale %>"
		src="<%= movie %>"
		swliveconnect="<%= swliveconnect %>"
		type="application/x-shockwave-flash"
		width="<%= width %>"
		wmode="<%= wmode %>">
	</embed>
</object>