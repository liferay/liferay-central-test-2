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

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />

	<liferay-ui:tabs
		names="general,email-notifications"
		param="tabs1"
		url="<%= portletURL %>"
	/>

	<c:choose>
		<c:when test='<%= tabs1.equals("email-notifications") %>'>

			<%
			String currentLanguageId = LanguageUtil.getLanguageId(request);
			Locale defaultLocale = LocaleUtil.getDefault();
			String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

			Locale[] locales = LanguageUtil.getAvailableLocales();

			String emailFromName = PrefsParamUtil.getString(preferences, request, "emailFromName");
			String emailFromAddress = PrefsParamUtil.getString(preferences, request, "emailFromAddress");

			String emailPasswordSentSubject = PrefsParamUtil.getString(preferences, request, "emailPasswordSentSubject_" + currentLanguageId, StringPool.BLANK);
			String emailPasswordSentBody = PrefsParamUtil.getString(preferences, request, "emailPasswordSentBody_" + currentLanguageId, StringPool.BLANK);

			String editorParam = "emailPasswordSentBody_" + currentLanguageId;
			String editorContent = emailPasswordSentBody;
			%>

			<script type="text/javascript">
				function <portlet:namespace />initEditor() {
					return "<%= UnicodeFormatter.toString(editorContent) %>";
				}

				function <portlet:namespace />saveConfiguration() {
					<c:if test='<%= tabs2.endsWith("-notification") %>'>
						document.<portlet:namespace />fm.<portlet:namespace /><%= editorParam %>.value = window.<portlet:namespace />editor.getHTML();
					</c:if>

					submitForm(document.<portlet:namespace />fm);
				}

				function <portlet:namespace />updateLanguage() {
					document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '';
					submitForm(document.<portlet:namespace />fm);
				}
			</script>

			<liferay-ui:tabs
				names="general,password-changed-notification"
				param="tabs2"
				url="<%= portletURL %>"
			/>

			<div class="portlet-msg-info">
				<liferay-ui:message key="enter-custom-values-or-leave-it-blank-to-use-the-default-portal-settings" />
			</div>

			<c:choose>
				<c:when test='<%= tabs2.equals("password-changed-notification") %>'>
					<aui:fieldset>
						<aui:input inlineLabel="left" label="enabled" name="emailPasswordSentEnabled" type="checkbox" value='<%= PrefsParamUtil.getBoolean(preferences, request, "emailPasswordSentEnabled", true) %>' />

						<aui:select label="language" name="languageId" onChange='<%= renderResponse.getNamespace() + "updateLanguage(this);" %>'>

							<%
							for (int i = 0; i < locales.length; i++) {
								String optionStyle = StringPool.BLANK;

								if (Validator.isNotNull(preferences.getValue("emailPasswordSentSubject_" + LocaleUtil.toLanguageId(locales[i]), StringPool.BLANK)) ||
									Validator.isNotNull(preferences.getValue("emailPasswordSentBody_" + LocaleUtil.toLanguageId(locales[i]), StringPool.BLANK))) {

									optionStyle = "style=\"font-weight: bold;\"";
								}
							%>

								<aui:option label="<%= locales[i].getDisplayName(locale) %>" selected="<%= currentLanguageId.equals(LocaleUtil.toLanguageId(locales[i])) %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

							<%
							}
							%>

						</aui:select>

						<aui:input cssClass="lfr-input-text-container" label="subject" name='<%= "emailPasswordSentSubject" + StringPool.UNDERLINE + currentLanguageId %>' value="<%= emailPasswordSentSubject %>" />

						<aui:field-wrapper label="body">
							<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" />

							<aui:input name="<%= editorParam %>" type="hidden" />
						</aui:field-wrapper>
					</aui:fieldset>

					<strong><liferay-ui:message key="definition-of-terms" /></strong>

					<br /><br />

					<table class="lfr-table">
					<tr>
						<td>
							<strong>[$FROM_ADDRESS$]</strong>
						</td>
						<td>
							<%= preferences.getValue("emailFromAddress", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_ADDRESS)) %>
						</td>
					</tr>
					<tr>
						<td>
							<strong>[$FROM_NAME$]</strong>
						</td>
						<td>
							<%= preferences.getValue("emailFromName", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_NAME)) %>
						</td>
					</tr>
					<tr>
						<td>
							<strong>[$PORTAL_URL$]</strong>
						</td>
						<td>
							<%= company.getVirtualHost() %>
						</td>
					</tr>
					<tr>
						<td>
							<strong>[$REMOTE_ADDRESS$]</strong>
						</td>
						<td>
							The browser's remote address
						</td>
					</tr>
					<tr>
						<td>
							<strong>[$REMOTE_HOST$]</strong>
						</td>
						<td>
							The browser's remote host
						</td>
					</tr>

					<tr>
						<td>
							<strong>[$TO_ADDRESS$]</strong>
						</td>
						<td>
							The address of the email recipient
						</td>
					</tr>
					<tr>
						<td>
							<strong>[$TO_NAME$]</strong>
						</td>
						<td>
							The name of the email recipient
						</td>
					</tr>

					<tr>
						<td>
							<strong>[$USER_AGENT$]</strong>
						</td>
						<td>
							The browser's user agent
						</td>
					</tr>

					<tr>
						<td>
							<strong>[$USER_ID$]</strong>
						</td>
						<td>
							The user ID
						</td>
					</tr>
					<tr>
						<td>
							<strong>[$USER_PASSWORD$]</strong>
						</td>
						<td>
							The user password
						</td>
					</tr>
					<tr>
						<td>
							<strong>[$USER_SCREENNAME$]</strong>
						</td>
						<td>
							The user screen name
						</td>
					</tr>
					</table>
				</c:when>
				<c:otherwise>
					<aui:fieldset>
						<aui:input cssClass="lfr-input-text-container" label="name" name="emailFromName" value="<%= emailFromName %>" />

						<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />

						<aui:input cssClass="lfr-input-text-container" label="address" name="emailFromAddress" value="<%= emailFromAddress %>" />
					</aui:fieldset>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<aui:fieldset>
				<aui:select label="authentication-type" name="authType">
					<aui:option label="default" value="" />
					<aui:option label="by-email-address" selected="<%= authType.equals(CompanyConstants.AUTH_TYPE_EA) %>" value="<%= CompanyConstants.AUTH_TYPE_EA %>" />
					<aui:option label="by-screen-name" selected="<%= authType.equals(CompanyConstants.AUTH_TYPE_SN) %>" value="<%= CompanyConstants.AUTH_TYPE_SN %>" />
					<aui:option label="by-user-id" selected="<%= authType.equals(CompanyConstants.AUTH_TYPE_ID) %>" value="<%= CompanyConstants.AUTH_TYPE_ID %>" />
				</aui:select>
			</aui:fieldset>
		</c:otherwise>
	</c:choose>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.login.configuration.jsp";
%>