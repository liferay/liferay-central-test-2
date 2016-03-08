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
groupId = ParamUtil.getLong(request, "groupId");

group = null;

if (groupId > 0) {
	group = GroupLocalServiceUtil.getGroup(groupId);
}

privateLayout = ParamUtil.getBoolean(request, "privateLayout");
String selectedLayoutIds = ParamUtil.getString(request, "selectedLayoutIds");

boolean disableInputs = ParamUtil.getBoolean(request, "disableInputs");

Map<String, String[]> parameterMap = (Map<String, String[]>)GetterUtil.getObject(request.getAttribute("select_pages.jsp-parameterMap"), Collections.emptyMap());
%>

<aui:input name="layoutIds" type="hidden" value="<%= ExportImportHelperUtil.getSelectedLayoutsJSON(groupId, privateLayout, selectedLayoutIds) %>" />

<ul class="flex-container layout-selector" id="<portlet:namespace />pages">
	<li class="layout-selector-options">
		<aui:fieldset label="pages-options">
			<c:choose>
				<c:when test="<%= privateLayout %>">
					<li>
						<aui:button disabled="<%= disableInputs %>" id="changeToPrivateLayoutsButton" value="change-to-public-pages" />
					</li>
				</c:when>
				<c:otherwise>
					<li>
						<aui:button disabled="<%= disableInputs %>" id="changeToPublicLayoutsButton" value="change-to-private-pages" />
					</li>
				</c:otherwise>
			</c:choose>

			<c:if test="<%= LayoutStagingUtil.isBranchingLayoutSet(group, privateLayout) %>">

				<%
				long layoutSetBranchId = MapUtil.getLong(parameterMap, "layoutSetBranchId");
				List<LayoutSetBranch> layoutSetBranches = null;

				if (disableInputs && (layoutSetBranchId > 0)) {
					layoutSetBranches = new ArrayList<>(1);

					layoutSetBranches.add(LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(layoutSetBranchId));
				}
				else {
					layoutSetBranches = LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(group.getGroupId(), privateLayout);
				}
				%>

				<aui:select disabled="<%= disableInputs %>" label="site-pages-variation" name="layoutSetBranchId">

					<%
					for (LayoutSetBranch layoutSetBranch : layoutSetBranches) {
						boolean selected = false;

						if ((layoutSetBranchId == layoutSetBranch.getLayoutSetBranchId()) || ((layoutSetBranchId == 0) && layoutSetBranch.isMaster())) {
							selected = true;
						}
					%>

					<aui:option label="<%= HtmlUtil.escape(layoutSetBranch.getName()) %>" selected="<%= selected %>" value="<%= layoutSetBranch.getLayoutSetBranchId() %>" />

					<%
					}
					%>

				</aui:select>
			</c:if>
		</aui:fieldset>
	</li>

	<li class="layout-selector-options">
		<aui:fieldset label="pages-to-publish">

			<%
			long selPlid = ParamUtil.getLong(request, "selPlid", LayoutConstants.DEFAULT_PLID);
			String treeId = ParamUtil.getString(request, "treeId");
			%>

			<div class="pages-selector">
				<liferay-layout:layouts-tree
					defaultStateChecked="<%= true %>"
					draggableTree="<%= false %>"
					groupId="<%= groupId %>"
					incomplete="<%= false %>"
					portletURL="<%= renderResponse.createRenderURL() %>"
					privateLayout="<%= privateLayout %>"
					rootNodeName="<%= group.getLayoutRootNodeName(privateLayout, locale) %>"
					selectableTree="<%= true %>"
					selectedLayoutIds="<%= selectedLayoutIds %>"
					selPlid="<%= selPlid %>"
					treeId="<%= treeId %>"
				/>
			</div>
		</aui:fieldset>
	</li>

	<li class="layout-selector-options">
		<aui:fieldset label="look-and-feel">
			<aui:input disabled="<%= disableInputs %>" helpMessage="export-import-theme-settings-help" label="theme-settings" name="<%= PortletDataHandlerKeys.THEME_REFERENCE %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.THEME_REFERENCE, true) %>" />

			<aui:input disabled="<%= disableInputs %>" label="logo" name="<%= PortletDataHandlerKeys.LOGO %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.LOGO, true) %>" />

			<aui:input disabled="<%= disableInputs %>" label="site-pages-settings" name="<%= PortletDataHandlerKeys.LAYOUT_SET_SETTINGS %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.LAYOUT_SET_SETTINGS, true) %>" />

			<aui:input disabled="<%= disableInputs %>" helpMessage="delete-missing-layouts-staging-help" label="delete-missing-layouts" name="<%= PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS, false) %>" />
		</aui:fieldset>
	</li>
</ul>