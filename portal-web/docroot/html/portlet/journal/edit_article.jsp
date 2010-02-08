<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String portletResource = ParamUtil.getString(request, "portletResource");

String tabs2 = ParamUtil.getString(request, "tabs2");

String redirect = ParamUtil.getString(request, "redirect");

// Make sure the redirect is correct. This is a workaround for a layout that
// has both the Journal and Journal Content portlets and the user edits an
// article through the Journal Content portlet and then hits cancel.

/*if (redirect.indexOf("p_p_id=" + PortletKeys.JOURNAL_CONTENT) != -1) {
	if (layoutTypePortlet.hasPortletId(PortletKeys.JOURNAL)) {
		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setWindowState(WindowState.NORMAL);
		portletURL.setPortletMode(PortletMode.VIEW);

		redirect = portletURL.toString();
	}
}*/

String originalRedirect = ParamUtil.getString(request, "originalRedirect", StringPool.BLANK);

if (originalRedirect.equals(StringPool.BLANK)) {
	originalRedirect = redirect;
}
else {
	redirect = originalRedirect;
}

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

long groupId = BeanParamUtil.getLong(article, request, "groupId", scopeGroupId);

String articleId = BeanParamUtil.getString(article, request, "articleId");
String newArticleId = ParamUtil.getString(request, "newArticleId");
String instanceIdKey = PwdGenerator.KEY1 + PwdGenerator.KEY2 + PwdGenerator.KEY3;

double version = BeanParamUtil.getDouble(article, request, "version", JournalArticleConstants.DEFAULT_VERSION);
boolean incrementVersion = ParamUtil.getBoolean(request, "incrementVersion");

Calendar displayDate = CalendarFactoryUtil.getCalendar(timeZone, locale);

if (article != null) {
	if (article.getDisplayDate() != null) {
		displayDate.setTime(article.getDisplayDate());
	}
}

boolean neverExpire = ParamUtil.getBoolean(request, "neverExpire", true);

Calendar expirationDate = CalendarFactoryUtil.getCalendar(timeZone, locale);

expirationDate.add(Calendar.YEAR, 1);

if (article != null) {
	if (article.getExpirationDate() != null) {
		neverExpire = false;

		expirationDate.setTime(article.getExpirationDate());
	}
}

boolean neverReview = ParamUtil.getBoolean(request, "neverReview", true);

Calendar reviewDate = CalendarFactoryUtil.getCalendar(timeZone, locale);

reviewDate.add(Calendar.MONTH, 9);

if (article != null) {
	if (article.getReviewDate() != null) {
		neverReview = false;

		reviewDate.setTime(article.getReviewDate());
	}
}

String type = BeanParamUtil.getString(article, request, "type", "general");

String structureId = BeanParamUtil.getString(article, request, "structureId");

JournalStructure structure = null;

String parentStructureId = StringPool.BLANK;
String structureName = LanguageUtil.get(pageContext, "default");
String structureDescription = StringPool.BLANK;
String structureXSD = StringPool.BLANK;

if (Validator.isNotNull(structureId)) {
	try {
		structure = JournalStructureLocalServiceUtil.getStructure(groupId, structureId);

		parentStructureId = structure.getParentStructureId();
		structureName = structure.getName();
		structureDescription = structure.getDescription();
		structureXSD = structure.getMergedXsd();
	}
	catch (NoSuchStructureException nsse) {
	}
}

List templates = new ArrayList();

if (structure != null) {
	templates = JournalTemplateLocalServiceUtil.getStructureTemplates(groupId, structureId);
}

String templateId = BeanParamUtil.getString(article, request, "templateId");

if ((structure == null) && Validator.isNotNull(templateId)) {
	JournalTemplate template = null;

	try {
		template = JournalTemplateLocalServiceUtil.getTemplate(groupId, templateId);

		structureId = template.getStructureId();

		structure = JournalStructureLocalServiceUtil.getStructure(groupId, structureId);

		structureName = structure.getName();

		templates = JournalTemplateLocalServiceUtil.getStructureTemplates(groupId, structureId);
	}
	catch (NoSuchTemplateException nste) {
	}
}

