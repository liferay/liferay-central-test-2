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

#header-bottom {
	background-color: #F6F8FB;
	height: 34px;
	margin: 0 0 10px;
}

#header-menu {
	background-color: #F3F5F5;
	font-size: 11px;
	line-height: 34px;
	margin: 0 0 10px;
	padding: 0 10px;
	text-align: right;
}

#header-title {
	background-color: #C1CABC;
	font-size: 20px;
	font-weight: 500;
	margin: 0 0 10px;
	padding: 7px 10px;
}

.avatar {
	clear: both;
	float: left;
	padding: 15px 0 5px;
}

.avatar img {
	display: block;
}

.avatar .change-avatar {
	display: block;
	text-align: center;
}

.ctrl-holder.primary-ctrl, .ctrl-holder.action-ctrl {
	margin: 1.8em 0;
}

.label-holder {
	font-weight: 700;
	padding: 15px 0 5px;
}

.radio-holder {
	line-height: 12px;
}

.form-navigation {
	background-color: #D7F1FF;
	border: 1px solid #88C5D9;
	float: right;
	margin: 0 0 0 15px;
	padding: 10px;
	width: 230px;
}

.form-navigation h3 {
	color: #036;
	font-size: 14px;
	font-weight: bold;
	margin: 0;
}

.form-navigation ul {
	margin-bottom: 10px;
}

.form-navigation li {
}

.form-navigation li a {
	cursor: pointer;
	display: block;
	padding: 2px 0 2px 5px;
}

.form-navigation li a:hover {
	background-color: #88C5D9;
	text-decoration: none;
}

.form-navigation li.selected {
	background: url(<%= themeImagesPath %>/control_panel/selected.png) no-repeat 0 0;
	font-weight: bold;
	margin: 0 0 0 -23px;
	padding: 0;
}

.form-navigation li.selected a {
	background-color: #5C696E;
	color: #FFF;
	display: block;
	margin: 0 0 0 15px;
	padding: 2px 10px;
	text-decoration: none;
}

.form-navigation .user-info, .form-navigation .organization-info {
	font-weight: bold;
	margin-bottom: 15px;
}

.form-navigation .user-info p span, .form-navigation .organization-info p span {
	color: #036;
	display: block;
	font-size: 14px;
}

.form-navigation .user-info .avatar, .form-navigation .organization-info .avatar {
	float: left;
	margin-right: 10px;
	padding: 0;
}

.form-navigation .button-holder {
	margin-top: 20px;
}

.form-row {
	border-bottom: 1px solid #CCC;
	margin-right: 10px;
	overflow: hidden;
	padding: 5px;
	padding-top: 1px;
	position: relative;
	width: 97%;
}

.form-row:hover {
	background-color: #DFFCCB;
	border-bottom: 1px solid #B2FF3A;
	border-top: 1px solid #B2FF3A;
	padding-top: 0;
}

.form-row .ctrl-holder {
	clear: none;
	float: left;
}

.form-row .row-controls {
	bottom: 5px;
	float: right;
	position: absolute;
	right: 0;
}

.form-row .row-controls a {
	background: url() no-repeat 2px 50%;
	display: block;
	float: left;
	font-size: 0;
	height: 16px;
	padding: 2px;
	text-indent: -9999em;
	width: 16px;
}

.form-row .row-controls .add-row {
	background-image: url(<%= themeImagesPath %>/common/add.png);
}

.form-row .row-controls .delete-row {
	background-image: url(<%= themeImagesPath %>/common/delete.png);
}

.form-section {
	display: none;
}

.form-section.selected {
	display: block;
	float: left;
	width: 63%;
}

.form-section h3 {
	border-bottom: 1px solid #000;
	clear: both;
	font-size: 14px;
	font-weight: 700;
	margin-top: 10px;
}

.portlet-msg-info.undo-queue {
	overflow: hidden;
}

.row-container {
	margin-top: -15px;
}

.undo-queue.queue-empty {
	visibility: hidden;
}

.undo-queue .undo-action {
	float: left;
}

.undo-queue .clear-undos {
	float: right;
}

.undo-queue.queue-single .clear-undos {
	display: none;
}

.uni-form .block-labels label, .uni-form .block-labels .label {
	font-weight: bold;
}

/*display: block;float: none;*/

}.uni-form .block-labels label input {
	vertical-align: middle;
}

.uni-form .block-labels .ctrl-holder {
	margin-bottom: 10px;
}

.uni-form fieldset {
	border: none;
	width: 100%;
}

.uni-form fieldset.col {
	margin-right: 10px;
	width: 32.7%;
}

.uni-form .row-container .form-row {
	margin-bottom: 10px;
}

.user-table, organization-table {
	border-collapse: collapse;
}

.lfr-portlet-toolbar {
	background: #F6F8FB;
	margin-bottom: 5px;
	overflow: hidden;
	padding: 2px;
}

.lfr-portlet-toolbar .lfr-toolbar-button a {
	background: url() no-repeat 5px 50%;
	border: 1px solid #F6F8FB;
	color: #9EA8AD;
	display: block;
	float: left;
	font-size: 12px;
	font-weight: bold;
	padding: 4px 14px 4px 29px;
	text-decoration: none;
}

.lfr-portlet-toolbar .lfr-toolbar-button a:hover {
	border: 1px solid #346799;
	color: #346799;
	padding: 4px 14px 4px 29px;
}

.ie6 .lfr-portlet-toolbar .lfr-toolbar-button a {
}

.lfr-portlet-toolbar .lfr-toolbar-button.current a {
	background-color: #CFD5D7;
	border: 1px solid #CFD5D7;
	color: #000;
}

.lfr-portlet-toolbar .lfr-toolbar-button.current a:hover {
	border: 1px solid #346799;
}

.lfr-portlet-toolbar .lfr-toolbar-button.add-button a {
	background-image: url(<%= themeImagesPath %>/common/add.png);
}

.lfr-portlet-toolbar .lfr-toolbar-button.custom-attributes-button a {
	background-image: url(<%= themeImagesPath %>/common/add.png);
}

.lfr-portlet-toolbar .lfr-toolbar-button.view-button a {
	background-image: url(<%= themeImagesPath %>/common/view_users.png);
}

.lfr-portlet-toolbar .lfr-toolbar-button.export-button a {
	background-image: url(<%= themeImagesPath %>/common/export.png);
}