<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
%>

<%@ include file="/html/portlet/calendar/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

CalEvent event = (CalEvent)request.getAttribute(WebKeys.CALENDAR_EVENT);

event = event.toEscapedModel();

Recurrence recurrence = null;

int recurrenceType = ParamUtil.getInteger(request, "recurrenceType", Recurrence.NO_RECURRENCE);
if (event.getRepeating()) {
	recurrence = event.getRecurrenceObj();
	recurrenceType = recurrence.getFrequency();
}

int dailyType = ParamUtil.getInteger(request, "dailyType");
if ((event.getRepeating()) && (recurrence != null)) {
	if (recurrence.getByDay() != null) {
		dailyType = 1;
	}
}

int dailyInterval = ParamUtil.getInteger(request, "dailyInterval", 1);
if ((event.getRepeating()) && (recurrence != null)) {
	dailyInterval = recurrence.getInterval();
}

int weeklyInterval = ParamUtil.getInteger(request, "weeklyInterval", 1);
if ((event.getRepeating()) && (recurrence != null)) {
	weeklyInterval = recurrence.getInterval();
}

boolean weeklyPosSu = _getWeeklyDayPos(request, Calendar.SUNDAY, event, recurrence);
boolean weeklyPosMo = _getWeeklyDayPos(request, Calendar.MONDAY, event, recurrence);
boolean weeklyPosTu = _getWeeklyDayPos(request, Calendar.TUESDAY, event, recurrence);
boolean weeklyPosWe = _getWeeklyDayPos(request, Calendar.WEDNESDAY, event, recurrence);
boolean weeklyPosTh = _getWeeklyDayPos(request, Calendar.THURSDAY, event, recurrence);
boolean weeklyPosFr = _getWeeklyDayPos(request, Calendar.FRIDAY, event, recurrence);
boolean weeklyPosSa = _getWeeklyDayPos(request, Calendar.SATURDAY, event, recurrence);

int monthlyType = ParamUtil.getInteger(request, "monthlyType");
if ((event.getRepeating()) && (recurrence != null)) {
	if (recurrence.getByMonthDay() == null) {
		monthlyType = 1;
	}
}

int monthlyDay0 = ParamUtil.getInteger(request, "monthlyDay0", 15);
if ((event.getRepeating()) && (recurrence != null)) {
	if (recurrence.getByMonthDay() != null) {
		monthlyDay0 = recurrence.getByMonthDay()[0];
	}
}

int monthlyInterval0 = ParamUtil.getInteger(request, "monthlyInterval0", 1);
if ((event.getRepeating()) && (recurrence != null)) {
	monthlyInterval0 = recurrence.getInterval();
}

int monthlyPos = ParamUtil.getInteger(request, "monthlyPos", 1);
if ((event.getRepeating()) && (recurrence != null)) {
	if (recurrence.getByMonth() != null) {
		monthlyPos = recurrence.getByMonth()[0];
	}
	else if (recurrence.getByDay() != null) {
		monthlyPos = recurrence.getByDay()[0].getDayPosition();
	}
}

int monthlyDay1 = ParamUtil.getInteger(request, "monthlyDay1", Calendar.SUNDAY);
if ((event.getRepeating()) && (recurrence != null)) {
	if (recurrence.getByMonth() != null) {
		monthlyDay1 = -1;
	}
	else if (recurrence.getByDay() != null) {
		monthlyDay1 = recurrence.getByDay()[0].getDayOfWeek();
	}
}

int monthlyInterval1 = ParamUtil.getInteger(request, "monthlyInterval1", 1);
if ((event.getRepeating()) && (recurrence != null)) {
	monthlyInterval1 = recurrence.getInterval();
}

int yearlyType = ParamUtil.getInteger(request, "yearlyType");
if ((event.getRepeating()) && (recurrence != null)) {
	if (recurrence.getByMonth() != null) {
		yearlyType = 1;
	}
}

int yearlyMonth0 = ParamUtil.getInteger(request, "yearlyMonth0", Calendar.JANUARY);
if ((event.getRepeating()) && (recurrence != null)) {
	if (recurrence.getByMonth() == null) {
		yearlyMonth0 = recurrence.getDtStart().get(Calendar.MONTH);
	}
}

int yearlyDay0 = ParamUtil.getInteger(request, "yearlyDay0", 15);
if ((event.getRepeating()) && (recurrence != null)) {
	if (recurrence.getByMonth() == null) {
		yearlyDay0 = recurrence.getDtStart().get(Calendar.DATE);
	}
}

