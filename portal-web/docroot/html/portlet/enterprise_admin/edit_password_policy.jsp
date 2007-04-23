<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

PasswordPolicy passwordPolicy = (PasswordPolicy)request.getAttribute(WebKeys.PASSWORD_POLICY);

long passwordPolicyId = BeanParamUtil.getLong(passwordPolicy, request, "passwordPolicyId");
%>

<liferay-util:include page="/html/portlet/enterprise_admin/tabs1.jsp">
	<liferay-util:param name="tabs1" value="password-policies" />
	<liferay-util:param name="backURL" value="<%= redirect %>" />
</liferay-util:include>

<form method="post" name="<portlet:namespace />fm" action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_password_policy" /></portlet:actionURL>">
<input name="<portlet:namespace /><%= Constants.CMD %>" value="<%= passwordPolicy == null ? Constants.ADD : Constants.UPDATE %>" type="hidden">
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>">
<input name="<portlet:namespace />passwordPolicyId" type="hidden" value="<%= passwordPolicyId %>">

<liferay-ui:tabs
	names="password-settings"
	param="tabs1"
	refresh="<%= false %>"
>
	<liferay-ui:section>
		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "name") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<liferay-ui:input-field model="<%= PasswordPolicy.class %>" bean="<%= passwordPolicy %>" field="name" />
			</td>
		</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "description") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<liferay-ui:input-field model="<%= PasswordPolicy.class %>" bean="<%= passwordPolicy %>" field="description" />
			</td>
		</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "changeable") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<liferay-ui:input-field model="<%= PasswordPolicy.class %>" bean="<%= passwordPolicy %>" field="changeable" />
				<span title="Check box if user is allowed to change their password.">?</span>
			</td>
		</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "change-required") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<liferay-ui:input-field model="<%= PasswordPolicy.class %>" bean="<%= passwordPolicy %>" field="changeRequired" />
				<span title="Check box if users are required to change password the first time they login.">?</span>
			</td>
		</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "minimum-age") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<liferay-ui:input-field model="<%= PasswordPolicy.class %>" bean="<%= passwordPolicy %>" field="minAge" />
				<span title="Minimum Age - The age (in seconds) that a password must be before it can be changed again. (0 seconds = User can change password immediately, 300 seconds = 5 minutes, 86400 seconds = 1 day).">?</span>
			</td>
		</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "encryption-type") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<select name="<portlet:namespace />storageScheme">
					<option value="md5">MD5</option>
				</select>
			</td>
		</tr>
		</table>
	</liferay-ui:section>
</liferay-ui:tabs>

<br>

<liferay-ui:tabs
	names="password-syntax-checking"
	param="tabs2"
	refresh="<%= false %>"
>
	<liferay-ui:section>
		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "syntax-checking-enabled") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<liferay-ui:input-field model="<%= PasswordPolicy.class %>" bean="<%= passwordPolicy %>" field="checkSyntax" />
				<span title="Check box if passwords are to be checked for length requirements and use of trivial words (such as dictionary words and user information).">?</span>
			</td>
		</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "allow-dictionary-words") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<liferay-ui:input-field model="<%= PasswordPolicy.class %>" bean="<%= passwordPolicy %>" field="allowDictionaryWords" />
				<span title="Check box if passwords can not contain dictionary words.">?</span>
			</td>
		</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "minimum-length") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<liferay-ui:input-field model="<%= PasswordPolicy.class %>" bean="<%= passwordPolicy %>" field="minLength" />
				<span title="Minimum Length - Minimum length of characters that a password can be.">?</span>
			</td>
		</tr>
		</table>
	</liferay-ui:section>
</liferay-ui:tabs>

<br>

<liferay-ui:tabs
	names="password-history"
	param="tabs3"
	refresh="<%= false %>"
