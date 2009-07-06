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

<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + className + ListTypeImpl.WEBSITE %>" message="please-select-a-type" />
<liferay-ui:error exception="<%= WebsiteURLException.class %>" message="please-enter-a-valid-url" />

<fieldset class="aui-block-labels">

	<%
	for (int i = 0; i < websitesIndexes.length; i++) {
		int websitesIndex = websitesIndexes[i];

		Website website = websites.get(i);
	%>

		<div class="lfr-form-row">
			<div class="row-fields">

				<%
				String fieldParam = "websiteId" + websitesIndex;
				%>

				<input id="<portlet:namespace /><%= fieldParam %>" name="<portlet:namespace /><%= fieldParam %>" type="hidden" value="" />

				<%
				fieldParam = "websiteUrl" + websitesIndex;
				%>

				<div class="aui-ctrl-holder">
					<label for="<portlet:namespace /><%= fieldParam %>"><liferay-ui:message key="url" /></label>

					<liferay-ui:input-field model="<%= Website.class %>" bean="<%= website %>" field="url" fieldParam="<%= fieldParam %>" />
				</div>

				<%
				fieldParam = "websiteTypeId" + websitesIndex;
				%>

				<div class="aui-ctrl-holder">
					<label for="<portlet:namespace /><%= fieldParam %>"><liferay-ui:message key="type" /></label>

					<select id="<portlet:namespace /><%= fieldParam %>" name="<portlet:namespace /><%= fieldParam %>">

						<%
						List<ListType> websiteTypes = ListTypeServiceUtil.getListTypes(className + ListTypeImpl.WEBSITE);

						for (ListType suffix : websiteTypes) {
						%>

							<option <%= (suffix.getListTypeId() == website.getTypeId()) ? "selected" : "" %> value="<%= suffix.getListTypeId() %>"><liferay-ui:message key="<%= suffix.getName() %>" /></option>

						<%
						}
						%>

					</select>
				</div>

				<%
				fieldParam = "websitePrimary" + websitesIndex;
				%>

				<div class="aui-ctrl-holder primary-ctrl">
					<label class="inline-label" for="<portlet:namespace /><%= fieldParam %>">
						<liferay-ui:message key="primary" />

						<input <%= website.isPrimary() ? "checked" : "" %> id="<portlet:namespace /><%= fieldParam %>" name="<portlet:namespace />websitePrimary" type="radio" value="<%= websitesIndex %>" />
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
					container: '#websites > fieldset',
					baseRows: '#websites > fieldset .lfr-form-row',
					fieldIndexes: '<portlet:namespace />websitesIndexes'
				}
			);
		}
	);
</script>