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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String redirect = (String)request.getAttribute("edit_article.jsp-redirect");

String portletResource = ParamUtil.getString(request, "portletResource");

JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

long groupId = BeanParamUtil.getLong(article, request, "groupId", scopeGroupId);

long classNameId = ParamUtil.getLong(request, "classNameId");
String classPK = ParamUtil.getString(request, "classPK");

String articleId = BeanParamUtil.getString(article, request, "articleId");
String newArticleId = ParamUtil.getString(request, "newArticleId");
String instanceIdKey = PwdGenerator.KEY1 + PwdGenerator.KEY2 + PwdGenerator.KEY3;

DDMStructure ddmStructure = (DDMStructure)request.getAttribute("edit_article.jsp-structure");

String templateId = StringPool.BLANK;

DDMTemplate ddmTemplate = (DDMTemplate)request.getAttribute("edit_article.jsp-template");

if (ddmTemplate != null) {
	templateId = ddmTemplate.getTemplateKey();
}
else {
	List<DDMTemplate> ddmTemplates = new ArrayList<DDMTemplate>();

	ddmTemplates.addAll(DDMTemplateServiceUtil.getTemplates(ddmStructure.getGroupId(), PortalUtil.getClassNameId(DDMStructure.class), ddmStructure.getStructureId()));

	if (groupId != ddmStructure.getGroupId()) {
		ddmTemplates.addAll(DDMTemplateServiceUtil.getTemplates(groupId, PortalUtil.getClassNameId(DDMStructure.class), ddmStructure.getStructureId()));
	}

	if (!ddmTemplates.isEmpty()) {
		ddmTemplate = ddmTemplates.get(0);

		templateId = ddmTemplate.getTemplateKey();
	}
}

String defaultLanguageId = (String)request.getAttribute("edit_article.jsp-defaultLanguageId");
String toLanguageId = (String)request.getAttribute("edit_article.jsp-toLanguageId");
%>

<liferay-ui:error-marker key="errorSection" value="content" />

<aui:model-context bean="<%= article %>" defaultLanguageId="<%= defaultLanguageId %>" model="<%= JournalArticle.class %>" />

<portlet:renderURL var="editArticleRenderPopUpURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="struts_action" value="/journal/edit_article" />
	<portlet:param name="articleId" value="<%= articleId %>" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="structureId" value="<%= ddmStructure.getStructureKey() %>" />
</portlet:renderURL>

