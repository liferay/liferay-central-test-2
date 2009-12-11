<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
%>

<%@ include file="/html/portlet/nested_portlets/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<aui:fieldset>
		<aui:legend label="layout-template" />

		<table border="0" cellpadding="0" cellspacing="10" style="margin-top: 10px;" width="100%">

		<%
		int CELLS_PER_ROW = 4;

		String layoutTemplateId = preferences.getValue("layout-template-id", PropsValues.NESTED_PORTLETS_LAYOUT_TEMPLATE_DEFAULT);

		List layoutTemplates = LayoutTemplateLocalServiceUtil.getLayoutTemplates(theme.getThemeId());

		layoutTemplates = PluginUtil.restrictPlugins(layoutTemplates, user);

		List unsupportedLayoutTemplates = ListUtil.fromArray(PropsUtil.getArray(PropsKeys.NESTED_PORTLETS_LAYOUT_TEMPLATE_UNSUPPORTED));

		for (int i = 0; i < layoutTemplates.size(); i++) {
			LayoutTemplate curLayoutTemplate = (LayoutTemplate)layoutTemplates.get(i);

			if (unsupportedLayoutTemplates.contains(curLayoutTemplate.getLayoutTemplateId())) {
				layoutTemplates.remove(i);
			}
		}

		for (int i = 0; i < layoutTemplates.size(); i++) {
			LayoutTemplate curLayoutTemplate = (LayoutTemplate)layoutTemplates.get(i);
		%>

			<c:if test="<%= (i % CELLS_PER_ROW) == 0 %>">
				<tr>
			</c:if>

			<td align="center" width="<%= 100 / CELLS_PER_ROW %>%">
				<img onclick="document.getElementById('<portlet:namespace />layoutTemplateId<%= i %>').checked = true;" src="<%= curLayoutTemplate.getContextPath() %><%= curLayoutTemplate.getThumbnailPath() %>" /><br />

				<aui:input checked="<%= layoutTemplateId.equals(curLayoutTemplate.getLayoutTemplateId()) %>" id='<%= "layoutTemplateId" + i %>' label="<%= curLayoutTemplate.getName() %>" name="layoutTemplateId" type="radio" value="<%= curLayoutTemplate.getLayoutTemplateId() %>" />
			</td>

			<c:if test="<%= (i % CELLS_PER_ROW) == (CELLS_PER_ROW - 1) %>">
				</tr>
			</c:if>

		<%
		}
		%>

		</table>
	</aui:fieldset>

	<%
	boolean portletDecorateDefault = GetterUtil.getBoolean(theme.getSetting("portlet-setup-show-borders-default"), true);

	boolean portletSetupShowBorders = GetterUtil.getBoolean(preferences.getValue("portlet-setup-show-borders", String.valueOf(portletDecorateDefault)));
	%>

	<aui:fieldset>
		<aui:legend label="display-settings" />

		<aui:input inlineLabel="left" label="show-borders" name="portletSetupShowBorders" type="checkbox" value="<%= portletSetupShowBorders %>" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>