<%
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

<%@ include file="init.jsp" %>

/******************************************************************************/
/* Liferay                                                                    */
/******************************************************************************/

/* Global */

A {
	color: <%= colorScheme.getPortletFont() %>;
	font-family: Tahoma, Arial;
	text-decoration: none;
}

A:hover {
	text-decoration: underline;
}

BODY {
	background-color: <%= colorScheme.getLayoutBg() %>;
	border: 0;
	margin: 0;
	padding: 0;
	text-align: center;
}

BODY#body-bg {
	background: <%= colorScheme.getBodyBg() %>;
}

BODY#iframe-body-bg {
}


SELECT {
	border-color: #CCCCCC;
	border-style: solid;
	border-width: 1px;
}

.form-button {
	border-color: <%= colorScheme.getPortletTitleBg() %>;
	border-style: solid;
	border-width: 1px;
	font-family: Arial;
	font-size: 10px;
}

.form-button-hover {
	border-bottom: solid 1px <%= colorScheme.getPortletTitleBg() %>;
	border-left: solid 1px <%= colorScheme.getLayoutBg() %>;
	border-right: solid 1px <%= colorScheme.getPortletTitleBg() %>;
	border-top: solid 1px <%= colorScheme.getLayoutBg() %>;
	font-family: Arial;
	font-size: 10px;
}

.form-text {
	border-color: #CCCCCC;
	border-style: solid;
	border-width: 1px;
	font-family: Arial;
	font-size: 80%;
}

.tree-js-pop-up DIV {
	background-color: #D3D3D3;
	border: 2px Outset #FFFFFF;
	display: none;
	padding: 4;
	position: absolute;
	z-index: 10;
}

/* Alpha */

.alpha {
	background-color: <%= colorScheme.getPortletTitleBg() %>;
}

.alpha-gradient {
	background: url(<%= themeDisplay.getPathColorSchemeImage() %>/portlet_title_bg_gradient.gif) repeat-x;
}

.alpha-neg-alert {
	color: <%= colorScheme.getPortletMsgError() %>;
}

.alpha-pos-alert {
	color: <%= colorScheme.getPortletMsgSuccess() %>;
}

.alpha-separator {
	background: url(<%= themeDisplay.getPathColorSchemeImage() %>/portlet_title_bg_x.gif) repeat-x bottom;
	border-bottom: none;
	padding: 1px;
}

A.alpha {
	background: none;
	color: <%= colorScheme.getPortletFont() %>;
	text-decoration: none;
}

A.alpha:hover {
	color: <%= colorScheme.getPortletFont() %>;
	text-decoration: underline;
}

FONT.alpha {
	background: none;
	color: <%= colorScheme.getPortletTitleText() %>;
	font-family: Tahoma, Arial;
}

/* Beta */

.beta {
	background-color: <%= colorScheme.getPortletMenuBg() %>;
}

.beta-gradient {
	background-color: <%= colorScheme.getPortletMenuBg() %>;
}

.beta-neg-alert {
	color: <%= colorScheme.getPortletMsgError() %>;
}

.beta-pos-alert {
	color: <%= colorScheme.getPortletMsgSuccess() %>;
}

.beta-separator {
	background: url(<%= themeDisplay.getPathColorSchemeImage() %>/portlet_menu_bg_x.gif) repeat-x bottom;
	border-bottom: none;
	padding: 1px;
}

A.beta {
	background: none;
	color: <%= colorScheme.getPortletFont() %>;
	text-decoration: none;
}

A.beta:hover {
	color: <%= colorScheme.getPortletFont() %>;
	text-decoration: underline;
}

FONT.beta {
	background: none;
	color: <%= colorScheme.getPortletMenuText() %>;
	font-family: Tahoma, Arial;
}

/* Gamma */

.gamma {
	background-color: none;
}

.gamma-gradient {
	background: url(<%= themeDisplay.getPathColorSchemeImage() %>/portlet_bg_x.gif) repeat-x;
}

