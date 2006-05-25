<%
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

<c:if test="<%= (layouts != null) && (layouts.size() > 0) %>">
	<div id="layout-nav-container">
		<div class="layout-nav-tabs-box">

	<%
	int tabNameMaxLength = GetterUtil.getInteger(PropsUtil.get(PropsUtil.LAYOUT_NAME_MAX_LENGTH));
	int tabsPerRow = GetterUtil.getInteger(PropsUtil.get(PropsUtil.LAYOUT_TABS_PER_ROW));
	int totalTabs = layouts.size();
	int totalTabRows = (totalTabs / tabsPerRow);

	if ((totalTabs % tabsPerRow) > 0) {
		totalTabRows++;
	}

	int rowCounter = 0;
	int currentTab = 0;

	boolean isSelectedTab = false;
	boolean isPrevSelectedTab = false;

	int selectedRow = 0;
	int selectedTab = 0;

	String rows[][] = new String[totalTabRows][tabsPerRow];

	String ancestorLayoutId = "";
	if (layout != null) {
		ancestorLayoutId = layout.getAncestorLayoutId();
	}

	for (int i = 0; i < layouts.size(); i++) {
		Layout curLayout = (Layout)layouts.get(i);

		String tabName = StringUtil.shorten(curLayout.getName(locale), tabNameMaxLength);
		String tabHREF = PortalUtil.getLayoutURL(curLayout, themeDisplay);
		boolean isGroupTab = true;// FIX ME curLayout.isGroup();
		isSelectedTab = selectable && (layout != null && (plid.equals(curLayout.getLayoutId()) || curLayout.getLayoutId().equals(ancestorLayoutId)));

		if (isSelectedTab && layoutTypePortlet.hasStateMax()) {
			String portletId = StringUtil.split(layoutTypePortlet.getStateMax())[0];

			PortletURLImpl portletURLImpl = new PortletURLImpl(request, portletId, plid, false);

			portletURLImpl.setWindowState(WindowState.NORMAL);
			portletURLImpl.setPortletMode(PortletMode.VIEW);
			portletURLImpl.setAnchor(false);

			tabHREF = portletURLImpl.toString();
		}

		String target = PortalUtil.getLayoutTarget(curLayout);

		String tabText = null;

		if (isSelectedTab) {
			tabText = "<a href=\"" + tabHREF + "\" " + target + ">" + tabName + "</a>";
			selectedRow = rowCounter;
			selectedTab = currentTab;
		}
		else {
			tabText = "<a href=\"" + tabHREF + "\" " + target + ">" + tabName + "</a>";
		}

		/*
		if (LayoutPermission.contains(permissionChecker, curLayout.getLayoutId(), curLayout.getOwnerId(), ActionKeys.VIEW)) {
			rows[rowCounter][currentTab] = tabText;
		}
		else {
			rows[rowCounter][currentTab] = null;
		}
		*/
		
		rows[rowCounter][currentTab] = tabText;

		if (currentTab == tabsPerRow - 1) {
			rowCounter++;
			currentTab = 0;
		}
		else {
			currentTab++;
		}
	}

	// Reorder the array so the row with the selected tab is on the bottom row

	if (rows.length > 1) {
		Collections.swap(Arrays.asList(rows), selectedRow, rows.length - 1);
	}
		// Render each row

		for (int i = rows.length - 1; i >= 0; i--) {

				// Render the tabs

				if (totalTabRows > 1 && i == rows.length - 2) {
					%>
					<div class="layout-tab" style="width: 20px;">
						<span style="cursor: pointer;">
							<img align="absmiddle" src="<%= themeDisplay.getPathThemeImage() %>/arrows/01_right.gif" onclick="toggleById('layout-nav-more-menu', true)
								? this.src = '<%= themeDisplay.getPathThemeImage() %>/arrows/01_down.gif'
								: this.src = '<%= themeDisplay.getPathThemeImage() %>/arrows/01_right.gif'" />
						</span>
						<ul id="layout-nav-more-menu" style="position: absolute; display: none;">
					<%
				}

				for (int j = 0; j < rows[i].length; j++) {
					if (rows[i][j] != null) {

						if (i < rows.length - 1) {

							// Create additional tabs menu

							%>

							<li><%= rows[i][j] %></li>

							<%
						}
						else {

							// Render active row

							if (selectable && (i == rows.length - 1) && (j == selectedTab)) {
								isSelectedTab = true;
							}
							else {
								isSelectedTab = false;
							}

							%>

							<div class="layout-tab<%= isSelectedTab ? "-selected" : "" %>"><span><%= rows[i][j] %></span></div>

							<%
						}
					}
				}

				if (totalTabRows > 1) {
					if (i == 0) {
					%>
							</ul>
						</div>
					<%
					}
					else if (i > 0 && i < rows.length - 1){
					%>
						<li class="beta-separator"></li>
					<%
					}
				}
			}
			%>
		</div>

		<div id="layout-global-search">
			<liferay-ui:journal-content-search />
		</div>
	</div>
	<div class="portlet-bottom-decoration-2"><div><div></div></div></div>
</c:if>