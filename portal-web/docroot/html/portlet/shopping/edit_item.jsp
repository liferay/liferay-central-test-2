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
ShoppingItem item = (ShoppingItem)request.getAttribute(WebKeys.SHOPPING_ITEM);

String itemId = null;
if (item != null) {
	itemId = item.getItemId();
}

String categoryId = ParamUtil.get(request, "category_id", ShoppingCategory.DEFAULT_PARENT_CATEGORY_ID);
if (item != null) {
	categoryId = item.getCategoryId();
}

String sku = request.getParameter("item_sku");
if ((sku == null) || (sku.equals(StringPool.NULL))) {
	sku = "";

	if (item != null) {
		sku = item.getSku();
	}
}

String name = request.getParameter("item_name");
if ((name == null) || (name.equals(StringPool.NULL))) {
	name = "";

	if (item != null) {
		name = item.getName();
	}
}

String description = request.getParameter("item_desc");
if ((description == null) || (description.equals(StringPool.NULL))) {
	description = "";

	if (item != null) {
		description = item.getDescription();
	}
}

String properties = request.getParameter("item_props");
if ((properties == null) || (properties.equals(StringPool.NULL))) {
	properties = "";

	if (item != null) {
		properties = item.getProperties();
	}
}

String supplierUserId = request.getParameter("item_supplier_user_id");
if ((supplierUserId == null) || (supplierUserId.equals(StringPool.NULL))) {
	supplierUserId = "";

	if (item != null) {
		supplierUserId = GetterUtil.getString(item.getSupplierUserId());
	}
}

boolean requiresShipping = ParamUtil.get(request, "item_r_s", true);
String requiresShippingParam = request.getParameter("item_r_s");
if ((requiresShippingParam == null) || (requiresShippingParam.equals(StringPool.NULL))) {
	if (item != null) {
		requiresShipping = item.isRequiresShipping();
	}
}

int stockQuantity = ParamUtil.get(request, "item_stock_quantity", 0);
String stockQuantityParam = request.getParameter("item_stock_quantity");
if ((stockQuantityParam == null) || (stockQuantityParam.equals(StringPool.NULL))) {
	if (item != null) {
		stockQuantity = item.getStockQuantity();
	}
}

boolean featured = ParamUtil.get(request, "item_featured", true);
String featuredParam = request.getParameter("item_featured");
if ((featuredParam == null) || (featuredParam.equals(StringPool.NULL))) {
	if (item != null) {
		featured = item.isFeatured();
	}
}

// Fields

int fieldId = ParamUtil.get(request, "item_field_id", -1);

ShoppingItemField[] itemFields = null;

int numberOfFields = ParamUtil.get(request, "n_of_fields", 0);
String numberOfFieldsParam = request.getParameter("n_of_fields");
if ((numberOfFieldsParam == null) || (numberOfFieldsParam.equals(StringPool.NULL))) {
	if (item != null) {
		itemFields = (ShoppingItemField[])ShoppingItemFieldServiceUtil.getItemFields(itemId).toArray(new ShoppingItemField[0]);
		numberOfFields = itemFields.length;
	}
	else {
		itemFields = new ShoppingItemField[0];
	}
}
else {
	itemFields = new ShoppingItemField[numberOfFields];
}

String fieldsQuantities = "";
if (item != null) {
	fieldsQuantities = GetterUtil.getString(item.getFieldsQuantities());
}

// Prices

int priceId = ParamUtil.get(request, "item_price_id", -1);

ShoppingItemPrice[] itemPrices = null;

int numberOfPrices = ParamUtil.get(request, "n_of_prices", 1);
String numberOfPricesParam = request.getParameter("n_of_prices");
if ((numberOfPricesParam == null) || (numberOfPricesParam.equals(StringPool.NULL))) {
	if (item != null) {
		itemPrices = (ShoppingItemPrice[])ShoppingItemPriceServiceUtil.getItemPrices(itemId).toArray(new ShoppingItemPrice[0]);
		numberOfPrices = itemPrices.length;
	}
	else {
		itemPrices = new ShoppingItemPrice[1];
	}
}
else {
	itemPrices = new ShoppingItemPrice[numberOfPrices];
}

// Images

boolean smallImage = ParamUtil.get(request, "item_small_image", false);
String smallImageParam = request.getParameter("item_small_image");
if ((smallImageParam == null) || (smallImageParam.equals(StringPool.NULL))) {
	if (item != null) {
		smallImage = item.isSmallImage();
	}
}

String smallImageURL = request.getParameter("item_small_image_url");
if ((smallImageURL == null) || (smallImageURL.equals(StringPool.NULL))) {
	smallImageURL = "";

	if (item != null) {
		smallImageURL = item.getSmallImageURL();
	}
}

boolean mediumImage = ParamUtil.get(request, "item_medium_image", false);
String mediumImageParam = request.getParameter("item_medium_image");
if ((mediumImageParam == null) || (mediumImageParam.equals(StringPool.NULL))) {
	if (item != null) {
		mediumImage = item.isMediumImage();
	}
}

