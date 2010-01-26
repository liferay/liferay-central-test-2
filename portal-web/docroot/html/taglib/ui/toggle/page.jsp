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

<%
String id = (String)request.getAttribute("liferay-ui:toggle:id");
String showImage = (String)request.getAttribute("liferay-ui:toggle:showImage");
String hideImage = (String)request.getAttribute("liferay-ui:toggle:hideImage");
String showMessage = (String)request.getAttribute("liferay-ui:toggle:showMessage");
String hideMessage = (String)request.getAttribute("liferay-ui:toggle:hideMessage");
String stateVar = (String)request.getAttribute("liferay-ui:toggle:stateVar");
String defaultStateValue = (String)request.getAttribute("liferay-ui:toggle:defaultStateValue");
String defaultImage = (String)request.getAttribute("liferay-ui:toggle:defaultImage");
String defaultMessage = (String)request.getAttribute("liferay-ui:toggle:defaultMessage");
%>

<c:choose>
	<c:when test="<%= Validator.isNotNull(showMessage) %>">
		<a href="javascript:<%= stateVar %>Toggle();" id="<%= id %>_message"><%= defaultMessage %></a>
	</c:when>
	<c:otherwise>
		<img
			alt="<liferay-ui:message key="toggle" />"
			id="<%= id %>_image"
			onclick="<%= stateVar %>Toggle();"
			src="<%= defaultImage %>"
			style="margin: 0px;"
		/>
	</c:otherwise>
</c:choose>

<aui:script>
	var <%= stateVar %> = "<%= defaultStateValue %>";

	function <%= stateVar %>Toggle(state, saveState) {
		if (state == null) {
			state = <%= stateVar %>;
		}

		if (state == "") {
			<%= stateVar %> = "none";

			document.getElementById("<%= id %>").style.display = "none";

			<c:choose>
				<c:when test="<%= Validator.isNotNull(showMessage) %>">
					document.getElementById("<%= id %>_message").innerHTML = "<%= showMessage %>";
				</c:when>
				<c:otherwise>
					document.getElementById("<%= id %>_image").src = "<%= showImage %>";
				</c:otherwise>
			</c:choose>

			if ((saveState == null) || saveState) {
				AUI().io(
					themeDisplay.getPathMain() + '/portal/session_click',
					{
						data: {
							'<%= id %>': 'none'
						}
					}
				);
			}
		}
		else {
			<%= stateVar %> = "";

			document.getElementById("<%= id %>").style.display = "";

			<c:choose>
				<c:when test="<%= Validator.isNotNull(showMessage) %>">
					document.getElementById("<%= id %>_message").innerHTML = "<%= hideMessage %>";
				</c:when>
				<c:otherwise>
					document.getElementById("<%= id %>_image").src = "<%= hideImage %>";
				</c:otherwise>
			</c:choose>

			if ((saveState == null) || saveState) {
				AUI().io(
					themeDisplay.getPathMain() + '/portal/session_click',
					{
						data: {
							'<%= id %>': ''
						}
					}
				);
			}
		}
	}
</aui:script>