.gamma-neg-alert {
	color: <%= colorScheme.getPortletMsgError() %>;
}

.gamma-pos-alert {
	color: <%= colorScheme.getPortletMsgSuccess() %>;
}

.gamma-separator {
	background: url(<%= themeDisplay.getPathColorSchemeImage() %>/gamma_dotted_x.gif) repeat-x bottom;
	border-bottom: none;
	padding: 1px;
}

#gamma-tab {
	border-bottom: 1px solid <%= colorScheme.getPortletMenuBg() %>;
	margin-left: 0px;
	margin-top: 0px;
	margin-bottom: 15px;
	margin-right: 0px;
	padding-bottom: 27px;
	padding-left: 0px;
}

#gamma-tab ul, #gamma-tab li {
	background: <%= colorScheme.getPortletBg() %>;
	border: 1px solid <%= colorScheme.getPortletMenuBg() %>;
	color: <%= colorScheme.getPortletFont() %>;
	display: inline;
	float: left;
	font-family: Tahoma, Arial;
	font-size: 13px;
	font-weight: normal;
	line-height: 22px;
	list-style-type: none;
	margin-right: 8px;
	padding: 2px 10px 2px 10px;
	text-decoration: none;
}

#gamma-tab ul#current, #gamma-tab li#current {
	background: <%= colorScheme.getPortletBg() %>;
	border-bottom: 1px solid <%= colorScheme.getPortletBg() %>;
	color: <%= colorScheme.getPortletFont() %>;
}

#gamma-tab li#toggle {
	background: none;
	border: 0px;
	float: right;
	margin-right: 0px;
	padding-right: 0px;
}

A.gamma {
	background: none;
	color: <%= colorScheme.getPortletFont() %>;
	text-decoration: none;
}

A.gamma:hover {
	color: <%= colorScheme.getPortletFont() %>;
	text-decoration: underline;
}

FONT.gamma {
	background: none;
	color: <%= colorScheme.getPortletFont() %>;
	font-family: Tahoma, Arial;
}

/* Bg */

.bg {
	background-color: none;
}

.bg-neg-alert {
	color: <%= colorScheme.getPortletMsgError() %>;
}

.bg-pos-alert {
	color: <%= colorScheme.getPortletMsgSuccess() %>;
}

A.bg {
	background: none;
	color: <%= colorScheme.getPortletFont() %>;
	text-decoration: none;
}

A.bg:hover {
	color: <%= colorScheme.getPortletFont() %>;
	text-decoration: underline;
}

FONT.bg {
	color: <%= colorScheme.getLayoutText() %>;
	font-family: Tahoma, Arial;
}

/******************************************************************************/
/* Liferay Layout CSS                                                         */
/******************************************************************************/

/* Main Layout */

<%
int layoutPadding = 10;
int layoutBorder = 31;
int layoutDecorationWidth = themeDisplay.getResTotal() + (layoutPadding * 2) + (layoutBorder * 2);
%>

#layout-outer-side-decoration {
	background: transparent url(<%= themeDisplay.getPathThemeImage() %>/custom/wood-frame-left.gif) repeat-y top left;
	margin: 10px auto 0 auto;
	width: <%= layoutDecorationWidth %>px;
}

#layout-inner-side-decoration {
	background: transparent url(<%= themeDisplay.getPathThemeImage() %>/custom/wood-frame-right.gif) repeat-y top right;
	width: <%= layoutDecorationWidth %>px;
}

#layout-box-outer-decoration {
}

#layout-box-inner-decoration {
	background: <%= colorScheme.getLayoutBg() %> url(<%= themeDisplay.getPathColorSchemeImage() %>/window-bg.gif) no-repeat bottom left;
	margin-left: auto;
	margin-right: auto;
	width: <%= themeDisplay.getResTotal() + layoutPadding * 2 %>px;
}

