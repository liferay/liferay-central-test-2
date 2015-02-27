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
String redirect = ParamUtil.getString(request, "redirect");

ShoppingCart cart = ShoppingUtil.getCart(renderRequest);

Map<ShoppingCartItem, Integer> items = cart.getItems();

ShoppingCoupon coupon = cart.getCoupon();

boolean minQuantityMultiple = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.SHOPPING_CART_MIN_QTY_MULTIPLE);
%>

<aui:script position="inline">
	var <portlet:namespace />itemsInStock = true;

	function <portlet:namespace />checkout() {
		var form = AUI.$(document.<portlet:namespace />fm);

		if (<portlet:namespace />itemsInStock || confirm('<%= UnicodeLanguageUtil.get(request, "your-cart-has-items-that-are-out-of-stock") %>')) {
			form.fm('<%= Constants.CMD %>').val('<%= Constants.CHECKOUT %>');
			form.fm('redirect').val('<portlet:actionURL><portlet:param name="struts_action" value="/shopping/checkout" /><portlet:param name="cmd" value='<%= Constants.CHECKOUT %>'/></portlet:actionURL>');
			<portlet:namespace />updateCart();
		}
	}

	function <portlet:namespace />updateCart() {
		var count = 0;
		var form = AUI.$(document.<portlet:namespace />fm);
		var invalidSKUs = '';
		var itemIds = '';
		var subtotal = 0;

		<%
		int itemsCount= 0;

		for (ShoppingCartItem cartItem : items.keySet()) {
			ShoppingItem item = cartItem.getItem();

			ShoppingItemPrice[] itemPrices = (ShoppingItemPrice[])ShoppingItemPriceLocalServiceUtil.getItemPrices(item.getItemId()).toArray(new ShoppingItemPrice[0]);

			int maxQuantity = _getMaxQuantity(itemPrices);
		%>

			count = form.fm('item_<%= item.getItemId() %>_<%= itemsCount %>_count').val();

			subtotal += <%= ShoppingUtil.calculateActualPrice(item, 1) %> * count;

			if ((count == '') || isNaN(count) || (count < 0) || ((count > <%= maxQuantity %>) && (<%= maxQuantity %> > 0))) {
				if (invalidSKUs != '') {
					invalidSKUs += ', ';
				}

				invalidSKUs += '<%= HtmlUtil.escapeJS(item.getSku()) %>';
			}

			for (var i = 0; i < count; i++) {
				itemIds += '<%= HtmlUtil.escapeJS(cartItem.getCartItemId()) %>,';
			}

			count = 0;

		<%
			itemsCount++;
		}
		%>

		if (form.fm('<%= Constants.CMD %>').val() == '<%= Constants.CHECKOUT %>') {
			if (subtotal < <%= shoppingGroupServiceSettings.getMinOrder() %>) {
				form.fm('<%= Constants.CMD %>').val('<%= Constants.UPDATE %>');
				form.fm('redirect').val('<%= currentURL %>');

				alert('<%= UnicodeLanguageUtil.format(request, "your-order-cannot-be-processed-because-it-falls-below-the-minimum-required-amount-of-x", currencyFormat.format(shoppingGroupServiceSettings.getMinOrder()), false) %>');

				return;
			}
		}

		form.fm('itemIds').val(itemIds);

		if (invalidSKUs == '') {
			submitForm(form);
		}
		else {
			alert('<%= UnicodeLanguageUtil.get(request, "please-enter-valid-quantities-for-the-following-skus") %>' + invalidSKUs);
		}
	}
</aui:script>

<portlet:actionURL var="cartURL">
	<portlet:param name="struts_action" value="/shopping/cart" />
</portlet:actionURL>

