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

package com.liferay.portlet.social.service.impl;

import com.liferay.ibm.icu.util.Calendar;
import com.liferay.ibm.icu.util.GregorianCalendar;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.cache.CounterCacheAdvice;
import com.liferay.portal.kernel.cache.BufferedCounter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.social.NoSuchEquityAssetEntryException;
import com.liferay.portlet.social.model.SocialEquityAssetEntry;
import com.liferay.portlet.social.model.SocialEquityLog;
import com.liferay.portlet.social.model.SocialEquitySetting;
import com.liferay.portlet.social.model.SocialEquitySettingConstants;
import com.liferay.portlet.social.service.base.SocialEquityLogLocalServiceBaseImpl;
import com.liferay.util.SocialEquity;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Date;
import java.util.List;

/**
 * <a href="SocialEquityLogLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Zsolt Berentey
 * @author Brian Wing Shun Chan
 */
public class SocialEquityLogLocalServiceImpl
	extends SocialEquityLogLocalServiceBaseImpl {

	public void addEquityLogs(
			long userId, long assetEntryId, String actionId)
		throws PortalException, SystemException {

		if (!PropsValues.SOCIAL_EQUITY_EQUITY_LOG_ENABLED) {
			return;
		}

		List<SocialEquityLog> equityLogs =
			socialEquityLogPersistence.findByU_AEI_A_A(
				userId, assetEntryId, actionId, true);

		if (!equityLogs.isEmpty()) {
			return;
		}

		User user = userPersistence.findByPrimaryKey(userId);

		AssetEntry assetEntry = assetEntryPersistence.findByPrimaryKey(
			assetEntryId);

		User assetEntryUser = null;

		try {
			assetEntryUser = userPersistence.findByPrimaryKey(
				assetEntry.getUserId());
		}
		catch (NoSuchUserException nsue) {
		}

		List<SocialEquitySetting> equitySettings =
			socialEquitySettingLocalService.getEquitySettings(
				assetEntry.getGroupId(), assetEntry.getClassNameId(), actionId);

		for (SocialEquitySetting equitySetting : equitySettings) {
			addEquityLog(user, assetEntry, assetEntryUser, equitySetting);
		}
	}

	public void addEquityLogs(
			long userId, String className, long classPK, String actionId)
		throws PortalException, SystemException {

		if (!PropsValues.SOCIAL_EQUITY_EQUITY_LOG_ENABLED) {
			return;
		}

		AssetEntry assetEntry = null;

		try {
			assetEntry = assetEntryLocalService.getEntry(
				className, classPK);
		}
		catch (NoSuchEntryException nsee) {
			return;
		}

		addEquityLogs(userId, assetEntry.getEntryId(), actionId);
	}

	public void checkEquityLogs() throws SystemException {
		int validity = getEquityDate();

		if (!PropsValues.SOCIAL_EQUITY_EQUITY_LOG_ENABLED) {
			return;
		}

		runCheckSQL(_CHECK_SOCIAL_EQUITY_ASSET_ENTRY_IQ, validity);

		assetEntryPersistence.clearCache();

		runCheckSQL(_CHECK_SOCIAL_EQUITY_USER, validity);
		runCheckSQL(_CHECK_SOCIAL_EQUITY_USER_CQ, validity);
		runCheckSQL(_CHECK_SOCIAL_EQUITY_USER_PQ, validity);

		userPersistence.clearCache();

		runCheckSQL(_CHECK_SOCIAL_EQUITY_LOGS, validity);

		socialEquityLogPersistence.clearCache();
	}

	public void deactivateEquityLogs(long assetEntryId)	throws SystemException {
		if (!PropsValues.SOCIAL_EQUITY_EQUITY_LOG_ENABLED) {
			return;
		}

		SocialEquityAssetEntry equityAssetEntry = null;

		try {
			equityAssetEntry =
				socialEquityAssetEntryPersistence.findByAssetEntryId(
					assetEntryId);

			socialEquityAssetEntryPersistence.removeByAssetEntryId(
				assetEntryId);
		}
		catch (NoSuchEquityAssetEntryException nseaee) {
			return;
		}

		User user = null;

		try {
			user = userPersistence.findByPrimaryKey(
				equityAssetEntry.getUserId());

			if (!user.isDefaultUser()) {
				getSocialEquityLogLocalService().incrementSocialEquityUser_CQ(
					equityAssetEntry.getGroupId() + StringPool.POUND +
					user.getUserId(), -equityAssetEntry.getInformationK(),
					-equityAssetEntry.getInformationB());

				getSocialEquityLogLocalService().incrementUser_CQ(
					user.getUserId(), -equityAssetEntry.getInformationK(),
					-equityAssetEntry.getInformationB());
			}
		}
		catch (NoSuchUserException nsue) {
		}

		List<SocialEquityLog> equityLogs =
			socialEquityLogPersistence.findByAEI_T_A(
				assetEntryId, SocialEquitySettingConstants.TYPE_INFORMATION,
				true);

		for (SocialEquityLog equityLog : equityLogs) {
			equityLog.setActive(false);

			socialEquityLogPersistence.update(equityLog, false);
		}
	}

	@BufferedCounter
	public void incrementSocialEquityAssetEntry_IQ(
		long assetEntryId, double k, double b) {
	}

	@BufferedCounter
	public void incrementSocialEquityUser_CQ(String id, double k, double b) {
	}

	@BufferedCounter
	public void incrementSocialEquityUser_PQ(String id, double k, double b) {
	}

	@BufferedCounter(flushMethod="none")
	public void incrementUser_CQ(long userId, double k, double b) {
	}

	@BufferedCounter(flushMethod="none")
	public void incrementUser_PQ(long userId, double k, double b) {
	}

	public void updateSocialEquityAssetEntry_IQ(
			long assetEntryId, double k, double b)
		throws SystemException {

		AssetEntry assetEntry = assetEntryPersistence.fetchByPrimaryKey(
			assetEntryId);

		assetEntry.setSocialInformationEquity(calculateEquity(
			getEquityDate(), k, b));

		String sql = CustomSQLUtil.get(_UPDATE_SOCIAL_EQUITY_ASSET_ENTRY_IQ);

		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$ASSET_ENTRY_ID$]",
				"[$INFORMATION_B$]",
				"[$INFORMATION_K$]"
			},
			new String[] {
				String.valueOf(assetEntryId),
				String.valueOf(b),
				String.valueOf(k)
			});

		runSQL(sql);
	}

	public void updateSocialEquityUser_CQ(String id, double k, double b)
		throws PortalException, SystemException {

		long groupId = Long.valueOf(
			StringUtil.extractFirst(id, StringPool.POUND));

		long userId = Long.valueOf(
			StringUtil.extractLast(id, StringPool.POUND));

		User user = userLocalService.getUser(userId);

		user.getSocialContributionEquity();

		int count = socialEquityUserPersistence.countByG_U(
			groupId, userId);

		if (count == 0) {
			addSocialEquityUser(groupId, user);
		}

		user.setSocialContributionEquity(calculateEquity(
			getEquityDate(), k, b));

		SocialEquity userContributionEquity =
			(SocialEquity)CounterCacheAdvice.getCounterValue(
				"com.liferay.portlet.social.service." +
				"SocialEquityLogLocalService#updateUser_CQ#none", userId);

		userContributionEquity.subtract(new SocialEquity(k, b));

		String sql = CustomSQLUtil.get(_UPDATE_SOCIAL_EQUITY_USER_CQ);

		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$GROUP_ID$]",
				"[$USER_ID$]",
				"[$CONTRIBUTION_B$]",
				"[$CONTRIBUTION_K$]"
			},
			new String[] {
				String.valueOf(groupId),
				String.valueOf(userId),
				String.valueOf(b),
				String.valueOf(k)
			});

		runSQL(sql);
	}

	public void updateSocialEquityUser_PQ(String id, double k, double b)
		throws PortalException, SystemException {

		long groupId = Long.valueOf(
				StringUtil.extractFirst(id, StringPool.POUND));

		long userId = Long.valueOf(
			StringUtil.extractLast(id, StringPool.POUND));

		User user = userLocalService.getUser(userId);

		int count = socialEquityUserPersistence.countByG_U(
			groupId, userId);

		if (count == 0) {
			addSocialEquityUser(groupId, user);
		}

		user.setSocialParticipationEquity(calculateEquity(
				getEquityDate(), k, b));

		SocialEquity userParticipationEquity =
			(SocialEquity)CounterCacheAdvice.getCounterValue(
				"com.liferay.portlet.social.service." +
				"SocialEquityLogLocalService#updateUser_PQ#none", userId);

		userParticipationEquity.subtract(new SocialEquity(k, b));

		String sql = CustomSQLUtil.get(_UPDATE_SOCIAL_EQUITY_USER_PQ);

		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$GROUP_ID$]",
				"[$PARTICIPATION_B$]",
				"[$PARTICIPATION_K$]",
				"[$USER_ID$]"
			},
			new String[] {
				String.valueOf(groupId),
				String.valueOf(b),
				String.valueOf(k),
				String.valueOf(userId)
			});

		runSQL(sql);
	}

	protected void addEquityLog(
			User user, AssetEntry assetEntry, User assetEntryUser,
			SocialEquitySetting equitySetting)
		throws SystemException {

		int actionDate = getEquityDate();

		double k = calculateK(
			equitySetting.getValue(), equitySetting.getValidity());
		double b = calculateB(
			actionDate, equitySetting.getValue(), equitySetting.getValidity());

		if (equitySetting.getType() ==
			SocialEquitySettingConstants.TYPE_INFORMATION) {

			int count = socialEquityAssetEntryPersistence.countByAssetEntryId(
				assetEntry.getEntryId());

			if (count == 0) {
				addSocialEquityAssetEntry(assetEntry);
			}

			getSocialEquityLogLocalService().incrementSocialEquityAssetEntry_IQ(
				assetEntry.getEntryId(), k, b);

			//updateAssetEntry(assetEntry, informationEquity);

			if ((assetEntryUser != null) && !assetEntryUser.isDefaultUser()) {
				getSocialEquityLogLocalService().incrementSocialEquityUser_CQ(
					assetEntry.getGroupId() + StringPool.POUND +
						assetEntryUser.getUserId(),
					k,b);

				getSocialEquityLogLocalService().incrementUser_CQ(
					assetEntryUser.getUserId(), k, b);
			}
		}
		else if (equitySetting.getType() ==
			SocialEquitySettingConstants.TYPE_PARTICIPATION) {

			if (!user.isDefaultUser()) {
				getSocialEquityLogLocalService().incrementSocialEquityUser_PQ(
					assetEntry.getGroupId() + StringPool.POUND +
						user.getUserId(),
					k, b);

				getSocialEquityLogLocalService().incrementUser_PQ(
					user.getUserId(), k, b);
			}
		}

		long equityLogId = counterLocalService.increment();

		SocialEquityLog equityLog = socialEquityLogPersistence.create(
			equityLogId);

		equityLog.setGroupId(assetEntry.getGroupId());
		equityLog.setCompanyId(user.getCompanyId());
		equityLog.setUserId(user.getUserId());
		equityLog.setAssetEntryId(assetEntry.getEntryId());
		equityLog.setActionId(equitySetting.getActionId());
		equityLog.setActionDate(actionDate);
		equityLog.setType(equitySetting.getType());
		equityLog.setValue(equitySetting.getValue());
		equityLog.setValidity(actionDate + equitySetting.getValidity());
		equityLog.setActive(true);

		socialEquityLogPersistence.update(equityLog, false);
	}

	protected void addSocialEquityAssetEntry(AssetEntry assetEntry)
		throws SystemException {

		String sql = CustomSQLUtil.get(_ADD_SOCIAL_EQUITY_ASSET_ENTRY);

		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$ASSET_ENTRY_ID$]",
				"[$COMPANY_ID$]",
				"[$EQUITY_ASSET_ENTRY_ID$]",
				"[$GROUP_ID$]",
				"[$USER_ID$]"
			},
			new String[] {
				String.valueOf(assetEntry.getEntryId()),
				String.valueOf(assetEntry.getCompanyId()),
				String.valueOf(counterLocalService.increment()),
				String.valueOf(assetEntry.getGroupId()),
				String.valueOf(assetEntry.getUserId())
			});

		runSQL(sql);
	}

	protected void addSocialEquityUser(long groupId, User user)
		throws SystemException {

		String sql = CustomSQLUtil.get(_ADD_SOCIAL_EQUITY_USER);

		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$COMPANY_ID$]",
				"[$EQUITY_USER_ID$]",
				"[$GROUP_ID$]",
				"[$USER_ID$]"
			},
			new String[] {
				String.valueOf(user.getCompanyId()),
				String.valueOf(counterLocalService.increment()),
				String.valueOf(groupId),
				String.valueOf(user.getUserId())
			});

		runSQL(sql);
	}

	protected double calculateB(int actionDate, int value, int validity) {
		return calculateK(value, validity) * (actionDate + validity) * -1;
	}

	protected double calculateEquity(int actionDate, double k, double b) {
		return k * actionDate + b;
	}

	protected double calculateK(int value, int validity) {
		if (validity == 0) {
			return 0;
		}

		return ((double)value / validity) * -1;
	}

	protected int getEquityDate() {
		return getEquityDate(new Date());
	}

	protected int getEquityDate(Date date) {
		Calendar calendar = new GregorianCalendar(2010, Calendar.JANUARY, 1);

		return calendar.fieldDifference(date, Calendar.DATE);
	}

	protected void runCheckSQL(String sqlId, int validity)
		throws SystemException {

		String sql = CustomSQLUtil.get(sqlId);

		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$TYPE_INFORMATION$]",
				"[$TYPE_PARTICIPATION$]",
				"[$VALIDITY$]"
			},
			new String[] {
				String.valueOf(SocialEquitySettingConstants.TYPE_INFORMATION),
				String.valueOf(SocialEquitySettingConstants.TYPE_PARTICIPATION),
				String.valueOf(validity)
			});

		runSQL(sql);
	}

	private static final String _ADD_SOCIAL_EQUITY_ASSET_ENTRY =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".addSocialEquityAssetEntry";

	private static final String _ADD_SOCIAL_EQUITY_USER =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".addSocialEquityUser";

	private static final String _CHECK_SOCIAL_EQUITY_ASSET_ENTRY_IQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".checkSocialEquityAssetEntry_IQ";

	private static final String _CHECK_SOCIAL_EQUITY_LOGS =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".checkSocialEquityLogs";

	private static final String _CHECK_SOCIAL_EQUITY_USER =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".checkSocialEquityUser";

	private static final String _CHECK_SOCIAL_EQUITY_USER_CQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".checkSocialEquityUser_CQ";

	private static final String _CHECK_SOCIAL_EQUITY_USER_PQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".checkSocialEquityUser_PQ";

	private static final String _UPDATE_SOCIAL_EQUITY_ASSET_ENTRY_IQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".updateSocialEquityAssetEntry_IQ";

	private static final String _UPDATE_SOCIAL_EQUITY_USER_CQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".updateSocialEquityUser_CQ";

	private static final String _UPDATE_SOCIAL_EQUITY_USER_PQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".updateSocialEquityUser_PQ";

}