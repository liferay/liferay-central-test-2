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

<script src="<%= themeDisplay.getPathJavaScript() %>/liferay/tree.js" type="text/javascript"></script>

<script type="text/javascript">
	layoutIcons = {
		folder: '<%= themeDisplay.getPathThemeImages() %>/trees/page.png',
		folderOpen: '<%= themeDisplay.getPathThemeImages() %>/trees/page.png',
		join: '<%= themeDisplay.getPathThemeImages() %>/trees/join.png',
		joinBottom: '<%= themeDisplay.getPathThemeImages() %>/trees/join_bottom.png',
		line: '<%= themeDisplay.getPathThemeImages() %>/trees/line.png',
		minus: '<%= themeDisplay.getPathThemeImages() %>/trees/minus.png',
		minusBottom: '<%= themeDisplay.getPathThemeImages() %>/trees/minus_bottom.png',
		page: '<%= themeDisplay.getPathThemeImages() %>/trees/page.png',
		plus: '<%= themeDisplay.getPathThemeImages() %>/trees/plus.png',
		plusBottom: '<%= themeDisplay.getPathThemeImages() %>/trees/plus_bottom.png',
		root: '<%= themeDisplay.getPathThemeImages() %>/trees/root.png',
		spacer: '<%= themeDisplay.getPathThemeImages() %>/trees/spacer.png'
	};

	var layoutArray = [];

	<%
	for (int i = 0; i < layoutList.size(); i++) {
		String layoutDesc = (String)layoutList.get(i);

		String[] nodeValues = StringUtil.split(layoutDesc, "|");

		String objId = nodeValues[3];
		String name = nodeValues[4];

		// Set a better name and remove depth because href should be in the 5th
		// position

		if (selPlid.equals(objId)) {
			name = "<strong>" + name + "</strong>";
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
	
		layoutArray[<%= i %>] = {
			depth: '<%= depth %>',
			id: '<%= nodeValues[0]  %>',
			img: '<%= name %>',
			ls: '<%= nodeValues[2] %>',
			href: 'javascript: self.location = \'<%= Http.encodeURL(portletURL.toString()) %>&<portlet:namespace />selPlid=<%= objId %>\';',
			parentId: '<%= nodeValues[1] %>',
			objId: '<%= nodeValues[3] %>',
			name: '<%= nodeValues[4] %>'
		};

	<%
	}
	%>

</script>