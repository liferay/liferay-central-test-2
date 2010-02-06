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
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.model.impl.SocialActivityInterpreterImpl;
import com.liferay.portlet.social.service.base.SocialActivityInterpreterLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="SocialActivityInterpreterLocalServiceImpl.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SocialActivityInterpreterLocalServiceImpl
	extends SocialActivityInterpreterLocalServiceBaseImpl {

	public void addActivityInterpreter(
		SocialActivityInterpreter activityInterpreter) {

		_activityInterpreters.add(activityInterpreter);
	}

	public void deleteActivityInterpreter(
		SocialActivityInterpreter activityInterpreter) {

		if (activityInterpreter != null) {
			_activityInterpreters.remove(activityInterpreter);
		}
	}

	public SocialActivityFeedEntry interpret(
		SocialActivity activity, ThemeDisplay themeDisplay) {

		if (activity.getMirrorActivityId() > 0) {
			SocialActivity mirrorActivity = null;

			try {
				mirrorActivity = socialActivityLocalService.getActivity(
					activity.getMirrorActivityId());
			}
			catch (Exception e) {
			}

			if (mirrorActivity != null) {
				activity = mirrorActivity;
			}
		}

		String className = PortalUtil.getClassName(activity.getClassNameId());

		for (int i = 0; i < _activityInterpreters.size(); i++) {
			SocialActivityInterpreterImpl activityInterpreter =
				(SocialActivityInterpreterImpl)_activityInterpreters.get(i);

			if (activityInterpreter.hasClassName(className)) {
				SocialActivityFeedEntry activityFeedEntry =
					activityInterpreter.interpret(activity, themeDisplay);

				if (activityFeedEntry != null) {
					activityFeedEntry.setPortletId(
						activityInterpreter.getPortletId());

					return activityFeedEntry;
				}
			}
		}

		return null;
	}

	private List<SocialActivityInterpreter> _activityInterpreters =
		new ArrayList<SocialActivityInterpreter>();

}