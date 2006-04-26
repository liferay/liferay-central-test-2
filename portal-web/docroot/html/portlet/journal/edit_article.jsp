<%
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

String redirect = ParamUtil.getString(request, "redirect");

JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

String articleId = BeanParamUtil.getString(article, request, "articleId");
String newArticleId = ParamUtil.getString(request, "newArticleId");

double version = BeanParamUtil.getDouble(article, request, "version", JournalArticle.DEFAULT_VERSION);
boolean incrementVersion = ParamUtil.getBoolean(request, "incrementVersion");

Calendar displayDate = new GregorianCalendar(timeZone, locale);

if (article != null) {
	if (article.getDisplayDate() != null) {
		displayDate.setTime(article.getDisplayDate());
	}
}

boolean neverExpire = ParamUtil.getBoolean(request, "neverExpire", true);

Calendar expirationDate = new GregorianCalendar(timeZone, locale);

expirationDate.add(Calendar.YEAR, 1);

if (article != null) {
	if (article.getExpirationDate() != null) {
		neverExpire = false;

		expirationDate.setTime(article.getExpirationDate());
	}
}

boolean neverReview = ParamUtil.getBoolean(request, "neverReview", true);

Calendar reviewDate = new GregorianCalendar(timeZone, locale);

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
		structure = JournalStructureLocalServiceUtil.getStructure(company.getCompanyId(), structureId);

		structureName = structure.getName();
	}
	catch (NoSuchStructureException nsse) {
	}
}

List templates = new ArrayList();

if (structure != null) {
	templates = JournalTemplateLocalServiceUtil.getStructureTemplates(company.getCompanyId(), structureId);
}

String templateId = BeanParamUtil.getString(article, request, "templateId");

String languageId = LanguageUtil.getLanguageId(request);
String defaultLanguageId = LocaleUtil.toLanguageId(Locale.getDefault());

String content = null;

if (article != null) {
	content = article.getContentByLocale(languageId);
}
else {
	content = ParamUtil.getString(request, "content");
}

boolean disableIncrementVersion = false;

