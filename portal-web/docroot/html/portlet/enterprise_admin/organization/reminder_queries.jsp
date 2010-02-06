<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

<%
Organization organization = (Organization)request.getAttribute(WebKeys.ORGANIZATION);

String reminderQueries = ParamUtil.getString(request, "reminderQueries");

String currentLanguageId = LanguageUtil.getLanguageId(request);
Locale defaultLocale = LocaleUtil.getDefault();
String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

Locale[] locales = LanguageUtil.getAvailableLocales();

if ((organization != null) && Validator.isNull(reminderQueries)) {
	reminderQueries = StringUtil.merge(organization.getReminderQueryQuestions(defaultLocale), StringPool.NEW_LINE);
}

Map<Locale, String> reminderQueriesMap = LocalizationUtil.getLocalizedParameter(renderRequest, "reminderQueries");
%>

<h3><liferay-ui:message key="reminder-queries" /></h3>

<div class="portlet-msg-info">
	<liferay-ui:message key="specify-custom-reminder-queries-for-the-users-of-this-organization" />
</div>

<aui:fieldset>
	<aui:input label='<%= LanguageUtil.get(pageContext, "default-language") + StringPool.COLON + StringPool.SPACE + defaultLocale.getDisplayName(defaultLocale) %>' name="reminderQueries" type="textarea" />

	<aui:select cssClass="localized-language-selector" inlineLabel="left" label='<%= LanguageUtil.get(pageContext, "localized-language") + StringPool.COLON %>' name="reminderQueryLanguageId" onChange='<%= renderResponse.getNamespace() + "updateReminderQueriesLanguage();" %>'>
		<aui:option value="" />

		<%
		for (int i = 0; i < locales.length; i++) {
			if (locales[i].equals(defaultLocale)) {
				continue;
			}

			String style = StringPool.BLANK;

			String curReminderQueries = reminderQueriesMap.get(locales[i]);

			if ((organization != null) && Validator.isNull(curReminderQueries)) {
				curReminderQueries = StringUtil.merge(organization.getReminderQueryQuestions(locales[i]), StringPool.NEW_LINE);
			}

			if (Validator.isNotNull(curReminderQueries)) {
				style = "font-weight: bold;";
			}
		%>

			<aui:option label="<%= locales[i].getDisplayName(locale) %>" selected="<%= (currentLanguageId.equals(LocaleUtil.toLanguageId(locales[i]))) %>" style="<%= style %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

		<%
		}
		%>

	</aui:select>

	<%
	for (int i = 0; i < locales.length; i++) {
		if (locales[i].equals(defaultLocale)) {
			continue;
		}

		String curReminderQueries = reminderQueriesMap.get(locales[i]);

		if ((organization != null) && Validator.isNull(curReminderQueries)) {
			curReminderQueries = StringUtil.merge(organization.getReminderQueryQuestions(locales[i]), StringPool.NEW_LINE);
		}
	%>

		<aui:input name='<%= "reminderQueries_" + LocaleUtil.toLanguageId(locales[i]) %>' type="hidden" value="<%= curReminderQueries %>" />

	<%
	}
	%>

	<aui:input label="" name="reminderQueries_temp" onChange='<%= renderResponse.getNamespace() + "onReminderQueriesChanged();" %>' type="textarea" />
</aui:fieldset>

<aui:script>
	var reminderQueriesChanged = false;
	var lastLanguageId = "<%= currentLanguageId %>";

	function <portlet:namespace />onReminderQueriesChanged() {
		reminderQueriesChanged = true;
	}

	function <portlet:namespace />updateReminderQueriesLanguage() {
		if (lastLanguageId != "<%= defaultLanguageId %>") {
			if (reminderQueriesChanged) {
				var reminderQueriesValue = AUI().one("#<portlet:namespace />reminderQueries_temp").attr("value");

				if (reminderQueriesValue == null) {
					reminderQueriesValue = "";
				}

				AUI().one("#<portlet:namespace />reminderQueries_" + lastLanguageId).attr("value", reminderQueriesValue);

				reminderQueriesChanged = false;
			}
		}

		var selLanguageId = "";

		for (var i = 0; i < document.<portlet:namespace />fm.<portlet:namespace />reminderQueryLanguageId.length; i++) {
			if (document.<portlet:namespace />fm.<portlet:namespace />reminderQueryLanguageId.options[i].selected) {
				selLanguageId = document.<portlet:namespace />fm.<portlet:namespace />reminderQueryLanguageId.options[i].value;

				break;
			}
		}

		if (selLanguageId != "") {
			<portlet:namespace />updateReminderQueriesLanguageTemps(selLanguageId);

			AUI().one("#<portlet:namespace />reminderQueries_temp").show();
		}
		else {
			AUI().one("#<portlet:namespace />reminderQueries_temp").hide();
		}

		lastLanguageId = selLanguageId;

		return null;
	}

	function <portlet:namespace />updateReminderQueriesLanguageTemps(lang) {
		if (lang != "<%= defaultLanguageId %>") {
			var reminderQueriesLang = AUI().one("#<portlet:namespace />reminderQueries_" + lang);

			if (reminderQueriesLang) {
				var reminderQueriesValue = reminderQueriesLang.attr("value");
			}

			var defaultReminderQueriesLang = AUI().one("#<portlet:namespace />reminderQueries_<%= defaultLanguageId %>");

			if (defaultReminderQueriesLang) {
				var defaultReminderQueriesValue = defaultReminderQueriesLan.attr("value");
			}

			if (defaultReminderQueriesValue == null) {
				defaultReminderQueriesValue = "";
			}

			if ((reminderQueriesValue == null) || (reminderQueriesValue == "")) {
				AUI().one("#<portlet:namespace />reminderQueries_temp").attr("value", defaultReminderQueriesValue);
			}
			else {
				AUI().one("#<portlet:namespace />reminderQueries_temp").attr("value", reminderQueriesValue);
			}
		}
	}

	<portlet:namespace />updateReminderQueriesLanguageTemps(lastLanguageId);

	Liferay.on(
		'submitForm',
		function(event, data) {
			<portlet:namespace />updateReminderQueriesLanguage();
		}
	);
</aui:script>