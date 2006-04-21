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

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/mail/edit" /><portlet:param name="page" value="reply_related" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
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
						<font class="portlet-font" style="font-size: x-small;"><%= LanguageUtil.get(pageContext, "include-original-text") %></font>
					</td>
					<td width="10">
						&nbsp;
					</td>
					<td>
						<select name="<portlet:namespace />i_o_t">
							<option <%= (includeOriginal) ? "selected" : "" %> value="1"><%= LanguageUtil.get(pageContext, "auto") %></option>
							<option <%= (!includeOriginal) ? "selected" : "" %> value="0"><%= LanguageUtil.get(pageContext, "manual") %></option>
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
						<font class="portlet-font" style="font-size: x-small;"><%= LanguageUtil.get(pageContext, "original-text-indicator") %></font>
					</td>
					<td width="10">
						&nbsp;
					</td>
					<td>
						<select name="<portlet:namespace />o_t_i">
							<option <%= (originalTextIndicator == 0) ? "selected" : "" %> value="0"><%= LanguageUtil.get(pageContext, "none") %></option>
							<option <%= (originalTextIndicator == 1) ? "selected" : "" %> value="1"><%= LanguageUtil.get(pageContext, "horizontal-separator") %></option>
							<option <%= (originalTextIndicator == 2) ? "selected" : "" %> value="2"><%= LanguageUtil.get(pageContext, "begin-each-line-with") %> &quot;&#62;&quot;</option>
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
						<font class="portlet-font" style="font-size: x-small;"><%= LanguageUtil.get(pageContext, "reply-to-address") %></font>
					</td>
					<td width="10">
						&nbsp;
					</td>
					<td>
						<input class="form-text" name="<portlet:namespace />r_t_a" size="25" type="text" value="<%= Validator.isNull(replyToAddress) ? user.getEmailAddress() : replyToAddress %>">
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