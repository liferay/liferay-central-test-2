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

package com.liferay.ant.build.logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Target;

/**
 * @author Kevin Yen
 */

public class LiferayBuildPerformanceLogger extends DefaultLogger {

	@Override
	public void targetFinished(BuildEvent buildEvent) {
		Target target = buildEvent.getTarget();

		long currentTime = System.currentTimeMillis();
		long startTime = _startTimes.get(target);

		long duration = currentTime - startTime;

		StringBuilder sb = new StringBuilder();

		sb.append("  [logging] ");
		sb.append(currentTime);
		sb.append(" : Target ");
		sb.append(target.getName());
		sb.append(" finished in ");
		sb.append(duration);
		sb.append("ms");

		printMessage(sb.toString(), out, buildEvent.getPriority());
	}

	@Override
	public void targetStarted(BuildEvent buildEvent) {
		Target target = buildEvent.getTarget();

		long currentTime = System.currentTimeMillis();

		_startTimes.put(target, currentTime);

		StringBuilder sb = new StringBuilder();

		sb.append("  [logging] ");
		sb.append(currentTime);
		sb.append(" : Target ");
		sb.append(target.getName());
		sb.append(" started");

		printMessage(sb.toString(), out, buildEvent.getPriority());
	}

	public LiferayBuildPerformanceLogger() {
		out = System.out;
		err = System.err;
	}

	private final Map<Target, Long> _startTimes = new HashMap<>();

}