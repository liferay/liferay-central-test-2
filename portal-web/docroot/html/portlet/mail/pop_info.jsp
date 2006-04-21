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

<%@ include file="/html/portlet/mail/init.jsp" %>

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "pop-info") %>' />

	<c:if test="<%= !user.hasCompanyMx() %>">
		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<font class="portlet-font" style="font-size: x-small;">
				<%= LanguageUtil.get(pageContext, "you-do-not-have-an-email-address-registered-with-this-company") %>
				</font>
			</td>
		</tr>
		</table>
	</c:if>

	<c:if test="<%= user.hasCompanyMx() %>">
		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<font class="portlet-font" style="font-size: x-small;">
				<%= LanguageUtil.get(pageContext, "incoming-pop-server") %>:
				</font>
			</td>
			<td width="10">
				&nbsp;
			</td>
			<td>
				<font class="portlet-font" style="font-size: x-small;"><b>
				<%= MailUtil.getPOP3Host() %>
				</b></font>
			</td>
		</tr>
		<tr>
			<td>
				<font class="portlet-font" style="font-size: x-small;">
				<%= LanguageUtil.get(pageContext, "outgoing-smtp-server") %>:
				</font>
			</td>
			<td width="10">
				&nbsp;
			</td>
			<td>
				<font class="portlet-font" style="font-size: x-small;"><b>
				<%= MailUtil.getSMTPHost() %>
				</b></font>
			</td>
		</tr>
		<tr>
			<td>
				<font class="portlet-font" style="font-size: x-small;">
				<%= LanguageUtil.get(pageContext, "account-name") %>:
				</font>
			</td>
			<td width="10">
				&nbsp;
			</td>
			<td>
				<font class="portlet-font" style="font-size: x-small;"><b>
				<%= MailUtil.getLogin(user.getUserId()) %>
				</b></font>
			</td>
		</tr>
		<tr>
			<td>
				<font class="portlet-font" style="font-size: x-small;">
				<%= LanguageUtil.get(pageContext, "account-password") %>:
				</font>
			</td>
			<td width="10">
				&nbsp;
			</td>
			<td>
				<font class="portlet-font" style="font-size: x-small;"><b>

				<%
				int passwordLength = PortalUtil.getUserPassword(request).length();

				for (int i = 0; i < passwordLength; i++) {
					out.print("*");
				}
				%>

				</b></font>
			</td>
		</tr>
		</table>
	</c:if>
</liferay-ui:box>