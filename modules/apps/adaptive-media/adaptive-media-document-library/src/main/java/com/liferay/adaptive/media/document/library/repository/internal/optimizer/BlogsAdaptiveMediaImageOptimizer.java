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

package com.liferay.adaptive.media.document.library.repository.internal.optimizer;

import com.liferay.adaptive.media.AdaptiveMediaException;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationHelper;
import com.liferay.adaptive.media.image.counter.AdaptiveMediaImageCounter;
import com.liferay.adaptive.media.image.optimizer.AdaptiveMediaImageOptimizer;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;
import com.liferay.adaptive.media.web.internal.background.task.OptimizeImagesBackgroundTaskConstants;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageSender;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.blogs.model.BlogsEntry"},
	service = AdaptiveMediaImageOptimizer.class
)
public class BlogsAdaptiveMediaImageOptimizer
	implements AdaptiveMediaImageOptimizer {

	@Override
	public void optimize(long companyId) {
		Collection<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				companyId);

		int total =
			counter.countExpectedAdaptiveMediaImages(companyId) *
				configurationEntries.size();

		final AtomicInteger counter = new AtomicInteger(0);

		for (ImageAdaptiveMediaConfigurationEntry configurationEntry :
				configurationEntries) {

			_optimize(companyId, configurationEntry.getUUID(), total, counter);
		}
	}

	@Override
	public void optimize(long companyId, String configurationEntryUuid) {
		int total = counter.countExpectedAdaptiveMediaImages(companyId);

		final AtomicInteger counter = new AtomicInteger(0);

		_optimize(companyId, configurationEntryUuid, total, counter);
	}

	private void _optimize(
		long companyId, String configurationEntryUuid, int total,
		AtomicInteger counter) {

		ActionableDynamicQuery actionableDynamicQuery =
			_dlFileEntryLocalService.getActionableDynamicQuery();

		long classNameId = _classNameLocalService.getClassNameId(
			BlogsEntry.class.getName());

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property companyIdProperty = PropertyFactoryUtil.forName(
						"companyId");

					dynamicQuery.add(companyIdProperty.eq(companyId));

					Property classNameIdProperty = PropertyFactoryUtil.forName(
						"classNameId");

					dynamicQuery.add(classNameIdProperty.eq(classNameId));

					Property mimeTypeProperty = PropertyFactoryUtil.forName(
						"mimeType");

					dynamicQuery.add(
						mimeTypeProperty.in(_SUPPORTED_IMAGE_MIME_TYPES));
				}

			});

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<DLFileEntry>() {

				@Override
				public void performAction(DLFileEntry dlFileEntry)
					throws PortalException {

					FileEntry fileEntry = new LiferayFileEntry(dlFileEntry);

					try {
						_processor.process(
							fileEntry.getFileVersion(), configurationEntryUuid);

						_sendStatusMessage(counter.incrementAndGet(), total);
					}
					catch (AdaptiveMediaException | PortalException e) {
						_log.error(
							"Unable to process file entry id " +
								fileEntry.getFileEntryId(),
							e);
					}
				}

			});

		try {
			actionableDynamicQuery.performActions();
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}
	}

	private void _sendStatusMessage(int count, int total) {
		Message message = new Message();

		message.put(
			BackgroundTaskConstants.BACKGROUND_TASK_ID,
			BackgroundTaskThreadLocal.getBackgroundTaskId());
		message.put(
			OptimizeImagesBackgroundTaskConstants.CLASS_NAME,
			getClass().getName());
		message.put(OptimizeImagesBackgroundTaskConstants.COUNT, count);
		message.put(OptimizeImagesBackgroundTaskConstants.TOTAL, total);

		message.put("status", BackgroundTaskConstants.STATUS_IN_PROGRESS);

		_backgroundTaskStatusMessageSender.sendBackgroundTaskStatusMessage(
			message);
	}

	private static final String[] _SUPPORTED_IMAGE_MIME_TYPES = new String[] {
		"image/bmp", "image/gif", "image/jpeg", "image/pjpeg", "image/png",
		"image/tiff", "image/x-citrix-jpeg", "image/x-citrix-png",
		"image/x-ms-bmp", "image/x-png", "image/x-tiff"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		BlogsAdaptiveMediaImageOptimizer.class);

	@Reference(target = "(class.name=com.liferay.blogs.model.BlogsEntry)")
	AdaptiveMediaImageCounter counter;

	@Reference
	private BackgroundTaskStatusMessageSender
		_backgroundTaskStatusMessageSender;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ImageAdaptiveMediaConfigurationHelper _configurationHelper;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private ImageAdaptiveMediaProcessor _processor;

}