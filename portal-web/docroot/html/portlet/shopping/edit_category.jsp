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
ShoppingCategory category = (ShoppingCategory)request.getAttribute(WebKeys.SHOPPING_CATEGORY);

List subCategories = ShoppingCategoryServiceUtil.getCategories(category);

String name = request.getParameter("category_name");
if ((name == null) || (name.equals(StringPool.NULL))) {
	name = category.getName();
}
%>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_category" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="submitForm(this); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>">
<input name="<portlet:namespace />redirect" type="hidden" value="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_category" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EDIT %>" /><portlet:param name="category_id" value="<%= category.getCategoryId() %>" /></portlet:actionURL>">
<input name="<portlet:namespace />category_id" type="hidden" value="<%= category.getCategoryId() %>">
<input name="<portlet:namespace />parent_category_id" type="hidden" value="<%= category.getParentCategoryId() %>">

<%

// Browse categories URL

PortletURL categoriesURL = renderResponse.createRenderURL();

categoriesURL.setParameter("struts_action", "/shopping/browse_categories");

// Browse parent category

PortletURL categoryURL = renderResponse.createActionURL();

categoryURL.setParameter("struts_action", "/shopping/edit_category");
categoryURL.setParameter(Constants.CMD, Constants.EDIT);

String boxTitle = ShoppingUtil.getNavCategories(request, pageContext, "gamma", categoriesURL, categoryURL);
%>

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value="<%= boxTitle %>" />
	<liferay-util:param name="box_bold_title" value="false" />

	<c:if test="<%= shoppingAdmin %>">
		<table border="0" cellpadding="0" cellspacing="0">

		<c:if test='<%= SessionMessages.contains(renderRequest, "category_updated") %>'>
			<tr>
				<td colspan="3">
					<font class="portlet-msg-success" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "you-have-successfully-updated-the-category-name") %></font>
				</td>
			</tr>
		</c:if>

		<tr>
			<td>
				<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "category-id") %></b></font>
			</td>
			<td width="10">
				&nbsp;
			</td>
			<td>
				<font class="portlet-font" style="font-size: x-small;">
				<%= category.getCategoryId() %>
				</font>
			</td>
		</tr>

		<c:if test="<%= SessionErrors.contains(renderRequest, CategoryNameException.class.getName()) %>">
			<tr>
				<td colspan="3">
					<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-category-name") %></font>
				</td>
			</tr>
		</c:if>

		<tr>
			<td>
				<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "category-name") %></b></font>
			</td>
			<td width="10">
				&nbsp;
			</td>
			<td>
				<input class="form-text" name="<portlet:namespace />category_name" type="text" value="<%= name %>">
			</td>
		</tr>
		</table>

		<br>

		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "update-category") %>' onClick="submitForm(document.<portlet:namespace />fm);">

				<input class="portlet-form-button" type="button" value="<%= LanguageUtil.get(pageContext, "delete-category") %>"
					onClick="
						if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-selected-category-and-all-its-items") %>')) {
							document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.DELETE %>';

							<c:if test="<%= category.getParentCategoryId().equals(ShoppingCategory.DEFAULT_PARENT_CATEGORY_ID) %>">
								document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:renderURL><portlet:param name="struts_action" value="/shopping/browse_categories" /></portlet:renderURL>';
							</c:if>

							<c:if test="<%= !category.getParentCategoryId().equals(ShoppingCategory.DEFAULT_PARENT_CATEGORY_ID) %>">
								document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_category" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EDIT %>" /><portlet:param name="category_id" value="<%= category.getParentCategoryId() %>" /></portlet:actionURL>';
							</c:if>

							submitForm(document.<portlet:namespace />fm);
						}"
				>
			</td>
		</tr>
		<tr>
			<td><img border="0" height="3" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
		</tr>
		<tr>
			<td>
				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "add-child-category") %>' onClick="var childCategoryWindow = window.open('<portlet:actionURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/shopping/edit_category" /><portlet:param name="parent_category_id" value="<%= category.getCategoryId() %>" /></portlet:actionURL>', 'childCategory', 'directories=no,height=110,location=no,menubar=no,resizable=no,scrollbars=no,status=no,toolbar=no,width=280'); void(''); childCategoryWindow.focus();">

				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "add-item") %>' onClick="self.location = '<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_item" /><portlet:param name="category_id" value="<%= category.getCategoryId() %>" /></portlet:actionURL>';">

				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "quick-add") %>' onClick="self.location = '<portlet:actionURL><portlet:param name="struts_action" value="/shopping/quick_add_items" /><portlet:param name="category_id" value="<%= category.getCategoryId() %>" /></portlet:actionURL>';">
			</td>
		</tr>
		</table>

		<br>
	</c:if>

	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
			<c:if test="<%= subCategories.size() > 0 %>">
				<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
					<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "browse") + " " + category.getName() %>' />
					<liferay-util:param name="box_width" value="<%= Integer.toString((int)(themeDisplay.getResolution() * .95)) %>" />
					<liferay-util:param name="box_bold_title" value="false" />

					<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td align="right">
							<c:if test="<%= subCategories.size() < 5 %>">
								<table border="0" cellpadding="0" cellspacing="0" width="100%">
								<tr>
									<td>

										<%
										for (int i = 0; i < subCategories.size(); i++) {
											ShoppingCategory subCategory = (ShoppingCategory)subCategories.get(i);
										%>

											<li>
												<font class="portlet-font" style="font-size: x-small;"><a href="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_category" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EDIT %>" /><portlet:param name="category_id" value="<%= subCategory.getCategoryId() %>" /></portlet:actionURL>">
												<%= subCategory.getName() %>
												</a></font>
											</li>

										<%
										}
										%>

									</td>
									<td width="1%">
									</td>
								</tr>
								</table>
							</c:if>

							<c:if test="<%= subCategories.size() >= 5 %>">
								<table border="0" cellpadding="0" cellspacing="0" width="100%">
								<tr>
									<td valign="top" width="33%">

										<%
										boolean division = false;
										int div = subCategories.size() / 3;
										int mod = subCategories.size() % 3;
										int pos = 0;

										int div1;
										int div2;

										if (mod == 1) {
											div1 = div + 1;
											div2 = div + div + 1;
										}
										else if (mod == 2) {
											div1 = div + 1;
											div2 = div + div + 1 + 1;
										}
										else {
											div1 = div;
											div2 = div + div;
										}

										for (int i = 0; i < subCategories.size(); i++) {
											ShoppingCategory subCategory = (ShoppingCategory)subCategories.get(i);

											if (division) {
										%>

												</td>
												<td valign="top" width="33%">

										<%
												division = false;
											}
										%>

											<li>
												<font class="portlet-font" style="font-size: x-small;"><a href="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_category" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EDIT %>" /><portlet:param name="category_id" value="<%= subCategory.getCategoryId() %>" /></portlet:actionURL>">
												<%= subCategory.getName() %>
												</a></font>
											</li>

									<%
										pos++;

										if ((pos == div1) || (pos == div2)) {
											division = true;
										}
									}
									%>

									</td>
									<td width="1%">
									</td>
								</tr>
								</table>
							</c:if>
						</td>
					</tr>
					</table>
				</liferay-ui:box>
			</c:if>

			<%
			int curValue = ParamUtil.get(request, "cur", 1);
			int delta = 10;
			int itemsSize = ShoppingItemServiceUtil.getItemsSize(category.getCompanyId(), category.getCategoryId());

			int itemsStart = (curValue - 1) * delta;
			int itemsEnd = itemsStart + delta;

			PortletURL pageIteratorURL = renderResponse.createActionURL();

			pageIteratorURL.setParameter("struts_action", "/shopping/edit_category");
			pageIteratorURL.setParameter(Constants.CMD, Constants.EDIT);
			pageIteratorURL.setParameter("category_id", category.getCategoryId());

			String pageIteratorURLToString = Http.decodeURL(pageIteratorURL.toString());
			%>

			<c:if test="<%= itemsSize > delta %>">
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td>
						<br>

						<%--
						<liferay-ui:page-iterator className="gamma" curParam='<%= renderResponse.getNamespace() + "cur" %>' curValue="<%= curValue %>" delta="<%= delta %>" fontSize="2" maxPages="10" total="<%= itemsSize %>" url="<%= pageIteratorURLToString %>" />
						--%>
					</td>
				</tr>
				</table>
			</c:if>

			<%
			ShoppingItem[] items = (ShoppingItem[])ShoppingItemServiceUtil.getItems(company.getCompanyId(), category.getCategoryId(), itemsStart, itemsEnd, prefs).toArray(new ShoppingItem[0]);

			// Cart URL

			PortletURL cartURL = renderResponse.createActionURL();

			cartURL.setParameter("struts_action", "/shopping/cart");

			String cartURLToString = cartURL.toString();

			// Category with current position URL

			PortletURL categoryCurURL = renderResponse.createActionURL();

			categoryCurURL.setParameter("struts_action", "/shopping/edit_category");
			categoryCurURL.setParameter(Constants.CMD, Constants.EDIT);
			categoryCurURL.setParameter("category_id", category.getCategoryId());
			categoryCurURL.setParameter("cur", Integer.toString(curValue));

			String categoryCurURLToString = categoryCurURL.toString();

			// Add to cart redirect

			String addToCartRedirect = null;

			if (GetterUtil.getBoolean(CompanyPropsUtil.get(company.getCompanyId(), PropsUtil.SHOPPING_CATEGORY_FORWARD_TO_CART))) {
				addToCartRedirect = Http.encodeURL(cartURLToString);
			}
			else {
				addToCartRedirect = Http.encodeURL(categoryCurURL.toString());
			}
			%>

			<c:if test="<%= items.length > 0 %>">

				<%
				PortletURL changeOrderURL = renderResponse.createActionURL();

				changeOrderURL.setParameter("struts_action", "/shopping/change_order");
				changeOrderURL.setParameter("redirect", categoryCurURLToString);

				String changeOrderURLToString = changeOrderURL.toString();
				%>

				<br>

				<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td valign="top">
						<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td></td>
							<td><img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="10"></td>
							<td>
								<font class="portlet-font" style="font-size: x-small;"><b><%= orderByCol.equals("s") ? "<i>" : ""  %><a href="<%= changeOrderURLToString %>&<portlet:namespace />category_id=<%= category.getCategoryId() %>&<portlet:namespace />order_by_col=s"><%= LanguageUtil.get(pageContext, "sku") %></a><%= orderByCol.equals("s") ? "</i>" : ""  %></b></font>
							</td>
							<td><img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="20"></td>
							<td width="99%">
								<font class="portlet-font" style="font-size: x-small;"><b><%= orderByCol.equals("n") ? "<i>" : ""  %><a href="<%= changeOrderURLToString %>&<portlet:namespace />category_id=<%= category.getCategoryId() %>&<portlet:namespace />order_by_col=n"><%= LanguageUtil.get(pageContext, "item-description") %></a><%= orderByCol.equals("n") ? "</i>" : ""  %></b></font>
							</td>
							<td><img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="10"></td>
							<td nowrap>
								<font class="portlet-font" style="font-size: x-small;"><b><%= orderByCol.equals("min") ? "<i>" : ""  %><a href="<%= changeOrderURLToString %>&<portlet:namespace />category_id=<%= category.getCategoryId() %>&<portlet:namespace />order_by_col=min"><%= LanguageUtil.get(pageContext, "min-qty") %></a><%= orderByCol.equals("min") ? "</i>" : ""  %></b></font>
							</td>
							<td><img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="10"></td>
							<td>
								<font class="portlet-font" style="font-size: x-small;"><b><%= orderByCol.equals("p") ? "<i>" : ""  %><a href="<%= changeOrderURLToString %>&<portlet:namespace />category_id=<%= category.getCategoryId() %>&<portlet:namespace />order_by_col=p"><%= LanguageUtil.get(pageContext, "price") %></a><%= orderByCol.equals("p") ? "</i>" : ""  %></b></font>
							</td>
							<td><img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="10"></td>
							<td></td>
						</tr>
						<tr>
							<td colspan="11">
								<br>
							</td>
						</tr>

						<%
						boolean showItemDescription = GetterUtil.getBoolean(CompanyPropsUtil.get(company.getCompanyId(), PropsUtil.SHOPPING_CATEGORY_SHOW_ITEM_DESCRIPTION));
						boolean showItemProperties = GetterUtil.getBoolean(CompanyPropsUtil.get(company.getCompanyId(), PropsUtil.SHOPPING_CATEGORY_SHOW_ITEM_PROPERTIES));
						boolean showItemPrices = GetterUtil.getBoolean(CompanyPropsUtil.get(company.getCompanyId(), PropsUtil.SHOPPING_CATEGORY_SHOW_ITEM_PRICES));

						for (int i = 0; i < items.length; i++) {
							ShoppingItem item = items[i];

							Properties props = new OrderedProperties();

							PropertiesUtil.load(props, item.getProperties());

							Enumeration enu = props.propertyNames();
						%>

							<tr>
								<td valign="top">
									<table border="0" cellpadding="2" cellspacing="0">
									<tr>
										<td><font class="portlet-font" style="font-size: xx-small;"><b><%= itemsStart + i + 1 %>.</b></font></td>
									</tr>
									</table>
								</td>
								<td></td>
								<td nowrap valign="top">
									<table border="0" cellpadding="2" cellspacing="0">
									<tr>
										<td><font class="portlet-font" style="font-size: xx-small;">[&nbsp;<b><%= item.getSku() %></b>&nbsp;]</font></td>
									</tr>
									</table>

									<table border="0" cellpadding="2" cellspacing="0">
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
									<font class="portlet-font" style="font-size: x-small;"><b><a href="<portlet:renderURL><portlet:param name="struts_action" value="/shopping/view_item" /><portlet:param name="item_id" value="<%= item.getItemId() %>" /></portlet:renderURL>"><%= item.getName() %></b></a></font>

									<c:if test="<%= shoppingAdmin %>">
										&nbsp;<font class="portlet-font" style="font-size: xx-small;">[<a href="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_item" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EDIT %>" /><portlet:param name="item_id" value="<%= item.getItemId() %>" /></portlet:actionURL>"><%= LanguageUtil.get(pageContext, "edit") %></a>]</font>
									</c:if>

									<br>

									<c:if test="<%= showItemDescription %>">
										<font class="portlet-font" style="font-size: x-small;">
										<%= item.getDescription() %><br>
										</font>

										<br>
									</c:if>

									<font class="portlet-font" style="font-size: x-small;">

									<%
									while (showItemProperties && enu.hasMoreElements()) {
										String propsKey = (String)enu.nextElement();
										String propsValue = props.getProperty(propsKey, StringPool.BLANK);
									%>

										<%= propsKey %>: <%= propsValue %><br>

									<%
									}
									%>

									</font>
								</td>
								<td></td>
								<td align="right" nowrap valign="top">
									<font class="portlet-font" style="font-size: x-small;">
									<%= item.getMinQuantity() %>
									</font>
								</td>
								<td></td>
								<td align="right" nowrap valign="top">
									<font class="portlet-font" style="font-size: x-small;">

									<c:if test="<%= item.getDiscount() <= 0 %>">
										<%= currency.getSymbol() %><%= doubleFormat.format(item.getPrice()) %><br>
									</c:if>

									<c:if test="<%= item.getDiscount() > 0 %>">
										<font class="portlet-msg-success" style="font-size: x-small;"><%= currency.getSymbol() %><%= doubleFormat.format(ShoppingUtil.calculateActualPrice(item)) %></font><br>
									</c:if>

									</font>
								</td>
								<td></td>
								<td align="right" nowrap valign="top">
									<table border="0" cellpadding="2" cellspacing="0">
									<tr>
										<td><font class="portlet-font" style="font-size: xx-small;">[<a href="<c:if test="<%= item.isFields() %>"><portlet:renderURL><portlet:param name="struts_action" value="/shopping/view_item" /><portlet:param name="item_id" value="<%= item.getItemId() %>" /></portlet:renderURL></c:if><c:if test="<%= !item.isFields() %>"><%= cartURLToString %>&<portlet:namespace />redirect=<%= addToCartRedirect %>&<portlet:namespace />shopping_<%= Constants.CMD %>=<%= Constants.ADD %>&<portlet:namespace />item_id=<%= item.getItemId() %></c:if>"><%= LanguageUtil.get(pageContext, "add") %></a>]</font></td>
									</tr>
									</table>
								</td>
							</tr>

							<c:if test="<%= i + 1 < items.length %>">
								<tr>
									<td colspan="11">
										<br>
									</td>
								</tr>
							</c:if>

						<%
						}
						%>

						</table>

						<c:if test="<%= itemsSize > delta %>">
							<br>

							<table border="0" cellpadding="0" cellspacing="0" width="100%">
							<tr>
								<td>
									<%--
									<liferay-ui:page-iterator className="gamma" curParam='<%= renderResponse.getNamespace() + "cur" %>' curValue="<%= curValue %>" delta="<%= delta %>" fontSize="2" maxPages="10" total="<%= itemsSize %>" url="<%= pageIteratorURLToString %>" />
									--%>
								</td>
							</tr>
							</table>
						</c:if>
					</td>
					<td><img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="30"></td>

					<c:if test="<%= GetterUtil.getBoolean(CompanyPropsUtil.get(company.getCompanyId(), PropsUtil.SHOPPING_CATEGORY_SHOW_SPECIAL_ITEMS)) %>">

						<%
						List featuredItems = ShoppingItemServiceUtil.getFeaturedItems(category.getCompanyId(), category.getCategoryId(), 5);
						List saleItems = ShoppingItemServiceUtil.getSaleItems(category.getCompanyId(), category.getCategoryId(), 5);
						%>

						<c:if test="<%= (featuredItems.size() > 0) || (saleItems.size() > 0) %>">
							<td valign="top" width="30%">

								<%
								List specialItems = featuredItems;
								String specialItemsTitle = LanguageUtil.get(pageContext, "featured-items");
								String specialItemsWidth = Integer.toString((int)(themeDisplay.getResolution() * .95 * .33));

								boolean showSpecialItemDescription = GetterUtil.getBoolean(CompanyPropsUtil.get(company.getCompanyId(), PropsUtil.SHOPPING_CATEGORY_SHOW_SPECIAL_ITEM_DESCRIPTION));
								%>

								<c:if test="<%= specialItems.size() > 0 %>">
									<%@ include file="/html/portlet/shopping/special_items.jsp" %>

									<br>
								</c:if>

								<%
								specialItems = saleItems;
								specialItemsTitle = LanguageUtil.get(pageContext, "sale-items");
								%>

								<c:if test="<%= specialItems.size() > 0 %>">
									<%@ include file="/html/portlet/shopping/special_items.jsp" %>
								</c:if>
							</td>
						</c:if>
					</c:if>
				</tr>
				</table>
			</c:if>

			<c:if test="<%= items.length == 0 %>">
				<c:if test="<%= GetterUtil.getBoolean(CompanyPropsUtil.get(company.getCompanyId(), PropsUtil.SHOPPING_CATEGORY_SHOW_SPECIAL_ITEMS)) %>">

					<%
					List featuredItems = ShoppingItemServiceUtil.getFeaturedItems(category.getCompanyId(), category.getCategoryId(), 5);
					List saleItems = ShoppingItemServiceUtil.getSaleItems(category.getCompanyId(), category.getCategoryId(), 5);
					%>

					<c:if test="<%= (featuredItems.size() > 0) || (saleItems.size() > 0) %>">
						<br>

						<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td valign="top" width="50%">

								<%
								List specialItems = featuredItems;
								String specialItemsTitle = LanguageUtil.get(pageContext, "featured-items");
								String specialItemsWidth = Integer.toString((int)(themeDisplay.getResolution() * .95 * .50));

								boolean showSpecialItemDescription = GetterUtil.getBoolean(CompanyPropsUtil.get(company.getCompanyId(), PropsUtil.SHOPPING_CATEGORY_SHOW_SPECIAL_ITEM_DESCRIPTION));
								%>

								<c:if test="<%= specialItems.size() > 0 %>">
									<%@ include file="/html/portlet/shopping/special_items.jsp" %>
								</c:if>
							</td>
							<td><img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="5"></td>
							<td valign="top" width="50%">

								<%
								specialItems = saleItems;
								specialItemsTitle = LanguageUtil.get(pageContext, "sale-items");
								%>

								<c:if test="<%= specialItems.size() > 0 %>">
									<%@ include file="/html/portlet/shopping/special_items.jsp" %>
								</c:if>
							</td>
						</tr>
						</table>
					</c:if>
				</c:if>
			</c:if>
		</td>
	</tr>
	</table>
</liferay-ui:box>

</form>

<c:if test="<%= shoppingAdmin %>">
	<script type="text/javascript">
		document.<portlet:namespace />fm.<portlet:namespace />category_name.focus();
	</script>
</c:if>