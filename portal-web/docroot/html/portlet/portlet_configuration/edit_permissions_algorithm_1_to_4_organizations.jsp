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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
String tabs3 = (String)request.getAttribute("edit_permissions_algorithm_1_to_4.jsp-tabs3");

String portletResource = (String)request.getAttribute("edit_permissions_algorithm_1_to_4.jsp-portletResource");
String modelResource = (String)request.getAttribute("edit_permissions_algorithm_1_to_4.jsp-modelResource");
long groupId = (Long)request.getAttribute("edit_permissions_algorithm_1_to_4.jsp-groupId");
Resource resource = (Resource)request.getAttribute("edit_permissions_algorithm_1_to_4.jsp-resource");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_permissions_algorithm_1_to_4.jsp-portletURL");

String organizationIds = ParamUtil.getString(request, "organizationIds");
long[] organizationIdsArray = StringUtil.split(organizationIds, 0L);
int organizationIdsPos = ParamUtil.getInteger(request, "organizationIdsPos");
%>

<aui:input name="organizationIds" type="hidden" value="<%= organizationIds %>" />
<aui:input name="organizationIdsPos" type="hidden" value="<%= organizationIdsPos %>" />
<aui:input name="organizationIdsPosValue" type="hidden" />
<aui:input name="organizationIdActionIds" type="hidden" />

