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
List categories = (List)request.getAttribute(WebKeys.SHOPPING_CATEGORIES);
%>

<c:if test="<%= (categories == null) || (categories.size() == 0) %>">
	<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
		<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "browse-categories") %>' />
		<liferay-util:param name="box_bold_title" value="false" />

		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="center">
				<font class="portlet-font" style="font-size: x-small;">
				<%= LanguageUtil.get(pageContext, "there-are-no-categories") %>
				</font>
			</td>
		</tr>
		</table>
	</liferay-ui:box>
</c:if>

<c:if test="<%= (categories != null) && (categories.size() > 0) %>">
	<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
		<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "browse-categories") %>' />
		<liferay-util:param name="box_bold_title" value="false" />

		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td align="right">
				<c:if test="<%= categories.size() < 5 %>">
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td>

							<%
							for (int i = 0; i < categories.size(); i++) {
								ShoppingCategory category = (ShoppingCategory)categories.get(i);
							%>

								<li>
									<font class="portlet-font" style="font-size: x-small;"><a href="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_category" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EDIT %>" /><portlet:param name="category_id" value="<%= category.getCategoryId() %>" /></portlet:actionURL>">
									<%= category.getName() %>
									</a></font>
								</li>

							<%
							}
							%>

						</td>
						<td width="1%">
						</td>
					</tr>
					</table>
				</c:if>

				<c:if test="<%= categories.size() >= 5 %>">
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td valign="top" width="33%">

							<%
							boolean division = false;
							int div = categories.size() / 3;
							int mod = categories.size() % 3;
							int pos = 0;

							int div1;
							int div2;

							if (mod == 1) {
								div1 = div + 1;
								div2 = div + div + 1;
							}
							else if (mod == 2) {
								div1 = div + 1;
								div2 = div + div + 1 + 1;
							}
							else {
								div1 = div;
								div2 = div + div;
							}

							for (int i = 0; i < categories.size(); i++) {
								ShoppingCategory category = (ShoppingCategory)categories.get(i);

								if (division) {
							%>

									</td>
									<td valign="top" width="33%">

							<%
									division = false;
								}
							%>

								<li>
									<font class="portlet-font" style="font-size: x-small;"><a href="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_category" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EDIT %>" /><portlet:param name="category_id" value="<%= category.getCategoryId() %>" /></portlet:actionURL>">
									<%= category.getName() %>
									</a></font>
								</li>

							<%
								pos++;

								if ((pos == div1) || (pos == div2)) {
									division = true;
								}
							}
							%>

						</td>
						<td width="1%">
						</td>
					</tr>
					</table>
				</c:if>
			</td>
		</tr>
		</table>
	</liferay-ui:box>
</c:if>