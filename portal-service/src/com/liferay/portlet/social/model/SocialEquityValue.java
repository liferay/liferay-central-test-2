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

package com.liferay.portlet.social.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <a href="SocialEquityValue.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Zsolt Berentey
 */
public class SocialEquityValue {

	public SocialEquityValue(double k, double b) {
		_k = k;
		_b = b;
	}

	public void add(SocialEquityValue value) {
		_k = _k + value._k;
		_b = _b + value._b;
	}

	public double getB() {
		return _b;
	}

	public double getK() {
		return _k;
	}

	public double getValue() {
		return getValue(getEquityDate(new Date()));
	}

	public double getValue(int equityDate) {
		return _k * equityDate + _b;
	}

	public void subtract(SocialEquityValue value) {
		_k = _k - value._k;
		_b = _b - value._b;
	}

	protected int getEquityDate(Date date) {
		GregorianCalendar calendar = new GregorianCalendar();

		calendar.setTime(date);

		long C2 = 1000 * 1000;

		long C6 = C2 * 1000 * 60 * 60 * 24;

		long d = calendar.getTimeInMillis() - _socialEquityBaseDate;

		return (int)(d/(C6/C2));
	}

	private double _b = 0;
	private double _k = 0;

	private static long _socialEquityBaseDate =
		new GregorianCalendar(2010, Calendar.JANUARY, 1).getTimeInMillis();

}