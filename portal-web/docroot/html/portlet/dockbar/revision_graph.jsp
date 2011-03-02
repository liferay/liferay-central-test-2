<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/dockbar/init.jsp" %>

<style type="text/css">
.layout-revision-container-root {
	margin: 0 0 0 2em;
}

.layout-revision-container-root ul.layout-revision-container {
	margin: 0 0 0 .5em;
	padding: 0;
}

.layout-revision-container-root li.layout-revision {
	list-style-image: url('<%= themeDisplay.getPathThemeImages() %>/navigation/bullet.png');
	margin: 1px 0;
	padding: 6px 0;
}

.layout-revision-container-root li.layout-revision.current {
	list-style-image: url('<%= themeDisplay.getPathThemeImages() %>/arrows/01_right.png');
}

.layout-revision-container-root li.layout-revision.current.head {
	list-style-image: url('<%= themeDisplay.getPathThemeImages() %>/arrows/02_right.png');
}

.layout-revision-container-root li.layout-revision.head {
	list-style-image: url('<%= themeDisplay.getPathThemeImages() %>/arrows/02_plus.png');
}

.layout-revision-container-root li.layout-revision a.selection-handle {
}

.layout-revision-container-root li.layout-revision a.user-handle {
}

.lfr-menu-list {
	z-index: 1002;
}
</style>

<%
long layoutSetBranchId = ParamUtil.getLong(request, "layoutSetBranchId");

long layoutRevisionId = GetterUtil.getLong(SessionClicks.get(request, Staging.class.getName(), LayoutRevisionConstants.encodeKey(layoutSetBranchId, plid), StringPool.BLANK));

List<LayoutRevision> revisions = LayoutRevisionLocalServiceUtil.getLayoutRevisions(layoutSetBranchId, LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID, plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new LayoutRevisionIdComparator(true));

if (!revisions.isEmpty()) {
	StringBundler sb = new StringBundler();

	sb.append("<ul class='layout-revision-container layout-revision-container-root'>");
	_getGraph(pageContext, revisions, layout, themeDisplay, renderResponse.getNamespace(), sb, layoutRevisionId);
	sb.append("</ul>");
%>

	<%= sb.toString() %>

<%
}
%>

<%!
public String _getGraph(PageContext pageContext, List<LayoutRevision> revisions, Layout layout, ThemeDisplay themeDisplay, String namespace, StringBundler sb, long layoutRevisionId) throws PortalException, SystemException {
	for (LayoutRevision revision : revisions) {
		sb.append("<li class='layout-revision");

		if (revision.getLayoutRevisionId() == layoutRevisionId) {
			sb.append(" current");
		}

		if (revision.isHead()) {
			sb.append(" head");
		}

		sb.append("'><a class='selection-handle' href='#' data-layoutRevisionId='");
		sb.append(revision.getLayoutRevisionId());
		sb.append("' data-layoutSetBranchId='");
		sb.append(revision.getLayoutSetBranchId());
		sb.append("'>");

		if (revision.isMajor()) {
			sb.append("<strong>");
			sb.append(revision.getLayoutRevisionId());

			sb.append("</strong>");
		}
		else {
			sb.append(revision.getLayoutRevisionId());
		}

		sb.append("</a>");

		User curUser = UserLocalServiceUtil.getUserById(revision.getUserId());

		sb.append(" - <a class='user-handle' href='");
		sb.append(curUser.getDisplayURL(themeDisplay));
		sb.append("'>");
		sb.append(curUser.getFullName());
		sb.append("</a>");

		if (revision.isPending()) {
			sb.append(" - <span class='status'>");
			sb.append(themeDisplay.translate("pending"));
			sb.append("</span>");
		}

		RequestDispatcher rd = pageContext.getServletContext().getRequestDispatcher("/html/portlet/dockbar/revision_action.jsp");

		pageContext.getRequest().setAttribute("revision_graph.jsp-layoutRevision", revision);

		StringServletResponse response = new StringServletResponse((HttpServletResponse)pageContext.getResponse());

		if (rd != null) {
			try {
				rd.include(pageContext.getRequest(), response);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		sb.append(response.getString());

		List<LayoutRevision> curRevisions = LayoutRevisionLocalServiceUtil.getLayoutRevisions(revision.getLayoutSetBranchId(), revision.getLayoutRevisionId(), revision.getPlid(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, new LayoutRevisionIdComparator(true));

		if (!revisions.isEmpty()) {
			sb.append("<ul class='layout-revision-container'>");

			_getGraph(pageContext, curRevisions, layout, themeDisplay, namespace, sb, layoutRevisionId);

			sb.append("</ul>");
		}

		sb.append("</li>");
	}

	return sb.toString();
}
%>