int yearlyInterval0 = ParamUtil.getInteger(request, "yearlyInterval0", 1);
if ((event.getRepeating()) && (recurrence != null)) {
	yearlyInterval0 = recurrence.getInterval();
}

int yearlyPos = ParamUtil.getInteger(request, "yearlyPos", 1);
if ((event.getRepeating()) && (recurrence != null)) {
	if (recurrence.getByMonth() != null) {
		yearlyPos = recurrence.getByMonth()[0];
	}
	else if (recurrence.getByDay() != null) {
		yearlyPos = recurrence.getByDay()[0].getDayPosition();
	}
}

int yearlyDay1 = ParamUtil.getInteger(request, "yearlyDay1", Calendar.SUNDAY);
if ((event.getRepeating()) && (recurrence != null)) {
	if (recurrence.getByMonth() != null) {
		yearlyDay1 = -1;
	}
	else if (recurrence.getByDay() != null) {
		yearlyDay1 = recurrence.getByDay()[0].getDayOfWeek();
	}
}

int yearlyMonth1 = ParamUtil.getInteger(request, "yearlyMonth1", Calendar.JANUARY);
if ((event.getRepeating()) && (recurrence != null)) {
	if (recurrence.getByMonth() != null) {
		yearlyMonth1 = recurrence.getByMonth()[0];
	}
}

int yearlyInterval1 = ParamUtil.getInteger(request, "yearlyInterval1", 1);
if ((event.getRepeating()) && (recurrence != null)) {
	yearlyInterval1 = recurrence.getInterval();
}

int endDateType = ParamUtil.getInteger(request, "endDateType");
if ((event.getRepeating()) && (recurrence != null)) {
	if (recurrence.getUntil() != null) {
		endDateType = 2;
	}
	else if (recurrence.getOccurrence() > 0) {
		endDateType = 1;
	}
}

request.setAttribute("view_event.jsp-event", event);
%>

<liferay-ui:tabs
	names="event"
	backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
/>

