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
List<Organization> organizations = (List<Organization>)request.getAttribute("user.organizations");
String organizationIds = (String)request.getAttribute("user.organizationIds");
List<Group> communities = (List<Group>)request.getAttribute("user.communities");

//Organizations

long[] organizationIdsArray = StringUtil.split(organizationIds, 0L);

List organizationNames = new ArrayList();
List organizationTypes = new ArrayList();

StringBuilder organizationsHTML = new StringBuilder();

if (organizations != null) {
	organizationIdsArray = new long[organizations.size()];

	Iterator itr = organizations.iterator();

	for (int i = 0; itr.hasNext(); i++) {
		Organization curOrganization = (Organization)itr.next();

		organizationIdsArray[i] = curOrganization.getOrganizationId();

		organizationNames.add(curOrganization.getName());
		organizationTypes.add(curOrganization.getType());

		if (i % 2 == 0) {
			organizationsHTML.append("<tr class=\"portlet-section-body results-row\" onmouseover=\"this.className = 'portlet-section-body-hover results-row hover';\" onmouseout=\"this.className = 'portlet-section-body results-row';\" >");
		}
		else {
			organizationsHTML.append("<tr class=\"portlet-section-alternate results-row alt\" onmouseover=\"this.className = 'portlet-section-alternate-hover results-row alt hover';\" onmouseout=\"this.className = 'portlet-section-alternate results-row alt';\" >");
		}

		organizationsHTML.append("<td align=\"left\" class=\"col-1\" colspan=\"1\" valign=\"middle\">"+curOrganization.getName()+"</td>");
		organizationsHTML.append("<td align=\"left\" class=\"col-2\" colspan=\"1\" valign=\"middle\">"+curOrganization.getType()+"</td>");
		organizationsHTML.append("<td align=\"right\" class=\"col-3\" colspan=\"1\" valign=\"middle\">");

		organizationsHTML.append("[<a href='javascript: ");
		organizationsHTML.append(renderResponse.getNamespace());
		organizationsHTML.append("removeOrganization(");
		organizationsHTML.append(i);
		organizationsHTML.append(");'>x</a>]");

		organizationsHTML.append("</td></tr>");
	}
}

else {
	organizationsHTML.append("<tr class=\"portlet-section-body results-row\"  >");
	organizationsHTML.append("<td align=\"center\" class=\"col-1\" colspan=\"3\" valign=\"middle\">No organizations were found</td></tr>");
}
%>

<script type="text/javascript">
	function <portlet:namespace />getSelectedOrganizationIds() {
		return document.<portlet:namespace />fm.<portlet:namespace />organizationIds.value;
	}

	function <portlet:namespace />removeOrganization(pos) {
		var selectedOrganizationIds = document.<portlet:namespace />fm.<portlet:namespace />organizationIds.value.split(",");
		var selectedOrganizationNames = document.<portlet:namespace />fm.<portlet:namespace />organizationNames.value.split("@@");
		var selectedOrganizationTypes = document.<portlet:namespace />fm.<portlet:namespace />organizationTypes.value.split("@@");

		selectedOrganizationIds.splice(pos, 1);
		selectedOrganizationNames.splice(pos, 1);
		selectedOrganizationTypes.splice(pos, 1);

		<portlet:namespace />updateOrganizations(selectedOrganizationIds, selectedOrganizationNames, selectedOrganizationTypes);
	}

	function <portlet:namespace />selectOrganization(organizationId, name, type) {

		var selectedOrgsIds = [];
		var selectedOrgsIdsField = document.<portlet:namespace />fm.<portlet:namespace />organizationIds.value;

		if (selectedOrgsIdsField != "") {
			selectedOrgsIds = selectedOrgsIdsField.split(",");
		}

		var selectedOrgsNames = [];
		var selectedOrgsNamesField = document.<portlet:namespace />fm.<portlet:namespace />organizationNames.value;

		if (selectedOrgsNamesField != "") {
			selectedOrgsNames = selectedOrgsNamesField.split("@@");
		}

		var selectedOrgsTypes = [];
		var selectedOrgsTypesField = document.<portlet:namespace />fm.<portlet:namespace />organizationTypes.value;

		if (selectedOrgsTypesField != "") {
			selectedOrgsTypes = selectedOrgsTypesField.split("@@");
		}

		selectedOrgsIds.push(organizationId);
		selectedOrgsNames.push(name);
		selectedOrgsTypes.push(type);

		<portlet:namespace />updateOrganizations(selectedOrgsIds, selectedOrgsNames, selectedOrgsTypes);
	}

	function <portlet:namespace />updateOrganizations(selectedOrgsIds, selectedOrgsNames, selectedOrgsTypes) {
		document.<portlet:namespace />fm.<portlet:namespace />organizationIds.value = selectedOrgsIds.join(',');
		document.<portlet:namespace />fm.<portlet:namespace />organizationNames.value = selectedOrgsNames.join('@@');
		document.<portlet:namespace />fm.<portlet:namespace />organizationTypes.value = selectedOrgsTypes.join('@@');

		var nameEl = document.getElementById("<portlet:namespace />organizationHTML");

		var organizationsHTML = '';

		if (selectedOrgsIds.length == 0){
			organizationsHTML += '<tr class="portlet-section-body results-row" ><td align="center" class="col-1" colspan="3" valign="middle">No organizations were found</td></tr>';
		}

		for (var i = 0; i < selectedOrgsIds.length; i++) {
			var id = selectedOrgsIds[i];
			var name = selectedOrgsNames[i];
			var type = selectedOrgsTypes[i];

			if (i % 2 == 0) {
				organizationsHTML += '<tr class="portlet-section-body results-row" onmouseover="this.className = \'portlet-section-body-hover results-row hover\';" onmouseout="this.className = \'portlet-section-body results-row\';" >';
			}
			else {
				organizationsHTML += '<tr class="portlet-section-alternate results-row alt" onmouseover="this.className = \'portlet-section-alternate-hover results-row alt hover\';" onmouseout="this.className = \'portlet-section-alternate results-row alt\';" >';
			}

			organizationsHTML += '<td align="left" class="col-1" colspan="1" valign="middle">' + name +'</td>';
			organizationsHTML += '<td align="left" class="col-2" colspan="1" valign="middle">' + type + '</td>';
			organizationsHTML += '<td align="right" class="col-3" colspan="1" valign="middle">';

			organizationsHTML += '[<a href="javascript:  <portlet:namespace />removeOrganization(' + i + ');">x</a>]</div>';

			organizationsHTML += '</td></tr>';
		}

		nameEl.innerHTML = organizationsHTML;
	}
