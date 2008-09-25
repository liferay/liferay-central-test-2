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

<%@ include file="/html/portlet/wsrp_consumer_admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

ConfiguredProducerElementBean configuredProducerBean = (ConfiguredProducerElementBean)row.getObject();
%>

<liferay-ui:icon-menu>
	<portlet:actionURL var="editURL">
		<portlet:param name="<%= Constants.ACTION %>" value="<%= String.valueOf(AdminPortletAction.GET_DETAILS) %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="configuredProducerName" value="<%= configuredProducerBean.getName() %>" />
		<portlet:param name="configuredProducerId" value="<%= configuredProducerBean.getId() %>" />
	</portlet:actionURL>

	<liferay-ui:icon image="edit" url="<%= editURL %>" />

	<portlet:actionURL var="installPortletURL">
		<portlet:param name="<%= Constants.ACTION %>" value="<%= String.valueOf(AdminPortletAction.GET_INFO_FOR_CHANNEL) %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="configuredProducerName" value="<%= configuredProducerBean.getName() %>" />
		<portlet:param name="configuredProducerId" value="<%= configuredProducerBean.getId() %>" />
	</portlet:actionURL>

	<liferay-ui:icon image="add" message="install-portlet" url="<%= installPortletURL %>" />
	
	<portlet:actionURL var="updateSDUrl">
		<portlet:param name="<%= Constants.ACTION %>" value="<%= String.valueOf(AdminPortletAction.UPDATE_SD) %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="configuredProducerName" value="<%= configuredProducerBean.getName() %>" />
		<portlet:param name="configuredProducerId" value="<%= configuredProducerBean.getId() %>" />
	</portlet:actionURL>

	<liferay-ui:icon image="view" message="update-service-description" url="<%= updateSDUrl %>" />

	<portlet:actionURL var="deleteURL">
		<portlet:param name="<%= Constants.ACTION %>" value="<%= String.valueOf(AdminPortletAction.DELETE) %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="selectedConfiguredProducers" value="<%= configuredProducerBean.getId() %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete url="<%= deleteURL %>" />
</liferay-ui:icon-menu>
