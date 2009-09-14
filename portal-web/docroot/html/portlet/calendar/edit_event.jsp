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

<%@ include file="/html/portlet/calendar/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

CalEvent event = (CalEvent)request.getAttribute(WebKeys.CALENDAR_EVENT);

long eventId = BeanParamUtil.getLong(event, request, "eventId");

Calendar startDate = CalendarUtil.roundByMinutes((Calendar)selCal.clone(), 15);

if (event != null) {
	if (!event.isTimeZoneSensitive()) {
		startDate = CalendarFactoryUtil.getCalendar();
	}

	startDate.setTime(event.getStartDate());
}

Calendar endDate = (Calendar)curCal.clone();

endDate.add(Calendar.YEAR, 1);

if (event != null) {
	if (!event.isTimeZoneSensitive()) {
		endDate = CalendarFactoryUtil.getCalendar();
	}

	if (event.getEndDate() != null) {
		endDate.setTime(event.getEndDate());
	}
}

endDate.set(Calendar.HOUR_OF_DAY, 23);
endDate.set(Calendar.MINUTE, 59);
endDate.set(Calendar.SECOND, 59);

int durationHour = BeanParamUtil.getInteger(event, request, "durationHour", 1);
int durationMinute = BeanParamUtil.getInteger(event, request, "durationMinute");
String type = BeanParamUtil.getString(event, request, "type");
boolean repeating = BeanParamUtil.getBoolean(event, request, "repeating");

Recurrence recurrence = null;

int recurrenceType = ParamUtil.getInteger(request, "recurrenceType", Recurrence.NO_RECURRENCE);
String recurrenceTypeParam = ParamUtil.getString(request, "recurrenceType");
if (Validator.isNull(recurrenceTypeParam) && (event != null)) {
	if (event.getRepeating()) {
		recurrence = event.getRecurrenceObj();
		recurrenceType = recurrence.getFrequency();
	}
}

int dailyType = ParamUtil.getInteger(request, "dailyType");
String dailyTypeParam = ParamUtil.getString(request, "dailyType");
if (Validator.isNull(dailyTypeParam) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		if (recurrence.getByDay() != null) {
			dailyType = 1;
		}
	}
}

int dailyInterval = ParamUtil.getInteger(request, "dailyInterval", 1);
String dailyIntervalParam = ParamUtil.getString(request, "dailyInterval");
if (Validator.isNull(dailyIntervalParam) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		dailyInterval = recurrence.getInterval();
	}
}

int weeklyInterval = ParamUtil.getInteger(request, "weeklyInterval", 1);
String weeklyIntervalParam = ParamUtil.getString(request, "weeklyInterval");
if (Validator.isNull(weeklyIntervalParam) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		weeklyInterval = recurrence.getInterval();
	}
}

boolean weeklyPosSu = _getWeeklyDayPos(request, Calendar.SUNDAY, event, recurrence);
boolean weeklyPosMo = _getWeeklyDayPos(request, Calendar.MONDAY, event, recurrence);
boolean weeklyPosTu = _getWeeklyDayPos(request, Calendar.TUESDAY, event, recurrence);
boolean weeklyPosWe = _getWeeklyDayPos(request, Calendar.WEDNESDAY, event, recurrence);
boolean weeklyPosTh = _getWeeklyDayPos(request, Calendar.THURSDAY, event, recurrence);
boolean weeklyPosFr = _getWeeklyDayPos(request, Calendar.FRIDAY, event, recurrence);
boolean weeklyPosSa = _getWeeklyDayPos(request, Calendar.SATURDAY, event, recurrence);

int monthlyType = ParamUtil.getInteger(request, "monthlyType");
String monthlyTypeParam = ParamUtil.getString(request, "monthlyType");
if (Validator.isNull(monthlyTypeParam) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		if (recurrence.getByMonthDay() == null) {
			monthlyType = 1;
		}
	}
}

int monthlyDay0 = ParamUtil.getInteger(request, "monthlyDay0", 15);
String monthlyDay0Param = ParamUtil.getString(request, "monthlyDay0");
if (Validator.isNull(monthlyDay0Param) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		if (recurrence.getByMonthDay() != null) {
			monthlyDay0 = recurrence.getByMonthDay()[0];
		}
	}
}