<div class="journal-article-body" id="<portlet:namespace />journalArticleBody">
	<div class="journal-article-body-content">
		<liferay-ui:error exception="<%= ArticleContentException.class %>" message="please-enter-valid-content" />
		<liferay-ui:error exception="<%= ArticleIdException.class %>" message="please-enter-a-valid-id" />
		<liferay-ui:error exception="<%= ArticleTitleException.class %>" message="please-enter-a-valid-name" />
		<liferay-ui:error exception="<%= ArticleVersionException.class %>" message="another-user-has-made-changes-since-you-started-editing-please-copy-your-changes-and-try-again" />
		<liferay-ui:error exception="<%= DuplicateArticleIdException.class %>" message="please-enter-a-unique-id" />
		<liferay-ui:error exception="<%= InvalidDDMStructureException.class %>" message="the-structure-you-selected-is-not-valid-for-this-folder" />
		<liferay-ui:error exception="<%= StorageFieldRequiredException.class %>" message="please-fill-out-all-required-fields" />

		<liferay-ui:error exception="<%= LocaleException.class %>">

			<%
			LocaleException le = (LocaleException)errorException;
			%>

			<c:if test="<%= le.getType() == LocaleException.TYPE_CONTENT %>">
				<liferay-ui:message arguments="<%= new String[] {StringUtil.merge(le.getSourceAvailableLocales(), StringPool.COMMA_AND_SPACE), StringUtil.merge(le.getTargetAvailableLocales(), StringPool.COMMA_AND_SPACE)} %>" key="the-default-language-x-does-not-match-the-portal's-available-languages-x" />
			</c:if>
		</liferay-ui:error>

		<div class="journal-article-header-edit" id="<portlet:namespace />articleHeaderEdit">
			<div class="journal-article-header-id">
				<c:if test="<%= (article == null) || article.isNew() %>">
					<c:choose>
						<c:when test="<%= PropsValues.JOURNAL_ARTICLE_FORCE_AUTOGENERATE_ID || (classNameId != JournalArticleConstants.CLASSNAME_ID_DEFAULT) %>">
							<aui:input name="newArticleId" type="hidden" />
							<aui:input name="autoArticleId" type="hidden" value="<%= true %>" />
						</c:when>
						<c:otherwise>
							<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) || windowState.equals(LiferayWindowState.POP_UP) %>" cssClass="lfr-input-text-container" field="articleId" fieldParam="newArticleId" label="id" name="newArticleId" value="<%= newArticleId %>" />

							<aui:input label="autogenerate-id" name="autoArticleId" type="checkbox" />
						</c:otherwise>
					</c:choose>
				</c:if>
			</div>

			<c:if test="<%= Validator.isNull(toLanguageId) %>">
				<div class="article-structure-template-toolbar journal-metadata">
					<span class="alert alert-block hide structure-message" id="<portlet:namespace />structureMessage">
						<liferay-ui:message key="this-structure-has-not-been-saved" />

						<liferay-ui:message arguments='<%= new Object[] {"journal-save-structure-trigger", "#"} %>' key="click-here-to-save-it-now" />
					</span>

					<aui:row>
						<aui:col cssClass="article-structure" width="<%= 50 %>">
							<span class="article-structure-label"><liferay-ui:message key="structure" />:</span>

							<aui:fieldset cssClass="article-structure-toolbar">
								<div class="journal-form-presentation-label">
									<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
									<aui:input name="structureId" type="hidden" value="<%= ddmStructure.getStructureKey() %>" />
									<aui:input name="structureName" type="hidden" value="<%= ddmStructure.getName(locale) %>" />
									<aui:input name="structureDescription" type="hidden" value="<%= ddmStructure.getDescription(locale) %>" />

									<span class="structure-name-label" id="<portlet:namespace />structureNameLabel">
										<c:choose>
											<c:when test="<%= DDMStructurePermission.contains(permissionChecker, ddmStructure, PortletKeys.JOURNAL, ActionKeys.UPDATE) %>">
												<aui:a href="javascript:;" id="editDDMStructure" label="<%= HtmlUtil.escape(ddmStructure.getName(locale)) %>" />
											</c:when>
											<c:otherwise>
												<%= HtmlUtil.escape(ddmStructure.getName(locale)) %>
											</c:otherwise>
										</c:choose>
									</span>

									<c:if test="<%= classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT %>">
										<liferay-ui:icon
											iconCssClass="icon-search"
											label="<%= true %>"
											linkCssClass="btn"
											message="select"
											url='<%= "javascript:" + renderResponse.getNamespace() + "openDDMStructureSelector();" %>'
										/>
									</c:if>
								</div>
							</aui:fieldset>
						</aui:col>

						<aui:col cssClass="article-template" width="<%= 50 %>">
							<span class="article-template-label"><liferay-ui:message key="template" />:</span>

							<aui:fieldset cssClass="article-template-toolbar">
								<div class="journal-form-presentation-label">
									<aui:input name="templateId" type="hidden" value="<%= templateId %>" />

									<span class="template-name-label" id="<portlet:namespace />templateNameLabel">
										<c:if test="<%= (ddmTemplate != null) && ddmTemplate.isSmallImage() %>">
											<img alt="" class="article-template-image" id="<portlet:namespace />templateImage" src="<%= HtmlUtil.escapeAttribute(_getTemplateImage(themeDisplay, ddmTemplate)) %>" />
										</c:if>

										<c:choose>
											<c:when test="<%= (ddmTemplate != null) && DDMTemplatePermission.contains(permissionChecker, ddmTemplate, PortletKeys.JOURNAL, ActionKeys.UPDATE) %>">
												<aui:a href="javascript:;" id="editDDMTemplate" label="<%= HtmlUtil.escape(ddmTemplate.getName(locale)) %>" />
											</c:when>
											<c:otherwise>
												<%= (ddmTemplate != null) ? HtmlUtil.escape(ddmTemplate.getName(locale)) : LanguageUtil.get(pageContext, "none") %>
											</c:otherwise>
										</c:choose>
									</span>

									<liferay-ui:icon
										iconCssClass="icon-search"
										label="<%= true %>"
										linkCssClass="btn"
										message="select"
										url='<%= "javascript:" + renderResponse.getNamespace() + "openDDMTemplateSelector();" %>'
									/>
								</div>
							</aui:fieldset>
						</aui:col>
					</aui:row>
				</div>
			</c:if>

			<div class="article-translation-toolbar journal-metadata">
				<div class="alert alert-info hide" id="<portlet:namespace />translationsMessage">
					<liferay-ui:message key="the-changes-in-your-translations-will-be-available-once-the-content-is-published" />
				</div>

				<div>
					<c:choose>
						<c:when test="<%= Validator.isNull(toLanguageId) %>">
							<span for="<portlet:namespace />defaultLanguageId"><liferay-ui:message key="web-content-default-language" /></span>:

							<span class="lfr-translation-manager-selector nobr">
								<span class="article-default-language lfr-token lfr-token-primary" id="<portlet:namespace />textLanguageId">
									<img alt="<liferay-ui:message key="default-language" />" src='<%= HtmlUtil.escapeAttribute(themeDisplay.getPathThemeImages() + "/language/" + defaultLanguageId + ".png") %>' />

									<%= LocaleUtil.fromLanguageId(defaultLanguageId).getDisplayName(locale) %>
								</span>

								<a href="javascript:;" id="<portlet:namespace />changeLanguageId"><liferay-ui:message key="change" /></a>

								<aui:select cssClass="hide" id="defaultLocale" inlineField="<%= true %>" label="" name="defaultLanguageId" title="default-language">

									<%
									Locale[] locales = LanguageUtil.getAvailableLocales(themeDisplay.getSiteGroupId());

									for (int i = 0; i < locales.length; i++) {
									%>

										<aui:option label="<%= locales[i].getDisplayName(locale) %>" selected="<%= defaultLanguageId.equals(LocaleUtil.toLanguageId(locales[i])) %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

									<%
									}
									%>

								</aui:select>

								<liferay-ui:icon-help message="default-language-help" />
							</span>

							<c:if test="<%= (article != null) && !article.isNew() %>">
								<span class="lfr-translation-manager-add-menu">
									<liferay-ui:icon-menu
										cssClass="add-translations-menu"
										direction="down"
										icon="../aui/plus"
										message='<%= LanguageUtil.get(pageContext, "add-translation") %>'
										showArrow="<%= true %>"
										showWhenSingleIcon="<%= true %>"
									>

										<%
										Locale[] locales = LanguageUtil.getAvailableLocales(themeDisplay.getSiteGroupId());

										for (int i = 0; i < locales.length; i++) {
											String taglibEditArticleURL = HttpUtil.addParameter(editArticleRenderPopUpURL.toString(), renderResponse.getNamespace() + "toLanguageId", LocaleUtil.toLanguageId(locales[i]));
											String taglibEditURL = "javascript:Liferay.Util.openWindow({cache: false, id: '" + renderResponse.getNamespace() + LocaleUtil.toLanguageId(locales[i]) + "', title: '" + HtmlUtil.escapeJS(LanguageUtil.get(pageContext, "web-content-translation")) + "', uri: '" + HtmlUtil.escapeJS(taglibEditArticleURL) + "'});";
										%>

											<liferay-ui:icon
												cssClass='<%= ArrayUtil.contains(article.getAvailableLanguageIds(), LocaleUtil.toLanguageId(locales[i])) ? "hide" : StringPool.BLANK %>'
												id='<%= renderResponse.getNamespace() + "languageId" + LocaleUtil.toLanguageId(locales[i]) %>'
												image='<%= "../language/" + LocaleUtil.toLanguageId(locales[i]) %>'
												message="<%= locales[i].getDisplayName(locale) %>"
												url="<%= taglibEditURL %>"
											/>

										<%
										}
										%>

									</liferay-ui:icon-menu>
								</span>
							</c:if>
						</c:when>
						<c:otherwise>
							<aui:input id="defaultLocale" name="defaultLanguageId" type="hidden" value="<%= defaultLanguageId %>" />
						</c:otherwise>
					</c:choose>
				</div>

				<c:if test="<%= article != null %>">

					<%
					String[] translations = article.getAvailableLanguageIds();
					%>

					<div class='<%= (Validator.isNull(toLanguageId) && (translations.length > 1)) ? "contains-translations" :"" %>' id="<portlet:namespace />availableTranslationContainer">
						<c:choose>
							<c:when test="<%= Validator.isNotNull(toLanguageId) %>">
								<liferay-util:buffer var="languageLabel">
									<%= LocaleUtil.fromLanguageId(toLanguageId).getDisplayName(locale) %>

									<img alt="" src='<%= HtmlUtil.escapeAttribute(themeDisplay.getPathThemeImages() + "/language/" + toLanguageId + ".png") %>' />
								</liferay-util:buffer>

								<%= LanguageUtil.format(pageContext, "translating-web-content-to-x", languageLabel, false) %>

								<aui:input name="toLanguageId" type="hidden" value="<%= toLanguageId %>" />
							</c:when>
							<c:otherwise>
								<span class='available-translations<%= (translations.length > 1) ? "" : " hide" %>' id="<portlet:namespace />availableTranslationsLinks">
									<label><liferay-ui:message key="available-translations" /></label>

										<%
										for (int i = 0; i < translations.length; i++) {
											if (translations[i].equals(defaultLanguageId)) {
												continue;
											}

											String editTranslationURL = HttpUtil.addParameter(editArticleRenderPopUpURL.toString(), renderResponse.getNamespace() + "toLanguageId", translations[i]);
										%>

										<a class="journal-article-translation-<%= translations[i] %> lfr-token" href="javascript:;" onClick="Liferay.Util.openWindow({cache: false, id: '<portlet:namespace /><%= translations[i] %>', title: '<%= UnicodeLanguageUtil.get(pageContext, "web-content-translation") %>', uri: '<%= editTranslationURL %>'});">
											<img alt="" src='<%= themeDisplay.getPathThemeImages() + "/language/" + translations[i] + ".png" %>' />

											<%= LocaleUtil.fromLanguageId(translations[i]).getDisplayName(locale) %>
										</a>

									<%
									}
									%>

								</span>
							</c:otherwise>
						</c:choose>
					</div>
				</c:if>
			</div>
		</div>

		<div class="journal-article-general-fields">
			<aui:input autoFocus="<%= (((article != null) && !article.isNew()) && !PropsValues.JOURNAL_ARTICLE_FORCE_AUTOGENERATE_ID && windowState.equals(WindowState.MAXIMIZED)) || windowState.equals(LiferayWindowState.POP_UP) %>" defaultLanguageId="<%= Validator.isNotNull(toLanguageId) ? toLanguageId : defaultLanguageId %>" languageId="<%= Validator.isNotNull(toLanguageId) ? toLanguageId : defaultLanguageId %>" name="title">
				<c:if test="<%= classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT %>">
					<aui:validator name="required" />
				</c:if>
			</aui:input>
		</div>

		<div class="journal-article-container" id="<portlet:namespace />journalArticleContainer">

			<%
			Fields ddmFields = null;

			if (article != null) {
				String content = null;

				if (Validator.isNotNull(toLanguageId)) {
					content = JournalArticleImpl.getContentByLocale(article.getDocument(), toLanguageId);
				}
				else {
					content = JournalArticleImpl.getContentByLocale(article.getDocument(), defaultLanguageId);
				}

				if (Validator.isNotNull(content)) {
					ddmFields = JournalConverterUtil.getDDMFields(ddmStructure, content);
				}
			}

			String requestedLanguageId = defaultLanguageId;

			if (Validator.isNotNull(toLanguageId)) {
				requestedLanguageId = toLanguageId;
			}
			%>

			<liferay-ddm:html
				checkRequired="<%= classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT %>"
				classNameId="<%= PortalUtil.getClassNameId(DDMStructure.class) %>"
				classPK="<%= ddmStructure.getStructureId() %>"
				fields="<%= ddmFields %>"
				repeatable="<%= Validator.isNull(toLanguageId) %>"
				requestedLocale="<%= LocaleUtil.fromLanguageId(requestedLanguageId) %>"
			/>

			<c:if test="<%= Validator.isNull(toLanguageId) %>">
				<aui:input label="searchable" name="indexable" />
			</c:if>
		</div>
	</div>

	<c:if test="<%= Validator.isNotNull(toLanguageId) %>">
		<aui:input name="structureId" type="hidden" value="<%= ddmStructure.getStructureKey() %>" />
	</c:if>
