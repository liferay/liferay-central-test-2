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

<c:if test="<%= user.hasCompanyMx() %>">
	<table border="0" cellpadding="4" cellspacing="0" width="100%">
	<tr>
		<td align="center">
			<table border="0" cellpadding="0" cellspacing="0">

			<c:if test='<%= SessionMessages.contains(renderRequest, portletConfig.getPortletName() + ".doEdit") %>'>
				<tr>
					<td colspan="3">
						<font class="portlet-msg-success" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "you-have-successfully-updated-your-preferences") %></font>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<br>
					</td>
				</tr>
			</c:if>

			<tr>
				<td valign="top">
					<font class="portlet-font" style="font-size: x-small;">
					<a href="<portlet:actionURL><portlet:param name="struts_action" value="/mail/edit" /><portlet:param name="page" value="forward_address" /></portlet:actionURL>"><bean:message key="forward-address" /></a><br>
					<a href="<portlet:actionURL><portlet:param name="struts_action" value="/mail/edit" /><portlet:param name="page" value="signature" /></portlet:actionURL>"><bean:message key="signature" /></a><br>
					<a href="<portlet:actionURL><portlet:param name="struts_action" value="/mail/edit" /><portlet:param name="page" value="vacation_message" /></portlet:actionURL>"><bean:message key="vacation-message" /></a><br>
					</font>
				</td>
				<td width="30">
					&nbsp;
				</td>
				<td valign="top">
					<font class="portlet-font" style="font-size: x-small;">
					<a href="<portlet:actionURL><portlet:param name="struts_action" value="/mail/edit" /><portlet:param name="page" value="mail_display" /></portlet:actionURL>"><bean:message key="mail-display-settings" /></a><br>
					<a href="<portlet:actionURL><portlet:param name="struts_action" value="/mail/edit" /><portlet:param name="page" value="reply_related" /></portlet:actionURL>"><bean:message key="reply-related-settings" /></a><br>
					<a href="<portlet:actionURL><portlet:param name="struts_action" value="/mail/edit" /><portlet:param name="page" value="filter" /></portlet:actionURL>"><bean:message key="filter-settings" /></a><br>
					<a href="<portlet:actionURL><portlet:param name="struts_action" value="/mail/edit" /><portlet:param name="page" value="sound" /></portlet:actionURL>"><bean:message key="sound-settings" /></a><br>
					</font>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	</table>
</c:if>

<c:if test="<%= !user.hasCompanyMx() %>">
	<table border="0" cellpadding="4" cellspacing="0">
	<tr>
		<td>
			<font class="portlet-msg-error" style="font-size: x-small;">
			You must <a class="gamma-neg-alert" href="<portlet:renderURL windowState="<%= WindowState.NORMAL.toString() %>" portletMode="<%= PortletMode.VIEW.toString() %>" />">register</a> an email address <b>@ <%= company.getMx() %></b> before editing your mail preferences.
			</font>
		</td>
	</tr>
	</table>
</c:if>