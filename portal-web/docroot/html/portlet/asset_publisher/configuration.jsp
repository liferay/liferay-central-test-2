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
		if (document.<portlet:namespace />fm.<portlet:namespace />groupIds) {
			document.<portlet:namespace />fm.<portlet:namespace />groupIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentGroupIds);
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
	.aui-form fieldset {
		padding: 0;
	}

	.aui-form fieldset legend {
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

<form action="<%= configurationActionURL.toString() %>" class="aui-form" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= HtmlUtil.escapeAttribute(tabs2) %>" />
<input name="<portlet:namespace />typeSelection" type="hidden" value="" />
<input name="<portlet:namespace />assetEntryId" type="hidden" value="" />
<input name="<portlet:namespace />assetParentId" type="hidden" value="" />
<input name="<portlet:namespace />assetTitle" type="hidden" value="" />
<input name="<portlet:namespace />assetEntryOrder" type="hidden" value="-1" />

<c:choose>
	<c:when test="<%= typeSelection.equals(StringPool.BLANK) %>">
		<liferay-ui:message key="asset-selection" />

		<select name="<portlet:namespace />selectionStyle" onchange="<portlet:namespace />chooseSelectionStyle();">
			<option <%= selectionStyle.equals("dynamic") ? " selected=\"selected\"" : "" %> value="dynamic"><liferay-ui:message key="dynamic" /></option>
			<option <%= selectionStyle.equals("manual") ? " selected=\"selected\"" : "" %> value="manual"><liferay-ui:message key="manual" /></option>
		</select>

		<br /><br />

		<c:choose>
			<c:when test='<%= selectionStyle.equals("manual") %>'>
				<liferay-ui:panel-container id='assetPublisherConfiguration' extended="<%= Boolean.TRUE %>" persistState="<%= true %>">
					<liferay-ui:panel id='assetPublisherSelection' title='<%= LanguageUtil.get(pageContext, "selection") %>' collapsible="<%= true %>" persistState="<%= true %>" extended="<%= true %>">

						<%
						String portletId = portletResource;
						%>

						<%@ include file="/html/portlet/asset_publisher/add_asset.jspf" %>

						<select name="<portlet:namespace/>assetEntryType" onchange="<portlet:namespace />selectionForType(this.options[this.selectedIndex].value);">
							<option value=""><liferay-ui:message key="select-existing" /></option>

							<%
							for (int i = 0; i < AssetUtil.ASSET_ENTRY_TYPE_CLASS_NAMES.length; i++) {
								if (!AssetUtil.ASSET_ENTRY_TYPE_CLASS_NAMES[i].equals(MBMessage.class.getName()) && !AssetUtil.ASSET_ENTRY_TYPE_CLASS_NAMES[i].equals(WikiPage.class.getName())) {
								%>

									<option value="<%= AssetUtil.ASSET_ENTRY_TYPE_CLASS_NAMES[i] %>"><liferay-ui:message key='<%= "model.resource." + AssetUtil.ASSET_ENTRY_TYPE_CLASS_NAMES[i] %>' /></option>

								<%
								}
							}
							%>

						</select>

						<br /><br />

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

							String assetEntryType = root.element("asset-entry-type").getText();
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
							rowURL.setParameter("typeSelection", assetEntryType);
							rowURL.setParameter("assetEntryId", String.valueOf(assetEntryId));
							rowURL.setParameter("assetEntryOrder", String.valueOf(assetEntryOrder));

							// Type

							row.addText(LanguageUtil.get(pageContext, "model.resource." + assetEntryType), rowURL);

							// Title

							if (assetEntryType.equals(IGImage.class.getName())) {
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
					</liferay-ui:panel>
					<liferay-ui:panel id='assetPublisherDisplaySettings' title='<%= LanguageUtil.get(pageContext, "display-settings") %>' collapsible="<%= true %>" persistState="<%= true %>" extended="<%= true %>">
						<%@ include file="/html/portlet/asset_publisher/display_settings.jspf" %>
					</liferay-ui:panel>
				</liferay-ui:panel-container>

				<br />

				<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveSelectBoxes();" />

				<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />
			</c:when>
			<c:when test='<%= selectionStyle.equals("dynamic") %>'>
				<liferay-ui:panel-container id='assetPublisherConfiguration' extended="<%= Boolean.TRUE %>" persistState="<%= true %>">
					<liferay-ui:panel id='assetPublisherSources' title='<%= LanguageUtil.get(pageContext, "source") %>' collapsible="<%= true %>" persistState="<%= true %>" extended="<%= true %>">
						<liferay-ui:message key="scope" />

						<input name="<portlet:namespace />groupIds" type="hidden" value="" />

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

						List<KeyValuePair> groupsLeftList = new ArrayList<KeyValuePair>();

						for (long groupId : groupIds) {
							Group group = GroupLocalServiceUtil.getGroup(groupId);

							groupsLeftList.add(new KeyValuePair(String.valueOf(groupId), group.getDescriptiveName()));
						}

						// Right list

						List<KeyValuePair> groupsRightList = new ArrayList<KeyValuePair>();

						Arrays.sort(groupIds);

						for (Group group : groups) {
							if (Arrays.binarySearch(groupIds, group.getGroupId()) < 0) {
								groupsRightList.add(new KeyValuePair(String.valueOf(group.getGroupId()), group.getDescriptiveName()));
							}
						}

						groupsRightList = ListUtil.sort(groupsRightList, new KeyValuePairComparator(false, true));
						%>

						<liferay-ui:input-move-boxes
							formName="fm"
							leftTitle="current"
							rightTitle="available"
							leftBoxName="currentGroupIds"
							rightBoxName="availableGroupIds"
							leftReorder="true"
							leftList="<%= groupsLeftList %>"
							rightList="<%= groupsRightList %>"
						/>

						<br /><br />

						<liferay-ui:message key="asset-entry-type" />

						<input name="<portlet:namespace />classNameIds" type="hidden" value="" />

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
					</liferay-ui:panel>
					<liferay-ui:panel id='assetPublisherQueryLogic' title='<%= LanguageUtil.get(pageContext, "query-logic") %>' collapsible="<%= true %>" persistState="<%= true %>" extended="<%= true %>">
						<liferay-ui:asset-tags-error />

						<div id="<portlet:namespace />queryRules">
							<fieldset class="aui-block-labels">
								<legend><liferay-ui:message key="displayed-assets-must-match-these-rules" /></legend>

								<%
								String queryLogicIndexesParam = ParamUtil.getString(request, "queryLogicIndexes") ;

								int[] queryLogicIndexes = null;

								if (Validator.isNotNull(queryLogicIndexesParam)){
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
												<div class="aui-ctrl-holder">
													<select name="<portlet:namespace />queryContains<%= index %>">
														<option <%= queryContains ? "selected" : StringPool.BLANK %> value="true"><liferay-ui:message key="contains" /></option>
														<option <%= !queryContains ? "selected" : StringPool.BLANK %> value="false"><liferay-ui:message key="does-not-contain" /></option>
													</select>
												</div>

												<div class="aui-ctrl-holder">
													<select name="<portlet:namespace />queryAndOperator<%= index %>">
														<option <%= queryAndOperator ? "selected" : StringPool.BLANK %> value="true"><liferay-ui:message key="all" /></option>
														<option <%= !queryAndOperator ? "selected" : StringPool.BLANK %> value="false"><liferay-ui:message key="any" /></option>
													</select>
												</div>

												<div class="aui-ctrl-holder">
													<liferay-ui:message key="of-the-following" />
												</div>

												<div class="aui-ctrl-holder">
													<select class="asset-query-name" id="<portlet:namespace />queryName<%= index %>" name="<portlet:namespace />queryName<%= index %>">
														<option <%= Validator.equals(queryName, "assetTags") ? "selected" : StringPool.BLANK %> value="assetTags"><liferay-ui:message key="tags" /></option>
														<option <%= Validator.equals(queryName, "assetCategories") ? "selected" : StringPool.BLANK %> value="assetCategories"><liferay-ui:message key="categories" /></option>
													</select>
												</div>

												<div class="aui-ctrl-holder tags-selector" style="display:<%= Validator.equals(queryName, "assetTags") ? "block;" : "none;" %>">
													<liferay-ui:asset-tags-selector
														hiddenInput='<%= "queryTagNames" + index %>'
														curTags='<%=  Validator.equals(queryName, "assetTags") ? queryValues : null %>'
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

							</fieldset>
						</div>

						<script type="text/javascript">
							jQuery(
								function () {
									var queryRules = jQuery('#<portlet:namespace />queryRules');
									var queryRulesContainer = queryRules.find('> fieldset');
									var queryRulesRows = queryRulesContainer.find('.lfr-form-row');

									var autoFields = new Liferay.AutoFields(
										{
											container: queryRulesContainer,
											baseRows: queryRulesRows,
											fieldIndexes: '<portlet:namespace />queryLogicIndexes'
										}
									);

									var initQueryNameFields = function(selectFields) {
										selectFields = selectFields || jQuery('.asset-query-name');

										selectFields.each(
											function() {
												var select = jQuery(this);
												var row = jQuery(this.parentNode.parentNode);

												select.change(
													function(event) {
														var tagsSelector = row.find('.tags-selector');
														var categoriesSelector = row.find('.categories-selector');

														if (select.val() == 'assetTags') {
															tagsSelector.show();
															categoriesSelector.hide();
														}
														else {
															tagsSelector.hide();
															categoriesSelector.show();
														}
													}
												);
											}
										);
									};

									autoFields.bind(
										'addRow',
										function(event, data) {
											data = data[0];

											var row = jQuery(data.row);
											var index = data.idSeed;

											initQueryNameFields(row.find('.asset-query-name'));

											var firstCategoriesInput = row.find('.categories-selector > input:visible:first');
											var categoriesRandomNamespace = (firstCategoriesInput.attr('name') || firstCategoriesInput.attr('id')).substring(0,5);

											new Liferay.AssetCategoriesSelector(
												{
													instanceVar: categoriesRandomNamespace,
													seed: index,
													hiddenInput: '<portlet:namespace/>queryCategoryIds',
													summarySpan: categoriesRandomNamespace + 'assetCategoriesSummary'
												}
											);

											var firstTagsInput = row.find('.tags-selector > input:visible:first');
											var tagsRandomNamespace = (firstTagsInput.attr('name') || firstTagsInput.attr('id')).substring(0, 5);

											new Liferay.AssetTagsSelector(
												{
													instanceVar: tagsRandomNamespace,
													seed: index,
													hiddenInput:  '<portlet:namespace/>queryTagNames',
													summarySpan: tagsRandomNamespace + 'assetTagsSummary',
													textInput: tagsRandomNamespace + 'assetTagNames',
													focus: false
												}
											);
										}
									);

									initQueryNameFields();
								}
							);
						</script>

						<br /><br />

						<liferay-ui:message key="include-tags-specified-in-the-url" />

						<liferay-ui:input-checkbox param="mergeUrlTags" defaultValue="<%= mergeUrlTags %>" />
					</liferay-ui:panel>
					<liferay-ui:panel id='assetPublisherGroupBy' title='<%= LanguageUtil.get(pageContext, "group-by") %>' collapsible="<%= true %>" persistState="<%= true %>" extended="<%= true %>">
						<liferay-ui:message key="group-by" />

						<select name="<portlet:namespace />assetVocabularyId">
							<option value=""></option>
							<option <%= (assetVocabularyId == -1) ? "selected" : "" %> value="-1"><liferay-ui:message key="asset-types" /></option>

							<%
							List<AssetVocabulary> assetVocabularies = AssetVocabularyLocalServiceUtil.getGroupVocabularies(scopeGroupId);

							if (!assetVocabularies.isEmpty()) {
							%>

								<optgroup label="<liferay-ui:message key="vocabularies" />">

									<%
									for (AssetVocabulary assetVocabulary : assetVocabularies) {
									%>

										<option <%= (assetVocabularyId == assetVocabulary.getVocabularyId()) ? "selected" : "" %> value="<%= assetVocabulary.getVocabularyId() %>"><%= assetVocabulary.getName() %></option>

									<%
									}
									%>

								</optgroup>

							<%
							}
							%>

						</select>

					</liferay-ui:panel>
					<liferay-ui:panel id='assetPublisherDisplaySettings' title='<%= LanguageUtil.get(pageContext, "display-settings") %>' collapsible="<%= true %>" persistState="<%= true %>" extended="<%= true %>">
						<%@ include file="/html/portlet/asset_publisher/display_settings.jspf" %>
					</liferay-ui:panel>
				</liferay-ui:panel-container>

				<br />

				<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveSelectBoxes();" />

				<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />
			</c:when>
		</c:choose>
	</c:when>
	<c:when test="<%= typeSelection.equals(BlogsEntry.class.getName()) %>">
		<input name="<portlet:namespace />assetEntryType" type="hidden" value="<%= BlogsEntry.class.getName() %>" />

		<liferay-ui:message key="select" />: <liferay-ui:message key='<%= "model.resource." + BlogsEntry.class.getName() %>' />

		<br /><br />

		<%@ include file="/html/portlet/asset_publisher/select_blogs_entry.jspf" %>
	</c:when>
	<c:when test="<%= typeSelection.equals(BookmarksEntry.class.getName()) %>">
		<input name="<portlet:namespace />assetEntryType" type="hidden" value="<%= BookmarksEntry.class.getName() %>" />

		<liferay-ui:message key="select" />: <liferay-ui:message key='<%= "model.resource." + BookmarksEntry.class.getName() %>' />

		<br /><br />

		<%@ include file="/html/portlet/asset_publisher/select_bookmarks_entry.jspf" %>
	</c:when>
	<c:when test="<%= typeSelection.equals(DLFileEntry.class.getName()) %>">
		<input name="<portlet:namespace />assetEntryType" type="hidden" value="<%= DLFileEntry.class.getName() %>" />

		<liferay-ui:message key="select" />: <liferay-ui:message key='<%= "model.resource." + DLFileEntry.class.getName() %>' />

		<br /><br />

		<%@ include file="/html/portlet/asset_publisher/select_document_library_file_entry.jspf" %>
	</c:when>
	<c:when test="<%= typeSelection.equals(IGImage.class.getName()) %>">
		<input name="<portlet:namespace />assetEntryType" type="hidden" value="<%= IGImage.class.getName() %>" />

		<liferay-ui:message key="select" />: <liferay-ui:message key='<%= "model.resource." + IGImage.class.getName() %>' />

		<br /><br />

		<%@ include file="/html/portlet/asset_publisher/select_image_gallery_image_entry.jspf" %>
	</c:when>
	<c:when test="<%= typeSelection.equals(JournalArticle.class.getName()) %>">
		<input name="<portlet:namespace />assetEntryType" type="hidden" value="<%= JournalArticle.class.getName() %>" />

		<liferay-ui:message key="select" />: <liferay-ui:message key='<%= "model.resource." + JournalArticle.class.getName() %>' />

		<br /><br />

		<%@ include file="/html/portlet/asset_publisher/select_journal_article.jspf" %>
	</c:when>
	<c:when test="<%= typeSelection.equals(MBMessage.class.getName()) %>">
		<input name="<portlet:namespace />assetEntryType" type="hidden" value="<%= MBMessage.class.getName() %>" />

		<liferay-ui:message key="select" />: <liferay-ui:message key='<%= "model.resource." + MBMessage.class.getName() %>' />

		<br /><br />

		<%@ include file="/html/portlet/asset_publisher/select_message_boards_message.jspf" %>
	</c:when>
	<c:when test="<%= typeSelection.equals(WikiPage.class.getName()) %>">
		<input name="<portlet:namespace />assetEntryType" type="hidden" value="<%= WikiPage.class.getName() %>" />

		<liferay-ui:message key="select" />: <liferay-ui:message key='<%= "model.resource." + WikiPage.class.getName() %>' />

		<br /><br />

		<%@ include file="/html/portlet/asset_publisher/select_wiki_page.jspf" %>
	</c:when>
</c:choose>

</form>