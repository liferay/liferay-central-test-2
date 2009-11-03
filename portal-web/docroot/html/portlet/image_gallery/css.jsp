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

<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.portal.kernel.util.StringUtil" %>

<liferay-util:buffer var="html">
	<liferay-util:include page="/html/portlet/document_library/css.jsp" />
</liferay-util:buffer>

<%
html = StringUtil.replace(html, "documentLibraryPanels", "imageGalleryPanels");
html = StringUtil.replace(html, "file-entries", "images");
html = StringUtil.replace(html, "file-entry", "image");
html = StringUtil.replace(html, "portlet-document-library", "portlet-image-gallery");
%>

<%= html %>

.portlet-image-gallery .image-popup .image-content {
	margin-top: 16px;
	text-align: center;
}

.portlet-image-gallery .image-popup .image-content .image-categorization {
	margin-bottom: 20px;
}

.portlet-image-gallery .image-popup .image-content .image-categorization .has-tags .taglib-asset-categories-summary {
	border-right: 1px solid #ccc;
	margin-right: 20px;
	padding-right: 10px;
}

.portlet-image-gallery .image-popup .image-content .image-name {
	font-weight: bold;
}

.portlet-image-gallery .image-popup .image-content .image-description {
	font-style: italic;
}

.portlet-image-gallery .image-popup .taglib-icon-list {
	float: right;
}

.portlet-image-gallery .image-popup .taglib-icon-list li {
	margin-left: 1em;
	margin-right: 0;
}

.portlet-image-gallery .image-score {
	display: block;
	margin: 0 0 5px 35px;
}

.portlet-image-gallery .image-thumbnail {
	border: 2px solid #EEEEEE;
	float: left;
	margin: 20px 4px 0;
	padding: 5px 5px 0;
	text-align: center;
	text-decoration:none;
}

.portlet-image-gallery .image-thumbnail:hover {
	border-color: #CCCCCC;
}

.portlet-image-gallery .image-date {
	float: none;
	border: none;
}

.portlet-image-gallery .image-download .image-name {
	top: 0;
}

.portlet-image-gallery .image-title {
	display: block;
}