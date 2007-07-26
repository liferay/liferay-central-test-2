<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

String emailMessageAddedSubjectPrefix = ParamUtil.getString(request, "emailMessageAddedSubjectPrefix", MBUtil.getEmailMessageAddedSubjectPrefix(prefs));
String emailMessageAddedBody = ParamUtil.getString(request, "emailMessageAddedBody", MBUtil.getEmailMessageAddedBody(prefs));
String emailMessageAddedSignature = ParamUtil.getString(request, "emailMessageAddedSignature", MBUtil.getEmailMessageAddedSignature(prefs));

String emailMessageUpdatedSubjectPrefix = ParamUtil.getString(request, "emailMessageUpdatedSubjectPrefix", MBUtil.getEmailMessageUpdatedSubjectPrefix(prefs));
String emailMessageUpdatedBody = ParamUtil.getString(request, "emailMessageUpdatedBody", MBUtil.getEmailMessageUpdatedBody(prefs));
String emailMessageUpdatedSignature = ParamUtil.getString(request, "emailMessageUpdatedSignature", MBUtil.getEmailMessageUpdatedSignature(prefs));

String userNameAttribute = ParamUtil.getString(request, "userNameAttribute", MBUtil.getUserNameAttribute(prefs));
int rssContentLength = ParamUtil.getInteger(request, "rssContentLength", MBUtil.getRSSContentLength(prefs));

String tabs2 = ParamUtil.getString(request, "tabs2", "email-from");

String redirect = ParamUtil.getString(request, "redirect");
%>

<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL" portletConfiguration="true">
	<liferay-portlet:param name="tabs2" value="<%= tabs2 %>" />
	<liferay-portlet:param name="redirect" value="<%= redirect %>" />
</liferay-portlet:renderURL>

<script type="text/javascript">

	<%
	String bodyEditorParam = "";
	String bodyEditorContent = "";
	String signatureEditorParam = "";
	String signatureEditorContent = "";

	if (tabs2.equals("message-added-email")) {
		bodyEditorParam = "emailMessageAddedBody";
		bodyEditorContent = emailMessageAddedBody;
		signatureEditorParam = "emailMessageAddedSignature";
		signatureEditorContent = emailMessageAddedSignature;
	}
	else if (tabs2.equals("message-updated-email")) {
		bodyEditorParam = "emailMessageUpdatedBody";
		bodyEditorContent = emailMessageUpdatedBody;
		signatureEditorParam = "emailMessageUpdatedSignature";
		signatureEditorContent = emailMessageUpdatedSignature;
	}
	%>

</script>

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= tabs2 %>" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>" />

<liferay-ui:tabs
	names="email-from,message-added-email,message-updated-email,thread-priorities,user-ranks,display-settings"
	param="tabs2"
	url="<%= portletURL %>"
/>

<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
<liferay-ui:error key="emailMessageAddedBody" message="please-enter-a-valid-body" />
<liferay-ui:error key="emailMessageAddedSignature" message="please-enter-a-valid-signature" />
<liferay-ui:error key="emailMessageAddedSubjectPrefix" message="please-enter-a-valid-subject" />
<liferay-ui:error key="emailMessageUpdatedBody" message="please-enter-a-valid-body" />
<liferay-ui:error key="emailMessageUpdatedSignature" message="please-enter-a-valid-signature" />
<liferay-ui:error key="emailMessageUpdatedSubjectPrefix" message="please-enter-a-valid-subject" />

