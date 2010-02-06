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
Organization organization = (Organization)request.getAttribute(WebKeys.ORGANIZATION);

long parentOrganizationId = ParamUtil.getLong(request, "parentOrganizationSearchContainerPrimaryKeys", (organization != null) ? organization.getParentOrganizationId() : OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);

String parentOrganizationName = ParamUtil.getString(request, "parentOrganizationName");

if (parentOrganizationId <= 0) {
	parentOrganizationId = OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID;

	if (organization != null) {
		parentOrganizationId = organization.getParentOrganizationId();
	}
}

String type = BeanParamUtil.getString(organization, request, "type", PropsValues.ORGANIZATIONS_TYPES[0]);
long regionId = BeanParamUtil.getLong(organization, request, "regionId");
long countryId = BeanParamUtil.getLong(organization, request, "countryId");
int statusId = BeanParamUtil.getInteger(organization, request, "statusId");

boolean deleteLogo = ParamUtil.getBoolean(request, "deleteLogo");

long groupId = 0;

if (organization != null) {
	groupId = organization.getGroup().getGroupId();
}
%>

<liferay-util:buffer var="removeOrganizationIcon">
	<liferay-ui:icon image="unlink" message="remove" label="<%= true %>" />
</liferay-util:buffer>

<liferay-ui:error-marker key="errorSection" value="details" />

<aui:model-context bean="<%= organization %>" model="<%= Organization.class %>" />

<h3><liferay-ui:message key="details" /></h3>

<aui:fieldset column="<%= true %>">
	<liferay-ui:error exception="<%= DuplicateOrganizationException.class %>" message="the-organization-name-is-already-taken" />
	<liferay-ui:error exception="<%= OrganizationNameException.class %>" message="please-enter-a-valid-name" />

	<aui:input name="name" />

	<c:choose>
		<c:when test="<%= PropsValues.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_ORGANIZATION_STATUS %>">
			<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + Organization.class.getName() + ListTypeConstants.ORGANIZATION_STATUS %>" message="please-select-a-type" />

			<aui:select label="status" name="statusId" listType="<%= ListTypeConstants.ORGANIZATION_STATUS %>" showEmptyOption="<%= true %>" />
		</c:when>
		<c:otherwise>
			<aui:input name="statusId" type="hidden" value="<%= (organization != null) ? organization.getStatusId() : ListTypeConstants.ORGANIZATION_STATUS_DEFAULT %>" />
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="<%= organization == null %>">
			<aui:select name="type">

				<%
				for (String curType : PropsValues.ORGANIZATIONS_TYPES) {
				%>

					<aui:option label="<%= curType %>" selected="<%= type.equals(curType) %>" />

				<%
				}
				%>

			</aui:select>
		</c:when>
		<c:otherwise>
			<aui:field-wrapper label="type">
				<liferay-ui:message key="<%= organization.getType() %>" />
			</aui:field-wrapper>

			<aui:input name="type" type="hidden" value="<%= organization.getType() %>" />
		</c:otherwise>
	</c:choose>

	<liferay-ui:error exception="<%= NoSuchCountryException.class %>" message="please-select-a-country" />

	<div id="<portlet:namespace />countryDiv" <%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.ORGANIZATIONS_COUNTRY_ENABLED, new Filter(String.valueOf(type))))? StringPool.BLANK : "style=\"display: none;\"" %>>
		<aui:select name="countryId" />

		<aui:select name="regionId" />
	</div>

	<c:if test="<%= organization != null %>">
		<aui:field-wrapper label="group-id">
			<%= groupId %>
		</aui:field-wrapper>
	</c:if>
</aui:fieldset>

