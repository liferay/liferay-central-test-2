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

	function <portlet:namespace />saveMetadataFields() {
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
	.lfr-panel .lfr-panel-titlebar {
		margin-bottom: 0;
	}

	.lfr-panel-content {
		background-color: #eee;
		padding: 10px;
	}
</style>

<form action="<%= configurationActionURL.toString() %>" method="post" name="<portlet:namespace />fm">
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
							<option value=""><liferay-ui:message key="select-existing" />...</option>

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

				<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveMetadataFields();" />

				<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />
			</c:when>
			<c:when test='<%= selectionStyle.equals("dynamic") %>'>
				<liferay-ui:panel-container id='assetPublisherConfiguration' extended="<%= Boolean.TRUE %>" persistState="<%= true %>">
					<liferay-ui:panel id='assetPublisherQueryLogic' title='<%= LanguageUtil.get(pageContext, "query-logic") %>' collapsible="<%= true %>" persistState="<%= true %>" extended="<%= true %>">
						<liferay-ui:asset-tags-error />

						<liferay-ui:message key="asset-entry-type" />

						<select name="<portlet:namespace/>classNameId">
							<option value=""><liferay-ui:message key="all" /></option>

							<%
							AssetEntryType[] assetEntryTypes = AssetEntryServiceUtil.getEntryTypes(themeDisplay.getLocale().toString());

							for (int i = 0; i < assetEntryTypes.length; i++) {
							%>

								<option <%= (classNameId == assetEntryTypes[i].getClassNameId()) ? "selected" : "" %> value="<%= assetEntryTypes[i].getClassNameId() %>"><liferay-ui:message key='<%= "model.resource." + assetEntryTypes[i].getClassName() %>' /></option>

							<%
							}
							%>

						</select>

						<br /><br />

						<liferay-ui:message key="displayed-content-must-contain-the-following-tags" />

						<br /><br />

						<liferay-ui:asset-tags-selector
							hiddenInput="assetTagNames"
							curTags="<%= StringUtil.merge(assetTagNames) %>"
							focus="<%= false %>"
						/>

						<br /><br />

						<liferay-ui:message key="displayed-content-must-not-contain-the-following-tags" />

						<br /><br />

						<liferay-ui:asset-tags-selector
							hiddenInput="notAssetTagNames"
							curTags="<%= StringUtil.merge(notAssetTagNames) %>"
							focus="<%= false %>"
						/>

						<br /><br />

						<liferay-ui:message key="displayed-content-must-contain-the-following-categories" />

						<br /><br />

						<liferay-ui:asset-categories-selector
							hiddenInput="assetCategoryIds"
							curCategoryIds="<%= StringUtil.merge(assetCategoryIds) %>"
							focus="<%= false %>"
						/>

						<br /><br />

						<liferay-ui:message key="displayed-content-must-not-contain-the-following-categories" />

						<br /><br />

						<liferay-ui:asset-categories-selector
							hiddenInput="notAssetCategoryIds"
							curCategoryIds="<%= StringUtil.merge(notAssetCategoryIds) %>"
							focus="<%= false %>"
						/>

						<br /><br />

						<liferay-ui:message key="search-operator" />

						<select name="<portlet:namespace />andOperator">
							<option <%= andOperator ? "selected" : "" %> value="1"><liferay-ui:message key="and" /></option>
							<option <%= !andOperator ? "selected" : "" %> value="0"><liferay-ui:message key="or" /></option>
						</select>

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

				<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveMetadataFields();" />

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