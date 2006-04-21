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

Properties props = new OrderedProperties();

//props.load(new ByteArrayInputStream(item.getProperties().getBytes("ISO-8859-1")));
PropertiesUtil.load(props, item.getProperties());

Enumeration enu = props.propertyNames();

ShoppingItemField[] itemFields = (ShoppingItemField[])ShoppingItemFieldServiceUtil.getItemFields(item.getItemId()).toArray(new ShoppingItemField[0]);
ShoppingItemPrice[] itemPrices = (ShoppingItemPrice[])ShoppingItemPriceServiceUtil.getItemPrices(item.getItemId()).toArray(new ShoppingItemPrice[0]);

ShoppingItem[] prevAndNext = ShoppingItemServiceUtil.getItemsPrevAndNext(item.getItemId(), prefs);
%>

<script type="text/javascript">
	function <portlet:namespace />addToCart() {
		document.<portlet:namespace />fm.<portlet:namespace />item_fields.value = "";

		<%
		for (int i = 0; i < itemFields.length; i++) {
			ShoppingItemField itemField = itemFields[i];

			String fieldName = itemField.getName();
			String[] fieldValues = itemField.getValuesArray();
		%>

			if (document.<portlet:namespace />fm.<portlet:namespace />item_field_<%= fieldName %>.value == "") {
				alert("<%= UnicodeLanguageUtil.get(pageContext, "please-select-all-options") %>");

				return;
			}

			document.<portlet:namespace />fm.<portlet:namespace />item_fields.value = document.<portlet:namespace />fm.<portlet:namespace />item_fields.value + '<%= fieldName %>=' + document.<portlet:namespace />fm.<portlet:namespace />item_field_<%= fieldName %>.value + '&';

		<%
		}
		%>

		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/cart" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace />redirect" type="hidden" value="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/cart" /></portlet:actionURL>">
<input name="<portlet:namespace />shopping_<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>">
<input name="<portlet:namespace />item_id" type="hidden" value="<%= item.getItemId() %>">
<input name="<portlet:namespace />item_fields" type="hidden" value="">

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "item-information") %>' />

	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="3">
					<font class="portlet-font" style="font-size: x-small;">

					<a href="<portlet:renderURL><portlet:param name="struts_action" value="/shopping/browse_categories" /></portlet:renderURL>"><%= LanguageUtil.get(pageContext, "browse-categories") %></a>

					<%
					List parentCategories = ShoppingCategoryServiceUtil.getParentCategories(item.getCategoryId());

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
				<td colspan="3">
					<br>
				</td>
			</tr>
			<tr>
				<td>
					<font class="portlet-font" style="font-size: xx-small;">[&nbsp;<b><%= item.getSku() %></b>&nbsp;]</font>
				</td>
				<td></td>
				<td>
					<font class="portlet-font" style="font-size: small;"><b>
					<%= item.getName() %>
					</b></font>

					<c:if test="<%= shoppingAdmin %>">
						&nbsp;<font class="portlet-font" style="font-size: xx-small;">[<a href="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_item" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EDIT %>" /><portlet:param name="item_id" value="<%= item.getItemId() %>" /></portlet:actionURL>"><%= LanguageUtil.get(pageContext, "edit") %></a>]</font>
					</c:if>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<br>
				</td>
			</tr>
			<tr>
				<td align="center" nowrap valign="top">
					<c:if test="<%= item.isMediumImage() %>">
						<img border="0" hspace="0" src="<%= Validator.isNotNull(item.getMediumImageURL()) ? item.getMediumImageURL() : themeDisplay.getPathImage() + "/shopping/item?img_id=" + item.getItemId() + "&medium=1" %>" vspace="0"><br>
					</c:if>

					<c:if test="<%= item.isLargeImage() %>">
						<font class="portlet-font" style="font-size: xx-small;"><a href="<%= Validator.isNotNull(item.getLargeImageURL()) ? item.getLargeImageURL() : themeDisplay.getPathImage() + "/shopping/item?img_id=" + item.getItemId() + "&large=1" %>" target="_blank"><%= LanguageUtil.get(pageContext, "see-large-photo") %></a></font>
					</c:if>
				</td>
				<td><img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="20"></td>
				<td valign="top" width="99%">
					<font class="portlet-font" style="font-size: x-small;">

					<%= item.getDescription() %><br>

					<br>

					<%
					while (enu.hasMoreElements()) {
						String propsKey = (String)enu.nextElement();
						String propsValue = props.getProperty(propsKey, StringPool.BLANK);
					%>

						<%= propsKey %>: <%= propsValue %><br>

					<%
					}
					%>

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

					<c:if test="<%= GetterUtil.getBoolean(CompanyPropsUtil.get(company.getCompanyId(), PropsUtil.SHOPPING_ITEM_SHOW_AVAILABILITY)) %>">
						<c:choose>
							<c:when test="<%= ShoppingUtil.isInStock(item) %>">
								<%= LanguageUtil.get(pageContext, "availability") %>: <font class="portlet-msg-success" style="font-size: x-small;"><%= LanguageUtil.get(pageContext, "in-stock") %></font><br>
							</c:when>
							<c:otherwise>
								<%= LanguageUtil.get(pageContext, "availability") %>: <font class="portlet-msg-error" style="font-size: x-small;"><%= LanguageUtil.get(pageContext, "out-of-stock") %></font><br>
							</c:otherwise>
						</c:choose>

						<br>
					</c:if>

					</font>

					<%
					for (int i = 0; i < itemFields.length; i++) {
						ShoppingItemField itemField = itemFields[i];

						String fieldName = itemField.getName();
						String[] fieldValues = itemField.getValuesArray();
						String fieldDescription = itemField.getDescription();
					%>

						<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td><font class="portlet-font" style="font-size: x-small;"><%= fieldName %>:</font></td>
							<td width="10">
								&nbsp;
							</td>
							<td>
								<select name="<portlet:namespace />item_field_<%= fieldName %>">
									<option value=""><%= LanguageUtil.get(pageContext, "select-option") %></option>

									<%
									for (int j = 0; j < fieldValues.length; j++) {
									%>

										<option value="<%= fieldValues[j] %>"><%= fieldValues[j] %></option>

									<%
									}
									%>

								</select>
							</td>

							<c:if test="<%= Validator.isNotNull(fieldDescription) %>">
								<td width="10">
									&nbsp;
								</td>
								<td><font class="portlet-font" style="font-size: x-small;"><%= fieldDescription %></font></td>
							</c:if>

						</tr>
						</table>

						<br>

					<%
					}
					%>

					<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "add-to-shopping-cart") %>' onClick="<portlet:namespace />addToCart();">
						</td>
					</tr>
					</table>

					<c:if test="<%= (prevAndNext[0] != null) || (prevAndNext[2] != null) %>">
						<br>

						<table border="0" cellpadding="0" cellspacing="0">
						<tr>

							<c:if test="<%= prevAndNext[0] != null %>">
								<td>
									<font class="portlet-font" style="font-size: x-small;"><b><a href="<portlet:renderURL><portlet:param name="struts_action" value="/shopping/view_item" /><portlet:param name="item_id" value="<%= prevAndNext[0].getItemId() %>" /></portlet:renderURL>">&#171;&nbsp; <%= LanguageUtil.get(pageContext, "previous") %></a></b></font>
								</td>
							</c:if>

							<c:if test="<%= (prevAndNext[0] != null) && (prevAndNext[2] != null) %>">
								<td>
									<font class="portlet-font" style="font-size: x-small;">&nbsp;&nbsp;|&nbsp;&nbsp;</font>
								</td>
							</c:if>

							<c:if test="<%= prevAndNext[2] != null %>">
								<td>
									<font class="portlet-font" style="font-size: x-small;"><b><a href="<portlet:renderURL><portlet:param name="struts_action" value="/shopping/view_item" /><portlet:param name="item_id" value="<%= prevAndNext[2].getItemId() %>" /></portlet:renderURL>"><%= LanguageUtil.get(pageContext, "next") %> &nbsp;&#187;</a></b></font>
								</td>
							</c:if>

						</tr>
						</table>
					</c:if>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	</table>
</liferay-ui:box>

</form>