/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.model;

/**
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

	public void setNew(boolean n) {
		_className.setNew(n);
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