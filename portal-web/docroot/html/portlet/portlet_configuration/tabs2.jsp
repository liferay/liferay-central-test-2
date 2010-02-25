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

configurationURL.setParameter("struts_action", "/portlet_configuration/edit_configuration");
configurationURL.setParameter("redirect", redirect);
configurationURL.setParameter("returnToFullPageURL", returnToFullPageURL);
configurationURL.setParameter("portletResource", portletResource);

// Archived setups

PortletURL archivedSetupsURL = renderResponse.createRenderURL();

archivedSetupsURL.setParameter("struts_action", "/portlet_configuration/edit_archived_setups");
archivedSetupsURL.setParameter("redirect", redirect);
archivedSetupsURL.setParameter("returnToFullPageURL", returnToFullPageURL);
archivedSetupsURL.setParameter("portletResource", portletResource);
%>

<liferay-ui:tabs
	names="current,archived"
	param="tabs2"
	url0="<%= configurationURL.toString() %>"
	url1="<%= archivedSetupsURL.toString() %>"
/>