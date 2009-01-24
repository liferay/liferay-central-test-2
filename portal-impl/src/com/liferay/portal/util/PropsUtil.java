/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.configuration.ConfigurationImpl;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.util.SystemProperties;

import java.util.Properties;

/**
 * <a href="PropsUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PropsUtil {

	public static void addProperties(Properties properties) {
		_instance._addProperties(properties);
	}

	public static boolean contains(String key) {
		return _instance._contains(key);
	}

	public static String get(String key) {
		return _instance._get(key);
	}

	public static String get(String key, Filter filter) {
		return _instance._get(key, filter);
	}

	public static String[] getArray(String key) {
		return _instance._getArray(key);
	}

	public static String[] getArray(String key, Filter filter) {
		return _instance._getArray(key, filter);
	}

	public static Properties getProperties() {
		return _instance._getProperties();
	}

	public static Properties getProperties(
		String prefix, boolean removePrefix) {

		return _instance._getProperties(prefix, removePrefix);
	}

	public static void removeProperties(Properties properties) {
		_instance._removeProperties(properties);
	}

	public static void set(String key, String value) {
		_instance._set(key, value);
	}

	private PropsUtil() {
		SystemProperties.set("default.liferay.home", _getDefaultLiferayHome());

		_configuration = new ConfigurationImpl(
			PropsUtil.class.getClassLoader(), PropsFiles.PORTAL);

		// Set the portal property "liferay.home" as a system property as well
		// so it can be referenced by Ehcache.

		SystemProperties.set(
			PropsKeys.LIFERAY_HOME, _get(PropsKeys.LIFERAY_HOME));

		// Set the portal property "resource.repositories.root" for backwards
		// compatibility.

		SystemProperties.set(
			PropsKeys.RESOURCE_REPOSITORIES_ROOT,
			_get(PropsKeys.RESOURCE_REPOSITORIES_ROOT));
	}

	private void _addProperties(Properties properties) {
		_configuration.addProperties(properties);
	}

	private boolean _contains(String key) {
		return _configuration.contains(key);
	}

	private String _get(String key) {
		return _configuration.get(key);
	}

	private String _get(String key, Filter filter) {
		return _configuration.get(key, filter);
	}

	private String[] _getArray(String key) {
		return _configuration.getArray(key);
	}

	private String[] _getArray(String key, Filter filter) {
		return _configuration.getArray(key, filter);
	}

	private String _getDefaultLiferayHome() {
		String defaultLiferayHome = null;

		if (ServerDetector.isGeronimo()) {
			defaultLiferayHome =
				SystemProperties.get("org.apache.geronimo.base.dir") + "/..";
		}
		else if (ServerDetector.isGlassfish()) {
			defaultLiferayHome =
				SystemProperties.get("com.sun.aas.instanceRoot") + "/..";
		}
		else if (ServerDetector.isJBoss()) {
			defaultLiferayHome =
				SystemProperties.get("jboss.server.home.dir") + "/..";
		}
		else if (ServerDetector.isJOnAS()) {
			defaultLiferayHome = SystemProperties.get("jonas.base") + "/..";
		}
		else if (ServerDetector.isWebLogic()) {
			defaultLiferayHome =
				SystemProperties.get("env.DOMAIN_HOME") + "/..";
		}
		else if (ServerDetector.isJetty()) {
			defaultLiferayHome = SystemProperties.get("jetty.home") + "/..";
		}
		else if (ServerDetector.isResin()) {
			defaultLiferayHome = SystemProperties.get("resin.home") + "/..";
		}
		else if (ServerDetector.isTomcat()) {
			defaultLiferayHome = SystemProperties.get("catalina.base") + "/..";
		}
		else {
			defaultLiferayHome = SystemProperties.get("user.home") + "/liferay";
		}

		defaultLiferayHome = StringUtil.replace(
			defaultLiferayHome, StringPool.BACK_SLASH, StringPool.SLASH);

		defaultLiferayHome = StringUtil.replace(
			defaultLiferayHome, StringPool.DOUBLE_SLASH, StringPool.SLASH);

		if (defaultLiferayHome.endsWith("/..")) {
			int pos = defaultLiferayHome.lastIndexOf(
				StringPool.SLASH, defaultLiferayHome.length() - 4);

			if (pos != -1) {
				defaultLiferayHome = defaultLiferayHome.substring(0, pos);
			}
		}

		return defaultLiferayHome;
	}

	private Properties _getProperties() {
		return _configuration.getProperties();
	}

	private Properties _getProperties(String prefix, boolean removePrefix) {
		return _configuration.getProperties(prefix, removePrefix);
	}

	private void _removeProperties(Properties properties) {
		_configuration.removeProperties(properties);
	}

	private void _set(String key, String value) {
		_configuration.set(key, value);
	}

	private static PropsUtil _instance = new PropsUtil();

	private Configuration _configuration;

}