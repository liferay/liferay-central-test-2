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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "version-history");

String redirect = ParamUtil.getString(request, "redirect");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

String uploadProgressId = "dlFileEntryUploadProgress";

FileEntry fileEntry = (FileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);

long fileEntryId = fileEntry.getFileEntryId();

long folderId = fileEntry.getFolderId();

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/document_library/view");
	portletURL.setParameter("folderId", String.valueOf(folderId));

	redirect = portletURL.toString();
}

Folder folder = fileEntry.getFolder();
FileVersion fileVersion = (FileVersion)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_VERSION);

boolean versionSpecific = false;

if (fileVersion != null) {
	versionSpecific = true;
}
else if ((user.getUserId() == fileEntry.getUserId()) || permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE)) {
	fileVersion = fileEntry.getLatestFileVersion();
}
else {
	fileVersion = fileEntry.getFileVersion();
}

long fileVersionId = fileVersion.getFileVersionId();

long fileEntryTypeId = 0;

if (fileVersion.getModel() instanceof DLFileVersion) {
	DLFileVersion dlFileVersion = (DLFileVersion)fileVersion.getModel();

	fileEntryTypeId = dlFileVersion.getFileEntryTypeId();
}

Lock lock = fileEntry.getLock();

String[] conversions = new String[0];

if (PropsValues.DL_FILE_ENTRY_CONVERSIONS_ENABLED && PrefsPropsUtil.getBoolean(PropsKeys.OPENOFFICE_SERVER_ENABLED, PropsValues.OPENOFFICE_SERVER_ENABLED)) {
	conversions = (String[])DocumentConversionUtil.getConversions(fileVersion.getExtension());
}

long assetClassPK = 0;

if (!fileVersion.isApproved() && !fileVersion.getVersion().equals(DLFileEntryConstants.VERSION_DEFAULT) && !fileEntry.isInTrash()) {
	assetClassPK = fileVersion.getFileVersionId();
}
else {
	assetClassPK = fileEntry.getFileEntryId();
}

String webDavURL = StringPool.BLANK;

if (portletDisplay.isWebDAVEnabled()) {
	webDavURL = DLUtil.getWebDavURL(themeDisplay, folder, fileEntry);
}

boolean hasAudio = AudioProcessorUtil.hasAudio(fileVersion);
boolean hasImages = ImageProcessorUtil.hasImages(fileVersion);
boolean hasPDFImages = PDFProcessorUtil.hasImages(fileVersion);
boolean hasVideo = VideoProcessorUtil.hasVideo(fileVersion);

AssetEntry layoutAssetEntry = AssetEntryLocalServiceUtil.fetchEntry(DLFileEntryConstants.getClassName(), assetClassPK);

request.setAttribute(WebKeys.LAYOUT_ASSET_ENTRY, layoutAssetEntry);

DLActionsDisplayContext dlActionsDisplayContext = new DLActionsDisplayContext(request, dlPortletInstanceSettings);
DLFileVersionActionsDisplayContext dlFileVersionActionsDisplayContext = DLFileVersionActionsDisplayContextUtil.getDLFileVersionActionsDisplayContext(request, response, fileVersion);
%>

<portlet:actionURL var="editFileEntry">
	<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
</portlet:actionURL>

<aui:form action="<%= editFileEntry %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="fileEntryId" type="hidden" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
</aui:form>

<c:if test="<%= showHeader && (folder != null) %>">
	<liferay-ui:header
		backURL="<%= redirect %>"
		localizeTitle="<%= false %>"
		title="<%= fileVersion.getTitle() %>"
	/>
</c:if>

