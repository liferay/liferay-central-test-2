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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.social.model.SocialEquityLog;
import com.liferay.portlet.social.model.SocialEquitySetting;
import com.liferay.portlet.social.model.SocialEquitySettingConstants;
import com.liferay.portlet.social.service.base.SocialEquityLogLocalServiceBaseImpl;

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

		List<SocialEquitySetting> equitySettings =
			socialEquitySettingPersistence.findByC_A(
				assetEntry.getClassNameId(), actionId);

		for (SocialEquitySetting equitySetting : equitySettings) {
			addEquityLog(user, assetEntry, equitySetting);
		}
	}

	public void checkEquityLogs() throws SystemException {
		String[] sqls = getCheckEquityLogSQLs();

		for (String sql : sqls) {
			runSQL(sql);
		}

		userPersistence.clearCache();
		assetEntryPersistence.clearCache();
	}

	protected void addEquityLog(
			User user, AssetEntry assetEntry, SocialEquitySetting equitySetting)
		throws SystemException {

		int actionDate = getActionDate();
		double k = getK(
			equitySetting.getValue(), equitySetting.getValidity());
		double b = getB(
			actionDate, equitySetting.getValue(), equitySetting.getValidity());

		String[] sqls = getAddEquityLogSQLs(
			user, assetEntry, equitySetting, actionDate, k, b);

		for (String sql : sqls) {
			runSQL(sql);
		}

		//userPersistence.clearCache(user);

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

	protected int getActionDate() {
		return getActionDate(new Date());
	}

	protected int getActionDate(Date date) {
		Calendar calendar = new GregorianCalendar(2010, Calendar.JANUARY, 1);

		return calendar.fieldDifference(date, Calendar.DATE);
	}

	protected String[] getAddEquityLogSQLs(
		User user, AssetEntry assetEntry, SocialEquitySetting equitySetting,
		int actionDate, double k, double b) {

		int type = equitySetting.getType();

		if (type == SocialEquitySettingConstants.TYPE_INFORMATION) {
		}
		else if (type == SocialEquitySettingConstants.TYPE_PARTICIPATION) {
		}

		return null;
	}

	protected double getB(int actionDate, int value, int validity) {
		return getK(value, validity) * (actionDate + validity) * -1;
	}

	protected String[] getCheckEquityLogSQLs() {
		return null;
	}

	protected double getK(int value, int validity) {
		if (validity == 0) {
			return 0;
		}

		return ((double)value / validity) * -1;
	}

}