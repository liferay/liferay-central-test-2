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

/*
 ---------- Alloy form controls ----------
*/

.aui-form {
	margin: 0;
	overflow: hidden;
	padding: 0;
	position: relative;
	width: 100%;
}

.aui-form .aui-ctrl-holder {
	overflow: hidden;
	clear: both;
	margin: 0;
	padding: 0;
}

.aui-form .aui-button-holder {
	clear: both;
	overflow: hidden;
}

.aui-form .reset-button {
}

.aui-form .submit-button {
}

.aui-form .inline-label, .aui-form .aui-inline-labels .inline-label, .aui-form .aui-block-labels .inline-label {
	display: inline;
	float: none;
	margin: 0 1em 0 0;
	width: auto;
}

.aui-form .inline-label input {
}

.aui-form .focused {
}

.aui-form .aui-inline-labels .aui-ctrl-holder {
}

.aui-form .aui-inline-labels label, .aui-form .aui-inline-labels .label {
	float: left;
	line-height: 100%;
	margin: .3em 2% 0 0;
	padding: 0;
	position: relative;
}

.aui-form .aui-inline-labels .text-input, .aui-form .aui-inline-labels .file-upload {
	float: left;
}

.aui-form .aui-inline-labels .file-upload {
}

.aui-form .aui-inline-labels .select-input {
	float: left;
}

.aui-form .aui-inline-labels textarea {
	float: left;
}

.aui-form .aui-inline-labels .form-hint {
	clear: both;
}

.aui-form .aui-inline-labels .form-hint strong {
}

.aui-form .aui-block-labels .aui-ctrl-holder {
}

.aui-form .aui-block-labels label, .aui-form .aui-block-labels .label {
	display: block;
	float: none;
	font-weight: bold;
	line-height: 100%;
	margin: .3em 0;
	padding: 0;
	width: auto;
}

.aui-form .aui-block-labels label.hidden-label {
	float: left;
	font-size: 0;
}

.aui-block-labels label input {
	vertical-align: middle;
}

.aui-form .aui-block-labels .label {
	float: left;
	margin-right: 3em;
}

.aui-form .aui-block-labels .inline-label {
	position: relative;
	top: .15em;
}

.aui-form .aui-block-labels .text-input, .aui-form .aui-block-labels .file-upload {
	float: left;
}

.aui-form .aui-block-labels .file-upload {
}

.aui-form .aui-block-labels .select-input {
	display: block;
}

.aui-form .aui-block-labels textarea {
	display: block;
	float: left;
}

.aui-form .aui-block-labels .form-hint {
	clear: none;
	float: right;
	margin: 0;
}

.aui-form .aui-block-labels .aui-ctrl-holder {
}

.aui-form .aui-block-labels .focused {
}

.aui-form .aui-ctrl-holder .text-input:focus {
}

.aui-form div.focused .text-input:focus {
}

.aui-form div.focused .form-hint {
}

.aui-form label em, .aui-form .label em {
	display: block;
	font-style: normal;
	font-weight: bold;
	left: 100%;
	position: absolute;
}

.aui-form .aui-block-labels label em, .aui-form .aui-block-labels .label em {
	display: inline;
	position: static;
}

.aui-form #error-msg, .aui-form .error {
}

.aui-form #error-msg dt, .aui-form #error-msg h3 {
}

.aui-form #error-msg dd {
}

.aui-form #error-msg ol {
}

.aui-form #error-msg ol li {
}

.aui-form .error-field {
}

.aui-form .aui-form-column {
	float: left;
	margin-right: 10px;
	width: auto;
}

.aui-form .column-left {
	float: left;
	margin-right: 10px;
	width: auto;
}

.aui-form .column-right {
	float: right;
	width: 33%;
}

.ie6 .aui-form, .ie6 .aui-form fieldset, .ie6 .aui-ctrl-holder, .ie6 .aui-ctrl-holder span, .ie6 .form-hint {
	zoom: 1;
}

.ie6 .aui-block-labels .form-hint {
	margin-top: 0;
}

.aui-form fieldset {
	border: none;
	margin: 0;
}

.aui-form fieldset legend {
	font-weight: bold;
	color: #000;
	font-size: 120%;
}

.aui-form .aui-ctrl-holder {
	padding: 3px;
}

.aui-form .aui-button-holder {
	text-align: left;
	margin-top: 15px;
}

.aui-form .aui-ctrl-holder .aui-button-holder {
	clear: none;
	margin-left: 5px;
	margin-right: 5px;
	margin-top: 0;
}

.aui-form .focused {
	background: #FFFCDF;
}

.aui-form .aui-ctrl-holder.inline-label label {
	display: inline;
}


.aui-form .aui-inline-labels label,  .aui-form .aui-inline-labels .label {
	width: 45%;
}

.aui-form .aui-inline-labels .text-input,  .aui-form .aui-inline-labels .file-upload {
	width: 45%;
}

.aui-form .aui-inline-labels .select-input {
	width: 45%;
}

.aui-form .aui-inline-labels textarea {
	height: 12em;
	width: 45%;
}

.aui-form .aui-inline-labels .form-hint {
	margin-left: 47%;
	margin-top: 0;
}

.aui-form .aui-block-labels .text-input,  .aui-form .aui-block-labels .file-upload {
	width: 53%;
}

.aui-form .aui-block-labels .select-input {
	display: block;
	width: 53.5%;
}

.aui-form .aui-block-labels textarea {
	height: 12em;
	width: 53%;
}

.aui-form .aui-block-labels .form-hint {
	width: 45%;
}

.aui-form .aui-form-column {
	margin: 0 2% 0 0;
	width: 47.9%;
}

.aui-form .column-left {
	width: 49%;
}

.aui-form .column-right {
	width: 49%;
}

.aui-form #error-msg {
	background: #FFDFDF;
	border: 1px solid #DF7D7D;
	border-width: 1px 0;
	margin: 0 0 1em 0;
	padding: 1em;
}

.aui-form .error {
	background: #FFDFDF;
	border: 1px solid #DF7D7D;
	border-width: 1px 0;
}

.aui-form #error-msg dt,  .aui-form #error-msg h3 {
	font-size: 110%;
	font-weight: bold;
	line-height: 100%;
	margin: 0 0 .5em 0;
}

.aui-form #error-msg dd {
	margin: 0;
	padding: 0;
}

.aui-form #error-msg ol {
	margin: 0;
	padding: 0;
}

.aui-form #error-msg ol li {
	border-bottom: 1px dotted #DF7D7D;
	list-style-position: inside;
	margin: 0;
	padding: 2px;
	position: relative;
}

.aui-form .error-field {
	background: #FFBFBF;
	color: #AF4C4C;
	margin: 0 0 6px 0;
	padding: 4px;
}

.aui-button-row {
	padding: 5px;
	border: 1px solid #ccc;
	background: #eee;
}