<%
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

<%@ include file="/html/portlet/shopping/init.jsp" %>

<%
ShoppingOrder order = (ShoppingOrder)request.getAttribute(WebKeys.SHOPPING_ORDER);

String billingFirstName = request.getParameter("order_b_first_name");
if ((billingFirstName == null) || (billingFirstName.equals(StringPool.NULL))) {
	billingFirstName = GetterUtil.get(order.getShippingFirstName(), user.getFirstName());
}

String billingLastName = request.getParameter("order_b_last_name");
if ((billingLastName == null) || (billingLastName.equals(StringPool.NULL))) {
	billingLastName = GetterUtil.get(order.getShippingLastName(), user.getLastName());
}

String billingEmailAddress = request.getParameter("order_b_email_address");
if ((billingEmailAddress == null) || (billingEmailAddress.equals(StringPool.NULL))) {
	billingEmailAddress = GetterUtil.get(order.getShippingEmailAddress(), user.getEmailAddress());
}

String billingCompany = request.getParameter("order_b_company");
if ((billingCompany == null) || (billingCompany.equals(StringPool.NULL))) {
	billingCompany = GetterUtil.getString(order.getShippingCompany());
}

String billingStreet = request.getParameter("order_b_street");
if ((billingStreet == null) || (billingStreet.equals(StringPool.NULL))) {
	billingStreet = GetterUtil.getString(order.getShippingStreet());
}

String billingCity = request.getParameter("order_b_city");
if ((billingCity == null) || (billingCity.equals(StringPool.NULL))) {
	billingCity = GetterUtil.getString(order.getShippingCity());
}

String billingStateSel = request.getParameter("order_b_state_sel");
if ((billingStateSel == null) || (billingStateSel.equals(StringPool.NULL))) {
	billingStateSel = GetterUtil.getString(order.getShippingState());
}

String billingState = request.getParameter("order_b_state");
if ((billingState == null) || (billingState.equals(StringPool.NULL))) {
	billingState = GetterUtil.getString(order.getShippingState());
}

if (Validator.isNotNull(billingStateSel)) {
	billingState = "";
}

String billingZip = request.getParameter("order_b_zip");
if ((billingZip == null) || (billingZip.equals(StringPool.NULL))) {
	billingZip = GetterUtil.getString(order.getShippingZip());
}

String billingCountry = request.getParameter("order_b_country");
if ((billingCountry == null) || (billingCountry.equals(StringPool.NULL))) {
	billingCountry = GetterUtil.getString(order.getShippingCountry());
}

String billingPhone = request.getParameter("order_b_phone");
if ((billingPhone == null) || (billingPhone.equals(StringPool.NULL))) {
	billingPhone = GetterUtil.getString(order.getShippingPhone());
}

boolean shipToBilling = ParamUtil.get(request, "order_s_t_b", false);
String shipToBillingParam = request.getParameter("order_s_t_b");
if ((shipToBillingParam == null) || (shipToBillingParam.equals(StringPool.NULL))) {
	shipToBilling = order.isShipToBilling();
}

String shippingFirstName = request.getParameter("order_s_first_name");
if ((shippingFirstName == null) || (shippingFirstName.equals(StringPool.NULL))) {
	shippingFirstName = GetterUtil.get(order.getShippingFirstName(), user.getFirstName());
}

String shippingLastName = request.getParameter("order_s_last_name");
if ((shippingLastName == null) || (shippingLastName.equals(StringPool.NULL))) {
	shippingLastName = GetterUtil.get(order.getShippingLastName(), user.getLastName());
}

String shippingEmailAddress = request.getParameter("order_s_email_address");
if ((shippingEmailAddress == null) || (shippingEmailAddress.equals(StringPool.NULL))) {
	shippingEmailAddress = GetterUtil.get(order.getShippingEmailAddress(), user.getEmailAddress());
}

String shippingCompany = request.getParameter("order_s_company");
if ((shippingCompany == null) || (shippingCompany.equals(StringPool.NULL))) {
	shippingCompany = GetterUtil.getString(order.getShippingCompany());
}

