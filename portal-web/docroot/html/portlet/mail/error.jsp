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

<%@ page isErrorPage="true" %>

<%
AddressException ae = null;
MailMessageException mme = null;
SendFailedException sfe = null;

if (exception instanceof AddressException) {
	ae = (AddressException)exception;
}
else if (exception instanceof MailMessageException) {
	mme = (MailMessageException)exception;
}
else if (exception instanceof SendFailedException) {
	sfe = (SendFailedException)exception;
}
%>

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "error") %>' />

	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
			<font class="portlet-msg-error" style="font-size: x-small;">
			<%= LanguageUtil.get(pageContext, "an-unexpected-error-occurred-while-sending-your-message") %>
			</font>
		</td>
	</tr>
	</table>

	<br>

	<c:if test="<%= ae != null %>">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td>
				<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<font class="portlet-font" style="font-size: x-small;">
						<%= LanguageUtil.get(pageContext, "the-following-is-an-invalid-address") %>
						</font>
					</td>
					<td width="10">
						&nbsp;
					</td>
					<td>
						<font class="portlet-font" style="font-size: x-small;">

						<%= ae.getRef() %>

						</font>
					</td>
				</tr>
				</table>
			</td>
		</tr>
		</table>
	</c:if>

	<c:if test="<%= mme != null %>">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td>
				<font class="portlet-font" style="font-size: x-small;">

				<%
				PortletURL portletURL = renderResponse.createRenderURL();

				portletURL.setParameter("struts_action", "/mail/compose_message");

				LanguageWrapper restartWrapper = new LanguageWrapper(
					"<a class=\"gamma\" href=\"" + portletURL.toString() + "\">",
					"restart",
					"</a>");
				%>

				<%= LanguageUtil.format(pageContext, "the-message-object-has-expired-and-is-no-longer-available", restartWrapper) %>
				</font>
			</td>
		</tr>
		</table>
	</c:if>

	<c:if test="<%= sfe != null %>">

		<%
		javax.mail.Address[] addresses = sfe.getInvalidAddresses();
		%>

		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td>
				<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td valign="top">
						<font class="portlet-font" style="font-size: x-small;">

						<c:if test="<%= (addresses != null) && (addresses.length > 1) %>">
							<%= LanguageUtil.get(pageContext, "the-following-are-invalid-addresses") %>
						</c:if>

						<c:if test="<%= (addresses != null) && (addresses.length == 1) %>">
							<%= LanguageUtil.get(pageContext, "the-following-is-an-invalid-address") %>
						</c:if>

						<c:if test="<%= (addresses == null) || (addresses.length == 0) %>">
							<%= LanguageUtil.get(pageContext, "there-were-no-recipient-addresses") %>
						</c:if>

						</font>
					</td>
					<td width="10">
						&nbsp;
					</td>
					<td>
						<font class="portlet-font" style="font-size: x-small;">

						<%
						if ((addresses != null) && (addresses.length > 0)) {
							for (int i = 0; i < addresses.length; i++) {
						%>

								<%= Html.formatTo(addresses[i].toString()) %><%= (i != (addresses.length - 1)) ? ", " : "" %>

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
	</c:if>
</liferay-ui:box>