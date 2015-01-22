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

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
PortletURL configurationRenderURL = (PortletURL)request.getAttribute("configuration.jsp-configurationRenderURL");
String selectScope = (String)request.getAttribute("configuration.jsp-selectScope");
String selectStyle = (String)request.getAttribute("configuration.jsp-selectStyle");
String eventName = "_" + HtmlUtil.escapeJS(assetPublisherDisplayContext.getPortletResource()) + "_selectAsset";
%>

<liferay-ui:tabs
	formName="fm"
	names="asset-selection,display-settings,subscriptions"
	param="tabs2"
	refresh="<%= false %>"
>
	<liferay-ui:section>
		<liferay-ui:error-marker key="errorSection" value="asset-selection" />

		<%= selectStyle %>

		<aui:fieldset label="scope">
			<%= selectScope %>
		</aui:fieldset>

		<aui:fieldset label="model.resource.com.liferay.portlet.asset">

			<%
			List<AssetEntry> assetEntries = AssetPublisherUtil.getAssetEntries(renderRequest, portletPreferences, permissionChecker, assetPublisherDisplayContext.getGroupIds(), true, assetPublisherDisplayContext.isEnablePermissions());
			%>

			<liferay-ui:search-container
				emptyResultsMessage="no-assets-selected"
				iteratorURL="<%= configurationRenderURL %>"
				total="<%= assetEntries.size() %>"
			>
				<liferay-ui:search-container-results
					results="<%= assetEntries.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.portlet.asset.model.AssetEntry"
					escapedModel="<%= true %>"
					keyProperty="entryId"
					modelVar="assetEntry"
				>

					<%
					AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(assetEntry.getClassName());

					AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(assetEntry.getClassPK());
					%>

					<liferay-ui:search-container-column-text name="title">
						<i class="<%= assetRenderer.getIconCssClass() %>"></i>

						<%= HtmlUtil.escape(assetRenderer.getTitle(locale)) %>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="type"
						value="<%= assetRendererFactory.getTypeName(locale) %>"
					/>

					<liferay-ui:search-container-column-date
						name="modified-date"
						value="<%= assetEntry.getModifiedDate() %>"
					/>

					<liferay-ui:search-container-column-jsp
						align="right"
						cssClass="entry-action"
						path="/html/portlet/asset_publisher/asset_selection_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator paginate="<%= total > SearchContainer.DEFAULT_DELTA %>" />
			</liferay-ui:search-container>

			<c:if test='<%= SessionMessages.contains(renderRequest, "deletedMissingAssetEntries") %>'>
				<div class="alert alert-info">
					<liferay-ui:message key="the-selected-assets-have-been-removed-from-the-list-because-they-do-not-belong-in-the-scope-of-this-portlet" />
				</div>
			</c:if>

			<%
			long[] groupIds = assetPublisherDisplayContext.getGroupIds();

			for (long groupId : groupIds) {
			%>

				<div class="select-asset-selector">
					<div class="edit-controls lfr-meta-actions">
						<liferay-ui:icon-menu
							cssClass="select-existing-selector"
							direction="right" icon="../aui/plus"
							message='<%= LanguageUtil.format(request, (groupIds.length == 1) ? "select" : "select-in-x", HtmlUtil.escape((GroupLocalServiceUtil.getGroup(groupId)).getDescriptiveName(locale)), false) %>'
							showWhenSingleIcon="<%= true %>"
						>

							<%
							PortletURL assetBrowserURL = PortletURLFactoryUtil.create(request, PortletKeys.ASSET_BROWSER, PortalUtil.getControlPanelPlid(company.getCompanyId()), PortletRequest.RENDER_PHASE);

							assetBrowserURL.setParameter("groupId", String.valueOf(groupId));
							assetBrowserURL.setParameter("selectedGroupIds", String.valueOf(groupId));
							assetBrowserURL.setParameter("eventName", eventName);
							assetBrowserURL.setPortletMode(PortletMode.VIEW);
							assetBrowserURL.setWindowState(LiferayWindowState.POP_UP);

							List <AssetRendererFactory> assetRendererFactories = ListUtil.sort(AssetRendererFactoryRegistryUtil.getAssetRendererFactories(company.getCompanyId()), new AssetRendererFactoryTypeNameComparator(locale));

							for (AssetRendererFactory curRendererFactory : assetRendererFactories) {
								if (!curRendererFactory.isSelectable()) {
									continue;
								}

								assetBrowserURL.setParameter("typeSelection", curRendererFactory.getClassName());

								Map<String, Object> data = new HashMap<String, Object>();

								data.put("groupid", String.valueOf(groupId));

								if (!curRendererFactory.isSupportsClassTypes()) {
									data.put("href", assetBrowserURL.toString());

									String type = curRendererFactory.getTypeName(locale);

									data.put("title", LanguageUtil.format(request, "select-x", type, false));
									data.put("type", type);
							%>

									<liferay-ui:icon
										cssClass="asset-selector"
										data="<%= data %>"
										iconCssClass="<%= curRendererFactory.getIconCssClass() %>"
										id="<%= groupId + FriendlyURLNormalizerUtil.normalize(type) %>"
										message="<%= type %>"
										url="javascript:;"
									/>

							<%
								}
								else {
									ClassTypeReader classTypeReader = curRendererFactory.getClassTypeReader();

									List<ClassType> assetAvailableClassTypes = classTypeReader.getAvailableClassTypes(PortalUtil.getSharedContentSiteGroupIds(company.getCompanyId(), scopeGroupId, user.getUserId()), locale);

									for (ClassType assetAvailableClassType : assetAvailableClassTypes) {
										assetBrowserURL.setParameter("subtypeSelectionId", String.valueOf(assetAvailableClassType.getClassTypeId()));

										data.put("href", assetBrowserURL.toString());

										String type = assetAvailableClassType.getName();

										data.put("title", LanguageUtil.format(request, "select-x", type, false));
										data.put("type", type);
							%>

										<liferay-ui:icon
											cssClass="asset-selector"
											data="<%= data %>"
											iconCssClass="<%= curRendererFactory.getIconCssClass() %>"
											id="<%= groupId + FriendlyURLNormalizerUtil.normalize(type) %>"
											message="<%= type %>"
											url="javascript:;"
										/>

							<%
									}
								}
							}
							%>

						</liferay-ui:icon-menu>
					</div>
				</div>

			<%
			}
			%>

		</aui:fieldset>
	</liferay-ui:section>
	<liferay-ui:section>
		<liferay-ui:error-marker key="errorSection" value="display-settings" />

		<%@ include file="/html/portlet/asset_publisher/display_settings.jspf" %>
	</liferay-ui:section>
	<liferay-ui:section>
		<liferay-ui:error-marker key="errorSection" value="subscriptions" />

		<%@ include file="/html/portlet/asset_publisher/email_subscription_settings.jspf" %>
	</liferay-ui:section>
</liferay-ui:tabs>

<aui:button-row>
	<aui:button onClick='<%= renderResponse.getNamespace() + "saveSelectBoxes();" %>' type="submit" />
</aui:button-row>

<aui:script sandbox="<%= true %>">
	function selectAsset(assetEntryId, assetClassName, assetType, assetEntryTitle, groupName) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'add-selection';
		document.<portlet:namespace />fm.<portlet:namespace />assetEntryId.value = assetEntryId;
		document.<portlet:namespace />fm.<portlet:namespace />assetEntryType.value = assetClassName;

		submitForm(document.<portlet:namespace />fm);
	}

	$('body').on(
		'click',
		'.asset-selector a',
		function(event) {
			event.preventDefault();

			var currentTarget = $(event.currentTarget);

			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						destroyOnHide: true,
						modal: true
					},
					eventName: '<%= eventName %>',
					id: '<%= eventName %>' + currentTarget.attr('id'),
					title: currentTarget.data('title'),
					uri: currentTarget.data('href')
				},
				function(event) {
					selectAsset(event.assetentryid, event.assetclassname, event.assettype, event.assettitle, event.groupdescriptivename);
				}
			);
		}
	);
</aui:script>