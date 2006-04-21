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
String[] ccTypes = shoppingConfig.getCcTypes();
Set ccTypesSet = new HashSet();
for (int i = 0; i < ccTypes.length; i++) {
	ccTypesSet.add(ccTypes[i]);
}

NumberFormat taxFormat = NumberFormat.getPercentInstance(locale);
taxFormat.setMinimumFractionDigits(3);
%>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/update_setup" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="document.<portlet:namespace />fm.<portlet:namespace />config_cc_types.value = listSelect(document.<portlet:namespace />fm.<portlet:namespace />config_sel_cc_types2); submitForm(this); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="payment">
<input name="<portlet:namespace />redirect" type="hidden" value="<portlet:renderURL><portlet:param name="struts_action" value="/shopping/setup_payment" /></portlet:renderURL>">
<input name="<portlet:namespace />config_cc_types" type="hidden" value="">

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

	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td align="center">
			<table border="0" cellpadding="4" cellspacing="0">
			<tr>
				<td colspan="3">
					<font class="portlet-font" style="font-size: xx-small;">

					<%= LanguageUtil.get(pageContext, "enter-a-paypal-email-address-to-send-all-payments-to-paypal") %> <%= LanguageUtil.format(pageContext, "go-to-paypal-and-set-up-ipn-to-post-to-x", "<b>http://" + company.getPortalURL() + themeDisplay.getPathMain() + "/shopping/notify</b>", false) %><br><br>

					<%= LanguageUtil.get(pageContext, "enter-a-blank-paypal-email-address-to-disable-paypal") %><br><br>

					</font>
				</td>
			</tr>
			<tr>
				<td nowrap>
					<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "paypal-email-address") %></b></font>
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td>
					<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<input class="form-text" name="<portlet:namespace />config_pp_e_a" type="text" size="25" value="<%= shoppingConfig.getPayPalEmailAddress() %>">
						</td>
					</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td nowrap>
					<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "credit-cards") %></b></font>
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td>

					<%
					List leftKVPs = new ArrayList();
					for (int i = 0; i < ccTypes.length; i++) {
						leftKVPs.add(new KeyValuePair(ccTypes[i], LanguageUtil.get(pageContext, ccTypes[i])));
					}

					request.setAttribute(WebKeys.MOVE_BOXES_LEFT_LIST, leftKVPs);

					List rightKVPs = new ArrayList();
					for (int i = 0; i < ShoppingConfig.CC_TYPES.length; i++) {
						if (!ccTypesSet.contains(ShoppingConfig.CC_TYPES[i])) {
							rightKVPs.add(new KeyValuePair(ShoppingConfig.CC_TYPES[i], LanguageUtil.get(pageContext, ShoppingConfig.CC_TYPES[i])));
						}
					}
					Collections.sort(rightKVPs, new KeyValuePairComparator(false, true));

					request.setAttribute(WebKeys.MOVE_BOXES_RIGHT_LIST, rightKVPs);
					%>

					<liferay-util:include page="/html/common/move_boxes.jsp">
						<liferay-util:param name="form_name" value='<%= renderResponse.getNamespace() + "fm" %>' />
						<liferay-util:param name="left_title" value='<%= LanguageUtil.get(pageContext, "current") %>' />
						<liferay-util:param name="right_title" value='<%= LanguageUtil.get(pageContext, "available") %>' />
						<liferay-util:param name="left_box_name" value='<%= renderResponse.getNamespace() + "config_sel_cc_types2" %>' />
						<liferay-util:param name="right_box_name" value='<%= renderResponse.getNamespace() + "config_sel_cc_types" %>' />
						<liferay-util:param name="left_reorder" value="true" />
					</liferay-util:include>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td><img border="0" height="8" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
					</tr>
					<tr>
						<td><div class="beta-separator"></div></td>
					</tr>
					<tr>
						<td><img border="0" height="8" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
					</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "currency") %></b></font>
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td>
					<select name="<portlet:namespace />config_c_id">

					<%
					for (int i = 0; i < ShoppingConfig.CURRENCY_IDS.length; i++) {
					%>

						<option <%= shoppingConfig.getCurrencyId().equals(ShoppingConfig.CURRENCY_IDS[i]) ? "selected" : "" %> value="<%= ShoppingConfig.CURRENCY_IDS[i] %>"><%= ShoppingConfig.CURRENCY_IDS[i] %></option>

					<%
					}
					%>

					</select>
				</td>
			</tr>
			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "tax-state") %></b></font>
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td>
					<select name="<portlet:namespace />config_t_s">

						<%
						for (int i = 0; i < StateUtil.STATES.length; i++) {
						%>

							<option <%= shoppingConfig.getTaxState().equals(StateUtil.STATES[i].getId()) ? "selected" : "" %> value="<%= StateUtil.STATES[i].getId() %>"><%= StateUtil.STATES[i].getName() %></option>

						<%
						}
						%>

					</select>
				</td>
			</tr>
			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "tax-rate") %></b></font>
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td>
					<input class="form-text" maxlength="7" name="<portlet:namespace />config_t_r" type="text" size="7" value="<%= taxFormat.format(shoppingConfig.getTaxRate()) %>">
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td><img border="0" height="8" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
					</tr>
					<tr>
						<td><div class="beta-separator"></div></td>
					</tr>
					<tr>
						<td><img border="0" height="8" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
					</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "minimum-order") %></b></font>
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td>
					<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<input class="form-text" maxlength="7" name="<portlet:namespace />config_m_o" type="text" size="7" value="<%= currency.getSymbol() %><%= doubleFormat.format(shoppingConfig.getMinOrder()) %>">
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td>
							<font class="portlet-font" style="font-size: xx-small;">
							<%= LanguageUtil.format(pageContext, "x-disables-the-minimum-order-requirement", currency.getSymbol() + doubleFormat.format(0), false) %>
							</font>
						</td>
					</tr>
					</table>
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
	document.<portlet:namespace />fm.<portlet:namespace />config_pp_e_a.focus();
</script>