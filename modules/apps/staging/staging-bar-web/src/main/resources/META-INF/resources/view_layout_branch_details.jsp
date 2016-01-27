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

<%@ include file="/init.jsp" %>

<%
LayoutBranch layoutBranch = (LayoutBranch)request.getAttribute("view.jsp-layoutBranch");
LayoutRevision layoutRevision = (LayoutRevision)request.getAttribute("view.jsp-layoutRevision");
LayoutSetBranch layoutSetBranch = (LayoutSetBranch)request.getAttribute("view.jsp-layoutSetBranch");
String stagingFriendlyURL = (String)request.getAttribute("view.jsp-stagingFriendlyURL");
%>

<%
List<LayoutRevision> layoutRevisions = LayoutRevisionLocalServiceUtil.getChildLayoutRevisions(layoutRevision.getLayoutSetBranchId(), LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID, plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new LayoutRevisionCreateDateComparator(true));
%>

<li class="control-menu-nav-item">
	<a class="control-menu-label staging-variation-label" href="javascript:;" id="manageLayoutRevisions" onclick='<%= renderResponse.getNamespace() + "openPageVariationsDialog();" %>'>
		<liferay-ui:message key="page-variations" />
	</a>

	<div class="dropdown">
		<a class="dropdown-toggle layout-branch-selector staging-variation-selector" data-toggle="dropdown" href="#1">
			<liferay-ui:message key="<%= HtmlUtil.escape(layoutBranch.getName()) %>" />

			<aui:icon image="caret-double-l" markupView="lexicon" />
		</a>

		<ul class="dropdown-menu">

			<%
			for (LayoutRevision rootLayoutRevision : layoutRevisions) {
				LayoutBranch curLayoutBranch = rootLayoutRevision.getLayoutBranch();

				boolean selected = (curLayoutBranch.getLayoutBranchId() == layoutRevision.getLayoutBranchId());
			%>

			<portlet:actionURL name="selectLayoutBranch" var="curLayoutBranchURL">
				<portlet:param name="redirect" value="<%= stagingFriendlyURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(curLayoutBranch.getGroupId()) %>" />
				<portlet:param name="layoutBranchId" value="<%= String.valueOf(curLayoutBranch.getLayoutBranchId()) %>" />
				<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(curLayoutBranch.getLayoutSetBranchId()) %>" />
			</portlet:actionURL>

			<li>
				<a class='<%= selected ? "disabled" : StringPool.BLANK %>' href='<%= selected ? "javascript:;" : curLayoutBranchURL %>'>
					<liferay-ui:message key="<%= HtmlUtil.escape(curLayoutBranch.getName()) %>" />
				</a>
			</li>

			<%
			}
			%>

		</ul>
	</div>
</li>

<aui:script>
	function <portlet:namespace />openPageVariationsDialog() {
		var pageVariationsDialog = Liferay.Util.openWindow(
			{
				dialog: {
					destroyOnHide: true
				},
				id: 'pagesVariationsDialog',

				<liferay-util:buffer var="helpIcon">
					<liferay-ui:icon-help message="page-variations-help" />
				</liferay-util:buffer>

				title: '<liferay-ui:message arguments="<%= helpIcon %>" key="page-variations-x" />',

				<liferay-portlet:renderURL var="layoutBranchesURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="mvcRenderCommandName" value="viewLayoutBranches" />
					<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranch.getLayoutSetBranchId()) %>" />
				</liferay-portlet:renderURL>

				uri: '<%= HtmlUtil.escapeJS(layoutBranchesURL) %>'
			}
		);
	}

	$('.layout-branch-selector').on(
		'mouseenter',
		function(event) {
			Liferay.Portal.ToolTip.show(event.currentTarget, '<liferay-ui:message key="page-variation" />');
		}
	);
</aui:script>