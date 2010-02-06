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

import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Namespace;
import com.liferay.portal.kernel.xml.QName;

/**
 * <a href="AttributeImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AttributeImpl extends NodeImpl implements Attribute {

	public AttributeImpl(org.dom4j.Attribute attribute) {
		super(attribute);

		_attribute = attribute;
	}

	public Object getData() {
		return _attribute.getData();
	}

	public boolean equals(Object obj) {
		org.dom4j.Attribute attribute =
			((AttributeImpl)obj).getWrappedAttribute();

		return _attribute.equals(attribute);
	}

	public Namespace getNamespace() {
		org.dom4j.Namespace namespace = _attribute.getNamespace();

		if (namespace == null) {
			return null;
		}
		else {
			return new NamespaceImpl(namespace);
		}
	}

	public String getNamespacePrefix() {
		return _attribute.getNamespacePrefix();
	}

	public String getNamespaceURI() {
		return _attribute.getNamespaceURI();
	}

	public QName getQName() {
		org.dom4j.QName qName = _attribute.getQName();

		if (qName == null) {
			return null;
		}
		else {
			return new QNameImpl(qName);
		}
	}

	public String getQualifiedName() {
		return _attribute.getQualifiedName();
	}

	public String getValue() {
		return _attribute.getValue();
	}

	public org.dom4j.Attribute getWrappedAttribute() {
		return _attribute;
	}

	public int hashCode() {
		return _attribute.hashCode();
	}

	public void setData(Object data) {
		_attribute.setData(data);
	}

	public void setNamespace(Namespace namespace) {
		NamespaceImpl namespaceImpl = (NamespaceImpl)namespace;

		_attribute.setNamespace(namespaceImpl.getWrappedNamespace());
	}

	public void setValue(String value) {
		_attribute.setValue(value);
	}

	private org.dom4j.Attribute _attribute;

}