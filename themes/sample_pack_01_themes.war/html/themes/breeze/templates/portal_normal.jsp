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

<%@ include file="portal_init.jsp" %>

<html>

<head>
	<title><%= title %></title>
	<liferay:include page='<%= Constants.TEXT_HTML_DIR + "/common/themes/top_head.jsp" %>' />
</head>

<body id="body-bg">

<liferay:include page="/html/common/themes/top_warning.jsp" flush="true" />

<%@ include file="top.jsp" %>

<%@ include file="navigation.jsp" %>

<c:choose>
	<c:when test="<%= selectable %>">
		<liferay:include page="<%= Constants.TEXT_HTML_DIR + tilesContent %>" flush="true" />
	</c:when>
	<c:otherwise>

		<%
		portletDisplay.recycle();

		portletDisplay.setTitle(title);
		portletDisplay.setWidth(themeDisplay.getResTotal());
		%>

		<c:if test="<%= tilesSubNav.equals(StringPool.BLANK) %>">
			<theme:box top="portlet_top.jsp" bottom="portlet_bottom.jsp">
				<br>

				<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td align="center">
						<liferay:include page="<%= Constants.TEXT_HTML_DIR + tilesContent %>" flush="true" />
					</td>
				</tr>
				</table>

				<br>
			</theme:box>
		</c:if>

		<c:if test="<%= !tilesSubNav.equals(StringPool.BLANK) %>">
			<theme:box top="portlet_top.jsp" bottom="portlet_bottom.jsp">
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr class="beta">
					<td>
						<liferay:include page="<%= Constants.TEXT_HTML_DIR + tilesSubNav %>" flush="true" />
					</td>
				</tr>
				</table>

				<br>

				<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td align="center">
						<liferay:include page="<%= Constants.TEXT_HTML_DIR + tilesContent %>" flush="true" />
					</td>
				</tr>
				</table>

				<br>
			</theme:box>
		</c:if>
	</c:otherwise>
</c:choose>

<%@ include file="bottom.jsp" %>

<liferay:include page="/html/common/themes/bottom-ext.jsp" flush="true" />
<liferay:include page="/html/common/themes/session_timeout.jsp" flush="true" />
<liferay:include page="/html/common/themes/sound_alerts.jsp" flush="true" />

</body>

</html>
