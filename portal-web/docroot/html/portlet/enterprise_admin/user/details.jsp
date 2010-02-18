<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
Contact selContact = (Contact)request.getAttribute("user.selContact");

String displayEmailAddress = StringPool.BLANK;

if (selUser != null) {
	displayEmailAddress = selUser.getDisplayEmailAddress();
}

int prefixId = BeanParamUtil.getInteger(selContact, request, "prefixId");
int suffixId = BeanParamUtil.getInteger(selContact, request, "suffixId");
boolean male = BeanParamUtil.getBoolean(selContact, request, "male", true);

Calendar birthday = CalendarFactoryUtil.getCalendar();

birthday.set(Calendar.MONTH, Calendar.JANUARY);
birthday.set(Calendar.DATE, 1);
birthday.set(Calendar.YEAR, 1970);

if (selContact != null) {
	birthday.setTime(selContact.getBirthday());
}

boolean deletePortrait = ParamUtil.getBoolean(request, "deletePortrait");
%>

<liferay-ui:error-marker key="errorSection" value="details" />

<aui:model-context bean="<%= selUser %>" model="<%= User.class %>" />

<h3><liferay-ui:message key="details" /></h3>

<aui:fieldset column="<%= true %>">
	<aui:select label="title" name="prefixId" listType="<%= ListTypeConstants.CONTACT_PREFIX %>" showEmptyOption="<%= true %>" />

	<liferay-ui:error exception="<%= DuplicateUserScreenNameException.class %>" message="the-screen-name-you-requested-is-already-taken" />
	<liferay-ui:error exception="<%= ReservedUserScreenNameException.class %>" message="the-screen-name-you-requested-is-reserved" />
	<liferay-ui:error exception="<%= UserScreenNameException.class %>" message="please-enter-a-valid-screen-name" />

	<c:if test="<%= !PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE) || (selUser != null) %>">
		<c:choose>
			<c:when test="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE) || (PropsValues.FIELD_EDITABLE_COM_LIFERAY_PORTAL_MODEL_USER_SCREENNAME_ADMIN && !permissionChecker.isCompanyAdmin()) || (PropsValues.FIELD_EDITABLE_COM_LIFERAY_PORTAL_MODEL_USER_SCREENNAME_USER_MX && !selUser.hasCompanyMx()) %>">
				<aui:field-wrapper name="screenName">
					<%= selUser.getScreenName() %>

					<aui:input name="screenName" type="hidden" value="<%= selUser.getScreenName() %>" />
				</aui:field-wrapper>
			</c:when>
			<c:otherwise>
				<aui:input name="screenName" />
			</c:otherwise>
		</c:choose>
	</c:if>

	<liferay-ui:error exception="<%= DuplicateUserEmailAddressException.class %>" message="the-email-address-you-requested-is-already-taken" />
	<liferay-ui:error exception="<%= ReservedUserEmailAddressException.class %>" message="the-email-address-you-requested-is-reserved" />
	<liferay-ui:error exception="<%= UserEmailAddressException.class %>" message="please-enter-a-valid-email-address" />

	<c:choose>
		<c:when test="<%= (selUser != null) && ((PropsValues.FIELD_EDITABLE_COM_LIFERAY_PORTAL_MODEL_USER_EMAILADDRESS_ADMIN && !permissionChecker.isCompanyAdmin()) || (PropsValues.FIELD_EDITABLE_COM_LIFERAY_PORTAL_MODEL_USER_EMAILADDRESS_USER_MX && !selUser.hasCompanyMx())) %>">
			<aui:field-wrapper name="emailAddress">
				<%= displayEmailAddress %>

				<aui:input name="emailAddress" type="hidden" value="<%= selUser.getEmailAddress() %>" />
			</aui:field-wrapper>
		</c:when>
		<c:otherwise>
			<aui:input name="emailAddress" />
		</c:otherwise>
	</c:choose>

	<liferay-ui:error exception="<%= ContactFirstNameException.class %>" message="please-enter-a-valid-first-name" />
	<liferay-ui:error exception="<%= ContactFullNameException.class %>" message="please-enter-a-valid-first-middle-and-last-name" />

	<aui:model-context bean="<%= selContact %>" model="<%= Contact.class %>" />

	<aui:input name="firstName" />

	<aui:input name="middleName" />

	<liferay-ui:error exception="<%= ContactLastNameException.class %>" message="please-enter-a-valid-last-name" />

	<aui:input name="lastName" />

	<aui:select label="suffix" name="suffixId" listType="<%= ListTypeConstants.CONTACT_SUFFIX %>" showEmptyOption="<%= true %>" />
