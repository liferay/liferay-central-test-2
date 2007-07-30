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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import java.util.Map;

/**
 * <a href="AuthPipeline.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AuthPipeline {

	public static int authenticateByEmailAddress(
			String[] classes, long companyId, String emailAddress,
			String password, Map headerMap, Map parameterMap)
		throws AuthException {

		return _authenticate(
			classes, companyId, emailAddress, password,
			CompanyImpl.AUTH_TYPE_EA, headerMap, parameterMap);
	}

	public static int authenticateByScreenName(
			String[] classes, long companyId, String screenName,
			String password, Map headerMap, Map parameterMap)
		throws AuthException {

		return _authenticate(
			classes, companyId, screenName, password, CompanyImpl.AUTH_TYPE_SN,
			headerMap, parameterMap);
	}

	public static int authenticateByUserId(
			String[] classes, long companyId, long userId, String password,
			Map headerMap, Map parameterMap)
		throws AuthException {

		return _authenticate(
			classes, companyId, String.valueOf(userId), password,
			CompanyImpl.AUTH_TYPE_ID, headerMap, parameterMap);
	}

	public static void onFailureByEmailAddress(
			String[] classes, long companyId, String emailAddress,
			Map headerMap, Map parameterMap)
		throws AuthException {

		_onFailure(
			classes, companyId, emailAddress, CompanyImpl.AUTH_TYPE_EA,
			headerMap, parameterMap);
	}

	public static void onFailureByScreenName(
			String[] classes, long companyId, String screenName,
			Map headerMap, Map parameterMap)
		throws AuthException {

		_onFailure(
			classes, companyId, screenName, CompanyImpl.AUTH_TYPE_SN, headerMap,
			parameterMap);
	}

	public static void onFailureByUserId(
			String[] classes, long companyId, long userId, Map headerMap,
			Map parameterMap)
		throws AuthException {

		_onFailure(
			classes, companyId, String.valueOf(userId),
			CompanyImpl.AUTH_TYPE_ID, headerMap, parameterMap);
	}

	public static void onMaxFailuresByEmailAddress(
			String[] classes, long companyId, String emailAddress,
			Map headerMap, Map parameterMap)
		throws AuthException {

		onFailureByEmailAddress(
			classes, companyId, emailAddress, headerMap, parameterMap);
	}

	public static void onMaxFailuresByScreenName(
			String[] classes, long companyId, String screenName,
			Map headerMap, Map parameterMap)
		throws AuthException {

		onFailureByScreenName(
			classes, companyId, screenName, headerMap, parameterMap);
	}

	public static void onMaxFailuresByUserId(
			String[] classes, long companyId, long userId, Map headerMap,
			Map parameterMap)
		throws AuthException {

		onFailureByUserId(classes, companyId, userId, headerMap, parameterMap);
	}

	private static int _authenticate(
			String[] classes, long companyId, String login, String password,
			String authType, Map headerMap, Map parameterMap)
		throws AuthException {

		if ((classes == null) || (classes.length == 0)) {
			return 1;
		}

		for (int i = 0; i < classes.length; i++) {
			String className = classes[i];

			if (Validator.isNotNull(className)) {
				Authenticator auth =
					(Authenticator)InstancePool.get(classes[i]);

				try {
					int authResult = Authenticator.FAILURE;

					if (authType.equals(CompanyImpl.AUTH_TYPE_EA)) {
						authResult = auth.authenticateByEmailAddress(
							companyId, login, password, headerMap,
							parameterMap);
					}
					else if (authType.equals(CompanyImpl.AUTH_TYPE_SN)) {
						authResult = auth.authenticateByScreenName(
							companyId, login, password, headerMap,
							parameterMap);
					}
					else if (authType.equals(CompanyImpl.AUTH_TYPE_ID)) {
						long userId = GetterUtil.getLong(login);

						authResult = auth.authenticateByUserId(
							companyId, userId, password, headerMap,
							parameterMap);
					}

					if (authResult != Authenticator.SUCCESS) {
						return authResult;
					}
				}
				catch (AuthException ae) {
					throw ae;
				}
				catch (Exception e) {
					throw new AuthException(e);
				}
			}
		}

		return Authenticator.SUCCESS;
	}

	private static void _onFailure(
			String[] classes, long companyId, String login, String authType,
			Map headerMap, Map parameterMap)
		throws AuthException {

		if ((classes == null) || (classes.length == 0)) {
			return;
		}

		for (int i = 0; i < classes.length; i++) {
			String className = classes[i];

			if (Validator.isNotNull(className)) {
				AuthFailure authFailure =
					(AuthFailure)InstancePool.get(classes[i]);

				try {
					if (authType.equals(CompanyImpl.AUTH_TYPE_EA)) {
						authFailure.onFailureByEmailAddress(
							companyId, login, headerMap, parameterMap);
					}
					else if (authType.equals(CompanyImpl.AUTH_TYPE_SN)) {
						authFailure.onFailureByScreenName(
							companyId, login, headerMap, parameterMap);
					}
					else if (authType.equals(CompanyImpl.AUTH_TYPE_ID)) {
						long userId = GetterUtil.getLong(login);

						authFailure.onFailureByUserId(
							companyId, userId, headerMap, parameterMap);
					}
				}
				catch (AuthException ae) {
					throw ae;
				}
				catch (Exception e) {
					throw new AuthException(e);
				}
			}
		}
	}

}