<aui:form action="<%= cartURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "updateCart();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="itemIds" type="hidden" />

	<liferay-util:include page="/html/portlet/shopping/tabs1.jsp">
		<liferay-util:param name="tabs1" value="cart" />
	</liferay-util:include>

	<liferay-ui:error exception="<%= CartMinQuantityException.class %>">

		<%
		CartMinQuantityException cmqe = (CartMinQuantityException)errorException;

		long[] badItemIds = StringUtil.split(cmqe.getMessage(), 0L);
		%>

		<liferay-ui:message key="all-quantities-must-be-greater-than-the-minimum-quantity-of-the-item" /><br />

		<c:if test="<%= minQuantityMultiple %>">
			<br />

			<liferay-ui:message key="all-quantities-must-be-a-multiple-of-the-minimum-quantity-of-the-item" /><br />
		</c:if>

		<br />

		<liferay-ui:message key="please-reenter-your-quantity-for-the-items-with-the-following-skus" />

		<%
		for (int i = 0; i < badItemIds.length; i++) {
			ShoppingItem item = ShoppingItemServiceUtil.getItem(badItemIds[i]);
		%>

			<strong><%= HtmlUtil.escape(item.getSku()) %></strong><c:if test="<%= i + 1 < badItemIds.length %>">,</c:if>

		<%
		}
		%>

	</liferay-ui:error>

	<liferay-ui:error exception="<%= CouponActiveException.class %>" message="the-specified-coupon-is-not-active" />
	<liferay-ui:error exception="<%= CouponEndDateException.class %>" message="the-specified-coupon-is-no-longer-available" />
	<liferay-ui:error exception="<%= CouponStartDateException.class %>" message="the-specified-coupon-is-no-yet-available" />
	<liferay-ui:error exception="<%= NoSuchCouponException.class %>" message="please-enter-a-valid-coupon-code" />

	<%
	SearchContainer searchContainer = new SearchContainer();

	List<String> headerNames = new ArrayList<String>();

	headerNames.add("sku");
	headerNames.add("description");
	headerNames.add("quantity");
	headerNames.add("price");

	searchContainer.setHeaderNames(headerNames);
	searchContainer.setEmptyResultsMessage("your-cart-is-empty");
	searchContainer.setHover(false);

	int total = items.size();

	searchContainer.setTotal(total);

	List resultRows = searchContainer.getResultRows();

	int itemsCount = 0;

	for (Map.Entry<ShoppingCartItem, Integer> entry : items.entrySet()) {
		ShoppingCartItem cartItem = entry.getKey();
		Integer count = entry.getValue();

		ShoppingItem item = cartItem.getItem();

		item = item.toEscapedModel();

		String[] fieldsArray = cartItem.getFieldsArray();

		ShoppingItemField[] itemFields = (ShoppingItemField[])ShoppingItemFieldLocalServiceUtil.getItemFields(item.getItemId()).toArray(new ShoppingItemField[0]);
		ShoppingItemPrice[] itemPrices = (ShoppingItemPrice[])ShoppingItemPriceLocalServiceUtil.getItemPrices(item.getItemId()).toArray(new ShoppingItemPrice[0]);

		if (!SessionErrors.isEmpty(renderRequest)) {
			count = new Integer(ParamUtil.getInteger(request, "item_" + item.getItemId() + "_" + itemsCount + "_count"));
		}

		ResultRow row = new ResultRow(item, item.getItemId(), itemsCount);

		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setParameter("struts_action", "/shopping/view_item");
		rowURL.setParameter("redirect", currentURL);
		rowURL.setParameter("itemId", String.valueOf(item.getItemId()));

		// SKU and small image

		StringBundler sb = new StringBundler(6);

		if (item.isSmallImage()) {
			sb.append("<br />");
			sb.append("<img alt=\"");
			sb.append(HtmlUtil.escapeAttribute(item.getSku()));
			sb.append("\" src=\"");
			sb.append(item.getShoppingItemImageURL(themeDisplay));
			sb.append("\">");
		}
		else {
			sb.append(item.getSku());
		}

		row.addText(sb.toString(), rowURL);

		// Description

		sb = new StringBundler();

		sb.append(item.getName());

		if (Validator.isNotNull(item.getDescription())) {
			sb.append("<br />");
			sb.append(item.getDescription());
		}

		/*Properties props = new OrderedProperties();

		PropertiesUtil.load(props, item.getProperties());

		Enumeration enu = props.propertyNames();

		while (enu.hasMoreElements()) {
			String propsKey = (String)enu.nextElement();
			String propsValue = props.getProperty(propsKey, StringPool.BLANK);

			sb.append("<br />");
			sb.append(propsKey);
			sb.append(": ");
			sb.append(propsValue);
		}*/

		if (PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.SHOPPING_ITEM_SHOW_AVAILABILITY)) {
			sb.append("<br /><br />");

			if (ShoppingUtil.isInStock(item, itemFields, fieldsArray, count)) {
				sb.append(LanguageUtil.get(request, "availability"));
				sb.append(": ");
				sb.append("<div class=\"alert alert-success\">");
				sb.append(LanguageUtil.get(request, "in-stock"));
				sb.append("</div>");
			}
			else {
				sb.append(LanguageUtil.get(request, "availability"));
				sb.append(": ");
				sb.append("<div class=\"alert alert-danger\">");
				sb.append(LanguageUtil.get(request, "out-of-stock"));
				sb.append("</div>");

				sb.append("<script type=\"text/javascript\">");
				sb.append(renderResponse.getNamespace());
				sb.append("itemsInStock = false;");
				sb.append("</script>");
			}
		}

		if (fieldsArray.length > 0) {
			sb.append("<br />");
		}

		for (int j = 0; j < fieldsArray.length; j++) {
			int pos = fieldsArray[j].indexOf("=");

			String fieldName = fieldsArray[j].substring(0, pos);
			String fieldValue = fieldsArray[j].substring(pos + 1);

			sb.append("<br />");
			sb.append(HtmlUtil.escape(fieldName));
			sb.append(": ");
			sb.append(HtmlUtil.escape(fieldValue));
		}

		if (itemPrices.length > 0) {
			sb.append("<br />");
		}

		for (int j = 0; j < itemPrices.length; j++) {
			ShoppingItemPrice itemPrice = itemPrices[j];

			if (itemPrice.getStatus() == ShoppingItemPriceConstants.STATUS_INACTIVE) {
				continue;
			}

			sb.append("<br />");

			if ((itemPrice.getMinQuantity() == 0) && (itemPrice.getMaxQuantity() == 0)) {
				sb.append(LanguageUtil.get(request, "price"));
				sb.append(": ");
			}
			else if (itemPrice.getMaxQuantity() != 0) {
				sb.append(LanguageUtil.format(request, "price-for-x-to-x-items", new Object[] {"<strong>" + new Integer(itemPrice.getMinQuantity()) + "</strong>", "<strong>" + new Integer(itemPrice.getMaxQuantity()) + "</strong>"}, false));
			}
			else if (itemPrice.getMaxQuantity() == 0) {
				sb.append(LanguageUtil.format(request, "price-for-x-items-and-above", "<strong>" + new Integer(itemPrice.getMinQuantity()) + "</strong>", false));
			}

			if (itemPrice.getDiscount() <= 0) {
				sb.append(currencyFormat.format(itemPrice.getPrice()));
			}
			else {
				sb.append("<strike>");
				sb.append(currencyFormat.format(itemPrice.getPrice()));
				sb.append("</strike> ");
				sb.append("<div class=\"alert alert-success\">");
				sb.append(currencyFormat.format(ShoppingUtil.calculateActualPrice(itemPrice)));
				sb.append("</div> / ");
				sb.append(LanguageUtil.get(request, "you-save"));
				sb.append(": ");
				sb.append("<div class=\"alert alert-danger\">");
				sb.append(currencyFormat.format(ShoppingUtil.calculateDiscountPrice(itemPrice)));
				sb.append(" (");
				sb.append(percentFormat.format(itemPrice.getDiscount()));
				sb.append(StringPool.CLOSE_PARENTHESIS);
				sb.append("</div>");
			}
		}

		row.addText(sb.toString(), rowURL);

		// Quantity

		sb.setIndex(0);

		int maxQuantity = _getMaxQuantity(itemPrices);

		if (minQuantityMultiple && (item.getMinQuantity() > 1) && (maxQuantity != 0)) {
			sb.append("<select name=\"");
			sb.append(renderResponse.getNamespace());
			sb.append("item_");
			sb.append(item.getItemId());
			sb.append("_");
			sb.append(itemsCount);
			sb.append("_count\">");

			sb.append("<option value=\"0\">0</option>");

			for (int j = 1; j <= (maxQuantity / item.getMinQuantity()); j++) {
				int curQuantity = item.getMinQuantity() * j;

				sb.append("<option ");

				if (curQuantity == count.intValue()) {
					sb.append("selected ");
				}

				sb.append("value=\"");
				sb.append(curQuantity);
				sb.append("\">");
				sb.append(curQuantity);
				sb.append("</option>");
			}

			sb.append("</select>");
		}
		else {
			sb.append("<input name=\"");
			sb.append(renderResponse.getNamespace());
			sb.append("item_");
			sb.append(item.getItemId());
			sb.append("_");
			sb.append(itemsCount);
			sb.append("_count\" size=\"2\" type=\"text\" value=\"");
			sb.append(count);
			sb.append("\">");
		}

		row.addText(sb.toString());

		// Price

		row.addText(currencyFormat.format(ShoppingUtil.calculateActualPrice(item, count.intValue()) / count.intValue()), rowURL);

		// Add result row

		resultRows.add(row);

		itemsCount++;
	}
	%>

	<liferay-ui:search-iterator paginate="<%= false %>" searchContainer="<%= searchContainer %>" />

	<aui:fieldset>

		<%
		double subtotal = ShoppingUtil.calculateSubtotal(items);
		double actualSubtotal = ShoppingUtil.calculateActualSubtotal(items);
		double discountSubtotal = ShoppingUtil.calculateDiscountSubtotal(items);
		%>

			<c:choose>
				<c:when test="<%= subtotal == actualSubtotal %>">
					<aui:input name="subtotal" type="resource" value="<%= currencyFormat.format(subtotal) %>" />
				</c:when>
				<c:otherwise>
					<aui:field-wrapper label="subtotal">
						<div class="alert alert-success">
							<strike><%= currencyFormat.format(subtotal) %></strike> <%= currencyFormat.format(actualSubtotal) %>
						</div>
					</aui:field-wrapper>
				</c:otherwise>
			</c:choose>

		<c:if test="<%= subtotal != actualSubtotal %>">
			<aui:field-wrapper label="you-save">
				<div class="alert alert-danger">
					<%= currencyFormat.format(discountSubtotal) %> (<%= percentFormat.format(ShoppingUtil.calculateDiscountPercent(items)) %>)
				</div>
			</aui:field-wrapper>
		</c:if>

		<c:choose>
			<c:when test="<%= !shoppingGroupServiceSettings.useAlternativeShipping() %>">
				<aui:input name="shipping" type="resource" value="<%= currencyFormat.format(ShoppingUtil.calculateShipping(items)) %>" />
			</c:when>
			<c:otherwise>
				<aui:select label="shipping" name="alternativeShipping">

					<%
					String[][] alternativeShipping = shoppingGroupServiceSettings.getAlternativeShipping();

					for (int i = 0; i < 10; i++) {
						String altShippingName = alternativeShipping[0][i];
						String altShippingDelta = alternativeShipping[1][i];

						if (Validator.isNotNull(altShippingName) && Validator.isNotNull(altShippingDelta)) {
					%>

							<aui:option label='<%= LanguageUtil.get(request, altShippingName) + "(" + currencyFormat.format(ShoppingUtil.calculateAlternativeShipping(items, i)) + ")" %>' selected="<%= i == cart.getAltShipping() %>" value="<%= i %>" />

					<%
						}
					}
					%>

				</aui:select>
			</c:otherwise>
		</c:choose>

		<%
		double insurance = ShoppingUtil.calculateInsurance(items);
		%>

		<c:if test="<%= insurance > 0 %>">
			<aui:select label="insurance" name="insure">
				<aui:option label="none" selected="<%= !cart.isInsure() %>" value="0" />
				<aui:option label="<%= currencyFormat.format(insurance) %>" selected="<%= cart.isInsure() %>" value="1" />
			</aui:select>
		</c:if>

		<aui:input label="coupon-code" name="couponCodes" size="30" style="text-transform: uppercase;" type="text" value="<%= cart.getCouponCodes() %>" />

		<c:if test="<%= coupon != null %>">
			<aui:a href="javascript:;" label='<%= "(" + LanguageUtil.get(request, "description") + ")" %>' onClick='<%= renderResponse.getNamespace() + "viewCoupon();" %>' style="font-size: xx-small;" />

			<aui:field-wrapper label="coupon-discount">
				<div class="alert alert-danger">
					<%= currencyFormat.format(ShoppingUtil.calculateCouponDiscount(items, coupon)) %>
				</div>
			</aui:field-wrapper>
		</c:if>
	</aui:fieldset>

	<%
	String[] ccTypes = shoppingGroupServiceSettings.getCcTypes();

	if (shoppingGroupServiceSettings.usePayPal()) {
	%>

		<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="paypal" />" src="<%= themeDisplay.getPathThemeImages() %>/shopping/cc_paypal.png" />

		<br /><br />

	<%
	}
	else if (!shoppingGroupServiceSettings.usePayPal() && (ccTypes.length > 0)) {
		for (int i = 0; i < ccTypes.length; i++) {
	%>

			<img alt="<%= HtmlUtil.escapeAttribute(ccTypes[i]) %>" src="<%= themeDisplay.getPathThemeImages() %>/shopping/cc_<%= HtmlUtil.escapeAttribute(ccTypes[i]) %>.png" />

	<%
		}
	%>

		<br /><br />

	<%
	}
	%>

	<aui:button-row>
		<aui:button onClick='<%= renderResponse.getNamespace() + "updateCart();" %>' value="update-cart" />

		<aui:button disabled="<%= items.isEmpty() %>" onClick='<%= renderResponse.getNamespace() + "checkout();" %>' type="submit" value="checkout" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />viewCoupon() {
		Liferay.Util.openWindow(
			{
				dialog: {
					height: 200,
					width: 280
				},
				id: '<portlet:namespace />viewCoupon',
				refreshWindow: window,
				title: '<%= UnicodeLanguageUtil.get(request, "coupon") %>',
				uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/shopping/view_coupon" /><portlet:param name="couponId" value="<%= String.valueOf(coupon.getCouponId()) %>" /></portlet:renderURL>'
			}
		);
	}
</aui:script>

<%!
private static int _getMaxQuantity(ShoppingItemPrice[] itemPrices) {
	int maxQuantity = 0;

	for (ShoppingItemPrice itemPrice : itemPrices) {
		if (itemPrice.getMaxQuantity() == 0) {
			return 0;
		}

		if (maxQuantity < itemPrice.getMaxQuantity()) {
			maxQuantity = itemPrice.getMaxQuantity();
		}
	}

	return maxQuantity;
}
%>