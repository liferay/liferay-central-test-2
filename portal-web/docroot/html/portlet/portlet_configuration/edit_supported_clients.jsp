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

PortletPreferences portletSetup = PortletPreferencesFactoryUtil.getLayoutPortletSetup(layout, portletResource);

Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);

Set allPortletModes = portlet.getAllPortletModes();
%>

<liferay-util:include page="/html/portlet/portlet_configuration/tabs1.jsp">
	<liferay-util:param name="tabs1" value="supported-clients" />
</liferay-util:include>

<portlet:actionURL var="editSupportedClientsURL">
	<portlet:param name="struts_action" value="/portlet_configuration/edit_supported_clients" />
</portlet:actionURL>

<aui:form action="<%= editSupportedClientsURL %>" method="post" name=">fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="returnToFullPageURL" type="hidden" value="<%= returnToFullPageURL %>" />
	<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />

	<%
	Iterator itr = allPortletModes.iterator();

	while (itr.hasNext()) {
		String curPortletMode = (String)itr.next();

		String mobileDevicesParam = "portlet-setup-supported-clients-mobile-devices-" + curPortletMode;
		boolean mobileDevicesDefault = portlet.hasPortletMode(ContentTypes.XHTML_MP, PortletModeFactory.getPortletMode(curPortletMode));

		boolean mobileDevices = GetterUtil.getBoolean(portletSetup.getValue(mobileDevicesParam, String.valueOf(mobileDevicesDefault)));
	%>

		<aui:fieldset label='<%= LanguageUtil.get(pageContext, "portlet-mode") + ": " + LanguageUtil.get(pageContext, curPortletMode) %>'>
			<aui:input inlineLabel="left" label="regular-browsers" name='<%= "regularBrowsersEnabled" + curPortletMode %>' type="checkbox" value="<%= true %>" disabled="<%= true %>" />

			<aui:input inlineLabel="left" label="mobile-devices" name="<%= mobileDevicesParam %>" type="checkbox" value="<%= mobileDevices %>" />
		</aui:fieldset>

	<%
	}
	%>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>