<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String className = (String)request.getAttribute("emailAddresses.className");
long classPK = (Long)request.getAttribute("emailAddresses.classPK");

List<EmailAddress> emailAddresses = Collections.EMPTY_LIST;

int[] emailAddressesIndexes = null;

String emailAddressesIndexesParam = ParamUtil.getString(request, "emailAddressesIndexes");

if (Validator.isNotNull(emailAddressesIndexesParam)) {
	emailAddresses = new ArrayList<EmailAddress>();

	emailAddressesIndexes = StringUtil.split(emailAddressesIndexesParam, 0);

	for (int emailAddressesIndex : emailAddressesIndexes) {
		emailAddresses.add(new EmailAddressImpl());
	}
}
else {
	if (classPK > 0) {
		emailAddresses = EmailAddressServiceUtil.getEmailAddresses(className, classPK);

		emailAddressesIndexes = new int[emailAddresses.size()];

		for (int i = 0; i < emailAddresses.size() ; i++) {
			emailAddressesIndexes[i] = i;
		}
	}

	if (emailAddresses.isEmpty()) {
		emailAddresses = new ArrayList<EmailAddress>();

		emailAddresses.add(new EmailAddressImpl());

		emailAddressesIndexes = new int[] {0};
	}

	if (emailAddressesIndexes == null) {
		emailAddressesIndexes = new int[0];
	}
}
%>

<liferay-ui:error-marker key="errorSection" value="additionalEmailAddresses" />

<h3><liferay-ui:message key="additional-email-addresses" /></h3>

<liferay-ui:error exception="<%= EmailAddressException.class %>" message="please-enter-a-valid-email-address" />
<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + className + ListTypeConstants.EMAIL_ADDRESS %>" message="please-select-a-type" />

<aui:fieldset>

	<%
	for (int i = 0; i < emailAddressesIndexes.length; i++) {
		int emailAddressesIndex = emailAddressesIndexes[i];

		EmailAddress emailAddress = emailAddresses.get(i);
	%>

		<aui:model-context bean="<%= emailAddress %>" model="<%= EmailAddress.class %>" />

		<div class="lfr-form-row">
			<div class="row-fields">
				<aui:input name='<%= "emailAddressId" + emailAddressesIndex %>' type="hidden" value="<%= emailAddress.getEmailAddressId() %>" />

				<aui:input fieldParam='<%= "emailAddressAddress" + emailAddressesIndex %>' label="email-address" name="address" />

				<aui:select label="type" name='<%= "emailAddressTypeId" + emailAddressesIndex %>' listType="<%= className + ListTypeConstants.EMAIL_ADDRESS %>" />

				<aui:field-wrapper cssClass="primary-ctrl">
					<aui:input checked="<%= emailAddress.isPrimary() %>" id='<%= "emailAddressPrimary" + emailAddressesIndex %>' inlineLabel="left" label="primary" name="emailAddressPrimary" type="radio" value="<%= emailAddressesIndex %>" />
				</aui:field-wrapper>
			</div>
		</div>

	<%
	}
	%>

</aui:fieldset>

<aui:script use="liferay-auto-fields">
	new Liferay.AutoFields(
		{
			contentBox: '#additionalEmailAddresses > fieldset',
			fieldIndexes: '<portlet:namespace />emailAddressesIndexes'
		}
	).render();
</aui:script>