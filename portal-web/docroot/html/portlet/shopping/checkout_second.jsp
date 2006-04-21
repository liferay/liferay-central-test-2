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
ShoppingCart cart = ShoppingCartServiceUtil.getCart(session.getId(), company.getCompanyId());
Map items = cart.getItems();
ShoppingCoupon coupon = cart.getCoupon();

int altShipping = cart.getAltShipping();
String altShippingName = shoppingConfig.getAlternativeShippingName(altShipping);

ShoppingOrder order = (ShoppingOrder)request.getAttribute(WebKeys.SHOPPING_ORDER);
%>

<script type="text/javascript">
	function <portlet:namespace />continueCheckout() {
		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/checkout" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.SAVE %>">
<input name="<portlet:namespace />order_b_first_name" type="hidden" value="<%= order.getBillingFirstName() %>">
<input name="<portlet:namespace />order_b_last_name" type="hidden" value="<%= order.getBillingLastName() %>">
<input name="<portlet:namespace />order_b_email_address" type="hidden" value="<%= order.getBillingEmailAddress() %>">
<input name="<portlet:namespace />order_b_company" type="hidden" value="<%= order.getBillingCompany() %>">
<input name="<portlet:namespace />order_b_street" type="hidden" value="<%= order.getBillingStreet() %>">
<input name="<portlet:namespace />order_b_city" type="hidden" value="<%= order.getBillingCity() %>">
<input name="<portlet:namespace />order_b_state" type="hidden" value="<%= order.getBillingState() %>">
<input name="<portlet:namespace />order_b_zip" type="hidden" value="<%= order.getBillingZip() %>">
<input name="<portlet:namespace />order_b_country" type="hidden" value="<%= order.getBillingCountry() %>">
<input name="<portlet:namespace />order_b_phone" type="hidden" value="<%= order.getBillingPhone() %>">
<input name="<portlet:namespace />order_s_t_b" type="hidden" value="<%= order.isShipToBilling() %>">
<input name="<portlet:namespace />order_s_first_name" type="hidden" value="<%= order.getShippingFirstName() %>">
<input name="<portlet:namespace />order_s_last_name" type="hidden" value="<%= order.getShippingLastName() %>">
<input name="<portlet:namespace />order_s_email_address" type="hidden" value="<%= order.getShippingEmailAddress() %>">
<input name="<portlet:namespace />order_s_company" type="hidden" value="<%= order.getShippingCompany() %>">
<input name="<portlet:namespace />order_s_street" type="hidden" value="<%= order.getShippingStreet() %>">
<input name="<portlet:namespace />order_s_city" type="hidden" value="<%= order.getShippingCity() %>">
<input name="<portlet:namespace />order_s_state" type="hidden" value="<%= order.getShippingState() %>">
<input name="<portlet:namespace />order_s_zip" type="hidden" value="<%= order.getShippingZip() %>">
<input name="<portlet:namespace />order_s_country" type="hidden" value="<%= order.getShippingCountry() %>">
<input name="<portlet:namespace />order_s_phone" type="hidden" value="<%= order.getShippingPhone() %>">
<input name="<portlet:namespace />order_cc_name" type="hidden" value="<%= order.getCcName() %>">
<input name="<portlet:namespace />order_cc_type" type="hidden" value="<%= order.getCcType() %>">
<input name="<portlet:namespace />order_cc_number" type="hidden" value="<%= order.getCcNumber() %>">
<input name="<portlet:namespace />order_cc_exp_month" type="hidden" value="<%= order.getCcExpMonth() %>">
<input name="<portlet:namespace />order_cc_exp_year" type="hidden" value="<%= order.getCcExpYear() %>">
<input name="<portlet:namespace />order_cc_ver_number" type="hidden" value="<%= order.getCcVerNumber() %>">
<input name="<portlet:namespace />order_comments" type="hidden" value="<%= order.getComments() %>">

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "checkout") %>' />

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
		<td nowrap valign="top">
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "name") %>
			</b></font>
		</td>
		<td valign="top" width="48%">
			<font class="portlet-font" style="font-size: x-small;">
			<%= order.getBillingFirstName() %> <%= order.getBillingLastName() %>
			</font>
		</td>
		<td nowrap valign="top">
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "name") %>
			</b></font>
		</td>
		<td valign="top" width="48%">
			<font class="portlet-font" style="font-size: x-small;">
			<%= order.getShippingFirstName() %> <%= order.getShippingLastName() %>
			</font>
		</td>
	</tr>
	<tr>
		<td nowrap valign="top">
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "email-address") %>
			</b></font>
		</td>
		<td valign="top">
			<font class="portlet-font" style="font-size: x-small;">
			<%= order.getBillingEmailAddress() %>
			</font>
		</td>
		<td nowrap valign="top">
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "email-address") %>
			</b></font>
		</td>
		<td valign="top">
			<font class="portlet-font" style="font-size: x-small;">
			<%= order.getShippingEmailAddress() %>
			</font>
		</td>
	</tr>

	<c:if test="<%= Validator.isNotNull(order.getBillingCompany()) || Validator.isNotNull(order.getShippingCompany()) %>">
		<tr>
			<td nowrap valign="top">
				<font class="portlet-font" style="font-size: x-small;"><b>
				<%= LanguageUtil.get(pageContext, "company") %>
				</b></font>
			</td>
			<td valign="top">
				<font class="portlet-font" style="font-size: x-small;">
				<%= order.getBillingCompany() %>
				</font>
			</td>
			<td nowrap valign="top">
				<font class="portlet-font" style="font-size: x-small;"><b>
				<%= LanguageUtil.get(pageContext, "company") %>
				</b></font>
			</td>
			<td valign="top">
				<font class="portlet-font" style="font-size: x-small;">
				<%= order.getShippingCompany() %>
				</font>
			</td>
		</tr>
	</c:if>

	<tr>
		<td nowrap valign="top">
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "street") %>
			</b></font>
		</td>
		<td valign="top">
			<font class="portlet-font" style="font-size: x-small;">
			<%= order.getBillingStreet() %>
			</font>
		</td>
		<td nowrap valign="top">
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "street") %>
			</b></font>
		</td>
		<td valign="top">
			<font class="portlet-font" style="font-size: x-small;">
			<%= order.getShippingStreet() %>
			</font>
		</td>
	</tr>
	<tr>
		<td nowrap valign="top">
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "city") %>
			</b></font>
		</td>
		<td valign="top">
			<font class="portlet-font" style="font-size: x-small;">
			<%= order.getBillingCity() %>
			</font>
		</td>
		<td nowrap valign="top">
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "city") %>
			</b></font>
		</td>
		<td valign="top">
			<font class="portlet-font" style="font-size: x-small;">
			<%= order.getShippingCity() %>
			</font>
		</td>
	</tr>
	<tr>
		<td nowrap valign="top">
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "state") %>
			</b></font>
		</td>
		<td valign="top">
			<font class="portlet-font" style="font-size: x-small;">
			<%= order.getBillingState() %>
			</font>
		</td>
		<td nowrap valign="top">
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "state") %>
			</b></font>
		</td>
		<td valign="top">
			<font class="portlet-font" style="font-size: x-small;">
			<%= order.getShippingState() %>
			</font>
		</td>
	</tr>
	<tr>
		<td nowrap valign="top">
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "zip") %>
			</b></font>
		</td>
		<td valign="top">
			<font class="portlet-font" style="font-size: x-small;">
			<%= order.getBillingZip() %>
			</font>
		</td>
		<td nowrap valign="top">
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "zip") %>
			</b></font>
		</td>
		<td valign="top">
			<font class="portlet-font" style="font-size: x-small;">
			<%= order.getShippingZip() %>
			</font>
		</td>
	</tr>
	<tr>
		<td nowrap valign="top">
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "country") %>
			</b></font>
		</td>
		<td valign="top">
			<font class="portlet-font" style="font-size: x-small;">
			<%= order.getBillingCountry() %>
			</font>
		</td>
		<td nowrap valign="top">
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "country") %>
			</b></font>
		</td>
		<td valign="top">
			<font class="portlet-font" style="font-size: x-small;">
			<%= order.getShippingCountry() %>
			</font>
		</td>
	</tr>
	<tr>
		<td nowrap valign="top">
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "phone") %>
			</b></font>
		</td>
		<td valign="top">
			<font class="portlet-font" style="font-size: x-small;">
			<%= order.getBillingPhone() %>
			</font>
		</td>
		<td nowrap valign="top">
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "phone") %>
			</b></font>
		</td>
		<td valign="top">
			<font class="portlet-font" style="font-size: x-small;">
			<%= order.getShippingPhone() %>
			</font>
		</td>
	</tr>
	<tr>
		<td colspan="4">
			<br>
		</td>
	</tr>

	<c:if test="<%= !usePayPal %>">
		<tr class="beta">
			<td colspan="4">
				<font class="beta" size="2"><b>
				<%= LanguageUtil.get(pageContext, "credit-card") %>
				</b></font>
			</td>
		</tr>
		<tr>
			<td valign="top">
				<font class="portlet-font" style="font-size: x-small;"><b>
				<%= LanguageUtil.get(pageContext, "full-name") %>
				</b></font>
			</td>
			<td colspan="3" nowrap valign="top">
				<font class="portlet-font" style="font-size: x-small;">
				<%= order.getCcName() %>
				</font>
			</td>
		</tr>
		<tr>
			<td valign="top">
				<font class="portlet-font" style="font-size: x-small;"><b>
				<%= LanguageUtil.get(pageContext, "type") %>
				</b></font>
			</td>
			<td nowrap valign="top">
				<font class="portlet-font" style="font-size: x-small;">
				<%= LanguageUtil.get(pageContext, order.getCcType()) %>
				</font>
			</td>
		</tr>
		<tr>
			<td nowrap>
				<font class="portlet-font" style="font-size: x-small;"><b>
				<%= LanguageUtil.get(pageContext, "number") %>
				</b></font>
			</td>
			<td colspan="3" nowrap valign="top">
				<font class="portlet-font" style="font-size: x-small;">
				<%= CreditCard.hide(order.getCcNumber()) %>
				</font>
			</td>
		</tr>
		<tr>
			<td nowrap>
				<font class="portlet-font" style="font-size: x-small;"><b>
				<%= LanguageUtil.get(pageContext, "expiration-date") %>
				</b></font>
			</td>
			<td colspan="3" nowrap valign="top">
				<font class="portlet-font" style="font-size: x-small;">
				<%= CalendarUtil.getMonths(locale)[order.getCcExpMonth()] %>, <%= order.getCcExpYear() %>
				</font>
			</td>
		</tr>

		<c:if test="<%= Validator.isNotNull(order.getCcVerNumber()) %>">
			<tr>
				<td nowrap>
					<font class="portlet-font" style="font-size: x-small;"><b>
					<%= LanguageUtil.get(pageContext, "verification-number") %>
					</b></font>
				</td>
				<td colspan="3" nowrap valign="top">
					<font class="portlet-font" style="font-size: x-small;">
					<%= order.getCcVerNumber() %>
					</font>
				</td>
			</tr>
		</c:if>

		<tr>
			<td colspan="4">
				<br>
			</td>
		</tr>
	</c:if>

	</table>

	<%
	boolean showAvailability = GetterUtil.getBoolean(CompanyPropsUtil.get(company.getCompanyId(), PropsUtil.SHOPPING_ITEM_SHOW_AVAILABILITY));
	%>

	<table border="0" cellpadding="4" cellspacing="0" width="100%">
	<tr class="beta">
		<td>
			<font class="beta" size="2"><b>
			<%= LanguageUtil.get(pageContext, "item-description") %>
			</b></font>
		</td>

		<c:if test="<%= showAvailability %>">
			<td>
				<font class="beta" size="2"><b>
				<%= LanguageUtil.get(pageContext, "availability") %>
				</b></font>
			</td>
		</c:if>

		<td>
			<font class="beta" size="2"><b>
			<%= LanguageUtil.get(pageContext, "quantity") %>
			</b></font>
		</td>
		<td>
			<font class="beta" size="2"><b>
			<%= LanguageUtil.get(pageContext, "price") %>
			</b></font>
		</td>
		<td>
			<font class="beta" size="2"><b>
			<%= LanguageUtil.get(pageContext, "total") %>
			</b></font>
		</td>
	</tr>

	<%
	StringBuffer itemsSB = new StringBuffer();

	Iterator itr = items.entrySet().iterator();

	while (itr.hasNext()) {
		Map.Entry entry = (Map.Entry)itr.next();

		ShoppingCartItem cartItem = (ShoppingCartItem)entry.getKey();
		Integer count = (Integer)entry.getValue();

		ShoppingItem item = cartItem.getItem();
		String[] fieldsArray = cartItem.getFieldsArray();

		ShoppingItemField[] itemFields = (ShoppingItemField[])ShoppingItemFieldServiceUtil.getItemFields(item.getItemId()).toArray(new ShoppingItemField[0]);

		for (int i = 0; i < count.intValue(); i++) {
			itemsSB.append(item.getItemId()).append(",");
		}
	%>

		<tr>
			<td valign="top">
				<font class="portlet-font" style="font-size: x-small;">

				<b><a href="<portlet:renderURL><portlet:param name="struts_action" value="/shopping/view_item" /><portlet:param name="item_id" value="<%= item.getItemId() %>" /></portlet:renderURL>">
				<%= item.getName() %>
				</b></a>

				<c:if test="<%= item.isFields() %>">
					(<%= StringUtil.replace(StringUtil.merge(cartItem.getFieldsArray(), ", "), "=", ": ") %>)
				</c:if>

				</font>
			</td>

			<c:if test="<%= showAvailability %>">
				<td valign="top">
					<c:choose>
						<c:when test="<%= ShoppingUtil.isInStock(item, itemFields, fieldsArray) %>">
							<font class="portlet-msg-success" style="font-size: x-small;">
							<%= LanguageUtil.get(pageContext, "in-stock") %>
							</font><br>
						</c:when>
						<c:otherwise>
							<font class="portlet-msg-error" style="font-size: x-small;">
							<%= LanguageUtil.get(pageContext, "out-of-stock") %>
							</font><br>
						</c:otherwise>
					</c:choose>
				</td>
			</c:if>

			<td valign="top">
				<font class="portlet-font" style="font-size: x-small;">
				<%= count %>
				</font>
			</td>
			<td valign="top">
				<font class="portlet-font" style="font-size: x-small;">
				<%= currency.getSymbol() %><%= doubleFormat.format(ShoppingUtil.calculateActualPrice(item, count.intValue()) / count.intValue()) %>
				</font>
			</td>
			<td>
				<font class="portlet-font" style="font-size: x-small;">
				<%= currency.getSymbol() %><%= doubleFormat.format(ShoppingUtil.calculateActualPrice(item, count.intValue())) %>
				</font>
			</td>
		</tr>

	<%
	}
	%>

	<input name="<portlet:namespace />item_ids" type="hidden" value="<%= itemsSB.toString() %>">
	<input name="<portlet:namespace />coupon_ids" type="hidden" value="<%= cart.getCouponIds() %>">

	<tr>
		<td colspan="<%= showAvailability ? "3" : "2" %>"></td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "subtotal") %>
			</b></font>
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;">
			<%= currency.getSymbol() %><%= doubleFormat.format(ShoppingUtil.calculateActualSubtotal(items)) %>
			</font>
		</td>
	</tr>
	<tr>
		<td colspan="<%= showAvailability ? "3" : "2" %>"></td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "tax") %>
			</b></font>
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;">
			<%= currency.getSymbol() %><%= doubleFormat.format(ShoppingUtil.calculateTax(items, order.getBillingState())) %>
			</font>
		</td>
	</tr>
	<tr>
		<td colspan="<%= showAvailability ? "3" : "2" %>"></td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "shipping") %>
			</b></font>

			<font class="portlet-font" style="font-size: xx-small;">
			<%= Validator.isNotNull(altShippingName) ? "(" + altShippingName + ")" : StringPool.BLANK %>
			</font>
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;">
			<%= currency.getSymbol() %><%= doubleFormat.format(ShoppingUtil.calculateAlternativeShipping(items, altShipping)) %>
			</font>
		</td>
	</tr>

	<%
	double insurance = ShoppingUtil.calculateInsurance(items);
	%>

	<c:if test="<%= cart.isInsure() && (insurance > 0) %>">
		<td colspan="<%= showAvailability ? "3" : "2" %>"></td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "insurance") %>
			</b></font>
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;">
			<%= currency.getSymbol() %><%= doubleFormat.format(insurance) %>
			</font>
		</td>
	</c:if>

	<c:if test="<%= coupon != null %>">
		<tr>
			<td colspan="<%= showAvailability ? "3" : "2" %>"></td>
			<td>
				<font class="portlet-font" style="font-size: x-small;"><b>
				<a href="javascript: var viewCouponWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/shopping/view_coupon" /><portlet:param name="coupon_id" value="<%= coupon.getCouponId() %>" /></portlet:renderURL>', 'viewCoupon', 'directories=no,height=200,location=no,menubar=no,resizable=no,scrollbars=yes,status=no,toolbar=no,width=280'); void(''); viewCouponWindow.focus();"><u>
				<%= LanguageUtil.get(pageContext, "coupon-discount") %>
				</a></u>
				</b></font>
			</td>
			<td>
				<font class="portlet-msg-error" style="font-size: x-small;">
				<%= currency.getSymbol() %><%= doubleFormat.format(ShoppingUtil.calculateCouponDiscount(items, order.getBillingState(), coupon)) %>
				</font>
			</td>
		</tr>
	</c:if>

	<tr>
		<td colspan="<%= showAvailability ? "3" : "2" %>"></td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "total") %>
			</b></font>
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;">
			<%= currency.getSymbol() %><%= doubleFormat.format(ShoppingUtil.calculateTotal(items, order.getBillingState(), coupon, altShipping, cart.isInsure())) %>
			</font>
		</td>
	</tr>
	</table>

	<br>

	<input class="portlet-form-button" type="button" value='<%= usePayPal ? LanguageUtil.get(pageContext, "continue") : LanguageUtil.get(pageContext, "finished") %>' onClick="<portlet:namespace />continueCheckout();"><br>
</liferay-ui:box>

</form>