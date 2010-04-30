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

package com.liferay.portal.kernel.log;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * <a href="LogFactoryWrapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class LogFactoryWrapper implements LogFactory {

	public LogFactoryWrapper(LogFactory logFactory) {
		_logFactory = logFactory;
	}

	public void setLogFactory(LogFactory logFactory) {
		_logFactory = logFactory;

		Iterator<Map.Entry<String, List<Log>>> itr1 =
			_logs.entrySet().iterator();

		while (itr1.hasNext()) {
			Map.Entry<String, List<Log>> entry = itr1.next();

			String name = entry.getKey();
			List<Log> list = entry.getValue();

			Iterator<Log> itr2 = list.iterator();

			while (itr2.hasNext()) {
				LogWrapper logWrapper = (LogWrapper)itr2.next();

				logWrapper.setLog(_logFactory.getLog(name));
			}
		}
	}

	public Log getLog(Class<?> c) {
		return getLog(c.getName());
	}

	public Log getLog(String name) {
		List<Log> list = _logs.get(name);

		if (list == null) {
			list = new Vector<Log>();

			_logs.put(name, list);
		}

		Log log = new LogWrapper(_logFactory.getLog(name));

		list.add(log);

		return log;
	}

	private LogFactory _logFactory;
	private Map<String, List<Log>> _logs = new Hashtable<String, List<Log>>();

}