<c:choose>
	<c:when test='<%= tabs2.equals("email-from") %>'>
		<table class="liferay-table">
		<tr>
			<td>
				<liferay-ui:message key="name" />
			</td>
			<td>
				<input class="liferay-input-text" name="<portlet:namespace />emailFromName" type="text" value="<%= emailFromName %>" />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="address" />
			</td>
			<td>
				<input class="liferay-input-text" name="<portlet:namespace />emailFromAddress" type="text" value="<%= emailFromAddress %>" />
			</td>
		</tr>
		</table>

		<br />

		<b><liferay-ui:message key="definition-of-terms" /></b>

		<br /><br />

		<table class="liferay-table">
		<tr>
			<td>
				<b>[$COMPANY_ID$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-company-id-associated-with-the-message-board" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$MAILING_LIST_ADDRESS$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-email-address-of-the-mailing-list" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$MESSAGE_USER_ADDRESS$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-email-address-of-the-user-who-added-the-message" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$MESSAGE_USER_NAME$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-user-who-added-the-message" />
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
	<c:when test='<%= tabs2.startsWith("message-") %>'>
		<table class="liferay-table">
		<tr>
			<td>
				<liferay-ui:message key="enabled" />
			</td>
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
					<c:when test='<%= tabs2.equals("message-added-email") %>'>
						<input class="liferay-input-text" name="<portlet:namespace />emailMessageAddedSubjectPrefix" type="text" value="<%= emailMessageAddedSubjectPrefix %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("message-updated-email") %>'>
						<input class="liferay-input-text" name="<portlet:namespace />emailMessageUpdatedSubjectPrefix" type="text" value="<%= emailMessageUpdatedSubjectPrefix %>" />
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
				<textarea class="liferay-textarea" name="<%= bodyEditorParam %>" wrap="soft"><%= bodyEditorContent %></textarea>
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
				<textarea class="liferay-textarea" name="<%= signatureEditorParam %>" wrap="soft"><%= signatureEditorContent %></textarea>
			</td>
		</tr>
		</table>

		<br />

		<b><liferay-ui:message key="definition-of-terms" /></b>

		<br /><br />

		<table class="liferay-table">
		<tr>
			<td>
				<b>[$CATEGORY_NAME$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-category-in-which-the-message-has-been-posted" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$COMPANY_ID$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-company-id-associated-with-the-message-board" />
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
				<b>[$MAILING_LIST_ADDRESS$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-email-address-of-the-mailing-list" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$MESSAGE_BODY$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-message-body" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$MESSAGE_ID$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-message-id" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$MESSAGE_SUBJECT$]</b>
			</td>
			<td>
				The message subject
			</td>
		</tr>
		<tr>
			<td>
				<b>[$MESSAGE_USER_ADDRESS$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-email-address-of-the-user-who-added-the-message" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$MESSAGE_USER_NAME$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-user-who-added-the-message" />
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
	<c:when test='<%= tabs2.equals("thread-priorities") %>'>
		<liferay-ui:message key="enter-the-name,-image,-and-priority-level-in-descending-order" />

		<br /><br />

		<table class="liferay-table">
		<tr>
			<td>
				<liferay-ui:message key="name" />
			</td>
			<td>
				<liferay-ui:message key="image" />
			</td>
			<td>
				<liferay-ui:message key="priority" />
			</td>
		</tr>

		<%
		priorities = prefs.getValues("priorities", new String[0]);

		for (int i = 0; i < 10; i++) {
			String name = StringPool.BLANK;
			String image = StringPool.BLANK;
			String value = StringPool.BLANK;

			if (priorities.length > i) {
				String[] priority = StringUtil.split(priorities[i]);

				try {
					name = priority[0];
					image = priority[1];
					value = priority[2];
				}
				catch (Exception e) {
				}

				if (Validator.isNull(name) && Validator.isNull(image)) {
					value = StringPool.BLANK;
				}
			}
		%>

			<tr>
				<td>
					<input name="<portlet:namespace />priorityName<%= i %>" size="20" type="text" value="<%= name %>" />
				</td>
				<td>
					<input name="<portlet:namespace />priorityImage<%= i %>" size="50" type="text" value="<%= image %>" />
				</td>
				<td>
					<input name="<portlet:namespace />priorityValue<%= i %>" size="4" type="text" value="<%= value %>" />
				</td>
			</tr>

		<%
		}
		%>

		</table>
	</c:when>
	<c:when test='<%= tabs2.equals("user-ranks") %>'>
		<liferay-ui:message key="enter-rank-and-minimum-post-pairs-per-line" />

		<br /><br />

		<%
		String languageId = ParamUtil.getString(request, "languageId", LocaleUtil.toLanguageId(locale));
		%>

		<textarea class="liferay-textarea" name="<portlet:namespace />ranks"><%= StringUtil.merge(prefs.getValues(MBUtil.getRanksKey(languageId), new String[0]), StringPool.NEW_LINE) %></textarea><br />

		<select name="<portlet:namespace />languageId" onChange="<portlet:namespace />getLocalizedRanks();">

			<%
			Locale[] locales = LanguageUtil.getAvailableLocales();

			for (int i = 0; i < locales.length; i++) {
			%>

				<option <%= (languageId.equals(LocaleUtil.toLanguageId(locales[i]))) ? "selected" : "" %> value="<%= LocaleUtil.toLanguageId(locales[i]) %>"><%= locales[i].getDisplayName(locales[i]) %></option>

			<%
			}
			%>

		</select>

		<br />

		<script type="text/javascript">
			function <portlet:namespace />getLocalizedRanks() {
				self.location = '<%= portletURL %>&<portlet:namespace />languageId=' + document.<portlet:namespace />fm.<portlet:namespace />languageId.value;
			}
		</script>
	</c:when>
	<c:when test='<%= tabs2.equals("display-settings") %>'>
		<table class="liferay-table">
		<tr>
			<td>
				<liferay-ui:message key="show-user-name-as" />
			</td>
			<td>
				<select name="<portlet:namespace />userNameAttribute">
					<option <%= userNameAttribute.equals(UserAttributes.USER_NAME_FULL) ? "selected" : "" %> value="<%= UserAttributes.USER_NAME_FULL %>"><liferay-ui:message key="full-name" /></option>
					<option <%= userNameAttribute.equals(UserAttributes.USER_NAME_NICKNAME) ? "selected" : "" %> value="<%= UserAttributes.USER_NAME_NICKNAME %>"><liferay-ui:message key="screen-name" /></option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="rss-feed-content-length" />
			</td>
			<td>
				<input name="<portlet:namespace />rssContentLength" size="4" type="text" value="<%= rssContentLength %>" />
			</td>
		</tr>
		</table>
	</c:when>
</c:choose>

<br />

<input type="submit" value="<liferay-ui:message key="save" />" />

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="self.location = '<%= redirect %>';" />

</form>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.message_boards.edit_configuration.jsp";
%>