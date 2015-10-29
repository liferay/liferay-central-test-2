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
LayoutRevision layoutRevision = (LayoutRevision)request.getAttribute("view.jsp-layoutRevision");

if ((layoutRevision == null) && (layout != null)) {
	layoutRevision = LayoutStagingUtil.getLayoutRevision(layout);
}

LayoutSetBranch layoutSetBranch = (LayoutSetBranch)request.getAttribute("view.jsp-layoutSetBranch");

if (layoutSetBranch == null) {
	layoutSetBranch = LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(layoutRevision.getLayoutSetBranchId());
}

boolean workflowEnabled = WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, LayoutRevision.class.getName());

boolean hasWorkflowTask = false;

if (workflowEnabled) {
	hasWorkflowTask = StagingUtil.hasWorkflowTask(user.getUserId(), layoutRevision);
}

String taglibHelpMessage = null;

if (layoutRevision.isHead()) {
	taglibHelpMessage = LanguageUtil.format(request, "this-version-will-be-published-when-x-is-published-to-live", HtmlUtil.escape(layoutSetBranch.getName()), false);
}
else if (hasWorkflowTask) {
	taglibHelpMessage = "you-are-currently-reviewing-this-page.-you-can-make-changes-and-send-them-to-the-next-step-in-the-workflow-when-ready";
}
else {
	taglibHelpMessage = "a-new-version-is-created-automatically-if-this-page-is-modified";
}
%>

<li class="control-menu-nav-item">
	<c:if test="<%= !layoutRevision.isIncomplete() %>">

		<%
		String taglibURL = "javascript:Liferay.fire('" + liferayPortletResponse.getNamespace() + "viewHistory', {layoutRevisionId: '" + layoutRevision.getLayoutRevisionId() + "', layoutSetBranchId: '" + layoutRevision.getLayoutSetBranchId() + "'}); void(0);";
		%>

		<liferay-ui:icon
			iconCssClass="icon-time"
			id="viewHistoryLink"
			message="history"
			method="get"
			url="<%= taglibURL %>"
		/>
	</c:if>

	<c:if test="<%= !hasWorkflowTask %>">
		<c:if test="<%= !layoutRevision.isMajor() && (layoutRevision.getParentLayoutRevisionId() != LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID) %>">

			<%
			String taglibURL = "javascript:Liferay.fire('" + liferayPortletResponse.getNamespace() + "undo', {layoutRevisionId: '" + layoutRevision.getLayoutRevisionId() + "', layoutSetBranchId: '" + layoutRevision.getLayoutSetBranchId() + "'}); void(0);";
			%>

			<liferay-ui:icon
				iconCssClass="icon-undo"
				id="undoLink"
				message="undo"
				url="<%= taglibURL %>"
			/>
		</c:if>

		<c:if test="<%= layoutRevision.hasChildren() %>">

			<%
			List<LayoutRevision> childLayoutRevisions = layoutRevision.getChildren();

			LayoutRevision firstChildLayoutRevision = childLayoutRevisions.get(0);

			if (firstChildLayoutRevision.isInactive()) {
			%>

				<%
				String taglibURL = "javascript:Liferay.fire('" + liferayPortletResponse.getNamespace() + "redo', {layoutRevisionId: '" + firstChildLayoutRevision.getLayoutRevisionId() + "', layoutSetBranchId: '" + firstChildLayoutRevision.getLayoutSetBranchId() + "'}); void(0);";
				%>

				<liferay-ui:icon
					iconCssClass="icon-repeat"
					id="redoLink"
					message="redo"
					url="<%= taglibURL %>"
				/>

			<%
			}
			%>

		</c:if>
	</c:if>

	<c:if test="<%= !layoutRevision.isIncomplete() %>">
		<span class="layout-revision-version"><label><liferay-ui:message key="version" />:</label> <span class=""><%= layoutRevision.getLayoutRevisionId() %></span></span>

		<aui:model-context bean="<%= layoutRevision %>" model="<%= LayoutRevision.class %>" />

		<aui:workflow-status showIcon="<%= false %>" showLabel="<%= false %>" status="<%= layoutRevision.getStatus() %>" statusMessage='<%= layoutRevision.isHead() ? "ready-for-publication" : null %>' />

		<aui:script>
			AUI.$('.layout-revision-info .taglib-workflow-status').on(
				'mouseenter',
				function(event) {
					Liferay.Portal.ToolTip.show(event.currentTarget, '<liferay-ui:message key="<%= taglibHelpMessage %>" />');
				}
			);
		</aui:script>

		<c:if test="<%= hasWorkflowTask %>">

			<%
			PortletURL portletURL = PortalUtil.getControlPanelPortletURL(request, PortletKeys.MY_WORKFLOW_TASK, 0, PortletRequest.RENDER_PHASE);

			portletURL.setParameter("mvcPath", "/edit_workflow_task.jsp");

			WorkflowTask workflowTask = StagingUtil.getWorkflowTask(user.getUserId(), layoutRevision);

			portletURL.setParameter("workflowTaskId", String.valueOf(workflowTask.getWorkflowTaskId()));

			portletURL.setPortletMode(PortletMode.VIEW);
			portletURL.setWindowState(LiferayWindowState.POP_UP);

			String layoutURL = PortalUtil.getLayoutFriendlyURL(layout, themeDisplay);

			layoutURL = HttpUtil.addParameter(layoutURL, "layoutSetBranchId", layoutRevision.getLayoutSetBranchId());
			layoutURL = HttpUtil.addParameter(layoutURL, "layoutRevisionId", layoutRevision.getLayoutRevisionId());

			portletURL.setParameter("closeRedirect", layoutURL);
			%>

			<liferay-ui:icon
				cssClass="submit-link"
				iconCssClass="icon-random"
				id="reviewTaskIcon"
				message="workflow"
				method="get"
				url="<%= portletURL.toString() %>"
				useDialog="<%= true %>"
			/>
		</c:if>
	</c:if>
