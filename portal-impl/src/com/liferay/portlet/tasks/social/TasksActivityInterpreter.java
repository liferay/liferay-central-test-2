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

package com.liferay.portlet.tasks.social;

import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;
import com.liferay.portlet.tasks.model.TasksProposal;
import com.liferay.portlet.tasks.service.TasksProposalLocalServiceUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

/**
 * <a href="TasksActivityInterpreter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class TasksActivityInterpreter extends BaseSocialActivityInterpreter {

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	public SocialActivityFeedEntry interpret(
		SocialActivity activity, ThemeDisplay themeDisplay) {

		try {
			return doInterpret(activity, themeDisplay);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e);
			}
		}

		return null;
	}

	protected SocialActivityFeedEntry doInterpret(
			SocialActivity activity, ThemeDisplay themeDisplay)
		throws Exception {

		String creatorUserName = getUserName(
			activity.getUserId(), themeDisplay);
		String receiverUserName = getUserName(
			activity.getReceiverUserId(), themeDisplay);

		String type = activity.getType();

		// Title

		String title = StringPool.BLANK;

		if (type.equals(TasksActivityKeys.ASSIGN)) {
			title = themeDisplay.translate(
				"activity-tasks-x-assigned-proposal-to-x-for-review",
				new Object[] {creatorUserName, receiverUserName});
		}
		else if (type.equals(TasksActivityKeys.PROPOSE)) {
			title = themeDisplay.translate(
				"activity-tasks-x-created-proposal", creatorUserName);
		}
		else if (type.equals(TasksActivityKeys.REVIEW)) {
			title = themeDisplay.translate(
				"activity-tasks-x-reviewed-proposal-from-x",
				new Object[] {creatorUserName, receiverUserName});
		}

		// Body

		TasksProposal proposal = TasksProposalLocalServiceUtil.getProposal(
			activity.getClassPK());

		StringMaker sm = new StringMaker();

		sm.append("<b>");
		sm.append(proposal.getName());
		sm.append("</b> (");
		sm.append(
			themeDisplay.translate(
				"model.resource." + proposal.getClassName()));
		sm.append(")<br />");
		sm.append(themeDisplay.translate("description"));
		sm.append(": ");
		sm.append(proposal.getDescription());

		if (!activity.equals(TasksActivityKeys.PROPOSE)) {
			JSONObject extraData = new JSONObject(activity.getExtraData());

			int stage = extraData.getInt("stage");
			boolean completed = extraData.getBoolean("completed");
			boolean rejected = extraData.getBoolean("rejected");

			sm.append("<br />");
			sm.append(themeDisplay.translate("stage"));
			sm.append(": ");
			sm.append(stage);
			sm.append("<br />");
			sm.append(themeDisplay.translate("status"));
			sm.append(": ");

			if (completed && rejected) {
				sm.append(themeDisplay.translate("rejected"));
			}
			else if (completed && !rejected) {
				sm.append(themeDisplay.translate("approved"));
			}
			else {
				sm.append(themeDisplay.translate("awaiting-approval"));
			}
		}

		String body = sm.toString();

		return new SocialActivityFeedEntry(title, body);
	}

	private static final String[] _CLASS_NAMES = new String[] {
		TasksProposal.class.getName()
	};

	private static Log _log = LogFactory.getLog(TasksActivityInterpreter.class);

}