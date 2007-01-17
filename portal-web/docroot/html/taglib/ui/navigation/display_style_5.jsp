<%
/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/ui/navigation/init.jsp" %>

<%
PortletPreferences prefs = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	prefs = PortletPreferencesFactory.getPortletSetup(request, portletResource, true, true);
}

String rootLayoutId = layout.getAncestorLayoutId();
int displayDepth = 0;

List rootLayouts = LayoutLocalServiceUtil.getLayouts(layout.getOwnerId(), rootLayoutId);

StringBuffer sb = new StringBuffer();

_buildSiteMap(rootLayouts, displayDepth, 1, themeDisplay, sb);
%>

<div class="portlet-nav-map">
	<%= sb.toString() %>
</div>

<script type="text/javascript">
	NavFlyout.initialize($("p_p_id<portlet:namespace />"));
	$("p_p_id<portlet:namespace />").onclick = function() {
		NavFlyout.initialize($("p_p_id<portlet:namespace />"));
	}
	NavFlyout.initToggle($("p_p_id<portlet:namespace />"), "<%= themeDisplay.getPathThemeImage() %>/arrows/02_plus.gif");
</script>

<%!
private void _buildSiteMap(List layouts, int displayDepth, int curDepth, ThemeDisplay themeDisplay, StringBuffer sb) throws Exception {
	PermissionChecker permissionChecker = themeDisplay.getPermissionChecker();
	boolean openList = true;
	boolean closeList = false;

	for (int i = 0; i < layouts.size(); i++) {
		Layout layout = (Layout)layouts.get(i);

		if (!layout.isHidden() && LayoutPermission.contains(permissionChecker, layout, ActionKeys.VIEW)) {
			String layoutURL = PortalUtil.getLayoutURL(layout, themeDisplay);
			String target = PortalUtil.getLayoutTarget(layout);
			int children = layout.getChildren().size();

			if (openList) {
				sb.append("<ul class=\"portlet-nav-map-list portlet-nav-map-level_" + curDepth + "\">");
				openList = false;
				closeList = true;
			}

			sb.append("<li>");
			sb.append("<a href=\"");
			sb.append(layoutURL);
			sb.append("\" ");
			sb.append(target);
			sb.append("> ");
			sb.append(layout.getName(themeDisplay.getLocale()));
			sb.append("</a>");

			if (curDepth > 1 && children > 0) {
				sb.append("&nbsp;&raquo;");
			}

			if ((displayDepth == 0) || (displayDepth > curDepth) && children > 0) {
				_buildSiteMap(layout.getChildren(), displayDepth, curDepth + 1, themeDisplay, sb);
			}
			
			sb.append("</li>");
		}
	}
	
	if (closeList) {
		sb.append("</ul>");
	}
}
%>