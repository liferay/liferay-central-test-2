<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
StringBuilder sb = new StringBuilder();

if (showGuestGroup) {
	_buildGuestGroupBreadcrumb(themeDisplay, sb);
}

if (showParentGroups) {
	_buildParentGroupsBreadcrumb(selLayout.getLayoutSet(), portletURL, themeDisplay, sb);
}

if (showLayout) {
	_buildLayoutBreadcrumb(selLayout, selLayoutParam, portletURL, themeDisplay, true, sb);
}

if (showPortletBreadcrumb) {
	_buildPortletBreadcrumb(request, sb);
}

String breadCrumbString = sb.toString();

if (Validator.isNotNull(breadCrumbString)) {
	String listToken = "<li";
	int tokenLength = listToken.length();

	int pos = breadCrumbString.indexOf(listToken);

	breadCrumbString = StringUtil.insert(breadCrumbString, " class=\"first\"", pos + tokenLength);

	pos = breadCrumbString.lastIndexOf(listToken);

	breadCrumbString = StringUtil.insert(breadCrumbString, " class=\"last\"", pos + tokenLength);
}
%>

<ul class="breadcrumbs lfr-component">
	<%= breadCrumbString %>
</ul>

<%!
private void _buildLayoutBreadcrumb(Layout selLayout, String selLayoutParam, PortletURL portletURL, ThemeDisplay themeDisplay, boolean selectedLayout, StringBuilder sb) throws Exception {
	String layoutURL = _getBreadcrumbLayoutURL(selLayout, selLayoutParam, portletURL, themeDisplay);
	String target = PortalUtil.getLayoutTarget(selLayout);

	StringBuilder breadCrumbSB = new StringBuilder();

	breadCrumbSB.append("<li><span><a href=\"");
	breadCrumbSB.append(layoutURL);
	breadCrumbSB.append("\" ");
	breadCrumbSB.append(target);
	breadCrumbSB.append(">");

	breadCrumbSB.append(HtmlUtil.escape(selLayout.getName(themeDisplay.getLocale())));

	breadCrumbSB.append("</a></span></li>");

	Layout layoutParent = null;
	long layoutParentId = selLayout.getParentLayoutId();

	if (layoutParentId != LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
		layoutParent = LayoutLocalServiceUtil.getLayout(selLayout.getGroupId(), selLayout.isPrivateLayout(), layoutParentId);

		_buildLayoutBreadcrumb(layoutParent, selLayoutParam, portletURL, themeDisplay, false, sb);

		sb.append(breadCrumbSB.toString());
	}
	else {
		sb.append(breadCrumbSB.toString());
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