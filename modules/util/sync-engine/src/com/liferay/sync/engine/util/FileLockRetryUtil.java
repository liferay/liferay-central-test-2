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

package com.liferay.sync.engine.util;

import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shinn Lok
 */
public class FileLockRetryUtil {

	public static void registerFilePathCallable(
		FilePathCallable filePathCallable) {

		_filePathCallables.add(filePathCallable);
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		FileLockRetryUtil.class);

	private static final List<FilePathCallable> _filePathCallables =
		new ArrayList<>();

	static {
		ScheduledExecutorService scheduledExecutorService =
			Executors.newSingleThreadScheduledExecutor();

		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				Iterator<FilePathCallable> iterator =
					_filePathCallables.iterator();

				while (iterator.hasNext()) {
					FilePathCallable filePathCallable = iterator.next();

					FileChannel fileChannel = null;

					FileLock fileLock = null;

					try {
						Path filePath = filePathCallable.getFilePath();

						fileChannel = FileChannel.open(
							filePath, StandardOpenOption.READ,
							StandardOpenOption.WRITE);

						fileLock = FileUtil.getFileLock(fileChannel);

						if (fileLock != null) {
							filePathCallable.call();

							iterator.remove();
						}
					}
					catch (Exception e) {
						if (_logger.isDebugEnabled()) {
							_logger.debug(e.getMessage(), e);
						}
					}
					finally {
						FileUtil.releaseFileLock(fileLock);

						StreamUtil.cleanUp(fileChannel);
					}
				}
			}

		};

		scheduledExecutorService.scheduleAtFixedRate(
			runnable, 0, 1, TimeUnit.SECONDS);
	}

}