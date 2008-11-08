<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/login/init.jsp" %>

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() %>">

		<%
		String signedInAs = user.getFullName();

		if (themeDisplay.isShowMyAccountIcon()) {
			signedInAs = "<a href=\"" + themeDisplay.getURLMyAccount().toString() + "\">" + signedInAs + "</a>";
		}
		%>

		<%= LanguageUtil.format(pageContext, "you-are-signed-in-as-x", signedInAs) %>
	</c:when>
	<c:otherwise>

		<%
		String redirect = ParamUtil.getString(renderRequest, "redirect");

		String login = LoginUtil.getLogin(request, "login", company);
		String password = StringPool.BLANK;
		boolean rememberMe = ParamUtil.getBoolean(request, "rememberMe");
		%>

		<form action="<portlet:actionURL secure="<%= PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS || request.isSecure() %>"><portlet:param name="saveLastPath" value="0" /><portlet:param name="struts_action" value="/login/login" /></portlet:actionURL>" class="uni-form" method="post" name="<portlet:namespace />fm1">
		<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(redirect) %>" />
		<input name="<portlet:namespace />rememberMe" type="hidden" value="<%= rememberMe %>" />

		<liferay-ui:error exception="<%= AuthException.class %>" message="authentication-failed" />
		<liferay-ui:error exception="<%= CookieNotSupportedException.class %>" message="authentication-failed-please-enable-browser-cookies" />
		<liferay-ui:error exception="<%= NoSuchUserException.class %>" message="please-enter-a-valid-login" />
		<liferay-ui:error exception="<%= PasswordExpiredException.class %>" message="your-password-has-expired" />
		<liferay-ui:error exception="<%= UserEmailAddressException.class %>" message="please-enter-a-valid-login" />
		<liferay-ui:error exception="<%= UserLockoutException.class %>" message="this-account-has-been-locked" />
		<liferay-ui:error exception="<%= UserPasswordException.class %>" message="please-enter-a-valid-password" />
		<liferay-ui:error exception="<%= UserScreenNameException.class %>" message="please-enter-a-valid-screen-name" />

		<fieldset class="block-labels">
			<c:choose>
				<c:when test="<%= OpenIdUtil.isEnabled(company.getCompanyId()) %>">
					<legend><liferay-ui:message key="sign-in-with-a-regular-account" /></legend>
				</c:when>
				<c:otherwise>
					<legend><liferay-ui:message key="sign-in" /></legend>
				</c:otherwise>
			</c:choose>

			<div class="ctrl-holder">
				<label for="<portlet:namespace />login"><liferay-ui:message key="login" /></label>

				<input class="lfr-input-text" name="<portlet:namespace />login" type="text" value="<%= HtmlUtil.escape(login) %>" />
			</div>

			<div class="ctrl-holder">
				<label for="<portlet:namespace />password"><liferay-ui:message key="password" /></label>

				<input class="lfr-input-text" id="<portlet:namespace />password" name="<portlet:namespace />password" type="password" value="<%= password %>" />

				<span id="<portlet:namespace />passwordCapsLockSpan" style="display: none;"><liferay-ui:message key="caps-lock-is-on" /></span>
			</div>

			<c:if test="<%= company.isAutoLogin() && !PropsValues.SESSION_DISABLED %>">
				<div class="ctrl-holder inline-label">
					<label for="<portlet:namespace />rememberMeCheckbox"><liferay-ui:message key="remember-me" /></label>

					<input <%= rememberMe ? "checked" : "" %> id="<portlet:namespace />rememberMeCheckbox" type="checkbox" />
				</div>
			</c:if>

			<div class="button-holder">
				<input type="submit" value="<liferay-ui:message key="sign-in" />" />
			</div>
		</fieldset>

		</form>

		<c:if test="<%= OpenIdUtil.isEnabled(company.getCompanyId()) %>">

			<%
			String openId = ParamUtil.getString(request, "openId");
			%>

			<form action="<%= themeDisplay.getPathMain() %>/portal/open_id_request" class="uni-form" method="post" name="fm2">

			<fieldset class="block-labels">
				<legend><liferay-ui:message key="sign-in-with-an-open-id-provider" /></legend>

				<div class="ctrl-holder">
					<label for="<portlet:namespace />openId"><liferay-ui:message key="open-id" /></label>

					<input class="openid-login" name="<portlet:namespace />openId" type="text" value="<%= HtmlUtil.escape(openId) %>" />
				</div>

				<div class="button-holder">
					<input type="submit" value="<liferay-ui:message key="sign-in" />" />
				</div>
			</fieldset>

			</form>
		</c:if>

		<div>
			<c:if test="<%= company.isStrangers() %>">
				<a href="<%= themeDisplay.getURLCreateAccount() %>"><liferay-ui:message key="create-account" /></a>
			</c:if>

			<c:if test="<%= company.isSendPassword() %>">
				&nbsp;|&nbsp;

				<a href="<portlet:renderURL><portlet:param name="struts_action" value="/login/forgot_password" /></portlet:renderURL>"><liferay-ui:message key="forgot-password" />?</a>
			</c:if>
		</div>

		<script type="text/javascript">
			jQuery(
				function() {
					jQuery('#<portlet:namespace />password').keypress(
						function(event) {
							Liferay.Util.showCapsLock(event, '<portlet:namespace />passwordCapsLockSpan');
						}
					);

					jQuery('#<portlet:namespace />rememberMeCheckbox').click(
						function() {
							var checked = 'off';

							if (this.checked) {
								checked = 'on';
							}

							jQuery('#<portlet:namespace />rememberMe').val(checked);
						}
					);
				}
			);

			<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
				Liferay.Util.focusFormField(document.<portlet:namespace />fm1.<portlet:namespace />login);
			</c:if>
		</script>
	</c:otherwise>
</c:choose>