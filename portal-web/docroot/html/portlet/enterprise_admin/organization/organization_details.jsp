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
Organization organization = (Organization)request.getAttribute("organization.selOrganization");

int statusId = BeanParamUtil.getInteger(organization, request, "statusId");
long countryId = BeanParamUtil.getLong(organization, request, "countryId");
long regionId = BeanParamUtil.getLong(organization, request, "regionId");
String type = BeanParamUtil.getString(organization, request, "type", PropsValues.ORGANIZATIONS_TYPES[0]);
long parentOrganizationId = BeanParamUtil.getLong(organization, request, "parentOrganizationId");

String parentOrganizationName = ParamUtil.getString(request, "parentOrganizationName");

if (parentOrganizationId <= 0) {
	parentOrganizationId = OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID;

	if (organization != null) {
		parentOrganizationId = organization.getParentOrganizationId();
	}
}
%>

<script type="text/javascript">
	function <portlet:namespace />saveOrganization(cmd) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= organization == null ? Constants.ADD : Constants.UPDATE %>";

		if (document.<portlet:namespace />fm.<portlet:namespace />websiteSuffixes) {
			document.<portlet:namespace />fm.<portlet:namespace />websiteSuffixes.value = websiteSuffixesArray.join(',');
		}

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />openOrganizationSelector() {
		var url = '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/select_organization" /><portlet:param name="tabs1" value="organizations" /><portlet:param name="parentType" value="" /></portlet:renderURL>';

		<c:choose>
			<c:when test="<%= organization == null %>">
				var type = document.<portlet:namespace />fm.<portlet:namespace />type.value;
			</c:when>
			<c:otherwise>
				var type = '<%= type %>';
			</c:otherwise>
		</c:choose>

		url = Liferay.Util.addParams('childType=' + type, url);

		var organizationWindow = window.open(url, 'organization', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680');

		organizationWindow.focus();
	}

	function <portlet:namespace />removeOrganization() {
		document.<portlet:namespace />fm.<portlet:namespace />parentOrganizationId.value = "";

		var nameEl = document.getElementById("<portlet:namespace />parentOrganizationHTML");

		nameEl.href = "#";

		var parentOrganizationHTML = '';

		parentOrganizationHTML += '<tr class="portlet-section-body results-row">';
		parentOrganizationHTML += '<td align="center" class="col-1" colspan="3" valign="middle"><liferay-ui:message key="this-organization-does-not-have-a-parent" /></td></tr>';

		nameEl.innerHTML = parentOrganizationHTML;

		document.getElementById("<portlet:namespace />removeOrganizationButton").disabled = true;
	}

	function <portlet:namespace />selectOrganization(organizationId, name, type) {
		document.<portlet:namespace />fm.<portlet:namespace />parentOrganizationId.value = organizationId;

		var nameEl = document.getElementById("<portlet:namespace />parentOrganizationHTML");

		var href = "<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_organization" /><portlet:param name="backURL" value="<%= currentURL %>" /></portlet:renderURL>&<portlet:namespace />organizationId=" + organizationId;

		var parentOrganizationHTML = '';

		parentOrganizationHTML += '<tr class="portlet-section-body results-row" onmouseover="this.className = \'portlet-section-body-hover results-row hover\';" onmouseout="this.className = \'portlet-section-body results-row\';" >';
		parentOrganizationHTML += '<td align="left" class="col-1" colspan="1" valign="middle">';
		parentOrganizationHTML += '<a href="' + href + '">' + name +'</a></td>';
		parentOrganizationHTML += '<td align="left" class="col-2" colspan="1" valign="middle">' + type + '</td>';
		parentOrganizationHTML += '<td align="right" class="col-3" colspan="1" valign="middle">';
		parentOrganizationHTML += '[<a href="javascript:  <portlet:namespace />removeOrganization();">x</a>]</div>';
		parentOrganizationHTML += '</td></tr>';

		nameEl.innerHTML = parentOrganizationHTML;
	}
</script>

<h3><liferay-ui:message key="organization-details" /></h3>

<fieldset class="block-labels col">
	<div class="ctrl-holder">
		<label for="<portlet:namespace />name"><liferay-ui:message key="name" /></label>

		<liferay-ui:input-field model="<%= Organization.class %>" bean="<%= organization %>" field="name" />
	</div>

	<div class="ctrl-holder">
		<label for="<portlet:namespace />type"><liferay-ui:message key="type" /></label>
		<c:choose>
			<c:when test="<%= organization == null %>">
				<select id="<portlet:namespace />type" name="<portlet:namespace />type">
					<%
					for (String curType : PropsValues.ORGANIZATIONS_TYPES) {
					%>
						<option <%= (type.equals(curType)) ? "selected" : "" %> value="<%= curType %>"><liferay-ui:message key="<%= curType %>" /></option>
					<%
					}
					%>
				</select>
			</c:when>
			<c:otherwise>
				<%= LanguageUtil.get(pageContext, organization.getType()) %>
				<input name="<portlet:namespace />type" type="hidden" value="<%= organization.getType() %>" />
			</c:otherwise>
		</c:choose>
	</div>

	<c:if test="<%= organization != null %>">
		<div class="ctrl-holder">
			<label for="<portlet:namespace />groupId"><liferay-ui:message key="group-id" /></label>
			<%= organization.getGroup().getGroupId() %>
			<input name="<portlet:namespace />groupId" type="hidden" value="<%= organization.getGroup().getGroupId() %>" />
		</div>
	</c:if>
</fieldset>

<fieldset class="block-labels col">
	<c:choose>
		<c:when test="<%= PropsValues.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_ORGANIZATION_STATUS %>">
			<div class="ctrl-holder">
				<label for="<portlet:namespace />statusId"><liferay-ui:message key="status" /></label>

				<select name="<portlet:namespace />statusId">
					<option value=""></option>

					<%
					List statuses = ListTypeServiceUtil.getListTypes(ListTypeImpl.ORGANIZATION_STATUS);

					for (int i = 0; i < statuses.size(); i++) {
						ListType status = (ListType)statuses.get(i);
					%>

					<option <%= status.getListTypeId() == statusId ? "selected" : "" %> value="<%= String.valueOf(status.getListTypeId()) %>"><%= LanguageUtil.get(pageContext, status.getName()) %></option>

					<%
					}
					%>

				</select>
			</div>
		</c:when>
		<c:otherwise>
			<input name="<portlet:namespace />statusId" type="hidden" value="<%= (organization != null) ? organization.getStatusId() : ListTypeImpl.ORGANIZATION_STATUS_DEFAULT %>" />
		</c:otherwise>
	</c:choose>

	<div id="<portlet:namespace />countryDiv" <%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.ORGANIZATIONS_COUNTRY_ENABLED, new Filter(String.valueOf(type))))? StringPool.BLANK : "style=\"display: none\"" %>>
		<div class="ctrl-holder">
			<label for="<portlet:namespace />countryId"><liferay-ui:message key="country" /> </label>

			<select id="<portlet:namespace />countryId" name="<portlet:namespace />countryId"></select>
		</div>

		<div class="ctrl-holder">
			<label for="<portlet:namespace />regionId"><liferay-ui:message key="region" /></label>

			<select id="<portlet:namespace />regionId" name="<portlet:namespace />regionId"></select>
		</div>
	</div>