String languageId = LanguageUtil.getLanguageId(request);

String defaultLanguageId = ParamUtil.getString(request, "defaultLanguageId");

if (article == null) {
	defaultLanguageId = languageId;
}
else {
	if (Validator.isNull(defaultLanguageId)) {
		defaultLanguageId =	article.getDefaultLocale();
	}
}

Locale defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);

String content = null;

if (article != null) {
	content = ParamUtil.getString(request, "content");

	if (Validator.isNull(content)) {
		content = article.getContent();
	}

	content = JournalArticleImpl.getContentByLocale(content, Validator.isNotNull(structureId), languageId);
}
else {
	content = ParamUtil.getString(request, "content");
}

Document contentDoc = null;

String[] availableLocales = null;

if (Validator.isNotNull(content)) {
	try {
		contentDoc = SAXReaderUtil.read(content);

		Element contentEl = contentDoc.getRootElement();

		availableLocales = StringUtil.split(contentEl.attributeValue("available-locales"));

		if (!ArrayUtil.contains(availableLocales, defaultLanguageId)) {
			availableLocales = ArrayUtil.append(availableLocales, defaultLanguageId);
		}

		if (structure == null) {
			content = contentDoc.getRootElement().element("static-content").getText();
		}
	}
	catch (Exception e) {
		contentDoc = null;
	}
}

boolean disableIncrementVersion = false;

if (PropsValues.JOURNAL_ARTICLE_FORCE_INCREMENT_VERSION) {
	boolean latestVersion = (article == null) || (article != null && JournalArticleLocalServiceUtil.isLatestVersion(article.getGroupId(), articleId, version));

	if (!latestVersion) {
		incrementVersion = true;
		disableIncrementVersion = true;
	}

	if ((article != null) && article.isApproved()) {
		incrementVersion = true;
		disableIncrementVersion = true;
	}
}

boolean smallImage = BeanParamUtil.getBoolean(article, request, "smallImage");
String smallImageURL = BeanParamUtil.getString(article, request, "smallImageURL");
%>

<aui:form enctype="multipart/form-data" method="post" name="fm2">
	<input name="groupId" type="hidden" value="" />
	<input name="articleId" type="hidden" value="" />
	<input name="version" type="hidden" value="" />
	<input name="title" type="hidden" value="" />
	<input name="xml" type="hidden" value="" />
</aui:form>

<portlet:actionURL var="editArticleActionURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
	<portlet:param name="struts_action" value="/journal/edit_article" />
</portlet:actionURL>

<portlet:renderURL var="editArticleRenderURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
	<portlet:param name="struts_action" value="/journal/edit_article" />
</portlet:renderURL>

