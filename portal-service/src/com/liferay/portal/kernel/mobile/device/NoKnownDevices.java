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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class represents unknown device
 *
 * @author Milen Dyankov
 * @author Michael C. Han
 */
public class NoKnownDevices implements KnownDevices {

	public static NoKnownDevices getInstance() {
		return _instance;
	}

	public Map<Capability, Set<String>> getDeviceIdsByCapabilities() {
		return Collections.emptyMap();
	}

	public Set<VersionableName> getBrands() {
		return Collections.unmodifiableSet(_brands);
	}

	public Set<VersionableName> getOperatingSystems() {
		return Collections.unmodifiableSet(_oses);
	}

	public Set<VersionableName> getBrowsers() {
		return Collections.unmodifiableSet(_browsers);
	}

	public Set<String> getPointingMethods() {
		return Collections.unmodifiableSet(_pointingMethods);
	}

	private NoKnownDevices() {
		_brands.add(
			new VersionableName(UnknownDevice.UNKNOWN, UnknownDevice.UNKNOWN));

		_browsers.add(
			new VersionableName(UnknownDevice.UNKNOWN, UnknownDevice.UNKNOWN));

		_oses.add(
			new VersionableName(UnknownDevice.UNKNOWN, UnknownDevice.UNKNOWN));

		_pointingMethods.add(UnknownDevice.UNKNOWN);
	}

	private static NoKnownDevices _instance = new NoKnownDevices();

	private Set<VersionableName> _brands = new HashSet<VersionableName>();
	private Set<VersionableName> _browsers = new HashSet<VersionableName>();
	private Set<VersionableName> _oses = new HashSet<VersionableName>();
	private Set<String> _pointingMethods = new HashSet<String>();

}