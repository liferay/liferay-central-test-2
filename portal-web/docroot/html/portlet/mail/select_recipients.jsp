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
com.liferay.portlet.mail.model.Message msgModel = (com.liferay.portlet.mail.model.Message)session.getAttribute(WebKeys.MAIL_MESSAGE_MODEL);

String listType = ParamUtil.get(request, "list_type", "all");
%>

<form method="post" name="<portlet:namespace />fm" onSubmit="document.<portlet:namespace />fm.<portlet:namespace />cmd.value = '<%= Constants.ADD %>'; document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:renderURL><portlet:param name="struts_action" value="/mail/compose_message" /></portlet:renderURL>'; submitForm(document.<portlet:namespace />fm); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />redirect" type="hidden" value="">
<c:if test="<%= msgModel.isReturnReceipt() %>">
	<input name="<portlet:namespace />msg_return_receipt_1" type="hidden" value="on">
</c:if>
<c:if test="<%= msgModel.isHTMLFormat() %>">
	<input name="<portlet:namespace />html_cb_1" type="hidden" value="on">
</c:if>

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "select-recipients") %>' />

	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td valign="top">
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "type-name") %></b></font><br>
			<input class="form-text" tabindex="1" name="<portlet:namespace />recipient_sel_name" size="30" type="text" onKeyUp="autoComplete(document.<portlet:namespace />fm.<portlet:namespace />recipient, this.value);"><br>

			<center>

			<br>

			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "choose-list") %></b></font><br>

					<select name="<portlet:namespace />list_type" onChange="submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/mail/select_recipients" /></portlet:actionURL>');">
						<option <%= (listType.equals("all")) ? "selected" : "" %> value="all"><%= LanguageUtil.get(pageContext, "all") %></option>
						<option <%= (listType.equals("ab")) ? "selected" : "" %> value="ab"><%= LanguageUtil.get(pageContext, "javax.portlet.title.10") %></option>
						<option <%= (listType.equals("cd")) ? "selected" : "" %> value="cd"><%= LanguageUtil.get(pageContext, "javax.portlet.title.11") %></option>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					<br>

					<select tabindex="2" name="<portlet:namespace />recipient" size="20" onChange="s = this.options[this.selectedIndex].text; document.<portlet:namespace />fm.<portlet:namespace />recipient_sel_name.value = s.substring(0, s.length - 3);">

					<%
					List recipients = null;

					if (listType.equals("ab")) {
						recipients = ABUtil.getRecipients();
					}
					else if (listType.equals("cd")) {
						recipients = PortalUtil.getRecipients();
					}
					else {
						recipients = PortalUtil.getAllRecipients();
					}

					for (int i = 0; i < recipients.size(); i++) {
						Recipient recipient = (Recipient)recipients.get(i);
					%>

						<option value="<%= Html.formatTo(recipient.getRecipientInternetAddress()) %>"><%= recipient.getRecipientName() %><%= (recipient.isMultipleRecipients()) ? StringPool.STAR : StringPool.BLANK %>&nbsp;&nbsp;&nbsp;</option>

					<%
					}
					%>

					</select>
				</td>
			</tr>
			</table>

			<br>

			<input class="portlet-form-button" type="submit" value="<%= LanguageUtil.get(pageContext, "finished") %>">

			</center>
		</td>
		<td width="10">
			&nbsp;
		</td>
		<td valign="top">
			<table>
			<tr>
				<td valign="top">
					<input class="portlet-form-button" tabindex="3" type="button" value='<%= LanguageUtil.get(pageContext, "to") %>' onClick="autoFill(document.<portlet:namespace />fm.<portlet:namespace />recipient, document.<portlet:namespace />fm.<portlet:namespace />msg_to);">
				</td>
				<td rowspan="3" width="10">
					&nbsp;
				</td>
				<td>
					<c:if test="<%= msgModel.getTo() != null %>">
						<textarea class="form-text" cols="50" name="<portlet:namespace />msg_to" rows="10" tabindex="7"><%= msgModel.getTo() %></textarea>
					</c:if>

					<c:if test="<%= msgModel.getTo() == null %>">
						<textarea class="form-text" cols="50" name="<portlet:namespace />msg_to" rows="10" tabindex="7"></textarea>
					</c:if>
				</td>
			</tr>
			<tr>
				<td valign="top">
					<input class="portlet-form-button" tabindex="4" type="button" value='<%= LanguageUtil.get(pageContext, "cc") %>' onClick="autoFill(document.<portlet:namespace />fm.<portlet:namespace />recipient, document.<portlet:namespace />fm.<portlet:namespace />msg_cc);">
				</td>
				<td>
					<c:if test="<%= msgModel.getCc() != null %>">
						<textarea class="form-text" cols="50" name="<portlet:namespace />msg_cc" rows="10" tabindex="7"><%= msgModel.getCc() %></textarea>
					</c:if>

					<c:if test="<%= msgModel.getCc() == null %>">
						<textarea class="form-text" cols="50" name="<portlet:namespace />msg_cc" rows="10" tabindex="7"></textarea>
					</c:if>
				</td>
			</tr>
			<tr>
				<td valign="top">
					<input class="portlet-form-button" tabindex="5" type="button" value='<%= LanguageUtil.get(pageContext, "bcc") %>' onClick="autoFill(document.<portlet:namespace />fm.<portlet:namespace />recipient, document.<portlet:namespace />fm.<portlet:namespace />msg_bcc);">
				</td>
				<td>
					<c:if test="<%= msgModel.getBcc() != null %>">
						<textarea class="form-text" cols="50" name="<portlet:namespace />msg_bcc" rows="10" tabindex="7"><%= msgModel.getBcc() %></textarea>
					</c:if>

					<c:if test="<%= msgModel.getBcc() == null %>">
						<textarea class="form-text" cols="50" name="<portlet:namespace />msg_bcc" rows="10" tabindex="7"></textarea>
					</c:if>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	</table>
</liferay-ui:box>

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />recipient_sel_name.focus();
</script>