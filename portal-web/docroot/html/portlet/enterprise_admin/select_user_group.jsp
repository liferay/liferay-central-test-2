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

<aui:form method="post" name="fm">
	<liferay-ui:tabs names="user-groups" />

	<%
	String target = ParamUtil.getString(request, "target");

	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/enterprise_admin/select_user_group");
	%>

	<liferay-ui:search-container
		searchContainer="<%= new UserGroupSearch(renderRequest, portletURL) %>"
	>
		<liferay-ui:search-form
			page="/html/portlet/enterprise_admin/user_group_search.jsp"
		/>

		<%
		UserGroupSearchTerms searchTerms = (UserGroupSearchTerms)searchContainer.getSearchTerms();

		LinkedHashMap userGroupParams = new LinkedHashMap();
		%>

		<liferay-ui:search-container-results>

			<%
			if (filterManageableUserGroups) {
				List<UserGroup> userGroups = UserGroupLocalServiceUtil.getUserGroups(company.getCompanyId());

				userGroups = EnterpriseAdminUtil.filterUserGroups(permissionChecker, userGroups);

				total = userGroups.size();
				results = ListUtil.subList(userGroups, searchContainer.getStart(), searchContainer.getEnd());
			}
			else {
				results = UserGroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), userGroupParams, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
				total = UserGroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), userGroupParams);
			}

			pageContext.setAttribute("results", results);
			pageContext.setAttribute("total", total);
			%>

		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.UserGroup"
			escapedModel="<%= true %>"
			keyProperty="userGroupId"
			modelVar="userGroup"
		>

			<%
			StringBuilder sb = new StringBuilder();

			sb.append("javascript:opener.");
			sb.append(renderResponse.getNamespace());
			sb.append("selectUserGroup('");
			sb.append(userGroup.getUserGroupId());
			sb.append("', '");
			sb.append(UnicodeFormatter.toString(userGroup.getName()));
			sb.append("', '");
			sb.append(target);
			sb.append("');");
			sb.append("window.close();");

			String rowHREF = sb.toString();
			%>

			<liferay-ui:search-container-column-text
				href="<%= rowHREF %>"
				name="name"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowHREF %>"
				name="description"
				value="<%= LanguageUtil.get(pageContext, userGroup.getDescription()) %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
</aui:script>