int monthlyInterval0 = ParamUtil.getInteger(request, "monthlyInterval0", 1);
String monthlyInterval0Param = ParamUtil.getString(request, "monthlyInterval0");
if (Validator.isNull(monthlyInterval0Param) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		monthlyInterval0 = recurrence.getInterval();
	}
}

int monthlyPos = ParamUtil.getInteger(request, "monthlyPos", 1);
String monthlyPosParam = ParamUtil.getString(request, "monthlyPos");
if (Validator.isNull(monthlyPosParam) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		if (recurrence.getByMonth() != null) {
			monthlyPos = recurrence.getByMonth()[0];
		}
		else if (recurrence.getByDay() != null) {
			monthlyPos = recurrence.getByDay()[0].getDayPosition();
		}
	}
}

int monthlyDay1 = ParamUtil.getInteger(request, "monthlyDay1", Calendar.SUNDAY);
String monthlyDay1Param = ParamUtil.getString(request, "monthlyDay1");
if (Validator.isNull(monthlyDay1Param) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		if (recurrence.getByMonth() != null) {
			monthlyDay1 = -1;
		}
		else if (recurrence.getByDay() != null) {
			monthlyDay1 = recurrence.getByDay()[0].getDayOfWeek();
		}
	}
}

int monthlyInterval1 = ParamUtil.getInteger(request, "monthlyInterval1", 1);
String monthlyInterval1Param = ParamUtil.getString(request, "monthlyInterval1");
if (Validator.isNull(monthlyInterval1Param) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		monthlyInterval1 = recurrence.getInterval();
	}
}

int yearlyType = ParamUtil.getInteger(request, "yearlyType");
String yearlyTypeParam = ParamUtil.getString(request, "yearlyType");
if (Validator.isNull(yearlyTypeParam) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		if (recurrence.getByMonth() != null) {
			yearlyType = 1;
		}
	}
}

int yearlyMonth0 = ParamUtil.getInteger(request, "yearlyMonth0", Calendar.JANUARY);
String yearlyMonth0Param = ParamUtil.getString(request, "yearlyMonth0");
if (Validator.isNull(yearlyMonth0Param) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		if (recurrence.getByMonth() == null) {
			yearlyMonth0 = recurrence.getDtStart().get(Calendar.MONTH);
		}
	}
}

int yearlyDay0 = ParamUtil.getInteger(request, "yearlyDay0", 15);
String yearlyDay0Param = ParamUtil.getString(request, "yearlyDay0");
if (Validator.isNull(yearlyDay0Param) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		if (recurrence.getByMonth() == null) {
			yearlyDay0 = recurrence.getDtStart().get(Calendar.DATE);
		}
	}
}

int yearlyInterval0 = ParamUtil.getInteger(request, "yearlyInterval0", 1);
String yearlyInterval0Param = ParamUtil.getString(request, "yearlyInterval0");
if (Validator.isNull(yearlyInterval0Param) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		yearlyInterval0 = recurrence.getInterval();
	}
}

int yearlyPos = ParamUtil.getInteger(request, "yearlyPos", 1);
String yearlyPosParam = ParamUtil.getString(request, "yearlyPos");
if (Validator.isNull(yearlyPosParam) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		if (recurrence.getByMonth() != null) {
			yearlyPos = recurrence.getByMonth()[0];
		}
		else if (recurrence.getByDay() != null) {
			yearlyPos = recurrence.getByDay()[0].getDayPosition();
		}
	}
}

int yearlyDay1 = ParamUtil.getInteger(request, "yearlyDay1", Calendar.SUNDAY);
String yearlyDay1Param = ParamUtil.getString(request, "yearlyDay1");
if (Validator.isNull(yearlyDay1Param) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		if (recurrence.getByMonth() != null) {
			yearlyDay1 = -1;
		}
		else if (recurrence.getByDay() != null) {
			yearlyDay1 = recurrence.getByDay()[0].getDayOfWeek();
		}
	}
}

int yearlyMonth1 = ParamUtil.getInteger(request, "yearlyMonth1", Calendar.JANUARY);
String yearlyMonth1Param = ParamUtil.getString(request, "yearlyMonth1");
if (Validator.isNull(yearlyMonth1Param) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		if (recurrence.getByMonth() != null) {
			yearlyMonth1 = recurrence.getByMonth()[0];
		}
	}
}

