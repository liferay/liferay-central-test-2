<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<liferay-ui:icon-menu align="left" direction="down" icon="" message="sort-by" showExpanded="<%= false %>" showWhenSingleIcon="<%= false %>">

	<%
	String orderByCol = ParamUtil.getString(request, "orderByCol");
	String orderByType = ParamUtil.getString(request, "orderByType");
	%>

	<portlet:resourceURL var="sortByTitle">
		<portlet:param name="struts_action" value="/document_library/view" />
		<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="viewSortButton" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="orderByCol" value="title" />
		<portlet:param name="orderByType" value='<%= (orderByCol.equals("title") && orderByType.equals("asc")) ? "desc" : "asc" %>' />
	</portlet:resourceURL>

	<%
	String taglibUrl = "javascript:" + liferayPortletResponse.getNamespace() + "sortEntries('" + sortByTitle.toString() + "')";
	%>

	<liferay-ui:icon
		image="folder"
		message="title"
		url="<%= taglibUrl %>"
	/>

	<portlet:resourceURL var="sortByCreationDate">
		<portlet:param name="struts_action" value="/document_library/view" />
		<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="viewSortButton" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="orderByCol" value="creationDate" />
		<portlet:param name="orderByType" value='<%= (orderByCol.equals("creationDate") && orderByType.equals("asc")) ? "desc" : "asc" %>' />
	</portlet:resourceURL>

	<%
	taglibUrl = "javascript:" + liferayPortletResponse.getNamespace() + "sortEntries('" + sortByCreationDate.toString() + "')";
	%>

	<liferay-ui:icon
		image="folder"
		message="create-date"
		url="<%= taglibUrl %>"
	/>

	<portlet:resourceURL var="sortByModifiedDate">
		<portlet:param name="struts_action" value="/document_library/view" />
		<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="viewSortButton" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="orderByCol" value="modifiedDate" />
		<portlet:param name="orderByType" value='<%= (orderByCol.equals("modifiedDate") && orderByType.equals("asc")) ? "desc" : "asc" %>' />
	</portlet:resourceURL>

	<%
	taglibUrl = "javascript:" + liferayPortletResponse.getNamespace() + "sortEntries('" + sortByModifiedDate.toString() + "')";
	%>

	<liferay-ui:icon
		image="folder"
		message="modified-date"
		url="<%= taglibUrl %>"
	/>

	<portlet:resourceURL var="sortByReadCount">
		<portlet:param name="struts_action" value="/document_library/view" />
		<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="viewSortButton" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="orderByCol" value="readCount" />
		<portlet:param name="orderByType" value='<%= (orderByCol.equals("readCount") && orderByType.equals("asc")) ? "desc" : "asc" %>' />
	</portlet:resourceURL>

	<%
	taglibUrl = "javascript:" + liferayPortletResponse.getNamespace() + "sortEntries('" + sortByReadCount.toString() + "')";
	%>

	<liferay-ui:icon
		image="folder"
		message="read-count"
		url="<%= taglibUrl %>"
	/>

	<portlet:resourceURL var="sortBySize">
		<portlet:param name="struts_action" value="/document_library/view" />
		<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="viewSortButton" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="orderByCol" value="size" />
		<portlet:param name="orderByType" value='<%= (orderByCol.equals("size") && orderByType.equals("asc")) ? "desc" : "asc" %>' />
	</portlet:resourceURL>

	<%
	taglibUrl = "javascript:" + liferayPortletResponse.getNamespace() + "sortEntries('" + sortBySize.toString() + "')";
	%>

	<liferay-ui:icon
		image="folder"
		message="size"
		url="<%= taglibUrl %>"
	/>
</liferay-ui:icon-menu>

<aui:script use="aui-base">
	var entriesContainer = A.one('#<portlet:namespace />documentContainer');

	Liferay.provide(
		window,
		'<portlet:namespace />sortEntries',
		function(url) {
			entriesContainer.plug(A.LoadingMask);

			entriesContainer.loadingmask.toggle();

			A.io.request(
				url,
				{
					after: {
						success: function(event, id, obj) {
							entriesContainer.unplug(A.LoadingMask);

							var responseData = this.get('responseData');

							var content = A.Node.create(responseData);

							var sortButtonContainer = A.one('#<portlet:namespace />sortButtonContainer');
							var sortButton = content.one('#<portlet:namespace />sortButton')

							sortButtonContainer.setContent(sortButton);

							var entries = content.one('#<portlet:namespace />entries');

							entriesContainer.setContent(entries);
						}
					}
				}
			);
		},
		['aui-base']
	);
</aui:script>