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

<script src="<%= themeDisplay.getPathJavaScript() %>/tree.js" type="text/javascript"></script>

<script type="text/javascript">
	var layoutsIcons = new Array();

	layoutsIcons[0] = "<%= themeDisplay.getPathThemeImages() %>/trees/root.png";
	layoutsIcons[1] = "<%= themeDisplay.getPathThemeImages() %>/trees/spacer.png";
	layoutsIcons[2] = "<%= themeDisplay.getPathThemeImages() %>/trees/line.png";
	layoutsIcons[3] = "<%= themeDisplay.getPathThemeImages() %>/trees/join.png";
	layoutsIcons[4] = "<%= themeDisplay.getPathThemeImages() %>/trees/join_bottom.png";
	layoutsIcons[5] = "<%= themeDisplay.getPathThemeImages() %>/trees/minus.png";
	layoutsIcons[6] = "<%= themeDisplay.getPathThemeImages() %>/trees/minus_bottom.png";
	layoutsIcons[7] = "<%= themeDisplay.getPathThemeImages() %>/trees/plus.png";
	layoutsIcons[8] = "<%= themeDisplay.getPathThemeImages() %>/trees/plus_bottom.png";
	//layoutsIcons[9] = "<%= themeDisplay.getPathThemeImages() %>/trees/folder.png";
	//layoutsIcons[10] = "<%= themeDisplay.getPathThemeImages() %>/trees/folder_open.png";
	layoutsIcons[9] = "<%= themeDisplay.getPathThemeImages() %>/trees/page.png";
	layoutsIcons[10] = "<%= themeDisplay.getPathThemeImages() %>/trees/page.png";
	layoutsIcons[11] = "<%= themeDisplay.getPathThemeImages() %>/trees/page.png";

	// id | parentId | ls | obj id | name | img | href

	var layoutsArray = new Array();

	<%
	for (int i = 0; i < layoutList.size(); i++) {
		String layoutDesc = (String)layoutList.get(i);

		String[] nodeValues = StringUtil.split(layoutDesc, "|");

		String objId = nodeValues[3];
		String name = nodeValues[4];

		// Set a better name and remove depth because href should be in the 5th
		// position

		name = "<nobr>" + name + "</nobr>";

		if (selPlid.equals(objId)) {
			name = "<b>" + name + "</b>";
		}

		nodeValues[4] = name;

		int depth = 0;

		if (i != 0) {
			depth = GetterUtil.getInteger(nodeValues[6]);
			nodeValues[6] = "";
		}

		layoutDesc = StringUtil.merge(nodeValues, "|");

		if (i != 0) {
			layoutDesc = layoutDesc.substring(0, layoutDesc.length() - 1);
		}
	%>

		layoutsArray[<%= i %>] = "<%= UnicodeFormatter.toString(layoutDesc) %>|javascript: self.location = '<%= Http.encodeURL(portletURL.toString()) %>&<portlet:namespace />selPlid=<%= objId %>';|<%= depth %>";

	<%
	}
	%>

</script>