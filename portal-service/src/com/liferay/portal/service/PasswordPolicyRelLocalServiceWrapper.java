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