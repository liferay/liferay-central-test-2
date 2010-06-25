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

<%@ include file="/html/portlet/topusers/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

SocialEquityUser socialEquityUser = (SocialEquityUser)row.getObject();

//User refUser = UserLocalServiceUtil.getUser(socialEquityUser.getUserId());
%>

<liferay-ui:user-display
	userId="<%= socialEquityUser.getUserId() %>"
	userName=""
>
<c:if test="<%= userDisplay != null %>">
	<br />
	<liferay-ui:message key="social-rank" />:&nbsp;<%= socialEquityUser.getRank() %>
	<br />
	<liferay-ui:message key="social-contribution-score" />:&nbsp;<%= (int)socialEquityUser.getContributionEquity() %>
	<br />
	<liferay-ui:message key="social-participation-score" />:&nbsp;<%= (int)socialEquityUser.getParticipationEquity() %>

</c:if>
</liferay-ui:user-display>