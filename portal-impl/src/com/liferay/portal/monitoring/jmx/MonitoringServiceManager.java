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

package com.liferay.portal.monitoring.jmx;

import com.liferay.portal.monitoring.Level;
import com.liferay.portal.monitoring.MonitoringService;

import java.util.Set;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class MonitoringServiceManager implements MonitoringServiceManagerMBean {

	public String getLevel(String namespace) {
		Level level = _monitoringService.getLevel(
			namespace);

		if (level == null) {
			level = Level.OFF;
		}

		return level.toString();
	}

	public String[] getNamespaces() {
		Set<String> namespaces = _monitoringService.getNamespaces();

		return namespaces.toArray(new String[namespaces.size()]);
	}

	public void setLevel(String namespace, String levelName) {
		Level level = Level.valueOf(levelName);

		_monitoringService.setLevel(namespace, level);
	}

	public void setMonitoringService(MonitoringService monitoringService) {
		_monitoringService = monitoringService;
	}

	private MonitoringService _monitoringService;

}