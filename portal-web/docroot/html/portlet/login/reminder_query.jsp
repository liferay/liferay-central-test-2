<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/login/init.jsp" %>

<%
String emailAddress = ParamUtil.getString(request, "emailAddress");
String redirect = ParamUtil.getString(renderRequest, "redirect");

User user2 = UserLocalServiceUtil.getUserByEmailAddress(company.getPrimaryKey(), emailAddress);
%>

<liferay-ui:tabs
	names="reminder-query"
	backURL="<%= redirect %>" />

<form action="<portlet:actionURL windowState="<%= WindowState.NORMAL.toString() %>"><portlet:param name="struts_action" value="/login/view" /></portlet:actionURL>" method="post" name="fm3">
<input name="<%= Constants.CMD %>" type="hidden" value="forgot-password-reminder-query" />
<input name="emailAddress" type="hidden" value="<%= HtmlUtil.escape(emailAddress) %>" />

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="<%= user2.getReminderQueryQuestion() %>" />
	</td>
	<td>
		<input name="reminderQueryAnswer" type="text"  />
	</td>
</tr>
</table>

<br />

<input type="submit" value="<liferay-ui:message key="send-new-password" />" />

</form>