int yearlyInterval1 = ParamUtil.getInteger(request, "yearlyInterval1", 1);
String yearlyInterval1Param = ParamUtil.getString(request, "yearlyInterval1");
if (Validator.isNull(yearlyInterval1Param) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		yearlyInterval1 = recurrence.getInterval();
	}
}

int endDateType = ParamUtil.getInteger(request, "endDateType");
String endDateTypeParam = ParamUtil.getString(request, "endDateType");
if (Validator.isNull(endDateTypeParam) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		if (recurrence.getUntil() != null) {
			endDateType = 2;
		}
		else if (recurrence.getOccurrence() > 0) {
			endDateType = 1;
		}
	}
}

int endDateOccurrence = ParamUtil.getInteger(request, "endDateOccurrence", 10);
String endDateOccurrenceParam = ParamUtil.getString(request, "endDateOccurrence");
if (Validator.isNull(endDateOccurrenceParam) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		endDateOccurrence = recurrence.getOccurrence();
	}
}

int remindBy = BeanParamUtil.getInteger(event, request, "remindBy", CalEventImpl.REMIND_BY_EMAIL);
int firstReminder = BeanParamUtil.getInteger(event, request, "firstReminder", (int)Time.MINUTE * 15);
int secondReminder = BeanParamUtil.getInteger(event, request, "secondReminder", (int)Time.MINUTE * 5);
%>

<script type="text/javascript">
	function <portlet:namespace />init() {
		<c:choose>
			<c:when test="<%= recurrenceType == Recurrence.NO_RECURRENCE %>">
				<portlet:namespace />showTable("<portlet:namespace />neverTable");
			</c:when>
			<c:when test="<%= recurrenceType == Recurrence.DAILY %>">
				<portlet:namespace />showTable("<portlet:namespace />dailyTable");
			</c:when>
			<c:when test="<%= recurrenceType == Recurrence.WEEKLY %>">
				<portlet:namespace />showTable("<portlet:namespace />weeklyTable");
			</c:when>
			<c:when test="<%= recurrenceType == Recurrence.MONTHLY %>">
				<portlet:namespace />showTable("<portlet:namespace />monthlyTable");
			</c:when>
			<c:when test="<%= recurrenceType == Recurrence.YEARLY %>">
				<portlet:namespace />showTable("<portlet:namespace />yearlyTable");
			</c:when>
		</c:choose>
	}

	function <portlet:namespace />showTable(id) {
		document.getElementById("<portlet:namespace />neverTable").style.display = "none";
		document.getElementById("<portlet:namespace />dailyTable").style.display = "none";
		document.getElementById("<portlet:namespace />weeklyTable").style.display = "none";
		document.getElementById("<portlet:namespace />monthlyTable").style.display = "none";
		document.getElementById("<portlet:namespace />yearlyTable").style.display = "none";

		document.getElementById(id).style.display = "block";
	}

	function <portlet:namespace />saveEvent() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= event == null ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}
</script>

<portlet:actionURL var="editEventURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
	<portlet:param name="struts_action" value="/calendar/edit_event" />
</portlet:actionURL>