String shippingStreet = request.getParameter("order_s_street");
if ((shippingStreet == null) || (shippingStreet.equals(StringPool.NULL))) {
	shippingStreet = GetterUtil.getString(order.getShippingStreet());
}

String shippingCity = request.getParameter("order_s_city");
if ((shippingCity == null) || (shippingCity.equals(StringPool.NULL))) {
	shippingCity = GetterUtil.getString(order.getShippingCity());
}

String shippingStateSel = request.getParameter("order_s_state_sel");
if ((shippingStateSel == null) || (shippingStateSel.equals(StringPool.NULL))) {
	shippingStateSel = GetterUtil.getString(order.getShippingState());
}

String shippingState = request.getParameter("order_s_state");
if ((shippingState == null) || (shippingState.equals(StringPool.NULL))) {
	shippingState = GetterUtil.getString(order.getShippingState());
}

if (Validator.isNotNull(shippingStateSel)) {
	shippingState = "";
}

String shippingZip = request.getParameter("order_s_zip");
if ((shippingZip == null) || (shippingZip.equals(StringPool.NULL))) {
	shippingZip = GetterUtil.getString(order.getShippingZip());
}

String shippingCountry = request.getParameter("order_s_country");
if ((shippingCountry == null) || (shippingCountry.equals(StringPool.NULL))) {
	shippingCountry = GetterUtil.getString(order.getShippingCountry());
}

String shippingPhone = request.getParameter("order_s_phone");
if ((shippingPhone == null) || (shippingPhone.equals(StringPool.NULL))) {
	shippingPhone = GetterUtil.getString(order.getShippingPhone());
}

String ccName = request.getParameter("order_cc_name");
if ((ccName == null) || (ccName.equals(StringPool.NULL))) {
	ccName = GetterUtil.get(order.getCcName(), user.getFullName());
}

String ccType = ParamUtil.getString(request, "order_cc_type");

String ccNumber = ParamUtil.getString(request, "order_cc_number");

Calendar cal = new GregorianCalendar();

int ccExpMonth = ParamUtil.get(request, "order_cc_exp_month", cal.get(Calendar.MONTH));
int ccExpYear = ParamUtil.get(request, "order_cc_exp_year", cal.get(Calendar.YEAR));

if (request.getParameter("order_cc_exp_month") == null) {
	if (ccExpMonth == Calendar.DECEMBER) {
		ccExpMonth = Calendar.JANUARY;
		ccExpYear++;
	}
	else {
		ccExpMonth++;
	}
}

String ccVerNumber = ParamUtil.getString(request, "order_cc_ver_number");

List addresses = null;
if (themeDisplay.isSignedIn()) {
	addresses = user.getAddresses();
}
%>

<script type="text/javascript">
	function <portlet:namespace />updateAddress(addressId, type) {

		<%
		for (int i = 0; addresses != null && i < addresses.size(); i++) {
			Address address = (Address)addresses.get(i);
		%>

			if ("<%= address.getAddressId() %>" == addressId) {
				document.getElementById("<portlet:namespace />order_" + type + "_first_name").value = "<%= user.getFirstName() %>";
				document.getElementById("<portlet:namespace />order_" + type + "_last_name").value = "<%= user.getLastName() %>";
				document.getElementById("<portlet:namespace />order_" + type + "_email_address").value = "<%= user.getEmailAddress() %>";
				document.getElementById("<portlet:namespace />order_" + type + "_street").value = "<%= GetterUtil.getString(address.getStreet1()) %>";
				document.getElementById("<portlet:namespace />order_" + type + "_city").value = "<%= GetterUtil.getString(address.getCity()) %>";
				//document.getElementById("<portlet:namespace />order_" + type + "_state").value = "<%= GetterUtil.getString(address.getState()) %>";

				var stateSel = document.getElementById("<portlet:namespace />order_" + type + "_state_sel");
				var stateSelValue = "<%= GetterUtil.getString(address.getState()) %>";

				for (var i = 0; i < stateSel.length; i++) {
					if (stateSel[i].value == stateSelValue) {
						stateSel.selectedIndex = i;

						break;
					}
				}

				document.getElementById("<portlet:namespace />order_" + type + "_zip").value = "<%= GetterUtil.getString(address.getZip()) %>";
				document.getElementById("<portlet:namespace />order_" + type + "_country").value = "<%= GetterUtil.getString(address.getCountry()) %>";
				document.getElementById("<portlet:namespace />order_" + type + "_phone").value = "<%= GetterUtil.getString(address.getPhone()) %>";
			}

		<%
		}
		%>

	}
