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

import com.liferay.portal.kernel.messaging.proxy.MessagingProxy;
import com.liferay.portal.kernel.messaging.proxy.ProxyMode;

import javax.servlet.http.HttpServletRequest;

/**
 * Contract for device recognition providers. Implementors should be able to
 * provide information about all known devices and must be able to recognize
 * user's device form provided {@link HttpServletRequest} object.
 *
 * @author Milen Dyankov
 * @author Michael C. Han
 */
@MessagingProxy(mode = ProxyMode.SYNC)
public interface DeviceRecognitionProvider {

	/**
	 * Detects the mobile device based on information provided in the
	 * HttpServletRequest
	 *
	 * @param httpServletRequest the servlet request containing device details
	 * @return the detected device
	 */
	public Device detectDevice(HttpServletRequest httpServletRequest);

	/**
	 * Obtains all know devices for this provider
	 *
	 * @return all known devices for this provider
	 */
	public KnownDevices getKnownDevices();

}