String mediumImageURL = request.getParameter("item_medium_image_url");
if ((mediumImageURL == null) || (mediumImageURL.equals(StringPool.NULL))) {
	mediumImageURL = "";

	if (item != null) {
		mediumImageURL = item.getMediumImageURL();
	}
}

boolean largeImage = ParamUtil.get(request, "item_large_image", false);
String largeImageParam = request.getParameter("item_large_image");
if ((largeImageParam == null) || (largeImageParam.equals(StringPool.NULL))) {
	if (item != null) {
		largeImage = item.isLargeImage();
	}
}

String largeImageURL = request.getParameter("item_large_image_url");
if ((largeImageURL == null) || (largeImageURL.equals(StringPool.NULL))) {
	largeImageURL = "";

	if (item != null) {
		largeImageURL = item.getLargeImageURL();
	}
}

String scroll = ParamUtil.getString(request, "scroll");
%>

<c:if test='<%= scroll.equals("item_fields") %>'>
	<script language="JavaScript" event="onLoad()" for="window">
		document.<portlet:namespace />fm.<portlet:namespace />item_fields.scrollIntoView();
	</script>
</c:if>

<c:if test='<%= scroll.equals("item_prices") %>'>
	<script language="JavaScript" event="onLoad()" for="window">
		document.<portlet:namespace />fm.<portlet:namespace />item_prices.scrollIntoView();
	</script>
</c:if>

<script type="text/javascript">
	function <portlet:namespace />addField() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.EDIT %>";
		document.<portlet:namespace />fm.<portlet:namespace />scroll.value = "item_fields";
		document.<portlet:namespace />fm.<portlet:namespace />n_of_fields.value = <%= numberOfFields + 1 %>;
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />addPrice() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.EDIT %>";
		document.<portlet:namespace />fm.<portlet:namespace />scroll.value = "item_prices";
		document.<portlet:namespace />fm.<portlet:namespace />n_of_prices.value = <%= numberOfPrices + 1 %>;
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />cancel() {
		self.location = "<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_category" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EDIT %>" /><portlet:param name="category_id" value="<%= categoryId %>" /></portlet:actionURL>";
	}

	function <portlet:namespace />deleteField(i) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.EDIT %>";
		document.<portlet:namespace />fm.<portlet:namespace />scroll.value = "item_fields";
		document.<portlet:namespace />fm.<portlet:namespace />item_field_id.value = i;
		document.<portlet:namespace />fm.<portlet:namespace />n_of_fields.value = <%= numberOfFields - 1 %>;
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />deleteItem() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_category" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EDIT %>" /><portlet:param name="category_id" value="<%= categoryId %>" /></portlet:actionURL>";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />deletePrice(i) {
		if (document.<portlet:namespace />fm.<portlet:namespace />item_default_price[i].checked) {
			alert("<%= UnicodeLanguageUtil.get(pageContext, "you-cannot-delete-or-deactivate-a-default-price") %>");
		}
		else if (document.<portlet:namespace />fm.<portlet:namespace />n_of_prices.value > 1) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.EDIT %>";
			document.<portlet:namespace />fm.<portlet:namespace />scroll.value = "item_prices";
			document.<portlet:namespace />fm.<portlet:namespace />item_price_id.value = i;
			document.<portlet:namespace />fm.<portlet:namespace />n_of_prices.value = <%= numberOfPrices - 1 %>;
			submitForm(document.<portlet:namespace />fm);
		}
	}

	function <portlet:namespace />editItemQuantities() {
		var itemQuantitiesURL = "<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" anchor="false"><portlet:param name="struts_action" value="/shopping/edit_item_quantities" /></liferay-portlet:renderURL>&<portlet:namespace />item_fields_quantities=" + document.<portlet:namespace />fm.<portlet:namespace />item_fields_quantities.value;

		<%
		for (int i = 0; i < numberOfFields; i++) {
		%>

			itemQuantitiesURL += "&<portlet:namespace />n_<%= i %>=" + encodeURIComponent(document.<portlet:namespace />fm.<portlet:namespace />item_<%= i %>_field_name.value);
			itemQuantitiesURL += "&<portlet:namespace />v_<%= i %>=" + encodeURIComponent(document.<portlet:namespace />fm.<portlet:namespace />item_<%= i %>_field_values.value);

		<%
		}
		%>

		var itemQuantitiesWindow = window.open(itemQuantitiesURL, "itemQuantities", "directories=no,height=400,location=no,menubar=no,resizable=no,scrollbars=yes,status=no,toolbar=no,width=300");
		void("");
		itemQuantitiesWindow.focus();
	}

	function <portlet:namespace />saveItem() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.ADD %>";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />saveAndAddAnotherItem() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.ADD %>";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_item" /><portlet:param name="category_id" value="<%= categoryId %>" /></portlet:actionURL>";
		submitForm(document.<portlet:namespace />fm);
	}

	<c:if test="<%= item != null %>">
		function <portlet:namespace />updateItem() {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.UPDATE %>";
			document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<portlet:renderURL><portlet:param name="struts_action" value="/shopping/view_item" /><portlet:param name="item_id" value="<%= itemId %>" /></portlet:renderURL>";
			submitForm(document.<portlet:namespace />fm);
		}
	</c:if>
