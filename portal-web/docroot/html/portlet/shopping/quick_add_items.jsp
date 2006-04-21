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
String categoryId = ParamUtil.get(request, "category_id", ShoppingCategory.DEFAULT_PARENT_CATEGORY_ID);
%>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/quick_add_items" /><portlet:param name="category_id" value="<%= categoryId %>" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>">
<input name="<portlet:namespace />redirect" type="hidden" value="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_category" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EDIT %>" /><portlet:param name="category_id" value="<%= categoryId %>" /></portlet:actionURL>">

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "quick-add") %>' />

	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td align="center">
			<table border="0" cellpadding="0" cellspacing="2">
			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><%= LanguageUtil.format(pageContext, "enter-one-isbn-number-per-line-to-add-books", new LanguageWrapper("<b>", "50", "</b>"), false) %></font>
				</td>
			</tr>
			<tr>
				<td>
					<textarea class="form-text" cols="70" name="<portlet:namespace />item_isbn" rows="5" wrap="soft"></textarea>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td align="center">
			<br>

			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "add") %>' onClick="alert('<%= UnicodeLanguageUtil.get(pageContext, "please-be-patient") %>'); submitForm(document.<portlet:namespace />fm);">

			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="self.location = '<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_category" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EDIT %>" /><portlet:param name="category_id" value="<%= categoryId %>" /></portlet:actionURL>';">
		</td>
	</tr>
	</table>
</liferay-ui:box>

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />item_isbn.focus();
</script>