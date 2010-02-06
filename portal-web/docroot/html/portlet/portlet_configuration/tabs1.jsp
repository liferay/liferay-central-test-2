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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

String portletResource = ParamUtil.getString(request, "portletResource");

Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);

// Configuration

PortletURL configurationURL = renderResponse.createRenderURL();

configurationURL.setWindowState(WindowState.MAXIMIZED);

configurationURL.setParameter("struts_action", "/portlet_configuration/edit_configuration");
configurationURL.setParameter("redirect", redirect);
configurationURL.setParameter("returnToFullPageURL", returnToFullPageURL);
configurationURL.setParameter("portletResource", portletResource);

// Supported clients

PortletURL supportedClientsURL = renderResponse.createRenderURL();

supportedClientsURL.setWindowState(WindowState.MAXIMIZED);

supportedClientsURL.setParameter("struts_action", "/portlet_configuration/edit_supported_clients");
supportedClientsURL.setParameter("redirect", redirect);
supportedClientsURL.setParameter("returnToFullPageURL", returnToFullPageURL);
supportedClientsURL.setParameter("portletResource", portletResource);

// Permissions

PortletURL permissionsURL = renderResponse.createRenderURL();

permissionsURL.setWindowState(WindowState.MAXIMIZED);

permissionsURL.setParameter("struts_action", "/portlet_configuration/edit_permissions");
permissionsURL.setParameter("redirect", redirect);
permissionsURL.setParameter("returnToFullPageURL", returnToFullPageURL);
permissionsURL.setParameter("portletResource", portletResource);
permissionsURL.setParameter("resourcePrimKey", PortletPermissionUtil.getPrimaryKey(layout.getPlid(), portletResource));

// Public render parameters

PortletURL publicRenderParametersURL = renderResponse.createRenderURL();

publicRenderParametersURL.setWindowState(WindowState.MAXIMIZED);

publicRenderParametersURL.setParameter("struts_action", "/portlet_configuration/edit_public_render_parameters");
publicRenderParametersURL.setParameter("redirect", redirect);
publicRenderParametersURL.setParameter("returnToFullPageURL", returnToFullPageURL);
publicRenderParametersURL.setParameter("portletResource", portletResource);

// Sharing

PortletURL sharingURL = renderResponse.createRenderURL();

sharingURL.setWindowState(WindowState.MAXIMIZED);

sharingURL.setParameter("struts_action", "/portlet_configuration/edit_sharing");
sharingURL.setParameter("redirect", redirect);
sharingURL.setParameter("returnToFullPageURL", returnToFullPageURL);
sharingURL.setParameter("portletResource", portletResource);

// Scope

PortletURL scopeURL = renderResponse.createRenderURL();

scopeURL.setWindowState(WindowState.MAXIMIZED);

scopeURL.setParameter("struts_action", "/portlet_configuration/edit_scope");
scopeURL.setParameter("redirect", redirect);
scopeURL.setParameter("returnToFullPageURL", returnToFullPageURL);
scopeURL.setParameter("portletResource", portletResource);

int pos = 0;

String tabs1Names = StringPool.BLANK;

if (Validator.isNotNull(portlet.getConfigurationActionClass())) {
	tabs1Names += ",setup";

	request.setAttribute("liferay-ui:tabs:url" + pos++, configurationURL.toString());
}

if (portlet.hasMultipleMimeTypes()) {
	tabs1Names += ",supported-clients";

	request.setAttribute("liferay-ui:tabs:url" + pos++, supportedClientsURL.toString());
}

tabs1Names += ",permissions";

request.setAttribute("liferay-ui:tabs:url" + pos++, permissionsURL.toString());

if (!portlet.getPublicRenderParameters().isEmpty()) {
	tabs1Names += ",communication";

	request.setAttribute("liferay-ui:tabs:url" + pos++, publicRenderParametersURL.toString());
}

tabs1Names += ",sharing";

request.setAttribute("liferay-ui:tabs:url" + pos++, sharingURL.toString());

if (portlet.isScopeable()) {
	tabs1Names += ",scope";

	request.setAttribute("liferay-ui:tabs:url" + pos++, scopeURL.toString());
}

if (tabs1Names.startsWith(",")) {
	tabs1Names = tabs1Names.substring(1);
}

String tabs1 = ParamUtil.getString(request, "tabs1");

PortalUtil.addPortletBreadcrumbEntry(request, ((RenderResponseImpl)renderResponse).getTitle(), null);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "configuration"), null);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, tabs1), currentURL);
%>

<liferay-ui:tabs names="<%= tabs1Names %>" />