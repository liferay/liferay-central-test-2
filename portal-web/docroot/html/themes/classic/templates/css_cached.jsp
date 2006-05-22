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

<%

String fontXXS = "0.7em";
String fontXS = "0.8em";
String fontS = "0.9em";
String fontDefault = "1em";
String fontL = "1.1em";
String fontXL = "1.2em";
String fontXXL = "1.3em";

%>

a {
	color: <%= colorScheme.getPortletFont() %>;
	text-decoration: none;
}

a:hover {
	text-decoration: underline;
}

body {
	background-color: <%= colorScheme.getBodyBg() %>;
	border: 0;
	margin: 0;
	padding: 0;
	text-align: center;
	font-family: Tahoma, Arial;
	font-size: 76%;
}

body * { font-size: <%= fontDefault %>; }

.font-xx-small { font-size: <%= fontXXS %>; }
.font-x-small { font-size: <%= fontXS %>; }
.font-small { font-size: <%= fontS %>; }
.font-large { font-size: <%= fontL %>; }
.font-x-large { font-size: <%= fontXL %>; }
.font-xx-large { font-size: <%= fontXXL %>; }

form {
	margin: 0;
	padding: 0;
}

select {
	border-color: #CCCCCC;
	border-style: solid;
	border-width: 1px;
}

ul {
	list-style-type: none;
	margin: 5px 0 10px 20px;
	padding: 0;
}

img { border: 0; margin: 0; padding: 0 }

input, button, select, textarea { font-size: <%= fontXS %>; }

textarea { font-family: Arial; }

.form-button {
	border-color: <%= colorScheme.getPortletTitleBg() %>;
	border-style: solid;
	border-width: 1px;
}

.form-button-hover {
	border-bottom: solid 1px <%= colorScheme.getPortletTitleBg() %>;
	border-left: solid 1px <%= colorScheme.getLayoutBg() %>;
	border-right: solid 1px <%= colorScheme.getPortletTitleBg() %>;
	border-top: solid 1px <%= colorScheme.getLayoutBg() %>;
}

.form-text {
	border-color: #CCCCCC;
	border-style: solid;
	border-width: 1px;
}

.tree-js-pop-up DIV {
	background-color: #D3D3D3;
	border: 2px Outset #FFFFFF;
	display: none;
	padding: 4;
	position: absolute;
	text-align: left;
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
	background: url(<%= themeDisplay.getPathColorSchemeImage() %>/portlet_title_bg_x.gif) repeat-x;
	height: 1px;
	font-size: 0;
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
}

/* Beta */

.beta {
	background-color: <%= colorScheme.getPortletMenuBg() %>;
}

.beta-gradient {
	background: url(<%= themeDisplay.getPathColorSchemeImage() %>/portlet_menu_bg_gradient.gif) repeat-x;
}

.beta-neg-alert {
	color: <%= colorScheme.getPortletMsgError() %>;
}

.beta-pos-alert {
	color: <%= colorScheme.getPortletMsgSuccess() %>;
}

.beta-separator {
	background: url(<%= themeDisplay.getPathColorSchemeImage() %>/portlet_menu_bg_x.gif) repeat-x;
	height: 1px;
	font-size: 0;
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

.gamma-tab {
	border-bottom: 1px solid <%= colorScheme.getPortletMenuBg() %>;
	margin-left: 0px;
	margin-top: 0px;
	margin-bottom: 15px;
	margin-right: 0px;
	padding-bottom: 27px;
	padding-left: 0px;
}

.gamma-tab ul, .gamma-tab li {
	background: #F6F6F6;
	border: 1px solid #CCCCCC;
	border-bottom: 1px solid <%= colorScheme.getPortletMenuBg() %>;
	color: #CCCCCC;
	display: inline;
	float: left;
	line-height: 22px;
	list-style-type: none;
	margin-right: 8px;
	padding: 2px 10px 2px 10px;
	text-decoration: none;
}

.gamma-tab ul.current, .gamma-tab li.current {
	background: <%= colorScheme.getPortletBg() %>;
	border: 1px solid <%= colorScheme.getPortletMenuBg() %>;
	border-bottom: 1px solid <%= colorScheme.getPortletBg() %>;
	color: <%= colorScheme.getPortletFont() %>;
}

.gamma-tab li a { color: #777777; }
.gamma-tab li.current a { color: <%= colorScheme.getPortletFont() %>; }

.gamma-tab li.toggle {
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
}

/******************************************************************************/
/* Liferay Layout CSS                                                         */
/******************************************************************************/

#layout-outer-side-decoration {
	margin-left: auto;
	margin-right: auto;
}

#layout-box {
	background-color: <%= colorScheme.getLayoutBg() %>;
	margin-top: 10px;
	text-align: center;
	width: 100%;
}

