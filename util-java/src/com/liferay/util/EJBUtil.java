/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import java.util.Map;
import java.util.Properties;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.rmi.PortableRemoteObject;

/**
 * <a href="EJBUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class EJBUtil {

	public static EJBHome getHome(Properties props, String location)
		throws NamingException {

		return _instance._getHome(props, location);
	}

	public static EJBLocalHome getLocalHome(String location)
		throws NamingException {

		return _instance._getLocalHome(location);
	}

	public static void flush() {
		_instance._flush();
	}

	private EJBUtil() {
		_contexts = CollectionFactory.getSyncHashMap();
		_homes = CollectionFactory.getSyncHashMap();
		_localHomes = CollectionFactory.getSyncHashMap();
	}

	private void _flush() {
		_homes.clear();
		_localHomes.clear();
	}

	private Context _getContext(Properties props) throws NamingException {
		if (props == null) {
			return new InitialContext();
		}
		else {
			String key = props.getProperty(Context.PROVIDER_URL);

			if (key == null) {
				return new InitialContext();
			}

			Context context = (Context)_contexts.get(key);

			if (context == null) {
				context = new InitialContext(props);

				_contexts.put(key, context);
			}

			return context;
		}
	}

	private EJBHome _getHome(Properties props, String location)
		throws NamingException {

		EJBHome home = (EJBHome)_homes.get(location);

		if (home == null) {
			Context context = _getContext(props);

			Object obj = JNDIUtil.lookup(context, location);

			home = (EJBHome)PortableRemoteObject.narrow(obj, EJBHome.class);

			_homes.put(location, home);
		}

		return home;
	}

	private EJBLocalHome _getLocalHome(String location) throws NamingException {
		EJBLocalHome localHome = (EJBLocalHome)_localHomes.get(location);

		if (localHome == null) {
			Context context = _getContext(null);

			Object obj = JNDIUtil.lookup(context, location);

			localHome = (EJBLocalHome)obj;

			_localHomes.put(location, localHome);
		}

		return localHome;
	}

	private static EJBUtil _instance = new EJBUtil();

	private Map _contexts;
	private Map _homes;
	private Map _localHomes;

}