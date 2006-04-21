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
List orderItems = ShoppingOrderItemServiceUtil.getOrderItems(order.getOrderId());

DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, locale);
df.setTimeZone(timeZone);

String scroll = ParamUtil.getString(request, "scroll");
%>

<c:if test='<%= scroll.equals("note_content") %>'>
	<script language="JavaScript" event="onLoad()" for="window">
		document.<portlet:namespace />fm.<portlet:namespace />note_content.scrollIntoView();
	</script>
</c:if>

<form method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />addNote(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />redirect" type="hidden" value="">
<input name="<portlet:namespace />scroll" type="hidden" value="">
<input name="<portlet:namespace />order_id" type="hidden" value="<%= order.getOrderId() %>">

<c:if test="<%= shoppingAdmin %>">
	<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
		<liferay-util:param name="box_br_wrap_content" value="false" />

		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "update") %>' onClick="if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-update-this-order") %>')) { document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.UPDATE %>'; document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_order" /><portlet:param name="order_id" value="<%= order.getOrderId() %>" /></portlet:actionURL>'; submitForm(document.<portlet:namespace />fm); }">

				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "delete") %>' onClick="if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-this-order") %>')) { document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.DELETE %>'; document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:renderURL><portlet:param name="struts_action" value="/shopping/view_orders" /></portlet:renderURL>'; submitForm(document.<portlet:namespace />fm); }">

				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="self.location = '<portlet:renderURL><portlet:param name="struts_action" value="/shopping/view_orders" /></portlet:renderURL>';">
			</td>
			<td width="30">
				&nbsp;
			</td>
			<td>
				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "invoice") %>' onClick="window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/shopping/edit_order_invoice" /><portlet:param name="order_id" value="<%= order.getOrderId() %>" /></portlet:renderURL>');">

				<c:if test="<%= shoppingConfig.getOrderEmail().isSend() %>">
					<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, (order.isSendOrderEmail() ? "" : "re") + "send-order-email") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'send_order_email'; document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_order" /><portlet:param name="order_id" value="<%= order.getOrderId() %>" /></portlet:actionURL>'; submitForm(document.<portlet:namespace />fm);">
				</c:if>

				<c:if test="<%= shoppingConfig.getShippingEmail().isSend() %>">
					<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, (order.isSendShippingEmail() ? "" : "re") + "send-shipping-email") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'send_shipping_email'; document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_order" /><portlet:param name="order_id" value="<%= order.getOrderId() %>" /></portlet:actionURL>'; submitForm(document.<portlet:namespace />fm);">
				</c:if>
			</td>
		</tr>
		</table>
	</liferay-ui:box>

	<br>
</c:if>

<c:if test='<%= SessionMessages.contains(renderRequest, "order_updated") %>'>
	<table border="0" cellpadding="0" cellspacing="0" width="95%">
	<tr>
		<td>
			<font class="portlet-msg-success" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "you-have-successfully-updated-this-order") %></font>
		</td>
	</tr>
	</table>

	<br>
</c:if>

<c:if test='<%= SessionMessages.contains(renderRequest, "order_email_sent") %>'>
	<table border="0" cellpadding="0" cellspacing="0" width="95%">
	<tr>
		<td>
			<font class="portlet-msg-success" style="font-size: xx-small;"><%= LanguageUtil.format(pageContext, "you-have-sent-an-email-to-x-about-this-order", "<b>" + order.getBillingEmailAddress() + "</b>", false) %></font>
		</td>
	</tr>
	</table>

	<br>
</c:if>

<c:if test='<%= SessionMessages.contains(renderRequest, "shipping_email_sent") %>'>
	<table border="0" cellpadding="0" cellspacing="0" width="95%">
	<tr>
		<td>
			<font class="portlet-msg-success" style="font-size: xx-small;"><%= LanguageUtil.format(pageContext, "you-have-sent-an-email-to-x-about-shipping-this-order", "<b>" + order.getShippingEmailAddress() + "</b>", false) %></font>
		</td>
	</tr>
	</table>

	<br>