if (GetterUtil.getBoolean(PropsUtil.get(PropsUtil.JOURNAL_ARTICLE_FORCE_INCREMENT_VERSION))) {
	boolean latestVersion = (article == null) || (article != null && JournalArticleLocalServiceUtil.isLatestVersion(company.getCompanyId(), articleId, version));

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
	var count = 0;

	function <portlet:namespace />approveArticle() {
		<portlet:namespace />saveArticle("<%= Constants.APPROVE %>");
	}

	<c:if test="<%= article != null %>">
		function <portlet:namespace />changeLanguageView() {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= article == null ? Constants.ADD : Constants.UPDATE %>";
			document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/journal/edit_article" /><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="articleId" value="<%= articleId %>" /><portlet:param name="version" value="<%= String.valueOf(version) %>" /></portlet:renderURL>&<portlet:namespace />languageId=" + document.<portlet:namespace />fm.<portlet:namespace />languageId.value;
			document.<portlet:namespace />fm.<portlet:namespace />content.value = <portlet:namespace />getArticleContent();
			submitForm(document.<portlet:namespace />fm);
		}
	</c:if>

	function <portlet:namespace />deleteArticle() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
		submitForm(document.<portlet:namespace />fm);
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

	function <portlet:namespace />expireArticle() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.EXPIRE %>";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />getArticleContent() {
		<c:choose>
			<c:when test="<%= structure == null %>">
				<c:choose>
					<c:when test="<%= BrowserSniffer.is_rtf(request) %>">
						return parent.<portlet:namespace />editor.getHTML();
					</c:when>
					<c:otherwise>
						return document.<portlet:namespace />fm.<portlet:namespace />editor.value;
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				var stillLocalized = false;

				for (var i = 0; i < count; i++) {
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

						if ((elTypeValue == "text") || (elTypeValue == "text_box")) {
							elContentValue = elContent.value;
							elContentValue = "<![CDATA[" + elContentValue + "]]>";
						}
						else if (elTypeValue == "text_area") {
							if (<%= BrowserSniffer.is_rtf(request) %>) {
								eval("elContentValue = parent.<portlet:namespace />structure_el" + i + "_content.getHTML();");
							}
							else {
								elContentValue = elContent.value;
							}
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

	function initEditor() {
		return "<%= UnicodeFormatter.toString(content) %>";
	}

	function <portlet:namespace />previewArticle() {
		document.<portlet:namespace />fm.title.value = document.<portlet:namespace />fm.<portlet:namespace />title.value;
		document.<portlet:namespace />fm.xml.value = <portlet:namespace />getArticleContent();
		document.<portlet:namespace />fm.action = "<%= themeDisplay.getPathMain() %>/journal/view_article_content?<%= Constants.CMD %>=<%= Constants.PREVIEW %>&articleId=<%= Http.encodeURL(articleId) %>&version=<%= version %>&languageId=" + document.<portlet:namespace />fm.<portlet:namespace />languageId.value + "&templateId=" + getSelectedRadioValue(document.<portlet:namespace />fm.<portlet:namespace />templateId);
		document.<portlet:namespace />fm.target = "_blank";
		document.<portlet:namespace />fm.submit();
		<portlet:namespace />resetForm();
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

	function <portlet:namespace />selectStructure(structureId) {
		document.<portlet:namespace />fm.<portlet:namespace />structureId.value = structureId;
		document.<portlet:namespace />fm.<portlet:namespace />content.value = <portlet:namespace />getArticleContent();
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />selectTemplate(structureId, templateId) {
		document.<portlet:namespace />fm.<portlet:namespace />structureId.value = structureId;
		document.<portlet:namespace />fm.<portlet:namespace />templateId.value = templateId;
		document.<portlet:namespace />fm.<portlet:namespace />content.value = <portlet:namespace />getArticleContent();
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />getChoice(value) {
		for (var i = 0; i < document.<portlet:namespace />fm.<portlet:namespace />languageId.length; i++) {
			if (document.<portlet:namespace />fm.<portlet:namespace />languageId.options[i].value == value) {
				return document.<portlet:namespace />fm.<portlet:namespace />languageId.options[i].index;
			}
		}

		return null;
	}

	function <portlet:namespace />setImageDeleteState(button, hidden, img, file) {
		var deleteState = document.getElementById(hidden);

		if (deleteState.value != "yes") {
			deleteState.value = "yes";
			document.images[img].style.display = "none";
			document.getElementById(file).disabled = true;
			button.value = "<%= LanguageUtil.get(pageContext, "cancel") %>";
		}
		else {
			deleteState.value = "no";
			document.images[img].style.display = "block";
			document.getElementById(file).disabled = false;
			button.value = "<%= LanguageUtil.get(pageContext, "delete") %>";
		}
	}

	function toggleArticleImage(obj, imageId) {
		var hidden = toggleById(imageId, true);
		var spanText = obj.getElementsByTagName("span");

		if (hidden) {
			spanText[0].style.display = "none";
			spanText[1].style.display = "";
		}
		else {
			spanText[0].style.display = "";
			spanText[1].style.display = "none";
		}
	}
</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/journal/edit_article" /></portlet:actionURL>" enctype="multipart/form-data" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveArticle(); return false";>
<input name="<portlet:namespace />portletResource" type="hidden" value="<%= portletResource %>">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>">
<input name="<portlet:namespace />articleId" type="hidden" value="<%= articleId %>">
<input name="<portlet:namespace />version" type="hidden" value="<%= version %>">
<input name="<portlet:namespace />content" type="hidden" value="">
<input name="<portlet:namespace />articleURL" type="hidden" value="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/journal/edit_article" /></portlet:renderURL>">
<input name="<portlet:namespace />approve" type="hidden" value="">
<input name="title" type="hidden" value="">
<input name="xml" type="hidden" value="">
<input name="xsl" type="hidden" value="">

<liferay-ui:tabs names="article" />

<liferay-ui:error exception="<%= ArticleDisplayDateException.class %>" message="please-enter-a-valid-display-date" />
<liferay-ui:error exception="<%= ArticleExpirationDateException.class %>" message="please-enter-a-valid-expiration-date" />
<liferay-ui:error exception="<%= ArticleIdException.class %>" message="please-enter-a-valid-id" />
<liferay-ui:error exception="<%= ArticleTitleException.class %>" message="please-enter-a-valid-title" />
<liferay-ui:error exception="<%= DuplicateArticleIdException.class %>" message="please-enter-a-unique-id" />

<table border="0" cellpadding="0" cellspacing="0">
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "id") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<table border="0" cellpadding="0" cellspacing="0">
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
			<td style="padding-left: 30px;"></td>
			<td>
				<c:if test="<%= article == null %>">
					<liferay-ui:input-checkbox param="autoArticleId" />

					<%= LanguageUtil.get(pageContext, "autogenerate-id") %>
				</c:if>
			</td>
		</tr>
		</table>
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "status") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<c:choose>
			<c:when test="<%= article == null %>">
				<%= LanguageUtil.get(pageContext, "new") %>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="<%= article.isApproved() %>">
						<%= LanguageUtil.get(pageContext, "approved") %>
					</c:when>
					<c:when test="<%= article.isExpired() %>">
						<%= LanguageUtil.get(pageContext, "expired") %>
					</c:when>
					<c:otherwise>
						<%= LanguageUtil.get(pageContext, "not-approved") %>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</td>
</tr>

<c:if test="<%= article != null %>">
	<tr>
		<td colspan="3">
			<br>
		</td>
	</tr>
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "version") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<%= version %>
				</td>
				<td style="padding-left: 30px;"></td>
				<td>
					<liferay-ui:input-checkbox param="incrementVersion" defaultValue="<%= incrementVersion %>" disabled="<%= disableIncrementVersion %>" />

					<%= LanguageUtil.get(pageContext, "increment-version") %>
				</td>
			</tr>
			</table>
		</td>
	</tr>
</c:if>

<tr>
	<td colspan="3">
		<br>
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "title") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<liferay-ui:input-field model="<%= JournalArticle.class %>" bean="<%= article %>" field="title" />
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "type") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<c:choose>
			<c:when test="<%= article == null %>">
				<select name="<portlet:namespace />type">

					<%
					for (int i = 0; i < JournalArticle.TYPES.length; i++) {
					%>

						<option <%= type.equals(JournalArticle.TYPES[i]) ? "selected" : "" %> value="<%= JournalArticle.TYPES[i] %>"><%= LanguageUtil.get(pageContext, JournalArticle.TYPES[i]) %></option>

					<%
					}
					%>

				</select>
			</c:when>
			<c:otherwise>
				<input name="<portlet:namespace />type" type="hidden" value="<%= type %>">

				<%= LanguageUtil.get(pageContext, type) %>
			</c:otherwise>
		</c:choose>
	</td>
	</td>
</tr>

<c:choose>
	<c:when test="<%= (article != null) && (structure != null) %>">
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "language") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<select name="<portlet:namespace />languageId" <%= (article == null) ? "disabled" : "" %> onChange="<portlet:namespace />changeLanguageView();">

					<%
					Locale[] locales = LanguageUtil.getAvailableLocales();

					for (int i = 0; i < locales.length; i++) {
					%>

						<option <%= (languageId.equals(LocaleUtil.toLanguageId(locales[i]))) ? "selected" : "" %> value="<%= LocaleUtil.toLanguageId(locales[i]) %>"><%= locales[i].getDisplayName(locales[i]) %></option>

					<%
					}
					%>

				</select>
			</td>
		</tr>
	</c:when>
	<c:otherwise>
		<input name="<portlet:namespace />languageId" type="hidden" value="<%= defaultLanguageId %>">
	</c:otherwise>
