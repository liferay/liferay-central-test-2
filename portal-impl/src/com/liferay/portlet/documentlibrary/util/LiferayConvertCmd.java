/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import jodd.util.StringPool;

import org.im4java.core.ConvertCmd;

/**
 * @author Alexander Chow
 */
public class LiferayConvertCmd extends ConvertCmd {

	public static void run(
			String globalSearchPath, Properties resourceLimitsProperties,
			List<String> commandArguments)
		throws Exception {

		setGlobalSearchPath(globalSearchPath);

		LinkedList<String> arguments = new LinkedList<String>();

		arguments.addAll(_instance.getCommand());

		for (Object key : resourceLimitsProperties.keySet()) {
			String value = (String)resourceLimitsProperties.get(key);

			if (Validator.isNull(value)) {
				continue;
			}

			arguments.add("-limit");
			arguments.add((String)key);
			arguments.add(value);
		}

		arguments.addAll(commandArguments);

		if (_log.isInfoEnabled()) {
			StringBundler sb = new StringBundler(arguments.size() * 2);

			for (String argument : arguments) {
				sb.append(argument);
				sb.append(StringPool.SPACE);
			}

			_log.info("Excecuting command '" + sb.toString() + "'");
		}

		_instance.run(arguments);
	}

	private static Log _log = LogFactoryUtil.getLog(LiferayConvertCmd.class);

	private static LiferayConvertCmd _instance = new LiferayConvertCmd();

}