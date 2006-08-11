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

<%@ page import="com.liferay.client.portal.model.OrganizationModel" %>
<%@ page import="com.liferay.client.portal.service.http.OrganizationServiceSoap" %>
<%@ page import="com.liferay.client.portal.service.http.OrganizationServiceSoapServiceLocator" %>

<%@ page import="java.net.URL" %>

You belong to the following organizations:

<br><br>

<%
OrganizationServiceSoapServiceLocator locator = new OrganizationServiceSoapServiceLocator();

OrganizationServiceSoap soap = locator.getPortal_OrganizationService(_getURL("Portal_OrganizationService"));

OrganizationModel[] organizations = soap.getUserOrganizations(request.getRemoteUser());

for (int i = 0; i < organizations.length; i++) {
	OrganizationModel organization = organizations[i];
%>

	<%= organization.getName() %><br>

<%
}
%>

<%!
private URL _getURL(String serviceName) throws Exception {

	// Unathenticated url

	String url = "http://localhost:8080/tunnel-web/axis/" + serviceName;

	// Authenticated url

	if (true) {
		String userId = "liferay.com.1";
		String password = "qUqP5cyxm6YcTAhz05Hph5gvu9M=";

		url = "http://" + userId + ":" + password + "@localhost:8080/tunnel-web/secure/axis/" + serviceName;
	}

	return new URL(url);
}
%>