<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

.portlet-calendar .calendar-container {
	background: url(<%= themeImagesPath %>/calendar/calendar_day_drop_shadow.png) no-repeat 0 100%;
	border: 1px solid #999;
	width: 400px;
}

.ie6 .portlet-calendar .calendar-container {
	background: none;
}

.portlet-calendar .calendar-day {
	float:left;
	text-align: center;
	width: 210px;
}

.portlet-calendar .calendar-day h2 {
	background: url(<%= themeImagesPath %>/calendar/day_heading.png) repeat-x 0 100%;
	font-size: 2em;
	margin: 0;
	padding: 0.5em 0;
}

.portlet-calendar .calendar-day h3 {
	font-size: 11em;
	line-height: 1.2;
	margin: 0;
	vertical-align: middle;
}

.portlet-calendar .taglib-calendar {
	border-color: #999;
	margin-bottom: 1.5em;
	width: 190px;
}

.portlet-calendar .calendar-container .taglib-calendar {
	background: url(<%= themeImagesPath %>/calendar/calendar_drop_shadow.png) repeat-y 0 0;
	clear: none;
	float: right;
	margin-left: -8px;
	padding-left: 8px;
	margin-bottom: 0;
}

.ie6 .portlet-calendar .taglib-calendar {
	background: none;
	border-left: 1px solid;
	margin-left: 0;
	padding-left: 0;
	width: 189px;
}

.portlet-calendar .taglib-calendar table {
}

.portlet-calendar .calendar-container .taglib-calendar table {
	border: none;
}

.portlet-calendar .taglib-calendar table .first {
	border-left: none;
}

.portlet-calendar .taglib-calendar table .last {
	border-right: none;
}

.portlet-calendar .calendar-inactive {
	color: #999;
}

.portlet-calendar .calendar-current-day a {
	color: #fff;
	font-weight: bold;
	text-decoration: none;
}

.portlet-calendar .taglib-calendar tr td.calendar-current-day a:hover, .taglib-calendar tr td.calendar-current-day a:focus {
	background-color: #5881B5;
}

.portlet-calendar .has-events a span {
	background: url(<%= themeImagesPath %>/calendar/event_indicator.png) no-repeat 50% 95%;
	padding-bottom: 5px;
}

.portlet-calendar .calendar-current-day.has-events a span {
	background-image: url(<%= themeImagesPath %>/calendar/event_indicator_current.png);
}