>
	<liferay-ui:section>
		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "history-enabled") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<liferay-ui:input-field model="<%= PasswordPolicy.class %>" bean="<%= passwordPolicy %>" field="history" />
				<span title="Check box if passwords can not be recycled.">?</span>
			</td>
		</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "history-count") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<liferay-ui:input-field model="<%= PasswordPolicy.class %>" bean="<%= passwordPolicy %>" field="historyCount" />
				<span title="History Count - Number of passwords to store in history, to make sure they are not recycled in the future (2 to 24 passwords).">?</span>
			</td>
		</tr>
		</table>
	</liferay-ui:section>
</liferay-ui:tabs>

<br>

<liferay-ui:tabs
	names="password-expiration"
	param="tabs4"
	refresh="<%= false %>"
>
	<liferay-ui:section>
		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "expiration-enabled") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<liferay-ui:input-field model="<%= PasswordPolicy.class %>" bean="<%= passwordPolicy %>" field="expireable" />
				<span title="Check box if passwords will automatically expire after a specified time.">?</span>
			</td>
		</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "maximum-age") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<liferay-ui:input-field model="<%= PasswordPolicy.class %>" bean="<%= passwordPolicy %>" field="maxAge" />
				<span title="Maximum Age - The age (in seconds) before a password will expire and the user is required to change the password. (2592000 seconds = 30 days, 7776000 seconds = 90 days)">?</span>
			</td>
		</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "warning-time") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<liferay-ui:input-field model="<%= PasswordPolicy.class %>" bean="<%= passwordPolicy %>" field="warningTime" />
				<span title="Warning Time - The time (in seconds), before the password expires, to warn the user that the password will be expiring. (86400 seconds = 1 day)">?</span>
			</td>
		</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "grace-limit") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<liferay-ui:input-field model="<%= PasswordPolicy.class %>" bean="<%= passwordPolicy %>" field="graceLimit" />
				<span title="Grace Limit - The number of logins which are permitted with a password that has expired">?</span>
			</td>
		</tr>
		</table>
	</liferay-ui:section>
</liferay-ui:tabs>

<br>

<liferay-ui:tabs
	names="lockout"
	param="tabs5"
	refresh="<%= false %>"
>
	<liferay-ui:section>
		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "lockout-enabled") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<liferay-ui:input-field model="<%= PasswordPolicy.class %>" bean="<%= passwordPolicy %>" field="lockout" />
				<span title="Check box if users will be locked out of system after a certain number of failed login attempts.">?</span>
			</td>
		</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "maximum-failure") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<liferay-ui:input-field model="<%= PasswordPolicy.class %>" bean="<%= passwordPolicy %>" field="maxFailure" />
				<span title="Maximim Failure - The number of failed login attempts before user is locked out.">?</span>
			</td>

		</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "reset-failure-count") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<liferay-ui:input-field model="<%= PasswordPolicy.class %>" bean="<%= passwordPolicy %>" field="resetFailureCount" />
				<span title="Reset Failure Count - The time (in seconds) after which the ''Login Failure Count'' is reset.">?</span>
			</td>
		</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "require-unlock") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<liferay-ui:input-field model="<%= PasswordPolicy.class %>" bean="<%= passwordPolicy %>" field="requireUnlock" />
				<span title="Require Unlock - Check box if users are locked out indefinately until an Administrator manually unlocks their account.">?</span>
			</td>
		</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "lockout-duration") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<liferay-ui:input-field model="<%= PasswordPolicy.class %>" bean="<%= passwordPolicy %>" field="lockoutDuration" />
				<span title="Lockout Duration - The time (in seconds) that a User Account will have to wait before he or she can login again.  This value wil be ignored if 'RequireUnlock' value is checked.">?</span>
			</td>
		</tr>
		</table>
	</liferay-ui:section>
</liferay-ui:tabs>

<br>

<input type="submit" value='<%= LanguageUtil.get(pageContext, "save") %>'>

<input type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="self.location = '<%= redirect %>';">

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />name.focus();
</script>