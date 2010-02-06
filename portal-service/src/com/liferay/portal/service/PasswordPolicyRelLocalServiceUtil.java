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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="PasswordPolicyRelLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link PasswordPolicyRelLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PasswordPolicyRelLocalService
 * @generated
 */
public class PasswordPolicyRelLocalServiceUtil {
	public static com.liferay.portal.model.PasswordPolicyRel addPasswordPolicyRel(
		com.liferay.portal.model.PasswordPolicyRel passwordPolicyRel)
		throws com.liferay.portal.SystemException {
		return getService().addPasswordPolicyRel(passwordPolicyRel);
	}

	public static com.liferay.portal.model.PasswordPolicyRel createPasswordPolicyRel(
		long passwordPolicyRelId) {
		return getService().createPasswordPolicyRel(passwordPolicyRelId);
	}

	public static void deletePasswordPolicyRel(long passwordPolicyRelId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deletePasswordPolicyRel(passwordPolicyRelId);
	}

	public static void deletePasswordPolicyRel(
		com.liferay.portal.model.PasswordPolicyRel passwordPolicyRel)
		throws com.liferay.portal.SystemException {
		getService().deletePasswordPolicyRel(passwordPolicyRel);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.PasswordPolicyRel getPasswordPolicyRel(
		long passwordPolicyRelId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getPasswordPolicyRel(passwordPolicyRelId);
	}

	public static java.util.List<com.liferay.portal.model.PasswordPolicyRel> getPasswordPolicyRels(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getPasswordPolicyRels(start, end);
	}

	public static int getPasswordPolicyRelsCount()
		throws com.liferay.portal.SystemException {
		return getService().getPasswordPolicyRelsCount();
	}

	public static com.liferay.portal.model.PasswordPolicyRel updatePasswordPolicyRel(
		com.liferay.portal.model.PasswordPolicyRel passwordPolicyRel)
		throws com.liferay.portal.SystemException {
		return getService().updatePasswordPolicyRel(passwordPolicyRel);
	}

	public static com.liferay.portal.model.PasswordPolicyRel updatePasswordPolicyRel(
		com.liferay.portal.model.PasswordPolicyRel passwordPolicyRel,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updatePasswordPolicyRel(passwordPolicyRel, merge);
	}

	public static com.liferay.portal.model.PasswordPolicyRel addPasswordPolicyRel(
		long passwordPolicyId, java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		return getService()
				   .addPasswordPolicyRel(passwordPolicyId, className, classPK);
	}

	public static void addPasswordPolicyRels(long passwordPolicyId,
		java.lang.String className, long[] classPKs)
		throws com.liferay.portal.SystemException {
		getService().addPasswordPolicyRels(passwordPolicyId, className, classPKs);
	}

	public static void deletePasswordPolicyRel(java.lang.String className,
		long classPK) throws com.liferay.portal.SystemException {
		getService().deletePasswordPolicyRel(className, classPK);
	}

	public static void deletePasswordPolicyRel(long passwordPolicyId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		getService()
			.deletePasswordPolicyRel(passwordPolicyId, className, classPK);
	}

	public static void deletePasswordPolicyRels(long passwordPolicyId)
		throws com.liferay.portal.SystemException {
		getService().deletePasswordPolicyRels(passwordPolicyId);
	}

	public static void deletePasswordPolicyRels(long passwordPolicyId,
		java.lang.String className, long[] classPKs)
		throws com.liferay.portal.SystemException {
		getService()
			.deletePasswordPolicyRels(passwordPolicyId, className, classPKs);
	}

	public static com.liferay.portal.model.PasswordPolicyRel getPasswordPolicyRel(
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getPasswordPolicyRel(className, classPK);
	}

	public static com.liferay.portal.model.PasswordPolicyRel getPasswordPolicyRel(
		long passwordPolicyId, java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .getPasswordPolicyRel(passwordPolicyId, className, classPK);
	}

	public static boolean hasPasswordPolicyRel(long passwordPolicyId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		return getService()
				   .hasPasswordPolicyRel(passwordPolicyId, className, classPK);
	}

	public static PasswordPolicyRelLocalService getService() {
		if (_service == null) {
			_service = (PasswordPolicyRelLocalService)PortalBeanLocatorUtil.locate(PasswordPolicyRelLocalService.class.getName());
		}

		return _service;
	}

	public void setService(PasswordPolicyRelLocalService service) {
		_service = service;
	}

	private static PasswordPolicyRelLocalService _service;
}