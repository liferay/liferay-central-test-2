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

<%@ include file="/html/portal/init.jsp" %>

<table border="0" cellpadding="0" cellspacing="0">
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "copy-page") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<select name="<portlet:namespace />copyLayoutId">
			<option value=""></option>

			<%
			List layoutList = (List)request.getAttribute(WebKeys.LAYOUT_LISTER_LIST);

			for (int i = 0; i < layoutList.size(); i++) {

				// id | parentId | ls | obj id | name | img | depth

				String layoutDesc = (String)layoutList.get(i);

				String[] nodeValues = StringUtil.split(layoutDesc, "|");

				String objId = nodeValues[3];
				String name = nodeValues[4];

				int depth = 0;

				if (i != 0) {
					depth = GetterUtil.getInteger(nodeValues[6]);
				}

				for (int j = 0; j < depth; j++) {
					name = "-&nbsp;" + name;
				}

				Layout copiableLayout = null;

				try {
					copiableLayout = LayoutLocalServiceUtil.getLayout(Layout.getLayoutId(objId), Layout.getOwnerId(objId));
				}
				catch (Exception e) {
				}

				if (copiableLayout != null) {
			%>

					<option value="<%= copiableLayout.getLayoutId() %>"><%= name %></option>

			<%
				}
			}
			%>

		</select>
	</td>
</tr>
</table>
<%--You can quickly copy the content and template for this page if you specify the page ID to copy from. These changes will take precedence over any other content and layout changes that you may have already specified.

<br><br>

<table border="0" cellpadding="0" cellspacing="0">
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "copy-page") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<input class="form-text" name="TypeSettingsProperties(article-id)" size="30" type="text" value="<bean:write name="SEL_LAYOUT" property="typeSettingsProperties(article-id)" />">
	</td>
</tr>
</table>--%>