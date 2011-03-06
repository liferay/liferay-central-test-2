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

<%@ include file="/html/portlet/dockbar/init.jsp" %>

<%
LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(layout);

PortletURL portletURL = renderResponse.createRenderURL();
%>

<portlet:actionURL var="editLayoutRevisonURL">
	<portlet:param name="struts_action" value="/dockbar/edit_layouts" />
	<portlet:param name="groupId" value="<%= String.valueOf(layoutRevision.getGroupId()) %>" />
</portlet:actionURL>

<aui:form action="<%= editLayoutRevisonURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "savePage();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
	<aui:input name="layoutRevisionId" type="hidden" value="<%= layoutRevision.getLayoutRevisionId() %>" />
	<aui:input name="updateRecentLayoutRevisionId" type="hidden" value="<%= false %>" />

	<div class="dockbar dockbar-staging" id="<portlet:namespace />dockbarStaging">
		<ul class="aui-toolbar">
			<li class="select-branch" id="<portlet:namespace />selectBranch">

				<%
				List<LayoutSetBranch> layoutSetBranches = LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(layout.getGroupId(), layout.isPrivateLayout());
				%>

				<aui:select id="layoutSetBranchId" inlineField="<%= true %>" label="branch" name="layoutSetBranchId">

					<%
					for (LayoutSetBranch layoutSetBranch : layoutSetBranches) {
						boolean selected = false;

						if (layoutSetBranch.getLayoutSetBranchId() == layoutRevision.getLayoutSetBranchId()) {
							selected = true;
						}
					%>

						<aui:option label="<%= HtmlUtil.escape(layoutSetBranch.getName()) %>" selected="<%= selected %>" value="<%= layoutSetBranch.getLayoutSetBranchId() %>" />

					<%
					}
					%>

				</aui:select>
			</li>

			<c:if test="<%= !layoutRevision.isMajor() && (!layoutRevision.hasChildren()) && (layoutRevision.getParentLayoutRevisionId() != LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID) %>">
				<li class="undo-revision" id="<portlet:namespace />undoRevision">
					<a href="javascript:;">
						<liferay-ui:message key="undo" />
					</a>
				</li>
			</c:if>

			<li class="view-history" id="<portlet:namespace />viewHistory">
				<a href="javascript:;">
					<liferay-ui:message key="history" />
				</a>
			</li>

			<li class="layout-revision-info" id="<portlet:namespace />layoutRevisionInfo">
				<span class="layout-revision-id"><%= layoutRevision.getLayoutRevisionId() %></span>
			</li>
		</ul>
	</div>
</aui:form>

<aui:script position="inline" use="liferay-staging">
	Liferay.Staging.Dockbar.init(
		{
			namespace: '<portlet:namespace />'
		}
	);
</aui:script>