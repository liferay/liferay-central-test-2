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

<%@ include file="/html/taglib/ui/breadcrumb/init.jsp" %>

<%
StringBundler sb = new StringBundler();

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

<ul class="breadcrumbs breadcrumbs-vertical lfr-component">
	<%= breadCrumbString %>
</ul>