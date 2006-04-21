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
ShoppingCoupon coupon = (ShoppingCoupon)request.getAttribute(WebKeys.SHOPPING_COUPON);

String couponId = request.getParameter("coupon_id");
if ((couponId == null) || (couponId.equals(StringPool.NULL))) {
	couponId = "";

	if (coupon != null) {
		couponId = coupon.getCouponId();
	}
}

boolean autoCouponId = ParamUtil.get(request, "auto_coupon_id", false);

String name = request.getParameter("coupon_name");
if ((name == null) || (name.equals(StringPool.NULL))) {
	name = "";

	if (coupon != null) {
		name = coupon.getName();
	}
}

String description = request.getParameter("coupon_desc");
if ((description == null) || (description.equals(StringPool.NULL))) {
	description = "";

	if (coupon != null) {
		description = coupon.getDescription();
	}
}

Calendar startDate = new GregorianCalendar(timeZone, locale);

if (coupon != null) {
	if (coupon.getStartDate() != null) {
		startDate.setTime(coupon.getStartDate());
	}
}

String sdMonth = request.getParameter("coupon_sd_month");
if ((sdMonth == null) || (sdMonth.equals(StringPool.NULL))) {
	sdMonth = "";

	if (startDate != null) {
		sdMonth = Integer.toString(startDate.get(Calendar.MONTH));
	}
}

String sdDay = request.getParameter("coupon_sd_day");
if ((sdDay == null) || (sdDay.equals(StringPool.NULL))) {
	sdDay = "";

	if (startDate != null) {
		sdDay = Integer.toString(startDate.get(Calendar.DATE));
	}
}

String sdYear = request.getParameter("coupon_sd_year");
if ((sdYear == null) || (sdYear.equals(StringPool.NULL))) {
	sdYear = "";

	if (startDate != null) {
		sdYear = Integer.toString(startDate.get(Calendar.YEAR));
	}
}

Calendar endDate = new GregorianCalendar(timeZone, locale);
endDate.add(Calendar.YEAR, 1);

if (coupon != null) {
	if (coupon.getEndDate() != null) {
		endDate.setTime(coupon.getEndDate());
	}
}

String edMonth = request.getParameter("coupon_ed_month");
if ((edMonth == null) || (edMonth.equals(StringPool.NULL))) {
	edMonth = "";

	if (endDate != null) {
		edMonth = Integer.toString(endDate.get(Calendar.MONTH));
	}
}

String edDay = request.getParameter("coupon_ed_day");
if ((edDay == null) || (edDay.equals(StringPool.NULL))) {
	edDay = "";

	if (endDate != null) {
		edDay = Integer.toString(endDate.get(Calendar.DATE));
	}
}

String edYear = request.getParameter("coupon_ed_year");
if ((edYear == null) || (edYear.equals(StringPool.NULL))) {
	edYear = "";

	if (endDate != null) {
		edYear = Integer.toString(endDate.get(Calendar.YEAR));
	}
}

boolean neverExpires = ParamUtil.get(request, "coupon_never_expires", false);
String neverExpiresParam = request.getParameter("coupon_never_expires");
if ((neverExpiresParam == null) || (neverExpiresParam.equals(StringPool.NULL))) {
	if (coupon != null) {
		if (coupon.getEndDate() == null) {
			neverExpires = true;
		}
	}
}

boolean active = ParamUtil.get(request, "coupon_active", true);
String activeParam = request.getParameter("coupon_active");
if ((activeParam == null) || (activeParam.equals(StringPool.NULL))) {
	if (coupon != null) {
		active = coupon.isActive();
	}
}

String limitCategories = request.getParameter("coupon_limit_categories");
if ((limitCategories == null) || (limitCategories.equals(StringPool.NULL))) {
	limitCategories = "";

	if (coupon != null) {
		limitCategories = coupon.getLimitCategories();
	}
}

String limitSkus = request.getParameter("coupon_limit_skus");
if ((limitSkus == null) || (limitSkus.equals(StringPool.NULL))) {
	limitSkus = "";

	if (coupon != null) {
		limitSkus = coupon.getLimitSkus();
	}
}

double minOrder = ParamUtil.get(request, "coupon_min_order", 0.0);
String minOrderParam = request.getParameter("coupon_min_order");
if ((minOrderParam == null) || (minOrderParam.equals(StringPool.NULL))) {
	if (coupon != null) {
		minOrder = coupon.getMinOrder();
	}
}

double discount = ParamUtil.get(request, "coupon_discount", 0.0);
String discountParam = request.getParameter("coupon_discount");
if ((discountParam == null) || (discountParam.equals(StringPool.NULL))) {
	if (coupon != null) {
		discount = coupon.getDiscount();
	}
}