<div class="view">
	<c:if test="<%= dlActionsDisplayContext.isShowActions() %>">
		<liferay-ui:app-view-toolbar>
			<aui:button-row cssClass="edit-toolbar" id='<%= renderResponse.getNamespace() + "fileEntryToolbar" %>' />
		</liferay-ui:app-view-toolbar>
	</c:if>

	<aui:row>
		<aui:col cssClass="lfr-asset-column-details" width="<%= 70 %>">
			<c:if test="<%= dlActionsDisplayContext.isShowActions() %>">
				<liferay-ui:app-view-toolbar>
					<aui:button-row cssClass="edit-toolbar" id='<%= renderResponse.getNamespace() + "fileEntryToolbar" %>' />
				</liferay-ui:app-view-toolbar>
			</c:if>

			<div class="alert alert-danger hide" id="<portlet:namespace />openMSOfficeError"></div>

			<c:if test="<%= (fileEntry.getLock() != null) && DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE) %>">
				<c:choose>
					<c:when test="<%= fileEntry.hasLock() %>">
						<div class="alert alert-success">
							<c:choose>
								<c:when test="<%= lock.isNeverExpires() %>">
									<liferay-ui:message key="you-now-have-an-indefinite-lock-on-this-document" />
								</c:when>
								<c:otherwise>

									<%
									String lockExpirationTime = StringUtil.toLowerCase(LanguageUtil.getTimeDescription(request, DLFileEntryConstants.LOCK_EXPIRATION_TIME));
									%>

									<%= LanguageUtil.format(request, "you-now-have-a-lock-on-this-document", lockExpirationTime, false) %>
								</c:otherwise>
							</c:choose>
						</div>
					</c:when>
					<c:otherwise>
						<div class="alert alert-danger">
							<%= LanguageUtil.format(request, "you-cannot-modify-this-document-because-it-was-locked-by-x-on-x", new Object[] {HtmlUtil.escape(PortalUtil.getUserName(lock.getUserId(), String.valueOf(lock.getUserId()))), dateFormatDateTime.format(lock.getCreateDate())}, false) %>
						</div>
					</c:otherwise>
				</c:choose>
			</c:if>

			<liferay-util:buffer var="documentTitle">
				<%= fileVersion.getTitle() %>

				<c:if test="<%= versionSpecific %>">
					(<liferay-ui:message key="version" /> <%= fileVersion.getVersion() %>)
				</c:if>
			</liferay-util:buffer>

			<div class="body-row">
				<div class="document-info">
					<c:if test="<%= dlFileVersionActionsDisplayContext.isAssetMetadataVisible() %>">
						<h2 class="document-title" title="<%= HtmlUtil.escapeAttribute(documentTitle) %>">
							<%= HtmlUtil.escape(documentTitle) %>
						</h2>

						<span class="document-thumbnail">

							<%
							String thumbnailSrc = DLUtil.getThumbnailSrc(fileEntry, fileVersion, null, themeDisplay);

							if (layoutAssetEntry != null) {
								AssetEntry incrementAssetEntry = AssetEntryServiceUtil.incrementViewCounter(layoutAssetEntry.getClassName(), fileEntry.getFileEntryId());

								if (incrementAssetEntry != null) {
									layoutAssetEntry = incrementAssetEntry;
								}
							}
							%>

							<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="thumbnail" />" class="thumbnail" src="<%= thumbnailSrc %>" style="<%= DLUtil.getThumbnailStyle(true, 0) %>" />
						</span>

						<span class="user-date">

							<%
							String displayURL = StringPool.BLANK;

							User userDisplay = UserLocalServiceUtil.fetchUser(fileEntry.getUserId());

							if (userDisplay != null) {
								displayURL = userDisplay.getDisplayURL(themeDisplay);
							}
							%>

							<liferay-ui:icon iconCssClass="icon-plus" label="<%= true %>" message='<%= LanguageUtil.format(request, "uploaded-by-x-x", new Object[] {displayURL, HtmlUtil.escape(fileEntry.getUserName()), dateFormatDateTime.format(fileEntry.getCreateDate())}, false) %>' />
						</span>

						<c:if test="<%= dlPortletInstanceSettings.isEnableRatings() && fileEntry.isSupportsSocial() %>">
							<span class="lfr-asset-ratings">
								<liferay-ui:ratings
									className="<%= DLFileEntryConstants.getClassName() %>"
									classPK="<%= fileEntryId %>"
								/>
							</span>
						</c:if>

						<c:if test="<%= dlPortletInstanceSettings.isEnableRelatedAssets() && fileEntry.isSupportsSocial() %>">
							<div class="entry-links">
								<liferay-ui:asset-links
									assetEntryId="<%= layoutAssetEntry.getEntryId() %>"
								/>
							</div>
						</c:if>
					</c:if>

					<span class="document-description">
						<%= HtmlUtil.escape(fileVersion.getDescription()) %>
					</span>

					<c:if test="<%= dlFileVersionActionsDisplayContext.isAssetMetadataVisible() && fileEntry.isSupportsSocial() %>">
						<div class="lfr-asset-categories">
							<liferay-ui:asset-categories-summary
								className="<%= DLFileEntryConstants.getClassName() %>"
								classPK="<%= assetClassPK %>"
							/>
						</div>

						<div class="lfr-asset-tags">
							<liferay-ui:asset-tags-summary
								className="<%= DLFileEntryConstants.getClassName() %>"
								classPK="<%= assetClassPK %>"
								message="tags"
							/>
						</div>
					</c:if>
				</div>

				<aui:model-context bean="<%= fileVersion %>" model="<%= DLFileVersion.class %>" />

				<c:if test="<%= PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED %>">

					<%
					boolean showImageContainer = true;
					%>

					<%@ include file="/html/portlet/document_library/view_file_entry_preview.jspf" %>
				</c:if>

				<c:if test="<%= dlFileVersionActionsDisplayContext.isAssetMetadataVisible() && PropsValues.DL_FILE_ENTRY_COMMENTS_ENABLED %>">
					<liferay-ui:panel collapsible="<%= true %>" cssClass="lfr-document-library-comments" extended="<%= true %>" persistState="<%= true %>" title="comments">
						<portlet:actionURL var="discussionURL">
							<portlet:param name="struts_action" value="/document_library/edit_file_entry_discussion" />
						</portlet:actionURL>

						<liferay-ui:discussion
							className="<%= DLFileEntryConstants.getClassName() %>"
							classPK="<%= fileEntryId %>"
							formAction="<%= discussionURL %>"
							formName="fm2"
							ratingsEnabled="<%= dlPortletInstanceSettings.isEnableCommentRatings() %>"
							redirect="<%= currentURL %>"
							userId="<%= fileEntry.getUserId() %>"
						/>
					</liferay-ui:panel>
				</c:if>
			</div>
		</aui:col>

		<aui:col cssClass="context-pane lfr-asset-column-details" last="<%= true %>" width="<%= 30 %>">
			<div class="asset-details body-row">
				<c:if test="<%= dlFileVersionActionsDisplayContext.isAssetMetadataVisible() %>">
					<div class="asset-details-content">
						<h3 class="version <%= fileEntry.isCheckedOut() ? "icon-lock" : StringPool.BLANK %>">
							<liferay-ui:message key="version" /> <%= HtmlUtil.escape(fileVersion.getVersion()) %>
						</h3>

						<c:if test="<%= !portletId.equals(PortletKeys.TRASH) %>">
							<div>
								<aui:workflow-status model="<%= DLFileEntry.class %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= fileVersion.getStatus() %>" />
							</div>
						</c:if>

						<div class="icon-user lfr-asset-icon">
							<liferay-ui:message arguments="<%= HtmlUtil.escape(fileVersion.getStatusByUserName()) %>" key="last-updated-by-x" translateArguments="<%= false %>" />
						</div>

						<div class="icon-calendar lfr-asset-icon">
							<%= dateFormatDateTime.format(fileVersion.getModifiedDate()) %>
						</div>

						<c:if test="<%= Validator.isNotNull(fileVersion.getDescription()) %>">
							<blockquote class="lfr-asset-description">
								<%= HtmlUtil.escape(fileVersion.getDescription()) %>
							</blockquote>
						</c:if>

						<span class="download-document">
							<c:if test="<%= DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.VIEW) %>">
								<liferay-ui:icon
									iconCssClass="icon-download"
									label="<%= true %>"
									message='<%= LanguageUtil.get(request, "download") + " (" + TextFormatter.formatStorageSize(fileVersion.getSize(), locale) + ")" %>'
									url="<%= DLUtil.getDownloadURL(fileEntry, fileVersion, themeDisplay, StringPool.BLANK) %>"
								/>
							</c:if>
						</span>

						<span class="conversions">

							<%
							for (int i = 0; i < conversions.length; i++) {
								String conversion = conversions[i];
							%>

								<liferay-ui:icon
									iconCssClass="<%= DLUtil.getFileIconCssClass(conversion) %>"
									label="<%= true %>"
									message="<%= StringUtil.toUpperCase(conversion) %>"
									url='<%= DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&targetExtension=" + conversion) %>'
								/>

							<%
							}
							%>

						</span>

						<span class="webdav-url">
							<c:choose>
								<c:when test="<%= portletDisplay.isWebDAVEnabled() && fileEntry.isSupportsSocial() %>">
									<liferay-ui:message key="get-url-or-webdav-url" />
								</c:when>

								<c:otherwise>
									<liferay-ui:message key="get-url" />
								</c:otherwise>
							</c:choose>
						</span>

						<div class="hide lfr-asset-field url-file-container">
							<aui:input name="url" type="resource" value="<%= DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK, false, true) %>" />
						</div>

						<c:if test="<%= portletDisplay.isWebDAVEnabled() && fileEntry.isSupportsSocial() %>">
							<div class="hide lfr-asset-field webdav-url-file-container">

								<%
								String webDavHelpMessage = null;

								if (BrowserSnifferUtil.isWindows(request)) {
									webDavHelpMessage = LanguageUtil.format(request, "webdav-windows-help", new Object[] {"http://www.microsoft.com/downloads/details.aspx?FamilyId=17C36612-632E-4C04-9382-987622ED1D64", "http://www.liferay.com/web/guest/community/wiki/-/wiki/Main/WebDAV"}, false);
								}
								else {
									webDavHelpMessage = LanguageUtil.format(request, "webdav-help", "http://www.liferay.com/web/guest/community/wiki/-/wiki/Main/WebDAV", false);
								}
								%>

								<aui:input helpMessage="<%= webDavHelpMessage %>" name="webDavURL"  type="resource" value="<%= webDavURL %>" />
							</div>
						</c:if>
					</div>
				</c:if>

				<%
					request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
				%>

				<div class="lfr-asset-panels">
					<liferay-ui:panel-container extended="<%= false %>" id="documentLibraryAssetPanelContainer" persistState="<%= true %>">

						<%
						if (fileEntryTypeId > 0) {
							try {
								DLFileEntryType fileEntryType = DLFileEntryTypeServiceUtil.getFileEntryType(fileEntryTypeId);

								List<DDMStructure> ddmStructures = fileEntryType.getDDMStructures();

								for (DDMStructure ddmStructure : ddmStructures) {
									Fields fields = null;

									try {
										DLFileEntryMetadata fileEntryMetadata = DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(ddmStructure.getStructureId(), fileVersionId);

										fields = StorageEngineUtil.getFields(fileEntryMetadata.getDDMStorageId());
									}
									catch (Exception e) {
									}
						%>

									<liferay-ui:panel collapsible="<%= true %>" cssClass="metadata" extended="<%= true %>" id="documentLibraryMetadataPanel" persistState="<%= true %>" title="<%= HtmlUtil.escape(ddmStructure.getName(locale)) %>">

										<liferay-ddm:html
											classNameId="<%= PortalUtil.getClassNameId(DDMStructure.class) %>"
											classPK="<%= ddmStructure.getPrimaryKey() %>"
											fields="<%= fields %>"
											fieldsNamespace="<%= String.valueOf(ddmStructure.getPrimaryKey()) %>"
											readOnly="<%= true %>"
											requestedLocale="<%= locale %>"
											showEmptyFieldLabel="<%= false %>"
										/>

									</liferay-ui:panel>

						<%
								}
							}
							catch (Exception e) {
							}
						}
						%>

						<liferay-ui:custom-attributes-available className="<%= DLFileEntryConstants.getClassName() %>" classPK="<%= fileVersionId %>" editable="<%= false %>">
							<liferay-ui:panel collapsible="<%= true %>" cssClass="custom-fields" id="documentLibraryCustomAttributesPanel" persistState="<%= true %>" title="custom-fields">
								<liferay-ui:custom-attribute-list
									className="<%= DLFileEntryConstants.getClassName() %>"
									classPK="<%= fileVersionId %>"
									editable="<%= false %>"
									label="<%= true %>"
								/>
							</liferay-ui:panel>
						</liferay-ui:custom-attributes-available>

						<%
						try {
							List<DDMStructure> ddmStructures = DDMStructureLocalServiceUtil.getClassStructures(company.getCompanyId(), PortalUtil.getClassNameId(RawMetadataProcessor.class), new StructureStructureKeyComparator(true));

							for (DDMStructure ddmStructure : ddmStructures) {
								Fields fields = null;

								try {
									DLFileEntryMetadata fileEntryMetadata = DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(ddmStructure.getStructureId(), fileVersionId);

									fields = StorageEngineUtil.getFields(fileEntryMetadata.getDDMStorageId());
								}
								catch (Exception e) {
								}

								if (fields != null) {
									String name = "metadata." + ddmStructure.getName(locale, true);
						%>

									<liferay-ui:panel collapsible="<%= true %>" cssClass="lfr-asset-metadata" id="documentLibraryAssetMetadataPanel" persistState="<%= true %>" title="<%= name %>">

										<liferay-ddm:html
											classNameId="<%= PortalUtil.getClassNameId(DDMStructure.class) %>"
											classPK="<%= ddmStructure.getPrimaryKey() %>"
											fields="<%= fields %>"
											fieldsNamespace="<%= String.valueOf(ddmStructure.getPrimaryKey()) %>"
											readOnly="<%= true %>"
											requestedLocale="<%= locale %>"
											showEmptyFieldLabel="<%= false %>"
										/>

									</liferay-ui:panel>

						<%
								}
							}
						}
						catch (Exception e) {
						}
						%>

						<c:if test="<%= dlFileVersionActionsDisplayContext.isAssetMetadataVisible() %>">
							<liferay-ui:panel collapsible="<%= true %>" cssClass="version-history" id="documentLibraryVersionHistoryPanel" persistState="<%= true %>" title="version-history">

								<%
								boolean comparableFileEntry = DocumentConversionUtil.isComparableVersion(fileVersion.getExtension());
								boolean showNonApprovedDocuments = false;

								if ((user.getUserId() == fileEntry.getUserId()) || permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
									showNonApprovedDocuments = true;
								}

								PortletURL viewFileEntryURL = renderResponse.createRenderURL();

								viewFileEntryURL.setParameter("struts_action", "/document_library/view_file_entry");
								viewFileEntryURL.setParameter("redirect", currentURL);
								viewFileEntryURL.setParameter("fileEntryId", String.valueOf(fileEntry.getFileEntryId()));

								RowChecker rowChecker = null;

								if (comparableFileEntry) {
									rowChecker = new RowChecker(renderResponse);

									rowChecker.setAllRowIds(null);
								}

								int status = WorkflowConstants.STATUS_APPROVED;

								if (showNonApprovedDocuments) {
									status = WorkflowConstants.STATUS_ANY;
								}

								List fileVersions = fileEntry.getFileVersions(status);
								%>

								<liferay-ui:search-container
									iteratorURL="<%= viewFileEntryURL %>"
									rowChecker="<%= rowChecker %>"
									total="<%= fileVersions.size() %>"
								>
									<liferay-ui:search-container-results
										results="<%= ListUtil.subList(fileVersions, searchContainer.getStart(), searchContainer.getEnd()) %>"
									/>

									<liferay-ui:search-container-row
										className="com.liferay.portal.kernel.repository.model.FileVersion"
										keyProperty="fileVersionId"
										modelVar="curFileVersion"
									>
										<liferay-ui:search-container-column-text
											property="version"
										/>

										<liferay-ui:search-container-column-date
											name="date"
											property="createDate"
										/>

										<liferay-ui:search-container-column-text
											name="size"
											value="<%= (TextFormatter.formatStorageSize(curFileVersion.getSize(), locale)) %>"
										/>

										<c:if test="<%= showNonApprovedDocuments && !portletId.equals(PortletKeys.TRASH) %>">
											<liferay-ui:search-container-column-status property="status" />
										</c:if>

										<liferay-ui:search-container-column-jsp
											align="right"
											cssClass="entry-action"
											path="/html/portlet/document_library/file_entry_history_action.jsp"
										/>
									</liferay-ui:search-container-row>

									<liferay-ui:search-iterator />
								</liferay-ui:search-container>

								<%
								if (comparableFileEntry && !fileVersions.isEmpty()) {
									FileVersion curFileVersion = (FileVersion)fileVersions.get(0);
								%>

									<portlet:actionURL var="compareVersionsURL">
										<portlet:param name="struts_action" value="/document_library/compare_versions" />
									</portlet:actionURL>

									<aui:form action="<%= compareVersionsURL %>" method="post" name="fm1" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "compare();" %>'>
										<aui:input name="backURL" type="hidden" value="<%= currentURL %>" />
										<aui:input name="sourceFileVersionId" type="hidden" value="<%= curFileVersion.getFileVersionId() %>" />
										<aui:input name="targetFileVersionId" type="hidden" value="<%= fileVersion.getFileVersionId() %>" />

										<aui:button-row>
											<aui:button type="submit" value="compare-versions" />
										</aui:button-row>
									</aui:form>

								<%
								}
								%>

							</liferay-ui:panel>
						</c:if>
					</liferay-ui:panel-container>
				</div>
			</div>
		</aui:col>
	</aui:row>
