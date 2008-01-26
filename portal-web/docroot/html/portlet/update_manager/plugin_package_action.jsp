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

<%@ include file="/html/portlet/update_manager/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] rowObj = (Object[])row.getObject();

PluginPackage pluginPackage = (PluginPackage)rowObj[0];
PluginPackage availablePluginPackage = (PluginPackage)rowObj[1];
String pluginPackageStatus = (String)rowObj[2];
String uploadProgressId = (String)rowObj[3];
String redirect = (String)rowObj[4];

String downloadURL = StringPool.BLANK;

if (availablePluginPackage != null) {
	downloadURL = availablePluginPackage.getDownloadURL();
}
%>

<c:if test='<%= pluginPackageStatus.equals("update-available") || pluginPackageStatus.equals("update-ignored") %>'>
	<input type="button" value="<liferay-ui:message key="update" />" onClick="<%= uploadProgressId %>.startProgress(); <portlet:namespace/>remoteDeploy('<%= downloadURL %>', '<%= pluginPackage.getContext() %>', '<%= uploadProgressId %>');" />

	<c:choose>
		<c:when test="<%= !PluginPackageUtil.isIgnored(pluginPackage) %>">
			<input type="button" value="<liferay-ui:message key="ignore" />" onClick="<portlet:namespace/>ignorePackages('<%= pluginPackage.getPackageId() %>');" />
		</c:when>
		<c:otherwise>
			<input type="button" value="<liferay-ui:message key="unignore" />" onClick="<portlet:namespace/>unignorePackages('<%= pluginPackage.getPackageId() %>');" />
		</c:otherwise>
	</c:choose>

	<br />

	<c:choose>
		<c:when test="<%= PluginPackageUtil.isTrusted(availablePluginPackage.getRepositoryURL()) %>">
			<liferay-ui:message key="trusted" />
		</c:when>
		<c:otherwise>
			<liferay-ui:message key="untrusted" />
		</c:otherwise>
	</c:choose>
</c:if>