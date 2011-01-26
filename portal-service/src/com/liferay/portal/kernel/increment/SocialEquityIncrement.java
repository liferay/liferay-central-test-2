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

package com.liferay.portal.kernel.increment;

import com.liferay.portlet.social.model.SocialEquityIncrementPayload;
import com.liferay.portlet.social.model.SocialEquityValue;

/**
 * @author Zsolt Berentey
 */
public class SocialEquityIncrement
	implements Increment<SocialEquityIncrementPayload> {

	public SocialEquityIncrement(
		SocialEquityIncrementPayload socialEquityIncrementPayload) {

		_socialEquityIncrementPayload = socialEquityIncrementPayload;
	}

	public void decrease(
		SocialEquityIncrementPayload deltaSocialEquityIncrementPayload) {

		SocialEquityValue socialEquityValue =
			_socialEquityIncrementPayload.getEquityValue();

		socialEquityValue.subtract(
			deltaSocialEquityIncrementPayload.getEquityValue());
	}

	public Increment<SocialEquityIncrementPayload> decreaseForNew(
		SocialEquityIncrementPayload deltaSocialEquityIncrementPayload) {

		SocialEquityIncrementPayload socialEquityIncrementPayload =
			_socialEquityIncrementPayload.clone();

		SocialEquityValue socialEquityValue = new SocialEquityValue(
			_socialEquityIncrementPayload.getEquityValue().getK() -
				deltaSocialEquityIncrementPayload.getEquityValue().getK(),
			_socialEquityIncrementPayload.getEquityValue().getB() -
				deltaSocialEquityIncrementPayload.getEquityValue().getB());

		socialEquityIncrementPayload.setEquityValue(socialEquityValue);

		return new SocialEquityIncrement(socialEquityIncrementPayload);
	}

	public SocialEquityIncrementPayload getValue() {
		return _socialEquityIncrementPayload;
	}

	public void increase(
		SocialEquityIncrementPayload deltaSocialEquityIncrementPayload) {

		SocialEquityValue socialEquityValue =
			_socialEquityIncrementPayload.getEquityValue();

		socialEquityValue.add(
			deltaSocialEquityIncrementPayload.getEquityValue());
	}

	public Increment<SocialEquityIncrementPayload> increaseForNew(
		SocialEquityIncrementPayload deltaSocialEquityIncrementPayload) {

		SocialEquityIncrementPayload socialEquityIncrementPayload =
			_socialEquityIncrementPayload.clone();

		SocialEquityValue socialEquityValue = new SocialEquityValue(
			_socialEquityIncrementPayload.getEquityValue().getK() +
				deltaSocialEquityIncrementPayload.getEquityValue().getK(),
			_socialEquityIncrementPayload.getEquityValue().getB() +
				deltaSocialEquityIncrementPayload.getEquityValue().getB());

		socialEquityIncrementPayload.setEquityValue(socialEquityValue);

		return new SocialEquityIncrement(socialEquityIncrementPayload);
	}

	public void setValue(
		SocialEquityIncrementPayload valueSocialEquityIncrementPayload) {

		_socialEquityIncrementPayload = valueSocialEquityIncrementPayload;
	}

	private SocialEquityIncrementPayload _socialEquityIncrementPayload;

}