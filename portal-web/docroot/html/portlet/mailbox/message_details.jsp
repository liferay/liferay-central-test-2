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
%><%@
include file="/html/portlet/init.jsp" %><%@
page import="com.liferay.portlet.mailbox.util.MailMessage,
			 com.liferay.util.Html,
			 java.lang.Object.StringBuffer,
			 javax.mail.Address"
%><%

MailMessage mm = (MailMessage)request.getAttribute("mailMessage");

List attachments = mm.getAttachments();
Address from = mm.getFrom();
Address cc[] = mm.getCc();
Address bcc[] = mm.getBcc();
Address []to = mm.getTo();

%>
<div>
	<div style="font-weight: bold"><%= mm.getSubject() %></div>
	<table cellpadding="0" cellspacing="0" border="0" class="font-small">
	<tr>
		<td style="padding-right: 5px"><%= LanguageUtil.get(pageContext, "from") %>: </td>
		<td><%= Html.escape(from.toString(), false) %></td>
	</tr>
	<%
	if (to != null && to.length > 1) {
		StringBuffer sb = new StringBuffer();
		
		_createAddresses(sb, to);
		%>
		<tr>
			<td style="padding-right: 5px"><%= LanguageUtil.get(pageContext, "to") %>: </td>
			<td><%= sb.toString() %></td>
		</tr>
		<%
	}
	if (cc != null) {
		StringBuffer sb = new StringBuffer();
		
		_createAddresses(sb, cc);
		%>
		<tr>
			<td style="padding-right: 5px"><%= LanguageUtil.get(pageContext, "cc") %>: </td>
			<td><%= sb.toString() %></td>
		</tr>
		<%
	}
	%>
	</table>
</div>

<%!
private void _createAddresses(StringBuffer sb, Address[] addresses) {
	for (int i = 0; i < addresses.length; i++) {
		sb.append(Html.escape(addresses[i].toString(), false));
		if (i != (addresses.length - 1)) {
			sb.append(", ");
		}
	}
}
%>