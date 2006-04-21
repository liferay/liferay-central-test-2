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
String play = request.getParameter("play");
if (Validator.isNull(play)) {
	play = "";
}
%>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/mail/edit" /><portlet:param name="page" value="sound" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>">
<input name="<portlet:namespace />play" type="hidden" value="">

<table border="0" cellpadding="4" cellspacing="0" width="100%">
<tr>
	<td align="center">
		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "new-mail-notification") %></b></font>
					</td>
					<td width="10">
						&nbsp;
					</td>
					<td>
						<select name="<portlet:namespace />n_m_n" onChange="document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = ''; document.<portlet:namespace />fm.<portlet:namespace />play.value = 'n_m_n_' + this.value; submitForm(document.<portlet:namespace />fm);">
							<option <%= (((newMailNotification == 0) && (play.equals(""))) || (play.equals("n_m_n_0"))) ? "selected" : "" %> value="0"><%= LanguageUtil.get(pageContext, "none") %></option>
							<option <%= (((newMailNotification == 1) && (play.equals(""))) || (play.equals("n_m_n_1"))) ? "selected" : "" %> value="1"><%= LanguageUtil.get(pageContext, "male-voice") %></option>
							<option <%= (((newMailNotification == 2) && (play.equals(""))) || (play.equals("n_m_n_2"))) ? "selected" : "" %> value="2"><%= LanguageUtil.get(pageContext, "female-voice") %></option>
							<option <%= (((newMailNotification == 3) && (play.equals(""))) || (play.equals("n_m_n_3"))) ? "selected" : "" %> value="3"><%= LanguageUtil.get(pageContext, "cow") %></option>
							<option <%= (((newMailNotification == 4) && (play.equals(""))) || (play.equals("n_m_n_4"))) ? "selected" : "" %> value="4"><%= LanguageUtil.get(pageContext, "laser") %></option>
							<option <%= (((newMailNotification == 5) && (play.equals(""))) || (play.equals("n_m_n_5"))) ? "selected" : "" %> value="5">Ooo</option>
							<option <%= (((newMailNotification == 6) && (play.equals(""))) || (play.equals("n_m_n_6"))) ? "selected" : "" %> value="6"><%= LanguageUtil.get(pageContext, "toad") %></option>
							<option <%= (((newMailNotification == 7) && (play.equals(""))) || (play.equals("n_m_n_7"))) ? "selected" : "" %> value="7"><%= LanguageUtil.get(pageContext, "worm") %></option>
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

<%
if (play.startsWith("n_m_n_") && !play.equals("n_m_n_0")) {
%>

	<embed autostart="true" hidden="true" loop="false" src="<%= themeDisplay.getPathSound() %>/mail/new_mail_<%= play.substring("n_m_n_".length(), "n_m_n_".length() + 1) %>.wav">

<%
}
%>