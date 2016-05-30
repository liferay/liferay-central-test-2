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

<%@ include file="/admin/common/init.jsp" %>

<%
KBArticle kbArticle = (KBArticle)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);

KBTemplate kbTemplate = (KBTemplate)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_TEMPLATE);

resourcePrimKey = BeanParamUtil.getLong(kbArticle, request, "resourcePrimKey");

kbFolderClassNameId = PortalUtil.getClassNameId(KBFolderConstants.getClassName());

long parentResourceClassNameId = BeanParamUtil.getLong(kbArticle, request, "parentResourceClassNameId", kbFolderClassNameId);

long parentResourcePrimKey = BeanParamUtil.getLong(kbArticle, request, "parentResourcePrimKey", KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);

String content = BeanParamUtil.getString(kbArticle, request, "content", BeanPropertiesUtil.getString(kbTemplate, "content"));
String[] sections = AdminUtil.unescapeSections(BeanPropertiesUtil.getString(kbArticle, "sections", StringUtil.merge(kbSectionPortletInstanceConfiguration.adminKBArticleSections())));

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);

	renderResponse.setTitle((kbArticle != null) ? kbArticle.getTitle() : LanguageUtil.get(request, "new-article"));
}
%>

<c:if test="<%= !portletTitleBasedNavigation %>">
	<liferay-ui:header
		backURL="<%= redirect %>"
		localizeTitle="<%= (kbArticle == null) %>"
		title='<%= (kbArticle == null) ? "new-article" : kbArticle.getTitle() %>'
	/>
</c:if>

