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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.PasswordPolicyServiceUtil;

/**
 * <a href="PasswordPolicyServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a HTTP utility for the
 * {@link com.liferay.portal.service.PasswordPolicyServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link com.liferay.portal.security.auth.HttpPrincipal} parameter.
 * </p>
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PasswordPolicyServiceSoap
 * @see       com.liferay.portal.security.auth.HttpPrincipal
 * @see       com.liferay.portal.service.PasswordPolicyServiceUtil
 * @generated
 */
public class PasswordPolicyServiceHttp {
	public static com.liferay.portal.model.PasswordPolicy addPasswordPolicy(
		HttpPrincipal httpPrincipal, java.lang.String name,
		java.lang.String description, boolean changeable,
		boolean changeRequired, long minAge, boolean checkSyntax,
		boolean allowDictionaryWords, int minAlphanumeric, int minLength,
		int minLowerCase, int minNumbers, int minSymbols, int minUpperCase,
		boolean history, int historyCount, boolean expireable, long maxAge,
		long warningTime, int graceLimit, boolean lockout, int maxFailure,
		long lockoutDuration, long resetFailureCount, long resetTicketMaxAge)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = name;

			if (name == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = description;

			if (description == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new BooleanWrapper(changeable);

			Object paramObj3 = new BooleanWrapper(changeRequired);

			Object paramObj4 = new LongWrapper(minAge);

			Object paramObj5 = new BooleanWrapper(checkSyntax);

			Object paramObj6 = new BooleanWrapper(allowDictionaryWords);

			Object paramObj7 = new IntegerWrapper(minAlphanumeric);

			Object paramObj8 = new IntegerWrapper(minLength);

			Object paramObj9 = new IntegerWrapper(minLowerCase);

			Object paramObj10 = new IntegerWrapper(minNumbers);

			Object paramObj11 = new IntegerWrapper(minSymbols);

			Object paramObj12 = new IntegerWrapper(minUpperCase);

			Object paramObj13 = new BooleanWrapper(history);

			Object paramObj14 = new IntegerWrapper(historyCount);

			Object paramObj15 = new BooleanWrapper(expireable);

			Object paramObj16 = new LongWrapper(maxAge);

			Object paramObj17 = new LongWrapper(warningTime);

			Object paramObj18 = new IntegerWrapper(graceLimit);

			Object paramObj19 = new BooleanWrapper(lockout);

			Object paramObj20 = new IntegerWrapper(maxFailure);

			Object paramObj21 = new LongWrapper(lockoutDuration);

			Object paramObj22 = new LongWrapper(resetFailureCount);

			Object paramObj23 = new LongWrapper(resetTicketMaxAge);

			MethodWrapper methodWrapper = new MethodWrapper(PasswordPolicyServiceUtil.class.getName(),
					"addPasswordPolicy",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11, paramObj12, paramObj13,
						paramObj14, paramObj15, paramObj16, paramObj17,
						paramObj18, paramObj19, paramObj20, paramObj21,
						paramObj22, paramObj23
					});

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.model.PasswordPolicy)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deletePasswordPolicy(HttpPrincipal httpPrincipal,
		long passwordPolicyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(passwordPolicyId);

			MethodWrapper methodWrapper = new MethodWrapper(PasswordPolicyServiceUtil.class.getName(),
					"deletePasswordPolicy", new Object[] { paramObj0 });

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.PasswordPolicy updatePasswordPolicy(
		HttpPrincipal httpPrincipal, long passwordPolicyId,
		java.lang.String name, java.lang.String description,
		boolean changeable, boolean changeRequired, long minAge,
		boolean checkSyntax, boolean allowDictionaryWords, int minAlphanumeric,
		int minLength, int minLowerCase, int minNumbers, int minSymbols,
		int minUpperCase, boolean history, int historyCount,
		boolean expireable, long maxAge, long warningTime, int graceLimit,
		boolean lockout, int maxFailure, long lockoutDuration,
		long resetFailureCount, long resetTicketMaxAge)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(passwordPolicyId);

			Object paramObj1 = name;

			if (name == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = description;

			if (description == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new BooleanWrapper(changeable);

			Object paramObj4 = new BooleanWrapper(changeRequired);

			Object paramObj5 = new LongWrapper(minAge);

			Object paramObj6 = new BooleanWrapper(checkSyntax);

			Object paramObj7 = new BooleanWrapper(allowDictionaryWords);

			Object paramObj8 = new IntegerWrapper(minAlphanumeric);

			Object paramObj9 = new IntegerWrapper(minLength);

			Object paramObj10 = new IntegerWrapper(minLowerCase);

			Object paramObj11 = new IntegerWrapper(minNumbers);

			Object paramObj12 = new IntegerWrapper(minSymbols);

			Object paramObj13 = new IntegerWrapper(minUpperCase);

			Object paramObj14 = new BooleanWrapper(history);

			Object paramObj15 = new IntegerWrapper(historyCount);

			Object paramObj16 = new BooleanWrapper(expireable);

			Object paramObj17 = new LongWrapper(maxAge);

			Object paramObj18 = new LongWrapper(warningTime);

			Object paramObj19 = new IntegerWrapper(graceLimit);

			Object paramObj20 = new BooleanWrapper(lockout);

			Object paramObj21 = new IntegerWrapper(maxFailure);

			Object paramObj22 = new LongWrapper(lockoutDuration);

			Object paramObj23 = new LongWrapper(resetFailureCount);

			Object paramObj24 = new LongWrapper(resetTicketMaxAge);

			MethodWrapper methodWrapper = new MethodWrapper(PasswordPolicyServiceUtil.class.getName(),
					"updatePasswordPolicy",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11, paramObj12, paramObj13,
						paramObj14, paramObj15, paramObj16, paramObj17,
						paramObj18, paramObj19, paramObj20, paramObj21,
						paramObj22, paramObj23, paramObj24
					});

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.model.PasswordPolicy)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PasswordPolicyServiceHttp.class);
}