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
String className = (String)request.getAttribute("addresses.className");
long classPK = (Long)request.getAttribute("addresses.classPK");

List<Address> addresses = Collections.EMPTY_LIST;

int[] addressesIndexes = null;

String addressesIndexesParam = ParamUtil.getString(request, "addressesIndexes");

if (Validator.isNotNull(addressesIndexesParam)) {
	addresses = new ArrayList<Address>();

	addressesIndexes = StringUtil.split(addressesIndexesParam, 0);

	for (int addressesIndex : addressesIndexes) {
		addresses.add(new AddressImpl());
	}
}
else {
	if (classPK > 0) {
		addresses = AddressServiceUtil.getAddresses(className, classPK);

		addressesIndexes = new int[addresses.size()];

		for (int i = 0; i < addresses.size() ; i++) {
			addressesIndexes[i] = i;
		}
	}

	if (addresses.isEmpty()) {
		addresses = new ArrayList<Address>();

		addresses.add(new AddressImpl());

		addressesIndexes = new int[] {0};
	}

	if (addressesIndexes == null) {
		addressesIndexes = new int[0];
	}
}
%>

<liferay-ui:error-marker key="errorSection" value="addresses" />

<h3><liferay-ui:message key="addresses" /></h3>

<liferay-ui:error exception="<%= AddressCityException.class %>" message="please-enter-a-valid-city" />
<liferay-ui:error exception="<%= AddressStreetException.class %>" message="please-enter-a-valid-street" />
<liferay-ui:error exception="<%= AddressZipException.class %>" message="please-enter-a-valid-zip" />
<liferay-ui:error exception="<%= NoSuchCountryException.class %>" message="please-select-a-country" />
<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + className + ListTypeConstants.ADDRESS %>" message="please-select-a-type" />
<liferay-ui:error exception="<%= NoSuchRegionException.class %>" message="please-select-a-region" />

<aui:fieldset>

	<%
	for (int i = 0; i < addressesIndexes.length; i++) {
		int addressesIndex = addressesIndexes[i];

		Address address = addresses.get(i);
	%>

		<aui:model-context bean="<%= address %>" model="<%= Address.class %>" />

		<div class="lfr-form-row">
			<div class="row-fields">
				<aui:column>
					<aui:input name='<%= "addressId" + addressesIndex %>' type="hidden" value="<%= address.getAddressId() %>" />

					<aui:input fieldParam='<%= "addressStreet1_" + addressesIndex %>' name="street1" />

					<aui:input fieldParam='<%= "addressStreet2_" + addressesIndex %>' name="street2" />

					<aui:input fieldParam='<%= "addressStreet3_" + addressesIndex %>' name="street3" />

					<aui:select label="country" name='<%= "addressCountryId" + addressesIndex %>' />

					<aui:select label="region" name='<%= "addressRegionId" + addressesIndex %>' />
				</aui:column>

				<aui:column>
					<aui:select label="type" name='<%= "addressTypeId" + addressesIndex %>' listType="<%= className + ListTypeConstants.ADDRESS %>" />

					<aui:input name="zip" fieldParam='<%= "addressZip" + addressesIndex %>' />

					<aui:input name="city" fieldParam='<%= "addressCity" + addressesIndex %>' />

					<aui:field-wrapper cssClass="primary-ctrl">
						<aui:input checked="<%= address.isPrimary() %>" id='<%= "addressPrimary" + addressesIndex %>' inlineLabel="left" label="primary" name="addressPrimary" type="radio" value="<%= addressesIndex %>" />
					</aui:field-wrapper>

					<aui:input cssClass="mailing-ctrl" fieldParam='<%= "addressMailing" + addressesIndex %>' inlineLabel="left" name="mailing" />
				</aui:column>
			</div>
		</div>

		<aui:script use="liferay-dynamic-select">
			new Liferay.DynamicSelect(
				[
					{
						select: "<portlet:namespace />addressCountryId<%= addressesIndex %>",
						selectId: "countryId",
						selectDesc: "name",
						selectVal: "<%= address.getCountryId() %>",
						selectData: Liferay.Address.getCountries
					},
					{
						select: "<portlet:namespace />addressRegionId<%= addressesIndex %>",
						selectId: "regionId",
						selectDesc: "name",
						selectVal: "<%= address.getRegionId() %>",
						selectData: Liferay.Address.getRegions
					}
				]
			);
		</aui:script>

	<%
	}
	%>

</aui:fieldset>

<aui:script use="liferay-auto-fields,liferay-dynamic-select">
	var addresses = new Liferay.AutoFields(
		{
			contentBox: '#addresses > fieldset',
			fieldIndexes: '<portlet:namespace />addressesIndexes',
			on: {
				'autorow:clone': function(event) {
					var row = event.row.get('contentBox');
					var guid = event.guid;

					var dynamicSelects = row.one('select[data-componentType=dynamic_select]');

					if (dynamicSelects) {
						dynamicSelects.detach('change');
					}

					new Liferay.DynamicSelect(
						[
							{
								select: "<portlet:namespace />addressCountryId" + guid,
								selectId: "countryId",
								selectDesc: "name",
								selectVal: '',
								selectData: Liferay.Address.getCountries
							},
							{
								select: "<portlet:namespace />addressRegionId" + guid,
								selectId: "regionId",
								selectDesc: "name",
								selectVal: '',
								selectData: Liferay.Address.getRegions
							}
						]
					);
				}
			}
		}
	).render();
</aui:script>