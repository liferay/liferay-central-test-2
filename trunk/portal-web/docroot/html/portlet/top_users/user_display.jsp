<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
--%>

<%@ include file="/html/portlet/top_users/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

SocialEquityUser socialEquityUser = (SocialEquityUser)row.getObject();
%>

<liferay-ui:user-display
	userId="<%= socialEquityUser.getUserId() %>"
	userName=""
>
	<c:if test="<%= userDisplay != null %>">
		<div class="user-rank">
			<span><liferay-ui:message key="rank" />:</span> <%= socialEquityUser.getRank() %>
		</div>

		<div class="user-contribution-score">
			<span><liferay-ui:message key="contribution-score" />:</span> <%= Math.round(socialEquityUser.getContributionEquity()) %>
		</div>

		<div class="user-participation-score">
			<span><liferay-ui:message key="participation-score" />:</span> <%= Math.round(socialEquityUser.getParticipationEquity()) %>
		</div>
	</c:if>
</liferay-ui:user-display>