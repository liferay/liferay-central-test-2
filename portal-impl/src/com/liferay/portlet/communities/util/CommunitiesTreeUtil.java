/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.communities.util;

import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Arrays;
import java.util.List;

/**
 * <a href="CommunitiesTreeUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Eduardo Lundgren
 *
 */
public class CommunitiesTreeUtil {

	public static void buildLayoutsTreeHTML(long groupId,
			boolean privateLayout, long parentLayoutId, LongWrapper nodeId,
			long[] openNodes, boolean selectableTree, long[] selectedNodes,
			String portletURL, ThemeDisplay themeDisplay, StringBuilder sb,
			boolean ajaxRender)
		throws Exception {

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			groupId, privateLayout, parentLayoutId);

		if (layouts.size() == 0) {
			return;
		}

		boolean nodeOpen = false;

		if (!ajaxRender) {
			sb.append("<ul class='has-children ");

			if ((Arrays.binarySearch(openNodes, nodeId.getValue()) >= 0) ||
				nodeId.getValue() == 1) {

				nodeOpen = true;

				sb.append("node-open ");
			}

			sb.append("'>");
		}

		for (Layout layout : layouts) {
			nodeId.increment();

			List<Layout> childLayouts = layout.getChildren();

			String image = "spacer.png";

			if (nodeOpen || ajaxRender) {

				sb.append("<li branchid='");
				sb.append(layout.getPlid());
				sb.append("' layoutid='");
				sb.append(layout.getLayoutId());
				sb.append("' privateLayout='");
				sb.append(layout.isPrivateLayout());
				sb.append("' class='tree-item ");

				if (childLayouts.size() > 0) {
					image = "plus.png";

					sb.append("has-children ");

					if (Arrays.binarySearch(
						openNodes, nodeId.getValue()) >= 0) {

						image = "minus.png";

						sb.append("node-open ");
					}
				}

				sb.append("' nodeid='");
				sb.append(nodeId.getValue());
				sb.append("'><img class='expand-image' src='");
				sb.append(themeDisplay.getPathThemeImages() + "/trees/" + image);
				sb.append("'/>");

				if (selectableTree && Validator.isNotNull(selectedNodes)) {
					sb.append("<img class='select-state' src='");

					if (Arrays.binarySearch(
						selectedNodes, layout.getPlid()) >= 0) {

						sb.append(themeDisplay.getPathThemeImages() +
							"/trees/checked.png");
					}
					else {
						sb.append(themeDisplay.getPathThemeImages() +
							"/trees/checkbox.png");
					}

					sb.append("'/>");
				}

				sb.append("<a href='");
				sb.append(portletURL);
				sb.append(StringPool.AMPERSAND);
				sb.append(portletDisplay.getNamespace());
				sb.append("selPlid=");
				sb.append(layout.getPlid());
				sb.append("'><img src='");
				sb.append(themeDisplay.getPathThemeImages() + "/trees/page.png");
				sb.append("'/><span>");
				sb.append(layout.getName(themeDisplay.getLocale()));
				sb.append("</span></a>");

				CommunitiesTreeUtil.buildLayoutsTreeHTML(
					groupId, privateLayout, layout.getLayoutId(), nodeId,
					openNodes, selectableTree, selectedNodes, portletURL,
					themeDisplay, sb, false);

				sb.append("</li>");
			}
		}

		if (!ajaxRender) {
			sb.append("</ul>");
		}
	}
}