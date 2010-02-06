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
String className = (String)request.getAttribute("websites.className");
long classPK = (Long)request.getAttribute("websites.classPK");

List<Website> websites = Collections.EMPTY_LIST;

int[] websitesIndexes = null;

String websitesIndexesParam = ParamUtil.getString(request, "websitesIndexes");

if (Validator.isNotNull(websitesIndexesParam)) {
	websites = new ArrayList<Website>();

	websitesIndexes = StringUtil.split(websitesIndexesParam, 0);

	for (int websitesIndex : websitesIndexes) {
		websites.add(new WebsiteImpl());
	}
}
else {
	if (classPK > 0) {
		websites = WebsiteServiceUtil.getWebsites(className, classPK);

		websitesIndexes = new int[websites.size()];

		for (int i = 0; i < websites.size(); i++) {
			websitesIndexes[i] = i;
		}
	}

	if (websites.isEmpty()) {
		websites = new ArrayList<Website>();

		websites.add(new WebsiteImpl());

		websitesIndexes = new int[] {0};
	}

	if (websitesIndexes == null) {
		websitesIndexes = new int[0];
	}
}
%>

<liferay-ui:error-marker key="errorSection" value="websites" />

<h3><liferay-ui:message key="websites" /></h3>

<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + className + ListTypeConstants.WEBSITE %>" message="please-select-a-type" />
<liferay-ui:error exception="<%= WebsiteURLException.class %>" message="please-enter-a-valid-url" />

<aui:fieldset>

	<%
	for (int i = 0; i < websitesIndexes.length; i++) {
		int websitesIndex = websitesIndexes[i];

		Website website = websites.get(i);
	%>

		<aui:model-context bean="<%= website %>" model="<%= Website.class %>" />

		<div class="lfr-form-row">
			<div class="row-fields">

				<aui:input name='<%= "websiteId" + websitesIndex %>' type="hidden" />

				<aui:input fieldParam='<%= "websiteUrl" + websitesIndex %>' name="url" />

				<aui:select label="type" name='<%= "websiteTypeId" + websitesIndex %>' listType="<%= className + ListTypeConstants.WEBSITE %>" />

				<aui:field-wrapper cssClass="primary-ctrl">
					<aui:input checked="<%= website.isPrimary() %>" id='<%= "websitePrimary" + websitesIndex %>' inlineLabel="left" label="primary" name="websitePrimary" type="radio" value="<%= websitesIndex %>" />
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
			contentBox: '#websites > fieldset',
			fieldIndexes: '<portlet:namespace />websitesIndexes'
		}
	).render();
</aui:script>