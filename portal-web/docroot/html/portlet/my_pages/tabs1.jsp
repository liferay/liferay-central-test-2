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

<%@ include file="/html/portlet/my_account/init.jsp" %>

<%
long userId = ParamUtil.getLong(request, "userId");

User user2 = UserLocalServiceUtil.getUserById(userId);

boolean hasPowerUserRole = RoleLocalServiceUtil.hasUserRole(userId, company.getCompanyId(), RoleConstants.POWER_USER, true);

boolean privateLayoutsModifiable = PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_MODIFIABLE && (!PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_POWER_USER_REQUIRED || hasPowerUserRole);
boolean publicLayoutsModifiable = PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_MODIFIABLE && (!PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_POWER_USER_REQUIRED || hasPowerUserRole);
%>

<c:if test="<%= privateLayoutsModifiable || publicLayoutsModifiable %>">

	<%
	String backURL = ParamUtil.getString(request, "backURL");
	%>

	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="publicPagesURL">
		<portlet:param name="struts_action" value="/my_pages/edit_pages" />
		<portlet:param name="tabs1" value="public-pages" />
		<portlet:param name="privateLayout" value="<%= String.valueOf(false) %>" />
		<portlet:param name="backURL" value="<%= backURL %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(user2.getGroup().getGroupId()) %>" />
	</portlet:renderURL>

	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="privatePagesURL">
		<portlet:param name="struts_action" value="/my_pages/edit_pages" />
		<portlet:param name="tabs1" value="private-pages" />
		<portlet:param name="privateLayout" value="<%= String.valueOf(true) %>" />
		<portlet:param name="backURL" value="<%= backURL %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(user2.getGroup().getGroupId()) %>" />
	</portlet:renderURL>

	<c:choose>
		<c:when test="<%= privateLayoutsModifiable && publicLayoutsModifiable %>">
			<liferay-ui:tabs
				names="public-pages,private-pages"
				param="tabs1"
				url0="<%= publicPagesURL %>"
				url1="<%= privatePagesURL %>"
			/>
		</c:when>
		<c:when test="<%= publicLayoutsModifiable %>">
			<liferay-ui:tabs
				names="public-pages"
				param="tabs1"
				url0="<%= publicPagesURL %>"
			/>
		</c:when>
		<c:when test="<%= privateLayoutsModifiable %>">
			<liferay-ui:tabs
				names="private-pages"
				param="tabs1"
				url0="<%= privatePagesURL %>"
			/>
		</c:when>
	</c:choose>
</c:if>