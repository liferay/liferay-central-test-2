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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.User;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.social.model.SocialEquityAssetEntry;
import com.liferay.portlet.social.model.SocialEquityLog;
import com.liferay.portlet.social.model.SocialEquitySetting;
import com.liferay.portlet.social.model.SocialEquitySettingConstants;
import com.liferay.portlet.social.service.base.SocialEquityLogLocalServiceBaseImpl;
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
				assetEntry.getClassNameId(), actionId);

		for (SocialEquitySetting equitySetting : equitySettings) {
			addEquityLog(user, assetEntry, assetEntryUser, equitySetting);
		}
	}

	public void addEquityLogs(
			long userId, String className, long classPK, String actionId)
		throws PortalException, SystemException {

		AssetEntry assetEntry = assetEntryLocalService.getEntry(
			className, classPK);

		addEquityLogs(userId, assetEntry.getEntryId(), actionId);
	}

	public void checkEquityLogs() throws SystemException {
		int validity = getEquityDate();

		runCheckSQL(_CHECK_SOCIAL_EQUITY_ASSET_ENTRY_IQ_1, validity);
		runCheckSQL(_CHECK_SOCIAL_EQUITY_ASSET_ENTRY_IQ_2, validity);
		runCheckSQL(_CHECK_ASSET_ENTRY, validity);

		assetEntryPersistence.clearCache();

		runCheckSQL(_CHECK_SOCIAL_EQUITY_USER, validity);
		runCheckSQL(_CHECK_SOCIAL_EQUITY_USER_CQ, validity);
		runCheckSQL(_CHECK_SOCIAL_EQUITY_USER_PQ_1, validity);
		runCheckSQL(_CHECK_SOCIAL_EQUITY_USER_PQ_2, validity);
		runCheckSQL(_CHECK_SOCIAL_EQUITY_USER_PEQ, validity);
		runCheckSQL(_CHECK_USER_CQ_PQ, validity);
		runCheckSQL(_CHECK_USER_PEQ, validity);

		userPersistence.clearCache();

		runCheckSQL(_CHECK_SOCIAL_EQUITY_LOGS, validity);

		socialEquityLogPersistence.clearCache();
	}

	public void deactivateEquityLogs(long assetEntryId)
		throws PortalException, SystemException {

		SocialEquityAssetEntry equityAssetEntry =
			socialEquityAssetEntryPersistence.findByAssetEntryId(assetEntryId);

		socialEquityAssetEntryPersistence.removeByAssetEntryId(assetEntryId);

		User user = null;

		try {
			user = userPersistence.findByPrimaryKey(
				equityAssetEntry.getUserId());

			if (!user.isDefaultUser()) {
				updateSocialEquityUser_CQ(
					equityAssetEntry.getGroupId(), user.getUserId());
				updateSocialEquityUser_PEQ(
					equityAssetEntry.getGroupId(), user.getUserId());
				updateUser_CQ_PQ(user.getUserId());
				updateUser_PEQ(user.getUserId());

				userPersistence.clearCache(user);
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

	public void deactivateEquityLogs(String className, long classPK)
		throws PortalException, SystemException {

		AssetEntry assetEntry = assetEntryLocalService.getEntry(
			className, classPK);

		deactivateEquityLogs(assetEntry.getEntryId());
	}

	protected void addEquityLog(
			User user, AssetEntry assetEntry, User assetEntryUser,
			SocialEquitySetting equitySetting)
		throws SystemException {

		int actionDate = getEquityDate();

		double k = getK(
			equitySetting.getValue(), equitySetting.getValidity());
		double b = getB(
			actionDate, equitySetting.getValue(), equitySetting.getValidity());

		if (equitySetting.getType() ==
			SocialEquitySettingConstants.TYPE_INFORMATION) {

			int count = socialEquityAssetEntryPersistence.countByAssetEntryId(
				assetEntry.getEntryId());

			if (count == 0) {
				addSocialEquityAssetEntry(assetEntry);
			}

			updateSocialEquityAssetEntry_IQ(
				assetEntry.getEntryId(), actionDate, k, b);

			updateAssetEntry(assetEntry.getEntryId());

			assetEntryPersistence.clearCache(assetEntry);

			if ((assetEntryUser != null) && !assetEntryUser.isDefaultUser()) {
				updateSocialEquityUser_CQ(
					assetEntry.getGroupId(), assetEntryUser.getUserId());
				updateSocialEquityUser_PEQ(
					assetEntry.getGroupId(), assetEntryUser.getUserId());
				updateUser_CQ_PQ(assetEntryUser.getUserId());
				updateUser_PEQ(assetEntryUser.getUserId());

				userPersistence.clearCache(assetEntryUser);
			}
		}
		else if (equitySetting.getType() ==
			SocialEquitySettingConstants.TYPE_PARTICIPATION) {

			int count = socialEquityUserPersistence.countByUserId(
				user.getUserId());

			if (count == 0) {
				addSocialEquityUser(assetEntry.getGroupId(), user);
			}

			if (!user.isDefaultUser()) {
				updateSocialEquityUser_PQ(
					assetEntry.getGroupId(), user.getUserId(), actionDate, k,
					b);
				updateSocialEquityUser_PEQ(
					assetEntry.getGroupId(), user.getUserId());
				updateUser_CQ_PQ(user.getUserId());
				updateUser_PEQ(user.getUserId());

				userPersistence.clearCache(user);
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

	protected double getB(int actionDate, int value, int validity) {
		return getK(value, validity) * (actionDate + validity) * -1;
	}

	protected int getEquityDate() {
		return getEquityDate(new Date());
	}

	protected int getEquityDate(Date date) {
		Calendar calendar = new GregorianCalendar(2010, Calendar.JANUARY, 1);

		return calendar.fieldDifference(date, Calendar.DATE);
	}

	protected double getK(int value, int validity) {
		if (validity == 0) {
			return 0;
		}

		return ((double)value / validity) * -1;
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

	protected void updateAssetEntry(long assetEntryId) throws SystemException {
		String sql = CustomSQLUtil.get(_UPDATE_ASSET_ENTRY);

		sql = StringUtil.replace(
			sql, "[$ASSET_ENTRY_ID$]", String.valueOf(assetEntryId));

		runSQL(sql);
	}

	protected void updateSocialEquityAssetEntry_IQ(
			long assetEntryId, int activityDate, double k, double b)
		throws SystemException {

		String sql = CustomSQLUtil.get(_UPDATE_SOCIAL_EQUITY_ASSET_ENTRY_IQ);

		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$ACTIVITY_DATE$]",
				"[$ASSET_ENTRY_ID$]",
				"[$INFORMATION_B$]",
				"[$INFORMATION_K$]"
			},
			new String[] {
				String.valueOf(activityDate),
				String.valueOf(assetEntryId),
				String.valueOf(b),
				String.valueOf(k)
			});

		runSQL(sql);
	}

	protected void updateSocialEquityUser_CQ(long groupId, long userId)
		throws SystemException {

		String sql = CustomSQLUtil.get(_UPDATE_SOCIAL_EQUITY_USER_CQ);

		sql = StringUtil.replace(
			sql,
			new String[] {"[$GROUP_ID$]", "[$USER_ID$]"},
			new String[] {String.valueOf(groupId), String.valueOf(userId)});

		runSQL(sql);
	}

	protected void updateSocialEquityUser_PEQ(long groupId, long userId)
		throws SystemException {

		String sql = CustomSQLUtil.get(_UPDATE_SOCIAL_EQUITY_USER_PEQ);

		sql = StringUtil.replace(
			sql,
			new String[] {"[$GROUP_ID$]", "[$USER_ID$]"},
			new String[] {String.valueOf(groupId), String.valueOf(userId)});

		runSQL(sql);
	}

	protected void updateSocialEquityUser_PQ(
			long groupId, long userId, int activityDate, double k, double b)
		throws SystemException {

		String sql = CustomSQLUtil.get(_UPDATE_SOCIAL_EQUITY_USER_PQ);

		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$ACTIVITY_DATE$]",
				"[$GROUP_ID$]",
				"[$PARTICIPATION_B$]",
				"[$PARTICIPATION_K$]",
				"[$USER_ID$]"
			},
			new String[] {
				String.valueOf(activityDate),
				String.valueOf(groupId),
				String.valueOf(b),
				String.valueOf(k),
				String.valueOf(userId)
			});

		runSQL(sql);
	}

	protected void updateUser_CQ_PQ(long userId) throws SystemException {
		String sql = CustomSQLUtil.get(_UPDATE_USER_CQ_PQ);

		sql = StringUtil.replace(sql, "[$USER_ID$]", String.valueOf(userId));

		runSQL(sql);
	}

	protected void updateUser_PEQ(long userId) throws SystemException {
		String sql = CustomSQLUtil.get(_UPDATE_USER_PEQ);

		sql = StringUtil.replace(sql, "[$USER_ID$]", String.valueOf(userId));

		runSQL(sql);
	}

	private static final String _ADD_SOCIAL_EQUITY_ASSET_ENTRY =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".addSocialEquityAssetEntry";

	private static final String _ADD_SOCIAL_EQUITY_USER =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".addSocialEquityUser";

	private static final String _CHECK_ASSET_ENTRY =
		SocialEquityLogLocalServiceImpl.class.getName() + ".checkAssetEntry";

	private static final String _CHECK_SOCIAL_EQUITY_ASSET_ENTRY_IQ_1 =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".checkSocialEquityAssetEntry_IQ_1";

	private static final String _CHECK_SOCIAL_EQUITY_ASSET_ENTRY_IQ_2 =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".checkSocialEquityAssetEntry_IQ_2";

	private static final String _CHECK_SOCIAL_EQUITY_LOGS =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".checkSocialEquityLogs";

	private static final String _CHECK_SOCIAL_EQUITY_USER =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".checkSocialEquityUser";

	private static final String _CHECK_SOCIAL_EQUITY_USER_CQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".checkSocialEquityUser_CQ";

	private static final String _CHECK_SOCIAL_EQUITY_USER_PEQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".checkSocialEquityUser_PEQ";

	private static final String _CHECK_SOCIAL_EQUITY_USER_PQ_1 =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".checkSocialEquityUser_PQ_1";

	private static final String _CHECK_SOCIAL_EQUITY_USER_PQ_2 =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".checkSocialEquityUser_PQ_2";

	private static final String _CHECK_USER_CQ_PQ =
		SocialEquityLogLocalServiceImpl.class.getName() + ".checkUser_CQ_PQ";

	private static final String _CHECK_USER_PEQ =
		SocialEquityLogLocalServiceImpl.class.getName() + ".checkUser_PEQ";

	private static final String _UPDATE_ASSET_ENTRY =
		SocialEquityLogLocalServiceImpl.class.getName() + ".updateAssetEntry";

	private static final String _UPDATE_SOCIAL_EQUITY_ASSET_ENTRY_IQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".updateSocialEquityAssetEntry_IQ";

	private static final String _UPDATE_SOCIAL_EQUITY_USER_CQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".updateSocialEquityUser_CQ";

	private static final String _UPDATE_SOCIAL_EQUITY_USER_PEQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".updateSocialEquityUser_PEQ";

	private static final String _UPDATE_SOCIAL_EQUITY_USER_PQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".updateSocialEquityUser_PQ";

	private static final String _UPDATE_USER_CQ_PQ =
		SocialEquityLogLocalServiceImpl.class.getName() + ".updateUser_CQ_PQ";

	private static final String _UPDATE_USER_PEQ =
		SocialEquityLogLocalServiceImpl.class.getName() + ".updateUser_PEQ";

}