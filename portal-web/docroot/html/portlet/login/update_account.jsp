<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/html/portlet/login/init.jsp" %>

<%
int birthdayDay = ParamUtil.getInteger(request, "birthdayDay");
int birthdayMonth = ParamUtil.getInteger(request, "birthdayMonth");
int birthdayYear = ParamUtil.getInteger(request, "birthdayYear");
String emailAddress = ParamUtil.getString(request, "emailAddress");
long facebookId = ParamUtil.getLong(request, "facebookId");
String firstName = ParamUtil.getString(request, "firstName");
String jobTitle = ParamUtil.getString(request, "jobTitle");
String lastName = ParamUtil.getString(request, "lastName");
boolean male = ParamUtil.get(request, "male", true);
String middleName = ParamUtil.getString(request, "middleName");
String openId = ParamUtil.getString(request, "openId");
int prefixId = ParamUtil.getInteger(request, "prefixId");
String screenName = ParamUtil.getString(request, "screenName");
int suffixId = ParamUtil.getInteger(request, "suffixId");
%>

<div class="anonymous-account">
	<portlet:actionURL var="createAccountURL">
		<portlet:param name="struts_action" value="/login/create_account" />
		<portlet:param name="birthdayDay" value="<%= String.valueOf(birthdayDay) %>" />
		<portlet:param name="birthdayMonth" value="<%= String.valueOf(birthdayMonth) %>" />
		<portlet:param name="birthdayYear" value="<%= String.valueOf(birthdayYear) %>" />
		<portlet:param name="emailAddress" value="<%= emailAddress %>" />
		<portlet:param name="facebookId" value="<%= String.valueOf(facebookId) %>" />
		<portlet:param name="firstName" value="<%= firstName %>" />
		<portlet:param name="jobTitle" value="<%= jobTitle %>" />
		<portlet:param name="lastName" value="<%= lastName %>" />
		<portlet:param name="male" value="<%= String.valueOf(male) %>" />
		<portlet:param name="middleName" value="<%= middleName %>" />
		<portlet:param name="openId" value="<%= openId %>" />
		<portlet:param name="prefixId" value="<%= String.valueOf(prefixId) %>" />
		<portlet:param name="screenName" value="<%= screenName %>" />
		<portlet:param name="suffixId" value="<%= String.valueOf(suffixId) %>" />
	</portlet:actionURL>

	<aui:form action="<%= createAccountURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
	</aui:form>

	<div class="portlet-msg-alert">
		<liferay-ui:message key="this-email-address-has-already-been-used-within-the-portal-do-you-want-to-associate-that-activity-to-your-account" />
	</div>

	<aui:button name="updateUser" value="associate-account" onClick='<%= renderResponse.getNamespace() + "updateUser()" %>'/>
	<aui:button name="resetUser" value="create-new-account" onClick='<%= renderResponse.getNamespace() + "resetUser()" %>'/>
</div>

<aui:script>
	function <portlet:namespace />updateUser() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />resetUser() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.RESET %>";
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>