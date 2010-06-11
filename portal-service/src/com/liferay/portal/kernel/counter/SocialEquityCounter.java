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

package com.liferay.portal.kernel.counter;

import com.liferay.portal.kernel.util.SocialEquity;

/**
 * <a href="SocialEquityCounter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Zsolt Berentey
 */
public class SocialEquityCounter implements Counter<SocialEquity> {

	public SocialEquityCounter(SocialEquity value) {
		_value = value;
	}

	public void decrement(SocialEquity delta) {
		_value.subtract(delta);
	}

	public Counter<SocialEquity> decrementByCreate(Counter<?> delta) {

		if (delta.getValue() instanceof SocialEquity) {
			return new SocialEquityCounter(
				new SocialEquity(
					_value.getK() - ((SocialEquity)delta.getValue()).getK(),
					_value.getB() - ((SocialEquity)delta.getValue()).getB()
				)
			);
		}

		throw new IllegalArgumentException("Invalid type " +
			delta.getValue().getClass().getName() +
				". Expected type is SocialEquity.");
	}

	public Counter<SocialEquity> decrementByCreate(SocialEquity delta) {
		return new SocialEquityCounter(
			new SocialEquity(
				_value.getK() - delta.getK(),
				_value.getB() - delta.getB()
			)
		);
	}

	public SocialEquity getValue() {
		return _value;
	}

	public void increment(SocialEquity delta) {
		_value.add(delta);
	}

	public Counter<SocialEquity> incrementByCreate(Counter<?> delta) {
		if (delta.getValue() instanceof SocialEquity) {
			return new SocialEquityCounter(
				new SocialEquity(
					_value.getK() + ((SocialEquity)delta.getValue()).getK(),
					_value.getB() + ((SocialEquity)delta.getValue()).getB()
				)
			);
		}

		throw new IllegalArgumentException("Invalid type " +
			delta.getValue().getClass().getName() +
				". Expected type is SocialEquity.");
	}

	public Counter<SocialEquity> incrementByCreate(SocialEquity delta) {
		return new SocialEquityCounter(
			new SocialEquity(
				_value.getK() + delta.getK(),
				_value.getB() + delta.getB()
			)
		);
	}

	public void setValue(SocialEquity value) {
		_value = value;
	}

	private SocialEquity _value = new SocialEquity(0,0);

}