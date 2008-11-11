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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String currentLanguageId = LanguageUtil.getLanguageId(request);
Locale defaultLocale = LocaleUtil.getDefault();
String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

Locale[] locales = LanguageUtil.getAvailableLocales();

Organization organization = (Organization)request.getAttribute(WebKeys.ORGANIZATION);
Set<String> reminderQueries = organization.getReminderQueryQuestions(defaultLocale);
String reminderQueriesValue = StringUtil.merge(reminderQueries, StringPool.NEW_LINE);
%>

<h3><liferay-ui:message key="reminder-queries" /></h3>

<span class="portlet-msg-info">
	<liferay-ui:message key="specify-custom-reminder-queries-for-the-users-of-this-organization.-enter-one-question-per-line" />
</span>

<fieldset class="block-labels">
	<div class="ctrl-holder">
		<label for="<portlet:namespace />reminderQueries"><liferay-ui:message key="default-language" />: <%= defaultLocale.getDisplayName(defaultLocale) %></label>
		<textarea name="<portlet:namespace />reminderQueries" style='height: 105px; width: 400px;'><%= reminderQueriesValue %></textarea>
	</div>

	<div class="ctrl-holder">
		<label for="<portlet:namespace />reminderQueryLanguageId"><liferay-ui:message key="localized-language" />:</label>

		<select id="<portlet:namespace />reminderQueryLanguageId" name="<portlet:namespace />reminderQueryLanguageId" onChange="<portlet:namespace />updateReminderQueriesLanguage();">
			<option value="" />
			<%
			for (int i = 0; i < locales.length; i++) {
				if (locales[i].equals(defaultLocale)) {
					continue;
				}

				String optionStyle = StringPool.BLANK;

				if (!organization.getReminderQueryQuestions(locales[i]).isEmpty()) {
					optionStyle = "style=\"font-weight: bold\"";
				}
			%>

				<option <%= (currentLanguageId.equals(LocaleUtil.toLanguageId(locales[i]))) ? "selected" : "" %> <%= optionStyle %> value="<%= LocaleUtil.toLanguageId(locales[i]) %>"><%= locales[i].getDisplayName(locales[i]) %></option>

			<%
			}
			%>

		</select>

		<%
		for (int i = 0; i < locales.length; i++) {
			if (locales[i].equals(defaultLocale)) {
				continue;
			}
		%>

			<input type="hidden" id="<portlet:namespace />reminderQueries_<%= LocaleUtil.toLanguageId(locales[i]) %>" name="<portlet:namespace />reminderQueries_<%= LocaleUtil.toLanguageId(locales[i]) %>" style='display:none; height: 105px; width: 400px;' value="<%= StringUtil.merge(organization.getReminderQueryQuestions(locales[i]), StringPool.NEW_LINE) %>">

		<%
		}
		%>

		<textarea id="<portlet:namespace />reminderQueries_temp" name="<portlet:namespace />reminderQueries_temp" onChange="<portlet:namespace />onReminderQueriesChanged();" style="display: none; height: 105px; width: 400px;'"></textarea>
	</div>
</fieldset>

<script type="text/javascript">
	var reminderQueriesChanged = false;
	var lastLanguageId = "<%= currentLanguageId %>";

	function <portlet:namespace />onReminderQueriesChanged() {
		console.log("changed");
		reminderQueriesChanged = true;
	}

	function <portlet:namespace />updateReminderQueriesLanguage() {
		if (lastLanguageId != "<%= defaultLanguageId %>") {
			console.log("updateReminderQueriesLanguage");
			if (reminderQueriesChanged) {
				var reminderQueriesValue = jQuery("#<portlet:namespace />reminderQueries_temp").attr("value");

				if (reminderQueriesValue == null) {
					reminderQueriesValue = "";
				}

				console.log("value: " + reminderQueriesValue);
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