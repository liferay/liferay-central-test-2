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

.portlet-directory .section {
	float: left;
	margin-left: 10px;
	width: 47%;
}

.portlet-directory .section h3 {
	background: url() no-repeat 0 50%;
	border-bottom: 1px solid #CCC;
	line-height: 1.5;
	margin-bottom: 0.5em;
	padding-left: 25px;
}

.portlet-directory .section li {
	list-style: none;
	margin: 0;
	padding-left: 25px;
}

.portlet-directory .section ul {
	margin: 0;
}

.portlet-directory .user-sms h3 {
	background-image: url(<%= themeImagesPath %>/common/telephone_mobile.png);
}

.portlet-directory .user-additional-email-addresses h3 {
	background-image: url(<%= themeImagesPath %>/mail/unread.png);
}

.portlet-directory .user-addresses .mailing-name {
	display: block;
	font-style: italic;
}

.portlet-directory .user-addresses .primary {
	background-position: 3px 5px;
}

.portlet-directory .user-addresses h3 {
	background-image: url(<%= themeImagesPath %>/dock/home.png);
}

.portlet-directory .user-details {
	clear: both;
}

.portlet-directory .user-instant-messenger h3 {
	background-image: url(<%= themeImagesPath %>/common/conversation.png);
}

.portlet-directory .user-phones h3 {
	background-image: url(<%= themeImagesPath %>/common/telephone.png);
}

.portlet-directory .user-social-network h3 {
	background-image: url(<%= themeImagesPath %>/common/group.png);
}

.portlet-directory .user-websites h3 {
	background-image: url(<%= themeImagesPath %>/common/history.png);
}

.portlet-directory .details .avatar {
	float: left;
	width: 100px;
}

.portlet-directory .details dd {
	margin-bottom: 0.8em;
}

.portlet-directory .details dl {
	margin-left: 115px;
}

.portlet-directory .details dt, .portlet-directory .details dd {
	clear: both;
}

.portlet-directory .details dt {
	font-weight: bold;
	line-height: 1.1;
	margin-bottom: 0;
}

.portlet-directory .details {
	overflow: hidden;
}

.ie6 .portlet-directory .user-information, .ie6 .portlet-directory .organization-information {
	height: 1%;
}

.ie6 .portlet-directory {
	height: 1%;
}

.ie6 .portlet-directory dl.property-list {
	height: 1%;
}

.portlet-directory .primary {
	background: #EEE url(<%= themeImagesPath %>/dock/my_place_current.png) no-repeat 3px 50%;
	color: #020509;
	font-weight: bold;
	margin-bottom: 10px;
	padding: 5px;
	padding-left: 10px;
}

.portlet-directory .property-list dd img {
	vertical-align: middle;
}

.portlet-directory .property-list dd, .portlet-directory .property-list dd {
	padding-left: 5px;
}

.portlet-directory .property-list dt, .portlet-directory .property-list dd, .portlet-directory .property-list li {
	margin-bottom: 5px;
}

.portlet-directory .property-list dt, .portlet-directory .property-list dd {
	float: left;
	line-height: 1.5;
	margin: 0;
}

.portlet-directory .property-list dt {
	clear: left;
	font-weight: bold;
	min-width: 5em;
}

.portlet-directory .user-information, .organization-information {
	overflow: hidden;
}

.portlet-directory dl.property-list {
	margin-top: 0;
	overflow: hidden;
	padding: 0;
}

.portlet-directory table.org-labor-table td {
	background-color: #EFEFEF;
	padding: 5px;
	text-align: center;
}

.portlet-directory table.org-labor-table td.no-color {
	background-color: #FFF;
}

.portlet-directory table.org-labor-table th {
	background-color: #999;
	color: white;
	padding: 1px 5px 1px 5px;
}

.portlet-directory table.org-labor-table {
	border: 1px solid white;
	margin-bottom: 30px;
	margin-top: 10px;
}