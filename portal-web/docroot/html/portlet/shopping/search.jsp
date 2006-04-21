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
String shoppingCMD = ParamUtil.getString(request, "shopping_" + Constants.CMD);

String[] selCategoryId = StringUtil.split(ParamUtil.getString(request, "shopping_sel_category_id"));
String keywords = ParamUtil.getString(request, "keywords");
%>

<c:if test="<%= shoppingCMD.equals(Constants.SEARCH) %>">
	<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
		<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "search-results") %>' />

		<%
		int curValue = ParamUtil.get(request, "cur", 1);
		int delta = 10;

		int itemsStart = (curValue - 1) * delta;
		int itemsEnd = itemsStart + delta;

		ShoppingItem[] items = null;
		int itemsSize = 0;

		if (Validator.isNotNull(keywords)) {
			items = (ShoppingItem[])ShoppingItemServiceUtil.search(company.getCompanyId(), selCategoryId, keywords, itemsStart, itemsEnd).toArray(new ShoppingItem[0]);
			itemsSize = ShoppingItemServiceUtil.searchSize(company.getCompanyId(), selCategoryId, keywords);
		}

		PortletURL pageIteratorURL = renderResponse.createRenderURL();

		pageIteratorURL.setParameter("struts_action", "/shopping/search");
		pageIteratorURL.setParameter("shopping_" + Constants.CMD, Constants.SEARCH);
		pageIteratorURL.setParameter("keywords", keywords);

		String pageIteratorURLToString = Http.decodeURL(pageIteratorURL.toString());
		%>

		<c:if test="<%= itemsSize > delta %>">
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td>
					<%--
					<liferay-ui:page-iterator className="gamma" curParam='<%= renderResponse.getNamespace() + "cur" %>' curValue="<%= curValue %>" delta="<%= delta %>" fontSize="2" maxPages="10" total="<%= itemsSize %>" url="<%= pageIteratorURLToString %>" />
					--%>
				</td>
			</tr>
			</table>

			<br>
		</c:if>

		<table border="0" cellpadding="0" cellspacing="0" width="100%">

		<c:if test="<%= items != null && items.length > 0 %>">
			<tr>
				<td>
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td></td>
						<td><img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="10"></td>
						<td>
							<font class="portlet-font" style="font-size: x-small;"><b>
							<%= LanguageUtil.get(pageContext, "sku") %>
							</b></font>
						</td>
						<td><img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="10"></td>
						<td width="99%">
							<font class="portlet-font" style="font-size: x-small;"><b>
							<%= LanguageUtil.get(pageContext, "item-description") %>
							</b></font>
						</td>
						<td><img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="10"></td>
						<td nowrap>
							<font class="portlet-font" style="font-size: x-small;"><b>
							<%= LanguageUtil.get(pageContext, "min-qty") %>
							</b></font>
						</td>
						<td><img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="10"></td>
						<td>
							<font class="portlet-font" style="font-size: x-small;"><b>
							<%= LanguageUtil.get(pageContext, "price") %>
							</b></font>
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

					// Cart URL

					PortletURL cartURL = renderResponse.createActionURL();

					cartURL.setParameter("struts_action", "/shopping/cart");

					String cartURLToString = cartURL.toString();

					// Search with current position URL

					PortletURL searchCurURL = renderResponse.createRenderURL();

					searchCurURL.setParameter("struts_action", "/shopping/search");
					searchCurURL.setParameter("shopping_" + Constants.CMD, Constants.SEARCH);
					searchCurURL.setParameter("keywords", keywords);
					searchCurURL.setParameter("cur", Integer.toString(curValue));

					String searchCurURLToString = searchCurURL.toString();

					// Add to cart redirect

					String addToCartRedirect = null;

					if (GetterUtil.getBoolean(CompanyPropsUtil.get(company.getCompanyId(), PropsUtil.SHOPPING_CATEGORY_FORWARD_TO_CART))) {
						addToCartRedirect = Http.encodeURL(cartURLToString);
					}
					else {
						addToCartRedirect = Http.encodeURL(searchCurURL.toString());
					}

					boolean showItemDescription = GetterUtil.getBoolean(CompanyPropsUtil.get(company.getCompanyId(), PropsUtil.SHOPPING_CATEGORY_SHOW_ITEM_DESCRIPTION));
					boolean showItemProperties = GetterUtil.getBoolean(CompanyPropsUtil.get(company.getCompanyId(), PropsUtil.SHOPPING_CATEGORY_SHOW_ITEM_PROPERTIES));
					boolean showItemPrices = GetterUtil.getBoolean(CompanyPropsUtil.get(company.getCompanyId(), PropsUtil.SHOPPING_CATEGORY_SHOW_ITEM_PRICES));

					for (int i = 0; i < items.length; i++) {
						ShoppingItem item = (ShoppingItem)items[i];

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
				</td>
			</tr>
		</c:if>

		<c:if test="<%= items == null || items.length == 0 %>">
			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;">
					<%= LanguageUtil.get(pageContext, "no-items-were-found-that-matched-the-keywords") %> <b><%= request.getParameter("keywords") %></b>.
					</font>
				</td>
			</tr>
		</c:if>

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
	</liferay-ui:box>
</c:if>