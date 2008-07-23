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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.model.BaseSocialRequestInterpreter;
import com.liferay.portlet.social.model.SocialRequest;
import com.liferay.portlet.social.model.SocialRequestFeedEntry;
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ShareRequestInterpreter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Neel Haldar
 *
 */
public class ShareRequestInterpreter extends BaseSocialRequestInterpreter {

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	protected SocialRequestFeedEntry doInterpret(
			SocialRequest request, ThemeDisplay themeDisplay) throws Exception {

		String className = PortalUtil.getClassName(request.getClassNameId());
		if (_CLASS_NAMES[0].equals(className)) {
			String[] extDataArray = StringUtil.split(
					request.getExtraData(),
						ShareRequestKeys.SHARE_EXTRA_DATA_DELIMETER);
			String extData = extDataArray[0];
			return new SocialRequestFeedEntry(
					themeDisplay.translate("request-share-widget"), extData);
		}
		return null;
	}

	protected boolean doProcessConfirmation(
			SocialRequest request, ThemeDisplay themeDisplay) {
		try {
			String className = ShareActivityInterpreter.class.getName();

			String extraData = request.getExtraData();
			String[] extraDataArray = StringUtil.split(
					extraData, ShareRequestKeys.SHARE_EXTRA_DATA_DELIMETER);
			String portletTitle = extraDataArray[1];

			SocialActivityLocalServiceUtil.addActivity(
					request.getUserId(), 0, className, request.getUserId(),
						ShareRequestKeys.SHARE_REQUEST, portletTitle,
							request.getReceiverUserId());
		} catch (Exception e) {
			_log.error(e, e);
		}

		return true;
	}
	
	private static final String[] _CLASS_NAMES = new String[] {
		ShareRequestInterpreter.class.getName() };
	
	private static Log _log = LogFactory.getLog(ShareRequestInterpreter.class);
	
}