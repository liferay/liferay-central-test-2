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
 ---------- Expanse form controls ----------
*/

.exp-form {
	margin: 0;
	overflow: hidden;
	padding: 0;
	position: relative;
	width: 100%;
}

.exp-form .exp-ctrl-holder {
	overflow: hidden;
	clear: both;
	margin: 0;
	padding: 0;
}

.exp-form .exp-button-holder {
	clear: both;
	overflow: hidden;
}

.exp-form .reset-button {
}

.exp-form .submit-button {
}

.exp-form .inline-label, .exp-form .exp-inline-labels .inline-label, .exp-form .exp-block-labels .inline-label {
	display: inline;
	float: none;
	margin: 0 1em 0 0;
	width: auto;
}

.exp-form .inline-label input {
}

.exp-form .focused {
}

.exp-form .exp-inline-labels .exp-ctrl-holder {
}

.exp-form .exp-inline-labels label, .exp-form .exp-inline-labels .label {
	float: left;
	line-height: 100%;
	margin: .3em 2% 0 0;
	padding: 0;
	position: relative;
}

.exp-form .exp-inline-labels .text-input, .exp-form .exp-inline-labels .file-upload {
	float: left;
}

.exp-form .exp-inline-labels .file-upload {
}

.exp-form .exp-inline-labels .select-input {
	float: left;
}

.exp-form .exp-inline-labels textarea {
	float: left;
}

.exp-form .exp-inline-labels .form-hint {
	clear: both;
}

.exp-form .exp-inline-labels .form-hint strong {
}

.exp-form .exp-block-labels .exp-ctrl-holder {
}

.exp-form .exp-block-labels label, .exp-form .exp-block-labels .label {
	display: block;
	float: none;
	font-weight: bold;
	line-height: 100%;
	margin: .3em 0;
	padding: 0;
	width: auto;
}

.exp-form .exp-block-labels label.hidden-label {
	float: left;
	font-size: 0;
}

.exp-block-labels label input {
	vertical-align: middle;
}

.exp-form .exp-block-labels .label {
	float: left;
	margin-right: 3em;
}

.exp-form .exp-block-labels .inline-label {
	position: relative;
	top: .15em;
}

.exp-form .exp-block-labels .text-input, .exp-form .exp-block-labels .file-upload {
	float: left;
}

.exp-form .exp-block-labels .file-upload {
}

.exp-form .exp-block-labels .select-input {
	display: block;
}

.exp-form .exp-block-labels textarea {
	display: block;
	float: left;
}

.exp-form .exp-block-labels .form-hint {
	clear: none;
	float: right;
	margin: 0;
}

.exp-form .exp-block-labels .exp-ctrl-holder {
}

.exp-form .exp-block-labels .focused {
}

.exp-form .exp-ctrl-holder .text-input:focus {
}

.exp-form div.focused .text-input:focus {
}

.exp-form div.focused .form-hint {
}

.exp-form label em, .exp-form .label em {
	display: block;
	font-style: normal;
	font-weight: bold;
	left: 100%;
	position: absolute;
}

.exp-form .exp-block-labels label em, .exp-form .exp-block-labels .label em {
	display: inline;
	position: static;
}

.exp-form #error-msg, .exp-form .error {
}

.exp-form #error-msg dt, .exp-form #error-msg h3 {
}

.exp-form #error-msg dd {
}

.exp-form #error-msg ol {
}

.exp-form #error-msg ol li {
}

.exp-form .error-field {
}

.exp-form .exp-form-column {
	float: left;
}

.exp-form .column-left {
	float: left;
}

.exp-form .column-right {
	float: right;
}

.ie6 .exp-form, .ie6 .exp-form fieldset, .ie6 .exp-ctrl-holder, .ie6 .exp-ctrl-holder span, .ie6 .form-hint {
	zoom: 1;
}

.ie6 .exp-block-labels .form-hint {
	margin-top: 0;
}

.exp-form fieldset {
	border: none;
	margin: 0;
}

.exp-form fieldset legend {
	font-weight: bold;
	color: #000;
	font-size: 120%;
}

.exp-form .exp-ctrl-holder {
	padding: 3px;
}

.exp-form .exp-button-holder {
	text-align: left;
	margin-top: 15px;
}

.exp-form .exp-ctrl-holder .exp-button-holder {
	clear: none;
	margin-left: 5px;
	margin-right: 5px;
	margin-top: 0;
}

.exp-form .focused {
	background: #FFFCDF;
}

.exp-form .exp-ctrl-holder.inline-label label {
	display: inline;
}


.exp-form .exp-inline-labels label,  .exp-form .exp-inline-labels .label {
	width: 45%;
}

.exp-form .exp-inline-labels .text-input,  .exp-form .exp-inline-labels .file-upload {
	width: 45%;
}

.exp-form .exp-inline-labels .select-input {
	width: 45%;
}

.exp-form .exp-inline-labels textarea {
	height: 12em;
	width: 45%;
}

.exp-form .exp-inline-labels .form-hint {
	margin-left: 47%;
	margin-top: 0;
}

.exp-form .exp-block-labels .text-input,  .exp-form .exp-block-labels .file-upload {
	width: 53%;
}

.exp-form .exp-block-labels .select-input {
	display: block;
	width: 53.5%;
}

.exp-form .exp-block-labels textarea {
	height: 12em;
	width: 53%;
}

.exp-form .exp-block-labels .form-hint {
	width: 45%;
}

.exp-form .exp-form-column {
	margin: 0 2% 0 0;
	width: 47.9%;
}

.exp-form .column-left {
	width: 49%;
}

.exp-form .column-right {
	width: 49%;
}

.exp-form #error-msg {
	background: #FFDFDF;
	border: 1px solid #DF7D7D;
	border-width: 1px 0;
	margin: 0 0 1em 0;
	padding: 1em;
}

.exp-form .error {
	background: #FFDFDF;
	border: 1px solid #DF7D7D;
	border-width: 1px 0;
}

.exp-form #error-msg dt,  .exp-form #error-msg h3 {
	font-size: 110%;
	font-weight: bold;
	line-height: 100%;
	margin: 0 0 .5em 0;
}

.exp-form #error-msg dd {
	margin: 0;
	padding: 0;
}

.exp-form #error-msg ol {
	margin: 0;
	padding: 0;
}

.exp-form #error-msg ol li {
	border-bottom: 1px dotted #DF7D7D;
	list-style-position: inside;
	margin: 0;
	padding: 2px;
	position: relative;
}

.exp-form .error-field {
	background: #FFBFBF;
	color: #AF4C4C;
	margin: 0 0 6px 0;
	padding: 4px;
}