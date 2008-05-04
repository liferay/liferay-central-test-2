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

PortletPreferences prefs = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);

String facebookAppName = PrefsParamUtil.getString(prefs, request, "lfr-facebook-app-name");
boolean facebookShowAddAppLink = PrefsParamUtil.getBoolean(prefs, request, "lfr-facebook-show-add-app-link");
String facebookAPIKey = PrefsParamUtil.getString(prefs, request, "lfr-facebook-api-key");

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

<c:if test="<%= Validator.isNull(facebookAppName) %>">
	<div class="portlet-msg-alert">
		<a href="http://www.facebook.com/developers/editapp.php?new" target="_blank"><%= LanguageUtil.format(pageContext, "register-x-as-an-application-in-facebook", portletDisplay.getTitle()) %></a>
	</div>
</c:if>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/portlet_configuration/edit_facebook" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SAVE %>" /></portlet:actionURL>" class="uni-form" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(redirect) %>" />
<input name="<portlet:namespace />portletResource" type="hidden" value="<%= HtmlUtil.escape(portletResource) %>">

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="application-name" />
	</td>
	<td>
		<input class="lfr-input-text" id="<portlet:namespace />facebookAppName" name="<portlet:namespace />facebookAppName" type="text" value="<%= HtmlUtil.toInputSafe(facebookAppName) %>" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="api-key" />
	</td>
	<td>
		<input class="lfr-input-text" id="<portlet:namespace />facebookAPIKey" name="<portlet:namespace />facebookAPIKey" type="text" value="<%= HtmlUtil.toInputSafe(facebookAPIKey) %>" />
	</td>
</tr>

<c:if test="<%= Validator.isNotNull(facebookAppName) %>">
	<tr>
		<td>
			<liferay-ui:message key="fbml-callback-url" />
		</td>
		<td>
			<liferay-ui:input-resource url="<%= FacebookUtil.getCallbackURL(fbmlPortletURL.toString(), facebookAppName) %>" />
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
</c:if>

</table>

<c:if test="<%= Validator.isNotNull(facebookAppName) %>">
	<br />

	<div>
		<%= LanguageUtil.format(pageContext, "show-link-to-add-x-to-facebook", portletDisplay.getTitle()) %> <liferay-ui:input-checkbox param="facebookShowAddAppLink" defaultValue="<%= facebookShowAddAppLink %>" />
	</div>
</c:if>

<br />

<div class="button-holder">
	<input type="submit" value="<liferay-ui:message key="save" />" />

	<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />
</div>

</form>