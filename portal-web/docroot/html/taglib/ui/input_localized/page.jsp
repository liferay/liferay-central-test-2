<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_input_localized") + StringPool.UNDERLINE;

boolean autoFocus = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-localized:autoFocus"));
boolean autoSize = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-localized:autoSize"));
Locale[] availableLocales = (Locale[])request.getAttribute("liferay-ui:input-localized:availableLocales");
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-localized:cssClass"));
String defaultLanguageId = (String)request.getAttribute("liferay-ui:input-localized:defaultLanguageId");
boolean disabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-localized:disabled"));
String fieldPrefix = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-localized:fieldPrefix"));
String fieldPrefixSeparator = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-localized:fieldPrefixSeparator"));
String id = (String)request.getAttribute("liferay-ui:input-localized:id");
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("liferay-ui:input-localized:dynamicAttributes");
boolean ignoreRequestValue = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-localized:ignoreRequestValue"));
String languageId = (String)request.getAttribute("liferay-ui:input-localized:languageId");
String maxLength = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-localized:maxLength"));
String name = (String)request.getAttribute("liferay-ui:input-localized:name");
String xml = (String)request.getAttribute("liferay-ui:input-localized:xml");
String type = (String)request.getAttribute("liferay-ui:input-localized:type");

Locale defaultLocale = null;

if (Validator.isNotNull(defaultLanguageId)) {
	defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);
}
else {
	defaultLocale = LocaleUtil.getDefault();
	defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);
}

String mainLanguageId = defaultLanguageId;

if (Validator.isNotNull(languageId)) {
	mainLanguageId = languageId;
}

Locale mainLocale = LocaleUtil.fromLanguageId(mainLanguageId);

String mainLanguageDir = LanguageUtil.get(mainLocale, "lang.dir");

String mainLanguageValue = LocalizationUtil.getLocalization(xml, mainLanguageId, false);

if (!ignoreRequestValue) {
	mainLanguageValue = ParamUtil.getString(request, name + StringPool.UNDERLINE + mainLanguageId, mainLanguageValue);
}

if (Validator.isNull(mainLanguageValue)) {
	mainLanguageValue = LocalizationUtil.getLocalization(xml, defaultLanguageId, true);
}

String fieldNamePrefix = StringPool.BLANK;
String fieldNameSuffix = StringPool.BLANK;

if (Validator.isNotNull(fieldPrefix)) {
	fieldNamePrefix = fieldPrefix + fieldPrefixSeparator;
	fieldNameSuffix = fieldPrefixSeparator;
}

String fieldSuffix = StringPool.BLANK;

if (!Validator.isNull(languageId)) {
	fieldSuffix = StringPool.UNDERLINE + mainLanguageId;
}

List<String> languageIds = new ArrayList<String>();

String fieldName = HtmlUtil.escapeAttribute(name + fieldSuffix);

Exception exception = (Exception)request.getAttribute("liferay-ui:error:exception");
String focusField = (String)request.getAttribute("liferay-ui:error:focusField");

Set<Locale> errorLocales = new HashSet<Locale>();

if ((exception != null) && fieldName.equals(focusField)) {
	if (LocalizedException.class.isAssignableFrom(exception.getClass())) {
		LocalizedException le = (LocalizedException)exception;

		Map<Locale, Exception> localizedExceptionsMap = le.getLocalizedExceptionsMap();

		errorLocales = localizedExceptionsMap.keySet();
	}
}
%>

