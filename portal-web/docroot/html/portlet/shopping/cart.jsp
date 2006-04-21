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

boolean minQuantityMultiple = GetterUtil.getBoolean(CompanyPropsUtil.get(company.getCompanyId(), PropsUtil.SHOPPING_CART_MIN_QTY_MULTIPLE));
%>

<script type="text/javascript">
	var itemsInStock = true;

	function <portlet:namespace />checkout() {
		if (<%= (ShoppingUtil.meetsMinOrder(shoppingConfig, items)) ? "true" : "false" %>) {
			if (!itemsInStock) {
				if (confirm("<%= UnicodeLanguageUtil.get(pageContext, "your-cart-has-items-that-are-out-of-stock") %>")) {
					document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<portlet:actionURL><portlet:param name="struts_action" value="/shopping/checkout" /></portlet:actionURL>";
					<portlet:namespace />updateCart();
				}
			}
			else {
				document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<portlet:actionURL><portlet:param name="struts_action" value="/shopping/checkout" /></portlet:actionURL>";
				<portlet:namespace />updateCart();
			}
		}
		else {
			alert("<%= UnicodeLanguageUtil.format(pageContext, "your-order-cannot-be-processed-because-it-falls-below-the-minimum-required-amount-of-x", currency.getSymbol() + doubleFormat.format(shoppingConfig.getMinOrder()), false) %>");
		}
	}

	function <portlet:namespace />emptyCart() {
		document.<portlet:namespace />fm.<portlet:namespace />item_ids.value = "";
		document.<portlet:namespace />fm.<portlet:namespace />coupon_ids.value = "";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />updateCart() {
		var itemIds = "";
		var count = 0;

		<%
		int cartPos = 0;

		Iterator itr = items.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry entry = (Map.Entry)itr.next();

			ShoppingCartItem cartItem = (ShoppingCartItem)entry.getKey();

			ShoppingItem item = cartItem.getItem();
		%>

			count = document.<portlet:namespace />fm.<portlet:namespace />item_<%= item.getItemId() %>_<%= cartPos++ %>_count.value;

			for (var i = 0; i < count; i++) {
				itemIds += "<%= cartItem.getCartItemId() %>,";
			}

			count = 0;

		<%
		}
		%>

		document.<portlet:namespace />fm.<portlet:namespace />item_ids.value = itemIds;
		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/cart" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace />redirect" type="hidden" value="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/cart" /></portlet:actionURL>">
<input name="<portlet:namespace />shopping_<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>">
<input name="<portlet:namespace />item_ids" type="hidden" value="">

<c:if test="<%= items.size() > 0 %>">
	<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
		<liferay-util:param name="box_br_wrap_content" value="false" />

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "update-cart") %>' onClick="<portlet:namespace />updateCart();">

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "empty-cart") %>' onClick="<portlet:namespace />emptyCart();">

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "checkout") %>' onClick="<portlet:namespace />checkout();">
	</liferay-ui:box>

	<br>
</c:if>

<c:if test='<%= SessionMessages.contains(renderRequest, "cart_updated") %>'>
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
			<font class="portlet-msg-success" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "you-have-successfully-updated-your-shopping-cart") %></font>
		</td>
	</tr>
	</table>

	<br>
</c:if>

<c:if test="<%= SessionErrors.contains(renderRequest, CartMinQuantityException.class.getName()) %>">

	<%
	CartMinQuantityException cmqe = (CartMinQuantityException)request.getAttribute(CartMinQuantityException.class.getName());

	String[] badItemIds = StringUtil.split(cmqe.getMessage());
	%>

	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
			<font class="portlet-msg-error" style="font-size: xx-small;">

			<%= LanguageUtil.get(pageContext, "all-quantities-must-be-greater-than-the-minimum-quantity-of-the-item") %><br>

			<c:if test="<%= minQuantityMultiple %>">
				<br>

				<%= LanguageUtil.get(pageContext, "all-quantities-must-be-a-multiple-of-the-minimum-quantity-of-the-item") %><br>
			</c:if>

			<br>

			<%= LanguageUtil.get(pageContext, "please-reenter-your-quantity-for-the-items-with-the-following-skus") %>

			<%
			for (int i = 0; i < badItemIds.length; i++) {
				ShoppingItem item = ShoppingItemServiceUtil.getItemById(badItemIds[i]);
			%>

				<b><%= item.getSku() %></b><c:if test="<%= i + 1 < badItemIds.length %>">,</c:if>

			<%
			}
			%>

			</font>
		</td>
	</tr>
	</table>

	<br>
