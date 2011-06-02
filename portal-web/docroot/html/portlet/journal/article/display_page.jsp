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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

String defaultLanguageId = (String)request.getAttribute("edit_article.jsp-defaultLanguageId");

String layoutUuid = BeanParamUtil.getString(article, request, "layoutUuid");

String layoutBreadcrumb = StringPool.BLANK;

if (Validator.isNotNull(layoutUuid)) {
	Layout selLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(layoutUuid, themeDisplay.getParentGroupId());

	layoutBreadcrumb = _getLayoutBreadcrumb(selLayout, locale);
}

boolean preselectCurrentLayout = false;

if ((article == null) || article.isNew()) {
	UnicodeProperties typeSettingsProperties = layout.getTypeSettingsProperties();

	long refererPlid = ParamUtil.getLong(request, "refererPlid", LayoutConstants.DEFAULT_PLID);

	if (refererPlid > 0) {
		Layout refererLayout = LayoutLocalServiceUtil.getLayout(refererPlid);

		typeSettingsProperties = refererLayout.getTypeSettingsProperties();

		String defaultAssetPublisherPortletId = typeSettingsProperties.getProperty(LayoutTypePortletConstants.DEFAULT_ASSET_PUBLISHER_PORTLET_ID);

		if (Validator.isNotNull(defaultAssetPublisherPortletId)) {
			preselectCurrentLayout = true;
		}
	}
}

int privateLayoutsCount = themeDisplay.getParentGroup().getPrivateLayoutsPageCount();
int publicLayoutsCount = themeDisplay.getParentGroup().getPublicLayoutsPageCount();
%>

<liferay-ui:error-marker key="errorSection" value="display-page" />

<h3><liferay-ui:message key="display-page" /><liferay-ui:icon-help message="default-display-page-help" /></h3>

<div id="<portlet:namespace />pagesContainer">
	<aui:input id="pagesContainerInput" name="layoutUuid" type="hidden" value="<%= layoutUuid %>"/>

	<div id="<portlet:namespace />dispayPageItemContainer" class="dispay-page-item-container aui-helper-hidden">
		<span class="dispay-page-item">
			<span>
				<span id="<portlet:namespace />displayPageNameInput"><%= layoutBreadcrumb %></span>
				<span class="dispay-page-item-remove aui-icon aui-icon-close" id="<portlet:namespace />displayPageItemRemove" tabindex="0">
				</span>
			</span>
		</span>
	</div>
</div>

<div class="dispay-page-toolbar-container" id="<portlet:namespace />toolbarContainer">
</div>

