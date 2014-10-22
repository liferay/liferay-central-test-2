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

<%@ include file="/html/portlet/journal_content/init.jsp" %>

<%
JournalArticle article = journalContentDisplayContext.getArticle();

String ddmTemplateKey = journalContentDisplayContext.getDDMTemplateKey();
%>

<liferay-ui:error exception="<%= NoSuchArticleException.class %>" message="the-web-content-could-not-be-found" />

<div class="alert alert-info">
	<span class="displaying-help-message-holder <%= article == null ? StringPool.BLANK : "hide" %>">
		<liferay-ui:message key="please-select-a-web-content-from-the-list-below" />
	</span>

	<span class="displaying-article-id-holder <%= article == null ? "hide" : StringPool.BLANK %>">
		<liferay-ui:message key="displaying-content" />: <span class="displaying-article-id"><%= article != null ? article.getTitle(locale) : StringPool.BLANK %></span>
	</span>
</div>

<c:if test="<%= article != null %>">

	<%
	List<DDMTemplate> ddmTemplates = journalContentDisplayContext.getDDMTemplates();
	%>

	<c:if test="<%= !ddmTemplates.isEmpty() %>">
		<aui:fieldset>
			<liferay-ui:message key="override-default-template" />

			<liferay-ui:table-iterator
				list="<%= ddmTemplates %>"
				listType="com.liferay.portlet.dynamicdatamapping.model.DDMTemplate"
				rowLength="3"
				rowPadding="30"
			>

				<%
				boolean templateChecked = false;

				if (ddmTemplateKey.equals(tableIteratorObj.getTemplateKey())) {
					templateChecked = true;
				}

				if ((tableIteratorPos.intValue() == 0) && Validator.isNull(ddmTemplateKey)) {
					templateChecked = true;
				}
				%>

				<liferay-portlet:renderURL portletName="<%= PortletKeys.DYNAMIC_DATA_MAPPING %>" var="editTemplateURL">
					<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_template" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="refererPortletName" value="<%= PortletKeys.JOURNAL_CONTENT %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(tableIteratorObj.getGroupId()) %>" />
					<portlet:param name="templateId" value="<%= String.valueOf(tableIteratorObj.getTemplateId()) %>" />
				</liferay-portlet:renderURL>

				<liferay-util:buffer var="linkContent">
					<aui:a href="<%= editTemplateURL %>" id="tableIteratorObjName"><%= HtmlUtil.escape(tableIteratorObj.getName(locale)) %></aui:a>
				</liferay-util:buffer>

				<aui:input checked="<%= templateChecked %>" label="<%= linkContent %>" name="overideTemplateId" onChange='<%= "if (this.checked) {document." + renderResponse.getNamespace() + "fm." + renderResponse.getNamespace() + "ddmTemplateKey.value = this.value;}" %>' type="radio" value="<%= tableIteratorObj.getTemplateKey() %>" />

				<c:if test="<%= tableIteratorObj.isSmallImage() %>">
					<br />

					<img alt="" hspace="0" src="<%= HtmlUtil.escapeAttribute(tableIteratorObj.getTemplateImageURL(themeDisplay)) %>" vspace="0" />
				</c:if>
			</liferay-ui:table-iterator>

			<br />
		</aui:fieldset>
	</c:if>
</c:if>

<aui:button name="webContentSelector" value="select-web-content" />

