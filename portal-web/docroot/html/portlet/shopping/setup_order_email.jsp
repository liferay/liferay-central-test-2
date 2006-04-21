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

<%@ include file="/html/portlet/shopping/init.jsp" %>

<%
EmailConfig orderEmail = shoppingConfig.getOrderEmail();
%>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/update_setup" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="order_email">
<input name="<portlet:namespace />redirect" type="hidden" value="<portlet:renderURL><portlet:param name="struts_action" value="/shopping/setup_order_email" /></portlet:renderURL>">

<c:if test="<%= SessionMessages.contains(renderRequest, UpdateShoppingConfigAction.class.getName()) %>">
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
			<font class="portlet-msg-success" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "you-have-successfully-updated-the-portlet-setup") %></font>
		</td>
	</tr>
	</table>

	<br>
</c:if>

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "setup") %>' />

	<table border="0" cellpadding="0" cellspacing="2" width="100%">
	<tr>
		<td>
			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><%= LanguageUtil.get(pageContext, "send-order-email-to-users") %></font>
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td>
					<select name="<portlet:namespace />config_oe_send">
						<option <%= orderEmail.isSend() ? "selected" : "" %> value="1"><%= LanguageUtil.get(pageContext, "true") %></option>
						<option <%= !orderEmail.isSend() ? "selected" : "" %> value="0"><%= LanguageUtil.get(pageContext, "false") %></option>
					</select>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<br>

			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "email-subject") %></b></font>
		</td>
	</tr>
	<tr>
		<td>
			<input class="form-text" name="<portlet:namespace />config_oe_subject" size="70" type="text" value="<%= GetterUtil.getString(orderEmail.getSubject()) %>">
		</td>
	</tr>
	<tr>
		<td>
			<br>

			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "email-body") %></b></font>
		</td>
	</tr>
	<tr>
		<td>
			<textarea class="form-text" cols="70" name="<portlet:namespace />config_oe_body" rows="10" wrap="soft"><%= GetterUtil.getString(orderEmail.getBody()) %></textarea>
		</td>
	</tr>
	<tr>
		<td>
			<br>

			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "definition-of-terms") %></b></font>
		</td>
	</tr>
	<tr>
		<td>
			<table border="0" cellpadding="4" cellspacing="0">
			<tr>
				<td>
					<font class="portlet-font" style="font-size: xx-small;"><b>
					[$ADMIN_EMAIL_ADDRESS$]
					</b></font>
				</td>
				<td>
					<font class="portlet-font" style="font-size: xx-small;">
					<%= company.getEmailAddress() %>
					</font>
				</td>
			</tr>
			<tr>
				<td>
					<font class="portlet-font" style="font-size: xx-small;"><b>
					[$ADMIN_NAME$]
					</b></font>
				</td>
				<td>
					<font class="portlet-font" style="font-size: xx-small;">
					<%= company.getShortName() %> Administrator
					</font>
				</td>
			</tr>
			<tr>
				<td>
					<font class="portlet-font" style="font-size: xx-small;"><b>
					[$COMPANY_NAME$]
					</b></font>
				</td>
				<td>
					<font class="portlet-font" style="font-size: xx-small;">
					<%= company.getName() %>
					</font>
				</td>
			</tr>
			<tr>
				<td>
					<font class="portlet-font" style="font-size: xx-small;"><b>
					[$ORDER_NUMBER$]
					</b></font>
				</td>
				<td>
					<font class="portlet-font" style="font-size: xx-small;">
					The order number
					</font>
				</td>
			</tr>
			<tr>
				<td>
					<font class="portlet-font" style="font-size: xx-small;"><b>
					[$ORDER_SUMMARY$]
					</b></font>
				</td>
				<td>
					<font class="portlet-font" style="font-size: xx-small;">
					The order summary
					</font>
				</td>
			</tr>
			<tr>
				<td>
					<font class="portlet-font" style="font-size: xx-small;"><b>
					[$PORTAL_URL$]
					</b></font>
				</td>
				<td>
					<font class="portlet-font" style="font-size: xx-small;">
					http://<%= company.getPortalURL() %>
					</font>
				</td>
			</tr>
			<tr>
				<td>
					<font class="portlet-font" style="font-size: xx-small;"><b>
					[$USER_EMAIL_ADDRESS$]
					</b></font>
				</td>
				<td>
					<font class="portlet-font" style="font-size: xx-small;">
					The email address of the user
					</font>
				</td>
			</tr>
			<tr>
				<td>
					<font class="portlet-font" style="font-size: xx-small;"><b>
					[$USER_NAME$]
					</b></font>
				</td>
				<td>
					<font class="portlet-font" style="font-size: xx-small;">
					The name of the user
					</font>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td align="center">
			<br>

			<input class="portlet-form-button" type="submit" value="<%= LanguageUtil.get(pageContext, "save") %>">

			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="self.location = '<portlet:renderURL><portlet:param name="struts_action" value="/shopping/setup" /></portlet:renderURL>';">
		</td>
	</tr>
	</table>
</liferay-ui:box>

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />config_oe_subject.focus();
</script>