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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "email-from");

String redirect = ParamUtil.getString(request, "redirect");

String portletResource = ParamUtil.getString(request, "portletResource");

PortletPreferences portletSetup = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);

String emailFromName = ParamUtil.getString(request, "emailFromName", JournalUtil.getEmailFromName(portletSetup));
String emailFromAddress = ParamUtil.getString(request, "emailFromAddress", JournalUtil.getEmailFromAddress(portletSetup));

String emailArticleApprovalDeniedSubject = ParamUtil.getString(request, "emailArticleApprovalDeniedSubject", JournalUtil.getEmailArticleApprovalDeniedSubject(portletSetup));
String emailArticleApprovalDeniedBody = ParamUtil.getString(request, "emailArticleApprovalDeniedBody", JournalUtil.getEmailArticleApprovalDeniedBody(portletSetup));

String emailArticleApprovalGrantedSubject = ParamUtil.getString(request, "emailArticleApprovalGrantedSubject", JournalUtil.getEmailArticleApprovalGrantedSubject(portletSetup));
String emailArticleApprovalGrantedBody = ParamUtil.getString(request, "emailArticleApprovalGrantedBody", JournalUtil.getEmailArticleApprovalGrantedBody(portletSetup));

String emailArticleApprovalRequestedSubject = ParamUtil.getString(request, "emailArticleApprovalRequestedSubject", JournalUtil.getEmailArticleApprovalRequestedSubject(portletSetup));
String emailArticleApprovalRequestedBody = ParamUtil.getString(request, "emailArticleApprovalRequestedBody", JournalUtil.getEmailArticleApprovalRequestedBody(portletSetup));

String emailArticleReviewSubject = ParamUtil.getString(request, "emailArticleReviewSubject", JournalUtil.getEmailArticleReviewSubject(portletSetup));
String emailArticleReviewBody = ParamUtil.getString(request, "emailArticleReviewBody", JournalUtil.getEmailArticleReviewBody(portletSetup));
%>

<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL" portletConfiguration="true">
	<portlet:param name="tabs2" value="<%= tabs2 %>" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</liferay-portlet:renderURL>

<script type="text/javascript">

	<%
	String editorParam = "";
	String editorContent = "";

	if (tabs2.equals("web-content-approval-denied-email")) {
		editorParam = "emailArticleApprovalDeniedBody";
		editorContent = emailArticleApprovalDeniedBody;
	}
	else if (tabs2.equals("web-content-approval-granted-email")) {
		editorParam = "emailArticleApprovalGrantedBody";
		editorContent = emailArticleApprovalGrantedBody;
	}
	else if (tabs2.equals("web-content-approval-requested-email")) {
		editorParam = "emailArticleApprovalRequestedBody";
		editorContent = emailArticleApprovalRequestedBody;
	}
	else if (tabs2.equals("web-content-review-email")) {
		editorParam = "emailArticleReviewBody";
		editorContent = emailArticleReviewBody;
	}
	%>

	function <portlet:namespace />initEditor() {
		return "<%= UnicodeFormatter.toString(editorContent) %>";
	}

	function <portlet:namespace />saveConfiguration() {
		<c:if test='<%= tabs2.startsWith("article-approval-") || tabs2.startsWith("article-review-") %>'>
			document.<portlet:namespace />fm.<portlet:namespace /><%= editorParam %>.value = window.<portlet:namespace />editor.getHTML();
		</c:if>

		submitForm(document.<portlet:namespace />fm);
	}
