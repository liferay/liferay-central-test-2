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
 * <a href="PasswordPolicyLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link PasswordPolicyLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PasswordPolicyLocalService
 * @generated
 */
public class PasswordPolicyLocalServiceUtil {
	public static com.liferay.portal.model.PasswordPolicy addPasswordPolicy(
		com.liferay.portal.model.PasswordPolicy passwordPolicy)
		throws com.liferay.portal.SystemException {
		return getService().addPasswordPolicy(passwordPolicy);
	}

	public static com.liferay.portal.model.PasswordPolicy createPasswordPolicy(
		long passwordPolicyId) {
		return getService().createPasswordPolicy(passwordPolicyId);
	}

	public static void deletePasswordPolicy(long passwordPolicyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deletePasswordPolicy(passwordPolicyId);
	}

	public static void deletePasswordPolicy(
		com.liferay.portal.model.PasswordPolicy passwordPolicy)
		throws com.liferay.portal.SystemException {
		getService().deletePasswordPolicy(passwordPolicy);
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

	public static com.liferay.portal.model.PasswordPolicy getPasswordPolicy(
		long passwordPolicyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getPasswordPolicy(passwordPolicyId);
	}

	public static java.util.List<com.liferay.portal.model.PasswordPolicy> getPasswordPolicies(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getPasswordPolicies(start, end);
	}

	public static int getPasswordPoliciesCount()
		throws com.liferay.portal.SystemException {
		return getService().getPasswordPoliciesCount();
	}

	public static com.liferay.portal.model.PasswordPolicy updatePasswordPolicy(
		com.liferay.portal.model.PasswordPolicy passwordPolicy)
		throws com.liferay.portal.SystemException {
		return getService().updatePasswordPolicy(passwordPolicy);
	}

	public static com.liferay.portal.model.PasswordPolicy updatePasswordPolicy(
		com.liferay.portal.model.PasswordPolicy passwordPolicy, boolean merge)
		throws com.liferay.portal.SystemException {
		return getService().updatePasswordPolicy(passwordPolicy, merge);
	}

	public static com.liferay.portal.model.PasswordPolicy addPasswordPolicy(
		long userId, boolean defaultPolicy, java.lang.String name,
		java.lang.String description, boolean changeable,
		boolean changeRequired, long minAge, boolean checkSyntax,
		boolean allowDictionaryWords, int minLength, boolean history,
		int historyCount, boolean expireable, long maxAge, long warningTime,
		int graceLimit, boolean lockout, int maxFailure, long lockoutDuration,
		long resetFailureCount)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addPasswordPolicy(userId, defaultPolicy, name, description,
			changeable, changeRequired, minAge, checkSyntax,
			allowDictionaryWords, minLength, history, historyCount, expireable,
			maxAge, warningTime, graceLimit, lockout, maxFailure,
			lockoutDuration, resetFailureCount);
	}

	public static void checkDefaultPasswordPolicy(long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().checkDefaultPasswordPolicy(companyId);
	}

	public static com.liferay.portal.model.PasswordPolicy getDefaultPasswordPolicy(
		long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getDefaultPasswordPolicy(companyId);
	}

	public static com.liferay.portal.model.PasswordPolicy getPasswordPolicy(
		long companyId, long organizationId, long locationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .getPasswordPolicy(companyId, organizationId, locationId);
	}

	public static com.liferay.portal.model.PasswordPolicy getPasswordPolicy(
		long companyId, long[] organizationIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getPasswordPolicy(companyId, organizationIds);
	}

	public static com.liferay.portal.model.PasswordPolicy getPasswordPolicyByUserId(
		long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getPasswordPolicyByUserId(userId);
	}

	public static java.util.List<com.liferay.portal.model.PasswordPolicy> search(
		long companyId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getService().search(companyId, name, start, end, obc);
	}

	public static int searchCount(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getService().searchCount(companyId, name);
	}

	public static com.liferay.portal.model.PasswordPolicy updatePasswordPolicy(
		long passwordPolicyId, java.lang.String name,
		java.lang.String description, boolean changeable,
		boolean changeRequired, long minAge, boolean checkSyntax,
		boolean allowDictionaryWords, int minLength, boolean history,
		int historyCount, boolean expireable, long maxAge, long warningTime,
		int graceLimit, boolean lockout, int maxFailure, long lockoutDuration,
		long resetFailureCount)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updatePasswordPolicy(passwordPolicyId, name, description,
			changeable, changeRequired, minAge, checkSyntax,
			allowDictionaryWords, minLength, history, historyCount, expireable,
			maxAge, warningTime, graceLimit, lockout, maxFailure,
			lockoutDuration, resetFailureCount);
	}

	public static PasswordPolicyLocalService getService() {
		if (_service == null) {
			_service = (PasswordPolicyLocalService)PortalBeanLocatorUtil.locate(PasswordPolicyLocalService.class.getName());
		}

		return _service;
	}

	public void setService(PasswordPolicyLocalService service) {
		_service = service;
	}

	private static PasswordPolicyLocalService _service;
}