<c:choose>
	<c:when test="<%= organizationIdsArray.length == 0 %>">
		<liferay-ui:tabs
			names="current,available"
			param="tabs3"
			url="<%= portletURL.toString() %>"
		/>

		<liferay-ui:search-container
			rowChecker="<%= new RowChecker(renderResponse) %>"
			searchContainer="<%= new OrganizationSearch(renderRequest, portletURL) %>"
		>
			<liferay-ui:search-form
				page="/html/portlet/enterprise_admin/organization_search.jsp"
			/>

			<%
			OrganizationSearchTerms searchTerms = (OrganizationSearchTerms)searchContainer.getSearchTerms();

			long parentOrganizationId = OrganizationConstants.ANY_PARENT_ORGANIZATION_ID;

			LinkedHashMap organizationParams = new LinkedHashMap();

			if (tabs3.equals("current")) {
				organizationParams.put("permissionsResourceId", new Long(resource.getResourceId()));
				organizationParams.put("permissionsGroupId", new Long(groupId));
			}
			%>

			<liferay-ui:search-container-results>
				<%@ include file="/html/portlet/enterprise_admin/organization_search_results.jspf" %>
			</liferay-ui:search-container-results>

			<liferay-ui:search-container-row
				className="com.liferay.portal.model.Organization"
				escapedModel="<%= true %>"
				keyProperty="organizationId"
				modelVar="organization"
			>
				<liferay-ui:search-container-column-text
					name="name"
					orderable="<%= true %>"
					property="name"
				/>

				<liferay-ui:search-container-column-text
					buffer="buffer"
					name="parent-organization"
				>

					<%
					if (organization.getParentOrganizationId() > 0) {
						try {
							Organization parentOrganization = OrganizationLocalServiceUtil.getOrganization(organization.getParentOrganizationId());

							buffer.append(HtmlUtil.escape(parentOrganization.getName()));
						}
						catch (Exception e) {
						}
					}
					%>

				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					name="type"
					orderable="<%= true %>"
					value="<%= LanguageUtil.get(pageContext, organization.getType()) %>"
				/>

				<liferay-ui:search-container-column-text
					name="city"
					value="<%= organization.getAddress().getCity() %>"
				/>

				<liferay-ui:search-container-column-text
					buffer="buffer"
					name="permissions"
				>

					<%

					//boolean organizationIntersection = false;

					List permissions = PermissionLocalServiceUtil.getGroupPermissions(organization.getGroup().getGroupId(), resource.getResourceId());

					/*if (permissions.isEmpty()) {
						permissions = PermissionLocalServiceUtil.getOrgGroupPermissions(organization.getOrganizationId(), groupId, resource.getResourceId());

						if (!permissions.isEmpty()) {
							organizationIntersection = true;
						}
					}*/

					List actions = ResourceActionsUtil.getActions(permissions);
					List actionsNames = ResourceActionsUtil.getActionsNames(pageContext, actions);

					buffer.append(StringUtil.merge(actionsNames, ", "));

					/*if (permissions.isEmpty()) {
						row.addText(StringPool.BLANK);
					}
					else {
						row.addText(LanguageUtil.get(pageContext, (organizationIntersection ? "yes" : "no")));
					}*/
					%>

				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<div class="separator"><!-- --></div>

			<aui:button onClick='<%= renderResponse.getNamespace() + "updateOrganizationPermissions();" %>' value="update-permissions" />

			<br /><br />

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</c:when>
	<c:otherwise>

		<%
		Organization organization = OrganizationLocalServiceUtil.getOrganization(organizationIdsArray[organizationIdsPos]);
		%>

		<liferay-ui:tabs names="<%= HtmlUtil.escape(organization.getName()) %>" />

		<%
		List permissions = PermissionLocalServiceUtil.getGroupPermissions(organization.getGroup().getGroupId(), resource.getResourceId());

		List actions1 = ResourceActionsUtil.getResourceActions(portletResource, modelResource);
		List actions2 = ResourceActionsUtil.getActions(permissions);

		// Left list

		List leftList = new ArrayList();

		for (int i = 0; i < actions2.size(); i++) {
			String actionId = (String)actions2.get(i);

			leftList.add(new KeyValuePair(actionId, ResourceActionsUtil.getAction(pageContext, actionId)));
		}

		leftList = ListUtil.sort(leftList, new KeyValuePairComparator(false, true));

		// Right list

		List rightList = new ArrayList();

		for (int i = 0; i < actions1.size(); i++) {
			String actionId = (String)actions1.get(i);

			if (!actions2.contains(actionId)) {
				rightList.add(new KeyValuePair(actionId, ResourceActionsUtil.getAction(pageContext, actionId)));
			}
		}

		rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
		%>

		<div class="assign-permissions">
			<liferay-ui:input-move-boxes
				formName="fm"
				leftTitle="what-they-can-do"
				rightTitle="what-they-cant-do"
				leftBoxName="current_actions"
				rightBoxName="available_actions"
				leftList="<%= leftList %>"
				rightList="<%= rightList %>"
			/>

			<aui:button-row>

				<%
				String previousOnClick = renderResponse.getNamespace() + "saveOrganizationPermissions(" + (organizationIdsPos - 1) + ", '" + organizationIdsArray[organizationIdsPos] + "');";
				String nextOnClick = renderResponse.getNamespace() + "saveOrganizationPermissions(" + (organizationIdsPos + 1) + ", '" + organizationIdsArray[organizationIdsPos] + "');";
				String finishedOnClick = renderResponse.getNamespace() + "saveOrganizationPermissions(-1, '"+ organizationIdsArray[organizationIdsPos] + "');";
				%>

				<aui:button cssClass="previous" disabled="<%= organizationIdsPos <= 0 %>" onClick='<%= previousOnClick %>' value="previous" />

				<aui:button cssClass="next" disabled="<%= organizationIdsPos + 1 >= organizationIdsArray.length %>" onClick='<%= nextOnClick %>' value="next" />

				<aui:button cssClass="finished" onClick="<%= finishedOnClick %>" value="finished"  />
			</aui:button-row>
		</div>

		<%--<table class="lfr-table">
		<tr>
			<td>
				<liferay-ui:message key="assign-permissions-only-to-users-that-are-also-members-of-the-current-community" />
			</td>
			<td>
				<select name="<portlet:namespace />organizationIntersection">
					<option <%= organizationIntersection ? "selected" : "" %> value="1"><liferay-ui:message key="yes" /></option>
					<option <%= !organizationIntersection ? "selected" : "" %> value="0"><liferay-ui:message key="no" /></option>
				</select>
			</td>
		</tr>
		</table>

		<br />--%>
	</c:otherwise>
</c:choose>