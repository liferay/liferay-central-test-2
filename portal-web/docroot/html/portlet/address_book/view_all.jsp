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

boolean companyMx = user.hasCompanyMx();
%>

<form method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace />redirect" type="hidden" value="">

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "view-all") %>' />

	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
			<c:if test="<%= companyMx %>">
				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "compose-email") %>' onClick="submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/address_book/compose_message_to" /></portlet:actionURL>');">
			</c:if>

			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "delete") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<%= request.getAttribute(WebKeys.CURRENT_URL) %>'; submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/address_book/delete" /></portlet:actionURL>');">
		</td>
	</tr>
	</table>

	<br>

	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
			<font class="portlet-font" style="font-size: x-small;">
			<a href="<portlet:renderURL><portlet:param name="struts_action" value="/address_book/view_all" /></portlet:renderURL>"><%= LanguageUtil.get(pageContext, "view-all") %></a>
			</font>
		</td>
		<td width="10">
			&nbsp;
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;">

			<%
			List allRecipients = ABUtil.getRecipients();

			for (int i = 65; i <= 90; i++) {
				char c = (char)i;

				if (ABUtil.hasRecipientWith(allRecipients, c)) {
			%>

					<a href="<portlet:renderURL><portlet:param name="struts_action" value="/address_book/view_all" /><portlet:param name="letter" value="<%= Character.toString(c) %>" /></portlet:renderURL>"><u><%= c %></u></a>&nbsp;

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

	<br>

	<table border="0" cellpadding="4" cellspacing="0" width="100%">
	<tr class="portlet-section-header" style="font-size: x-small; font-weight: bold;">
		<td>
			<input name="<portlet:namespace />allbox" type="checkbox" onClick="checkAll(document.<portlet:namespace />fm, new Array('<portlet:namespace />contact_ids', '<portlet:namespace />list_ids'), this);">
		</td>
		<td>
			<%= LanguageUtil.get(pageContext, "name") %>
		</td>
		<td>
			<%= LanguageUtil.get(pageContext, "email-address") %>
		</td>
	</tr>

	<c:if test="<%= (recipients == null) || (recipients.size() == 0) %>">
		<tr class="portlet-section-body" style="font-size: x-small;">
			<td align="center" colspan="3" valign="top">
				<%= LanguageUtil.get(pageContext, "your-address-book-is-empty") %>
			</td>
		</tr>
	</c:if>

	<%
	for (int i = 0; i < recipients.size(); i++) {
		Recipient recipient = (Recipient)recipients.get(i);

		String className = "portlet-section-body";
		String classHoverName = "portlet-section-body-hover";

		if (MathUtil.isEven(i)) {
			className = "portlet-section-alternate";
			classHoverName = "portlet-section-alternate-hover";
		}
	%>

		<tr class="<%= className %>" style="font-size: x-small;" onMouseEnter="this.className = '<%= classHoverName %>';" onMouseLeave="this.className = '<%= className %>';">
			<td>
				<input name="<portlet:namespace /><%= (recipient.isMultipleRecipients()) ? "list_ids" : "contact_ids" %>" type="checkbox" value="<%= recipient.getRecipientId() %>" onClick="checkAllBox(document.<portlet:namespace />fm, new Array('<portlet:namespace />contact_ids', '<portlet:namespace />list_ids'), <portlet:namespace />allbox);">
			</td>
			<td>
				<c:if test="<%= !recipient.isMultipleRecipients() %>">
					<a href="<portlet:actionURL><portlet:param name="struts_action" value="/address_book/edit_contact" /><portlet:param name="contact_id" value="<%= recipient.getRecipientId() %>" /></portlet:actionURL>"><%= recipient.getRecipientName() %></a>
				</c:if>

				<c:if test="<%= recipient.isMultipleRecipients() %>">
					<a href="<portlet:actionURL><portlet:param name="struts_action" value="/address_book/edit_list" /><portlet:param name="list_id" value="<%= recipient.getRecipientId() %>" /></portlet:actionURL>"><%= recipient.getRecipientName() %></a>*
				</c:if>
			</td>
			<td>
				<c:if test="<%= !recipient.isMultipleRecipients() %>">
					<c:if test="<%= companyMx %>">

						<%
						String mailLayoutId = PortalUtil.getLayoutIdWithPortletId(layouts, PortletKeys.MAIL, layoutId);

						PortletURL mailURL = new PortletURLImpl(request, PortletKeys.MAIL, mailLayoutId, true);

						mailURL.setWindowState(WindowState.MAXIMIZED);
						mailURL.setPortletMode(PortletMode.VIEW);

						mailURL.setParameter("struts_action", "/mail/compose_message_to");
						mailURL.setParameter("recipient_address", recipient.getRecipientAddress());
						mailURL.setParameter("recipient_name", recipient.getRecipientName());
						%>

						<a href="<%= mailURL.toString() %>"><%= recipient.getRecipientAddress() %></a>
					</c:if>

					<c:if test="<%= !companyMx %>">
						<%= recipient.getRecipientAddress() %>
					</c:if>
				</c:if>
			</td>
		</tr>

	<%
	}
	%>

	</table>
</liferay-ui:box>

</form>