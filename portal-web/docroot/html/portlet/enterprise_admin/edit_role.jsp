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
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

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

<liferay-util:include page="/html/portlet/enterprise_admin/role/toolbar.jsp">
	<liferay-util:param name="toolbarItem" value='<%= (role == null) ? "add" : "view-all" %>' />
	<liferay-util:param name="backURL" value="<%= backURL %>" />
</liferay-util:include>

<c:if test="<%= role != null %>">
	<liferay-util:include page="/html/portlet/enterprise_admin/edit_role_tabs.jsp">
		<liferay-util:param name="tabs1" value="edit" />
	</liferay-util:include>
</c:if>

<script type="text/javascript">
	function <portlet:namespace />saveRole() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= role == null ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_role" /></portlet:actionURL>");
	}
</script>

<form class="exp-form" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveRole(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(redirect) %>" />
<input name="<portlet:namespace />roleId" type="hidden" value="<%= roleId %>" />

<liferay-ui:error exception="<%= DuplicateRoleException.class %>" message="please-enter-a-unique-name" />
<liferay-ui:error exception="<%= RequiredRoleException.class %>" message="old-role-name-is-a-required-system-role" />
<liferay-ui:error exception="<%= RoleNameException.class %>" message="please-enter-a-valid-name" />

<fieldset class="exp-block-labels">
	<c:if test="<%= role != null %>">
		<div class="exp-ctrl-holder">
			<label><%= LanguageUtil.get(pageContext, PortalUtil.isSystemRole(role.getName()) ? "name" : "old-name") %></label>

			<%= HtmlUtil.escape(role.getName()) %>
		</div>
	</c:if>

	<c:choose>
		<c:when test="<%= (role != null) && PortalUtil.isSystemRole(role.getName()) %>">
			<input type="hidden" name="name" value="<%= HtmlUtil.escape(role.getName()) %>" />
		</c:when>
		<c:otherwise>
			<div class="exp-ctrl-holder">
				<label><%= LanguageUtil.get(pageContext, ((role != null) ? "new-name" : "name")) %></label>

				<liferay-ui:input-field model="<%= Role.class %>" bean="<%= role %>" field="name" />
			</div>
		</c:otherwise>
	</c:choose>

	<c:if test="<%= role != null %>">
		<div class="exp-ctrl-holder">
			<label><liferay-ui:message key="title" /></label>

			<input id="<portlet:namespace />title_<%= defaultLanguageId %>" name="<portlet:namespace />title_<%= defaultLanguageId %>" size="30" type="text" value="<%= HtmlUtil.escape(role.getTitle(defaultLocale)) %>" />

			<img class="default-language" src="<%= themeDisplay.getPathThemeImages() %>/language/<%= defaultLanguageId %>.png" />

			<%
			List<String> languageIds = new ArrayList<String>();

			for (int i = 0; i < locales.length; i++) {
				if (locales[i].equals(defaultLocale)) {
					continue;
				}

				if (Validator.isNotNull(role.getTitle(locales[i], false))) {
					languageIds.add(LanguageUtil.getLanguageId(locales[i]));
				}
			}
			%>

			<a class="lfr-floating-trigger" href="javascript:;" id="<portlet:namespace />languageSelectorTrigger">
				<liferay-ui:message key="other-languages" /> (<%= languageIds.size() %>)
			</a>

			<%
			if (languageIds.isEmpty()) {
				languageIds.add(StringPool.BLANK);
			}
			%>

			<div class="lfr-floating-container lfr-role-selector" id="<portlet:namespace />languageSelector">
				<div class="lfr-panel exp-form">
					<div class="lfr-panel-titlebar">
						<h3 class="lfr-panel-title"><span><liferay-ui:message key="other-languages" /></span></h3>
					</div>

					<div class="lfr-panel-content">

						<%
						for (int i = 0; i < languageIds.size(); i++) {
							String languageId = languageIds.get(i);
						%>

							<div class="lfr-form-row">
								<div class="row-fields">
									<div class="exp-ctrl-holder exp-form-column">
										<img class="language-flag" src="<%= themeDisplay.getPathThemeImages() %>/language/<%= Validator.isNotNull(languageId) ? languageId : "../spacer" %>.png" />

										<select id="<portlet:namespace />languageId<%= i %>">
											<option value="" />

											<%
											for (int j = 0; j < locales.length; j++) {
												if (locales[j].equals(defaultLocale)) {
													continue;
												}

												String optionStyle = StringPool.BLANK;

												if (Validator.isNotNull(role.getTitle(locales[j], false))) {
													optionStyle = "style=\"font-weight: bold\"";
												}
											%>

												<option <%= (languageId.equals(LocaleUtil.toLanguageId(locales[j]))) ? "selected" : "" %> <%= optionStyle %> value="<%= LocaleUtil.toLanguageId(locales[j]) %>"><%= locales[j].getDisplayName(locale) %></option>

											<%
											}
											%>

										</select>
									</div>

									<div class="exp-ctrl-holder exp-form-column">
										<label><liferay-ui:message key="title" /></label>

										<input class="language-value" name="<portlet:namespace />title_<%= languageId %>" type="text" value="<%= HtmlUtil.escape(role.getTitle(languageId, false)) %>" />
									</div>
								</div>
							</div>

						<%
						}
						%>

					</div>
				</div>
			</div>

		</div>
	</c:if>

	<div class="exp-ctrl-holder">
		<label><liferay-ui:message key="description" /></label>

		<liferay-ui:input-field model="<%= Role.class %>" bean="<%= role %>" field="description" />
	</div>

	<div class="exp-ctrl-holder">
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
			<div class="exp-ctrl-holder">
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

	<div class="exp-button-holder">
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

<script type="text/javascript">
	jQuery(
		function () {
			new Liferay.AutoFields(
				{
					container: '#<portlet:namespace />languageSelector .lfr-panel-content',
					baseRows: '#<portlet:namespace />languageSelector .lfr-form-row'
				}
			);

			var panel = new Liferay.PanelFloating(
				{
					container: '#<portlet:namespace />languageSelector',
					trigger: '#<portlet:namespace />languageSelectorTrigger',
					width: 500,
					isCollapsible: false
				}
			);

			panel.bind(
				'hide',
				function(event) {
					var instance = this;

					var container = instance.get('container');

					jQuery(document.<portlet:namespace />fm).append(container);
				}
			);

			jQuery('#<portlet:namespace />languageSelector select').change(
				function(event) {
					var selectedOption = this[this.selectedIndex];
					var selectedValue = selectedOption.value;

					var newName = '<portlet:namespace />title_';

					var currentRow = jQuery(this).parents('.lfr-form-row:first');

					var img = currentRow.find('img.language-flag');
					var imgSrc = 'spacer';

					if (selectedValue) {
						newName ='<portlet:namespace />title_' + selectedValue;

						imgSrc = 'language/' + selectedValue;
					}

					var inputField = currentRow.find('.language-value');

					inputField.attr('name', newName);
					inputField.attr('id', newName);

					img.attr('src', '<%= themeDisplay.getPathThemeImages() %>/' + imgSrc + '.png');
				}
			);
		}
	);
</script>