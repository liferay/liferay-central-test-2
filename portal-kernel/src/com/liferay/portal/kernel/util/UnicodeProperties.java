/*
 * Copyright (c) 2008, Your Corporation. All Rights Reserved.
 */

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import java.util.HashMap;

/**
 * <a href="UnicodeProperties.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * This is a rewrite of java.util.Properties that is not synchronized and
 * natively supports non-ASCII encodings. It can also be configured to be
 * "safe", allowing the values to have new line characters. When stored to a
 * given BufferedWriter, "safe" properties will replace all new line characters
 * with a _SAFE_NEWLINE_CHARACTER_.
 * </p>
 *
 * <p>
 * In its current form, this is not intended to replace java.util.Properties for
 * reading properties flat files.
 * </p>
 *
 * @author Alexander Chow
 *
 */
public class UnicodeProperties extends HashMap<String, String> {

	public UnicodeProperties() {
		super();
	}

	public UnicodeProperties(boolean safe) {
		super();

		_safe = safe;
	}

	public String getProperty(String key) {
		return get(key);
	}

	public String getProperty(String key, String defaultValue) {
		if (containsKey(key)) {
			return getProperty(key);
		}
		else {
			return defaultValue;
		}
	}

	public boolean isSafe() {
		return _safe;
	}

	public void load(String props) throws IOException {
		if (Validator.isNull(props)) {
			return;
		}

		BufferedReader br = null;

		try {
			br = new BufferedReader(new StringReader(props));

			String line = br.readLine();

			while (line != null) {
				line = line.trim();

				if (_isComment(line)) {
					line = br.readLine();

					continue;
				}

				int pos = line.indexOf(StringPool.EQUAL);

				if (pos != -1) {
					String key = line.substring(0, pos).trim();
					String value = line.substring(pos + 1).trim();

					if (_safe) {
						value = _decode(value);
					}

					setProperty(key, value);
				}
				else {
					_log.error("Invalid property on line " + line);
				}

				line = br.readLine();
			}
		}
		finally {
			if (br != null) {
				try {
					br.close();
				}
				catch (Exception e) {
				}
			}
		}
	}

	public String put(String key, String value) {
		if (key == null) {
			return null;
		}
		else {
			if (value == null) {
                _length -= key.length() - 2;
                return remove(key);
			}
			else {
				return super.put(key, value);
			}
		}
	}

	public String remove(Object key) {
		if (key == null) {
			return null;
		}
		else {
            //_length -= key.length() - 2;
			return super.remove(key);
		}
	}

	public String setProperty(String key, String value) {
        _length += key.length() + value.length() + 2;
        return put(key, value);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder(_length);

		for (String key : keySet()) {
			String value = get(key);

			if (Validator.isNotNull(value)) {
				if (_safe) {
					value = _encode(value);
				}

				sb.append(key + StringPool.EQUAL + value + StringPool.NEW_LINE);
			}
		}

		return sb.toString();
	}

	private static String _decode(String value) {
		return StringUtil.replace(
			value, _SAFE_NEWLINE_CHARACTER, StringPool.NEW_LINE);
	}

	private static String _encode(String value) {
		return StringUtil.replace(
			value,
			new String[] {
				StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE,
				StringPool.RETURN
			},
			new String[] {
				_SAFE_NEWLINE_CHARACTER, _SAFE_NEWLINE_CHARACTER,
				_SAFE_NEWLINE_CHARACTER
			});
	}

	private boolean _isComment(String line) {
		return line.length() == 0 || line.startsWith(StringPool.POUND);
	}

	private static final String _SAFE_NEWLINE_CHARACTER =
		"_SAFE_NEWLINE_CHARACTER_";

	private static Log _log = LogFactoryUtil.getLog(UnicodeProperties.class);

	private boolean _safe = false;
    private int _length;

}