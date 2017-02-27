/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.adaptive.media.blogs.web.internal.filter;

import com.liferay.adaptive.media.content.transformer.ContentTransformerHandler;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;

import java.util.Iterator;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Alejandro Tardín
 */
public class ContentTransformerUtil {

	public static ContentTransformerHandler getContentTransformerHandler() {
		if (_contentTransformerHandler == null) {
			_contentTransformerHandler = _getContentTransformerHandler();
		}

		return _contentTransformerHandler;
	}

	private static ContentTransformerHandler _getContentTransformerHandler() {
		BundleContext bundleContext = FrameworkUtil.getBundle(
			ContentTransformerUtil.class).getBundleContext();

		ServiceTrackerList<ContentTransformerHandler, ContentTransformerHandler>
			contentTransformerHandlers = ServiceTrackerListFactory.open(
				bundleContext, ContentTransformerHandler.class);

		Iterator<ContentTransformerHandler> iterator =
			contentTransformerHandlers.iterator();

		if (iterator.hasNext()) {
			return iterator.next();
		}

		return null;
	}

	private static ContentTransformerHandler _contentTransformerHandler;

}