<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

if (classPK > 0) {
	addresses = AddressServiceUtil.getAddresses(className, classPK);
}

if (addresses.isEmpty()) {
	addresses = new ArrayList<Address>();

	addresses.add(new AddressImpl());
}
%>

<liferay-ui:error-marker key="errorSection" value="addresses" />

<h3><liferay-ui:message key="addresses" /></h3>

<liferay-ui:error exception="<%= AddressCityException.class %>" message="please-enter-a-valid-city" />
<liferay-ui:error exception="<%= AddressStreetException.class %>" message="please-enter-a-valid-street" />
<liferay-ui:error exception="<%= AddressZipException.class %>" message="please-enter-a-valid-zip" />
<liferay-ui:error exception="<%= NoSuchCountryException.class %>" message="please-select-a-country" />
<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + className + ListTypeImpl.ADDRESS %>" message="please-select-a-type" />
<liferay-ui:error exception="<%= NoSuchRegionException.class %>" message="please-select-a-region" />

<fieldset class="block-labels">

	<%
	for (int i = 0; i < addresses.size(); i++) {
		Address address = addresses.get(i);
	%>

		<div class="lfr-form-row">
			<div class="row-fields">
				<div class="col">

					<%
					String fieldParam = "addressId" + i;
					%>

					<input id="<portlet:namespace /><%= fieldParam %>" name="<portlet:namespace /><%= fieldParam %>" type="hidden" value="" />

					<%
					fieldParam = "addressStreet1" + i;
					%>

					<div class="ctrl-holder">
						<label for="<portlet:namespace /><%= fieldParam %>"><liferay-ui:message key="street1" /></label>

						<liferay-ui:input-field model="<%= Address.class %>" bean="<%= address %>" field="street1" fieldParam="<%= fieldParam %>" />
					</div>

					<%
					fieldParam = "addressStreet2" + i;
					%>

					<div class="ctrl-holder">
						<label for="<portlet:namespace /><%= fieldParam %>"><liferay-ui:message key="street2" /></label>

						<liferay-ui:input-field model="<%= Address.class %>" bean="<%= address %>" field="street2" fieldParam="<%= fieldParam %>" />
					</div>

					<%
					fieldParam = "addressStreet3" + i;
					%>

					<div class="ctrl-holder">
						<label for="<portlet:namespace /><%= fieldParam %>"><liferay-ui:message key="street3" /></label>

						<liferay-ui:input-field model="<%= Address.class %>" bean="<%= address %>" field="street3" fieldParam="<%= fieldParam %>" />
					</div>

					<%
					fieldParam = "addressCountryId" + i;
					%>

					<div class="ctrl-holder">
						<label for="<portlet:namespace /><%= fieldParam %>"><liferay-ui:message key="country" /></label>

						<select id="<portlet:namespace /><%= fieldParam %>" name="<portlet:namespace /><%= fieldParam %>"></select>
					</div>

					<%
					fieldParam = "addressRegionId" + i;
					%>

					<div class="ctrl-holder">
						<label for="<portlet:namespace /><%= fieldParam %>"><liferay-ui:message key="region" /></label>

						<select id="<portlet:namespace /><%= fieldParam %>" name="<portlet:namespace /><%= fieldParam %>"></select>
					</div>
				</div>

				<div class="col">

					<%
					fieldParam = "addressTypeId" + i;
					%>

					<div class="ctrl-holder">
						<label for="<portlet:namespace /><%= fieldParam %>"><liferay-ui:message key="type" /></label>

						<select name="<portlet:namespace /><%= fieldParam %>">

						<%
						List<ListType> addressTypes = ListTypeServiceUtil.getListTypes(Contact.class.getName() + ListTypeImpl.ADDRESS);

						for (ListType suffix : addressTypes) {
						%>

							<option <%= (suffix.getListTypeId() == address.getTypeId()) ? "selected" : "" %> value="<%= suffix.getListTypeId() %>"><liferay-ui:message key="<%= suffix.getName() %>" /></option>

						<%
						}
						%>

						</select>
					</div>

					<%
					fieldParam = "addressZip" + i;
					%>

					<div class="ctrl-holder">
						<label for="<portlet:namespace /><%= fieldParam %>"><liferay-ui:message key="zip" /></label>

						<liferay-ui:input-field model="<%= Address.class %>" bean="<%= address %>" field="zip" fieldParam="<%= fieldParam %>" />
					</div>

					<%
					fieldParam = "addressCity" + i;
					%>

					<div class="ctrl-holder">
						<label for="<portlet:namespace /><%= fieldParam %>"><liferay-ui:message key="city" /></label>

						<liferay-ui:input-field model="<%= Address.class %>" bean="<%= address %>" field="city" fieldParam="<%= fieldParam %>" />
					</div>

					<%
					fieldParam = "addressPrimary" + i;
					%>

					<div class="ctrl-holder primary-ctrl">
						<label class="inline-label" for="<portlet:namespace /><%= fieldParam %>">
							<liferay-ui:message key="primary" />

							<input <%= address.isPrimary() ? "checked" : "" %> id="<portlet:namespace /><%= fieldParam %>" name="<portlet:namespace />addressPrimary" type="radio" value="<%= i %>" />
						</label>
					</div>

					<%
					fieldParam = "addressMailing" + i;
					%>

					<div class="ctrl-holder mailing-ctrl">
						<label class="inline-label" for="<portlet:namespace /><%= fieldParam %>">
							<liferay-ui:message key="mailing" />

							<liferay-ui:input-field model="<%= Address.class %>" bean="<%= address %>" field="mailing" fieldParam="<%= fieldParam %>" />
						</label>
					</div>
				</div>
			</div>
		</div>

		<script type="text/javascript">
			jQuery(
				function () {
					new Liferay.DynamicSelect(
						[
							{
								select: "<portlet:namespace />addressCountryId<%= i %>",
								selectId: "countryId",
								selectDesc: "name",
								selectVal: "<%= address.getCountryId() %>",
								selectData: function(callback) {
									Liferay.Service.Portal.Country.getCountries(
										{
											active: true
										},
										callback
									);
								}
							},
							{
								select: "<portlet:namespace />addressRegionId<%= i %>",
								selectId: "regionId",
								selectDesc: "name",
								selectVal: "<%= address.getRegionId() %>",
								selectData: function(callback, selectKey) {
									Liferay.Service.Portal.Region.getRegions(
										{
											countryId: selectKey,
											active: true
										},
										callback
									);
								}
							}
						]
					);
				}
			);
		</script>

	<%
	}
	%>

</fieldset>

<script type="text/javascript">
	jQuery(
		function () {
			new Liferay.AutoFields(
				{
					container: '#addresses > fieldset',
					baseRows: '#addresses > fieldset .lfr-form-row',
					fieldIndexes: '<portlet:namespace />addressesIndexes'
				}
			);
		}
	);
</script>