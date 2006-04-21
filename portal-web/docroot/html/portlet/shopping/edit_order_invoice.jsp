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

DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);
df.setTimeZone(timeZone);
%>

<table bgcolor="<%= colorScheme.getPortletBg() %>" border="0" cellpadding="8" cellspacing="8" height="100%" width="100%">
<tr>
	<td valign="top">
		<table border="0" cellpadding="0" cellspacing="0" width="656">
		<tr>
			<td>
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td valign="top">
						<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="50"><a href="http://<%= company.getHomeURL() %>"><img border="0" hspace="0" src="<%= themeDisplay.getPathImage() %>/company_logo?img_id=<%= company.getCompanyId() %>" vspace="0"></a></td>
							<td width="5">
								&nbsp;
							</td>
							<td nowrap valign="bottom"><font class="gamma" size="4"><b><%= company.getName() %></b></font></td>
						</tr>
						<tr>
							<td colspan="3"><img border="0" height="5" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
						</tr>
						<tr>
							<td colspan="3"><font class="portlet-font" style="font-size: xx-small;"><%= company.getStreet() %><br><%= company.getCity() %>, <%= company.getState() %> <%= company.getZip() %></font></td>
						</tr>
						</table>
					</td>
					<td align="right" valign="top">
						<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<font face="Arial" size="6"><b>
								<%= LanguageUtil.get(pageContext, "invoice") %>
								</b></font>
							</td>
						</tr>
						</table>

						<table border="0" cellpadding="2" cellspacing="0" width="100%">
						<tr class="beta">
							<td align="right">
								<font class="beta" size="2"><b>
								<%= LanguageUtil.get(pageContext, "date") %>
								</b></font>
							</td>
							<td align="right">
								<font class="beta" size="2"><b>
								<%= LanguageUtil.get(pageContext, "order") %> #
								</b></font>
							</td>
						</tr>
						<tr>
							<td align="right">
								<font class="portlet-font" style="font-size: xx-small;"><b>
								<%= df.format(order.getCreateDate()) %>
								</b></font>
							</td>
							<td align="right">
								<font class="portlet-font" style="font-size: xx-small;"><b>
								<%= order.getOrderId() %>
								</b></font>
							</td>
						</tr>
						</table>
					</td>
				</tr>
				</table>

				<br><br>

				<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td colspan="2">
						<font class="portlet-font" style="font-size: x-small;"><b>
						<%= LanguageUtil.get(pageContext, "billing-address") %>
						</b></font>
					</td>
					<td colspan="2">
						<font class="portlet-font" style="font-size: x-small;"><b>
						<%= LanguageUtil.get(pageContext, "shipping-address") %>
						</b></font>
					</td>
				</tr>
				<tr>
					<td colspan="2" valign="top" width="50%">
						<font class="portlet-font" style="font-size: xx-small;">

						<%= order.getBillingFirstName() %> <%= order.getBillingLastName() %><br>

						<c:if test="<%= Validator.isNotNull(order.getBillingCompany()) %>">
							<%= order.getBillingCompany() %><br>
						</c:if>

						<%= order.getBillingStreet() %><br>
						<%= order.getBillingCity() %>, <%= order.getBillingState() %> <%= order.getBillingZip() %><br>
						<%= order.getBillingCountry() %><br>

						<br>

						<%= order.getBillingPhone() %><br>
						<%= order.getBillingEmailAddress() %>

						</font>
					</td>
					<td colspan="2" valign="top" width="50%">
						<font class="portlet-font" style="font-size: xx-small;">

						<%= order.getShippingFirstName() %> <%= order.getShippingLastName() %><br>

						<c:if test="<%= Validator.isNotNull(order.getShippingCompany()) %>">
							<%= order.getShippingCompany() %><br>
						</c:if>

						<%= order.getShippingStreet() %><br>
						<%= order.getShippingCity() %>, <%= order.getShippingState() %> <%= order.getShippingZip() %><br>
						<%= order.getShippingCountry() %><br>

						<br>

						<%= order.getShippingPhone() %><br>
						<%= order.getShippingEmailAddress() %>

						</font>
					</td>
				</tr>
				</table>

				<br><br>

				<table border="0" cellpadding="2" cellspacing="0" width="100%">
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
							<font class="portlet-font" style="font-size: xx-small;">

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
							<font class="portlet-font" style="font-size: xx-small;">
							<%= quantity %>
							</font>
						</td>
						<td valign="top">
							<font class="portlet-font" style="font-size: xx-small;">
							<%= currency.getSymbol() %><%= doubleFormat.format(orderItem.getPrice()) %>
							</font>
						</td>
						<td>
							<font class="portlet-font" style="font-size: xx-small;">
							<%= currency.getSymbol() %><%= doubleFormat.format(orderItem.getPrice() * quantity) %>
							</font>
						</td>
					</tr>

				<%
				}
				%>

				<tr>
					<td colspan="4"><img border="0" height="5" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
				</tr>
				<tr>
					<td colspan="2"></td>
					<td>
						<font class="portlet-font" style="font-size: xx-small;"><b>
						<%= LanguageUtil.get(pageContext, "subtotal") %>
						</b></font>
					</td>
					<td>
						<font class="portlet-font" style="font-size: xx-small;">
						<%= currency.getSymbol() %><%= doubleFormat.format(ShoppingUtil.calculateActualSubtotal(orderItems)) %>
						</font>
					</td>
				</tr>
				<tr>
					<td colspan="2"></td>
					<td>
						<font class="portlet-font" style="font-size: xx-small;"><b>
						<%= LanguageUtil.get(pageContext, "tax") %>
						</b></font>
					</td>
					<td>
						<font class="portlet-font" style="font-size: xx-small;">
						<%= currency.getSymbol() %><%= doubleFormat.format(order.getTax()) %>
						</font>
					</td>
				</tr>
				<tr>
					<td colspan="2"></td>
					<td>
						<font class="portlet-font" style="font-size: xx-small;"><b>
						<%= LanguageUtil.get(pageContext, "shipping") %>
						</b></font>
					</td>
					<td>
						<font class="portlet-font" style="font-size: xx-small;">
						<%= currency.getSymbol() %><%= doubleFormat.format(order.getShipping()) %>
						</font>
					</td>
				</tr>
				<tr>
					<td colspan="4"><img border="0" height="15" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
				</tr>
				<tr>
					<td colspan="2"></td>
					<td>
						<font class="portlet-font" style="font-size: small;"><b>
						<%= LanguageUtil.get(pageContext, "total") %>
						</b></font>
					</td>
					<td>
						<font class="portlet-font" style="font-size: small;"><b>
						<%= currency.getSymbol() %><%= doubleFormat.format(ShoppingUtil.calculateTotal(order)) %>
						</b></font>
					</td>
				</tr>
				</table>

				<%
				List notes = ShoppingOrderServiceUtil.getNotes(order.getOrderId());
				%>

				<c:if test="<%= notes.size() > 0 %>">
					<br><br>

					<table border="0" cellpadding="2" cellspacing="0" width="100%">
					<tr class="beta">
						<td>
							<font class="beta" size="2"><b>
							<%= LanguageUtil.get(pageContext, "additional-notes") %>
							</b></font>
						</td>
					</tr>
					<tr>
						<td>
							<table border="0" cellpadding="2" cellspacing="0" width="100%">

							<%
							DateFormat noteDF = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);
							noteDF.setTimeZone(timeZone);

							for (int i = 0; i < notes.size(); i++) {
								Note note = (Note)notes.get(i);
							%>

								<tr>
									<td><font class="portlet-font" style="font-size: xx-small;"><b>-</b></font></td>
									<td><font class="portlet-font" style="font-size: xx-small;"><b><%= noteDF.format(note.getCreateDate()) %> &gt;</b> <%= PortalUtil.getUserName(note.getUserId(), note.getUserName()) %></font></td>
								</tr>
								<tr>
									<td>
									</td>
									<td width="99%">
										<font class="portlet-font" style="font-size: xx-small;">
										<%= note.getContent() %>
										</font>
									</td>
								</tr>
								<tr>
									<td colspan="2"><img border="0" height="4" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
								</tr>

							<%
							}
							%>

							</table>
						</td>
					</tr>
					</table>
				</c:if>
			</td>
		</tr>
		</table>
	</td>
</tr>
</table>