String discountType = request.getParameter("coupon_discount_type");
if ((discountType == null) || (discountType.equals(StringPool.NULL))) {
	discountType = "";

	if (coupon != null) {
		discountType = coupon.getDiscountType();
	}
}
%>

<script type="text/javascript">
	function <portlet:namespace />saveCoupon() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= coupon == null ? Constants.ADD : Constants.UPDATE %>";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<portlet:renderURL><portlet:param name="struts_action" value="/shopping/view_coupons" /></portlet:renderURL>";
		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_coupon" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveCoupon(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />redirect" type="hidden" value="<portlet:renderURL><portlet:param name="struts_action" value="/shopping/view_coupons" /></portlet:renderURL>">
<input name="<portlet:namespace />auto_coupon_id" type="hidden" value="<%= autoCouponId %>">
<input name="<portlet:namespace />coupon_never_expires" type="hidden" value="<%= neverExpires %>">

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_br_wrap_content" value="false" />

	<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<c:if test="<%= coupon == null %>">
				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "save") %>' onClick="<portlet:namespace />saveCoupon();">

				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "save-and-add-another") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.ADD %>'; document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_coupon" /></portlet:actionURL>'; submitForm(document.<portlet:namespace />fm);">
			</c:if>

			<c:if test="<%= coupon != null %>">
				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "update") %>' onClick="<portlet:namespace />saveCoupon();">

				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "delete") %>' onClick="if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-this-coupon") %>')) { document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.DELETE %>'; document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:renderURL><portlet:param name="struts_action" value="/shopping/view_coupons" /></portlet:renderURL>'; submitForm(document.<portlet:namespace />fm); }">
			</c:if>

			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="self.location = '<portlet:renderURL><portlet:param name="struts_action" value="/shopping/view_coupons" /></portlet:renderURL>';">
		</td>
	</tr>
	</table>
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
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "coupon-information") %>' />

	<table border="0" cellpadding="0" cellspacing="0" width="90%">
	<tr>
		<td>
			<table border="0" cellpadding="0" cellspacing="2">

			<c:if test="<%= SessionErrors.contains(renderRequest, DuplicateCouponIdException.class.getName()) %>">
				<tr>
					<td>
						<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-unique-id") %></font>
					</td>
				</tr>
			</c:if>

			<c:if test="<%= SessionErrors.contains(renderRequest, CouponIdException.class.getName()) %>">
				<tr>
					<td>
						<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-id") %></font>
					</td>
				</tr>
			</c:if>

			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "id") %></b></font>
				</td>
			</tr>
			<tr>
				<td>
					<c:if test="<%= coupon == null %>">
						<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<input class="form-text" <%= autoCouponId ? "disabled" : "" %> name="<portlet:namespace />coupon_id" type="text" size="25" value="<%= couponId %>">
							</td>
							<td width="30">
								&nbsp;
							</td>
							<td>
								<font class="portlet-font" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "autogenerate-id") %></font> <input <%= autoCouponId ? "checked" : "" %> type="checkbox" onClick="if (this.checked) { document.<portlet:namespace />fm.<portlet:namespace />auto_coupon_id.value = '1'; } else { document.<portlet:namespace />fm.<portlet:namespace />auto_coupon_id.value = '0'; } document.<portlet:namespace />fm.<portlet:namespace />coupon_id.disabled = this.checked;">
							</td>
						</tr>
						</table>
					</c:if>

					<c:if test="<%= coupon != null %>">
						<input name="<portlet:namespace />coupon_id" type="hidden" value="<%= couponId %>">
						<input name="<portlet:namespace />auto_coupon_id" type="hidden" value="0">

						<font class="portlet-font" style="font-size: x-small;">
						<%= couponId %>
						</font>
					</c:if>
				</td>
			</tr>
			<tr>
				<td>
					<br>
				</td>
			</tr>

			<c:if test="<%= SessionErrors.contains(renderRequest, CouponNameException.class.getName()) %>">
				<tr>
					<td>
						<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-name") %></font>
					</td>
				</tr>
			</c:if>

			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><%= LanguageUtil.get(pageContext, "name") %></font>
				</td>
			</tr>
			<tr>
				<td>
					<input class="form-text" name="<portlet:namespace />coupon_name" type="text" size="50" value="<%= name %>">
				</td>
			</tr>
			<tr>
				<td>
					<br>
				</td>
			</tr>

			<c:if test="<%= SessionErrors.contains(renderRequest, CouponDescriptionException.class.getName()) %>">
				<tr>
					<td>
						<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-description") %></font>
					</td>
				</tr>
			</c:if>

			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><%= LanguageUtil.get(pageContext, "description") %></font>
				</td>
			</tr>
			<tr>
				<td>
					<textarea class="form-text" cols="70" name="<portlet:namespace />coupon_desc" rows="5" wrap="soft"><%= GetterUtil.getString(description) %></textarea>
				</td>
			</tr>
			<tr>
				<td>
					<br>
				</td>
			</tr>

			<c:if test="<%= SessionErrors.contains(renderRequest, CouponDateException.class.getName()) %>">
				<tr>
					<td>
						<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-start-date-that-comes-before-the-end-date") %></font>
					</td>
				</tr>
			</c:if>

			<c:if test="<%= SessionErrors.contains(renderRequest, CouponStartDateException.class.getName()) %>">
				<tr>
					<td>
						<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-start-date") %></font>
					</td>
				</tr>
			</c:if>

			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "start-date") %></b></font>
				</td>
			</tr>
			<tr>
				<td>

					<%
					int sdYearInt = Integer.parseInt(sdYear);
					%>

					<%--<liferay-ui:input-date
						formName='<%= renderResponse.getNamespace() + "fm" %>'
						monthParam='<%= renderResponse.getNamespace() + "coupon_sd_month" %>'
						monthValue='<%= Integer.parseInt(sdMonth) %>'
						dayParam='<%= renderResponse.getNamespace() + "coupon_sd_day" %>'
						dayValue='<%= Integer.parseInt(sdDay) %>'
						yearParam='<%= renderResponse.getNamespace() + "coupon_sd_year" %>'
						yearValue='<%= sdYearInt %>'
						yearRangeStart='<%= sdYearInt - 10 %>'
						yearRangeEnd='<%= sdYearInt + 10 %>'
						firstDayOfWeek='<%= startDate.getFirstDayOfWeek() - 1 %>'
						locale='<%= locale.toString() %>'
						calendarImage='<%= themeDisplay.getPathThemeImage() + "/calendar/calendar.gif" %>'
					/>--%>
				</td>
			</tr>
			<tr>
				<td>
					<br>
				</td>
			</tr>

			<c:if test="<%= SessionErrors.contains(renderRequest, CouponEndDateException.class.getName()) %>">
				<tr>
					<td>
						<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-end-date") %></font>
					</td>
				</tr>
			</c:if>

			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "end-date") %></b></font>
				</td>
			</tr>
			<tr>
				<td>
					<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td nowrap>

							<%
							int edYearInt = Integer.parseInt(edYear);
							%>

							<%--<liferay-ui:input-date
								formName='<%= renderResponse.getNamespace() + "fm" %>'
								monthParam='<%= renderResponse.getNamespace() + "coupon_ed_month" %>'
								monthValue='<%= Integer.parseInt(edMonth) %>'
								dayParam='<%= renderResponse.getNamespace() + "coupon_ed_day" %>'
								dayValue='<%= Integer.parseInt(edDay) %>'
								yearParam='<%= renderResponse.getNamespace() + "coupon_ed_year" %>'
								yearValue='<%= edYearInt %>'
								yearRangeStart='<%= edYearInt - 10 %>'
								yearRangeEnd='<%= edYearInt + 10 %>'
								firstDayOfWeek='<%= endDate.getFirstDayOfWeek() - 1 %>'
								locale='<%= locale.toString() %>'
								calendarImage='<%= themeDisplay.getPathThemeImage() + "/calendar/calendar.gif" %>'
							/>--%>
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td valign="bottom">
							<font class="portlet-font" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "never-expires") %></font> <input <%= (neverExpires) ? "checked" : "" %> type="checkbox" onClick="if (this.checked) { document.<portlet:namespace />fm.<portlet:namespace />coupon_never_expires.value = '1'; } else { document.<portlet:namespace />fm.<portlet:namespace />coupon_never_expires.value = '0'; }">
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
					<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "active") %></b></font>
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td>
							<select name="<portlet:namespace />coupon_active">
								<option <%= (active) ? "selected" : "" %> value="1"><%= LanguageUtil.get(pageContext, "yes") %></option>
								<option <%= (!active) ? "selected" : "" %> value="0"><%= LanguageUtil.get(pageContext, "no") %></option>
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
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "discount") %>' />

	<table border="0" cellpadding="0" cellspacing="0" width="90%">
	<tr>
		<td>
			<font class="portlet-font" style="font-size: x-small;">

			<%= LanguageUtil.format(pageContext, "coupons-can-be-set-to-only-apply-to-orders-above-a-minimum-amount", currency.getSymbol() + doubleFormat.format(0), false) %><br><br>

			<%= LanguageUtil.get(pageContext, "set-the-discount-amount-and-the-discount-type") %><br><br>

			<%= LanguageUtil.get(pageContext, "if-the-discount-type-is-free-shipping,-then-shipping-charges-are-subtracted-from-the-order") %>

			</font>
		</td>
	</tr>
	</table>

	<br>

	<table border="0" cellpadding="0" cellspacing="0" width="90%">
	<tr>
		<td>
			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "minimum-order") %></b></font>
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td>
					<input class="form-text" name="<portlet:namespace />coupon_min_order" size="4" type="text" value="<%= currency.getSymbol() %><%= doubleFormat.format(minOrder) %>">
				</td>
				<td width="30">
					&nbsp;
				</td>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "discount") %></b></font>
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td>
					<input class="form-text" name="<portlet:namespace />coupon_discount" size="4" type="text" value="<%= doubleFormat.format(discount) %>">
				</td>
				<td width="30">
					&nbsp;
				</td>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "discount-type") %></b></font>
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td>
					<select name="<portlet:namespace />coupon_discount_type">

						<%
						for (int i = 0; i < ShoppingCoupon.DISCOUNT_TYPES.length; i++) {
						%>

							<option <%= discountType.equals(ShoppingCoupon.DISCOUNT_TYPES[i]) ? "selected" : "" %> value="<%= ShoppingCoupon.DISCOUNT_TYPES[i] %>"><%= LanguageUtil.get(pageContext, ShoppingCoupon.DISCOUNT_TYPES[i]) %></option>

						<%
						}
						%>

					</select>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	</table>
