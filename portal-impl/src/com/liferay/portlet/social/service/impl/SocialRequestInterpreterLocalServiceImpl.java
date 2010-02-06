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

package com.liferay.portlet.social.service.impl;

import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.model.SocialRequest;
import com.liferay.portlet.social.model.SocialRequestFeedEntry;
import com.liferay.portlet.social.model.SocialRequestInterpreter;
import com.liferay.portlet.social.model.impl.SocialRequestInterpreterImpl;
import com.liferay.portlet.social.service.base.SocialRequestInterpreterLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="SocialRequestInterpreterLocalServiceImpl.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SocialRequestInterpreterLocalServiceImpl
	extends SocialRequestInterpreterLocalServiceBaseImpl {

	public void addRequestInterpreter(
		SocialRequestInterpreter requestInterpreter) {

		_requestInterpreters.add(requestInterpreter);
	}

	public void deleteRequestInterpreter(
		SocialRequestInterpreter requestInterpreter) {

		if (requestInterpreter != null) {
			_requestInterpreters.remove(requestInterpreter);
		}
	}

	public SocialRequestFeedEntry interpret(
		SocialRequest request, ThemeDisplay themeDisplay) {

		String className = PortalUtil.getClassName(request.getClassNameId());

		for (int i = 0; i < _requestInterpreters.size(); i++) {
			SocialRequestInterpreterImpl requestInterpreter =
				(SocialRequestInterpreterImpl)_requestInterpreters.get(i);

			if (requestInterpreter.hasClassName(className)) {
				SocialRequestFeedEntry requestFeedEntry =
					requestInterpreter.interpret(request, themeDisplay);

				if (requestFeedEntry != null) {
					requestFeedEntry.setPortletId(
						requestInterpreter.getPortletId());

					return requestFeedEntry;
				}
			}
		}

		return null;
	}

	public void processConfirmation(
		SocialRequest request, ThemeDisplay themeDisplay) {

		String className = PortalUtil.getClassName(request.getClassNameId());

		for (int i = 0; i < _requestInterpreters.size(); i++) {
			SocialRequestInterpreterImpl requestInterpreter =
				(SocialRequestInterpreterImpl)_requestInterpreters.get(i);

			if (requestInterpreter.hasClassName(className)) {
				boolean value = requestInterpreter.processConfirmation(
					request, themeDisplay);

				if (value) {
					return;
				}
			}
		}
	}

	public void processRejection(
		SocialRequest request, ThemeDisplay themeDisplay) {

		String className = PortalUtil.getClassName(request.getClassNameId());

		for (int i = 0; i < _requestInterpreters.size(); i++) {
			SocialRequestInterpreterImpl requestInterpreter =
				(SocialRequestInterpreterImpl)_requestInterpreters.get(i);

			if (requestInterpreter.hasClassName(className)) {
				boolean value = requestInterpreter.processRejection(
					request, themeDisplay);

				if (value) {
					return;
				}
			}
		}
	}

	private List<SocialRequestInterpreter> _requestInterpreters =
		new ArrayList<SocialRequestInterpreter>();

}