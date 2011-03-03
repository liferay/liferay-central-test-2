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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
Group selGroup = (Group)request.getAttribute(WebKeys.GROUP);

Group group = (Group)request.getAttribute("edit_pages.jsp-group");
Group liveGroup = (Group)request.getAttribute("edit_pages.jsp-liveGroup");
long groupId = (Long)request.getAttribute("edit_pages.jsp-groupId");
long liveGroupId = (Long)request.getAttribute("edit_pages.jsp-liveGroupId");
long stagingGroupId = (Long)request.getAttribute("edit_pages.jsp-stagingGroupId");
long selPlid = ((Long)request.getAttribute("edit_pages.jsp-selPlid")).longValue();
boolean privateLayout = ((Boolean)request.getAttribute("edit_pages.jsp-privateLayout")).booleanValue();
UnicodeProperties liveGroupTypeSettings = (UnicodeProperties)request.getAttribute("edit_pages.jsp-liveGroupTypeSettings");
LayoutSet selLayoutSet = ((LayoutSet)request.getAttribute("edit_pages.jsp-selLayoutSet"));

String rootNodeName = (String)request.getAttribute("edit_pages.jsp-rootNodeName");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_pages.jsp-portletURL");

int pagesCount = 0;

if (selGroup.isLayoutSetPrototype()) {
	privateLayout = true;
}

if (privateLayout) {
	if (group != null) {
		pagesCount = group.getPrivateLayoutsPageCount();
	}
}
else {
	if (group != null) {
		pagesCount = group.getPublicLayoutsPageCount();
	}
}

String[] mainSections = PropsValues.LAYOUT_SET_FORM_UPDATE;

if (!company.isCommunityLogo()) {
	mainSections = ArrayUtil.remove(mainSections, "logo");
}

String branchingKey = "branchingPublic";

if (privateLayout) {
	branchingKey = "branchingPrivate";
}

if (GetterUtil.getBoolean(liveGroupTypeSettings.getProperty(branchingKey))) {
	mainSections = ArrayUtil.append(mainSections, "branches");
}

String[][] categorySections = {mainSections};
%>

<div class="header-row">
	<div class="header-row-content">
		<aui:button-row cssClass="edit-toolbar" id='<%= renderResponse.getNamespace() + "layoutSetToolbar" %>'>
			<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.MANAGE_LAYOUTS) %>">
				<c:if test="<%= SessionErrors.contains(renderRequest, LayoutImportException.class.getName()) || SessionErrors.contains(renderRequest, LARFileException.class.getName()) || SessionErrors.contains(renderRequest, LARTypeException.class.getName()) %>">
					<liferay-util:html-top>
						<div class="aui-helper-hidden" id="<portlet:namespace />importPage">
							<liferay-util:include page="/html/portlet/layouts_admin/export_import.jsp">
								<liferay-util:param name="<%= Constants.CMD %>" value="<%= Constants.IMPORT %>" />
								<liferay-util:param name="groupId" value="<%= String.valueOf(groupId) %>" />
								<liferay-util:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
								<liferay-util:param name="privateLayout" value="<%= String.valueOf(liveGroupId) %>" />
								<liferay-util:param name="rootNodeName" value="<%= rootNodeName %>" />
							</liferay-util:include>
						</div>
					</liferay-util:html-top>

					<aui:script use="aui-dialog">
						new A.Dialog(
							{
								bodyContent: A.one('#<portlet:namespace />importPage').show(),
								centered: true,
								modal: true,
								title: '<liferay-ui:message key="import" />',
								width: 600
							}
						).render();
					</aui:script>
				</c:if>
			</c:if>
		</aui:button-row>
	</div>
</div>

<c:if test="<%= liveGroup.isStaged() %>">
	<liferay-util:include page="/html/portlet/layouts_admin/staging_toolbar.jsp" />
</c:if>

<liferay-util:html-top>
	<liferay-util:include page="/html/portlet/layouts_admin/add_layout.jsp" />
</liferay-util:html-top>

