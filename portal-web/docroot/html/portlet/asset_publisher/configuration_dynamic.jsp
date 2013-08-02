<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
List<AssetRendererFactory> classTypesAssetRendererFactories = (List<AssetRendererFactory>) request.getAttribute("configuration.jsp-classTypesAssetRendererFactories");
PortletURL configurationRenderURL = (PortletURL) request.getAttribute("configuration.jsp-configurationRenderURL");
String redirect = (String) request.getAttribute("configuration.jsp-redirect");
String rootPortletId = (String) request.getAttribute("configuration.jsp-rootPortletId");
String selectScope = (String) request.getAttribute("configuration.jsp-selectScope");
%>

<liferay-ui:panel-container extended="<%= true %>" id="assetPublisherDynamicSelectionStylePanelContainer" persistState="<%= true %>">
	<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="assetPublisherSourcePanel" persistState="<%= true %>" title="source">
		<aui:fieldset cssClass='<%= rootPortletId.equals(PortletKeys.RELATED_ASSETS) ? "aui-helper-hidden" : "" %>' label="scope">
			<%= selectScope %>
		</aui:fieldset>

		<aui:fieldset label="asset-entry-type">

			<%
			Set<Long> availableClassNameIdsSet = SetUtil.fromArray(availableClassNameIds);

			// Left list

			List<KeyValuePair> typesLeftList = new ArrayList<KeyValuePair>();

			for (long classNameId : classNameIds) {
				String className = PortalUtil.getClassName(classNameId);

				typesLeftList.add(new KeyValuePair(String.valueOf(classNameId), ResourceActionsUtil.getModelResource(locale, className)));
			}

			// Right list

			List<KeyValuePair> typesRightList = new ArrayList<KeyValuePair>();

			Arrays.sort(classNameIds);
			%>

			<aui:select label="" name="preferences--anyAssetType--">
				<aui:option label="any" selected="<%= anyAssetType %>" value="<%= true %>" />
				<aui:option label='<%= LanguageUtil.get(pageContext, "select-more-than-one") + "..." %>' selected="<%= !anyAssetType && (classNameIds.length > 1) %>" value="<%= false %>" />

				<optgroup label="<liferay-ui:message key="asset-type" />">

					<%
					for (long classNameId : availableClassNameIdsSet) {
						ClassName className = ClassNameLocalServiceUtil.getClassName(classNameId);

						if (Arrays.binarySearch(classNameIds, classNameId) < 0) {
							typesRightList.add(new KeyValuePair(String.valueOf(classNameId), ResourceActionsUtil.getModelResource(locale, className.getValue())));
						}
					%>

					<aui:option label="<%= ResourceActionsUtil.getModelResource(locale, className.getValue()) %>" selected="<%= (classNameIds.length == 1) && (classNameId == classNameIds[0]) %>" value="<%= classNameId %>" />

					<%
					}
					%>

				</optgroup>
			</aui:select>

			<aui:input name="preferences--classNameIds--" type="hidden" />

			<%
			typesRightList = ListUtil.sort(typesRightList, new KeyValuePairComparator(false, true));
			%>

			<div class="<%= anyAssetType ? "aui-helper-hidden" : "" %>" id="<portlet:namespace />classNamesBoxes">
				<liferay-ui:input-move-boxes
					leftBoxName="currentClassNameIds"
					leftList="<%= typesLeftList %>"
					leftReorder="true"
					leftTitle="selected"
					rightBoxName="availableClassNameIds"
					rightList="<%= typesRightList %>"
					rightTitle="available"
				/>
			</div>

			<%
			for (AssetRendererFactory assetRendererFactory : AssetRendererFactoryRegistryUtil.getAssetRendererFactories()) {
				if (assetRendererFactory.getClassTypes(new long[] {themeDisplay.getCompanyGroupId(), scopeGroupId}, themeDisplay.getLocale()) == null) {
					continue;
				}

				classTypesAssetRendererFactories.add(assetRendererFactory);

				Map<Long, String> assetAvailableClassTypes = assetRendererFactory.getClassTypes(new long[] {themeDisplay.getCompanyGroupId(), scopeGroupId}, themeDisplay.getLocale());

				String className = AssetPublisherUtil.getClassName(assetRendererFactory);

				Set<Long> assetAvailableClassTypeIdsSet = assetAvailableClassTypes.keySet();

				Long[] assetAvailableClassTypeIds = assetAvailableClassTypeIdsSet.toArray(new Long[assetAvailableClassTypeIdsSet.size()]);

				Long[] assetSelectedClassTypeIds = AssetPublisherUtil.getClassTypeIds(preferences, className, assetAvailableClassTypeIds);

				// Left list

				List<KeyValuePair> subTypesLeftList = new ArrayList<KeyValuePair>();

				for (long subTypeId : assetSelectedClassTypeIds) {
					subTypesLeftList.add(new KeyValuePair(String.valueOf(subTypeId), HtmlUtil.escape(assetAvailableClassTypes.get(subTypeId))));
				}

				Arrays.sort(assetSelectedClassTypeIds);

				// Right list

				List<KeyValuePair> subTypesRightList = new ArrayList<KeyValuePair>();

				boolean anyAssetSubType = GetterUtil.getBoolean(preferences.getValue("anyClassType" + className, Boolean.TRUE.toString()));
			%>

			<div class='asset-subtype <%= (assetSelectedClassTypeIds.length < 1) ? "" : "aui-helper-hidden" %>' id="<portlet:namespace /><%= className %>Options">
				<aui:select label='<%= LanguageUtil.format(pageContext, "x-subtype", ResourceActionsUtil.getModelResource(locale, assetRendererFactory.getClassName())) %>' name='<%= "preferences--anyClassType" + className + "--" %>'>
					<aui:option label="any" selected="<%= anyAssetSubType %>" value="<%= true %>" />
					<aui:option label='<%= LanguageUtil.get(pageContext, "select-more-than-one") + "..." %>' selected="<%= !anyAssetSubType && (assetSelectedClassTypeIds.length > 1) %>" value="<%= false %>" />

					<optgroup label="<liferay-ui:message key="subtype" />">

						<%
						for (Long classTypeId : assetAvailableClassTypes.keySet()) {
							if (Arrays.binarySearch(assetSelectedClassTypeIds, classTypeId) < 0) {
								subTypesRightList.add(new KeyValuePair(String.valueOf(classTypeId), HtmlUtil.escape(assetAvailableClassTypes.get(classTypeId))));
							}
						%>

							<aui:option label="<%= HtmlUtil.escapeAttribute(assetAvailableClassTypes.get(classTypeId)) %>" selected="<%= !anyAssetSubType && (assetSelectedClassTypeIds.length == 1) && (classTypeId.equals(assetSelectedClassTypeIds[0])) %>" value="<%= classTypeId %>" />

						<%
						}
						%>

					</optgroup>
				</aui:select>

				<aui:input name='<%= "preferences--classTypeIds" + className + "--" %>' type="hidden" />

				<%
				typesRightList = ListUtil.sort(typesRightList, new KeyValuePairComparator(false, true));
				%>

				<div class="<%= assetSelectedClassTypeIds.length > 1 ? "" : "aui-helper-hidden" %>" id="<portlet:namespace /><%= className %>Boxes">
					<liferay-ui:input-move-boxes
						leftBoxName='<%= className + "currentClassTypeIds" %>'
						leftList="<%= subTypesLeftList %>"
						leftReorder="true"
						leftTitle="selected"
						rightBoxName='<%= className + "availableClassTypeIds" %>'
						rightList="<%= subTypesRightList %>"
						rightTitle="available"
					/>
				</div>
			</div>

			<%
			}
			%>

		</aui:fieldset>
	</liferay-ui:panel>

	<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="assetPublisherQueryRulesPanelContainer" persistState="<%= true %>" title="filter[action]">
		<liferay-ui:asset-tags-error />

		<div id="<portlet:namespace />queryRules">
			<aui:fieldset label="displayed-assets-must-match-these-rules">

				<%
				String queryLogicIndexesParam = ParamUtil.getString(request, "queryLogicIndexes");

				int[] queryLogicIndexes = null;

				if (Validator.isNotNull(queryLogicIndexesParam)) {
					queryLogicIndexes = StringUtil.split(queryLogicIndexesParam, 0);
				}
				else {
					queryLogicIndexes = new int[0];

					for (int i = 0; true; i++) {
						String queryValues = PrefsParamUtil.getString(preferences, request, "queryValues" + i);

						if (Validator.isNull(queryValues)) {
							break;
						}

						queryLogicIndexes = ArrayUtil.append(queryLogicIndexes, i);
					}

					if (queryLogicIndexes.length == 0) {
						queryLogicIndexes = ArrayUtil.append(queryLogicIndexes, -1);
					}
				}

				int index = 0;

				for (int queryLogicIndex : queryLogicIndexes) {
					String queryValues = StringUtil.merge(preferences.getValues("queryValues" + queryLogicIndex , new String[0]));
					String tagNames = ParamUtil.getString(request, "queryTagNames" + queryLogicIndex, queryValues);
					String categoryIds = ParamUtil.getString(request, "queryCategoryIds" + queryLogicIndex, queryValues);

					if (Validator.isNotNull(tagNames) || Validator.isNotNull(categoryIds) || (queryLogicIndexes.length == 1)) {
						request.setAttribute("configuration.jsp-categorizableGroupIds", _getCategorizableGroupIds(groupIds));
						request.setAttribute("configuration.jsp-index", String.valueOf(index));
						request.setAttribute("configuration.jsp-queryLogicIndex", String.valueOf(queryLogicIndex));
				%>

						<div class="lfr-form-row">
							<div class="row-fields">
								<liferay-util:include page="/html/portlet/asset_publisher/edit_query_rule.jsp" />
							</div>
						</div>

				<%
					}

					index++;
				}
				%>

			</aui:fieldset>
		</div>

		<aui:input label='<%= LanguageUtil.format(pageContext, "show-only-assets-with-x-as-its-display-page", HtmlUtil.escape(layout.getName(locale)), false) %>' name="preferences--showOnlyLayoutAssets--" type="checkbox" value="<%= showOnlyLayoutAssets %>" />

		<aui:input label="include-tags-specified-in-the-url" name="preferences--mergeUrlTags--" type="checkbox" value="<%= mergeUrlTags %>" />

		<aui:input helpMessage="include-tags-set-by-other-applications-help" label="include-tags-set-by-other-applications" name="preferences--mergeLayoutTags--" type="checkbox" value="<%= mergeLayoutTags %>" />

		<aui:script use="liferay-auto-fields">
			var autoFields = new Liferay.AutoFields(
				{
					contentBox: '#<portlet:namespace />queryRules > fieldset',
					fieldIndexes: '<portlet:namespace />queryLogicIndexes',
					url: '<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/portlet_configuration/edit_query_rule" /></portlet:renderURL>'
				}
			).render();

			Liferay.Util.toggleSelectBox('<portlet:namespace />defaultScope','false','<portlet:namespace />scopesBoxes');
		</aui:script>
	</liferay-ui:panel>
	<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="assetPublisherCustomUserAttributesQueryRulesPanelContainer" persistState="<%= true %>" title="custom-user-attributes">
		<aui:input helpMessage="custom-user-attributes-help" label="displayed-assets-must-match-these-custom-user-profile-attributes" name="preferences--customUserAttributes--" value="<%= customUserAttributes %>" />
	</liferay-ui:panel>
	<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="assetPublisherOrderingAndGroupingPanel" persistState="<%= true %>" title="ordering-and-grouping">
		<aui:fieldset>
			<span class="aui-field-row">
				<aui:select inlineField="<%= true %>" inlineLabel="left" label="order-by" name="preferences--orderByColumn1--">
					<aui:option label="title" selected='<%= orderByColumn1.equals("title") %>' />
					<aui:option label="create-date" selected='<%= orderByColumn1.equals("createDate") %>' value="createDate" />
					<aui:option label="modified-date" selected='<%= orderByColumn1.equals("modifiedDate") %>' value="modifiedDate" />
					<aui:option label="publish-date" selected='<%= orderByColumn1.equals("publishDate") %>' value="publishDate" />
					<aui:option label="expiration-date" selected='<%= orderByColumn1.equals("expirationDate") %>' value="expirationDate" />
					<aui:option label="priority" selected='<%= orderByColumn1.equals("priority") %>' value="priority" />
					<aui:option label="view-count" selected='<%= orderByColumn1.equals("viewCount") %>' value="viewCount" />
					<aui:option label="ratings" selected='<%= orderByColumn1.equals("ratings") %>' value="ratings" />
				</aui:select>

				<aui:select inlineField="<%= true %>" label="" name="preferences--orderByType1--">
					<aui:option label="ascending" selected='<%= orderByType1.equals("ASC") %>' value="ASC" />
					<aui:option label="descending" selected='<%= orderByType1.equals("DESC") %>' value="DESC" />
				</aui:select>
			</span>

			<span class="aui-field-row">
				<aui:select inlineField="<%= true %>" inlineLabel="left" label="and-then-by" name="preferences--orderByColumn2--">
					<aui:option label="title" selected='<%= orderByColumn2.equals("title") %>' />
					<aui:option label="create-date" selected='<%= orderByColumn2.equals("createDate") %>' value="createDate" />
					<aui:option label="modified-date" selected='<%= orderByColumn2.equals("modifiedDate") %>' value="modifiedDate" />
					<aui:option label="publish-date" selected='<%= orderByColumn2.equals("publishDate") %>' value="publishDate" />
					<aui:option label="expiration-date" selected='<%= orderByColumn2.equals("expirationDate") %>' value="expirationDate" />
					<aui:option label="priority" selected='<%= orderByColumn2.equals("priority") %>' value="priority" />
					<aui:option label="view-count" selected='<%= orderByColumn2.equals("viewCount") %>' value="viewCount" />
					<aui:option label="ratings" selected='<%= orderByColumn2.equals("ratings") %>' value="ratings" />
				</aui:select>

				<aui:select inlineField="<%= true %>" label="" name="preferences--orderByType2--">
					<aui:option label="ascending" selected='<%= orderByType2.equals("ASC") %>' value="ASC" />
					<aui:option label="descending" selected='<%= orderByType2.equals("DESC") %>' value="DESC" />
				</aui:select>
			</span>

			<span class="aui-field-row">
				<aui:select inlineField="<%= true %>" inlineLabel="left" label="group-by" name="preferences--assetVocabularyId--">
					<aui:option value="" />
					<aui:option label="asset-types" selected="<%= assetVocabularyId == -1 %>" value="-1" />

					<%
					Group companyGroup = company.getGroup();

					if (scopeGroupId != companyGroup.getGroupId()) {
						List<AssetVocabulary> assetVocabularies = AssetVocabularyLocalServiceUtil.getGroupVocabularies(scopeGroupId, false);

						if (!assetVocabularies.isEmpty()) {
						%>

							<optgroup label="<liferay-ui:message key="vocabularies" />">

								<%
								for (AssetVocabulary assetVocabulary : assetVocabularies) {
									assetVocabulary = assetVocabulary.toEscapedModel();
								%>

									<aui:option label="<%= assetVocabulary.getTitle(locale) %>" selected="<%= assetVocabularyId == assetVocabulary.getVocabularyId() %>" value="<%= assetVocabulary.getVocabularyId() %>" />

								<%
								}
								%>

							</optgroup>

						<%
						}
					}
					%>

					<%
					List<AssetVocabulary> assetVocabularies = AssetVocabularyLocalServiceUtil.getGroupVocabularies(companyGroup.getGroupId(), false);

					if (!assetVocabularies.isEmpty()) {
					%>

						<optgroup label="<liferay-ui:message key="vocabularies" /> (<liferay-ui:message key="global" />)">

							<%
							for (AssetVocabulary assetVocabulary : assetVocabularies) {
								assetVocabulary = assetVocabulary.toEscapedModel();
							%>

								<aui:option label="<%= assetVocabulary.getTitle(locale) %>" selected="<%= assetVocabularyId == assetVocabulary.getVocabularyId() %>" value="<%= assetVocabulary.getVocabularyId() %>" />

							<%
							}
							%>

						</optgroup>

					<%
					}
					%>

				</aui:select>
			</span>
		</aui:fieldset>
	</liferay-ui:panel>
	<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="assetPublisherDisplaySettingsPanel" persistState="<%= true %>" title="display-settings">
		<%@ include file="/html/portlet/asset_publisher/display_settings.jspf" %>
	</liferay-ui:panel>

	<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="assetPublisherRssPanel" persistState="<%= true %>" title="rss">
		<aui:fieldset>
			<aui:input label="enable-rss-subscription" name="preferences--enableRss--" type="checkbox" value="<%= enableRSS %>" />

			<div id="<portlet:namespace />rssOptions">
				<aui:input label="rss-feed-name" name="preferences--rssName--" type="text" value="<%= rssName %>" />

				<aui:select label="maximum-items-to-display" name="preferences--rssDelta--">
					<aui:option label="1" selected="<%= rssDelta == 1 %>" />
					<aui:option label="2" selected="<%= rssDelta == 2 %>" />
					<aui:option label="3" selected="<%= rssDelta == 3 %>" />
					<aui:option label="4" selected="<%= rssDelta == 4 %>" />
					<aui:option label="5" selected="<%= rssDelta == 5 %>" />
					<aui:option label="10" selected="<%= rssDelta == 10 %>" />
					<aui:option label="15" selected="<%= rssDelta == 15 %>" />
					<aui:option label="20" selected="<%= rssDelta == 20 %>" />
					<aui:option label="25" selected="<%= rssDelta == 25 %>" />
					<aui:option label="30" selected="<%= rssDelta == 30 %>" />
					<aui:option label="40" selected="<%= rssDelta == 40 %>" />
					<aui:option label="50" selected="<%= rssDelta == 50 %>" />
					<aui:option label="60" selected="<%= rssDelta == 60 %>" />
					<aui:option label="70" selected="<%= rssDelta == 70 %>" />
					<aui:option label="80" selected="<%= rssDelta == 80 %>" />
					<aui:option label="90" selected="<%= rssDelta == 90 %>" />
					<aui:option label="100" selected="<%= rssDelta == 100 %>" />
				</aui:select>

				<aui:select label="display-style" name="preferences--rssDisplayStyle--">
					<aui:option label="<%= RSSUtil.DISPLAY_STYLE_ABSTRACT %>" selected="<%= rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_ABSTRACT) %>" />
					<aui:option label="<%= RSSUtil.DISPLAY_STYLE_TITLE %>" selected="<%= rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE) %>" />
				</aui:select>

				<aui:select label="format" name="preferences--rssFormat--">
					<aui:option label="RSS 1.0" selected='<%= rssFormat.equals("rss10") %>' value="rss10" />
					<aui:option label="RSS 2.0" selected='<%= rssFormat.equals("rss20") %>' value="rss20" />
					<aui:option label="Atom 1.0" selected='<%= rssFormat.equals("atom10") %>' value="atom10" />
				</aui:select>
			</div>
		</aui:fieldset>
	</liferay-ui:panel>
