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

package com.liferay.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.InvocationTargetException;

import java.util.Comparator;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * <a href="PropertyComparator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Patrick Brady
 * @author Raymond Augé
 */
public class PropertyComparator implements Comparator<Object> {

	public PropertyComparator(String propertyName) {
		this(new String[] {propertyName}, true, false);
	}

	public PropertyComparator(String[] propertyNames) {
		this(propertyNames, true, false);
	}

	public PropertyComparator(
		String propertyName, boolean asc, boolean caseSensitive) {

		this(new String[] {propertyName}, asc, caseSensitive);
	}

	public PropertyComparator(
		String[] propertyNames, boolean asc, boolean caseSensitive) {

		_propertyNames = propertyNames;
		_asc = asc;
		_caseSensitive = caseSensitive;
	}

	public int compare(Object obj1, Object obj2) {
		try {
			for (String propertyName : _propertyNames) {
				Object property1 = PropertyUtils.getProperty(
					obj1, propertyName);
				Object property2 = PropertyUtils.getProperty(
					obj2, propertyName);

				if (!_asc) {
					Object temp = property1;

					property1 = property2;
					property2 = temp;
				}

				if (property1 instanceof String) {
					int result = 0;

					if (_caseSensitive) {
						result = property1.toString().compareTo(
							property2.toString());
					}
					else {
						result = property1.toString().compareToIgnoreCase(
							property2.toString());
					}

					if (result != 0) {
						return result;
					}
				}

				if (property1 instanceof Comparable<?>) {
					int result = ((Comparable<Object>)property1).compareTo(
						property2);

					if (result != 0) {
						return result;
					}
				}
			}
		}
		catch (IllegalAccessException iae) {
			_log.error(iae.getMessage());
		}
		catch (InvocationTargetException ite) {
			_log.error(ite.getMessage());
		}
		catch (NoSuchMethodException nsme) {
			_log.error(nsme.getMessage());
		}

		return -1;
	}

	private static Log _log = LogFactoryUtil.getLog(PropertyComparator.class);

	private String[] _propertyNames;
	private boolean _asc;
	private boolean _caseSensitive;

}