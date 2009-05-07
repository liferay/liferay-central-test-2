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

boolean adminEmailUserAddedEnable = ParamUtil.getBoolean(request, "emailUserAddedEnable",  PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_USER_ADDED_ENABLED));
String adminEmailUserAddedSubject = ParamUtil.getString(request, "emailUserAddedSubject", PrefsPropsUtil.getContent(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_USER_ADDED_SUBJECT));
String adminEmailUserAddedBody = ParamUtil.getString(request, "emailUserAddedBody", PrefsPropsUtil.getContent(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_USER_ADDED_BODY));

boolean adminEmailPasswordSentEnable = ParamUtil.getBoolean(request, "emailPasswordSentEnable",  PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_ENABLED));
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
		<fieldset class="exp-block-labels">
			<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />settings(<%= PropsKeys.ADMIN_EMAIL_FROM_NAME %>)"><liferay-ui:message key="name" /></label>

				<input class="lfr-input-text" name="<portlet:namespace />settings(<%= PropsKeys.ADMIN_EMAIL_FROM_NAME %>)" type="text" value="<%= adminEmailFromName %>" />
			</div>

			<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />settings(<%= PropsKeys.ADMIN_EMAIL_FROM_ADDRESS %>)"><liferay-ui:message key="address" /></label>

				<input class="lfr-input-text" name="<portlet:namespace />settings(<%= PropsKeys.ADMIN_EMAIL_FROM_ADDRESS %>)" type="text" value="<%= adminEmailFromAddress %>" />
			</div>
		</fieldset>
	</liferay-ui:section>

	<liferay-ui:section>
		<fieldset class="exp-block-labels">
			<div class="exp-ctrl-holder">
				<label class="inline-label" for="<portlet:namespace />settings(<%= PropsKeys.ADMIN_EMAIL_USER_ADDED_ENABLED %>)"><liferay-ui:message key="enabled" /></label>

				<liferay-ui:input-checkbox param='<%= "settings(" + PropsKeys.ADMIN_EMAIL_USER_ADDED_ENABLED + ")" %>' defaultValue="<%= adminEmailUserAddedEnable %>" />
			</div>

			<liferay-ui:error key="emailUserAddedSubject" message="please-enter-a-valid-subject" />

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />settings(<%= PropsKeys.ADMIN_EMAIL_USER_ADDED_SUBJECT %>)"><liferay-ui:message key="subject" /></label>

				<input class="lfr-input-text" name="<portlet:namespace />settings(<%= PropsKeys.ADMIN_EMAIL_USER_ADDED_SUBJECT %>)" type="text" value="<%= adminEmailUserAddedSubject %>" />
			</div>

			<liferay-ui:error key="emailUserAddedBody" message="please-enter-a-valid-body" />

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />settings(<%= PropsKeys.ADMIN_EMAIL_USER_ADDED_BODY %>)"><liferay-ui:message key="body" /></label>

				<liferay-ui:input-editor name="emailUserAddedBody" editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" toolbarSet="email" initMethod='<%= renderResponse.getNamespace() + "initEmailUserAddedBodyEditor" %>' width="470" />

				<input id="adminEmailUserAddedBody" name="<portlet:namespace />settings(<%= PropsKeys.ADMIN_EMAIL_USER_ADDED_BODY %>)" type="hidden" value="<%= HtmlUtil.escape(adminEmailUserAddedBody) %>" />
			</div>

			<div class="terms email-user-add">
				<%@ include file="/html/portlet/enterprise_admin/settings/definition_of_terms.jspf" %>
			</div>
		</fieldset>
	</liferay-ui:section>

	<liferay-ui:section>
		<fieldset class="exp-block-labels">
			<div class="exp-ctrl-holder">
				<label class="inline-label" for="<portlet:namespace />settings(<%= PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_ENABLED %>)"><liferay-ui:message key="enabled" /></label>

				<liferay-ui:input-checkbox param='<%= "settings(" + PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_ENABLED + ")" %>' defaultValue="<%= adminEmailPasswordSentEnable %>" />
			</div>

			<liferay-ui:error key="emailPasswordSentSubject" message="please-enter-a-valid-subject" />

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />settings(<%= PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_SUBJECT %>)"><liferay-ui:message key="subject" /></label>

				<input class="lfr-input-text" name="<portlet:namespace />settings(<%= PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_SUBJECT %>)" type="text" value="<%= adminEmailPasswordSentSubject %>" />
			</div>

			<liferay-ui:error key="emailPasswordSentBody" message="please-enter-a-valid-body" />

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />settings(<%= PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_BODY %>)"><liferay-ui:message key="body" /></label>

				<liferay-ui:input-editor name="emailPasswordSentBody" editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>"  toolbarSet="email" initMethod='<%= renderResponse.getNamespace() + "initEmailPasswordSentBodyEditor" %>' width="470" />

				<input id="adminEmailPasswordSentBody" name="<portlet:namespace />settings(<%= PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_BODY %>)" type="hidden" value="<%= HtmlUtil.escape(adminEmailPasswordSentBody) %>" />
			</div>

			<div class="terms email-password-sent">
				<%@ include file="/html/portlet/enterprise_admin/settings/definition_of_terms.jspf" %>
			</div>
		</fieldset>
	</liferay-ui:section>
</liferay-ui:tabs>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.enterprise_admin.view.jsp";
%>