#layout-box {
	margin-left: auto;
	margin-right: auto;
	padding: 10px 0 70px 0;
	width: <%= themeDisplay.getResTotal() %>px;
}

#layout-top {
	text-align: left;
	height: 54px;
	position: relative;
	width: <%= themeDisplay.getResTotal() + 2 %>px;
}

#layout-logo {
	float: left;
}

#layout-greeting {
	float: left;
	display: none;
}

#layout-user-menu {
	position: absolute;
	right: 0;
	text-align: right;
}

#layout-top-decoration {
	height: 36px;
	position: relative;
}

#layout-corner-ul {
	background: transparent url(<%= themeDisplay.getPathThemeImage() %>/custom/wood-frame-top-left.gif) no-repeat top left;
	height: 36px;
	left: -28px;
	overflow: hidden;
	position: absolute;
	top: 0px;
	width: 750px;
}

#layout-corner-ur {
	background: transparent url(<%= themeDisplay.getPathThemeImage() %>/custom/wood-frame-top-right.gif) no-repeat top right;
	height: 36px;
	overflow: hidden;
	position: absolute;
	right: -27px;
	top: 0px;
	width: 384px;
}

#layout-corner-bl {
	background: <%= colorScheme.getBodyBg() %> url(<%= themeDisplay.getPathColorSchemeImage() %>/portlet_corner_bl.gif) no-repeat;
	height: 5px;
	left: 0px;
	overflow: hidden;
	position: absolute;
	top: 0px;
	width: 5px;
}

#layout-corner-br {
	background: <%= colorScheme.getBodyBg() %> url(<%= themeDisplay.getPathColorSchemeImage() %>/portlet_corner_br.gif) no-repeat;
	height: 5px;
	overflow: hidden;
	position: absolute;
	right: 0px;
	top: 0px;
	width: 5px;
}

#layout-corner-2-bl {
	background: transparent url(<%= themeDisplay.getPathThemeImage() %>/custom/wood-frame-bottom-left.gif) no-repeat top left;
	height: 97px;
	left: -86px;
	overflow: hidden;
	position: absolute;
	top: 0px;
	width: 851px;
}

#layout-corner-2-br {
	background: transparent url(<%= themeDisplay.getPathThemeImage() %>/custom/wood-frame-bottom-right.gif) no-repeat top right;
	height: 97px;
	overflow: hidden;
	position: absolute;
	right: -70px;
	top: 0px;
	width: 607px;
}

#layout-bottom-decoration {
	display: none;
	height: 5px;
	top: -5px;
	position: relative;
	width: 100%;
}

#layout-bottom-decoration-2 {
	height: 97px;
	position: relative;
}

/* Top Navigation Layout */

#layout-nav-container {
	width: <%= themeDisplay.getResTotal() %>;
}

.layout-nav-tabs {
}

.layout-nav-row {
	position: relative;
	height: <%= TAB_IMAGE_HEIGHT %>;
	width: 100%;
}

.layout-nav-tab {
	background-color: <%= colorScheme.getPortletSectionBodyBg() %>;
	height: <%= TAB_IMAGE_HEIGHT %>;
	position: absolute;
}

.layout-nav-tab-selected {
	background-color: <%= colorScheme.getPortletSectionSelectedBg() %>;
	height: <%= TAB_IMAGE_HEIGHT %>;
	position: absolute;
	z-index: 100;
}

.layout-tab {
	border: 1px solid <%= colorScheme.getPortletSectionBody() %>;
	border-left: none;
	height: 100%;
}

.layout-tab-selected {
	border: 1px solid <%= colorScheme.getPortletSectionSelected() %>;
	border-left: none;
	height: 100%;
}

.layout-tab-text {
	position: relative;
	top: 4px;
}

/* Column Layout */

#layout-content-container {
	padding: 0;
}

.layout-column-default {
	background-color: none;
}

.layout-column-highlight {
	background-color: #EEF;
}

