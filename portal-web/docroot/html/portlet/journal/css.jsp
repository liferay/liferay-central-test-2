<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/css_init.jsp" %>

.portlet-journal .journal-edit-article-extra {
	border: 1px solid #CCC;
	width: 100%;
}

.portlet-journal .journal-edit-article-extra tr td {
	padding: 5px;
}

.portlet-journal .journal-article-status-approved {
	color: green;
}

.portlet-journal .journal-article-status-expired {
	color: red;
}

.portlet-journal .journal-article-status-not-approved {
	color: orange;
}

.journal-template-error .scroll-pane {
	border: 1px solid #BFBFBF;
	max-height: 200px;
	min-height: 50px;
	overflow: auto;
	width: 100%;
}

.journal-template-error .scroll-pane .inner-scroll-pane {
	min-width: 104%;
}

.journal-template-error .scroll-pane .error-line {
	background: #fdd;
}

.journal-template-error .scroll-pane pre {
	margin: 0px;
	white-space: pre;
}

.journal-template-error .scroll-pane pre span {
	background: #B5BFC4;
	border-right: 1px solid #BFBFBF;
	display: block;
	float: left;
	margin-right: 4px;
	padding-right: 4px;
	text-align: right;
	width: 40px;
}