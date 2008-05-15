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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String portletResource = ParamUtil.getString(request, "portletResource");

PortletPreferences prefs = PortletPreferencesFactoryUtil.getPortletSetup(layout, portletResource);

String facebookAPIKey = PrefsParamUtil.getString(prefs, request, "lfr-facebook-api-key");
String facebookCanvasPageURL = PrefsParamUtil.getString(prefs, request, "lfr-facebook-canvas-page-url");
boolean facebookShowAddAppLink = PrefsParamUtil.getBoolean(prefs, request, "lfr-facebook-show-add-app-link");

PortletURL fbmlPortletURL = new PortletURLImpl(request, portletResource, plid, PortletRequest.RENDER_PHASE);

fbmlPortletURL.setWindowState(WindowState.MAXIMIZED);

fbmlPortletURL.setParameter("struts_action", "/message_boards/view");

PortletURL iframePortletURL = new PortletURLImpl(request, portletResource, plid, PortletRequest.RENDER_PHASE);

iframePortletURL.setWindowState(LiferayWindowState.POP_UP);

iframePortletURL.setParameter("struts_action", "/message_boards/view");
%>

<liferay-util:include page="/html/portlet/portlet_configuration/tabs1.jsp">
	<liferay-util:param name="tabs1" value="facebook" />
</liferay-util:include>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/portlet_configuration/edit_facebook" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SAVE %>" /></portlet:actionURL>" class="uni-form" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(redirect) %>" />
<input name="<portlet:namespace />portletResource" type="hidden" value="<%= HtmlUtil.escape(portletResource) %>">

<div class="portlet-msg-info">
	<a href="http://www.facebook.com/developers/editapp.php?new" target="_blank"><liferay-ui:message key="get-the-api-key-and-canvas-page-url-from-facebook" /></a>
</div>

<table class="lfr-table">
<tr class="facebook-api">
	<td>
		<liferay-ui:message key="api-key" />
	</td>
	<td class="api-input" colspan="2">
		<input class="lfr-input-text" id="<portlet:namespace />facebookAPIKey" name="<portlet:namespace />facebookAPIKey" type="text" value="<%= HtmlUtil.toInputSafe(facebookAPIKey) %>" />
	</td>
</tr>
<tr class="canvas-url">
	<td>
		<liferay-ui:message key="canvas-page-url" />
	</td>
	<td class="url-text">
		http://apps.facebook.com/
	</td>
	<td class="url-input">
		<input class="lfr-input-text flexible" id="<portlet:namespace />facebookCanvasPageURL" name="<portlet:namespace />facebookCanvasPageURL" type="text" value="<%= HtmlUtil.toInputSafe(facebookCanvasPageURL) %>" />/
	</td>
</tr>
</table>

<c:if test="<%= Validator.isNotNull(facebookCanvasPageURL) %>">
	<br />

	<div class="portlet-msg-info">
		<liferay-ui:message key="copy-one-of-the-callback-urls-to-facebook" />
	</div>

	<table class="lfr-table">
	<tr>
		<td>
			<liferay-ui:message key="fbml-callback-url" />
		</td>
		<td>
			<liferay-ui:input-resource url="<%= FacebookUtil.getCallbackURL(fbmlPortletURL.toString(), facebookCanvasPageURL) %>" />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="iframe-callback-url" />
		</td>
		<td>
			<liferay-ui:input-resource url="<%= iframePortletURL.toString() %>" />
		</td>
	</tr>
	</table>

	<br />

	<div>
		<%= LanguageUtil.format(pageContext, "allow-users-to-add-x-to-facebook", portletDisplay.getTitle()) %> <liferay-ui:input-checkbox param="facebookShowAddAppLink" defaultValue="<%= facebookShowAddAppLink %>" />
	</div>
</c:if>

<br />

<div class="button-holder">
	<input type="submit" value="<liferay-ui:message key="save" />" />

	<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />
</div>

</form>