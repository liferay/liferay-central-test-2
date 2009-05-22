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
boolean termsOfUseRequired = ParamUtil.getBoolean(request, "settings(" + PropsKeys.TERMS_OF_USE_REQUIRED +")", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.TERMS_OF_USE_REQUIRED, PropsValues.TERMS_OF_USE_REQUIRED));
boolean usersScreenNameAlwaysAutogenerate = ParamUtil.getBoolean(request, "settings(" + PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE +")", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE, PropsValues.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE));
boolean fieldEnableMale = ParamUtil.getBoolean(request, "settings(" + PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_MALE +")", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_MALE, PropsValues.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_MALE));
boolean fieldEnableBirthday = ParamUtil.getBoolean(request, "settings(" + PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_BIRTHDAY +")", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_BIRTHDAY, PropsValues.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_BIRTHDAY));

String adminReservedScreenNames = ParamUtil.getString(request, "settings(" + PropsKeys.ADMIN_RESERVED_SCREEN_NAMES +")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_RESERVED_SCREEN_NAMES));
String adminReservedEmailAddresses = ParamUtil.getString(request, "settings(" + PropsKeys.ADMIN_RESERVED_EMAIL_ADDRESSES +")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_RESERVED_EMAIL_ADDRESSES));
String adminDefaultGroupNames = ParamUtil.getString(request, "settings(" + PropsKeys.ADMIN_DEFAULT_GROUP_NAMES + ")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_DEFAULT_GROUP_NAMES));
String adminDefaultRoleNames = ParamUtil.getString(request, "settings(" + PropsKeys.ADMIN_DEFAULT_ROLE_NAMES + ")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_DEFAULT_ROLE_NAMES));
String adminDefaultUserGroupNames = ParamUtil.getString(request, "settings(" + PropsKeys.ADMIN_DEFAULT_USER_GROUP_NAMES + ")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_DEFAULT_USER_GROUP_NAMES));
%>

<h3><liferay-ui:message key="users" /></h3>

<liferay-ui:tabs
	names="fields,reserved-credentials,default-user-associations"
	refresh="<%= false %>"
>
	<liferay-ui:section>
		<fieldset class="exp-block-labels">
			<div class="exp-ctrl-holder">
				<label class="inline-label" for="<portlet:namespace />settings(<%= PropsKeys.TERMS_OF_USE_REQUIRED %>)"><liferay-ui:message key="terms-of-use-required" /></label>

				<liferay-ui:input-checkbox param='<%= "settings(" + PropsKeys.TERMS_OF_USE_REQUIRED + ")" %>' defaultValue="<%= termsOfUseRequired %>" />
			</div>

			<div class="exp-ctrl-holder">
				<label class="inline-label" for="<portlet:namespace />settings(<%= PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE %>)"><liferay-ui:message key="autogenerate-user-screen-names" /></label>

				<liferay-ui:input-checkbox param='<%= "settings(" + PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE + ")" %>' defaultValue="<%= usersScreenNameAlwaysAutogenerate %>" />
			</div>

			<div class="exp-ctrl-holder">
				<label class="inline-label" for="<portlet:namespace />settings(<%= PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_BIRTHDAY %>)"><liferay-ui:message key="enable-birthday" /></label>

				<liferay-ui:input-checkbox param='<%= "settings(" + PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_BIRTHDAY + ")" %>' defaultValue="<%= fieldEnableBirthday %>" />
			</div>

			<div class="exp-ctrl-holder">
				<label class="inline-label" for="<portlet:namespace />settings(<%= PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_MALE %>)"><liferay-ui:message key="enable-gender" /></label>

				<liferay-ui:input-checkbox param='<%= "settings(" + PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_MALE + ")" %>' defaultValue="<%= fieldEnableMale %>" />
			</div>
		</fieldset>
	</liferay-ui:section>

	<liferay-ui:section>
		<fieldset class="exp-block-labels">
			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />settings(<%= PropsKeys.ADMIN_RESERVED_SCREEN_NAMES %>)"><liferay-ui:message key="screen-names" /> <liferay-ui:icon-help message="enter-one-screen-name-per-line-to-reserve-the-screen-name" /></label>

				<textarea class="lfr-textarea" name="<portlet:namespace />settings(<%= PropsKeys.ADMIN_RESERVED_SCREEN_NAMES %>)"><%= HtmlUtil.escape(adminReservedScreenNames) %></textarea>
			</div>

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />settings(<%= PropsKeys.ADMIN_RESERVED_EMAIL_ADDRESSES %>)"><liferay-ui:message key="email-addresses" /> <liferay-ui:icon-help message="enter-one-screen-name-per-line-to-reserve-the-screen-name" /></label>

				<textarea class="lfr-textarea" name="<portlet:namespace />settings(<%= PropsKeys.ADMIN_RESERVED_EMAIL_ADDRESSES %>)"><%= HtmlUtil.escape(adminReservedEmailAddresses) %></textarea>
			</div>
		</fieldset>
	</liferay-ui:section>

	<liferay-ui:section>
		<fieldset class="exp-block-labels">
			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />settings(<%= PropsKeys.ADMIN_DEFAULT_GROUP_NAMES %>)"><liferay-ui:message key="communities" /> <liferay-ui:icon-help message="enter-the-default-community-names-per-line-that-are-associated-with-newly-created-users" /></label>

				<textarea class="lfr-textarea" name="<portlet:namespace />settings(<%= PropsKeys.ADMIN_DEFAULT_GROUP_NAMES %>)"><%= HtmlUtil.escape(adminDefaultGroupNames) %></textarea>
			</div>

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />settings(<%= PropsKeys.ADMIN_DEFAULT_ROLE_NAMES %>)"><liferay-ui:message key="roles" /> <liferay-ui:icon-help message="enter-the-default-role-names-per-line-that-are-associated-with-newly-created-users" /></label>

				<textarea class="lfr-textarea" name="<portlet:namespace />settings(<%= PropsKeys.ADMIN_DEFAULT_ROLE_NAMES %>)"><%= HtmlUtil.escape(adminDefaultRoleNames) %></textarea>
			</div>

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />settings(<%= PropsKeys.ADMIN_DEFAULT_USER_GROUP_NAMES %>)"><liferay-ui:message key="user-groups" /> <liferay-ui:icon-help message="enter-the-default-user-group-names-per-line-that-are-associated-with-newly-created-users" /></label>

				<textarea class="lfr-textarea" name="<portlet:namespace />settings(<%= PropsKeys.ADMIN_DEFAULT_USER_GROUP_NAMES %>)"><%= HtmlUtil.escape(adminDefaultUserGroupNames) %></textarea>
			</div>
		</fieldset>
	</liferay-ui:section>
</liferay-ui:tabs>