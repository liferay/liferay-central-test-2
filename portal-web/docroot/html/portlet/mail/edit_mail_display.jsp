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

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/mail/edit" /><portlet:param name="page" value="mail_display" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>">

<table border="0" cellpadding="4" cellspacing="0" width="100%">
<tr>
	<td align="center">
		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<font class="portlet-font" style="font-size: x-small;"><%= LanguageUtil.get(pageContext, "messages-per-inbox-preview") %></font>
					</td>
					<td width="10">
						&nbsp;
					</td>
					<td>
						<select name="<portlet:namespace />msg_p_i_p">
							<option <%= (messagesPerPortlet == 3) ? "selected" : "" %> value="3">3</option>
							<option <%= (messagesPerPortlet == 5) ? "selected" : "" %> value="5">5</option>
							<option <%= (messagesPerPortlet == 10) ? "selected" : "" %> value="10">10</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<br>
					</td>
				</tr>
				<tr>
					<td>
						<font class="portlet-font" style="font-size: x-small;"><%= LanguageUtil.get(pageContext, "messages-per-page") %></font>
					</td>
					<td width="10">
						&nbsp;
					</td>
					<td>
						<select name="<portlet:namespace />msg_p_p">
							<option <%= (messagesPerPage == 10) ? "selected" : "" %> value="10">10</option>
							<option <%= (messagesPerPage == 25) ? "selected" : "" %> value="25">25</option>
							<option <%= (messagesPerPage == 50) ? "selected" : "" %> value="50">50</option>
							<option <%= (messagesPerPage == 100) ? "selected" : "" %> value="100">100</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<br>
					</td>
				</tr>
				<tr>
					<td>
						<font class="portlet-font" style="font-size: x-small;"><%= LanguageUtil.get(pageContext, "message-headers") %></font>
					</td>
					<td width="10">
						&nbsp;
					</td>
					<td>
						<select name="<portlet:namespace />msg_h">
							<option <%= (messageHeaders == 0) ? "selected" : "" %> value="0"><%= LanguageUtil.get(pageContext, "none") %></option>
							<option <%= (messageHeaders == 1) ? "selected" : "" %> value="1"><%= LanguageUtil.get(pageContext, "basic") %></option>
							<option <%= (messageHeaders == 2) ? "selected" : "" %> value="2"><%= LanguageUtil.get(pageContext, "full") %></option>
							<option <%= (messageHeaders == 3) ? "selected" : "" %> value="3"><%= LanguageUtil.get(pageContext, "advanced") %></option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<br>
					</td>
				</tr>
				<tr>
					<td>
						<font class="portlet-font" style="font-size: x-small;"><%= LanguageUtil.get(pageContext, "message-recipients-limit") %></font>
					</td>
					<td width="10">
						&nbsp;
					</td>
					<td>
						<select name="<portlet:namespace />msg_r_l">
							<option <%= (messageRecipientsLimit == 10) ? "selected" : "" %> value="10">10</option>
							<option <%= (messageRecipientsLimit == 25) ? "selected" : "" %> value="25">25</option>
							<option <%= (messageRecipientsLimit == 50) ? "selected" : "" %> value="50">50</option>
							<option <%= (messageRecipientsLimit == 100) ? "selected" : "" %> value="100">100</option>
						</select>
					</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<br>
			</td>
		</tr>
		<tr>
			<td align="center">
				<input class="portlet-form-button" type="button" value="<bean:message key="save-settings" />" onClick="submitForm(document.<portlet:namespace />fm);">

				<input class="portlet-form-button" type="button" value="<bean:message key="cancel" />" onClick="self.location = '<portlet:actionURL><portlet:param name="struts_action" value="/mail/edit" /></portlet:actionURL>';">
			</td>
		</tr>
		</table>
	</td>
</tr>
</table>

</form>