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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<aui:input name="filesCount" type="hidden" value="3" />

<aui:fieldset>
	<aui:input helpMessage="import-wiki-pages-help" label="pages-file" name="file0" type="file" />

	<aui:input helpMessage="import-wiki-users-help" label='<%= LanguageUtil.get(pageContext, "users-file") + "(" + LanguageUtil.get(pageContext, "optional") + ")" %>' name="file1" type="file" />

	<aui:input helpMessage="import-wiki-images-help" label='<%= LanguageUtil.get(pageContext, "images-file") + "(" + LanguageUtil.get(pageContext, "optional") + ")" %>' name="file2" type="file" />

	<aui:input label='<%= WikiPageConstants.FRONT_PAGE + "(" + LanguageUtil.get(pageContext, "optional") + ")" %>' name="<%= WikiImporterKeys.OPTIONS_FRONT_PAGE %>" type="text" size="40" value="Main Page" />

	<aui:input checked="<%= true %>" inlineLabel="left" label="import-only-the-latest-version-and-not-the-full-history" name="<%= WikiImporterKeys.OPTIONS_IMPORT_LATEST_VERSION %>" type="checkbox" />
</aui:fieldset>