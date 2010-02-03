<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

.portlet-journal-content .icon-actions {
	float: left;
	margin: 1px 10px 1px 1px;
}

.portlet-journal-content .icons-container {
	clear: both;
	height: auto;
	margin-top: 1em;
	overflow: hidden;
	width: auto;
}

.ie6 .portlet-journal-content .icons-container {
	height: 1%;
}

.portlet-journal-content .journal-content-article {
	clear: right;
}

.portlet-journal-content .journal-content-article:after {
	clear: both;
	content: "";
	display: block;
	height: 0;
}

.ie .portlet-journal-content .journal-content-article {
	zoom: 1;
}

.portlet-journal-content .taglib-discussion {
	margin-top: 18px;
}

.portlet-journal-content .taglib-ratings-wrapper {
	margin-top: 1em;
}

.portlet-journal-content .aui-tabview-list {
	margin: 18px 0;
}

.portlet-journal-content .user-actions {
	padding-bottom: 2.5em;
}

.portlet-journal-content .user-actions .export-actions, .portlet-journal-content .user-actions .print-action, .portlet-journal-content .user-actions .locale-actions {
	float: right;
}

.portlet-journal-content .user-actions .print-action {
	margin-left: 1em;
}

.portlet-journal-content .user-actions .locale-separator {
	border-right: 1px solid #CCC;
	float: right;
	margin-right: 1em;
	padding: 0.8em 0.5em;
}