</div>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />compare',
		function() {
			var A = AUI();

			var rowIds = A.all('input[name=<portlet:namespace />rowIds]:checked');
			var sourceFileVersionId = A.one('input[name="<portlet:namespace />sourceFileVersionId"]');
			var targetFileVersionId = A.one('input[name="<portlet:namespace />targetFileVersionId"]');

			var rowIdsSize = rowIds.size();

			if (rowIdsSize == 1) {
				if (sourceFileVersionId) {
					sourceFileVersionId.val(rowIds.item(0).val());
				}
			}
			else if (rowIdsSize == 2) {
				if (sourceFileVersionId) {
					sourceFileVersionId.val(rowIds.item(1).val());
				}

				if (targetFileVersionId) {
					targetFileVersionId.val(rowIds.item(0).val());
				}
			}

			submitForm(document.<portlet:namespace />fm1);
		},
		['aui-base', 'selector-css3']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />initRowsChecked',
		function() {
			var A = AUI();

			var rowIds = A.all('input[name=<portlet:namespace />rowIds]');

			rowIds.each(
				function(item, index) {
					if (index >= 2) {
						item.set('checked', false);
					}
				}
			);
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />updateRowsChecked',
		function(element) {
			var A = AUI();

			var rowsChecked = A.all('input[name=<portlet:namespace />rowIds]:checked');

			if (rowsChecked.size() > 2) {
				var index = 2;

				if (rowsChecked.item(2).compareTo(element)) {
					index = 1;
				}

				rowsChecked.item(index).set('checked', false);
			}
		},
		['aui-base', 'selector-css3']
	);
