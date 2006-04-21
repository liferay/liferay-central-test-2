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

<%@ include file="/html/portlet/address_book/init.jsp" %>

<%
List recipients = (List)request.getAttribute(WebKeys.ADDRESS_BOOK_RECIPIENTS);
%>

<table border="0" cellpadding="4" cellspacing="0" width="100%">
<tr>
	<td align="center">
		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<font class="portlet-font" style="font-size: x-small;">
				<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/address_book/view_all" /></portlet:renderURL>"><%= LanguageUtil.get(pageContext, "view-all") %></a>
				</font>
			</td>
			<td width="10">
				&nbsp;
			</td>
			<td>
				<font class="portlet-font" style="font-size: x-small;">

				<%
				for (int i = 65; i <= 90; i++) {
					char c = (char)i;

					if (ABUtil.hasRecipientWith(recipients, c)) {
				%>

						<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/address_book/view_all" /><portlet:param name="letter" value="<%= Character.toString(c) %>" /></portlet:renderURL>"><u><%= c %></u></a>&nbsp;

				<%
					}
					else {
				%>

						<%= c %>&nbsp;

				<%
					}
				}
				%>

				</font>
			</td>
		</tr>
		</table>
	</td>
</tr>
</table>