</script>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_item" /></portlet:actionURL>" enctype="multipart/form-data" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= item == null ? Constants.ADD : Constants.UPDATE %>">
<input name="<portlet:namespace />redirect" type="hidden" value="">
<input name="<portlet:namespace />scroll" type="hidden" value="">
<input name="<portlet:namespace />category_id" type="hidden" value="<%= categoryId %>">
<input name="<portlet:namespace />item_id" type="hidden" value="<%= itemId %>">
<input name="<portlet:namespace />item_field_id" type="hidden" value="">
<input name="<portlet:namespace />n_of_fields" type="hidden" value="<%= numberOfFields %>">
<input name="<portlet:namespace />item_fields_quantities" type="hidden" value="<%= fieldsQuantities %>">
<input name="<portlet:namespace />item_price_id" type="hidden" value="">
<input name="<portlet:namespace />n_of_prices" type="hidden" value="<%= numberOfPrices %>">

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_br_wrap_content" value="false" />

	<c:if test="<%= item == null %>">
		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "save") %>' onClick="<portlet:namespace />saveItem();">

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "save-and-add-another") %>' onClick="<portlet:namespace />saveAndAddAnotherItem();">
	</c:if>

	<c:if test="<%= item != null %>">
		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "update") %>' onClick="<portlet:namespace />updateItem();">

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "delete") %>' onClick="<portlet:namespace />deleteItem();">
	</c:if>

	<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="<portlet:namespace />cancel();">
</liferay-ui:box>

<br>

<c:if test="<%= !SessionErrors.isEmpty(renderRequest) %>">
	<table border="0" cellpadding="0" cellspacing="0" width="95%">
	<tr>
		<td>
			<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "you-have-entered-invalid-data") %></font>
		</td>
	</tr>
	</table>

	<br>
</c:if>

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "item-information") %>' />

	<table border="0" cellpadding="0" cellspacing="0" width="90%">
	<tr>
		<td>
			<table border="0" cellpadding="0" cellspacing="2">
			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;">

					<a href="<portlet:renderURL><portlet:param name="struts_action" value="/shopping/browse_categories" /></portlet:renderURL>"><%= LanguageUtil.get(pageContext, "browse-categories") %></a>

					<%
					List parentCategories = ShoppingCategoryServiceUtil.getParentCategories(categoryId);

					for (int i = 0; i < parentCategories.size(); i++) {
						ShoppingCategory parentCategory = (ShoppingCategory)parentCategories.get(i);
					%>

						&nbsp;<font class="portlet-msg-error" style="font-size: x-small;">&raquo;</font>&nbsp;

						<a href="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_category" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EDIT %>" /><portlet:param name="category_id" value="<%= parentCategory.getCategoryId() %>" /></portlet:actionURL>"><%= parentCategory.getName() %></a>

					<%
					}
					%>

					</font>
				</td>
			</tr>
			<tr>
				<td>
					<br>
				</td>
			</tr>
			<tr>
				<td>
					<table border="0" cellpadding="0" cellspacing="0">

					<c:if test="<%= SessionErrors.contains(renderRequest, ItemSKUException.class.getName()) || SessionErrors.contains(renderRequest, DuplicateItemSKUException.class.getName()) || SessionErrors.contains(renderRequest, ItemNameException.class.getName()) %>">
						<tr>
							<td colspan="7">
								<c:if test="<%= SessionErrors.contains(renderRequest, ItemSKUException.class.getName()) %>">
									<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-item-sku") %></font>
								</c:if>

								<c:if test="<%= SessionErrors.contains(renderRequest, DuplicateItemSKUException.class.getName()) %>">
									<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "the-item-sku-you-requested-is-already-taken") %></font>
								</c:if>

								<c:if test="<%= SessionErrors.contains(renderRequest, ItemNameException.class.getName()) %>">
									<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-item-name") %></font>
								</c:if>
							</td>
						</tr>
					</c:if>

					<tr>
						<td>
							<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "sku") %></b></font>
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td>
							<input class="form-text" name="<portlet:namespace />item_sku" size="15" type="text" value="<%= sku %>">
						</td>
						<td width="30">
							&nbsp;
						</td>
						<td>
							<font class="portlet-font" style="font-size: x-small;"><%= LanguageUtil.get(pageContext, "name") %></font>
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td>
							<input class="form-text" name="<portlet:namespace />item_name" size="55" type="text" value="<%= name %>">
						</td>
					</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<br>
				</td>
			</tr>
			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><%= LanguageUtil.get(pageContext, "description") %></font>
				</td>
			</tr>
			<tr>
				<td>
					<textarea class="form-text" cols="70" name="<portlet:namespace />item_desc" rows="7" wrap="soft"><%= GetterUtil.getString(description) %></textarea>
				</td>
			</tr>
			<tr>
				<td>
					<br>
				</td>
			</tr>
			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "properties") %></b></font>
				</td>
			</tr>
			<tr>
				<td>
					<textarea class="form-text" cols="70" name="<portlet:namespace />item_props" rows="5" wrap="soft"><%= GetterUtil.getString(properties) %></textarea>
				</td>
			</tr>
			<tr>
				<td>
					<br>
				</td>
			</tr>
			<tr>
				<td>
					<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "requires-shipping") %></b></font>
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td>
							<select name="<portlet:namespace />item_r_s">
								<option <%= (requiresShipping) ? "selected" : "" %> value="1"><%= LanguageUtil.get(pageContext, "yes") %></option>
								<option <%= (!requiresShipping) ? "selected" : "" %> value="0"><%= LanguageUtil.get(pageContext, "no") %></option>
							</select>
						</td>

						<c:if test="<%= numberOfFields == 0 %>">
							<td width="30">
								&nbsp;
							</td>
							<td>
								<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "stock-quantity") %></b></font>
							</td>
							<td width="10">
								&nbsp;
							</td>
							<td>
								<input class="form-text" name="<portlet:namespace />item_stock_quantity" size="4" type="text" value="<%= stockQuantity %>">
							</td>
						</c:if>

						<c:if test="<%= numberOfFields > 0 %>">
							<input name="<portlet:namespace />item_stock_quantity" type="hidden" value="<%= stockQuantity %>">
						</c:if>

						<td width="30">
							&nbsp;
						</td>
						<td>
							<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "featured") %></b></font>
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td>
							<select name="<portlet:namespace />item_featured">
								<option <%= (featured) ? "selected" : "" %> value="1"><%= LanguageUtil.get(pageContext, "yes") %></option>
								<option <%= (!featured) ? "selected" : "" %> value="0"><%= LanguageUtil.get(pageContext, "no") %></option>
							</select>
						</td>
					</tr>
					</table>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	</table>