</fieldset>

<h3><liferay-ui:message key="parent-organization" /></h3>

<%
Organization parentOrganization = null;

if (parentOrganizationId != OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {
	try {
		parentOrganization = OrganizationLocalServiceUtil.getOrganization(parentOrganizationId);

		parentOrganizationName = parentOrganization.getName();
	}
	catch (NoSuchOrganizationException nsoe) {
	}
}

StringBuilder parentOrganizationHTML = new StringBuilder();

if (parentOrganization != null) {
	PortletURL parentOrganizationURL = renderResponse.createRenderURL();
	parentOrganizationURL.setParameter("struts_action", "/enterprise_admin/edit_organization");
	parentOrganizationURL.setParameter("backURL", currentURL);
	parentOrganizationURL.setParameter("organizationId", String.valueOf(parentOrganization.getOrganizationId()));

	parentOrganizationHTML.append("<tr class=\"portlet-section-body results-row\" onmouseover=\"this.className = 'portlet-section-body-hover results-row hover';\" onmouseout=\"this.className = 'portlet-section-body results-row';\" >");

	parentOrganizationHTML.append("<td align=\"left\" class=\"col-2\" colspan=\"1\" valign=\"middle\"><a href=\"");
	parentOrganizationHTML.append(parentOrganizationURL.toString());
	parentOrganizationHTML.append("\">");
	parentOrganizationHTML.append(parentOrganization.getName());
	parentOrganizationHTML.append("</a></td>");
	parentOrganizationHTML.append("<td align=\"left\" class=\"col-1\" colspan=\"1\" valign=\"middle\">");
	parentOrganizationHTML.append(parentOrganization.getType());
	parentOrganizationHTML.append("</td>");
	parentOrganizationHTML.append("<td align=\"right\" class=\"col-3\" colspan=\"1\" valign=\"middle\">");

	parentOrganizationHTML.append("[<a href='javascript: ");
	parentOrganizationHTML.append(renderResponse.getNamespace());
	parentOrganizationHTML.append("removeOrganization();'>x</a>]");

	parentOrganizationHTML.append("</td></tr>");
}
else {
	parentOrganizationHTML.append("<tr class=\"portlet-section-body results-row\"  >");
	parentOrganizationHTML.append("<td align=\"center\" class=\"col-1\" colspan=\"3\" valign=\"middle\">");
	parentOrganizationHTML.append(LanguageUtil.get(pageContext, "this-organization-does-not-have-a-parent"));
	parentOrganizationHTML.append("</td></tr>");
}
%>

<input name="<portlet:namespace />parentOrganizationId" type="hidden" value="<%= parentOrganizationId %>" />

<div class="results-grid" >
	<table class="taglib-search-iterator">
		<tr class="portlet-section-header results-header">

			<th class="col-1"><liferay-ui:message key="name" /></th>
			<th class="col-2"><liferay-ui:message key="type" /></th>
			<th class="col-3"></th>
		</tr>
		<tbody id="<portlet:namespace />parentOrganizationHTML">
			<%= parentOrganizationHTML.toString() %>
		</tbody>
	</table>
</div>

<input type="button" value="<liferay-ui:message key="select" />" onClick="<portlet:namespace />openOrganizationSelector();" />

<script type="text/javascript">
	jQuery('#<portlet:namespace />type').change(
		function(event) {
			<%
			for (String curType : PropsValues.ORGANIZATIONS_TYPES) {
			%>
				if (this.value == '<%= curType %>'){
					jQuery('#<portlet:namespace />countryDiv').<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.ORGANIZATIONS_COUNTRY_ENABLED, new Filter(String.valueOf(curType)))) ? "show" : "hide" %>();
				}
			<%
			}
			%>
		}
	);

	new Liferay.DynamicSelect(
	[
		{
			select: "<portlet:namespace />countryId",
			selectId: "countryId",
			selectDesc: "name",
			selectVal: "<%= countryId %>",
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
			select: "<portlet:namespace />regionId",
			selectId: "regionId",
			selectDesc: "name",
			selectVal: "<%= regionId %>",
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
</script>