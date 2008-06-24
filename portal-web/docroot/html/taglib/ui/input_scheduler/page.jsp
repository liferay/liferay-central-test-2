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

<%@ include file="/html/taglib/init.jsp" %>

<fieldset>
	<legend><liferay-ui:message key="schedule-event" /></legend>

	<table class="lfr-table">
	<tr>
		<td>
			<liferay-ui:message key="description" />
		</td>
		<td>
			<input name="<portlet:namespace />description" type="text" />
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="start-date" />
		</td>
		<td>

			<%
			Calendar cal = CalendarFactoryUtil.getCalendar(timeZone, locale);
			%>

			<liferay-ui:input-date
				monthParam="schedulerStartDateMonth"
				monthValue="<%= cal.get(Calendar.MONTH) %>"
				dayParam="schedulerStartDateDay"
				dayValue="<%= cal.get(Calendar.DATE) %>"
				yearParam="schedulerStartDateYear"
				yearValue="<%= cal.get(Calendar.YEAR) %>"
				yearRangeStart="<%= cal.get(Calendar.YEAR) - 100 %>"
				yearRangeEnd="<%= cal.get(Calendar.YEAR) %>"
				firstDayOfWeek="<%= cal.getFirstDayOfWeek() - 1 %>"
				disabled="<%= false %>"
			/>

			&nbsp;

			<liferay-ui:input-time
				hourParam="schedulerStartDateHour"
				hourValue="<%= cal.get(Calendar.HOUR) %>"
				minuteParam="schedulerStartDateMinute"
				minuteValue="<%= cal.get(Calendar.MINUTE) %>"
				minuteInterval="1"
				amPmParam="schedulerStartDateAmPm"
				amPmValue="<%= cal.get(Calendar.AM_PM) %>"
			/>

		</td>
	</tr>
	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="end-date" />
		</td>
		<td valign="top">
			<table class="lfr-table">
			<tr>
				<td>
					<input checked name="<portlet:namespace />endDateType" type="radio" value="0"> <liferay-ui:message key="no-end-date" /><br />

					<input name="<portlet:namespace />endDateType" type="radio" value="1"> <liferay-ui:message key="end-by" />

					<liferay-ui:input-date
						monthParam="schedulerEndDateMonth"
						monthValue="<%= cal.get(Calendar.MONTH) %>"
						dayParam="schedulerEndDateDay"
						dayValue="<%= cal.get(Calendar.DATE) %>"
						yearParam="schedulerEndDateYear"
						yearValue="<%= cal.get(Calendar.YEAR) %>"
						yearRangeStart="<%= cal.get(Calendar.YEAR) - 100 %>"
						yearRangeEnd="<%= cal.get(Calendar.YEAR) %>"
						firstDayOfWeek="<%= cal.getFirstDayOfWeek() - 1 %>"
						disabled="<%= false %>"
					/>

					&nbsp;

					<liferay-ui:input-time
						hourParam="schedulerEndDateHour"
						hourValue="<%= cal.get(Calendar.HOUR) %>"
						minuteParam="schedulerEndDateMinute"
						minuteValue="<%= cal.get(Calendar.MINUTE) %>"
						minuteInterval="1"
						amPmParam="schedulerEndDateAmPm"
						amPmValue="<%= cal.get(Calendar.AM_PM) %>"
					/>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	</table>
</fieldset>

