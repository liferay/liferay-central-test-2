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

<%@ include file="/html/portlet/directory/init.jsp" %>

<%
List<Organization> organizations = (List<Organization>)request.getAttribute("user.organizations");

String className = (String)request.getAttribute("phones.className");
long classPK = (Long)request.getAttribute("phones.classPK");

List<Phone> personalPhones = Collections.EMPTY_LIST;
List<Phone> organizationPhones = new ArrayList<Phone>();

if (classPK > 0) {
	personalPhones = PhoneServiceUtil.getPhones(className, classPK);
}

for (int i = 0; i < organizations.size(); i++) {
	organizationPhones.addAll(PhoneServiceUtil.getPhones(Organization.class.getName(), organizations.get(i).getOrganizationId()));
}
%>

<c:if test="<%= !personalPhones.isEmpty() || !organizationPhones.isEmpty() %>">
	<h3><liferay-ui:message key="phones" /></h3>

	<c:if test="<%= !organizationPhones.isEmpty() %>">
		<h4><liferay-ui:message key="organization-phones" /></h4>

		<ul class="property-list">

		<%
		for (Phone phone: organizationPhones) {
		%>

			<li class="<%= phone.isPrimary() ? "primary" : "" %>">
				<%= phone.getNumber() %> <%= phone.getExtension() %> <%= LanguageUtil.get(pageContext, phone.getType().getName()) %>
			</li>

		<%
		}
		%>

		</ul>
	</c:if>

	<c:if test="<%= !personalPhones.isEmpty() %>">
		<h4><liferay-ui:message key="personal-phones" /></h4>

		<ul class="property-list">

		<%
		for (Phone phone: personalPhones) {
		%>

			<li class="<%= phone.isPrimary() ? "primary" : "" %>">
				<%= phone.getNumber() %> <%= phone.getExtension() %> <%= LanguageUtil.get(pageContext, phone.getType().getName()) %>
			</li>

		<%
		}
		%>

		</ul>
	</c:if>
</c:if>