</c:if>

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "order") + " #" + order.getOrderId() %>' />

	<table border="0" cellpadding="4" cellspacing="0" width="100%">
	<tr class="beta">
		<td colspan="2">
			<font class="beta" size="2"><b>
			<%= LanguageUtil.get(pageContext, "general") %>
			</b></font>
		</td>

		<c:if test="<%= usePayPal %>">
			<td colspan="2">
				<font class="beta" size="2"><b>
				PayPal
				</b></font>
			</td>
		</c:if>

		<c:if test="<%= !usePayPal %>">
			<td colspan="2">
				<font class="beta" size="2"><b>
				<%= LanguageUtil.get(pageContext, "credit-card") %>
				</b></font>
			</td>
		</c:if>
	</tr>
	<tr>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "order") %> #
			</b></font>
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;">
			<%= order.getOrderId() %>
			</font>
		</td>

		<c:if test="<%= usePayPal %>">
			<td nowrap>
				<font class="portlet-font" style="font-size: x-small;"><b>
				<%= LanguageUtil.get(pageContext, "status") %>
				</b></font>
			</td>
			<td>
				<select name="<portlet:namespace />order_pp_payment_status">

				<%
				for (int i = 0; i < ShoppingOrder.STATUSES.length; i++) {
				%>

					<option <%= ShoppingUtil.getPpPaymentStatus(ShoppingOrder.STATUSES[i]).equals(order.getPpPaymentStatus()) ? "selected" : "" %> value="<%= ShoppingOrder.STATUSES[i] %>"><%= LanguageUtil.get(pageContext, ShoppingOrder.STATUSES[i]) %></option>

				<%
				}
				%>

				</select>
			</td>
		</c:if>

		<c:if test="<%= !usePayPal %>">
			<td nowrap>
				<font class="portlet-font" style="font-size: x-small;"><b>
				<%= LanguageUtil.get(pageContext, "full-name") %>
				</b></font>
			</td>
			<td>
				<font class="portlet-font" style="font-size: x-small;">
				<%= order.getCcName() %>
				</font>
			</td>
		</c:if>
	</tr>
	<tr>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "order-date") %>
			</b></font>
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;">
			<%= df.format(order.getCreateDate()) %>
			</font>
		</td>

		<c:if test="<%= usePayPal %>">
			<td nowrap>
				<font class="portlet-font" style="font-size: x-small;"><b>
				<%= LanguageUtil.get(pageContext, "transaction-id") %>
				</b></font>
			</td>
			<td>
				<input class="form-text" name="<portlet:namespace />order_pp_txn_id" type="text" value="<%= order.getPpTxnId() %>">
			</td>
		</c:if>

		<c:if test="<%= !usePayPal %>">
			<td nowrap>
				<font class="portlet-font" style="font-size: x-small;"><b>
				<%= LanguageUtil.get(pageContext, "type") %>
				</b></font>
			</td>
			<td>
				<font class="portlet-font" style="font-size: x-small;">
				<%= LanguageUtil.get(pageContext, order.getCcType()) %>
				</font>
			</td>
		</c:if>
	</tr>
	<tr>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "last-modified") %>
			</b></font>
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;">
			<%= df.format(order.getModifiedDate()) %>
			</font>
		</td>

		<c:if test="<%= usePayPal %>">
			<td nowrap>
				<font class="portlet-font" style="font-size: x-small;"><b>
				<%= LanguageUtil.get(pageContext, "payment-gross") %>
				</b></font>
			</td>
			<td>
				<input class="form-text" name="<portlet:namespace />order_pp_payment_gross" type="text" value="<%= currency.getSymbol() %><%= doubleFormat.format(order.getPpPaymentGross()) %>">
			</td>
		</c:if>

		<c:if test="<%= !usePayPal %>">
			<td nowrap>
				<font class="portlet-font" style="font-size: x-small;"><b>
				<%= LanguageUtil.get(pageContext, "number") %>
				</b></font>
			</td>
			<td>
				<font class="portlet-font" style="font-size: x-small;">
				<%= CreditCard.hide(order.getCcNumber()) %>
				</font>
			</td>
		</c:if>
	</tr>
	<tr>
		<td colspan="2"></td>

		<c:if test="<%= usePayPal %>">
			<td nowrap>
				<font class="portlet-font" style="font-size: x-small;"><b>
				<%= LanguageUtil.get(pageContext, "receiver-email-address") %>
				</b></font>
			</td>
			<td>
				<input class="form-text" name="<portlet:namespace />order_pp_receiver_email" type="text" value="<%= GetterUtil.get(order.getPpReceiverEmail(), shoppingConfig.getPayPalEmailAddress()) %>">
			</td>
		</c:if>

		<c:if test="<%= !usePayPal %>">
			<td nowrap>
				<font class="portlet-font" style="font-size: x-small;"><b>
				<%= LanguageUtil.get(pageContext, "expiration-date") %>
				</b></font>
			</td>
			<td>
				<font class="portlet-font" style="font-size: x-small;">
				<%= CalendarUtil.getMonths(locale)[order.getCcExpMonth()] %>, <%= order.getCcExpYear() %>
				</font>
			</td>
		</c:if>
	</tr>

	<c:if test="<%= usePayPal %>">
		<tr>
			<td colspan="2"></td>
			<td nowrap>
				<font class="portlet-font" style="font-size: x-small;"><b>
				<%= LanguageUtil.get(pageContext, "payer-email-address") %>
				</b></font>
			</td>
			<td>
				<input class="form-text" name="<portlet:namespace />order_pp_payer_email" type="text" value="<%= order.getPpPayerEmail() %>">
			</td>
		</tr>

		<c:if test="<%= order.getPpPaymentStatus().equals(ShoppingOrder.STATUS_CHECKOUT) %>">
			<tr>
				<td colspan="2"></td>
				<td nowrap>
					<font class="portlet-font" style="font-size: x-small;"><b>
					<%= LanguageUtil.get(pageContext, "paypal-order") %>
					</b></font>
				</td>
				<td>
					<font class="portlet-font" style="font-size: x-small;">

					<%
					String payPalLinkOpen = "<b><a class=\"gamma\" href=\"" + ShoppingUtil.getPayPalRedirectURL(shoppingConfig, order, ShoppingUtil.calculateTotal(order), ShoppingUtil.getPayPalReturnURL(renderResponse.createActionURL(), order), ShoppingUtil.getPayPalNotifyURL(company, themeDisplay.getPathMain())) + "\"><u>";
					String payPalLinkClose = "</u></a></b>";
					%>

					<%= LanguageUtil.format(pageContext, "please-complete-your-order", new Object[] {payPalLinkOpen, payPalLinkClose}, false) %><br>

					</font>
				</td>
			</tr>
		</c:if>
	</c:if>

	<c:if test="<%= !usePayPal && Validator.isNotNull(order.getCcVerNumber()) %>">
		<tr>
			<td colspan="2"></td>
			<td nowrap>
				<font class="portlet-font" style="font-size: x-small;"><b>
				<%= LanguageUtil.get(pageContext, "verification-number") %>
				</b></font>
			</td>
			<td>
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

	<c:if test="<%= Validator.isNotNull(order.getComments()) %>">
		<tr class="beta">
			<td colspan="4">
				<font class="beta" size="2"><b>
				<%= LanguageUtil.get(pageContext, "comments") %>
				</b></font>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<font class="portlet-font" style="font-size: x-small;">
				<%= order.getComments() %>
				</font>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<br>
			</td>
		</tr>
	</c:if>

	</table>

	<table border="0" cellpadding="4" cellspacing="0" width="100%">
	<tr class="beta">
		<td>
			<font class="beta" size="2"><b>
			<%= LanguageUtil.get(pageContext, "item-description") %>
			</b></font>
		</td>
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
	Iterator itr = orderItems.iterator();

	while (itr.hasNext()) {
		ShoppingOrderItem orderItem = (ShoppingOrderItem)itr.next();

		ShoppingItem item = null;
		String itemId = null;
		try {
			item = ShoppingItemServiceUtil.getItemById(ShoppingUtil.getItemId(orderItem.getItemId()));
			itemId = item.getItemId();
		}
		catch (Exception e) {
		}

		String fieldsArray[] = StringUtil.split(ShoppingUtil.getItemFields(orderItem.getItemId()), "&");

		int quantity = orderItem.getQuantity();
	%>

		<tr>
			<td valign="top">
				<font class="portlet-font" style="font-size: x-small;">

				<b>

				<c:if test="<%= item != null %>">
					<a href="<portlet:renderURL><portlet:param name="struts_action" value="/shopping/view_item" /><portlet:param name="item_id" value="<%= itemId %>" /></portlet:renderURL>">
					<%= orderItem.getName() %>
					</a>
				</c:if>

				<c:if test="<%= item == null %>">
					<%= orderItem.getName() %>
				</c:if>

				</b>

				<c:if test="<%= fieldsArray.length > 0 %>">
					(<%= StringUtil.replace(StringUtil.merge(fieldsArray, ", "), "=", ": ") %>)
				</c:if>

				</font>
			</td>
			<td valign="top">
				<font class="portlet-font" style="font-size: x-small;">
				<%= quantity %>
				</font>
			</td>
			<td valign="top">
				<font class="portlet-font" style="font-size: x-small;">
				<%= currency.getSymbol() %><%= doubleFormat.format(orderItem.getPrice()) %>
				</font>
			</td>
			<td>
				<font class="portlet-font" style="font-size: x-small;">
				<%= currency.getSymbol() %><%= doubleFormat.format(orderItem.getPrice() * quantity) %>
				</font>
			</td>
		</tr>

	<%
	}
	%>

	<tr>
		<td colspan="2"></td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "subtotal") %>
			</b></font>
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;">
			<%= currency.getSymbol() %><%= doubleFormat.format(ShoppingUtil.calculateActualSubtotal(orderItems)) %>
			</font>
		</td>
	</tr>
	<tr>
		<td colspan="2"></td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "tax") %>
			</b></font>
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;">
			<%= currency.getSymbol() %><%= doubleFormat.format(order.getTax()) %>
			</font>
		</td>
	</tr>
	<tr>
		<td colspan="2"></td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "shipping") %>
			</b></font>

			<font class="portlet-font" style="font-size: xx-small;">
			<%= Validator.isNotNull(order.getAltShipping()) ? "(" + order.getAltShipping() + ")" : StringPool.BLANK %>
			</font>
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;">
			<%= currency.getSymbol() %><%= doubleFormat.format(order.getShipping()) %>
			</font>
		</td>
	</tr>

	<c:if test="<%= Validator.isNotNull(order.getCouponIds()) %>">
		<tr>
			<td colspan="2"></td>
			<td>
				<font class="portlet-font" style="font-size: x-small;"><b>
				<a href="javascript: var viewCouponWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/shopping/view_coupon" /><portlet:param name="coupon_id" value="<%= order.getCouponIds() %>" /></portlet:renderURL>', 'viewCoupon', 'directories=no,height=200,location=no,menubar=no,resizable=no,scrollbars=yes,status=no,toolbar=no,width=280'); void(''); viewCouponWindow.focus();"><u>
				<%= LanguageUtil.get(pageContext, "coupon-discount") %>
				</a></u>
				</b></font>
			</td>
			<td>
				<font class="portlet-msg-error" style="font-size: x-small;">
				<%= currency.getSymbol() %><%= doubleFormat.format(order.getCouponDiscount()) %>
				</font>
			</td>
		</tr>
	</c:if>

	<tr>
		<td colspan="2"></td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "total") %>
			</b></font>
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;">
			<%= currency.getSymbol() %><%= doubleFormat.format(ShoppingUtil.calculateTotal(order)) %>
			</font>
		</td>
	</tr>
	</table>
