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

<%@ include file="/html/portal/init.jsp" %>

<%
String key = ParamUtil.getString(request, "key");

MembershipInvitation mi = MembershipInvitationLocalServiceUtil.getMembershipInvitation(key);

Group invitedGroup = GroupLocalServiceUtil.getGroup(mi.getGroupId());
%>

<script type="text/javascript">
jQuery(
	function() {
		jQuery("#<portlet:namespace />acceptButton").click(
			function() {
				jQuery("#<portlet:namespace />accept").val("true");
				submitForm(document.<portlet:namespace />fm1);
			}
		);
		jQuery("#<portlet:namespace />declineButton").click(
			function() {
				jQuery("#<portlet:namespace />accept").val("false");
				submitForm(document.<portlet:namespace />fm1);
			}
		);
	}
);
</script>

<div align="center">
	<form action="<portlet:actionURL windowState="<%= WindowState.NORMAL.toString() %>"><portlet:param name="struts_action" value="/login/confirm_membership_invitation" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm1">

	<liferay-ui:error exception="<%= MembershipInvitationAlreadyUsedException.class %>" message="this-membership-invitation-has-already-been-used-please-request-a-new-invitation" />
	<liferay-ui:error exception="<%= MembershipInvitationUserNotInvitedException.class %>" message="access-denied" />
	<liferay-ui:error exception="<%= NoSuchMembershipInvitationException.class %>" message="this-membership-invitation-is-invalid" />

	<input id="<portlet:namespace />accept" name="<portlet:namespace />accept" type="hidden">
	<input name="<portlet:namespace />key" type="hidden" value="<%= HtmlUtil.escape(key) %>">

	<liferay-ui:message key="would-you-like-to-join-the-community" /> <%= invitedGroup.getName() %>? <br />

	<input id="<portlet:namespace />acceptButton" name="<portlet:namespace />acceptButton" type="Button" value="<liferay-ui:message key="accept" />" />
	<input id="<portlet:namespace />declineButton" name="<portlet:namespace />declineButton" type="Button" value="<liferay-ui:message key="decline" />" />
	</form>
</div>