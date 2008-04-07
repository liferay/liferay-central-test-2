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

.portlet-alerts {
}

.portlet-alerts .entry {
	border: 6px solid #c00;
	margin: 8px 0px;
	padding: 6px;
}

.portlet-alerts .entry-scope {
	display: block;
}

.portlet-alerts .read-false .entry-title {
	color: #000;
	display: block;
	margin-bottom: 4px;
}

.portlet-alerts .read-true .entry-title {
	color: #CCC;
	display: block;
	font-size: 1.5em;
	font-weight: bold;
	margin: 0px 0px 4px 0px;
}

.portlet-alerts .read-false .entry-title .entry-url {
	color: #000;
}

.portlet-alerts .read-true .entry-title .entry-url {
	color: #CCC;
}

.portlet-alerts .entry-content {
	display: block;
	padding: 2px 2px 2px 30px;
}

.portlet-alerts .entry-scope {
	color: #CCC;
	display: block;
}

.portlet-alerts .edit-actions {
	float: right;
}

.portlet-alerts .entry-type-general {
	background: #fff url('<%= themeImagesPath %>/common/page.png') left top no-repeat;
}

.portlet-alerts .entry-type-news {
	background: #fff url('<%= themeImagesPath %>/common/page.png') left top no-repeat;
}

.portlet-alerts .entry-type-test {
	background: #fff url('<%= themeImagesPath %>/common/page.png') left top no-repeat;
}

.portlet-announcements {
}

.portlet-announcements .entry {
	border-bottom: 1px solid #ccc;
	margin: 4px 0px 1.2em;
	padding-bottom: 0.5em;
}

.portlet-announcements .entry.last {
	border: none;
}

.portlet-announcements .entry-scope {
	display: block;
}

.portlet-announcements .entry-title {
	display: block;
	margin-bottom: 0.8em;
	margin-right: 100px;
}

.portlet-announcements .read-false .entry-title {
}

.portlet-announcements .read-true .entry-title {
	opacity: 0.5;
}

.portlet-announcements .entry-content {
	display: block;
	margin-bottom: 0.5em;
	padding: 2px 2px 2px 30px;
}

.portlet-announcements .entry-scope {
	color: #555;
	display: block;
}

.portlet-announcements .edit-actions {
	float: right;
}

.portlet-announcements .delete-entry {
	padding-right: 2em;
}

.portlet-announcements .entry-type-general {
	background: #fff url('<%= themeImagesPath %>/common/all_pages.png') 4px 4px no-repeat;
}

.portlet-announcements .entry-type-news {
	background: #fff url('<%= themeImagesPath %>/common/page.png') 4px 4px no-repeat;
}

.portlet-announcements .entry-type-test {
	background: #fff url('<%= themeImagesPath %>/common/page.png') 4px 4px no-repeat;
}