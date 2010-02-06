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
String className = (String)request.getAttribute("addresses.className");
long classPK = (Long)request.getAttribute("addresses.classPK");

List<Address> addresses = Collections.EMPTY_LIST;

if (classPK > 0) {
	addresses = AddressServiceUtil.getAddresses(className, classPK);
}
%>

<c:if test="<%= !addresses.isEmpty() %>">
	<h3><liferay-ui:message key="address" /></h3>

	<ul class="property-list">

	<%
	for (Address address: addresses) {
		String street1 = address.getStreet1();
		String street2 = address.getStreet2();
		String street3 = address.getStreet3();

		String zipCode = address.getZip();
		String city = address.getCity();

		String mailingName = LanguageUtil.get(pageContext, address.getType().getName());
	%>

		<li class="<%= address.isPrimary() ? "primary" : "" %>">
			<em class="mailing-name"><%= mailingName %></em>

			<c:if test="<%= Validator.isNotNull(street1) %>">
				<%= street1 %><br />
			</c:if>

			<c:if test="<%= Validator.isNotNull(street2) %>">
				<%= street2 %><br />
			</c:if>

			<c:if test="<%= Validator.isNotNull(street3) %>">
				<%= street3 %><br />
			</c:if>

			<c:if test="<%= Validator.isNotNull(zipCode) %>">
				<%= zipCode %>,
			</c:if>

			<c:if test="<%= Validator.isNotNull(city) %>">
				<%= city %>
			</c:if>

			<c:if test="<%= address.isMailing() %>">(<liferay-ui:message key="mailing" />)</c:if>
		</li>

	<%
	}
	%>

	</ul>
</c:if>