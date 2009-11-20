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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

PasswordPolicy passwordPolicy = (PasswordPolicy)request.getAttribute(WebKeys.PASSWORD_POLICY);

long passwordPolicyId = BeanParamUtil.getLong(passwordPolicy, request, "passwordPolicyId");

boolean defaultPolicy = BeanParamUtil.getBoolean(passwordPolicy, request, "defaultPolicy");
long minAge = BeanParamUtil.getLong(passwordPolicy, request, "minAge");
int historyCount = BeanParamUtil.getInteger(passwordPolicy, request, "historyCount");
long maxAge = BeanParamUtil.getLong(passwordPolicy, request, "maxAge");
long warningTime = BeanParamUtil.getLong(passwordPolicy, request, "warningTime");
long resetFailureCount = BeanParamUtil.getLong(passwordPolicy, request, "resetFailureCount");
boolean requireUnlock = BeanParamUtil.getBoolean(passwordPolicy, request, "requireUnlock");
long lockoutDuration = BeanParamUtil.getLong(passwordPolicy, request, "lockoutDuration");
%>

<liferay-util:include page="/html/portlet/enterprise_admin/password_policy/toolbar.jsp">
	<liferay-util:param name="toolbarItem" value='<%= (passwordPolicy == null) ? "add" : "view-all" %>' />
	<liferay-util:param name="backURL" value="<%= backURL %>" />
</liferay-util:include>

<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editPasswordPolicyURL">
	<portlet:param name="struts_action" value="/enterprise_admin/edit_password_policy" />
</portlet:actionURL>

