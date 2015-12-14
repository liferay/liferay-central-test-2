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
Group selGroup = (Group)request.getAttribute(WebKeys.GROUP);

Group group = layoutsAdminDisplayContext.getGroup();
Group liveGroup = layoutsAdminDisplayContext.getLiveGroup();

Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

PortletURL redirectURL = layoutsAdminDisplayContext.getRedirectURL();

long refererPlid = ParamUtil.getLong(request, "refererPlid", LayoutConstants.DEFAULT_PLID);

Set<Long> parentPlids = new HashSet<Long>();

long parentPlid = refererPlid;

while (parentPlid > 0) {
	try {
		Layout parentLayout = LayoutLocalServiceUtil.getLayout(parentPlid);

		if (parentLayout.isRootLayout()) {
			break;
		}

		parentPlid = parentLayout.getParentPlid();

		parentPlids.add(parentPlid);
	}
	catch (Exception e) {
		break;
	}
}

LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(selLayout);

String layoutSetBranchName = StringPool.BLANK;

boolean incomplete = false;

if (layoutRevision != null) {
	long layoutSetBranchId = layoutRevision.getLayoutSetBranchId();

	incomplete = StagingUtil.isIncomplete(selLayout, layoutSetBranchId);

	if (incomplete) {
		LayoutSetBranch layoutSetBranch = LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(layoutSetBranchId);

		layoutSetBranchName = layoutSetBranch.getName();
	}
}

renderResponse.setTitle(selLayout.getName(locale));
%>

<c:if test="<%= !group.isLayoutPrototype() && (selLayout != null) %>">
	<aui:nav-bar>
		<aui:nav cssClass="navbar-nav" id="layoutsNav">
			<c:if test="<%= LayoutPermissionUtil.contains(permissionChecker, selLayout, ActionKeys.DELETE) %>">
				<aui:nav-item cssClass="remove-layout" label="delete" />
			</c:if>
		</aui:nav>
	</aui:nav-bar>
</c:if>

