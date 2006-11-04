<%
/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
		<div id="layout-box">
			<table border="0" cellspacing="0" cellpadding="0" width="100%" id="layout-top-banner">
			<tr>
				<td valign="top" width="100%">
					<a href="<%= themeDisplay.getURLHome() %>"><img src="<%= themeDisplay.getCompanyLogo() %>"></a>
				</td>
				<td id="layout-global-search" valign="top" align="right">
					<liferay-ui:journal-content-search />
				</td>
				<td valign="top" align="right" class="font-small">
					<span style="font-weight: bold;"><%= user.getGreeting() %></span><br />

					<a href="<%= themeDisplay.getURLHome() %>"><bean:message key="home" /></a> -

					<c:choose>
						<c:when test="<%= themeDisplay.isSignedIn() %>">
							<c:if test="<%= themeDisplay.isShowMyAccountIcon() %>">
								<a href="<%= themeDisplay.getURLMyAccount() %>"><bean:message key="my-account" /></a> -
							</c:if>

							<a href="<%= themeDisplay.getURLSignOut() %>"><bean:message key="sign-out" /><br />

							<c:if test="<%= themeDisplay.isShowAddContentIcon() %>">
								<a href="javascript:<%= themeDisplay.getURLAddContent() %>"><bean:message key="add-content" /></a>
							</c:if>

							<c:if test="<%= themeDisplay.isShowPageSettingsIcon() %>">
								- <a href="javascript: showPageSettings()"><bean:message key="page-settings" /></a>
							</c:if>

							<div id="layout-my-places">
								<liferay-portlet:runtime portletName="<%= PortletKeys.MY_PLACES %>" />
								<div style="clear: both; font-size: 0"></div>
							</div>
						</c:when>
						<c:otherwise>
							<a href="<%= themeDisplay.getURLSignIn() %>"><bean:message key="sign-in" /></a>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			</table>