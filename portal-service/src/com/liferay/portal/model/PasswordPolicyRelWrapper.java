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
 * <a href="PasswordPolicyRelSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
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

	public boolean setNew(boolean n) {
		return _passwordPolicyRel.setNew(n);
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