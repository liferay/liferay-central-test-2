<%
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
String portletResource = ParamUtil.getString(request, "portletResource");

PortletPreferences prefs = PortletPreferencesFactory.getPortletSetup(request, portletResource, false, true);

String emailFromName = ParamUtil.getString(request, "emailFromName", MBUtil.getEmailFromName(prefs));
String emailFromAddress = ParamUtil.getString(request, "emailFromAddress", MBUtil.getEmailFromAddress(prefs));

String emailMessageAddedSubject = ParamUtil.getString(request, "emailMessageAddedSubject", MBUtil.getEmailMessageAddedSubject(prefs));
String emailMessageAddedBody = ParamUtil.getString(request, "emailMessageAddedBody", MBUtil.getEmailMessageAddedBody(prefs));

String emailMessageUpdatedSubject = ParamUtil.getString(request, "emailMessageUpdatedSubject", MBUtil.getEmailMessageUpdatedSubject(prefs));
String emailMessageUpdatedBody = ParamUtil.getString(request, "emailMessageUpdatedBody", MBUtil.getEmailMessageUpdatedBody(prefs));

String tabs2 = ParamUtil.getString(request, "tabs2", "email-from");

String redirect = ParamUtil.getString(request, "redirect");
%>

<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL" portletConfiguration="true">
	<liferay-portlet:param name="tabs2" value="<%= tabs2 %>" />
	<liferay-portlet:param name="redirect" value="<%= redirect %>" />
</liferay-portlet:renderURL>

<script type="text/javascript">

	<%
	String editorParam = "";
	String editorContent = "";

	if (tabs2.equals("message-added-email")) {
		editorParam = "emailMessageAddedBody";
		editorContent = emailMessageAddedBody;
	}
	else if (tabs2.equals("message-updated-email")) {
		editorParam = "emailMessageUpdatedBody";
		editorContent = emailMessageUpdatedBody;
	}
	%>

	function initEditor() {
		return "<%= UnicodeFormatter.toString(editorContent) %>";
	}

	function <portlet:namespace />saveConfiguration() {
		<c:if test='<%= tabs2.startsWith("message-") %>'>
			document.<portlet:namespace />fm.<portlet:namespace /><%= editorParam %>.value = parent.<portlet:namespace />editor.getHTML();
		</c:if>

		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveConfiguration(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>">
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= tabs2 %>">
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>">

<liferay-ui:tabs
	names="email-from,message-added-email,message-updated-email"
	param="tabs2"
	url="<%= portletURL %>"
/>

<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
<liferay-ui:error key="emailMessageAddedBody" message="please-enter-a-valid-body" />
<liferay-ui:error key="emailMessageAddedSubject" message="please-enter-a-valid-subject" />
<liferay-ui:error key="emailMessageUpdatedBody" message="please-enter-a-valid-body" />
<liferay-ui:error key="emailMessageUpdatedSubject" message="please-enter-a-valid-subject" />

<c:choose>
	<c:when test='<%= tabs2.equals("email-from") %>'>
		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "name") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<input class="form-text" name="<portlet:namespace />emailFromName" size="<%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>" type="text" value="<%= emailFromName %>">
			</td>
		</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "address") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<input class="form-text" name="<portlet:namespace />emailFromAddress" size="<%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>" type="text" value="<%= emailFromAddress %>">
			</td>
		</tr>
		</table>
	</c:when>
	<c:when test='<%= tabs2.startsWith("message-") %>'>
		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "enabled") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<c:choose>
					<c:when test='<%= tabs2.equals("message-added-email") %>'>
						<liferay-ui:input-checkbox param="emailMessageAddedEnabled" defaultValue="<%= MBUtil.getEmailMessageAddedEnabled(prefs) %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("message-updated-email") %>'>
						<liferay-ui:input-checkbox param="emailMessageUpdatedEnabled" defaultValue="<%= MBUtil.getEmailMessageUpdatedEnabled(prefs) %>" />
					</c:when>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<br>
			</td>
		</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "subject") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<c:choose>
					<c:when test='<%= tabs2.equals("message-added-email") %>'>
						<input class="form-text" name="<portlet:namespace />emailMessageAddedSubject" size="<%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>" type="text" value="<%= emailMessageAddedSubject %>">
					</c:when>
					<c:when test='<%= tabs2.equals("message-updated-email") %>'>
						<input class="form-text" name="<portlet:namespace />emailMessageUpdatedSubject" size="<%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>" type="text" value="<%= emailMessageUpdatedSubject %>">
					</c:when>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<br>
			</td>
		</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "body") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<iframe frameborder="0" height="400" id="<portlet:namespace />editor" name="<portlet:namespace />editor" scrolling="no" src="<%= themeDisplay.getPathJavaScript() %>/editor/editor.jsp?p_l_id=<%= plid %>&editorImpl=<%= PropsUtil.get(EDITOR_WYSIWYG_IMPL_KEY) %>" width="640"></iframe>

				<input name="<portlet:namespace /><%= editorParam %>" type="hidden" value="">
			</td>
		</tr>
		</table>

		<br>

		<b><%= LanguageUtil.get(pageContext, "definition-of-terms") %></b>

		<br><br>

		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<b>[$FROM_ADDRESS$]</b>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<%= emailFromAddress %>
			</td>
		</tr>
		<tr>
			<td>
				<b>[$FROM_NAME$]</b>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<%= emailFromName %>
			</td>
		</tr>
		<tr>
			<td>
				<b>[$MESSAGE_BODY$]</b>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				The message body
			</td>
		</tr>
		<tr>
			<td>
				<b>[$MESSAGE_SUBJECT$]</b>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				The message subject
			</td>
		</tr>
		<tr>
			<td>
				<b>[$MESSAGE_USER_NAME$]</b>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				The user who added the message
			</td>
		</tr>
		<tr>
			<td>
				<b>[$PORTAL_URL$]</b>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<%= company.getPortalURL() %>
			</td>
		</tr>
		<tr>
			<td>
				<b>[$PORTLET_NAME$]</b>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<%= ((RenderResponseImpl)renderResponse).getTitle() %>
			</td>
		</tr>
		<tr>
			<td>
				<b>[$TO_ADDRESS$]</b>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				The address of the email recipient
			</td>
		</tr>
		<tr>
			<td>
				<b>[$TO_NAME$]</b>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				The name of the email recipient
			</td>
		</tr>
		</table>
	</c:when>
</c:choose>

<br>

<input class="portlet-form-button" type="submit" value='<%= LanguageUtil.get(pageContext, "save") %>'>

<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="self.location = '<%= redirect %>';">

</form>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.message_boards.edit_configuration.jsp";
%>