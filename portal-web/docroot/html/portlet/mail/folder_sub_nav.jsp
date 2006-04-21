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

<%
Folder folder = (Folder)session.getAttribute(WebKeys.MAIL_FOLDER);
String folderName = folder.getName();
%>

<table border="0" cellpadding="4" cellspacing="0" width="100%">
<tr>
	<td class="beta-gradient" nowrap width="60%">

		<%
		String addressBookLayoutId = PortalUtil.getLayoutIdWithPortletId(layouts, PortletKeys.ADDRESS_BOOK, layoutId);

		PortletURL addressBookURL = new PortletURLImpl(request, PortletKeys.ADDRESS_BOOK, addressBookLayoutId, false);

		addressBookURL.setWindowState(WindowState.MAXIMIZED);
		addressBookURL.setPortletMode(PortletMode.VIEW);

		addressBookURL.setParameter("struts_action", "/address_book/view_all");
		%>

		<font class="beta" size="2"><a class="beta" href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mail/view_folder" /></portlet:renderURL>"><bean:message key="INBOX" /></a> - <a class="beta" href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mail/compose_message" /></portlet:renderURL>"><bean:message key="compose" /></a> - <a class="beta" href="<%= addressBookURL.toString() %>"><bean:message key="address-book" /></a> - <a class="beta" href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mail/view_folders" /></portlet:renderURL>"><bean:message key="folders" /></a> - <a class="beta" href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mail/search" /></portlet:renderURL>"><bean:message key="search" /></a> - <a class="beta" href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mail/pop_info" /></portlet:renderURL>"><bean:message key="pop-info" /></a></font>
	</td>
	<td align="right" class="beta-gradient" nowrap width="40%">
		<font class="beta" size="1"><b><a class="beta" href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mail/view_folder" /><portlet:param name="folder_name" value="<%= folderName %>" /></portlet:renderURL>"><%= (MailUtil.isDefaultFolder(folderName)) ? LanguageUtil.get(pageContext, folderName) : folderName %></a> (<%= folder.getUnreadMessageCount() %> <%= LanguageUtil.get(pageContext, "new") %>)</b></font>
	</td>
</tr>
</table>