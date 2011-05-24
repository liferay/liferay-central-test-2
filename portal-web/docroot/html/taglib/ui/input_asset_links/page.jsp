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

<%@ include file="/html/taglib/ui/input_asset_links/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_input_asset_links_page") + StringPool.UNDERLINE;

String className = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-asset-links:className"));
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:input-asset-links:classPK"));

AssetEntry assetEntry = null;

List<AssetLink> assetLinks = new ArrayList<AssetLink>();

if (classPK > 0) {
	assetEntry = AssetEntryLocalServiceUtil.getEntry(className, classPK);

	assetLinks = AssetLinkLocalServiceUtil.getLinks(assetEntry.getEntryId(), AssetLinkConstants.TYPE_RELATED);
}

Group controlPanelGroup = GroupLocalServiceUtil.getGroup(themeDisplay.getCompanyId(), GroupConstants.CONTROL_PANEL);

PortletURL assetBrowserURL = PortletURLFactoryUtil.create(request, PortletKeys.ASSET_BROWSER, LayoutLocalServiceUtil.getDefaultPlid(controlPanelGroup.getGroupId(), true), PortletRequest.RENDER_PHASE);

assetBrowserURL.setWindowState(LiferayWindowState.POP_UP);
assetBrowserURL.setPortletMode(PortletMode.VIEW);

assetBrowserURL.setParameter("struts_action", "/asset_browser/view");
%>

<liferay-ui:icon-menu align="left" cssClass="select-existing-selector" icon='<%= themeDisplay.getPathThemeImages() + "/common/search.png" %>' id='<%= randomNamespace + "inputAssetLinks" %>' message="select" showWhenSingleIcon="<%= true %>">

	<%
	for (AssetRendererFactory curRendererFactory : AssetRendererFactoryRegistryUtil.getAssetRendererFactories()) {
		if (curRendererFactory.isSelectable()) {
			assetBrowserURL.setParameter("typeSelection", curRendererFactory.getClassName());
			assetBrowserURL.setParameter("callback", randomNamespace + "addAssetLink");

			String href = "javascript:openAssetBrowser('" + assetBrowserURL.toString() + "')";
		%>

			<liferay-ui:icon
				message='<%= "model.resource." + curRendererFactory.getClassName() %>'
				src="<%= curRendererFactory.getIconPath(portletRequest) %>"
				url="<%= href %>"
			/>

		<%
		}
	}
	%>

</liferay-ui:icon-menu>

<br />

<div class="separator"><!-- --></div>

<liferay-util:buffer var="removeLinkIcon">
	<liferay-ui:icon
		image="unlink"
		label="<%= true %>"
		message="remove"
	/>
</liferay-util:buffer>

<liferay-ui:search-container
	headerNames="type,title,null"
	id='<%= portletResponse.getNamespace() + "assetLinkSearchContainer" %>'
>
	<liferay-ui:search-container-results
		results="<%= assetLinks %>"
		total="<%= assetLinks.size() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portlet.asset.model.AssetLink"
		keyProperty="entryId2"
		modelVar="assetLink"
	>

		<%
		AssetEntry assetLinkEntry = null;

		long assetLinkEntryId = 0;

		if (assetLink.getEntryId1() == assetEntry.getEntryId()) {
			assetLinkEntry = AssetEntryLocalServiceUtil.getEntry(assetLink.getEntryId2());
		}
		else {
			assetLinkEntry = AssetEntryLocalServiceUtil.getEntry(assetLink.getEntryId1());
		}

		String assetTitle = StringPool.BLANK;
		String assetType = StringPool.BLANK;

		if (assetLinkEntry != null) {
			AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(PortalUtil.getClassName(assetLinkEntry.getClassNameId()));

			AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(assetLinkEntry.getClassPK());

			assetLinkEntryId = assetLinkEntry.getEntryId();

			assetTitle = assetRenderer.getTitle(locale);
			assetType = ResourceActionsUtil.getModelResource(locale, assetLinkEntry.getClassName());
        }
		%>

		<liferay-ui:search-container-column-text
			name="type"
			value="<%= assetType %>"
		/>

		<liferay-ui:search-container-column-text
			name="title"
			value="<%= assetTitle %>"
		/>

		<liferay-ui:search-container-column-text>
			<a class="modify-link" data-rowId="<%= assetLinkEntryId %>" href="javascript:;"><%= removeLinkIcon %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator paginate="<%= false %>" />
</liferay-ui:search-container>

<aui:input name="assetLinkEntryIds" type="hidden" />

<aui:script>
	function openAssetBrowser(url) {
		var assetWindow = Liferay.Util.openWindow({dialog: {width: 820, constrain: true}, id: 'asset_browser', title: 'Asset Browser', uri:url});
	}

	Liferay.provide(
		window,
		'<%= randomNamespace %>addAssetLink',
		function(entryId, entryType, entryTitle) {
			var A = AUI();

			var searchContainerName = '<%= portletResponse.getNamespace() %>assetLinkSearchContainer';

			searchContainer = Liferay.SearchContainer.get(searchContainerName);

			var rowColumns = [];

			rowColumns.push(entryType);
			rowColumns.push(entryTitle);
			rowColumns.push('<a class="modify-link" data-rowId="' + entryId + '" href="javascript:;"><%= UnicodeFormatter.toString(removeLinkIcon) %></a>');

			searchContainer.addRow(rowColumns, entryId);

			searchContainer.updateDataStore();
		},
		['liferay-search-container']
	);

</aui:script>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<%= portletResponse.getNamespace() %>assetLinkSearchContainer');

	searchContainer.get('contentBox').delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));
		},
		'.modify-link'
	);
</aui:script>