</c:if>

<c:if test="<%= SessionErrors.contains(renderRequest, NoSuchCouponException.class.getName()) %>">
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
			<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-coupon-code") %></font>
		</td>
	</tr>
	</table>

	<br>
</c:if>

<c:if test="<%= SessionErrors.contains(renderRequest, CouponActiveException.class.getName()) %>">
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
			<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "the-specified-coupon-is-not-active") %></font>
		</td>
	</tr>
	</table>

	<br>
</c:if>

<c:if test="<%= SessionErrors.contains(renderRequest, CouponStartDateException.class.getName()) %>">
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
			<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "the-specified-coupon-is-no-yet-available") %></font>
		</td>
	</tr>
	</table>

	<br>
</c:if>

<c:if test="<%= SessionErrors.contains(renderRequest, CouponEndDateException.class.getName()) %>">
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
			<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "the-specified-coupon-is-no-longer-available") %></font>
		</td>
	</tr>
	</table>

	<br>
</c:if>

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "shopping-cart") %>' />

	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "sku") %>
			</b></font>
		</td>
		<td><img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="20"></td>
		<td width="99%">
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "item-description") %>
			</b></font>
		</td>
		<td><img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="10"></td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "quantity") %>
			</b></font>
		</td>
		<td><img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="10"></td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b>
			<%= LanguageUtil.get(pageContext, "price") %>
			</b></font>
		</td>
	</tr>

	<%
	cartPos = 0;

	itr = items.entrySet().iterator();

	while (itr.hasNext()) {
		Map.Entry entry = (Map.Entry)itr.next();

		ShoppingCartItem cartItem = (ShoppingCartItem)entry.getKey();
		Integer count = (Integer)entry.getValue();

		ShoppingItem item = cartItem.getItem();
		String[] fieldsArray = cartItem.getFieldsArray();

		ShoppingItemField[] itemFields = (ShoppingItemField[])ShoppingItemFieldServiceUtil.getItemFields(item.getItemId()).toArray(new ShoppingItemField[0]);
		ShoppingItemPrice[] itemPrices = (ShoppingItemPrice[])ShoppingItemPriceServiceUtil.getItemPrices(item.getItemId()).toArray(new ShoppingItemPrice[0]);

		if (!SessionErrors.isEmpty(renderRequest)) {
			count = new Integer(ParamUtil.getInteger(request, "item_" + item.getItemId() + "_" + (cartPos) + "_count"));
		}
	%>

		<tr>
			<td colspan="7"><img border="0" height="10" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
		</tr>
		<tr>
			<td nowrap valign="top">
				<table border="0" cellpadding="4" cellspacing="0">
				<tr>
					<td><font class="portlet-font" style="font-size: xx-small;">[&nbsp;<b><%= item.getSku() %></b>&nbsp;]</font></td>
				</tr>
				</table>

				<table border="0" cellpadding="4" cellspacing="0">
				<tr>
					<td>
						<c:if test="<%= item.isSmallImage() %>">
							<a href="<portlet:renderURL><portlet:param name="struts_action" value="/shopping/view_item" /><portlet:param name="item_id" value="<%= item.getItemId() %>" /></portlet:renderURL>"><img border="0" hspace="0" src="<%= Validator.isNotNull(item.getSmallImageURL()) ? item.getSmallImageURL() : themeDisplay.getPathImage() + "/shopping/item?img_id=" + item.getItemId() + "&small=1" %>" vspace="0"></a>
						</c:if>
					</td>
				</tr>
				</table>
			</td>
			<td></td>
			<td valign="top">
				<font class="portlet-font" style="font-size: small;"><b><a href="<portlet:renderURL><portlet:param name="struts_action" value="/shopping/view_item" /><portlet:param name="item_id" value="<%= item.getItemId() %>" /></portlet:renderURL>">
				<%= item.getName() %><br>
				</a></b></font>

				<font class="portlet-font" style="font-size: x-small;">
				<%= item.getDescription() %><br>

				<br>

				<%
				for (int i = 0; i < itemPrices.length; i++) {
					ShoppingItemPrice itemPrice = itemPrices[i];
				%>

					<c:choose>
						<c:when test="<%= (itemPrice.getMinQuantity()) == 0 && (itemPrice.getMaxQuantity() == 0) %>">
							<%= LanguageUtil.get(pageContext, "price") %>:
						</c:when>
						<c:when test="<%= itemPrice.getMaxQuantity() != 0 %>">
							<%= LanguageUtil.format(pageContext, "price-for-x-to-x-items", new Object[] {"<b>" + new Integer(itemPrice.getMinQuantity()) + "</b>", "<b>" + new Integer(itemPrice.getMaxQuantity()) + "</b>"}, false) %>
						</c:when>
						<c:when test="<%= itemPrice.getMaxQuantity() == 0 %>">
							<%= LanguageUtil.format(pageContext, "price-for-x-items-and-above", "<b>" + new Integer(itemPrice.getMinQuantity()) + "</b>", false) %>
						</c:when>
					</c:choose>

					<c:if test="<%= itemPrice.getDiscount() <= 0 %>">
						<%= currency.getSymbol() %><%= doubleFormat.format(itemPrice.getPrice()) %><br>
					</c:if>

					<c:if test="<%= itemPrice.getDiscount() > 0 %>">
						<%= currency.getSymbol() %><strike><%= doubleFormat.format(itemPrice.getPrice()) %></strike> <font class="portlet-msg-success" style="font-size: x-small;"><%= currency.getSymbol() %><%= doubleFormat.format(ShoppingUtil.calculateActualPrice(itemPrice)) %></font> / <%= LanguageUtil.get(pageContext, "you-save") %>: <font class="portlet-msg-error" style="font-size: x-small;"><%= currency.getSymbol() %><%= doubleFormat.format(ShoppingUtil.calculateDiscountPrice(itemPrice)) %> (<%= percentFormat.format(itemPrice.getDiscount()) %>)</font><br>
					</c:if>

				<%
				}
				%>

				<br>

				<!--<%= LanguageUtil.get(pageContext, "min-qty") %>: <%= ShoppingUtil.getMinQuantity(item) %><br>-->

				<c:if test="<%= GetterUtil.getBoolean(CompanyPropsUtil.get(company.getCompanyId(), PropsUtil.SHOPPING_ITEM_SHOW_AVAILABILITY)) %>">
					<c:choose>
						<c:when test="<%= ShoppingUtil.isInStock(item, itemFields, fieldsArray) %>">
							<%= LanguageUtil.get(pageContext, "availability") %>: <font class="portlet-msg-success" style="font-size: x-small;"><%= LanguageUtil.get(pageContext, "in-stock") %></font><br>
						</c:when>
						<c:otherwise>
							<%= LanguageUtil.get(pageContext, "availability") %>: <font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "out-of-stock") %></font><br>

							<script type="text/javascript">
								itemsInStock = false;
							</script>
						</c:otherwise>
					</c:choose>

					<br>
				</c:if>

				<%
				for (int i = 0; i < fieldsArray.length; i++) {
					int pos = fieldsArray[i].indexOf("=");

					String fieldName = fieldsArray[i].substring(0, pos);
					String fieldValue = fieldsArray[i].substring(pos + 1, fieldsArray[i].length());
				%>

					<%= fieldName %>: <%= fieldValue %><br>

				<%
				}
				%>

				</font>
			</td>
			<td></td>
			<td valign="top">
				<c:choose>
					<c:when test="<%= minQuantityMultiple && (item.getMinQuantity() > 0) %>">
						<select name="<portlet:namespace />item_<%= item.getItemId() %>_<%= cartPos++ %>_count">
							<option value="0">0</option>

							<%
							for (int i = 1; i <= 10; i++) {
								int curQuantity = item.getMinQuantity() * i;
							%>

								<option <%= curQuantity == count.intValue() ? "selected" : "" %> value="<%= curQuantity %>"><%= curQuantity %></option>

							<%
							}
							%>

						</select>
					</c:when>

					<c:otherwise>
						<input class="form-text" name="<portlet:namespace />item_<%= item.getItemId() %>_<%= cartPos++ %>_count" size="2" type="text" value="<%= count %>">
					</c:otherwise>
				</c:choose>
			</td>
			<td></td>
			<td valign="top">
				<font class="portlet-font" style="font-size: x-small;">
				<%= currency.getSymbol() %><%= doubleFormat.format(ShoppingUtil.calculateActualPrice(item, count.intValue()) / count.intValue()) %>
				</font>
			</td>
		</tr>

	<%
	}
	%>

	<tr>
		<td colspan="7">
			<br>
		</td>
	</tr>
	<tr>
		<td colspan="7">
			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><b>
					<%= LanguageUtil.get(pageContext, "subtotal") %>:
					</b></font>
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td>
					<font class="portlet-font" style="font-size: x-small;">

					<%
					double subtotal = ShoppingUtil.calculateSubtotal(items);
					double actualSubtotal = ShoppingUtil.calculateActualSubtotal(items);
					double discountSubtotal = ShoppingUtil.calculateDiscountSubtotal(items);
					%>

					<c:if test="<%= subtotal == actualSubtotal %>">
						<%= currency.getSymbol() %><%= doubleFormat.format(subtotal) %>
					</c:if>

					<c:if test="<%= subtotal != actualSubtotal %>">
						<strike><%= currency.getSymbol() %><%= doubleFormat.format(subtotal) %></strike> <font class="portlet-msg-success" style="font-size: x-small;"><%= currency.getSymbol() %><%= doubleFormat.format(actualSubtotal) %></font>
					</c:if>

					</font>
				</td>
			</tr>

			<c:if test="<%= subtotal != actualSubtotal %>">
				<tr>
					<td>
						<font class="portlet-font" style="font-size: x-small;"><b>
						<%= LanguageUtil.get(pageContext, "you-save") %>:
						</b></font>
					</td>
					<td width="10">
						&nbsp;
					</td>
					<td>
						<font class="portlet-msg-error" style="font-size: x-small;">
						<%= currency.getSymbol() %><%= doubleFormat.format(discountSubtotal) %> (<%= percentFormat.format(ShoppingUtil.calculateDiscountPercent(items)) %>)
						</font>
					</td>
				</tr>
			</c:if>

			<tr>
				<td>
					<br>
				</td>
			</tr>
			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><b>
					<%= LanguageUtil.get(pageContext, "shipping") %>:
					</b></font>
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td>
					<c:choose>
						<c:when test="<%= !shoppingConfig.useAlternativeShipping() %>">
							<font class="portlet-font" style="font-size: x-small;">
							<%= currency.getSymbol() %><%= doubleFormat.format(ShoppingUtil.calculateShipping(items)) %>
							</font>
						</c:when>
						<c:otherwise>
							<select name="<portlet:namespace />alt_shipping">

								<%
								String[][] alternativeShipping = shoppingConfig.getAlternativeShipping();

								for (int i = 0; i < 10; i++) {
									String altShippingName = alternativeShipping[0][i];
									String altShippingDelta = alternativeShipping[1][i];

									if (Validator.isNotNull(altShippingName) && Validator.isNotNull(altShippingDelta)) {
								%>

										<option <%= i == cart.getAltShipping() ? "selected" : "" %> value="<%= i %>"><%= altShippingName %> (<%= currency.getSymbol() %><%= doubleFormat.format(ShoppingUtil.calculateAlternativeShipping(items, i)) %>)</option>

								<%
									}
								}
								%>

							</select>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>
					<br>
				</td>
			</tr>

			<%
			double insurance = ShoppingUtil.calculateInsurance(items);
			%>

			<c:if test="<%= insurance > 0 %>">
				<tr>
					<td>
						<font class="portlet-font" style="font-size: x-small;"><b>
						<%= LanguageUtil.get(pageContext, "insurance") %>:
						</b></font>
					</td>
					<td width="10">
						&nbsp;
					</td>
					<td>
						<select name="<portlet:namespace />insure">
							<option <%= !cart.isInsure() ? "selected" : "" %> value="0"><%= LanguageUtil.get(pageContext, "none") %></option>
							<option <%= cart.isInsure() ? "selected" : "" %> value="1"><%= currency.getSymbol() %><%= doubleFormat.format(insurance) %></option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<br>
					</td>
				</tr>
			</c:if>

			<tr>
				<td nowrap>
					<font class="portlet-font" style="font-size: x-small;"><b>
					<%= LanguageUtil.get(pageContext, "coupon-code") %>:
					</b></font>
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td colspan="5">
					<input class="form-text" name="<portlet:namespace />coupon_ids" type="text" value="<%= cart.getCouponIds() %>">

					<c:if test="<%= coupon != null %>">
						&nbsp;<font class="portlet-font" style="font-size: xx-small;">[<a href="javascript: var viewCouponWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/shopping/view_coupon" /><portlet:param name="coupon_id" value="<%= coupon.getCouponId() %>" /></portlet:renderURL>', 'viewCoupon', 'directories=no,height=200,location=no,menubar=no,resizable=no,scrollbars=yes,status=no,toolbar=no,width=280'); void(''); viewCouponWindow.focus();"><%= LanguageUtil.get(pageContext, "view") %></a>]</font>
					</c:if>
				</td>
			</tr>

			<c:if test="<%= coupon != null %>">
				<!--<tr>
					<td>
						<font class="portlet-font" style="font-size: x-small;"><b>
						<%= LanguageUtil.get(pageContext, "description") %>:
						</b></font>
					</td>
					<td width="10">
						&nbsp;
					</td>
					<td>
						<font class="portlet-font" style="font-size: x-small;"><b>
						<%= coupon.getName() %>
						</b></font>
					</td>
				</tr>
				<tr>
					<td colspan="2"></td>
					<td>
						<table border="0" cellpadding="0" cellspacing="0" width="75%">
						<tr>
							<td>
								<font class="portlet-font" style="font-size: xx-small;">
								<%= coupon.getDescription() %>
								</font>
							</td>
						</tr>
						</table>
					</td>
				</tr>-->
				<tr>
					<td>
						<font class="portlet-font" style="font-size: x-small;"><b>
						<%= LanguageUtil.get(pageContext, "coupon-discount") %>:
						</b></font>
					</td>
					<td width="10">
						&nbsp;
					</td>
					<td>
						<font class="portlet-msg-error" style="font-size: x-small;">
						<%= currency.getSymbol() %><%= doubleFormat.format(ShoppingUtil.calculateCouponDiscount(items, coupon)) %>
						</font>
					</td>
				</tr>
			</c:if>

			</table>
		</td>
	</tr>
	</table>
</liferay-ui:box>

<c:if test="<%= items.size() > 0 %>">
	<br>

	<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
		<liferay-util:param name="box_br_wrap_content" value="false" />

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "update-cart") %>' onClick="<portlet:namespace />updateCart();">

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "empty-cart") %>' onClick="<portlet:namespace />emptyCart();">

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "checkout") %>' onClick="<portlet:namespace />checkout();">
	</liferay-ui:box>
</c:if>

</form>