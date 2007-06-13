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

/* ---------- Interactive Dock ---------- */

.js .lfr-dock.interactive-mode {
	float: right;
	min-width: 150px;
	position: relative;
	right: 10px;
	top: 10px;
}

.js .lfr-dock.interactive-mode h2 {
	background: url(<%= themeDisplay.getPathThemeImages() %>/dock/menu_bar.png) no-repeat 100% -30px;
	font-size: 1.2em;
	margin-bottom: 0;
	padding: 0 29px 0 0;
	position: relative;
	z-index: 82;
}

.js .lfr-dock.interactive-mode h2 span {
	background: url(<%= themeDisplay.getPathThemeImages() %>/dock/menu_bar.png) no-repeat 0 0;
	display: block;
	font-size: 1.2em;
	margin-bottom: 0;
	padding: 0.5em 0.5em 0.5em 2em;
}

.js .lfr-dock.expanded .lfr-dock-list-container {
	border-top: none;
	top: -2px;
}

.js .lfr-dock.interactive-mode ul {
	background: url(<%= themeDisplay.getPathThemeImages() %>/dock/menu_bg.png) no-repeat 0 0;
	display: none;
	float: none;
}

.js .lfr-dock.interactive-mode li {
	display: block;
	float: none;
	margin-bottom: 0pt;
	margin-left: 0.2em;
}

.js .lfr-dock.interactive-mode li a {
	background-position: 0.5em 50%;
	background-repeat: no-repeat;
	border-top: 1px solid #ccc;
	display: block;
	margin-left: 0.3em;
	padding: 0.5em 0pt 0.5em 2.5em;
	text-decoration: none;
}

.js .lfr-dock.interactive-mode ul.lfr-dock-list > li:first-child a, .js .lfr-dock.interactive-mode ul.lfr-dock-list > li:first-child ul.show-my-places li:first-child a {
	border-top: none;
}

.js .lfr-dock.interactive-mode li a:hover {
	background-position: 1.5em 50%;
	padding-left: 3.5em;
}

.js .lfr-dock.interactive-mode ul.lfr-dock-list > li:first-child a, .js .lfr-dock.interactive-mode ul.lfr-dock-list > li:first-child a:hover, .js .lfr-dock.interactive-mode ul.lfr-dock-list li.my-places ul.show-my-places li ul li:first-child a, .js .lfr-dock.interactive-mode ul.lfr-dock-list li.my-places ul.show-my-places li ul li:first-child a:hover {
	border-top: none;
}

.js .my-places {
	display: none;
}

.js .lfr-dock.interactive-mode li.my-places li a {
	background-image: none;
}

.js .lfr-dock.interactive-mode li.my-places li ul li.current a, .js .lfr-dock.interactive-mode li.my-places li ul li.current a:hover {
	background: transparent url(<%= themeDisplay.getPathThemeImages() %>/dock/my_place_current.png) no-repeat 1em 50%;
	margin: 0.1em 0 0 0.2em;
	padding-left: 3.5em;
}

.js .interactive-mode .my-places .show-my-places {
	display: block;
	min-width: 200px;
	padding: 0.2em;
	position: absolute;
	right: 85%;
	top: 87%;
}

.js .interactive-mode .my-places.send-right .show-my-places {
	left: 100%;
	top: -10px;
}

.js .interactive-mode .my-places.send-down .show-my-places {
	left: 0;
	top: 100%;
}

.js .interactive-mode .my-places .show-my-places li {
	background: url() no-repeat 5px 50%;
	margin: 0;
}

.js .interactive-mode .my-places .show-my-places li .my-places-toggle {
	background: url(<%= themeDisplay.getPathThemeImages() %>/dock/my_place.png) no-repeat 3px 50%;
	font-size: 1.1em;
	font-weight: bold;
	padding: 0.5em 0.2em 0.5em 2em;
}

.js .interactive-mode .my-places .show-my-places li .my-places-toggle h3 {
	background: url(<%= themeDisplay.getPathThemeImages() %>/dock/expand_community.png) no-repeat 100% 50%;
	padding-right: 1.8em;
}

.js .interactive-mode .my-places .show-my-places li .my-places-toggle.hide h3 {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/dock/collapse_community.png);
}

.js .interactive-mode .my-places .show-my-places li ul {
	background: none no-repeat 3px 50%;
	border: none;
	display: none;
}

.js .interactive-mode .my-places .show-my-places li ul {
	margin-left: 0.5em;
}

.js .interactive-mode .my-places .show-my-places li ul li {
	position: relative;
}

.js .interactive-mode .my-places .show-my-places ul li.public a, .js .my-places .show-my-places ul li.public a:hover {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/dock/my_places_public.png);
}

.js .interactive-mode .my-places .show-my-places li.private a, .js .my-places .show-my-places li.private a:hover {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/dock/my_places_private.png);
}

.js .interactive-mode .my-places .show-my-places ul li.public a.add-page, .js .interactive-mode .my-places .show-my-places ul li.private a.add-page {
	background: url(<%= themeDisplay.getPathThemeImages() %>/dock/page_settings.png) no-repeat;
	border: none;
	display: block;
	font-size: 0;
	height: 16px;
	padding: 0;
	position: absolute;
	right: 3px;
	text-indent: -9999em;
	top: 3px;
	width: 16px;
}
.js .interactive-mode .my-places .show-my-places ul li.public a.add-page:hover, .js .interactive-mode .my-places .show-my-places ul li.private a.add-page:hover {
	background: url(<%= themeDisplay.getPathThemeImages() %>/dock/page_settings.png) no-repeat;
	padding: 0;
}

.ie6.js .lfr-dock.interactive-mode {
	white-space: nowrap;
	width: 150px;
}

.ie.js .lfr-dock .my-places .show-my-places h3 {
	font-size: 1.1em;
}

.ie6.js .lfr-dock.expanded.interactive-mode .lfr-dock-list-container {
	height: 1%;
}

.ie6.js .interactive-mode .my-places .show-my-places {
	width: 200px;
}

.ie6.js .lfr-dock.interactive-mode li {
	height: 1%;
}

.ie6.js .lfr-dock.interactive-mode li a {
	height: 1%;
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
	border-collapse: collapse;
	clear: both;
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