<aui:fieldset column="<%= true %>">
	<div>
		<c:if test="<%= organization != null %>">

			<%
			LayoutSet publicLayoutSet =	LayoutSetLocalServiceUtil.getLayoutSet(groupId, false);
			LayoutSet privateLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(groupId, true);
			%>

			<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="editOrganizationLogoURL">
				<portlet:param name="struts_action" value="/enterprise_admin/edit_organization_logo" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
				<portlet:param name="publicLayoutSetId" value="<%= String.valueOf(publicLayoutSet.getLayoutSetId()) %>" />
			</portlet:renderURL>

			<%
			String taglibEditURL = "javascript:" + renderResponse.getNamespace() + "openEditOrganizationLogoWindow('" + editOrganizationLogoURL + "');";

			long logoId = organization.getLogoId();
			%>

			<aui:a cssClass="change-avatar" href="<%= taglibEditURL %>">
				<img alt="<liferay-ui:message key="logo" />" class="avatar" id="<portlet:namespace />avatar" src="<%= themeDisplay.getPathImage() %>/organization_logo?img_id=<%= deleteLogo ? 0 : logoId %>&t=<%= ImageServletTokenUtil.getToken(logoId) %>" />
			</aui:a>

			<div class="portrait-icons">

				<liferay-ui:icon image="edit" message="change" url="<%= taglibEditURL %>" label="<%= true %>" />

				<c:if test="<%= logoId != 0 %>">

					<%
					String taglibDeleteURL = "javascript:" + renderResponse.getNamespace() + "deleteLogo('" + themeDisplay.getPathImage() + "/organization_logo?img_id=0');";
					%>

					<liferay-ui:icon image="delete" url="<%= taglibDeleteURL %>" label="<%= true %>" cssClass="modify-link" />

					<aui:input name="deleteLogo" type="hidden" value="<%= deleteLogo %>" />
				</c:if>
			</div>
		</c:if>
	</div>
</aui:fieldset>

<%
Organization parentOrganization = null;

if ((parentOrganizationId == OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) && !permissionChecker.isCompanyAdmin()) {
	List<Organization> manageableOrganizations = new ArrayList<Organization>();

	for (Organization curOrganization : user.getOrganizations()) {
		if (OrganizationPermissionUtil.contains(permissionChecker, curOrganization.getOrganizationId(), ActionKeys.MANAGE_SUBORGANIZATIONS)) {
			manageableOrganizations.add(curOrganization);
		}
	}

	if (manageableOrganizations.size() == 1) {
		parentOrganizationId = manageableOrganizations.get(0).getOrganizationId();
	}
}

