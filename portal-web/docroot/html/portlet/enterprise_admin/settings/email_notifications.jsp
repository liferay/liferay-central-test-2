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

<h3><liferay-ui:message key="email-notifications" /></h3>

<%
String adminEmailFromName = ParamUtil.getString(request, "settings(" + PropsKeys.ADMIN_EMAIL_FROM_NAME + ")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_NAME));
String adminEmailFromAddress = ParamUtil.getString(request, "settings(" + PropsKeys.ADMIN_EMAIL_FROM_ADDRESS + ")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_ADDRESS));

boolean adminEmailUserAddedEnable = ParamUtil.getBoolean(request, "emailUserAddedEnable", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_USER_ADDED_ENABLED));
String adminEmailUserAddedSubject = ParamUtil.getString(request, "emailUserAddedSubject", PrefsPropsUtil.getContent(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_USER_ADDED_SUBJECT));
String adminEmailUserAddedBody = ParamUtil.getString(request, "emailUserAddedBody", PrefsPropsUtil.getContent(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_USER_ADDED_BODY));

boolean adminEmailPasswordSentEnable = ParamUtil.getBoolean(request, "emailPasswordSentEnable", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_ENABLED));
String adminEmailPasswordSentSubject = ParamUtil.getString(request, "emailPasswordSentSubject", PrefsPropsUtil.getContent(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_SUBJECT));
String adminEmailPasswordSentBody = ParamUtil.getString(request, "emailPasswordSentBody", PrefsPropsUtil.getContent(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_BODY));
%>

<script type="text/javascript">
	function <portlet:namespace />initEmailUserAddedBodyEditor() {
		return "<%= UnicodeFormatter.toString(adminEmailUserAddedBody) %>";
	}

	function <portlet:namespace />initEmailPasswordSentBodyEditor() {
		return "<%= UnicodeFormatter.toString(adminEmailPasswordSentBody) %>";
	}

	function <portlet:namespace />saveEmails() {
		try {
			document.<portlet:namespace />fm['<portlet:namespace />settings(<%= PropsKeys.ADMIN_EMAIL_USER_ADDED_BODY %>)'].value = window.emailUserAddedBody.getHTML();
			document.<portlet:namespace />fm['<portlet:namespace />settings(<%= PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_BODY %>)'].value = window.emailPasswordSentBody.getHTML();
		}
		catch(error) {
		}
	}
</script>

<liferay-ui:error-marker key="errorSection" value="email_notifications" />

<liferay-ui:tabs
	names="sender,account-created-notification,password-changed-notification"
	refresh="<%= false %>"
>
	<liferay-ui:section>
		<aui:fieldset>
			<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />

			<aui:input cssClass="lfr-input-text-container" label="name" name='<%= "settings(" + PropsKeys.ADMIN_EMAIL_FROM_NAME + ")" %>' type="text" value="<%= adminEmailFromName %>" />

			<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />

			<aui:input cssClass="lfr-input-text-container" label="address" name='<%= "settings(" + PropsKeys.ADMIN_EMAIL_FROM_ADDRESS + ")" %>' type="text" value="<%= adminEmailFromAddress %>" />
		</aui:fieldset>
	</liferay-ui:section>

	<liferay-ui:section>
		<aui:fieldset>
			<aui:input inlineLabel="left" label="enabled" name='<%= "settings(" + PropsKeys.ADMIN_EMAIL_USER_ADDED_ENABLED + ")" %>' type="checkbox" value="<%= adminEmailUserAddedEnable %>" />

			<liferay-ui:error key="emailUserAddedSubject" message="please-enter-a-valid-subject" />

			<aui:input cssClass="lfr-input-text-container" label="subject" name='<%= "settings(" + PropsKeys.ADMIN_EMAIL_USER_ADDED_SUBJECT + ")" %>' type="text" value="<%= adminEmailUserAddedSubject %>" />

			<liferay-ui:error key="emailUserAddedBody" message="please-enter-a-valid-body" />

			<aui:field-wrapper label="body">
				<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" initMethod='<%= renderResponse.getNamespace() + "initEmailUserAddedBodyEditor" %>' name="emailUserAddedBody" toolbarSet="email" width="470" />

				<aui:input name='<%= "settings(" + PropsKeys.ADMIN_EMAIL_USER_ADDED_BODY + ")" %>' type="hidden" value="<%= adminEmailUserAddedBody %>" />
			</aui:field-wrapper>

			<div class="terms email-user-add">
				<%@ include file="/html/portlet/enterprise_admin/settings/definition_of_terms.jspf" %>
			</div>
		</aui:fieldset>
	</liferay-ui:section>

	<liferay-ui:section>
		<aui:fieldset>
			<aui:input inlineLabel="left" label="enabled" name='<%= "settings(" + PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_ENABLED + ")" %>' type="checkbox" value="<%= adminEmailPasswordSentEnable %>" />

			<liferay-ui:error key="emailPasswordSentSubject" message="please-enter-a-valid-subject" />

			<aui:input cssClass="lfr-input-text-container" label="subject" name='<%= "settings(" + PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_SUBJECT + ")" %>' type="text" value="<%= adminEmailPasswordSentSubject %>" />

			<liferay-ui:error key="emailPasswordSentBody" message="please-enter-a-valid-body" />

			<aui:field-wrapper label="body">
				<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" initMethod='<%= renderResponse.getNamespace() + "initEmailPasswordSentBodyEditor" %>' name="emailPasswordSentBody" toolbarSet="email" width="470" />

				<aui:input name='<%= "settings(" + PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_BODY + ")" %>' type="hidden" value="<%= adminEmailPasswordSentBody %>" />
			</aui:field-wrapper>

			<div class="terms email-password-sent">
				<%@ include file="/html/portlet/enterprise_admin/settings/definition_of_terms.jspf" %>
			</div>
		</aui:fieldset>
	</liferay-ui:section>
</liferay-ui:tabs>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.enterprise_admin.view.jsp";
%>