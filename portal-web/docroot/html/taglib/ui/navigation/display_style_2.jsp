<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

<script type="text/javascript">
	function <portlet:namespace />updateTitle(title) {
		var titleBar = document.getElementById('portlet-title-bar_<%= portletDisplay.getId() %>');

		if (titleBar) {
			titleBar.innerHTML = title;
		}
	}
</script>

<%
Set layoutFamilySet = new LinkedHashSet();

Layout ancestorLayout = layout;

while (true) {
	layoutFamilySet.add(ancestorLayout);

	if (ancestorLayout.getParentLayoutId() != LayoutImpl.DEFAULT_PARENT_LAYOUT_ID) {
		ancestorLayout = LayoutLocalServiceUtil.getLayout(ancestorLayout.getGroupId(), ancestorLayout.isPrivateLayout(), ancestorLayout.getParentLayoutId());
	}
	else {
		break;
	}
}

int depth = ParamUtil.getInteger(request, "depth", 2);
boolean showTitle = ParamUtil.getBoolean(request, "showTitle");

if (layoutFamilySet.size() >= depth) {
	Layout[] layoutFamilyArray = (Layout[])layoutFamilySet.toArray(new Layout[0]);

	Layout depthLevelLayout = null;

	if (depth > 0) {
		depthLevelLayout = layoutFamilyArray[layoutFamilyArray.length - depth];
	}

	StringMaker sm = new StringMaker();

	sm.append("<div class=\"nav-menu\">");

	_buildNavigation(depthLevelLayout, layout, layoutFamilySet, themeDisplay, 1, bulletStyle, showTitle, sm);

	sm.append("</div>");
%>

	<%= sm.toString() %>

<%
}
%>

<%!
private void _buildNavigation(Layout layout, Layout selLayout, Set layoutFamilySet, ThemeDisplay themeDisplay, int layoutLevel, int bulletStyle, boolean showTitle, StringMaker sm) throws Exception {
	List layoutChildren = null;

	if (layout != null) {
		layoutChildren = layout.getChildren();
	}
	else {
		layoutChildren = LayoutLocalServiceUtil.getLayouts(selLayout.getGroupId(), selLayout.isPrivateLayout(), LayoutImpl.DEFAULT_PARENT_LAYOUT_ID);
	}

	if (layoutChildren.size() > 0) {
		if (layoutLevel == 1) {
			String layoutURL = StringPool.BLANK;
			String target = StringPool.BLANK;

			if (layout != null) {
				layoutURL = PortalUtil.getLayoutURL(layout, themeDisplay);
				target = PortalUtil.getLayoutTarget(layout);
			}

			if (showTitle) {
				PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

				sm.append("<script type='text/javascript'>");
				sm.append(portletDisplay.getNamespace() + "updateTitle('");
				sm.append("<a style=\"color: ");
				sm.append(themeDisplay.getColorScheme().getPortletTitleText());
				sm.append(";\" href=\"");
			}
			else {
				sm.append("<h3>");
				sm.append("<a href=\"");
			}

			String layoutName = StringPool.BLANK;

			if (layout != null){
				layoutName = layout.getName(themeDisplay.getLocale());
			}

			sm.append(layoutURL);
			sm.append("\" ");
			sm.append(target);
			sm.append("> ");
			sm.append(layoutName);
			sm.append("</a>");

			if (!showTitle) {
				sm.append("</h3>");
			}
			else {
				sm.append("');");
				sm.append("</script>");
			}
		}

		if (bulletStyle == 1) {
			sm.append("<ul>");

			for (int i = 0; i < layoutChildren.size(); i++) {
				Layout layoutChild = (Layout)layoutChildren.get(i);

				if (!layoutChild.isHidden() && LayoutPermission.contains(themeDisplay.getPermissionChecker(), layoutChild, ActionKeys.VIEW)) {
					String layoutURL = PortalUtil.getLayoutURL(layoutChild, themeDisplay);
					String target = PortalUtil.getLayoutTarget(layoutChild);

					String className = StringPool.BLANK;

					if (layoutFamilySet.contains(layoutChild) && (layoutChild.getChildren().size() > 0)) {
						className = "open";
					}

					if (selLayout.getLayoutId() == layoutChild.getLayoutId()) {
						className = "selected";
					}

					sm.append("<li ");

					if (Validator.isNotNull(className)) {
						sm.append("class=\"");
						sm.append(className);
						sm.append("\" ");
					}

					sm.append(">");
					sm.append("<a ");

					if (Validator.isNotNull(className)) {
						sm.append("class=\"");
						sm.append(className);
						sm.append("\" ");
					}

					sm.append("href=\"");
					sm.append(layoutURL);
					sm.append("\" ");
					sm.append(target);
					sm.append("> ");
					sm.append(layoutChild.getName(themeDisplay.getLocale()));
					sm.append("</a>");

					if (layoutFamilySet.contains(layoutChild)) {
						_buildNavigation(layoutChild, selLayout, layoutFamilySet, themeDisplay, layoutLevel + 1, bulletStyle, showTitle, sm);
					}

					sm.append("</li>");
				}
			}

			sm.append("</ul>");
		}
		else {
			if (layoutLevel == 1) {
				sm.append("<div style=\"margin-left: 5px;\">");
			}
			else {
				sm.append("<div style=\"margin-left: 10px;\">");
			}

			for (int i = 0; i < layoutChildren.size(); i++) {
				Layout layoutChild = (Layout)layoutChildren.get(i);

				if (!layoutChild.isHidden() && LayoutPermission.contains(themeDisplay.getPermissionChecker(), layoutChild, ActionKeys.VIEW)) {
					String layoutURL = PortalUtil.getLayoutURL(layoutChild, themeDisplay);
					String target = PortalUtil.getLayoutTarget(layoutChild);

					String layoutName = layoutChild.getName(themeDisplay.getLocale());

					if (layoutLevel == 1) {
						if (i != 0) {
							sm.append("<div class=\"alpha-separator\"></div>");
						}

						sm.append("<div style=\"padding: 2px;\">");
					}
					else {
						sm.append("<div style=\"padding: 2px;\" class=\"font-small\">");
					}

					if (layoutLevel == 2) {
						sm.append(" &raquo; ");
					}
					else if (layoutLevel >= 3) {
						sm.append(" &#9642; ");
					}

					if (layoutFamilySet.contains(layoutChild)) {
						sm.append("<b>");
					}

					sm.append("<a href=\"");
					sm.append(layoutURL);
					sm.append("\" ");

					if (layoutLevel != 1) {
						sm.append("style=\"color: #606060;\" ");
					}

					sm.append(target);
					sm.append(">");
					sm.append(layoutName);
					sm.append("</a>");

					if (layoutFamilySet.contains(layoutChild)) {
						sm.append("</b>");
					}

					sm.append("</div>");

					if (layoutLevel == 1) {
						if (i == layoutChildren.size() - 1) {
							sm.append("<div class=\"alpha-separator\"></div>");
						}
						else if (layoutFamilySet.contains(layoutChild) && layoutChild.getChildren().size() > 0) {
							sm.append("<div class=\"alpha-separator\"></div>");
						}
					}

					if (layoutFamilySet.contains(layoutChild)) {
						_buildNavigation(layoutChild, selLayout, layoutFamilySet, themeDisplay, layoutLevel + 1, bulletStyle, showTitle, sm);
					}
				}
			}

			sm.append("</div>");
		}
	}
}
%>