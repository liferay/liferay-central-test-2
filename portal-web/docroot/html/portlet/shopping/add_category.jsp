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
String parentCategoryId = ParamUtil.get(request, "parent_category_id", ShoppingCategory.DEFAULT_PARENT_CATEGORY_ID);

String name = request.getParameter("category_name");
if ((name == null) || (name.equals(StringPool.NULL))) {
	name = "";
}

PortletURL categoriesURL = renderResponse.createRenderURL();

categoriesURL.setParameter("struts_action", "/shopping/browse_categories");
%>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_category" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="submitForm(this); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>">
<input name="<portlet:namespace />redirect" type="hidden" value='<%= themeDisplay.isStatePopUp() ? themeDisplay.getPathMain() + "/common/close" : categoriesURL.toString() %>'>
<input name="<portlet:namespace />parent_category_id" type="hidden" value="<%= parentCategoryId %>">

<c:choose>
	<c:when test="<%= !themeDisplay.isStatePopUp() %>">
		<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
			<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "add-category") %>' />

			<%@ include file="/html/portlet/shopping/add_category_form.jsp" %>
		</liferay-ui:box>
	</c:when>
	<c:otherwise>
	<%@ include file="/html/portlet/shopping/add_category_form.jsp" %>
	</c:otherwise>
</c:choose>

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />category_name.focus();
</script>