</liferay-ui:box>

<br>

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "supplier") %>' />

	<table border="0" cellpadding="0" cellspacing="0" width="90%">
	<tr>
		<td>
			<font class="portlet-font" style="font-size: x-small;">
			<%= LanguageUtil.get(pageContext, "in-the-case-where-a-customer-completes-an-order-that-contains-items-from-many-suppliers") %>
			</font>
		</td>
	</tr>
	</table>

	<br>

	<table border="0" cellpadding="0" cellspacing="0" width="90%">
	<tr>
		<td>
			<table border="0" cellpadding="0" cellspacing="2">
			<tr>
				<td>
					<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "supplier-user-id") %></b></font>
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td>
							<input class="form-text" name="<portlet:namespace />item_supplier_user_id" size="20" type="text" value="<%= supplierUserId %>">
						</td>
					</tr>
					</table>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	</table>
</liferay-ui:box>

<br>

<input name="<portlet:namespace />item_fields" type="hidden">

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "fields") %>' />

	<table border="0" cellpadding="0" cellspacing="0" width="90%">
	<tr>
		<td>
			<font class="portlet-font" style="font-size: x-small;">
			<%= LanguageUtil.get(pageContext, "fields-are-added-if-you-need-to-distinguish-items-based-on-criteria-chosen-by-the-user") %>
			</font>
		</td>
	</tr>
	</table>

	<br>

	<table border="0" cellpadding="0" cellspacing="0" width="90%">

	<%
	for (int i = 0; i < numberOfFields; i++) {
		int curFieldId = i;

		if (fieldId > -1 && i >= fieldId) {
			curFieldId++;
		}

		String fieldName = ParamUtil.getString(request, "item_" + curFieldId + "_field_name");
		String fieldNameParam = request.getParameter("item_" + curFieldId + "_field_name");
		if ((fieldNameParam == null) || (fieldNameParam.equals(StringPool.NULL))) {
			if (itemFields[curFieldId] != null) {
				fieldName = itemFields[curFieldId].getName();
			}
		}

		String[] fieldValues = StringUtil.split(ParamUtil.getString(request, "item_" + curFieldId + "_field_values"));
		String fieldValuesParam = request.getParameter("item_" + curFieldId + "_field_values");
		if ((fieldValuesParam == null) || (fieldValuesParam.equals(StringPool.NULL))) {
			if (itemFields[curFieldId] != null) {
				fieldValues = itemFields[curFieldId].getValuesArray();
			}
		}

		String fieldDescription = ParamUtil.getString(request, "item_" + curFieldId + "_field_desc");
		String fieldDescriptionParam = request.getParameter("item_" + curFieldId + "_field_desc");
		if ((fieldDescriptionParam == null) || (fieldDescriptionParam.equals(StringPool.NULL))) {
			if (itemFields[curFieldId] != null) {
				fieldDescription = itemFields[curFieldId].getDescription();
			}
		}
	%>

		<tr>
			<td>
				<table border="0" cellpadding="0" cellspacing="2">
				<tr>
					<td>
						<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<font class="portlet-font" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "name") %></font>
							</td>
							<td width="5">
								&nbsp;
							</td>
							<td>
								<input class="form-text" name="<portlet:namespace />item_<%= i %>_field_name" size="8" type="text" value="<%= fieldName %>">
							</td>
							<td width="30">
								&nbsp;
							</td>
							<td>
								<font class="portlet-font" style="font-size: xx-small;"><b><%= LanguageUtil.get(pageContext, "values") %></b></font>
							</td>
							<td width="5">
								&nbsp;
							</td>
							<td>
								<input class="form-text" name="<portlet:namespace />item_<%= i %>_field_values" size="20" type="text" value='<%= StringUtil.merge(fieldValues, ", ") %>'>
							</td>
							<td width="30">
								&nbsp;
							</td>
							<td>
								<font class="portlet-font" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "description") %></font>
							</td>
							<td width="5">
								&nbsp;
							</td>
							<td>
								<input class="form-text" name="<portlet:namespace />item_<%= i %>_field_desc" size="25" type="text" value="<%= Html.formatTo(fieldDescription) %>">
							</td>

							<c:if test="<%= numberOfFields > 0 %>">
								<td width="30">
									&nbsp;
								</td>
								<td>
									<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "delete") %>' onClick="<portlet:namespace />deleteField(<%= i %>);">
								</td>
							</c:if>
						</tr>
						</table>
					</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td><img border="0" height="10" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
		</tr>
		<tr>
			<td class="gamma"><img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
		</tr>
		<tr>
			<td><img border="0" height="10" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
		</tr>

	<%
	}
	%>

	<tr>
		<td>
			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "add-field") %>' onClick="<portlet:namespace />addField();">

			<c:if test="<%= numberOfFields > 0 %>">
				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "edit-stock-quantity") %>' onClick="<portlet:namespace />editItemQuantities();">
			</c:if>
		</td>
	</tr>
	</table>
