<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
String displayStyle = ddlFormAdminDisplayContext.getDisplayStyle();

PortletURL portletURL = ddlFormAdminDisplayContext.getPortletURL();

portletURL.setParameter("displayStyle", displayStyle);

RecordSetSearch recordSetSearch = new RecordSetSearch(renderRequest, portletURL);

String orderByCol = ParamUtil.getString(request, "orderByCol", "modified-date");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

OrderByComparator<DDLRecordSet> orderByComparator = DDLFormPortletUtil.getDDLRecordSetOrderByComparator(orderByCol, orderByType);

recordSetSearch.setOrderByCol(orderByCol);
recordSetSearch.setOrderByComparator(orderByComparator);
recordSetSearch.setOrderByType(orderByType);
%>

<liferay-util:include page="/admin/search_bar.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/admin/toolbar.jsp" servletContext="<%= application %>" />

<div class="container-fluid-1280" id="<portlet:namespace />formContainer">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="searchContainerForm">
		<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />

		<liferay-ui:search-container
			emptyResultsMessage="no-forms-were-found"
			id="searchContainer"
			searchContainer="<%= recordSetSearch %>"
		>

			<%
			request.setAttribute(WebKeys.SEARCH_CONTAINER, searchContainer);
			%>

			<liferay-ui:search-container-results
				results="<%= ddlFormAdminDisplayContext.getSearchContainerResults(searchContainer) %>"
				total="<%= ddlFormAdminDisplayContext.getSearchContainerTotal(searchContainer) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.dynamic.data.lists.model.DDLRecordSet"
				cssClass="entry-display-style"
				escapedModel="<%= true %>"
				keyProperty="recordSetId"
				modelVar="recordSet"
			>
				<portlet:renderURL var="rowURL">
					<portlet:param name="mvcPath" value="/admin/edit_record_set.jsp" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
					<portlet:param name="displayStyle" value="<%= displayStyle %>" />
				</portlet:renderURL>

				<c:choose>
					<c:when test='<%= displayStyle.equals("descriptive") %>'>
						<liferay-ui:search-container-column-image
							src='<%= themeDisplay.getPathThemeImages() + "/file_system/large/article.png" %>'
							toggleRowChecker="<%= true %>"
						/>

						<liferay-ui:search-container-column-jsp
							colspan="2"
							href="<%= rowURL %>"
							path="/admin/view_record_set_descriptive.jsp"
						/>

						<liferay-ui:search-container-column-jsp
							path="/admin/record_set_action.jsp"
						/>

					</c:when>
					<c:when test='<%= displayStyle.equals("icon") %>'>

						<%
						row.setCssClass("col-md-3 col-sm-4 col-xs-12");
						%>

						<liferay-ui:search-container-column-text colspan="<%= 2 %>">
							<liferay-frontend:vertical-card
								actionJsp="/admin/record_set_action.jsp"
								actionJspServletContext="<%= application %>"
								cssClass="entry-display-style"
								imageUrl='<%= themeDisplay.getPathThemeImages() + "/file_system/large/article.png" %>'
								resultRow="<%= row %>"
								showCheckbox= "<%= false %>"
								title="<%= recordSet.getName(locale) %>"
								url="<%= rowURL %>"
							>
								<liferay-frontend:vertical-card-sticker-bottom>
									<liferay-ui:user-portrait
										userId="<%= recordSet.getUserId() %>"
									/>
								</liferay-frontend:vertical-card-sticker-bottom>

								<liferay-frontend:vertical-card-header>
									<liferay-ui:message arguments="<%= new String[] {LanguageUtil.getTimeDescription(locale, System.currentTimeMillis() - recordSet.getModifiedDate().getTime(), true), HtmlUtil.escape(recordSet.getUserName())} %>" key="x-ago-by-x" translateArguments="<%= false %>" />
								</liferay-frontend:vertical-card-header>
							</liferay-frontend:vertical-card>
						</liferay-ui:search-container-column-text>

					</c:when>
					<c:otherwise>

						<liferay-ui:search-container-column-text
							href="<%= rowURL %>"
							name="name"
							value="<%= recordSet.getName(locale) %>"
						/>

						<liferay-ui:search-container-column-text
							name="description"
							value="<%= StringUtil.shorten(recordSet.getDescription(locale), 100) %>"
						/>

						<liferay-ui:search-container-column-date
							name="modified-date"
							value="<%= recordSet.getModifiedDate() %>"
						/>

						<liferay-ui:search-container-column-jsp
							align="right"
							cssClass="checkbox-cell entry-action"
							path="/admin/record_set_action.jsp"
						/>

					</c:otherwise>
				</c:choose>

			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
		</liferay-ui:search-container>
	</aui:form>
</div>

<c:if test="<%= ddlFormAdminDisplayContext.isShowAddRecordSetButton() %>">
	<portlet:renderURL var="addRecordSetURL">
		<portlet:param name="mvcPath" value="/admin/edit_record_set.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "new-form") %>' url="<%= addRecordSetURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>