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

<%@ include file="/html/portlet/directory/init.jsp" %>

<%
User selUser = (User)request.getAttribute("user.selUser");
Contact selContact = (Contact)request.getAttribute("user.selContact");
List<Organization> organizations = (List<Organization>)request.getAttribute("user.organizations");

String organizationsHTML = StringPool.BLANK;

if (!organizations.isEmpty()) {
	organizationsHTML = organizations.get(0).getName();
}

for (int i = 1; i<organizations.size(); i++) {
	organizationsHTML += ", "+ organizations.get(i).getName();
}
%>

<h2><%= selUser.getFullName() %></h2>

<div class="details">
	<img alt="<liferay-ui:message key="avatar" />" class="avatar" id="<portlet:namespace />avatar" src='<%= themeDisplay.getPathImage() %>/user_<%= selUser.isFemale() ? "female" : "male" %>_portrait?img_id=<%= selUser.getPortraitId() %>&t=<%= ImageServletTokenUtil.getToken(selUser.getPortraitId()) %>' />

	<dl class="property-list">
		<c:if test="<%= Validator.isNotNull(selUser.getDisplayEmailAddress()) %>">
			<dt>
				<liferay-ui:message key="email-address" />
			</dt>
			<dd>
				<%= selUser.getDisplayEmailAddress() %>
			</dd>
		</c:if>

		<c:if test="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_BIRTHDAY) %>">
			<dt>
				<liferay-ui:message key="birthday" />
			</dt>
			<dd>
				<%= dateFormatDate.format(selUser.getBirthday()) %>
			</dd>
		</c:if>

		<c:if test="<%= Validator.isNotNull(selContact.getJobTitle()) %>">
			<dt>
				<liferay-ui:message key="job-title" />
			</dt>
			<dd>
				<%= HtmlUtil.escape(selContact.getJobTitle()) %>
			</dd>
		</c:if>

		<c:if test="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_MALE) %>">
			<dt>
				<liferay-ui:message key="gender" />
			</dt>
			<dd>
				<%= LanguageUtil.get(pageContext, selUser.isMale() ? "male" : "female") %>
			</dd>
		</c:if>

		<c:if test="<%= !organizations.isEmpty() %>">
			<dt>
				<c:choose>
					<c:when test="<%= organizations.size() > 1 %>">
						<liferay-ui:message key="organizations" />
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="organization" />
					</c:otherwise>
				</c:choose>
			</dt>
			<dd>
				<%= HtmlUtil.escape(organizationsHTML) %>
			</dd>
		</c:if>
	</dl>
</div>