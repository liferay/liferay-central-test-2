<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] objArray = (Object[])row.getObject();

Role role = (Role)objArray[0];
Resource resource = (Resource)objArray[1];
ResourceCode resourceCode = ResourceCodeLocalServiceUtil.getResourceCode(resource.getCodeId());

String resourceName = resourceCode.getName();
String resourceTitle = resourceName + ", " + resource.getPrimKey();
String modelString = resourceName;

try {
	modelString = ResourceLocalServiceUtil.getModel(resource).toHtmlString();

	String[] parts = StringUtil.split(resourceName, ".");
	resourceTitle = parts[parts.length - 1] + ", " + resource.getPrimKey();
}
catch (Exception e) {
	modelString = e.toString();
}
%>

<div style="vertical-align: top; overflow: auto; ">
	<liferay-ui:panel-container id='<%= "_resource_" + resource.getResourceId() %>' cssClass="model-details">
		<liferay-ui:panel id='<%= "_resource_panel_" + resource.getResourceId() %>' title="<%= resourceTitle %>" defaultState="closed">
			<div style="width: 350px; height: 100px; "><%= modelString %></div>
		</liferay-ui:panel>
	</liferay-ui:panel-container>
</div>