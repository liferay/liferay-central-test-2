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

/* This CSS makes all the content scroll correctly when using position: absolute. */

.portlet-document-library .portlet-content-container {
	position: relative;
}

.ie6 .portlet-document-library .portlet-content-container {
	height: 1%;
}

/* This CSS overrides the styles for the Actions button. */

.portlet-document-library li .lfr-actions.visible {
	position: absolute;
}

/* This CSS sets the default styles for the windows explorer display style. */

.portlet-document-library .col-folders {
	left: 45%;
	line-height: 20px;
	overflow: hidden;
	position: absolute;
}

.portlet-document-library .col-documents {
	left: 65%;
	line-height: 20px;
	overflow: hidden;
	position: absolute;
}

.portlet-document-library .col-name {
	left: 12px;
	line-height: 20px;
	position: absolute;
}

.portlet-document-library .folder-image {
	cursor: pointer;
	padding-top: 2px;
	vertical-align: bottom;
}

.portlet-document-library .expand-image {
	cursor: pointer;
	padding-top: 2px;
	vertical-align: length;
}

.ie .portlet-document-library .folder-image, .ie .portlet-document-library .expand-image {
	vertical-align: middle;
}

.portlet-document-library .header {
	background-color: #E6E6E6;
	font-weight: bold;
	line-height: 20px;
	height: 20px;
}

.portlet-document-library .list-item {
	padding: 8px 0 0 20px;
}

.portlet-document-library .top-row {
	border-bottom: 1px solid #E6E6E6;
	padding: 5px 0 8px 0;
}

.portlet-document-library .upload-file {
	background-image: url(<%= themeImagesPath %>/document_library/page.png);
}