</aui:fieldset>

<aui:fieldset column="<%= true %>">
	<div>
		<c:if test="<%= selUser != null %>">
			<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="editUserPortraitURL">
				<portlet:param name="struts_action" value="/enterprise_admin/edit_user_portrait" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="p_u_i_d" value="<%= String.valueOf(selUser.getUserId()) %>" />
				<portlet:param name="portrait_id" value="<%= String.valueOf(selUser.getPortraitId()) %>" />
			</portlet:renderURL>

			<%
			String taglibEditURL = "javascript:" + renderResponse.getNamespace() + "openEditUserPortraitWindow('" + editUserPortraitURL + "');";
			%>

			<aui:a cssClass="change-avatar" href="<%= taglibEditURL %>"><img alt="<liferay-ui:message key="avatar" />" class="avatar" id="<portlet:namespace />avatar" src='<%= themeDisplay.getPathImage() %>/user_<%= selUser.isFemale() ? "female" : "male" %>_portrait?img_id=<%= deletePortrait ? 0 : selUser.getPortraitId() %>&t=<%= ImageServletTokenUtil.getToken(selUser.getPortraitId()) %>' /></aui:a>

			<div class="portrait-icons">
				<liferay-ui:icon image="edit" message="change" url="<%= taglibEditURL %>" label="<%= true %>" />

				<c:if test="<%= selUser.getPortraitId() > 0 %>">

					<%
					String taglibDeleteURL = "javascript:" + renderResponse.getNamespace() + "deletePortrait('" + themeDisplay.getPathImage() + "/user_" + (selUser.isFemale() ? "female" : "male") + "_portrait?img_id=0');";
					%>

					<liferay-ui:icon image="delete" url="<%= taglibDeleteURL %>" label="<%= true %>" cssClass="modify-link" />

					<aui:input name="deletePortrait" type="hidden" value="<%= deletePortrait %>" />
				</c:if>
			</div>
		</c:if>
	</div>

	<c:if test="<%= selUser != null %>">
		<liferay-ui:error exception="<%= DuplicateUserIdException.class %>" message="the-user-id-you-requested-is-already-taken" />
		<liferay-ui:error exception="<%= ReservedUserIdException.class %>" message="the-user-id-you-requested-is-reserved" />
		<liferay-ui:error exception="<%= UserIdException.class %>" message="please-enter-a-valid-user-id" />

		<aui:field-wrapper name="userId">
			<%= selUser.getUserId() %>

			<aui:input name="userId" type="hidden" value="<%= selUser.getUserId() %>" />
		</aui:field-wrapper>
	</c:if>

	<c:choose>
		<c:when test="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_BIRTHDAY) %>">
			<aui:input name="birthday" value="<%= birthday %>" />
		</c:when>
		<c:otherwise>
			<aui:input name="birthdayMonth" type="hidden" value="<%= Calendar.JANUARY %>" />
			<aui:input name="birthdayDay" type="hidden" value="1" />
			<aui:input name="birthdayYear" type="hidden" value="1970" />
		</c:otherwise>
	</c:choose>

	<c:if test="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_MALE) %>">
		<aui:select label="gender" name="male">
			<aui:option label="male" value="1" />
			<aui:option label="female" selected="<%= !male %>" value="0" />
		</aui:select>
	</c:if>

	<aui:input name="jobTitle" />
</aui:fieldset>

<aui:script>
	function <portlet:namespace />changePortrait(newPortraitURL) {
		AUI().one('#<portlet:namespace />avatar').attr('src', newPortraitURL);
		AUI().one('.avatar').attr('src', newPortraitURL);

		AUI().one('#<portlet:namespace />deletePortrait').val(false);
	}

	function <portlet:namespace />deletePortrait(defaultPortraitURL) {
		AUI().one('#<portlet:namespace />deletePortrait').val(true);

		AUI().one('#<portlet:namespace />avatar').attr('src', defaultPortraitURL);
		AUI().one('.avatar').attr('src', defaultPortraitURL);
	}

	function <portlet:namespace />openEditUserPortraitWindow(editUserPortraitURL) {
		var editUserPortraitWindow = window.open(editUserPortraitURL, '<liferay-ui:message key="change" />', 'directories=no,height=400,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=500');

		editUserPortraitWindow.focus();
	}
</aui:script>

<aui:script use="event,node">
	var modifyLinks = A.all('span.modify-link');

	if (modifyLinks) {
		modifyLinks.on(
			'click',
			function() {
				A.fire(
					'enterpriseAdmin:trackChanges',
					A.one('.selected .modify-link')
				);
			}
		);
	}
</aui:script>