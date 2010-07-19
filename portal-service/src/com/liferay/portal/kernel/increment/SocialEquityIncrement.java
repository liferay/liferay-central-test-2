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

package com.liferay.portal.kernel.increment;

import com.liferay.portlet.social.model.SocialEquityValue;

/**
 * @author Zsolt Berentey
 */
public class SocialEquityIncrement implements Increment<SocialEquityValue> {

	public SocialEquityIncrement(SocialEquityValue value) {
		_value = value;
	}

	public void decrease(SocialEquityValue delta) {
		_value.subtract(delta);
	}

	public Increment<SocialEquityValue> decreaseForNew(
		SocialEquityValue delta) {

		return new SocialEquityIncrement(
			new SocialEquityValue(
				_value.getK() - delta.getK(),
				_value.getB() - delta.getB()
			)
		);
	}

	public SocialEquityValue getValue() {
		return _value;
	}

	public void increase(SocialEquityValue delta) {
		_value.add(delta);
	}

	public Increment<SocialEquityValue> increaseForNew(
		SocialEquityValue delta) {

		SocialEquityValue value = new SocialEquityValue(
			_value.getK() + delta.getK(), _value.getB() + delta.getB());

		return new SocialEquityIncrement(value);
	}

	public void setValue(SocialEquityValue value) {
		_value = value;
	}

	private SocialEquityValue _value;

}