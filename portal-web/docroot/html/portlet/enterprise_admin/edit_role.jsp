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
String redirect = ParamUtil.getString(request, "redirect");

Role role = (Role)request.getAttribute(WebKeys.ROLE);

long roleId = BeanParamUtil.getLong(role, request, "roleId");

int type = ParamUtil.getInteger(request, "type");
String subtype = BeanParamUtil.getString(role, request, "subtype");

String currentLanguageId = LanguageUtil.getLanguageId(request);
Locale currentLocale = LocaleUtil.fromLanguageId(currentLanguageId);
Locale defaultLocale = LocaleUtil.getDefault();
String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

Locale[] locales = LanguageUtil.getAvailableLocales();
%>

<liferay-ui:tabs
	names="roles"
	backURL="<%= redirect %>"
/>

<c:choose>
	<c:when test="<%= (role != null) && PortalUtil.isSystemRole(role.getName()) %>">
		<%= LanguageUtil.format(pageContext, "x-is-a-required-system-role", role.getTitle(locale)) %>
	</c:when>
	<c:otherwise>
		<script type="text/javascript">
			function <portlet:namespace />saveRole() {
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= role == null ? Constants.ADD : Constants.UPDATE %>";

				<c:if test="<%= role != null %>">
					<portlet:namespace />updateLanguage();
				</c:if>

				submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_role" /></portlet:actionURL>");
			}
		</script>

		<form class="uni-form" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveRole(); return false;">
		<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
		<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(redirect) %>" />
		<input name="<portlet:namespace />roleId" type="hidden" value="<%= roleId %>" />

		<liferay-ui:error exception="<%= DuplicateRoleException.class %>" message="please-enter-a-unique-name" />
		<liferay-ui:error exception="<%= RequiredRoleException.class %>" message="old-role-name-is-a-required-system-role" />
		<liferay-ui:error exception="<%= RoleNameException.class %>" message="please-enter-a-valid-name" />

		<fieldset class="block-labels">
			<c:if test="<%= role != null %>">
				<div class="ctrl-holder">
					<label><liferay-ui:message key="old-name" /></label>

					<%= role.getName() %>
				</div>
			</c:if>

			<div class="ctrl-holder">
				<label><%= LanguageUtil.get(pageContext, ((role != null) ? "new-name" : "name")) %></label>

				<liferay-ui:input-field model="<%= Role.class %>" bean="<%= role %>" field="name" />
			</div>

			<c:if test="<%= role != null %>">
				<div class="ctrl-holder">
					<label><liferay-ui:message key="title" /></label>

					<table class="lfr-table">
					<tr>
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

									if (Validator.isNotNull(role.getTitle(locales[i], false))) {

										optionStyle = "style=\"font-weight: bold\"";
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
						<td>
							<input id="<portlet:namespace />title_<%= defaultLanguageId %>" name="<portlet:namespace />title_<%= defaultLanguageId %>" size="30" type="text" value="<%= role.getTitle(defaultLocale) %>" />
						</td>
						<td>

							<%
							for (int i = 0; i < locales.length; i++) {
								if (locales[i].equals(defaultLocale)) {
									continue;
								}
							%>

								<input id="<portlet:namespace />title_<%= LocaleUtil.toLanguageId(locales[i]) %>" name="<portlet:namespace />title_<%= LocaleUtil.toLanguageId(locales[i]) %>" type="hidden" value="<%= role.getTitle(locales[i], false) %>" />

							<%
							}
							%>

							<input id="<portlet:namespace />title_temp" size="30" type="text" <%= currentLocale.equals(defaultLocale) ? "style='display: none'" : "" %> onChange="<portlet:namespace />onTitleChanged();" />
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<br />
						</td>
					</tr>
					</table>
				</div>
			</c:if>

			<div class="ctrl-holder">
				<label><liferay-ui:message key="description" /></label>

				<liferay-ui:input-field model="<%= Role.class %>" bean="<%= role %>" field="description" />
			</div>

			<div class="ctrl-holder">
				<label><liferay-ui:message key="type" /></label>

				<c:choose>
					<c:when test="<%= ((role == null) && (type == 0)) %>">
						<select name="<portlet:namespace/>type">
							<option value="<%= RoleConstants.TYPE_REGULAR %>"><liferay-ui:message key="regular" /></option>
							<option value="<%= RoleConstants.TYPE_COMMUNITY %>"><liferay-ui:message key="community" /></option>
							<option value="<%= RoleConstants.TYPE_ORGANIZATION %>"><liferay-ui:message key="organization" /></option>
						</select>
					</c:when>
					<c:when test="<%= (role == null) %>">
						<input type="hidden" name="<portlet:namespace/>type" value="<%= String.valueOf(type) %>" />

						<%= LanguageUtil.get(pageContext, RoleConstants.getTypeLabel(type)) %>
					</c:when>
					<c:otherwise>
						<%= LanguageUtil.get(pageContext, role.getTypeLabel()) %>
					</c:otherwise>
				</c:choose>
			</div>

			<c:if test="<%= role != null %>">

				<%
				String[] subtypes = null;

				if (role.getType() == RoleConstants.TYPE_COMMUNITY) {
					subtypes = PropsValues.ROLES_COMMUNITY_SUBTYPES;
				}
				else if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
					subtypes = PropsValues.ROLES_ORGANIZATION_SUBTYPES;
				}
				else if (role.getType() == RoleConstants.TYPE_REGULAR) {
					subtypes = PropsValues.ROLES_REGULAR_SUBTYPES;
				}
				else {
					subtypes = new String[0];
				}
				%>

				<c:if test="<%= subtypes.length > 0 %>">
					<div class="ctrl-holder">
						<label><liferay-ui:message key="subtype" /></label>

						<select name="<portlet:namespace/>subtype">
							<option value=""></option>

							<%
							for (String curSubtype : subtypes) {
							%>

								<option <%= subtype.equals(curSubtype) ? "selected" : "" %> value="<%= curSubtype %>"><liferay-ui:message key="<%= curSubtype %>" /></option>

							<%
							}
							%>

						</select>
					</div>
				</c:if>
			</c:if>

			<div class="button-holder">
				<input type="submit" value="<liferay-ui:message key="save" />" />

				<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />
			</div>
		</fieldset>

		</form>

		<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
			<script type="text/javascript">
				Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
			</script>
		</c:if>
	</c:otherwise>
