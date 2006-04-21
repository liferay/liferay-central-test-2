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
boolean active = ParamUtil.get(request, "coupon_active", true);
String discountType = ParamUtil.getString(request, "coupon_discount_type");

int curValue = ParamUtil.get(request, "cur", 1);
int delta = 20;

int couponsStart = (curValue - 1) * delta;
int couponsEnd = couponsStart + delta;

List coupons = ShoppingCouponServiceUtil.getCoupons(company.getCompanyId(), active, discountType, couponsStart, couponsEnd);
int couponsSize = ShoppingCouponServiceUtil.getCouponsSize(company.getCompanyId(), active, discountType);

DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);
df.setTimeZone(timeZone);
%>

<form action="<portlet:renderURL><portlet:param name="struts_action" value="/shopping/view_coupons" /></portlet:renderURL>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace />coupon_active" type="hidden" value="<%= active %>">
<input name="<portlet:namespace />coupon_discount_type" type="hidden" value="<%= discountType %>">

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "coupons") %>' />

	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "add-coupon") %>' onClick="self.location = '<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_coupon" /></portlet:actionURL>';">
		</td>
	</tr>
	</table>

	<br>

	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<select onChange="document.<portlet:namespace />fm.<portlet:namespace />coupon_discount_type.value = this[this.selectedIndex].value; submitForm(document.<portlet:namespace />fm);">
						<option <%= discountType.equals("") ? "selected" : "" %> value=""><%= LanguageUtil.get(pageContext, "all-discount-types") %></option>

						<%
						for (int i = 0; i < ShoppingCoupon.DISCOUNT_TYPES.length; i++) {
						%>

							<option <%= discountType.equals(ShoppingCoupon.DISCOUNT_TYPES[i]) ? "selected" : "" %> value="<%= ShoppingCoupon.DISCOUNT_TYPES[i] %>"><%= LanguageUtil.get(pageContext, ShoppingCoupon.DISCOUNT_TYPES[i]) %></option>

						<%
						}
						%>

					</select>
				</td>
				<td width="30"><img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="30"></td>
				<td>
					<select onChange="document.<portlet:namespace />fm.<portlet:namespace />coupon_active.value = this[this.selectedIndex].value; submitForm(document.<portlet:namespace />fm);">
						<option <%= active ? "selected" : "" %> value="1"><%= LanguageUtil.get(pageContext, "active") %></option>
						<option <%= !active ? "selected" : "" %> value="0"><%= LanguageUtil.get(pageContext, "inactive") %></option>
					</select>
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
			<%= LanguageUtil.get(pageContext, "coupon-id") %>
		</td>
		<td>
			<%= LanguageUtil.get(pageContext, "start-date") %>
		</td>
		<td>
			<%= LanguageUtil.get(pageContext, "end-date") %>
		</td>
		<td>
			<%= LanguageUtil.get(pageContext, "discount-type") %>
		</td>
	</tr>

	<c:if test="<%= (coupons == null) || (coupons.size() == 0) %>">
		<tr class="portlet-section-body" style="font-size: x-small;">
			<td align="center" colspan="4" valign="top">
				<%= LanguageUtil.get(pageContext, "there-are-no-coupons") %>
			</td>
		</tr>
	</c:if>

	<%
	for (int i = 0; i < coupons.size(); i++) {
		ShoppingCoupon coupon = (ShoppingCoupon)coupons.get(i);

		String className = "portlet-section-body";
		String classHoverName = "portlet-section-body-hover";

		if (MathUtil.isEven(i)) {
			className = "portlet-section-alternate";
			classHoverName = "portlet-section-alternate-hover";
		}
	%>

		<tr class="<%= className %>" style="font-size: x-small;" onMouseEnter="this.className = '<%= classHoverName %>';" onMouseLeave="this.className = '<%= className %>';">
			<td nowrap>
				<a href="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_coupon" /><portlet:param name="coupon_id" value="<%= coupon.getCouponId() %>" /></portlet:actionURL>"><%= coupon.getCouponId() %></a>
			</td>
			<td nowrap>
				<%= df.format(coupon.getStartDate()) %>
			</td>
			<td nowrap>
				<%= coupon.getEndDate() != null ? df.format(coupon.getEndDate()) : LanguageUtil.get(pageContext, "never") %>
			</td>
			<td nowrap>
				<%= LanguageUtil.get(pageContext, coupon.getDiscountType()) %>
			</td>
		</tr>

	<%
	}
	%>

	</table>

	<c:if test="<%= couponsSize > delta %>">
		<br>

		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td>

				<%
				PortletURL portletURL = renderResponse.createRenderURL();

				portletURL.setParameter("struts_action", "/shopping/view_coupons");
				portletURL.setParameter("coupon_active", Boolean.toString(active));
				portletURL.setParameter("coupon_discount_type", discountType);
				%>

				<%--
				<liferay-ui:page-iterator className="gamma" curParam='<%= renderResponse.getNamespace() + "cur" %>' curValue="<%= curValue %>" delta="<%= delta %>" fontSize="2" maxPages="10" total="<%= couponsSize %>" url="<%= Http.decodeURL(portletURL.toString()) %>" />
				--%>
			</td>
		</tr>
		</table>
	</c:if>
</liferay-ui:box>

</form>