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

.aui-column-container {
	overflow: hidden;
	width: 100%;
}

.ie6 .aui-column-container, .ie7 .aui-column-container {
	display: block;
}

.aui-column {
	float: left;
	overflow: hidden;
}

.aui-column-first {
}

.aui-column-last {
}

.aui-column-last {
	float: right;
	margin-left: -5px;
}

.aui-w10 {
	width: 10%;
}

.aui-w15 {
	width: 15%;
}

.aui-w20 {
	width: 20%;
}

.aui-w25 {
	width: 25%;
}

.aui-w30 {
	width: 30%;
}

.aui-w35 {
	width: 35%;
}

.aui-w40 {
	width: 40%;
}

.aui-w45 {
	width: 45%;
}

.aui-w50 {
	width: 50%;
}

.aui-w55 {
	width: 55%;
}

.aui-w60 {
	width: 60%;
}

.aui-w65 {
	width: 65%;
}

.aui-w70 {
	width: 70%;
}

.aui-w75 {
	width: 75%;
}

.aui-w80 {
	width: 80%;
}

.aui-w85 {
	width: 85%;
}

.aui-w90 {
	width: 90%;
}

.aui-w95 {
	width: 95%;
}

.aui-w33 {
	width: 33.333%;
}

.aui-w66 {
	width: 66.666%;
}

.aui-w38 {
	width: 38.2%;
}

.aui-w62 {
	width: 61.8%;
}

.aui-column-content, .aui-column-content-center {
	padding: 0 0.5em;
}

.aui-column-first .aui-column-content, .aui-column .aui-column-content-first {
	padding: 0 1em 0 0;
}

.aui-column-last .aui-column-content, .aui-column .aui-column-content-last {
	padding: 0 0 0 1em;
}

.ie6 .aui-column-first, .ie6 .aui-column-content-first {
	display: inline;
}

.ie6 .aui-column-container .aui-column-content-center,
.ie6 .aui-column-container .aui-column-content-first,
.ie6 .aui-column-container .aui-column-content-last {
	word-wrap: break-word;
	overflow: hidden;
}

.ie body {
	position: relative;
}

.ie5 body, .ie6 body {
	position: static;
}

.clearfix:after {
	content: ".";
	display: block;
	height: 0;
	clear: both;
	visibility: hidden;
}

.clearfix {
	display: block;
}


.floatbox {
	overflow: hidden;
}

#ie_clearing {
	display: none;
}

.ie .clearfix {
	display: inline-block;
}

.ie7 .clearfix {
	display: block;
}

.ie5 .clearfix, .ie6 .clearfix, .ie7 .clearfix {
	height: 1%;
}