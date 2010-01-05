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

<%@ page import="com.liferay.portal.model.ModelHintsConstants" %>

<%@ include file="/html/portal/alloy/base.css.jsp" %>
<%@ include file="/html/portal/alloy/form.css.jsp" %>
<%@ include file="/html/portal/alloy/layout.css.jsp" %>
<%@ include file="/html/portal/aui/css.jspf" %>

<%@ include file="/html/portal/css/portal/generic_portal.jspf" %>

/* ---------- Add content styles ---------- */

.portal-add-content-search {
	margin-bottom: 8px;
}

.portal-add-content .lfr-portlet-used {
	color: #ccc;
	cursor: default;
}

.portal-add-content .portlet-msg-info {
	color: #333;
	margin-bottom: 0;
	margin-top: 10px;
}

.portal-add-content .lfr-portlet-used a {
	display: none;
}

.lfr-add-content {
	margin-bottom: 0.5em;
}

.lfr-add-content.collapsed {
}

.lfr-add-content.expanded {
}

.lfr-add-content h2 {
	cursor: pointer;
	font-size: 1.1em;
	font-weight: bold;
	margin: 0;
}

.lfr-add-content.collapsed h2, .lfr-add-content .lfr-add-content.collapsed h2 {
	background: url(<%= themeImagesPath %>/arrows/01_plus.png) no-repeat 100% 50%;
	border: none;
}

.lfr-add-content.expanded h2, .lfr-add-content .lfr-add-content.expanded h2 {
	background: url(<%= themeImagesPath %>/arrows/01_minus.png) no-repeat 100% 50%;
}

.lfr-add-content h2 span {
	background: url(<%= themeImagesPath %>/add_content/portlet_category.png) no-repeat 0 50%;
	padding-left: 20px;
}

.lfr-install-more {
	border-top: 1px solid #ccc;
	margin: 10px 0 0;
	padding-top: 10px;
}

.lfr-install-more a {
	background: url(<%= themeImagesPath %>/common/install_more.png) no-repeat 0 50%;
	display: block;
	font-weight: bold;
	padding-left: 20px;
}

.lfr-content-category {
	padding-left: 10px;
	padding-top: 3px;
}

.lfr-content-category.hidden {
	display: none;
}

.lfr-content-category.visible {
	border-bottom: 1px solid #ddd;
	border-top: 1px solid #ddd;
	display: block;
}

.ie6 .lfr-content-category.visible {
	height: 1%;
}

.lfr-has-sidebar {
	padding-left: 270px;
}

/* ---------- Flyout ---------- */

.lfr-flyout ul {
	display: none;
}

.js li.lfr-flyout {
	display: block;
	position: relative;
}

.js .lfr-flyout-has-children {
	background: url(<%= themeImagesPath %>/arrows/04_left.png) no-repeat 5px 50%;
	padding-left: 12px;
}

.js .lfr-flyout-has-children.send-right {
	background-image: url(<%= themeImagesPath %>/arrows/04_right.png);
}

.js .lfr-flyout-has-children ul {
	min-width: 150px;
	position: absolute;
	right: 100%;
	top: -1px;
}

.js .lfr-flyout-has-children.send-right ul {
	left: 100%;
	right: auto;
}

/* ---------- Panel component ---------- */

.lfr-panel.lfr-extended, .lfr-panel-container, .lfr-floating-container {
	border: 1px solid;
	border-color: #DEDEDE #BFBFBF #BFBFBF #DEDEDE;
}

.lfr-panel-container, .lfr-floating-container {
	background: #fff;
	clear: both;
}

.lfr-floating-container {
	position: relative;
}

.lfr-panel-container .lfr-panel {
	border: none;
}

.lfr-panel .lfr-panel-titlebar {
	margin-bottom: 0.5em;
	overflow: hidden;
}

.lfr-panel.lfr-collapsible .lfr-panel-titlebar {
	background: url(<%= themeImagesPath %>/arrows/05_down.png) no-repeat 2px 50%;
	cursor: pointer;
	padding-left: 15px;
}

.lfr-panel-titlebar .lfr-panel-title {
	float: left;
	font-size: 1.2em;
	font-weight: bold;
	margin: 0;
}

.lfr-panel-titlebar .lfr-panel-button {
	background: url(<%= themeImagesPath %>/application/panel_header_toggler_close.png) no-repeat 0 0;
	display: none;
	float: right;
	height: 22px;
	width: 19px;
}

