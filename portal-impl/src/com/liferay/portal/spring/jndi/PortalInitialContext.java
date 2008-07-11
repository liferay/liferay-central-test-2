/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.spring.jndi;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

import java.util.Hashtable;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="PortalInitialContext.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortalInitialContext implements Context {

	public Object addToEnvironment(String propName, Object propVal) {
		return null;
	}

	public void bind(Name name, Object obj) {
	}

	public void bind(String name, Object obj) {
	}

	public void close() {
	}

	public Name composeName(Name name, Name prefix) {
		return null;
	}

	public String composeName(String name, String prefix) {
		return null;
	}

	public Context createSubcontext(Name name) {
		return null;
	}

	public Context createSubcontext(String name) {
		return null;
	}

	public void destroySubcontext(Name name) {
	}

	public void destroySubcontext(String name) {
	}

	public Hashtable<?, ?> getEnvironment() {
		return null;
	}

	public String getNameInNamespace() {
		return null;
	}

	public NameParser getNameParser(Name name) {
		return null;
	}

	public NameParser getNameParser(String name) {
		return null;
	}

	public NamingEnumeration<NameClassPair> list(Name name) {
		return null;
	}

	public NamingEnumeration<NameClassPair> list(String name) {
		return null;
	}

	public NamingEnumeration<Binding> listBindings(Name name) {
		return null;
	}

	public NamingEnumeration<Binding> listBindings(String name) {
		return null;
	}

	public Object lookup(Name name) {
		return null;
	}

	public Object lookup(String name) throws NamingException {
		Object value = null;

		if (name.equals("jdbc/LiferayPool")) {
			try {
				value = PortalBeanLocatorUtil.locate("liferayDataSource");
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (value == null) {
			throw new NamingException();
		}

		return value;
	}

	public Object lookupLink(Name name) {
		return null;
	}

	public Object lookupLink(String name) {
		return null;
	}

	public void rebind(Name name, Object obj) {
	}

	public void rebind(String name, Object obj) {
	}

	public Object removeFromEnvironment(String propName) {
		return null;
	}

	public void rename(Name oldName, Name newName) {
	}

	public void rename(String oldName, String newName) {
	}

	public void unbind(Name name) {
	}

	public void unbind(String name) {
	}

	private static Log _log = LogFactory.getLog(PortalInitialContext.class);

}