</liferay-ui:box>

<br>

<input name="<portlet:namespace />item_prices" type="hidden">

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "prices") %>' />

	<table border="0" cellpadding="0" cellspacing="0" width="90%">

	<%
	for (int i = 0; i < numberOfPrices; i++) {
		int curPriceId = i;

		if (priceId > -1 && i >= priceId) {
			curPriceId++;
		}

		int minQuantity = ParamUtil.get(request, "item_" + curPriceId + "_min_quantity", 0);
		String minQuantityParam = request.getParameter("item_" + curPriceId + "_min_quantity");
		if ((minQuantityParam == null) || (minQuantityParam.equals(StringPool.NULL))) {
			if (itemPrices[curPriceId] != null) {
				minQuantity = itemPrices[curPriceId].getMinQuantity();
			}
		}

		int maxQuantity = ParamUtil.get(request, "item_" + curPriceId + "_max_quantity", 0);
		String maxQuantityParam = request.getParameter("item_" + curPriceId + "_max_quantity");
		if ((maxQuantityParam == null) || (maxQuantityParam.equals(StringPool.NULL))) {
			if (itemPrices[curPriceId] != null) {
				maxQuantity = itemPrices[curPriceId].getMaxQuantity();
			}
		}

		double price = ParamUtil.get(request, "item_" + curPriceId + "_price", 0.0);
		String priceParam = request.getParameter("item_" + curPriceId + "_price");
		if ((priceParam == null) || (priceParam.equals(StringPool.NULL))) {
			if (itemPrices[curPriceId] != null) {
				price = itemPrices[curPriceId].getPrice();
			}
		}

		double discount = ParamUtil.get(request, "item_" + curPriceId + "_discount", 0.0) / 100;
		String discountParam = request.getParameter("item_" + curPriceId + "_discount");
		if ((discountParam == null) || (discountParam.equals(StringPool.NULL))) {
			if (itemPrices[curPriceId] != null) {
				discount = itemPrices[curPriceId].getDiscount();
			}
		}

		boolean taxable = ParamUtil.get(request, "item_" + curPriceId + "_taxable", true);
		String taxableParam = request.getParameter("item_" + curPriceId + "_taxable");
		if ((taxableParam == null) || (taxableParam.equals(StringPool.NULL))) {
			if (itemPrices[curPriceId] != null) {
				taxable = itemPrices[curPriceId].isTaxable();
			}
		}

		double shipping = ParamUtil.get(request, "item_" + curPriceId + "_shipping", 0.0);
		String shippingParam = request.getParameter("item_" + curPriceId + "_shipping");
		if ((shippingParam == null) || (shippingParam.equals(StringPool.NULL))) {
			if (itemPrices[curPriceId] != null) {
				shipping = itemPrices[curPriceId].getShipping();
			}
		}

		boolean useShippingFormula = ParamUtil.get(request, "item_" + curPriceId + "_u_s_f", true);
		String useShippingFormulaParam = request.getParameter("item_" + curPriceId + "_u_s_f");
		if ((useShippingFormulaParam == null) || (useShippingFormulaParam.equals(StringPool.NULL))) {
			if (itemPrices[curPriceId] != null) {
				useShippingFormula = itemPrices[curPriceId].isUseShippingFormula();
			}
		}

		boolean active = ParamUtil.get(request, "item_" + curPriceId + "_active", true);
		String activeParam = request.getParameter("item_" + curPriceId + "_active");
		if ((activeParam == null) || (activeParam.equals(StringPool.NULL))) {
			if (itemPrices[curPriceId] != null) {
				int status = itemPrices[curPriceId].getStatus();

				if (status == ShoppingItemPrice.STATUS_ACTIVE_DEFAULT || status == ShoppingItemPrice.STATUS_ACTIVE) {
					active = true;
				}
				else {
					active = false;
				}
			}
		}

		String defaultPriceParam = request.getParameter("item_default_price");
		boolean defaultPrice = (curPriceId == 0 ? true : false);
		if (Validator.isNotNull(defaultPriceParam)) {
			if (ParamUtil.get(request, "item_default_price", 0) == curPriceId) {
				defaultPrice = true;
			}
			else {
				defaultPrice = false;
			}
		}
		else {
			if (itemPrices[curPriceId] != null) {
				int status = itemPrices[curPriceId].getStatus();

				if (status == ShoppingItemPrice.STATUS_ACTIVE_DEFAULT) {
					defaultPrice = true;
				}
				else {
					defaultPrice = false;
				}
			}
		}
	%>

		<input name="<portlet:namespace />item_<%= i %>_taxable" type="hidden" value="<%= taxable %>">
		<input name="<portlet:namespace />item_<%= i %>_u_s_f" type="hidden" value="<%= useShippingFormula %>">
		<input name="<portlet:namespace />item_<%= i %>_active" type="hidden" value="<%= active %>">

		<tr>
			<td>
				<table border="0" cellpadding="0" cellspacing="2">
				<tr>
					<td>
						<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<font class="portlet-font" style="font-size: xx-small;"><b><%= LanguageUtil.get(pageContext, "min-qty") %></b></font>
							</td>
							<td width="5">
								&nbsp;
							</td>
							<td>
								<input class="form-text" name="<portlet:namespace />item_<%= i %>_min_quantity" size="4" type="text" value="<%= minQuantity %>">
							</td>
							<td width="30">
								&nbsp;
							</td>
							<td>
								<font class="portlet-font" style="font-size: xx-small;"><b><%= LanguageUtil.get(pageContext, "max-qty") %></b></font>
							</td>
							<td width="5">
								&nbsp;
							</td>
							<td>
								<input class="form-text" name="<portlet:namespace />item_<%= i %>_max_quantity" size="4" type="text" value="<%= maxQuantity %>">
							</td>
							<td width="30">
								&nbsp;
							</td>
							<td>
								<font class="portlet-font" style="font-size: xx-small;"><b><%= LanguageUtil.get(pageContext, "price") %></b></font>
							</td>
							<td width="5">
								&nbsp;
							</td>
							<td>
								<input class="form-text" name="<portlet:namespace />item_<%= i %>_price" size="4" type="text" value="<%= currency.getSymbol() %><%= doubleFormat.format(price) %>">
							</td>
							<td width="30">
								&nbsp;
							</td>
							<td>
								<font class="portlet-font" style="font-size: xx-small;"><b><%= LanguageUtil.get(pageContext, "discount") %></b></font>
							</td>
							<td width="5">
								&nbsp;
							</td>
							<td>
								<input class="form-text" name="<portlet:namespace />item_<%= i %>_discount" size="4" type="text" value="<%= percentFormat.format(discount) %>">
							</td>
							<td width="30">
								&nbsp;
							</td>
							<td>
								<font class="portlet-font" style="font-size: xx-small;"><b><%= LanguageUtil.get(pageContext, "taxable") %></b></font>
							</td>
							<td width="5">
								&nbsp;
							</td>
							<td>
								<input <%= taxable ? "checked" : "" %> type="checkbox" onClick="if (this.checked) { document.<portlet:namespace />fm.<portlet:namespace />item_<%= i %>_taxable.value = '1'; } else { document.<portlet:namespace />fm.<portlet:namespace />item_<%= i %>_taxable.value = '0'; }">
							</td>
						</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td><img border="0" height="2" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
				</tr>
				<tr>
					<td>
						<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<font class="portlet-font" style="font-size: xx-small;"><b><%= LanguageUtil.get(pageContext, "shipping") %></b></font>
							</td>
							<td width="5">
								&nbsp;
							</td>
							<td>
								<input class="form-text" name="<portlet:namespace />item_<%= i %>_shipping" size="4" type="text" value="<%= currency.getSymbol() %><%= doubleFormat.format(shipping) %>">
							</td>
							<td width="30">
								&nbsp;
							</td>
							<td>
								<font class="portlet-font" style="font-size: xx-small;"><b><%= LanguageUtil.get(pageContext, "use-shipping-formula") %></b></font>
							</td>
							<td width="5">
								&nbsp;
							</td>
							<td>
								<input <%= useShippingFormula ? "checked" : "" %> type="checkbox" onClick="if (this.checked) { document.<portlet:namespace />fm.<portlet:namespace />item_<%= i %>_u_s_f.value = '1'; } else { document.<portlet:namespace />fm.<portlet:namespace />item_<%= i %>_u_s_f.value = '0'; }">
							</td>
							<td width="30">
								&nbsp;
							</td>
							<td>
								<font class="portlet-font" style="font-size: xx-small;"><b><%= LanguageUtil.get(pageContext, "active") %></b></font>
							</td>
							<td width="5">
								&nbsp;
							</td>
							<td>
								<input <%= active ? "checked" : "" %> type="checkbox" onClick="if (this.checked) { document.<portlet:namespace />fm.<portlet:namespace />item_<%= i %>_active.value = '1'; } else { document.<portlet:namespace />fm.<portlet:namespace />item_<%= i %>_active.value = '0'; }">
							</td>
							<td width="30">
								&nbsp;
							</td>
							<td>
								<font class="portlet-font" style="font-size: xx-small;"><b><%= LanguageUtil.get(pageContext, "default") %></b></font>
							</td>
							<td width="5">
								&nbsp;
							</td>
							<td>
								<input <%= defaultPrice ? "checked" : "" %> name="<portlet:namespace />item_default_price" type="radio" value="<%= i %>" onClick="document.<portlet:namespace />fm.<portlet:namespace />item_<%= i %>_active.checked = true;">
							</td>

							<c:if test="<%= numberOfPrices > 1 %>">
								<td width="30">
									&nbsp;
								</td>
								<td>
									<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "delete") %>' onClick="<portlet:namespace />deletePrice(<%= i %>);">
								</td>
							</c:if>
						</tr>
						</table>
					</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td><img border="0" height="10" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
		</tr>
		<tr>
			<td class="gamma"><img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
		</tr>
		<tr>
			<td><img border="0" height="10" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
		</tr>

	<%
	}
	%>

	<tr>
		<td>
			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "add-price") %>' onClick="<portlet:namespace />addPrice();">
		</td>
	</tr>
	</table>
