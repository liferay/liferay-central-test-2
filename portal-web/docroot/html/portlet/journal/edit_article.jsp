<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

long groupId = BeanParamUtil.getLong(article, request, "groupId", portletGroupId.longValue());

String articleId = BeanParamUtil.getString(article, request, "articleId");
String newArticleId = ParamUtil.getString(request, "newArticleId");

double version = BeanParamUtil.getDouble(article, request, "version", JournalArticleImpl.DEFAULT_VERSION);
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

String type = BeanParamUtil.getString(article, request, "type");

String structureId = BeanParamUtil.getString(article, request, "structureId");

JournalStructure structure = null;

String structureName = StringPool.BLANK;

if (Validator.isNotNull(structureId)) {
	try {
		structure = JournalStructureLocalServiceUtil.getStructure(groupId, structureId);

		structureName = structure.getName();
	}
	catch (NoSuchStructureException nsse) {
	}
}

List templates = new ArrayList();

if (structure != null) {
	templates = JournalTemplateLocalServiceUtil.getStructureTemplates(groupId, structureId);
}

String templateId = BeanParamUtil.getString(article, request, "templateId");

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
	content = article.getContentByLocale(languageId);
}
else {
	content = ParamUtil.getString(request, "content");
}

boolean disableIncrementVersion = false;

if (GetterUtil.getBoolean(PropsUtil.get(PropsUtil.JOURNAL_ARTICLE_FORCE_INCREMENT_VERSION))) {
	boolean latestVersion = (article == null) || (article != null && JournalArticleLocalServiceUtil.isLatestVersion(themeDisplay.getPortletGroupId(), articleId, version));

	if (!latestVersion) {
		incrementVersion = true;
		disableIncrementVersion = true;
	}

	if ((article != null) && article.isApproved()) {
		incrementVersion = true;
		disableIncrementVersion = true;
	}
}
%>

