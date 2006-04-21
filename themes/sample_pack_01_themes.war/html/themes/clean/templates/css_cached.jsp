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
	color: <%= colorScheme.getPortletFontDim() %>;
	text-decoration: none;
}

A:hover {
	text-decoration: underline;
}

BODY {
	background-color: <%= colorScheme.getBodyBg() %>;
	border: 0;
	margin: 0;
	padding: 0;
	text-align: center;
}

FORM {
	margin: 0;
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
	background-color: <%= colorScheme.getPortletBg() %>;
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
	background-color: <%= colorScheme.getLayoutBg() %>;
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

#layout-box {
	background-color: <%= colorScheme.getLayoutBg() %>;
	border: none;
	margin: 0 auto 0 auto;
	padding: 10px 0 10px 0;
}

#layout-outer-side-decoration {
}

#layout-inner-side-decoration {
}

#layout-corner-ul { }
#layout-corner-ur { }
#layout-corner-bl { }
#layout-corner-br { }
#layout-corner-2-bl { }
#layout-corner-2-br { }
#layout-top-decoration { }
#layout-bottom-decoration { }
#layout-bottom-decoration-2 { }

#bottom-container {
	border: 0;
	clear: both;
	padding: 0;
	text-align: left;
	width: <%= themeDisplay.getResTotal() %>;
}

#bottom-content {
	font-family: Verdana, Arial;
	font-size: 6.5pt;
	font-weight: normal;
	vertical-align: bottom;
}

/* Navigation Layout */

#layout-nav-container {
	text-align:left;
	position:relative;
	width: <%= themeDisplay.getResTotal() %>;
	z-index: 9;
}
#iframe_hack {
	border: 0px none #FFFFFF;
	display:none;
	height: 50px;
	left:40px;
	position:absolute;
	width:140px;
	z-index:9;

}
#startMenu {
	background: white;
	border: 1px solid <%= colorScheme.getPortletTitleText() %>;
	color: <%= colorScheme.getPortletTitleText() %>;
	display:none;
	font-family: "Trebuchet MS","Lucida Sans", Arial, Verdana, sans-serif;
	font-size: 10pt;
	left:40px;
	position:absolute;
	text-align:left;
	width:140px;
	z-index:10;
}
#startMenu a {
	color:<%= colorScheme.getPortletTitleText() %>;
	text-decoration:none;
}
#startMenu ul {
	padding:0px;
	margin:0px;
}

#startMenu li {
	list-style:none;
	margin:5px;
	padding:0px;
}

#startMenu a:hover{
	background: <%= colorScheme.getPortletTitleBg() %>;
}

.menuItem {
	color:black !important;
}

.submenuItem {
	display:none;
	background: white;
	margin: 0px 0px 0px 10px;
	padding:0px;
}

.submenuItem ul {
	padding:0px;
	margin:0px;
	/height:0px;
}
.submenuItem li {
	font-size:8pt;
	margin:0px;
	padding:0px;
}
#layout-nav-menu-list {
	margin:0px;
	padding:0px;

}
#tabButton {
	width:40px;
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
	background-color: <%= colorScheme.getPortletBg() %>;
	border: 1px solid <%= colorScheme.getPortletTitleText() %>;
	border-top: none;
	padding: 5px 0 5px 0;
	<c:if test="<%= BrowserSniffer.is_ie(request) %>">
		height: 100%;
	</c:if>
}

.portlet-header-bar {
	background-color: <%= colorScheme.getPortletTitleBg() %>;
	border: 1px solid <%= colorScheme.getPortletTitleText() %>;
	height:20px;
}

.portlet-title {
	float:left;
	height: 20px;
	padding-left:5px;
	padding-top:1px;
}
.portlet-title span{
	font-family: "Trebuchet MS","Lucida Sans", Arial, Verdana, sans-serif;
	font-size: 9pt;
	font-weight: bold;
	color: <%= colorScheme.getPortletTitleText() %>;

}

.portlet-small-icon-bar {
	float:right;
	width: 100px;
	text-align:right;
	padding-right:5px;
	padding-top:2px;
}

.portlet-small-icon {
	height: 14px;
	margin: -1px;
	width: 14px;
}

.portlet-inner-top {
	margin: 0 auto 0 auto;
}

.portlet-bottom-blank {
	margin-bottom: 5px;
}

.portlet-corner-ul { }
.portlet-corner-ur { }
.portlet-corner-bl { }
.portlet-corner-br { }
.portlet-corner-2-bl { }
.portlet-corner-2-br { }
.portlet-top-decoration { }
.portlet-bottom-decoration { }
.portlet-bottom-decoration-2 { }

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

.portlet-section-body-hover, TR.portlet-section-body:hover {
	color: <%= colorScheme.getPortletSectionBodyHover() %>;
	background: <%= colorScheme.getPortletSectionBodyHoverBg() %>;
	font-family: Tahoma, Arial;
	font-size: medium;
}

.portlet-section-body A {
	color: <%= colorScheme.getPortletSectionBody() %>;
}

.portlet-section-body-hover A, TR.portlet-section-body:hover A {
	color: <%= colorScheme.getPortletSectionBodyHover() %>;
}

.portlet-section-alternate {
	color: <%= colorScheme.getPortletSectionAlternate() %>;
	background: <%= colorScheme.getPortletSectionAlternateBg() %>;
	font-family: Tahoma, Arial;
	font-size: medium;
}

.portlet-section-alternate-hover, TR.portlet-section-alternate:hover {
	color: <%= colorScheme.getPortletSectionAlternateHover() %>;
	background: <%= colorScheme.getPortletSectionAlternateHoverBg() %>;
	font-family: Tahoma, Arial;
	font-size: medium;
}

.portlet-section-alternate A {
	color: <%= colorScheme.getPortletSectionAlternate() %>;
}

.portlet-section-alternate-hover A, TR.portlet-section-alternate:hover A {
	color: <%= colorScheme.getPortletSectionAlternateHover() %>;
}

.portlet-section-selected {
	color: <%= colorScheme.getPortletSectionSelected() %>;
	background: <%= colorScheme.getPortletSectionSelectedBg() %>;
	font-family: Tahoma, Arial;
	font-size: medium;
}

.portlet-section-selected-hover, TR.portlet-section-selected:hover {
	color: <%= colorScheme.getPortletSectionSelectedHover() %>;
	background: <%= colorScheme.getPortletSectionSelectedHoverBg() %>;
	font-family: Tahoma, Arial;
	font-size: medium;
}

.portlet-section-selected A {
	color: <%= colorScheme.getPortletSectionSelected() %>;
}

.portlet-section-selected-hover A, TR.portlet-section-selected:hover A {
	color: <%= colorScheme.getPortletSectionSelectedHover() %>;
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
	color: <%= colorScheme.getPortletFont() %>;
	margin: 10px 0 10px 15px;
	padding: 0px;
	font-family: Arial;
	font-size: x-small;
	text-align: left;
}

.portlet-navigation-menu UL {
	margin: 0 0 0 5px;
	padding: 0px 0 0 15px;
}

/******************************************************************************/
/* Display Tag Library                                                        */
/******************************************************************************/

TR.even { background-color: <%= colorScheme.getPortletMenuBg() %>; color: #000000; font-family: Tahoma, Arial; }

TR.odd { background-color: <%= colorScheme.getPortletBg() %>; color: #000000; font-family: Tahoma, Arial; }
