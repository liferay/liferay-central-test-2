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

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveConfiguration(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= HtmlUtil.escapeAttribute(tabs2) %>" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(redirect) %>" />

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
		<table class="lfr-table">
		<tr>
			<td>
				<liferay-ui:message key="name" />
			</td>
			<td>
				<input class="lfr-input-text" name="<portlet:namespace />emailFromName" type="text" value="<%= emailFromName %>" />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="address" />
			</td>
			<td>
				<input class="lfr-input-text" name="<portlet:namespace />emailFromAddress" type="text" value="<%= emailFromAddress %>" />
			</td>
		</tr>
		</table>
	</c:when>
	<c:when test='<%= tabs2.startsWith("article-approval-") || tabs2.startsWith("article-review-") %>'>
		<table class="lfr-table">
		<tr>
			<td>
				<liferay-ui:message key="enabled" />
			</td>
			<td>
				<c:choose>
					<c:when test='<%= tabs2.equals("web-content-approval-denied-email") %>'>
						<liferay-ui:input-checkbox param="emailArticleApprovalDeniedEnabled" defaultValue="<%= JournalUtil.getEmailArticleApprovalDeniedEnabled(portletSetup) %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-approval-granted-email") %>'>
						<liferay-ui:input-checkbox param="emailArticleApprovalGrantedEnabled" defaultValue="<%= JournalUtil.getEmailArticleApprovalGrantedEnabled(portletSetup) %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-approval-requested-email") %>'>
						<liferay-ui:input-checkbox param="emailArticleApprovalRequestedEnabled" defaultValue="<%= JournalUtil.getEmailArticleApprovalRequestedEnabled(portletSetup) %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-review-email") %>'>
						<liferay-ui:input-checkbox param="emailArticleReviewEnabled" defaultValue="<%= JournalUtil.getEmailArticleReviewEnabled(portletSetup) %>" />
					</c:when>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<br />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="subject" />
			</td>
			<td>
				<c:choose>
					<c:when test='<%= tabs2.equals("web-content-approval-denied-email") %>'>
						<input class="lfr-input-text" name="<portlet:namespace />emailArticleApprovalDeniedSubject" type="text" value="<%= emailArticleApprovalDeniedSubject %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-approval-granted-email") %>'>
						<input class="lfr-input-text" name="<portlet:namespace />emailArticleApprovalGrantedSubject" type="text" value="<%= emailArticleApprovalGrantedSubject %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-approval-requested-email") %>'>
						<input class="lfr-input-text" name="<portlet:namespace />emailArticleApprovalRequestedSubject" type="text" value="<%= emailArticleApprovalRequestedSubject %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-review-email") %>'>
						<input class="lfr-input-text" name="<portlet:namespace />emailArticleReviewSubject" type="text" value="<%= emailArticleReviewSubject %>" />
					</c:when>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<br />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="body" />
			</td>
			<td>
				<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" />

				<input name="<portlet:namespace /><%= editorParam %>" type="hidden" value="" />
			</td>
		</tr>
		</table>

		<br />

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

<br />

<input type="submit" value="<liferay-ui:message key="save" />" />

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />

</form>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.journal.edit_configuration.jsp";
%>