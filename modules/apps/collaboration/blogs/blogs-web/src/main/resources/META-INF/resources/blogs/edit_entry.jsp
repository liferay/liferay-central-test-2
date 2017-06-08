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

<%@ include file="/blogs/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

BlogsEntry entry = (BlogsEntry)request.getAttribute(WebKeys.BLOGS_ENTRY);

long entryId = BeanParamUtil.getLong(entry, request, "entryId");

String title = BeanParamUtil.getString(entry, request, "title");
String subtitle = BeanParamUtil.getString(entry, request, "subtitle");
String content = BeanParamUtil.getString(entry, request, "content");
String urlTitle = BeanParamUtil.getString(entry, request, "urlTitle");

String description = BeanParamUtil.getString(entry, request, "description");

boolean customAbstract = ParamUtil.getBoolean(request, "customAbstract", (entry != null) && Validator.isNotNull(entry.getDescription()) ? true : false);

if (!customAbstract) {
	description = StringUtil.shorten(content, pageAbstractLength);
}

boolean allowPingbacks = PropsValues.BLOGS_PINGBACK_ENABLED && BeanParamUtil.getBoolean(entry, request, "allowPingbacks", true);
boolean allowTrackbacks = PropsValues.BLOGS_TRACKBACK_ENABLED && BeanParamUtil.getBoolean(entry, request, "allowTrackbacks", true);
String coverImageCaption = BeanParamUtil.getString(entry, request, "coverImageCaption");
long coverImageFileEntryId = BeanParamUtil.getLong(entry, request, "coverImageFileEntryId");
long smallImageFileEntryId = BeanParamUtil.getLong(entry, request, "smallImageFileEntryId");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation) {
	renderResponse.setTitle((entry != null) ? BlogsEntryUtil.getDisplayTitle(resourceBundle, entry) : LanguageUtil.get(request, "new-blog-entry"));
}
%>

<liferay-util:buffer var="saveStatus">
	<small class="text-capitalize text-muted" id="<portlet:namespace />saveStatus">
		<c:if test="<%= entry != null %>">
			<aui:workflow-status markupView="lexicon" showHelpMessage="<%= false %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= entry.getStatus() %>" />

			<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - entry.getStatusDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
		</c:if>
	</small>
</liferay-util:buffer>

<liferay-util:buffer var="readingTime">
	<c:if test="<%= blogsPortletInstanceConfiguration.enableReadingTime() %>">

		<%
		int readingTimeInMinutes = (entry == null) ? 0 : com.liferay.blogs.web.internal.util.BlogsUtil.getReadingTimeMinutes(entry.getContent());
		%>

		<small class="text-capitalize text-muted" id="<portlet:namespace />readingTime">
			<c:if test="<%= readingTimeInMinutes > 0 %>">
				&nbsp;-&nbsp;
				<liferay-ui:message arguments="<%= readingTimeInMinutes %>" key="x-minutes-read" translateArguments="<%= false %>" />
			</c:if>
		</small>
	</c:if>
</liferay-util:buffer>

<c:if test="<%= portletTitleBasedNavigation %>">
	<liferay-frontend:info-bar fixed="<%= true %>">
		<%= saveStatus %>
		<%= readingTime %>
	</liferay-frontend:info-bar>
</c:if>

<portlet:actionURL name="/blogs/edit_entry" var="editEntryURL" />

