<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portlet.calendar.model.CalEvent" %>

<aui:fieldset label="schedule-event">
	<aui:input name="description" type="text" />

	<%
	Calendar cal = CalendarFactoryUtil.getCalendar(timeZone, locale);
	%>

	<aui:field-wrapper label="start-date">
		<div class="aui-field-row">
			<liferay-ui:input-date
				monthParam="schedulerStartDateMonth"
				monthValue="<%= cal.get(Calendar.MONTH) %>"
				dayParam="schedulerStartDateDay"
				dayValue="<%= cal.get(Calendar.DATE) %>"
				yearParam="schedulerStartDateYear"
				yearValue="<%= cal.get(Calendar.YEAR) %>"
				yearRangeStart="<%= cal.get(Calendar.YEAR) %>"
				yearRangeEnd="<%= cal.get(Calendar.YEAR) + 5 %>"
				firstDayOfWeek="<%= cal.getFirstDayOfWeek() - 1 %>"
				disabled="<%= false %>"
			/>

			&nbsp;

			<liferay-ui:input-time
				hourParam="schedulerStartDateHour"
				hourValue="<%= cal.get(Calendar.HOUR) %>"
				minuteParam="schedulerStartDateMinute"
				minuteValue="<%= cal.get(Calendar.MINUTE) %>"
				minuteInterval="<%= 1 %>"
				amPmParam="schedulerStartDateAmPm"
				amPmValue="<%= cal.get(Calendar.AM_PM) %>"
			/>
		</div>
	</aui:field-wrapper>

	<aui:field-wrapper label="end-date">
		<div class="aui-field-row">
			<aui:input checked="<%= true %>" label="no-end-date" name="endDateType" type="radio" value="0" />
		</div>

		<div class="aui-field-row">
			<aui:input first="true" inlineField="<%= true %>" label="end-by" name="endDateType" type="radio" value="1" />

			<liferay-ui:input-date
				monthParam="schedulerEndDateMonth"
				monthValue="<%= cal.get(Calendar.MONTH) %>"
				dayParam="schedulerEndDateDay"
				dayValue="<%= cal.get(Calendar.DATE) %>"
				yearParam="schedulerEndDateYear"
				yearValue="<%= cal.get(Calendar.YEAR) %>"
				yearRangeStart="<%= cal.get(Calendar.YEAR) %>"
				yearRangeEnd="<%= cal.get(Calendar.YEAR) + 5 %>"
				firstDayOfWeek="<%= cal.getFirstDayOfWeek() - 1 %>"
				disabled="<%= false %>"
			/>

			&nbsp;

			<liferay-ui:input-time
				hourParam="schedulerEndDateHour"
				hourValue="<%= cal.get(Calendar.HOUR) %>"
				minuteParam="schedulerEndDateMinute"
				minuteValue="<%= cal.get(Calendar.MINUTE) %>"
				minuteInterval="<%= 1 %>"
				amPmParam="schedulerEndDateAmPm"
				amPmValue="<%= cal.get(Calendar.AM_PM) %>"
			/>
		</div>

	</aui:field-wrapper>
</aui:fieldset>

<liferay-ui:input-repeat />

<aui:script>
	function <portlet:namespace />showTable(id) {
		document.getElementById("<portlet:namespace />neverTable").style.display = "none";
		document.getElementById("<portlet:namespace />dailyTable").style.display = "none";
		document.getElementById("<portlet:namespace />weeklyTable").style.display = "none";
		document.getElementById("<portlet:namespace />monthlyTable").style.display = "none";
		document.getElementById("<portlet:namespace />yearlyTable").style.display = "none";

		document.getElementById(id).style.display = "block";
	}
</aui:script>