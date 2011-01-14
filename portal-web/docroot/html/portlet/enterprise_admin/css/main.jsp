<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/html/portlet/css_init.jsp" %>

.portlet-enterprise-admin .aui-form .aui-fieldset textarea {
	width: 90%;
}

.portlet-enterprise-admin #header-bottom {
	background-color: #F6F8FB;
	height: 34px;
	margin: 0 0 10px;
}

.portlet-enterprise-admin #header-menu {
	background-color: #F3F5F5;
	font-size: 11px;
	line-height: 34px;
	margin: 0 0 10px;
	padding: 0 10px;
	text-align: right;
}

.portlet-enterprise-admin #header-title {
	background-color: #C1CABC;
	font-size: 20px;
	font-weight: 500;
	margin: 0 0 10px;
	padding: 7px 10px;
}

.portlet-enterprise-admin .avatar {
	border: 1px solid #88C5D9;
	clear: both;
	width: 100px;
}

.portlet-enterprise-admin .avatar img {
	display: block;
}

.portlet-enterprise-admin .change-avatar img {
	display: block;
	margin: 10px auto;
}

.portlet-enterprise-admin .lfr-change-logo img {
	border-width: 0;
	display: block;
	width: auto;
}

.portlet-enterprise-admin .company-logo {
	border: none;
	width: 100px;
}

.portlet-enterprise-admin .aui-field.action-ctrl, .portlet-enterprise-admin .aui-field.mailing-ctrl, .portlet-enterprise-admin .aui-field.primary-ctrl {
	margin: 1.8em 0;
}

.portlet-enterprise-admin .email-user-add .password-changed-notification {
	display: none;
}

.portlet-enterprise-admin .label-holder {
	font-weight: 700;
	padding: 15px 0 5px;
}

.portlet-enterprise-admin .organization-information {
	overflow: hidden;
}

.ie6 .portlet-enterprise-admin .organization-information {
	height: 1%;
}

.portlet-enterprise-admin .organization-search {
	float: right;
	margin: 0 0 0.5em 0.5em;
}

.portlet-enterprise-admin .organizations-list-view-icon {
	float:right;
}

.portlet-enterprise-admin .section {
	float: left;
	margin-left: 10px;
	width: 47%;
}

.portlet-enterprise-admin .section h3 {
	background: url() no-repeat scroll 2px 50%;
	border-bottom: 1px solid #CCC;
	line-height: 1.5;
	margin-bottom: 0.5em;
	padding-left: 25px;
}

.portlet-enterprise-admin .section li {
	list-style: none;
	margin: 0;
	padding-left: 25px;
}

.portlet-enterprise-admin .section ul {
	margin: 0;
}

.portlet-enterprise-admin .entity-addresses .mailing-name {
	display: block;
	font-style: italic;
}

.portlet-enterprise-admin .entity-addresses .primary {
	background-position: 3px 5px;
}

.portlet-enterprise-admin .entity-details {
	clear: both;
}

.portlet-enterprise-admin .entity-addresses h3 {
	background-image: url(<%= themeImagesPath %>/dock/home.png);
}

.portlet-enterprise-admin .entity-comments h3 {
	background-image: url(<%= themeImagesPath %>/dock/welcome_message.png);
}

.portlet-enterprise-admin .entity-email-addresses h3 {
	background-image: url(<%= themeImagesPath %>/mail/unread.png);
}

.portlet-enterprise-admin .entity-phones h3 {
	background-image: url(<%= themeImagesPath %>/common/telephone.png);
}

.portlet-enterprise-admin .entity-websites h3 {
	background-image: url(<%= themeImagesPath %>/common/history.png);
}

.portlet-enterprise-admin .radio-holder {
	line-height: 12px;
}



.portlet-enterprise-admin .form-navigator .user-info, .portlet-enterprise-admin .form-navigator .organization-info, .portlet-enterprise-admin .form-navigator .company-info {
	font-weight: bold;
	margin-bottom: 15px;
}