</div>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />postProcessTranslation',
		function(formDate, cmd, newVersion, newLanguageId, newLanguage, newStatusMessage) {
			var A = AUI();

			document.<portlet:namespace />fm1.<portlet:namespace />formDate.value = formDate;
			document.<portlet:namespace />fm1.<portlet:namespace />version.value = newVersion;

			var taglibWorkflowStatus = A.one('#<portlet:namespace />journalArticleWrapper .taglib-workflow-status');

			var statusNode = taglibWorkflowStatus.one('.workflow-status strong');

			statusNode.html(newStatusMessage);

			var versionNode = taglibWorkflowStatus.one('.workflow-version strong');

			versionNode.html(newVersion);

			var availableTranslationContainer = A.one('#<portlet:namespace />availableTranslationContainer');

			var translationLink = availableTranslationContainer.one('.journal-article-translation-' + newLanguageId);

			if (cmd == '<%= Constants.DELETE_TRANSLATION %>') {
				var availableLocales = A.one('#<portlet:namespace />availableLocales' + newLanguageId);

				if (availableLocales) {
					availableLocales.remove();
				}

				if (translationLink) {
					translationLink.remove();
				}

				A.one('#<portlet:namespace />languageId' + newLanguageId).ancestor('li').show();

				var availableLocales = availableTranslationContainer.all('a.lfr-token');

				if (availableLocales.size() === 0) {
					availableTranslationContainer.removeClass('contains-translations');

					A.one('#<portlet:namespace />availableTranslationsLinks').hide();
					A.one('#<portlet:namespace />translationsMessage').hide();
				}
			}
			else if (!translationLink) {
				var availableTranslationsLinks = A.one('#<portlet:namespace />availableTranslationsLinks');
				var translationsMessage = A.one('#<portlet:namespace />translationsMessage');

				statusNode.replaceClass('workflow-status-approved', 'workflow-status-draft');

				statusNode.html('<%= UnicodeLanguageUtil.get(pageContext, "draft") %>');

				availableTranslationContainer.addClass('contains-translations');

				availableTranslationsLinks.show();
				translationsMessage.show();

				var TPL_TRANSLATION = '<a class="journal-article-translation-{newLanguageId} lfr-token" href="javascript:;"><img alt="" src="<%= themeDisplay.getPathThemeImages() %>/language/{newLanguageId}.png" />{newLanguage}</a>';

				var translationLinkTpl = A.Lang.sub(
					TPL_TRANSLATION,
					{
						newLanguageId: newLanguageId,
						newLanguage: newLanguage
					}
				);

				translationLink = A.Node.create(translationLinkTpl);

				var editTranslationURL = '<%= editArticleRenderPopUpURL %>&<portlet:namespace />toLanguageId=' + newLanguageId;

				translationLink.on(
					'click',
					function(event) {
						Liferay.Util.openWindow(
							{
								id: '<portlet:namespace />' + newLanguageId,
								title: '<%= UnicodeLanguageUtil.get(pageContext, "web-content-translation") %>',
								uri: editTranslationURL
							}
						);
					}
				);

				availableTranslationsLinks.append(translationLink);

				A.one('#<portlet:namespace />languageId' + newLanguageId).ancestor('li').hide();

				var languageInput = A.Node.create('<input name="<portlet:namespace />available_locales" type="hidden" value="' + newLanguageId + '" />');

				A.one('#<portlet:namespace />fm1').append(languageInput);
			}
		},
		['aui-base']
	);

	Liferay.Util.disableToggleBoxes('<portlet:namespace />autoArticleIdCheckbox','<portlet:namespace />newArticleId', true);
