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

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * This class provides convenient methods for device recognition
 *
 * @author Milen Dyankov
 */
public class DeviceDetectionUtil {

	public static Device detectDevice(HttpServletRequest request) {
		return _deviceRecognitionProvider.detectDevice(request);
	}

	/**
	 * Obtains a list of available brand versions.  The list contains the brand
	 * and version numbers
	 *
	 * @return all known brands and models
	 */
	public static Set<VersionableName> getKnownBrands() {
		KnownDevices knownDevices =
			_deviceRecognitionProvider.getKnownDevices();

		return knownDevices.getBrands();
	}

	/**
	 * Obtains a list of known mobile browsers.
	 *
	 * @return all known browsers and versions
	 */
	public Set<VersionableName> getKnownBrowsers() {
		KnownDevices knownDevices =
			_deviceRecognitionProvider.getKnownDevices();

		return knownDevices.getBrowsers();
	}

	/**
	 * Obtains all known devices with a specified capability value
	 *
	 * @param capability the name and value of the capability
	 * @return all known devices having given capability value
	 */
	public static Set<String> getKnownDeviceIdsByCapability(
		Capability capability) {

		KnownDevices knownDevices =
			_deviceRecognitionProvider.getKnownDevices();

		Map<Capability, Set<String>> capabilityDeviceIdsMap =
			knownDevices.getDeviceIdsByCapabilities();

		return capabilityDeviceIdsMap.get(capability);
	}

	/**
	 * Obtain a list of known operating systems.  List comprised of OS name and
	 * version
	 *
	 * @return all known operating systems and versions
	 */
	public static Set<VersionableName> getKnownOperatingSystems() {
		KnownDevices knownDevices =
			_deviceRecognitionProvider.getKnownDevices();

		return knownDevices.getOperatingSystems();
	}

	/**
	 * Obtains a list of known pointing methods.
	 *
	 * @return all known pointing methods
	 */
	public static Set<String> getKnownPointingMethods() {
		KnownDevices knownDevices =
			_deviceRecognitionProvider.getKnownDevices();

		return knownDevices.getPointingMethods();
	}

	public void setDeviceRecognitionProvider(
		DeviceRecognitionProvider deviceRecognitionProvider) {

		_deviceRecognitionProvider = deviceRecognitionProvider;
	}

	private static DeviceRecognitionProvider _deviceRecognitionProvider;

}