.lfr-extended.lfr-collapsible .lfr-panel-button {
	display: block;
}

.lfr-panel.lfr-collapsed .lfr-panel-titlebar .lfr-panel-button {
	background-image: url(<%= themeImagesPath %>/application/panel_header_toggler_open.png);
}

.lfr-panel-titlebar .lfr-panel-button:hover {
	background-position: 0 100%;
}

.lfr-panel.lfr-collapsed .lfr-panel-titlebar {
	background-image: url(<%= themeImagesPath %>/arrows/05_right.png);
	margin-bottom: 0;
}

.lfr-panel.lfr-extended .lfr-panel-titlebar {
	background: #d6d6d6 url(<%= themeImagesPath %>/application/panel_header.png) repeat-x 0 0;
	border-bottom: 1px solid #cdcdcd;
	line-height: 1.6;
	padding: 2px;
}

.ie6 .lfr-extended.lfr-collapsible .lfr-panel-titlebar {
	height: 1%;
}

.lfr-extended.lfr-collapsed .lfr-panel-titlebar {
	border-bottom: none;
}

.lfr-panel-container .lfr-extended.lfr-collapsible .lfr-panel-titlebar {
	border-top: 1px solid #cecece;
}

.lfr-panel.lfr-collapsed .lfr-panel-content {
	display: none;
}

.js .lfr-floating-container {
	position: absolute;
}

.js .lfr-floating-trigger {
	background-image: url(<%= themeImagesPath %>/arrows/05_down.png);
	background-position: 100% 50%;
	background-repeat: no-repeat;
	padding: 3px;
	padding-right: 15px;
	text-decoration: none;
}

.js .lfr-trigger-selected {
	background-color: #069;
	color: #fff;
}

.lfr-floating-container .col {
	float: left;
	margin-right: 10px;
	width: auto;
}

.lfr-floating-container .lfr-form-row {
	border: none;
}

.lfr-panel .lfr-panel-content .undo-queue {
	border-left: none;
	border-right: none;
	border-top: none;
	margin: -0.5em 0 10px;
}

/* ---------- Panel pagination ---------- */

.lfr-panel-content .aui-paginator-container .aui-paginator-page-container,
.lfr-panel-content .aui-paginator-container .lfr-paginator-next,
.lfr-panel-content .aui-paginator-container .lfr-paginator-prev {
	display: inline-block;
}

.lfr-panel-content .aui-paginator-container .lfr-paginator-next,
.lfr-panel-content .aui-paginator-container .lfr-paginator-prev {
	padding: 8px;
}

.lfr-panel-content .aui-paginator-container {
	background: #cfd2d5;
	border-bottom: 1px solid #dcdee0;
	border-top: 1px solid #dcdee0;
	text-align: center;
}

.lfr-panel-content .aui-paginator-page-container .aui-paginator-page-link {
	background: none;
	border: 1px solid #CFD2D5;
	display: inline-block;
	float: none;
	padding: 5px 10px;
	text-align: center;
	width: auto;
}

.lfr-panel-content .aui-paginator-page-container .aui-paginator-page-link.aui-paginator-current-page:hover {
	background: #5094d7 url(<%= themeImagesPath %>/application/current_page_bg.png) repeat-x 0 0;
	border-color: #31659c #396ea8 #4c8ccb;
	color: #fff;
	font-weight: bold;
}

.lfr-panel-content .aui-paginator-page-container .aui-paginator-page-link.aui-paginator-current-page {
	background: #99a7b3 url(<%= themeImagesPath %>/application/current_page_hover_bg.png) repeat-x 0 0;
	border-color: #6b7785 #7c8994 #919fa9;
	color: #fff;
	font-weight: bold;
}

.lfr-panel-content .aui-paginator-container {
	overflow: hidden;
}

/* ---------- Position helper ---------- */

.lfr-position-helper {
	position: absolute;
	z-index: 1000;
}


/* ---------- Auto row styles ---------- */

.lfr-form-row {
	border-bottom: 1px solid #CCC;
	margin-top: 10px;
	overflow: hidden;
	padding: 5px;
	padding-top: 1px;
}

.lfr-form-row:hover {
	background-color: #DFFCCB;
	border: solid #B2FF3A;
	border-width: 1px 0;
	padding-top: 0;
}