</c:choose>

<tr>
	<td colspan="3">
		<br>
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "display-date") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<liferay-ui:input-field model="<%= JournalArticle.class %>" bean="<%= article %>" field="displayDate" defaultValue="<%= displayDate %>" />
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "expiration-date") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<liferay-ui:input-field model="<%= JournalArticle.class %>" bean="<%= article %>" field="expirationDate" defaultValue="<%= expirationDate %>" disabled="<%= neverExpire %>" />
			</td>
			<td style="padding-left: 30px;"></td>
			<td>
				<liferay-ui:input-checkbox param="neverExpire" defaultValue="<%= neverExpire %>" onClick='<%= renderResponse.getNamespace() + "disableInputDate('expirationDate', this.checked);" %>' />

				<%= LanguageUtil.get(pageContext, "never-auto-expire") %>
			</td>
		</tr>
		</table>
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "review-date") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<liferay-ui:input-field model="<%= JournalArticle.class %>" bean="<%= article %>" field="reviewDate" defaultValue="<%= reviewDate %>" disabled="<%= neverReview %>" />
			</td>
			<td style="padding-left: 30px;"></td>
			<td>
				<liferay-ui:input-checkbox param="neverReview" defaultValue="<%= neverReview %>" onClick='<%= renderResponse.getNamespace() + "disableInputDate('reviewDate', this.checked);" %>' />

				<%= LanguageUtil.get(pageContext, "never-review") %>
			</td>
		</tr>
		</table>
	</td>
</tr>

<c:if test="<%= article == null %>">
	<tr>
		<td colspan="3">
			<br>
		</td>
	</tr>
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "permissions") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<liferay-ui:input-permissions />
		</td>
	</tr>
</c:if>

</table>

<br>

<input class="portlet-form-button" type="submit" value='<%= LanguageUtil.get(pageContext, "save") %>'>

