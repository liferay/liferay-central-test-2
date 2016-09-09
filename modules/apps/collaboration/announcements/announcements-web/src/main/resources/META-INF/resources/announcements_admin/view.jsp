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

<%@ include file="/announcements_admin/init.jsp" %>

<%
String distributionScope = ParamUtil.getString(request, "distributionScope");

long classNameId = -1;
long classPK = -1;

String[] distributionScopeArray = StringUtil.split(distributionScope);

if (distributionScopeArray.length == 2) {
	classNameId = GetterUtil.getLong(distributionScopeArray[0]);
	classPK = GetterUtil.getLong(distributionScopeArray[1]);
}

if ((classNameId == 0) && (classPK == 0) && !PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_GENERAL_ANNOUNCEMENTS)) {
	throw new PrincipalException.MustHavePermission(permissionChecker, ActionKeys.ADD_GENERAL_ANNOUNCEMENTS);
}
%>

<div class="container-fluid-1280">
	<aui:form action="<%= currentURL %>" method="post" name="fm">
		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset id="fieldSet">

				<%
				boolean submitOnChange = true;
				%>

				<%@ include file="/announcements/entry_select_scope.jspf" %>
			</aui:fieldset>
		</aui:fieldset-group>

		<c:choose>
			<c:when test="<%= distributionScopeArray.length > 0 %>">
				<portlet:renderURL var="addEntryURL">
					<portlet:param name="mvcRenderCommandName" value="/announcements/edit_entry" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="distributionScope" value="<%= distributionScope %>" />
				</portlet:renderURL>

				<liferay-frontend:add-menu>
					<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-entry") %>' url="<%= addEntryURL %>" />
				</liferay-frontend:add-menu>
			</c:when>
			<c:otherwise>
				<div class="alert alert-info">
					<liferay-ui:message key="please-select-a-distribution-scope" />
				</div>
			</c:otherwise>
		</c:choose>

		<c:if test="<%= Validator.isNotNull(distributionScope) %>">

			<%
			PortletURL iteratorURL = PortletURLUtil.clone(currentURLObj, renderResponse);

			iteratorURL.setParameter("distributionScope", distributionScope);

			List<String> headerNames = new ArrayList<String>();

			headerNames.add("title");
			headerNames.add("type");
			headerNames.add("modified-date");
			headerNames.add("display-date");
			headerNames.add("expiration-date");
			headerNames.add(StringPool.BLANK);

			SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, iteratorURL, headerNames, "no-entries-were-found");

			int total = AnnouncementsEntryLocalServiceUtil.getEntriesCount(classNameId, classPK, portletName.equals(AnnouncementsPortletKeys.ALERTS));

			searchContainer.setTotal(total);

			List<AnnouncementsEntry> results = AnnouncementsEntryLocalServiceUtil.getEntries(classNameId, classPK, portletName.equals(AnnouncementsPortletKeys.ALERTS), searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);

			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				AnnouncementsEntry entry = results.get(i);

				entry = entry.toEscapedModel();

				ResultRow row = new ResultRow(entry, entry.getEntryId(), i);

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("mvcRenderCommandName", "/announcements/edit_entry");
				rowURL.setParameter("redirect", announcementsRequestHelper.getCurrentURL());
				rowURL.setParameter("entryId", String.valueOf(entry.getEntryId()));

				// Title

				row.addText(entry.getTitle(), rowURL);

				// Type

				row.addText(LanguageUtil.get(request, entry.getType()), rowURL);

				// Modified date

				row.addDate(entry.getModifiedDate(), rowURL);

				// Display date

				row.addDate(entry.getDisplayDate(), rowURL);

				// Expiration date

				row.addDate(entry.getExpirationDate(), rowURL);

				// Action

				row.addJSP("/announcements_admin/entry_action.jsp", "entry-action", application, request, response);

				// Add result row

				resultRows.add(row);
			}
			%>

			<liferay-ui:search-iterator markupView="lexicon" searchContainer="<%= searchContainer %>" />
		</c:if>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />selectDistributionScope(distributionScope) {
		var url = '<%= currentURL %>&<portlet:namespace />distributionScope=' + distributionScope;

		submitForm(document.<portlet:namespace />fm, url);
	}
</aui:script>