.lfr-form-row .aui-ctrl-holder {
	clear: none;
	float: left;
}

.lfr-form-row legend .field-label{
	float:left;
	margin-right: 10px;
}

.lfr-form-row .handle-sort-vertical {
	background: url(<%= themeImagesPath %>/application/handle_sort_vertical.png) no-repeat 0 50%;
	cursor: move;
	padding-left: 20px;
}

.lfr-form-row fieldset {
	border: none;
	margin: 0;
	padding: 0;
}

.aui-autorow {
	margin-bottom: 10px;
	position: relative;
}

.ie .aui-autorow {
	width: 100%;
}

.lfr-autorow-controls {
	bottom: 5px;
	position: absolute;
	right: 5px;
}

/* ---------- Undo manager ---------- */

.aui-undomanager .lfr-undo-queue {
	margin: 10px auto;
}

.lfr-action-undo {
	float: left;
}

.lfr-action-clear {
	float: right;
}

.aui-undomanager .lfr-queue-empty, .lfr-queue-single .lfr-action-clear {
	display: none;
}

/* ---------- Panel Page styles ---------- */

.lfr-panel-page .portal-add-content {
	padding: 0;
	padding-left: 4px;
}

.lfr-panel-page .panel-page-content {
	border-left: 1px solid #ccc;
	padding-left: 1em;
}

.lfr-panel-page .lfr-add-content h2 {
	border: 1px solid #ccc;
	border-right: none;
	padding: 1px;
}

.lfr-panel-page .lfr-add-content h2 span {
	background: #efefef;
	display: block;
	padding: 2px;
	padding-left: 5px;
}

.lfr-panel-page .lfr-add-content .lfr-content-category h2 {
	border: none;
	border-bottom: 1px solid #ccc;
}

.lfr-panel-page .lfr-add-content .lfr-content-category h2 span {
	background: none;
}

.lfr-panel-page.panel-page-frontpage .panel-page-content h2 {
	margin-top: 0;
}

/* ---------- Javascript template styles ---------- */

.lfr-template {
	display: none;
}

/* ---------- Portlet item ---------- */

.lfr-portlet-item {
	background: url(<%= themeImagesPath %>/add_content/portlet_item.png) no-repeat 0 50%;
	border: 1px solid #fff;
	cursor: move;
	font-size: 1.1em;
	margin-bottom: 3px;
	padding: 0 5px 0 20px;
}

.lfr-portlet-item.lfr-instanceable {
	background-image: url(<%= themeImagesPath %>/add_content/portlet_item_instanceable.png);
}

.lfr-portlet-item:hover, .lfr-portlet-item.over {
	background-color: #ffc;
	border-color: #fc0;
}

.ie .lfr-portlet-item {
	height: 1%;
}

.lfr-portlet-item p {
	font-size: 1em;
	margin: 0;
	padding-right: 30px;
	position: relative;
}

.lfr-portlet-item p a {
	cursor: pointer;
	font-size: 0.9em;
	font-weight: bold;
	position: absolute;
	right: 0;
	top: 0;
}

.ie .lfr-portlet-item p a {
	top: -2px;
}

.ie6 .lfr-portlet-item p a {
	right: 20px;
}

#layout_configuration_content {
	width: 95%;
}

/* ---------- Portlet css editor ---------- */

#portlet-set-properties .aui-form {
	clear: both;
}

#portlet-set-properties fieldset {
	margin-bottom: 1em;
}

#portlet-set-properties fieldset fieldset {
	margin-top: 1em;
}

#portlet-set-properties fieldset fieldset legend {
	font-size: 1.1em;
}

#portlet-set-properties .aui-form .text-input {
	margin-right: 5px;
	width: 80px;
}

#portlet-set-properties .aui-form fieldset.col {
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

#portlet-set-properties .aui-form #custom-css {
	height: 300px;
	width: 400px;
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

#portlet-set-properties .aui-form {
	clear: both;
}

/* ---------- Toolbar ---------- */

.lfr-toolbar {
	background-color: #e5e4e8;
	padding-top: 3px;
}

.lfr-toolbar:after {
	clear: both;
	content: ".";
	display: block;
	height: 0;
	visibility: hidden;
}

.ie .lfr-toolbar {
	height: 1%;
}