<aui:form action="<%= editArticleActionURL %>" enctype="multipart/form-data" method="post" name="fm1" onSubmit='<%= renderResponse.getNamespace() + "saveArticle(); return false;" %>'>
	<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="originalRedirect" type="hidden" value="<%= originalRedirect %>" />
	<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="articleId" type="hidden" value="<%= articleId %>" />
	<aui:input name="version" type="hidden" value="<%= version %>" />
	<aui:input name="content" type="hidden" />
	<aui:input name="originalContent" type="hidden" />
	<aui:input name="defaultLocale" type="hidden" value="<%= defaultLanguageId %>" />
	<aui:input name="parentStructureId" type="hidden" value="<%= parentStructureId %>" />
	<aui:input name="articleURL" type="hidden" value="<%= editArticleRenderURL %>" />
	<aui:input name="approve" type="hidden" />
	<aui:input name="saveAndContinue" type="hidden" />
	<aui:input name="deleteArticleIds" type="hidden" value="<%= articleId + EditArticleAction.VERSION_SEPARATOR + version %>" />
	<aui:input name="expireArticleIds" type="hidden" value="<%= articleId + EditArticleAction.VERSION_SEPARATOR + version %>" />

	<liferay-ui:tabs
		names="web-content"
		formName="fm1"
		backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
	/>

	<aui:model-context bean="<%= article %>" model="<%= JournalArticle.class %>" />

	<table class="lfr-table" width="100%">
	<tr>
		<td valign="top">
			<liferay-ui:error exception="<%= ArticleContentException.class %>" message="please-enter-valid-content" />
			<liferay-ui:error exception="<%= ArticleIdException.class %>" message="please-enter-a-valid-id" />
			<liferay-ui:error exception="<%= ArticleTitleException.class %>" message="please-enter-a-valid-name" />
			<liferay-ui:error exception="<%= DuplicateArticleIdException.class %>" message="please-enter-a-unique-id" />
			<liferay-ui:asset-tags-error />

			<table class="lfr-table" id="<portlet:namespace />articleHeaderEdit">
			<tr>
				<td>
					<c:choose>
						<c:when test="<%= article == null %>">
							<c:choose>
								<c:when test="<%= PropsValues.JOURNAL_ARTICLE_FORCE_AUTOGENERATE_ID %>">
									<aui:input name="newArticleId" type="hidden" />
									<aui:input name="autoArticleId" type="hidden" value="<%= true %>" />
								</c:when>
								<c:otherwise>
									<aui:input cssClass="lfr-input-text-container" field="articleId" fieldParam="newArticleId" label="id" name="newArticleId" value="<%= newArticleId %>" />

									<aui:input label="autogenerate-id" name="autoArticleId" type="checkbox" />
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<aui:field-wrapper label="id">
								<%= articleId %>
							</aui:field-wrapper>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>
					<aui:input bean="<%= article %>" model="<%= JournalArticle.class %>" label="name" name="title" />
				</td>
			</tr>
			<tr>
				<td>
					<input name="<portlet:namespace />lastLanguageId" type="hidden" value="<%= languageId %>" />

					<table class="lfr-table">
					<tr>
						<td>
							<aui:select disabled="<%= article == null %>" id="languageIdSelect" label="language" name="languageId">

								<%
								Locale[] locales = LanguageUtil.getAvailableLocales();

								for (int i = 0; i < locales.length; i++) {
								%>

									<aui:option label="<%= locales[i].getDisplayName(locale) %>" selected="<%= languageId.equals(LocaleUtil.toLanguageId(locales[i])) %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

								<%
								}
								%>

							</aui:select>
						</td>
						<td>
							<c:if test="<%= (article != null) && !languageId.equals(defaultLanguageId) %>">
								<aui:button name="removeArticleLocaleButton" onClick='<%= renderResponse.getNamespace() + "removeArticleLocale();" %>' type="button" value="remove" />
							</c:if>
						</td>
						<td>
							<table class="lfr-table">
							<tr>
								<td>
									<aui:select label="default-language" disabled="<%= article == null %>" name="defaultLanguageIdSelect">

										<%
										if ((availableLocales != null) && (availableLocales.length > 0)) {
											boolean wasLanguageId = false;

											for (int i = 0; i < availableLocales.length; i++) {
												if (availableLocales[i].equals(languageId)) {
													wasLanguageId = true;
												}

												Locale availableLocale = LocaleUtil.fromLanguageId(availableLocales[i]);
										%>

												<aui:option label="<%= availableLocale.getDisplayName(availableLocale) %>" selected="<%= availableLocales[i].equals(defaultLanguageId) %>" value="<%= availableLocales[i] %>" />

										<%
											}

											if (!wasLanguageId) {
												Locale languageLocale = LocaleUtil.fromLanguageId(languageId);
										%>

												<aui:option label="<%= languageLocale.getDisplayName(languageLocale) %>" value="<%= languageId %>" />

										<%
											}
										}
										else {
										%>

											<aui:option label="<%= defaultLocale.getDisplayName(defaultLocale) %>" value="<%= defaultLanguageId %>" />

										<%
										}
										%>

									</aui:select>

									<c:if test="<%= article == null %>">
										<aui:input name="defaultLanguageId" type="hidden" value="<%= defaultLanguageId %>" />
									</c:if>
								</td>
							</tr>
							</table>
						</td>
					</tr>
					</table>
				</td>
			</tr>
			</table>

			<br />

			<div class="journal-article-container" id="<portlet:namespace />journalArticleContainer">
				<c:choose>
					<c:when test="<%= structure == null %>">
						<div id="<portlet:namespace />structureTreeWrapper">
							<ul class="structure-tree" id="<portlet:namespace />structureTree">
								<li class="structure-field" dataName="content" dataType="text_area">
									<span class="journal-article-close"></span>

									<span class="folder">
										<div class="field-container">
											<div class="journal-article-move-handler"></div>

											<label class="journal-article-field-label" for="">
												<span>Content</span>
											</label>

											<div class="journal-article-component-container">
												<liferay-ui:input-editor name='<%= renderResponse.getNamespace() + "structure_el_TextAreaField_content" %>' editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" toolbarSet="liferay-article" onChangeMethod='<%= renderResponse.getNamespace() + "editorContentChanged" %>' width="100%" />
											</div>

											<div class="journal-article-required-message portlet-msg-error">
												<liferay-ui:message key="this-field-is-required" />
											</div>

											<div class="journal-article-buttons">
												<input class="edit-button" type="button" value="<liferay-ui:message key="edit-options" />" />

												<input class="repeatable-button" type="button" value="<liferay-ui:message key="repeat" />" />
											</div>
										</div>

										<ul class="folder-droppable"></ul>
									</span>
								</li>
							</ul>
						</div>
					</c:when>
					<c:otherwise>

						<%
						Document xsdDoc = SAXReaderUtil.read(structure.getMergedXsd());

						if (contentDoc != null) {
						%>

							<input name="<portlet:namespace />available_locales" type="hidden" value="<%= HtmlUtil.escapeAttribute(defaultLanguageId) %>" />

							<%
							boolean languageFound = false;

							if ((availableLocales != null) && (availableLocales.length > 0)) {
								for (int i = 0; i < availableLocales.length ; i++) {
									if (!availableLocales[i].equals(defaultLanguageId)) {
							%>

										<input name="<portlet:namespace />available_locales" type="hidden" value="<%= availableLocales[i] %>" />

										<aui:script>
											document.<portlet:namespace />fm1.<portlet:namespace />languageId.options[<portlet:namespace />getChoice('<%= availableLocales[i] %>')].className = 'focused';
										</aui:script>

									<%
									}
									else{
									%>

										<aui:script>
											document.<portlet:namespace />fm1.<portlet:namespace />languageId.options[<portlet:namespace />getChoice('<%= availableLocales[i] %>')].className = 'focused';
										</aui:script>

							<%
									}

									if (availableLocales[i].equals(languageId)) {
										languageFound = true;
									}
								}
							}

							if (!languageFound && !languageId.equals(defaultLanguageId)) {
							%>

								<input name="<portlet:namespace />available_locales" type="hidden" value="<%= languageId %>" />

								<aui:script>
									document.<portlet:namespace />fm1.<portlet:namespace />removeArticleLocaleButton.disabled = true;
								</aui:script>

						<%
							}
						}
						else {
							contentDoc = SAXReaderUtil.createDocument(SAXReaderUtil.createElement("root"));
						%>

							<input name="<portlet:namespace />available_locales" type="hidden" value="<%= HtmlUtil.escapeAttribute(defaultLanguageId) %>" />

						<%
						}
						%>

						<div class="structure-tree-wrapper" id="<portlet:namespace />structureTreeWrapper">
							<ul class="structure-tree" id="<portlet:namespace />structureTree">
								<% _format(groupId, contentDoc.getRootElement(), xsdDoc.getRootElement(), new IntegerWrapper(0), new Integer(-1), pageContext, request); %>
							</ul>
						</div>
					</c:otherwise>
				</c:choose>

				<c:if test="<%= article == null %>">
					<aui:field-wrapper label="permissions">
						<liferay-ui:input-permissions
							modelName="<%= JournalArticle.class.getName() %>"
						/>
					</aui:field-wrapper>
				</c:if>
			</div>

			<br />

			<%
			String abstractId = portletDisplay.getNamespace() + "abstract";
			String abstractTitle = LanguageUtil.get(pageContext, "abstract");
			%>

			<liferay-ui:panel defaultState="closed" extended="<%= false %>" id="<%= abstractId %>" persistState="<%= true %>" title="<%= abstractTitle %>">
				<liferay-ui:error exception="<%= ArticleSmallImageNameException.class %>">

				<%
				String[] imageExtensions = PrefsPropsUtil.getStringArray(PropsKeys.JOURNAL_IMAGE_EXTENSIONS, StringPool.COMMA);
				%>

				<liferay-ui:message key="image-names-must-end-with-one-of-the-following-extensions" /> <%= StringUtil.merge(imageExtensions, ", ") %>.
				</liferay-ui:error>

				<liferay-ui:error exception="<%= ArticleSmallImageSizeException.class %>" message="please-enter-a-small-image-with-a-valid-file-size" />

				<aui:fieldset>
					<aui:input name="description" />

					<aui:input inlineLabel="left" label="use-small-image" name="smallImage" />

					<aui:input label="small-image-url" name="smallImageURL" />

					<span style="font-size: xx-small;">-- <%= LanguageUtil.get(pageContext, "or").toUpperCase() %> --</span>

					<aui:input cssClass="lfr-input-text-container" label="small-image" name="smallFile" type="file" />

					<liferay-ui:custom-attributes-available className="<%= JournalArticle.class.getName() %>">
						<liferay-ui:custom-attribute-list
							className="<%= JournalArticle.class.getName() %>"
							classPK="<%= (article != null) ? article.getResourcePrimKey() : 0 %>"
							editable="<%= true %>"
							label="<%= true %>"
						/>
					</liferay-ui:custom-attributes-available>

				</aui:fieldset>
			</liferay-ui:panel>

			<br />

			<aui:button-row>

				<%
				boolean hasSavePermission = false;

				if (article != null) {
					hasSavePermission = JournalArticlePermission.contains(permissionChecker, article, ActionKeys.UPDATE);
				}
				else {
					hasSavePermission = JournalPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_ARTICLE);
				}
				%>

				<c:if test="<%= hasSavePermission %>">
					<aui:button name="saveArticleBtn" value="save" />

					<aui:button name="saveArticleAndContinueBtn" value="save-and-continue" />

					<%
					boolean showSaveAndApproveButton = false;

					if (JournalPermission.contains(permissionChecker, scopeGroupId, ActionKeys.APPROVE_ARTICLE)) {
						if (article == null) {
							if (!WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(company.getCompanyId(), groupId, JournalArticle.class.getName())) {
								showSaveAndApproveButton = true;
							}
						}
						else if (!article.isApproved() && !WorkflowInstanceLinkLocalServiceUtil.hasWorkflowInstanceLink(company.getCompanyId(), groupId, JournalArticle.class.getName(), article.getResourcePrimKey())) {
							showSaveAndApproveButton = true;
						}
					}
					%>

					<c:if test="<%= showSaveAndApproveButton %>">
						<aui:button name="saveArticleAndApproveBtn" value="save-and-approve" />
					</c:if>
				</c:if>

				<c:if test="<%= Validator.isNotNull(structureId) %>">
					<aui:button name="previewArticleBtn" value="preview" />
				</c:if>

				<c:if test="<%= structure != null %>">
					<aui:button name="downloadArticleContentBtn" value="download" />
				</c:if>

				<aui:button onClick="<%= redirect %>" type="cancel" />
			</aui:button-row>
		</td>

		<td valign="top">
			<%@ include file="edit_article_extra.jspf" %>
		</td>
	</tr>
	</table>
