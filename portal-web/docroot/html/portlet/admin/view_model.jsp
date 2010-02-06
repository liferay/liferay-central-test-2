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

<%@ include file="/html/portlet/admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

ResourcePermission resourcePermission = (ResourcePermission)row.getObject();

String modelString = StringPool.BLANK;
String resourceTitle = resourcePermission.getName();

try {
	StringBuilder sb = new StringBuilder();

	sb.append("<table class=\"lfr-table\">\n");

	BaseModel model = PortalUtil.getBaseModel(resourcePermission);

	Document doc = SAXReaderUtil.read(new UnsyncStringReader(model.toXmlString()));

	Element root = doc.getRootElement();

	Iterator<Element> itr = root.elements("column").iterator();

	while (itr.hasNext()) {
		Element column = itr.next();

		String name = column.elementText("column-name");
		String value = column.elementText("column-value");

		sb.append("<tr><td align=\"right\" valign=\"top\"><strong>");
		sb.append(name);
		sb.append("</strong></td><td>");
		sb.append(value);
		sb.append("</td></tr>");
	}

	sb.append("</table>");

	modelString = sb.toString();

	String[] parts = StringUtil.split(resourcePermission.getName(), StringPool.PERIOD);

	resourceTitle = parts[parts.length - 1] + ", " + resourcePermission.getPrimKey();
}
catch (Exception e) {
	modelString = e.toString();
}
%>

<div style="overflow: auto; vertical-align: top;">
	<liferay-ui:panel-container cssClass="model-details" id='<%= renderResponse.getNamespace() + "resource" + resourcePermission.getResourcePermissionId() %>'>
		<liferay-ui:panel defaultState="closed" id='<%= renderResponse.getNamespace() + "resourcePanel" + resourcePermission.getResourcePermissionId() %>' title="<%= resourceTitle %>">
			<div style="height: 100px; width: 350px;"><%= modelString %></div>
		</liferay-ui:panel>
	</liferay-ui:panel-container>
</div>