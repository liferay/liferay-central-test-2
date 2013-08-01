/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Polesovsky
 * @author Raymond Augé
 */
public class SanitizerLogWrapper extends LogWrapper {

	public static void init() {
		_LOG_SANITIZER_ENABLED = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LOG_SANITIZER_ENABLED));

		if (!_LOG_SANITIZER_ENABLED) {
			return;
		}

		_LOG_SANITIZER_ESCAPE_HTML_ENABLED = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LOG_SANITIZER_ESCAPE_HTML_ENABLED));

		_LOG_SANITIZER_REPLACEMENT_CHARACTER = (char)GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.LOG_SANITIZER_REPLACEMENT_CHARACTER));

		int[] whitelistCharacters = GetterUtil.getIntegerValues(
			PropsUtil.getArray(PropsKeys.LOG_SANITIZER_WHITELIST_CHARACTERS));

		for (int whitelistCharacter : whitelistCharacters) {
			if ((whitelistCharacter >= 0) &&
				(whitelistCharacter < _whitelistCharacters.length)) {

				_whitelistCharacters[whitelistCharacter] = 1;
			}
			else {
				System.err.println(
					"Unable to register log whitelist character " +
						whitelistCharacter);
			}
		}
	}

	public SanitizerLogWrapper(Log log) {
		super(log);
	}

	@Override
	public void debug(Object msg) {
		if (!isDebugEnabled()) {
			return;
		}

		super.debug(sanitize(msg));
	}

	@Override
	public void debug(Object msg, Throwable t) {
		if (!isDebugEnabled()) {
			return;
		}

		super.debug(sanitize(msg), sanitize(t));
	}

	@Override
	public void debug(Throwable t) {
		if (!isDebugEnabled()) {
			return;
		}

		super.debug(sanitize(t));
	}

	@Override
	public void error(Object msg) {
		if (!isErrorEnabled()) {
			return;
		}

		super.error(sanitize(msg));
	}

	@Override
	public void error(Object msg, Throwable t) {
		if (!isErrorEnabled()) {
			return;
		}

		super.error(sanitize(msg), sanitize(t));
	}

	@Override
	public void error(Throwable t) {
		if (!isErrorEnabled()) {
			return;
		}

		super.error(sanitize(t));
	}

	@Override
	public void fatal(Object msg) {
		if (!isFatalEnabled()) {
			return;
		}

		super.fatal(sanitize(msg));
	}

	@Override
	public void fatal(Object msg, Throwable t) {
		if (!isFatalEnabled()) {
			return;
		}

		super.fatal(sanitize(msg), sanitize(t));
	}

	@Override
	public void fatal(Throwable t) {
		if (!isFatalEnabled()) {
			return;
		}

		super.fatal(sanitize(t));
	}

	@Override
	public void info(Object msg) {
		if (!isInfoEnabled()) {
			return;
		}

		super.info(sanitize(msg));
	}

	@Override
	public void info(Object msg, Throwable t) {
		if (!isInfoEnabled()) {
			return;
		}

		super.info(sanitize(msg), sanitize(t));
	}

	@Override
	public void info(Throwable t) {
		if (!isInfoEnabled()) {
			return;
		}

		super.info(sanitize(t));
	}

	@Override
	public void trace(Object msg) {
		if (!isTraceEnabled()) {
			return;
		}

		super.trace(sanitize(msg));
	}

	@Override
	public void trace(Object msg, Throwable t) {
		if (!isTraceEnabled()) {
			return;
		}

		super.trace(sanitize(msg), sanitize(t));
	}

	@Override
	public void trace(Throwable t) {
		if (!isTraceEnabled()) {
			return;
		}

		super.trace(sanitize(t));
	}

	@Override
	public void warn(Object msg) {
		if (!isWarnEnabled()) {
			return;
		}

		super.warn(sanitize(msg));
	}

	@Override
	public void warn(Object msg, Throwable t) {
		if (!isWarnEnabled()) {
			return;
		}

		super.warn(sanitize(msg), sanitize(t));
	}

	@Override
	public void warn(Throwable t) {
		if (!isWarnEnabled()) {
			return;
		}

		super.warn(sanitize(t));
	}

	protected String sanitize(Object obj) {
		if (obj == null) {
			return null;
		}

		String message = obj.toString();

		return sanitize(message, message);
	}

	protected String sanitize(String message, String defaultResult) {
		if (!_LOG_SANITIZER_ENABLED) {
			return message;
		}

		if (message == null) {
			return null;
		}

		char[] chars = message.toCharArray();
		boolean sanitized = false;

		for (int i = 0; i < chars.length; i++) {
			int c = chars[i];

			if ((c >= 0) && (c < _whitelistCharacters.length) &&
				(_whitelistCharacters[c] == 0)) {

				chars[i] = _LOG_SANITIZER_REPLACEMENT_CHARACTER;
				sanitized = true;
			}
		}

		if (sanitized) {
			String sanitizedMessage = new String(chars);

			sanitizedMessage = sanitizedMessage.concat(_SANITIZED);

			if (_LOG_SANITIZER_ESCAPE_HTML_ENABLED) {
				return HtmlUtil.escape(sanitizedMessage);
			}

			return sanitizedMessage;
		}

		return defaultResult;
	}

	protected Throwable sanitize(Throwable throwable) {
		if (!_LOG_SANITIZER_ENABLED) {
			return throwable;
		}

		List<Throwable> throwables = new ArrayList<Throwable>();

		Throwable tempThrowable = throwable;

		while (tempThrowable != null) {
			throwables.add(tempThrowable);

			tempThrowable = tempThrowable.getCause();
		}

		Throwable resultThrowable = null;

		boolean sanitized = false;

		for (int i = throwables.size() - 1; i > - 1; i--) {
			Throwable curThrowable = throwables.get(i);

			String message = curThrowable.toString();

			String sanitizedMessage = sanitize(message, null);

			if (!sanitized && (sanitizedMessage == null)) {
				resultThrowable = curThrowable;

				continue;
			}

			if (sanitizedMessage == null) {
				sanitizedMessage = message;
			}

			sanitized = true;

			resultThrowable = new LogSanitizerException(
				sanitizedMessage, curThrowable.getStackTrace(),
				resultThrowable);
		}

		return resultThrowable;
	}

	private static final String _SANITIZED = " [Sanitized]";

	private static boolean _LOG_SANITIZER_ENABLED = false;

	private static boolean _LOG_SANITIZER_ESCAPE_HTML_ENABLED = false;

	private static char _LOG_SANITIZER_REPLACEMENT_CHARACTER =
		CharPool.UNDERLINE;

	private static int[] _whitelistCharacters = new int[128];

}