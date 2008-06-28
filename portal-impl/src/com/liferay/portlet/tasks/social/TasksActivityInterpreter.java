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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;
import com.liferay.portlet.tasks.model.TasksProposal;
import com.liferay.portlet.tasks.service.TasksProposalLocalServiceUtil;

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

	protected SocialActivityFeedEntry doInterpret(
			SocialActivity activity, ThemeDisplay themeDisplay)
		throws Exception {

		String creatorUserName = getUserName(
			activity.getUserId(), themeDisplay);
		String receiverUserName = getUserName(
			activity.getReceiverUserId(), themeDisplay);

		int activityType = activity.getType();

		JSONObject extraData = null;

		if (Validator.isNotNull(activity.getExtraData())) {
			extraData = new JSONObject(activity.getExtraData());
		}

		// Title

		String title = StringPool.BLANK;

		if (activityType == TasksActivityKeys.ADD_PROPOSAL) {
			title = themeDisplay.translate(
				"activity-tasks-add-proposal", creatorUserName);
		}
		else if (activityType == TasksActivityKeys.ASSIGN_PROPOSAL) {
			title = themeDisplay.translate(
				"activity-tasks-assign-proposal",
				new Object[] {creatorUserName, receiverUserName});
		}
		else if (activityType == TasksActivityKeys.REVIEW_PROPOSAL) {
			title = themeDisplay.translate(
				"activity-tasks-review-proposal",
				new Object[] {creatorUserName, receiverUserName});
		}

		// Body

		TasksProposal proposal = TasksProposalLocalServiceUtil.getProposal(
			activity.getClassPK());

		StringBuilder sb = new StringBuilder();

		sb.append("<b>");
		sb.append(proposal.getName());
		sb.append("</b> (");
		sb.append(
			themeDisplay.translate(
				"model.resource." + proposal.getClassName()));
		sb.append(")<br />");
		sb.append(themeDisplay.translate("description"));
		sb.append(": ");
		sb.append(proposal.getDescription());

		if (activityType != TasksActivityKeys.ADD_PROPOSAL) {
			int stage = extraData.getInt("stage");
			boolean completed = extraData.getBoolean("completed");
			boolean rejected = extraData.getBoolean("rejected");

			sb.append("<br />");
			sb.append(themeDisplay.translate("stage"));
			sb.append(": ");
			sb.append(stage);
			sb.append("<br />");
			sb.append(themeDisplay.translate("status"));
			sb.append(": ");

			if (completed && rejected) {
				sb.append(themeDisplay.translate("rejected"));
			}
			else if (completed && !rejected) {
				sb.append(themeDisplay.translate("approved"));
			}
			else {
				sb.append(themeDisplay.translate("awaiting-approval"));
			}
		}

		String body = sb.toString();

		return new SocialActivityFeedEntry(title, body);
	}

	private static final String[] _CLASS_NAMES = new String[] {
		TasksProposal.class.getName()
	};

}