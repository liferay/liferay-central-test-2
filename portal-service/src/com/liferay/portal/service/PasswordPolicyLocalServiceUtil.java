/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
 * <a href="PasswordPolicyLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the <code>com.liferay.portal.service.PasswordPolicyLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean instance.
 * It's convenient to be able to just write one line to call a method on a bean
 * instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.PasswordPolicyLocalServiceFactory</code> is
 * responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.PasswordPolicyLocalService
 * @see com.liferay.portal.service.PasswordPolicyLocalServiceFactory
 *
 */
public class PasswordPolicyLocalServiceUtil {
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		PasswordPolicyLocalService passwordPolicyLocalService = PasswordPolicyLocalServiceFactory.getService();

		return passwordPolicyLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		PasswordPolicyLocalService passwordPolicyLocalService = PasswordPolicyLocalServiceFactory.getService();

		return passwordPolicyLocalService.dynamicQuery(queryInitializer, begin,
			end);
	}

	public static com.liferay.portal.model.PasswordPolicy addPasswordPolicy(
		long userId, boolean defaultPolicy, java.lang.String name,
		java.lang.String description, java.lang.String storageScheme,
		boolean changeable, boolean changeRequired, long minAge,
		boolean checkSyntax, boolean allowDictionaryWords, int minLength,
		boolean history, int historyCount, boolean expireable, long maxAge,
		long warningTime, int graceLimit, boolean lockout, int maxFailure,
		long lockoutDuration, long resetFailureCount)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PasswordPolicyLocalService passwordPolicyLocalService = PasswordPolicyLocalServiceFactory.getService();

		return passwordPolicyLocalService.addPasswordPolicy(userId,
			defaultPolicy, name, description, storageScheme, changeable,
			changeRequired, minAge, checkSyntax, allowDictionaryWords,
			minLength, history, historyCount, expireable, maxAge, warningTime,
			graceLimit, lockout, maxFailure, lockoutDuration, resetFailureCount);
	}

	public static void checkDefaultPasswordPolicy(long companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PasswordPolicyLocalService passwordPolicyLocalService = PasswordPolicyLocalServiceFactory.getService();
		passwordPolicyLocalService.checkDefaultPasswordPolicy(companyId);
	}

	public static void deletePasswordPolicy(long passwordPolicyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PasswordPolicyLocalService passwordPolicyLocalService = PasswordPolicyLocalServiceFactory.getService();
		passwordPolicyLocalService.deletePasswordPolicy(passwordPolicyId);
	}

	public static com.liferay.portal.model.PasswordPolicy getDefaultPasswordPolicy(
		long companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PasswordPolicyLocalService passwordPolicyLocalService = PasswordPolicyLocalServiceFactory.getService();

		return passwordPolicyLocalService.getDefaultPasswordPolicy(companyId);
	}

	public static com.liferay.portal.model.PasswordPolicy getPasswordPolicy(
		long passwordPolicyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PasswordPolicyLocalService passwordPolicyLocalService = PasswordPolicyLocalServiceFactory.getService();

		return passwordPolicyLocalService.getPasswordPolicy(passwordPolicyId);
	}

	public static com.liferay.portal.model.PasswordPolicy getPasswordPolicyByUserId(
		long userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PasswordPolicyLocalService passwordPolicyLocalService = PasswordPolicyLocalServiceFactory.getService();

		return passwordPolicyLocalService.getPasswordPolicyByUserId(userId);
	}

	public static java.util.List search(long companyId, java.lang.String name,
		int begin, int end) throws com.liferay.portal.SystemException {
		PasswordPolicyLocalService passwordPolicyLocalService = PasswordPolicyLocalServiceFactory.getService();

		return passwordPolicyLocalService.search(companyId, name, begin, end);
	}

	public static int searchCount(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		PasswordPolicyLocalService passwordPolicyLocalService = PasswordPolicyLocalServiceFactory.getService();

		return passwordPolicyLocalService.searchCount(companyId, name);
	}

	public static com.liferay.portal.model.PasswordPolicy updatePasswordPolicy(
		long passwordPolicyId, java.lang.String name,
		java.lang.String description, java.lang.String storageScheme,
		boolean changeable, boolean changeRequired, long minAge,
		boolean checkSyntax, boolean allowDictionaryWords, int minLength,
		boolean history, int historyCount, boolean expireable, long maxAge,
		long warningTime, int graceLimit, boolean lockout, int maxFailure,
		long lockoutDuration, long resetFailureCount)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PasswordPolicyLocalService passwordPolicyLocalService = PasswordPolicyLocalServiceFactory.getService();

		return passwordPolicyLocalService.updatePasswordPolicy(passwordPolicyId,
			name, description, storageScheme, changeable, changeRequired,
			minAge, checkSyntax, allowDictionaryWords, minLength, history,
			historyCount, expireable, maxAge, warningTime, graceLimit, lockout,
			maxFailure, lockoutDuration, resetFailureCount);
	}
}