#layout-column_n1, #layout-column_n2, #layout-column_w1 {
	float: left;
}

.layout-blank_n1_portlet, .layout-blank_n2_portlet, .layout-blank_w1_portlet {
	float: left;
	font-size: 0px;
	height: 5px;
}

.layout-margin-div {
	float: left;
	height: 1px;
	width: 10px;
}

#layout-bottom-container {
	clear: both;
	font-family: Verdana, Arial;
	font-size: 7pt;
	font-weight: normal;
	text-align: left;
	width: <%= themeDisplay.getResTotal() %>px;
}

.layout-add-select-style {
	font-family: Verdana, Arial;
	font-size: 7pt;
	font-weight: normal;
}

#layout-add_n1, #layout-add_n2, #layout-add_w1	{
	float: left;
}

#bottom-container {
	border: 0;
	clear: both;
	padding: 0;
	text-align: left;
	width: <%= themeDisplay.getResTotal() %>;
}

#bottom-content {
	color: #ffffff;
	font-family: Verdana, Arial;
	font-size: 6.5pt;
	font-weight: normal;
}

#bottom-content A {
	color: #ffffff;
}

/******************************************************************************/
/* Portlet CSS                                                                */
/******************************************************************************/

/* Liferay Portlet */

<c:choose>
	<c:when test="<%= BrowserSniffer.is_ie(request) %>">
	#portlet-dragging * {
		filter: alpha(opacity=75);
	}
	</c:when>
	<c:otherwise>
	#portlet-dragging {
		opacity: 0.75;
	}
	</c:otherwise>
</c:choose>

.portlet-container {
	margin-top: 10px;
}

.portlet-box {
	<c:if test="<%= BrowserSniffer.is_ie(request) %>">
		height: 100%;
	</c:if>
}

.portlet-content {
}

.portlet-header-bar {
	background: <%= colorScheme.getPortletTitleBg() %>;
	border: 1px solid <%= colorScheme.getPortletFontDim() %>;
	position: relative;
	text-align: left;
	z-index: 2;
}

.portlet-title {
	color: <%= colorScheme.getPortletTitleText() %>;
	padding: 3px 0 3px 10px;
	font-family: Tahoma, Arial;
	font-size: 10pt;
	font-weight: bold;
}

.portlet-menu {
	background: <%= colorScheme.getPortletMenuBg() %> url(<%= themeDisplay.getPathColorSchemeImage() %>/portlet_menu_bg_gradient.gif) repeat-x;
	color: #000000;
	font-family: Tahoma, Arial;
	height: 27px;
	left: 10px;
	padding: 5px 3px 0px 3px;
	position: absolute;
}

.portlet-small-icon-bar {
	height: 17px;
	padding-right: 10px;
	position: absolute;
	right: 0;
	top: 3;
	text-align: right;
	visibility: hidden;
	width: 70%;
	z-index: 2;
}

.portlet-small-icon {
	height: 14px;
	margin: -1px;
	width: 14px;
}

.portlet-corner-ul {
	display: none;
	background: url(<%= themeDisplay.getPathThemeImage() %>/portlet/header-left.gif) no-repeat;
	height: 30px;
	left: -1px;
	overflow: hidden;
	position: absolute;
	top: 0px;
	width: 9px;
}

.portlet-corner-ur {
	display: none;
	background: url(<%= themeDisplay.getPathThemeImage() %>/portlet/header-right.gif) no-repeat;
	height: 30px;
	overflow: hidden;
	position: absolute;
	right: -1px;
	top: 0px;
	width: 9px;
}

.portlet-corner-bl {
	display: none;
	background: <%= colorScheme.getLayoutBg() %> url(<%= themeDisplay.getPathColorSchemeImage() %>/portlet_corner_bl.gif) no-repeat;
	height: 5px;
	left: 0px;
	overflow: hidden;
	position: absolute;
	top: 0px;
	width: 5px;
}