<fieldset>
	<legend><liferay-ui:message key="repeat" /></legend>

	<table class="lfr-table">
	<tr>
		<td>
			<input checked name="<portlet:namespace />recurrenceType" type="radio" value="<%= Recurrence.NO_RECURRENCE %>" onClick="<portlet:namespace />showTable('<portlet:namespace />neverTable');"> <liferay-ui:message key="never" /><br />
			<input name="<portlet:namespace />recurrenceType" type="radio" value="<%= Recurrence.DAILY %>" onClick="<portlet:namespace />showTable('<portlet:namespace />dailyTable');"> <liferay-ui:message key="daily" /><br />
			<input name="<portlet:namespace />recurrenceType" type="radio" value="<%= Recurrence.WEEKLY %>" onClick="<portlet:namespace />showTable('<portlet:namespace />weeklyTable');"> <liferay-ui:message key="weekly" /><br />
			<input name="<portlet:namespace />recurrenceType" type="radio" value="<%= Recurrence.MONTHLY %>" onClick="<portlet:namespace />showTable('<portlet:namespace />monthlyTable');"> <liferay-ui:message key="monthly" /><br />
			<input name="<portlet:namespace />recurrenceType" type="radio" value="<%= Recurrence.YEARLY %>" onClick="<portlet:namespace />showTable('<portlet:namespace />yearlyTable');"> <liferay-ui:message key="yearly" />
		</td>
		<td valign="top">
			<div id="<portlet:namespace />neverTable" style="display: none;">
				<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<liferay-ui:message key="do-not-repeat-this-event" />
					</td>
				</tr>
				</table>
			</div>

			<div id="<portlet:namespace />dailyTable" style="display: none;">
				<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<input checked name="<portlet:namespace />dailyType" type="radio" value="0"> <input maxlength="3" name="<portlet:namespace />dailyInterval" size="3" type="text" /> <liferay-ui:message key="day-s" /><br />
						<input name="<portlet:namespace />dailyType" type="radio" value="1"> <liferay-ui:message key="every-weekday" />
					</td>
				</tr>
				</table>
			</div>

			<div id="<portlet:namespace />weeklyTable" style="display: none;">
				<table class="lfr-table">
				<tr>
					<td>
						<liferay-ui:message key="recur-every" /> <input maxlength="2" name="<portlet:namespace />weeklyInterval" size="2" type="text" /> <liferay-ui:message key="weeks-on" />

						<%
						String[] days = CalendarUtil.getDays(locale);
						%>

						<table class="lfr-table">
						<tr>
							<td nowrap>
								<input name="<portlet:namespace />weeklyDayPos<%= Calendar.SUNDAY %>" type="checkbox"> <%= days[0] %>
							</td>
							<td nowrap>
								<input name="<portlet:namespace />weeklyDayPos<%= Calendar.MONDAY %>" type="checkbox"> <%= days[1] %>
							</td>
							<td nowrap>
								<input name="<portlet:namespace />weeklyDayPos<%= Calendar.TUESDAY %>" type="checkbox"> <%= days[2] %>
							</td>
							<td nowrap>
								<input  name="<portlet:namespace />weeklyDayPos<%= Calendar.WEDNESDAY %>" type="checkbox"> <%= days[3] %>
							</td>
						</tr>
						<tr>
							<td nowrap>
								<input name="<portlet:namespace />weeklyDayPos<%= Calendar.THURSDAY %>" type="checkbox"> <%= days[4] %>
							</td>
							<td nowrap>
								<input name="<portlet:namespace />weeklyDayPos<%= Calendar.FRIDAY %>" type="checkbox"> <%= days[5] %>
							</td>
							<td colspan="2" nowrap>
								<input name="<portlet:namespace />weeklyDayPos<%= Calendar.SATURDAY %>" type="checkbox"> <%= days[6] %>
							</td>
						</tr>
						</table>
					</td>
				</tr>
				</table>
			</div>

			<div id="<portlet:namespace />monthlyTable" style="display: none;">
				<table class="lfr-table">
				<tr>
					<td nowrap>
						<input checked name="<portlet:namespace />monthlyType" type="radio" value="0"> <liferay-ui:message key="day" /> <input maxlength="2" name="<portlet:namespace />monthlyDay0" size="2" type="text" /> <liferay-ui:message key="of-every" /> <input maxlength="2" name="<portlet:namespace />monthlyInterval0" size="2" type="text" /> <liferay-ui:message key="month-s" /><br />

						<input name="<portlet:namespace />monthlyType" type="radio" value="1">

						<liferay-ui:message key="the" />

						<select name="<portlet:namespace />monthlyPos">
							<option value="1"><liferay-ui:message key="first" /></option>
							<option value="2"><liferay-ui:message key="second" /></option>
							<option value="3"><liferay-ui:message key="third" /></option>
							<option value="4"><liferay-ui:message key="fourth" /></option>
							<option value="-1"><liferay-ui:message key="last" /></option>
						</select>

						<select name="<portlet:namespace />monthlyDay1">
							<option value="<%= Calendar.SUNDAY %>"><%= days[0] %></option>
							<option value="<%= Calendar.MONDAY %>"><%= days[1] %></option>
							<option value="<%= Calendar.TUESDAY %>"><%= days[2] %></option>
							<option value="<%= Calendar.WEDNESDAY %>"><%= days[3] %></option>
							<option value="<%= Calendar.THURSDAY %>"><%= days[4] %></option>
							<option value="<%= Calendar.FRIDAY %>"><%= days[5] %></option>
							<option value="<%= Calendar.SATURDAY %>"><%= days[6] %></option>
						</select>

						<liferay-ui:message key="of-every" /> <input maxlength="2" name="<portlet:namespace />monthlyInterval1" size="2" type="text" /> <liferay-ui:message key="month-s" />
					</td>
				</tr>
				</table>
			</div>

			<div id="<portlet:namespace />yearlyTable" style="display: none;">
				<table class="lfr-table">
				<tr>
					<td nowrap>
						<input checked name="<portlet:namespace />yearlyType" type="radio" value="0"> <liferay-ui:message key="every" />

						<select name="<portlet:namespace />yearlyMonth0">

						<%
						int[] monthIds = CalendarUtil.getMonthIds();
						String[] months = CalendarUtil.getMonths(locale);

						for (int i = 0; i < 12; i++) {
						%>

							<option value="<%= monthIds[i] %>"><%= months[i] %></option>

						<%
						}
						%>

						</select>

						<input maxlength="2" name="<portlet:namespace />yearlyDay0" size="2" type="text" /> <liferay-ui:message key="of-every" /> <input maxlength="2" name="<portlet:namespace />yearlyInterval0" size="2" type="text" /> <liferay-ui:message key="year-s" /><br />

						<input name="<portlet:namespace />yearlyType" type="radio" value="1"> <liferay-ui:message key="the" />

						<select name="<portlet:namespace />yearlyPos">
							<option value="1"><liferay-ui:message key="first" /></option>
							<option value="2"><liferay-ui:message key="second" /></option>
							<option value="3"><liferay-ui:message key="third" /></option>
							<option value="4"><liferay-ui:message key="fourth" /></option>
							<option value="-1"><liferay-ui:message key="last" /></option>
						</select>

						<select name="<portlet:namespace />yearlyDay1">
							<option value="<%= Calendar.SUNDAY %>"><%= days[0] %></option>
							<option value="<%= Calendar.MONDAY %>"><%= days[1] %></option>
							<option value="<%= Calendar.TUESDAY %>"><%= days[2] %></option>
							<option value="<%= Calendar.WEDNESDAY %>"><%= days[3] %></option>
							<option value="<%= Calendar.THURSDAY %>"><%= days[4] %></option>
							<option value="<%= Calendar.FRIDAY %>"><%= days[5] %></option>
							<option value="<%= Calendar.SATURDAY %>"><%= days[6] %></option>
						</select>

						<liferay-ui:message key="of" />

						<select name="<portlet:namespace />yearlyMonth1">

							<%
							for (int i = 0; i < 12; i++) {
							%>

								<option value="<%= monthIds[i] %>"><%= months[i] %></option>

							<%
							}
							%>

						</select>

						<liferay-ui:message key="of-every" /> <input maxlength="2" name="<portlet:namespace />yearlyInterval1" size="2" type="text" /> <liferay-ui:message key="year-s" />
					</td>
				</tr>
				</table>
			</div>
		</td>
	</tr>
	</table>
</fieldset>