</li>

<li class="control-menu-nav-item">
	<c:if test="<%= !hasWorkflowTask %>">
		<c:if test="<%= !layoutRevision.isHead() && LayoutPermissionUtil.contains(permissionChecker, layoutRevision.getPlid(), ActionKeys.UPDATE) %>">
			<c:if test="<%= layoutRevision.isIncomplete() %>">
				<p>
					<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(layoutRevision.getName(locale)), HtmlUtil.escape(layoutSetBranch.getName())} %>" key="the-page-x-is-not-enabled-in-x,-but-is-available-in-other-pages-variations" translateArguments="<%= false %>" />
				</p>
			</c:if>

			<%
			String label = null;

			if (layoutRevision.isIncomplete()) {
				label = LanguageUtil.format(request, "enable-in-x", layoutSetBranch.getName(), false);
			}
			else {
				if (workflowEnabled) {
					label = "submit-for-publication";
				}
				else {
					label = "mark-as-ready-for-publication";
				}
			}
			%>

			<portlet:actionURL name="updateLayoutRevision" var="publishURL">
				<portlet:param name="redirect" value="<%= PortalUtil.getLayoutFullURL(themeDisplay) %>" />
				<portlet:param name="layoutRevisionId" value="<%= String.valueOf(layoutRevision.getLayoutRevisionId()) %>" />
				<portlet:param name="major" value="true" />
				<portlet:param name="workflowAction" value="<%= String.valueOf(layoutRevision.isIncomplete() ? WorkflowConstants.ACTION_SAVE_DRAFT : WorkflowConstants.ACTION_PUBLISH) %>" />
			</portlet:actionURL>

			<%
			List<LayoutRevision> pendingLayoutRevisions = LayoutRevisionLocalServiceUtil.getLayoutRevisions(layoutRevision.getLayoutSetBranchId(), layoutRevision.getPlid(), WorkflowConstants.STATUS_PENDING);

			String taglibURL = null;

			if (!workflowEnabled || pendingLayoutRevisions.isEmpty()) {
				taglibURL = "javascript:Liferay.fire('" + liferayPortletResponse.getNamespace() + "submit', {incomplete: " + layoutRevision.isIncomplete() + ", publishURL: '" + publishURL + "', currentURL: '" + currentURL + "'}); void(0);";
			}
			%>

			<a href="<%= taglibURL %>" id="submitLink">
				<liferay-ui:message key="<%= label %>" />
			</a>

			<c:if test="<%= workflowEnabled && !pendingLayoutRevisions.isEmpty() %>">

				<%
				String submitMessage = "you-cannot-submit-your-changes-because-someone-else-has-submitted-changes-for-approval";

				LayoutRevision pendingLayoutRevision = pendingLayoutRevisions.get(0);

				if ((pendingLayoutRevision != null) && (pendingLayoutRevision.getUserId() == user.getUserId())) {
					submitMessage = "you-cannot-submit-your-changes-because-your-previous-submission-is-still-waiting-for-approval";
				}
				%>

				<aui:script>
					AUI.$('.submit-link').on(
						'mouseenter',
						function(event) {
							Liferay.Portal.ToolTip.show(event.currentTarget, '<liferay-ui:message key="<%= submitMessage %>" />');
						}
					);
				</aui:script>
			</c:if>
		</c:if>
	</c:if>
</li>

<portlet:renderURL var="markAsReadyForPublicationURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<portlet:param name="mvcPath" value="/view_layout_revision_details.jsp" />
</portlet:renderURL>

<portlet:renderURL var="viewHistoryURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="mvcPath" value="/view_layout_revisions.jsp" />
	<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranch.getLayoutSetBranchId()) %>" />
</portlet:renderURL>

<aui:script position="inline" use="liferay-staging-version">
	var stagingBar = Liferay.StagingBar;

	stagingBar.init(
		{
			markAsReadyForPublicationURL: '<%= markAsReadyForPublicationURL %>',
			namespace: '<portlet:namespace />',
			portletId: '<%= portletDisplay.getId() %>',
			viewHistoryURL: '<%= viewHistoryURL %>'
		}
	);
</aui:script>