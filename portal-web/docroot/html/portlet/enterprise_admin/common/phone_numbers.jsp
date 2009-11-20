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

<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + className + ListTypeConstants.PHONE %>" message="please-select-a-type" />
<liferay-ui:error exception="<%= PhoneNumberException.class %>" message="please-enter-a-valid-phone-number" />

<aui:fieldset>

	<%
	for (int i = 0; i < phonesIndexes.length; i++) {
		int phonesIndex = phonesIndexes[i];

		Phone phone = phones.get(i);
	%>

		<aui:model-context bean="<%= phone %>" model="<%= Phone.class %>" />

		<div class="lfr-form-row">
			<div class="row-fields">
				<aui:input name='<%= "phoneId" + phonesIndex %>' type="hidden" />

				<aui:input fieldParam='<%= "phoneNumber" + phonesIndex %>' name="number" />

				<aui:input fieldParam='<%= "phoneExtension" + phonesIndex %>' name="extension" />

				<aui:select label="type" name='<%= "phoneTypeId" + phonesIndex %>' listType="<%= className + ListTypeConstants.PHONE %>" />

				<aui:field-wrapper cssClass="primary-ctrl">
					<aui:input checked="<%= phone.isPrimary() %>" id='<%= "phonePrimary" + phonesIndex %>' inlineLabel="left" label="primary" name="phonePrimary" type="radio" value="<%= phonesIndex %>" />
				</aui:field-wrapper>
			</div>
		</div>

	<%
	}
	%>

</aui:fieldset>

<script type="text/javascript">
	AUI().ready(
		'liferay-auto-fields',
		function () {
			new Liferay.AutoFields(
				{
					contentBox: '#phoneNumbers > fieldset',
					fieldIndexes: '<portlet:namespace />phonesIndexes'
				}
			).render();
		}
	);
</script>