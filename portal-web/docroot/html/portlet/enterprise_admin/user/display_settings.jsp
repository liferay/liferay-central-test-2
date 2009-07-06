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
User selUser = (User)request.getAttribute("user.selUser");

String languageId = BeanParamUtil.getString(selUser, request, "languageId", user.getLanguageId());
String timeZoneId = BeanParamUtil.getString(selUser, request, "timeZoneId", user.getTimeZoneId());
%>

<h3><liferay-ui:message key="display-settings" /></h3>

<fieldset class="aui-block-labels">
	<div class="aui-ctrl-holder">
		<label for="<portlet:namespace />languageId"><liferay-ui:message key="language" /></label>

		<select name="<portlet:namespace />languageId">

			<%
			Locale selLocale = LocaleUtil.fromLanguageId(languageId);

			Locale[] locales = LanguageUtil.getAvailableLocales();

			Locale languageLocale = locale;

			for (Locale curLocale : locales) {
				if (portletName.equals(PortletKeys.MY_ACCOUNT)) {
					languageLocale = curLocale;
				}
			%>

				<option <%= (selLocale.getLanguage().equals(curLocale.getLanguage()) && selLocale.getCountry().equals(curLocale.getCountry())) ? "selected" : "" %> value="<%= curLocale.getLanguage() + "_" + curLocale.getCountry() %>"><%= curLocale.getDisplayName(languageLocale) %></option>

			<%
			}
			%>

		</select>
	</div>

	<div class="aui-ctrl-holder">
		<label for="<portlet:namespace />timeZoneId"><liferay-ui:message key="time-zone" /></label>

		<liferay-ui:input-time-zone name="timeZoneId" value="<%= timeZoneId %>" />
	</div>

	<div class="aui-ctrl-holder">
		<label for="<portlet:namespace />greeting"><liferay-ui:message key="greeting" /></label>

		<liferay-ui:input-field  model="<%= User.class %>" bean="<%= selUser %>" field="greeting" />
	</div>
</fieldset>