.portlet-corner-br {
	display: none;
	background: <%= colorScheme.getLayoutBg() %> url(<%= themeDisplay.getPathColorSchemeImage() %>/portlet_corner_br.gif) no-repeat;
	height: 5px;
	overflow: hidden;
	position: absolute;
	right: 0px;
	top: 0px;
	width: 5px;
}

.portlet-corner-2-bl {
	display: none;
	background: url(<%= themeDisplay.getPathThemeImage() %>/portlet/header-close-left.gif) no-repeat;
	height: 11px;
	left: -1px;
	overflow: hidden;
	position: absolute;
	top: 0px;
	width: 16px;
}

.portlet-corner-2-br {
	display: none;
	background: url(<%= themeDisplay.getPathThemeImage() %>/portlet/header-close-right.gif) no-repeat;
	height: 11px;
	overflow: hidden;
	position: absolute;
	right: -1px;
	top: 0px;
	width: 16px;
}

.portlet-top-decoration {
	display: none;
	height: 30px;
	position: relative;
	top: 0px;
	width: 100%;
	z-index: 1;
}

.portlet-inner-top {
	margin: 0 auto 0 auto;
}

.portlet-bottom-blank {
	display: none;
	margin-bottom: 5px;
}

.portlet-bottom-decoration {
	display:none;
	height: 5px;
	position: relative;
	top: -11px;
	width: 100%;
	z-index: 2;
}

.portlet-bottom-decoration-2 {
	display: none;
	background: url(<%= themeDisplay.getPathThemeImage() %>/portlet/header-close-middle.gif) repeat-x;
	height: 11px;
	position: relative;
	top: 0px;
	width: 100%;
}

/* Fonts */

.portlet-font {
	color: <%= colorScheme.getPortletFont() %>;
	font-family: Tahoma, Arial;
	font-size: medium;
}

.portlet-font-dim {
	color: <%= colorScheme.getPortletFontDim() %>;
	font-family: Tahoma, Arial;
	font-size: medium;
}

.portlet-font A {
	color: <%= colorScheme.getPortletFont() %>;
}

/* Messages */

.portlet-msg-status {
	color: <%= colorScheme.getPortletMsgStatus() %>;
	font-family: Tahoma, Arial;
	font-size: medium;
	font-style: italic;
}

.portlet-msg-info {
	color: <%= colorScheme.getPortletMsgInfo() %>;
	font-family: Tahoma, Arial;
	font-size: medium;
}

.portlet-msg-error {
	color: <%= colorScheme.getPortletMsgError() %>;
	font-family: Tahoma, Arial;
	font-size: medium;
}

.portlet-msg-alert {
	color: <%= colorScheme.getPortletMsgAlert() %>;
	font-family: Tahoma, Arial;
	font-size: medium;
	font-style: italic;
}

.portlet-msg-success {
	color: <%= colorScheme.getPortletMsgSuccess() %>;
	font-family: Tahoma, Arial;
	font-size: medium;
}

/* Sections */

.portlet-section-header {
	color: <%= colorScheme.getPortletSectionHeader() %>;
	background: <%= colorScheme.getPortletSectionHeaderBg() %>;
	font-family: Tahoma, Arial;
	font-size: medium;
}

.portlet-section-body {
	color: <%= colorScheme.getPortletSectionBody() %>;
	background: <%= colorScheme.getPortletSectionBodyBg() %>;
	font-family: Tahoma, Arial;
	font-size: medium;
}

TR.portlet-section-body:hover {
	color: <%= colorScheme.getPortletSectionBodyHover() %>;
	background: <%= colorScheme.getPortletSectionBodyHoverBg() %>;
	font-family: Tahoma, Arial;
	font-size: medium;
}

.portlet-section-body A {
	color: <%= colorScheme.getPortletSectionBody() %>;
}

TR.portlet-section-body:hover A {
	color: <%= colorScheme.getPortletSectionBodyHover() %>;
}

