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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
User selUser = (User)request.getAttribute("user.selUser");

List<AnnouncementsDelivery> deliveries = null;

if (selUser != null) {
	deliveries = AnnouncementsDeliveryLocalServiceUtil.getUserDeliveries(selUser.getUserId());
}
else {
	deliveries = new ArrayList<AnnouncementsDelivery>(AnnouncementsEntryConstants.TYPES.length);

	for (String type : AnnouncementsEntryConstants.TYPES) {
		AnnouncementsDelivery delivery = new AnnouncementsDeliveryImpl();

		delivery.setType(type);
		delivery.setWebsite(true);

		deliveries.add(delivery);
	}
}
%>

<h3><liferay-ui:message key="alerts-and-announcements" /></h3>

<liferay-ui:message key="select-the-delivery-options-for-alerts-and-announcements" />

<br /><br />

<liferay-ui:search-container>
	<liferay-ui:search-container-results
		results="<%= deliveries %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portlet.announcements.model.AnnouncementsDelivery"
		escapedModel="<%= true %>"
		keyProperty="deliveryId"
		modelVar="delivery"
	>
		<liferay-ui:search-container-column-text
			name="type"
			value="<%= LanguageUtil.get(pageContext, delivery.getType()) %>"
		/>
		<liferay-ui:search-container-column-jsp
			name="email"
			path="/html/portlet/enterprise_admin/user/announcements_checkbox.jsp"
		/>
		<liferay-ui:search-container-column-jsp
			name="sms"
			path="/html/portlet/enterprise_admin/user/announcements_checkbox.jsp"
		/>
		<liferay-ui:search-container-column-jsp
			name="website"
			path="/html/portlet/enterprise_admin/user/announcements_checkbox.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>