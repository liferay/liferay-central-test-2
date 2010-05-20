/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.util.PortalInitableUtil;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * <a href="PortalSessionListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortalSessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent event) {
		PortalSessionCreator portalSessionCreator =
			new PortalSessionCreator(event);

		PortalInitableUtil.init(portalSessionCreator);
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		PortalSessionDestroyer portalSessionDestroyer =
			new PortalSessionDestroyer(event);

		PortalInitableUtil.init(portalSessionDestroyer);

		ThreadLocalCacheManager.clearAll(Lifecycle.SESSION);
	}

}