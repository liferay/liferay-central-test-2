<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
%>

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2");

String redirect = ParamUtil.getString(request, "redirect");

String typeSelection = ParamUtil.getString(request, "typeSelection", StringPool.BLANK);

AssetRendererFactory rendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(typeSelection);

PortletURL configurationRenderURL = renderResponse.createRenderURL();

configurationRenderURL.setParameter("struts_action", "/portlet_configuration/edit_configuration");
configurationRenderURL.setParameter("redirect", redirect);
configurationRenderURL.setParameter("backURL", redirect);
configurationRenderURL.setParameter("portletResource", portletResource);

PortletURL configurationActionURL = renderResponse.createActionURL();

configurationActionURL.setParameter("struts_action", "/portlet_configuration/edit_configuration");
configurationActionURL.setParameter("redirect", redirect);
configurationActionURL.setParameter("backURL", redirect);
configurationActionURL.setParameter("portletResource", portletResource);
%>

<script type="text/javascript">
	function <portlet:namespace />chooseSelectionStyle() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'selection-style';

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />moveSelectionDown(assetEntryOrder) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'move-selection-down';
		document.<portlet:namespace />fm.<portlet:namespace />assetEntryOrder.value = assetEntryOrder;

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />moveSelectionUp(assetEntryOrder) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'move-selection-up';
		document.<portlet:namespace />fm.<portlet:namespace />assetEntryOrder.value = assetEntryOrder;

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />saveSelectBoxes() {
		if (document.<portlet:namespace />fm.<portlet:namespace />scopeIds) {
			document.<portlet:namespace />fm.<portlet:namespace />scopeIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentScopeIds);
		}

		if (document.<portlet:namespace />fm.<portlet:namespace />classNameIds) {
			document.<portlet:namespace />fm.<portlet:namespace />classNameIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentClassNameIds);
		}

		document.<portlet:namespace />fm.<portlet:namespace />metadataFields.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentMetadataFields);

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />selectAsset(assetEntryId, assetParentId, assetTitle, assetEntryOrder) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'add-selection';
		document.<portlet:namespace />fm.<portlet:namespace />assetEntryId.value = assetEntryId;
		document.<portlet:namespace />fm.<portlet:namespace />assetParentId.value = assetParentId;
		document.<portlet:namespace />fm.<portlet:namespace />assetTitle.value = assetTitle;
		document.<portlet:namespace />fm.<portlet:namespace />assetEntryOrder.value = assetEntryOrder;

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />selectionForType(type) {
		document.<portlet:namespace />fm.<portlet:namespace />typeSelection.value = type;
		document.<portlet:namespace />fm.<portlet:namespace />assetEntryOrder.value = -1;

		submitForm(document.<portlet:namespace />fm, '<%= configurationRenderURL.toString() %>');
	}
</script>

<style type="text/css">
	.aui-form .queryRules fieldset {
		padding: 0;
	}

	.aui-form .queryRules fieldset legend {
		font-size: 1em;
		font-weight: normal;
		padding: 0;
	}

	.aui-form .lfr-form-row {
		background-color: #F3F3F3;
	}

	.aui-form .lfr-form-row:hover {
		background-color: #DFFCCB;
	}

	.aui-form .lfr-form-row .aui-ctrl-holder {
		line-height: 2;
	}

	.aui-form .lfr-form-row .aui-ctrl-holder.tags-selector, .aui-form .lfr-form-row .aui-ctrl-holder.categories-selector{
		clear: both;
		line-height: 1.5;
	}

	.lfr-panel .lfr-panel-titlebar {
		margin-bottom: 0;
	}

	.lfr-panel-content {
		background-color: #F8F8F8;
		padding: 10px;
	}
</style>

