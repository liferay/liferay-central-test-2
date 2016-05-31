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

<%@ include file="/display/init.jsp" %>

<%
kbDisplayPortletInstanceConfiguration = ParameterMapUtil.setParameterMap(KBDisplayPortletInstanceConfiguration.class, kbDisplayPortletInstanceConfiguration, request.getParameterMap(), "preferences--", "--");

String tabs2 = ParamUtil.getString(request, "tabs2", Objects.equals(portletResource, KBPortletKeys.KNOWLEDGE_BASE_ARTICLE_DEFAULT_INSTANCE) ? "display-settings" : "general");

String tabs2Names = Objects.equals(portletResource, KBPortletKeys.KNOWLEDGE_BASE_ARTICLE_DEFAULT_INSTANCE) ? "display-settings" : "general,display-settings";
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL">
	<portlet:param name="tabs2" value="<%= tabs2 %>" />
</liferay-portlet:renderURL>

<liferay-ui:tabs
	names="<%= tabs2Names %>"
	param="tabs2"
	url="<%= configurationRenderURL %>"
/>

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />

	<%
	resourceClassNameId = kbDisplayPortletInstanceConfiguration.resourceClassNameId();

	if (resourceClassNameId == 0) {
		resourceClassNameId = kbFolderClassNameId;
	}
	%>

	<aui:input name="preferences--resourceClassNameId--" type="hidden" value="<%= resourceClassNameId %>" />
	<aui:input name="preferences--resourcePrimKey--" type="hidden" value="<%= kbDisplayPortletInstanceConfiguration.resourcePrimKey() %>" />

	<aui:fieldset>
		<c:choose>
			<c:when test='<%= tabs2.equals("general") %>'>
				<div class="input-append kb-field-wrapper">
					<aui:field-wrapper label="article-or-folder">

						<%
						String title = StringPool.BLANK;

						if (resourceClassNameId != kbFolderClassNameId) {
							KBArticle kbArticle = KBArticleLocalServiceUtil.fetchLatestKBArticle(kbDisplayPortletInstanceConfiguration.resourcePrimKey(), WorkflowConstants.STATUS_APPROVED);

							if (kbArticle != null) {
								title = kbArticle.getTitle();
							}
						}
						else {
							KBFolder kbFolder = KBFolderLocalServiceUtil.fetchKBFolder(kbDisplayPortletInstanceConfiguration.resourcePrimKey());

							if (kbFolder != null) {
								title = kbFolder.getName();
							}
						}
						%>

						<liferay-ui:input-resource id="configurationKBObject" url="<%= title %>" />

						<aui:button name="selectKBObjectButton" value="select" />
					</aui:field-wrapper>
				</div>
			</c:when>
			<c:when test='<%= tabs2.equals("display-settings") %>'>
				<aui:field-wrapper cssClass="kb-field-wrapper">
					<aui:input label="enable-description" name="preferences--enableKBArticleDescription--" type="checkbox" value="<%= kbDisplayPortletInstanceConfiguration.enableKBArticleDescription() %>" />

					<aui:input label="enable-ratings" name="preferences--enableKBArticleRatings--" type="checkbox" value="<%= kbDisplayPortletInstanceConfiguration.enableKBArticleRatings() %>" />

					<aui:input label="show-asset-entries" name="preferences--showKBArticleAssetEntries--" type="checkbox" value="<%= kbDisplayPortletInstanceConfiguration.showKBArticleAssetEntries() %>" />

					<aui:input label="show-attachments" name="preferences--showKBArticleAttachments--" type="checkbox" value="<%= kbDisplayPortletInstanceConfiguration.showKBArticleAttachments() %>" />

					<aui:input label="enable-related-assets" name="preferences--enableKBArticleAssetLinks--" type="checkbox" value="<%= kbDisplayPortletInstanceConfiguration.enableKBArticleAssetLinks() %>" />

					<aui:input label="enable-view-count-increment" name="preferences--enableKBArticleViewCountIncrement--" type="checkbox" value="<%= kbDisplayPortletInstanceConfiguration.enableKBArticleViewCountIncrement() %>" />

					<aui:input label="enable-subscriptions" name="preferences--enableKBArticleSubscriptions--" type="checkbox" value="<%= kbDisplayPortletInstanceConfiguration.enableKBArticleSubscriptions() %>" />

					<aui:input label="enable-history" name="preferences--enableKBArticleHistory--" type="checkbox" value="<%= kbDisplayPortletInstanceConfiguration.enableKBArticleHistory() %>" />

					<aui:input label="enable-print" name="preferences--enableKBArticlePrint--" type="checkbox" value="<%= kbDisplayPortletInstanceConfiguration.enableKBArticlePrint() %>" />

					<liferay-ui:social-bookmarks-settings
						displayPosition="<%= kbDisplayPortletInstanceConfiguration.socialBookmarksDisplayPosition() %>"
						displayStyle="<%= kbDisplayPortletInstanceConfiguration.socialBookmarksDisplayStyle() %>"
						enabled="<%= kbDisplayPortletInstanceConfiguration.enableSocialBookmarks() %>"
						types="<%= kbDisplayPortletInstanceConfiguration.socialBookmarksTypes() %>"
					/>
				</aui:field-wrapper>

				<aui:field-wrapper>
					<aui:input label="content-root-prefix" name="preferences--contentRootPrefix--" type="input" value="<%= kbDisplayPortletInstanceConfiguration.contentRootPrefix() %>" />
				</aui:field-wrapper>
			</c:when>
		</c:choose>

		<aui:button-row cssClass="kb-submit-buttons">
			<aui:button type="submit" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<c:if test='<%= tabs2.equals("general") %>'>
	<aui:script use="aui-base">
		<liferay-portlet:renderURL portletName="<%= portletResource %>" var="selectConfigurationKBObjectURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcPath" value="/display/select_configuration_object.jsp" />
			<portlet:param name="parentResourceClassNameId" value="<%= String.valueOf(kbFolderClassNameId) %>" />
			<portlet:param name="parentResourcePrimKey" value="<%= String.valueOf(KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
		</liferay-portlet:renderURL>

		A.one('#<portlet:namespace />selectKBObjectButton').on(
			'click',
			function(event) {
				Liferay.Util.selectEntity(
					{
						dialog: {
							constrain: true,
							destroyOnHide: true,
							modal: true
						},
						id: '<portlet:namespace />selectConfigurationKBObject',
						title: '<liferay-ui:message key="select-parent" />',
						uri: '<%= selectConfigurationKBObjectURL %>'
					},
					function(event) {
						document.<portlet:namespace />fm.<portlet:namespace />resourceClassNameId.value = event.resourceclassnameid;
						document.<portlet:namespace />fm.<portlet:namespace />resourcePrimKey.value = event.resourceprimkey;
						document.getElementById('<portlet:namespace />configurationKBObject').value = event.title;
					}
				);
			}
		);
	</aui:script>
</c:if>