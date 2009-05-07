<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
String openId = ParamUtil.getString(request, "openId");

User user2 = null;
Contact contact2 = null;

PasswordPolicy passwordPolicy = PasswordPolicyLocalServiceUtil.getDefaultPasswordPolicy(company.getCompanyId());

Calendar birthday = CalendarFactoryUtil.getCalendar();

birthday.set(Calendar.MONTH, Calendar.JANUARY);
birthday.set(Calendar.DATE, 1);
birthday.set(Calendar.YEAR, 1970);

boolean male = BeanParamUtil.getBoolean(contact2, request, "male", true);
%>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="saveLastPath" value="0" /><portlet:param name="struts_action" value="/login/create_account" /></portlet:actionURL>" class="exp-form" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
<input name="<portlet:namespace />openId" type="hidden" value="<%= HtmlUtil.escape(openId) %>" />

<liferay-ui:error exception="<%= CaptchaTextException.class %>" message="text-verification-failed" />
<liferay-ui:error exception="<%= ContactFirstNameException.class %>" message="please-enter-a-valid-first-name" />
<liferay-ui:error exception="<%= ContactLastNameException.class %>" message="please-enter-a-valid-last-name" />
<liferay-ui:error exception="<%= DuplicateUserEmailAddressException.class %>" message="the-email-address-you-requested-is-already-taken" />
<liferay-ui:error exception="<%= DuplicateUserIdException.class %>" message="the-user-id-you-requested-is-already-taken" />
<liferay-ui:error exception="<%= DuplicateUserScreenNameException.class %>" message="the-screen-name-you-requested-is-already-taken" />
<liferay-ui:error exception="<%= ReservedUserEmailAddressException.class %>" message="the-email-address-you-requested-is-reserved" />
<liferay-ui:error exception="<%= ReservedUserIdException.class %>" message="the-user-id-you-requested-is-reserved" />
<liferay-ui:error exception="<%= ReservedUserScreenNameException.class %>" message="the-screen-name-you-requested-is-reserved" />
<liferay-ui:error exception="<%= UserEmailAddressException.class %>" message="please-enter-a-valid-email-address" />
<liferay-ui:error exception="<%= UserIdException.class %>" message="please-enter-a-valid-user-id" />

<liferay-ui:error exception="<%= UserPasswordException.class %>">

	<%
	UserPasswordException upe = (UserPasswordException)errorException;
	%>

	<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_CONTAINS_TRIVIAL_WORDS %>">
		<liferay-ui:message key="that-password-uses-common-words-please-enter-in-a-password-that-is-harder-to-guess-i-e-contains-a-mix-of-numbers-and-letters" />
	</c:if>

	<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_INVALID %>">
		<liferay-ui:message key="that-password-is-invalid-please-enter-in-a-different-password" />
	</c:if>

	<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_LENGTH %>">
		<%= LanguageUtil.format(pageContext, "that-password-is-too-short-or-too-long-please-make-sure-your-password-is-between-x-and-512-characters", String.valueOf(passwordPolicy.getMinLength()), false) %>
	</c:if>

	<c:if test="<%= upe.getType() == UserPasswordException.PASSWORDS_DO_NOT_MATCH %>">
		<liferay-ui:message key="the-passwords-you-entered-do-not-match-each-other-please-re-enter-your-password" />
	</c:if>
</liferay-ui:error>

<liferay-ui:error exception="<%= UserScreenNameException.class %>" message="please-enter-a-valid-screen-name" />

<c:if test='<%= SessionMessages.contains(request, "missingOpenIdUserInformation") %>'>
	<span class="portlet-msg-info">
		<liferay-ui:message key="you-have-successfully-authenticated-please-provide-the-following-required-information-to-access-the-portal" />
	</span>
</c:if>

<fieldset class="exp-block-labels exp-form-column">
	<div class="exp-ctrl-holder">
		<label for="<portlet:namespace />firstName"><liferay-ui:message key="first-name" /></label>

		<liferay-ui:input-field model="<%= Contact.class %>" bean="<%= contact2 %>" field="firstName" />
	</div>

	<div class="exp-ctrl-holder">
		<label for="<portlet:namespace />middleName"><liferay-ui:message key="middle-name" /></label>

		<liferay-ui:input-field model="<%= Contact.class %>" bean="<%= contact2 %>" field="middleName" />
	</div>

	<div class="exp-ctrl-holder">
		<label for="<portlet:namespace />lastName"><liferay-ui:message key="last-name" /></label>

		<liferay-ui:input-field model="<%= Contact.class %>" bean="<%= contact2 %>" field="lastName" />
	</div>

	<c:if test="<%= !PropsValues.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE %>">
		<div class="exp-ctrl-holder">
			<label for=""><liferay-ui:message key="screen-name" /></label>

			<liferay-ui:input-field model="<%= User.class %>" bean="<%= user2 %>" field="screenName" />
		</div>
	</c:if>

	<div class="exp-ctrl-holder">
		<label for=""><liferay-ui:message key="email-address" /></label>

		<liferay-ui:input-field model="<%= User.class %>" bean="<%= user2 %>" field="emailAddress" />
	</div>
</fieldset>

<fieldset class="exp-block-labels exp-form-column">
	<c:if test="<%= PropsValues.LOGIN_CREATE_ACCOUNT_ALLOW_CUSTOM_PASSWORD %>">
		<div class="exp-ctrl-holder">
			<label for=""><liferay-ui:message key="password" /></label>

			<input name="<portlet:namespace />password1" size="30" type="password" value="" />
		</div>

		<div class="exp-ctrl-holder">
			<label for=""><liferay-ui:message key="enter-again" /></label>

			<input name="<portlet:namespace />password2" size="30" type="password" value="" />
		</div>
	</c:if>

	<c:choose>
		<c:when test="<%= PropsValues.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_BIRTHDAY %>">
			<div class="exp-ctrl-holder">
				<label for=""><liferay-ui:message key="birthday" /></label>

				<liferay-ui:input-field model="<%= Contact.class %>" bean="<%= contact2 %>" field="birthday" defaultValue="<%= birthday %>" />
			</div>
		</c:when>
		<c:otherwise>
			<input name="<portlet:namespace />birthdayMonth" type="hidden" value="<%= Calendar.JANUARY %>" />
			<input name="<portlet:namespace />birthdayDay" type="hidden" value="1" />
			<input name="<portlet:namespace />birthdayYear" type="hidden" value="1970" />
		</c:otherwise>
	</c:choose>

	<c:if test="<%= PropsValues.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_MALE %>">
		<div class="exp-ctrl-holder">
			<label for="<portlet:namespace />male"><liferay-ui:message key="gender" /></label>

			<select name="<portlet:namespace />male">
				<option value="1"><liferay-ui:message key="male" /></option>
				<option <%= !male? "selected" : "" %> value="0"><liferay-ui:message key="female" /></option>
			</select>
		</div>
	</c:if>

	<c:if test="<%= PropsValues.CAPTCHA_CHECK_PORTAL_CREATE_ACCOUNT %>">
		<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>" var="captchaURL">
			<portlet:param name="struts_action" value="/login/captcha" />
		</portlet:actionURL>

		<liferay-ui:captcha url="<%= captchaURL %>" />
	</c:if>
</fieldset>

<div class="exp-button-holder">
	<input type="submit" value="<liferay-ui:message key="save" />" />
</div>

</form>

<%@ include file="/html/portlet/login/navigation.jspf" %>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<script type="text/javascript">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />firstName);
	</script>
</c:if>