</c:choose>

<script type="text/javascript">
	var titleChanged = false;
	var lastLanguageId = "<%= currentLanguageId %>";

	function <portlet:namespace />onTitleChanged() {
		titleChanged = true;
	}

	function <portlet:namespace />updateLanguage() {
		if (lastLanguageId != "<%= defaultLanguageId %>") {
			if (titleChanged) {
				var titleValue = jQuery("#<portlet:namespace />title_temp").attr("value");

				if (titleValue == null) {
					titleValue = "";
				}

				jQuery("#<portlet:namespace />title_" + lastLanguageId).attr("value", titleValue);

				titleChanged = false;
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

			jQuery("#<portlet:namespace />title_temp").show();
		}
		else {
			jQuery("#<portlet:namespace />title_temp").hide();
		}

		lastLanguageId = selLanguageId;

		return null;
	}

	function <portlet:namespace />updateLanguageTemps(lang) {
		if (lang != "<%= defaultLanguageId %>") {
			var titleValue = jQuery("#<portlet:namespace />title_" + lang).attr("value");
			var defaultTitleValue = jQuery("#<portlet:namespace />title_<%= defaultLanguageId %>").attr("value");

			if (defaultTitleValue == null) {
				defaultTitleValue = "";
			}

			if ((titleValue == null) || (titleValue == "")) {
				jQuery("#<portlet:namespace />title_temp").attr("value", defaultTitleValue);
			}
			else {
				jQuery("#<portlet:namespace />title_temp").attr("value", titleValue);
			}
		}
	}

	<portlet:namespace />updateLanguageTemps(lastLanguageId);
</script>