#layout-top-banner {
	height: 75px;
	position: relative;
	text-align: left;
	z-index: 3;
}


#layout-user-menu {
	position: absolute;
	text-align: right;
	top: 0px;
	right: 0px;
}

#layout-user-menu form {
	margin-top: 5px;
	text-align: right;
}

#layout-my-places {
	position: relative;
	right: 0;
	width: 200px;
}

#layout-my-places ul {
	background-color: <%= colorScheme.getLayoutBg() %>;
	border: 1px solid <%= colorScheme.getPortletTitleBg() %>;
	position: absolute;
	text-align: left;
	right: 0;
	top: 0;
	margin: 0;
}

#layout-my-places ul li {
	padding: 2px;
}

#layout-global-search {
	position: absolute;
	top: 5px;
	right: 5px;
}

#layout-nav-more-menu {
	background-color: <%= colorScheme.getLayoutBg() %>;
	border: 1px solid <%= colorScheme.getPortletTitleBg() %>;
	position: absolute;
	text-align: left;
	left: 0px;
	top: 26px;
	margin: 0;
	width: 80px;
}

#layout-nav-more-menu li {
	padding: 2px 2px 2px 5px;
}

#layout-nav-container {
	background: <%= colorScheme.getLayoutBg() %> url(<%= themeDisplay.getPathColorSchemeImage() %>/portlet_menu_bg_gradient.gif) scroll repeat-x bottom left;
	border: 1px solid <%= colorScheme.getPortletTitleBg() %>;
	position: relative;
	z-index: 2;
}

.layout-nav-tabs-box {
	height: 27px;
	position: relative;
}

.layout-tab, .layout-tab-selected {
	border-right: 1px solid <%= colorScheme.getPortletTitleBg() %>;
	color: <%= colorScheme.getLayoutTabText() %>;
	float: left;
	height: 27px;
	position: relative;
	width: 80px;
}

.layout-tab-selected {
	background: <%= colorScheme.getLayoutTabSelectedBg() %> url(<%= themeDisplay.getPathColorSchemeImage() %>/portlet_title_bg_gradient.gif) scroll repeat-x center;
	color: <%= colorScheme.getLayoutTabSelectedText() %>;
}

.layout-tab span, .layout-tab-selected span {
	position: relative;
	top: 5px;
}

.layout-tab-separator {
	background: url(<%= themeDisplay.getPathColorSchemeImage() %>/portlet_title_bg_y.gif) repeat-y;
	font-size: 0;
	float: left;
	height: 15px;
	margin-top: 5px;
	width: 1px;
}

#layout-content-outer-decoration {
	z-index: 0;
}

/* Column Layout */

#layout-bottom-container {
	text-align: left;
}

.layout-add-select-style {
}

.portal-add-content {
	background-color: <%= colorScheme.getLayoutBg() %>;
	border: 3px solid <%= colorScheme.getPortletTitleBg() %>;
	padding: 8px;
}

#layout-column_column-1,
#layout-column_column-2,
#layout-column_column-3,
#layout-column_column-4,
#layout-column_column-5
{
	<c:choose>
		<c:when test="<%= BrowserSniffer.is_ie(request) %>">
			height: 75px;
		</c:when>
		<c:otherwise>
			min-height: 75px;
		</c:otherwise>
	</c:choose>
}

