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

Locale[] locales = LanguageUtil.getAvailableLocales();
String[] languageIds = LocaleUtil.toLanguageIds(locales);

String timeZoneId = ParamUtil.getString(request, "timeZoneId", user2.getTimeZoneId());
String languageId = ParamUtil.getString(request, "languageId", user2.getLanguageId());
String availableLocales = ParamUtil.getString(request, "settings(" + PropsKeys.LOCALES + ")", StringUtil.merge(languageIds));

boolean companySecurityCommunityLogo = ParamUtil.getBoolean(request, "settings(" + PropsKeys.COMPANY_SECURITY_COMMUNITY_LOGO + ")", company.isCommunityLogo());
boolean deleteLogo = ParamUtil.getBoolean(request, "deleteLogo");

String defaultRegularThemeId = ParamUtil.getString(request, "settings(" + PropsKeys.DEFAULT_REGULAR_THEME_ID + ")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.DEFAULT_REGULAR_THEME_ID, PropsValues.DEFAULT_REGULAR_THEME_ID));
String defaultWapThemeId = ParamUtil.getString(request, "settings(" + PropsKeys.DEFAULT_WAP_THEME_ID + ")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.DEFAULT_WAP_THEME_ID, PropsValues.DEFAULT_WAP_THEME_ID));
String defaultControlPanelThemeId = ParamUtil.getString(request, "settings(" + PropsKeys.CONTROL_PANEL_LAYOUT_REGULAR_THEME_ID + ")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.CONTROL_PANEL_LAYOUT_REGULAR_THEME_ID, PropsValues.CONTROL_PANEL_LAYOUT_REGULAR_THEME_ID));
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

<h3><liferay-ui:message key="language-and-time-zone" /></h3>

<fieldset class="exp-block-labels exp-form-column">
	<div class="exp-ctrl-holder">
		<label for="<portlet:namespace />languageId"><liferay-ui:message key="default-language" /></label>

		<select name="<portlet:namespace />languageId">

			<%
			Locale locale2 = LocaleUtil.fromLanguageId(languageId);

			for (int i = 0; i < locales.length; i++) {
			%>

				<option <%= (locale2.getLanguage().equals(locales[i].getLanguage()) && locale2.getCountry().equals(locales[i].getCountry())) ? "selected" : "" %> value="<%= locales[i].getLanguage() + "_" + locales[i].getCountry() %>"><%= locales[i].getDisplayName(locale) %></option>

			<%
			}
			%>

		</select>
	</div>

	<div class="exp-ctrl-holder">
		<label for="<portlet:namespace />settings(<%= PropsKeys.LOCALES %>)"><liferay-ui:message key="available-languages" /></label>

		<input class="lfr-input-text" name="<portlet:namespace />settings(<%= PropsKeys.LOCALES %>)" type="text" value="<%= availableLocales %>" />
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

	<a class="change-company-logo" href="javascript:<portlet:namespace />openEditCompanyLogoWindow('<%= editCompanyLogoURL %>');">
		<img alt="<liferay-ui:message key="logo" />" class="avatar" id="<portlet:namespace />avatar" src="<%= themeDisplay.getPathImage() %>/company_logo?img_id=<%= deleteLogo ? 0 : company.getLogoId() %>&t=<%= ImageServletTokenUtil.getToken(company.getLogoId()) %>" />
	</a>

	<div class="portrait-icons">

		<%
		String taglibEditURL = "javascript:" + renderResponse.getNamespace() + "openEditCompanyLogoWindow('" + editCompanyLogoURL +"');";
		%>

		<liferay-ui:icon image="edit" message="change" url="<%= taglibEditURL %>" label="<%= true %>" />

		<c:if test="<%= company.getLogoId() != 0 %>">

			<%
			String taglibDeleteURL = "javascript:" + renderResponse.getNamespace() + "deleteLogo('" + themeDisplay.getPathImage() + "/company_logo?img_id=0');";
			%>

			<liferay-ui:icon image="delete" url="<%= taglibDeleteURL %>" label="<%= true %>" cssClass="modify-link" />

			<input id="<portlet:namespace />deleteLogo" name="<portlet:namespace />deleteLogo" type="hidden" value="<%= deleteLogo %>" />
		</c:if>
	</div>
</fieldset>

<h3><liferay-ui:message key="look-and-feel" /></h3>

<fieldset class="exp-block-labels exp-form-column">
	<div class="exp-ctrl-holder">
		<label for="<portlet:namespace />settings(<%= PropsKeys.DEFAULT_REGULAR_THEME_ID %>)"><liferay-ui:message key="default-regular-theme" /></label>

		<select name="<portlet:namespace />settings(<%= PropsKeys.DEFAULT_REGULAR_THEME_ID %>)">

			<%
			List<Theme> themes = ThemeLocalServiceUtil.getThemes(company.getCompanyId(), 0, user.getUserId(), false);

			boolean deployed = false;

			for (Theme curTheme: themes) {
				if (Validator.equals(defaultRegularThemeId, curTheme.getThemeId())) {
					deployed = true;
				}
			%>

				<option <%= (Validator.equals(defaultRegularThemeId, curTheme.getThemeId())) ? "selected" : "" %> value="<%= curTheme.getThemeId() %>"><%= curTheme.getName() %></option>

			<%
			}

			if (!deployed) {
			%>

				<option selected value="<%= defaultRegularThemeId %>"><%= defaultRegularThemeId + "(" + LanguageUtil.get(pageContext, "undeployed") + ")" %></option>

			<%
			}
			%>

		</select>
	</div>

	<div class="exp-ctrl-holder">
		<label for="<portlet:namespace />settings(<%= PropsKeys.DEFAULT_REGULAR_THEME_ID %>)"><liferay-ui:message key="default-mobile-theme" /></label>

		<select name="<portlet:namespace />settings(<%= PropsKeys.DEFAULT_REGULAR_THEME_ID %>)">

			<%
			themes = ThemeLocalServiceUtil.getThemes(company.getCompanyId(), 0, user.getUserId(), true);
			deployed = false;

			for (Theme curTheme: themes) {
				if (Validator.equals(defaultWapThemeId, curTheme.getThemeId())) {
					deployed = true;
				}

			%>

				<option <%= (Validator.equals(defaultWapThemeId, curTheme.getThemeId())) ? "selected" : "" %> value="<%= curTheme.getThemeId() %>"><%= curTheme.getName() %></option>

			<%
			}

			if (!deployed) {
			%>

				<option selected value="<%= defaultWapThemeId %>"><%= defaultWapThemeId + "(" + LanguageUtil.get(pageContext, "undeployed") + ")" %></option>

			<%
			}
			%>

		</select>
	</div>

	<div class="exp-ctrl-holder">
		<label for="<portlet:namespace />settings(<%= PropsKeys.CONTROL_PANEL_LAYOUT_REGULAR_THEME_ID %>)"><liferay-ui:message key="default-control-panel-theme" /></label>

		<select name="<portlet:namespace />settings(<%= PropsKeys.CONTROL_PANEL_LAYOUT_REGULAR_THEME_ID %>)">

			<%

			Theme controlPanelTheme = ThemeLocalServiceUtil.getTheme(company.getCompanyId(), "controlpanel", false);

			if (controlPanelTheme != null) {
				themes.add(controlPanelTheme);
			}

			deployed = false;

			for (Theme curTheme: themes) {
				if (Validator.equals(defaultControlPanelThemeId, curTheme.getThemeId())) {
					deployed = true;
				}

			%>

				<option <%= (Validator.equals(defaultControlPanelThemeId, curTheme.getThemeId())) ? "selected" : "" %> value="<%= curTheme.getThemeId() %>"><%= curTheme.getName() %></option>

			<%
			}

			if (!deployed) {
			%>

				<option selected value="<%= defaultControlPanelThemeId %>"><%= defaultControlPanelThemeId + "(" + LanguageUtil.get(pageContext, "undeployed") + ")" %></option>

			<%
			}
			%>

		</select>
	</div>
</fieldset>