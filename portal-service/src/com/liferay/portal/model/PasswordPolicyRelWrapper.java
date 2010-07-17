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
 * This class is a wrapper for {@link PasswordPolicyRel}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PasswordPolicyRel
 * @generated
 */
public class PasswordPolicyRelWrapper implements PasswordPolicyRel {
	public PasswordPolicyRelWrapper(PasswordPolicyRel passwordPolicyRel) {
		_passwordPolicyRel = passwordPolicyRel;
	}

	public long getPrimaryKey() {
		return _passwordPolicyRel.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_passwordPolicyRel.setPrimaryKey(pk);
	}

	public long getPasswordPolicyRelId() {
		return _passwordPolicyRel.getPasswordPolicyRelId();
	}

	public void setPasswordPolicyRelId(long passwordPolicyRelId) {
		_passwordPolicyRel.setPasswordPolicyRelId(passwordPolicyRelId);
	}

	public long getPasswordPolicyId() {
		return _passwordPolicyRel.getPasswordPolicyId();
	}

	public void setPasswordPolicyId(long passwordPolicyId) {
		_passwordPolicyRel.setPasswordPolicyId(passwordPolicyId);
	}

	public java.lang.String getClassName() {
		return _passwordPolicyRel.getClassName();
	}

	public long getClassNameId() {
		return _passwordPolicyRel.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_passwordPolicyRel.setClassNameId(classNameId);
	}

	public long getClassPK() {
		return _passwordPolicyRel.getClassPK();
	}

	public void setClassPK(long classPK) {
		_passwordPolicyRel.setClassPK(classPK);
	}

	public com.liferay.portal.model.PasswordPolicyRel toEscapedModel() {
		return _passwordPolicyRel.toEscapedModel();
	}

	public boolean isNew() {
		return _passwordPolicyRel.isNew();
	}

	public void setNew(boolean n) {
		_passwordPolicyRel.setNew(n);
	}

	public boolean isCachedModel() {
		return _passwordPolicyRel.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_passwordPolicyRel.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _passwordPolicyRel.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_passwordPolicyRel.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _passwordPolicyRel.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _passwordPolicyRel.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_passwordPolicyRel.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _passwordPolicyRel.clone();
	}

	public int compareTo(
		com.liferay.portal.model.PasswordPolicyRel passwordPolicyRel) {
		return _passwordPolicyRel.compareTo(passwordPolicyRel);
	}

	public int hashCode() {
		return _passwordPolicyRel.hashCode();
	}

	public java.lang.String toString() {
		return _passwordPolicyRel.toString();
	}

	public java.lang.String toXmlString() {
		return _passwordPolicyRel.toXmlString();
	}

	public PasswordPolicyRel getWrappedPasswordPolicyRel() {
		return _passwordPolicyRel;
	}

	private PasswordPolicyRel _passwordPolicyRel;
}