</aui:form>

<%@ include file="edit_article_structure_extra.jspf" %>

<aui:script>
	var <portlet:namespace />documentLibraryInput = null;
	var <portlet:namespace />imageGalleryInput = null;
	var <portlet:namespace />contentChangedFlag = false;

	function <portlet:namespace />changeVersionView(version) {
		location.href = "<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/journal/edit_article" /><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /><portlet:param name="articleId" value="<%= articleId %>" /></liferay-portlet:renderURL>&<portlet:namespace />version=" + version;
	}

	function <portlet:namespace />contentChanged() {
		<portlet:namespace />contentChangedFlag = true;
	}

	function <portlet:namespace />deleteArticle() {
		if (confirm("<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-deactivate-this") %>")) {
			document.<portlet:namespace />fm1.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
			submitForm(document.<portlet:namespace />fm1);
		}
	}

	function <portlet:namespace />disableInputDate(date, checked) {
		document.<portlet:namespace />fm1["<portlet:namespace />" + date + "Month"].disabled = checked;
		document.<portlet:namespace />fm1["<portlet:namespace />" + date + "Day"].disabled = checked;
		document.<portlet:namespace />fm1["<portlet:namespace />" + date + "Year"].disabled = checked;
		document.<portlet:namespace />fm1["<portlet:namespace />" + date + "Hour"].disabled = checked;
		document.<portlet:namespace />fm1["<portlet:namespace />" + date + "Minute"].disabled = checked;
		document.<portlet:namespace />fm1["<portlet:namespace />" + date + "AmPm"].disabled = checked;

		var imageInputIdInput = AUI().one(document.<portlet:namespace />fm1["<portlet:namespace />" + date + "ImageInputIdInput"]);

		if (imageInputIdInput) {
			imageInputIdInput.toggleClass('disabled');
		}
	}

	function <portlet:namespace />editorContentChanged(text) {
		<portlet:namespace />contentChanged();
	}

	function <portlet:namespace />expireArticle() {
		document.<portlet:namespace />fm1.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.EXPIRE %>";
		submitForm(document.<portlet:namespace />fm1);
	}

	function <portlet:namespace />getChoice(value) {
		for (var i = 0; i < document.<portlet:namespace />fm1.<portlet:namespace />languageId.length; i++) {
			if (document.<portlet:namespace />fm1.<portlet:namespace />languageId.options[i].value == value) {
				return document.<portlet:namespace />fm1.<portlet:namespace />languageId.options[i].index;
			}
		}

		return null;
	}

	function <portlet:namespace />getLanguageViewURL(languageId) {
		return "<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/journal/edit_article" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /><portlet:param name="articleId" value="<%= articleId %>" /></liferay-portlet:renderURL>&<portlet:namespace />version=<%= version %>&<portlet:namespace />languageId=" + languageId;
	}

	function <portlet:namespace />getSuggestionsContent() {
		var content = '';

		content += document.<portlet:namespace />fm1.<portlet:namespace/>title.value + ' ';
		content += window.<portlet:namespace />editor.getHTML();

		return content;
	}

	function <portlet:namespace />initEditor() {
		return "<%= UnicodeFormatter.toString(content) %>";
	}

	function <portlet:namespace />removeArticleLocale() {
		if (confirm("<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-deactivate-this-language") %>")) {
			document.<portlet:namespace />fm1.<portlet:namespace /><%= Constants.CMD %>.value = "removeArticlesLocale";
			document.<portlet:namespace />fm1.<portlet:namespace />redirect.value = "<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="struts_action" value="/journal/edit_article" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /><portlet:param name="articleId" value="<%= articleId %>" /><portlet:param name="version" value="<%= String.valueOf(version) %>" /></portlet:renderURL>&<portlet:namespace />languageId=<%= defaultLanguageId %>";
			submitForm(document.<portlet:namespace />fm1);
		}
	}

	function <portlet:namespace />selectDocumentLibrary(url) {
		document.getElementById(<portlet:namespace />documentLibraryInput).value = url;
	}

	function <portlet:namespace />selectImageGallery(url) {
		document.getElementById(<portlet:namespace />imageGalleryInput).value = url;
	}

	function <portlet:namespace />selectStructure(structureId) {
		if (document.<portlet:namespace />fm1.<portlet:namespace />structureId.value != structureId) {
			document.<portlet:namespace />fm1.<portlet:namespace />structureId.value = structureId;
			document.<portlet:namespace />fm1.<portlet:namespace />templateId.value = "";
			submitForm(document.<portlet:namespace />fm1);
		}
	}

	function <portlet:namespace />selectTemplate(structureId, templateId) {
		document.<portlet:namespace />fm1.<portlet:namespace />structureId.value = structureId;
		document.<portlet:namespace />fm1.<portlet:namespace />templateId.value = templateId;
		submitForm(document.<portlet:namespace />fm1);
	}

	Liferay.Util.disableToggleBoxes('<portlet:namespace />autoArticleIdCheckbox','<portlet:namespace />newArticleId', true);

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		<c:choose>
			<c:when test="<%= PropsValues.JOURNAL_ARTICLE_FORCE_AUTOGENERATE_ID %>">
				Liferay.Util.focusFormField(document.<portlet:namespace />fm1.<portlet:namespace />title);
			</c:when>
			<c:otherwise>
				Liferay.Util.focusFormField(document.<portlet:namespace />fm1.<portlet:namespace /><%= (article == null) ? "newArticleId" : "title" %>);
			</c:otherwise>
		</c:choose>
	</c:if>
