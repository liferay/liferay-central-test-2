<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "email-from");

String redirect = ParamUtil.getString(request, "redirect");

String emailFromName = ParamUtil.getString(request, "emailFromName", WikiUtil.getEmailFromName(prefs));
String emailFromAddress = ParamUtil.getString(request, "emailFromAddress", WikiUtil.getEmailFromAddress(prefs));

String emailPageAddedSubjectPrefix = ParamUtil.getString(request, "emailPageAddedSubjectPrefix", WikiUtil.getEmailPageAddedSubjectPrefix(prefs));
String emailPageAddedBody = ParamUtil.getString(request, "emailPageAddedBody", WikiUtil.getEmailPageAddedBody(prefs));
String emailPageAddedSignature = ParamUtil.getString(request, "emailPageAddedSignature", WikiUtil.getEmailPageAddedSignature(prefs));

String emailPageUpdatedSubjectPrefix = ParamUtil.getString(request, "emailPageUpdatedSubjectPrefix", WikiUtil.getEmailPageUpdatedSubjectPrefix(prefs));
String emailPageUpdatedBody = ParamUtil.getString(request, "emailPageUpdatedBody", WikiUtil.getEmailPageUpdatedBody(prefs));
String emailPageUpdatedSignature = ParamUtil.getString(request, "emailPageUpdatedSignature", WikiUtil.getEmailPageUpdatedSignature(prefs));
%>

<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL" portletConfiguration="true">
	<portlet:param name="tabs2" value="<%= tabs2 %>" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</liferay-portlet:renderURL>

<script type="text/javascript">

	<%
	String bodyEditorParam = "";
	String bodyEditorBody = "";
	String signatureEditorParam = "";
	String signatureEditorBody = "";

	if (tabs2.equals("page-added-email")) {
		bodyEditorParam = "emailPageAddedBody";
		bodyEditorBody = emailPageAddedBody;
		signatureEditorParam = "emailPageAddedSignature";
		signatureEditorBody = emailPageAddedSignature;
	}
	else if (tabs2.equals("page-updated-email")) {
		bodyEditorParam = "emailPageUpdatedBody";
		bodyEditorBody = emailPageUpdatedBody;
		signatureEditorParam = "emailPageUpdatedSignature";
		signatureEditorBody = emailPageUpdatedSignature;
	}
	%>

	function <portlet:namespace />save() {
		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />save(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= tabs2 %>" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>" />

<liferay-ui:tabs
	names="email-from,page-added-email,page-updated-email"
	param="tabs2"
	url="<%= portletURL %>"
/>

<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
<liferay-ui:error key="emailPageAddedBody" message="please-enter-a-valid-body" />
<liferay-ui:error key="emailPageAddedSignature" message="please-enter-a-valid-signature" />
<liferay-ui:error key="emailPageAddedSubjectPrefix" message="please-enter-a-valid-subject" />
<liferay-ui:error key="emailPageUpdatedBody" message="please-enter-a-valid-body" />
<liferay-ui:error key="emailPageUpdatedSignature" message="please-enter-a-valid-signature" />
<liferay-ui:error key="emailPageUpdatedSubjectPrefix" message="please-enter-a-valid-subject" />

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

		<br />

		<b><liferay-ui:message key="definition-of-terms" /></b>

		<br /><br />

		<table class="lfr-table">
		<tr>
			<td>
				<b>[$COMPANY_ID$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-company-id-associated-with-the-wiki" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$COMPANY_MX$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-company-mx-associated-with-the-wiki" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$COMPANY_NAME$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-company-name-associated-with-the-wiki" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$COMMUNITY_NAME$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-community-name-associated-with-the-wiki" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$PAGE_USER_ADDRESS$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-email-address-of-the-user-who-added-the-page" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$PAGE_USER_NAME$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-user-who-added-the-page" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$PORTLET_NAME$]</b>
			</td>
			<td>
				<%= ((RenderResponseImpl)renderResponse).getTitle() %>
			</td>
		</tr>
		</table>
	</c:when>
	<c:when test='<%= tabs2.startsWith("page-") %>'>
		<table class="lfr-table">
		<tr>
			<td>
				<liferay-ui:message key="enabled" />
			</td>
			<td>
				<c:choose>
					<c:when test='<%= tabs2.equals("page-added-email") %>'>
						<liferay-ui:input-checkbox param="emailPageAddedEnabled" defaultValue="<%= WikiUtil.getEmailPageAddedEnabled(prefs) %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("page-updated-email") %>'>
						<liferay-ui:input-checkbox param="emailPageUpdatedEnabled" defaultValue="<%= WikiUtil.getEmailPageUpdatedEnabled(prefs) %>" />
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
				<liferay-ui:message key="subject-prefix" />
			</td>
			<td>
				<c:choose>
					<c:when test='<%= tabs2.equals("page-added-email") %>'>
						<input class="lfr-input-text" name="<portlet:namespace />emailPageAddedSubjectPrefix" type="text" value="<%= emailPageAddedSubjectPrefix %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("page-updated-email") %>'>
						<input class="lfr-input-text" name="<portlet:namespace />emailPageUpdatedSubjectPrefix" type="text" value="<%= emailPageUpdatedSubjectPrefix %>" />
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
				<textarea class="lfr-textarea" name="<%= bodyEditorParam %>" wrap="soft"><%= bodyEditorBody %></textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<br />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="signature" />
			</td>
			<td>
				<textarea class="lfr-textarea" name="<%= signatureEditorParam %>" wrap="soft"><%= signatureEditorBody %></textarea>
			</td>
		</tr>
		</table>

		<br />

		<b><liferay-ui:message key="definition-of-terms" /></b>

		<br /><br />

		<table class="lfr-table">
		<tr>
			<td>
				<b>[$COMPANY_ID$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-company-id-associated-with-the-wiki" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$COMPANY_MX$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-company-mx-associated-with-the-wiki" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$COMPANY_NAME$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-company-name-associated-with-the-wiki" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$COMMUNITY_NAME$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-community-name-associated-with-the-wiki" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$FROM_ADDRESS$]</b>
			</td>
			<td>
				<%= emailFromAddress %>
			</td>
		</tr>
		<tr>
			<td>
				<b>[$FROM_NAME$]</b>
			</td>
			<td>
				<%= emailFromName %>
			</td>
		</tr>
		<tr>
			<td>
				<b>[$NODE_NAME$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-node-in-which-the-page-was-added" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$PAGE_CONTENT$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-page-content" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$PAGE_ID$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-page-id" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$PAGE_TITLE$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-page-title" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$PAGE_URL$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-page-url" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$PAGE_USER_ADDRESS$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-email-address-of-the-user-who-added-the-page" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$PAGE_USER_NAME$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-user-who-added-the-page" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$PORTAL_URL$]</b>
			</td>
			<td>
				<%= company.getVirtualHost() %>
			</td>
		</tr>
		<tr>
			<td>
				<b>[$PORTLET_NAME$]</b>
			</td>
			<td>
				<%= ((RenderResponseImpl)renderResponse).getTitle() %>
			</td>
		</tr>
		<tr>
			<td>
				<b>[$TO_ADDRESS$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-address-of-the-email-recipient" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$TO_NAME$]</b>
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

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="self.location = '<%= redirect %>';" />

</form>