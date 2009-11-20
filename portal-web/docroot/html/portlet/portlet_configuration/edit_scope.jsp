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

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/portlet_configuration/edit_scope" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SAVE %>" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(currentURL) %>" />
<input name="<portlet:namespace />returnToFullPageURL" type="hidden" value="<%= HtmlUtil.escapeAttribute(returnToFullPageURL) %>" />
<input name="<portlet:namespace />portletResource" type="hidden" value="<%= HtmlUtil.escapeAttribute(portletResource) %>">

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="scope" />
	</td>
	<td>
		<select name="<portlet:namespace />scopeLayoutId">
			<option <%= (scopeLayoutId == 0) ? "selected" : "" %> value="0"> <liferay-ui:message key="default" /></option>
			<option <%= (scopeLayoutId == layout.getLayoutId()) ? "selected" : "" %> value="<%= layout.getLayoutId() %>"><liferay-ui:message key="current-page" /> (<%= HtmlUtil.escape(layout.getName(locale)) %>)</option>

			<%
			for (Layout curLayout : LayoutLocalServiceUtil.getLayouts(layout.getGroupId(), layout.isPrivateLayout())) {
				if (curLayout.getPlid() == layout.getPlid()) {
					continue;
				}

				if (curLayout.hasScopeGroup()) {
			%>

					<option <%= (scopeLayoutId == curLayout.getLayoutId()) ? "selected" : "" %> value="<%= curLayout.getLayoutId() %>"><%= HtmlUtil.escape(curLayout.getName(locale)) %></option>

			<%
				}
			}
			%>

		</select>
	</td>
</tr>
</table>

<br />

<input type="submit" value="<liferay-ui:message key="save" />" />

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(PortalUtil.escapeRedirect(redirect)) %>';" />

</form>