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

package com.liferay.portal.model;


/**
 * <a href="ClassNameSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ClassName}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ClassName
 * @generated
 */
public class ClassNameWrapper implements ClassName {
	public ClassNameWrapper(ClassName className) {
		_className = className;
	}

	public long getPrimaryKey() {
		return _className.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_className.setPrimaryKey(pk);
	}

	public java.lang.String getClassName() {
		return _className.getClassName();
	}

	public long getClassNameId() {
		return _className.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_className.setClassNameId(classNameId);
	}

	public java.lang.String getValue() {
		return _className.getValue();
	}

	public void setValue(java.lang.String value) {
		_className.setValue(value);
	}

	public com.liferay.portal.model.ClassName toEscapedModel() {
		return _className.toEscapedModel();
	}

	public boolean isNew() {
		return _className.isNew();
	}

	public boolean setNew(boolean n) {
		return _className.setNew(n);
	}

	public boolean isCachedModel() {
		return _className.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_className.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _className.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_className.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _className.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _className.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_className.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _className.clone();
	}

	public int compareTo(com.liferay.portal.model.ClassName className) {
		return _className.compareTo(className);
	}

	public int hashCode() {
		return _className.hashCode();
	}

	public java.lang.String toString() {
		return _className.toString();
	}

	public java.lang.String toXmlString() {
		return _className.toXmlString();
	}

	public ClassName getWrappedClassName() {
		return _className;
	}

	private ClassName _className;
}