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

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.model.SocialEquityActionMapping;
import com.liferay.portlet.social.model.SocialEquitySetting;
import com.liferay.portlet.social.model.SocialEquitySettingConstants;
import com.liferay.portlet.social.model.impl.SocialEquitySettingImpl;
import com.liferay.portlet.social.service.base.SocialEquitySettingLocalServiceBaseImpl;

import java.util.Collections;
import java.util.List;

/**
 * <a href="SocialEquitySettingLocalServiceImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Zsolt Berentey
 * @author Brian Wing Shun Chan
 */
public class SocialEquitySettingLocalServiceImpl
	extends SocialEquitySettingLocalServiceBaseImpl {

	public static final String CACHE_NAME =
		SocialEquitySettingLocalServiceImpl.class.getName();

	public List<SocialEquitySetting> getEquitySettings(
			long classNameId, String actionId)
		throws SystemException {

		String key = encodeKey(classNameId, actionId);

		List<SocialEquitySetting> socialEquitySettings =
			(List<SocialEquitySetting>)_portalCache.get(key);

		if (socialEquitySettings != null) {
			return socialEquitySettings;
		}

		String className = PortalUtil.getClassName(classNameId);

		SocialEquityActionMapping equityActionMapping =
			ResourceActionsUtil.getSocialEquityActionMapping(
				className, actionId);

		if (equityActionMapping == null) {
			socialEquitySettings = Collections.EMPTY_LIST;

			_portalCache.put(key, socialEquitySettings);

			return socialEquitySettings;
		}

		List<SocialEquitySetting> equitySettings =
			socialEquitySettingPersistence.findByC_A(classNameId, actionId);

		equitySettings = ListUtil.copy(equitySettings);

		if (equitySettings.isEmpty()) {
			if (equityActionMapping.getInformationValue() > 0) {
				SocialEquitySetting informationEquitySetting =
					getInformationEquitySetting(actionId, equityActionMapping);

				equitySettings.add(informationEquitySetting);
			}

			if (equityActionMapping.getParticipationValue() > 0) {
				SocialEquitySetting participationEquitySetting =
					getParticipationEquitySetting(
						actionId, equityActionMapping);

				equitySettings.add(participationEquitySetting);
			}
		}
		else if (equitySettings.size() == 1) {
			SocialEquitySetting equitySetting = equitySettings.get(0);

			if ((equityActionMapping.getInformationValue() > 0) &&
				(equitySetting.getType() ==
					SocialEquitySettingConstants.TYPE_PARTICIPATION)) {

				SocialEquitySetting informationEquitySetting =
					getInformationEquitySetting(actionId, equityActionMapping);

				equitySettings.add(informationEquitySetting);
			}
			else if ((equityActionMapping.getParticipationValue() > 0) &&
					 (equitySetting.getType() ==
						SocialEquitySettingConstants.TYPE_INFORMATION)) {

				SocialEquitySetting participationEquitySetting =
					getParticipationEquitySetting(
						actionId, equityActionMapping);

				equitySettings.add(participationEquitySetting);
			}
		}

		_portalCache.put(key, socialEquitySettings);

		return equitySettings;
	}

	protected String encodeKey(long classNameId, String actionId) {
		StringBundler sb = new StringBundler(5);

		sb.append(CACHE_NAME);
		sb.append(StringPool.POUND);
		sb.append(classNameId);
		sb.append(StringPool.POUND);
		sb.append(actionId);

		return sb.toString();
	}

	protected SocialEquitySetting getInformationEquitySetting(
		String actionId, SocialEquityActionMapping equityActionMapping) {

		SocialEquitySetting equitySetting = new SocialEquitySettingImpl();

		equitySetting.setActionId(actionId);
		equitySetting.setType(SocialEquitySettingConstants.TYPE_INFORMATION);
		equitySetting.setValue(equityActionMapping.getInformationValue());
		equitySetting.setValidity(equityActionMapping.getInformationLifespan());

		return equitySetting;
	}

	protected SocialEquitySetting getParticipationEquitySetting(
		String actionId, SocialEquityActionMapping equityActionMapping) {

		SocialEquitySetting equitySetting = new SocialEquitySettingImpl();

		equitySetting.setActionId(actionId);
		equitySetting.setType(SocialEquitySettingConstants.TYPE_PARTICIPATION);
		equitySetting.setValue(equityActionMapping.getParticipationValue());
		equitySetting.setValidity(
			equityActionMapping.getParticipationLifespan());

		return equitySetting;
	}

	private static PortalCache _portalCache = MultiVMPoolUtil.getCache(
		CACHE_NAME);

}