<span class="input-localized input-localized-<%= type %>" id="<portlet:namespace /><%= id %>BoundingBox">
	<c:choose>
		<c:when test='<%= type.equals("editor") %>'>
			<liferay-ui:input-editor
				cssClass='<%= \"language-value \" + cssClass %>'
				editorImpl="ckeditor"
				initMethod='<%= randomNamespace + \"InitEditor\" %>'
				name="<%= fieldName %>"
				onBlurMethod='<%= randomNamespace + \"OnBlurEditor\" %>'
				onChangeMethod='<%= randomNamespace + \"OnChangeEditor\" %>'
				onFocusMethod='<%= randomNamespace + \"OnFocusEditor\" %>'
				toolbarSet="simple"
			/>

			<aui:script>
				function <portlet:namespace /><%= randomNamespace %>InitEditor() {
					return "<%= UnicodeFormatter.toString(mainLanguageValue) %>";
				}

				function <portlet:namespace /><%= randomNamespace %>OnBlurEditor() {
					Liferay.component('<portlet:namespace /><%= fieldName %>').blur();
				}

				function <portlet:namespace /><%= randomNamespace %>OnChangeEditor() {
					var inputLocalized = Liferay.component('<portlet:namespace /><%= fieldName %>');

					var editor = window['<portlet:namespace /><%= fieldName %>'];

					inputLocalized.updateInputLanguage(editor.getHTML());
				}

				function <portlet:namespace /><%= randomNamespace %>OnFocusEditor() {
					Liferay.component('<portlet:namespace /><%= fieldName %>').focus();
				}
			</aui:script>

			<aui:script use="aui-base">
				A.all('#<portlet:namespace /><%= id %>ContentBox .palette-item-inner').on(
					'click',
					function() {
						window['<portlet:namespace /><%= fieldName %>'].focus();
					}
				);
			</aui:script>
		</c:when>
		<c:when test='<%= type.equals("input") %>'>
			<input aria-labeledby="<portlet:namespace /><%= HtmlUtil.escapeAttribute(id + fieldSuffix) %>_desc" class="language-value <%= cssClass %>" dir="<%= mainLanguageDir %>" <%= disabled ? "disabled=\"disabled\"" : "" %> id="<portlet:namespace /><%= HtmlUtil.escapeAttribute(id + fieldSuffix) %>" name="<portlet:namespace /><%= HtmlUtil.escapeAttribute(name + fieldSuffix) %>" type="text" value="<%= HtmlUtil.escapeAttribute(mainLanguageValue) %>" <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %> />
		</c:when>
		<c:when test='<%= type.equals("textarea") %>'>
			<textarea aria-labeledby="<portlet:namespace /><%= HtmlUtil.escapeAttribute(id + fieldSuffix) %>_desc" class="language-value <%= cssClass %>" dir="<%= mainLanguageDir %>" <%= disabled ? "disabled=\"disabled\"" : "" %> id="<portlet:namespace /><%= HtmlUtil.escapeAttribute(id + fieldSuffix) %>" name="<portlet:namespace /><%= HtmlUtil.escapeAttribute(name + fieldSuffix) %>" <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %>><%= HtmlUtil.escape(mainLanguageValue) %></textarea>

			<c:if test="<%= autoSize %>">
				<aui:script use="aui-autosize-deprecated">
					A.one('#<portlet:namespace /><%= HtmlUtil.escapeJS(id + fieldSuffix) %>').plug(A.Plugin.Autosize);
				</aui:script>
			</c:if>
		</c:when>
	</c:choose>

	<div class="hide-accessible" id="<portlet:namespace /><%= HtmlUtil.escapeAttribute(id + fieldSuffix) %>_desc"><%= defaultLocale.getDisplayName(LocaleUtil.fromLanguageId(LanguageUtil.getLanguageId(request))) %> <liferay-ui:message key="translation" /></div>

	<c:if test="<%= (availableLocales.length > 0) && Validator.isNull(languageId) %>">

		<%
		languageIds.add(defaultLanguageId);

		for (int i = 0; i < availableLocales.length; i++) {
			String curLanguageId = LocaleUtil.toLanguageId(availableLocales[i]);

			if (curLanguageId.equals(defaultLanguageId)) {
				continue;
			}

			String languageValue = null;

			if (Validator.isNotNull(xml)) {
				languageValue = LocalizationUtil.getLocalization(xml, curLanguageId, false);
			}

			if (Validator.isNotNull(languageValue) || (!ignoreRequestValue && (request.getParameter(name + StringPool.UNDERLINE + curLanguageId) != null))) {
				languageIds.add(curLanguageId);
			}
		}

		for (int i = 0; i < languageIds.size(); i++) {
			String curLanguageId = languageIds.get(i);

			Locale curLocale = LocaleUtil.fromLanguageId(curLanguageId);

			String curLanguageDir = LanguageUtil.get(curLocale, "lang.dir");

			String languageValue = StringPool.BLANK;

			if (Validator.isNotNull(xml)) {
				languageValue = LocalizationUtil.getLocalization(xml, curLanguageId, false);
			}

			if (!ignoreRequestValue) {
				languageValue = ParamUtil.getString(request, name + StringPool.UNDERLINE + curLanguageId, languageValue);
			}
		%>

			<aui:input dir="<%= curLanguageDir %>" disabled="<%= disabled %>" id="<%= HtmlUtil.escapeAttribute(id + StringPool.UNDERLINE + curLanguageId) %>" name="<%= HtmlUtil.escapeAttribute(fieldNamePrefix + name + StringPool.UNDERLINE + curLanguageId + fieldNameSuffix) %>" type="hidden" value="<%= languageValue %>" />

		<%
		}
		%>

		<div class="input-localized-content" id="<portlet:namespace /><%= id %>ContentBox" role="menu">
			<table class="palette-container">
				<tr class="palette-items-container">

					<%
					LinkedHashSet<String> uniqueLanguageIds = new LinkedHashSet<String>();

					uniqueLanguageIds.add(defaultLanguageId);

					for (int i = 0; i < availableLocales.length; i++) {
						String curLanguageId = LocaleUtil.toLanguageId(availableLocales[i]);

						uniqueLanguageIds.add(curLanguageId);
					}

					int index = 0;

					for (String curLanguageId : uniqueLanguageIds) {
						String itemCssClass = "palette-item";

						Locale curLocale = LocaleUtil.fromLanguageId(curLanguageId);

						if (errorLocales.contains(curLocale) || ((index == 0) && errorLocales.isEmpty())) {
							itemCssClass += " palette-item-selected";
						}

						if (defaultLanguageId.equals(curLanguageId)) {
							itemCssClass += " lfr-input-localized-default";
						}

						if (languageIds.contains(curLanguageId)) {
							itemCssClass += " lfr-input-localized";
						}
					%>

						<td class="palette-item <%= itemCssClass %>" data-index="<%= index++ %>" data-value="<%= curLanguageId %>" role="menuitem">
							<a class="palette-item-inner" href="javascript:void(0);">
								<img alt="<%= curLocale.getDisplayName(LocaleUtil.fromLanguageId(LanguageUtil.getLanguageId(request))) %> <liferay-ui:message key="translation" />" class="lfr-input-localized-flag" data-languageid="<%= curLanguageId %>" src="<%= themeDisplay.getPathThemeImages() %>/language/<%= curLanguageId %>.png" />
								<div class='<%= errorLocales.contains(curLocale) ? "lfr-input-localized-state lfr-input-localized-state-error" : "lfr-input-localized-state" %>'></div>
							</a>
						</td>

					<%
					}
					%>

				</tr>
			</table>
		</div>
	</c:if>