</liferay-ui:box>

<br>

<c:if test="<%= shoppingAdmin %>">
	<script type="text/javascript">
		function <portlet:namespace />addNote() {
			if (trimString(document.<portlet:namespace />fm.<portlet:namespace />note_content.value) == "") {
				alert("<%= UnicodeLanguageUtil.get(pageContext, "please-enter-a-valid-note") %>");
			}
			else {
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "add_note";
				document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_order" /><portlet:param name="scroll" value="note_content" /><portlet:param name="order_id" value="<%= order.getOrderId() %>" /></portlet:actionURL>";
				submitForm(document.<portlet:namespace />fm);
			}
		}

		function <portlet:namespace />deleteNote(noteId) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "delete_note";
			document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_order" /><portlet:param name="scroll" value="note_content" /><portlet:param name="order_id" value="<%= order.getOrderId() %>" /></portlet:actionURL>";
			document.<portlet:namespace />fm.<portlet:namespace />note_id.value = noteId;
			submitForm(document.<portlet:namespace />fm);
		}
	</script>

	<%
	List notes = ShoppingOrderServiceUtil.getNotes(order.getOrderId());

	request.setAttribute(WebKeys.NOTES_LIST, notes);
	%>

	<liferay-util:include page="/html/common/notes.jsp">
		<liferay-util:param name="namespace" value="<%= renderResponse.getNamespace() %>" />
	</liferay-util:include>

	<br>
