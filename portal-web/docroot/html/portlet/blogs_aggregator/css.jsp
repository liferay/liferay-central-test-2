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

<%@ include file="/html/portlet/css_init.jsp" %>

.portlet-blogs-aggregator .abstract {
	clear: both;
	margin-top: 1.5em;
	margin-bottom: 1.5em;
}

.portlet-blogs-aggregator .comments {
	margin-top: 1.5em;
}

.portlet-blogs-aggregator .entry-info {
	line-height: 2em;
	overflow: hidden;
}

.ie .portlet-blogs-aggregator .entry-info {
	height: 1%;
}

.portlet-blogs-aggregator .entry-author {
	background: url(<%= themeImagesPath %>/portlet/edit_guest.png) no-repeat 0 50%;
	border-right: 1px solid #999;
	color: #999;
	float: left;
	font-weight: bold;
	margin-right: 10px;
	padding-left: 20px;
	padding-right: 10px;
}

.portlet-blogs-aggregator .entry-categories {
	border-left: 1px solid #999;
	float: left;
	padding-left: 10px;
}

.portlet-blogs-aggregator .entry-content {
}

.portlet-blogs-aggregator .entry-date {
	background: url(<%= themeImagesPath %>/common/date.png) no-repeat 0 50%;
	color: #999;
	float: left;
	overflow: hidden;
	padding-left: 25px;
	padding-right: 10px;
}

.portlet-blogs-aggregator .entry-tags {
	border-left: 1px solid #999;
	overflow: hidden;
	padding-left: 10px;
	padding-right: 10px;
}

.portlet-blogs-aggregator .entry-title {
	display: block;
	font-size: 1.2em;
	font-weight: bold;
	margin-bottom: 0.2em;
}

.portlet-blogs-aggregator .search-container {
	margin-top: 1.5em;
}