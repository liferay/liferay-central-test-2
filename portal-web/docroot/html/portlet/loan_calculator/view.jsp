<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/loan_calculator/init.jsp" %>

<%
int loanAmount = ParamUtil.get(request, "loanAmount", 200000);
double interest = ParamUtil.get(request, "interest", 7.00);
int years = ParamUtil.get(request, "years", 30);
int paymentsPerYear = ParamUtil.get(request, "paymentsPerYear", 12);

double tempValue = Math.pow((1 + (interest / 100 / paymentsPerYear)), (years * paymentsPerYear));
double amountPerPayment = (loanAmount * tempValue * (interest / 100 / paymentsPerYear)) / (tempValue - 1);
double totalPaid = amountPerPayment * years * paymentsPerYear;
double interestPaid = totalPaid - loanAmount;

NumberFormat doubleFormat = NumberFormat.getNumberInstance(locale);

doubleFormat.setMaximumFractionDigits(2);
doubleFormat.setMinimumFractionDigits(2);

NumberFormat integerFormat = NumberFormat.getNumberInstance(locale);

integerFormat.setMaximumFractionDigits(0);
integerFormat.setMinimumFractionDigits(0);

NumberFormat percentFormat = NumberFormat.getPercentInstance(locale);
%>

<aui:script use="io-request,parse-content">
	var form = A.get('#<portlet:namespace />fm');
	var parentNode = form.get('parentNode');

	parentNode.plug(A.Plugin.ParseContent);

	form.on(
		'submit',
		function(event) {
			var uri = form.getAttribute('action');

			A.io.request(
				uri,
				{
					form: {
						id: form
					},
					on: {
						success: function(event, id, obj) {
							var responseData = this.get('responseData');

							parentNode.setContent(responseData);
						}
					}
				}
			);

			event.halt();
		}
	);
</aui:script>
<style type="text/css" media="screen">
	.aui-field-disabled .aui-field-content {
		color: #ccc;
	}
	.aui-field-disabled .aui-field-input-disabled {
		border-color: #ccc;
		background: #eee;
		color: #555;
	}
	.aui-field-focused label {
		border-left: 4px solid #333;
		color: #fc0;
	}
</style>
<aui:form name="myForm">
	<aui:input name="input1" label="Text 1" type="text" />
	<aui:input name="input2" label="Checkbox 1" type="checkbox" />
	<aui:input name="input3" label="Radio 1" type="radio" />
	<aui:input name="input4" label="Password 1" type="password" />
	<aui:input name="input5" label="Textarea 1" type="textarea" />
	<aui:input name="input6" label="File 1" type="file" />
	<aui:input name="input7" type="hidden" value="hiddenTest" />

	<aui:input name="input1a" disabled="true" label="Text 1" type="text" value="test" />
	<aui:input name="input2a" label="Checkbox 1" type="checkbox" />
	<aui:input name="input3a" label="Radio 1" type="radio" />
	<aui:input name="input4a" label="Password 1" type="password" />
	<aui:input name="input5a" label="Textarea 1" type="textarea" />
	<aui:input name="input6a" label="File 1" type="file" />
	<aui:input name="input7a" type="hidden" value="hiddenTest" />
</aui:form>
<form action="<liferay-portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/loan_calculator/view" /></liferay-portlet:renderURL>" id="<portlet:namespace />fm" method="post" name="<portlet:namespace />fm">

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="loan-amount" />
	</td>
	<td>
		<input name="<portlet:namespace />loanAmount" size="5" type="text" value="<%= integerFormat.format(loanAmount) %>" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="interest-rate" />
	</td>
	<td>
		<input name="<portlet:namespace />interest" size="5" type="text" value="<%= doubleFormat.format(interest) %>" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="years" />
	</td>
	<td>
		<input name="<portlet:namespace />years" size="5" type="text" value="<%= years %>" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="monthly-payment" />
	</td>
	<td>
		<strong><%= integerFormat.format(amountPerPayment) %></strong>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="interest-paid" />
	</td>
	<td>
		<strong><%= integerFormat.format(interestPaid) %></strong>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="total-paid" />
	</td>
	<td>
		<strong><%= integerFormat.format(totalPaid) %></strong>
	</td>
</tr>
</table>

<br />

<input type="submit" value="<liferay-ui:message key="calculate" />" />

</form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<aui:script>
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />loanAmount);
	</aui:script>
</c:if>