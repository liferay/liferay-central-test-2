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

<%@ include file="/html/portlet/login/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "general");
String tabs2 = ParamUtil.getString(request, "tabs2", "general");
String redirect = ParamUtil.getString(request, "redirect");
%>

<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL" portletConfiguration="true">
	<portlet:param name="tabs1" value="<%= tabs1 %>" />
	<portlet:param name="tabs2" value="<%= tabs2 %>" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</liferay-portlet:renderURL>

<script type="text/javascript">
	function <portlet:namespace />saveConfiguration() {
		<c:if test='<%= tabs2.equals("password-changed-notification") %>'>
			<portlet:namespace />updateLanguage();
		</c:if>

		submitForm(document.<portlet:namespace />fm);
	}
</script>
<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
<input name="<portlet:namespace />tabs1" type="hidden" value="<%= HtmlUtil.escape(tabs1) %>" />
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= HtmlUtil.escape(tabs2) %>" />

<liferay-ui:tabs
	names="general,email-notifications"
	param="tabs1"
	url="<%= portletURL %>"
/>

<c:choose>
	<c:when test='<%= tabs1.equals("general") %>'>
		<table class="lfr-table">
			<tr>
				<td>
					<liferay-ui:message key="authentication-type" />
				</td>
				<td>
					<select name="<portlet:namespace />authType">
						<option value=""><liferay-ui:message key="default" /></option>
						<option <%= authType.equals(CompanyConstants.AUTH_TYPE_EA) ? "selected" : "" %> value="<%= CompanyConstants.AUTH_TYPE_EA %>"><liferay-ui:message key="by-email-address" /></option>
						<option <%= authType.equals(CompanyConstants.AUTH_TYPE_SN) ? "selected" : "" %> value="<%= CompanyConstants.AUTH_TYPE_SN %>"><liferay-ui:message key="by-screen-name" /></option>
						<option <%= authType.equals(CompanyConstants.AUTH_TYPE_ID) ? "selected" : "" %> value="<%= CompanyConstants.AUTH_TYPE_ID %>"><liferay-ui:message key="by-user-id" /></option>
					</select>
				</td>
			</tr>
		</table>
	</c:when>

	<c:when test='<%= tabs1.equals("email-notifications") %>'>
		<%
		String emailFromName = PrefsParamUtil.getString(preferences, request, "emailFromName", StringPool.BLANK);
		String emailFromAddress = PrefsParamUtil.getString(preferences, request, "emailFromAddress", StringPool.BLANK);
		%>

		<liferay-ui:tabs
			names="general,password-changed-notification"
			param="tabs2"
			url="<%= portletURL %>"
		/>

		<c:choose>
			<c:when test='<%= tabs2.equals("general") %>'>

				<span class="portlet-msg-info">
					<liferay-ui:message key="enter-custom-values-for-the-email.-if-this-fields-are-empty,-the-default-portal-settings-will-be-used" />
				</span>

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
							<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />

							<input class="lfr-input-text" name="<portlet:namespace />emailFromAddress" type="text" value="<%= emailFromAddress %>" />
						</td>
					</tr>
				</table>

			</c:when>

			<c:when test='<%= tabs2.equals("password-changed-notification") %>'>

				<%
				String currentLanguageId = LanguageUtil.getLanguageId(request);
				Locale currentLocale = LocaleUtil.fromLanguageId(currentLanguageId);
				Locale defaultLocale = LocaleUtil.getDefault();
				String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);
				Locale[] locales = LanguageUtil.getAvailableLocales();

				String emailPasswordSentSubject = PrefsParamUtil.getString(preferences, request, "emailPasswordSentSubject_" + defaultLanguageId, StringPool.BLANK);
				String emailPasswordSentBody = PrefsParamUtil.getString(preferences, request, "emailPasswordSentBody_" + defaultLanguageId, StringPool.BLANK);
				%>

				<table class="lfr-table">
				<tr>
					<td>
						<liferay-ui:message key="enabled" />
					</td>
					<td>
						<liferay-ui:input-checkbox param="emailPasswordSentEnabled" defaultValue='<%= PrefsParamUtil.getBoolean(preferences, request, "emailPasswordSentEnabled", true) %>' />
					</td>
					<td>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<br />
					</td>
				</tr>
				<tr>
					<td>
					</td>
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

								String optionStyle = StringPool.BLANK;

								if (Validator.isNotNull(PrefsParamUtil.getString(preferences, request, "emailPasswordSentSubject_" + LocaleUtil.toLanguageId(locales[i]), StringPool.BLANK)) ||
									Validator.isNotNull(PrefsParamUtil.getString(preferences, request, "emailPasswordSentBody_" + LocaleUtil.toLanguageId(locales[i]), StringPool.BLANK))) {

									optionStyle = "style=\"font-weight: bold\"";
								}
							%>

								<option <%= (currentLanguageId.equals(LocaleUtil.toLanguageId(locales[i]))) ? "selected" : "" %> <%= optionStyle %> value="<%= LocaleUtil.toLanguageId(locales[i]) %>"><%= locales[i].getDisplayName(locales) %></option>

							<%
							}
							%>

						</select>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<br />
					</td>
				</tr>
				<tr>
					<td>
						<liferay-ui:message key="subject" />
					</td>
					<td>
						<input class="lfr-input-text" id="<portlet:namespace />emailPasswordSentSubject_<%= defaultLanguageId %>" name="<portlet:namespace />emailPasswordSentSubject_<%= defaultLanguageId %>" type="text" value="<%= emailPasswordSentSubject %>" />
					</td>
					<td>

						<%
						for (int i = 0; i < locales.length; i++) {
							if (locales[i].equals(defaultLocale)) {
								continue;
							}
						%>

						<input class="lfr-input-text" id="<portlet:namespace />emailPasswordSentSubject_<%= LocaleUtil.toLanguageId(locales[i]) %>" name="<portlet:namespace />emailPasswordSentSubject_<%= LocaleUtil.toLanguageId(locales[i]) %>"  type="hidden" value="<%= PrefsParamUtil.getString(preferences, request, "emailPasswordSentSubject_" + LocaleUtil.toLanguageId(locales[i]), StringPool.BLANK) %>" />

						<%
						}
						%>

						<input class="lfr-input-text" id="<portlet:namespace />emailPasswordSentSubject_temp"  type="text" <%= currentLocale.equals(defaultLocale) ? "style='display: none'" : "" %> onChange="<portlet:namespace />onSubjectChanged();" />
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<br />
					</td>
				</tr>
				<tr>
					<td>
						<liferay-ui:message key="body" />
					</td>
					<td>
						<textarea id="<portlet:namespace />emailPasswordSentBody_<%= defaultLanguageId %>" name="<portlet:namespace />emailPasswordSentBody_<%= defaultLanguageId %>" style='height: 200px; width: 350px;'><%= emailPasswordSentBody  %></textarea>
					</td>
					<td>

						<%
						for (int i = 0; i < locales.length; i++) {
							if (locales[i].equals(defaultLocale)) {
								continue;
							}
						%>

							<textarea id="<portlet:namespace />emailPasswordSentBody_<%= LocaleUtil.toLanguageId(locales[i]) %>" name="<portlet:namespace />emailPasswordSentBody_<%= LocaleUtil.toLanguageId(locales[i]) %>" style='display:none; height: 200px; width: 350px;' ><%= PrefsParamUtil.getString(preferences, request, "emailPasswordSentBody_" + LocaleUtil.toLanguageId(locales[i]), StringPool.BLANK) %></textarea>

						<%
						}
						%>

						<textarea id="<portlet:namespace />emailPasswordSentBody_temp" onChange="<portlet:namespace />onBodyChanged();"  <%= currentLocale.equals(defaultLocale) ? "style='display: none; height: 200px; width: 350px;'" : "style='height: 200px; width: 350px;'" %> ></textarea>

					</td>
				</tr>
				</table>

				<br />

				<b><liferay-ui:message key="definition-of-terms" /></b>

				<br /><br />

				<table class="lfr-table">
					<tr>
						<td>
							<b>[$FROM_ADDRESS$]</b>
						</td>
						<td>
							<%= preferences.getValue("emailFromAddress", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_ADDRESS)) %>
						</td>
					</tr>
					<tr>
						<td>
							<b>[$FROM_NAME$]</b>
						</td>
						<td>
							<%=  preferences.getValue("emailFromName", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_NAME)) %>
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
							<b>[$REMOTE_ADDRESS$]</b>
						</td>
						<td>
							The browser's remote address
						</td>
					</tr>
					<tr>
						<td>
							<b>[$REMOTE_HOST$]</b>
						</td>
						<td>
							The browser's remote host
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

					<tr>
						<td>
							<b>[$USER_AGENT$]</b>
						</td>
						<td>
							The browser's user agent
						</td>
					</tr>

					<tr>
						<td>
							<b>[$USER_ID$]</b>
						</td>
						<td>
							The user ID
						</td>
					</tr>
					<tr>
						<td>
							<b>[$USER_PASSWORD$]</b>
						</td>
						<td>
							The user password
						</td>
					</tr>
					<tr>
						<td>
							<b>[$USER_SCREENNAME$]</b>
						</td>
						<td>
							The user screen name
						</td>
					</tr>
				</table>

				<script type="text/javascript">

					var subjectChanged = false;
					var bodyChanged = false;
					var lastLanguageId = "<%= currentLanguageId %>";

					function <portlet:namespace />onSubjectChanged() {
						subjectChanged = true;
					}

					function <portlet:namespace />onBodyChanged() {
						bodyChanged = true;
					}

					function <portlet:namespace />updateLanguage() {
						if (lastLanguageId != "<%= defaultLanguageId %>") {
							if (subjectChanged) {
								var subjectValue = jQuery("#<portlet:namespace />emailPasswordSentSubject_temp").attr("value");

								if (subjectValue == null) {
									subjectValue = "";
								}

								jQuery("#<portlet:namespace />emailPasswordSentSubject_" + lastLanguageId).attr("value", subjectValue);

								subjectChanged = false;
							}

							if (bodyChanged) {
								var bodyValue = jQuery("#<portlet:namespace />emailPasswordSentBody_temp").attr("value");

								if (bodyValue == null) {
									bodyValue = "";
								}

								jQuery("#<portlet:namespace />emailPasswordSentBody_" + lastLanguageId).attr("value", bodyValue);

								bodyChanged = false;
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

							jQuery("#<portlet:namespace />emailPasswordSentSubject_temp").show();
							jQuery("#<portlet:namespace />emailPasswordSentBody_temp").show();
						}
						else {
							jQuery("#<portlet:namespace />emailPasswordSentSubject_temp").hide();
							jQuery("#<portlet:namespace />emailPasswordSentBody_temp").hide();
						}

						lastLanguageId = selLanguageId;

						return null;
					}

					function <portlet:namespace />updateLanguageTemps(lang) {
						if (lang != "<%= defaultLanguageId %>") {
							var subjectValue = jQuery("#<portlet:namespace />emailPasswordSentSubject_" + lang).attr("value");
							var bodyValue = jQuery("#<portlet:namespace />emailPasswordSentBody_" + lang).attr("value");
							var defaultSubjectValue = jQuery("#<portlet:namespace />emailPasswordSentSubject_<%= defaultLanguageId %>").attr("value");
							var defaultBodyValue = jQuery("#<portlet:namespace />emailPasswordSentBody_<%= defaultLanguageId %>").attr("value");

							if (defaultSubjectValue == null) {
								defaultSubjectValue = "";
							}

							if (defaultBodyValue == null) {
								defaultBodyValue = "";
							}

							if ((subjectValue == null) || (subjectValue == "")) {
								jQuery("#<portlet:namespace />emailPasswordSentSubject_temp").attr("value", defaultSubjectValue);
							}
							else {
								jQuery("#<portlet:namespace />emailPasswordSentSubject_temp").attr("value", subjectValue);
							}

							if ((bodyValue == null) || (bodyValue == "")) {
								jQuery("#<portlet:namespace />emailPasswordSentBody_temp").attr("value", defaultBodyValue);
							}
							else {
								jQuery("#<portlet:namespace />emailPasswordSentBody_temp").attr("value", bodyValue);
							}
						}
					}

					<portlet:namespace />updateLanguageTemps(lastLanguageId);

				</script>
			</c:when>
		</c:choose>
	</c:when>
</c:choose>

<br />

<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveConfiguration();" />

</form>