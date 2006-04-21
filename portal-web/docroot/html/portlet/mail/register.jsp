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

<c:if test="<%= !user.hasCompanyMx() %>">
	<table border="0" cellpadding="4" cellspacing="0" width="100%">

	<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mail/register" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="submitForm(this); return false;">

	<tr>
		<td align="center">
			<table border="0" cellpadding="0" cellspacing="0">
			<tr align="center">
				<td>
					<font class="portlet-font" style="font-size: x-small;">
					<%= LanguageUtil.get(pageContext, "choose-a-user-name-for-your-personal-company-email-address") %><br>
					</font>
				</td>
			</tr>
			</table>

			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="5"><img border="0" height="8" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
			</tr>

			<c:if test="<%= SessionErrors.contains(renderRequest, DuplicateUserEmailAddressException.class.getName()) %>">
				<tr>
					<td colspan="5">
						<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "the-email-address-you-requested-is-already-taken") %></font>
					</td>
				</tr>
				<tr>
					<td colspan="5"><img border="0" height="8" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
				</tr>
				</table>
			</c:if>

			<c:if test="<%= SessionErrors.contains(renderRequest, UserEmailAddressException.class.getName()) %>">
				<tr>
					<td colspan="5">
						<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-email-address") %></font>
					</td>
				</tr>
				<tr>
					<td colspan="5"><img border="0" height="8" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
				</tr>
			</c:if>

			<tr>
				<td>
					<input class="form-text" name="<portlet:namespace />user_name" type="text">
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td>
					<font class="portlet-font" style="font-size: small;"><b>@</b></font>
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td>
					<font class="portlet-font" style="font-size: small;"><b><%= company.getMx() %></b></font>
				</td>
			</tr>
			</table>

			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td><img border="0" height="8" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
			</tr>
			</table>

			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<input class="portlet-form-button" type="submit" value="<%= LanguageUtil.get(pageContext, "register") %>">
				</td>
			</tr>
			</table>
		</td>
	</tr>

	</form>

	</table>

	<c:if test="<%= renderRequest.getWindowState().equals(WindowState.MAXIMIZED) %>">
		<script type="text/javascript">
			document.<portlet:namespace />fm.<portlet:namespace />user_name.focus();
		</script>
	</c:if>
</c:if>

<c:if test="<%= user.hasCompanyMx() %>">
	<table border="0" cellpadding="4" cellspacing="0">
	<tr>
		<td>
			<font class="portlet-font" style="font-size: x-small;">

			<%= LanguageUtil.format(pageContext, "your-new-email-address-is-x", "<b>" + user.getEmailAddress() + "</b>", false) %><br>
			<%= LanguageUtil.get(pageContext, "this-email-address-will-also-serve-as-your-login") %><br>

			<br>

			<%
			PortletURL portletURL = renderResponse.createRenderURL();

			portletURL.setWindowState(WindowState.MAXIMIZED);
			portletURL.setPortletMode(PortletMode.VIEW);

			portletURL.setParameter("struts_action", "/mail/view_folder");
			%>

			<%= LanguageUtil.format(pageContext, "you-can-now-check-for-new-messages-in-your-x", "<a class=\"gamma\" href=\"" + portletURL.toString() + "\"><b>" + LanguageUtil.get(pageContext, "INBOX") + "</b></a>", false) %>

			</font>
		</td>
	</tr>
	</table>
</c:if>