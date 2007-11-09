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

PortletPreferences prefs = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource, false, true);

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

	function <portlet:namespace />save() {
		<c:if test='<%= tabs2.equals("user-ranks") || tabs2.equals("thread-priorities") %>'>
			<portlet:namespace />updateLanguage();
		</c:if>

		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />save(); return false;">
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
				<b>[$COMPANY_MX$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-company-mx-associated-with-the-message-board" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$COMPANY_NAME$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-company-name-associated-with-the-message-board" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$COMMUNITY_NAME$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-community-name-associated-with-the-message-board" />
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
				<b>[$COMPANY_MX$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-company-mx-associated-with-the-message-board" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$COMPANY_NAME$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-company-name-associated-with-the-message-board" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$COMMUNITY_NAME$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-community-name-associated-with-the-message-board" />
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
				<liferay-ui:message key="default-language" />: <%= defaultLocale.getDisplayName(defaultLocale) %>
			</td>
			<td>
				<br/>
			</td>
			<td>
				<liferay-ui:message key="localized-language" />:

				<select id="<portlet:namespace />languageId" onChange="<portlet:namespace />updateLanguage();">
					<option value="" />

					<%
					for (int i = 0; i < locales.length; i++) {
						if (locales[i].equals(defaultLocale)) {
							continue;
						}
					%>

						<option <%= (currentLanguageId.equals(LocaleUtil.toLanguageId(locales[i]))) ? "selected" : "" %> value="<%= LocaleUtil.toLanguageId(locales[i]) %>"><%= locales[i].getDisplayName(locales[i]) %></option>

					<%
					}
					%>

				</select>
			</td>
		</tr>
		<tr>
			<td>
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
				priorities = LocalizationUtil.getPrefsValues(prefs, "priorities", defaultLanguageId);

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
							<input name="<portlet:namespace />priorityName<%= i %>_<%= defaultLanguageId %>" size="15" type="text" value="<%= name %>" />
						</td>
						<td>
							<input name="<portlet:namespace />priorityImage<%= i %>_<%= defaultLanguageId %>" size="40" type="text" value="<%= image %>" />
						</td>
						<td>
							<input name="<portlet:namespace />priorityValue<%= i %>_<%= defaultLanguageId %>" size="4" type="text" value="<%= value %>" />
						</td>
					</tr>

				<%
				}
				%>

				</table>
			</td>
			<td>
				<br/>
			</td>
			<td>
				<table id="<portlet:namespace />localized-priorities-table" class="liferay-table" <%= currentLocale.equals(defaultLocale) ? "style='display: none'" : "" %>>
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
				for (int i = 0; i < 10; i++) {
				%>

					<tr>
						<td>
							<input id="<portlet:namespace />priorityName<%= i %>_temp" name="<portlet:namespace />priorityName<%= i %>_temp" size="15" type="text" onChange="<portlet:namespace />onChanged();" />
						</td>
						<td>
							<input id="<portlet:namespace />priorityImage<%= i %>_temp" name="<portlet:namespace />priorityImage<%= i %>_temp" size="40" type="text" onChange="<portlet:namespace />onChanged();" />
						</td>
						<td>
							<input id="<portlet:namespace />priorityValue<%= i %>_temp" name="<portlet:namespace />priorityValue<%= i %>_temp" size="4" type="text" onChange="<portlet:namespace />onChanged();" />
						</td>
					</tr>

				<%
				}
				%>

				</table>

				<%
				for (int i = 0; i < locales.length; i++) {
					if (locales[i].equals(defaultLocale)) {
						continue;
					}

					String[] tempPriorities = LocalizationUtil.getPrefsValues(prefs, "priorities", LocaleUtil.toLanguageId(locales[i]));

					for (int j = 0; j < 10; j++) {
						String name = StringPool.BLANK;
						String image = StringPool.BLANK;
						String value = StringPool.BLANK;

						if (tempPriorities.length > j) {
							String[] priority = StringUtil.split(tempPriorities[j]);

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

						<input id="<portlet:namespace />priorityName<%= j %>_<%= LocaleUtil.toLanguageId(locales[i]) %>" name="<portlet:namespace />priorityName<%= j %>_<%= LocaleUtil.toLanguageId(locales[i]) %>" type="hidden" value="<%= name %>" />
						<input id="<portlet:namespace />priorityImage<%= j %>_<%= LocaleUtil.toLanguageId(locales[i]) %>" name="<portlet:namespace />priorityImage<%= j %>_<%= LocaleUtil.toLanguageId(locales[i]) %>" type="hidden" value="<%= image %>" />
						<input id="<portlet:namespace />priorityValue<%= j %>_<%= LocaleUtil.toLanguageId(locales[i]) %>" name="<portlet:namespace />priorityValue<%= j %>_<%= LocaleUtil.toLanguageId(locales[i]) %>" type="hidden" value="<%= value %>" />

				<%
					}
				}
				%>

			</td>
		</tr>
		</table>

		<script type="text/javascript">
			var changed = false;
			var lastLanguageId = "<%= currentLanguageId %>";

			function <portlet:namespace />onChanged() {
				changed = true;
			}

			function <portlet:namespace />updateLanguage() {
				if (lastLanguageId != "<%= defaultLanguageId %>") {
					if (changed) {
						for (var i = 0; i < 10; i++) {
							var priorityName = jQuery("#<portlet:namespace />priorityName" + i + "_temp").attr("value");
							var priorityImage = jQuery("#<portlet:namespace />priorityImage" + i + "_temp").attr("value");
							var priorityValue = jQuery("#<portlet:namespace />priorityValue" + i + "_temp").attr("value");

							if (priorityName == null) {
								priorityName = "";
							}

							jQuery("#<portlet:namespace />priorityName" + i + "_" + lastLanguageId).attr("value", priorityName);

							if (priorityImage == null) {
								priorityImage = "";
							}

							jQuery("#<portlet:namespace />priorityImage" + i + "_" + lastLanguageId).attr("value", priorityImage);

							if (priorityValue == null) {
								priorityValue = "";
							}

							jQuery("#<portlet:namespace />priorityValue" + i + "_" + lastLanguageId).attr("value", priorityValue);
						}

						changed = false;
					}
				}

				var selLanguageId = "";

				for (var i = 0; i < document.<portlet:namespace />fm.<portlet:namespace />languageId.length; i++) {
					if (document.<portlet:namespace />fm.<portlet:namespace />languageId.options[i].selected) {
						selLanguageId = document.<portlet:namespace />fm.<portlet:namespace />languageId.options[i].value;

						break;
					}
				}

				if (selLanguageId != "") {
					<portlet:namespace />updateLanguageTemps(selLanguageId);

					jQuery("#<portlet:namespace />localized-priorities-table").show();
				}
				else {
					jQuery("#<portlet:namespace />localized-priorities-table").hide();
				}

				lastLanguageId = selLanguageId;

				return null;
			}

			function <portlet:namespace />updateLanguageTemps(lang) {
				if (lang != "<%= defaultLanguageId %>") {
					for (var i = 0; i < 10; i++) {
						var priorityName = jQuery("#<portlet:namespace />priorityName" + i + "_" + lang).attr("value");
						var priorityImage = jQuery("#<portlet:namespace />priorityImage" + i + "_" + lang).attr("value");
						var priorityValue = jQuery("#<portlet:namespace />priorityValue" + i + "_" + lang).attr("value");

						var defaultName = jQuery("#<portlet:namespace />priorityName" + i + "_" + "<%= defaultLanguageId %>").attr("value");
						var defaultImage = jQuery("#<portlet:namespace />priorityImage" + i + "_" + "<%= defaultLanguageId %>").attr("value");
						var defaultValue = jQuery("#<portlet:namespace />priorityValue" + i + "_" + "<%= defaultLanguageId %>").attr("value");

						if (defaultName == null) {
							defaultName = "";
						}

						if (defaultImage == null) {
							defaultImage = "";
						}

						if (defaultValue == null) {
							defaultValue = "";
						}

						if ((priorityName == null) || (priorityName == "")) {
							jQuery("#<portlet:namespace />priorityName" + i + "_temp").attr("value", defaultName);
						}
						else {
							jQuery("#<portlet:namespace />priorityName" + i + "_temp").attr("value", priorityName);
						}

						if ((priorityImage == null) || (priorityImage == "")) {
							jQuery("#<portlet:namespace />priorityImage" + i + "_temp").attr("value", defaultImage);
						}
						else {
							jQuery("#<portlet:namespace />priorityImage" + i + "_temp").attr("value", priorityImage);
						}

						if ((priorityValue == null) || (priorityValue == "")) {
							jQuery("#<portlet:namespace />priorityValue" + i + "_temp").attr("value", defaultValue);
						}
						else {
							jQuery("#<portlet:namespace />priorityValue" + i + "_temp").attr("value", priorityValue);
						}
					}
				}
			}

			<portlet:namespace />updateLanguageTemps(lastLanguageId);
		</script>
	</c:when>
	<c:when test='<%= tabs2.equals("user-ranks") %>'>
		<liferay-ui:message key="enter-rank-and-minimum-post-pairs-per-line" />

		<br /><br />

		<table class="liferay-table">
		<tr>
			<td>
				<liferay-ui:message key="default-language" />: <%= defaultLocale.getDisplayName(defaultLocale) %>
			</td>
			<td>
				<liferay-ui:message key="localized-language" />:

				<select id="<portlet:namespace />languageId" onChange="<portlet:namespace />updateLanguage();">
					<option value="" />

					<%
					for (int i = 0; i < locales.length; i++) {
						if (locales[i].equals(defaultLocale)) {
							continue;
						}
					%>

						<option <%= (currentLanguageId.equals(LocaleUtil.toLanguageId(locales[i]))) ? "selected" : "" %> value="<%= LocaleUtil.toLanguageId(locales[i]) %>"><%= locales[i].getDisplayName(locales[i]) %></option>

					<%
					}
					%>

				</select>
			</td>
		</tr>
		<tr>
			<td>
				<textarea class="liferay-textarea" id="<portlet:namespace />ranks_<%= defaultLanguageId %>" name="<portlet:namespace />ranks_<%= defaultLanguageId %>"><%= StringUtil.merge(LocalizationUtil.getPrefsValues(prefs, "ranks", defaultLanguageId), StringPool.NEW_LINE) %></textarea>
			</td>
			<td>

				<%
				for (int i = 0; i < locales.length; i++) {
					if (locales[i].equals(defaultLocale)) {
						continue;
					}
				%>

					<input id="<portlet:namespace />ranks_<%= LocaleUtil.toLanguageId(locales[i]) %>" name="<portlet:namespace />ranks_<%= LocaleUtil.toLanguageId(locales[i]) %>" type="hidden" value='<%= StringUtil.merge(LocalizationUtil.getPrefsValues(prefs, "ranks", LocaleUtil.toLanguageId(locales[i]), false), StringPool.NEW_LINE) %>' />

				<%
				}
				%>

				<textarea class="liferay-textarea" id="<portlet:namespace />ranks_temp" <%= currentLocale.equals(defaultLocale) ? "style='display: none'" : "" %> onChange="<portlet:namespace />onRanksChanged();"></textarea>
			</td>
		</tr>
		</table>

		<script type="text/javascript">
			var ranksChanged = false;
			var lastLanguageId = "<%= currentLanguageId %>";

			function <portlet:namespace />onRanksChanged() {
				ranksChanged = true;
			}

			function <portlet:namespace />updateLanguage() {
				if (lastLanguageId != "<%= defaultLanguageId %>") {
					if (ranksChanged) {
						var ranksValue = jQuery("#<portlet:namespace />ranks_temp").attr("value");

						if (ranksValue == null) {
							ranksValue = "";
						}

						jQuery("#<portlet:namespace />ranks_" + lastLanguageId).attr("value", ranksValue);

						ranksChanged = false;
					}
				}

				var selLanguageId = "";

				for (var i = 0; i < document.<portlet:namespace />fm.<portlet:namespace />languageId.length; i++) {
					if (document.<portlet:namespace />fm.<portlet:namespace />languageId.options[i].selected) {
						selLanguageId = document.<portlet:namespace />fm.<portlet:namespace />languageId.options[i].value;

						break;
					}
				}

				if (selLanguageId != "") {
					<portlet:namespace />updateLanguageTemps(selLanguageId);

					jQuery("#<portlet:namespace />ranks_temp").show();
				}
				else {
					jQuery("#<portlet:namespace />ranks_temp").hide();
				}

				lastLanguageId = selLanguageId;

				return null;
			}

			function <portlet:namespace />updateLanguageTemps(lang) {
				if (lang != "<%= defaultLanguageId %>") {
					var ranksValue = jQuery("#<portlet:namespace />ranks_" + lang).attr("value");
					var defaultRanksValue = jQuery("#<portlet:namespace />ranks_<%= defaultLanguageId %>").attr("value");

					if (defaultRanksValue == null) {
						defaultRanksValue = "";
					}

					if ((ranksValue == null) || (ranksValue == "")) {
						jQuery("#<portlet:namespace />ranks_temp").attr("value", defaultRanksValue);
					}
					else {
						jQuery("#<portlet:namespace />ranks_temp").attr("value", ranksValue);
					}
				}
			}

			<portlet:namespace />updateLanguageTemps(lastLanguageId);
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