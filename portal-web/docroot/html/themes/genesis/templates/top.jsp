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
			<div id="layout-top-banner">
				<div><a href="<%= themeDisplay.getURLHome() %>"><img border="0" hspace="0" src="<%= themeDisplay.getCompanyLogo() %>" vspace="0"></a></div>

				<div id="layout-user-menu" style="text-align: right">
					<span style="font-weight: bold"><%= user.getGreeting() %></span>
					<div class="font-small" style="margin-top: 5px;">
						<a href="<%= themeDisplay.getURLHome() %>"><bean:message key="home" /></a> -

						<c:if test="<%= !themeDisplay.isSignedIn() %>">
							<a href="<%= themeDisplay.getURLSignIn() %>"><bean:message key="sign-in" /></a>
						</c:if>

						<c:if test="<%= themeDisplay.isSignedIn() %>">
							<c:if test="<%= themeDisplay.isShowMyAccountIcon() %>">
								<a href="<%= themeDisplay.getURLMyAccount() %>"><bean:message key="my-account" /></a> -
							</c:if>

							<a href="<%= themeDisplay.getURLSignOut() %>"><bean:message key="sign-out" /></a><br />
							<a href="javascript: void(0);" onClick="<%= themeDisplay.getURLAddContent() %>"><bean:message key="add-content" /></a>&nbsp;-&nbsp;
							<a href="<%= themeDisplay.getURLPageSettings().toString() %>"><bean:message key="page-settings" /></a>
							<div id="layout-my-places"><liferay-portlet:runtime portletName="<%= PortletKeys.MY_PLACES %>" /></div>
						</c:if>
					</div>
				</div>
			</div>