.layout-column-highlight {
	background: url(<%= themeDisplay.getPathThemeImage() %>/common/diagonal-lines.gif) scroll repeat;
}

/******************************************************************************/
/* Portlet CSS                                                                */
/******************************************************************************/

/* Liferay Portlet */

<%--
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
--%>

.portlet-dragging-placeholder {
	background-color: <%= colorScheme.getPortletSectionBodyBg() %>;
}

.portlet-loading {
	background: url(<%= themeDisplay.getPathThemeImage() %>/progress_bar/loading_animation.gif) no-repeat top center;
	margin-top: 10px;
	padding-top: 40px;
	text-align: center;
}

.portlet-container {
	background-color: <%= colorScheme.getPortletBg() %>;
	position: relative;
}

.portlet-box {
	border: 1px solid <%= colorScheme.getPortletTitleBg() %>;
	margin-top: 1px;
}

.portlet-minimum-height {
	<c:if test="<%= BrowserSniffer.is_ie(request) %>">
		height: 1px;
	</c:if>
}

.portlet-header-bar {
	border: 1px solid <%= colorScheme.getPortletTitleBg() %>;
	background: url(<%= themeDisplay.getPathColorSchemeImage() %>/button_gradient.gif) scroll repeat-x center left;
	position: relative;
}

.portlet-borderless-container { position: relative; }
.portlet-borderless-bar { text-align: left; font-size: <%= fontXS %>; }

.portlet-wrap-title {
	height: 20px;
	position: relative;
}

.portlet-title {
	color: <%= colorScheme.getPortletTitleText() %>;
	font-weight: bold;
	position: absolute;
	top: 3px;
	left: 5px;
}

.portlet-small-icon-bar {
	padding-left: 2px;
	position: absolute;
	<c:choose>
		<c:when test="<%= BrowserSniffer.is_ie(request) %>">
			top: 3px;
		</c:when>
		<c:otherwise>
			top: 1px;
		</c:otherwise>
	</c:choose>
	right: 2px;
}

.portlet-top-decoration {
	background: url(<%= themeDisplay.getPathColorSchemeImage() %>/portlet_corner_ul.gif) no-repeat top left;
	height: 5px;
}

.portlet-top-decoration DIV {
	background: url(<%= themeDisplay.getPathColorSchemeImage() %>/portlet_corner_ur.gif) no-repeat top right;
	height: 5px;
}

.portlet-top-decoration DIV DIV {
	background: <%= colorScheme.getPortletBg() %>;
	border-top: 1px solid <%= colorScheme.getPortletTitleBg() %>;
	font-size: 0;
	height: 5px;
	margin: 0 5px 0 5px;
}


.portlet-inner-top {
	margin: 0 auto 0 auto;
}

.portlet-bottom-blank {
	margin-bottom: 5px;
}

.portlet-bottom-decoration {
	background: url(<%= themeDisplay.getPathColorSchemeImage() %>/portlet_corner_bl.gif) no-repeat top left;
	height: 5px;
}

.portlet-bottom-decoration DIV {
	background: url(<%= themeDisplay.getPathColorSchemeImage() %>/portlet_corner_br.gif) no-repeat top right;
	height: 5px;
}

.portlet-bottom-decoration DIV DIV {
	background: <%= colorScheme.getPortletBg() %>;
	border-bottom: 1px solid <%= colorScheme.getPortletTitleBg() %>;
	font-size: 0;
	height: 5px;
	margin: 0 5px 0 5px;
}

.portlet-bottom-decoration-2 {
	background: transparent url(<%= themeDisplay.getPathThemeImage() %>/shadow/middle.gif) scroll repeat-x top;
	margin-bottom: 5px;
	width: 100%;
}

.portlet-bottom-decoration-2 DIV {
	background: transparent url(<%= themeDisplay.getPathThemeImage() %>/shadow/left.gif) scroll no-repeat top left;
	width: 100%;
}

