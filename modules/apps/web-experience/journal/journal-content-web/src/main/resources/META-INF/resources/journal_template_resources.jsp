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

<%@ include file="/init.jsp" %>

<%
JournalArticle article = journalContentDisplayContext.getArticle();
DDMStructure ddmStructure = journalContentDisplayContext.getDDMStructure();
DDMTemplate ddmTemplate = journalContentDisplayContext.getDDMTemplate();
List<DDMTemplate> ddmTemplates = journalContentDisplayContext.getDDMTemplates();
String ddmTemplateImageURL = ddmTemplate.getTemplateImageURL(themeDisplay);
%>

<div class="template-preview-content" data-template-key="<%= ddmTemplate.getTemplateKey() %>">
	<c:choose>
		<c:when test="<%= journalContentDisplayContext.isDefaultTemplate() %>">
			<p class="text-muted"><liferay-ui:message key="web-content's-default-template" /> <liferay-ui:icon-help message="to-change-web-content's-default-template-you-have-to-edit-the-web-content" /></p>
		</c:when>
		<c:otherwise>
			<p class="text-muted"><liferay-ui:message key="web-content-display-template" /></p>
		</c:otherwise>
	</c:choose>

	<liferay-frontend:horizontal-card
		text="<%= ddmTemplate.getName(locale) %>"
	>
		<liferay-frontend:horizontal-card-col>
			<c:choose>
				<c:when test="<%= Validator.isNotNull(ddmTemplateImageURL) %>">
					<img alt="" class="<%= Validator.isNotNull(ddmTemplateImageURL) ? "icon-monospaced" : StringPool.BLANK %>" src="<%= ddmTemplateImageURL %>" />
				</c:when>
				<c:otherwise>
					<liferay-frontend:horizontal-card-icon
						icon="edit-layout"
					/>
				</c:otherwise>
			</c:choose>
		</liferay-frontend:horizontal-card-col>
	</liferay-frontend:horizontal-card>

	<div class="button-holder template-preview-button <%= ddmTemplates.size() > 1 ? StringPool.BLANK : "hidden" %>">
		<aui:button name="templateSelector" value="change" />

		<c:if test="<%= !journalContentDisplayContext.isDefaultTemplate() %>">
			<liferay-ui:alert message='<%= LanguageUtil.get(resourceBundle, "changing-the-template-won't-affect-the-original-web-content-defautl-template.-the-change-only-applies-to-this-web-content-display") %>' timeout="0" type="info" />

			<%
			String taglibOnClick = "Liferay.fire('changeTemplate', {ddmTemplateKey: '" + article.getDDMTemplateKey() + "'});";
			%>

			<liferay-ui:message key="or" /> <aui:a href="javascript:;" label="default-template" onClick="<%= taglibOnClick %>" />
		</c:if>
	</div>
</div>

<aui:script sandbox="<%= true %>">
	$('#<portlet:namespace />templateSelector').on(
		'click',
		function(event) {
			event.preventDefault();

			Liferay.Util.openDDMPortlet(
				{
					basePortletURL: '<%= PortalUtil.getControlPanelPortletURL(request, PortletProviderUtil.getPortletId(DDMStructure.class.getName(), PortletProvider.Action.VIEW), PortletRequest.RENDER_PHASE) %>',
					classNameId: '<%= PortalUtil.getClassNameId(DDMStructure.class) %>',
					classPK: <%= (ddmStructure != null) ? ddmStructure.getPrimaryKey() : 0 %>,
					dialog: {
						destroyOnHide: true
					},
					eventName: 'selectTemplate',
					groupId: <%= (article != null) ? article.getGroupId() : scopeGroupId %>,
					mvcPath: '/select_template.jsp',
					navigationStartsOn: '<%= DDMNavigationHelper.SELECT_TEMPLATE %>',
					refererPortletName: '<%= JournalContentPortletKeys.JOURNAL_CONTENT %>',
					resourceClassNameId: <%= (ddmStructure != null) ? ddmStructure.getClassNameId() : 0 %>,
					showAncestorScopes: true,
					showCacheableInput: true,
					templateId: <%= (ddmTemplate != null) ? ddmTemplate.getTemplateId() : StringPool.BLANK %>,
					title: '<liferay-ui:message key="templates" />'
				},
				function(event) {
					Liferay.fire(
						'changeTemplate',
						{
							ddmTemplateKey: event.ddmtemplatekey
						}
					);
				}
			);
		}
	);
</aui:script>