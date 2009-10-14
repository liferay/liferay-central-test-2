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

<%@ include file="/html/portlet/calendar/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "email-from");

String redirect = ParamUtil.getString(request, "redirect");

String emailFromName = ParamUtil.getString(request, "emailFromName", CalUtil.getEmailFromName(preferences));
String emailFromAddress = ParamUtil.getString(request, "emailFromAddress", CalUtil.getEmailFromAddress(preferences));

String emailEventReminderSubject = ParamUtil.getString(request, "emailEventReminderSubject", CalUtil.getEmailEventReminderSubject(preferences));
String emailEventReminderBody = ParamUtil.getString(request, "emailEventReminderBody", CalUtil.getEmailEventReminderBody(preferences));
%>

<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL" portletConfiguration="true">
	<portlet:param name="tabs2" value="<%= tabs2 %>" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</liferay-portlet:renderURL>

<script type="text/javascript">

	<%
	String editorParam = "emailEventReminderBody";
	String editorContent = emailEventReminderBody;
	%>

	function <portlet:namespace />initEditor() {
		return "<%= UnicodeFormatter.toString(editorContent) %>";
	}

	function <portlet:namespace />saveConfiguration() {
		<c:if test='<%= tabs2.equals("event-reminder-email") %>'>
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
	names="email-from,event-reminder-email,display-settings"
	param="tabs2"
	url="<%= portletURL %>"
/>

<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
<liferay-ui:error key="emailEventReminderBody" message="please-enter-a-valid-body" />
<liferay-ui:error key="emailEventReminderSubject" message="please-enter-a-valid-subject" />

<c:choose>
	<c:when test='<%= tabs2.equals("email-from") %>'>
		<table class="lfr-table">
		<tr>
			<td class="lfr-label">
				<liferay-ui:message key="name" />
			</td>
			<td>
				<input class="lfr-input-text" name="<portlet:namespace />emailFromName" type="text" value="<%= HtmlUtil.escape(emailFromName) %>" />
			</td>
		</tr>
		<tr>
			<td class="lfr-label">
				<liferay-ui:message key="address" />
			</td>
			<td>
				<input class="lfr-input-text" name="<portlet:namespace />emailFromAddress" type="text" value="<%= HtmlUtil.escape(emailFromAddress) %>" />
			</td>
		</tr>
		</table>
	</c:when>
	<c:when test='<%= tabs2.equals("event-reminder-email") %>'>
		<table class="lfr-table">
		<tr>
			<td class="lfr-label">
				<liferay-ui:message key="enabled" />
			</td>
			<td>
				<liferay-ui:input-checkbox param="emailEventReminderEnabled" defaultValue="<%= CalUtil.getEmailEventReminderEnabled(preferences) %>" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<br />
			</td>
		</tr>
		<tr>
			<td class="lfr-label">
				<liferay-ui:message key="subject" />
			</td>
			<td>
				<input class="lfr-input-text" name="<portlet:namespace />emailEventReminderSubject" type="text" value="<%= HtmlUtil.escape(emailEventReminderSubject) %>" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<br />
			</td>
		</tr>
		<tr>
			<td class="lfr-label">
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
				<strong>[$EVENT_START_DATE$]</strong>
			</td>
			<td>
				The event start date
			</td>
		</tr>
		<tr>
			<td>
				<strong>[$EVENT_TITLE$]</strong>
			</td>
			<td>
				The event title
			</td>
		</tr>
		<tr>
			<td>
				<strong>[$FROM_ADDRESS$]</strong>
			</td>
			<td>
				<%= HtmlUtil.escape(emailFromAddress) %>
			</td>
		</tr>
		<tr>
			<td>
				<strong>[$FROM_NAME$]</strong>
			</td>
			<td>
				<%= HtmlUtil.escape(emailFromName) %>
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
				The address of the email recipient
			</td>
		</tr>
		<tr>
			<td>
				<strong>[$TO_NAME$]</strong>
			</td>
			<td>
				The name of the email recipient
			</td>
		</tr>
		</table>
	</c:when>
	<c:when test='<%= tabs2.equals("display-settings") %>'>
		<fieldset>
			<legend><liferay-ui:message key="default-tab" /></legend>

			<table class="lfr-table">
			<tr>
				<td class="lfr-label">
					<liferay-ui:message key="default-tab" />
				</td>
				<td>
					<select name="<portlet:namespace />tabs1Default">

						<%
						for (String tabs1Name : tabs1NamesArray) {
						%>

							<option <%= tabs1Default.equals(tabs1Name) ? "selected" : "" %> value="<%= tabs1Name %>"><liferay-ui:message key="<%= tabs1Name %>" /></option>

						<%
						}
						%>
					</select>
				</td>
			</tr>
			</table>
		</fieldset>

		<br />

		<fieldset>
			<legend><liferay-ui:message key="summary-tab" /></legend>

			<table class="lfr-table">
			<tr>
				<td class="lfr-label">
					<liferay-ui:message key="orientation" />
				</td>
				<td>
					<select name="<portlet:namespace />summaryTabOrientation" size="1">
						<option <%= summaryTabOrientation.equals("horizontal") ? "selected" : "" %>value="horizontal"><liferay-ui:message key="horizontal" /></option>
						<option <%= summaryTabOrientation.equals("vertical") ? "selected" : "" %> value="vertical"><liferay-ui:message key="vertical" /></option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="lfr-label">
					<liferay-ui:message key="show-mini-month" />
				</td>
				<td>
					<liferay-ui:input-checkbox param="summaryTabShowMiniMonth" defaultValue="<%= summaryTabShowMiniMonth %>" />
				</td>
			</tr>
			<tr>
				<td class="lfr-label">
					<liferay-ui:message key="show-todays-events" />
				</td>
				<td>
					<liferay-ui:input-checkbox param="summaryTabShowTodaysEvents" defaultValue="<%= summaryTabShowTodaysEvents %>" />
				</td>
			</tr>
			</table>
		</fieldset>
	</c:when>
</c:choose>

<br />

<input type="submit" value="<liferay-ui:message key="save" />" />

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(PortalUtil.escapeRedirect(redirect)) %>';" />

</form>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.calendar.edit_configuration.jsp";
%>