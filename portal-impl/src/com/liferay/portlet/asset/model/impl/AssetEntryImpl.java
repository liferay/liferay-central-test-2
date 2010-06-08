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

package com.liferay.portlet.asset.model.impl;

import com.liferay.portal.cache.CounterCacheAdvice;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.social.service.SocialEquityAssetEntryLocalServiceUtil;
import com.liferay.util.SocialEquity;

import java.util.List;

/**
 * <a href="AssetEntryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AssetEntryImpl extends AssetEntryModelImpl implements AssetEntry {

	public AssetEntryImpl() {
	}

	public List<AssetCategory> getCategories() throws SystemException {
		return AssetCategoryLocalServiceUtil.getEntryCategories(getEntryId());
	}

	public long[] getCategoryIds() throws SystemException {
		return StringUtil.split(
			ListUtil.toString(getCategories(), "categoryId"), 0L);
	}

	public double getSocialInformationEquity() throws SystemException {
		if (_informationEquityBase == -1) {
			_informationEquityBase =
				SocialEquityAssetEntryLocalServiceUtil.getInformationEquity(
					getEntryId());
		}

		SocialEquity iqCounter =
			(SocialEquity)CounterCacheAdvice.getCounterValue(
				"com.liferay.portlet.social.service." +
				"SocialEquityLogLocalService#" +
				"updateSocialEquityAssetEntry_IQ", getEntryId());

		if (iqCounter == null) {
			return _informationEquityBase;
		}

		return _informationEquityBase + iqCounter.getValue();
	}

	public String[] getTagNames() throws SystemException {
		return StringUtil.split(ListUtil.toString(getTags(), "name"));
	}

	public List<AssetTag> getTags() throws SystemException {
		return AssetTagLocalServiceUtil.getEntryTags(getEntryId());
	}

	public void setSocialInformationEquity(double informationEquity)
		throws SystemException {

		if (_informationEquityBase == -1) {
			getSocialInformationEquity();
		}

		_informationEquityBase = _informationEquityBase + informationEquity;
	}

	private double _informationEquityBase = -1;

}