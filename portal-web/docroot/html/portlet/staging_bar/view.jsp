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

<%@ include file="/html/portlet/staging_bar/init.jsp" %>

<%
LayoutRevision layoutRevision = null;

if (layout != null) {
	layoutRevision = LayoutStagingUtil.getLayoutRevision(layout);
}
%>

<c:if test="<%= themeDisplay.isShowStagingIcon() %>">

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	String liveFriendlyURL = null;

	if (liveGroup != null) {
		try {
			Layout liveLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(layout.getUuid(), liveGroup.getGroupId());

			liveFriendlyURL = PortalUtil.getLayoutFriendlyURL(liveLayout, themeDisplay);
		}
		catch (Exception e) {
			liveFriendlyURL = PortalUtil.getGroupFriendlyURL(liveGroup, layout.getPrivateLayout(), themeDisplay);
		}

		liveFriendlyURL = PortalUtil.addPreservedParameters(themeDisplay, liveFriendlyURL);
	}

	String stagedFriendlyURL = null;

	if (stagingGroup != null) {
		try {
			Layout stagedLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(layout.getUuid(), stagingGroup.getGroupId());

			stagedFriendlyURL = PortalUtil.getLayoutFriendlyURL(stagedLayout, themeDisplay);
		}
		catch (Exception e) {
			stagedFriendlyURL = PortalUtil.getGroupFriendlyURL(stagingGroup, layout.getPrivateLayout(), themeDisplay);
		}

		stagedFriendlyURL = PortalUtil.addPreservedParameters(themeDisplay, stagedFriendlyURL);
	}
	%>

	<div class="staging-bar">
		<div class="staging-tabs">
			<c:if test="<%= liveGroup != null %>">
				<span class="tab-container">
					<aui:a cssClass='<%= "tab" + (!group.isStagingGroup() ? " selected" : StringPool.BLANK) %>' href="<%= !group.isStagingGroup() ? null : liveFriendlyURL %>" label="live" />
				</span>
			</c:if>

			<c:if test="<%= stagingGroup != null %>">

				<%
				List<LayoutSetBranch> layoutSetBranches = LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(stagingGroup.getGroupId(), layout.isPrivateLayout());
				%>

				<c:choose>
					<c:when test="<%= layoutSetBranches.size() > 0 %>">

						<%
						for (LayoutSetBranch layoutSetBranch : layoutSetBranches) {
							boolean selected = group.isStagingGroup() && layoutRevision != null && (layoutSetBranch.getLayoutSetBranchId() == layoutRevision.getLayoutSetBranchId());
						%>

							<portlet:actionURL var="layoutSetBranchURL">
								<portlet:param name="struts_action" value="/dockbar/edit_layouts" />
								<portlet:param name="<%= Constants.CMD %>" value="select_layout_set_branch" />
								<portlet:param name="groupId" value="<%= String.valueOf(layoutSetBranch.getGroupId()) %>" />
								<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranch.getLayoutSetBranchId()) %>" />
								<portlet:param name="redirect" value="<%= stagedFriendlyURL %>" />
							</portlet:actionURL>

							<span class="tab-container">
								<aui:a cssClass='<%= "layout-set-branch tab" + (selected ? " selected" : StringPool.BLANK) %>' href="<%= selected ? null : layoutSetBranchURL %>" label="<%= layoutSetBranch.getName() %>" />
							</span>

						<%
						}
						%>

					</c:when>
					<c:otherwise>

						<%
						boolean selected = group.isStagingGroup() || group.isStagedRemotely();
						%>

						<span class="tab-container">
							<aui:a cssClass='<%= "tab" +  (selected ? " selected" : StringPool.BLANK)  %>' href="<%= selected ? null : stagedFriendlyURL %>" label="backstage" />
						</span>
					</c:otherwise>
				</c:choose>
			</c:if>
		</div>

		<div class="staging-tabs-content">
			<aui:layout>
				<c:choose>
				<c:when test="<%= group.isStagingGroup() || group.isStagedRemotely() %>">
					<aui:column>
						<img alt="" class="staging-icon" src="<%= themeDisplay.getPathThemeImages() %>/staging_bar/backstage.png" />
					</aui:column>

					<c:if test="<%= layoutRevision != null %>">
						<aui:column columnWidth="50">
							<portlet:actionURL var="editLayoutRevisonURL">
								<portlet:param name="struts_action" value="/staging_bar/edit_layouts" />
								<portlet:param name="groupId" value="<%= String.valueOf(layoutRevision.getGroupId()) %>" />
							</portlet:actionURL>

							<aui:form action="<%= editLayoutRevisonURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "savePage();" %>'>
								<aui:input name="<%= Constants.CMD %>" type="hidden" />
								<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
								<aui:input name="layoutRevisionId" type="hidden" value="<%= layoutRevision.getLayoutRevisionId() %>" />
								<aui:input name="updateRecentLayoutRevisionId" type="hidden" value="<%= false %>" />
								<aui:input name="layoutSetBranchId" type="hidden" value="<%= layoutRevision.getLayoutSetBranchId() %>" />

								<div class="layout-info">
									<div class="layout-title">
										<label><liferay-ui:message key="page" /></label>

										<em><%= layoutRevision.getName(locale) %></em>
									</div>

									<aui:workflow-status status='<%= layoutRevision.getStatus() %>' version="<%= String.valueOf(layoutRevision.getLayoutRevisionId()) %>" />
								</div>

								<div class="layout-actions">
									<span class="backstage-toolbar" id="<portlet:namespace />backstageToolbar"></span>
								</div>
							</aui:form>
						</aui:column>

						<aui:script use="liferay-staging">
							var dockbar = Liferay.Staging.Dockbar;

							dockbar.init(
								{
									namespace: '<portlet:namespace />'
								}
							);

							<c:if test="<%= !layoutRevision.isMajor() && (!layoutRevision.hasChildren()) && (layoutRevision.getParentLayoutRevisionId() != LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID) %>">
								dockbar.backstageToolbar.add(dockbar.undoButton, 0);
							</c:if>
						</aui:script>
					</c:if>
				</c:when>
				<c:otherwise>
					<img alt="" class="staging-icon" src="<%= themeDisplay.getPathThemeImages() %>/staging_bar/live.png" />

					<%
					UnicodeProperties typeSettingsProperties = layout.getTypeSettingsProperties();

					long lastImportDate = GetterUtil.getLong(typeSettingsProperties.getProperty("last-import-date"));
					%>

					<c:choose>
						<c:when test="<%= lastImportDate > 0 %>">
							<%
							Date now = new Date();

							long lastPostAgo = now.getTime() - lastImportDate;

							String layoutSetBranchName = null;
							List<LayoutRevision> rootRevisions = new ArrayList<LayoutRevision>();

							long layoutSetBranchId = GetterUtil.getLong(typeSettingsProperties.getProperty("last-import-layout-set-branch-id"));

							if (layoutSetBranchId > 0) {

								try {
									LayoutSetBranch layoutSetBranch = LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(layoutSetBranchId);
									layoutSetBranchName = layoutSetBranch.getName();
								}
								catch(Exception e) {
								}
							}

							if (Validator.isNull(layoutSetBranchName)) {
								layoutSetBranchName = typeSettingsProperties.getProperty("last-import-layout-set-branch-name");
							}

							if (Validator.isNull(layoutSetBranchName)) {
								layoutSetBranchName = LanguageUtil.get(pageContext, "backstage");
							}

							String variationName = null;

							long layoutRevisionId = GetterUtil.getLong(typeSettingsProperties.getProperty("last-import-layout-revision-id"));

							if (layoutRevisionId > 0) {
								try {
									LayoutRevision importedLayoutRevision = LayoutRevisionLocalServiceUtil.getLayoutRevision(layoutRevisionId);
									variationName = importedLayoutRevision.getVariationName();

									rootRevisions = LayoutRevisionLocalServiceUtil.getLayoutRevisions(importedLayoutRevision.getLayoutSetBranchId(), 0, importedLayoutRevision.getPlid());
								}
								catch(Exception e) {
								}
							}

							if (Validator.isNull(variationName)) {
								variationName = typeSettingsProperties.getProperty("last-import-variation-name");
							}

							String publisherName = null;
							String userUuid = GetterUtil.getString(typeSettingsProperties.getProperty("last-import-user-uuid"));

							if (Validator.isNotNull(userUuid)) {
								try {
									User publisher = UserLocalServiceUtil.getUserByUuid(userUuid);
									publisherName = publisher.getFullName();
								}
								catch(Exception e) {
								}
							}

							if (Validator.isNull(publisherName)) {
								publisherName = typeSettingsProperties.getProperty("last-import-user-name");
							}
							%>

							<span class="last-publication-branch">
								<liferay-ui:message key="last-publication-from-x" arguments='<%= layoutSetBranchName %>' />

								<c:if test="<%= (Validator.isNotNull(variationName) && rootRevisions.size() > 1) || Validator.isNotNull(layoutRevisionId) %>">
									<span class="last-publication-variation-details">(
										<c:if test="<%= Validator.isNotNull(variationName) && rootRevisions.size() > 1 %>">
											<span class="variation-name"><liferay-ui:message key="variation" />: <strong><%= variationName %></strong></span>
										</c:if>

										<c:if test="<%= Validator.isNotNull(layoutRevisionId) %>">
											<span class="layout-version"><liferay-ui:message key="version" />: <strong><%= layoutRevisionId %></strong></span>
										</c:if>
									)</span>
								</c:if>
							</span>

							<span class="last-publication-user"><liferay-ui:message key="x-ago-by-x" arguments='<%= new String[]{LanguageUtil.getTimeDescription(pageContext, lastPostAgo, true), publisherName}%>' /></span>
						</c:when>
						<c:otherwise>
							<span class="staging-live-group-name"><liferay-ui:message key="x-is-staged" arguments="<%= liveGroup.getDescriptiveName() %>" /></span>

							<span class="staging-live-help"><liferay-ui:message key="staging-live-help-x" arguments="<%= liveGroup.getDescriptiveName() %>" /></span>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
			</aui:layout>
		</div>
	</div>
</c:if>