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

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
<input name="<portlet:namespace />tabs1" type="hidden" value="<%= HtmlUtil.escapeAttribute(tabs1) %>" />
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= HtmlUtil.escapeAttribute(tabs2) %>" />

<liferay-ui:tabs
	names="general,email-notifications"
	param="tabs1"
	url="<%= portletURL %>"
/>

<c:choose>
	<c:when test='<%= tabs1.equals("email-notifications") %>'>
		<script type="text/javascript">

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
				<table class="lfr-table">
				<tr>
					<td>
						<liferay-ui:message key="enabled" />
					</td>
					<td>
						<liferay-ui:input-checkbox param="emailPasswordSentEnabled" defaultValue='<%= PrefsParamUtil.getBoolean(preferences, request, "emailPasswordSentEnabled", true) %>' />
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<br />
					</td>
				</tr>
				<tr>
					<td>
						<liferay-ui:message key="language" />
					</td>
					<td>
						<select name="<portlet:namespace />languageId" onChange="<portlet:namespace />updateLanguage(this);">

							<%
							for (int i = 0; i < locales.length; i++) {
								String optionStyle = StringPool.BLANK;

								if (Validator.isNotNull(preferences.getValue("emailPasswordSentSubject_" + LocaleUtil.toLanguageId(locales[i]), StringPool.BLANK)) ||
									Validator.isNotNull(preferences.getValue("emailPasswordSentBody_" + LocaleUtil.toLanguageId(locales[i]), StringPool.BLANK))) {

									optionStyle = "style=\"font-weight: bold;\"";
								}
							%>

								<option <%= (currentLanguageId.equals(LocaleUtil.toLanguageId(locales[i]))) ? "selected" : "" %> <%= optionStyle %> value="<%= LocaleUtil.toLanguageId(locales[i]) %>"><%= locales[i].getDisplayName(locale) %></option>

							<%
							}
							%>

						</select>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<br />
					</td>
				</tr>
				<tr>
					<td>
						<liferay-ui:message key="subject" />
					</td>
					<td>
						<input class="lfr-input-text" name="<portlet:namespace />emailPasswordSentSubject_<%= currentLanguageId %>" type="text" value="<%= emailPasswordSentSubject %>" />
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
						<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" />

						<input name="<portlet:namespace /><%= editorParam %>" type="hidden" value="" />
					</td>
				</tr>
				</table>

				<br />

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
			</c:otherwise>
		</c:choose>

		<br />

		<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveConfiguration();" />
	</c:when>
	<c:otherwise>
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

		<br />

		<input type="button" value="<liferay-ui:message key="save" />" onClick="submitForm(document.<portlet:namespace />fm);" />
	</c:otherwise>
</c:choose>

</form>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.login.configuration.jsp";
%>