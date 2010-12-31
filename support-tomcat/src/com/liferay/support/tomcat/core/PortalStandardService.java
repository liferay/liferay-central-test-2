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

package com.liferay.support.tomcat.core;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.support.tomcat.connector.PortalConnector;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardService;

/**
 * <p>
 * See http://issues.liferay.com/browse/LEP-4602.
 * </p>
 *
 * @author Minhchau Dang
 */
public class PortalStandardService extends StandardService {

	public void addConnector(Connector connector) {

		try {
			connector = new PortalConnector(connector);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		super.addConnector(connector);
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortalStandardService.class);

}