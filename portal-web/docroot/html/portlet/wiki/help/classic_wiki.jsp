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

<h4>
	<liferay-ui:message key="text-styles" />
</h4>

<pre>
'quoted'
''italics''
'''bold'''
monospaced
</pre>

<h4>
	<liferay-ui:message key="headers" />
</h4>

<pre>
= Header 1 =
== Header 2 ==
=== Header 3 ===
</pre>

<h4>
	<liferay-ui:message key="links" />
</h4>

<pre>
CamelCaseWordsAreLinksToPages
[http://www.liferay.com Liferay's Website]
</pre>

<h4>
	<liferay-ui:message key="lists" />
</h4>

<pre>
<img alt="<liferay-ui:message key="tab" />"src="<%= themeDisplay.getPathThemeImages() %>/wiki/tab.png" />* Item
<img alt="<liferay-ui:message key="tab" />" src="<%= themeDisplay.getPathThemeImages() %>/wiki/tab.png" />&nbsp;<img alt="<liferay-ui:message key="tab" />" src="<%= themeDisplay.getPathThemeImages() %>/wiki/tab.png" />* Subitem

<img alt="<liferay-ui:message key="tab" />" src="<%= themeDisplay.getPathThemeImages() %>/wiki/tab.png" />1 Ordered Item
<img alt="<liferay-ui:message key="tab" />" src="<%= themeDisplay.getPathThemeImages() %>/wiki/tab.png" />&nbsp;<img alt="<liferay-ui:message key="tab" />" src="<%= themeDisplay.getPathThemeImages() %>/wiki/tab.png" />1 Ordered Subitem
</pre>