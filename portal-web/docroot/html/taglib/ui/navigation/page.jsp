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

<%@ include file="/html/taglib/ui/navigation/init.jsp" %>

<c:if test="<%= layout != null %>">

	<%
	Layout rootLayout = null;
	boolean hidden = false;

	List selBranch = new ArrayList();

	selBranch.add(layout);
	selBranch.addAll(layout.getAncestors());

	if (rootLayoutType.equals("relative")) {
		if ((rootLayoutLevel >= 0) && (rootLayoutLevel < selBranch.size())) {
			rootLayout = (Layout) selBranch.get(rootLayoutLevel);
		}
		else {
			rootLayout = null;
		}
	}
	else if (rootLayoutType.equals("absolute")) {
		int ancestorIndex = selBranch.size() - rootLayoutLevel;

		if ((ancestorIndex >= 0) && (ancestorIndex < selBranch.size())) {
			rootLayout = (Layout) selBranch.get(ancestorIndex);
		}
		else if (ancestorIndex == selBranch.size()) {
			rootLayout = null;
		}
		else {
			hidden = true;
		}
	}
	%>

	<div class="nav-menu nav-menu-style-<%= bulletStyle %>">

		<c:choose>
			<c:when test='<%= (headerType.equals("root-layout") && (rootLayout != null)) %>'>

				<%
				String layoutURL = PortalUtil.getLayoutURL(rootLayout, themeDisplay);
				String target = PortalUtil.getLayoutTarget(rootLayout);
				String layoutName = rootLayout.getName(themeDisplay.getLocale());
				%>

				<h2>
					<a href="<%= layoutURL %>" <%= target %>><%= layoutName %></a>
				</h2>
			</c:when>
			<c:when test='<%= headerType.equals("portlet-title") %>'>
				<h2><%= themeDisplay.getPortletDisplay().getTitle() %></h2>
			</c:when>
			<c:when test='<%= headerType.equals("breadcrumb") %>'>
				<liferay-ui:breadcrumb />
			</c:when>
		</c:choose>

		<%
		if (!hidden) {
			StringBundler sb = new StringBundler();

			_buildNavigation(rootLayout, layout, selBranch, themeDisplay, 1, includedLayouts, nestedChildren, sb);

			String content = sb.toString();

			/*if (!nestedChildren) {
				content = StringUtil.replace(content, "</a><ul class", "</a></li></ul><ul class");
				content = StringUtil.replace(content, "</ul></li>", "</ul><ul class=\"layouts\">");
			}*/
		%>

			<%= content %>

		<%
		}
		%>

	</div>
</c:if>

<%!
private void _buildNavigation(Layout rootLayout, Layout selLayout, List selBranch, ThemeDisplay themeDisplay, int layoutLevel, String includedLayouts, boolean nestedChildren, StringBundler sb) throws Exception {
	List layoutChildren = null;

	if (rootLayout != null) {
		layoutChildren = rootLayout.getChildren(themeDisplay.getPermissionChecker());
	}
	else {
		layoutChildren = LayoutLocalServiceUtil.getLayouts(selLayout.getGroupId(), selLayout.isPrivateLayout(), LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
	}

	if (!layoutChildren.isEmpty()) {
		StringBundler tailSB = null;

		if (!nestedChildren) {
			tailSB = new StringBundler();
		}

		sb.append("<ul class=\"layouts level-");
		sb.append(layoutLevel);
		sb.append("\">");

		for (int i = 0; i < layoutChildren.size(); i++) {
			Layout layoutChild = (Layout)layoutChildren.get(i);

			if (!layoutChild.isHidden() && LayoutPermissionUtil.contains(themeDisplay.getPermissionChecker(), layoutChild, ActionKeys.VIEW)) {
				String layoutURL = PortalUtil.getLayoutURL(layoutChild, themeDisplay);
				String target = PortalUtil.getLayoutTarget(layoutChild);

				boolean open = false;

				if (includedLayouts.equals("auto") && selBranch.contains(layoutChild) && !layoutChild.getChildren().isEmpty()) {
					open = true;
				}

				if (includedLayouts.equals("all")) {
					open = true;
				}

				StringBundler className = new StringBundler(2);

				if (open) {
					className.append("open ");
				}

				if (selLayout.getLayoutId() == layoutChild.getLayoutId()) {
					className.append("selected ");
				}

				sb.append("<li ");

				if (Validator.isNotNull(className)) {
					sb.append("class=\"");
					sb.append(className);
					sb.append("\" ");
				}

				sb.append(">");
				sb.append("<a ");

				if (Validator.isNotNull(className)) {
					sb.append("class=\"");
					sb.append(className);
					sb.append("\" ");
				}

				sb.append("href=\"");
				sb.append(layoutURL);
				sb.append("\" ");
				sb.append(target);
				sb.append("> ");
				sb.append(layoutChild.getName(themeDisplay.getLocale()));
				sb.append("</a>");

				if (open) {
					StringBundler layoutChildSB = null;

					if (nestedChildren) {
						layoutChildSB = sb;
					}
					else {
						layoutChildSB = tailSB;
					}

					_buildNavigation(layoutChild, selLayout, selBranch, themeDisplay, layoutLevel + 1, includedLayouts, nestedChildren, layoutChildSB);
				}

				sb.append("</li>");
			}
		}

		sb.append("</ul>");

		if (!nestedChildren) {
			sb.append(tailSB);
		}
	}
}
%>