</aui:script>

<aui:script>
	function <portlet:namespace />openDDMStructureSelector() {
		Liferay.Util.openDDMPortlet(
			{
				basePortletURL: '<%= PortletURLFactoryUtil.create(request, PortletKeys.DYNAMIC_DATA_MAPPING, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>',
				classPK: <%= ddmStructure.getPrimaryKey() %>,
				dialog: {
					destroyOnHide: true
				},
				eventName: '<portlet:namespace />selectStructure',
				groupId: <%= groupId %>,
				refererPortletName: '<%= PortletKeys.JOURNAL_CONTENT %>',
				showAncestorScopes: true,
				struts_action: '/dynamic_data_mapping/select_structure',
				title: '<%= UnicodeLanguageUtil.get(pageContext, "structures") %>'
			},
			function(event) {
				if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "selecting-a-new-structure-will-change-the-available-input-fields-and-available-templates") %>') && (document.<portlet:namespace />fm1.<portlet:namespace />ddmStructureId.value != event.ddmstructureid)) {
					document.<portlet:namespace />fm1.<portlet:namespace />ddmStructureId.value = event.ddmstructureid;
					document.<portlet:namespace />fm1.<portlet:namespace />structureId.value = event.ddmstructurekey;
					document.<portlet:namespace />fm1.<portlet:namespace />templateId.value = '';

					submitForm(document.<portlet:namespace />fm1, null, false, false);
				}
			}
		);
	}

	function <portlet:namespace />openDDMTemplateSelector() {
		Liferay.Util.openDDMPortlet(
			{
				basePortletURL: '<%= PortletURLFactoryUtil.create(request, PortletKeys.DYNAMIC_DATA_MAPPING, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>',
				classNameId: '<%= PortalUtil.getClassNameId(DDMStructure.class) %>',
				classPK: <%= ddmStructure.getPrimaryKey() %>,
				dialog: {
					destroyOnHide: true
				},
				eventName: '<portlet:namespace />selectTemplate',
				groupId: <%= groupId %>,
				refererPortletName: '<%= PortletKeys.JOURNAL_CONTENT %>',
				showAncestorScopes: true,
				struts_action: '/dynamic_data_mapping/select_template',
				templateId: <%= ddmTemplate.getTemplateId() %>,
				title: '<%= UnicodeLanguageUtil.get(pageContext, "templates") %>'
			},
			function(event) {
				if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "selecting-a-new-template-will-delete-all-unsaved-content") %>')) {
					document.<portlet:namespace />fm1.<portlet:namespace />ddmTemplateId.value = event.ddmtemplateid;

					submitForm(document.<portlet:namespace />fm1, null, false, false);
				}
			}
		);
	}