<div class="container-fluid-1280 entry-body">
	<aui:form action="<%= editEntryURL %>" cssClass="edit-entry" enctype="multipart/form-data" method="post" name="fm" onSubmit="event.preventDefault();">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
		<aui:input name="entryId" type="hidden" value="<%= entryId %>" />
		<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_PUBLISH %>" />

		<c:if test="<%= !portletTitleBasedNavigation %>">
			<div class="entry-options">
				<div class="status">
					<%= saveStatus %>
					<%= readingTime %>
				</div>
			</div>
		</c:if>

		<div class="lfr-form-content">
			<liferay-ui:error exception="<%= DuplicateFriendlyURLEntryException.class %>" message="the-url-title-is-already-in-use-please-enter-a-unique-url-title" />
			<liferay-ui:error exception="<%= EntryContentException.class %>" message="please-enter-valid-content" />
			<liferay-ui:error exception="<%= EntryCoverImageCropException.class %>" message="an-error-occurred-while-cropping-the-cover-image" />
			<liferay-ui:error exception="<%= EntryDescriptionException.class %>" message="please-enter-a-valid-abstract" />
			<liferay-ui:error exception="<%= EntryTitleException.class %>" message="please-enter-a-valid-title" />
			<liferay-ui:error exception="<%= EntryUrlTitleException.class %>" message="please-enter-a-valid-url-title" />

			<liferay-ui:error exception="<%= LiferayFileItemException.class %>">
				<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(LiferayFileItem.THRESHOLD_SIZE, locale) %>" key="please-enter-valid-content-with-valid-content-size-no-larger-than-x" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= FileSizeException.class %>">
				<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(DLValidatorUtil.getMaxAllowableSize(), locale) %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= UploadRequestSizeException.class %>">
				<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(UploadServletRequestConfigurationHelperUtil.getMaxSize(), locale) %>" key="request-is-larger-than-x-and-could-not-be-processed" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:asset-categories-error />

			<liferay-ui:asset-tags-error />

			<aui:model-context bean="<%= entry %>" model="<%= BlogsEntry.class %>" />

			<%
			BlogsItemSelectorHelper blogsItemSelectorHelper = (BlogsItemSelectorHelper)request.getAttribute(BlogsWebKeys.BLOGS_ITEM_SELECTOR_HELPER);
			String[] imageExtensions = PrefsPropsUtil.getStringArray(PropsKeys.BLOGS_IMAGE_EXTENSIONS, StringPool.COMMA);
			RequestBackedPortletURLFactory requestBackedPortletURLFactory = RequestBackedPortletURLFactoryUtil.create(liferayPortletRequest);
			%>

			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<portlet:actionURL name="/blogs/upload_cover_image" var="uploadCoverImageURL" />

					<div class="lfr-blogs-cover-image-selector">

						<%
						String coverImageSelectedItemEventName = liferayPortletResponse.getNamespace() + "coverImageSelectedItem";
						%>

						<liferay-item-selector:image-selector draggableImage="vertical" fileEntryId="<%= coverImageFileEntryId %>" itemSelectorEventName="<%= coverImageSelectedItemEventName %>" itemSelectorURL="<%= blogsItemSelectorHelper.getItemSelectorURL(requestBackedPortletURLFactory, themeDisplay, coverImageSelectedItemEventName) %>" maxFileSize="<%= PropsValues.BLOGS_IMAGE_MAX_SIZE %>" paramName="coverImageFileEntry" uploadURL="<%= uploadCoverImageURL %>" validExtensions='<%= StringUtil.merge(imageExtensions, ", ") %>' />
					</div>

					<aui:input name="coverImageCaption" type="hidden" />

					<div class="cover-image-caption <%= (coverImageFileEntryId == 0) ? "invisible" : "" %>">
						<small>
							<liferay-ui:input-editor contents="<%= coverImageCaption %>" editorName="alloyeditor" name="coverImageCaptionEditor" placeholder="caption" showSource="<%= false %>" />
						</small>
					</div>

					<div class="col-md-8 col-md-offset-2">
						<div class="entry-title form-group">
							<h1>
								<liferay-ui:input-editor contents="<%= HtmlUtil.escape(title) %>" editorName="alloyeditor" name="titleEditor" onChangeMethod="OnChangeTitle" placeholder="title" showSource="<%= false %>" />
							</h1>
						</div>

						<aui:input name="title" type="hidden" />

						<div class="entry-subtitle">
							<h4><liferay-ui:input-editor contents="<%= HtmlUtil.escape(subtitle) %>" editorName="alloyeditor" name="subtitleEditor" placeholder="subtitle" showSource="<%= false %>" /> </h4>
						</div>

						<aui:input name="subtitle" type="hidden" />

						<div class="entry-content form-group">
							<liferay-ui:input-editor contents="<%= content %>" editorName='<%= PropsUtil.get("editor.wysiwyg.portal-web.docroot.html.portlet.blogs.edit_entry.jsp") %>' name="contentEditor" onChangeMethod="OnChangeEditor" placeholder="content" required="<%= true %>">
								<aui:validator name="required" />
							</liferay-ui:input-editor>
						</div>

						<aui:input name="content" type="hidden" />
					</div>
				</aui:fieldset>

				<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="categorization">
					<liferay-asset:asset-categories-selector className="<%= BlogsEntry.class.getName() %>" classPK="<%= entryId %>" />

					<liferay-asset:asset-tags-selector className="<%= BlogsEntry.class.getName() %>" classPK="<%= entryId %>" />
				</aui:fieldset>

				<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="related-assets">
					<liferay-ui:input-asset-links
						className="<%= BlogsEntry.class.getName() %>"
						classPK="<%= entryId %>"
					/>
				</aui:fieldset>

				<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="configuration">

					<%
					Portlet portlet = PortletLocalServiceUtil.getPortletById(BlogsPortletKeys.BLOGS);

					String friendlyURLPrefix = StringUtil.shorten("/-/" + portlet.getFriendlyURLMapping(), 40) + StringPool.SLASH;
					%>

					<div class="clearfix form-group">

						<%
						boolean automaticURL;

						if (entry == null) {
							automaticURL = Validator.isNull(urlTitle);
						}
						else {
							String uniqueUrlTitle = BlogsEntryLocalServiceUtil.getUniqueUrlTitle(entry);

							automaticURL = uniqueUrlTitle.equals(urlTitle);
						}
						%>

						<h4>
							<liferay-ui:message key="url" />
						</h4>

						<div class="form-group" id="<portlet:namespace />urlOptions">
							<aui:input checked="<%= automaticURL %>" helpMessage="the-url-will-be-based-on-the-entry-title" label="automatic" name="automaticURL" type="radio" value="<%= true %>" />

							<aui:input checked="<%= !automaticURL %>" label="custom" name="automaticURL" type="radio" value="<%= false %>" />
						</div>

						<aui:input cssClass="input-medium" disabled="<%= automaticURL %>" helpMessage='<%= LanguageUtil.format(resourceBundle, "for-example-x", "<em>one-day-in-the-life-of-marion-cotillard</em>") %>' ignoreRequestValue="<%= true %>" label="blog-entry-url" name="urlTitle" prefix="<%= friendlyURLPrefix %>" type="text" value="<%= urlTitle %>" />
					</div>

					<div class="clearfix form-group">
						<label><liferay-ui:message key="abstract" /> <liferay-ui:icon-help message="an-abstract-is-a-brief-summary-of-a-blog-entry" /></label>

						<liferay-ui:error exception="<%= EntrySmallImageNameException.class %>">
							<liferay-ui:message key="image-names-must-end-with-one-of-the-following-extensions" /> <%= StringUtil.merge(imageExtensions, ", ") %>.
						</liferay-ui:error>

						<liferay-ui:error exception="<%= EntrySmallImageScaleException.class %>">
							<liferay-ui:message key="an-error-occurred-while-scaling-the-abstract-image" />
						</liferay-ui:error>

						<div class="form-group" id="<portlet:namespace />entryAbstractOptions">
							<aui:input checked="<%= !customAbstract %>" label='<%= LanguageUtil.format(request, "use-the-first-x-characters-of-the-entry-content", pageAbstractLength, false) %>' name="customAbstract" type="radio" value="<%= false %>" />

							<aui:input checked="<%= customAbstract %>" label="custom-abstract" name="customAbstract" type="radio" value="<%= true %>" />
						</div>

						<div class="entry-description form-group">
							<aui:input disabled="<%= !customAbstract %>" label="description" name="description" type="text" value="<%= description %>">
								<aui:validator name="required" />
							</aui:input>
						</div>

						<portlet:actionURL name="/blogs/upload_small_image" var="uploadSmallImageURL" />

						<div class="clearfix">
							<label class="control-label"><liferay-ui:message key="small-image" /></label>
						</div>

						<div class="lfr-blogs-small-image-selector">

							<%
							String smallImageSelectedItemEventName = liferayPortletResponse.getNamespace() + "smallImageSelectedItem";
							%>

							<liferay-item-selector:image-selector fileEntryId="<%= smallImageFileEntryId %>" itemSelectorEventName="<%= smallImageSelectedItemEventName %>" itemSelectorURL="<%= blogsItemSelectorHelper.getItemSelectorURL(requestBackedPortletURLFactory, themeDisplay, smallImageSelectedItemEventName) %>" maxFileSize="<%= PropsValues.BLOGS_IMAGE_MAX_SIZE %>" paramName="smallImageFileEntry" uploadURL="<%= uploadSmallImageURL %>" validExtensions='<%= StringUtil.merge(imageExtensions, ", ") %>' />
						</div>
					</div>

					<aui:input label="display-date" name="displayDate" />

					<c:if test="<%= (entry != null) && blogsGroupServiceSettings.isEmailEntryUpdatedEnabled() %>">

						<%
						boolean sendEmailEntryUpdated = ParamUtil.getBoolean(request, "sendEmailEntryUpdated");
						%>

						<aui:input helpMessage="comments-regarding-the-blog-entry-update" label="send-email-entry-updated" name="sendEmailEntryUpdated" type="toggle-switch" value="<%= sendEmailEntryUpdated %>" />

						<%
						String emailEntryUpdatedComment = ParamUtil.getString(request, "emailEntryUpdatedComment");
						%>

						<div id="<portlet:namespace />emailEntryUpdatedCommentWrapper">
							<aui:input label="" name="emailEntryUpdatedComment" type="textarea" value="<%= emailEntryUpdatedComment %>" />
						</div>
					</c:if>

					<c:if test="<%= PropsValues.BLOGS_PINGBACK_ENABLED %>">
						<aui:input helpMessage='<%= LanguageUtil.get(resourceBundle, "a-pingback-is-a-comment-that-is-created-when-you-link-to-another-blog-post-where-pingbacks-are-enabled") + " " + LanguageUtil.get(resourceBundle, "to-allow-pingbacks,-please-also-ensure-the-entry's-guest-view-permission-is-enabled") %>' label="allow-pingbacks" name="allowPingbacks" type="toggle-switch" value="<%= allowPingbacks %>" />
					</c:if>

					<c:if test="<%= PropsValues.BLOGS_TRACKBACK_ENABLED %>">
						<aui:input helpMessage="to-allow-trackbacks,-please-also-ensure-the-entry's-guest-view-permission-is-enabled" label="allow-trackbacks" name="allowTrackbacks" type="toggle-switch" value="<%= allowTrackbacks %>" />

						<aui:input label="trackbacks-to-send" name="trackbacks" />

						<c:if test="<%= (entry != null) && Validator.isNotNull(entry.getTrackbacks()) %>">

							<%
							int i = 0;

							for (String trackback : StringUtil.split(entry.getTrackbacks())) {
							%>

								<aui:input label="" name='<%= "trackback" + (i++) %>' title="" type="resource" value="<%= trackback %>" />

							<%
							}
							%>

						</c:if>
					</c:if>
				</aui:fieldset>

				<liferay-expando:custom-attributes-available className="<%= BlogsEntry.class.getName() %>">
					<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="custom-fields">
						<liferay-expando:custom-attribute-list
							className="<%= BlogsEntry.class.getName() %>"
							classPK="<%= entryId %>"
							editable="<%= true %>"
							label="<%= true %>"
						/>
					</aui:fieldset>
				</liferay-expando:custom-attributes-available>

				<c:if test="<%= (entry == null) || (entry.getStatus() == WorkflowConstants.STATUS_DRAFT) %>">
					<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="permissions">
						<liferay-ui:input-permissions
							modelName="<%= BlogsEntry.class.getName() %>"
						/>
					</aui:fieldset>
				</c:if>
			</aui:fieldset-group>

			<%
			boolean pending = false;

			if (entry != null) {
				pending = entry.isPending();
			}
			%>

			<c:if test="<%= pending %>">
				<div class="alert alert-info">
					<liferay-ui:message key="there-is-a-publication-workflow-in-process" />
				</div>
			</c:if>
		</div>

		<aui:button-row cssClass="blog-article-button-row">

			<%
			String saveButtonLabel = "save";

			if ((entry == null) || entry.isDraft() || entry.isApproved()) {
				saveButtonLabel = "save-as-draft";
			}

			String publishButtonLabel = "publish";

			if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, BlogsEntry.class.getName())) {
				publishButtonLabel = "submit-for-publication";
			}
			%>

			<c:if test="<%= (entry != null) && entry.isApproved() && WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(entry.getCompanyId(), entry.getGroupId(), BlogsEntry.class.getName()) %>">
				<div class="alert alert-info">
					<liferay-ui:message arguments="<%= ResourceActionsUtil.getModelResource(locale, BlogsEntry.class.getName()) %>" key="this-x-is-approved.-publishing-these-changes-will-cause-it-to-be-unpublished-and-go-through-the-approval-process-again" translateArguments="<%= false %>" />
				</div>
			</c:if>

			<aui:button cssClass="btn-lg" disabled="<%= pending %>" name="publishButton" type="submit" value="<%= publishButtonLabel %>" />

			<aui:button cssClass="btn-lg" name="saveButton" primary="<%= false %>" type="submit" value="<%= saveButtonLabel %>" />

			<aui:button cssClass="btn-lg" href="<%= redirect %>" name="cancelButton" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>

