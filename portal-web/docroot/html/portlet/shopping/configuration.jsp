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

<%@ include file="/html/portlet/shopping/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "payment-settings");
String tabs3 = ParamUtil.getString(request, "tabs3", "email-from");

String redirect = ParamUtil.getString(request, "redirect");
%>

<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL" portletConfiguration="true">
	<portlet:param name="tabs2" value="<%= tabs2 %>" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</liferay-portlet:renderURL>

<script type="text/javascript">

	<%
	String emailFromName = ParamUtil.getString(request, "emailFromName", shoppingPrefs.getEmailFromName());
	String emailFromAddress = ParamUtil.getString(request, "emailFromAddress", shoppingPrefs.getEmailFromAddress());

	String emailOrderConfirmationSubject = ParamUtil.getString(request, "emailOrderConfirmationSubject", shoppingPrefs.getEmailOrderConfirmationSubject());
	String emailOrderConfirmationBody = ParamUtil.getString(request, "emailOrderConfirmationBody", shoppingPrefs.getEmailOrderConfirmationBody());

	String emailOrderShippingSubject = ParamUtil.getString(request, "emailOrderShippingSubject", shoppingPrefs.getEmailOrderShippingSubject());
	String emailOrderShippingBody = ParamUtil.getString(request, "emailOrderShippingBody", shoppingPrefs.getEmailOrderShippingBody());

	String editorParam = "";
	String editorContent = "";

	if (tabs3.equals("confirmation-email")) {
		editorParam = "emailOrderConfirmationBody";
		editorContent = emailOrderConfirmationBody;
	}
	else if (tabs3.equals("shipping-email")) {
		editorParam = "emailOrderShippingBody";
		editorContent = emailOrderShippingBody;
	}
	%>

	function <portlet:namespace />initEditor() {
		return "<%= UnicodeFormatter.toString(editorContent) %>";
	}

	function <portlet:namespace />saveConfiguration() {
		<c:if test='<%= tabs2.equals("payment-settings") %>'>
			document.<portlet:namespace />fm.<portlet:namespace />ccTypes.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />current_cc_types);
		</c:if>

		<c:if test='<%= tabs3.endsWith("-email") %>'>
			document.<portlet:namespace />fm.<portlet:namespace /><%= editorParam %>.value = window.<portlet:namespace />editor.getHTML();
		</c:if>

		submitForm(document.<portlet:namespace />fm);
	}
