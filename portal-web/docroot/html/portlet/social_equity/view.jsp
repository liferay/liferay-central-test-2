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

<%@ include file="/html/portlet/social_equity/init.jsp" %>

<%
String keywords = ParamUtil.getString(request, "keywords");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/social_equity/view");
%>

<portlet:actionURL var="viewActionUrl">
	<portlet:param name="struts_action" value="/social_equity/view" />
</portlet:actionURL>

<aui:form action="<%= viewActionUrl %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" value="<%= currentURL %>" type="hidden" />

	<%
	Map<String, List<SocialEquityActionMapping>> equityActionMapping = (Map<String, List<SocialEquityActionMapping>>)request.getAttribute("equity-action-mapping");

	String[] equityModels = PortalUtil.getSocialEquityModels();

	for (String model : equityActionMapping.keySet()) {
		List<SocialEquityActionMapping> mappings = equityActionMapping.get(model);
	%>

		<h2><%= LanguageUtil.get(pageContext, "model.resource." + model) %></h2>

		<liferay-ui:search-container
			searchContainer='<%= new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, null, LanguageUtil.get(pageContext, "no-mappings-were-found")) %>'
			headerNames="name"
		>

			<liferay-ui:search-container-results
				results="<%= mappings %>"
				total="<%= mappings.size() %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portlet.social.model.SocialEquityActionMapping"
				modelVar="mapping"
			>

				<liferay-ui:search-container-column-text
					name="name"
					value='<%= LanguageUtil.get(pageContext, "action." + mapping.getActionKey()) %>'
				/>

				<liferay-ui:search-container-column-text name="IQ value / expiry">
					<input name='<%= portletDisplay.getNamespace() + model + "." + mapping.getActionKey() + ".iqvalue" %>' value="<%= String.valueOf(mapping.getInformationValue()) %>" size="4" /> /
					<input name='<%= portletDisplay.getNamespace() + model + "." + mapping.getActionKey() + ".iqexpiry" %>' value="<%= String.valueOf(mapping.getInformationLifespan()) %>" size="4" />
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text name="PQ value / expiry">
					<input name='<%= portletDisplay.getNamespace() + model + "." + mapping.getActionKey() + ".pqvalue" %>' value="<%= String.valueOf(mapping. getParticipationValue()) %>" size="4" /> /
					<input name='<%= portletDisplay.getNamespace() + model + "." + mapping.getActionKey() + ".pqexpiry" %>' value="<%= String.valueOf(mapping.getParticipationLifespan()) %>" size="4" />
				</liferay-ui:search-container-column-text>

			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	<% } %>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>

</aui:form>