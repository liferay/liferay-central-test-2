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

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author Milen Dyankov
 * @author Michael C. Han
 */
public interface Device extends Serializable {

	/**
	 * Returns device's brand name
	 *
	 * @return device's brand name
	 */
	public String getBrand();

	/**
	 * Returns web browser's name for this device
	 *
	 * @return web browser's name for this device
	 */
	public String getBrowser();

	/**
	 * Returns web browser's version for this device
	 *
	 * @return web browser's version for this device
	 */
	public String getBrowserVersion();

	/**
	 * Gets the value of given capability for this device
	 *
	 * @param name
	 *            the name of the capability
	 * @return the value of this capability for this device
	 */
	public String getCapability(String name);

	/**
	 * Provides map of all capabilities known to
	 * {@link com.liferay.portal.kernel.mobile.device.DeviceRecognitionProvider}
	 *
	 * @return map of all capabilities known to
	 *         {@link com.liferay.portal.kernel.mobile.device.DeviceRecognitionProvider}
	 */
	public Map<String, Capability> getCapabilities();

	/**
	 * Returns device's model name
	 *
	 * @return device's model name
	 */
	public String getModel();

	/**
	 * Returns operating system's name for this device
	 *
	 * @return operating system's name for this device
	 */
	public String getOS();

	/**
	 * Returns operating system's version for this device
	 *
	 * @return operating system's version for this device
	 */
	public String getOSVersion();

	/**
	 * Provides the recognized pointing method (touchscreen, trackball, ...)
	 *
	 * @return the recognized pointing method
	 */
	public String getPointingMethod();

	/**
	 * Returns <code>true</code> if device has QWERTY keyboard and
	 * <code>false</code> otherwise
	 *
	 * @return <code>true</code> if device has QWERTY keyboard and
	 *         <code>false</code> otherwise
	 */
	public boolean hasQwertyKeyboard();

	/**
	 * Obtains the screen size for the device
	 *
	 * @return screen size of device as a String
	 */
	public String getScreenSize();

	/**
	 * Returns <code>true</code> if device is tablet and <code>false</code>
	 * otherwise
	 *
	 * @return <code>true</code> if device is tablet and <code>false</code>
	 *         otherwise
	 */
	public boolean isTablet();

}