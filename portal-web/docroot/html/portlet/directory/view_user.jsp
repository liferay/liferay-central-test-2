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

<%@ include file="/html/portlet/directory/init.jsp" %>

<%
User selUser = PortalUtil.getSelectedUser(request);

Contact selContact =  selUser.getContact();

List<Organization> organizations = selUser.getOrganizations();

request.setAttribute("user.selUser", selUser);
request.setAttribute("user.selContact", selContact);
request.setAttribute("user.organizations", organizations);
request.setAttribute("addresses.className", Contact.class.getName());
request.setAttribute("addresses.classPK", selContact.getContactId());
request.setAttribute("emailAddresses.className", Contact.class.getName());
request.setAttribute("emailAddresses.classPK", selContact.getContactId());
request.setAttribute("phones.className", Contact.class.getName());
request.setAttribute("phones.classPK", selContact.getContactId());
request.setAttribute("websites.className", Contact.class.getName());
request.setAttribute("websites.classPK", selContact.getContactId());
%>

<liferay-util:include page="/html/portlet/directory/tabs1.jsp" />

<div class="user-information">
	<div class="section user-details">
		<liferay-util:include page="/html/portlet/directory/user/details.jsp" />
	</div>

	<div class="section user-addresses">
		<liferay-util:include page="/html/portlet/directory/user/addresses.jsp" />
	</div>

	<div class="section user-additional-email-addresses">
		<liferay-util:include page="/html/portlet/directory/common/additional_email_addresses.jsp" />
	</div>

	<div class="section user-websites">
		<liferay-util:include page="/html/portlet/directory/common/websites.jsp" />
	</div>

	<div class="section user-phones">
		<liferay-util:include page="/html/portlet/directory/user/phone_numbers.jsp" />
	</div>

	<div class="section user-instant-messenger">
		<liferay-util:include page="/html/portlet/directory/user/instant_messenger.jsp" />
	</div>

	<div class="section user-social-network">
		<liferay-util:include page="/html/portlet/directory/user/social_network.jsp" />
	</div>

	<div class="section user-sms">
		<liferay-util:include page="/html/portlet/directory/user/sms.jsp" />
	</div>

	<div class="section user-comments">
		<liferay-util:include page="/html/portlet/directory/user/comments.jsp" />
	</div>
</div>