.portlet-bottom-decoration-2 DIV DIV {
	background: transparent url(<%= themeDisplay.getPathThemeImage() %>/shadow/right.gif) scroll no-repeat top right;
	font-size: 0;
	height: 6px;
	width: 100%;
}

/* Fonts */

.portlet-font {
	color: <%= colorScheme.getPortletFont() %>;
}

.portlet-font A {
	color: <%= colorScheme.getPortletFont() %>;
}

.portlet-font-dim {
	color: <%= colorScheme.getPortletFontDim() %>;
}

/* Messages */

.portlet-msg-status {
	color: <%= colorScheme.getPortletMsgStatus() %>;
	font-style: italic;
}

.portlet-msg-info {
	color: <%= colorScheme.getPortletMsgInfo() %>;
}

.portlet-msg-error {
	color: <%= colorScheme.getPortletMsgError() %>;
}

.portlet-msg-alert {
	color: <%= colorScheme.getPortletMsgAlert() %>;
	font-style: italic;
}

.portlet-msg-success {
	color: <%= colorScheme.getPortletMsgSuccess() %>;
}

/* Sections */

.portlet-section-header {
	background: <%= colorScheme.getPortletSectionHeaderBg() %>;
	color: <%= colorScheme.getPortletSectionHeader() %>;
	font-weight: bold;
}

.portlet-section-header A {
	color: <%= colorScheme.getPortletSectionHeader() %>;
}

.portlet-section-body {
	color: <%= colorScheme.getPortletSectionBody() %>;
	background: <%= colorScheme.getPortletSectionBodyBg() %>;
}

.portlet-section-body-hover, TR.portlet-section-body:hover {
	color: <%= colorScheme.getPortletSectionBodyHover() %>;
	background: <%= colorScheme.getPortletSectionBodyHoverBg() %>;
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
}

.portlet-section-alternate-hover, TR.portlet-section-alternate:hover {
	color: <%= colorScheme.getPortletSectionAlternateHover() %>;
	background: <%= colorScheme.getPortletSectionAlternateHoverBg() %>;
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
}

.portlet-section-selected-hover, TR.portlet-section-selected:hover {
	color: <%= colorScheme.getPortletSectionSelectedHover() %>;
	background: <%= colorScheme.getPortletSectionSelectedHoverBg() %>;
}

.portlet-section-selected A {
	color: <%= colorScheme.getPortletSectionSelected() %>;
}

.portlet-section-selected-hover A, TR.portlet-section-selected:hover A {
	color: <%= colorScheme.getPortletSectionSelectedHover() %>;
}

.portlet-section-subheader {
	background: <%= colorScheme.getPortletSectionSubheaderBg() %>;
	color: <%= colorScheme.getPortletSectionSubheader() %>;
	padding: 2px;
}

.portlet-section-footer {
	color: <%= colorScheme.getLayoutText() %>;
}

.portlet-section-text {
	color: <%= colorScheme.getLayoutText() %>;
}

/* Forms */

.portlet-form-label {
	color: <%= colorScheme.getPortletFont() %>;
}

.portlet-form-input-field {
	border-color: #CCCCCC;
	border-style: solid;
	border-width: 1px;
}

.portlet-form-button {
	background: url(<%= themeDisplay.getPathColorSchemeImage() %>/button_gradient.gif) repeat-x;
	border-color: <%= colorScheme.getPortletTitleBg() %>;
	border-style: solid;
	border-width: 1px;
}

.portlet-icon-label {
	color: <%= colorScheme.getPortletFont() %>;
}

.portlet-dlg-icon-label {
	color: <%= colorScheme.getPortletFont() %>;
}

.portlet-form-field-label {
	color: <%= colorScheme.getPortletFont() %>;
}

.portlet-form-field {
	color: <%= colorScheme.getPortletFont() %>;
}

/******************************************************************************/
/* Display Tag Library                                                        */
/******************************************************************************/

TR.even { background-color: <%= colorScheme.getPortletMenuBg() %>; color: #000000; }

TR.odd { background-color: <%= colorScheme.getPortletBg() %>; color: #000000; }
