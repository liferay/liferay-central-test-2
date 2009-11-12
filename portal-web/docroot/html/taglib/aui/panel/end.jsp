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

<%@ include file="/html/taglib/init.jsp" %>
<%@ page import="com.liferay.taglib.aui.ToolTag" %>

<%
boolean collapsible = GetterUtil.getBoolean((String)request.getAttribute("aui:panel:collapsible"));
String label = GetterUtil.getString((String)request.getAttribute("aui:panel:label"));
String id = namespace + GetterUtil.getString((String)request.getAttribute("aui:panel:id"));
List<ToolTag> tools = (List<ToolTag>)request.getAttribute("aui:panel:tools");
%>

</div>

<script type="text/javascript" charset="utf-8">
	AUI().ready(
		'panel',
		'anim',
		function(A) {
			var container = new A.Panel(
				{
					collapsible: <%= collapsible %>,
					contentBox: '#<%= id %>',
					headerContent: '<%= label %>',
					bodyContent: A.one('#<%= id %>bodyContent')

					<c:if test="<%= tools != null %>">
						,tools: [

						<%
						for (int i = 0; i < tools.size(); i++) {
							ToolTag tool = tools.get(i);
						%>

							{
								icon: '<%= tool.getIcon() %>',
								id: '<%= tool.getId() %>',
								handler: function(event, panel) {
									<%= tool.getHandler() %>
								}

							}

							<c:if test="<%= i != (tools.size() - 1) %>">
								,
							</c:if>

						<%
						}
						%>

						]
					</c:if>

				}
			).render();

		}
	);
</script>