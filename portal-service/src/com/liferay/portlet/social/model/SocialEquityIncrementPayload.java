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

package com.liferay.portlet.social.model;

import com.liferay.portal.model.User;
import com.liferay.portlet.asset.model.AssetEntry;

/**
 * @author Zsolt Berentey
 */
public class SocialEquityIncrementPayload {

	public SocialEquityIncrementPayload clone() {
		SocialEquityIncrementPayload socialEquityIncrementPayload =
			new SocialEquityIncrementPayload();

		socialEquityIncrementPayload.setAssetEntry(getAssetEntry());
		socialEquityIncrementPayload.setEquityValue(getEquityValue());
		socialEquityIncrementPayload.setUser(getUser());

		return socialEquityIncrementPayload;
	}

	public AssetEntry getAssetEntry() {
		return _assetEntry;
	}

	public SocialEquityValue getEquityValue() {
		return _equityValue;
	}

	public User getUser() {
		return _user;
	}

	public void setAssetEntry(AssetEntry assetEntry) {
		_assetEntry = assetEntry;
	}

	public void setEquityValue(SocialEquityValue equityValue) {
		_equityValue = equityValue;
	}

	public void setUser(User user) {
		_user = user;
	}

	private AssetEntry _assetEntry;
	private SocialEquityValue _equityValue;
	private User _user;

}