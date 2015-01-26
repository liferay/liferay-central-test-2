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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
PortletPreferences companyPortletPreferences = PrefsPropsUtil.getPreferences(company.getCompanyId());

Group liveGroup = (Group)request.getAttribute("site.liveGroup");

UnicodeProperties groupTypeSettings = null;

if (liveGroup != null) {
	groupTypeSettings = liveGroup.getTypeSettingsProperties();
}
else {
	groupTypeSettings = new UnicodeProperties();
}
%>

<liferay-ui:error-marker key="errorSection" value="ratings" />

<h3><liferay-ui:message key="ratings" /></h3>

<div class="alert alert-info">
	<p><liferay-ui:message key="changing-ratings-type-could-lead-to-inaccurate-information" /></p>
</div>

<p><liferay-ui:message key="select-the-ratings-type-for-the-following-portlets" /></p>

<aui:fieldset>

	<%
	String[] portletIds = PortletRatingsDefinitionUtil.getPortletIds();

	for (String portletId : portletIds) {
		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);
	%>

		<p>
			<%= PortalUtil.getPortletTitle(portlet, application, locale) %>
		</p>

		<%
		String[] classNames = PortletRatingsDefinitionUtil.getClassNames(portletId);

		for (String className : classNames) {
			String propertyKey = className + StringPool.UNDERLINE + "RatingsType";

			PortletRatingsDefinition.RatingsType defaultRatingsType = PortletRatingsDefinitionUtil.getDefaultRatingsType(portletId, className);

			String companyRatingsTypeString = PrefsParamUtil.getString(companyPortletPreferences, request, propertyKey, defaultRatingsType.toString());

			String ratingsTypeString = PropertiesParamUtil.getString(groupTypeSettings, request, propertyKey, companyRatingsTypeString);
		%>

			<div class="ratings-type-select">
				<aui:select label='<%= (classNames.length > 1) ? ResourceActionsUtil.getModelResource(locale, className) : "" %>' name='<%= "TypeSettingsProperties--" + propertyKey + "--" %>'>

					<%
					for (PortletRatingsDefinition.RatingsType ratingsType : PortletRatingsDefinition.RatingsType.values()) {
					%>

						<aui:option label="<%= LanguageUtil.get(request, ratingsType.getValue()) %>" selected="<%= ratingsTypeString.equals(ratingsType.getValue()) %>" value="<%= ratingsType.getValue() %>" />

					<%
					}
					%>

				</aui:select>
			</div>

	<%
		}
	}
	%>

</aui:fieldset>