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

ResourcePermission resourcePermission = (ResourcePermission)objArray[0];

String modelString = "";
String resourceTitle = resourcePermission.getName();

try {
	BaseModel model = PortalUtil.getModel(resourcePermission);

	StringBuilder sb = new StringBuilder();

	sb.append("<table class=\"lfr-table\">\n");

	try {
		SAXReader reader = new SAXReader();

		String xml = model.toXmlString();

		Document doc = reader.read(new StringReader(xml));

		Element root = doc.getRootElement();

		Iterator<Element> itr = root.elements("column").iterator();

		while (itr.hasNext()) {
			Element column = itr.next();

			String name = column.element("column-name").getTextTrim();
			String value = column.element("column-value").getTextTrim();

			sb.append("<tr>");
			sb.append("<td align=\"right\" valign=\"top\">");
			sb.append("<b>" + name + "</b>");
			sb.append("</td>");
			sb.append("<td>");
			sb.append(value);
			sb.append("</td>");
			sb.append("</tr>\n");
		}
	}
	catch (Exception e) {
	}

	sb.append("</table>");

	modelString = sb.toString();

	String[] parts = StringUtil.split(resourcePermission.getName(), ".");
	resourceTitle = parts[parts.length - 1] + ", " + resourcePermission.getPrimKey();
}
catch (Exception e) {
	modelString = e.toString();
}
%>

<div style="vertical-align: top; overflow: auto; ">
	<liferay-ui:panel-container id='<%= "_resource_" + resourcePermission.getResourcePermissionId() %>' cssClass="model-details">
		<liferay-ui:panel id='<%= "_resource_panel_" + resourcePermission.getResourcePermissionId() %>' title="<%= resourceTitle %>" defaultState="closed">
			<div style="width: 350px; height: 100px; "><%= modelString %></div>
		</liferay-ui:panel>
	</liferay-ui:panel-container>
</div>