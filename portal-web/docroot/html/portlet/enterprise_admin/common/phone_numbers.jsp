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
String className = (String)request.getAttribute("phones.className");
long classPK = (Long)request.getAttribute("phones.classPK");

List<Phone> phones = Collections.EMPTY_LIST;

int[] phonesIndexes = null;

String phonesIndexesParam = ParamUtil.getString(request, "phonesIndexes");

if (Validator.isNotNull(phonesIndexesParam)) {
	phones = new ArrayList<Phone>();

	phonesIndexes = StringUtil.split(phonesIndexesParam, 0);

	for (int phonesIndex : phonesIndexes) {
		phones.add(new PhoneImpl());
	}
}
else {

	if (classPK > 0) {
		phones = PhoneServiceUtil.getPhones(className, classPK);

		phonesIndexes = new int[phones.size()];

		for (int i = 0; i < phones.size() ; i++) {
			phonesIndexes[i] = i;
		}
	}

	if (phones.isEmpty()) {
		phones = new ArrayList<Phone>();

		phones.add(new PhoneImpl());

		phonesIndexes = new int[] {0};
	}

	if (phonesIndexes == null) {
		phonesIndexes = new int[0];
	}
}

%>

<liferay-ui:error-marker key="errorSection" value="phoneNumbers" />

<h3><liferay-ui:message key="phone-numbers" /></h3>

<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + className + ListTypeImpl.PHONE %>" message="please-select-a-type" />
<liferay-ui:error exception="<%= PhoneNumberException.class %>" message="please-enter-a-valid-phone-number" />

<fieldset class="aui-block-labels">

   <%
	for (int i = 0; i < phonesIndexes.length; i++) {
		int phonesIndex = phonesIndexes[i];

		Phone phone = phones.get(i);
	%>

		<div class="lfr-form-row">
			<div class="row-fields">

				<%
				String fieldParam = "phoneId" + phonesIndex;
				%>

				<input id="<portlet:namespace /><%= fieldParam %>" name="<portlet:namespace /><%= fieldParam %>" type="hidden" value="" />

				<%
				fieldParam = "phoneNumber" + phonesIndex;
				%>

				<div class="aui-ctrl-holder">
					<label for="<portlet:namespace /><%= fieldParam %>"><liferay-ui:message key="number" /></label>

					<liferay-ui:input-field model="<%= Phone.class %>" bean="<%= phone %>" field="number" fieldParam="<%= fieldParam %>" />
				</div>

				<%
				fieldParam = "phoneExtension" + phonesIndex;
				%>

				<div class="aui-ctrl-holder">
					<label for="<portlet:namespace /><%= fieldParam %>"><liferay-ui:message key="extension" /></label>

					<liferay-ui:input-field model="<%= Phone.class %>" bean="<%= phone %>" field="extension" fieldParam="<%= fieldParam %>" />
				</div>

				<%
				fieldParam = "phoneTypeId" + phonesIndex;
				%>

				<div class="aui-ctrl-holder">
					<label for="<portlet:namespace /><%= fieldParam %>"><liferay-ui:message key="type" /></label>

					<select name="<portlet:namespace /><%= fieldParam %>">

						<%
						List<ListType> phoneTypes = ListTypeServiceUtil.getListTypes(className + ListTypeImpl.PHONE);

						for (ListType suffix : phoneTypes) {
						%>

							<option <%= (suffix.getListTypeId() == phone.getTypeId()) ? "selected" : "" %> value="<%= suffix.getListTypeId() %>"><liferay-ui:message key="<%= suffix.getName() %>" /></option>

						<%
						}
						%>

					</select>
				</div>

				<%
				fieldParam = "phonePrimary" + phonesIndex;
				%>

				<div class="aui-ctrl-holder primary-ctrl">
					<label class="inline-label" for="<portlet:namespace /><%= fieldParam %>">
						<liferay-ui:message key="primary" />

						<input <%= phone.isPrimary() ? "checked" : "" %> id="<portlet:namespace /><%= fieldParam %>" name="<portlet:namespace />phoneNumberPrimary" type="radio" value="<%= phonesIndex %>" />
					</label>
				</div>
			</div>
		</div>

	<%
	}
	%>

</fieldset>

<script type="text/javascript">
	AUI().ready(
		function () {
			new Liferay.AutoFields(
				{
					container: '#phoneNumbers > fieldset',
					baseRows: '#phoneNumbers > fieldset .lfr-form-row',
					fieldIndexes: '<portlet:namespace />phonesIndexes'
				}
			);
		}
	);
</script>