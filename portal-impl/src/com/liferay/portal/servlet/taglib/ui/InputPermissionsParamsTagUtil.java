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

package com.liferay.portal.servlet.taglib.ui;

import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import java.util.List;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * <a href="InputPermissionsParamsTagUtil.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Chan
 * @author Jorge Ferrer
 */
public class InputPermissionsParamsTagUtil {

	public static void doEndTag(String modelName, PageContext pageContext)
		throws JspException {

		try {
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			RenderResponse renderResponse =
				(RenderResponse)request.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE);

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			Layout layout = themeDisplay.getLayout();

			Group group = themeDisplay.getScopeGroup();
			Group layoutGroup = layout.getGroup();

			List<String> supportedActions =
				ResourceActionsUtil.getModelResourceActions(modelName);
			List<String> communityDefaultActions =
				ResourceActionsUtil.getModelResourceCommunityDefaultActions(
					modelName);
			List<String> guestDefaultActions =
				ResourceActionsUtil.getModelResourceGuestDefaultActions(
					modelName);
			List<String> guestUnsupportedActions =
				ResourceActionsUtil.getModelResourceGuestUnsupportedActions(
					modelName);

			StringBundler sb = new StringBundler();

			for (int i = 0; i < supportedActions.size(); i++) {
				String action = supportedActions.get(i);

				boolean communityChecked = communityDefaultActions.contains(
					action);
				boolean guestChecked = guestDefaultActions.contains(action);
				boolean guestDisabled = guestUnsupportedActions.contains(
					action);

				if (guestDisabled) {
					guestChecked = false;
				}

				if (group.isCommunity() || group.isOrganization()) {
					if (communityChecked) {
						sb.append(StringPool.AMPERSAND);
						sb.append(renderResponse.getNamespace());
						sb.append("communityPermissions=");
						sb.append(action);
					}
				}

				if (guestChecked) {
					sb.append(StringPool.AMPERSAND);
					sb.append(renderResponse.getNamespace());
					sb.append("guestPermissions=");
					sb.append(action);
				}
			}

			boolean inputPermissionsPublic = false;

			if (layoutGroup.getName().equals(GroupConstants.CONTROL_PANEL)) {
				if (!group.hasPrivateLayouts()) {
					inputPermissionsPublic = true;
				}
			}
			else if (layout.isPublicLayout()) {
				inputPermissionsPublic = true;
			}

			if (inputPermissionsPublic) {
				sb.append(StringPool.AMPERSAND);
				sb.append(renderResponse.getNamespace());
				sb.append("inputPermissionsPublic=1");
			}

			pageContext.getOut().print(sb.toString());
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

}