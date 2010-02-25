<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
String redirect = ParamUtil.getString(request, "redirect");

ShoppingCategory category = (ShoppingCategory)request.getAttribute(WebKeys.SHOPPING_CATEGORY);

long categoryId = BeanParamUtil.getLong(category, request, "categoryId");

long parentCategoryId = BeanParamUtil.getLong(category, request, "parentCategoryId", ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
%>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_category" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveCategory(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(redirect) %>" />
<input name="<portlet:namespace />categoryId" type="hidden" value="<%= categoryId %>" />
<input name="<portlet:namespace />parentCategoryId" type="hidden" value="<%= parentCategoryId %>" />

<liferay-ui:tabs
	names="category"
	backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
/>

<liferay-ui:error exception="<%= CategoryNameException.class %>" message="please-enter-a-valid-name" />

<c:if test="<%= parentCategoryId != ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID %>">
	<div class="breadcrumbs">
		<%= ShoppingUtil.getBreadcrumbs(parentCategoryId, pageContext, renderRequest, renderResponse) %>
	</div>
</c:if>

<table class="lfr-table">

<c:if test="<%= category != null %>">
	<tr>
		<td>
			<liferay-ui:message key="parent-category" />
		</td>
		<td>
			<table class="lfr-table">
			<tr>
				<td>

					<%
					String parentCategoryName = "";

					try {
						ShoppingCategory parentCategory = ShoppingCategoryLocalServiceUtil.getCategory(parentCategoryId);

						parentCategoryName = parentCategory.getName();
					}
					catch (NoSuchCategoryException nscce) {
					}
					%>

					<a href="<portlet:renderURL><portlet:param name="struts_action" value="/shopping/view" /><portlet:param name="categoryId" value="<%= String.valueOf(parentCategoryId) %>" /></portlet:renderURL>" id="<portlet:namespace />parentCategoryName">
					<%= parentCategoryName %></a>

					<input type="button" value="<liferay-ui:message key="select" />" onClick="var categoryWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/shopping/select_category" /><portlet:param name="categoryId" value="<%= String.valueOf(parentCategoryId) %>" /></portlet:renderURL>', 'category', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680'); void(''); categoryWindow.focus();" />

					<input id="<portlet:namespace />removeCategoryButton" type="button" value="<liferay-ui:message key="remove" />" onClick="<portlet:namespace />removeCategory();" />
				</td>
				<td>
					<div id="<portlet:namespace />merge-with-parent-checkbox-div"
						<c:if test="<%= category.getParentCategoryId() == ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID %>">
							style="display: none;"
						</c:if>
					>
						<liferay-ui:input-checkbox param="mergeWithParentCategory" />

						<liferay-ui:message key="merge-with-parent-category" />
					</div>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
</c:if>

<tr>
	<td>
		<liferay-ui:message key="name" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= ShoppingCategory.class %>" bean="<%= category %>" field="name" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="description" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= ShoppingCategory.class %>" bean="<%= category %>" field="description" />
	</td>
</tr>

<c:if test="<%= category == null %>">
	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="permissions" />
		</td>
		<td>
			<liferay-ui:input-permissions
				modelName="<%= ShoppingCategory.class.getName() %>"
			/>
		</td>
	</tr>
</c:if>

</table>

<br />

<input type="submit" value="<liferay-ui:message key="save" />" />

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(PortalUtil.escapeRedirect(redirect)) %>';" />

</form>

<aui:script>
	function <portlet:namespace />removeCategory() {
		document.<portlet:namespace />fm.<portlet:namespace />parentCategoryId.value = "<%= ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID %>";

		var nameEl = document.getElementById("<portlet:namespace />parentCategoryName");

		nameEl.href = "";
		nameEl.innerHTML = "";

		var mergeWithParent = AUI().one('#<portlet:namespace />merge-with-parent-checkbox-div');
		var mergeWithParentCategory = AUI().one('#<portlet:namespace />mergeWithParentCategoryCheckbox');

		if (mergeWithParent) {
			mergeWithParent.hide();
		}

		if (mergeWithParentCategory) {
			mergeWithParentCategory.set('checked', false);
		}
	}

	function <portlet:namespace />saveCategory() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= category == null ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />selectCategory(parentCategoryId, parentCategoryName) {
		document.<portlet:namespace />fm.<portlet:namespace />parentCategoryId.value = parentCategoryId;

		var nameEl = document.getElementById("<portlet:namespace />parentCategoryName");

		nameEl.href = "<portlet:renderURL><portlet:param name="struts_action" value="/message_boards/view" /></portlet:renderURL>&<portlet:namespace />categoryId=" + parentCategoryId;
		nameEl.innerHTML = parentCategoryName + "&nbsp;";

		if (parentCategoryId != <%= ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID %>) {
			var mergeWithParent = AUI().one('#<portlet:namespace />merge-with-parent-checkbox-div');

			if (mergeWithParent) {
				mergeWithParent.show();
			}
		}
	}

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
	</c:if>
</aui:script>