<aui:layout cssClass="event">
	<h3 class="event-title"><%= event.getTitle() %></h3>

	<aui:column columnWidth="<%= 75 %>" cssClass="folder-column folder-column-first" first="<%= true %>">
		<dl class="property-list">
			<dt>
				<liferay-ui:icon image="../common/calendar" />

				<liferay-ui:message key="start-date" />:
			</dt>
			<dd>
				<c:choose>
					<c:when test="<%= event.isTimeZoneSensitive() %>">
						<%= dateFormatDate.format(Time.getDate(event.getStartDate(), timeZone)) %>
					</c:when>
					<c:otherwise>
						<%= dateFormatDate.format(event.getStartDate()) %>
					</c:otherwise>
				</c:choose>
			</dd>
			<dt>
				<c:choose>
					<c:when test="<%= (endDateType == 0) || (endDateType == 2) %>">
						<liferay-ui:icon image="../common/calendar" />

						<liferay-ui:message key="end-date" />:
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="ocurrence-s" />:
					</c:otherwise>
				</c:choose>
			</dt>
			<dd>
				<c:if test="<%= (endDateType == 0) %>">
					<liferay-ui:message key="none" />
				</c:if>

				<c:if test="<%= (endDateType == 1) %>">
					<%= recurrence.getOccurrence() %>
				</c:if>

				<c:if test="<%= (endDateType == 2) %>">
					<%= event.isTimeZoneSensitive() ? dateFormatDate.format(Time.getDate(event.getEndDate(), timeZone)) : dateFormatDate.format(event.getEndDate()) %>
				</c:if>
			</dd>
			<dt>
				<liferay-ui:icon image="../common/time" />

				<liferay-ui:message key="duration" />:
			</dt>
			<dd>

				<%
				boolean allDay = CalUtil.isAllDay(event, timeZone, locale);
				%>

				<c:choose>
					<c:when test="<%= allDay %>">
						<abbr class="duration" title="P1D">
							<liferay-ui:message key="all-day" />:
						</abbr>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="<%= event.isTimeZoneSensitive() %>">
								<abbr class="dtstart" title="<%= dateFormatISO8601.format(Time.getDate(event.getStartDate(), timeZone)) %>">
									<%= dateFormatTime.format(Time.getDate(event.getStartDate(), timeZone)) %>
								</abbr>
							</c:when>
							<c:otherwise>
								<abbr class="dtstart" title="<%= dateFormatISO8601.format(event.getStartDate()) %>">
									<%= dateFormatTime.format(event.getStartDate()) %>
								</abbr>
							</c:otherwise>
						</c:choose>
						&#150;
						<c:choose>
							<c:when test="<%= event.isTimeZoneSensitive() %>">
								<%= dateFormatTime.format(Time.getDate(CalUtil.getEndTime(event), timeZone)) %>
							</c:when>
							<c:otherwise>
								<%= dateFormatTime.format(CalUtil.getEndTime(event)) %>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>

				<c:if test="<%= allDay %>">
					<liferay-ui:message key="all-day" />
				</c:if>

				<c:if test="<%= event.isTimeZoneSensitive() %>">
					(<liferay-ui:message key="time-zone-sensitive" />)
				</c:if>
			</dd>
			<dt>
				<liferay-ui:icon image="../common/attributes" />

				<liferay-ui:message key="type" />:
			</dt>
			<dd>
				<span class="categories"><%= LanguageUtil.get(pageContext, event.getType()) %></span>
			</dd>

			<c:if test="<%= (recurrenceType == Recurrence.DAILY) %>">
				<dt>
					<liferay-ui:icon image="../common/undo" />

					<liferay-ui:message key="repeat-daily" />:
				</dt>
				<dd>
					<c:if test="<%= (dailyType == 0) %>">
						<%= dailyInterval %> <liferay-ui:message key="day-s" />
					</c:if>

					<c:if test="<%= (dailyType == 1) %>">
						<liferay-ui:message key="every-weekday" />
					</c:if>
				</dd>
			</c:if>

			<c:if test="<%= (recurrenceType == Recurrence.WEEKLY) %>">
				<dt>
					<liferay-ui:icon image="../common/undo" />

					<liferay-ui:message key="repeat-weekly" />:
				</dt>
				<dd>
					<abbr class="rrule" title="FREQ=WEEKLY">
						<liferay-ui:message key="recur-every" /> <%= dailyInterval %> <liferay-ui:message key="weeks-on" />

						<%= weeklyPosSu ? (days[0] + ",") : "" %>
						<%= weeklyPosMo ? (days[1] + ",") : "" %>
						<%= weeklyPosTu ? (days[2] + ",") : "" %>
						<%= weeklyPosWe ? (days[3] + ",") : "" %>
						<%= weeklyPosTh ? (days[4] + ",") : "" %>
						<%= weeklyPosFr ? (days[5] + ",") : "" %>
						<%= weeklyPosSa ? days[6] : "" %>
					</abbr>
				</dd>
			</c:if>

			<c:if test="<%= (recurrenceType == Recurrence.MONTHLY) %>">
				<dt>
					<liferay-ui:icon image="../common/undo" />

					<liferay-ui:message key="repeat-monthly" />:
				</dt>
				<dd>
					<c:if test="<%= (monthlyType == 0) %>">
						<liferay-ui:message key="day" /> <%= monthlyDay0 %> <liferay-ui:message key="of-every" /> <%= monthlyInterval0 %> <liferay-ui:message key="month-s" />
					</c:if>

					<c:if test="<%= (monthlyType == 1) %>">
						<liferay-ui:message key="the" />

						<%= (monthlyPos == 1) ? LanguageUtil.get(pageContext, "first") : "" %>
						<%= (monthlyPos == 2) ? LanguageUtil.get(pageContext, "second") : "" %>
						<%= (monthlyPos == 3) ? LanguageUtil.get(pageContext, "third") : "" %>
						<%= (monthlyPos == 4) ? LanguageUtil.get(pageContext, "fourth") : "" %>
						<%= (monthlyPos == -1) ? LanguageUtil.get(pageContext, "last") : "" %>

						<%= (monthlyDay1 == Calendar.MONDAY) ? LanguageUtil.get(pageContext, "weekday") : "" %>
						<%= (monthlyDay1 == Calendar.SATURDAY) ? LanguageUtil.get(pageContext, "weekend-day") : "" %>
						<%= (monthlyDay1 == Calendar.SUNDAY) ? days[0] : "" %>
						<%= (monthlyDay1 == Calendar.MONDAY) ? days[1] : "" %>
						<%= (monthlyDay1 == Calendar.TUESDAY) ? days[2] : "" %>
						<%= (monthlyDay1 == Calendar.WEDNESDAY) ? days[3] : "" %>
						<%= (monthlyDay1 == Calendar.THURSDAY) ? days[4] : "" %>
						<%= (monthlyDay1 == Calendar.FRIDAY) ? days[5] : "" %>
						<%= (monthlyDay1 == Calendar.SATURDAY) ? days[6] : "" %>

						<liferay-ui:message key="of-every" /> <%= monthlyInterval1 %> <liferay-ui:message key="month-s" />
					</c:if>
				</dd>
			</c:if>

			<c:if test="<%= (recurrenceType == Recurrence.YEARLY) %>">
				<dt>
					<liferay-ui:icon image="../common/undo" />

					<liferay-ui:message key="repeat-yearly" />:
				</dt>
				<dd>
					<abbr class="rrule" title="FREQ=YEARLY">
						<c:if test="<%= (yearlyType == 0) %>">
							<liferay-ui:message key="every" /> <%= months[yearlyMonth0] %> <liferay-ui:message key="of-every" /> <%= yearlyInterval0 %> <liferay-ui:message key="year-s" />
						</c:if>

						<c:if test="<%= (yearlyType == 1) %>">
							<liferay-ui:message key="the" />

							<%= (yearlyPos == 1) ? LanguageUtil.get(pageContext, "first") : "" %>
							<%= (yearlyPos == 2) ? LanguageUtil.get(pageContext, "second") : "" %>
							<%= (yearlyPos == 3) ? LanguageUtil.get(pageContext, "third") : "" %>
							<%= (yearlyPos == 4) ? LanguageUtil.get(pageContext, "fourth") : "" %>
							<%= (yearlyPos == -1) ? LanguageUtil.get(pageContext, "last") : "" %>

							<%= (yearlyDay1 == Calendar.MONDAY) ? LanguageUtil.get(pageContext, "weekday") : "" %>
							<%= (yearlyDay1 == Calendar.SATURDAY) ? LanguageUtil.get(pageContext, "weekend-day") : "" %>
							<%= (yearlyDay1 == Calendar.SUNDAY) ? days[0] : "" %>
							<%= (yearlyDay1 == Calendar.MONDAY) ? days[1] : "" %>
							<%= (yearlyDay1 == Calendar.TUESDAY) ? days[2] : "" %>
							<%= (yearlyDay1 == Calendar.WEDNESDAY) ? days[3] : "" %>
							<%= (yearlyDay1 == Calendar.THURSDAY) ? days[4] : "" %>
							<%= (yearlyDay1 == Calendar.FRIDAY) ? days[5] : "" %>
							<%= (yearlyDay1 == Calendar.SATURDAY) ? days[6] : "" %>

							<liferay-ui:message key="of" /> <%= months[yearlyMonth1] %> <liferay-ui:message key="of-every" /> <%= yearlyInterval1 %> <liferay-ui:message key="year-s" />
						</c:if>
					</abbr>
				</dd>
			</c:if>
		</dl>

		<liferay-ui:custom-attributes-available className="<%= CalEvent.class.getName() %>">
			<liferay-ui:custom-attribute-list
				className="<%= CalEvent.class.getName() %>"
				classPK="<%= (event != null) ? event.getEventId() : 0 %>"
				editable="<%= false %>"
				label="<%= true %>"
			/>
		</liferay-ui:custom-attributes-available>

		<p>
			<%= event.getDescription() %>
		</p>

		<span class="entry-categories">
			<liferay-ui:asset-categories-summary
				className="<%= CalEvent.class.getName() %>"
				classPK="<%= event.getEventId() %>"
			/>
		</span>

		<span class="entry-tags">
			<liferay-ui:asset-tags-summary
				className="<%= CalEvent.class.getName() %>"
				classPK="<%= event.getEventId() %>"
				message="tags"
			/>
		</span>
	</aui:column>

	<aui:column columnWidth="<%= 25 %>" cssClass="detail-column detail-column-last" last="<%= true %>">
		<div class="folder-icon">
			<liferay-ui:icon
				image="../file_system/large/calendar"
				cssClass="folder-avatar"
			/>

			<div class="event-name">
				<h4><%= event.getTitle() %></h4>
			</div>
		</div>

		<%
		request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
		%>

		<liferay-util:include page="/html/portlet/calendar/event_action.jsp" />
	</aui:column>
</aui:layout>

<c:if test="<%= enableComments %>">
	<br />

	<liferay-ui:tabs names="comments" />

	<br /><br />

	<portlet:actionURL var="discussionURL">
		<portlet:param name="struts_action" value="/calendar/edit_event_discussion" />
	</portlet:actionURL>

	<liferay-ui:discussion
		formName="fm2"
		formAction="<%= discussionURL %>"
		className="<%= CalEvent.class.getName() %>"
		classPK="<%= event.getEventId() %>"
		userId="<%= event.getUserId() %>"
		subject="<%= event.getTitle() %>"
		redirect="<%= currentURL %>"
		ratingsEnabled="true"
	/>
</c:if>

<%
PortalUtil.setPageSubtitle(event.getTitle(), request);
PortalUtil.setPageDescription(event.getDescription(), request);

PortalUtil.addPortletBreadcrumbEntry(request, event.getTitle(), currentURL);
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