<aui:form action="<%= editEventURL %>" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "saveEvent(); return false;" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="eventId" type="hidden" value="<%= eventId %>" />

	<liferay-ui:error exception="<%= EventDurationException.class %>" message="please-enter-a-longer-duration" />
	<liferay-ui:error exception="<%= EventStartDateException.class %>" message="please-enter-a-valid-start-date" />
	<liferay-ui:error exception="<%= EventTitleException.class %>" message="please-enter-a-valid-title" />

	<aui:model-context bean="<%= event %>" model="<%= CalEvent.class %>" />

	<aui:fieldset>
		<aui:input name="startDate" value="<%= startDate %>" />

		<aui:field-wrapper label="duration">
			<aui:column>
				<aui:select label="hours" name="durationHour">

					<%
						for (int i = 0; i <= 24 ; i++) {
					%>
							<aui:option label="<%= i %>" selected="<%= durationHour == i %>" />
					<%
						}
					%>

				</aui:select>
			</aui:column>
			<aui:column>
				<aui:select label="minutes" name="durationMinute">

					<%
						for (int i=0; i <  60 ; i = i + 5) {
					%>
							<aui:option label='<%= ":" + (i < 10 ? "0" : StringPool.BLANK) + i %>' selected="<%= durationMinute == i %>" value="<%= i %>" />
					<%
						}
					%>

				</aui:select>
			</aui:column>
		</aui:field-wrapper>

		<aui:input inlineLabel="<%= true %>" label="all-day-event" name="allDay" type="checkbox" value="<%= event == null ? false : event.isAllDay() %>" />

		<aui:input inlineLabel="<%= true %>" name="timeZoneSensitive" type="checkbox" value="<%=  event == null ? true : event.isTimeZoneSensitive() %>" />

		<aui:input name="title" value='<%= event == null ? LanguageUtil.get(pageContext, "new-event") : event.getTitle() %>' />

		<aui:input name="description" />

		<aui:select name="type">

			<%
			for (int i = 0; i < CalEventImpl.TYPES.length; i++) {
			%>

				<aui:option label="<%= CalEventImpl.TYPES[i] %>" selected="<%= type.equals(CalEventImpl.TYPES[i]) %>" />

			<%
			}
			%>

		</aui:select>

		<liferay-ui:custom-attributes-available className="<%= CalEvent.class.getName() %>">
			<liferay-ui:custom-attribute-list
				className="<%= CalEvent.class.getName() %>"
				classPK="<%= (event != null) ? event.getEventId() : 0 %>"
				editable="<%= true %>"
				label="<%= true %>"
			/>
		</liferay-ui:custom-attributes-available>

		<c:if test="<%= event == null %>">
			<aui:field-wrapper label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= CalEvent.class.getName() %>"
				/>
			</aui:field-wrapper>
		</c:if>
	</aui:fieldset>

	<br />

	<liferay-ui:panel-container id="editEvent" extended="<%= Boolean.TRUE %>" persistState="<%= true %>">
		<liferay-ui:panel id="repeat" title='<%= LanguageUtil.get(pageContext, "repeat") %>' collapsible="<%= true %>" persistState="<%= true %>" extended="<%= true %>">
			<liferay-ui:error exception="<%= EventEndDateException.class %>" message="please-enter-a-valid-end-date" />

			<aui:fieldset>
				<aui:column columnWidth="20">
					<aui:field-wrapper label="repeat" name="recurrenceType">
						<aui:input checked="<%= recurrenceType == Recurrence.NO_RECURRENCE %>" label="never" name="recurrenceType" type="radio" value="<%= Recurrence.NO_RECURRENCE %>" onClick='<%= renderResponse.getNamespace() + "showTable('" + renderResponse.getNamespace() + "neverTable');" %>' />
						<aui:input checked="<%= recurrenceType == Recurrence.DAILY %>" label="daily" name="recurrenceType" type="radio" value="<%= Recurrence.DAILY %>" onClick='<%= renderResponse.getNamespace() + "showTable('" + renderResponse.getNamespace() + "dailyTable');" %>' />
						<aui:input checked="<%= recurrenceType == Recurrence.WEEKLY %>" label="weekly" name="recurrenceType" type="radio" value="<%= Recurrence.WEEKLY %>" onClick='<%= renderResponse.getNamespace() + "showTable('" + renderResponse.getNamespace() + "weeklyTable');" %>' />
						<aui:input checked="<%= recurrenceType == Recurrence.MONTHLY %>" label="monthly" name="recurrenceType" type="radio" value="<%= Recurrence.MONTHLY %>" onClick='<%= renderResponse.getNamespace() + "showTable('" + renderResponse.getNamespace() + "monthlyTable');" %>' />
						<aui:input checked="<%= recurrenceType == Recurrence.YEARLY %>" label="yearly" name="recurrenceType" type="radio" value="<%= Recurrence.YEARLY %>" onClick='<%= renderResponse.getNamespace() + "showTable('" + renderResponse.getNamespace() + "yearlyTable');" %>' />
					</aui:field-wrapper>
				</aui:column>

				<aui:column columnWidth="80">
					<div id="<portlet:namespace />neverTable" style="display: none;">
						<liferay-ui:message key="do-not-repeat-this-event" />
					</div>

					<div id="<portlet:namespace />dailyTable" style="display: none;">
						<aui:input checked="<%= dailyType == 0 %>" cssClass="input-container" inlineField="<%= true %>" label="recur-every" name="dailyType" type="radio" value="0" />
						<aui:input inlineField="<%= true %>" label="" maxlength="3" name="dailyInterval" size="3" type="text" value="<%= dailyInterval %>" /> <span class="after-field-text"><liferay-ui:message key="day-s" /></span><br />
						<aui:input checked="<%= (dailyType == 1) %>" label="every-weekday" name="dailyType" type="radio" value="1" />
					</div>

					<div id="<portlet:namespace />weeklyTable" style="display: none;">
						<aui:input inlineField="<%= true %>" inlineLabel="<%= true %>" label="recur-every" maxlength="2" name="weeklyInterval" size="2" type="text" value="<%= weeklyInterval %>" />
						<span class="after-field-text"><liferay-ui:message key="weeks-on" /></span>

						<aui:layout cssClass="weekdays">
							<aui:column>
								<aui:input checked="<%= weeklyPosSu %>" inlineLabel="<%= true %>" label="<%= days[0] %>" name="weeklyDayPos<%= Calendar.SUNDAY %>" type="checkbox" />
								<aui:input checked="<%= weeklyPosTh %>" inlineLabel="<%= true %>" label="<%= days[4] %>" name="weeklyDayPos<%= Calendar.THURSDAY %>" type="checkbox" />
							</aui:column>

							<aui:column>
								<aui:input checked="<%= weeklyPosMo %>" inlineLabel="<%= true %>" label="<%= days[1] %>" name="weeklyDayPos<%= Calendar.MONDAY %>" type="checkbox" />
								<aui:input checked="<%= weeklyPosFr %>" inlineLabel="<%= true %>" label="<%= days[5] %>" name="weeklyDayPos<%= Calendar.FRIDAY %>" type="checkbox" />
							</aui:column>

							<aui:column>
								<aui:input checked="<%= weeklyPosTu %>"inlineLabel="<%= true %>" label="<%= days[2] %>"  name="weeklyDayPos<%= Calendar.TUESDAY %>" type="checkbox" />
								<aui:input checked="<%= weeklyPosSa %>" inlineLabel="<%= true %>" label="<%= days[6] %>" name="weeklyDayPos<%= Calendar.SATURDAY %>" type="checkbox" />
							</aui:column>

							<aui:column>
								<aui:input checked="<%= weeklyPosWe %>" inlineLabel="<%= true %>" label="<%= days[3] %>" name="weeklyDayPos<%= Calendar.WEDNESDAY %>" type="checkbox" />
							</aui:column>
						</aui:layout>
					</div>

					<div id="<portlet:namespace />monthlyTable" style="display: none;">
						<aui:input checked="<%= (monthlyType == 0) %>" cssClass="input-container" inlineField="<%= true %>" label="day" name="monthlyType" type="radio" value="0" />

						<aui:input inlineField="<%= true %>" inlineLabel="<%= true %>" label="" maxlength="2" name="monthlyDay0" size="2" type="text" value="<%= monthlyDay0 %>" />

						<aui:input inlineField="<%= true %>" inlineLabel="<%= true %>" label="of-every" maxlength="2" name="monthlyInterval0" size="2" type="text" value="<%= monthlyInterval0 %>" />

						<span class="after-field-text"><liferay-ui:message key="month-s" /></span>

						<aui:input checked="<%= (monthlyType == 1) %>" cssClass="input-container" inlineField="<%= true %>" label="the" name="monthlyType" type="radio" value="1" />

						<aui:select cssClass="input-container"  inlineField="<%= true %>" inlineLabel="<%= true %>" label="" name="monthlyPos">
							<aui:option label="first" selected="<%= monthlyPos == 1 %>" value="1" />
							<aui:option label="second" selected="<%= monthlyPos == 2 %>" value="2" />
							<aui:option label="third" selected="<%= monthlyPos == 3 %>" value="3" />
							<aui:option label="fourth" selected="<%= monthlyPos == 4 %>" value="4" />
							<aui:option label="last" selected="<%= monthlyPos == -1 %>" value="-1" />
						</aui:select>

						<aui:select cssClass="input-container" inlineField="<%= true %>" label="" name="monthlyDay1">
							<aui:option label="<%= days[0] %>" selected="<%= monthlyDay1 == Calendar.SUNDAY %>" value="<%= Calendar.SUNDAY %>" />
							<aui:option label="<%= days[1] %>" selected="<%= monthlyDay1 == Calendar.MONDAY %>" value="<%= Calendar.MONDAY %>" />
							<aui:option label="<%= days[2] %>" selected="<%= monthlyDay1 == Calendar.TUESDAY %>" value="<%= Calendar.TUESDAY %>" />
							<aui:option label="<%= days[3] %>" selected="<%= monthlyDay1 == Calendar.WEDNESDAY %>" value="<%= Calendar.WEDNESDAY %>" />
							<aui:option label="<%= days[4] %>" selected="<%= monthlyDay1 == Calendar.THURSDAY %>" value="<%= Calendar.THURSDAY %>" />
							<aui:option label="<%= days[5] %>" selected="<%= monthlyDay1 == Calendar.FRIDAY %>" value="<%= Calendar.FRIDAY %>" />
							<aui:option label="<%= days[6] %>" selected="<%= monthlyDay1 == Calendar.SATURDAY %>" value="<%= Calendar.SATURDAY %>" />
						</aui:select>

						<aui:input inlineField="<%= true %>" inlineLabel="<%= true %>" label="of-every" maxlength="2" name="monthlyInterval1" size="2" type="text" value="<%= monthlyInterval1 %>" />

						<span class="after-field-text"><liferay-ui:message key="month-s" /></span>
					</div>

					<div id="<portlet:namespace />yearlyTable" style="display: none;">
						<aui:input checked="<%= yearlyType == 0 %>" cssClass="input-container" inlineField="<%= true %>" label="every" name="yearlyType" type="radio" value="0" />

						<aui:select cssClass="input-container" inlineField="<%= true %>" inlineLabel="<%= true %>" label="" name="yearlyMonth0">

						<%
						for (int i = 0; i < 12; i++) {
						%>

								<aui:option label="<%= months[i] %>" selected="<%= monthIds[i] == yearlyMonth0 %>" value="<%= monthIds[i] %>" />

						<%
						}
						%>

						</aui:select>

						<aui:input inlineField="<%= true %>" label="" maxlength="2" name="yearlyDay0" size="2" type="text" value="<%= yearlyDay0 %>" />

						<aui:input inlineField="<%= true %>" inlineLabel="<%= true %>" label="of-every" maxlength="2" name="yearlyInterval0" size="2" type="text" value="<%= yearlyInterval0 %>" />

						<span class="after-field-text"><liferay-ui:message key="year-s" /></span>

						<aui:input checked="<%= (yearlyType == 1) %>" cssClass="input-container" inlineField="<%= true %>" label="the" name="yearlyType" type="radio" value="1" />

						<aui:select cssClass="input-container" inlineField="<%= true %>" label="" name="yearlyPos">
							<aui:option label="first" selected="<%= yearlyPos == 1 %>" value="1" />
							<aui:option label="second" selected="<%= yearlyPos == 2 %>" value="2" />
							<aui:option label="third" selected="<%= yearlyPos == 3 %>" value="3" />
							<aui:option label="fourth" selected="<%= yearlyPos == 4 %>" value="4" />
							<aui:option label="last" selected="<%= yearlyPos == -1 %>" value="-1" />
						</aui:select>

						<aui:select cssClass="input-container" inlineField="<%= true %>" label="" name="yearlyDay1">
							<aui:option label="weekday" selected="<%= yearlyDay1 == Calendar.MONDAY %>" value="<%= Calendar.MONDAY %>" />
							<aui:option label="weekend-day" selected="<%= yearlyDay1 == Calendar.SATURDAY %>" value="<%= Calendar.SATURDAY %>" />
							<aui:option label="<%= days[0] %>" selected="<%= yearlyDay1 == Calendar.SUNDAY %>" value="<%= Calendar.SUNDAY %>" />
							<aui:option label="<%= days[1] %>" selected="<%= yearlyDay1 == Calendar.MONDAY %>" value="<%= Calendar.MONDAY %>" />
							<aui:option label="<%= days[2] %>" selected="<%= yearlyDay1 == Calendar.TUESDAY %>" value="<%= Calendar.TUESDAY %>" />
							<aui:option label="<%= days[3] %>" selected="<%= yearlyDay1 == Calendar.WEDNESDAY %>" value="<%= Calendar.WEDNESDAY %>" />
							<aui:option label="<%= days[4] %>" selected="<%= yearlyDay1 == Calendar.THURSDAY %>" value="<%= Calendar.THURSDAY %>" />
							<aui:option label="<%= days[5] %>" selected="<%= yearlyDay1 == Calendar.FRIDAY %>" value="<%= Calendar.FRIDAY %>" />
							<aui:option label="<%= days[6] %>" selected="<%= yearlyDay1 == Calendar.SATURDAY %>" value="<%= Calendar.SATURDAY %>" />
						</aui:select>

						<aui:select cssClass="input-container" inlineField="<%= true %>" inlineLabel="<%= true %>" label="of" name="yearlyMonth1">

							<%
							for (int i = 0; i < 12; i++) {
							%>

								<aui:option label="<%= months[i] %>" selected="<%= (monthIds[i] == yearlyMonth1) %>" value="<%= monthIds[i] %>" />

							<%
							}
							%>

						</aui:select>

						<aui:input inlineField="<%= true %>" inlineLabel="<%= true %>" label="of-every" maxlength="2" name="yearlyInterval1" size="2" type="text" value="<%= yearlyInterval1 %>" />

						<span class="after-field-text"><liferay-ui:message key="year-s" /></span>
					</div>
				</aui:column>

				<aui:field-wrapper cssClass="end-date-field" label="end-date" name="endDateType">
					<aui:input checked="<%= endDateType == 0 %>" cssClass="input-container" label="no-end-date" name="endDateType" type="radio" value="0" />
					<%--<input <%= (endDateType == 1) ? "checked" : "" %> name="<portlet:namespace />endDateType" type="radio" value="1"> End after <input maxlength="3" name="<portlet:namespace />endDateOccurrence" size="3" type="text" value="<%= endDateOccurrence %>" /> occurrence(s)<br />--%>

					<aui:input checked="<%= endDateType == 2 %>" cssClass="input-container" inlineField="<%= true %>" label="end-by" name="endDateType" type="radio" value="2" />

					<aui:input label="" name="endDate" value="<%= endDate %>" />
				</aui:field-wrapper>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel id="reminders" title='<%= LanguageUtil.get(pageContext, "reminders") %>' collapsible="<%= true %>" persistState="<%= true %>" extended="<%= true %>">
			<aui:fieldset>
				<aui:select inlineField="<%= true %>" inlineLabel="<%= true %>" label="remind-me" name="firstReminder">

					<%
					for (int i = 0; i < CalEventImpl.REMINDERS.length; i++) {
					%>

						<aui:option selected="<%= (firstReminder == CalEventImpl.REMINDERS[i])  %>" value="<%= CalEventImpl.REMINDERS[i] %>"><%= LanguageUtil.getTimeDescription(pageContext, CalEventImpl.REMINDERS[i]) %></aui:option>

					<%
					}
					%>

				</aui:select>

				<aui:select inlineField="<%= true %>" inlineLabel="<%= true %>" label="before-and-again" name="secondReminder">

					<%
					for (int i = 0; i < CalEventImpl.REMINDERS.length; i++) {
					%>

						<aui:option selected="<%= (secondReminder == CalEventImpl.REMINDERS[i]) %>" value="<%= CalEventImpl.REMINDERS[i] %>"><%= LanguageUtil.getTimeDescription(pageContext, CalEventImpl.REMINDERS[i]) %></aui:option>

					<%
					}
					%>

				</aui:select>

				<span class="after-field-text"><liferay-ui:message key="before-the-event-by" /></span>

				<aui:field-wrapper cssClass="reminders" label="">
					<aui:input checked="<%= remindBy == CalEventImpl.REMIND_BY_NONE %>" label="do-not-send-a-reminder" name="remindBy" type="radio" value="<%= CalEventImpl.REMIND_BY_NONE %>" />
					<aui:input checked="<%= remindBy == CalEventImpl.REMIND_BY_EMAIL %>" label="<%= LanguageUtil.get(pageContext, "email-address") + " (" + user.getEmailAddress() + ")" %>" name="remindBy" type="radio" value="<%= CalEventImpl.REMIND_BY_EMAIL %>" />
					<aui:input checked="<%= remindBy == CalEventImpl.REMIND_BY_SMS %>" label="<%= LanguageUtil.get(pageContext, "sms") + (Validator.isNotNull(contact.getSmsSn()) ? " (" + contact.getSmsSn() + ")" : "") %>" name="remindBy" type="radio" value="<%= CalEventImpl.REMIND_BY_SMS %>" />
					<aui:input checked="<%= remindBy == CalEventImpl.REMIND_BY_AIM %>" label="<%=  LanguageUtil.get(pageContext, "aim") + (Validator.isNotNull(contact.getAimSn()) ? " (" + contact.getAimSn() + ")" : "") %>" name="remindBy" type="radio" value="<%= CalEventImpl.REMIND_BY_AIM %>" />
					<aui:input checked="<%= remindBy == CalEventImpl.REMIND_BY_ICQ %>" label="<%=  LanguageUtil.get(pageContext, "icq") + (Validator.isNotNull(contact.getIcqSn()) ? " (" + contact.getIcqSn() + ")" : "") %>" name="remindBy" type="radio" value="<%= CalEventImpl.REMIND_BY_ICQ %>" />
					<aui:input checked="<%= remindBy == CalEventImpl.REMIND_BY_MSN %>" label="<%=  LanguageUtil.get(pageContext, "msn") + (Validator.isNotNull(contact.getMsnSn()) ? " (" + contact.getMsnSn() + ")" : "") %>" name="remindBy" type="radio" value="<%= CalEventImpl.REMIND_BY_MSN %>" />
					<aui:input checked="<%= remindBy == CalEventImpl.REMIND_BY_YM %>" label="<%=  LanguageUtil.get(pageContext, "ym") + (Validator.isNotNull(contact.getYmSn()) ? " (" + contact.getYmSn() + ")" : "") %>" name="remindBy" type="radio" value="<%= CalEventImpl.REMIND_BY_YM %>" />
				</aui:field-wrapper>
			</aui:fieldset>
		</liferay-ui:panel>
	</liferay-ui:panel-container>

	<aui:button-row>
		<aui:button type="submit" value="save" />

		<aui:button value="cancel" onClick="<%= redirect %>" />
	</aui:button-row>
