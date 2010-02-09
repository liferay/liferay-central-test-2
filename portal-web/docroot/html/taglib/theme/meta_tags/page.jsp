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

<%@ include file="/html/taglib/init.jsp" %>

<c:if test="<%= layout != null %>">

	<%
	String currentLanguageId = LanguageUtil.getLanguageId(request);
	Locale defaultLocale = LocaleUtil.getDefault();
	String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

	String w3cCurrentLanguageId = LocaleUtil.toW3cLanguageId(currentLanguageId);
	String w3cDefaultLanguageId = LocaleUtil.toW3cLanguageId(defaultLanguageId);

	String metaRobots = layout.getTypeSettingsProperties().getProperty("meta-robots_" + currentLanguageId);
	String metaRobotsLanguageId = w3cCurrentLanguageId;

	if (Validator.isNull(metaRobots)) {
		metaRobots = layout.getTypeSettingsProperties().getProperty("meta-robots_" + defaultLanguageId);
		metaRobotsLanguageId = w3cDefaultLanguageId;
	}
	%>

	<c:if test="<%= Validator.isNotNull(metaRobots) %>">
		<meta name="robots" content="<%= HtmlUtil.escape(metaRobots) %>" lang="<%= metaRobotsLanguageId %>" />
	</c:if>

	<%
	String metaDescription = layout.getTypeSettingsProperties().getProperty("meta-description_" + currentLanguageId);
	String metaDescriptionLanguageId = w3cCurrentLanguageId;

	if (Validator.isNull(metaDescription)) {
		metaDescription = layout.getTypeSettingsProperties().getProperty("meta-description_" + defaultLanguageId);
		metaDescriptionLanguageId = w3cDefaultLanguageId;
	}

	String dynamicMetaDescription = (String)request.getAttribute(WebKeys.PAGE_DESCRIPTION);

	if (Validator.isNotNull(dynamicMetaDescription)) {
		StringBuilder sb = new StringBuilder();

		sb.append(dynamicMetaDescription);

		if (Validator.isNotNull(metaDescription)) {
			sb.append(StringPool.PERIOD);
			sb.append(StringPool.SPACE);
			sb.append(metaDescription);
		}

		metaDescription = sb.toString();
	}
	%>

	<c:if test="<%= Validator.isNotNull(metaDescription) %>">
		<meta name="description" content="<%= HtmlUtil.escape(metaDescription) %>" lang="<%= metaDescriptionLanguageId %>" />
	</c:if>

	<%
	String metaKeywords = layout.getTypeSettingsProperties().getProperty("meta-keywords_" + currentLanguageId);
	String metaKeywordsLanguageId = w3cCurrentLanguageId;

	if (Validator.isNull(metaKeywords)) {
		metaKeywords = layout.getTypeSettingsProperties().getProperty("meta-keywords_" + defaultLanguageId);
		metaKeywordsLanguageId = w3cDefaultLanguageId;
	}

	List<String> dynamicMetaKeywords = (List<String>)request.getAttribute(WebKeys.PAGE_KEYWORDS);

	if (dynamicMetaKeywords != null) {
		StringBuilder sb = new StringBuilder();

		sb.append(StringUtil.merge(dynamicMetaKeywords));

		if (Validator.isNotNull(metaKeywords)) {
			sb.append(StringPool.COMMA);
			sb.append(StringPool.SPACE);
			sb.append(metaKeywords);
		}

		metaKeywords = sb.toString();
	}
	%>

	<c:if test="<%= Validator.isNotNull(metaKeywords) %>">
		<meta name="keywords" content="<%= HtmlUtil.escape(metaKeywords) %>" lang="<%= metaKeywordsLanguageId %>" />
	</c:if>
</c:if>