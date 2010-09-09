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

import com.liferay.portal.kernel.util.Time;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Zsolt Berentey
 */
public class SocialEquityValue {

	public SocialEquityValue(double k, double b) {
		_k = k;
		_b = b;
	}

	public SocialEquityValue(int actionDate, int value, int lifespan) {
		_k = calculateK(value, lifespan);
		_b = calculateB(actionDate, value, lifespan);
	}

	public void add(SocialEquityValue socialEquityValue) {
		add(socialEquityValue._k, socialEquityValue._b);
	}

	public void add(double k, double b) {
		_k = _k + k;
		_b = _b + b;
	}

	public void add(int actionDate, int value, int lifespan) {
		add(calculateK(value, lifespan),
			calculateB(actionDate, value, lifespan));
	}

	public SocialEquityValue clone() {
		return new SocialEquityValue(_k, _b);
	}

	public double getB() {
		return _b;
	}

	public double getK() {
		return _k;
	}

	public double getValue() {
		return getValue(getEquityDate());
	}

	public double getValue(int equityDate) {
		return _k * equityDate + _b;
	}

	public void subtract(SocialEquityValue socialEquityValue) {
		_k = _k - socialEquityValue._k;
		_b = _b - socialEquityValue._b;
	}

	protected double calculateB(int actionDate, int value, int lifespan) {
		return calculateK(value, lifespan) * (actionDate + lifespan) * -1;
	}

	protected double calculateK(int value, int lifespan) {
		if (lifespan == 0) {
			return 0;
		}

		return ((double)value / lifespan) * -1;
	}

	protected int getEquityDate() {
		long d = System.currentTimeMillis() - _BASE_TIME;

		return (int)(d / Time.DAY);
	}

	private static final long _BASE_TIME =
		new GregorianCalendar(2010, Calendar.JANUARY, 1).getTimeInMillis();

	private double _b;
	private double _k;

}