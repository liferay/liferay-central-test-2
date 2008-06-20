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

<%@ include file="/html/portlet/calendar/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "email-from");

String redirect = ParamUtil.getString(request, "redirect");

String emailFromName = ParamUtil.getString(request, "emailFromName", CalUtil.getEmailFromName(prefs));
String emailFromAddress = ParamUtil.getString(request, "emailFromAddress", CalUtil.getEmailFromAddress(prefs));

String emailEventReminderSubject = ParamUtil.getString(request, "emailEventReminderSubject", CalUtil.getEmailEventReminderSubject(prefs));
String emailEventReminderBody = ParamUtil.getString(request, "emailEventReminderBody", CalUtil.getEmailEventReminderBody(prefs));
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
		<c:if test='<%= tabs2.equals("display-order") %>'>
			document.<portlet:namespace />fm.<portlet:namespace />displayOrderTabs1.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />tabs1NamesBox);
		</c:if>

		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveConfiguration(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= HtmlUtil.escape(tabs2) %>" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(redirect) %>" />
<input name="<portlet:namespace />displayOrderTabs1" type="hidden" value="" />

<liferay-ui:tabs
	names="email-from,event-reminder-email,display-order,summary-tab"
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
	<c:when test='<%= tabs2.equals("event-reminder-email") %>'>
		<table class="lfr-table">
		<tr>
			<td>
				<liferay-ui:message key="enabled" />
			</td>
			<td>
				<liferay-ui:input-checkbox param="emailEventReminderEnabled" defaultValue="<%= CalUtil.getEmailEventReminderEnabled(prefs) %>" />
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
				<input class="lfr-input-text" name="<portlet:namespace />emailEventReminderSubject" type="text" value="<%= emailEventReminderSubject %>" />
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

		<b><liferay-ui:message key="definition-of-terms" /></b>

		<br /><br />

		<table class="lfr-table">
		<tr>
			<td>
				<b>[$EVENT_START_DATE$]</b>
			</td>
			<td>
				The event start date
			</td>
		</tr>
		<tr>
			<td>
				<b>[$EVENT_TITLE$]</b>
			</td>
			<td>
				The event title
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
				The address of the email recipient
			</td>
		</tr>
		<tr>
			<td>
				<b>[$TO_NAME$]</b>
			</td>
			<td>
				The name of the email recipient
			</td>
		</tr>
		</table>
	</c:when>
	<c:when test='<%= tabs2.equals("display-order") %>'>
		<table class="lfr-table">
			<tr>
				<td valign="top">
					<select name="<portlet:namespace />tabs1NamesBox" size="10">

						<%
						for (int i=0; i < tabs1NamesArray.length; i++) {
						%>

							<option value="<%= tabs1NamesArray[i] %>"><%= LanguageUtil.get(pageContext, tabs1NamesArray[i]) %></option>

						<%
						}
						%>

					</select>
				</td>
				<td valign="top">
					<a href="javascript: Liferay.Util.reorder(document.<portlet:namespace />fm.<portlet:namespace />tabs1NamesBox, 0);"><img border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/arrows/02_up.png" vspace="2" width="16" /></a><br />
					<a href="javascript: Liferay.Util.reorder(document.<portlet:namespace />fm.<portlet:namespace />tabs1NamesBox, 1);"><img border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/arrows/02_down.png" vspace="2" width="16" /></a><br />
				</td>
			</tr>
		</table>
	</c:when>
	<c:when test='<%= tabs2.equals("summary-tab") %>'>
		<table class="lfr-table">
		<tr>
			<td>
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
			<td>
				<liferay-ui:message key="show-mini-month" />
			</td>
			<td>
				<liferay-ui:input-checkbox param="summaryTabShowMiniMonth" defaultValue="<%= summaryTabShowMiniMonth %>" />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="show-todays-events" />
			</td>
			<td>
				<liferay-ui:input-checkbox param="summaryTabShowTodaysEvents" defaultValue="<%= summaryTabShowTodaysEvents %>" />
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
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.calendar.edit_configuration.jsp";
%>