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
String tabs2 = ParamUtil.getString(request, "tabs2", "any-website");

String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

String portletResource = ParamUtil.getString(request, "portletResource");

Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);

PortletPreferences prefs = PortletPreferencesFactoryUtil.getPortletSetup(layout, portletResource);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/portlet_configuration/edit_sharing");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
portletURL.setParameter("portletResource", portletResource);

String widgetURL = PortalUtil.getWidgetURL(portlet, themeDisplay);
%>

<liferay-util:include page="/html/portlet/portlet_configuration/tabs1.jsp">
	<liferay-util:param name="tabs1" value="sharing" />
</liferay-util:include>

<liferay-ui:tabs
	names="any-website,facebook"
	param="tabs2"
	url="<%= portletURL.toString() %>"
/>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/portlet_configuration/edit_sharing" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SAVE %>" /></portlet:actionURL>" class="uni-form" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= HtmlUtil.escape(tabs2) %>" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(currentURL) %>" />
<input name="<portlet:namespace />returnToFullPageURL" type="hidden" value="<%= HtmlUtil.escape(returnToFullPageURL) %>" />
<input name="<portlet:namespace />portletResource" type="hidden" value="<%= HtmlUtil.escape(portletResource) %>">

<c:choose>
	<c:when test='<%= tabs2.equals("any-website") %>'>

		<%
		boolean widgetShowAddAppLink = PrefsParamUtil.getBoolean(prefs, request, "lfr-widget-show-add-app-link");
		%>

		<div class="portlet-msg-info">
			<liferay-ui:message key="share-this-application-on-any-website" />
		</div>

		<textarea class="lfr-textarea" onClick="Liferay.Util.selectAndCopy(this);">&lt;script src=&quot;<%= themeDisplay.getPortalURL() %><%= themeDisplay.getPathContext() %>/html/js/liferay/widget.js&quot; type=&quot;text/javascript&quot;&gt;&lt;/script&gt;
&lt;script type=&quot;text/javascript&quot;&gt;
Liferay.Widget({ url: &#x27;<%= widgetURL %>&#x27;});
&lt;/script&gt;</textarea>

		<br /><br />

		<div>
			<%= LanguageUtil.format(pageContext, "allow-users-to-add-x-to-any-website", portletDisplay.getTitle()) %> <liferay-ui:input-checkbox param="widgetShowAddAppLink" defaultValue="<%= widgetShowAddAppLink %>" />
		</div>
	</c:when>
	<c:when test='<%= tabs2.equals("facebook") %>'>

		<%
		String facebookAPIKey = PrefsParamUtil.getString(prefs, request, "lfr-facebook-api-key");
		String facebookCanvasPageURL = PrefsParamUtil.getString(prefs, request, "lfr-facebook-canvas-page-url");
		boolean facebookShowAddAppLink = PrefsParamUtil.getBoolean(prefs, request, "lfr-facebook-show-add-app-link");

		String callbackURL = widgetURL;

		if (portlet.getFacebookIntegration().equals(PortletConstants.FACEBOOK_INTEGRATION_FBML)) {
			PortletURL fbmlPortletURL = new PortletURLImpl(request, portletResource, plid, PortletRequest.RENDER_PHASE);

			fbmlPortletURL.setWindowState(WindowState.MAXIMIZED);

			fbmlPortletURL.setParameter("struts_action", "/message_boards/view");

			callbackURL = FacebookUtil.getCallbackURL(fbmlPortletURL.toString(), facebookCanvasPageURL);
		}
		%>

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
				<liferay-ui:message key="copy-the-callback-url-and-specify-it-in-facebook" />

				<c:choose>
					<c:when test="<%= portlet.getFacebookIntegration().equals(PortletConstants.FACEBOOK_INTEGRATION_FBML) %>">
						<liferay-ui:message key="this-application-is-exposed-to-facebook-via-fbml" />
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="this-application-is-exposed-to-facebook-via-an-iframe" />
					</c:otherwise>
				</c:choose>
			</div>

			<table class="lfr-table">
			<tr>
				<td>
					<liferay-ui:message key="callback-url" />
				</td>
				<td>
					<liferay-ui:input-resource url="<%= callbackURL %>" />
				</td>
			</tr>
			</table>

			<br />

			<div>
				<%= LanguageUtil.format(pageContext, "allow-users-to-add-x-to-facebook", portletDisplay.getTitle()) %> <liferay-ui:input-checkbox param="facebookShowAddAppLink" defaultValue="<%= facebookShowAddAppLink %>" />
			</div>
		</c:if>
	</c:when>
</c:choose>

<br />

<div class="button-holder">
	<input type="submit" value="<liferay-ui:message key="save" />" />

	<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />
</div>

</form>