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

.portlet-image-gallery .image-thumbnail {
	float: left;
	margin: 20px 10px 0;
}

.image-popup .image-content {
	margin-top: 16px;
	text-align: center;
}

.image-popup .image-content .image-categorization {
	margin-bottom: 20px;
}

.image-popup .image-content .image-categorization .has-tags .taglib-asset-categories-summary {
	border-right: 1px solid #ccc;
	margin-right: 20px;
	padding-right: 10px;
}

.image-popup .image-content .image-name {
	font-weight: bold;
}
.image-popup .image-content .image-description {
	font-style: italic;
}

.image-popup .taglib-icon-list {
	float: right;
}

.image-popup .taglib-icon-list li {
	margin-left: 1em;
	margin-right: 0;
}