/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.struts;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.net.URL;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.ServletContext;

import org.apache.struts.util.MessageResourcesFactory;
import org.apache.struts.util.PropertyMessageResources;

/**
 * <a href="MultiMessageResources.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 */
public class MultiMessageResources extends PropertyMessageResources {

	public MultiMessageResources(
		MessageResourcesFactory factory, String config) {

		this(factory, config, true);
	}

	public MultiMessageResources(
		MessageResourcesFactory factory, String config, boolean returnNull) {

		super(factory, config, returnNull);

		_localeReadWriteLock = new ReentrantReadWriteLock();

		_localeReadLock = _localeReadWriteLock.readLock();
		_localeWriteLock = _localeReadWriteLock.writeLock();

		_messagesReadWriteLock = new ReentrantReadWriteLock();

		_messagesReadLock = _messagesReadWriteLock.readLock();
		_messagesWriteLock = _messagesReadWriteLock.writeLock();
	}

	public Map<String, String> getMessages() {
		_messagesReadLock.lock();

		try {
			return messages;
		}
		finally {
			_messagesReadLock.unlock();
		}
	}

	public void putLocale(String localeKey) {
		_localeWriteLock.lock();

		try {
			locales.put(localeKey, localeKey);
		}
		finally {
			_localeWriteLock.unlock();
		}
	}

	public Properties putMessages(Properties properties, String localeKey) {
		Properties oldProperties = new Properties();

		if (properties.size() < 1) {
			return oldProperties;
		}

		_messagesWriteLock.lock();

		try {
			Enumeration<Object> names = properties.keys();

			while (names.hasMoreElements()) {
				String key = (String)names.nextElement();

				String message = getMessage(
					LocaleUtil.fromLanguageId(localeKey), key);

				if (message != null) {
					oldProperties.put(key, message);
				}

				messages.put(
					messageKey(localeKey, key), properties.getProperty(key));
			}
		}
		finally {
			_messagesWriteLock.unlock();
		}

		return oldProperties;
	}

	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	public void loadLocale(String localeKey) {
		_localeReadLock.lock();

		try {
			if (locales.containsKey(localeKey)) {
				return;
			}
		}
		finally {
			_localeReadLock.unlock();
		}

		_localeWriteLock.lock();

		try {
			if (locales.containsKey(localeKey)) {
				return;
			}

			locales.put(localeKey, localeKey);
		}
		finally {
			_localeWriteLock.unlock();
		}

		String[] names = StringUtil.split(
			config.replace(StringPool.PERIOD, StringPool.SLASH));

		for (int i = 0; i < names.length; i++) {
			String name = names[i];

			if (localeKey.length() > 0) {
				name += "_" + localeKey;
			}

			name += ".properties";

			_loadProperties(name, localeKey, false);
		}

		for (int i = 0; i < names.length; i++) {
			String name = names[i];

			if (localeKey.length() > 0) {
				name += "_" + localeKey;
			}

			name += ".properties";

			_loadProperties(name, localeKey, true);
		}
	}

	private void _loadProperties(
		String name, String localeKey, boolean useServletContext) {

		Properties properties = new Properties();

		try {
			URL url = null;

			if (useServletContext) {
				url = _servletContext.getResource("/WEB-INF/" + name);
			}
			else {
				ClassLoader classLoader = getClass().getClassLoader();

				url = classLoader.getResource(name);
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					"Attempting to load " + name + " " + localeKey + " " +
						useServletContext);
			}

			if (url != null) {
				InputStream is = url.openStream();

				properties.load(is);

				is.close();

				if (_log.isInfoEnabled()) {
					_log.info(
						"Loading " + url + " with " + properties.size() +
							" values");
				}
			}
		}
		catch (Exception e) {
			_log.warn(e);
		}

		putMessages(properties, localeKey);
	}

	private static Log _log =
		LogFactoryUtil.getLog(MultiMessageResources.class);

	private Lock _localeReadLock;
	private ReadWriteLock _localeReadWriteLock;
	private Lock _localeWriteLock;
	private Lock _messagesReadLock;
	private ReadWriteLock _messagesReadWriteLock;
	private Lock _messagesWriteLock;
	private transient ServletContext _servletContext;

}