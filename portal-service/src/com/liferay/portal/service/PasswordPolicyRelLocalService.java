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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.Isolation;
import com.liferay.portal.kernel.annotation.Propagation;
import com.liferay.portal.kernel.annotation.Transactional;

/**
 * <a href="PasswordPolicyRelLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * {@link
 * com.liferay.portal.service.impl.PasswordPolicyRelLocalServiceImpl}}.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PasswordPolicyRelLocalServiceUtil
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface PasswordPolicyRelLocalService {
	public com.liferay.portal.model.PasswordPolicyRel addPasswordPolicyRel(
		com.liferay.portal.model.PasswordPolicyRel passwordPolicyRel)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.PasswordPolicyRel createPasswordPolicyRel(
		long passwordPolicyRelId);

	public void deletePasswordPolicyRel(long passwordPolicyRelId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deletePasswordPolicyRel(
		com.liferay.portal.model.PasswordPolicyRel passwordPolicyRel)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PasswordPolicyRel getPasswordPolicyRel(
		long passwordPolicyRelId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.PasswordPolicyRel> getPasswordPolicyRels(
		int start, int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPasswordPolicyRelsCount()
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.PasswordPolicyRel updatePasswordPolicyRel(
		com.liferay.portal.model.PasswordPolicyRel passwordPolicyRel)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.PasswordPolicyRel updatePasswordPolicyRel(
		com.liferay.portal.model.PasswordPolicyRel passwordPolicyRel,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.PasswordPolicyRel addPasswordPolicyRel(
		long passwordPolicyId, java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException;

	public void addPasswordPolicyRels(long passwordPolicyId,
		java.lang.String className, long[] classPKs)
		throws com.liferay.portal.SystemException;

	public void deletePasswordPolicyRel(java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException;

	public void deletePasswordPolicyRel(long passwordPolicyId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException;

	public void deletePasswordPolicyRels(long passwordPolicyId)
		throws com.liferay.portal.SystemException;

	public void deletePasswordPolicyRels(long passwordPolicyId,
		java.lang.String className, long[] classPKs)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PasswordPolicyRel getPasswordPolicyRel(
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PasswordPolicyRel getPasswordPolicyRel(
		long passwordPolicyId, java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasPasswordPolicyRel(long passwordPolicyId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException;
}