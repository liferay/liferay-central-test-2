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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.social.NoSuchEquityAssetEntryException;
import com.liferay.portlet.social.model.SocialEquityAssetEntry;
import com.liferay.portlet.social.service.base.SocialEquityAssetEntryLocalServiceBaseImpl;

import java.util.Date;

/**
 * <a href="SocialEquityAssetEntryLocalServiceImpl.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Zsolt Berentey
 */
public class SocialEquityAssetEntryLocalServiceImpl
	extends SocialEquityAssetEntryLocalServiceBaseImpl {

	public double getInformationEquity(long assetEntryId)
		throws SystemException {

		double informationEquity = 0;

		try {
			SocialEquityAssetEntry socialEquityAssetEntry =
				socialEquityAssetEntryPersistence.findByAssetEntryId(
					assetEntryId);

			informationEquity = calculateEquity(
				getEquityDate(new Date()),
				socialEquityAssetEntry.getInformationK(),
				socialEquityAssetEntry.getInformationB());
		}
		catch (NoSuchEquityAssetEntryException nseaee) {
		}

		return informationEquity;
	}

	protected double calculateEquity(int actionDate, double k, double b) {
		return k * actionDate + b;
	}

	protected int getEquityDate(Date date) {
		Calendar calendar = new GregorianCalendar(2010, Calendar.JANUARY, 1);

		return calendar.fieldDifference(date, Calendar.DATE);
	}

}