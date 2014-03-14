<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/html/portlet/portal_settings/init.jsp" %>

<h3><liferay-ui:message key="email-notifications" /></h3>

<%
String adminEmailFromName = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_NAME);
String adminEmailFromAddress = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);

PortletPreferences companyPortletPreferences = PrefsPropsUtil.getPreferences(company.getCompanyId(), true);
%>

<liferay-ui:error-marker key="errorSection" value="email_notifications" />

<liferay-ui:tabs
	names="sender,account-created-notification,email-verification-notification,password-changed-notification,password-reset-notification"
	refresh="<%= false %>"
>
	<liferay-ui:section>
		<aui:fieldset>
			<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />

			<aui:input cssClass="lfr-input-text-container" label="name" name='<%= "settings--" + PropsKeys.ADMIN_EMAIL_FROM_NAME + "--" %>' type="text" value="<%= adminEmailFromName %>" />

			<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />

			<aui:input cssClass="lfr-input-text-container" label="address" name='<%= "settings--" + PropsKeys.ADMIN_EMAIL_FROM_ADDRESS + "--" %>' type="text" value="<%= adminEmailFromAddress %>" />
		</aui:fieldset>
	</liferay-ui:section>

	<liferay-ui:section>
		<aui:fieldset>
			<aui:input label="enabled" name='<%= "settings--" + PropsKeys.ADMIN_EMAIL_USER_ADDED_ENABLED + "--" %>' type="checkbox" value="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_USER_ADDED_ENABLED) %>" />

			<liferay-ui:error key="emailUserAddedSubject" message="please-enter-a-valid-subject" />

			<aui:input cssClass="lfr-input-text-container" label="subject" name="settings--adminEmailUserAddedSubject--" type="text" value='<%= companyPortletPreferences.getValue("adminEmailUserAddedSubject", PropsValues.ADMIN_EMAIL_USER_ADDED_SUBJECT) %>' />

			<liferay-ui:error key="emailUserAddedBody" message="please-enter-a-valid-body" />

			<liferay-ui:email-notification-settings
				bodyLabel="body-with-password"
				emailBody='<%= PrefsParamUtil.getString(companyPortletPreferences, request, "adminEmailUserAddedBody", ContentUtil.get(PropsValues.ADMIN_EMAIL_USER_ADDED_BODY)) %>'
				emailParam="adminEmailUserAdded"
				fieldPrefix="settings"
				helpMessage="account-created-notification-body-with-password-help"
				showEmailEnabled="<%= false %>"
				showSubject="<%= false %>"
			/>

			<liferay-ui:error key="emailUserAddedNoPasswordBody" message="please-enter-a-valid-body" />

			<liferay-ui:email-notification-settings
				bodyLabel="body-without-password"
				emailBody='<%= PrefsParamUtil.getString(companyPortletPreferences, request, "adminEmailUserAddedNoPasswordBody", ContentUtil.get(PropsValues.ADMIN_EMAIL_USER_ADDED_NO_PASSWORD_BODY)) %>'
				emailParam="adminEmailUserAddedNoPassword"
				fieldPrefix="settings"
				helpMessage="account-created-notification-body-without-password-help"
				showEmailEnabled="<%= false %>"
				showSubject="<%= false %>"
			/>

			<aui:fieldset cssClass="terms email-user-add definition-of-terms">
				<%@ include file="/html/portlet/portal_settings/definition_of_terms.jspf" %>
			</aui:fieldset>
		</aui:fieldset>
	</liferay-ui:section>

	<liferay-ui:section>
		<liferay-ui:error key="emailVerificationSubject" message="please-enter-a-valid-subject" />
		<liferay-ui:error key="emailVerificationBody" message="please-enter-a-valid-body" />

		<liferay-ui:email-notification-settings
			emailBody='<%= PrefsParamUtil.getString(companyPortletPreferences, request, "adminEmailVerificationBody", ContentUtil.get(PropsValues.ADMIN_EMAIL_VERIFICATION_BODY)) %>'
			emailParam="adminEmailVerification"
			emailSubject='<%= PrefsParamUtil.getString(companyPortletPreferences, request, "adminEmailVerificationSubject", ContentUtil.get(PropsValues.ADMIN_EMAIL_VERIFICATION_SUBJECT)) %>'
			fieldPrefix="settings"
			showEmailEnabled="<%= false %>"
		/>

		<aui:fieldset cssClass="terms email-verification definition-of-terms">
			<%@ include file="/html/portlet/portal_settings/definition_of_terms.jspf" %>
		</aui:fieldset>
	</liferay-ui:section>

	<liferay-ui:section>
		<liferay-ui:error key="emailPasswordSentSubject" message="please-enter-a-valid-subject" />
		<liferay-ui:error key="emailPasswordSentBody" message="please-enter-a-valid-body" />

		<liferay-ui:email-notification-settings
			emailBody='<%= PrefsParamUtil.getString(companyPortletPreferences, request, "adminEmailPasswordSentBody", ContentUtil.get(PropsValues.ADMIN_EMAIL_PASSWORD_SENT_BODY)) %>'
			emailParam="adminEmailPasswordSent"
			emailSubject='<%= PrefsParamUtil.getString(companyPortletPreferences, request, "adminEmailPasswordSentSubject", ContentUtil.get(PropsValues.ADMIN_EMAIL_PASSWORD_SENT_SUBJECT)) %>'
			fieldPrefix="settings"
			showEmailEnabled="<%= false %>"
		/>

		<aui:fieldset cssClass="terms email-verification definition-of-terms">
			<%@ include file="/html/portlet/portal_settings/definition_of_terms.jspf" %>
		</aui:fieldset>
	</liferay-ui:section>

	<liferay-ui:section>
			<liferay-ui:error key="emailPasswordResetSubject" message="please-enter-a-valid-subject" />
			<liferay-ui:error key="emailPasswordResetBody" message="please-enter-a-valid-body" />

		<liferay-ui:email-notification-settings
			emailBody='<%= PrefsParamUtil.getString(companyPortletPreferences, request, "adminEmailPasswordResetBody", ContentUtil.get(PropsValues.ADMIN_EMAIL_PASSWORD_RESET_BODY)) %>'
			emailParam="adminEmailPasswordReset"
			emailSubject='<%= PrefsParamUtil.getString(companyPortletPreferences, request, "adminEmailPasswordResetSubject", ContentUtil.get(PropsValues.ADMIN_EMAIL_PASSWORD_RESET_SUBJECT)) %>'
			fieldPrefix="settings"
			showEmailEnabled="<%= false %>"
		/>

		<aui:fieldset cssClass="terms email-verification definition-of-terms">
			<%@ include file="/html/portlet/portal_settings/definition_of_terms.jspf" %>
		</aui:fieldset>
	</liferay-ui:section>