</script>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "saveConfiguration(); return false;" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<liferay-ui:tabs
		names="email-from,web-content-approval-denied-email,web-content-approval-granted-email,web-content-approval-requested-email,web-content-review-email"
		param="tabs2"
		url="<%= portletURL %>"
	/>

	<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
	<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
	<liferay-ui:error key="emailArticleApprovalDeniedBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailArticleApprovalDeniedSubject" message="please-enter-a-valid-subject" />
	<liferay-ui:error key="emailArticleApprovalGrantedBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailArticleApprovalGrantedSubject" message="please-enter-a-valid-subject" />
	<liferay-ui:error key="emailArticleApprovalRequestedBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailArticleApprovalRequestedSubject" message="please-enter-a-valid-subject" />
	<liferay-ui:error key="emailArticleReviewBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailArticleReviewSubject" message="please-enter-a-valid-subject" />

	<c:choose>
		<c:when test='<%= tabs2.equals("email-from") %>'>
			<aui:fieldset>
				<aui:input cssClass="lfr-input-text-container" label="name" name="emailFromName" type="text" value="<%= emailFromName %>" />

				<aui:input cssClass="lfr-input-text-container" label="address" name="emailFromAddress" type="text" value="<%= emailFromAddress %>" />
			</aui:fieldset>
		</c:when>
		<c:when test='<%= tabs2.startsWith("web-content-approval-") || tabs2.startsWith("web-content-review-") %>'>
			<aui:fieldset>
				<c:choose>
					<c:when test='<%= tabs2.equals("web-content-approval-denied-email") %>'>
						<aui:input inlineLabel="left" label="enabled" name="emailArticleApprovalDeniedEnabled" type="checkbox" value="<%= JournalUtil.getEmailArticleApprovalDeniedEnabled(portletSetup) %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-approval-granted-email") %>'>
						<aui:input inlineLabel="left" label="enabled" name="emailArticleApprovalGrantedEnabled" type="checkbox" value="<%= JournalUtil.getEmailArticleApprovalGrantedEnabled(portletSetup) %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-approval-requested-email") %>'>
						<aui:input inlineLabel="left" label="enabled" name="emailArticleApprovalRequestedEnabled" type="checkbox" value="<%= JournalUtil.getEmailArticleApprovalRequestedEnabled(portletSetup) %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-review-email") %>'>
						<aui:input inlineLabel="left" label="enabled" name="emailArticleReviewEnabled" type="checkbox" value="<%= JournalUtil.getEmailArticleReviewEnabled(portletSetup) %>" />
					</c:when>
				</c:choose>

				<c:choose>
					<c:when test='<%= tabs2.equals("web-content-approval-denied-email") %>'>
						<aui:input cssClass="lfr-input-text-container" label="subject" name="emailArticleApprovalDeniedSubject" type="text" value="<%= emailArticleApprovalDeniedSubject %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-approval-granted-email") %>'>
						<aui:input cssClass="lfr-input-text-container" label="subject" name="emailArticleApprovalGrantedSubject" type="text" value="<%= emailArticleApprovalGrantedSubject %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-approval-requested-email") %>'>
						<aui:input cssClass="lfr-input-text-container" label="subject" name="emailArticleApprovalRequestedSubject" type="text" value="<%= emailArticleApprovalRequestedSubject %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-review-email") %>'>
						<aui:input cssClass="lfr-input-text-container" label="subject" name="emailArticleReviewSubject" type="text" value="<%= emailArticleReviewSubject %>" />
					</c:when>
				</c:choose>

				<aui:field-wrapper label="body">
					<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" />

					<aui:input name="<%= editorParam %>" type="hidden" />
				</aui:field-wrapper>
			</aui:fieldset>

			<strong><liferay-ui:message key="definition-of-terms" /></strong>

			<br /><br />

			<table class="lfr-table">
			<tr>
				<td>
					<strong>[$ARTICLE_ID$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-web-content-id" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$ARTICLE_TITLE$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-web-content-title" />
				</td>
			</tr>

			<c:if test='<%= tabs2.startsWith("web-content-approval-") %>'>
				<tr>
					<td>
						<strong>[$ARTICLE_URL$]</strong>
					</td>
					<td>
						<liferay-ui:message key="the-web-content-url" />
					</td>
				</tr>
			</c:if>

			<tr>
				<td>
					<strong>[$ARTICLE_VERSION$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-web-content-version" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$FROM_ADDRESS$]</strong>
				</td>
				<td>
					<%= emailFromAddress %>
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$FROM_NAME$]</strong>
				</td>
				<td>
					<%= emailFromName %>
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$PORTAL_URL$]</strong>
				</td>
				<td>
					<%= company.getVirtualHost() %>
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$PORTLET_NAME$]</strong>
				</td>
				<td>
					<%= ((RenderResponseImpl)renderResponse).getTitle() %>
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$TO_ADDRESS$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-address-of-the-email-recipient" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$TO_NAME$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-name-of-the-email-recipient" />
				</td>
			</tr>
			</table>
		</c:when>
	</c:choose>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.journal.edit_configuration.jsp";
%>