</aui:script>

<aui:script use="aui-base,aui-dialog-iframe-deprecated,liferay-portlet-journal">
	var changeLink = A.one('#<portlet:namespace />changeLanguageId');
	var languageSelector = A.one('#<portlet:namespace />defaultLocale');
	var textLanguageId = A.one('#<portlet:namespace />textLanguageId');

	if (changeLink) {
		changeLink.on(
			'click',
			function(event) {
				if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "changing-the-default-language-will-delete-all-unsaved-content") %>')) {
					languageSelector.show();
					languageSelector.focus();

					changeLink.hide();
					textLanguageId.hide();
				}
			}
		);
	}

	Liferay.Portlet.Journal.PROXY = {};
	Liferay.Portlet.Journal.PROXY.instanceIdKey = '<%= instanceIdKey %>';
	Liferay.Portlet.Journal.PROXY.pathThemeCss = '<%= HttpUtil.encodeURL(themeDisplay.getPathThemeCss()) %>';
	Liferay.Portlet.Journal.PROXY.portletNamespace = '<portlet:namespace />';

	window.<portlet:namespace />journalPortlet = new Liferay.Portlet.Journal(Liferay.Portlet.Journal.PROXY.portletNamespace, '<%= (article != null) ? HtmlUtil.escape(articleId) : StringPool.BLANK %>');

	var defaultLocaleSelector = A.one('#<portlet:namespace/>defaultLocale');

	if (defaultLocaleSelector) {
		defaultLocaleSelector.on(
			'change',
			function(event) {
				var defaultLanguageId = defaultLocaleSelector.get('value');

				<portlet:renderURL var="updateDefaultLanguageURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
					<portlet:param name="struts_action" value="/journal/edit_article" />
					<portlet:param name="redirect" value="<%= redirect %>" />
					<portlet:param name="portletResource" value="<%= portletResource %>" />
					<portlet:param name="articleId" value="<%= articleId %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
					<portlet:param name="classNameId" value="<%= String.valueOf(classNameId) %>" />
					<portlet:param name="classPK" value="<%= classPK %>" />
					<portlet:param name="structureId" value="<%= ddmStructure.getStructureKey() %>" />
					<portlet:param name="templateId" value="<%= templateId %>" />
				</portlet:renderURL>

				var url = '<%= updateDefaultLanguageURL %>' + '&<portlet:namespace />defaultLanguageId=' + defaultLanguageId;

				window.location.href = url;
			}
		);
	}

	var editDDMTemplate = A.one('#<portlet:namespace />editDDMTemplate');

	if (editDDMTemplate) {
		var windowId = A.guid();

		editDDMTemplate.on(
			'click',
			function(event) {
				if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "editing-the-current-template-will-delete-all-unsaved-content") %>')) {
					Liferay.Util.openWindow(
						{
							id: windowId,
							refreshWindow: window,
							title: '<%= UnicodeLanguageUtil.get(pageContext, "templates") %>',

							<liferay-portlet:renderURL portletName="<%= PortletKeys.DYNAMIC_DATA_MAPPING %>" var="editTemplateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
								<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_template" />
								<portlet:param name="closeRedirect" value="<%= currentURL %>" />
								<portlet:param name="showBackURL" value="<%= Boolean.FALSE.toString() %>" />
								<portlet:param name="refererPortletName" value="<%= PortletKeys.JOURNAL %>" />
								<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
								<portlet:param name="classNameId" value="<%= String.valueOf(classNameId) %>" />
								<portlet:param name="templateId" value="<%= (ddmTemplate != null) ? String.valueOf(ddmTemplate.getTemplateId()) : StringPool.BLANK %>" />
							</liferay-portlet:renderURL>

							uri: '<%= editTemplateURL %>'
						}
					);
				}
			}
		);
	}

	<c:if test="<%= DDMStructurePermission.contains(permissionChecker, ddmStructure, PortletKeys.JOURNAL, ActionKeys.UPDATE) %>">
		var editDDMStructure = A.one('#<portlet:namespace />editDDMStructure');

		if (editDDMStructure) {
			var windowId = A.guid();

			editDDMStructure.on(
				'click',
				function(event) {
					if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "editing-the-current-structure-will-delete-all-unsaved-content") %>')) {
						Liferay.Util.openWindow(
							{
								id: windowId,
								refreshWindow: window,
								title: '<%= UnicodeLanguageUtil.get(pageContext, "structures") %>',

								<liferay-portlet:renderURL portletName="<%= PortletKeys.DYNAMIC_DATA_MAPPING %>" var="editStructureURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
									<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_structure" />
									<portlet:param name="closeRedirect" value="<%= currentURL %>" />
									<portlet:param name="showBackURL" value="<%= Boolean.FALSE.toString() %>" />
									<portlet:param name="refererPortletName" value="<%= PortletKeys.JOURNAL %>" />
									<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
									<portlet:param name="classNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)) %>" />
									<portlet:param name="classPK" value="<%= String.valueOf(ddmStructure.getStructureId()) %>" />
								</liferay-portlet:renderURL>

								uri: '<%= editStructureURL %>'
							}
						);
					}
				}
			);
		}
	</c:if>
</aui:script>

<%!
private String _getTemplateImage(ThemeDisplay themeDisplay, DDMTemplate ddmTemplate) {
	String imageURL = null;

	if (ddmTemplate.isSmallImage()) {
		if (Validator.isNotNull(ddmTemplate.getSmallImageURL())) {
			imageURL = ddmTemplate.getSmallImageURL();
		}
		else {
			imageURL = themeDisplay.getPathImage() + "/journal/template?img_id=" + ddmTemplate.getSmallImageId() + "&t=" + WebServerServletTokenUtil.getToken(ddmTemplate.getSmallImageId());
		}
	}

	return imageURL;
}
%>