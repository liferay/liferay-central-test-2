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
String[] displayPropertyKeys = new String[] {
	PropsUtil.SHOPPING_CART_MIN_QTY_MULTIPLE,
	PropsUtil.SHOPPING_CATEGORY_FORWARD_TO_CART,
	PropsUtil.SHOPPING_CATEGORY_SHOW_ITEM_DESCRIPTION,
	PropsUtil.SHOPPING_CATEGORY_SHOW_ITEM_PROPERTIES,
	PropsUtil.SHOPPING_CATEGORY_SHOW_ITEM_PRICES,
	PropsUtil.SHOPPING_CATEGORY_SHOW_SPECIAL_ITEMS,
	PropsUtil.SHOPPING_CATEGORY_SHOW_SPECIAL_ITEM_DESCRIPTION,
	PropsUtil.SHOPPING_ITEM_SHOW_AVAILABILITY
};

String[] displayPropertyDescriptions = new String[] {
	"must-cart-quantities-be-a-multiple-of-the-item's-minimum-quantity",
	"should-users-forward-to-the-cart-page-when-adding-an-item-from-the-category-page",
	"show-item-description-when-browsing-a-category",
	"show-item-properties-when-browsing-a-category",
	"show-item-prices-when-browsing-a-category",
	"show-special-items-when-browsing-a-category",
	"show-special-item-description-when-browsing-a-category",
	"show-availability-when-viewing-an-item"
};
%>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/update_setup" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="display">
<input name="<portlet:namespace />redirect" type="hidden" value="<portlet:renderURL><portlet:param name="struts_action" value="/shopping/setup_display" /></portlet:renderURL>">

<c:if test="<%= SessionMessages.contains(renderRequest, UpdateShoppingConfigAction.class.getName()) %>">
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
			<font class="portlet-msg-success" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "you-have-successfully-updated-the-portlet-setup") %></font>
		</td>
	</tr>
	</table>

	<br>
</c:if>

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "setup") %>' />

	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td align="center">
			<table border="0" cellpadding="4" cellspacing="0" width="100%">

			<%
			for (int i = 0; i < displayPropertyKeys.length; i++) {
				boolean displayPropertyBoolean = GetterUtil.getBoolean(CompanyPropsUtil.get(company.getCompanyId(), displayPropertyKeys[i]));

				String className = "portlet-section-body";
				String classHoverName = "portlet-section-body-hover";

				if (MathUtil.isEven(i)) {
					className = "portlet-section-alternate";
					classHoverName = "portlet-section-alternate-hover";
				}
			%>

				<tr class="<%= className %>" style="font-size: x-small;" onMouseEnter="this.className = '<%= classHoverName %>';" onMouseLeave="this.className = '<%= className %>';">
					<td>
						<%= LanguageUtil.get(pageContext, displayPropertyDescriptions[i]) %>
					</td>
					<td width="10">
						&nbsp;
					</td>
					<td>
						<select name="<portlet:namespace /><%= displayPropertyKeys[i] %>">
							<option <%= (displayPropertyBoolean) ? "selected" : "" %> value="1"><%= LanguageUtil.get(pageContext, "yes") %></option>
							<option <%= (!displayPropertyBoolean) ? "selected" : "" %> value="0"><%= LanguageUtil.get(pageContext, "no") %></option>
						</select>
					</td>
				</tr>

			<%
			}
			%>

			</table>
		</td>
	</tr>
	<tr>
		<td align="center">
			<br>

			<input class="portlet-form-button" type="submit" value="<%= LanguageUtil.get(pageContext, "save") %>">

			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="self.location = '<portlet:renderURL><portlet:param name="struts_action" value="/shopping/setup" /></portlet:renderURL>';">
		</td>
	</tr>
	</table>
</liferay-ui:box>

</form>