<c:if test="<%= ((article == null) || ((article != null) && !article.isApproved())) && PortletPermission.contains(permissionChecker, plid, PortletKeys.JOURNAL, ActionKeys.APPROVE_ARTICLE) %>">
	<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "save-and-approve") %>' onClick="<portlet:namespace />saveAndApproveArticle();">

	<c:if test="<%= article != null %>">
		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "approve") %>' onClick="<portlet:namespace />approveArticle();">
	</c:if>
</c:if>

<c:if test="<%= Validator.isNotNull(structureId) %>">
	<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "preview") %>' onClick="<portlet:namespace />previewArticle();">
</c:if>

<c:if test="<%= structure != null %>">
	<input class="portlet-form-button" type="button" value="<bean:message key="download" />" onClick="<portlet:namespace />downloadArticleContent();">
</c:if>

<c:if test="<%= article != null %>">
	<c:if test="<%= !article.isExpired() && JournalArticlePermission.contains(permissionChecker, article, ActionKeys.EXPIRE) %>">
		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "expire") %>' onClick="<portlet:namespace />expireArticle();">
	</c:if>

	<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.DELETE) %>">
		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "delete") %>' onClick="<portlet:namespace />deleteArticle();">
	</c:if>
</c:if>

<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="self.location = '<%= redirect %>';">

<br><br>

<liferay-ui:tabs names="design" />

<table border="0" cellpadding="0" cellspacing="0">
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "structure") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<input name="<portlet:namespace />structureId" type="hidden" value="<%= structureId %>">

		<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/journal/edit_structure" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="structureId" value="<%= structureId %>" /></portlet:renderURL>" id="<portlet:namespace />structureName">
		<%= structureName %>
		</a>

		<c:if test="<%= article == null %>">
			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "select") %>'
				onClick="
					if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "selecting-a-new-structure-will-change-the-available-input-fields-and-available-templates") %>')) {
						var structureWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/journal/select_structure" /></portlet:renderURL>', 'structure', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=no,status=no,toolbar=no,width=680');
						void('');
						structureWindow.focus();
					}"
				>

			<input <%= Validator.isNull(structureId) ? "disabled" : "" %> class="portlet-form-button" id="<portlet:namespace />removeStructureButton" type="button" value='<%= LanguageUtil.get(pageContext, "remove") %>' onClick="<portlet:namespace />removeStructure();">
		</c:if>
	</td>
</tr>
<tr>
	<td colspan="3">
		<br>
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "template") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<c:choose>
			<c:when test="<%= templates.size() == 0 %>">
				<input name="<portlet:namespace />templateId" type="hidden" value="<%= templateId %>">

				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "select") %>'
					onClick="
						if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "selecting-a-template-will-change-the-structure,-available-input-fields,-and-available-templates") %>')) {
							var structureWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/journal/select_template" /></portlet:renderURL>', 'structure', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=no,status=no,toolbar=no,width=680');
							void('');
							structureWindow.focus();
						}"
					>

				<%--
				<input <%= Validator.isNull(structureId) ? "disabled" : "" %> class="portlet-form-button" id="<portlet:namespace />removeStructureButton" type="button" value='<%= LanguageUtil.get(pageContext, "remove") %>' onClick="<portlet:namespace />removeStructure();">

				<%= LanguageUtil.get(pageContext, "please-select-a-structure-to-choose-a-template") %>
				--%>
			</c:when>
			<c:otherwise>
				<liferay-ui:table-iterator
					list="<%= templates %>"
					listType="com.liferay.portlet.journal.model.JournalTemplate"
					rowLength="3"
					rowPadding="30"
					rowBreak="<br />">

					<%
					boolean templateChecked = false;

					if (templateId.equals(tableIteratorObj.getTemplateId())) {
						templateChecked = true;
					}

					if ((tableIteratorPos.intValue() == 0) && Validator.isNull(templateId)) {
						templateChecked = true;
					}
					%>

					<input id="<portlet:namespace />template<%= tableIteratorObj.getTemplateId() %>_xsl" type="hidden" value="<%= JS.encodeURIComponent(tableIteratorObj.getXsl()) %>">

					<input <%= templateChecked ? "checked" : "" %> name="<portlet:namespace />templateId" type="radio" value="<%= tableIteratorObj.getTemplateId() %>">

					<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/journal/edit_template" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="templateId" value="<%= tableIteratorObj.getTemplateId() %>" /></portlet:renderURL>">
					<%= tableIteratorObj.getName() %>
					</a>

					<c:if test="<%= tableIteratorObj.isSmallImage() %>">
						<br>

						<img border="0" hspace="0" src="<%= Validator.isNotNull(tableIteratorObj.getSmallImageURL()) ? tableIteratorObj.getSmallImageURL() : themeDisplay.getPathImage() + "/journal/template?img_id=" + tableIteratorObj.getTemplateId() + "&medium=1" %>" vspace="0">
					</c:if>
				</liferay-ui:table-iterator>
			</c:otherwise>
		</c:choose>
	</td>