</aui:script>

<aui:script use="liferay-portlet-journal">

	<%
	String doAsUserId = themeDisplay.getDoAsUserId();

	if (Validator.isNull(doAsUserId)) {
		doAsUserId = Encryptor.encrypt(company.getKeyObj(), String.valueOf(themeDisplay.getUserId()));
	}
	%>

	Liferay.Portlet.Journal.PROXY = {};
	Liferay.Portlet.Journal.PROXY.doAsUserId = '<%= HttpUtil.encodeURL(doAsUserId) %>';
	Liferay.Portlet.Journal.PROXY.editorImpl = '<%= PropsUtil.get(EDITOR_WYSIWYG_IMPL_KEY) %>';
	Liferay.Portlet.Journal.PROXY.pathThemeCss = '<%= HttpUtil.encodeURL(themeDisplay.getPathThemeCss()) %>';
	Liferay.Portlet.Journal.PROXY.portletNamespace = '<portlet:namespace />';

	new Liferay.Portlet.Journal(Liferay.Portlet.Journal.PROXY.portletNamespace, '<%= articleId %>', '<%= instanceIdKey %>');
</aui:script>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.journal.edit_article_content.jsp";

private void _format(long groupId, Element contentParentElement, Element xsdParentElement, IntegerWrapper count, Integer depth, PageContext pageContext, HttpServletRequest request) throws Exception {
	depth = new Integer(depth.intValue() + 1);

	String languageId = LanguageUtil.getLanguageId(request);

	List<Element> xsdElements = xsdParentElement.elements();

	for (Element xsdElement : xsdElements) {
		String nodeName = xsdElement.getName();

		if (nodeName.equals("meta-data") || nodeName.equals("entry")) {
			continue;
		}

		String elName = xsdElement.attributeValue("name", StringPool.BLANK);
		String elType = xsdElement.attributeValue("type", StringPool.BLANK);
		String elIndexType = xsdElement.attributeValue("index-type", StringPool.BLANK);
		String repeatable = xsdElement.attributeValue("repeatable");
		boolean elRepeatable = GetterUtil.getBoolean(repeatable);
		String elParentStructureId = xsdElement.attributeValue("parent-structure-id");

		Map<String, String> elMetaData = _getMetaData(xsdElement, elName);

		List<Element> elSiblings = null;

		List<Element> contentElements = contentParentElement.elements();

		for (Element contentElement : contentElements) {
			if (elName.equals(contentElement.attributeValue("name", StringPool.BLANK))) {
				elSiblings = _getSiblings(contentParentElement, elName);

				break;
			}
		}

		if (elSiblings == null) {
			elSiblings = new ArrayList<Element>();

			Element contentElement = SAXReaderUtil.createElement("dynamic-element");

			contentElement.addAttribute("instance-id", PwdGenerator.getPassword());
			contentElement.addAttribute("name", elName);
			contentElement.addAttribute("type", elType);
			contentElement.addAttribute("index-type", elIndexType);

			contentElement.add(SAXReaderUtil.createElement("dynamic-content"));

			elSiblings.add(contentElement);
		}

		for (int i = 0; i < elSiblings.size(); i++) {
			Element contentElement = elSiblings.get(i);

			String elInstanceId = contentElement.attributeValue("instance-id");

			String elContent = GetterUtil.getString(contentElement.elementText("dynamic-content"));

			if (!elType.equals("text_area")) {
				elContent = HtmlUtil.toInputSafe(elContent);
			}

			String elLanguageId = StringPool.BLANK;

			Element dynamicContentEl = contentElement.element("dynamic-content");

			if (dynamicContentEl != null) {
				elLanguageId = dynamicContentEl.attributeValue("language-id", StringPool.BLANK);
			}
			else {
				elLanguageId = languageId;
			}

			request.setAttribute(WebKeys.JOURNAL_ARTICLE_GROUP_ID, String.valueOf(groupId));

			request.setAttribute(WebKeys.JOURNAL_ARTICLE_CONTENT_EL, contentElement);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL, xsdElement);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_CONTENT, elContent);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_COUNT, count);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_DEPTH, depth);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_INSTANCE_ID, elInstanceId);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_LANGUAGE_ID, elLanguageId);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_META_DATA, elMetaData);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_NAME, elName);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_PARENT_ID, elParentStructureId);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_REPEATABLE, String.valueOf(elRepeatable));
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_REPEATABLE_PROTOTYPE, (i == 0) ? "1" : "0");
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_TYPE, elType);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_INDEX_TYPE, elIndexType);

			pageContext.include("/html/portlet/journal/edit_article_content_xsd_el.jsp");

			count.increment();

			if (!elType.equals("list") && !elType.equals("multi-list") && !contentElement.elements().isEmpty()) {
				pageContext.include("/html/portlet/journal/edit_article_content_xsd_el_top.jsp");

				_format(groupId, contentElement, xsdElement, count, depth, pageContext, request);

				request.setAttribute(WebKeys.JOURNAL_STRUCTURE_CLOSE_DROPPABLE_TAG, Boolean.TRUE.toString());

				pageContext.include("/html/portlet/journal/edit_article_content_xsd_el_bottom.jsp");
			}

			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_CLOSE_DROPPABLE_TAG, Boolean.FALSE.toString());

			pageContext.include("/html/portlet/journal/edit_article_content_xsd_el_bottom.jsp");
		}
	}
}

private Map<String, String> _getMetaData(Element xsdElement, String elName) {
	Map<String, String> elMetaData = new HashMap<String, String>();

	Element metaData = xsdElement.element("meta-data");

	if (Validator.isNotNull(metaData)) {
		List<Element> elMetaDataements = metaData.elements();

		for (Element elMetaDataement : elMetaDataements) {
			String name = elMetaDataement.attributeValue("name");
			String content = elMetaDataement.getText().trim();

			elMetaData.put(name, content);
		}
	}
	else {
		elMetaData.put("label", elName);
	}

	return elMetaData;
}

private List<Element> _getSiblings(Element element, String name) {
	List<Element> elements = new ArrayList<Element>();

	Iterator<Element> itr = element.elements().iterator();

	while (itr.hasNext()) {
		Element curElement = itr.next();

		if (name.equals(curElement.attributeValue("name", StringPool.BLANK))) {
			elements.add(curElement);
		}
	}

	return elements;
}
%>