<script type="text/javascript">
	var <portlet:namespace />count = 0;
	var <portlet:namespace />documentLibraryInput = null;
	var <portlet:namespace />imageGalleryInput = null;
	var <portlet:namespace />contentChangedFlag = false;

	function <portlet:namespace />approveArticle() {
		<portlet:namespace />saveArticle("<%= Constants.APPROVE %>");
	}

	function <portlet:namespace />changeLanguageView() {
		if (<portlet:namespace />contentChangedFlag) {
			if (confirm("<%= UnicodeLanguageUtil.get(pageContext, "would-you-like-to-save-the-changes-made-to-this-language") %>")) {
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.UPDATE %>";
			}
			else {
				if (!confirm("<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-switch-the-languages-view") %>")) {
					var languageIdOptions = document.<portlet:namespace />fm.<portlet:namespace />languageId.options;

					for (var i = 0; i < languageIdOptions.length; i++) {
						if (languageIdOptions[i].value == "<%= languageId %>") {
							languageIdOptions[i].selected = true;
						}
					}

					return;
				}
			}
		}

		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/journal/edit_article" /><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /><portlet:param name="articleId" value="<%= articleId %>" /><portlet:param name="version" value="<%= String.valueOf(version) %>" /></portlet:renderURL>&<portlet:namespace />languageId=" + document.<portlet:namespace />fm.<portlet:namespace />languageId.value;
		document.<portlet:namespace />fm.<portlet:namespace />content.value = <portlet:namespace />getArticleContent();
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />contentChanged() {
		<portlet:namespace />contentChangedFlag = true;
	}

	function <portlet:namespace />deleteArticle() {
		if (confirm("<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-deactivate-this") %>")) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
			submitForm(document.<portlet:namespace />fm);
		}
	}

	function <portlet:namespace />disableInputDate(date, checked) {
		eval("document.<portlet:namespace />fm.<portlet:namespace />" + date + "Month.disabled = " + checked + ";");
		eval("document.<portlet:namespace />fm.<portlet:namespace />" + date + "Day.disabled = " + checked + ";");
		eval("document.<portlet:namespace />fm.<portlet:namespace />" + date + "Year.disabled = " + checked + ";");
		eval("document.<portlet:namespace />fm.<portlet:namespace />" + date + "Hour.disabled = " + checked + ";");
		eval("document.<portlet:namespace />fm.<portlet:namespace />" + date + "Minute.disabled = " + checked + ";");
		eval("document.<portlet:namespace />fm.<portlet:namespace />" + date + "AmPm.disabled = " + checked + ";");
	}

	function <portlet:namespace />downloadArticleContent() {
		document.<portlet:namespace />fm.xml.value = <portlet:namespace />getArticleContent();
		document.<portlet:namespace />fm.action = "<%= themeDisplay.getPathMain() %>/journal/get_article_content";
		document.<portlet:namespace />fm.submit();
		<portlet:namespace />resetForm();
	}

	function <portlet:namespace />editorContentChanged(text) {
		<portlet:namespace />contentChanged();
	}

	function <portlet:namespace />expireArticle() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.EXPIRE %>";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />getArticleContent() {
		<c:choose>
			<c:when test="<%= structure == null %>">
				return parent.<portlet:namespace />editor.getHTML();
			</c:when>
			<c:otherwise>
				var stillLocalized = false;

				for (var i = 0; i < <portlet:namespace />count; i++) {
					var elLanguage = document.getElementById("<portlet:namespace />structure_el" + i + "_localized");

					if (elLanguage.value != "false") {
						stillLocalized = true;
					}
				}

				var xsd = "<root";

				if (stillLocalized) {
					xsd += " default-locale='<%= defaultLanguageId %>'";
				}

				var availableLocales = document.<portlet:namespace />fm.<portlet:namespace />available_locales;

				if (stillLocalized && availableLocales.length > 1) {
					xsd += " available-locales='";

					for (var i = 1; i < availableLocales.length; i++) {
						if ((i + 1) == availableLocales.length) {
							xsd += availableLocales[i].value + "'";
						}
						else{
							xsd += availableLocales[i].value + ",";
						}
					}
				}

				xsd += ">";

				for (i = 0; i >= 0; i++) {
					var elDepth = document.getElementById("<portlet:namespace />structure_el" + i + "_depth");
					var elName = document.getElementById("<portlet:namespace />structure_el" + i + "_name");
					var elType = document.getElementById("<portlet:namespace />structure_el" + i + "_type");
					var elContent = document.getElementById("<portlet:namespace />structure_el" + i + "_content");
					var elLanguage = document.getElementById("<portlet:namespace />structure_el" + i + "_localized");

					if ((elDepth != null) && (elName != null) && (elType != null)) {
						var elDepthValue = elDepth.value;
						var elNameValue = elName.value;
						var elTypeValue = elType.value;
						var elContentValue = "";
						var elLanguageValue = elLanguage.value;

						if ((elTypeValue == "text") || (elTypeValue == "text_box") || (elTypeValue == "image_gallery") || (elTypeValue == "document_library")) {
							elContentValue = elContent.value;
							elContentValue = "<![CDATA[" + elContentValue + "]]>";
						}
						else if (elTypeValue == "text_area") {
							eval("elContentValue = parent.<portlet:namespace />structure_el" + i + "_content.getHTML();");

							elContentValue = "<![CDATA[" + elContentValue + "]]>";
						}
						else if (elTypeValue == "image") {
							var elContentName = elContent.getAttribute("name");

							if (stillLocalized && (elLanguageValue != null) && (elLanguageValue != "false") && (elLanguageValue != "") && (!elContentName.match(new RegExp(elLanguageValue + "$")))) {
								elContent.setAttribute("name", elContentName + "_" + elLanguageValue);
							}

							var elDeleteState = document.getElementById("<portlet:namespace />structure_el" + i + "_delete_state");

							if ((elDeleteState != null) && (elDeleteState.value == "yes")) {
								elContentValue = "delete";
							}

							elContentValue = "<![CDATA[" + elContentValue + "]]>";
						}
						else if (elTypeValue == "boolean") {
							elContentValue = elContent.checked ? "true" : "false";
							elContentValue = "<![CDATA[" + elContentValue + "]]>";
						}
						else if (elTypeValue == "list") {
							elContentValue = "";

							if (elContent.selectedIndex > -1) {
								elContentValue = elContent.options[elContent.selectedIndex].value;
							}

							elContentValue = "<![CDATA[" + elContentValue + "]]>";
						}
						else if (elTypeValue == "multi-list") {
							for (var l = 0; l < elContent.length; l++) {
								if (elContent.options[l].selected) {
									elContentValue += "<option><![CDATA[" + elContent.options[l].value + "]]></option>";
								}
							}
						}

						xsd += "<dynamic-element name='" + elNameValue + "' type='" + elTypeValue + "'><dynamic-content";

						if (stillLocalized && (elLanguageValue != null) && (elLanguageValue != "false") && (elLanguageValue != "")) {
							xsd += " language-id='" + elLanguageValue + "'";
						}

						xsd += ">" + elContentValue + "</dynamic-content>"

						var nextElDepth = document.getElementById("<portlet:namespace />structure_el" + (i + 1) + "_depth");

						if (nextElDepth != null) {
							nextElDepthValue = nextElDepth.value;

							if (elDepthValue == nextElDepthValue) {
								xsd += "</dynamic-element>";
							}
							else if (elDepthValue > nextElDepthValue) {
								var depthDiff = elDepthValue - nextElDepthValue;

								for (var j = 0; j <= depthDiff; j++) {
									xsd += "</dynamic-element>";
								}
							}
						}
						else {
							for (var j = 0; j <= elDepthValue; j++) {
								xsd += "</dynamic-element>";
							}
						}
					}
					else {
						break;
					}
				}

				xsd += "</root>";

				return xsd;
			</c:otherwise>
		</c:choose>
	}

	function <portlet:namespace />getChoice(value) {
		for (var i = 0; i < document.<portlet:namespace />fm.<portlet:namespace />languageId.length; i++) {
			if (document.<portlet:namespace />fm.<portlet:namespace />languageId.options[i].value == value) {
				return document.<portlet:namespace />fm.<portlet:namespace />languageId.options[i].index;
			}
		}

		return null;
	}

	function initEditor() {
		return "<%= UnicodeFormatter.toString(content) %>";
	}

	function <portlet:namespace />previewArticle() {
		document.<portlet:namespace />fm.title.value = document.<portlet:namespace />fm.<portlet:namespace />title.value;
		document.<portlet:namespace />fm.xml.value = <portlet:namespace />getArticleContent();
		document.<portlet:namespace />fm.action = "<%= themeDisplay.getPathMain() %>/journal/view_article_content?<%= Constants.CMD %>=<%= Constants.PREVIEW %>&groupId=<%= String.valueOf(groupId) %>&articleId=<%= Http.encodeURL(articleId) %>&version=<%= version %>&languageId=" + document.<portlet:namespace />fm.<portlet:namespace />languageId.value + "&templateId=" + Liferay.Util.getSelectedRadioValue(document.<portlet:namespace />fm.<portlet:namespace />templateId);
		document.<portlet:namespace />fm.target = "_blank";
		document.<portlet:namespace />fm.submit();
		<portlet:namespace />resetForm();
	}

	function <portlet:namespace />removeArticleLocale() {
		if (confirm("<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-deactivate-this-language") %>")) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "removeArticlesLocale";
			document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="struts_action" value="/journal/edit_article" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /><portlet:param name="articleId" value="<%= articleId %>" /><portlet:param name="version" value="<%= String.valueOf(version) %>" /></portlet:renderURL>&<portlet:namespace />languageId=<%= defaultLanguageId %>";
			submitForm(document.<portlet:namespace />fm);
		}
	}

	function <portlet:namespace />removeStructure() {
		document.<portlet:namespace />fm.<portlet:namespace />structureId.value = "";
		document.<portlet:namespace />fm.<portlet:namespace />content.value = "";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />resetForm() {
		document.<portlet:namespace />fm.action = "<portlet:actionURL><portlet:param name="struts_action" value="/journal/edit_article" /></portlet:actionURL>";
		document.<portlet:namespace />fm.target = "_self";
	}

	function <portlet:namespace />saveAndApproveArticle() {
		document.<portlet:namespace />fm.<portlet:namespace />approve.value = "1";
		<portlet:namespace />saveArticle();
	}

	function <portlet:namespace />saveArticle(cmd) {
		if (cmd == null) {
			cmd = "<%= article == null ? Constants.ADD : Constants.UPDATE %>";
		}

		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = cmd;

		<c:if test="<%= article == null %>">
			document.<portlet:namespace />fm.<portlet:namespace />articleId.value = document.<portlet:namespace />fm.<portlet:namespace />newArticleId.value;
		</c:if>

		document.<portlet:namespace />fm.<portlet:namespace />content.value = <portlet:namespace />getArticleContent();
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />selectDocumentLibrary(url) {
		document.getElementById(<portlet:namespace />documentLibraryInput).value = url;
	}

	function <portlet:namespace />selectImageGallery(url) {
		document.getElementById(<portlet:namespace />imageGalleryInput).value = url;
	}

	function <portlet:namespace />selectStructure(structureId) {
		if (document.<portlet:namespace />fm.<portlet:namespace />structureId.value != structureId) {
			document.<portlet:namespace />fm.<portlet:namespace />structureId.value = structureId;
			document.<portlet:namespace />fm.<portlet:namespace />templateId.value = "";
			document.<portlet:namespace />fm.<portlet:namespace />content.value = <portlet:namespace />getArticleContent();
			submitForm(document.<portlet:namespace />fm);
		}
	}

	function <portlet:namespace />selectTemplate(structureId, templateId) {
		document.<portlet:namespace />fm.<portlet:namespace />structureId.value = structureId;
		document.<portlet:namespace />fm.<portlet:namespace />templateId.value = templateId;
		document.<portlet:namespace />fm.<portlet:namespace />content.value = <portlet:namespace />getArticleContent();
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />setImageDeleteState(button, hidden, img, file) {
		var deleteState = document.getElementById(hidden);

		if (deleteState.value != "yes") {
			deleteState.value = "yes";
			document.images[img].style.display = "none";
			document.getElementById(file).disabled = true;
			button.value = "<liferay-ui:message key="cancel" />";
		}
		else {
			deleteState.value = "no";
			document.images[img].style.display = "block";
			document.getElementById(file).disabled = false;
			button.value = "<liferay-ui:message key="delete" />";
		}
	}
</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/journal/edit_article" /></portlet:actionURL>" class="uni-form" enctype="multipart/form-data" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveArticle(); return false;">
<input name="<portlet:namespace />portletResource" type="hidden" value="<%= portletResource %>" />
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= tabs2 %>" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>" />
<input name="<portlet:namespace />originalRedirect" type="hidden" value="<%= originalRedirect %>" />
<input name="<portlet:namespace />groupId" type="hidden" value="<%= groupId %>" />
<input name="<portlet:namespace />articleId" type="hidden" value="<%= articleId %>" />
<input name="<portlet:namespace />version" type="hidden" value="<%= version %>" />
<input name="<portlet:namespace />content" type="hidden" value="" />
<input name="<portlet:namespace />articleURL" type="hidden" value="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/journal/edit_article" /></portlet:renderURL>" />
<input name="<portlet:namespace />approve" type="hidden" value="" />
<input name="<portlet:namespace />deleteArticleIds" type="hidden" value="<%= articleId + EditArticleAction.VERSION_SEPARATOR + version %>" />
<input name="<portlet:namespace />expireArticleIds" type="hidden" value="<%= articleId + EditArticleAction.VERSION_SEPARATOR + version %>" />
<input name="title" type="hidden" value="" />
<input name="xml" type="hidden" value="" />
<input name="xsl" type="hidden" value="" />

<liferay-ui:tabs names="article" />

<%
SAXReader reader = new SAXReader();

Document contentDoc = null;

String[] availableLocales = null;
%>

<liferay-ui:tabs
	names="summary,content,schedule"
	formName="fm"
	param="tabs2"
	refresh="<%= false %>"
>
	<liferay-ui:section>
		<liferay-ui:error exception="<%= ArticleIdException.class %>" message="please-enter-a-valid-id" />
		<liferay-ui:error exception="<%= ArticleTitleException.class %>" message="please-enter-a-valid-name" />
		<liferay-ui:error exception="<%= ArticleTypeException.class %>" message="please-select-a-type" />
		<liferay-ui:error exception="<%= DuplicateArticleIdException.class %>" message="please-enter-a-unique-id" />
		<liferay-ui:tags-error />

		<table class="liferay-table">
		<tr>
			<td>
				<liferay-ui:message key="id" />
			</td>
			<td>
				<c:choose>
					<c:when test="<%= GetterUtil.getBoolean(PropsUtil.get(PropsUtil.JOURNAL_ARTICLE_FORCE_AUTOGENERATE_ID)) %>">
						<c:choose>
							<c:when test="<%= article == null %>">
								<liferay-ui:message key="autogenerate-id" />

								<input name="<portlet:namespace />newArticleId" type="hidden" value="" />
								<input name="<portlet:namespace />autoArticleId" type="hidden" value="true" />
							</c:when>
							<c:otherwise>
								<%= articleId %>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<table class="liferay-table">
						<tr>
							<td>
								<c:choose>
									<c:when test="<%= article == null %>">
										<liferay-ui:input-field model="<%= JournalArticle.class %>" bean="<%= article %>" field="articleId" fieldParam="newArticleId" defaultValue="<%= newArticleId %>" />
									</c:when>
									<c:otherwise>
										<%= articleId %>
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:if test="<%= article == null %>">
									<liferay-ui:input-checkbox param="autoArticleId" />

									<liferay-ui:message key="autogenerate-id" />
								</c:if>
							</td>
						</tr>
						</table>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="status" />
			</td>
			<td>
				<c:choose>
					<c:when test="<%= article == null %>">
						<liferay-ui:message key="new" />
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="<%= article.isExpired() %>">
								<liferay-ui:message key="expired" />
							</c:when>
							<c:when test="<%= article.isApproved() %>">
								<liferay-ui:message key="approved" />
							</c:when>
							<c:otherwise>
								<liferay-ui:message key="not-approved" />
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>

		<c:if test="<%= article != null %>">
			<tr>
				<td colspan="2">
					<br />
				</td>
			</tr>
			<tr>
				<td>
					<liferay-ui:message key="version" />
				</td>
				<td>
					<table class="liferay-table">
					<tr>
						<td>
							<%= version %>
						</td>
						<td>
							<liferay-ui:input-checkbox param="incrementVersion" defaultValue="<%= incrementVersion %>" disabled="<%= disableIncrementVersion %>" />

							<liferay-ui:message key="increment-version" />
						</td>
					</tr>
					</table>
				</td>
			</tr>
		</c:if>

		<tr>
			<td colspan="2">
				<br />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="name" />
			</td>
			<td>
				<liferay-ui:input-field model="<%= JournalArticle.class %>" bean="<%= article %>" field="title" />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="description" />
			</td>
			<td>
				<liferay-ui:input-field model="<%= JournalArticle.class %>" bean="<%= article %>" field="description" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<br />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="type" />
			</td>
			<td>
				<select name="<portlet:namespace />type">
					<option value=""></option>

					<%
					for (int i = 0; i < JournalArticleImpl.TYPES.length; i++) {
					%>

						<option <%= type.equals(JournalArticleImpl.TYPES[i]) ? "selected" : "" %> value="<%= JournalArticleImpl.TYPES[i] %>"><%= LanguageUtil.get(pageContext, JournalArticleImpl.TYPES[i]) %></option>

					<%
					}
					%>

				</select>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<br />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="tags" />
			</td>
			<td>

				<%
				long classPK = 0;

				if (article != null) {
					classPK = article.getResourcePrimKey();
				}
				%>

				<liferay-ui:tags-selector
					className="<%= JournalArticle.class.getName() %>"
					classPK="<%= classPK %>"
					hiddenInput="tagsEntries"
				/>
			</td>
		</tr>

		<c:if test="<%= article == null %>">
			<tr>
				<td colspan="2">
					<br />
				</td>
			</tr>
			<tr>
				<td>
					<liferay-ui:message key="permissions" />
				</td>
				<td>
					<liferay-ui:input-permissions
						modelName="<%= JournalArticle.class.getName() %>"
					/>
				</td>
			</tr>
		</c:if>

		</table>
	</liferay-ui:section>
	<liferay-ui:section>
		<liferay-ui:error exception="<%= ArticleContentException.class %>" message="please-enter-valid-content" />

		<c:choose>
			<c:when test="<%= (structure != null) %>">
				<table class="liferay-table">
				<tr>
					<td>
						<liferay-ui:message key="language" />
					</td>
					<td>
						<table class="liferay-table">
						<tr>
							<td>
								<select name="<portlet:namespace />languageId" onChange="<portlet:namespace />changeLanguageView();">

									<%
									Locale[] locales = LanguageUtil.getAvailableLocales();

									for (int i = 0; i < locales.length; i++) {
									%>

										<option <%= (languageId.equals(LocaleUtil.toLanguageId(locales[i]))) ? "selected" : "" %> value="<%= LocaleUtil.toLanguageId(locales[i]) %>"><%= locales[i].getDisplayName(locales[i]) %></option>

									<%
									}
									%>

								</select>

								<c:if test="<%= (article != null) && !languageId.equals(defaultLanguageId) %>">
									<input type="button" name="<portlet:namespace />removeArticleLocaleButton" value="<liferay-ui:message key="remove" />" onClick="<portlet:namespace />removeArticleLocale();" />
								</c:if>
							</td>
							<td>
								<table class="liferay-table">

								<%
								contentDoc = null;

								try {
									contentDoc = reader.read(new StringReader(content));

									Element contentEl = contentDoc.getRootElement();

									availableLocales = StringUtil.split(contentEl.attributeValue("available-locales"));
								}
								catch (Exception e) {
									contentDoc = null;
								}
								%>

								<tr>
									<td>
										<liferay-ui:message key="default-language" />
									</td>
									<td>
										<select name="<portlet:namespace />defaultLanguageId" onChange="<portlet:namespace />changeLanguageView();">

											<%
											if ((availableLocales != null) && (availableLocales.length > 0)) {
												boolean wasLanguageId = false;

												for (int i = 0; i < availableLocales.length; i++) {
													if (availableLocales[i].equals(languageId)) {
														wasLanguageId = true;
													}

													Locale availableLocale = LocaleUtil.fromLanguageId(availableLocales[i]);
											%>

													<option <%= (availableLocales[i].equals(defaultLanguageId)) ? "selected" : "" %> value="<%= availableLocales[i] %>"><%= availableLocale.getDisplayName(availableLocale) %></option>

											<%
												}

												if (!wasLanguageId) {
													Locale languageLocale = LocaleUtil.fromLanguageId(languageId);
											%>

													<option value="<%= languageId %>"><%= languageLocale.getDisplayName(languageLocale) %></option>

											<%
												}
											}
											else  {
											%>

												<option value="<%= defaultLanguageId %>"><%= defaultLocale.getDisplayName(defaultLocale) %></option>

											<%
											}
											%>

										</select>
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
			</c:when>
			<c:otherwise>
				<input name="<portlet:namespace />languageId" type="hidden" value="<%= defaultLanguageId %>" />
			</c:otherwise>
		</c:choose>

		<table class="liferay-table">
		<tr>
			<td>
				<liferay-ui:message key="structure" />
			</td>
			<td>
				<input name="<portlet:namespace />structureId" type="hidden" value="<%= structureId %>" />

				<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/journal/edit_structure" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /><portlet:param name="structureId" value="<%= structureId %>" /></portlet:renderURL>" id="<portlet:namespace />structureName">
				<%= structureName %>
				</a>

				<input type="button" value="<liferay-ui:message key="select" />"
					onClick="
						if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "selecting-a-new-structure-will-change-the-available-input-fields-and-available-templates") %>')) {
							var structureWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/journal/select_structure" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /></portlet:renderURL>', 'structure', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=no,status=no,toolbar=no,width=680');
							void('');
							structureWindow.focus();
						}"
					>

				<input <%= Validator.isNull(structureId) ? "disabled" : "" %> id="<portlet:namespace />removeStructureButton" type="button" value="<liferay-ui:message key="remove" />" onClick="<portlet:namespace />removeStructure();">
			</td>
		</tr>
		</table>

		<br />

		<table class="liferay-table">
		<tr>
			<td>
				<liferay-ui:message key="template" />
			</td>
			<td>
				<c:choose>
					<c:when test="<%= templates.size() == 0 %>">
						<input name="<portlet:namespace />templateId" type="hidden" value="<%= templateId %>" />

						<input type="button" value="<liferay-ui:message key="select" />"
							onClick="
								if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "selecting-a-template-will-change-the-structure,-available-input-fields,-and-available-templates") %>')) {
									var templateWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/journal/select_template" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /></portlet:renderURL>', 'template', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=no,status=no,toolbar=no,width=680');
									void('');
									templateWindow.focus();
								}"
							>
					</c:when>
					<c:otherwise>
						<liferay-ui:table-iterator
							list="<%= templates %>"
							listType="com.liferay.portlet.journal.model.JournalTemplate"
							rowLength="3"
							rowPadding="30"
						>

							<%
							boolean templateChecked = false;

							if (templateId.equals(tableIteratorObj.getTemplateId())) {
								templateChecked = true;
							}

							if ((tableIteratorPos.intValue() == 0) && Validator.isNull(templateId)) {
								templateChecked = true;
							}
							%>

							<input id="<portlet:namespace />template<%= tableIteratorObj.getTemplateId() %>_xsl" type="hidden" value="<%= JS.encodeURIComponent(tableIteratorObj.getXsl()) %>" />

							<input <%= templateChecked ? "checked" : "" %> name="<portlet:namespace />templateId" type="radio" value="<%= tableIteratorObj.getTemplateId() %>">

							<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/journal/edit_template" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="groupId" value="<%= String.valueOf(tableIteratorObj.getGroupId()) %>" /><portlet:param name="templateId" value="<%= tableIteratorObj.getTemplateId() %>" /></portlet:renderURL>">
							<%= tableIteratorObj.getName() %>
							</a>

							<c:if test="<%= tableIteratorObj.isSmallImage() %>">
								<br />

								<img border="0" hspace="0" src="<%= Validator.isNotNull(tableIteratorObj.getSmallImageURL()) ? tableIteratorObj.getSmallImageURL() : themeDisplay.getPathImage() + "/journal/template?img_id=" + tableIteratorObj.getSmallImageId() %>" vspace="0" />
							</c:if>
						</liferay-ui:table-iterator>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		</table>

		<br />

		<c:choose>
			<c:when test="<%= structure == null %>">
				<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" width="100%" />
			</c:when>
			<c:otherwise>
				<table border="0" cellpadding="0" cellspacing="0" width="100%">

				<input name="<portlet:namespace />available_locales" type="hidden" value="" />

				<%
				Document xsdDoc = reader.read(new StringReader(structure.getXsd()));

				if (contentDoc != null) {
				%>

					<input name="<portlet:namespace />available_locales" type="hidden" value="<%= defaultLanguageId %>"/>

				<%
					boolean languageFound = false;

					if ((availableLocales != null) && (availableLocales.length > 0)) {
						for (int i = 0; i < availableLocales.length ;i++) {
							if (!availableLocales[i].equals(defaultLanguageId)) {
				%>

								<input name="<portlet:namespace />available_locales" type="hidden" value="<%= availableLocales[i] %>" />

								<script type="text/javascript">
									document.<portlet:namespace />fm.<portlet:namespace />languageId.options[<portlet:namespace />getChoice('<%= availableLocales[i] %>')].className = 'focused';
								</script>

				<%
							}
							else{
							%>

								<script type="text/javascript">
									document.<portlet:namespace />fm.<portlet:namespace />languageId.options[<portlet:namespace />getChoice('<%= availableLocales[i] %>')].className = 'focused';
								</script>

				<%
							}

							if (availableLocales[i].equals(languageId)) {
								languageFound = true;
							}
						}
					}

					if (!languageFound && !languageId.equals(defaultLanguageId)) {
				%>

						<input name="<portlet:namespace />available_locales" type="hidden" value="<%= languageId %>"/>

						<script type="text/javascript">
							document.<portlet:namespace />fm.<portlet:namespace />removeArticleLocaleButton.disabled = true;
						</script>

				<%
					}
				}
				else {
					DocumentFactory docFactory = DocumentFactory.getInstance();

					contentDoc = docFactory.createDocument(docFactory.createElement("root"));
				%>

					<input name="<portlet:namespace />available_locales" type="hidden" value="<%= defaultLanguageId %>" />

				<%
				}

				_format(groupId, contentDoc, xsdDoc.getRootElement(), new IntegerWrapper(0), new Integer(-1), pageContext, request);
				%>

				</table>
			</c:otherwise>
		</c:choose>
	</liferay-ui:section>
	<liferay-ui:section>
		<liferay-ui:error exception="<%= ArticleDisplayDateException.class %>" message="please-enter-a-valid-display-date" />
		<liferay-ui:error exception="<%= ArticleExpirationDateException.class %>" message="please-enter-a-valid-expiration-date" />

		<table class="liferay-table">
		<tr>
			<td>
				<liferay-ui:message key="display-date" />
			</td>
			<td>
				<liferay-ui:input-field model="<%= JournalArticle.class %>" bean="<%= article %>" field="displayDate" defaultValue="<%= displayDate %>" />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="expiration-date" />
			</td>
			<td>
				<table class="liferay-table">
				<tr>
					<td>
						<liferay-ui:input-field model="<%= JournalArticle.class %>" bean="<%= article %>" field="expirationDate" defaultValue="<%= expirationDate %>" disabled="<%= neverExpire %>" />
					</td>
					<td>
						<liferay-ui:input-checkbox param="neverExpire" defaultValue="<%= neverExpire %>" onClick='<%= renderResponse.getNamespace() + "disableInputDate(\'expirationDate\', this.checked);" %>' />

						<liferay-ui:message key="never-auto-expire" />
					</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="review-date" />
			</td>
			<td>
				<table class="liferay-table">
				<tr>
					<td>
						<liferay-ui:input-field model="<%= JournalArticle.class %>" bean="<%= article %>" field="reviewDate" defaultValue="<%= reviewDate %>" disabled="<%= neverReview %>" />
					</td>
					<td>
						<liferay-ui:input-checkbox param="neverReview" defaultValue="<%= neverReview %>" onClick='<%= renderResponse.getNamespace() + "disableInputDate(\'reviewDate\', this.checked);" %>' />

						<liferay-ui:message key="never-review" />
					</td>
				</tr>
				</table>
			</td>
		</tr>
		</table>
	</liferay-ui:section>
