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

<%@ include file="/html/taglib/ui/form_navigator/init.jsp" %>

<aui:fieldset-group markupView="lexicon">
	<aui:fieldset>
		<c:if test="<%= deprecatedCategorySections.length > 0 %>">

			<%
			String section = deprecatedCategorySections[0];

			String sectionId = namespace + _getSectionId(section);
			String sectionJsp = jspPath + _getSectionJsp(section) + ".jsp";
			%>

			<!-- Begin fragment <%= sectionId %> -->

			<liferay-util:include page="<%= sectionJsp %>" portletId="<%= portletDisplay.getRootPortletId() %>" />

			<!-- End fragment <%= sectionId %> -->
		</c:if>

		<%
		List<FormNavigatorEntry<Object>> formNavigatorEntries = FormNavigatorEntryUtil.getFormNavigatorEntries(id, user, formModelBean);
		%>

		<c:if test="<%= ListUtil.isNotEmpty(formNavigatorEntries) %>">

			<%
			final FormNavigatorEntry formNavigatorEntry = formNavigatorEntries.get(0);

			String sectionId = namespace + _getSectionId(formNavigatorEntry.getKey());
			%>

			<!-- Begin fragment <%= sectionId %> -->

			<%
			PortalIncludeUtil.include(
				pageContext,
				new PortalIncludeUtil.HTMLRenderer() {

					public void renderHTML(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
						formNavigatorEntry.include(request, response);
					}

				});
			%>

			<!-- End fragment <%= sectionId %> -->
		</c:if>
	</aui:fieldset>

	<%
	for (int i = 1; i < deprecatedCategorySections.length; i++) {
		String section = deprecatedCategorySections[i];

		String sectionId = namespace + _getSectionId(section);
		String sectionJsp = jspPath + _getSectionJsp(section) + ".jsp";
	%>

		<!-- Begin fragment <%= sectionId %> -->

		<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="<%= section %>">
			<liferay-util:include page="<%= sectionJsp %>" portletId="<%= portletDisplay.getRootPortletId() %>" />
		</aui:fieldset>

		<!-- End fragment <%= sectionId %> -->

	<%
	}

	List<FormNavigatorEntry<Object>> formNavigatorEntries = FormNavigatorEntryUtil.getFormNavigatorEntries(id, user, formModelBean);

	for (int i = 1; i < formNavigatorEntries.size(); i++) {
		final FormNavigatorEntry formNavigatorEntry = formNavigatorEntries.get(i);

		String sectionId = namespace + _getSectionId(formNavigatorEntry.getKey());
	%>

		<!-- Begin fragment <%= sectionId %> -->

		<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="<%= formNavigatorEntry.getLabel(locale) %>">

			<%
			PortalIncludeUtil.include(
				pageContext,
				new PortalIncludeUtil.HTMLRenderer() {

					public void renderHTML(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
						formNavigatorEntry.include(request, response);
					}

				});
			%>

		</aui:fieldset>

		<!-- End fragment <%= sectionId %> -->

	<%
	}
	%>

</aui:fieldset-group>

<c:if test="<%= showButtons %>">
	<aui:button-row>
		<aui:button cssClass="btn-lg btn-primary" type="submit" />
	</aui:button-row>
</c:if>