</liferay-ui:box>

<br>

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "images") %>' />

	<table border="0" cellpadding="0" cellspacing="0" width="90%">
	<tr>
		<td>
			<table border="0" cellpadding="0" cellspacing="2">

			<c:if test="<%= SessionErrors.contains(renderRequest, ItemSmallImageNameException.class.getName()) %>">
				<tr>
					<td>

						<%
						String[] imageExtensions = PropsUtil.getArray(PropsUtil.SHOPPING_IMAGE_EXTENSIONS);
						%>

						<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "image-names-must-end-with-one-of-the-following-extensions") %> <%= StringUtil.merge(imageExtensions, ", ") %>.</font>
					</td>
				</tr>
			</c:if>

			<c:if test="<%= SessionErrors.contains(renderRequest, ItemSmallImageSizeException.class.getName()) %>">
				<tr>
					<td>
						<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-file-with-a-valid-file-size") %></font>
					</td>
				</tr>
			</c:if>

			<tr>
				<td>

					<%
					String smallImageMaxSize = Integer.toString(GetterUtil.getInteger(PropsUtil.get(PropsUtil.SHOPPING_IMAGE_SMALL_MAX_SIZE)) / 1024);
					%>

					<input name="<portlet:namespace />item_small_image" type="hidden" value="<%= smallImage %>">

					<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td colspan="3">
							<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "small-image") %></b></font> &nbsp;<font class="portlet-font" style="font-size: xx-small;">(<%= LanguageUtil.format(pageContext, "upload-images-no-larger-than-x-k", smallImageMaxSize, false) %>)</font>
						</td>
					</tr>
					<tr>
						<td>
							<input class="form-text" name="<portlet:namespace />item_small_i" size="50" type="file">
						</td>
						<td width="30">
							&nbsp;
						</td>
						<td>
							<font class="portlet-font" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "use-small-image") %></font> <input <%= smallImage ? "checked" : "" %> type="checkbox" onClick="if (this.checked) { document.<portlet:namespace />fm.<portlet:namespace />item_small_image.value = '1'; } else { document.<portlet:namespace />fm.<portlet:namespace />item_small_image.value = '0'; }">
						</td>
					</tr>
					</table>

					<table border="0" cellpadding="0" cellspacing="2">
					<tr>
						<td nowrap>
							<font class="portlet-font" style="font-size: xx-small;"><b>-- <%= LanguageUtil.get(pageContext, "or").toUpperCase() %> --</b></font>
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td nowrap>
							<font class="portlet-font" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "small-image-url") %></font>
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td>
							<input class="form-text" name="<portlet:namespace />item_small_image_url" size="40" type="text" value="<%= smallImageURL %>">
						</td>
					</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<br>
				</td>
			</tr>

			<c:if test="<%= SessionErrors.contains(renderRequest, ItemMediumImageNameException.class.getName()) %>">
				<tr>
					<td>

						<%
						String[] imageExtensions = PropsUtil.getArray(PropsUtil.SHOPPING_IMAGE_EXTENSIONS);
						%>

						<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "image-names-must-end-with-one-of-the-following-extensions") %> <%= StringUtil.merge(imageExtensions, ", ") %>.</font>
					</td>
				</tr>
			</c:if>

			<c:if test="<%= SessionErrors.contains(renderRequest, ItemMediumImageSizeException.class.getName()) %>">
				<tr>
					<td>
						<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-file-with-a-valid-file-size") %></font>
					</td>
				</tr>
			</c:if>

			<tr>
				<td>

					<%
					String mediumImageMaxSize = Integer.toString(GetterUtil.getInteger(PropsUtil.get(PropsUtil.SHOPPING_IMAGE_MEDIUM_MAX_SIZE)) / 1024);
					%>

					<input name="<portlet:namespace />item_medium_image" type="hidden" value="<%= mediumImage %>">

					<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td colspan="3">
							<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "medium-image") %></b></font> &nbsp;<font class="portlet-font" style="font-size: xx-small;">(<%= LanguageUtil.format(pageContext, "upload-images-no-larger-than-x-k", mediumImageMaxSize, false) %>)</font>
						</td>
					</tr>
					<tr>
						<td>
							<input class="form-text" name="<portlet:namespace />item_medium_i" size="50" type="file">
						</td>
						<td width="30">
							&nbsp;
						</td>
						<td>
							<font class="portlet-font" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "use-medium-image") %></font> <input <%= mediumImage ? "checked" : "" %> type="checkbox" onClick="if (this.checked) { document.<portlet:namespace />fm.<portlet:namespace />item_medium_image.value = '1'; } else { document.<portlet:namespace />fm.<portlet:namespace />item_medium_image.value = '0'; }">
						</td>
					</tr>
					</table>

					<table border="0" cellpadding="0" cellspacing="2">
					<tr>
						<td nowrap>
							<font class="portlet-font" style="font-size: xx-small;"><b>-- <%= LanguageUtil.get(pageContext, "or").toUpperCase() %> --</b></font>
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td nowrap>
							<font class="portlet-font" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "medium-image-url") %></font>
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td>
							<input class="form-text" name="<portlet:namespace />item_medium_image_url" size="40" type="text" value="<%= mediumImageURL %>">
						</td>
					</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<br>
				</td>
			</tr>

			<c:if test="<%= SessionErrors.contains(renderRequest, ItemLargeImageNameException.class.getName()) %>">
				<tr>
					<td>

						<%
						String[] imageExtensions = PropsUtil.getArray(PropsUtil.SHOPPING_IMAGE_EXTENSIONS);
						%>

						<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "image-names-must-end-with-one-of-the-following-extensions") %> <%= StringUtil.merge(imageExtensions, ", ") %>.</font>
					</td>
				</tr>
			</c:if>

			<c:if test="<%= SessionErrors.contains(renderRequest, ItemLargeImageSizeException.class.getName()) %>">
				<tr>
					<td>
						<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-file-with-a-valid-file-size") %></font>
					</td>
				</tr>
			</c:if>

			<tr>
				<td>

					<%
					String largeImageMaxSize = Integer.toString(GetterUtil.getInteger(PropsUtil.get(PropsUtil.SHOPPING_IMAGE_LARGE_MAX_SIZE)) / 1024);
					%>

					<input name="<portlet:namespace />item_large_image" type="hidden" value="<%= largeImage %>">

					<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td colspan="3">
							<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "large-image") %></b></font> &nbsp;<font class="portlet-font" style="font-size: xx-small;">(<%= LanguageUtil.format(pageContext, "upload-images-no-larger-than-x-k", largeImageMaxSize, false) %>)</font>
						</td>
					</tr>
					<tr>
						<td>
							<input class="form-text" name="<portlet:namespace />item_large_i" size="50" type="file">
						</td>
						<td width="30">
							&nbsp;
						</td>
						<td>
							<font class="portlet-font" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "use-large-image") %></font> <input <%= largeImage ? "checked" : "" %> type="checkbox" onClick="if (this.checked) { document.<portlet:namespace />fm.<portlet:namespace />item_large_image.value = '1'; } else { document.<portlet:namespace />fm.<portlet:namespace />item_large_image.value = '0'; }">
						</td>
					</tr>
					</table>

					<table border="0" cellpadding="0" cellspacing="2">
					<tr>
						<td nowrap>
							<font class="portlet-font" style="font-size: xx-small;"><b>-- <%= LanguageUtil.get(pageContext, "or").toUpperCase() %> --</b></font>
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td nowrap>
							<font class="portlet-font" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "large-image-url") %></font>
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td>
							<input class="form-text" name="<portlet:namespace />item_large_image_url" size="40" type="text" value="<%= largeImageURL %>">
						</td>
					</tr>
					</table>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	</table>
</liferay-ui:box>

<br>

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_br_wrap_content" value="false" />

	<c:if test="<%= item == null %>">
		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "save") %>' onClick="<portlet:namespace />saveItem();">

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "save-and-add-another") %>' onClick="<portlet:namespace />saveAndAddAnotherItem();">
	</c:if>

	<c:if test="<%= item != null %>">
		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "update") %>' onClick="<portlet:namespace />updateItem();">

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "delete") %>' onClick="<portlet:namespace />deleteItem();">
	</c:if>

	<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="<portlet:namespace />cancel();">
</liferay-ui:box>

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />item_sku.focus();
</script>