.portlet-enterprise-admin .form-navigator .user-info .user-name, .portlet-enterprise-admin .form-navigator .organization-info .organization-name, .portlet-enterprise-admin .form-navigator .company-info .company-name {
	color: #036;
	display: block;
	font-size: 14px;
}

.portlet-enterprise-admin .form-navigator .user-info .avatar, .portlet-enterprise-admin .form-navigator .organization-info .avatar {
	float: left;
	margin-right: 10px;
	padding: 0;
	width: 35px;
}

.portlet-enterprise-admin .instant-messenger, .portlet-enterprise-admin .social-network {
	clear: both;
	overflow: hidden;
}

.portlet-enterprise-admin .instant-messenger img, .portlet-enterprise-admin .social-network img {
	margin-left: 1em;
	margin-top: 1.8em;
}

.portlet-enterprise-admin .instant-messenger .aui-field, .portlet-enterprise-admin .social-network .aui-field {
	float: left;
}

.portlet-enterprise-admin .portrait-icons {
	margin-bottom: 20px;
	text-align: center;
	width: 200px;
}

.portlet-enterprise-admin table.org-labor-table {
	margin-bottom: 30px;
}

.portlet-enterprise-admin .aui-form .aui-fieldset table.org-labor-table td .aui-field {
	margin-bottom: 0;
	padding: 3px 1px;
}

.portlet-enterprise-admin .aui-form .aui-fieldset label, .portlet-enterprise-admin .aui-form .aui-fieldset .label {
	font-weight: bold;
}

.portlet-enterprise-admin .aui-form .aui-fieldset label input {
	vertical-align: middle;
}

.portlet-enterprise-admin .aui-form .aui-fieldset .aui-field {
	margin-bottom: 10px;
}

.portlet-enterprise-admin .aui-form .aui-fieldset .aui-field.mailing-ctrl {
	clear: both;
}

.portlet-enterprise-admin .aui-form .aui-fieldset .aui-field.mailing-ctrl span {
	margin-right: 0.5em;
}

.portlet-enterprise-admin .aui-form .aui-fieldset .aui-field.localized-language-selector {
	margin-bottom: 0;
}

.portlet-enterprise-admin .aui-form fieldset {
	border: none;
	padding: 0;
}

.portlet-enterprise-admin #addresses .aui-field {
	float: none;
	width: auto;
}

.portlet-enterprise-admin .aui-form .row-container {
}

.portlet-enterprise-admin .permission-scopes {
	display: block;
}

.portlet-enterprise-admin .permission-scopes:after {
	clear: both;
	content: ".";
	display: block;
	height: 0;
	visibility: hidden;
}

.ie .portlet-enterprise-admin .permission-scopes {
	height: 1%;
}

.portlet-enterprise-admin .permission-scopes.empty {
	display: none;
}

.portlet-enterprise-admin .permission-scopes .permission-scope {
	background: #DFF4FF;
	border: 1px solid #A7CEDF;
	float: left;
	margin-right: 3px;
	padding: 3px 20px 3px 6px;
	padding-right: 20px;
	position: relative;
}

.portlet-enterprise-admin .permission-scopes .permission-scope:hover {
	border-color: #AEB8BC;
}

.portlet-enterprise-admin .permission-scopes .permission-scope-delete {
	display: block;
	padding: 6px;
	position: absolute;
	right: 0;
	top: 3px;
}

.portlet-enterprise-admin .permission-scopes .permission-scope-delete span {
	background: url(<%= themeImagesPath %>/application/close_small.png) no-repeat 0 0;
	cursor: pointer;
	display: block;
	font-size: 0;
	height: 7px;
	text-indent: -9999em;
	width: 7px;
}

.portlet-enterprise-admin .permission-group {
	margin: 10px 0 0 10px;
}

.portlet-enterprise-admin .permission-scopes .permission-scope-delete:hover span {
	background-position: 0 100%;
}

.lfr-floating-container .aui-field input, .lfr-floating-container .aui-field img {
	vertical-align: top;
}