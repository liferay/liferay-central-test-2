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
		<div id="layout-box">
			<div id="layout-top-banner"><div id="layout-top-banner-left"><div id="layout-top-banner-right">

				<div id="layout-company-logo">
					<a href="<%= themeDisplay.getURLHome() %>">
						<c:choose>
						<c:when test="<%= BrowserSniffer.is_ie(request) %>">
							<img border="0" hspace="0" width="198" height="49" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0"
								style="filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='<%= themeDisplay.getPathThemeImage() %>/custom/liferay-logo.png');">
						</c:when>
						<c:otherwise>
							<img border="0" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/custom/liferay-logo.png" vspace="0">
						</c:otherwise>
						</c:choose>
					</a>
				</div>

				<div id="layout-user-menu">
					<c:if test="<%= themeDisplay.isSignedIn() %>">
						<span style="font-weight: bold"><%= user.getGreeting() %></span>
					</c:if>
					<div class="font-small">
						<c:if test="<%= themeDisplay.isSignedIn() %>">
							<c:if test="<%= themeDisplay.isShowMyAccountIcon() %>">
								<a href="<%= themeDisplay.getURLMyAccount() %>"><bean:message key="my-account" /></a> -
							</c:if>

							<a href="<%= themeDisplay.getURLSignOut() %>"><bean:message key="sign-out" /></a><br />

							<c:if test="<%= themeDisplay.isShowAddContentIcon() %>">
								<a href="javascript: void(0);" onClick="<%= themeDisplay.getURLAddContent() %>"><bean:message key="add-content" /></a>
							</c:if>

							<c:if test="<%= themeDisplay.isShowPageSettingsIcon() %>">
								-

								<a href="<%= themeDisplay.getURLPageSettings().toString() %>"><bean:message key="page-settings" /></a>
							</c:if>
						</c:if>
					</div>
				</div>
				<div id="layout-my-places"><liferay-portlet:runtime portletName="<%= PortletKeys.MY_PLACES %>" /></div>
				<c:if test="<%= !themeDisplay.isSignedIn() %>">
					<script type="text/javascript">
						<!--
						function displayLogin() {
							var loginDiv = document.getElementById("dhtml_login");
							var divStyle = loginDiv.style;
							divStyle.display = "block"; 
							divStyle.position = "relative";
							divStyle.top = "2px"; 
							divStyle.left = "35px";
							divStyle.width = "210px";
							divStyle.border = "1px solid black";
						}
						
						function closeLogin() {
							var loginDiv = document.getElementById("dhtml_login");
							var divStyle = loginDiv.style;
							divStyle.display = "none"; 
						}
						-->
					</script>
					<div align="right" style="position: absolute; right: 40px; top: 3px; z-index:15;">
						<a href="javascript:void(0);" onClick="displayLogin()"><bean:message key="sign-in" /></a>			

						<div id="dhtml_login" style="display:none; background:#dddddd;">
							<%
							String password = ParamUtil.getString(request, SessionParameters.get(request, "password"));
							boolean rememberMe = ParamUtil.getBoolean(request, "rememberMe");
							%>
							<form action="/c/portal/login" method="post" name="fm1">
								<input name="cmd" type="hidden" value="already-registered">
								<input name="rememberMe" type="hidden" value="false">
	
								<div style="clear:both"></div>

								<span class="font-small" style="float:left; margin-top:2px; padding:5px; text-align:right; width:65px;"><%= LanguageUtil.get(pageContext, "login") %></span> 
								<span style="float:left; padding:5px; text-align:right;"><input class="form-text" name="login" style="width: 120px;" type="text"/></span>

								<div style="clear:both"></div>

								<span class="font-small" style="float:left; margin-top:2px; padding:5px; text-align:right; width:65px;"><%= LanguageUtil.get(pageContext, "password") %></span> 
								<span style="float:left; padding:5px; text-align:right;;"><input class="form-text" name="<%= SessionParameters.get(request, "password") %>" style="width: 120px;" type="password"/></span>

								<div style="clear:both"></div>
								
								<div align="center" style="margin-bottom:3px">
									<input class="portlet-form-button" type="submit" value="Sign In">
									<input class="portlet-form-button" type="button" value="Close" onClick="closeLogin()">
								</div>
								
								<div align="center">
									<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td valign="middle">
											<input <%= rememberMe ? "checked" : "" %> type="checkbox"
												onClick="
													<c:if test="<%= company.isAutoLogin() && !request.isSecure() %>">
														if (this.checked) {
															document.fm1.rememberMe.value = 'on';
														}
														else {
															document.fm1.rememberMe.value = 'off';
														}
													</c:if>"
											>
										</td>
										<td class="font-small" valign="middle">
											<%= LanguageUtil.get(pageContext, "remember-me") %>
										</td>
									</tr>
									</table>
									</span>
								</div>
								<div align="center" class="font-small">
										<a href="/c/portal/login"><%= LanguageUtil.get(pageContext, "forgot-password") %></a>
								</div>
							</form>
						</div>
					</div>
				</c:if>
				<div id="layout-global-search"><liferay-ui:journal-content-search /></div>