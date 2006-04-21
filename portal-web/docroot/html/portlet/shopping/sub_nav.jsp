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
ShoppingCart cart = ShoppingCartServiceUtil.getCart(session.getId(), company.getCompanyId());
boolean showCart = renderRequest.getWindowState().equals(WindowState.MAXIMIZED) ? true : false;
boolean blink = SessionMessages.contains(renderRequest, "cart_updated");

int numOfItems = cart.getItems().size();
int numOfPieces = cart.getItemsSize();

String selCategoryId = ParamUtil.getString(request, "shopping_sel_category_id");
String keywords = ParamUtil.getString(request, "keywords");
%>

<form action="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/shopping/search" /></portlet:renderURL>" method="post" name="<portlet:namespace />search_fm">
<input name="<portlet:namespace />shopping_<%= Constants.CMD %>" type="hidden" value="<%= Constants.SEARCH %>">

<table border="0" cellpadding="4" cellspacing="0" width="100%">
<tr>
	<td class="beta-gradient" nowrap width="60%">
		<c:if test="<%= shoppingAdmin %>">
			<font class="beta" size="2"><a class="beta" href="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/shopping/edit_category" /></portlet:actionURL>"><%= LanguageUtil.get(pageContext, "add-category") %></a> - <a class="beta" href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/shopping/browse_categories" /></portlet:renderURL>"><%= LanguageUtil.get(pageContext, "browse-categories") %></a> - <a class="beta" href="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/shopping/cart" /></portlet:actionURL>"><%= LanguageUtil.get(pageContext, "shopping-cart") %></a> - <a class="beta" href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/shopping/view_orders" /></portlet:renderURL>"><%= LanguageUtil.get(pageContext, "orders") %></a> - <a class="beta" href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/shopping/view_coupons" /></portlet:renderURL>"><%= LanguageUtil.get(pageContext, "coupons") %></a> - <a class="beta" href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/shopping/setup" /></portlet:renderURL>"><%= LanguageUtil.get(pageContext, "setup") %></a></font>
		</c:if>
		<c:if test="<%= !shoppingAdmin %>">
			<font class="beta" size="2"><a class="beta" href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/shopping/browse_categories" /></portlet:renderURL>"><%= LanguageUtil.get(pageContext, "browse-categories") %></a> - <a class="beta" href="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/shopping/cart" /></portlet:actionURL>"><%= LanguageUtil.get(pageContext, "shopping-cart") %></a><c:if test="<%= themeDisplay.isSignedIn() %>"> - <a class="beta" href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/shopping/view_orders" /></portlet:renderURL>"><%= LanguageUtil.get(pageContext, "orders") %></a></c:if></font>
		</c:if>
	</td>
	<td align="right" class="beta-gradient" nowrap width="40%">
		<c:if test="<%= showCart %>">
			<a class="beta" href="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/shopping/cart" /></portlet:actionURL>"><img align="absbottom" border="0" height="15" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/shopping/cart.gif" vspace="0" width="15"></a><font class="beta" size="1"><b>&nbsp;&nbsp;&nbsp;<%= blink ? "<blink><span class=\"beta-pos-alert\">" : "" %>(<%= numOfItems %> <%= LanguageUtil.get(pageContext, "items") %><%= (numOfItems != numOfPieces) ? "/" + numOfPieces + " " + LanguageUtil.get(pageContext, "pieces") : "" %>)<%= blink ? "</span></blink>" : "" %></b></font>
		</c:if>
	</td>
</tr>
<tr class="gamma">
	<td colspan="2">
		<input class="form-text" name="<portlet:namespace />keywords" type="text" size="30" value="<%= keywords %>">

		<%
		String categoryId = ParamUtil.get(request, "shopping_category_id", ShoppingCategory.DEFAULT_PARENT_CATEGORY_ID);
		ShoppingCategory category = (ShoppingCategory)request.getAttribute(WebKeys.SHOPPING_CATEGORY);
		List subCategories = null;

		if (category != null) {
			subCategories = ShoppingCategoryServiceUtil.getCategories(category);
		}
		else {
			if (!categoryId.equals(ShoppingCategory.DEFAULT_PARENT_CATEGORY_ID)) {
				category = ShoppingCategoryServiceUtil.getCategory(categoryId);
			}

			subCategories = ShoppingCategoryServiceUtil.getCategories(company.getCompanyId(), categoryId);
		}
		%>

		<input name="shopping_category_id" type="hidden" value="<%= category != null ? category.getCategoryId() : StringPool.BLANK %>">

		<select name="shopping_sel_category_id">
			<option value=""><%= LanguageUtil.get(pageContext, "all-categories") %></option>

			<c:if test="<%= category != null %>">
				<option <%= selCategoryId.equals(category.getCategoryId()) ? "selected" : "" %> value="<%= category.getCategoryId() %>">&nbsp;&nbsp;-&nbsp;<%= category.getName() %></option>
			</c:if>

			<%
			for (int i = 0; i < subCategories.size(); i++) {
				ShoppingCategory subCategory = (ShoppingCategory)subCategories.get(i);
			%>

				<option <%= selCategoryId.equals(subCategory.getCategoryId()) ? "selected" : "" %> value="<%= subCategory.getCategoryId() %>"><%= category != null ? "&nbsp;&nbsp;" : "" %>&nbsp;&nbsp;-&nbsp;<%= subCategory.getName() %></option>

			<%
			}
			%>

		</select>

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "search") %>' onClick="submitForm(document.<portlet:namespace />search_fm);">
	</td>
</tr>
</table>

</form>