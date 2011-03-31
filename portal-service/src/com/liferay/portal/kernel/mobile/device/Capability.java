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

package com.liferay.portal.kernel.mobile.device;

import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

/**
 *
 * @author Milen Dyankov
 * @author Michael C. Han
 */
public class Capability implements Serializable {

	public Capability(String capabilityValue, String capabilityName) {
		_capabilityValue = capabilityValue;

		_capabilityName = capabilityName;
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		Capability capability = (Capability) obj;
		if (!_capabilityName.equals(capability.getCapabilityName())) {
			return false;
		}

		if (Validator.isNotNull(_capabilityValue)) {
			return _capabilityValue.equals(capability.getCapabilityValue());
		}

		if (Validator.isNotNull(capability.getCapabilityValue())) {
			return capability.getCapabilityValue().equals(_capabilityValue);
		}

		return true;
	}

	public String getCapabilityName() {
		return _capabilityName;
	}

	public String getCapabilityValue() {
		return _capabilityValue;
	}

	private String _capabilityName;
	private String _capabilityValue;

}