<aui:script use="aui-toolbar,aui-button-item,aui-io,tabview,aui-tree">
	var EVENT_CLICK = 'click';

	var CSS_HELPER_HIDDEN = 'aui-helper-hidden';

	var CSS_PAGE_UNACCEPTABLE = 'layout-page-unacceptable';

	var choosePageDialog;

	var dispayPageItemContainer = A.one('#<portlet:namespace />dispayPageItemContainer');

	var displayPageNameInput = A.one('#<portlet:namespace />displayPageNameInput');

	var okButton;

	var pagesContainerInput = A.one('#<portlet:namespace />pagesContainerInput');

	var pagesTabViewId = A.guid();

	var privatePagesTabId = A.guid();

	var privatePagesTabNode;

	var privatePagesTabContentId = A.guid();

	var publicPagesTabId = A.guid();

	var publicPagesTabContentId = A.guid();

	var publicPagesTabNode;

	var selectedNodeMessage;

	var tabView;

	var treeViewPrivatePages;

	var treeViewPublicPages;

	var treePublicPagesContainerId = '<portlet:namespace />treeContainerPublicPagesOutput';

	var treePrivatePagesContainerId = '<portlet:namespace />treeContainerPrivatePagesOutput';

	var TPL_TAB_VIEW = '<div id="<portlet:namespace />{pagesTabViewId}"></div>';

	var TPL_TAB_VIEW_PRIVATE = '<div id="<portlet:namespace />{privatePagesTabId}">' +
		'<div id="<portlet:namespace />{privatePagesTabContentId}"></div>' +
	'</div>';

	var TPL_TAB_VIEW_PUBLIC = '<div id="<portlet:namespace />{publicPagesTabId}">' +
		'<div id="<portlet:namespace />{publicPagesTabContentId}"></div>' +
	'</div>';

	var TPL_FOOTER_CONTENT = '<div class="aui-layout">' +
		'<div class="aui-layout-content">' +
			'<div class="aui-column aui-w60">' +
				'<div class="selected-page-message" id="<portlet:namespace />selectedPageMessage">' +
					Liferay.Language.get('there-is-no-selected-page') +
				'</div>' +
			'</div>' +
			'<div class="aui-column aui-w40">' +
				'<div id="<portlet:namespace />buttonsContainer">' +
				'</div>' +
			'</div>' +
		'</div>' +
	'</div>';

	<portlet:resourceURL var="treeURLPublicPages">
		<portlet:param name="struts_action" value="/journal/select_display_page" />
		<portlet:param name="cmd" value="<%= ActionKeys.VIEW_TREE %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(themeDisplay.getParentGroupId()) %>" />
		<portlet:param name="checkContentDisplayPage" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="expandFirstNode" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="saveState" value="<%= Boolean.FALSE.toString() %>" />
		<portlet:param name="treeId" value="treeContainerPublicPages" />
	</portlet:resourceURL>

	<portlet:resourceURL var="treeURLPrivatePages">
		<portlet:param name="struts_action" value="/journal/select_display_page" />
		<portlet:param name="cmd" value="<%= ActionKeys.VIEW_TREE %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(themeDisplay.getParentGroupId()) %>" />
		<portlet:param name="checkContentDisplayPage" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="expandFirstNode" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="saveState" value="<%= Boolean.FALSE.toString() %>" />
		<portlet:param name="tabs1" value="private-pages" />
		<portlet:param name="treeId" value="treeContainerPrivatePages" />
	</portlet:resourceURL>

	new A.Toolbar(
		{
			children: [
				{
					icon: 'search',
					id: '<portlet:namespace />chooseDisplayPage',
					handler: {
						fn: onShowChoosePageEvent
					},
					label: Liferay.Language.get('select')
				}
			]
		}
	).render('#<portlet:namespace />toolbarContainer');

	if (displayPageNameInput.text()) {
		dispayPageItemContainer.removeClass(CSS_HELPER_HIDDEN);
	}

	var removeDisplayPageItem = A.one('#<portlet:namespace />displayPageItemRemove');

	removeDisplayPageItem.on(
		EVENT_CLICK,
		function(event) {
			pagesContainerInput.set('value', '');

			dispayPageItemContainer.addClass(CSS_HELPER_HIDDEN);
		}
	);

	function bindTreeUI(treeInstance) {
		treeInstance.after(
			'lastSelectedChange',
			function(event) {
				setSelectedPage(event.newVal);
			}
		);
	}

	function getChosenPagePath(node) {
		var buffer = [];

		while(node && node instanceof A.TreeNode) {
			var labelEl = node.get('labelEl');

			var link = labelEl.one('a');

			if (!link) {
				break;
			}

			var label = link.text();

			buffer.unshift(label);

			node = node.get('parentNode');
		}

		if (buffer.length) {
			var pathPrefix = Liferay.Language.get('private-pages');

			var publicPages = isPublicPages();

			if (publicPages) {
				pathPrefix = Liferay.Language.get('public-pages');
			}

			buffer.unshift(pathPrefix);
		}

		return buffer.join(' > ');
	}

	function getChoosePageDialog() {
		if (!choosePageDialog) {
			var bodyContent = A.substitute(
				TPL_TAB_VIEW,
				{
					pagesTabViewId: pagesTabViewId
				}
			);

			choosePageDialog = new A.Dialog(
				{
					align: {
						node: A.one('#portlet_<%= portletDisplay.getId() %>'),
						points: ['tc', 'tc']
					},
					bodyContent: bodyContent,
					cssClass: 'display-page-dialog',
					footerContent: TPL_FOOTER_CONTENT,
					hidden: true,
					resizable: false,
					title: Liferay.Language.get('choose-a-display-page'),
					width: 450
				}
			).render();

			choosePageDialog.hide();

			selectedNodeMessage = A.one('#<portlet:namespace />selectedPageMessage');

			okButton = new A.ButtonItem({
				disabled: true,
				label: Liferay.Language.get('ok')
			});

			var cancelButton = new A.ButtonItem({
				label: Liferay.Language.get('cancel')
			});

			var buttonsContaner = '#<portlet:namespace />buttonsContainer';

			okButton.render(buttonsContaner);

			cancelButton.render(buttonsContaner);

			okButton.on(EVENT_CLICK, setDisplayPage);

			cancelButton.on(EVENT_CLICK, choosePageDialog.hide, choosePageDialog);

			var tabs = [];

			if (<%= privateLayoutsCount > 0 %>) {
				tabs.push(
					{
						label: Liferay.Language.get('private-pages'),
						content: A.substitute(
							TPL_TAB_VIEW_PRIVATE,
							{
								privatePagesTabContentId: privatePagesTabContentId,
								privatePagesTabId: privatePagesTabId
							}
						)
					}
				);
			}

			if (<%= publicLayoutsCount > 0 %>) {
				tabs.push(
					{
						label: Liferay.Language.get('public-pages'),
						content: A.substitute(
							TPL_TAB_VIEW_PUBLIC,
							{
								publicPagesTabContentId: publicPagesTabContentId,
								publicPagesTabId: publicPagesTabId
							}
						)
					}
				);
			}

			tabView = new A.TabView(
				{
					children: tabs,
					srcNode: '#<portlet:namespace />' + pagesTabViewId
				}
			);

			tabView.render();

			choosePageDialog.hide();

			tabView.after(
				'selectionChange',
				function() {
					selectedNodeMessage.empty();

					loadPages();
				}
			);

			if (<%= publicLayoutsCount > 0 %>) {
				publicPagesTabNode = A.one('#<portlet:namespace />' + publicPagesTabContentId);
				publicPagesTabNode.plug(A.Plugin.ParseContent);
			}

			if (<%= privateLayoutsCount > 0 %>) {
				privatePagesTabNode = A.one('#<portlet:namespace />' + privatePagesTabContentId);
				privatePagesTabNode.plug(A.Plugin.ParseContent);
			}

			choosePageDialog.on('visibleChange', onChoosePageDialogVisibleChange);
		}

		return choosePageDialog;
	}

	function isPublicPages() {
		if (tabView.size() == 2) {
			var selectedTab = tabView.get('selection');

			var index = selectedTab.get('index');

			return (index == 1);
		}
		else if (<%= publicLayoutsCount > 0 %>) {
			return true;
		}

		return false;
	}

	function loadPages() {
		var url;

		var publicPages = isPublicPages();

		if (publicPages && !treeViewPublicPages) {
			url = '<%= treeURLPublicPages %>';
		}
		else if (!treeViewPrivatePages) {
			url = '<%= treeURLPrivatePages %>';
		}

		if (url) {
			A.io.request(
				url,
				{
					on: {
						success: function(event, id, obj) {
							var response = this.get('responseData');

							onPagesLoadSuccess(response, publicPages);
						}
					}
				}
			);
		}
		else {
			var treeInstance = publicPages ? treeViewPublicPages : treeViewPrivatePages;

			setSelectedPage(treeInstance.get('lastSelected'));
		}
	}

	function onPagesLoadSuccess(response, publicPages) {
		var treeContainerId;

		var treeWrapper;

		if (publicPages) {
			treeContainerId = treePublicPagesContainerId;

			treeWrapper = publicPagesTabNode;
		}
		else {
			treeContainerId = treePrivatePagesContainerId;

			treeWrapper = privatePagesTabNode;
		}

		treeWrapper.setContent(response);

		var treeContainer = A.one('#' + treeContainerId);

		var processTreeTask = A.debounce(
			function() {
				treeViewInstance = treeContainer.getData('treeInstance');

				if (treeViewInstance) {
					processTreeTask.cancel();

					if (publicPages) {
						treeViewPublicPages = treeViewInstance;
					}
					else {
						treeViewPrivatePages = treeViewInstance;
					}

					bindTreeUI(treeViewInstance);

					preventPageReload(treeContainer);

					setSelectedPage(treeViewInstance.get('lastSelected'));
				}
				else {
					processTreeTask();
				}
			},
			100
		);

		processTreeTask();
	}

	function onShowChoosePageEvent(event) {
		var choosePageDialog = getChoosePageDialog();

		choosePageDialog.show();
	}

	function onChoosePageDialogVisibleChange(event) {
		if (!event.newVal) {
			var treeContainer;

			if (treeViewPublicPages) {
				treeViewPublicPages.destroy();

				treeViewPublicPages = null;
			}

			if (treeViewPrivatePages) {
				treeViewPrivatePages.destroy();

				treeViewPrivatePages = null;
			}

			if (treeContainer) {
				treeContainer.purge(true);
			}

			selectedNodeMessage.html(Liferay.Language.get('there-is-no-selected-page'));
		}
		else {
			loadPages();
		}
	}

	function preventPageReload(treeNode) {
		treeNode.on(
			EVENT_CLICK,
			function(e) {
				e.halt();
			}
		);
	}

	function setDisplayPage() {
		var publicPages = isPublicPages();

		var tree;

		if (publicPages && treeViewPublicPages) {
			tree = treeViewPublicPages;
		}
		else if (treeViewPrivatePages){
			tree = treeViewPublicPages;
		}
		else {
			return;
		}

		var lastSelected = tree.get('lastSelected');

		if (lastSelected) {
			var labelEl = lastSelected.get('labelEl');

			var link = labelEl.one('a');

			if (!link || link.hasClass(CSS_PAGE_UNACCEPTABLE)) {
				return;
			}

			var label = getChosenPagePath(lastSelected);

			var uuid = link.attr('data-uuid');

			pagesContainerInput.set('value', uuid);

			displayPageNameInput.html(label);

			dispayPageItemContainer.removeClass(CSS_HELPER_HIDDEN);

			if (A.UA.webkit) {
				var parentNode = removeDisplayPageItem.get('parentNode');
				removeDisplayPageItem.remove();
				parentNode.appendChild(removeDisplayPageItem);
			}
		}

		choosePageDialog.hide();
	}

	function setSelectedPage(lastSelectedNode) {
		if (lastSelectedNode) {
			var labelEl = lastSelectedNode.get('labelEl');

			var link = labelEl.one('a');

			var text = getChosenPagePath(lastSelectedNode);

			if (link && !link.hasClass(CSS_PAGE_UNACCEPTABLE)) {
				selectedNodeMessage.html(text);

				okButton.set('disabled', false);
			}
			else {
				if (text) {
					text = A.substitute(Liferay.Language.get('x-is-not-a-content-display-page'), ['"' + text + '"']);
				}
				else {
					text = Liferay.Language.get('there-is-no-selected-page');
				}

				selectedNodeMessage.html(text);

				okButton.set('disabled', true);
			}
		}
		else {
			selectedNodeMessage.html(Liferay.Language.get('there-is-no-selected-page'));

			okButton.set('disabled', true);
		}
	}
</aui:script>

<%!
private String _getLayoutBreadcrumb(Layout layout, Locale locale) throws PortalException, SystemException {
	StringBundler sb = new StringBundler();

	if (layout.isPrivateLayout()) {
		sb.append(LanguageUtil.get(locale, "private-pages"));
	}
	else {
		sb.append(LanguageUtil.get(locale, "public-pages"));
	}

	sb.append(StringPool.SPACE);
	sb.append(StringPool.GREATER_THAN);
	sb.append(StringPool.SPACE);

	List<Layout> ancestors = layout.getAncestors();

	Collections.reverse(ancestors);

	for (Layout ancestor : ancestors) {
		sb.append(ancestor.getName(locale));

		sb.append(StringPool.SPACE);
		sb.append(StringPool.GREATER_THAN);
		sb.append(StringPool.SPACE);
	}

	sb.append(layout.getName(locale));

	return sb.toString();
}
%>