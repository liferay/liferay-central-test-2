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

<%@ page import="com.liferay.portal.util.MaintenanceUtil" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>

<%
boolean invokingSession = false;

if (session.getId().equals(MaintenanceUtil.getSessionId())) {
	invokingSession = true;
}
%>

<html>

<head>
	<meta http-equiv="refresh" content="30; url=<%= PortalUtil.getPortalURL(request) %>">
</head>

<body>

<center>

<table border="0" cellpadding="0" cellspacing="0" height="100%" width="700">
<tr>
	<td align="center" valign="middle">
		<table border="0" cellpadding="1" cellspacing="0" width="100%">
		<tr>
			<td bgcolor="#FF0000">
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td bgcolor="#FFFFFF">
						<br />

						<table border="0" cellpadding="10" cellspacing="0" width="100%">
						<tr>
							<td align="center">
								The system is currently undergoing maintenance. Please try again later.
							</td>
						</tr>

						<%
						if (invokingSession) {
						%>

							<tr>
								<td>
									<%= MaintenanceUtil.getStatus() %>
								</td>
							</tr>

						<%
						}
						%>

						</table>

						<br />
					</td>
				</tr>
				</table>
			</td>
		</tr>
		</table>
	</td>
</tr>
</table>

</center>

</body>

</html>