</script>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/checkout" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>">
<input name="<portlet:namespace />order_s_t_b" type="hidden" value="<%= shipToBilling %>">

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "checkout") %>' />

	<c:if test="<%= !SessionErrors.isEmpty(renderRequest) %>">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td>
				<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "you-have-entered-invalid-data") %></font>
			</td>
		</tr>
		</table>

		<br>
	</c:if>

	<table border="0" cellpadding="4" cellspacing="0" width="100%">
	<tr class="beta">
		<td colspan="2">
			<font class="beta" size="2"><b>
			<%= LanguageUtil.get(pageContext, "billing-address") %>
			</b></font>
		</td>
		<td colspan="2">
			<font class="beta" size="2"><b>
			<%= LanguageUtil.get(pageContext, "shipping-address") %>
			</b></font>
		</td>
	</tr>
	<tr>
		<td colspan="2"></td>
		<td colspan="2">
			<font class="portlet-font" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "same-as-billing") %></font> <input <%= shipToBilling ? "checked" : "" %> tabindex="12" type="checkbox" onClick="if (this.checked) { document.<portlet:namespace />fm.<portlet:namespace />order_s_t_b.value = '1'; } else { document.<portlet:namespace />fm.<portlet:namespace />order_s_t_b.value = '0'; }">
		</td>
	</tr>

	<c:if test="<%= addresses != null && addresses.size() > 0 %>">
		<tr>
			<td colspan="2">
				<select onChange="<portlet:namespace />updateAddress(this[this.selectedIndex].value, 'b');">
					<option value="">-- <%= LanguageUtil.get(pageContext, "my-addresses") %> --</option>

					<%
					for (int i = 0; addresses != null && i < addresses.size(); i++) {
						Address address = (Address)addresses.get(i);
					%>

						<option value="<%= address.getAddressId() %>"><%= address.getDescription() %></option>

					<%
					}
					%>

				</select>
			</td>
			<td colspan="2">
				<select onChange="<portlet:namespace />updateAddress(this[this.selectedIndex].value, 's');">
					<option value="">-- <%= LanguageUtil.get(pageContext, "my-addresses") %> --</option>

					<%
					for (int i = 0; addresses != null && i < addresses.size(); i++) {
						Address address = (Address)addresses.get(i);
					%>

						<option value="<%= address.getAddressId() %>"><%= address.getDescription() %></option>

					<%
					}
					%>

				</select>
			</td>
		</tr>
	</c:if>

	<c:if test="<%= SessionErrors.contains(renderRequest, BillingFirstNameException.class.getName()) %>">
		<tr>
			<td colspan="2">
				<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-first-name") %></font>
			</td>
			<td colspan="2"></td>
		</tr>
	</c:if>

	<c:if test="<%= SessionErrors.contains(renderRequest, ShippingFirstNameException.class.getName()) %>">
		<tr>
			<td colspan="2"></td>
			<td colspan="2">
				<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-first-name") %></font>
			</td>
		</tr>
	</c:if>

	<tr>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "first-name") %>
			</b></font>
		</td>
		<td width="48%">
			<input class="form-text" id="<portlet:namespace />order_b_first_name" name="<portlet:namespace />order_b_first_name" tabindex="1" type="text" size="25" value="<%= billingFirstName %>">
		</td>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "first-name") %>
			</b></font>
		</td>
		<td width="48%">
			<input class="form-text" id="<portlet:namespace />order_s_first_name" name="<portlet:namespace />order_s_first_name" tabindex="13" type="text" size="25" value="<%= shippingFirstName %>">
		</td>
	</tr>

	<c:if test="<%= SessionErrors.contains(renderRequest, BillingLastNameException.class.getName()) %>">
		<tr>
			<td colspan="2">
				<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-last-name") %></font>
			</td>
			<td colspan="2"></td>
		</tr>
	</c:if>

	<c:if test="<%= SessionErrors.contains(renderRequest, ShippingLastNameException.class.getName()) %>">
		<tr>
			<td colspan="2"></td>
			<td colspan="2">
				<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-last-name") %></font>
			</td>
		</tr>
	</c:if>

	<tr>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "last-name") %>
			</b></font>
		</td>
		<td width="48%">
			<input class="form-text" id="<portlet:namespace />order_b_last_name" name="<portlet:namespace />order_b_last_name" tabindex="2" type="text" size="25" value="<%= billingLastName %>">
		</td>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "last-name") %>
			</b></font>
		</td>
		<td width="48%">
			<input class="form-text" id="<portlet:namespace />order_s_last_name" name="<portlet:namespace />order_s_last_name" tabindex="14" type="text" size="25" value="<%= shippingLastName %>">
		</td>
	</tr>

	<c:if test="<%= SessionErrors.contains(renderRequest, BillingEmailAddressException.class.getName()) %>">
		<tr>
			<td colspan="2">
				<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-email-address") %></font>
			</td>
			<td colspan="2"></td>
		</tr>
	</c:if>

	<c:if test="<%= SessionErrors.contains(renderRequest, ShippingEmailAddressException.class.getName()) %>">
		<tr>
			<td colspan="2"></td>
			<td colspan="2">
				<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-email-address") %></font>
			</td>
		</tr>
	</c:if>

	<tr>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "email-address") %>
			</b></font>
		</td>
		<td width="48%">
			<input class="form-text" id="<portlet:namespace />order_b_email_address" name="<portlet:namespace />order_b_email_address" tabindex="3" type="text" size="25" value="<%= billingEmailAddress %>">
		</td>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "email-address") %>
			</b></font>
		</td>
		<td width="48%">
			<input class="form-text" id="<portlet:namespace />order_s_email_address" name="<portlet:namespace />order_s_email_address" tabindex="15" type="text" size="25" value="<%= shippingEmailAddress %>">
		</td>
	</tr>
	<tr>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "company") %>
			</b></font>
		</td>
		<td width="48%">
			<input class="form-text" name="<portlet:namespace />order_b_company" tabindex="4" type="text" size="25" value="<%= billingCompany %>">
		</td>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "company") %>
			</b></font>
		</td>
		<td width="48%">
			<input class="form-text" name="<portlet:namespace />order_s_company" tabindex="16" type="text" size="25" value="<%= shippingCompany %>">
		</td>
	</tr>

	<c:if test="<%= SessionErrors.contains(renderRequest, BillingStreetException.class.getName()) %>">
		<tr>
			<td colspan="2">
				<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-street") %></font>
			</td>
			<td colspan="2"></td>
		</tr>
	</c:if>

	<c:if test="<%= SessionErrors.contains(renderRequest, ShippingStreetException.class.getName()) %>">
		<tr>
			<td colspan="2"></td>
			<td colspan="2">
				<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-street") %></font>
			</td>
		</tr>
	</c:if>

	<tr>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "street") %>
			</b></font>
		</td>
		<td width="48%">
			<input class="form-text" id="<portlet:namespace />order_b_street" name="<portlet:namespace />order_b_street" tabindex="5" type="text" size="25" value="<%= billingStreet %>">
		</td>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "street") %>
			</b></font>
		</td>
		<td width="48%">
			<input class="form-text" id="<portlet:namespace />order_s_street" name="<portlet:namespace />order_s_street" tabindex="17" type="text" size="25" value="<%= shippingStreet %>">
		</td>
	</tr>

	<c:if test="<%= SessionErrors.contains(renderRequest, BillingCityException.class.getName()) %>">
		<tr>
			<td colspan="2">
				<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-city") %></font>
			</td>
			<td colspan="2"></td>
		</tr>
	</c:if>

	<c:if test="<%= SessionErrors.contains(renderRequest, ShippingCityException.class.getName()) %>">
		<tr>
			<td colspan="2"></td>
			<td colspan="2">
				<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-city") %></font>
			</td>
		</tr>
	</c:if>

	<tr>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "city") %>
			</b></font>
		</td>
		<td width="48%">
			<input class="form-text" id="<portlet:namespace />order_b_city" name="<portlet:namespace />order_b_city" tabindex="6" type="text" size="25" value="<%= billingCity %>">
		</td>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "city") %>
			</b></font>
		</td>
		<td width="48%">
			<input class="form-text" id="<portlet:namespace />order_s_city" name="<portlet:namespace />order_s_city" tabindex="18" type="text" size="25" value="<%= shippingCity %>">
		</td>
	</tr>

	<c:if test="<%= SessionErrors.contains(renderRequest, BillingStateException.class.getName()) %>">
		<tr>
			<td colspan="2">
				<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-state") %></font>
			</td>
			<td colspan="2"></td>
		</tr>
	</c:if>

	<c:if test="<%= SessionErrors.contains(renderRequest, ShippingStateException.class.getName()) %>">
		<tr>
			<td colspan="2"></td>
			<td colspan="2">
				<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-state") %></font>
			</td>
		</tr>
	</c:if>

	<tr>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "state") %>
			</b></font>
		</td>
		<td width="48%">
			<select id="<portlet:namespace />order_b_state_sel" name="<portlet:namespace />order_b_state_sel" tabindex="7">
				<option value="">Outside US</option>

				<%
				for (int i = 0; i < StateUtil.STATES.length; i++) {
				%>

					<option <%= billingStateSel.equals(StateUtil.STATES[i].getId()) ? "selected" : "" %> value="<%= StateUtil.STATES[i].getId() %>"><%= StateUtil.STATES[i].getName() %></option>

				<%
				}
				%>

			</select>
		</td>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "state") %>
			</b></font>
		</td>
		<td width="48%">
			<select id="<portlet:namespace />order_s_state_sel" name="<portlet:namespace />order_s_state_sel" tabindex="19">
				<option value="">Outside US</option>

				<%
				for (int i = 0; i < StateUtil.STATES.length; i++) {
				%>

					<option <%= shippingStateSel.equals(StateUtil.STATES[i].getId()) ? "selected" : "" %> value="<%= StateUtil.STATES[i].getId() %>"><%= StateUtil.STATES[i].getName() %></option>

				<%
				}
				%>

			</select>
		</td>
	</tr>
	<tr>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "other-state") %>
			</b></font>
		</td>
		<td width="48%">
			<input class="form-text" name="<portlet:namespace />order_b_state" tabindex="8" type="text" size="25" value="<%= billingState %>">
		</td>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "other-state") %>
			</b></font>
		</td>
		<td width="48%">
			<input class="form-text" name="<portlet:namespace />order_s_state" tabindex="20" type="text" size="25" value="<%= shippingState %>">
		</td>
	</tr>

	<c:if test="<%= SessionErrors.contains(renderRequest, BillingZipException.class.getName()) %>">
		<tr>
			<td colspan="2">
				<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-zip") %></font>
			</td>
			<td colspan="2"></td>
		</tr>
	</c:if>

	<c:if test="<%= SessionErrors.contains(renderRequest, ShippingZipException.class.getName()) %>">
		<tr>
			<td colspan="2"></td>
			<td colspan="2">
				<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-zip") %></font>
			</td>
		</tr>
	</c:if>

	<tr>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "zip") %>
			</b></font>
		</td>
		<td width="48%">
			<input class="form-text" id="<portlet:namespace />order_b_zip" name="<portlet:namespace />order_b_zip" tabindex="9" type="text" size="25" value="<%= billingZip %>">
		</td>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "zip") %>
			</b></font>
		</td>
		<td width="48%">
			<input class="form-text" id="<portlet:namespace />order_s_zip" name="<portlet:namespace />order_s_zip" tabindex="21" type="text" size="25" value="<%= shippingZip %>">
		</td>
	</tr>

	<c:if test="<%= SessionErrors.contains(renderRequest, BillingCountryException.class.getName()) %>">
		<tr>
			<td colspan="2">
				<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-country") %></font>
			</td>
			<td colspan="2"></td>
		</tr>
	</c:if>

	<c:if test="<%= SessionErrors.contains(renderRequest, ShippingCountryException.class.getName()) %>">
		<tr>
			<td colspan="2"></td>
			<td colspan="2">
				<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-country") %></font>
			</td>
		</tr>
	</c:if>

	<tr>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "country") %>
			</b></font>
		</td>
		<td width="48%">
			<input class="form-text" id="<portlet:namespace />order_b_country" name="<portlet:namespace />order_b_country" tabindex="10" type="text" size="25" value="<%= billingCountry %>">
		</td>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "country") %>
			</b></font>
		</td>
		<td width="48%">
			<input class="form-text" id="<portlet:namespace />order_s_country" name="<portlet:namespace />order_s_country" tabindex="22" type="text" size="25" value="<%= shippingCountry %>">
		</td>
	</tr>

	<c:if test="<%= SessionErrors.contains(renderRequest, BillingPhoneException.class.getName()) %>">
		<tr>
			<td colspan="2">
				<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-phone") %></font>
			</td>
			<td colspan="2"></td>
		</tr>
	</c:if>

	<c:if test="<%= SessionErrors.contains(renderRequest, ShippingPhoneException.class.getName()) %>">
		<tr>
			<td colspan="2"></td>
			<td colspan="2">
				<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-phone") %></font>
			</td>
		</tr>
	</c:if>

	<tr>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "phone") %>
			</b></font>
		</td>
		<td width="48%">
			<input class="form-text" id="<portlet:namespace />order_b_phone" name="<portlet:namespace />order_b_phone" tabindex="11" type="text" size="25" value="<%= billingPhone %>">
		</td>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "phone") %>
			</b></font>
		</td>
		<td width="48%">
			<input class="form-text" id="<portlet:namespace />order_s_phone" name="<portlet:namespace />order_s_phone" tabindex="23" type="text" size="25" value="<%= shippingPhone %>">
		</td>
	</tr>
	<tr>
		<td colspan="4">
			<br>
		</td>
	</tr>

	<%
	String[] ccTypes = shoppingConfig.getCcTypes();
	%>

	<c:if test="<%= !usePayPal && (ccTypes.length > 0) %>">
		<tr class="beta">
			<td colspan="4">
				<font class="beta" size="2"><b>
				<%= LanguageUtil.get(pageContext, "credit-card") %>
				</b></font>
			</td>
		</tr>

		<c:if test="<%= SessionErrors.contains(renderRequest, CCNameException.class.getName()) %>">
			<tr>
				<td colspan="4">
					<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-the-full-name-exactly-as-it-is-appears-on-your-credit-card") %></font>
				</td>
			</tr>
		</c:if>

		<tr>
			<td nowrap>
				<font class="portlet-font" style="font-size: x-small;"><b>
				<%= LanguageUtil.get(pageContext, "full-name") %>
				</b></font>
			</td>
			<td>
				<input class="form-text" name="<portlet:namespace />order_cc_name" tabindex="24" type="text" size="25" value="<%= ccName %>">
			</td>
			<td colspan="2" rowspan="2" valign="bottom">

				<%
				for (int i = 0; i < ccTypes.length; i++) {
				%>

					<img align="absbottom" border="0" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/shopping/<%= ccTypes[i] %>.gif" vspace="0">

				<%
				}
				%>

			</td>
		</tr>

		<c:if test="<%= SessionErrors.contains(renderRequest, CCTypeException.class.getName()) %>">
			<tr>
				<td colspan="4">
					<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-credit-card-type") %></font>
				</td>
			</tr>
		</c:if>

		<tr>
			<td nowrap>
				<font class="portlet-font" style="font-size: x-small;"><b>
				<%= LanguageUtil.get(pageContext, "type") %>
				</b></font>
			</td>
			<td>
				<select name="<portlet:namespace />order_cc_type" tabindex="25">
					<option value=""></option>

					<%
					for (int i = 0; i < ccTypes.length; i++) {
					%>

						<option <%= ccTypes[i].equals(ccType) ? "selected" : "" %> value="<%= ccTypes[i] %>"><%= LanguageUtil.get(pageContext, ccTypes[i]) %></option>

					<%
					}
					%>

				</select>
			</td>
		</tr>

		<c:if test="<%= SessionErrors.contains(renderRequest, CCNumberException.class.getName()) %>">
			<tr>
				<td colspan="4">
					<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-credit-card-number") %></font>
				</td>
			</tr>
		</c:if>

		<tr>
			<td nowrap>
				<font class="portlet-font" style="font-size: x-small;"><b>
				<%= LanguageUtil.get(pageContext, "number") %>
				</b></font>
			</td>
			<td colspan="3">
				<input class="form-text" name="<portlet:namespace />order_cc_number" tabindex="26" type="text" size="25" value="<%= ccNumber %>">
			</td>
		</tr>

		<c:if test="<%= SessionErrors.contains(renderRequest, CCExpirationException.class.getName()) %>">
			<tr>
				<td colspan="4">
					<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-credit-card-expiration-date") %></font>
				</td>
			</tr>
		</c:if>

		<tr>
			<td nowrap>
				<font class="portlet-font" style="font-size: x-small;"><b>
				<%= LanguageUtil.get(pageContext, "expiration-date") %>
				</b></font>
			</td>
			<td>
				<select name="<portlet:namespace />order_cc_exp_month" tabindex="27">

					<%
					String[] months = CalendarUtil.getMonths(locale);

					for (int i = 0; i < months.length; i++) {
					%>

						<option <%= (i == ccExpMonth) ? "selected" : "" %> value="<%= i %>"><%= months[i] %></option>

					<%
					}
					%>

				</select>

				<select name="<portlet:namespace />order_cc_exp_year" tabindex="28">

					<%
					int currentYear = cal.get(Calendar.YEAR);

					for (int i = currentYear; i <= currentYear + 5; i++) {
					%>

						<option <%= (i == ccExpYear) ? "selected" : "" %> value="<%= i %>"><%= i %></option>

					<%
					}
					%>

				</select>
			</td>
			<td colspan="2" rowspan="2" valign="bottom">
				<img align="absbottom" border="0" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/shopping/cc_ver_number.gif" vspace="0">
			</td>
		</tr>
		<tr>
			<td nowrap>
				<font class="portlet-font" style="font-size: x-small;"><b>
				<%= LanguageUtil.get(pageContext, "verification-number") %>
				</b></font>
			</td>
			<td>
				<input class="form-text" name="<portlet:namespace />order_cc_ver_number" tabindex="29" type="text" size="3" value="<%= ccVerNumber %>">
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<br>
			</td>
		</tr>
	</c:if>

	<tr class="beta">
		<td colspan="4">
			<font class="beta" size="2"><b>
			<%= LanguageUtil.get(pageContext, "comments") %>
			</b></font>
		</td>
	</tr>
	<tr>
		<td colspan="4">
			<textarea class="form-text" name="<portlet:namespace />order_comments" cols="80" rows="5" tabindex="30"></textarea>
		</td>
	</tr>
	<tr>
		<td colspan="4">
			<br>
		</td>
	</tr>
	</table>

	<input class="portlet-form-button" tabindex="30" type="button" value="<bean:message key="continue" />" onClick="submitForm(document.<portlet:namespace />fm);"><br>
</liferay-ui:box>

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />order_b_first_name.focus();
</script>