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

<%@ include file="/html/portlet/communities/init.jsp" %>

<liferay-ui:tabs names="error" backURL="javascript: history.go(-1);" />

<liferay-ui:error exception="<%= NoSuchGroupException.class %>" message="the-community-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchLayoutException.class %>" message="the-page-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchProposalException.class %>" message="the-proposal-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchReviewException.class %>" message="the-review-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchRoleException.class %>" message="the-role-could-not-be-found" />
<liferay-ui:error exception="<%= PrincipalException.class %>" message="you-do-not-have-the-required-permissions" />

<liferay-ui:error exception="<%= RemoteExportException.class %>">
	<%
	RemoteExportException ree = (RemoteExportException)errorException;

	PKParser pkParser = new PKParser(ree.getMessage());

	String exception = pkParser.getString("exception");
	String subject = pkParser.getString("subject");
	%>
	<c:choose>
		<c:when test='<%= exception.equals("ConnectException") %>'>
			<%= LanguageUtil.format(pageContext, "could-not-connect-to-address-x,please-verify-that-the-specified-port-is-correct", "<tt>" + subject + "</tt>") %>
		</c:when>
		<c:when test='<%= exception.equals("NoLayoutsSelectedException") %>'>
			<%= LanguageUtil.get(pageContext, "no-layouts-are-selected-for-export") %>
		</c:when>
		<c:when test='<%= exception.equals("NoSuchGroupException") %>'>
			<%= LanguageUtil.format(pageContext, "remote-group-with-id-x-does-not-exist", subject) %>
		</c:when>
	</c:choose>
</liferay-ui:error>