<aui:form action="<%= configurationActionURL.toString() %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="typeSelection" type="hidden" />
	<aui:input name="assetEntryId" type="hidden" />
	<aui:input name="assetParentId" type="hidden" />
	<aui:input name="assetTitle" type="hidden" />
	<aui:input name="assetEntryOrder" type="hidden" value="-1" />

	<c:choose>
		<c:when test="<%= typeSelection.equals(StringPool.BLANK) %>">
			<aui:select label="asset-selection" name="selectionStyle" onChange='<%= renderResponse.getNamespace() + "chooseSelectionStyle();" %>'>
				<aui:option label="dynamic" selected='<%= selectionStyle.equals("dynamic") %>'/>
				<aui:option label="manual" selected='<%= selectionStyle.equals("manual") %>'/>
			</aui:select>

			<c:choose>
				<c:when test='<%= selectionStyle.equals("manual") %>'>
					<liferay-ui:panel-container extended="<%= true %>" id='assetPublisherConfiguration' persistState="<%= true %>">
						<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id='assetPublisherSelection' persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "selection") %>' >
							<aui:fieldset>

								<%
								String portletId = portletResource;
								%>

								<%@ include file="/html/portlet/asset_publisher/add_asset.jspf" %>

								<aui:select name="assetEntryType" onChange='<%= renderResponse.getNamespace() + "selectionForType(this.options[this.selectedIndex].value);" %>'>
									<aui:option label="select-existing" value="" />

									<%
									for (AssetRendererFactory curRendererFactory : AssetRendererFactoryRegistryUtil.getAssetRendererFactories()) {
										if (curRendererFactory.isSelectable()) {
										%>

											<aui:option label='<%= "model.resource." + curRendererFactory.getClassName() %>' value="<%= curRendererFactory.getClassName() %>" />

										<%
										}
									}
									%>

								</aui:select>

								<%
								List<Long> deletedAssets = new ArrayList<Long>();

								List<String> headerNames = new ArrayList<String>();

								headerNames.add("type");
								headerNames.add("title");
								headerNames.add(StringPool.BLANK);

								SearchContainer searchContainer = new SearchContainer(renderRequest, new DisplayTerms(renderRequest), new DisplayTerms(renderRequest), SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, configurationRenderURL, headerNames, LanguageUtil.get(pageContext, "no-assets-selected"));

								int total = assetEntryXmls.length;

								searchContainer.setTotal(total);

								List results = ListUtil.fromArray(assetEntryXmls);

								int end = (assetEntryXmls.length < searchContainer.getEnd()) ? assetEntryXmls.length : searchContainer.getEnd();

								results = results.subList(searchContainer.getStart(), end);

								searchContainer.setResults(results);

								List resultRows = searchContainer.getResultRows();

								for (int i = 0; i < results.size(); i++) {
									String assetEntryXml = (String)results.get(i);

									Document doc = SAXReaderUtil.read(assetEntryXml);

									Element root = doc.getRootElement();

									int assetEntryOrder = searchContainer.getStart() + i;

									DocUtil.add(root, "asset-order", assetEntryOrder);

									if (assetEntryOrder == (total - 1)) {
										DocUtil.add(root, "last", true);
									}
									else {
										DocUtil.add(root, "last", false);
									}

									String assetEntryClassName = root.element("asset-entry-type").getText();
									long assetEntryId = GetterUtil.getLong(root.element("asset-entry-id").getText());

									AssetEntry assetEntry = null;

									try {
										assetEntry = AssetEntryLocalServiceUtil.getEntry(assetEntryId);

										assetEntry = assetEntry.toEscapedModel();
									}
									catch (NoSuchEntryException nsee) {
										deletedAssets.add(assetEntryId);

										continue;
									}

									ResultRow row = new ResultRow(doc, null, assetEntryOrder);

									PortletURL rowURL = renderResponse.createRenderURL();

									rowURL.setParameter("struts_action", "/portlet_configuration/edit_configuration");
									rowURL.setParameter("redirect", redirect);
									rowURL.setParameter("backURL", redirect);
									rowURL.setParameter("portletResource", portletResource);
									rowURL.setParameter("typeSelection", assetEntryClassName);
									rowURL.setParameter("assetEntryId", String.valueOf(assetEntryId));
									rowURL.setParameter("assetEntryOrder", String.valueOf(assetEntryOrder));

									// Type

									row.addText(LanguageUtil.get(pageContext, "model.resource." + assetEntryClassName), rowURL);

									// Title

									if (assetEntryClassName.equals(IGImage.class.getName())) {
										IGImage image = IGImageLocalServiceUtil.getImage(assetEntry.getClassPK());

										image = image.toEscapedModel();

										StringBuilder sb = new StringBuilder();

										sb.append("<img border=\"1\" src=\"");
										sb.append(themeDisplay.getPathImage());
										sb.append("/image_gallery?img_id=");
										sb.append(image.getSmallImageId());
										sb.append("&t=");
										sb.append(ImageServletTokenUtil.getToken(image.getSmallImageId()));
										sb.append("\" title=\"");
										sb.append(image.getDescription());
										sb.append("\" />");

										row.addText(sb.toString(), rowURL);
									}
									else {
										row.addText(assetEntry.getTitle(), rowURL);
									}

									// Action

									row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/asset_publisher/asset_selection_action.jsp");

									// Add result row

									resultRows.add(row);
								}

								AssetPublisherUtil.removeAndStoreSelection(deletedAssets, preferences);
								%>

								<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
							</aui:fieldset>
						</liferay-ui:panel>
						<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id='assetPublisherDisplaySettings' persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "display-settings") %>'>
							<%@ include file="/html/portlet/asset_publisher/display_settings.jspf" %>
						</liferay-ui:panel>
					</liferay-ui:panel-container>

					<aui:button-row>
						<aui:button onClick='<%= renderResponse.getNamespace() + "saveSelectBoxes();" %>' value="save" />

						<aui:button onClick="<%= redirect %>" value="cancel" />
					</aui:button-row>
				</c:when>
				<c:when test='<%= selectionStyle.equals("dynamic") %>'>
					<liferay-ui:panel-container extended="<%= true %>" id='assetPublisherConfiguration' persistState="<%= true %>">
						<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id='assetPublisherSources' persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "source") %>'>
							<aui:fieldset>
								<aui:legend label="scope" />

								<aui:select label="" name="defaultScope">
									<aui:option label="<%= themeDisplay.getScopeGroup().getDescriptiveName() %>" selected="<%= defaultScope %>" value="<%= true %>" />
									<aui:option label='<%= LanguageUtil.get(pageContext,"select") + "..." %>' selected="<%= !defaultScope %>" value="<%= false %>" />
								</aui:select>

								<aui:input name="scopeIds" type="hidden" />

								<%
								Set<Group> groups = new HashSet<Group>();

								groups.add(company.getGroup());
								groups.add(themeDisplay.getScopeGroup());

								for (Layout curLayout : LayoutLocalServiceUtil.getLayouts(layout.getGroupId(), layout.isPrivateLayout())) {
									if (curLayout.hasScopeGroup()) {
										groups.add(curLayout.getScopeGroup());
									}
								}

								// Left list

								List<KeyValuePair> scopesLeftList = new ArrayList<KeyValuePair>();

								for (long groupId : groupIds) {
									Group group = GroupLocalServiceUtil.getGroup(groupId);

									scopesLeftList.add(new KeyValuePair(_getKey(group), group.getDescriptiveName()));
								}

								// Right list

								List<KeyValuePair> scopesRightList = new ArrayList<KeyValuePair>();

								Arrays.sort(groupIds);

								for (Group group : groups) {
									if (Arrays.binarySearch(groupIds, group.getGroupId()) < 0) {
										scopesRightList.add(new KeyValuePair(_getKey(group), group.getDescriptiveName()));
									}
								}

								scopesRightList = ListUtil.sort(scopesRightList, new KeyValuePairComparator(false, true));
								%>

								<div id="<portlet:namespace />scopesBoxes" style="display: <%= defaultScope ? "none" : "block" %>;">
									<liferay-ui:input-move-boxes
										formName="fm"
										leftTitle="current"
										rightTitle="available"
										leftBoxName="currentScopeIds"
										rightBoxName="availableScopeIds"
										leftReorder="true"
										leftList="<%= scopesLeftList %>"
										rightList="<%= scopesRightList %>"
									/>
								</div>
							</aui:fieldset>

							<aui:fieldset>
								<aui:legend label="asset-entry-type" />

								<aui:select label="" name="anyAssetType">
									<aui:option label="any" selected="<%= anyAssetType %>" value="<%= true %>" />
									<aui:option label='<%= LanguageUtil.get(pageContext, "filter[action]") + "..." %>' selected="<%= !anyAssetType %>" value="<%= false %>" />
								</aui:select>

								<aui:input name="classNameIds" type="hidden" />

								<%
								Set<Long> availableClassNameIdsSet = SetUtil.fromArray(availableClassNameIds);

								// Left list

								List<KeyValuePair> typesLeftList = new ArrayList<KeyValuePair>();

								for (long classNameId : classNameIds) {
									ClassName className = ClassNameServiceUtil.getClassName(classNameId);

									typesLeftList.add(new KeyValuePair(String.valueOf(classNameId), LanguageUtil.get(pageContext, "model.resource." + className.getValue())));
								}

								// Right list

								List<KeyValuePair> typesRightList = new ArrayList<KeyValuePair>();

								Arrays.sort(classNameIds);

								for (long classNameId : availableClassNameIdsSet) {
									if (Arrays.binarySearch(classNameIds, classNameId) < 0) {
										ClassName className = ClassNameServiceUtil.getClassName(classNameId);

										typesRightList.add(new KeyValuePair(String.valueOf(classNameId), LanguageUtil.get(pageContext, "model.resource." + className.getValue())));
									}
								}

								typesRightList = ListUtil.sort(typesRightList, new KeyValuePairComparator(false, true));
								%>

								<div id="<portlet:namespace />classNamesBoxes" style="display: <%= anyAssetType ? "none" : "block" %>;">
									<liferay-ui:input-move-boxes
										formName="fm"
										leftTitle="current"
										rightTitle="available"
										leftBoxName="currentClassNameIds"
										rightBoxName="availableClassNameIds"
										leftReorder="true"
										leftList="<%= typesLeftList %>"
										rightList="<%= typesRightList %>"
									/>
								</div>
							</aui:fieldset>
						</liferay-ui:panel>

						<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id='assetPublisherQueryLogic' persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "filter[action]") %>'>
							<liferay-ui:asset-tags-error />

							<div id="<portlet:namespace />queryRules">
								<aui:fieldset>
									<aui:legend label="displayed-assets-must-match-these-rules" />

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
											queryLogicIndexes = ArrayUtil.append(queryLogicIndexes, 0);
										}
									}

									int index = 0;

									for (int queryLogicIndex : queryLogicIndexes) {
										boolean queryContains = PrefsParamUtil.getBoolean(preferences, request, "queryContains" + queryLogicIndex, true);
										boolean queryAndOperator = PrefsParamUtil.getBoolean(preferences, request, "queryAndOperator" + queryLogicIndex);
										String queryName = PrefsParamUtil.getString(preferences, request, "queryName" + queryLogicIndex, "assetTags");
										String queryValues = StringUtil.merge(preferences.getValues("queryValues" + queryLogicIndex , new String[0]));

										if (Validator.equals(queryName, "assetTags")) {
											queryValues = ParamUtil.getString(request, "queryTagNames" + queryLogicIndex, queryValues);
										}
										else {
											queryValues = ParamUtil.getString(request, "queryCategoryIds" + queryLogicIndex, queryValues);
										}

										if (Validator.isNotNull(queryValues) || queryLogicIndexes.length == 1) {
									%>

											<div class="lfr-form-row">
												<div class="row-fields">
													<aui:select inlineField="<%= true %>" label="" name='<%= "queryContains" + index %>'>
														<aui:option label="contains" selected="<%= queryContains %>" value="true" />
														<aui:option label="does-not-contain" selected="<%= !queryContains %>" value="false" />
													</aui:select>

													<aui:select inlineField="<%= true %>" label="" name='<%= "queryAndOperator" + index %>'>
														<aui:option label="all" selected="<%= queryAndOperator %>" value="true" />
														<aui:option label="any" selected="<%= !queryAndOperator %>" value="false" />
													</aui:select>

													<aui:select cssClass="asset-query-name" inlineLabel="left" label="of-the-following" name='<%= "queryName" + index %>'>
														<aui:option label="tags" selected='<%= Validator.equals(queryName, "assetTags") %>' value="assetTags" />
														<aui:option label="categories" selected='<%= Validator.equals(queryName, "assetCategories") %>' value="assetCategories" />
													</aui:select>

													<div class="aui-ctrl-holder tags-selector" style="display:<%= Validator.equals(queryName, "assetTags") ? "block;" : "none;" %>">
														<liferay-ui:asset-tags-selector
															hiddenInput='<%= "queryTagNames" + index %>'
															curTags='<%= Validator.equals(queryName, "assetTags") ? queryValues : null %>'
															focus="<%= false %>"
														/>
													</div>

													<div class="aui-ctrl-holder categories-selector" style="display:<%= Validator.equals(queryName, "assetCategories") ? "block;" : "none;" %>">
														<liferay-ui:asset-categories-selector
															hiddenInput='<%= "queryCategoryIds" + index %>'
															curCategoryIds='<%= Validator.equals(queryName, "assetCategories") ? queryValues : null %>'
															focus="<%= false %>"
														/>
													</div>

												</div>
											</div>

									<%
											}

											index++;
									}
									%>

								</aui:fieldset>
							</div>

							<script type="text/javascript">
								AUI().ready(
									'liferay-auto-fields',
									'liferay-categories-selector',
									function (A) {
										Liferay.Util.toggleSelectBox('<portlet:namespace />defaultScope','false','<portlet:namespace />scopesBoxes');
										Liferay.Util.toggleSelectBox('<portlet:namespace />anyAssetType','false','<portlet:namespace />classNamesBoxes');

										var autoFields = new Liferay.AutoFields(
											{
												contentBox: '#<portlet:namespace />queryRules > fieldset',
												fieldIndexes: '<portlet:namespace />queryLogicIndexes'
											}
										).render();

										var initQueryNameFields = function(selectFields) {
											selectFields = selectFields || A.all('.asset-query-name');

											if (selectFields) {
												selectFields.each(
													function(select) {
														var row = select.get('parentNode.parentNode');

														select.on(
															'change',
															function(event) {
																var tagsSelector = row.all('.tags-selector');
																var categoriesSelector = row.all('.categories-selector');

																if (select.val() == 'assetTags') {
																	if (tagsSelector) {
																		tagsSelector.show();
																	}

																	if (categoriesSelector) {
																		categoriesSelector.hide();
																	}
																}
																else {
																	if (tagsSelector) {
																		tagsSelector.hide();
																	}

																	if (categoriesSelector) {
																		categoriesSelector.show();
																	}
																}
															}
														);
													}
												);
											}
										};

										autoFields.on(
											'autorow:clone',
											function(event) {
												var row = event.row.get('contentBox');
												var guid = event.guid;

												initQueryNameFields(row.all('.asset-query-name'));

												var firstCategoriesInput = row.one('.categories-selector > input');

												var categoriesRandomNamespace = (firstCategoriesInput.attr('name') || firstCategoriesInput.attr('id') || '').substring(0,5);

												new Liferay.AssetCategoriesSelector(
													{
														instanceVar: categoriesRandomNamespace,
														seed: guid,
														hiddenInput: '<portlet:namespace/>queryCategoryIds',
														summarySpan: categoriesRandomNamespace + 'assetCategoriesSummary'
													}
												);

												var firstTagsInput = row.one('.tags-selector > input');

												if (firstTagsInput) {
													var tagsRandomNamespace = (firstTagsInput.attr('name') || firstTagsInput.attr('id') || '').substring(0, 5);

													new Liferay.AssetTagsSelector(
														{
															instanceVar: tagsRandomNamespace,
															seed: guid,
															hiddenInput: '<portlet:namespace/>queryTagNames',
															summarySpan: tagsRandomNamespace + 'assetTagsSummary',
															textInput: tagsRandomNamespace + 'assetTagNames',
															focus: false
														}
													);
												}
											}
										);

										initQueryNameFields();
									}
								);
							</script>

							<aui:input inlineLabel="left" label="include-tags-specified-in-the-url" name="mergeUrlTags" type="checkbox" value="<%= mergeUrlTags %>" />
					</liferay-ui:panel>
					<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id='assetPublisherGroupBy' persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "ordering-and-grouping") %>'>
						<aui:fieldset>
							<aui:select inlineField="<%= true %>" inlineLabel="left" label="order-by" name="orderByColumn1">
								<aui:option label="title" selected='<%= orderByColumn2.equals("title") %>' />
								<aui:option label="create-date" selected='<%= orderByColumn2.equals("createDate") %>' value="createDate" />
								<aui:option label="modified-date" selected='<%= orderByColumn2.equals("modifiedDate") %>' value="modifiedDate" />
								<aui:option label="publish-date" selected='<%= orderByColumn2.equals("publishDate") %>' value="publishDate" />
								<aui:option label="expiration-date" selected='<%= orderByColumn2.equals("expirationDate") %>' value="expirationDate" />
								<aui:option label="priority" selected='<%= orderByColumn2.equals("priority") %>'><liferay-ui:message key="priority" /></aui:option>
								<aui:option label="view-count" selected='<%= orderByColumn2.equals("viewCount") %>' value="viewCount" />
							</aui:select>

							<aui:select label="" name="orderByType1">
								<aui:option label="ascending" selected='<%= orderByType1.equals("ASC") %>' value="ASC" />
								<aui:option label="descending" selected='<%= orderByType1.equals("DESC") %>' value="DESC" />
							</aui:select>

							<aui:select inlineField="<%= true %>" inlineLabel="left" label="order-by" name="orderByColumn2">
								<aui:option label="title" selected='<%= orderByColumn2.equals("title") %>' />
								<aui:option label="create-date" selected='<%= orderByColumn2.equals("createDate") %>' value="createDate" />
								<aui:option label="modified-date" selected='<%= orderByColumn2.equals("modifiedDate") %>' value="modifiedDate" />
								<aui:option label="publish-date" selected='<%= orderByColumn2.equals("publishDate") %>' value="publishDate" />
								<aui:option label="expiration-date" selected='<%= orderByColumn2.equals("expirationDate") %>' value="expirationDate" />
								<aui:option label="priority" selected='<%= orderByColumn2.equals("priority") %>'><liferay-ui:message key="priority" /></aui:option>
								<aui:option label="view-count" selected='<%= orderByColumn2.equals("viewCount") %>' value="viewCount" />
							</aui:select>

							<aui:select label="" name="orderByType2">
								<aui:option label="ascending" selected='<%= orderByType2.equals("ASC") %>' value="ASC" />
								<aui:option label="descending" selected='<%= orderByType2.equals("DESC") %>' value="DESC" />
							</aui:select>

							<aui:select label="group-by" name="assetVocabularyId">
								<aui:option value="" />
								<aui:option label="asset-types" selected="<%= assetVocabularyId == -1 %>" value="-1" />

								<%
								List<AssetVocabulary> assetVocabularies = AssetVocabularyLocalServiceUtil.getGroupVocabularies(scopeGroupId);

								if (!assetVocabularies.isEmpty()) {
								%>

									<optgroup label="<liferay-ui:message key="vocabularies" />">

										<%
										for (AssetVocabulary assetVocabulary : assetVocabularies) {
										%>

											<aui:option label="<%= assetVocabulary.getName() %>" selected="<%= assetVocabularyId == assetVocabulary.getVocabularyId() %>" value="<%= assetVocabulary.getVocabularyId() %>" />

										<%
										}
										%>

									</optgroup>

								<%
								}
								%>

							</aui:select>
						</aui:fieldset>
					</liferay-ui:panel>
					<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id='assetPublisherDisplaySettings' persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "display-settings") %>'>
						<%@ include file="/html/portlet/asset_publisher/display_settings.jspf" %>
					</liferay-ui:panel>
				</liferay-ui:panel-container>

					<aui:button-row>
						<aui:button onClick='<%= renderResponse.getNamespace() + "saveSelectBoxes();" %>' value="save" />

						<aui:button onClick="<%= redirect %>" value="cancel" />
					</aui:button-row>
				</c:when>
			</c:choose>
		</c:when>
		<c:when test="<%= typeSelection.equals(BlogsEntry.class.getName()) %>">
			<aui:input name="assetEntryType" type="hidden" value="<%= BlogsEntry.class.getName() %>" />

			<liferay-ui:message key="select" />: <liferay-ui:message key='<%= "model.resource." + BlogsEntry.class.getName() %>' />

			<br /><br />

			<%@ include file="/html/portlet/asset_publisher/select_blogs_entry.jspf" %>
		</c:when>
		<c:when test="<%= typeSelection.equals(BookmarksEntry.class.getName()) %>">
			<aui:input name="assetEntryType" type="hidden" value="<%= BookmarksEntry.class.getName() %>" />

			<liferay-ui:message key="select" />: <liferay-ui:message key='<%= "model.resource." + BookmarksEntry.class.getName() %>' />

			<br /><br />

			<%@ include file="/html/portlet/asset_publisher/select_bookmarks_entry.jspf" %>
		</c:when>
		<c:when test="<%= typeSelection.equals(DLFileEntry.class.getName()) %>">
			<aui:input name="assetEntryType" type="hidden" value="<%= DLFileEntry.class.getName() %>" />

			<liferay-ui:message key="select" />: <liferay-ui:message key='<%= "model.resource." + DLFileEntry.class.getName() %>' />

			<br /><br />

			<%@ include file="/html/portlet/asset_publisher/select_document_library_file_entry.jspf" %>
		</c:when>
		<c:when test="<%= typeSelection.equals(IGImage.class.getName()) %>">
			<aui:input name="assetEntryType" type="hidden" value="<%= IGImage.class.getName() %>" />

			<liferay-ui:message key="select" />: <liferay-ui:message key='<%= "model.resource." + IGImage.class.getName() %>' />

			<br /><br />

			<%@ include file="/html/portlet/asset_publisher/select_image_gallery_image_entry.jspf" %>
		</c:when>
		<c:when test="<%= typeSelection.equals(JournalArticle.class.getName()) %>">
			<aui:input name="assetEntryType" type="hidden" value="<%= JournalArticle.class.getName() %>" />

			<liferay-ui:message key="select" />: <liferay-ui:message key='<%= "model.resource." + JournalArticle.class.getName() %>' />

			<br /><br />

			<%@ include file="/html/portlet/asset_publisher/select_journal_article.jspf" %>
		</c:when>
		<c:when test="<%= typeSelection.equals(MBMessage.class.getName()) %>">
			<aui:input name="assetEntryType" type="hidden" value="<%= MBMessage.class.getName() %>" />

			<liferay-ui:message key="select" />: <liferay-ui:message key='<%= "model.resource." + MBMessage.class.getName() %>' />

			<br /><br />

			<%@ include file="/html/portlet/asset_publisher/select_message_boards_message.jspf" %>
		</c:when>
		<c:when test="<%= typeSelection.equals(WikiPage.class.getName()) %>">
			<aui:input name="assetEntryType" type="hidden" value="<%= WikiPage.class.getName() %>" />

			<liferay-ui:message key="select" />: <liferay-ui:message key='<%= "model.resource." + WikiPage.class.getName() %>' />

			<br /><br />

			<%@ include file="/html/portlet/asset_publisher/select_wiki_page.jspf" %>
		</c:when>
	</c:choose>
</aui:form>

<%!
private String _getKey(Group group) throws Exception {
	String key = null;

	if (group.isLayout()) {
		Layout layout = LayoutLocalServiceUtil.getLayout(group.getClassPK());

		key = "Layout" + StringPool.UNDERLINE + layout.getLayoutId();
	}
	else {
		key = "Group" + StringPool.UNDERLINE + group.getGroupId();;
	}

	return key;
}
%>

<script type="text/javascript">
	Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />selectionStyle);
</script>