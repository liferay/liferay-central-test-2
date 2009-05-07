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
Contact selContact = (Contact)request.getAttribute("user.selContact");

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

<script type="text/javascript">
	function <portlet:namespace />changePortrait(newPortraitURL) {
		jQuery('#<portlet:namespace />avatar').attr('src', newPortraitURL);
		jQuery('.avatar').attr('src', newPortraitURL);

		jQuery('#<portlet:namespace />deletePortrait').val(false);
	}

	function <portlet:namespace />deletePortrait(defaultPortraitURL) {
		jQuery('#<portlet:namespace />deletePortrait').val(true);

		jQuery('#<portlet:namespace />avatar').attr('src', defaultPortraitURL);
		jQuery('.avatar').attr('src', defaultPortraitURL);
	}

	function <portlet:namespace />openEditUserPortraitWindow(editUserPortraitURL) {
		var editUserPortraitWindow = window.open(editUserPortraitURL, '<liferay-ui:message key="change" />', 'directories=no,height=400,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=500');

		editUserPortraitWindow.focus();
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

<liferay-ui:error-marker key="errorSection" value="details" />

<h3><liferay-ui:message key="details" /></h3>

<fieldset class="exp-block-labels exp-form-column">
	<div class="exp-ctrl-holder">
		<label for="title"><liferay-ui:message key="title" /></label>

		<select name="<portlet:namespace />prefixId">
			<option value=""></option>

			<%
			List<ListType> prefixes = ListTypeServiceUtil.getListTypes(ListTypeImpl.CONTACT_PREFIX);

			for (ListType prefix : prefixes) {
			%>

				<option <%= (prefix.getListTypeId() == prefixId) ? "selected" : "" %> value="<%= prefix.getListTypeId() %>"><liferay-ui:message key="<%= prefix.getName() %>" /></option>

			<%
			}
			%>

		</select>
	</div>

	<liferay-ui:error exception="<%= DuplicateUserScreenNameException.class %>" message="the-screen-name-you-requested-is-already-taken" />
	<liferay-ui:error exception="<%= ReservedUserScreenNameException.class %>" message="the-screen-name-you-requested-is-reserved" />
	<liferay-ui:error exception="<%= UserScreenNameException.class %>" message="please-enter-a-valid-screen-name" />

	<c:if test="<%= !PropsValues.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE || (selUser != null) %>">
		<div class="exp-ctrl-holder">
			<label for="<portlet:namespace />screenName"><liferay-ui:message key="screen-name" /></label>

			<c:choose>
				<c:when test="<%= PropsValues.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE %>">
					<%= selUser.getScreenName() %>

					<input name="<portlet:namespace />screenName" type="hidden" value="<%= selUser.getScreenName() %>" />
				</c:when>
				<c:otherwise>
					<liferay-ui:input-field model="<%= User.class %>" bean="<%= selUser %>" field="screenName" />
				</c:otherwise>
			</c:choose>
		</div>
	</c:if>

	<liferay-ui:error exception="<%= DuplicateUserEmailAddressException.class %>" message="the-email-address-you-requested-is-already-taken" />
	<liferay-ui:error exception="<%= ReservedUserEmailAddressException.class %>" message="the-email-address-you-requested-is-reserved" />
	<liferay-ui:error exception="<%= UserEmailAddressException.class %>" message="please-enter-a-valid-email-address" />

	<div class="exp-ctrl-holder">
		<label for="<portlet:namespace />emailAddress"><liferay-ui:message key="email-address" /></label>

		<liferay-ui:input-field model="<%= User.class %>" bean="<%= selUser %>" field="emailAddress" />
	</div>

	<liferay-ui:error exception="<%= ContactFirstNameException.class %>" message="please-enter-a-valid-first-name" />

	<div class="exp-ctrl-holder">
		<label for="<portlet:namespace />firstName"><liferay-ui:message key="first-name" /></label>

		<liferay-ui:input-field model="<%= Contact.class %>" bean="<%= selContact %>" field="firstName" />
	</div>

	<div class="exp-ctrl-holder">
		<label for="<portlet:namespace />middleName"><liferay-ui:message key="middle-name" /></label>

		<liferay-ui:input-field model="<%= Contact.class %>" bean="<%= selContact %>" field="middleName" />
	</div>

	<liferay-ui:error exception="<%= ContactLastNameException.class %>" message="please-enter-a-valid-last-name" />

	<div class="exp-ctrl-holder">
		<label for="<portlet:namespace />lastName"><liferay-ui:message key="last-name" /></label>

		<liferay-ui:input-field model="<%= Contact.class %>" bean="<%= selContact %>" field="lastName" />
	</div>

	<div class="exp-ctrl-holder">
		<label for="<portlet:namespace />suffixId"><liferay-ui:message key="suffix" /></label>

		<select name="<portlet:namespace />suffixId">
			<option value=""></option>

			<%
			List<ListType> suffixes = ListTypeServiceUtil.getListTypes(ListTypeImpl.CONTACT_SUFFIX);

			for (ListType suffix : suffixes) {
			%>

				<option <%= (suffix.getListTypeId() == suffixId) ? "selected" : "" %> value="<%= suffix.getListTypeId() %>"><liferay-ui:message key="<%= suffix.getName() %>" /></option>
			<%
			}
			%>

		</select>
	</div>
</fieldset>

<fieldset class="exp-block-labels exp-form-column">
	<div>
		<c:if test="<%= selUser != null %>">
			<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="editUserPortraitURL">
				<portlet:param name="struts_action" value="/enterprise_admin/edit_user_portrait" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="p_u_i_d" value="<%= String.valueOf(selUser.getUserId()) %>" />
				<portlet:param name="portrait_id" value="<%= String.valueOf(selUser.getPortraitId()) %>" />
			</portlet:renderURL>

			<a class="change-avatar" href="javascript: <portlet:namespace />openEditUserPortraitWindow('<%= editUserPortraitURL %>');"><img alt="<liferay-ui:message key="avatar" />" class="avatar" id="<portlet:namespace />avatar" src='<%= themeDisplay.getPathImage() %>/user_<%= selUser.isFemale() ? "female" : "male" %>_portrait?img_id=<%= deletePortrait ? 0 : selUser.getPortraitId() %>&t=<%= ImageServletTokenUtil.getToken(selUser.getPortraitId()) %>' /></a>

			<div class="portrait-icons">

				<%
				String taglibEditURL = "javascript: " + renderResponse.getNamespace() + "openEditUserPortraitWindow('" + editUserPortraitURL +"');";
				%>

				<liferay-ui:icon image="edit" message="change" url="<%= taglibEditURL %>" label="<%= true %>" />

				<c:if test="<%= selUser.getPortraitId() > 0 %>">

					<%
					String taglibDeleteURL = "javascript: " + renderResponse.getNamespace() + "deletePortrait('" + themeDisplay.getPathImage() + "/user_" + (selUser.isFemale() ? "female" : "male") + "_portrait?img_id=0');";
					%>

					<liferay-ui:icon image="delete" url="<%= taglibDeleteURL %>" label="<%= true %>" cssClass="modify-link" />

					<input id="<portlet:namespace />deletePortrait" name="<portlet:namespace />deletePortrait" type="hidden" value="<%= deletePortrait %>" />
				</c:if>
			</div>
		</c:if>
	</div>

	<c:if test="<%= selUser != null %>">
		<liferay-ui:error exception="<%= DuplicateUserIdException.class %>" message="the-user-id-you-requested-is-already-taken" />
		<liferay-ui:error exception="<%= ReservedUserIdException.class %>" message="the-user-id-you-requested-is-reserved" />
		<liferay-ui:error exception="<%= UserIdException.class %>" message="please-enter-a-valid-user-id" />

		<div class="exp-ctrl-holder">
			<label for="<portlet:namespace />userId"><liferay-ui:message key="user-id" /></label>

			<%= selUser.getUserId() %>

			<input name="<portlet:namespace />userId" type="hidden" value="<%= selUser.getUserId() %>" />
		</div>
	</c:if>

	<div class="exp-ctrl-holder">
		<c:choose>
			<c:when test="<%= PropsValues.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_BIRTHDAY %>">
				<label for="<portlet:namespace />birthday"><liferay-ui:message key="birthday" /></label>

				<liferay-ui:input-field model="<%= Contact.class %>" bean="<%= selContact %>" field="birthday" defaultValue="<%= birthday %>" />
			</c:when>
			<c:otherwise>
				<input name="<portlet:namespace />birthdayMonth" type="hidden" value="<%= Calendar.JANUARY %>" />
				<input name="<portlet:namespace />birthdayDay" type="hidden" value="1" />
				<input name="<portlet:namespace />birthdayYear" type="hidden" value="1970" />
			</c:otherwise>
		</c:choose>
	</div>

	<c:if test="<%= PropsValues.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_MALE %>">
		<div class="exp-ctrl-holder">
			<label for="<portlet:namespace />male"><liferay-ui:message key="gender" /></label>

			<select name="<portlet:namespace />male">
				<option value="1"><liferay-ui:message key="male" /></option>
				<option <%= !male? "selected" : "" %> value="0"><liferay-ui:message key="female" /></option>
			</select>
		</div>
	</c:if>

	<div class="exp-ctrl-holder">
		<label for="<portlet:namespace />jobTitle"><liferay-ui:message key="job-title" /></label>

		<liferay-ui:input-field model="<%= Contact.class %>" bean="<%= selContact %>" field="jobTitle" />
	</div>
</fieldset>