</span>

<c:if test="<%= Validator.isNotNull(maxLength) %>">
	<aui:script use="aui-char-counter">
		new A.CharCounter(
			{
				input: '#<portlet:namespace /><%= HtmlUtil.escapeJS(id + fieldSuffix) %>',
				maxLength: <%= maxLength %>
			}
		);
	</aui:script>
</c:if>

<c:choose>
	<c:when test="<%= (availableLocales.length > 0) && Validator.isNull(languageId) %>">
		<aui:script use="liferay-input-localized">
			var defaultLanguageId = '<%= defaultLanguageId %>';

			var available = {};

			var errors = {};

			<%
			for (Locale availableLocale : availableLocales) {
				String availableLanguageId = LocaleUtil.toLanguageId(availableLocale);
			%>

				available['<%= availableLanguageId %>'] = '<%= availableLocale.getDisplayName(locale) %>';

			<%
			}
			%>

			var availableLanguageIds = A.Array.dedupe(
				[defaultLanguageId].concat(A.Object.keys(available))
			);

			<%
			for (Locale errorLocale : errorLocales) {
				String errorLocaleId = LocaleUtil.toLanguageId(errorLocale);
			%>

				errors['<%= errorLocaleId %>'] = '<%= errorLocale.getDisplayName(locale) %>';

			<%
			}
			%>

			var errorLanguageIds = A.Array.dedupe(A.Object.keys(errors));

			Liferay.InputLocalized.register(
				'<portlet:namespace /><%= HtmlUtil.escapeJS(id + fieldSuffix) %>',
				{
					boundingBox: '#<portlet:namespace /><%= id %>BoundingBox',
					columns: 20,
					contentBox: '#<portlet:namespace /><%= id %>ContentBox',

					<c:if test='<%= type.equals("editor") %>'>
						editor: window['<portlet:namespace /><%= fieldName %>'],
					</c:if>

					fieldPrefix: '<%= fieldPrefix %>',
					fieldPrefixSeparator: '<%= fieldPrefixSeparator %>',
					id: '<%= id %>',
					inputPlaceholder: '#<portlet:namespace /><%= HtmlUtil.escapeJS(id + fieldSuffix) %>',
					items: availableLanguageIds,
					itemsError: errorLanguageIds,
					lazy: <%= !type.equals("editor") %>,
					name: '<%= name %>',
					namespace: '<portlet:namespace />',
					toggleSelection: false,
					translatedLanguages: '<%= StringUtil.merge(languageIds) %>'
				}
			);

			<c:if test="<%= autoFocus %>">
				Liferay.Util.focusFormField('#<portlet:namespace /><%= HtmlUtil.escapeJS(id + fieldSuffix) %>');
			</c:if>
		</aui:script>
	</c:when>
	<c:otherwise>
		<c:if test="<%= autoFocus %>">
			<aui:script>
				Liferay.Util.focusFormField('#<portlet:namespace /><%= HtmlUtil.escapeJS(id + fieldSuffix) %>');
			</aui:script>
		</c:if>
	</c:otherwise>
</c:choose>