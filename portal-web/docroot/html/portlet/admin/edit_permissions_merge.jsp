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

<c:choose>
	<c:when test='<%= tabs2.equals("users") %>'>
		<%
		MultiValueMap mvp = new MultiValueMap();

		List<Role> roles = RoleLocalServiceUtil.getRoles(RoleConstants.TYPE_REGULAR, "lfr-permission-algorithm-5");

		for (Iterator itr = roles.iterator(); itr.hasNext(); ) {
			Role role = (Role)itr.next();

			List entries = UserLocalServiceUtil.getRoleUsers(role.getRoleId());

			if (entries.size() > 1) {
				continue;
			}

			Object entry = entries.get(0);

			mvp.put(entry, role);
		}

		for (Iterator itr = mvp.keySet().iterator(); itr.hasNext(); ) {
			Object key = itr.next();

			if (mvp.size(key) == 1) {
				itr.remove();
			}
		}

		List entries = new ArrayList(mvp.keySet());
		%>

		<liferay-ui:search-container
			searchContainer="<%= new SearchContainer(renderRequest, portletURL, null, "no-generated-roles-to-merge") %>"
		>
			<liferay-ui:search-container-results
				total="<%= entries.size() %>"
				results="<%= ListUtil.subList(entries, searchContainer.getStart(), searchContainer.getEnd()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.model.User"
				escapedModel="<%= true %>"
				keyProperty="userId"
				modelVar="user2"
			>
				<%
				Collection<Role> roles2 = mvp.getCollection(user2);

				List<Long> roleIds = new ArrayList<Long>();

				for (Role role : roles2) {
					roleIds.add(role.getRoleId());
				}
				%>

				<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editEntryURL">
					<portlet:param name="struts_action" value="/admin_server/edit_user" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="tabs1Names" value="users" />
					<portlet:param name="p_u_i_d" value="<%= String.valueOf(user2.getUserId()) %>" />
				</portlet:renderURL>

				<liferay-ui:search-container-column-text
					href="<%= editEntryURL %>"
					name="name"
					value="<%= user2.getScreenName() %>"
				/>

				<liferay-ui:search-container-column-text
					buffer="buffer"
					name="role"
				>
					<%
					PortletURL editRoleURL = renderResponse.createRenderURL();

					editRoleURL.setWindowState(WindowState.MAXIMIZED);

					editRoleURL.setParameter("struts_action", "/admin_server/edit_role_permissions");
					editRoleURL.setParameter(Constants.CMD, Constants.VIEW);
					editRoleURL.setParameter("redirect", currentURL);

					for (Role role : roles2) {
						editRoleURL.setParameter("roleId", String.valueOf(role.getRoleId()));

						buffer.append("<a href=\"" + editRoleURL.toString() + "\">" + role.getName() + "</a><br />");
					}
					%>
				</liferay-ui:search-container-column-text>

				<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="mergeURL">
					<portlet:param name="struts_action" value="/admin_server/edit_permissions" />
					<portlet:param name="<%= Constants.CMD %>" value="merge" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="roleIds" value="<%= StringUtil.merge(roleIds) %>" />
				</portlet:actionURL>

				<liferay-ui:search-container-column-button
					align="right"
					href="<%= renderResponse.getNamespace() + "invoke('" + mergeURL + "')" %>"
					name="<%= LanguageUtil.get(pageContext, "merge") %>"
				/>
			</liferay-ui:search-container-row>
			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</c:when>
	<c:otherwise>
		<%
		MultiValueMap mvp = new MultiValueMap();

		List<Role> roles = RoleLocalServiceUtil.getRoles(RoleConstants.TYPE_COMMUNITY, "lfr-permission-algorithm-5");

		for (Iterator itr = roles.iterator(); itr.hasNext(); ) {
			Role role = (Role)itr.next();

			List entries = GroupLocalServiceUtil.getRoleGroups(role.getRoleId());

			if (entries.size() > 1) {
				continue;
			}

			Group group = (Group)entries.get(0);

			if (tabs2.equals("organizations") && !group.isOrganization()) {
				continue;
			}
			else if (tabs2.equals("communities") && !group.isCommunity()) {
				continue;
			}

			mvp.put(group, role);
		}

		for (Iterator itr = mvp.keySet().iterator(); itr.hasNext(); ) {
			Object key = itr.next();

			if (mvp.size(key) == 1) {
				itr.remove();
			}
		}

		List entries = new ArrayList(mvp.keySet());
		%>

		<liferay-ui:search-container
			searchContainer="<%= new SearchContainer(renderRequest, portletURL, null, "no-generated-roles-to-merge") %>"
		>
			<liferay-ui:search-container-results
				total="<%= entries.size() %>"
				results="<%= ListUtil.subList(entries, searchContainer.getStart(), searchContainer.getEnd()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.model.Group"
				escapedModel="<%= true %>"
				keyProperty="groupId"
				modelVar="group"
			>
				<%
				Collection<Role> roles2 = mvp.getCollection(group);

				List<Long> roleIds = new ArrayList<Long>();

				for (Role role : roles2) {
					roleIds.add(role.getRoleId());
				}

				String entryName = group.getName();

				PortletURL editEntryURL = renderResponse.createRenderURL();

				editEntryURL.setWindowState(WindowState.MAXIMIZED);
				editEntryURL.setParameter("redirect", currentURL);

				if (group.isCommunity()) {
					editEntryURL.setParameter("struts_action", "/admin_server/edit_community");
					editEntryURL.setParameter("groupId", String.valueOf(group.getGroupId()));
				}
				else if (group.isOrganization()) {
					Organization organization = OrganizationLocalServiceUtil.getOrganization(group.getClassPK());
					entryName = organization.getName();

					editEntryURL.setParameter("struts_action", "/admin_server/edit_organization");
					editEntryURL.setParameter("tabs1Names", "organizations");
					editEntryURL.setParameter("organizationId", String.valueOf(organization.getOrganizationId()));
				}
				%>

				<liferay-ui:search-container-column-text
					href="<%= editEntryURL %>"
					name="name"
					value="<%= entryName %>"
				/>

				<liferay-ui:search-container-column-text
					buffer="buffer"
					name="role"
				>
					<%
					PortletURL editRoleURL = renderResponse.createRenderURL();

					editRoleURL.setWindowState(WindowState.MAXIMIZED);

					editRoleURL.setParameter("struts_action", "/admin_server/edit_role_permissions");
					editRoleURL.setParameter(Constants.CMD, Constants.VIEW);
					editRoleURL.setParameter("redirect", currentURL);

					for (Role role : roles2) {
						editRoleURL.setParameter("roleId", String.valueOf(role.getRoleId()));

						buffer.append("<a href=\"" + editRoleURL.toString() + "\">" + role.getName() + "</a><br />");
					}
					%>
				</liferay-ui:search-container-column-text>

				<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="mergeURL">
					<portlet:param name="struts_action" value="/admin_server/edit_permissions" />
					<portlet:param name="<%= Constants.CMD %>" value="merge" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="roleIds" value="<%= StringUtil.merge(roleIds) %>" />
				</portlet:actionURL>

				<liferay-ui:search-container-column-button
					align="right"
					href="<%= renderResponse.getNamespace() + "invoke('" + mergeURL + "')" %>"
					name="<%= LanguageUtil.get(pageContext, "merge") %>"
				/>
			</liferay-ui:search-container-row>
			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</c:otherwise>
</c:choose>