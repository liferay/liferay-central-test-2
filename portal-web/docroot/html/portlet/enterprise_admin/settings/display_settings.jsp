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
boolean deleteLogo = ParamUtil.getBoolean(request, "deleteLogo");
%>

<script type="text/javascript">
	function <portlet:namespace />changeLogo(newLogoURL) {
		jQuery('#<portlet:namespace />avatar').attr('src', newLogoURL);
		jQuery('.company-logo').attr('src', newLogoURL);

		jQuery('#<portlet:namespace />deleteLogo').val(false);
	}

	function <portlet:namespace />deleteLogo(defaultLogoURL) {
		jQuery('#<portlet:namespace />deleteLogo').val(true);

		jQuery('#<portlet:namespace />avatar').attr('src', defaultLogoURL);
		jQuery('.company-logo').attr('src', defaultLogoURL);
	}

	function <portlet:namespace />openEditCompanyLogoWindow(editCompanyLogoURL) {
		var editCompanyLogoWindow = window.open(editCompanyLogoURL, '<liferay-ui:message key="change" />', 'directories=no,height=400,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=500');

		editCompanyLogoWindow.focus();
	}

	jQuery(
		function() {
			jQuery('span.modify-link').bind(
				'click',
				function() {
					jQuery(this).trigger('change');
				}
			);
		}
	);
</script>

<h3><liferay-ui:message key="display-settings" /></h3>

<fieldset class="exp-block-labels exp-form-column">
	<div class="exp-ctrl-holder">
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

	<div class="exp-ctrl-holder">
		<label for="<portlet:namespace />timeZoneId"><liferay-ui:message key="time-zone" /></label>

		<liferay-ui:input-time-zone name="timeZoneId" value="<%= timeZoneId %>" />
	</div>
</fieldset>

<h3><liferay-ui:message key="logo" /></h3>

<fieldset class="exp-block-labels exp-form-column">
	<div class="exp-ctrl-holder">
		<label class="inline-label" for="<portlet:namespace />settings(<%= PropsKeys.COMPANY_SECURITY_COMMUNITY_LOGO %>)"><liferay-ui:message key="allow-community-administrators-to-use-their-own-logo" /></label>

		<liferay-ui:input-checkbox param='<%= "settings(" + PropsKeys.COMPANY_SECURITY_COMMUNITY_LOGO + ")" %>' defaultValue="<%= companySecurityCommunityLogo %>" />
	</div>

	<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="editCompanyLogoURL">
		<portlet:param name="struts_action" value="/enterprise_admin/edit_company_logo" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>

	<a class="change-company-logo" href="javascript: <portlet:namespace />openEditCompanyLogoWindow('<%= editCompanyLogoURL %>');">
		<img alt="<liferay-ui:message key="logo" />" class="avatar" id="<portlet:namespace />avatar" src="<%= themeDisplay.getPathImage() %>/company_logo?img_id=<%= deleteLogo ? 0 : company.getLogoId() %>&t=<%= ImageServletTokenUtil.getToken(company.getLogoId()) %>" />
	</a>

	<div class="portrait-icons">

		<%
		String taglibEditURL = "javascript: " + renderResponse.getNamespace() + "openEditCompanyLogoWindow('" + editCompanyLogoURL +"');";
		%>

		<liferay-ui:icon image="edit" message="change" url="<%= taglibEditURL %>" label="<%= true %>" />

		<c:if test="<%= company.getLogoId() != 0 %>">

			<%
			String taglibDeleteURL = "javascript: " + renderResponse.getNamespace() + "deleteLogo('" + themeDisplay.getPathImage() + "/company_logo?img_id=0');";
			%>

			<liferay-ui:icon image="delete" url="<%= taglibDeleteURL %>" label="<%= true %>" cssClass="modify-link" />

			<input id="<portlet:namespace />deleteLogo" name="<portlet:namespace />deleteLogo" type="hidden" value="<%= deleteLogo %>" />
		</c:if>
	</div>
</fieldset>