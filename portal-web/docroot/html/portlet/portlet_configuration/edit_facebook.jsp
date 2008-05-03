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

PortletURL portletURL = new PortletURLImpl(request, portletResource, plid, PortletRequest.RENDER_PHASE);
portletURL.setWindowState(WindowState.MAXIMIZED);
portletURL.setParameter("struts_action", "/message_boards/view");

PortletURL popupPortletURL = new PortletURLImpl(request, portletResource, plid, PortletRequest.RENDER_PHASE);
popupPortletURL.setWindowState(LiferayWindowState.POP_UP);
popupPortletURL.setParameter("struts_action", "/message_boards/view");
%>

<liferay-util:include page="/html/portlet/portlet_configuration/tabs1.jsp">
	<liferay-util:param name="tabs1" value="facebook" />
</liferay-util:include>

<c:choose>
	<c:when test="<%= Validator.isNull(facebookAppName) %>">
		<div class="portlet-msg-alert">
			<a href="http://www.facebook.com/developers/editapp.php?new" target="_blank"><liferay-ui:message key="register-this-portlet-as-an-app-in-facebook-and-fill-the-details-in-the-form-below"/></a>
		</div>
	</c:when>
	<c:otherwise>
		<div class="portlet-msg-info">
			<liferay-ui:message key="use-the-following-callback-urls-in-facebook-depending-on-the-type-of-integration-you-have-chosen"/>
			<ul>
				<li><b><liferay-ui:message key="fbml"/></b>: <%= _getCallbackURL(portletURL.toString(), facebookAppName) %></li>
				<li><b><liferay-ui:message key="iframe"/></b>: <%= popupPortletURL.toString() %></li>
			</ul>
		</div>

	</c:otherwise>
</c:choose>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/portlet_configuration/edit_facebook" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SAVE %>" /></portlet:actionURL>" class="uni-form" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(redirect) %>" />
<input name="<portlet:namespace />portletResource" type="hidden" value="<%= HtmlUtil.escape(portletResource) %>">

<fieldset class="block-labels">
	<legend><liferay-ui:message key="application-details" /></legend>

	<div class="ctrl-holder">
		<label for="<portlet:namespace/>facebookAppName"><liferay-ui:message key="application-name" /></label>

		<input class="lfr-input-text" id="<portlet:namespace />facebookAppName" name="<portlet:namespace />facebookAppName" type="text" value="<%= HtmlUtil.toInputSafe(facebookAppName) %>" />
	</div>

	<div class="ctrl-holder">
		<label for="<portlet:namespace />facebookAPIKey"><liferay-ui:message key="api-key" /></label>

		<input class="lfr-input-text" id="<portlet:namespace />facebookAPIKey" name="<portlet:namespace />facebookAPIKey" type="text" value="<%= HtmlUtil.toInputSafe(facebookAPIKey) %>" />
	</div>

</fieldset>

<fieldset class="block-labels">
	<legend><liferay-ui:message key="integration" /></legend>

	<div class="ctrl-holder">
		<label><liferay-ui:message key="show-link-to-add-app-to-facebook" /> <liferay-ui:input-checkbox param="facebookShowAddAppLink" defaultValue="<%= facebookShowAddAppLink %>" /></label>
	</div>

</fieldset>

<br />

<div class="button-holder">
	<input type="submit" value="<liferay-ui:message key="save" />" />

	<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />
</div>

</form>
<%!
	private String _getCallbackURL(String portletURL, String facebookAppName) {
		int pos = portletURL.indexOf('/', Http.HTTPS_WITH_SLASH.length());

		StringMaker callbackURL = new StringMaker();

		callbackURL.append(portletURL.substring(0, pos));
		callbackURL.append("/facebook/");
		callbackURL.append(facebookAppName);
		callbackURL.append(portletURL.substring(pos));

		String callbackURLString = callbackURL.toString();

		if (!callbackURLString.endsWith(StringPool.SLASH)) {
			callbackURLString += StringPool.SLASH;
		}

		return callbackURLString;
	}
%>