</liferay-ui:tabs>

<br />

<input type="submit" value="<liferay-ui:message key="save" />" />

<c:if test="<%= ((article == null) || ((article != null) && !article.isApproved())) && PortletPermission.contains(permissionChecker, plid.longValue(), PortletKeys.JOURNAL, ActionKeys.APPROVE_ARTICLE) %>">
	<input type="button" value="<liferay-ui:message key="save-and-approve" />" onClick="<portlet:namespace />saveAndApproveArticle();" />

	<c:if test="<%= article != null %>">
		<input type="button" value="<liferay-ui:message key="approve" />" onClick="<portlet:namespace />approveArticle();" />
	</c:if>
</c:if>

<c:if test="<%= Validator.isNotNull(structureId) %>">
	<input type="button" value="<liferay-ui:message key="preview" />" onClick="<portlet:namespace />previewArticle();" />
</c:if>

<c:if test="<%= structure != null %>">
	<input type="button" value="<liferay-ui:message key="download" />" onClick="<portlet:namespace />downloadArticleContent();" />
</c:if>

<c:if test="<%= article != null %>">
	<c:if test="<%= !article.isExpired() && JournalArticlePermission.contains(permissionChecker, article, ActionKeys.EXPIRE) %>">
		<input type="button" value="<liferay-ui:message key="expire" />" onClick="<portlet:namespace />expireArticle();" />
	</c:if>

	<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.DELETE) %>">
		<input type="button" value="<liferay-ui:message key="delete" />" onClick="<portlet:namespace />deleteArticle();" />
	</c:if>
