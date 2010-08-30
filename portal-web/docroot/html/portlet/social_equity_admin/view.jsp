<%@ page import="com.liferay.portlet.social.service.SocialEquityGroupSettingLocalServiceUtil" %>
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

<%@ include file="/html/portlet/social_equity_admin/init.jsp" %>

<%
Map<String, List<SocialEquityActionMapping>> equityActionMappingsMap = (Map<String, List<SocialEquityActionMapping>>)request.getAttribute(WebKeys.SOCIAL_EQUITY_ACTION_MAPPINGS_MAP);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/social_equity_admin/view");

String updateRanksOnClick = renderResponse.getNamespace() + "saveServer('updateRanks');";
String toggleInformationEquityOnClick = renderResponse.getNamespace() + "saveServer('toggleInformationEnabled');";
String toggleParticipationEquityOnClick = renderResponse.getNamespace() + "saveServer('toggleParticipationEnabled');";

boolean informationEquityEnabled = SocialEquityGroupSettingLocalServiceUtil.isSocialEquityEnabled(themeDisplay.getScopeGroupId(), Group.class.getName(), SocialEquitySettingConstants.TYPE_INFORMATION);
boolean participationEquityEnabled = SocialEquityGroupSettingLocalServiceUtil.isSocialEquityEnabled(themeDisplay.getScopeGroupId(), Group.class.getName(), SocialEquitySettingConstants.TYPE_PARTICIPATION);

String toggleInformationEquityLabel = "enable-information-equity";

if (informationEquityEnabled) {
	toggleInformationEquityLabel = "disable-information-equity";
}

String toggleParticipationEquityLabel = "enable-participation-equity";

if (participationEquityEnabled) {
	toggleParticipationEquityLabel = "disable-participation-equity";
}

%>

<portlet:actionURL var="editEquityActionMappingsURL">
	<portlet:param name="struts_action" value="/social_equity_admin/view" />
</portlet:actionURL>

<aui:form action="<%= editEquityActionMappingsURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" value="<%= currentURL %>" type="hidden" />

	<aui:button-row>
		<aui:button type="button" onClick="<%= toggleInformationEquityOnClick %>" value="<%=toggleInformationEquityLabel %>" />
		<aui:button type="button" onClick="<%= toggleParticipationEquityOnClick %>" value="<%=toggleParticipationEquityLabel %>" />
		<aui:button type="button" onClick="<%= updateRanksOnClick %>" value="update-ranking" />
	</aui:button-row>

	<%
	for (String className : equityActionMappingsMap.keySet()) {
		List<SocialEquityActionMapping> equityActionMappings = equityActionMappingsMap.get(className);
	%>

		<h3><%= ResourceActionsUtil.getModelResource(locale, className) %></h3>

		<table class="lfr-table">
		<tr>
			<td>
				<liferay-ui:message key="features" />:
			</td>
			<td>
				<aui:input disabled="<%=!informationEquityEnabled %>" inlineLabel="left" label="information-equity" name='<%=className + ".informationEquityEnabled" %>' type="checkbox" value="<%= SocialEquityGroupSettingLocalServiceUtil.isSocialEquityEnabled(themeDisplay.getScopeGroupId(), className, SocialEquitySettingConstants.TYPE_INFORMATION) %>" />
			</td>
			<td>
				<aui:input disabled="<%=!participationEquityEnabled %>" inlineLabel="left" label="participation-equity" name='<%=className + ".participationEquityEnabled" %>' type="checkbox" value="<%= SocialEquityGroupSettingLocalServiceUtil.isSocialEquityEnabled(themeDisplay.getScopeGroupId(), className, SocialEquitySettingConstants.TYPE_PARTICIPATION) %>" />
			</td>
		</tr>
		</table>

		<br />

		<liferay-ui:search-container
			headerNames="name"
			iteratorURL="<%= portletURL %>"
		>
			<liferay-ui:search-container-results
				results="<%= equityActionMappings %>"
				total="<%= equityActionMappings.size() %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portlet.social.model.SocialEquityActionMapping"
				modelVar="equityActionMapping"
			>
				<liferay-ui:search-container-column-text
					name="name"
					value="<%= ResourceActionsUtil.getAction(locale, equityActionMapping.getActionId()) %>"
				/>

				<liferay-ui:search-container-column-text name="information-value">
					<aui:input label="" name='<%= className + "." + equityActionMapping.getActionId() + ".informationValue" %>' size="4" type="text" value="<%= equityActionMapping.getInformationValue() %>" />
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text name="information-lifespan">
					<aui:input label="" name='<%= className + "." + equityActionMapping.getActionId() + ".informationLifespan" %>' size="4" type="text" value="<%= equityActionMapping.getInformationLifespan() %>" />
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text name="daily-limit">
					<aui:input label="" name='<%= className + "." + equityActionMapping.getActionId() + ".informationDailyLimit" %>' size="4" type="text" value="<%= equityActionMapping.getInformationDailyLimit() %>" />
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text name="participation-value">
					<aui:input label="" name='<%= className + "." + equityActionMapping.getActionId() + ".participationValue" %>' size="4" type="text" value="<%= equityActionMapping.getParticipationValue() %>" />
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text name="participation-lifespan">
					<aui:input label="" name='<%= className + "." + equityActionMapping.getActionId() + ".participationLifespan" %>' size="4" type="text" value="<%= equityActionMapping.getParticipationLifespan() %>" />
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text name="daily-limit">
					<aui:input label="" name='<%= className + "." + equityActionMapping.getActionId() + ".participationDailyLimit" %>' size="4" type="text" value="<%= equityActionMapping.getParticipationDailyLimit() %>" />
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>

	<%
	}
	%>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveServer(cmd) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = cmd;
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>