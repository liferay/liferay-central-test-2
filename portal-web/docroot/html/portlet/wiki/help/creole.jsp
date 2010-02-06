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
//italics//
**bold**
</pre>

<h4>
	<liferay-ui:message key="headers" />
</h4>

<pre>
== Large heading ==
=== Medium heading ===
==== Small heading ====
</pre>

<h4>
	<liferay-ui:message key="links" />
</h4>

<pre>
[[Link to a page]]
[[http://www.liferay.com|Link to website]]
</pre>

<h4>
	<liferay-ui:message key="lists" />
</h4>

<pre>
* Item
** Subitem
# Ordered Item
## Ordered Subitem
</pre>

<h4>
	<liferay-ui:message key="images" />
</h4>

<pre>
{{attached-image.png}}
{{Page Name/other-image.jpg|label}}
</pre>

<h4>
	<liferay-ui:message key="other" />
</h4>

<pre>
&lt;&lt;TableOfContents&gt;&gt;
{{{ Preformatted }}}
</pre>