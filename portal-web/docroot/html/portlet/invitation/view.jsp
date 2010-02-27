<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
%>

<%@ include file="/html/portlet/invitation/init.jsp" %>

<c:choose>
	<c:when test="<%= windowState.equals(WindowState.NORMAL) %>">
		<liferay-ui:success key="invitationSent" message="your-invitations-have-been-sent" />

		<a href="<portlet:renderURL><portlet:param name="struts_action" value="/invitation/view" /></portlet:renderURL>">
			<liferay-ui:message key="invite-friends" />
		</a>
	</c:when>
	<c:otherwise>
		<form action="<portlet:actionURL><portlet:param name="struts_action" value="/invitation/view" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
		<input name="redirect" type="hidden" value="<portlet:renderURL windowState="<%= WindowState.NORMAL.toString() %>" />" />

		<%= LanguageUtil.format(pageContext, "enter-up-to-x-email-addresses-of-friends-you-would-like-to-invite", String.valueOf(InvitationUtil.getEmailMessageMaxRecipients())) %>

		<br /><br />

		<%
		Set invalidEmailAddresses = (Set)SessionErrors.get(renderRequest, "emailAddresses");

		int emailMessageMaxRecipients = InvitationUtil.getEmailMessageMaxRecipients();

		for (int i = 0; i < emailMessageMaxRecipients; i++) {
			String emailAddress = ParamUtil.getString(request, "emailAddress" + i);
		%>

			<c:if test='<%= (invalidEmailAddresses != null) && invalidEmailAddresses.contains("emailAddress" + i) %>'>
				<div class="portlet-msg-error">
				<liferay-ui:message key="please-enter-a-valid-email-address" />
				</div>
			</c:if>

			<input class="lfr-input-text" name="<portlet:namespace />emailAddress<%= i %>" type="text" value="<%= emailAddress %>" />

			<br />

		<%
		}
		%>

		<br />

		<input type="button" value="<liferay-ui:message key="invite-friends" />" onClick="submitForm(document.<portlet:namespace />fm);" />

		</form>
	</c:otherwise>
</c:choose>