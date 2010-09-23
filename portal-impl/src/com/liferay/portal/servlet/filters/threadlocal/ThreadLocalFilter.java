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

package com.liferay.portal.servlet.filters.threadlocal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ThreadLocalRegistry;
import com.liferay.portal.kernel.util.ThreadUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PropsValues;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ThreadLocalFilter extends BasePortalFilter {

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		ScheduledFuture scheduledFuture = null;
		if (PropsValues.SLOWNESS_DETECTER_ENABLED) {
			scheduledFuture = _scheduledExecutorService.schedule(_dumper,
				_slownessDetecterThreshold, TimeUnit.SECONDS);
		}

		try {
			processFilter(
				ThreadLocalFilter.class, request, response, filterChain);
		}
		finally {
			if (scheduledFuture != null) {
				scheduledFuture.cancel(true);
			}
			ThreadLocalRegistry.resetThreadLocals();
		}
	}

	private static class Dumper implements Runnable {

		public void run() {
			if (_log.isInfoEnabled()) {
				_log.info(ThreadUtil.threadDump());
			}
			else {
				_log.error(
					"Thread dumps require the log level to be at least INFO "
					+ "for " + ThreadLocalFilter.class.getName());
			}
		}

	}

	public static final int DEFAULT_SLOWNESS_DETECTER_THRESHOLD = 5;

	public static final boolean ENABLED = GetterUtil.getBoolean(
		PropsUtil.get(ThreadLocalFilter.class.getName()));

	public static final int MAX_DUMPER_THREAD_NUMBER = 5;

	private static Dumper _dumper;
	private static Log _log = LogFactoryUtil.getLog(ThreadLocalFilter.class);
	private static ScheduledExecutorService _scheduledExecutorService;
	private static int _slownessDetecterThreshold;

	static {
		if (PropsValues.SLOWNESS_DETECTER_ENABLED) {
			_dumper = new Dumper();
			_scheduledExecutorService = Executors.newScheduledThreadPool(
				MAX_DUMPER_THREAD_NUMBER);
			_slownessDetecterThreshold= PropsValues.SLOWNESS_DETECTER_THRESHOLD;
			if (_slownessDetecterThreshold <= 0) {
				_slownessDetecterThreshold =
					DEFAULT_SLOWNESS_DETECTER_THRESHOLD;
			}
		}
	}

}