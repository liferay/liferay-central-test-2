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

package com.liferay.adaptive.media.content.transformer;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = ContentTransformerHandler.class)
public class ContentTransformerHandler {

	public <T> T transform(
		ContentTransformerContentType<T> contentType, T originalContent) {

		List<ContentTransformer> contentTransformers =
			_serviceTrackerMap.getService(contentType);

		if (contentTransformers == null) {
			return originalContent;
		}

		T transformedContent = originalContent;

		for (ContentTransformer<T> contentTransformer : contentTransformers) {
			try {
				transformedContent = contentTransformer.transform(
					transformedContent);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return transformedContent;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, ContentTransformer.class, null,
			(serviceReference, emitter) -> {
				ContentTransformer contentTransformer =
					bundleContext.getService(serviceReference);

				emitter.emit(contentTransformer.getContentType());

				bundleContext.ungetService(serviceReference);
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	protected final void setServiceTrackerMap(
		ServiceTrackerMap
			<ContentTransformerContentType, List<ContentTransformer>>
				serviceTrackerMap) {

		_serviceTrackerMap = serviceTrackerMap;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentTransformerHandler.class);

	private ServiceTrackerMap
		<ContentTransformerContentType, List<ContentTransformer>>
			_serviceTrackerMap;

}