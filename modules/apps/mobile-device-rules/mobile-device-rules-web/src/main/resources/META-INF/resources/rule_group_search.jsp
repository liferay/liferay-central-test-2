<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

<%@ include file="/init.jsp" %>

<%
RuleGroupSearch searchContainer = (RuleGroupSearch)request.getAttribute("liferay-ui:search:searchContainer");

RuleGroupDisplayTerms displayTerms = (RuleGroupDisplayTerms)searchContainer.getDisplayTerms();
%>

<liferay-ui:search-toggle
	buttonLabel="search"
	displayTerms="<%= displayTerms %>"
	id="toggle_id_mobile_device_rules_rule_group_search"
>
	<aui:fieldset>
		<aui:input label="name" name="<%= RuleGroupDisplayTerms.NAME %>" size="20" type="text" value="<%= displayTerms.getName() %>" />
	</aui:fieldset>
</liferay-ui:search-toggle>