<div <%= portletTitleBasedNavigation ? "class=\"container-fluid-1280\"" : StringPool.BLANK %>>
	<liferay-portlet:actionURL name="updateKBArticle" var="updateKBArticleURL" />

	<aui:form action="<%= updateKBArticleURL %>" method="post" name="fm">
		<aui:input name="mvcPath" type="hidden" value='<%= templatePath + "edit_article.jsp" %>' />
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (kbArticle == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="resourcePrimKey" type="hidden" value="<%= String.valueOf(resourcePrimKey) %>" />
		<aui:input name="parentResourceClassNameId" type="hidden" value="<%= parentResourceClassNameId %>" />
		<aui:input name="parentResourcePrimKey" type="hidden" value="<%= parentResourcePrimKey %>" />
		<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_SAVE_DRAFT %>" />

		<liferay-ui:error exception="<%= DuplicateFileException.class %>" message="please-enter-a-unique-document-name" />
		<liferay-ui:error exception="<%= FileNameException.class %>" message="please-enter-a-file-with-a-valid-file-name" />
		<liferay-ui:error exception="<%= KBArticleUrlTitleException.MustNotBeDuplicate.class %>" message="please-enter-a-unique-friendly-url" />

		<%
		long uploadServletRequestImplMaxSize = PrefsPropsUtil.getLong(PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE);
		%>

		<liferay-ui:error exception="<%= FileSizeException.class %>">

			<%
			long fileMaxSize = PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE);

			if (fileMaxSize == 0) {
				fileMaxSize = uploadServletRequestImplMaxSize;
			}

			fileMaxSize /= 1024;
			%>

			<liferay-ui:message arguments="<%= fileMaxSize %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= KBArticleUrlTitleException.MustNotContainInvalidCharacters.class %>" message="please-enter-a-friendly-url-that-starts-with-a-slash-and-contains-alphanumeric-characters-dashes-and-underscores" />

		<liferay-ui:error exception="<%= KBArticleUrlTitleException.MustNotExceedMaximumSize.class %>">

			<%
			int friendlyURLMaxLength = ModelHintsUtil.getMaxLength(KBArticle.class.getName(), "urlTitle");
			%>

			<liferay-ui:message arguments="<%= String.valueOf(friendlyURLMaxLength) %>" key="please-enter-a-friendly-url-with-fewer-than-x-characters" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= KBArticleContentException.class %>">
			<liferay-ui:message arguments='<%= ModelHintsUtil.getMaxLength(KBArticle.class.getName(), "urlTitle") %>' key="please-enter-valid-content" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= KBArticleSourceURLException.class %>" message="please-enter-a-valid-source-url" />
		<liferay-ui:error exception="<%= KBArticleTitleException.class %>" message="please-enter-a-valid-title" />
		<liferay-ui:error exception="<%= NoSuchFileException.class %>" message="the-document-could-not-be-found" />

		<liferay-ui:error exception="<%= UploadRequestSizeException.class %>">
			<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(uploadServletRequestImplMaxSize, locale) %>" key="request-is-larger-than-x-and-could-not-be-processed" translateArguments="<%= false %>" />
		</liferay-ui:error>

		<liferay-ui:asset-categories-error />

		<liferay-ui:asset-tags-error />

		<c:choose>
			<c:when test="<%= (kbArticle != null) && kbArticle.isApproved() %>">
				<div class="alert alert-info">
					<liferay-ui:message key="a-new-version-is-created-automatically-if-this-content-is-modified" />
				</div>
			</c:when>
			<c:when test="<%= (kbArticle != null) && kbArticle.isPending() %>">
				<div class="alert alert-info">
					<liferay-ui:message key="there-is-a-publication-workflow-in-process" />
				</div>
			</c:when>
		</c:choose>

		<aui:model-context bean="<%= kbArticle %>" model="<%= KBArticle.class %>" />

		<c:if test="<%= kbArticle != null %>">
			<aui:workflow-status id="<%= String.valueOf(resourcePrimKey) %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= kbArticle.getStatus() %>" version="<%= String.valueOf(kbArticle.getVersion()) %>" />
		</c:if>

		<aui:fieldset>
			<aui:input name="title" required="<%= true %>" />

			<aui:field-wrapper cssClass="input-append input-flex-add-on input-prepend" helpMessage='<%= LanguageUtil.format(request, "for-example-x", "<em>/introduction-to-service-builder</em>") %>' label="friendly-url">

				<%
				StringBundler sb = new StringBundler();

				sb.append("/-/");

				Portlet portlet = PortletLocalServiceUtil.getPortletById(KBPortletKeys.KNOWLEDGE_BASE_DISPLAY);

				sb.append(portlet.getFriendlyURLMapping());

				long kbFolderId = KnowledgeBaseUtil.getKBFolderId(parentResourceClassNameId, parentResourcePrimKey);

				if (kbFolderId != KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
					KBFolder kbFolder = KBFolderLocalServiceUtil.getKBFolder(kbFolderId);

					sb.append(StringPool.SLASH);
					sb.append(kbFolder.getUrlTitle());
				}
				%>

				<span class="input-group-addon" id="<portlet:namespace />urlBase"><liferay-ui:message key="<%= StringUtil.shorten(sb.toString(), 40) %>" /></span>

				<aui:input cssClass="input-medium" disabled="<%= kbArticle != null %>" label="" name="urlTitle" placeholder="/sample-article-url-title" value="<%= (kbArticle == null ? StringPool.BLANK : (StringPool.SLASH + kbArticle.getUrlTitle())) %>" />
			</aui:field-wrapper>

			<aui:field-wrapper label="content" required="<%= true %>">
				<liferay-ui:input-editor width="100%" />

				<aui:input name="content" type="hidden" />
			</aui:field-wrapper>

			<c:if test="<%= enableKBArticleDescription %>">
				<aui:input name="description" />
			</c:if>

			<c:if test="<%= kbGroupServiceConfiguration.sourceURLEnabled() %>">
				<aui:input label="source-url" name="sourceURL" />
			</c:if>

			<c:if test="<%= ArrayUtil.isNotEmpty(kbSectionPortletInstanceConfiguration.adminKBArticleSections()) && (parentResourceClassNameId == kbFolderClassNameId) %>">
				<aui:model-context bean="<%= null %>" model="<%= KBArticle.class %>" />

				<aui:select ignoreRequestValue="<%= true %>" multiple="<%= true %>" name="sections">

					<%
					Map<String, String> sectionsMap = new TreeMap<String, String>();

					for (String section : kbSectionPortletInstanceConfiguration.adminKBArticleSections()) {
						sectionsMap.put(LanguageUtil.get(request, section), section);
					}

					for (Map.Entry<String, String> entry : sectionsMap.entrySet()) {
					%>

						<aui:option label="<%= entry.getKey() %>" selected="<%= ArrayUtil.contains(sections, entry.getValue()) %>" value="<%= entry.getValue() %>" />

					<%
					}
					%>

				</aui:select>

				<aui:model-context bean="<%= kbArticle %>" model="<%= KBArticle.class %>" />
			</c:if>

			<c:if test="<%= kbArticle == null %>">
				<aui:field-wrapper cssClass='<%= (parentResourcePrimKey != KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) ? "hide" : StringPool.BLANK %>' label="permissions">
					<liferay-ui:input-permissions
						modelName="<%= KBArticle.class.getName() %>"
					/>
				</aui:field-wrapper>
			</c:if>

			<liferay-ui:panel collapsible="<%= true %>" defaultState="closed" extended="<%= false %>" persistState="<%= true %>" title="attachments">
				<aui:fieldset>
					<div id="<portlet:namespace />attachments">
						<liferay-util:include page="/admin/common/attachments.jsp" servletContext="<%= application %>" />
					</div>
				</aui:fieldset>
			</liferay-ui:panel>

			<liferay-ui:panel collapsible="<%= true %>" defaultState="closed" extended="<%= false %>" persistState="<%= true %>" title="categorization">
				<aui:fieldset>
					<aui:input classPK="<%= (kbArticle != null) ? kbArticle.getClassPK() : 0 %>" name="categories" type="assetCategories" />

					<aui:input classPK="<%= (kbArticle != null) ? kbArticle.getClassPK() : 0 %>" name="tags" type="assetTags" />
				</aui:fieldset>
			</liferay-ui:panel>

			<liferay-ui:panel collapsible="<%= true %>" defaultState="closed" extended="<%= false %>" persistState="<%= true %>" title="related-assets">
				<liferay-ui:input-asset-links
					className="<%= KBArticle.class.getName() %>"
					classPK="<%= (kbArticle == null) ? KBArticleConstants.DEFAULT_PARENT_RESOURCE_PRIM_KEY : kbArticle.getClassPK() %>"
				/>
			</liferay-ui:panel>

			<aui:button-row cssClass="kb-submit-buttons">
				<c:if test="<%= (kbArticle == null) || !kbArticle.isPending() %>">
					<aui:button name="publish" type="submit" value='<%= WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, KBArticle.class.getName()) ? "submit-for-publication" : "publish" %>' />
				</c:if>

				<aui:button primary="<%= false %>" type="submit" value='<%= (kbArticle == null) || kbArticle.isApproved() || kbArticle.isDraft() ? "save-as-draft" : "save" %>' />

				<aui:button href="<%= redirect %>" type="cancel" />
			</aui:button-row>
		</aui:fieldset>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />initEditor() {
		return '<%= UnicodeFormatter.toString(content) %>';
	}
