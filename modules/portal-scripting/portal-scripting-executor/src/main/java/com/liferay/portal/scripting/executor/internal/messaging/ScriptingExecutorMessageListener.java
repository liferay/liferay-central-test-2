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

package com.liferay.portal.scripting.executor.internal.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scripting.Scripting;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.scripting.executor.internal.ScriptingExecutorMessagingConstants;

import java.io.InputStream;

import java.net.URL;

import java.util.HashMap;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class ScriptingExecutorMessageListener extends BaseMessageListener {

	public ScriptingExecutorMessageListener(Scripting scripting) {
		_scripting = scripting;
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		String scriptingLanguage = message.getString(
			ScriptingExecutorMessagingConstants.
				SCRIPTING_LANGUAGE_MESSAGE_ATTRIBUTE);

		List<URL> urls = (List<URL>)message.get(
			ScriptingExecutorMessagingConstants.URLS_MESSAGE_ATTRIBUTE);

		for (URL url : urls) {
			try (InputStream inputStream = url.openStream()) {
				_scripting.exec(
					null, new HashMap<String, Object>(), scriptingLanguage,
					StringUtil.read(inputStream));
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to execute script " + url.getFile(), e);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ScriptingExecutorMessageListener.class);

	private final Scripting _scripting;

}