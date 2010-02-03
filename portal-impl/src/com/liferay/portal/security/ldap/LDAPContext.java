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

package com.liferay.portal.security.ldap;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.ldap.DummyDirContext;

import javax.naming.Name;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;

/**
 * <a href="LDAPContext.java.html"><b><i>View Source</i></b></a>
 *
 * @author Edward Han
 */
public class LDAPContext extends DummyDirContext {
	public LDAPContext(boolean ignoreCase) {
		super();

		_attributes = new BasicAttributes(ignoreCase);
	}

	public LDAPContext(Attributes attributes) {
		_attributes = attributes;
	}

	public void addAttribute(String name, Object object) {
		_attributes.put(name, object);
	}

	public Attributes getAttributes() {
		return _attributes;
	}

	public void setAttributes(Attributes attributes) {
		_attributes = attributes;
	}

	public Attributes getAttributes(String name) throws NamingException {
		if (Validator.isNotNull(name)) {
			throw new NameNotFoundException();
		}

		return (Attributes) _attributes.clone();
	}

	public Attributes getAttributes(Name name) throws NamingException {
		return getAttributes(name.toString());
	}

	public Attributes getAttributes(String name, String[] ids)
		throws NamingException {

		if (Validator.isNotNull(name)) {
			throw new NameNotFoundException();
		}

		Attributes attrs = new BasicAttributes(true);

		for (int i = 0; i < ids.length; i++) {
			Attribute attr = _attributes.get(ids[i]);

			if (attr != null) {
				attrs.put(attr);
			}
		}

		return attrs;
	}

	public Attributes getAttributes(Name name, String[] ids)
		throws NamingException {

		return getAttributes(name.toString(), ids);
	}

	private Attributes _attributes;
}