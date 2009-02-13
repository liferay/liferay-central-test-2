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
User user2 = company.getDefaultUser();

String timeZoneId = ParamUtil.getString(request, "timeZoneId", user2.getTimeZoneId());
String languageId = ParamUtil.getString(request, "languageId", user2.getLanguageId());
boolean companySecurityCommunityLogo = ParamUtil.getBoolean(request, "settings(" + PropsKeys.COMPANY_SECURITY_COMMUNITY_LOGO + ")", company.isCommunityLogo());
%>

<h3><liferay-ui:message key="display-settings" /></h3>

<fieldset class="block-labels col">
	<div class="ctrl-holder">
		<label for="<portlet:namespace />languageId"><liferay-ui:message key="language" /></label>

		<select name="<portlet:namespace />languageId">

			<%
			Locale locale2 = LocaleUtil.fromLanguageId(languageId);

			Locale[] locales = LanguageUtil.getAvailableLocales();

			for (int i = 0; i < locales.length; i++) {
			%>

				<option <%= (locale2.getLanguage().equals(locales[i].getLanguage()) && locale2.getCountry().equals(locales[i].getCountry())) ? "selected" : "" %> value="<%= locales[i].getLanguage() + "_" + locales[i].getCountry() %>"><%= locales[i].getDisplayName(locale) %></option>

			<%
			}
			%>

		</select>
	</div>

	<div class="ctrl-holder">
		<label for="<portlet:namespace />timeZoneId"><liferay-ui:message key="time-zone" /></label>

		<liferay-ui:input-time-zone name="timeZoneId" value="<%= timeZoneId %>" />
	</div>
</fieldset>

<h3><liferay-ui:message key="logo" /></h3>

<fieldset class="block-labels col">
	<div class="ctrl-holder">
		<label class="inline-label" for="<portlet:namespace />settings(<%= PropsKeys.COMPANY_SECURITY_COMMUNITY_LOGO %>)"><liferay-ui:message key="allow-community-administrators-to-use-their-own-logo" /></label>

		<liferay-ui:input-checkbox param='<%= "settings(" + PropsKeys.COMPANY_SECURITY_COMMUNITY_LOGO + ")" %>' defaultValue="<%= companySecurityCommunityLogo %>" />
	</div>

	<div class="ctrl-holder">
		<img alt="<liferay-ui:message key="logo" />" src="<%= themeDisplay.getPathImage() %>/company_logo?img_id=<%= company.getLogoId() %>&t=<%= ImageServletTokenUtil.getToken(company.getLogoId()) %>" /><br />

		<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_company_logo" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>" style="font-size: xx-small;"><liferay-ui:message key="change" /></a><br />
	</div>
</fieldset>