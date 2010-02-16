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

package com.liferay.portal.service;


/**
 * <a href="PasswordPolicyRelLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link PasswordPolicyRelLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PasswordPolicyRelLocalService
 * @generated
 */
public class PasswordPolicyRelLocalServiceWrapper
	implements PasswordPolicyRelLocalService {
	public PasswordPolicyRelLocalServiceWrapper(
		PasswordPolicyRelLocalService passwordPolicyRelLocalService) {
		_passwordPolicyRelLocalService = passwordPolicyRelLocalService;
	}

	public com.liferay.portal.model.PasswordPolicyRel addPasswordPolicyRel(
		com.liferay.portal.model.PasswordPolicyRel passwordPolicyRel)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyRelLocalService.addPasswordPolicyRel(passwordPolicyRel);
	}

	public com.liferay.portal.model.PasswordPolicyRel createPasswordPolicyRel(
		long passwordPolicyRelId) {
		return _passwordPolicyRelLocalService.createPasswordPolicyRel(passwordPolicyRelId);
	}

	public void deletePasswordPolicyRel(long passwordPolicyRelId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_passwordPolicyRelLocalService.deletePasswordPolicyRel(passwordPolicyRelId);
	}

	public void deletePasswordPolicyRel(
		com.liferay.portal.model.PasswordPolicyRel passwordPolicyRel)
		throws com.liferay.portal.kernel.exception.SystemException {
		_passwordPolicyRelLocalService.deletePasswordPolicyRel(passwordPolicyRel);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyRelLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyRelLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	public com.liferay.portal.model.PasswordPolicyRel getPasswordPolicyRel(
		long passwordPolicyRelId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyRelLocalService.getPasswordPolicyRel(passwordPolicyRelId);
	}

	public java.util.List<com.liferay.portal.model.PasswordPolicyRel> getPasswordPolicyRels(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyRelLocalService.getPasswordPolicyRels(start, end);
	}

	public int getPasswordPolicyRelsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyRelLocalService.getPasswordPolicyRelsCount();
	}

	public com.liferay.portal.model.PasswordPolicyRel updatePasswordPolicyRel(
		com.liferay.portal.model.PasswordPolicyRel passwordPolicyRel)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyRelLocalService.updatePasswordPolicyRel(passwordPolicyRel);
	}

	public com.liferay.portal.model.PasswordPolicyRel updatePasswordPolicyRel(
		com.liferay.portal.model.PasswordPolicyRel passwordPolicyRel,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyRelLocalService.updatePasswordPolicyRel(passwordPolicyRel,
			merge);
	}

	public com.liferay.portal.model.PasswordPolicyRel addPasswordPolicyRel(
		long passwordPolicyId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyRelLocalService.addPasswordPolicyRel(passwordPolicyId,
			className, classPK);
	}

	public void addPasswordPolicyRels(long passwordPolicyId,
		java.lang.String className, long[] classPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		_passwordPolicyRelLocalService.addPasswordPolicyRels(passwordPolicyId,
			className, classPKs);
	}

	public void deletePasswordPolicyRel(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		_passwordPolicyRelLocalService.deletePasswordPolicyRel(className,
			classPK);
	}

	public void deletePasswordPolicyRel(long passwordPolicyId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		_passwordPolicyRelLocalService.deletePasswordPolicyRel(passwordPolicyId,
			className, classPK);
	}

	public void deletePasswordPolicyRels(long passwordPolicyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_passwordPolicyRelLocalService.deletePasswordPolicyRels(passwordPolicyId);
	}

	public void deletePasswordPolicyRels(long passwordPolicyId,
		java.lang.String className, long[] classPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		_passwordPolicyRelLocalService.deletePasswordPolicyRels(passwordPolicyId,
			className, classPKs);
	}

	public com.liferay.portal.model.PasswordPolicyRel getPasswordPolicyRel(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyRelLocalService.getPasswordPolicyRel(className,
			classPK);
	}

	public com.liferay.portal.model.PasswordPolicyRel getPasswordPolicyRel(
		long passwordPolicyId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyRelLocalService.getPasswordPolicyRel(passwordPolicyId,
			className, classPK);
	}

	public boolean hasPasswordPolicyRel(long passwordPolicyId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyRelLocalService.hasPasswordPolicyRel(passwordPolicyId,
			className, classPK);
	}

	public PasswordPolicyRelLocalService getWrappedPasswordPolicyRelLocalService() {
		return _passwordPolicyRelLocalService;
	}

	private PasswordPolicyRelLocalService _passwordPolicyRelLocalService;
}