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

package com.liferay.portal.util;

/**
 * <a href="PropsUtil_IW.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PropsUtil_IW {
	public static PropsUtil_IW getInstance() {
		return _instance;
	}

	public void addProperties(java.util.Properties properties) {
		PropsUtil.addProperties(properties);
	}

	public boolean contains(java.lang.String key) {
		return PropsUtil.contains(key);
	}

	public java.lang.String get(java.lang.String key) {
		return PropsUtil.get(key);
	}

	public java.lang.String get(java.lang.String key,
		com.liferay.portal.kernel.configuration.Filter filter) {
		return PropsUtil.get(key, filter);
	}

	public java.lang.String[] getArray(java.lang.String key) {
		return PropsUtil.getArray(key);
	}

	public java.lang.String[] getArray(java.lang.String key,
		com.liferay.portal.kernel.configuration.Filter filter) {
		return PropsUtil.getArray(key, filter);
	}

	public java.util.Properties getProperties() {
		return PropsUtil.getProperties();
	}

	public java.util.Properties getProperties(java.lang.String prefix,
		boolean removePrefix) {
		return PropsUtil.getProperties(prefix, removePrefix);
	}

	public void removeProperties(java.util.Properties properties) {
		PropsUtil.removeProperties(properties);
	}

	public void set(java.lang.String key, java.lang.String value) {
		PropsUtil.set(key, value);
	}

	private PropsUtil_IW() {
	}

	private static PropsUtil_IW _instance = new PropsUtil_IW();
}