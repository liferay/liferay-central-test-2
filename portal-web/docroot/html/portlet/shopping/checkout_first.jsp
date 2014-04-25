<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
--%>

<%@ include file="/html/portlet/shopping/init.jsp" %>

<%
ShoppingOrder order = (ShoppingOrder)request.getAttribute(WebKeys.SHOPPING_ORDER);

String billingState = BeanParamUtil.getString(order, request, "billingState");
String billingStateSel = ParamUtil.getString(request, "billingStateSel");

if (StateUtil.isStateId(billingState)) {
	billingStateSel = billingState;
	billingState = StringPool.BLANK;
}

String shippingState = BeanParamUtil.getString(order, request, "shippingState");
String shippingStateSel = ParamUtil.getString(request, "shippingStateSel");

if (StateUtil.isStateId(shippingState)) {
	shippingStateSel = shippingState;
	shippingState = StringPool.BLANK;
}

String ccType = ParamUtil.getString(request, "ccType");

Calendar cal = CalendarFactoryUtil.getCalendar();

int ccExpMonth = ParamUtil.getInteger(request, "ccExpMonth", cal.get(Calendar.MONTH));
int ccExpYear = ParamUtil.getInteger(request, "ccExpYear", cal.get(Calendar.YEAR));

if (request.getParameter("ccExpMonth") == null) {
	if (ccExpMonth == Calendar.DECEMBER) {
		ccExpMonth = Calendar.JANUARY;
		ccExpYear++;
	}
	else {
		ccExpMonth++;
	}
}

List addresses = AddressServiceUtil.getAddresses(Contact.class.getName(), contact.getContactId());
%>

<portlet:actionURL var="checkoutURL">
	<portlet:param name="struts_action" value="/shopping/checkout" />
</portlet:actionURL>

