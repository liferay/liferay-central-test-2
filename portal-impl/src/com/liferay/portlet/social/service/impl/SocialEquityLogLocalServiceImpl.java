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
import com.liferay.portlet.social.model.SocialEquityValue;
import com.liferay.portlet.social.service.base.SocialEquityLogLocalServiceBaseImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

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

	//TODO: This should run on one node only!
	//Called by CheckEquityLogMessageListener via MessageBus from
	//MainServlet in initSocialEquityLogScheduler()
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

		updateRanks();
	}

	public void deactivateEquityLogs(long assetEntryId)
		throws PortalException, SystemException {

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
				SocialEquityValue socialEquity =
					new SocialEquityValue(
						-equityAssetEntry.getInformationK(),
						-equityAssetEntry.getInformationB());

				incrementSocialEquityUser_CQ(
					equityAssetEntry.getGroupId() + StringPool.POUND +
					user.getUserId(), socialEquity);
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

	public void incrementSocialEquityAssetEntry_IQ(
			long assetEntryId, SocialEquityValue value)
		throws SystemException {

		AssetEntry assetEntry = assetEntryPersistence.fetchByPrimaryKey(
			assetEntryId);

		assetEntry.updateSocialInformationEquity(value.getValue());

		int count = socialEquityAssetEntryPersistence.countByAssetEntryId(
			assetEntryId);

		if (count == 0) {
			addSocialEquityAssetEntry(assetEntry);
		}

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
				String.valueOf(value.getB()),
				String.valueOf(value.getK())
			});

		runSQL(sql);
	}

	public void incrementSocialEquityUser_CQ(
			String id, SocialEquityValue value)
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

			user.updateSocialContributionEquity(value.getValue());

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
					String.valueOf(value.getB()),
					String.valueOf(value.getK())
				});

			runSQL(sql);
	}

	public void incrementSocialEquityUser_PQ(
		String id, SocialEquityValue value)
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

		user.updateSocialParticipationEquity(value.getValue());

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
				String.valueOf(value.getB()),
				String.valueOf(value.getK()),
				String.valueOf(userId)
			});

		runSQL(sql);
	}

	protected void addEquityLog(
			User user, AssetEntry assetEntry, User assetEntryUser,
			SocialEquitySetting equitySetting)
		throws PortalException, SystemException {

		int actionDate = getEquityDate();

		double k = calculateK(
			equitySetting.getValue(), equitySetting.getValidity());
		double b = calculateB(
			actionDate, equitySetting.getValue(), equitySetting.getValidity());

		SocialEquityValue socialEquity = new SocialEquityValue(k, b);

		if (equitySetting.getType() ==
			SocialEquitySettingConstants.TYPE_INFORMATION) {

			getSocialEquityLogLocalService().incrementSocialEquityAssetEntry_IQ(
				assetEntry.getEntryId(), socialEquity);

			if ((assetEntryUser != null) && !assetEntryUser.isDefaultUser()) {
				getSocialEquityLogLocalService().incrementSocialEquityUser_CQ(
					assetEntry.getGroupId() + StringPool.POUND +
						assetEntryUser.getUserId(),
					socialEquity);
			}
		}
		else if (equitySetting.getType() ==
			SocialEquitySettingConstants.TYPE_PARTICIPATION) {

			if (!user.isDefaultUser()) {
				getSocialEquityLogLocalService().incrementSocialEquityUser_PQ(
					assetEntry.getGroupId() + StringPool.POUND +
						user.getUserId(),
					socialEquity);
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

	protected void updateRanks() throws SystemException {
		try {
			DataSource dataSource = socialEquityLogPersistence.getDataSource();

			JdbcTemplate jdbc = new JdbcTemplate(dataSource);

			UpdateRanksCallbackHandler handler =
				new UpdateRanksCallbackHandler(jdbc);

			String sql = CustomSQLUtil.get(_ITERATE_OVER_SOCIAL_EQUITY_USER);

			sql = StringUtil.replace(
				sql, "[$ACTION_DATE$]", String.valueOf(getEquityDate()));

			jdbc.query(sql, handler);

			handler.flush();

		}
		catch (Exception e) {
			throw new SystemException(e);
		}

	}

	private class UpdateRanksCallbackHandler implements RowCallbackHandler {

		public UpdateRanksCallbackHandler(JdbcTemplate jdbcTemplate) {
			_setter = new UpdateRanksPreparedStatementSetter(jdbcTemplate);
		}

		public void flush() {
			_setter.flush();
		}

		public void processRow(ResultSet rs) throws SQLException {
			_setter.add(rs.getLong(1), rs.getLong(2));
		}

		private UpdateRanksPreparedStatementSetter _setter = null;

	}

	private class UpdateRanksPreparedStatementSetter
		implements BatchPreparedStatementSetter {

		public UpdateRanksPreparedStatementSetter(JdbcTemplate jdbcTemplate) {
			_jdbc = jdbcTemplate;
		}

		public void add(long id, long rank) {
			_sqlParams.add(new Long[] {id, rank});

			if (_sqlParams.size() >= 100) {
				flush();
			}
		}

		public void flush() {
			try {
				_jdbc.batchUpdate(_sql, this);
			}
			catch (DataAccessException dae) {
				throw dae;
			}
			finally {
				_sqlParams.clear();
			}
		}

		public int getBatchSize() {
			return _sqlParams.size();
		}

		public void setValues(PreparedStatement ps, int index)
			throws SQLException {

			ps.setLong(1, _sqlParams.get(index)[1]);
			ps.setLong(2, _sqlParams.get(index)[0]);
			ps.setLong(3, _sqlParams.get(index)[1]);

		}

		private JdbcTemplate _jdbc = null;
		private String _sql= CustomSQLUtil.get(_UPDATE_SOCIAL_EQUITY_USER_RANK);
		private List<Long[]> _sqlParams = new ArrayList<Long[]>();

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

	private static final String _ITERATE_OVER_SOCIAL_EQUITY_USER =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".iterateOverSocialEquityUser";

	private static final String _UPDATE_SOCIAL_EQUITY_ASSET_ENTRY_IQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".updateSocialEquityAssetEntry_IQ";

	private static final String _UPDATE_SOCIAL_EQUITY_USER_CQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".updateSocialEquityUser_CQ";

	private static final String _UPDATE_SOCIAL_EQUITY_USER_PQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".updateSocialEquityUser_PQ";

	private static final String _UPDATE_SOCIAL_EQUITY_USER_RANK =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".updateSocialEquityUserRank";

}