<c:choose>
	<c:when test="<%= incomplete %>">
		<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(selLayout.getName(locale)), HtmlUtil.escape(layoutSetBranchName)} %>" key="the-page-x-is-not-enabled-in-x,-but-is-available-in-other-pages-variations" translateArguments="<%= false %>" />

		<aui:button-row>
			<aui:button id="enableLayoutButton" name="enableLayout" value='<%= LanguageUtil.format(request, "enable-in-x", HtmlUtil.escape(layoutSetBranchName), false) %>' />

			<portlet:actionURL name="enableLayout" var="enableLayoutURL">
				<portlet:param name="mvcPath" value="/view.jsp" />
				<portlet:param name="redirect" value="<%= redirectURL.toString() %>" />
				<portlet:param name="incompleteLayoutRevisionId" value="<%= String.valueOf(layoutRevision.getLayoutRevisionId()) %>" />
			</portlet:actionURL>

			<aui:script use="aui-base">
				AUI.$('#<portlet:namespace />enableLayoutButton').on(
					'click',
					function(event) {
						submitForm(document.hrefFm, '<%= enableLayoutURL %>');
					}
				);
			</aui:script>

			<aui:button cssClass="remove-layout" id="deleteLayoutButton" name="deleteLayout" value="delete-in-all-pages-variations" />

			<portlet:actionURL name="deleteLayout" var="deleteLayoutURL">
				<portlet:param name="mvcPath" value="/view.jsp" />
				<portlet:param name="redirect" value='<%= HttpUtil.addParameter(redirectURL.toString(), liferayPortletResponse.getNamespace() + "selPlid", selLayout.getParentPlid()) %>' />
				<portlet:param name="plid" value="<%= String.valueOf(layoutsAdminDisplayContext.getSelPlid()) %>" />
				<portlet:param name="layoutSetBranchId" value="0" />
				<portlet:param name="selPlid" value="<%= String.valueOf(selLayout.getParentPlid()) %>" />
			</portlet:actionURL>

			<aui:script use="aui-base">
				AUI.$('#<portlet:namespace />deleteLayoutButton').on(
					'click',
					function(event) {
						submitForm(document.hrefFm, '<%= deleteLayoutURL %>');
					}
				);
			</aui:script>
		</aui:button-row>
	</c:when>
	<c:otherwise>
		<portlet:actionURL name="editLayout" var="editLayoutURL">
			<portlet:param name="mvcPath" value="/view.jsp" />
		</portlet:actionURL>

		<aui:form action='<%= HttpUtil.addParameter(editLayoutURL, "refererPlid", plid) %>' cssClass="edit-layout-form" enctype="multipart/form-data" method="post" name="fm">
			<aui:input name="redirect" type="hidden" value='<%= HttpUtil.addParameter(redirectURL.toString(), liferayPortletResponse.getNamespace() + "selPlid", layoutsAdminDisplayContext.getSelPlid()) %>' />
			<aui:input name="groupId" type="hidden" value="<%= selGroup.getGroupId() %>" />
			<aui:input name="liveGroupId" type="hidden" value="<%= layoutsAdminDisplayContext.getLiveGroupId() %>" />
			<aui:input name="stagingGroupId" type="hidden" value="<%= layoutsAdminDisplayContext.getStagingGroupId() %>" />
			<aui:input name="selPlid" type="hidden" value="<%= layoutsAdminDisplayContext.getSelPlid() %>" />
			<aui:input name="privateLayout" type="hidden" value="<%= layoutsAdminDisplayContext.isPrivateLayout() %>" />
			<aui:input name="layoutId" type="hidden" value="<%= layoutsAdminDisplayContext.getLayoutId() %>" />
			<aui:input name="<%= PortletDataHandlerKeys.SELECTED_LAYOUTS %>" type="hidden" />

			<c:if test="<%= layoutRevision != null %>">
				<aui:input name="layoutSetBranchId" type="hidden" value="<%= layoutRevision.getLayoutSetBranchId() %>" />
			</c:if>

			<c:if test="<%= !group.isLayoutPrototype() && (selLayout != null) %>">
				<c:if test="<%= selGroup.hasLocalOrRemoteStagingGroup() && !selGroup.isStagingGroup() %>">
					<div class="alert alert-warning">
						<liferay-ui:message key="changes-are-immediately-available-to-end-users" />
					</div>
				</c:if>

				<%
				Group selLayoutGroup = selLayout.getGroup();
				%>

				<c:choose>
					<c:when test="<%= !SitesUtil.isLayoutUpdateable(selLayout) %>">
						<div class="alert alert-warning">
							<liferay-ui:message key="this-page-cannot-be-modified-because-it-is-associated-to-a-site-template-does-not-allow-modifications-to-it" />
						</div>
					</c:when>
					<c:when test="<%= !SitesUtil.isLayoutDeleteable(selLayout) %>">
						<div class="alert alert-warning">
							<liferay-ui:message key="this-page-cannot-be-deleted-and-cannot-have-child-pages-because-it-is-associated-to-a-site-template" />
						</div>
					</c:when>
				</c:choose>

				<c:if test="<%= (selLayout.getGroupId() != layoutsAdminDisplayContext.getGroupId()) && selLayoutGroup.isUserGroup() %>">

					<%
					UserGroup userGroup = UserGroupLocalServiceUtil.getUserGroup(selLayoutGroup.getClassPK());
					%>

					<div class="alert alert-warning">
						<liferay-ui:message arguments="<%= HtmlUtil.escape(userGroup.getName()) %>" key="this-page-cannot-be-modified-because-it-belongs-to-the-user-group-x" translateArguments="<%= false %>" />
					</div>
				</c:if>
			</c:if>

			<liferay-ui:form-navigator
				formModelBean="<%= selLayout %>"
				id="<%= FormNavigatorConstants.FORM_NAVIGATOR_ID_LAYOUT %>"
				markupView="lexicon"
				showButtons="<%= (selLayout.getGroupId() == layoutsAdminDisplayContext.getGroupId()) && SitesUtil.isLayoutUpdateable(selLayout) && LayoutPermissionUtil.contains(permissionChecker, selLayout, ActionKeys.UPDATE) %>"
			/>
		</aui:form>
	</c:otherwise>
</c:choose>

<%
redirectURL.setParameter("selPlid", String.valueOf(selLayout.getParentPlid()));
%>

<portlet:actionURL name="deleteLayout" var="deleteLayoutURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
	<portlet:param name="redirect" value="<%= redirectURL.toString() %>" />
	<portlet:param name="plid" value="<%= String.valueOf(layoutsAdminDisplayContext.getSelPlid()) %>" />
</portlet:actionURL>

<aui:script use="aui-base">
	A.one('#<portlet:namespace />layoutsNav').delegate(
		'click',
		function() {
			if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-page") %>')) {
				submitForm(document.hrefFm, '<%= deleteLayoutURL %>');
			}
		},
		'.remove-layout'
	);
</aui:script>