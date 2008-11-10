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
String emailAddress = ParamUtil.getString(request, "emailAddress");
%>

<form action="<portlet:actionURL><portlet:param name="saveLastPath" value="0" /><portlet:param name="struts_action" value="/login/forgot_password" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">

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

<c:if test="<%= PropsValues.CAPTCHA_CHECK_PORTAL_SEND_PASSWORD %>">
	<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>" var="captchaURL">
		<portlet:param name="struts_action" value="/login/captcha" />
	</portlet:actionURL>

	<liferay-ui:captcha url="<%= captchaURL %>" />
</c:if>

<input type="submit" value="<liferay-ui:message key="send-new-password" />" />

</form>

<%@ include file="/html/portlet/login/navigation.jsp" %>

<script type="text/javascript">
	Liferay.Util.focusFormField(document.<portlet:namespace />fm.emailAddress);
</script>