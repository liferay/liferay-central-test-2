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

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

ActionUtil.getGroup(request);

Group group = (Group)request.getAttribute(WebKeys.GROUP);

long groupId = BeanParamUtil.getLong(group, request, "groupId");

String friendlyURL = BeanParamUtil.getString(group, request, "friendlyURL");

ActionUtil.getMembershipRequest(request);

MembershipRequest membershipRequest = (MembershipRequest)request.getAttribute(WebKeys.MEMBERSHIP_REQUEST);
%>

<portlet:actionURL var="replyMembershipRequestURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
	<portlet:param name="struts_action" value="/communities/reply_membership_request" />
</portlet:actionURL>

<aui:form action="<%= replyMembershipRequestURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="membershipRequestId" type="hidden" value="<%= membershipRequest.getMembershipRequestId() %>" />

	<liferay-ui:tabs
		names="community"
		backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
	/>

	<liferay-ui:error exception="<%= DuplicateGroupException.class %>" message="please-enter-a-unique-name" />
	<liferay-ui:error exception="<%= GroupNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= MembershipRequestCommentsException.class %>" message="please-enter-valid-comments" />
	<liferay-ui:error exception="<%= RequiredGroupException.class %>" message="old-group-name-is-a-required-system-group" />

	<aui:model-context bean="<%= membershipRequest %>" model="<%= MembershipRequest.class %>" />

	<aui:fieldset>
		<aui:field-wrapper label="community">
			<%= group.getName() %>
		</aui:field-wrapper>

		<c:if test="<%= Validator.isNotNull(group.getDescription()) %>">
			<aui:field-wrapper label="description">
				<%= group.getDescription() %>
			</aui:field-wrapper>
		</c:if>

		<aui:field-wrapper label="user-name">
			<%= PortalUtil.getUserName(membershipRequest.getUserId(), StringPool.BLANK) %>
		</aui:field-wrapper>

		<aui:field-wrapper label="user-comments">
			<%= membershipRequest.getComments() %>
		</aui:field-wrapper>

		<aui:select label="status" name="statusId">
			<aui:option label="approve" value="<%= MembershipRequestConstants.STATUS_APPROVED %>" />
			<aui:option label="deny" value="<%= MembershipRequestConstants.STATUS_DENIED %>" />
		</aui:select>

		<aui:input name="replyComments" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<aui:script>
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />statusId);
	</aui:script>
</c:if>