</c:if>

<c:if test="<%= shoppingAdmin %>">
	<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
		<liferay-util:param name="box_br_wrap_content" value="false" />

		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "update") %>' onClick="if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-update-this-order") %>')) { document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.UPDATE %>'; document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_order" /><portlet:param name="order_id" value="<%= order.getOrderId() %>" /></portlet:actionURL>'; submitForm(document.<portlet:namespace />fm); }">

				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "delete") %>' onClick="if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-this-order") %>')) { document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.DELETE %>'; document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:renderURL><portlet:param name="struts_action" value="/shopping/view_orders" /></portlet:renderURL>'; submitForm(document.<portlet:namespace />fm); }">

				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="self.location = '<portlet:renderURL><portlet:param name="struts_action" value="/shopping/view_orders" /></portlet:renderURL>';">
			</td>
			<td width="30">
				&nbsp;
			</td>
			<td>
				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "invoice") %>' onClick="window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/shopping/edit_order_invoice" /><portlet:param name="order_id" value="<%= order.getOrderId() %>" /></portlet:renderURL>');">

				<c:if test="<%= shoppingConfig.getOrderEmail().isSend() %>">
					<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, (order.isSendOrderEmail() ? "" : "re") + "send-order-email") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'send_order_email'; document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_order" /><portlet:param name="order_id" value="<%= order.getOrderId() %>" /></portlet:actionURL>'; submitForm(document.<portlet:namespace />fm);">
				</c:if>

				<c:if test="<%= shoppingConfig.getShippingEmail().isSend() %>">
					<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, (order.isSendShippingEmail() ? "" : "re") + "send-shipping-email") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'send_shipping_email'; document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_order" /><portlet:param name="order_id" value="<%= order.getOrderId() %>" /></portlet:actionURL>'; submitForm(document.<portlet:namespace />fm);">
				</c:if>
			</td>
		</tr>
		</table>
	</liferay-ui:box>

	<br>
</c:if>

</form>