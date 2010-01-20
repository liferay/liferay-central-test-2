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

<%@ include file="/html/portlet/directory/init.jsp" %>

<%
themeDisplay.setIncludeServiceJs(true);

OrganizationSearch searchContainer = (OrganizationSearch)request.getAttribute("liferay-ui:search:searchContainer");

OrganizationDisplayTerms displayTerms = (OrganizationDisplayTerms)searchContainer.getDisplayTerms();

String type = displayTerms.getType();
%>

<liferay-ui:search-toggle
	id="toggle_id_directory_organization_search"
	displayTerms="<%= displayTerms %>"
	buttonLabel="search"
>
	<table class="lfr-table">
	<tr>
		<td>
			<liferay-ui:message key="name" />
		</td>
		<td>
			<liferay-ui:message key="street" />
		</td>
		<td>
			<liferay-ui:message key="city" />
		</td>
		<td>
			<liferay-ui:message key="zip" />
		</td>
	</tr>
	<tr>
		<td>
			<input name="<portlet:namespace /><%= displayTerms.NAME %>" size="20" type="text" value="<%= HtmlUtil.escape(displayTerms.getName()) %>" />
		</td>
		<td>
			<input name="<portlet:namespace /><%= displayTerms.STREET %>" size="20" type="text" value="<%= HtmlUtil.escape(displayTerms.getStreet()) %>" />
		</td>
		<td>
			<input name="<portlet:namespace /><%= displayTerms.CITY %>" size="20" type="text" value="<%= HtmlUtil.escape(displayTerms.getCity()) %>" />
		</td>
		<td>
			<input name="<portlet:namespace /><%= displayTerms.ZIP %>" size="20" type="text" value="<%= HtmlUtil.escape(displayTerms.getZip()) %>" />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="type" />
		</td>
		<td>
			<liferay-ui:message key="country" />
		</td>
		<td>
			<liferay-ui:message key="region" />
		</td>
		<td colspan="2"></td>
	</tr>
	<tr>
		<td>
			<select name="<portlet:namespace /><%= displayTerms.TYPE %>">
				<option <%= (displayTerms.getType() == null) ? "selected" : "" %> value=""><liferay-ui:message key="any" /></option>

				<%
				for (String curType : PropsValues.ORGANIZATIONS_TYPES) {
				%>

					<option <%= type.equals(curType) ? "selected" : "" %> value="<%= curType %>"><liferay-ui:message key="<%= curType %>" /></option>

				<%
				}
				%>

			</select>
		</td>
		<td>
			<select id="<portlet:namespace /><%= displayTerms.COUNTRY_ID %>" name="<portlet:namespace /><%= displayTerms.COUNTRY_ID %>"></select>
		</td>
		<td>
			<select id="<portlet:namespace /><%= displayTerms.REGION_ID %>" name="<portlet:namespace /><%= displayTerms.REGION_ID %>"></select>
		</td>
		<td colspan="2"></td>
	</tr>
	</table>
</liferay-ui:search-toggle>

<%
Organization organization = null;

if (displayTerms.getParentOrganizationId() > 0) {
	try {
		organization = OrganizationLocalServiceUtil.getOrganization(displayTerms.getParentOrganizationId());
	}
	catch (NoSuchOrganizationException nsoe) {
	}
}
%>

<c:if test="<%= organization != null %>">
	<input name="<portlet:namespace /><%= UserDisplayTerms.ORGANIZATION_ID %>" type="hidden" value="<%= organization.getOrganizationId() %>" />

	<br />

	<liferay-ui:message key="filter-by-organization" />: <%= HtmlUtil.escape(organization.getName()) %><br />
</c:if>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<aui:script>
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= displayTerms.NAME %>);
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= displayTerms.KEYWORDS %>);
	</aui:script>
</c:if>

<aui:script use="liferay-dynamic-select">
	new Liferay.DynamicSelect(
		[
			{
				select: '<portlet:namespace /><%= displayTerms.COUNTRY_ID %>',
				selectId: 'countryId',
				selectDesc: 'name',
				selectVal: '<%= displayTerms.getCountryId() %>',
				selectData: Liferay.Address.getCountries
			},
			{
				select: '<portlet:namespace /><%= displayTerms.REGION_ID %>',
				selectId: 'regionId',
				selectDesc: 'name',
				selectVal: '<%= displayTerms.getRegionId() %>',
				selectData: Liferay.Address.getRegions
			}
		]
	);
</aui:script>