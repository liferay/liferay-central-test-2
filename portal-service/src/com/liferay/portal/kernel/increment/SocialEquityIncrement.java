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

	public SocialEquityIncrement(SocialEquityIncrementPayload value) {
		_payload = value;
	}

	public void decrease(SocialEquityIncrementPayload delta) {
		_payload.getEquityValue().subtract(delta.getEquityValue());
	}

	public Increment<SocialEquityIncrementPayload> decreaseForNew(
		SocialEquityIncrementPayload delta) {

		SocialEquityIncrementPayload payload = _payload.clone();

		payload.setEquityValue(new SocialEquityValue(
			_payload.getEquityValue().getK() - delta.getEquityValue().getK(),
			_payload.getEquityValue().getB() - delta.getEquityValue().getB()
		));

		return new SocialEquityIncrement(payload);
	}

	public SocialEquityIncrementPayload getValue() {
		return _payload;
	}

	public void increase(SocialEquityIncrementPayload delta) {
		_payload.getEquityValue().add(delta.getEquityValue());
	}

	public Increment<SocialEquityIncrementPayload> increaseForNew(
		SocialEquityIncrementPayload delta) {

		SocialEquityIncrementPayload payload = _payload.clone();

		payload.setEquityValue(new SocialEquityValue(
			_payload.getEquityValue().getK() + delta.getEquityValue().getK(),
			_payload.getEquityValue().getB() + delta.getEquityValue().getB()
		));

		return new SocialEquityIncrement(payload);
	}

	public void setValue(SocialEquityIncrementPayload value) {
		_payload = value;
	}

	private SocialEquityIncrementPayload _payload;

}