.portlet-section-alternate {
	color: <%= colorScheme.getPortletSectionAlternate() %>;
	background: <%= colorScheme.getPortletSectionAlternateBg() %>;
	font-family: Tahoma, Arial;
	font-size: medium;
}

TR.portlet-section-alternate:hover {
	color: <%= colorScheme.getPortletSectionAlternateHover() %>;
	background: <%= colorScheme.getPortletSectionAlternateHoverBg() %>;
	font-family: Tahoma, Arial;
	font-size: medium;
}

.portlet-section-alternate A {
	color: <%= colorScheme.getPortletSectionAlternate() %>;
}

TR.portlet-section-alternate:hover A {
	color: <%= colorScheme.getPortletSectionAlternateHover() %>;
}

.portlet-section-selected {
	color: <%= colorScheme.getPortletSectionSelected() %>;
	background: <%= colorScheme.getPortletSectionSelectedBg() %>;
	font-family: Tahoma, Arial;
	font-size: medium;
}

.portlet-section-selected:hover, TR.portlet-section-selected:hover {
	color: <%= colorScheme.getPortletSectionSelectedHover() %>;
	background: <%= colorScheme.getPortletSectionSelectedHoverBg() %>;
	font-family: Tahoma, Arial;
	font-size: medium;
}

.portlet-section-selected A, portlet-section-selected A:hover {
	background: none;
}

.portlet-section-subheader {
	color: <%= colorScheme.getPortletSectionSubheader() %>;
	background: <%= colorScheme.getPortletSectionSubheaderBg() %>;
	font-family: Tahoma, Arial;
	font-size: medium;
}

.portlet-section-footer {
	color: <%= colorScheme.getLayoutText() %>;
	font-family: Tahoma, Arial;
	font-size: medium;
}

.portlet-section-text {
	color: <%= colorScheme.getLayoutText() %>;
	font-family: Tahoma, Arial;
	font-size: medium;
}

/* Forms */

.portlet-form-label {
	color: <%= colorScheme.getPortletFont() %>;
	font-family: Tahoma, Arial;
	font-size: medium;
}

.portlet-form-input-field {
	border-color: #CCCCCC;
	border-style: solid;
	border-width: 1px;
	font-family: Arial;
	font-size: 80%;
}

.portlet-form-button {
	background: url(<%= themeDisplay.getPathColorSchemeImage() %>/button_gradient.gif) repeat-x;
	border-color: <%= colorScheme.getPortletTitleBg() %>;
	border-style: solid;
	border-width: 1px;
	font-family: Arial;
	font-size: 10px;
}

.portlet-icon-label {
	color: <%= colorScheme.getPortletFont() %>;
	font-family: Tahoma, Arial;
	font-size: small;
}

.portlet-dlg-icon-label {
	color: <%= colorScheme.getPortletFont() %>;
	font-family: Tahoma, Arial;
	font-size: small;
}

.portlet-form-field-label {
	color: <%= colorScheme.getPortletFont() %>;
	font-family: Tahoma, Arial;
	font-size: medium;
}

.portlet-form-field {
	color: <%= colorScheme.getPortletFont() %>;
	font-family: Tahoma, Arial;
	font-size: medium;
}

/******************************************************************************/
/* Navigation Portlet                                                         */
/******************************************************************************/

.portlet-navigation-menu {
	margin: 10px 0 10px 15px;
	font-family: Arial;
	font-size: x-small;
	text-align: left;
}

.portlet-navigation-menu UL {
	margin: 0 0 0 5px;
	padding: 0 0 0 15px;
}

/******************************************************************************/
/* Display Tag Library                                                        */
/******************************************************************************/

TR.even { background-color: <%= colorScheme.getPortletMenuBg() %>; color: #000000; font-family: Tahoma, Arial; }

TR.odd { background-color: <%= colorScheme.getPortletBg() %>; color: #000000; font-family: Tahoma, Arial; }
