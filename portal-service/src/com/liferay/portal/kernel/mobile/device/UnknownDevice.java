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

import java.util.Collections;
import java.util.Map;

/**
 * Class represents unknown device
 *
 * @author Milen Dyankov
 */
public class UnknownDevice extends AbstractDevice {

	public static final String UNKNOWN = "unknown";

	public static UnknownDevice getInstance() {
		return _instance;
	}

	public Map<String, Capability> getCapabilities() {
		return Collections.emptyMap();
	}

	public String getCapability(String name) {
		return null;
	}

	public String getBrand() {
		return UNKNOWN;
	}

	public String getModel() {
		return UNKNOWN;
	}

	public String getOS() {
		return UNKNOWN;
	}

	public String getOSVersion() {
		return UNKNOWN;
	}

	public String getBrowser() {
		return UNKNOWN;
	}

	public String getBrowserVersion() {
		return UNKNOWN;
	}

	public String getPointingMethod() {
		return UNKNOWN;
	}

	public String getScreenSize() {
		return UNKNOWN;
	}

	public boolean isTablet() {
		return false;
	}

	public boolean hasQwertyKeyboard() {
		return true;
	}

	private UnknownDevice() {
	}

	private static UnknownDevice _instance = new UnknownDevice();

}