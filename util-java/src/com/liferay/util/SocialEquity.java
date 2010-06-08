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

package com.liferay.util;

import java.util.Date;

import com.liferay.ibm.icu.util.Calendar;
import com.liferay.ibm.icu.util.GregorianCalendar;

/**
 * <a href="SocialEquity.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Zsolt Berentey
 */
public class SocialEquity extends Number {

	public SocialEquity(double k, double b) {
		_k = k;
		_b = b;
		_equityDate = getEquityDate(new Date());
	}

	public void add(SocialEquity value) {
		_k = _k + value._k;
		_b = _b + value._b;
	}

	@Override
	public double doubleValue() {
		return _k * _equityDate + _b;
	}

	@Override
	public float floatValue() {
		return (float)doubleValue();
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
	
	@Override
	public int intValue() {
		return (int)doubleValue();
	}

	@Override
	public long longValue() {
		return (long)doubleValue();
	}

	public void subtract(SocialEquity value) {
		_k = _k - value._k;
		_b = _b - value._b;
	}
	
	protected int getEquityDate(Date date) {
		Calendar calendar = new GregorianCalendar(2010, Calendar.JANUARY, 1);

		return calendar.fieldDifference(date, Calendar.DATE);
	}
	
	private int _equityDate = 0;
	private double _b = 0;
	private double _k = 0;
}
