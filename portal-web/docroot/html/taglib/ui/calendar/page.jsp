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

<%@ include file="/html/taglib/init.jsp" %>

<%
int month = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:calendar:month"));
int day = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:calendar:day"));
int year = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:calendar:year"));
String headerPattern = (String)request.getAttribute("liferay-ui:calendar:headerPattern");
Format headerFormat = (Format)request.getAttribute("liferay-ui:calendar:headerFormat");
Set data = (Set)request.getAttribute("liferay-ui:calendar:data");
boolean showAllPotentialWeeks = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:calendar:showAllPotentialWeeks"));

Calendar selCal = CalendarFactoryUtil.getCalendar(timeZone, locale);

selCal.set(Calendar.MONTH, month);
selCal.set(Calendar.DATE, day);
selCal.set(Calendar.YEAR, year);

int selMonth = selCal.get(Calendar.MONTH);
int selDay = selCal.get(Calendar.DATE);
int selYear = selCal.get(Calendar.YEAR);

int maxDayOfMonth = selCal.getActualMaximum(Calendar.DATE);

selCal.set(Calendar.DATE, 1);
int dayOfWeek = selCal.get(Calendar.DAY_OF_WEEK);
selCal.set(Calendar.DATE, selDay);

Calendar curCal = CalendarFactoryUtil.getCalendar(timeZone, locale);

int curMonth = curCal.get(Calendar.MONTH);
int curDay = curCal.get(Calendar.DATE);
int curYear = curCal.get(Calendar.YEAR);

Calendar prevCal = (Calendar)selCal.clone();

prevCal.add(Calendar.MONTH, -1);

int maxDayOfPrevMonth = prevCal.getActualMaximum(Calendar.DATE);
int weekNumber = 1;
%>

<div class="taglib-calendar">
	<table class="lfr-table calendar-panel">

	<c:if test="<%= Validator.isNotNull(headerPattern) || (headerFormat != null) %>">

		<%
		Format dateFormat = headerFormat;

		if (Validator.isNotNull(headerPattern)) {
			dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(headerPattern, locale);
		}
		%>

		<tr class="calendar-header">
			<th colspan="7">
				<%= dateFormat.format(selCal.getTime()) %>
			</th>
		</tr>
	</c:if>

	<tr class="portlet-section-header results-header">

		<%
		for (int i = 0; i < 7; i++) {
			int daysIndex = (selCal.getFirstDayOfWeek() + i - 1) % 7;

			String className = StringPool.BLANK;

			if (i == 0) {
				className = "first";
			}
			else if (i == 6) {
				className = "last";
			}
		%>

			<th class="<%= className %>">
				<%= LanguageUtil.get(pageContext, CalendarUtil.DAYS_ABBREVIATION[daysIndex]) %>
			</th>

		<%
		}
		%>

	</tr>
	<tr>

		<%
		if (((selCal.getFirstDayOfWeek()) == Calendar.MONDAY)) {
			if (dayOfWeek == 1) {
				dayOfWeek += 6;
			}
			else {
				dayOfWeek --;
			}
		}

		maxDayOfPrevMonth = (maxDayOfPrevMonth - dayOfWeek) + 1;

		for (int i = 1; i < dayOfWeek; i++) {
			String className = "calendar-inactive calendar-previous-month";

			if (i == 1) {
				className += " first";
			}
			else if (i == 7) {
				className += " last";
			}
		%>

			<td class="<%= className %>"><%= maxDayOfPrevMonth + i %></td>

		<%
		}

		for (int i = 1; i <= maxDayOfMonth; i++) {
			if (dayOfWeek > 7) {
		%>

				</tr>
				<tr>

		<%
				dayOfWeek = 1;
				weekNumber++;
			}

			Calendar tempCal = (Calendar)selCal.clone();

			tempCal.set(Calendar.MONTH, selMonth);
			tempCal.set(Calendar.DATE, i);
			tempCal.set(Calendar.YEAR, selYear);

			boolean hasData = (data != null) && data.contains(new Integer(i));

			String className = "";

			if ((selMonth == curMonth) &&
				(i == curDay) &&
				(selYear == curYear)) {

				className = "calendar-current-day portlet-section-selected";
			}

			if (hasData) {
				className += " has-events";
			}

			if (dayOfWeek == 1) {
				className += " first";
			}
			else if (dayOfWeek == 7) {
				className += " last";
			}

			dayOfWeek++;
		%>

			<td class="<%= className %>">
				<a href="javascript:<%= namespace %>updateCalendar(<%= selMonth %>, <%= i %>, <%= selYear %>);"><span><%= i %></span></a>
			</td>

		<%
		}

		int dayOfNextMonth = 1;

		for (int i = 7; i >= dayOfWeek; i--) {
			String className = "calendar-inactive calendar-next-month";

			if (dayOfWeek == 1) {
				className += " first";
			}
			else if (i == dayOfWeek) {
				className += " last";
			}
		%>

			<td class="<%= className %>"><%= dayOfNextMonth++ %></td>

		<%
		}

		if (showAllPotentialWeeks && weekNumber < 6) {
		%>

			<tr>

				<%
				for (int i = 1; i <= 7; i++) {
					String className = "calendar-inactive calendar-next-month";

					if (i == 1) {
						className += " first";
					}
					else if (i == 7) {
						className += " last";
					}
				%>

					<td class="<%= className %>"><%= dayOfNextMonth++ %></td>

				<%
				}
				%>

			</tr>

		<%
		}
		%>

	</tr>
	</table>
</div>