</liferay-ui:box>

<br>

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "limits") %>' />

	<table border="0" cellpadding="0" cellspacing="0" width="90%">
	<tr>
		<td>
			<table border="0" cellpadding="0" cellspacing="2">

			<c:if test="<%= SessionErrors.contains(renderRequest, CouponLimitCategoriesException.class.getName()) %>">

				<%
				List categoryIds = (List)SessionErrors.get(renderRequest, CouponLimitCategoriesException.class.getName());
				%>

				<tr>
					<td>
						<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "the-following-are-invalid-category-ids") %> <%= StringUtil.merge((String[])categoryIds.toArray(new String[0])) %></font>
					</td>
				</tr>
			</c:if>

			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><b>
					<%= LanguageUtil.get(pageContext, "categories") %>
					</b></font><br>

					<font class="portlet-font" style="font-size: xx-small;">
					<%= LanguageUtil.get(pageContext, "this-coupon-only-applies-to-items-that-are-children-of-this-comma-delimited-list-of-categories") %><br>
					<%= LanguageUtil.get(pageContext, "leave-this-blank-if-the-coupon-does-not-check-for-the-parent-categories-of-an-item") %>
					</font>
				</td>
			</tr>
			<tr>
				<td>
					<textarea class="form-text" cols="80" name="<portlet:namespace />coupon_limit_categories" rows="8" wrap="soft"><%= limitCategories %></textarea>
				</td>
			</tr>
			<tr>
				<td>
					<br>
				</td>
			</tr>

			<c:if test="<%= SessionErrors.contains(renderRequest, CouponLimitSKUsException.class.getName()) %>">

				<%
				List skus = (List)SessionErrors.get(renderRequest, CouponLimitSKUsException.class.getName());
				%>

				<tr>
					<td>
						<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "the-following-are-invalid-item-skus") %> <%= StringUtil.merge((String[])skus.toArray(new String[0])) %></font>
					</td>
				</tr>
			</c:if>

			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><b>
					<%= LanguageUtil.get(pageContext, "skus") %>
					</b></font><br>

					<font class="portlet-font" style="font-size: xx-small;">
					<%= LanguageUtil.get(pageContext, "this-coupon-only-applies-to-items-with-a-sku-that-corresponds-to-this-comma-delimited-list-of-item-skus") %><br>
					<%= LanguageUtil.get(pageContext, "leave-this-blank-if-the-coupon-does-not-check-for-the-item-sku") %>
					</font>
				</td>
			</tr>
			<tr>
				<td>
					<textarea class="form-text" cols="80" name="<portlet:namespace />coupon_limit_skus" rows="8" wrap="soft"><%= limitSkus %></textarea>
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

	<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<c:if test="<%= coupon == null %>">
				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "save") %>' onClick="<portlet:namespace />saveCoupon();">

				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "save-and-add-another") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.ADD %>'; document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_coupon" /></portlet:actionURL>'; submitForm(document.<portlet:namespace />fm);">
			</c:if>

			<c:if test="<%= coupon != null %>">
				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "update") %>' onClick="<portlet:namespace />saveCoupon();">

				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "delete") %>' onClick="if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-this-coupon") %>')) { document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.DELETE %>'; document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:renderURL><portlet:param name="struts_action" value="/shopping/view_coupons" /></portlet:renderURL>'; submitForm(document.<portlet:namespace />fm); }">
			</c:if>

			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="self.location = '<portlet:renderURL><portlet:param name="struts_action" value="/shopping/view_coupons" /></portlet:renderURL>';">
		</td>
	</tr>
	</table>
</liferay-ui:box>

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />coupon_<%= ((coupon == null) && !autoCouponId) ? "id" : "name" %>.focus();
</script>