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
String itemSelectedEventName = (String)request.getAttribute(LayoutItemSelectorView.ITEM_SELECTED_EVENT_NAME);
URLItemSelectorCriterion urlItemSelectorCriterion = (URLItemSelectorCriterion)request.getAttribute(LayoutItemSelectorView.LAYOUT_ITEM_SELECTOR_CRITERION);

long groupId = themeDisplay.getScopeGroupId();

Group group = GroupLocalServiceUtil.fetchGroup(groupId);

request.setAttribute(WebKeys.GROUP, group);

String tabs1Names = "";

if (group.getPublicLayoutsPageCount() > 0) {
	tabs1Names += "public-pages,";
}

if (group.getPrivateLayoutsPageCount() > 0) {
	tabs1Names += "private-pages";
}
%>

<liferay-ui:tabs names="<%= tabs1Names %>" refresh="<%= false %>">

	<%
	boolean checkContentDisplayPage = ParamUtil.getBoolean(request, "checkContentDisplayPage");
	String selectedLayoutIds = ParamUtil.getString(request, "selectedLayoutIds");
	long selPlid = ParamUtil.getLong(request, "selPlid", LayoutConstants.DEFAULT_PLID);

	PortletURL editLayoutURL = PortletProviderUtil.getPortletURL(request, Layout.class.getName(), PortletProvider.Action.EDIT);

	editLayoutURL.setParameter("redirect", currentURL);
	editLayoutURL.setParameter("groupId", String.valueOf(groupId));
	editLayoutURL.setParameter("viewLayout", Boolean.TRUE.toString());
	%>

	<c:if test="<%= group.getPublicLayoutsPageCount() > 0 %>">

		<%
		editLayoutURL.setParameter("tabs1", "public-pages");
		%>

		<liferay-ui:section>
			<div>
				<liferay-ui:layouts-tree
					checkContentDisplayPage="<%= checkContentDisplayPage %>"
					draggableTree="<%= false %>"
					groupId="<%= groupId %>"
					portletURL="<%= editLayoutURL %>"
					rootNodeName="<%= group.getLayoutRootNodeName(false, themeDisplay.getLocale()) %>"
					saveState="<%= false %>"
					selPlid="<%= selPlid %>"
					selectedLayoutIds="<%= selectedLayoutIds %>"
					treeId="treeContainerPublicPages"
				/>
			</div>
		</liferay-ui:section>
	</c:if>

	<c:if test="<%= group.getPrivateLayoutsPageCount() > 0 %>">

		<%
		editLayoutURL.setParameter("tabs1", "private-pages");
		%>

		<liferay-ui:section>
			<div>
				<liferay-ui:layouts-tree
					checkContentDisplayPage="<%= checkContentDisplayPage %>"
					draggableTree="<%= false %>"
					groupId="<%= groupId %>"
					portletURL="<%= editLayoutURL %>"
					privateLayout="<%= true %>"
					rootNodeName="<%= group.getLayoutRootNodeName(true, themeDisplay.getLocale()) %>"
					saveState="<%= false %>"
					selPlid="<%= selPlid %>"
					selectedLayoutIds="<%= selectedLayoutIds %>"
					treeId="treeContainerPrivatePages"
				/>
			</div>
		</liferay-ui:section>
	</c:if>
</liferay-ui:tabs>

<div class="alert" id="<portlet:namespace />selectPageMessage">
	<aui:button cssClass="selector-button" disabled="<%= true %>" value="choose" />

	<span class="selected-page-message">
		<liferay-ui:message key="there-is-no-selected-page" />
	</span>
</div>

<aui:script use="aui-base">
	var LString = A.Lang.String;
	var Util = Liferay.Util;

	var bindTreeUI = function(containerId) {
		var container = A.one('#<portlet:namespace />' + containerId);

		if (container) {
			container.swallowEvent('click', true);

			var tree = container.getData('tree-view');

			tree.after('lastSelectedChange', setSelectedPage);
		}
	};

	var getChosenPagePath = function(node) {
		var buffer = [];

		if (A.instanceOf(node, A.TreeNode)) {
			var labelText = LString.escapeHTML(node.get('labelEl').text());

			buffer.push(labelText);

			node.eachParent(
				function(treeNode) {
					var labelEl = treeNode.get('labelEl');

					if (labelEl) {
						labelText = LString.escapeHTML(labelEl.text());

						buffer.unshift(labelText);
					}
				}
			);
		}

		return buffer.join(' > ');
	};

	var selectPageMessage = A.one('#<portlet:namespace />selectPageMessage');

	var button = selectPageMessage.one('.selector-button');

	button.on(
		'click',
		function(event) {
			var currentTarget = event.currentTarget;

			Util.getOpener().Liferay.fire(
				'<%= itemSelectedEventName %>',
				{

					<%
					String ckEditorFuncNum = ParamUtil.getString(request, "CKEditorFuncNum");
					%>

					<c:if test="<%= Validator.isNotNull(ckEditorFuncNum) %>">
						ckeditorfuncnum: <%= ckEditorFuncNum %>,
					</c:if>

					layoutpath: currentTarget.attr('data-layoutpath'),
					returnType: currentTarget.attr('data-returnType'),
					value: currentTarget.attr('data-value')
				}
			);

			Util.getWindow().destroy();
		}
	);

	var setSelectedPage = function(event) {
		var disabled = true;

		var messageText = '<%= UnicodeLanguageUtil.get(request, "there-is-no-selected-page") %>';

		var messageType = 'alert';

		var lastSelectedNode = event.newVal;

		var labelEl = lastSelectedNode.get('labelEl');

		var link = labelEl.one('a');

		var url = link.attr('data-url');
		var uuid = link.attr('data-uuid');

		if (link && url) {
			disabled = false;

			messageText = getChosenPagePath(lastSelectedNode);

			messageType = 'info';

			button.attr('data-layoutpath', messageText);

			<%
			String returnType = StringPool.BLANK;

			for (Class<?> desiredReturnType : urlItemSelectorCriterion.getDesiredReturnTypes()) {
				if (desiredReturnType == URL.class) {
					returnType = URL.class.getName();
				}
				else if (desiredReturnType == UUID.class) {
					returnType = UUID.class.getName();
				}
				else {
					continue;
				}

				break;
			}

			if (Validator.isNull(returnType)) {
				throw new IllegalArgumentException("Invalid return type " + returnType);
			}
			%>

			button.attr('data-returnType', '<%= returnType %>');

			<c:choose>
				<c:when test="<%= returnType.equals(URL.class.getName()) %>">
					button.attr('data-value', url);
				</c:when>
				<c:when test="<%= returnType.equals(UUID.class.getName()) %>">
					button.attr('data-value', uuid);
				</c:when>
			</c:choose>
		}

		Liferay.Util.toggleDisabled(button, disabled);

		selectPageMessage.one('.selected-page-message').html(messageText);

		selectPageMessage.attr('className', 'alert alert-' + messageType);
	};

	<c:if test="<%= group.getPublicLayoutsPageCount() > 0 %>">
		bindTreeUI('treeContainerPublicPagesOutput');
	</c:if>

	<c:if test="<%= group.getPrivateLayoutsPageCount() > 0 %>">
		bindTreeUI('treeContainerPrivatePagesOutput');
	</c:if>
</aui:script>