<aui:script use="aui-dialog,aui-toolbar">
	var popUp;
	var exportPopUp;
	var importPopUp;

	var layoutSetToolbar = new A.Toolbar(
		{
			activeState: false,
			boundingBox: '#<portlet:namespace />layoutSetToolbar',
			children: [
				<c:if test="<%= !group.isLayoutPrototype() %>">
					{
						handler: function(event) {
							if (!popUp) {
								var content = A.one('#<portlet:namespace />addLayout');

								popUp = new A.Dialog(
									{
										bodyContent: content.show(),
										centered: true,
										title: '<liferay-ui:message key="add-page" />',
										modal: true,
										width: 500
									}
								).render();
							}

							popUp.show();
						},
						icon: 'circle-plus',
						label: '<liferay-ui:message key="add-page" />'
					},
				</c:if>

				<c:if test="<%= (pagesCount > 0) && (liveGroup.isStaged() || selGroup.isLayoutSetPrototype() || selGroup.isStagingGroup() || portletName.equals(PortletKeys.COMMUNITIES) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_ORGANIZATIONS) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_COMMUNITIES) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_USER_GROUPS) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_USERS) || portletName.equals(PortletKeys.GROUP_PAGES)) %>">
					<liferay-portlet:actionURL plid="<%= selPlid %>" portletName="<%= PortletKeys.MY_PLACES %>" var="viewPagesURL">
						<liferay-portlet:param name="struts_action" value="/my_places/view" />
						<liferay-portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
						<liferay-portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
					</liferay-portlet:actionURL>

					{
						handler: function(event) {
							window.open('<%= viewPagesURL %>').focus();
						},
						icon: 'search',
						label: '<liferay-ui:message key="view-pages" />'
					},
				</c:if>

				<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.MANAGE_LAYOUTS) %>">
					{
								type: 'ToolbarSpacer'
					},
					{
						handler: function(event) {
							if (!exportPopUp) {
								exportPopUp = new A.Dialog(
									{
										centered: true,
										constrain: true,
										modal: true,
										title: '<liferay-ui:message key="export" />',
										width: 600
									}
								).render();

								<portlet:renderURL var="exportPagesURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
									<portlet:param name="struts_action" value="/layouts_admin/export_layouts" />
									<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EXPORT %>" />
									<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
									<portlet:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
									<portlet:param name="privateLayout" value="<%= String.valueOf(liveGroupId) %>" />
									<portlet:param name="redirect" value="<%= currentURL %>" />
									<portlet:param name="rootNodeName" value="<%= rootNodeName %>" />
								</portlet:renderURL>

								exportPopUp.plug(
									A.Plugin.IO,
									{
										after: {
											success: function() {
												exportPopUp.centered();
											}
										},
										autoLoad: false,
										uri: '<%= exportPagesURL.toString() %>'
									}
								);
							}

							exportPopUp.show();
							exportPopUp.io.start();
						},
						icon: 'arrowthick-1-b',
						label: '<liferay-ui:message key="export" />'
					},
					{
						handler: function(event) {
							if (!importPopUp) {
								importPopUp = new A.Dialog(
									{
										centered: true,
										constrain: true,
										modal: true,
										title: '<liferay-ui:message key="import" />',
										width: 600
									}
								).render();

								<portlet:renderURL var="importPagesURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
									<portlet:param name="struts_action" value="/layouts_admin/import_layouts" />
									<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.IMPORT %>" />
									<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
									<portlet:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
									<portlet:param name="privateLayout" value="<%= String.valueOf(liveGroupId) %>" />
									<portlet:param name="redirect" value="<%= currentURL %>" />
									<portlet:param name="redirectWindowState" value="<%= renderRequest.getWindowState().toString() %>" />
									<portlet:param name="rootNodeName" value="<%= rootNodeName %>" />
								</portlet:renderURL>

								importPopUp.plug(
									A.Plugin.IO,
									{
										after: {
											success: function() {
												importPopUp.centered();

												var form = importPopUp.get('contentBox').one('#<portlet:namespace />fm1');
												form.on(
													'submit',
													function(event) {
														importPopUp.io.showLoading();
													}
												);
											}
										},
										autoLoad: false,
										uri: '<%= importPagesURL.toString() %>'
									}
								);
							}

							importPopUp.show();
							importPopUp.centered();
							importPopUp.io.start();
						},
						icon: 'arrowthick-1-t',
						label: '<liferay-ui:message key="import" />'
					}
				</c:if>
			]
		}
	).render();
</aui:script>

<portlet:actionURL var="editLayoutSetURL">
	<portlet:param name="struts_action" value="/layouts_admin/edit_layout_set" />
</portlet:actionURL>

<aui:form action="<%= editLayoutSetURL %>" cssClass="edit-layoutset-form" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveLayoutset();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="liveGroupId" type="hidden" value="<%= liveGroupId %>" />
	<aui:input name="stagingGroupId" type="hidden" value="<%= stagingGroupId %>" />
	<aui:input name="selPlid" type="hidden" value="<%= selPlid %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= privateLayout %>" />
	<aui:input name="layoutSetId" type="hidden" value="<%= selLayoutSet.getLayoutSetId() %>" />
	<aui:input name="<%= PortletDataHandlerKeys.SELECTED_LAYOUTS %>" type="hidden" />

	<liferay-ui:form-navigator
		categoryNames="<%= _CATEGORY_NAMES %>"
		categorySections="<%= categorySections %>"
		jspPath="/html/portlet/layouts_admin/layoutset/"
	/>
</aui:form>

<aui:script>
	function <portlet:namespace />saveLayoutset(action) {
		document.<portlet:namespace />fm.encoding = 'multipart/form-data';

		if (action) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = action;
		}
		else {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'update';
		}

		document.<portlet:namespace />fm.<portlet:namespace />redirect.value += Liferay.Util.getHistoryParam('<portlet:namespace />');;

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />updateLogo() {
		document.<portlet:namespace />fm.encoding = 'multipart/form-data';
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'logo';
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />updateRobots() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'robots';
		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />removePage',
		function(box) {
			var A = AUI();

			var selectEl = A.one(box);

			var currentValue = selectEl.val() || null;

			Liferay.Util.removeItem(box);
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />updateDisplayOrder',
		function() {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'display_order';
			document.<portlet:namespace />fm.<portlet:namespace />layoutIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />layoutIdsBox);
			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />updateStaging',
		function() {
			var A = AUI();

			var selectEl = A.one('#<portlet:namespace />stagingType');

			var currentValue = selectEl.val() || null;

			var ok = false;

			if (currentValue == 0) {
				ok = confirm('<%= UnicodeLanguageUtil.format(pageContext, "are-you-sure-you-want-to-deactivate-staging-for-x", liveGroup.getDescriptiveName()) %>');
			}
			else if (currentValue == 1) {
				ok = confirm('<%= UnicodeLanguageUtil.format(pageContext, "are-you-sure-you-want-to-activate-local-staging-for-x", liveGroup.getDescriptiveName()) %>');
			}
			else if (currentValue == 2) {
				ok = confirm('<%= UnicodeLanguageUtil.format(pageContext, "are-you-sure-you-want-to-activate-remote-staging-for-x", liveGroup.getDescriptiveName()) %>');
			}

			if (ok) {
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'staging';
				submitForm(document.<portlet:namespace />fm);
			}
		},
		['aui-base']
	);
</aui:script>

<%!
private static String[] _CATEGORY_NAMES = {""};
%>