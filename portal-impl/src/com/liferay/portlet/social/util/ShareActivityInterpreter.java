/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portlet.social.util;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;

/**
 * <a href="ShareActivityInterpreter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Neel Haldar
 *
 */
public class ShareActivityInterpreter extends BaseSocialActivityInterpreter {

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	protected SocialActivityFeedEntry doInterpret(
			SocialActivity activity, ThemeDisplay themeDisplay)
				throws Exception {

		String creatorUserName = getUserName(
				activity.getUserId(), themeDisplay);
		String receiverUserName = getUserName(
				activity.getReceiverUserId(), themeDisplay);

		String portletTitle = activity.getExtraData();
		User creatorUser = UserLocalServiceUtil.getUserById(
				activity.getUserId());
		User receiverUser = UserLocalServiceUtil.getUserById(
				activity.getReceiverUserId());

		// Link
		StringBuilder sb = new StringBuilder();

		sb.append(themeDisplay.getURLPortal());
		sb.append(themeDisplay.getPathFriendlyURLPublic());
		sb.append(StringPool.SLASH);
		sb.append(creatorUser.getScreenName());
		sb.append("/friends");

		String link = sb.toString();
		link = StringPool.BLANK;

		// Title
		String title = StringPool.BLANK;

		sb = new StringBuilder();

		sb.append("<a href=\"");
		sb.append(themeDisplay.getURLPortal());
		sb.append(themeDisplay.getPathFriendlyURLPublic());
		sb.append(StringPool.SLASH);
		sb.append(creatorUser.getScreenName());
		sb.append("/profile\">");
		sb.append(creatorUserName);
		sb.append("</a>");

		String creatorUserNameURL = sb.toString();

		sb = new StringBuilder();

		sb.append("<a href=\"");
		sb.append(themeDisplay.getURLPortal());
		sb.append(themeDisplay.getPathFriendlyURLPublic());
		sb.append(StringPool.SLASH);
		sb.append(receiverUser.getScreenName());
		sb.append("/profile\">");
		sb.append(receiverUserName);
		sb.append("</a>");

		String receiverUserNameURL = sb.toString();

		title = themeDisplay.translate(
				"request-share-activity-update",
					new Object[]{receiverUserNameURL,
						creatorUserNameURL, portletTitle});

		// Body
		String body = StringPool.BLANK;

		return new SocialActivityFeedEntry(link, title, body);
	}
	
	private static final String[] _CLASS_NAMES = new String[] { 
			ShareActivityInterpreter.class.getName() };	
	
}