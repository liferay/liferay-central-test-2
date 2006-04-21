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
String ppPaymentStatus = ParamUtil.getString(request, "order_ppps");
String view = ParamUtil.get(request, "view", "all");

int curValue = ParamUtil.get(request, "cur", 1);
int delta = 20;

int ordersStart = (curValue - 1) * delta;
int ordersEnd = ordersStart + delta;

List orders = null;
int ordersSize = 0;

if (!shoppingAdmin || view.equals("personal")) {
	orders = ShoppingOrderServiceUtil.getOrders(ppPaymentStatus, ordersStart, ordersEnd);
	ordersSize = ShoppingOrderServiceUtil.getOrdersSize(ppPaymentStatus);
}
else {
	orders = ShoppingOrderServiceUtil.getOrders(company.getCompanyId(), ppPaymentStatus, ordersStart, ordersEnd);
	ordersSize = ShoppingOrderServiceUtil.getOrdersSize(company.getCompanyId(), ppPaymentStatus);
}

DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);
df.setTimeZone(timeZone);
%>

<form action="<portlet:renderURL><portlet:param name="struts_action" value="/shopping/view_orders" /></portlet:renderURL>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace />order_ppps" type="hidden" value="">
<input name="<portlet:namespace />view" type="hidden" value="<%= view %>">

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "orders") %>' />

	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<select onChange="document.<portlet:namespace />fm.<portlet:namespace />order_ppps.value = this[this.selectedIndex].value; submitForm(document.<portlet:namespace />fm);">
						<option <%= ppPaymentStatus.equals("") ? "selected" : "" %> value=""><%= LanguageUtil.get(pageContext, "all-orders") %></option>

						<%
						for (int i = 0; i < ShoppingOrder.STATUSES.length; i++) {
						%>

							<option <%= ShoppingOrder.STATUSES[i].equals(ppPaymentStatus) ? "selected" : "" %> value="<%= ShoppingOrder.STATUSES[i] %>"><%= LanguageUtil.get(pageContext, ShoppingOrder.STATUSES[i]) %></option>

						<%
						}
						%>

					</select>
				</td>
				<td width="30"><img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="30"></td>
				<td>
					<c:if test="<%= shoppingAdmin %>">
						<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<font class="portlet-font" style="font-size: x-small;">

								<b><%= LanguageUtil.get(pageContext, "view") %>:</b>&nbsp;&nbsp;

								<c:if test='<%= view.equals("all") %>'>
									<i><%= LanguageUtil.get(pageContext, "all") %>&nbsp;</i>
								</c:if>

								<c:if test='<%= !view.equals("all") %>'>
									<a href="<portlet:renderURL><portlet:param name="struts_action" value="/shopping/view_orders" /><portlet:param name="order_ppps" value="<%= ppPaymentStatus %>" /><portlet:param name="view" value="all" /></portlet:renderURL>"><%= LanguageUtil.get(pageContext, "all") %></a>
								</c:if>

								&nbsp;|&nbsp;

								<c:if test='<%= view.equals("personal") %>'>
									<i><%= LanguageUtil.get(pageContext, "personal") %>&nbsp;</i>
								</c:if>

								<c:if test='<%= !view.equals("personal") %>'>
									<a href="<portlet:renderURL><portlet:param name="struts_action" value="/shopping/view_orders" /><portlet:param name="order_ppps" value="<%= ppPaymentStatus %>" /><portlet:param name="view" value="personal" /></portlet:renderURL>"><%= LanguageUtil.get(pageContext, "personal") %></a>
								</c:if>

								</font>
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

	<br>

	<table border="0" cellpadding="4" cellspacing="0" width="100%">
	<tr class="portlet-section-header" style="font-size: x-small; font-weight: bold;">
		<td>
			<%= LanguageUtil.get(pageContext, "order") %> #
		</td>
		<td>
			<%= LanguageUtil.get(pageContext, "date") %>
		</td>
		<td>
			<%= LanguageUtil.get(pageContext, "status") %>
		</td>

		<c:if test='<%= shoppingAdmin && view.equals("all") %>'>
			<td>
				<%= LanguageUtil.get(pageContext, "customer") %>
			</td>
		</c:if>

		<%--<c:if test="<%= usePayPal %>">
			<td>
				<%= LanguageUtil.get(pageContext, "transaction-id") %>
			</td>
			<td>
				<%= LanguageUtil.get(pageContext, "payment-gross") %>
			</td>
			<td>
				<%= LanguageUtil.get(pageContext, "payer-email-address") %>
			</td>
		</c:if>--%>
	</tr>

	<c:if test="<%= (orders == null) || (orders.size() == 0) %>">
		<tr class="portlet-section-body" style="font-size: x-small;">
			<td align="center" colspan="<%= (shoppingAdmin && view.equals("all")) ? "4" : "3" %>" valign="top">
				<%= LanguageUtil.get(pageContext, "there-are-no-orders") %>
			</td>
		</tr>
	</c:if>

	<%
	for (int i = 0; i < orders.size(); i++) {
		ShoppingOrder order = (ShoppingOrder)orders.get(i);

		String className = "portlet-section-body";
		String classHoverName = "portlet-section-body-hover";

		if (MathUtil.isEven(i)) {
			className = "portlet-section-alternate";
			classHoverName = "portlet-section-alternate-hover";
		}
	%>

		<tr class="<%= className %>" style="font-size: x-small;" onMouseEnter="this.className = '<%= classHoverName %>';" onMouseLeave="this.className = '<%= className %>';">
			<td nowrap>
				<a href="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_order" /><portlet:param name="order_id" value="<%= order.getOrderId() %>" /></portlet:actionURL>"><%= order.getOrderId() %></a>
			</td>
			<td nowrap>
				<%= df.format(order.getCreateDate()) %>
			</td>
			<td nowrap>
				<c:if test="<%= order.getPpPaymentStatus().equals(ShoppingOrder.STATUS_CHECKOUT) %>">
					<font class="portlet-msg-error" style="font-size: x-small;">
				</c:if>

				<c:if test="<%= order.getPpPaymentStatus().equals(ShoppingOrder.STATUS_COMPLETED) %>">
					<font class="portlet-msg-success" style="font-size: x-small;">
				</c:if>

				<%= ShoppingUtil.getPpPaymentStatus(order, pageContext) %>

				<c:if test="<%= order.getPpPaymentStatus().equals(ShoppingOrder.STATUS_CHECKOUT) || order.getPpPaymentStatus().equals(ShoppingOrder.STATUS_COMPLETED) %>">
					</font>
				</c:if>
			</td>

			<c:if test='<%= shoppingAdmin && view.equals("all") %>'>

				<%
				User customer = null;

				try {
					customer = UserLocalServiceUtil.getUserById(order.getUserId());
				}
				catch (Exception e) {
				}
				%>

				<td nowrap>
					<c:if test="<%= customer == null %>">
						<%= order.getBillingFirstName() %> <%= order.getBillingLastName() %>
					</c:if>

					<c:if test="<%= customer != null %>">
						<a href="<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" portletName="<%= PortletKeys.DIRECTORY %>"><liferay-util:param name="struts_action" value="/directory/view_user_profile" /></liferay-portlet:renderURL>&p_u_e_a=<%= customer.getEmailAddress() %>"><%= customer.getFullName() %></a>
					</c:if>
				</td>
			</c:if>
		</tr>

	<%
	}
	%>

	</table>

	<c:if test="<%= ordersSize > delta %>">
		<br>

		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td>

				<%
				PortletURL portletURL = renderResponse.createRenderURL();

				portletURL.setParameter("struts_action", "/shopping/view_orders");
				portletURL.setParameter("order_ppps", ppPaymentStatus);
				portletURL.setParameter("view", view);
				%>

				<%--
				<liferay-ui:page-iterator className="gamma" curParam='<%= renderResponse.getNamespace() + "cur" %>' curValue="<%= curValue %>" delta="<%= delta %>" fontSize="2" maxPages="10" total="<%= ordersSize %>" url="<%= Http.decodeURL(portletURL.toString()) %>" />
				--%>
			</td>
		</tr>
		</table>
	</c:if>
</liferay-ui:box>

</form>