if (parentOrganizationId != OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {
	try {
		parentOrganization = OrganizationLocalServiceUtil.getOrganization(parentOrganizationId);

		parentOrganizationName = parentOrganization.getName();
	}
	catch (NoSuchOrganizationException nsoe) {
	}
}

List<Organization> parentOrganizations = new ArrayList<Organization>();

if (parentOrganization != null) {
	parentOrganizations.add(parentOrganization);
}
%>

<h3><liferay-ui:message key="parent-organization" /></h3>

<liferay-ui:error exception="<%= OrganizationParentException.class %>" message="please-enter-a-valid-parent-organization" />

<liferay-ui:search-container
	headerNames="name,type"
	id='<%= renderResponse.getNamespace() + "parentOrganizationSearchContainer" %>'
>
	<liferay-ui:search-container-results
		results="<%= parentOrganizations %>"
		total="<%= parentOrganizations.size() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Organization"
		escapedModel="<%= true %>"
		keyProperty="organizationId"
		modelVar="curOrganization"
	>
		<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="rowURL">
			<portlet:param name="struts_action" value="/enterprise_admin/edit_organization" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="organizationId" value="<%= String.valueOf(curOrganization.getOrganizationId()) %>" />
		</portlet:renderURL>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="name"
			property="name"
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="type"
			value="<%= LanguageUtil.get(pageContext, curOrganization.getType()) %>"
		/>

		<liferay-ui:search-container-column-text>
			<a class="modify-link" data-rowId="<%= curOrganization.getOrganizationId() %>" href="javascript:;"><%= removeOrganizationIcon %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<br />

<liferay-ui:icon
	image="add"
	message="select"
	url='<%= "javascript:" + renderResponse.getNamespace() + "openOrganizationSelector();" %>'
	label="<%= true %>"
	cssClass="modify-link"
/>

<aui:script>
	function <portlet:namespace />changeLogo(newLogoURL) {
		AUI().one('#<portlet:namespace />avatar').attr('src', newLogoURL);
		AUI().one('.avatar').attr('src', newLogoURL);

		AUI().one('#<portlet:namespace />deleteLogo').val(false);
	}

	function <portlet:namespace />deleteLogo(defaultLogoURL) {
		AUI().one('#<portlet:namespace />deleteLogo').val(true);

		AUI().one('#<portlet:namespace />avatar').attr('src', defaultLogoURL);
		AUI().one('.avatar').attr('src', defaultLogoURL);
	}

	function <portlet:namespace />openEditOrganizationLogoWindow(editOrganizationLogoURL) {
		var editOrganizationLogoWindow = window.open(editOrganizationLogoURL, '<liferay-ui:message key="change" />', 'directories=no,height=400,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=500');

		editOrganizationLogoWindow.focus();
	}

	function <portlet:namespace />openOrganizationSelector() {
		var url = '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/select_organization" /></portlet:renderURL>';

		<c:choose>
			<c:when test="<%= organization == null %>">
				var type = document.<portlet:namespace />fm.<portlet:namespace />type.value;
			</c:when>
			<c:otherwise>
				var type = '<%= type %>';
			</c:otherwise>
		</c:choose>

		var organizationWindow = window.open(url, 'organization', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680');

		organizationWindow.focus();
	}

	function <portlet:namespace />selectOrganization(organizationId, name, type) {
		AUI().use(
			'liferay-search-container',
			function(A) {
				var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />parentOrganizationSearchContainer');

				var rowColumns = [];

				var href = "<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_organization" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>&<portlet:namespace />organizationId=" + organizationId;

				rowColumns.push(<portlet:namespace />createURL(href, name));
				rowColumns.push(<portlet:namespace />createURL(href, type));
				rowColumns.push('<a class="modify-link" data-rowId="' + organizationId + '" href="javascript:;"><%= UnicodeFormatter.toString(removeOrganizationIcon) %></a>');

				searchContainer.deleteRow(1, searchContainer.getData());
				searchContainer.addRow(rowColumns, organizationId);
				searchContainer.updateDataStore(organizationId);

				<portlet:namespace />trackChanges();
			}
		);
	}

	function <portlet:namespace />trackChanges() {
		AUI().use(
			'event',
			function(A) {
				A.fire(
					'enterpriseAdmin:trackChanges',
					A.one('.selected .modify-link')
				);
			}
		);
	}
</aui:script>

<aui:script use="event,node">
	var modifyLinks = A.all('span.modify-link');

	if (modifyLinks) {
		modifyLinks.on(
			'click',
			function() {
				<portlet:namespace />trackChanges();
			}
		);
	}
</aui:script>

<aui:script use="liferay-dynamic-select,liferay-search-container">
	new Liferay.DynamicSelect(
		[
			{
				select: "<portlet:namespace />countryId",
				selectId: "countryId",
				selectDesc: "name",
				selectVal: "<%= countryId %>",
				selectData: Liferay.Address.getCountries
			},
			{
				select: "<portlet:namespace />regionId",
				selectId: "regionId",
				selectDesc: "name",
				selectVal: "<%= regionId %>",
				selectData: Liferay.Address.getRegions
			}
		]
	);

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />parentOrganizationSearchContainer');

	searchContainer.get('contentBox').delegate(
		'click',
		function(event) {
			var link = event.currentTarget;
			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));

			<portlet:namespace />trackChanges();
		},
		'.modify-link'
	);
</aui:script>

<c:if test="<%= organization == null %>">
	<aui:script use="node">
		A.one('#<portlet:namespace />type').on(
			'change',
			function(event) {

				<%
				for (String curType : PropsValues.ORGANIZATIONS_TYPES) {
				%>

					if (event.target.val() == '<%= curType %>') {
						A.one('#<portlet:namespace />countryDiv').<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.ORGANIZATIONS_COUNTRY_ENABLED, new Filter(String.valueOf(curType)))) ? "show" : "hide" %>();
					}

				<%
				}
				%>

			}
		);
	</aui:script>
</c:if>