.ie6 .lfr-toolbar {
	width: 750px;
}

.lfr-toolbar .lfr-button, .lfr-emoticon-container .lfr-button {
	border: 1px solid #e5e4e8;
	cursor: pointer;
	margin: 0 2px;
	padding: 2px 4px;
}

.lfr-toolbar .lfr-button img {
	vertical-align: text-top;
}

.lfr-toolbar .lfr-button:focus {
	border: 1px solid #000;
}

.lfr-toolbar .lfr-button:hover {
	border-bottom-color: #777;
	border-right-color: #777;
	border: 1px solid #ccc;
}

.lfr-toolbar .lfr-separator {
	border-left: 1px solid #fff;
	border-right: 1px solid #ccc;
	font-size: 12px;
	height: 25px;
	margin: 0 5px;
}

.lfr-toolbar select, .lfr-toolbar .lfr-button, .lfr-toolbar .lfr-separator, .lfr-emoticon-container .lfr-button {
	float: left;
}

.lfr-toolbar select {
	margin: 0 5px;
}

.ie .lfr-toolbar select {
	margin-top: 3px;
}

.lfr-emoticon-container {
	background: #e5e4e8;
	border: 1px solid #ccc;
	padding-top: 5px;
	overflow: hidden;
	width: 180px;
}

.lfr-emoticon-container .lfr-button {
	margin: 0;
}

/* ---------- Tree ---------- */

.lfr-tree {
}

.lfr-tree .expand-image {
}

.lfr-tree a {
	text-decoration: none;
}

.lfr-tree li {
	margin-bottom: 2px;
	padding-left: 0;
}

.lfr-tree li ul {
}

.lfr-tree li ul li, .lfr-tree li.tree-item {
	padding-left: 0;
}

.lfr-tree li.tree-item {
	padding-left: 5px;
}

.lfr-tree li.tree-item li {
	padding-left: 20px;
}

.lfr-tree li.tree-item ul {
	margin-left: 0;
	margin-top: 5px;
}

<%@ include file="/html/portal/css/portal/uploader.jspf" %>
<%@ include file="/html/portal/css/portal/page_settings.jspf" %>
<%@ include file="/html/portal/css/portal/notifications.jspf" %>
<%@ include file="/html/portal/css/portal/asset_category_selector.jspf" %>
<%@ include file="/html/portal/css/portal/tag_selector.jspf" %>
<%@ include file="/html/portal/css/portal/generic_portlet.jspf" %>
<%@ include file="/html/portal/css/portal/forms.jspf" %>
<%@ include file="/html/portal/css/portal/separator.jspf" %>

<%@ include file="/html/portal/css/taglib/asset_categories_summary.jspf" %>
<%@ include file="/html/portal/css/taglib/calendar.jspf" %>
<%@ include file="/html/portal/css/taglib/captcha.jspf" %>
<%@ include file="/html/portal/css/taglib/diff.jspf" %>
<%@ include file="/html/portal/css/taglib/diff_html.jspf" %>
<%@ include file="/html/portal/css/taglib/discussion.jspf" %>
<%@ include file="/html/portal/css/taglib/flags.jspf" %>
<%@ include file="/html/portal/css/taglib/icon_list.jspf" %>
<%@ include file="/html/portal/css/taglib/icon_menu.jspf" %>
<%@ include file="/html/portal/css/taglib/input_localized.jspf" %>
<%@ include file="/html/portal/css/taglib/input_move_boxes.jspf" %>
<%@ include file="/html/portal/css/taglib/input_repeat.jspf" %>
<%@ include file="/html/portal/css/taglib/ratings.jspf" %>
<%@ include file="/html/portal/css/taglib/search_iterator.jspf" %>
<%@ include file="/html/portal/css/taglib/search_toggle.jspf" %>
<%@ include file="/html/portal/css/taglib/social_activities.jspf" %>
<%@ include file="/html/portal/css/taglib/social_bookmarks.jspf" %>
<%@ include file="/html/portal/css/taglib/tags_summary.jspf" %>
<%@ include file="/html/portal/css/taglib/user_display.jspf" %>
<%@ include file="/html/portal/css/taglib/webdav.jspf" %>

<%@ include file="/html/portal/css/portal/openid.jspf" %>
<%@ include file="/html/portal/css/portal/accessibility.jspf" %>