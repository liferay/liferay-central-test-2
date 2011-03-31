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

/**
 * Implementations should provide information about all client devices known to
 * particular {@link com.liferay.portal.kernel.mobile.device.DeviceRecognitionProvider}
 *
 * @author Milen Dyankov
 * @author Michael C. Han
 */
public interface KnownDevices {

	/**
	 * Returns a map containing all known devices having given capability values
	 *
	 * @return a map containing all known devices having given capability values
	 */
	public Map<Capability, Set<String>> getDeviceIdsByCapabilities();

	/**
	 * Returns all known brands
	 *
	 * @return all known brands
	 */
	public Set<VersionableName> getBrands();

	/**
	 * Returns all known operating systems
	 *
	 * @return all known operating systems
	 */
	public Set<VersionableName> getOperatingSystems();

	/**
	 * Returns all known browsers
	 *
	 * @return all known browsers
	 */
	public Set<VersionableName> getBrowsers();

	/**
	 * Returns all known pointing methods
	 *
	 * @return all known pointing methods
	 */
	public Set<String> getPointingMethods();

}