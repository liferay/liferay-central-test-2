/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.server.capabilities;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.server.DeepNamedValueScanner;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 * @author Igor Spasic
 */
public class TomcatServerCapabilities implements ServerCapabilities {

	public void determine(ServletContext servletContext) throws Exception {
		determineSupportsHotDeploy(servletContext);
	}

	public boolean isSupportsHotDeploy() {
		return _supportsHotDeploy;
	}

	protected void determineSupportsHotDeploy(ServletContext servletContext)
		throws Exception {

		DeepNamedValueScanner deepNamedValueScanner =
			new DeepNamedValueScanner("autoDeploy");

		deepNamedValueScanner.scan(servletContext);

		if (_log.isDebugEnabled()) {
			if (!deepNamedValueScanner.isScanning()) {
				StringBundler sb = new StringBundler(5);

				sb.append("Deep named value scanner found ");
				sb.append(deepNamedValueScanner.getMatchingCount());
				sb.append(" matches in ");
				sb.append(deepNamedValueScanner.getElapsedTime());
				sb.append(" ms");

				_log.debug(sb.toString());
			}
			else {
				_log.debug("Deep named value scanner did not finish scanning");
			}
		}

		Boolean autoDeployValue =
			(Boolean)deepNamedValueScanner.getMatchedValue();

		_supportsHotDeploy = autoDeployValue.booleanValue();
	}

	private static Log _log = LogFactoryUtil.getLog(
		TomcatServerCapabilities.class);

	private boolean _supportsHotDeploy;

}