<portlet:actionURL name="/blogs/edit_entry" var="editEntryURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<portlet:param name="ajax" value="<%= Boolean.TRUE.toString() %>" />
</portlet:actionURL>

<aui:script>
	function <portlet:namespace />OnChangeEditor(html) {
		var blogs = Liferay.component('<portlet:namespace />Blogs');

		if (blogs) {
			blogs.setDescription(html);

			blogs.updateReadingTime(html);
		}
	}

	function <portlet:namespace />OnChangeTitle(title) {
		var blogs = Liferay.component('<portlet:namespace />Blogs');

		if (blogs) {
			blogs.updateFriendlyURL(title);
		}
	}

	<c:if test="<%= (entry != null) && blogsGroupServiceSettings.isEmailEntryUpdatedEnabled() %>">
		Liferay.Util.toggleBoxes('<portlet:namespace />sendEmailEntryUpdated', '<portlet:namespace />emailEntryUpdatedCommentWrapper');
	</c:if>
</aui:script>

<aui:script use="liferay-blogs">
	var blogs = Liferay.component(
		'<portlet:namespace />Blogs',
		new Liferay.Blogs(
			{
				calculateReadingTimeURL: '<portlet:resourceURL id="/blogs/calculate_reading_time" />',
				constants: {
					'ACTION_PUBLISH': '<%= WorkflowConstants.ACTION_PUBLISH %>',
					'ACTION_SAVE_DRAFT': '<%= WorkflowConstants.ACTION_SAVE_DRAFT %>',
					'ADD': '<%= Constants.ADD %>',
					'CMD': '<%= Constants.CMD %>',
					'STATUS_DRAFT': '<%= WorkflowConstants.STATUS_DRAFT %>',
					'UPDATE': '<%= Constants.UPDATE %>',
					'X_MINUTES_READ': '&nbsp;-&nbsp; <%= UnicodeLanguageUtil.get(resourceBundle, "x-minutes-read") %>'
				},
				descriptionLength: '<%= pageAbstractLength %>',
				editEntryURL: '<%= editEntryURL %>',

				<c:if test="<%= entry != null %>">
					entry: {
						content: '<%= UnicodeFormatter.toString(content) %>',
						customDescription: <%= customAbstract %>,
						description: '<%= UnicodeFormatter.toString(description) %>',
						pending: <%= entry.isPending() %>,
						status: '<%= entry.getStatus() %>',
						subtitle: '<%= UnicodeFormatter.toString(subtitle) %>',
						title: '<%= UnicodeFormatter.toString(title) %>',
						userId: '<%= entry.getUserId() %>'
					},
				</c:if>

				namespace: '<portlet:namespace />'
			}
		)
	);

	var clearSaveDraftHandle = function(event) {
		if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
			blogs.destroy();

			Liferay.detach('destroyPortlet', clearSaveDraftHandle);
		}
	};

	Liferay.on('destroyPortlet', clearSaveDraftHandle);
</aui:script>

<%
if (entry != null) {
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("mvcRenderCommandName", "/blogs/view_entry");
	portletURL.setParameter("entryId", String.valueOf(entry.getEntryId()));

	PortalUtil.addPortletBreadcrumbEntry(request, BlogsEntryUtil.getDisplayTitle(resourceBundle, entry), portletURL.toString());
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-entry"), currentURL);
}
%>