</liferay-ui:tabs>

<aui:script>
	function <portlet:namespace />saveEmails() {
		try {
			document.<portlet:namespace />fm['<portlet:namespace />settings--adminEmailUserAddedBody--'].value = window['<portlet:namespace />adminEmailUserAdded'].getHTML();
		}
		catch (e) {
		}

		try {
			document.<portlet:namespace />fm['<portlet:namespace />settings--adminEmailUserAddedNoPasswordBody--'].value = window['<portlet:namespace />adminEmailUserAddedNoPassword'].getHTML();
		}
		catch (e) {
		}

		try {
			document.<portlet:namespace />fm['<portlet:namespace />settings--adminEmailPasswordSentBody--'].value = window['<portlet:namespace />adminEmailPasswordSent'].getHTML();
		}
		catch (e) {
		}

		try {
			document.<portlet:namespace />fm['<portlet:namespace />settings--adminEmailPasswordResetBody--'].value = window['<portlet:namespace />adminEmailPasswordReset'].getHTML();
		}
		catch (e) {
		}

		try {
			document.<portlet:namespace />fm['<portlet:namespace />settings--adminEmailVerificationBody--'].value = window['<portlet:namespace />adminEmailVerification'].getHTML();
		}
		catch (e) {
		}
	}
</aui:script>