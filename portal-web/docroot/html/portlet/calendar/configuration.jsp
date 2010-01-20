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

String editorParam = "emailEventReminderBody";
String editorContent = emailEventReminderBody;
%>

<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL" portletConfiguration="true">
	<portlet:param name="tabs2" value="<%= tabs2 %>" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</liferay-portlet:renderURL>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "saveConfiguration(); return false;" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

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
			<aui:fieldset>
				<aui:input cssClass="lfr-input-text-container" label="name" name="emailFromName" type="text" value="<%= emailFromName %>" />

				<aui:input cssClass="lfr-input-text-container" label="address" name="emailFromAddress" type="text" value="<%= emailFromAddress %>" />
			</aui:fieldset>
		</c:when>
		<c:when test='<%= tabs2.equals("event-reminder-email") %>'>
			<aui:fieldset>
				<aui:input inlineLabel="left" label="enabled" name="emailEventReminderEnabled" type="checkbox" value="<%= CalUtil.getEmailEventReminderEnabled(preferences) %>" />

				<aui:input cssClass="lfr-input-text-container" label="subject" name="emailEventReminderSubject" type="text" value="<%= emailEventReminderSubject %>" />

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
			<aui:fieldset>
				<aui:legend label="default-tab" />

				<aui:select label="default-tab" name="tabs1Default">

					<%
					for (String tabs1Name : tabs1NamesArray) {
					%>

					<aui:option label="<%= tabs1Name %>" selected="<%= tabs1Default.equals(tabs1Name) %>" />

					<%
					}
					%>

				</aui:select>
			</aui:fieldset>

			<aui:fieldset>
				<aui:legend label="summary-tab" />

				<aui:select label="summary-tab" name="summaryTabOrientation">
					<aui:option label="horizontal" selected='<%= summaryTabOrientation.equals("horizontal") %>' />
					<aui:option label="vertical" selected='<%= summaryTabOrientation.equals("vertical") %>' />
				</aui:select>

				<aui:input inlineLabel="left" label="show-mini-month" name="summaryTabShowMiniMonth" type="checkbox" value="<%= summaryTabShowMiniMonth %>" />

				<aui:input inlineLabel="left" label="show-todays-events" name="summaryTabShowTodaysEvents" type="checkbox" value="<%= summaryTabShowTodaysEvents %>" />
			</aui:fieldset>
		</c:when>
	</c:choose>

	<br />

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />initEditor() {
		return "<%= UnicodeFormatter.toString(editorContent) %>";
	}

	function <portlet:namespace />saveConfiguration() {
		<c:if test='<%= tabs2.equals("event-reminder-email") %>'>
			document.<portlet:namespace />fm.<portlet:namespace /><%= editorParam %>.value = window.<portlet:namespace />editor.getHTML();
		</c:if>

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.calendar.edit_configuration.jsp";
%>