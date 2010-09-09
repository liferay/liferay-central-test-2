/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;
import com.liferay.portlet.social.model.SocialEquityFeedEntry;
import com.liferay.portlet.social.model.SocialEquityLog;
import com.liferay.portlet.social.model.impl.SocialActivityImpl;
import com.liferay.portlet.social.service.SocialActivityInterpreterLocalServiceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zsolt Berentey
 */
public class SocialEquityActionInterpreter
	extends BaseSocialActivityInterpreter {

	public String[] getClassNames() {
		return null;
	}

	public SocialEquityFeedEntry interpret(
		SocialEquityLog equityLog, ThemeDisplay themeDisplay) {

		return interpret(equityLog, 1, themeDisplay);
	}

	public List<SocialEquityFeedEntry> interpret(
		List<SocialEquityLog> equityLogs, ThemeDisplay themeDisplay) {

		List<SocialEquityFeedEntry> entries =
			new ArrayList<SocialEquityFeedEntry>();

		Map<String, List<SocialEquityLog>> guestActions =
			new HashMap<String, List<SocialEquityLog>>();

		for (SocialEquityLog equityLog : equityLogs) {
			if (isDefaultUser(equityLog.getUserId())) {
				String key =
					equityLog.getActionId() + "." + equityLog.getAssetEntryId();

				if (!guestActions.containsKey(key)) {
					guestActions.put(key, new ArrayList<SocialEquityLog>());
				}

				guestActions.get(key).add(equityLog);
			}
			else {
				entries.add(interpret(equityLog, themeDisplay));
			}
		}

		// Guest actions

		for (List<SocialEquityLog> guestLogs : guestActions.values()) {
			entries.add(interpretAggregate(guestLogs, themeDisplay));
		}

		return entries;

	}

	protected SocialEquityFeedEntry addEquityInformation(
		SocialActivityFeedEntry feedEntry, SocialEquityLog equityLog,
		int count, ThemeDisplay themeDisplay) {

		if (feedEntry == null) {
			return null;
		}

		String[] messages = new String[] {
			"contribution-equity-awarded", "participation-equity-awarded"
		};

		SocialEquityFeedEntry equityFeedEntry = new SocialEquityFeedEntry(
			feedEntry);

		String equityInfo = themeDisplay.translate(
			messages[equityLog.getType() - 1],
			new Object[] {equityLog.getValue(), equityLog.getLifespan()});

		if (count > 1) {
			String countMessage =
				" (" + themeDisplay.translate("x-times", count) + ")";

			equityFeedEntry.setTitle(feedEntry.getTitle() + countMessage);
		}

		equityFeedEntry.setEquityInfo(equityInfo);

		return equityFeedEntry;
	}

	protected SocialActivityFeedEntry doInterpret(
		SocialActivity activity, ThemeDisplay themeDisplay) throws Exception {

		return null;
	}

	protected SocialActivityFeedEntry doInterpret(
			SocialEquityLog equityLog, ThemeDisplay themeDisplay)
		throws Exception {

		String creatorUserName = null;

		if (isDefaultUser(equityLog.getUserId())) {
			creatorUserName = themeDisplay.translate("anonymous");
		}
		else {
			creatorUserName = getUserName(equityLog.getUserId(), themeDisplay);
		}

		AssetEntry entry = AssetEntryLocalServiceUtil.getEntry(
			equityLog.getAssetEntryId());

		// Title

		String titlePattern = null;

		if (equityLog.getActionId().equals(ActionKeys.VIEW)) {
			titlePattern = "activity-social-equity-action-view";
		}
		else if (equityLog.getActionId().equals(ActionKeys.ADD_DISCUSSION)) {
			titlePattern = "activity-social-equity-action-comment";
		}
		else if (equityLog.getActionId().equals(ActionKeys.ADD_VOTE)) {
			titlePattern = "activity-social-equity-action-vote";
		}
		else {
			titlePattern = "activity-social-equity-action-default";
		}

		String entryTitle = HtmlUtil.escape(cleanContent(entry.getTitle()));

		String action = ResourceActionsUtil.getAction(
			themeDisplay.getLocale(), equityLog.getActionId());

		String className = ResourceActionsUtil.getModelResource(
			themeDisplay.getLocale(), entry.getClassName());

		Object[] titleArguments = new Object[] {
			creatorUserName, className.toLowerCase(), entryTitle, action
		};

		String title = themeDisplay.translate(titlePattern, titleArguments);

		// Body

		String body = StringPool.BLANK;

		return new SocialActivityFeedEntry(null, title, body);

	}

	protected SocialEquityFeedEntry interpret(
		SocialEquityLog equityLog, int count, ThemeDisplay themeDisplay) {

		Integer socialActivity =
			SocialActivityToEquityActionMapper.getSocialActivity(
				equityLog.getActionId());

		SocialActivityFeedEntry feedEntry = null;

		try {

			if (socialActivity == null) {
				feedEntry = doInterpret(equityLog, themeDisplay);
			}
			else {
				AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
					equityLog.getAssetEntryId());

				SocialActivity activity = new SocialActivityImpl();

				activity.setCompanyId(equityLog.getCompanyId());
				activity.setGroupId(equityLog.getGroupId());
				activity.setUserId(equityLog.getUserId());
				activity.setClassNameId(assetEntry.getClassNameId());
				activity.setClassPK(assetEntry.getClassPK());
				activity.setType(socialActivity);

				feedEntry = SocialActivityInterpreterLocalServiceUtil.interpret(
					activity, themeDisplay);
			}

			return addEquityInformation(feedEntry, equityLog, count,
				themeDisplay);

		}
		catch (Exception e) {
			_log.error("Unable to interpret equity action", e);
		}

		return null;
	}

	protected SocialEquityFeedEntry interpretAggregate(
		List<SocialEquityLog> equityLogs, ThemeDisplay themeDisplay) {

		return interpret(equityLogs.get(0), equityLogs.size(), themeDisplay);

	}

	protected boolean isDefaultUser(long userId) {
		try {
			User user = UserLocalServiceUtil.getUserById(userId);

			return user.isDefaultUser();
		}
		catch (Exception e) {
			return true;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SocialEquityActionInterpreter.class);

}