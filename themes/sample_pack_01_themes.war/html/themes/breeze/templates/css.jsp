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

<%@ include file="init.jsp" %>

#layout-content-container {
	<c:choose>
		<c:when test="<%= themeDisplay.isStatePopUp() %>">
			height: 100%; width: 100%;
		</c:when>
		<c:otherwise>
			width: <%= Integer.toString(themeDisplay.getResTotal()) %>;
		</c:otherwise>
	</c:choose>
}

#layout-column_n1 {
	width: <%= themeDisplay.getResNarrow() %>px;
}

#layout-column_n2 {
	width: <%= themeDisplay.getResNarrow() %>px;
}

#layout-column_w1 {
	width: <%= themeDisplay.getResWide() %>px;
}

.layout-blank_n1_portlet {
	width: <%= themeDisplay.getResNarrow() %>px;
}

.layout-blank_n2_portlet {
	width: <%= themeDisplay.getResNarrow() %>px;
}

.layout-blank_w1_portlet {
	width: <%= themeDisplay.getResWide() %>px;
}

#layout-add_n1	{
	width: <%= themeDisplay.getResNarrow() %>px;
}

#layout-add_n2 {
	width: <%= themeDisplay.getResNarrow() %>px;
}

#layout-add_w1	{
	width: <%= themeDisplay.getResWide() %>px;
}

.portlet-container {
	<c:if test="<%= themeDisplay.isStatePopUp() %>">
		height: 100%; width: 100%;
	</c:if>
}
