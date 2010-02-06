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

package com.liferay.portal.xml;

import com.liferay.portal.kernel.xml.Namespace;

/**
 * <a href="NamespaceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class NamespaceImpl extends NodeImpl implements Namespace {

	public NamespaceImpl(org.dom4j.Namespace namespace) {
		super(namespace);

		_namespace = namespace;
	}

	public boolean equals(Object obj) {
		org.dom4j.Namespace namespace =
			((NamespaceImpl)obj).getWrappedNamespace();

		return _namespace.equals(namespace);
	}

	public short getNodeType() {
		return _namespace.getNodeType();
	}

	public String getPrefix() {
		return _namespace.getPrefix();
	}

	public String getURI() {
		return _namespace.getURI();
	}

	public org.dom4j.Namespace getWrappedNamespace() {
		return _namespace;
	}

	public String getXPathNameStep() {
		return _namespace.getXPathNameStep();
	}

	public int hashCode() {
		return _namespace.hashCode();
	}

	private org.dom4j.Namespace _namespace;

}