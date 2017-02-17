/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.adaptive.media.processor.content;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = ContentProcessor.class)
public class ContentProcessor {

	public <T> T process(ContentType<T> contentType, T originalContent) {
		try {
			List<ConcreteContentProcessor> processors = ListUtil.fromCollection(
				_processorsMap.getService(contentType));

			T processedContent = originalContent;

			for (ConcreteContentProcessor<T> processor : processors) {
				processedContent = processor.process(processedContent);
			}

			return processedContent;
		}
		catch (Exception e) {
			_log.error(e);
			return originalContent;
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_processorsMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, ConcreteContentProcessor.class, null,
			(serviceReference, emitter) -> {
				ConcreteContentProcessor processor = bundleContext.getService(
					serviceReference);

				emitter.emit(processor.getContentType());

				bundleContext.ungetService(serviceReference);
			});
	}

	@Deactivate
	protected void deactivate() {
		_processorsMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentProcessor.class);

	private ServiceTrackerMap<ContentType, List<ConcreteContentProcessor>>
		_processorsMap;

}