</aui:script>

<aui:script use="aui-base,event-input">
	<c:if test="<%= kbArticle == null %>">
		var titleInput = A.one('#<portlet:namespace />title');
		var urlTitleInput = A.one('#<portlet:namespace />urlTitle');

		var urlTitleCustomized = false;

		titleInput.on(
			'input',
			function(event) {
				if (!urlTitleCustomized) {
					var urlTitle = titleInput.val();

					urlTitle = urlTitle.replace(/[^a-zA-Z0-9_-]/g, '-');

					if (urlTitle[0] === '-') {
						urlTitle = urlTitle.replace(/^-+/, '');
					}

					urlTitle = urlTitle.replace(/--+/g, '-');

					urlTitleInput.val('/' + urlTitle.toLowerCase());
				}
			}
		);

		urlTitleInput.on(
			'input',
			function() {
				urlTitleCustomized = true;
			}
		);
	</c:if>

	var form = A.one('#<portlet:namespace />fm');

	var publishButton = form.one('#<portlet:namespace />publish');

	if (publishButton) {
		publishButton.on(
			'click',
			function() {
				var workflowActionInput = form.one('#<portlet:namespace />workflowAction');

				if (workflowActionInput) {
					workflowActionInput.val('<%= WorkflowConstants.ACTION_PUBLISH %>');
				}
			}
		);
	}

	form.on(
		'submit',
		function() {
			var contentInput = form.one('#<portlet:namespace />content');

			if (contentInput) {
				contentInput.val(<portlet:namespace />editor.getHTML());
			}

			updateMultipleKBArticleAttachments();
		}
	);

	var updateMultipleKBArticleAttachments = function() {
		var Lang = A.Lang;

		var selectedFileNameContainer = A.one('#<portlet:namespace />selectedFileNameContainer');

		var TPL_INPUT = '<input id="<portlet:namespace />selectedFileName{id}" name="<portlet:namespace />selectedFileName" type="hidden" value="{value}" />';

		var values = A.all('input[name=<portlet:namespace />selectUploadedFile]:checked').val();

		var buffer = [];

		for (var i = 0; i < values.length; i++) {
			buffer[i] = Lang.sub(
				TPL_INPUT,
				{
					id: i,
					value: values[i]
				}
			);
		}

		selectedFileNameContainer.html(buffer.join(''));
	};
</aui:script>