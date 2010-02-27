<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
%>

<%@ include file="/html/portlet/shopping/init.jsp" %>

<%
CouponSearch searchContainer = (CouponSearch)request.getAttribute("liferay-ui:search:searchContainer");

CouponDisplayTerms displayTerms = (CouponDisplayTerms)searchContainer.getDisplayTerms();
%>

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="code" />
	</td>
	<td>
		<liferay-ui:message key="discount-type" />
	</td>
	<td>
		<liferay-ui:message key="active" />
	</td>
</tr>
<tr>
	<td>
		<input name="<portlet:namespace /><%= displayTerms.CODE %>" size="20" type="text" value="<%= HtmlUtil.escape(displayTerms.getCode()) %>" />
	</td>
	<td>
		<select name="<%= displayTerms.DISCOUNT_TYPE %>">
			<option value=""></option>

			<%
			for (int i = 0; i < ShoppingCouponConstants.DISCOUNT_TYPES.length; i++) {
			%>

				<option <%= displayTerms.getDiscountType().equals(ShoppingCouponConstants.DISCOUNT_TYPES[i]) ? "selected" : "" %> value="<%= ShoppingCouponConstants.DISCOUNT_TYPES[i] %>"><%= LanguageUtil.get(pageContext, ShoppingCouponConstants.DISCOUNT_TYPES[i]) %></option>

			<%
			}
			%>

		</select>
	</td>
	<td>
		<select name="<portlet:namespace /><%= displayTerms.ACTIVE %>">
			<option <%= displayTerms.isActive() ? "selected" : "" %> value="1"><liferay-ui:message key="yes" /></option>
			<option <%= !displayTerms.isActive() ? "selected" : "" %> value="0"><liferay-ui:message key="no" /></option>
		</select>
	</td>
</tr>
</table>

<br />

<div>
	<select name="<portlet:namespace /><%= displayTerms.AND_OPERATOR %>">
		<option <%= displayTerms.isAndOperator() ? "selected" : "" %> value="1"><liferay-ui:message key="and" /></option>
		<option <%= !displayTerms.isAndOperator() ? "selected" : "" %> value="0"><liferay-ui:message key="or" /></option>
	</select>

	<input type="submit" value="<liferay-ui:message key="search" />" />

	<input type="button" value="<liferay-ui:message key="add-coupon" />" onClick="<portlet:namespace />addCoupon();" />
</div>

<aui:script>
	function <portlet:namespace />addCoupon() {
		document.<portlet:namespace />fm.method = 'post';
		submitForm(document.<portlet:namespace />fm, '<portlet:renderURL><portlet:param name="struts_action" value="/shopping/edit_coupon" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>');
	}

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= displayTerms.CODE %>);
	</c:if>
</aui:script>