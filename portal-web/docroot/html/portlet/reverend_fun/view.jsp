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

<%@ include file="/html/portlet/reverend_fun/init.jsp" %>

<c:choose>
	<c:when test="<%= windowState.equals(WindowState.NORMAL) %>">
		<table class="lfr-table">
		<tr>
			<td>

				<%
				String currentDate = ReverendFunUtil.getCurrentDate();

				Calendar currentCal = CalendarFactoryUtil.getCalendar();

				currentCal.setTime(dateFormat.parse(currentDate));
				%>

				<a href="<portlet:renderURL />"><img border="0" src="http://rev-fun.gospelcom.net/<%= currentCal.get(Calendar.YEAR) %>/<%= decimalFormat.format(currentCal.get(Calendar.MONTH) + 1) %>/<%= currentDate %>_sm.gif" width="72" /></a>
			</td>
			<td>
				Reverend Fun, daily humor for daily people!
			</td>
		</tr>
		</table>
	</c:when>
	<c:otherwise>

		<%
		String date = ParamUtil.getString(request, "date");

		if (!ReverendFunUtil.hasDate(date)) {
			date = ReverendFunUtil.getCurrentDate();
		}

		String previousDate = ReverendFunUtil.getPreviousDate(date);
		String nextDate = ReverendFunUtil.getNextDate(date);
		%>

		<table class="lfr-table">
		<tr>
			<td>
				<strong><%= DateFormat.getDateInstance(DateFormat.LONG, locale).format(dateFormat.parse(date)) %></strong>
			</td>
			<td align="right">
				<c:if test="<%= previousDate != null %>">
					<a href="<portlet:renderURL><portlet:param name="date" value="<%= previousDate %>" /></portlet:renderURL>"><liferay-ui:message key="previous" /></a>
				</c:if>

				<c:if test="<%= previousDate != null && nextDate != null %>">
					-
				</c:if>

				<c:if test="<%= nextDate != null %>">
					<a href="<portlet:renderURL><portlet:param name="date" value="<%= nextDate %>" /></portlet:renderURL>"><liferay-ui:message key="next" /></a>
				</c:if>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<br />

				<img alt="<liferay-ui:message key="cartoon" />" src="http://rev-fun.gospelcom.net/add_toon_info.php?date=<%= date %>" />
			</td>
		</tr>
		</table>
	</c:otherwise>
</c:choose>