<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="true" varImpl="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />
	<aui:input name="preferences--assetEntryId--" type="hidden" value="<%= journalContentDisplayContext.getAssetEntryId() %>" />
	<aui:input name="preferences--ddmTemplateKey--" type="hidden" value="<%= ddmTemplateKey %>" />
	<aui:input name="preferences--extensions--" type="hidden" value="<%= journalContentDisplayContext.getExtensions() %>" />

	<aui:fieldset>
		<aui:input name="portletId" type="resource" value="<%= journalContentDisplayContext.getPortletResource() %>" />
	</aui:fieldset>

	<aui:fieldset>
		<aui:field-wrapper>
			<aui:input name="preferences--showAvailableLocales--" type="checkbox" value="<%= journalContentDisplayContext.isShowAvailableLocales() %>" />
		</aui:field-wrapper>

		<aui:field-wrapper helpMessage='<%= !journalContentDisplayContext.isOpenOfficeServerEnabled() ? "enabling-openoffice-integration-provides-document-conversion-functionality" : StringPool.BLANK %>' label="enable-conversion-to">
			<liferay-ui:input-move-boxes
				leftBoxName="currentExtensions"
				leftList="<%= journalContentDisplayContext.getCurrentExtensions() %>"
				leftReorder="true"
				leftTitle="current"
				rightBoxName="availableExtensions"
				rightList="<%= journalContentDisplayContext.getAvailableExtensions() %>"
				rightTitle="available"
			/>
		</aui:field-wrapper>

		<aui:field-wrapper>
			<aui:input name="preferences--enablePrint--" type="checkbox" value="<%= journalContentDisplayContext.isEnablePrint() %>" />

			<aui:input name="preferences--enableRelatedAssets--" type="checkbox" value="<%= journalContentDisplayContext.isEnableRelatedAssets() %>" />

			<aui:input name="preferences--enableRatings--" type="checkbox" value="<%= journalContentDisplayContext.isEnableRatings() %>" />

			<c:if test="<%= PropsValues.JOURNAL_ARTICLE_COMMENTS_ENABLED %>">
				<aui:input name="preferences--enableComments--" type="checkbox" value="<%= journalContentDisplayContext.isEnableComments() %>" />

				<aui:input name="preferences--enableCommentRatings--" type="checkbox" value="<%= journalContentDisplayContext.isEnableCommentRatings() %>" />
			</c:if>

			<aui:input name="preferences--enableViewCountIncrement--" type="checkbox" value="<%= journalContentDisplayContext.isEnableViewCountIncrement() %>" />
		</aui:field-wrapper>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base">
	A.one('#<portlet:namespace />webContentSelector').on(
		'click',
		function(event) {
	        event.preventDefault();

			var currentTarget = event.currentTarget;

			<liferay-portlet:renderURL portletName="<%= PortletKeys.ASSET_BROWSER %>" refererPlid="<%= PortalUtil.getControlPanelPlid(company.getCompanyId()) %>" var="selectWebContentURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="struts_action" value="/asset_browser/view" />
				<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
				<portlet:param name="selectedGroupIds" value="<%= StringUtil.merge(PortalUtil.getSharedContentSiteGroupIds(company.getCompanyId(), scopeGroupId, user.getUserId())) %>" />
				<portlet:param name="eventName" value="selectContent" />
				<portlet:param name="typeSelection" value="<%= JournalArticle.class.getName() %>" />
			</liferay-portlet:renderURL>

			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						destroyOnHide: true,
						modal: true
					},
					eventName: 'selectContent',
					id: 'selectContent',
					title: '<liferay-ui:message key="select-web-content" />',
					uri: '<%= selectWebContentURL %>'
				},
				function(event) {
					document.<portlet:namespace />fm.<portlet:namespace />assetEntryId.value = event.assetentryid;
					document.<portlet:namespace />fm.<portlet:namespace />ddmTemplateKey.value = '';

					A.one('.displaying-article-id-holder').show();
					A.one('.displaying-help-message-holder').hide();

					var displayArticleId = A.one('.displaying-article-id');

					displayArticleId.html(event.assettitle + ' (<liferay-ui:message key="modified" />)');

					displayArticleId.addClass('modified');
				}
			);
		}
	);

	Liferay.provide(
		window,
		'<portlet:namespace />saveConfiguration',
		function() {
			document.<portlet:namespace />fm.<portlet:namespace />extensions.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentExtensions);

			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);
</aui:script>