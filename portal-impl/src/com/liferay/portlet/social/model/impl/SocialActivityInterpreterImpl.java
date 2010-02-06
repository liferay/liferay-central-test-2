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
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;
import com.liferay.portlet.social.model.SocialActivityInterpreter;

import java.util.HashSet;
import java.util.Set;

/**
 * <a href="SocialActivityInterpreterImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class SocialActivityInterpreterImpl
	implements SocialActivityInterpreter {

	public SocialActivityInterpreterImpl(
		String portletId, SocialActivityInterpreter activityInterpreter) {

		_portletId = portletId;
		_activityInterpreter = activityInterpreter;

		String[] classNames = _activityInterpreter.getClassNames();

		for (String className : classNames) {
			_classNames.add(className);
		}
	}

	public String[] getClassNames() {
		return _activityInterpreter.getClassNames();
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

	public SocialActivityFeedEntry interpret(
		SocialActivity activity, ThemeDisplay themeDisplay) {

		return _activityInterpreter.interpret(
			activity, themeDisplay);
	}

	private String _portletId;
	private SocialActivityInterpreter _activityInterpreter;
	private Set<String> _classNames = new HashSet<String>();

}