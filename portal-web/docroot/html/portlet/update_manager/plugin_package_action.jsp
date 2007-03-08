<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/admin/init.jsp" %>

<%
ResultRow row = (ResultRow) request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] rowObj = (Object[]) row.getObject();
PluginPackage pluginPackage = (PluginPackage) rowObj[0];
PluginPackage availablePluginPackage = (PluginPackage) rowObj[1];
String pluginPackageStatus = (String) rowObj[2];
String downloadProgressId = (String) rowObj[3];
String redirect = (String) rowObj[4];

String downloadURL = (availablePluginPackage == null)?"":availablePluginPackage.getDownloadURL();
%>

<c:if test='<%= pluginPackageStatus.equals("update-available") %>'>
	<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="updateURL">
		<portlet:param name="struts_action" value="/update_manager/edit_server" />
		<portlet:param name="redirect" value="<%= redirect %>" />
		<portlet:param name="<%= Constants.CMD %>" value="remoteDeploy" />
		<portlet:param name="url" value="<%= downloadURL %>" />
		<portlet:param name="deploymentContext" value="<%= pluginPackage.getContext() %>" />
		<portlet:param name="<%= Constants.PROGRESS_ID %>" value="<%= downloadProgressId %>" />
	</portlet:actionURL>

	<liferay-ui:icon image="download" url='<%= "javascript: " + downloadProgressId + ".startProgress(); self.location='" + updateURL + "'"%>' message="update"/>
</c:if>