<aui:form action="<%= editPasswordPolicyURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= passwordPolicy == null ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="passwordPolicyId" type="hidden" value="<%= passwordPolicyId %>" />

	<liferay-ui:error exception="<%= DuplicatePasswordPolicyException.class %>" message="please-enter-a-unique-name" />
	<liferay-ui:error exception="<%= PasswordPolicyNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= passwordPolicy %>" model="<%= PasswordPolicy.class %>" />

	<liferay-ui:panel-container extended="<%= true %>" id="editPasswordPolicy" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="passwordPolicyGeneral" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "general") %>'>

			<aui:fieldset>
				<aui:input disabled="<%= defaultPolicy %>" name="name" />

				<aui:input name="description" />

				<aui:input helpMessage="changeable-help" inlineLabel="left" name="changeable" />

				<div id="<portlet:namespace />changeableSettings">
					<aui:input helpMessage="change-required-help" inlineLabel="left" name="changeRequired" />

					<aui:select helpMessage="minimum-age-help" label="minimum-age" name="minAge">
						<aui:option label="none" selected="<%= (minAge == 0) %>" value="0" />

						<%
						for (int i = 0; i < 15; i++) {
						%>

							<aui:option label="<%= LanguageUtil.getTimeDescription(pageContext, _DURATIONS[i] * 1000) %>" selected="<%= (minAge == _DURATIONS[i]) %>" value="<%= _DURATIONS[i] %>" />

						<%
						}
						%>

					</aui:select>
				</div>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="passwordSyntaxChecking" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "password-syntax-checking") %>'>
			<aui:fieldset>

				<aui:input helpMessage="syntax-checking-enabled-help" inlineLabel="left" label="syntax-checking-enabled" name="checkSyntax" />

				<div id="<portlet:namespace />syntaxSettings">
					<aui:input helpMessage="allow-dictionary-words-help" inlineLabel="left" name="allowDictionaryWords" />

					<aui:input helpMessage="minimum-length-help" label="minimum-length" name="minLength" />
				</div>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="passwordHistory" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "password-history") %>'>
			<aui:fieldset>

				<aui:input helpMessage="history-enabled-help" inlineLabel="left" label="history-enabled" name="history" />

				<div id="<portlet:namespace />historySettings">
					<aui:select helpMessage="history-count-help" name="historyCount">

						<%
						for (int i = 2; i < 25; i++) {
						%>

							<aui:option label="<%= i %>" selected="<%= (historyCount == i) %>" />

						<%
						}
						%>

					</aui:select>
				</div>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="passwordExpiration" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "password-expiration") %>'>
			<aui:fieldset>

				<aui:input helpMessage="expiration-enabled-help" inlineLabel="left" label="expiration-enabled" name="expireable" />

				<div id="<portlet:namespace />expirationSettings">
					<aui:select helpMessage="maximum-age-help" label="maximum-age" name="maxAge">

						<%
						for (int i = 15; i < _DURATIONS.length; i++) {
						%>

							<aui:option label="<%= LanguageUtil.getTimeDescription(pageContext, _DURATIONS[i] * 1000) %>" selected="<%= (maxAge == _DURATIONS[i]) %>" value="<%= _DURATIONS[i] %>" />

						<%
						}
						%>

					</aui:select>

					<aui:select helpMessage="warning-time-help" name="warningTime">

						<%
						for (int i = 7; i < 16; i++) {
						%>

							<aui:option label="<%= LanguageUtil.getTimeDescription(pageContext, _DURATIONS[i] * 1000) %>" value="<%= _DURATIONS[i] %>" selected="<%= (warningTime == _DURATIONS[i]) %>" />

						<%
						}
						%>

					</aui:select>

					<aui:input helpMessage="grace-limit-help" name="graceLimit" />
				</div>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="passwordLockout" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "lockout") %>'>
			<aui:fieldset>
				<aui:input helpMessage="lockout-enabled-help" inlineLabel="left" label="lockout-enabled" name="lockout" />

				<div id="<portlet:namespace />lockoutSettings">
					<aui:input helpMessage="maximum-failure-help" label="maximum-failure" name="maxFailure" />

					<aui:select helpMessage="reset-failure-count-help" name="resetFailureCount">

						<%
						for (int i = 0; i < 15; i++) {
						%>

							<aui:option label="<%= LanguageUtil.getTimeDescription(pageContext, _DURATIONS[i] * 1000) %>" selected="<%= (resetFailureCount == _DURATIONS[i]) %>" value="<%= _DURATIONS[i] %>" />

						<%
						}
						%>

					</aui:select>

					<aui:select helpMessage="lockout-duration-help" name="lockoutDuration">
						<aui:option label="until-unlocked-by-an-administrator" selected="<%= (requireUnlock) %>" value="0" />

						<%
						for (int i = 0; i < 15; i++) {
						%>

							<aui:option label="<%= LanguageUtil.getTimeDescription(pageContext, _DURATIONS[i] * 1000) %>" selected="<%= (!requireUnlock && (lockoutDuration == _DURATIONS[i])) %>" value="<%= _DURATIONS[i] %>" />

						<%
						}
						%>

					</aui:select>
				</div>
			</aui:fieldset>
		</liferay-ui:panel>
	</liferay-ui:panel-container>

	<aui:button-row>
		<aui:button type="submit" value="save" />

		<aui:button onClick="<%= redirect %>" value="cancel" />
	</aui:button-row>
</aui:form>

<script type="text/javascript">
	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= defaultPolicy ? "description" : "name" %>);
	</c:if>

	Liferay.Util.toggleBoxes('<portlet:namespace />changeableCheckbox', '<portlet:namespace />changeableSettings');
	Liferay.Util.toggleBoxes('<portlet:namespace />checkSyntaxCheckbox', '<portlet:namespace />syntaxSettings');
	Liferay.Util.toggleBoxes('<portlet:namespace />historyCheckbox', '<portlet:namespace />historySettings');
	Liferay.Util.toggleBoxes('<portlet:namespace />expireableCheckbox', '<portlet:namespace />expirationSettings');
	Liferay.Util.toggleBoxes('<portlet:namespace />lockoutCheckbox', '<portlet:namespace />lockoutSettings');
</script>

<%
if (passwordPolicy != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, passwordPolicy.getName(), null);
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-user"), currentURL);
}
%>

<%!
private static final long[] _DURATIONS = {300, 600, 1800, 3600, 7200, 10800, 21600, 43200, 86400, 172800, 259200, 345600, 432000, 518400, 604800, 1209600, 1814400, 2419200, 4838400, 7862400, 15724800, 31449600};
%>