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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

String portletResource = ParamUtil.getString(request, "portletResource");

PortletPreferences preferences = PortletPreferencesFactoryUtil.getLayoutPortletSetup(layout, portletResource);

long scopeLayoutId = GetterUtil.getLong(preferences.getValue("lfr-scope-layout-id", null));

Group group = layout.getGroup();
%>

<liferay-util:include page="/html/portlet/portlet_configuration/tabs1.jsp">
	<liferay-util:param name="tabs1" value="scope" />
</liferay-util:include>

<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editScopeURL">
	<portlet:param name="struts_action" value="/portlet_configuration/edit_scope" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SAVE %>" />
</portlet:actionURL>

<aui:form action="<%= editScopeURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="returnToFullPageURL" type="hidden" value="<%= returnToFullPageURL %>" />
	<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />

	<aui:fieldset>
		<aui:select label="scope" name="scopeLayoutId">
			<aui:option label="default" selected="<%= scopeLayoutId == 0 %>" value="0" />
			<aui:option label='<%= LanguageUtil.get(pageContext,"current-page") + " (" + HtmlUtil.escape(layout.getName(locale)) + ")" %>' selected="<%= scopeLayoutId == layout.getLayoutId() %>" value="<%= layout.getLayoutId() %>" />

			<%
			for (Layout curLayout : LayoutLocalServiceUtil.getLayouts(layout.getGroupId(), layout.isPrivateLayout())) {
				if (curLayout.getPlid() == layout.getPlid()) {
					continue;
				}

				if (curLayout.hasScopeGroup()) {
			%>

					<aui:option label="<%= HtmlUtil.escape(curLayout.getName(locale)) %>" selected="<%= scopeLayoutId == curLayout.getLayoutId() %>" value="<%= curLayout.getLayoutId() %>" />

			<%
				}
			}
			%>

		</aui:select>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>