<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portal/init.jsp" %>

<%
response.setContentType(Constants.TEXT_CSS);
%>

<%@ include file="/html/portal/css_cached_uniform.jsp" %>

/* ---------- Modules ---------- */

/* ---------- Add content styles ---------- */

.portal-add-content-search {
	margin-bottom: 8px;
}

.portal-add-content img{
	vertical-align: middle;
}

.portal-add-content table td{
	padding: 0 5px;
}

.portal-add-content table td{
	padding: 0 5px;
}

.portal-add-content table td:first-child, .portal-add-content table td.first-child {
	padding-left: 0;
}

.portal-add-content table td:last-child, .portal-add-content table td.last-child {
	padding-right: 0;
}

/* ---------- Color picker ---------- */

.lfr-color-picker {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/color_picker/picker_container_bg.png);
	font-family: Arial,Helvetica,sans-serif;
	font-size: 11px;
	height: 192px;
	position: relative;
	width: 305px;
}

.lfr-color {
	background-color: #F00;
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/color_picker/picker_bg.png);
	background-position: center;
	background-repeat: no-repeat;
	height: 184px;
	left: 8px;
	position: absolute;
	top: 5px;
	width: 184px;
}

.lfr-color-indic {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/color_picker/select.gif);
	height: 11px;
	position: absolute;
	width: 11px;
}

.lfr-hue {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/color_picker/hue.png);
	height: 186px;
	left: 200px;
	position: absolute;
	top: 3px;
	width: 18px;
}

.lfr-hue-indic {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/color_picker/color_indic.gif);
	height: 7px;
	position: absolute;
	width: 18px;
}

.lfr-current-color {
	border: 2px solid #999;
	height: 60px;
	position: absolute;
	right: 10px;
	top: 38px;
	width: 60px;
}

.lfr-color-values {
	left: 240px;
	position: absolute;
	top: 80px;
}

.lfr-color-values input {
	font-size: 11px;
	padding: 1px;
}

.lfr-old-color {
	background-color: #fff;
	height: 50%;
	position: absolute;
	top: 50%;
	width: 100%;
}

.lfr-rgbR, .lfr-rgbG, .lfr-rgbB {
	width: 20px;
}

.lfr-hex {
	width: 40px;
}

.lfr-color-picker-close {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/color_picker/close.png);
	cursor: pointer;
	height: 30px;
	position: absolute;
	right: 5px;
	text-indent: -9999em;
	top: 0;
	width: 30px;
}

/* ---------- Portlet css editor ---------- */

#portlet-set-properties {
	display: none;
}

#portlet-set-properties .uni-form {
	clear: both;
}

#portlet-set-properties fieldset {
	margin: 0 0 1em;
}

#portlet-set-properties fieldset fieldset {
	margin-top: 1em;
}

#portlet-set-properties fieldset fieldset legend {
	font-size: 1.1em;
}

#portlet-set-properties .uni-form .text-input {
	width: 80px;
	margin-right: 5px;
}

#portlet-set-properties .uni-form fieldset.col {
	width: 29%;
}

#portlet-set-properties .common {
	width: 27%;
}

#portlet-set-properties .extra {
	width: 20%;
}

#portlet-set-properties #lfr-border-width, #portlet-set-properties #lfr-border-style, #portlet-set-properties #lfr-border-color {
	float: left;
}

#portlet-set-properties #lfr-border-width {
	width: 25%;
}

#portlet-set-properties #lfr-border-style {
	width: 15%;
}

#portlet-set-properties #lfr-border-color {
	width: 20%;
}

#portlet-set-properties #lfr-padding, #portlet-set-properties #lfr-margin {
	width: 25%;
}

#portlet-set-properties .uni-form #custom-css {
	width: 400px;
	height: 300px;
}
#portlet-set-properties .form-hint {
	float: none;
}
#portlet-set-properties .lfr-bg-image-properties {
	display: none;
}
#portlet-set-properties #border-note {
	display: none;
	margin-top: 10px;
}