</tr>
</table>

<br>

<liferay-ui:tabs names="content" />

<liferay-ui:error exception="<%= ArticleContentException.class %>" message="please-enter-valid-content" />

<c:choose>
	<c:when test="<%= structure == null %>">
		<c:choose>
			<c:when test="<%= BrowserSniffer.is_rtf(request) %>">
				<iframe frameborder="0" height="400" id="<portlet:namespace />editor" name="<portlet:namespace />editor" scrolling="no" src="<%= themeDisplay.getPathJavaScript() %>/editor/editor.jsp?p_l_id=<%= plid %>&editorImpl=<%= PropsUtil.get(EDITOR_WYSIWYG_IMPL_KEY) %>" width="100%"></iframe>
			</c:when>
			<c:otherwise>
				<textarea class="form-text" cols="80" id="<portlet:namespace />editor" name="<portlet:namespace />editor" rows="20" wrap="soft" onKeyDown="checkTab(this); disableEsc();"><%= Html.toInputSafe(GetterUtil.getString(content)) %></textarea>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<table border="0" cellpadding="0" cellspacing="0" width="100%">

		<input name="<portlet:namespace />available_locales" type="hidden" value="">

		<%
		SAXReader reader = new SAXReader();

		Document xsdDoc = reader.read(new StringReader(structure.getXsd()));

		Document contentDoc = null;

		try {
			contentDoc = reader.read(new StringReader(content));
		%>

			<input name="<portlet:namespace />available_locales" type="hidden" value="<%= defaultLanguageId %>"/>

		<%
			Element contentEl = contentDoc.getRootElement();

			boolean languageFound = false;

			String[] availableLocales = StringUtil.split(contentEl.attributeValue("available-locales"));

			if ((availableLocales != null) && (availableLocales.length > 0)) {
				for (int i = 0; i < availableLocales.length ;i++) {
					if (!availableLocales[i].equals(defaultLanguageId)) {
		%>

						<input name="<portlet:namespace />available_locales" type="hidden" value="<%= availableLocales[i] %>">

						<script type="text/javascript">
							document.<portlet:namespace />fm.<portlet:namespace />languageId.options[<portlet:namespace />getChoice('<%= availableLocales[i] %>')].style.color = '<%= colorScheme.getPortletMsgError() %>';
						</script>

		<%
					}
					else{
					%>

						<script type="text/javascript">
							document.<portlet:namespace />fm.<portlet:namespace />languageId.options[<portlet:namespace />getChoice('<%= availableLocales[i] %>')].style.color = '<%= colorScheme.getPortletMsgError() %>';
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

		<%
			}
		}
		catch (Exception e) {
			DocumentFactory docFactory = DocumentFactory.getInstance();

			contentDoc = docFactory.createDocument(docFactory.createElement("root"));
		%>

			<input name="<portlet:namespace />available_locales" type="hidden" value="<%= defaultLanguageId %>">

		<%
		}

		_format(contentDoc, xsdDoc.getRootElement(), new IntegerWrapper(0), new Integer(-1), pageContext, request);
		%>

		</table>
	</c:otherwise>
</c:choose>

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace /><%= (article == null) ? "newArticleId" : "title" %>.focus();
</script>

<%!
private void _format(Document contentDoc, Element root, IntegerWrapper count, Integer depth, PageContext pageContext, HttpServletRequest req) throws Exception {
	depth = new Integer(depth.intValue() + 1);

	Iterator itr = root.elements().iterator();

	while (itr.hasNext()) {
		Element el = (Element)itr.next();

		req.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL, el);
		req.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_COUNT, count);
		req.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_DEPTH, depth);

		req.setAttribute(WebKeys.JOURNAL_ARTICLE_CONTENT_DOC, contentDoc);

		pageContext.include("/html/portlet/journal/edit_article_content_xsd_el.jsp");

		count.increment();

		String elType = el.attributeValue("type", StringPool.BLANK);

		if (!elType.equals("list") && !elType.equals("multi-list")) {
			_format(contentDoc, el, count, depth, pageContext, req);
		}
	}
}

public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.journal.edit_article_content.jsp";
%>