</script>

<h3><liferay-ui:message key="user-associations" /></h3>

<h4><liferay-ui:message key="organizations" /></h4>

<input name="<portlet:namespace />organizationIds" type="hidden" value="<%= StringUtil.merge(organizationIdsArray) %>" />
<input name="<portlet:namespace />organizationNames" type="hidden" value='<%= StringUtil.merge(organizationNames, "@@") %>' />
<input name="<portlet:namespace />organizationTypes" type="hidden" value='<%= StringUtil.merge(organizationTypes, "@@") %>' />

<div class="results-grid" >
	<table class="taglib-search-iterator">
		<tr class="portlet-section-header results-header">

			<th class="col-1"><liferay-ui:message key="name" /></th>
			<th class="col-2"><liferay-ui:message key="type" /></th>
			<th class="col-3"></th>
		</tr>
		<tbody id="<portlet:namespace />organizationHTML">
			<%= organizationsHTML.toString() %>
		</tbody>
	</table>

</div>

<input type="button" value="<liferay-ui:message key="select" />" onClick="var organizationWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/select_organization" /><portlet:param name="tabs1" value="organizations" /></portlet:renderURL>', 'organization', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680'); void(''); organizationWindow.focus();" />

<%
StringBuilder communitiesHTML = new StringBuilder();

if (!communities.isEmpty()){
	for (int i = 0; i < communities.size(); i++) {
		Group group = communities.get(i);

		group = group.toEscapedModel();

		if (i % 2 == 0) {
			communitiesHTML.append("<tr class=\"portlet-section-body results-row\" onmouseover=\"this.className = 'portlet-section-body-hover results-row hover';\" onmouseout=\"this.className = 'portlet-section-body results-row';\" >");
		}
		else {
			communitiesHTML.append("<tr class=\"portlet-section-alternate results-row alt\" onmouseover=\"this.className = 'portlet-section-alternate-hover results-row alt hover';\" onmouseout=\"this.className = 'portlet-section-alternate results-row alt';\" >");
		}

		communitiesHTML.append("<td align=\"left\" class=\"col-1\" colspan=\"1\" valign=\"middle\">"+ group.getName()+"</td>");
		communitiesHTML.append("<td align=\"left\" class=\"col-2\" colspan=\"1\" valign=\"middle\">"+group.getTypeLabel()+"</td>");
		communitiesHTML.append("<td align=\"right\" class=\"col-3\" colspan=\"1\" valign=\"middle\">");

		communitiesHTML.append("</td></tr>");

	}
}
else {
	communitiesHTML.append("<tr class=\"portlet-section-body results-row\"  >");
	communitiesHTML.append("<td align=\"center\" class=\"col-1\" colspan=\"3\" valign=\"middle\">No communities were found</td></tr>");
}
%>

<br />
<br />

<h4><liferay-ui:message key="communities" /></h4>

<div class="results-grid" >
	<table class="taglib-search-iterator">
		<tr class="portlet-section-header results-header">

			<th class="col-1"><liferay-ui:message key="name" /></th>
			<th class="col-2"><liferay-ui:message key="type" /></th>
			<th class="col-3">&nbsp;</th>
		</tr>
		<tbody id="<portlet:namespace />communityHTML">
			<%= communitiesHTML.toString() %>
		</tbody>
	</table>
</div>