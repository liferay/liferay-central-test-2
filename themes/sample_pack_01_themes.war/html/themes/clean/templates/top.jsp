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

<div id="layout-outer-side-decoration">
	<div id="layout-inner-side-decoration">
		<div id="layout-top-decoration">
			<div id="layout-corner-ul"></div>
			<div id="layout-corner-ur"></div>
		</div>
		<div id="layout-box">
			<table border="0" cellpadding="0" cellspacing="0" width="<%= themeDisplay.getResTotal() %>">
			<tr>
				<td><a class="bg" href="<%= themeDisplay.getURLHome() %>"><img border="0" hspace="0" src="<%= themeDisplay.getCompanyLogo() %>" vspace="0"></a></td>
				<td align="right" nowrap valign="bottom">
					<font class="bg" size="2"><b>
					<%= user.getGreeting() %><br>
					</b></font>

					<font class="bg" size="1">

					<a class="bg" href="<%= themeDisplay.getURLHome() %>"><bean:message key="home" /></a> -

					<c:if test="<%= !themeDisplay.isSignedIn() %>">
						<a class="bg" href="<%= themeDisplay.getURLLanguage() %>"><bean:message key="language" /></a> -

						<a class="bg" href="<%= themeDisplay.getURLSignIn() %>"><bean:message key="sign-in" /></a>
					</c:if>

					<c:if test="<%= themeDisplay.isSignedIn() %>">
						<c:if test="<%= themeDisplay.getURLMyAccount() != null %>">
							<a class="bg" href="<%= themeDisplay.getURLMyAccount() %>"><bean:message key="my-account" /></a> -
						</c:if>

						<a class="bg" href="<%= themeDisplay.getURLSignOut() %>"><bean:message key="sign-out" /></a>
					</c:if>

					</font>
				</td>
			</tr>
			</table>

			<table border="0" cellpadding="0" cellspacing="0" width="<%= themeDisplay.getResTotal() %>">
			<tr>
				<td><img border="0" height="10" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
			</tr>
			</table>