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
%>

<liferay-ui:error-marker key="user.errorSection" value="details" />

<h3><liferay-ui:message key="user-details" /></h3>

<fieldset class="block-labels col">

	<div class="ctrl-holder">
		<label for="title"><liferay-ui:message key="title" /></label>

		<select name="<portlet:namespace />prefixId" id="title">
			<option value=""></option>

			<%
			List<ListType> prefixes = ListTypeServiceUtil.getListTypes(ListTypeImpl.CONTACT_PREFIX);

			for (ListType prefix : prefixes) {
			%>
				<option <%= prefix.getListTypeId() == prefixId ? "selected" : "" %> value="<%= String.valueOf(prefix.getListTypeId()) %>"><%= LanguageUtil.get(pageContext, prefix.getName()) %></option>
			<%
			}
			%>
		</select>
	</div>

	<liferay-ui:error exception="<%= ReservedUserScreenNameException.class %>" message="the-screen-name-you-requested-is-reserved" />
	<liferay-ui:error exception="<%= UserScreenNameException.class %>" message="please-enter-a-valid-screen-name" />
	<liferay-ui:error exception="<%= DuplicateUserScreenNameException.class %>" message="the-screen-name-you-requested-is-already-taken" />

	<div class="ctrl-holder">
		<label for="<portlet:namespace />screenName"><liferay-ui:message key="screen-name" /></label>

		<liferay-ui:input-field model="<%= User.class %>" bean="<%= selUser %>" field="screenName" />
	</div>

	<liferay-ui:error exception="<%= DuplicateUserEmailAddressException.class %>" message="the-email-address-you-requested-is-already-taken" />
	<liferay-ui:error exception="<%= ReservedUserEmailAddressException.class %>" message="the-email-address-you-requested-is-reserved" />
	<liferay-ui:error exception="<%= UserEmailAddressException.class %>" message="please-enter-a-valid-email-address" />

	<div class="ctrl-holder">
		<label for="<portlet:namespace />emailAddress"><liferay-ui:message key="email-address" /></label>

		<liferay-ui:input-field model="<%= User.class %>" bean="<%= selUser %>" field="emailAddress" />
	</div>

	<liferay-ui:error exception="<%= ContactFirstNameException.class %>" message="please-enter-a-valid-first-name" />

	<div class="ctrl-holder">
		<label for="<portlet:namespace />firstName"><liferay-ui:message key="first-name" /></label>

		<liferay-ui:input-field model="<%= Contact.class %>" bean="<%= selContact %>" field="firstName" />
	</div>

	<div class="ctrl-holder">
		<label for="<portlet:namespace />middleName"><liferay-ui:message key="middle-name" /></label>

		<liferay-ui:input-field model="<%= Contact.class %>" bean="<%= selContact %>" field="middleName" />
	</div>

	<liferay-ui:error exception="<%= ContactLastNameException.class %>" message="please-enter-a-valid-last-name" />

	<div class="ctrl-holder">
		<label for="<portlet:namespace />lastName"><liferay-ui:message key="last-name" /></label>

		<liferay-ui:input-field model="<%= Contact.class %>" bean="<%= selContact %>" field="lastName" />
	</div>

	<div class="ctrl-holder">
		<label for="<portlet:namespace />suffixId"><liferay-ui:message key="suffix" /></label>

		<select name="<portlet:namespace />suffixId">
			<option value=""></option>

			<%
			List<ListType> suffixes = ListTypeServiceUtil.getListTypes(ListTypeImpl.CONTACT_SUFFIX);

			for (ListType suffix : suffixes) {
			%>
				<option <%= suffix.getListTypeId() == suffixId ? "selected" : "" %> value="<%= String.valueOf(suffix.getListTypeId()) %>"><%= LanguageUtil.get(pageContext, suffix.getName()) %></option>
			<%
			}
			%>
		</select>
	</div>
</fieldset>

<fieldset class="block-labels col">
	<div class="avatar">
		<c:if test="<%= selUser != null %>">
			<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="changeAvatarURL">
				<portlet:param name="struts_action" value="/enterprise_admin/edit_user_portrait" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="p_u_i_d" value="<%= String.valueOf(selUser.getUserId()) %>" />
				<portlet:param name="portrait_id" value="<%= String.valueOf(selUser.getPortraitId()) %>" />
			</portlet:renderURL>

			<a class="change-avatar"  href="javascript: ;" onclick="<portlet:namespace />showPopUp('<%= changeAvatarURL %>');return false;">
				<img alt="<liferay-ui:message key="avatar" />" class="avatar" id="<portlet:namespace />avatar" src='<%= themeDisplay.getPathImage() %>/user_<%= selUser.isFemale() ? "female" : "male" %>_portrait?img_id=<%= selUser.getPortraitId() %>&t=<%= ImageServletTokenUtil.getToken(selUser.getPortraitId()) %>' width="100" /><br />

				<liferay-ui:message key="change" />
			</a>
		</c:if>
	</div>

	<c:if test="<%= selUser != null %>">
		<liferay-ui:error exception="<%= DuplicateUserIdException.class %>" message="the-user-id-you-requested-is-already-taken" />
		<liferay-ui:error exception="<%= ReservedUserIdException.class %>" message="the-user-id-you-requested-is-reserved" />
		<liferay-ui:error exception="<%= UserIdException.class %>" message="please-enter-a-valid-user-id" />

		<div class="ctrl-holder">
			<label for="<portlet:namespace />userId"><liferay-ui:message key="user-id" /></label>
			<%= selUser.getUserId() %>

			<input name="< portlet:namespace />userId" type="hidden" value="<%= selUser.getUserId() %>" />
		</div>
	</c:if>

	<div class="ctrl-holder">
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
		<div class="ctrl-holder">
			<label for="<portlet:namespace />male"><liferay-ui:message key="gender" /></label>

			<select name="<portlet:namespace />male">
				<option value="1"><liferay-ui:message key="male" /></option>
				<option <%= (!male)? "selected" : "" %> value="0"><liferay-ui:message key="female" /></option>
			</select>
		</div>
	</c:if>

</fieldset>

<script type="text/javascript">
	function <portlet:namespace />showPopUp(url) {
		var changeAvatarWindow = window.open(url, '< liferay-ui:message key="change-avatar" />', 'directories=no,location=no,height=400,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=500');

		changeAvatarWindow.focus();
	}

	function <portlet:namespace />changeImage(){
		var old = jQuery('#<portlet:namespace />avatar').attr('src');
		jQuery('#<portlet:namespace />avatar').attr('src', old + 1);
		jQuery('.avatar').attr('src', old + 1);
	}
</script>