</liferay-ui:panel-container>

<aui:button-row>
	<aui:button onClick='<%= renderResponse.getNamespace() + "saveSelectBoxes();" %>' type="submit" />
</aui:button-row>

<aui:script use="aui-base">
	var assetSelector = A.one('#<portlet:namespace />anyAssetType');
	var assetMulitpleSelector = A.one('#<portlet:namespace />currentClassNameIds');

	<%
	for (AssetRendererFactory curRendererFactory : classTypesAssetRendererFactories) {
		String className = AssetPublisherUtil.getClassName(curRendererFactory);
	%>

		Liferay.Util.toggleSelectBox('<portlet:namespace />anyClassType<%= className %>','false','<portlet:namespace /><%= className %>Boxes');

		var <portlet:namespace /><%= className %>Options = A.one('#<portlet:namespace /><%= className %>Options');

		function <portlet:namespace />toggle<%= className %>() {
			var assetOptions = assetMulitpleSelector.all('option');

			if ((assetSelector.val() == '<%= curRendererFactory.getClassNameId() %>') ||
				((assetSelector.val() == 'false') && (assetOptions.size() == 1) && (assetOptions.item(0).val() == '<%= curRendererFactory.getClassNameId() %>'))) {

				<portlet:namespace /><%= className %>Options.show();
			}
			else {
				<portlet:namespace /><%= className %>Options.hide();
			}
		}

	<%
	}
	%>

	function <portlet:namespace />toggleSubclasses() {

		<%
		for (AssetRendererFactory curRendererFactory : classTypesAssetRendererFactories) {
			String className = AssetPublisherUtil.getClassName(curRendererFactory);
		%>

			<portlet:namespace />toggle<%= className %>();

		<%
		}
		%>

	}

	<portlet:namespace />toggleSubclasses();

	assetSelector.on(
		'change',
		function(event) {
			<portlet:namespace />toggleSubclasses();
		}
	);

	Liferay.after(
		'inputmoveboxes:moveItem',
		function(event) {
			if ((event.fromBox.get('id') == '<portlet:namespace />currentClassNameIds') || (event.toBox.get('id') == '<portlet:namespace />currentClassNameIds')) {
				<portlet:namespace />toggleSubclasses();
			}
		}
	);
</aui:script>

<%!
private long[] _getCategorizableGroupIds(long[] groupIds) throws Exception {
	Set<Long> categorizableGroupIds = new HashSet<Long>(groupIds.length);

	for (long groupId : groupIds) {
		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.isLayout()) {
			groupId = group.getParentGroupId();
		}

		categorizableGroupIds.add(groupId);
	}

	return ArrayUtil.toLongArray(categorizableGroupIds);
}
%>