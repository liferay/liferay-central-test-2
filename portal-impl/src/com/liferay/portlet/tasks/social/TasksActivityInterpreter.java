/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.tasks.social;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;
import com.liferay.portlet.tasks.model.TasksProposal;
import com.liferay.portlet.tasks.service.TasksProposalLocalServiceUtil;
import com.liferay.portlet.tasks.service.permission.TasksProposalPermission;

/**
 * @author Raymond Augé
 */
public class TasksActivityInterpreter extends BaseSocialActivityInterpreter {

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	protected SocialActivityFeedEntry doInterpret(
			SocialActivity activity, ThemeDisplay themeDisplay)
		throws Exception {

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!TasksProposalPermission.contains(
				permissionChecker, activity.getClassPK(), ActionKeys.VIEW)) {

			return null;
		}

		String creatorUserName = getUserName(
			activity.getUserId(), themeDisplay);
		String receiverUserName = getUserName(
			activity.getReceiverUserId(), themeDisplay);

		int activityType = activity.getType();

		JSONObject extraData = null;

		if (Validator.isNotNull(activity.getExtraData())) {
			extraData = JSONFactoryUtil.createJSONObject(
				activity.getExtraData());
		}

		// Title

		String groupName = StringPool.BLANK;

		if (activity.getGroupId() != themeDisplay.getScopeGroupId()) {
			Group group = GroupLocalServiceUtil.getGroup(activity.getGroupId());

			groupName = group.getDescriptiveName();
		}

		String titlePattern = null;
		Object[] titleArguments = null;

		if (activityType == TasksActivityKeys.ADD_PROPOSAL) {
			titlePattern = "activity-tasks-add-proposal";

			if (Validator.isNotNull(groupName)) {
				titlePattern += "-in";
			}

			titleArguments = new Object[] {
				creatorUserName, HtmlUtil.escape(groupName)
			};
		}
		else if (activityType == TasksActivityKeys.ASSIGN_PROPOSAL) {
			titlePattern = "activity-tasks-assign-proposal";

			if (Validator.isNotNull(groupName)) {
				titlePattern += "-in";
			}

			titleArguments = new Object[] {
				creatorUserName, receiverUserName, HtmlUtil.escape(groupName)
			};
		}
		else if (activityType == TasksActivityKeys.REVIEW_PROPOSAL) {
			titlePattern = "activity-tasks-review-proposal";

			if (Validator.isNotNull(groupName)) {
				titlePattern += "-in";
			}

			titleArguments = new Object[] {
				creatorUserName, receiverUserName, HtmlUtil.escape(groupName)
			};
		}

		String title = themeDisplay.translate(titlePattern, titleArguments);

		// Body

		TasksProposal proposal = TasksProposalLocalServiceUtil.getProposal(
			activity.getClassPK());

		StringBundler sb = new StringBundler();

		sb.append("<b>");
		sb.append(HtmlUtil.escape(proposal.getName()));
		sb.append("</b> (");
		sb.append(
			themeDisplay.translate(
				"model.resource." + proposal.getClassName()));
		sb.append(")<br />");
		sb.append(themeDisplay.translate("description"));
		sb.append(": ");
		sb.append(HtmlUtil.escape(proposal.getDescription()));

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