</c:if>

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="self.location = '<%= redirect %>';" />

</form>

<script type="text/javascript">
	//document.<portlet:namespace />fm.<portlet:namespace /><%= (article == null) ? "newArticleId" : "title" %>.focus();
</script>

<%!
private void _format(long groupId, Document contentDoc, Element root, IntegerWrapper count, Integer depth, PageContext pageContext, HttpServletRequest req) throws Exception {
	depth = new Integer(depth.intValue() + 1);

	Iterator itr = root.elements().iterator();

	while (itr.hasNext()) {
		Element el = (Element)itr.next();

		req.setAttribute(WebKeys.JOURNAL_ARTICLE_GROUP_ID, String.valueOf(groupId));

		req.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL, el);
		req.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_COUNT, count);
		req.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_DEPTH, depth);

		req.setAttribute(WebKeys.JOURNAL_ARTICLE_CONTENT_DOC, contentDoc);

		pageContext.include("/html/portlet/journal/edit_article_content_xsd_el.jsp");

		count.increment();

		String elType = el.attributeValue("type", StringPool.BLANK);

		if (!elType.equals("list") && !elType.equals("multi-list")) {
			_format(groupId, contentDoc, el, count, depth, pageContext, req);
		}
	}
}

public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.journal.edit_article_content.jsp";
%>