#portlet-set-properties .uni-form {
	clear: both;
}

/* ---------- Tree ---------- */

ul.gamma {
}

ul.gamma .expand-image {
}

ul.gamma a {
	text-decoration: none;
}

ul.gamma li {
	margin-bottom: 2px;
	padding-left: 0;
}

ul.gamma li ul {
}

ul.gamma li ul li, ul.gamma li.tree-item {
	padding-left: 0;
}

ul.gamma img {
	vertical-align: middle;
}

ul.gamma li.tree-item {
	padding-left: 5px;
}

ul.gamma li.tree-item a img {
	cursor: move;
}

ul.gamma li.tree-item li {
	padding-left: 20px;
}

ul.gamma li.tree-item ul {
	margin-left: 0;
	margin-top: 5px;
}

ul.gamma li.tree-item a, ul.gamma li.tree-item .expand-image {
	cursor: pointer;
}

ul.gamma .tree-item-hover {
	background: #7D93C1;
	padding: 5px;
}

.toggle-expand {
	padding-bottom: 10px;
}

.toggle-expand a {
	padding: 2px 0 2px 20px;
}

#lfr-expand {
	background: url(<%= themeDisplay.getPathThemeImages() %>/trees/expand_all.png) no-repeat 0 50%;
}

#lfr-collapse {
	background: url(<%= themeDisplay.getPathThemeImages() %>/trees/collapse_all.png) no-repeat 0 50%;
}

/* ---------- Portlets ---------- */

/* ---------- Generic styling ---------- */

.breadcrumbs {
	margin-bottom: 10px;
	margin-left: 5px;
}

.liferay-table {
	border-collapse: collapse
}

.liferay-table tr td {
	padding: 0 5px;
}

.liferay-table tr td:first-child, .liferay-table tr td.first-child {
	padding-left: 0;
}

.liferay-table tr td:last-child, .liferay-table tr td.last-child {
	padding-right: 0;
}

/* ---------- Liferay forms ---------- */

.liferay-input-text {
	width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;
}

.liferay-textarea {
	height: <%= ModelHintsDefaults.TEXTAREA_DISPLAY_HEIGHT %>px;
	width: <%= ModelHintsDefaults.TEXTAREA_DISPLAY_WIDTH %>px;
}

/* ---------- Separator ---------- */

.separator {
	margin: 15px auto;
}

/* ---------- Taglib discussion thread ---------- */

.taglib-discussion td img {
	vertical-align: middle;
}

/* ---------- Specific portlet styles ---------- */

/* ---------- Quick note ---------- */

.portlet-quick-note {
	margin: 2px;
	padding: 5px;
}

.portlet-quick-note textarea {
	min-height: 100px;
	padding: 3px;
	width: 95%;
}

.ie6 .portlet-quick-note textarea {
	height: expression(this.height < 100 ? '100px' : this.height);
}

.portlet-quick-note .note-color {
	border: 1px solid;
	cursor: pointer;
	float: left;
	font-size: 0;
	height: 10px;
	margin: 3px 5px;
	width: 10px;
}

.portlet-quick-note .note-color.yellow {
	background-color: #ffc;
	border-color: #fc0;
	margin-left: 0;
}

.portlet-quick-note .note-color.green {
	background-color: #cfc;
	border-color: #0c0;
}

.portlet-quick-note .note-color.blue {
	background-color: #ccf;
	border-color: #309;
}

.portlet-quick-note .note-color.red {
	background-color: #fcc;
	border-color: #f00;
}

.portlet-quick-note a.close-note {
	float: right;
}

/* ---------- jQuery plugins ---------- */

/* ---------- Tabs ---------- */

.tabs-hide {
	display: none;
}

.ie6 .tabs-nav {
	display: inline-block;
}

.ie6 .tabs-nav .tabs-disabled a {
	filter: alpha(opacity=40);
}