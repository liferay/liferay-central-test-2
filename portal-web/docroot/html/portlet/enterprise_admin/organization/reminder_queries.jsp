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

<span class="portlet-msg-info">
	<liferay-ui:message key="specify-custom-reminder-queries-for-the-users-of-this-organization" />
</span>

<fieldset class="aui-block-labels">
	<div class="aui-ctrl-holder">
		<label for="<portlet:namespace />reminderQueries"><liferay-ui:message key="default-language" />: <%= defaultLocale.getDisplayName(defaultLocale) %></label>

		<textarea class="lfr-textarea" name="<portlet:namespace />reminderQueries"><%= reminderQueries %></textarea>
	</div>

	<div class="aui-ctrl-holder">
		<label for="<portlet:namespace />reminderQueryLanguageId"><liferay-ui:message key="localized-language" />:</label>

		<select id="<portlet:namespace />reminderQueryLanguageId" name="<portlet:namespace />reminderQueryLanguageId" onChange="<portlet:namespace />updateReminderQueriesLanguage();">
			<option value="" />

			<%
			for (int i = 0; i < locales.length; i++) {
				if (locales[i].equals(defaultLocale)) {
					continue;
				}

				String optionStyle = StringPool.BLANK;

				String curReminderQueries = reminderQueriesMap.get(locales[i]);

				if ((organization != null) && Validator.isNull(curReminderQueries)) {
					curReminderQueries = StringUtil.merge(organization.getReminderQueryQuestions(locales[i]), StringPool.NEW_LINE);
				}

				if (Validator.isNotNull(curReminderQueries)) {
					optionStyle = "style=\"font-weight: bold;\"";
				}
			%>

				<option <%= (currentLanguageId.equals(LocaleUtil.toLanguageId(locales[i]))) ? "selected" : "" %> <%= optionStyle %> value="<%= LocaleUtil.toLanguageId(locales[i]) %>"><%= locales[i].getDisplayName(locale) %></option>

			<%
			}
			%>

		</select>

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

			<input type="hidden" id="<portlet:namespace />reminderQueries_<%= LocaleUtil.toLanguageId(locales[i]) %>" name="<portlet:namespace />reminderQueries_<%= LocaleUtil.toLanguageId(locales[i]) %>" value="<%= curReminderQueries %>" />

		<%
		}
		%>

		<textarea  class="lfr-textarea" id="<portlet:namespace />reminderQueries_temp" name="<portlet:namespace />reminderQueries_temp" onChange="<portlet:namespace />onReminderQueriesChanged();"></textarea>
	</div>
</fieldset>

<script type="text/javascript">
	var reminderQueriesChanged = false;
	var lastLanguageId = "<%= currentLanguageId %>";

	function <portlet:namespace />onReminderQueriesChanged() {
		reminderQueriesChanged = true;
	}

	function <portlet:namespace />updateReminderQueriesLanguage() {
		if (lastLanguageId != "<%= defaultLanguageId %>") {
			if (reminderQueriesChanged) {
				var reminderQueriesValue = jQuery("#<portlet:namespace />reminderQueries_temp").attr("value");

				if (reminderQueriesValue == null) {
					reminderQueriesValue = "";
				}

				jQuery("#<portlet:namespace />reminderQueries_" + lastLanguageId).attr("value", reminderQueriesValue);

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

			jQuery("#<portlet:namespace />reminderQueries_temp").show();
		}
		else {
			jQuery("#<portlet:namespace />reminderQueries_temp").hide();
		}

		lastLanguageId = selLanguageId;

		return null;
	}

	function <portlet:namespace />updateReminderQueriesLanguageTemps(lang) {
		if (lang != "<%= defaultLanguageId %>") {
			var reminderQueriesValue = jQuery("#<portlet:namespace />reminderQueries_" + lang).attr("value");
			var defaultReminderQueriesValue = jQuery("#<portlet:namespace />reminderQueries_<%= defaultLanguageId %>").attr("value");

			if (defaultReminderQueriesValue == null) {
				defaultReminderQueriesValue = "";
			}

			if ((reminderQueriesValue == null) || (reminderQueriesValue == "")) {
				jQuery("#<portlet:namespace />reminderQueries_temp").attr("value", defaultReminderQueriesValue);
			}
			else {
				jQuery("#<portlet:namespace />reminderQueries_temp").attr("value", reminderQueriesValue);
			}
		}
	}

	<portlet:namespace />updateReminderQueriesLanguageTemps(lastLanguageId);

	Liferay.bind(
		'submitForm',
		function(event, data) {
			<portlet:namespace />updateReminderQueriesLanguage();
		}
	);
</script>