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
String[] blocked = prefs.getValues("blocked", new String[0]);
%>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/mail/edit" /><portlet:param name="page" value="filter" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>">
<input name="<portlet:namespace />blocked_list" type="hidden" value="">

<table border="0" cellpadding="4" cellspacing="0" width="100%">
<tr>
	<td align="center">
		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td valign="top">
						<font class="portlet-font" style="font-size: x-small;"><b>
						<%= LanguageUtil.get(pageContext, "type-a-single-email-address") %><br>
						</b></font>

						<input class="form-text" name="<portlet:namespace />blocked_e_a" size="30" type="text" value="">
					</td>
					<td width="10">
						&nbsp;
					</td>
					<td valign="top">
						<font class="portlet-font" style="font-size: x-small;"><b><br></b></font>

						<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<input class="portlet-form-button" type="button" value="<%= LanguageUtil.get(pageContext, "add") %> &gt;&gt;" onClick="addItem(document.<portlet:namespace />fm.<portlet:namespace />blocked_sel, document.<portlet:namespace />fm.<portlet:namespace />blocked_e_a.value, document.<portlet:namespace />fm.<portlet:namespace />blocked_e_a.value); sortBox(document.<portlet:namespace />fm.<portlet:namespace />blocked_sel);">
							</td>
						</tr>
						<tr>
							<td><img border="0" height="3" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
						</tr>
						<tr>
							<td>
								<input class="portlet-form-button" type="button" value="&lt;&lt; <%= LanguageUtil.get(pageContext, "remove") %>" onClick="removeItem(document.<portlet:namespace />fm.<portlet:namespace />blocked_sel);">
							</td>
						</tr>
						</table>
					</td>
					<td width="10">
						&nbsp;
					</td>
					<td>
						<font class="portlet-font" style="font-size: x-small;"><b>
						<%= LanguageUtil.get(pageContext, "block-sender-list") %><br>
						</b></font>

						<select multiple name="<portlet:namespace />blocked_sel" size="10">

						<%
						for (int i = 0; i < blocked.length; i++) {
						%>

							<option value="<%= blocked[i] %>"><%= blocked[i] %></option>

						<%
						}
						%>

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
				<input class="portlet-form-button" type="button" value="<bean:message key="save-settings" />" onClick="document.<portlet:namespace />fm.<portlet:namespace />blocked_list.value = listSelect(document.<portlet:namespace />fm.<portlet:namespace />blocked_sel); submitForm(document.<portlet:namespace />fm);">

				<input class="portlet-form-button" type="button" value="<bean:message key="cancel" />" onClick="self.location = '<portlet:actionURL><portlet:param name="struts_action" value="/mail/edit" /></portlet:actionURL>';">
			</td>
		</tr>
		</table>
	</td>
</tr>
</table>

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />blocked_e_a.focus();
</script>