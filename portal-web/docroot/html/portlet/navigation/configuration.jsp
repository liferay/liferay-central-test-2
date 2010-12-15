<%--
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
--%>

<%@ include file="/html/portlet/navigation/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
%>

<aui:layout>
	<aui:column columnWidth="50">
		<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

		<aui:form action="<%= configurationURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
			<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

			<aui:fieldset column="<%= true %>">
				<aui:select name="displayStyle">
					<aui:option label="custom" selected='<%= displayStyle.equals("[custom]") %>' value="[custom]" />

					<optgroup label="<liferay-ui:message key="predefined" />">

						<%
						for (String displayStyleOption : PropsValues.NAVIGATION_DISPLAY_STYLE_OPTIONS) {
						%>

							<aui:option label="<%= displayStyleOption %>" selected="<%= displayStyle.equals(displayStyleOption) %>" />

						<%
						}
						%>

					</optgroup>
				</aui:select>

				<aui:select name="bulletStyle">

					<%
					String[] bulletStyleOptions = StringUtil.split(theme.getSetting("bullet-style-options"));

					for (String bulletStyleOption : bulletStyleOptions) {
					%>

						<aui:option label="<%= LanguageUtil.get(pageContext, bulletStyleOption) %>" selected="<%= bulletStyle.equals(bulletStyleOption) %>" />

					<%
					}
					%>

					<c:if test="<%= bulletStyleOptions.length == 0 %>">
						<aui:option label="default" value="" />
					</c:if>
				</aui:select>
			</aui:fieldset>

			<aui:fieldset column="<%= true %>">
				<div id="<portlet:namespace />customDisplayOptions">
					<aui:select label="header" name="headerType">
						<aui:option label="none" selected='<%= headerType.equals("none") %>' />
						<aui:option label="portlet-title" selected='<%= headerType.equals("portlet-title") %>' />
						<aui:option label="root-layout" selected='<%= headerType.equals("root-layout") %>' />
						<aui:option label="breadcrumb" selected='<%= headerType.equals("breadcrumb") %>' />
					</aui:select>

					<aui:select label="root-layout" name="rootLayoutType">
						<aui:option label="parent-at-level" selected='<%= rootLayoutType.equals("absolute") %>' value="absolute" />
						<aui:option label="relative-parent-up-by" selected='<%= rootLayoutType.equals("relative") %>' value="relative" />
					</aui:select>

					<aui:select name="rootLayoutLevel">

						<%
						for (int i = 0; i <= 4; i++) {
						%>

							<aui:option label="<%= i %>" selected="<%= rootLayoutLevel == i %>" />

						<%
						}
						%>

					</aui:select>

					<aui:select name="includedLayouts">
						<aui:option label="auto" selected='<%= includedLayouts.equals("auto") %>' />
						<aui:option label="all" selected='<%= includedLayouts.equals("all") %>' />
					</aui:select>

					<aui:select name="nestedChildren">
						<aui:option label="yes" selected="<%= nestedChildren %>" value="1" />
						<aui:option label="no" selected="<%= !nestedChildren %>" value="0" />
					</aui:select>
				</div>
			</aui:fieldset>

			<aui:button-row>
				<aui:button type="submit" />
			</aui:button-row>
		</aui:form>
	</aui:column>
	<aui:column columnWidth="50">
		<liferay-portlet:preview
			portletName="<%= portletResource %>"
			queryString="struts_action=/navigation/view"
			showBorders="<%= true %>"
		/>
	</aui:column>
</aui:layout>

<aui:script use="aui-base">
	var selectDisplayStyle = A.one('#<portlet:namespace />displayStyle');
	var selectBulletStyle = A.one('#<portlet:namespace />bulletStyle');
	var selectHeaderType = A.one('#<portlet:namespace />headerType');
	var selectRootLayoutType = A.one('#<portlet:namespace />rootLayoutType');
	var selectRootLayoutLevel = A.one('#<portlet:namespace />rootLayoutLevel');
	var selectIncludedLayouts = A.one('#<portlet:namespace />includedLayouts');
	var selectNestedChildren = A.one('#<portlet:namespace />nestedChildren');
	var customDisplayOptions = A.one('#<portlet:namespace />customDisplayOptions');

	var selects = A.all('#<portlet:namespace />fm select');

	var curPortletBoundaryId = '#p_p_id_<%= portletResource %>_';

	var toggleCustomFields = function() {
		if (customDisplayOptions) {
			var data = {};
			var action = 'hide';

			var displayStyle = selectDisplayStyle.val();

			if (displayStyle == '[custom]') {
				action = 'show';

				data['_<%= portletResource %>_header-type'] = selectHeaderType.get('value');
				data['_<%= portletResource %>_root-layout-type'] = selectRootLayoutType.get('value');
				data['_<%= portletResource %>_root-layout-level'] = selectRootLayoutLevel.get('value');
				data['_<%= portletResource %>_included-layouts'] = selectIncludedLayouts.get('value');
				data['_<%= portletResource %>_nested-children'] = selectNestedChildren.get('value');
			}

			customDisplayOptions[action]();

			data['_<%= portletResource %>_bullet-style'] = selectBulletStyle.get('value');
			data['_<%= portletResource %>_display-style'] = selectDisplayStyle.get('value');

			Liferay.Portlet.refresh(curPortletBoundaryId, data);
		}
	}

	selects.on('change', toggleCustomFields);

	toggleCustomFields();
</aui:script>