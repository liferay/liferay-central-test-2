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

<%@ include file="/html/portal/init.jsp" %>

<%
String emailAddress1 = ParamUtil.getString(request, "emailAddress1");
String emailAddress2 = ParamUtil.getString(request, "emailAddress2");
%>

<form action="<%= themeDisplay.getPathMain() %>/portal/update_email_address" class="aui-form" method="post" name="fm" onSubmit="submitForm(document.fm); return false;">
<input name="doAsUserId" type="hidden" value="<%= HtmlUtil.escapeAttribute(themeDisplay.getDoAsUserId()) %>" />
<input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
<input name="<%= WebKeys.REFERER %>" type="hidden" value="<%= themeDisplay.getPathMain() %>?doAsUserId=<%= HtmlUtil.escapeAttribute(themeDisplay.getDoAsUserId()) %>" />

<c:choose>
	<c:when test="<%= SessionErrors.contains(request, DuplicateUserEmailAddressException.class.getName()) %>">
		<div class="portlet-msg-error">
			<liferay-ui:message key="the-email-address-you-requested-is-already-taken" />
		</div>
	</c:when>
	<c:when test="<%= SessionErrors.contains(request, ReservedUserEmailAddressException.class.getName()) %>">
		<div class="portlet-msg-error">
			<liferay-ui:message key="the-email-address-you-requested-is-reserved" />
		</div>
	</c:when>
	<c:when test="<%= SessionErrors.contains(request, UserEmailAddressException.class.getName()) %>">
		<div class="portlet-msg-error">
			<liferay-ui:message key="please-enter-a-valid-email-address" />
		</div>
	</c:when>
	<c:otherwise>
		<div class="portlet-msg-info">
			<liferay-ui:message key="please-enter-a-valid-email-address" />
		</div>
	</c:otherwise>
</c:choose>

<fieldset class="aui-block-labels">
	<legend><liferay-ui:message key="email-address" /></legend>

	<span class="aui-field">
		<span class="aui-field-content">
			<label for="emailAddress1"><liferay-ui:message key="email-address" /></label>

			<input class="lfr-input-text" name="emailAddress1" type="text" value="<%= HtmlUtil.escapeAttribute(emailAddress1) %>" />
		</span>
	</span>

	<span class="aui-field">
		<span class="aui-field-content">
			<label for="emailAddress2"><liferay-ui:message key="enter-again" /></label>

			<input class="lfr-input-text" name="emailAddress2" type="text" value="<%= HtmlUtil.escapeAttribute(emailAddress2) %>" />
		</span>
	</span>
</fieldset>

<input type="submit" value="<liferay-ui:message key="save" />" />

</form>

<aui:script>
	Liferay.Util.focusFormField(document.fm.emailAddress1);
</aui:script>