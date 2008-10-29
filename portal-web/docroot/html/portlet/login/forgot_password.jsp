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

<%
String emailAddress = ParamUtil.getString(renderRequest, "emailAddress");
String backURL = ParamUtil.getString(renderRequest, "backURL");
%>

<liferay-ui:tabs
	names="forgot-password"
	backURL="<%= backURL %>" />

<form action="<portlet:actionURL windowState="<%= WindowState.NORMAL.toString() %>"><portlet:param name="struts_action" value="/login/view" /></portlet:actionURL>" method="post" name="fm3">
	<input name="<%= Constants.CMD %>" type="hidden" value="forgot-password-email" />
	<input name="backURL" type="hidden" value="<%= HtmlUtil.escape(backURL) %>" />
	<input name="redirect" type="hidden" value="<%= HtmlUtil.escape(currentURL) %>" />

	<liferay-ui:success key="request_processed" message="your-request-processed-successfully" />

	<liferay-ui:error exception="<%= CaptchaTextException.class %>" message="text-verification-failed" />
	<liferay-ui:error exception="<%= NoSuchUserException.class %>" message="the-email-address-you-requested-is-not-registered-in-our-database" />
	<liferay-ui:error exception="<%= SendPasswordException.class %>" message="your-password-can-only-be-sent-to-an-external-email-address" />
	<liferay-ui:error exception="<%= UserEmailAddressException.class %>" message="please-enter-a-valid-email-address" />

	<table class="lfr-table">
		<tr>
			<td>
				<liferay-ui:message key="email-address" />
			</td>
			<td>
				<input name="emailAddress" size="30" type="text" value="<%= HtmlUtil.escape(emailAddress) %>" />
			</td>
		</tr>
	</table>

	<br />

	<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>" var="captchaURL">
		<portlet:param name="struts_action" value="/login/captcha" />
	</portlet:actionURL>

	<c:if test="<%= PropsValues.CAPTCHA_CHECK_PORTAL_SEND_PASSWORD %>">
		<liferay-ui:captcha url='<%= captchaURL.toString() %>' />
	</c:if>

	<c:choose>
		<c:when test="<%= PropsValues.USERS_REMINDER_QUERIES_ENABLED %>">
			<input type="submit" value="<liferay-ui:message key="next" />" />

		</c:when>
		<c:otherwise>
			<input type="submit" value="<liferay-ui:message key="send-new-password" />" />
		</c:otherwise>
	</c:choose>
</form>

<script type="text/javascript">
	Liferay.Util.focusFormField(document.fm3.emailAddress);
</script>