</aui:script>

<c:if test="<%= DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.VIEW) && DLUtil.isOfficeExtension(fileVersion.getExtension()) && portletDisplay.isWebDAVEnabled() && BrowserSnifferUtil.isIeOnWin32(request) %>">
	<%@ include file="/html/portlet/document_library/action/open_document_js.jspf" %>
</c:if>

<aui:script use="aui-base,aui-toolbar">
	var showURLFile = A.one('.show-url-file');
	var showWebDavFile = A.one('.show-webdav-url-file');

	if (showURLFile) {
		showURLFile.on(
			'click',
			function(event) {
				var URLFileContainer = A.one('.url-file-container');

				URLFileContainer.toggleClass('hide');
			}
		);
	}

	if (showWebDavFile) {
		showWebDavFile.on(
			'click',
			function(event) {
				var WebDavFileContainer = A.one('.webdav-url-file-container');

				WebDavFileContainer.toggleClass('hide');
			}
		);
	}

	<c:if test="<%= dlActionsDisplayContext.isShowActions() %>">
		var buttonRow = A.one('#<portlet:namespace />fileEntryToolbar');

		var fileEntryButtonGroup = [];

		<c:if test="<%= dlFileVersionActionsDisplayContext.isDownloadButtonVisible() %>">
			fileEntryButtonGroup.push(
				{
					icon: 'icon-download',
					label: '<%= UnicodeLanguageUtil.get(request, "download") %>',
					on: {
						click: function(event) {
							location.href = '<%= DLUtil.getDownloadURL(fileEntry, fileVersion, themeDisplay, StringPool.BLANK) %>';
						}
					}
				}
			);
		</c:if>

		<c:if test="<%= dlFileVersionActionsDisplayContext.isOpenInMsOfficeButtonVisible() %>">
			fileEntryButtonGroup.push(
				{
					icon: 'icon-file-alt',
					label: '<%= UnicodeLanguageUtil.get(request, "open-in-ms-office") %>',
					on: {
						click: function(event) {
							<portlet:namespace />openDocument('<%= DLUtil.getWebDavURL(themeDisplay, fileEntry.getFolder(), fileEntry, PropsValues.DL_FILE_ENTRY_OPEN_IN_MS_OFFICE_MANUAL_CHECK_IN_REQUIRED) %>');
						}
					}
				}
			);
		</c:if>

		<c:if test="<%= dlFileVersionActionsDisplayContext.isEditButtonVisible() %>">
			fileEntryButtonGroup.push(
				{

					<portlet:renderURL var="editURL">
						<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
					</portlet:renderURL>

					icon: 'icon-edit',
					label: '<%= UnicodeLanguageUtil.get(request, "edit") %>',
					on: {
						click: function(event) {
							location.href = '<%= editURL.toString() %>';
						}
					}
				}
			);
		</c:if>

		<c:if test="<%= dlFileVersionActionsDisplayContext.isMoveButtonVisible() %>">
			fileEntryButtonGroup.push(
				{

					<portlet:renderURL var="moveURL">
						<portlet:param name="struts_action" value="/document_library/move_entry" />
						<portlet:param name="redirect" value="<%= redirect %>" />
						<portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
					</portlet:renderURL>

					icon: 'icon-move',
					label: '<%= UnicodeLanguageUtil.get(request, "move") %>',
					on: {
						click: function(event) {
							location.href = '<%= moveURL.toString() %>';
						}
					}
				}
			);
		</c:if>

		<c:if test="<%= dlFileVersionActionsDisplayContext.isCheckoutDocumentButtonVisible() %>">
			fileEntryButtonGroup.push(
				{

					icon: 'icon-unlock',
					label: '<%= UnicodeLanguageUtil.get(request, "checkout[document]") %>',
					on: {
						click: function(event) {
							document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.CHECKOUT %>';
							submitForm(document.<portlet:namespace />fm);
						}
					}
				}
			);
		</c:if>

		<c:if test="<%= dlFileVersionActionsDisplayContext.isCancelCheckoutDocumentButtonVisible() %>">
			fileEntryButtonGroup.push(
				{

					icon: 'icon-undo',
					label: '<%= UnicodeLanguageUtil.get(request, "cancel-checkout[document]") %>',
					on: {
						click: function(event) {
							document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.CANCEL_CHECKOUT %>';
							submitForm(document.<portlet:namespace />fm);
						}
					}
				}
			);
		</c:if>

		<c:if test="<%= dlFileVersionActionsDisplayContext.isCheckinButtonVisible() %>">
			fileEntryButtonGroup.push(
				{

					icon: 'icon-lock',
					label: '<%= UnicodeLanguageUtil.get(request, "checkin") %>',
					on: {
						click: function(event) {
							document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.CHECKIN %>';
							submitForm(document.<portlet:namespace />fm);
						}
					}
				}
			);
		</c:if>

		<c:if test="<%= dlFileVersionActionsDisplayContext.isPermissionsButtonVisible() %>">
			fileEntryButtonGroup.push(
				{
					<liferay-security:permissionsURL
						modelResource="<%= DLFileEntryConstants.getClassName() %>"
						modelResourceDescription="<%= fileEntry.getTitle() %>"
						resourcePrimKey="<%= String.valueOf(fileEntry.getFileEntryId()) %>"
						var="permissionsURL"
						windowState="<%= LiferayWindowState.POP_UP.toString() %>"
					/>

					icon: 'icon-lock',
					label: '<%= UnicodeLanguageUtil.get(request, "permissions") %>',
					on: {
						click: function(event) {
							Liferay.Util.openWindow(
								{
									title: '<%= UnicodeLanguageUtil.get(request, "permissions") %>',
									uri: '<%= permissionsURL.toString() %>'
								}
							);
						}
					}
				}
			);
		</c:if>

		<c:if test="<%= dlFileVersionActionsDisplayContext.isMoveToTheRecycleBinButtonVisible() %>">
			fileEntryButtonGroup.push(
				{
					<portlet:renderURL var="viewFolderURL">
						<portlet:param name="struts_action" value="/document_library/view" />
						<portlet:param name="folderId" value="<%= String.valueOf(fileEntry.getFolderId()) %>" />
					</portlet:renderURL>

					icon: 'icon-trash',
					label: '<%= UnicodeLanguageUtil.get(request, "move-to-the-recycle-bin") %>',
					on: {
						click: function(event) {
							document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.MOVE_TO_TRASH %>';
							document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<%= viewFolderURL.toString() %>';
							submitForm(document.<portlet:namespace />fm);
						}
					}
				}
			);
		</c:if>

		<c:if test="<%= dlFileVersionActionsDisplayContext.isDeleteButtonVisible() %>">
			fileEntryButtonGroup.push(
				{
					<portlet:renderURL var="viewFolderURL">
						<portlet:param name="struts_action" value="/document_library/view" />
						<portlet:param name="folderId" value="<%= String.valueOf(fileEntry.getFolderId()) %>" />
					</portlet:renderURL>

					icon: 'icon-remove',
					label: '<%= UnicodeLanguageUtil.get(request, "delete") %>',
					on: {
						click: function(event) {
							if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
								document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.DELETE %>';
								document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<%= viewFolderURL.toString() %>';
								submitForm(document.<portlet:namespace />fm);
							}
						}
					}
				}
			);
		</c:if>

		var fileEntryToolbar = new A.Toolbar(
			{
				boundingBox: buttonRow,
				children: [fileEntryButtonGroup]
			}
		).render();

		buttonRow.setData('fileEntryToolbar', fileEntryToolbar);
	</c:if>

	<portlet:namespace />initRowsChecked();

	A.all('input[name=<portlet:namespace />rowIds]').on(
		'click',
		function(event) {
			<portlet:namespace />updateRowsChecked(event.currentTarget);
		}
	);
</aui:script>

<%
if (!portletId.equals(PortletKeys.TRASH)) {
	DLUtil.addPortletBreadcrumbEntries(fileEntry, request, renderResponse);
}
%>