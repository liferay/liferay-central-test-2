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

.lfr-announcements {
}

.lfr-alerts {
}

.lfr-announcements .announcement {
	margin: 4px 0px;
}

.lfr-alerts .announcement {
	margin: 8px 0px;
	border: 6px solid #c00;
	padding: 6px;
}

.lfr-announcements .announcement-scope {
	display: block;
}

.lfr-alerts .announcement-scope {
	display: block;
}

.lfr-announcements .read-false .announcement-title {
	display: block;
	font-size: 1.6em;
	font-weight: bold;
	color: #000;
	margin: 0px 0px 4px 0px;
}

.lfr-alerts .read-false .announcement-title {
	display: block;
	font-size: 1.6em;
	font-weight: bold;
	color: #000;
	margin: 0px 0px 4px 0px;
}

.lfr-announcements .read-true .announcement-title {
	display: block;
	font-size: 1.5em;
	font-weight: bold;
	color: #CCC;
	margin: 0px 0px 4px 0px;
}

.lfr-alerts .read-true .announcement-title {
	display: block;
	font-size: 1.5em;
	font-weight: bold;
	color: #CCC;
	margin: 0px 0px 4px 0px;
}

.lfr-announcements .read-false .announcement-title .announcement-url {
	color: #000;
}

.lfr-alerts .read-false .announcement-title .announcement-url {
	color: #000;
}

.lfr-announcements .read-true .announcement-title .announcement-url {
	color: #CCC;
}

.lfr-alerts .read-true .announcement-title .announcement-url {
	color: #CCC;
}

.lfr-announcements .announcement-content {
	display: block;
	padding: 2px 2px 2px 30px;
}

.lfr-alerts .announcement-content {
	display: block;
	padding: 2px 2px 2px 30px;
}

.lfr-announcements .announcement-scope {
	display: block;
	color: #CCC;
}

.lfr-alerts .announcement-scope {
	display: block;
	color: #CCC;
}

.lfr-announcements .edit-actions {
	float: right;
}

.lfr-alerts .edit-actions {
	float: right;
}

.lfr-announcements .announcement-type-general {
	background: #fff url('<%= themeImagesPath %>/common/page.png') left top no-repeat;
}

.lfr-alerts .announcement-type-general {
	background: #fff url('<%= themeImagesPath %>/common/page.png') left top no-repeat;
}

.lfr-announcements .announcement-type-news {
	background: #fff url('<%= themeImagesPath %>/common/page.png') left top no-repeat;
}

.lfr-alerts .announcement-type-news {
	background: #fff url('<%= themeImagesPath %>/common/page.png') left top no-repeat;
}

.lfr-announcements .announcement-type-test {
	background: #fff url('<%= themeImagesPath %>/common/page.png') left top no-repeat;
}

.lfr-alerts .announcement-type-test {
	background: #fff url('<%= themeImagesPath %>/common/page.png') left top no-repeat;
}