<aui:form action="<%= checkoutURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

	<liferay-util:include page="/html/portlet/shopping/tabs1.jsp">
		<liferay-util:param name="tabs1" value="cart" />
	</liferay-util:include>

	<aui:model-context bean="<%= order %>" model="<%= ShoppingOrder.class %>" />

	<liferay-ui:panel-container extended="<%= true %>" id="shoppingCheckoutPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="shoppingCheckoutBillingAddressPanel" persistState="<%= true %>" title="billing-address">
			<liferay-ui:error exception="<%= BillingCityException.class %>" message="please-enter-a-valid-city" />
			<liferay-ui:error exception="<%= BillingCountryException.class %>" message="please-enter-a-valid-country" />
			<liferay-ui:error exception="<%= BillingEmailAddressException.class %>" message="please-enter-a-valid-email-address" />
			<liferay-ui:error exception="<%= BillingFirstNameException.class %>" message="please-enter-a-valid-first-name" />
			<liferay-ui:error exception="<%= BillingLastNameException.class %>" message="please-enter-a-valid-last-name" />
			<liferay-ui:error exception="<%= BillingPhoneException.class %>" message="please-enter-a-valid-phone" />
			<liferay-ui:error exception="<%= BillingStateException.class %>" message="please-enter-a-valid-state" />
			<liferay-ui:error exception="<%= BillingStreetException.class %>" message="please-enter-a-valid-street" />
			<liferay-ui:error exception="<%= BillingZipException.class %>" message="please-enter-a-valid-postal-code" />

			<aui:fieldset column="<%= true %>">
				<c:if test="<%= !addresses.isEmpty() %>">

					<%
					String taglibUpdateBillingAddress = renderResponse.getNamespace() + "updateAddress(this[this.selectedIndex].value, 'billing');";
					%>

					<aui:select label="" name="addressBilling" onChange="<%= taglibUpdateBillingAddress %>" title="billing-address">
						<aui:option label='<%= "--" + LanguageUtil.get(pageContext,"my-addresses") + "--" %>' />

						<%
						for (int i = 0; addresses != null && i < addresses.size(); i++) {
							Address address = (Address)addresses.get(i);
						%>

							<aui:option label="<%= HtmlUtil.escape(address.getStreet1()) %>" value="<%= address.getAddressId() %>" />

						<%
						}
						%>

					</aui:select>
				</c:if>

				<%@ include file="/html/portlet/shopping/checkout_first_billing_address.jspf" %>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="shoppingCheckoutShippingAddressPanel" persistState="<%= true %>" title="shipping-address">
			<liferay-ui:error exception="<%= ShippingCityException.class %>" message="please-enter-a-valid-city" />
			<liferay-ui:error exception="<%= ShippingCountryException.class %>" message="please-enter-a-valid-country" />
			<liferay-ui:error exception="<%= ShippingEmailAddressException.class %>" message="please-enter-a-valid-email-address" />
			<liferay-ui:error exception="<%= ShippingFirstNameException.class %>" message="please-enter-a-valid-first-name" />
			<liferay-ui:error exception="<%= ShippingLastNameException.class %>" message="please-enter-a-valid-last-name" />
			<liferay-ui:error exception="<%= ShippingPhoneException.class %>" message="please-enter-a-valid-phone" />
			<liferay-ui:error exception="<%= ShippingStateException.class %>" message="please-enter-a-valid-state" />
			<liferay-ui:error exception="<%= ShippingStreetException.class %>" message="please-enter-a-valid-street" />
			<liferay-ui:error exception="<%= ShippingZipException.class %>" message="please-enter-a-valid-postal-code" />

			<aui:fieldset column="<%= true %>">
				<c:if test="<%= !addresses.isEmpty() %>">

					<%
					String taglibUpdateShippingAddress = renderResponse.getNamespace() + "updateAddress(this[this.selectedIndex].value, 'shipping');";
					%>

					<aui:select label="" name="addressShipping" onChange="<%= taglibUpdateShippingAddress %>" title="shipping-address">
						<aui:option label='<%= "--" + LanguageUtil.get(pageContext,"my-addresses") + "--" %>' />

						<%
						for (int i = 0; addresses != null && i < addresses.size(); i++) {
							Address address = (Address)addresses.get(i);
						%>

							<aui:option label="<%= HtmlUtil.escape(address.getStreet1()) %>" value="<%= address.getAddressId() %>" />

						<%
						}
						%>

					</aui:select>
				</c:if>

				<%@ include file="/html/portlet/shopping/checkout_first_shipping_address.jspf" %>
			</aui:fieldset>
		</liferay-ui:panel>

		<%
		String[] ccTypes = shoppingSettings.getCcTypes();
		%>

		<c:if test="<%= !shoppingSettings.usePayPal() && (ccTypes.length > 0) %>">
			<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="shoppingCheckoutCreditCardPanel" persistState="<%= true %>" title="credit-card">
				<liferay-ui:error exception="<%= CCExpirationException.class %>" message="please-enter-a-valid-credit-card-expiration-date" />
				<liferay-ui:error exception="<%= CCNameException.class %>" message="please-enter-the-full-name-exactly-as-it-is-appears-on-your-credit-card" />
				<liferay-ui:error exception="<%= CCNumberException.class %>" message="please-enter-a-valid-credit-card-number" />
				<liferay-ui:error exception="<%= CCTypeException.class %>" message="please-enter-a-valid-credit-card-type" />

				<aui:fieldset column="<%= true %>">

					<%
					for (int i = 0; i < ccTypes.length; i++) {
					%>

						<img alt="<%= HtmlUtil.escapeAttribute(ccTypes[i]) %>" src="<%= themeDisplay.getPathThemeImages() %>/shopping/cc_<%= HtmlUtil.escapeAttribute(ccTypes[i]) %>.png" />

					<%
					}
					%>

					<aui:input label="full-name" name="ccName" />

					<aui:select label="type" name="ccType" showEmptyOption="<%= true %>">

						<%
						for (int i = 0; i < ccTypes.length; i++) {
						%>

							<aui:option label='<%= "cc_" + HtmlUtil.escape(ccTypes[i]) %>' selected="<%= ccTypes[i].equals(ccType) %>" value="<%= HtmlUtil.escapeAttribute(ccTypes[i]) %>" />

						<%
						}
						%>

					</aui:select>

					<aui:input bean="<%= null %>" label="number" name="ccNumber" />

					<aui:field-wrapper label="expiration-date">
						<aui:col width="<%= 50 %>">
							<aui:select label="" name="ccExpMonth">

								<%
								String[] months = CalendarUtil.getMonths(locale);

								for (int i = 0; i < months.length; i++) {
								%>

									<aui:option label="<%= months[i] %>" selected="<%= (i == ccExpMonth) %>" value="<%= i %>" />

								<%
								}
								%>

							</aui:select>
						</aui:col>

						<aui:col width="<%= 50 %>">
							<aui:select label="" name="ccExpYear">

								<%
								int currentYear = cal.get(Calendar.YEAR);

								for (int i = currentYear; i <= currentYear + 5; i++) {
								%>

									<aui:option label="<%= i %>" selected="<%= i == ccExpYear %>" />

								<%
								}
								%>

							</aui:select>
						</aui:col>
					</aui:field-wrapper>

					<img alt="" src="<%= themeDisplay.getPathThemeImages() %>/shopping/cc_ver_number.png" />

					<aui:input bean="<%= null %>" label="verification-number" name="ccVerNumber" />
				</aui:fieldset>
			</liferay-ui:panel>
		</c:if>

		<c:if test="<%= PropsValues.SHOPPING_ORDER_COMMENTS_ENABLED %>">
			<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="shoppingCheckoutCommentsPanel" persistState="<%= true %>" title="comments">
				<aui:fieldset>
					<aui:input label="" name="comments" />
				</aui:fieldset>
			</liferay-ui:panel>
		</c:if>
	</liferay-ui:panel-container>

	<aui:button-row>
		<aui:button type="submit" value="continue" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />updateAddress(addressId, type) {

		<%
		for (int i = 0; addresses != null && i < addresses.size(); i++) {
			Address address = (Address)addresses.get(i);

			Region region = address.getRegion();
			Country country = address.getCountry();
		%>

			if ("<%= address.getAddressId() %>" == addressId) {
				document.getElementById("<portlet:namespace />" + type + "Street").value = "<%= HtmlUtil.escapeJS(address.getStreet1()) %>";
				document.getElementById("<portlet:namespace />" + type + "City").value = "<%= HtmlUtil.escapeJS(address.getCity()) %>";

				var stateSel = document.getElementById("<portlet:namespace />" + type + "StateSel");
				var stateSelValue = "<%= HtmlUtil.escapeJS(region.getRegionCode()) %>";

				for (var i = 0; i < stateSel.length; i++) {
					if (stateSel[i].value == stateSelValue) {
						stateSel.selectedIndex = i;

						break;
					}
				}

				document.getElementById("<portlet:namespace />" + type + "Zip").value = "<%= HtmlUtil.escapeJS(address.getZip()) %>";
				document.getElementById("<portlet:namespace />" + type + "Country").value = "<%= HtmlUtil.escapeJS(country.getName()) %>";
			}

		<%
		}
		%>

	}
</aui:script>