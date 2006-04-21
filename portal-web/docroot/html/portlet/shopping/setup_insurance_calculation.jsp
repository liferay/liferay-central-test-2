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

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/update_setup" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="insurance_calculation">
<input name="<portlet:namespace />redirect" type="hidden" value="<portlet:renderURL><portlet:param name="struts_action" value="/shopping/setup_insurance_calculation" /></portlet:renderURL>">

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
				<td>
					<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "insurance-calculation") %></b></font>
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td>
					<table border="0" cellpadding="6" cellspacing="1" width="100%">
					<tr>
						<td align="center" class="beta">
							<font class="beta" size="2">
							<%= currency.getSymbol() %>
							</font>
						</td>
						<td class="beta" colspan="5">
							<font class="beta" size="2">
							<%= LanguageUtil.get(pageContext, "flat-amount") %>: <%= LanguageUtil.get(pageContext, "calculate-a-flat-insurance-amount-based-on-the-total-amount-of-the-purchase") %>
							</font>
						</td>
					</tr>
					<tr>
						<td align="center" class="gamma">
							<font class="portlet-font" style="font-size: x-small;">
							%
							</font>
						</td>
						<td class="gamma" colspan="5">
							<font class="portlet-font" style="font-size: x-small;">
							<%= LanguageUtil.get(pageContext, "percentage") %>: <%= LanguageUtil.get(pageContext, "calculate-the-insurance-based-on-a-percentage-of-the-total-amount-of-the-purchase") %>
							</font>
						</td>
					</tr>
					<tr>
						<td colspan="2">
						</td>
						<td>
						</td>
						<td>
						</td>
						<td colspan="2">
						</td>
					</tr>
					<tr>
						<td colspan="2">
						</td>
						<td align="center">
							<input <%= shoppingConfig.getInsuranceFormula().equals(ShoppingConfig.INSURANCE_FLAT_AMOUNT) ? "checked" : "" %> name="<portlet:namespace />config_i" type="radio" value="<%= ShoppingConfig.INSURANCE_FLAT_AMOUNT %>">
						</td>
						<td>
						</td>
						<td align="center">
							<input <%= shoppingConfig.getInsuranceFormula().equals(ShoppingConfig.INSURANCE_PERCENTAGE) ? "checked" : "" %> name="<portlet:namespace />config_i" type="radio" value="<%= ShoppingConfig.INSURANCE_PERCENTAGE %>">
						</td>
						<td>
						</td>
					</tr>

					<%
					int insuranceRange = 0;

					for (int i = 0; i < 5; i++) {
						double insuranceRangeA = ShoppingConfig.INSURANCE_RANGE[insuranceRange++];
						double insuranceRangeB = ShoppingConfig.INSURANCE_RANGE[insuranceRange++];
					%>

						<tr>
							<td>
							</td>
							<td nowrap>
								<font class="portlet-font" style="font-size: x-small;">
								<%= currency.getSymbol() %><%= insuranceRangeA %>

								<c:if test="<%= !Double.isInfinite(insuranceRangeB) %>">
									- <%= currency.getSymbol() %><%= insuranceRangeB %>
								</c:if>

								<c:if test="<%= Double.isInfinite(insuranceRangeB) %>">
									and over
								</c:if>

								</font>
							</td>
							<td align="center" class="beta">
								<font class="beta" size="2">
								<%= currency.getSymbol() %>
								</font>
							</td>
							<td align="center">
								<input class="form-text" maxlength="6" name="<portlet:namespace />config_i_<%= i %>" size="6" type="text" value="<%= shoppingConfig.getInsurance()[i] %>">
							</td>
							<td align="center" class="gamma">
								<font class="portlet-font" style="font-size: x-small;">
								%
								</font>
							</td>
							<td width="30%">
							</td>
						</tr>

					<%
					}
					%>

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