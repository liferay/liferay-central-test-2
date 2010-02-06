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

package com.liferay.portlet.social.model.impl;

import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.social.model.SocialRequest;
import com.liferay.portlet.social.model.SocialRequestFeedEntry;
import com.liferay.portlet.social.model.SocialRequestInterpreter;

import java.util.HashSet;
import java.util.Set;

/**
 * <a href="SocialRequestInterpreterImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class SocialRequestInterpreterImpl
	implements SocialRequestInterpreter {

	public SocialRequestInterpreterImpl(
		String portletId, SocialRequestInterpreter requestInterpreter) {

		_portletId = portletId;
		_requestInterpreter = requestInterpreter;

		String[] classNames = _requestInterpreter.getClassNames();

		for (String className : classNames) {
			_classNames.add(className);
		}
	}

	public String[] getClassNames() {
		return _requestInterpreter.getClassNames();
	}

	public String getPortletId() {
		return _portletId;
	}

	public boolean hasClassName(String className) {
		if (_classNames.contains(className)) {
			return true;
		}
		else {
			return false;
		}
	}

	public SocialRequestFeedEntry interpret(
		SocialRequest request, ThemeDisplay themeDisplay) {

		return _requestInterpreter.interpret(
			request, themeDisplay);
	}

	public boolean processConfirmation(
		SocialRequest request, ThemeDisplay themeDisplay) {

		return _requestInterpreter.processConfirmation(
			request, themeDisplay);
	}

	public boolean processRejection(
		SocialRequest request, ThemeDisplay themeDisplay) {

		return _requestInterpreter.processRejection(request, themeDisplay);
	}

	private String _portletId;
	private SocialRequestInterpreter _requestInterpreter;
	private Set<String> _classNames = new HashSet<String>();

}