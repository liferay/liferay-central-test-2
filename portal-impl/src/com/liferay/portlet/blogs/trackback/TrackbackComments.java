/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.trackback;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.service.ServiceContext;

/**
 * @author André de Oliveira
 */
public interface TrackbackComments {

	public long addTrackbackComment(
			long userId, long groupId, String className, long classPK,
			String blogName, String title, String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException, SystemException;

}