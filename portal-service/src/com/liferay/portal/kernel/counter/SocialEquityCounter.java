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

import com.liferay.portlet.social.model.SocialEquityValue;

/**
 * @author Zsolt Berentey
 */
public class SocialEquityCounter implements Counter<SocialEquityValue> {

	public SocialEquityCounter(SocialEquityValue value) {
		_value = value;
	}

	public void decrement(SocialEquityValue delta) {
		_value.subtract(delta);
	}

	public Counter<SocialEquityValue> decrementByCreate(Counter<?> delta) {

		if (delta.getValue() instanceof SocialEquityValue) {
			return new SocialEquityCounter(
				new SocialEquityValue(
					_value.getK()-((SocialEquityValue)delta.getValue()).getK(),
					_value.getB()-((SocialEquityValue)delta.getValue()).getB()
				)
			);
		}

		throw new IllegalArgumentException("Invalid type " +
			delta.getValue().getClass().getName() +
				". Expected type is SocialEquity.");
	}

	public Counter<SocialEquityValue> decrementByCreate(
		SocialEquityValue delta) {

		return new SocialEquityCounter(
			new SocialEquityValue(
				_value.getK() - delta.getK(),
				_value.getB() - delta.getB()
			)
		);
	}

	public SocialEquityValue getValue() {
		return _value;
	}

	public void increment(SocialEquityValue delta) {
		_value.add(delta);
	}

	public Counter<SocialEquityValue> incrementByCreate(Counter<?> delta) {
		if (delta.getValue() instanceof SocialEquityValue) {
			return new SocialEquityCounter(
				new SocialEquityValue(
					_value.getK()+((SocialEquityValue)delta.getValue()).getK(),
					_value.getB()+((SocialEquityValue)delta.getValue()).getB()
				)
			);
		}

		throw new IllegalArgumentException("Invalid type " +
			delta.getValue().getClass().getName() +
				". Expected type is SocialEquity.");
	}

	public Counter<SocialEquityValue> incrementByCreate(
		SocialEquityValue delta) {

		return new SocialEquityCounter(
			new SocialEquityValue(
				_value.getK() + delta.getK(),
				_value.getB() + delta.getB()
			)
		);
	}

	public void setValue(SocialEquityValue value) {
		_value = value;
	}

	private SocialEquityValue _value = new SocialEquityValue(0,0);

}