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

	if (emailAddressesIndexes == null){
		emailAddressesIndexes = new int[0];
	}
}
%>

<liferay-ui:error-marker key="errorSection" value="additionalEmailAddresses" />

<h3><liferay-ui:message key="additional-email-addresses" /></h3>

<liferay-ui:error exception="<%= EmailAddressException.class %>" message="please-enter-a-valid-email-address" />
<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + className + ListTypeImpl.EMAIL_ADDRESS %>" message="please-select-a-type" />

<fieldset class="exp-block-labels">

	<%
	for (int i = 0; i < emailAddressesIndexes.length; i++) {
		int emailAddressesIndex = emailAddressesIndexes[i];

		EmailAddress emailAddress = emailAddresses.get(i);
	%>

		<div class="lfr-form-row">
			<div class="row-fields">

				<%
				String fieldParam = "emailAddressId" + emailAddressesIndex;
				%>

				<input id="<portlet:namespace /><%= fieldParam %>" name="<portlet:namespace /><%= fieldParam %>" type="hidden" value="" />

				<%
				fieldParam = "emailAddressAddress" + emailAddressesIndex;
				%>

				<div class="exp-ctrl-holder">
					<label for="<portlet:namespace /><%= fieldParam %>"><liferay-ui:message key="email-address" /></label>

					<liferay-ui:input-field model="<%= EmailAddress.class %>" bean="<%= emailAddress %>" field="address" fieldParam="<%= fieldParam %>" />
				</div>

				<%
				fieldParam = "emailAddressTypeId" + emailAddressesIndex;
				%>

				<div class="exp-ctrl-holder">
					<label for="<portlet:namespace /><%= fieldParam %>"><liferay-ui:message key="type" /></label>

					<select id="<portlet:namespace /><%= fieldParam %>" name="<portlet:namespace /><%= fieldParam %>">

						<%
						List<ListType> emailAddressTypes = ListTypeServiceUtil.getListTypes(className + ListTypeImpl.EMAIL_ADDRESS);

						for (ListType suffix : emailAddressTypes) {
						%>

							<option <%= (suffix.getListTypeId() == emailAddress.getTypeId()) ? "selected" : "" %> value="<%= suffix.getListTypeId() %>"><liferay-ui:message key="<%= suffix.getName() %>" /></option>

						<%
						}
						%>

					</select>
				</div>

				<%
				fieldParam = "emailAddressPrimary" + emailAddressesIndex;
				%>

				<div class="exp-ctrl-holder primary-ctrl">
					<label class="inline-label" for="<portlet:namespace /><%= fieldParam %>">
						<liferay-ui:message key="primary" />

						<input <%= emailAddress.isPrimary() ? "checked" : "" %> id="<portlet:namespace /><%= fieldParam %>" name="<portlet:namespace />additionalEmailAddressPrimary" type="radio" value="<%= emailAddressesIndex %>" />
					</label>
				</div>
			</div>
		</div>

	<%
	}
	%>

</fieldset>

<script type="text/javascript">
	jQuery(
		function () {
			new Liferay.AutoFields(
				{
					container: '#additionalEmailAddresses > fieldset',
					baseRows: '#additionalEmailAddresses > fieldset .lfr-form-row',
					fieldIndexes: '<portlet:namespace />emailAddressesIndexes'
				}
			);
		}
	);
</script>