</script>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "saveConfiguration(); return false;" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="tabs3" type="hidden" value="<%= tabs3 %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="ccTypes" type="hidden" />

	<liferay-ui:tabs
		names="payment-settings,shipping-calculation,insurance-calculation,emails"
		param="tabs2"
		url="<%= portletURL %>"
	/>

	<c:choose>
		<c:when test='<%= tabs2.equals("payment-settings") %>'>
			<div class="portlet-msg-info">
				<liferay-ui:message key="enter-a-paypal-email-address-to-send-all-payments-to-paypal" /> <%= LanguageUtil.format(pageContext, "go-to-paypal-and-set-up-ipn-to-post-to-x", "<strong>" + themeDisplay.getPortalURL() + themeDisplay.getPathMain() + "/shopping/notify</strong>", false) %>
			</div>

			<div class="portlet-msg-info">
				<liferay-ui:message key="enter-a-blank-paypal-email-address-to-disable-paypal" />
			</div>

			<aui:fieldset>
				<aui:input cssClass="lfr-input-text-container" label="paypal-email-address" name="payPalEmailAddress" type="text" value="<%= shoppingPrefs.getPayPalEmailAddress() %>" />

				<aui:field-wrapper label="credit-cards">

					<%

					String[] ccTypes1 = ShoppingPreferences.CC_TYPES;
					String[] ccTypes2 = shoppingPrefs.getCcTypes();

					// Left list

					List leftList = new ArrayList();

					for (int i = 0; i < ccTypes2.length; i++) {
						String ccType = (String)ccTypes2[i];

						leftList.add(new KeyValuePair(ccType, LanguageUtil.get(pageContext, "cc_" + ccType)));
					}

					// Right list

					List rightList = new ArrayList();

					for (int i = 0; i < ccTypes1.length; i++) {
						String ccType = (String)ccTypes1[i];

						if (!ArrayUtil.contains(ccTypes2, ccType)) {
							rightList.add(new KeyValuePair(ccType, LanguageUtil.get(pageContext, "cc_" + ccType)));
						}
					}
					%>

					<liferay-ui:input-move-boxes
						leftTitle="current"
						rightTitle="available"
						leftBoxName="current_cc_types"
						rightBoxName="available_cc_types"
						leftReorder="true"
						leftList="<%= leftList %>"
						rightList="<%= rightList %>"
					/>
				</aui:field-wrapper>

				<aui:select label="currency" name="currencyId">

					<%
					for (int i = 0; i < ShoppingPreferences.CURRENCY_IDS.length; i++) {
					%>

						<aui:option label="<%= ShoppingPreferences.CURRENCY_IDS[i] %>" selected="<%= shoppingPrefs.getCurrencyId().equals(ShoppingPreferences.CURRENCY_IDS[i]) %>" />

					<%
					}
					%>

				</aui:select>

				<aui:select name="taxState">

					<%
					for (int i = 0; i < StateUtil.STATES.length; i++) {
					%>

						<aui:option label="<%= StateUtil.STATES[i].getName() %>" selected="<%= shoppingPrefs.getTaxState().equals(StateUtil.STATES[i].getId()) %>" value="<%= StateUtil.STATES[i].getId() %>" />

					<%
					}
					%>

				</aui:select>

				<aui:input maxlength="7" name="taxRate" type="text" size="7" value="<%= taxFormat.format(shoppingPrefs.getTaxRate()) %>" />

				<aui:input label="minimum-order" maxlength="7" name="minOrder" type="text" size="7" value="<%= doubleFormat.format(shoppingPrefs.getMinOrder()) %>" />
			</aui:fieldset>
		</c:when>
		<c:when test='<%= tabs2.equals("shipping-calculation") %>'>
			<div class="portlet-msg-info">
				<liferay-ui:message key="calculate-a-flat-shipping-amount-based-on-the-total-amount-of-the-purchase" /> <span style="font-size: xx-small;">-- <%= LanguageUtil.get(pageContext, "or").toUpperCase() %> --</span> <liferay-ui:message key="calculate-the-shipping-based-on-a-percentage-of-the-total-amount-of-the-purchase" />
			</div>

			<aui:fieldset>
				<aui:select label="formula" name="shippingFormula">
					<aui:option label="flat-amount" selected='<%= shoppingPrefs.getShippingFormula().equals("flat") %>' value="flat" />
					<aui:option label="percentage" selected='<%= shoppingPrefs.getShippingFormula().equals("percentage") %>' />
				</aui:select>

				<aui:field-wrapper label="values">

					<%
					int shippingRange = 0;

					for (int i = 0; i < 5; i++) {
						double shippingRangeA = ShoppingPreferences.INSURANCE_RANGE[shippingRange++];
						double shippingRangeB = ShoppingPreferences.INSURANCE_RANGE[shippingRange++];
					%>

					<%= currencyFormat.format(shippingRangeA) %>

					<c:if test="<%= !Double.isInfinite(shippingRangeB) %>">
						- <%= currencyFormat.format(shippingRangeB) %>
					</c:if>

					<c:if test="<%= Double.isInfinite(shippingRangeB) %>">
						and over
					</c:if>

					<aui:input label="" maxlength="6" name='<%= "shipping" + i %>' size="6" type="text" value="<%= GetterUtil.getString(shoppingPrefs.getShipping()[i]) %>" />


					<%
					}
					%>

				</aui:field-wrapper>
			</aui:fieldset>
		</c:when>
		<c:when test='<%= tabs2.equals("insurance-calculation") %>'>
			<div class="portlet-msg-info">
				<liferay-ui:message key="calculate-a-flat-insurance-amount-based-on-the-total-amount-of-the-purchase" /> <span style="font-size: xx-small;">-- <%= LanguageUtil.get(pageContext, "or").toUpperCase() %> --</span> <liferay-ui:message key="calculate-the-insurance-based-on-a-percentage-of-the-total-amount-of-the-purchase" />
			</div>

			<aui:fieldset>
				<aui:select label="formula" name="insuranceFormula">
					<aui:option label="flat-amount" selected='<%= shoppingPrefs.getInsuranceFormula().equals("flat") %>' value="flat" />
					<aui:option label="percentage" selected='<%= shoppingPrefs.getInsuranceFormula().equals("percentage") %>' />
				</aui:select>

				<aui:field-wrapper label="values">

					<%
					int insuranceRange = 0;

					for (int i = 0; i < 5; i++) {
						double insuranceRangeA = ShoppingPreferences.INSURANCE_RANGE[insuranceRange++];
						double insuranceRangeB = ShoppingPreferences.INSURANCE_RANGE[insuranceRange++];
					%>

					<%= currencyFormat.format(insuranceRangeA) %>

					<c:if test="<%= !Double.isInfinite(insuranceRangeB) %>">
						- <%= currencyFormat.format(insuranceRangeB) %>
					</c:if>

					<c:if test="<%= Double.isInfinite(insuranceRangeB) %>">
						and over
					</c:if>

					<aui:input label="" maxlength="6" name='<%= "insurance" + i %>' size="6" type="text" value="<%= GetterUtil.getString(shoppingPrefs.getInsurance()[i]) %>" />

					<%
					}
					%>

				</aui:field-wrapper>
			</aui:fieldset>
		</c:when>
		<c:when test='<%= tabs2.equals("emails") %>'>
			<liferay-ui:tabs
				names="email-from,confirmation-email,shipping-email"
				param="tabs3"
				url="<%= portletURL.toString() %>"
			/>

			<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
			<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
			<liferay-ui:error key="emailOrderShippingBody" message="please-enter-a-valid-body" />
			<liferay-ui:error key="emailOrderShippingSubject" message="please-enter-a-valid-subject" />
			<liferay-ui:error key="emailOrderConfirmationBody" message="please-enter-a-valid-body" />
			<liferay-ui:error key="emailOrderConfirmationSubject" message="please-enter-a-valid-subject" />

			<c:choose>
				<c:when test='<%= tabs3.endsWith("-email") %>'>
					<aui:fieldset>
						<c:choose>
							<c:when test='<%= tabs3.equals("confirmation-email") %>'>
								<aui:input inlineLabel="left" label="enabled" name="emailOrderConfirmationEnabled" type="checkbox" value="<%= shoppingPrefs.getEmailOrderConfirmationEnabled() %>" />
							</c:when>
							<c:when test='<%= tabs3.equals("shipping-email") %>'>
								<aui:input inlineLabel="left" label="enabled" name="emailOrderShippingEnabled" type="checkbox" value="<%= shoppingPrefs.getEmailOrderShippingEnabled() %>" />
							</c:when>
						</c:choose>
						<c:choose>
							<c:when test='<%= tabs3.equals("confirmation-email") %>'>
								<aui:input cssClass="lfr-input-text-container" label="subject" name="emailOrderConfirmationSubject" type="text" value="<%= emailOrderConfirmationSubject %>" />
							</c:when>
							<c:when test='<%= tabs3.equals("shipping-email") %>'>
								<aui:input cssClass="lfr-input-text-container" label="subject" name="emailOrderShippingSubject" type="text" value="<%= emailOrderShippingSubject %>" />
							</c:when>
						</c:choose>

						<aui:field-wrapper label="body">
							<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" />

							<aui:input name="<%= editorParam %>" type="hidden" value="" />
						</aui:field-wrapper>

						<br />

						<strong><liferay-ui:message key="definition-of-terms" /></strong>

						<br /><br />

						<table class="lfr-table">
						<tr>
							<td>
								<strong>[$FROM_ADDRESS$]</strong>
							</td>
							<td>
								<%= emailFromAddress %>
							</td>
						</tr>
						<tr>
							<td>
								<strong>[$FROM_NAME$]</strong>
							</td>
							<td>
								<%= emailFromName %>
							</td>
						</tr>
						<tr>
							<td>
								<strong>[$ORDER_BILLING_ADDRESS$]</strong>
							</td>
							<td>
								The order billing address
							</td>
						</tr>
						<tr>
							<td>
								<strong>[$ORDER_CURRENCY$]</strong>
							</td>
							<td>
								The order currency
							</td>
						</tr>
						<tr>
							<td>
								<strong>[$ORDER_NUMBER$]</strong>
							</td>
							<td>
								The order ID
							</td>
						</tr>
						<tr>
							<td>
								<strong>[$ORDER_SHIPPING_ADDRESS$]</strong>
							</td>
							<td>
								The order shipping address
							</td>
						</tr>
						<tr>
							<td>
								<strong>[$ORDER_TOTAL$]</strong>
							</td>
							<td>
								The order total
							</td>
						</tr>
						<tr>
							<td>
								<strong>[$PORTAL_URL$]</strong>
							</td>
							<td>
								<%= company.getVirtualHost() %>
							</td>
						</tr>
						<tr>
							<td>
								<strong>[$PORTLET_NAME$]</strong>
							</td>
							<td>
								<%= ((RenderResponseImpl)renderResponse).getTitle() %>
							</td>
						</tr>
						<tr>
							<td>
								<strong>[$TO_ADDRESS$]</strong>
							</td>
							<td>
								The address of the email recipient
							</td>
						</tr>
						<tr>
							<td>
								<strong>[$TO_NAME$]</strong>
							</td>
							<td>
								The name of the email recipient
							</td>
						</tr>
						</table>
					</aui:fieldset>
				</c:when>
				<c:otherwise>
					<aui:fieldset>
						<aui:input cssClass="lfr-input-text-container" label="name" name="emailFromName" type="text" value="<%= emailFromName %>" />

						<aui:input cssClass="lfr-input-text-container" label="address" name="emailFromAddress" type="text" value="<%= emailFromAddress %>" />
					</aui:fieldset>
				</c:otherwise>
			</c:choose>
		</c:when>
	</c:choose>

	<aui:button-row>
		<aui:button name="saveButton" type="submit" value="save" />

		<aui:button name="cancelButton" onClick="<%= redirect %>" value="cancel" />
	</aui:button-row>
</aui:form>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.shopping.edit_configuration.jsp";
%>