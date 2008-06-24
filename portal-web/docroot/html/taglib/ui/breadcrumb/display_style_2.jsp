<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/ui/breadcrumb/init.jsp" %>

<%
StringMaker sm = new StringMaker();

_buildBreadcrumb(selLayout, selLayoutParam, portletURL, true, request, themeDisplay, sm);
%>

<%= sm.toString() %>

<%!
private void _buildBreadcrumb(Layout selLayout, String selLayoutParam, PortletURL portletURL, boolean selectedLayout, HttpServletRequest req, ThemeDisplay themeDisplay, StringMaker sm) throws Exception {
	String junctionPointURL = null;

	List<Layout> selBranch = selLayout.getJunctionAncestors(req);

	for (int i = selBranch.size() - 1; i >= 0; i--) {
		Layout curLayout = selBranch.get(i);

		String layoutURL = _getBreadcrumbLayoutURL(curLayout, selLayoutParam, portletURL, themeDisplay);

		if (curLayout.getType().equals(LayoutConstants.TYPE_JUNCTION_POINT)) {
			junctionPointURL = layoutURL;

			continue;
		}

		String target = PortalUtil.getLayoutTarget(curLayout);

		if (i == 0) {
			if (curLayout.getParentLayoutId() != LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
				sm.append("<br />");
				sm.append("<br />");
			}

			sm.append("<div class=\"font-xx-large\" style=\"font-weight: bold;\">");
			sm.append(curLayout.getName(themeDisplay.getLocale()));
			sm.append("</div>");
			sm.append("<br />");
		}
		else {
			sm.append("<a href=\"");
			sm.append((junctionPointURL != null) ? junctionPointURL : layoutURL);
			sm.append("\" ");
			sm.append(target);
			sm.append(">");
			sm.append(curLayout.getName(themeDisplay.getLocale()));
			sm.append("</a>");

			sm.append(" &raquo; ");

			junctionPointURL = null;
		}
	}
}

private String _getBreadcrumbLayoutURL(Layout selLayout, String selLayoutParam, PortletURL portletURL, ThemeDisplay themeDisplay) throws Exception {
	if (portletURL == null) {
		return PortalUtil.getLayoutURL(selLayout, themeDisplay);
	}
	else {
		portletURL.setParameter(selLayoutParam, String.valueOf(selLayout.getPlid()));

		return portletURL.toString();
	}
}
%>