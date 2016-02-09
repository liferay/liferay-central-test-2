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

<%@ include file="/image_gallery_display/init.jsp" %>

<%
SearchContainer igSearchContainer = (SearchContainer)request.getAttribute("view.jsp-igSearchContainer");

DLPortletInstanceSettingsHelper dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(igRequestHelper);
%>

<liferay-ui:search-container
	emptyResultsMessage="there-are-no-media-files-in-this-folder"
	searchContainer="<%= igSearchContainer %>"
>
	<liferay-ui:search-container-row
		className="Object"
		modelVar="result"
	>

		<%@ include file="/document_library/cast_result.jspf" %>

		<c:choose>
			<c:when test="<%= fileEntry != null %>">

				<%
				String thumbnailId = null;

				if (fileShortcut != null) {
					thumbnailId = "shortcut_" + fileShortcut.getFileShortcutId();
				}
				else {
					thumbnailId = "entry_" + fileEntry.getFileEntryId();
				}

				FileVersion fileVersion = fileEntry.getFileVersion();

				boolean hasAudio = AudioProcessorUtil.hasAudio(fileVersion);
				boolean hasImages = ImageProcessorUtil.hasImages(fileVersion);
				boolean hasPDFImages = PDFProcessorUtil.hasImages(fileVersion);
				boolean hasVideo = VideoProcessorUtil.hasVideo(fileVersion);

				String href = themeDisplay.getPathThemeImages() + "/file_system/large/" + DLUtil.getGenericName(fileEntry.getExtension()) + ".png";
				String src = DLUtil.getThumbnailSrc(fileEntry, fileVersion, themeDisplay);

				int playerHeight = 500;

				String dataOptions = StringPool.BLANK;

				if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED) {
					if (hasAudio) {
						href = DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, HtmlUtil.escapeURL("&audioPreview=1") + "&supportedAudio=1&mediaGallery=1");

						for (String audioContainer : PropsValues.DL_FILE_ENTRY_PREVIEW_AUDIO_CONTAINERS) {
							dataOptions += "&" + audioContainer + "PreviewURL=" + HtmlUtil.escapeURL(DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&supportedAudio=1&audioPreview=1&type=" + audioContainer));
						}

						playerHeight = 43;
					}
					else if (hasImages) {
						href = DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&imagePreview=1");
					}
					else if (hasPDFImages) {
						href = DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&previewFileIndex=1");
					}
					else if (hasVideo) {
						href = DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&supportedVideo=1&mediaGallery=1");

						for (String videoContainer : PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_CONTAINERS) {
							dataOptions += "&" + videoContainer + "PreviewURL=" + HtmlUtil.escapeURL(DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&supportedVideo=1&videoPreview=1&type=" + videoContainer));
						}
					}
				}

				row.setClassName("col-md-3 col-sm-4 col-xs-6");
				%>

				<liferay-ui:search-container-column-text>
					<div class="image-link preview" <%= (hasAudio || hasVideo) ? "data-options=\"height=" + playerHeight + "&thumbnailURL=" + HtmlUtil.escapeURL(DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&videoThumbnail=1")) + "&width=640" + dataOptions + "\"" : StringPool.BLANK %> href="<%= href %>" thumbnailId="<%= thumbnailId %>" title="<%= HtmlUtil.escapeAttribute(fileEntry.getTitle()) %>">
						<liferay-frontend:vertical-card
							actionJsp='<%= dlPortletInstanceSettingsHelper.isShowActions() ? "/image_gallery_display/image_action.jsp" : StringPool.BLANK %>'
							actionJspServletContext="<%= application %>"
							cssClass="entry-display-style"
							imageUrl="<%= href %>"
							resultRow="<%= row %>"
							title="<%= fileEntry.getTitle() %>"
						>

							<%
							List assetTags = AssetTagServiceUtil.getTags(DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());
							%>

							<liferay-frontend:vertical-card-footer>
								<div id="<portlet:namespace />categorizationContainer_<%= fileEntry.getFileEntryId() %>" style="display: none;">
									<span <%= !assetTags.isEmpty() ? "class=\"has-tags\"" : "" %>>
										<liferay-ui:asset-categories-summary
											className="<%= DLFileEntryConstants.getClassName() %>"
											classPK="<%= fileEntry.getFileEntryId() %>"
										/>
									</span>

									<liferay-ui:asset-tags-summary
										className="<%= DLFileEntryConstants.getClassName() %>"
										classPK="<%= fileEntry.getFileEntryId() %>"
									/>
								</div>
							</liferay-frontend:vertical-card-footer>
						</liferay-frontend:vertical-card>
					</div>
				</liferay-ui:search-container-column-text>
			</c:when>
			<c:otherwise>
				<portlet:renderURL var="viewFolderURL">
					<portlet:param name="mvcRenderCommandName" value="/image_gallery_display/view" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
				</portlet:renderURL>

				<%
				row.setCssClass("col-md-4 col-sm-4 col-xs-6");
				%>

				<liferay-ui:search-container-column-text>
					<liferay-frontend:horizontal-card
						actionJsp='<%= dlPortletInstanceSettingsHelper.isShowActions() ? "/document_library/folder_action.jsp" : StringPool.BLANK %>'
						actionJspServletContext="<%= application %>"
						resultRow="<%= row %>"
						text="<%= HtmlUtil.escape(curFolder.getName()) %>"
						url="<%= viewFolderURL %>"
					>
						<liferay-frontend:horizontal-card-col>
							<liferay-frontend:horizontal-card-icon
								icon='<%= curFolder.isMountPoint() ? "repository" : "folder" %>'
							/>
						</liferay-frontend:horizontal-card-col>
					</liferay-frontend:horizontal-card>
				</liferay-ui:search-container-column-text>
			</c:otherwise>
		</c:choose>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator displayStyle="icon" markupView="lexicon" resultRowSplitter="<%= new DLResultRowSplitter() %>" searchContainer="<%= igSearchContainer %>" />
</liferay-ui:search-container>

<%
PortletURL embeddedPlayerURL = renderResponse.createRenderURL();

embeddedPlayerURL.setParameter("mvcPath", "/image_gallery_display/embedded_player.jsp");
embeddedPlayerURL.setWindowState(LiferayWindowState.POP_UP);
%>

<aui:script use="aui-image-viewer,aui-image-viewer-media">
	var viewportRegion = A.getDoc().get('viewportRegion');

	var maxHeight = (viewportRegion.height / 2);
	var maxWidth = (viewportRegion.width / 2);

	new A.ImageViewer(
		{
			after: {
				<c:if test="<%= dlPortletInstanceSettingsHelper.isShowActions() %>">
					load: function(event) {
						var instance = this;

						var currentLink = instance.getCurrentLink();

						var thumbnailId = currentLink.attr('thumbnailId');

						var actions = instance._actions;

						if (actions) {
							var defaultAction = A.one('#<portlet:namespace />buttonsContainer_' + thumbnailId);

							actions.empty();

							var action = defaultAction.clone().show();

							actions.append(action);
						}
					}
				</c:if>
			},
			delay: 5000,
			infoTemplate: '<%= LanguageUtil.format(request, "image-x-of-x", new String[] {"{current}", "{total}"}, false) %>',
			links: '#<portlet:namespace />imageGalleryAssetInfo .image-link.preview',
			maxHeight: maxHeight,
			maxWidth: maxWidth,
			playingLabel: '(<liferay-ui:message key="playing" />)',
			plugins: [
				{
					cfg: {
						'providers.liferay': {
							container: '<iframe frameborder="0" height="{height}" scrolling="no" src="<%= embeddedPlayerURL.toString() %>&<portlet:namespace />thumbnailURL={thumbnailURL}&<portlet:namespace />mp3PreviewURL={mp3PreviewURL}&<portlet:namespace />mp4PreviewURL={mp4PreviewURL}&<portlet:namespace />oggPreviewURL={oggPreviewURL}&<portlet:namespace />ogvPreviewURL={ogvPreviewURL}" width="{width}"></iframe>',
							matcher: /(.+)&mediaGallery=1/,
							mediaRegex: /(.+)&mediaGallery=1/,
							options: A.merge(
								A.MediaViewerPlugin.DEFAULT_OPTIONS,
								{
									'mp3PreviewURL': '',
									'mp4PreviewURL': '',
									'oggPreviewURL': '',
									'ogvPreviewURL': '',
									'thumbnailURL': ''
								}
							)
						}
					},
					fn: A.MediaViewerPlugin
				}
			],
			zIndex: ++Liferay.zIndex.WINDOW
		}
	).render();
</aui:script>