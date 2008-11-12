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
User user2 = (User)request.getAttribute(ForgotPasswordAction.class.getName());
%>

<form action="<portlet:actionURL><portlet:param name="saveLastPath" value="0" /><portlet:param name="struts_action" value="/login/forgot_password" /></portlet:actionURL>" class="uni-form" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace />redirect" type="hidden" value="<portlet:renderURL />" />

<liferay-ui:error exception="<%= CaptchaTextException.class %>" message="text-verification-failed" />
<liferay-ui:error exception="<%= NoSuchUserException.class %>" message="the-email-address-you-requested-is-not-registered-in-our-database" />
<liferay-ui:error exception="<%= SendPasswordException.class %>" message="your-password-can-only-be-sent-to-an-external-email-address" />
<liferay-ui:error exception="<%= UserEmailAddressException.class %>" message="please-enter-a-valid-email-address" />
<liferay-ui:error exception="<%= UserReminderQueryException.class %>" message="Your answer does not match what is in our database." />

<fieldset class="block-labels">
	<c:choose>
		<c:when test="<%= user2 == null%>">

			<%
			String emailAddress = ParamUtil.getString(request, "emailAddress");
			%>

			<input name="<portlet:namespace />step" type="hidden" value="1" />

			<div class="ctrl-holder">
				<label for="<portlet:namespace />emailAddress"><liferay-ui:message key="email-address" /></label>

				<input name="<portlet:namespace />emailAddress" size="30" type="text" value="<%= HtmlUtil.escape(emailAddress) %>" />
			</div>

			<c:if test="<%= PropsValues.CAPTCHA_CHECK_PORTAL_SEND_PASSWORD %>">
				<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>" var="captchaURL">
					<portlet:param name="struts_action" value="/login/captcha" />
				</portlet:actionURL>

				<div class="ctrl-holder">
					<liferay-ui:captcha url="<%= captchaURL %>" />
				</div>
			</c:if>

			<div class="button-holder">
				<input type="submit" value="<liferay-ui:message key='<%= PropsValues.USERS_REMINDER_QUERIES_ENABLED ? "next" : "send-new-password" %>' />" />
			</div>

		</c:when>
		<c:otherwise>
			<input name="<portlet:namespace />step" type="hidden" value="2" />
			<input name="<portlet:namespace />emailAddress" type="hidden" value="<%= user2.getEmailAddress() %>" />

			<div class="portlet-msg-info">
				<%= LanguageUtil.format(pageContext, "a-new-password-will-be-sent-to-x-if-you-can-correctly-answer-the-following-question", user2.getEmailAddress()) %>
			</div>

			<div class="ctrl-holder">
				<label for="<portlet:namespace />answer"><liferay-ui:message key="<%= user2.getReminderQueryQuestion() %>" /></label>

				<input name="<portlet:namespace />answer" type="text" />
			</div>

			<div class="button-holder">
				<input type="submit" value="<liferay-ui:message key="send-new-password" />" />
			</div>
		</c:otherwise>
	</c:choose>
</fieldset>
</form>

<%@ include file="/html/portlet/login/navigation.jsp" %>

<script type="text/javascript">
	Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= (user2 == null) ? "emailAddress" : "answer" %>);
</script>