</aui:form>

<script type="text/javascript">
	<portlet:namespace />init();

	document.<portlet:namespace />fm.<portlet:namespace />endDateHour.disabled = true;
	document.<portlet:namespace />fm.<portlet:namespace />endDateMinute.disabled = true;
	document.<portlet:namespace />fm.<portlet:namespace />endDateAmPm.disabled = true;
</script>

<%
if (event != null) {
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setWindowState(WindowState.MAXIMIZED);

	portletURL.setParameter("struts_action", "/calendar/view_event");
	portletURL.setParameter("redirect", currentURL);
	portletURL.setParameter("eventId", String.valueOf(event.getEventId()));

	PortalUtil.addPortletBreadcrumbEntry(request, event.getTitle(), portletURL.toString());
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-event"), currentURL);
}
%>

<%!
private boolean _getWeeklyDayPos(HttpServletRequest req, int day, CalEvent event, Recurrence recurrence) {
	boolean weeklyPos = ParamUtil.getBoolean(req, "weeklyDayPos" + day);

	String weeklyPosParam = ParamUtil.getString(req, "weeklyDayPos" + day);

	if (Validator.isNull(weeklyPosParam) && (event != null)) {
		if ((event.getRepeating()) && (recurrence != null)) {
			DayAndPosition[] dayPositions = recurrence.getByDay();

			if (dayPositions != null) {
				for (int i = 0; i < dayPositions.length; i++) {
					if (dayPositions[i].getDayOfWeek() == day) {
						return true;
					}
				}
			}
		}
	}

	return weeklyPos;
}
%>