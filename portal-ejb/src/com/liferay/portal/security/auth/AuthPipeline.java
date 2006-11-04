/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.util.InstancePool;
import com.liferay.util.Validator;

import java.util.Map;

/**
 * <a href="AuthPipeline.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class AuthPipeline {

    public static int authenticateByEmailAddress(
			String[] classes, String companyId, String emailAddress,
			String password, Map headerMap, Map parameterMap)
    	throws AuthException {

		return _authenticate(
			classes, companyId, emailAddress, password, true, headerMap,
			parameterMap);
	}

    public static int authenticateByUserId(
			String[] classes, String companyId, String userId, String password,
			Map headerMap, Map parameterMap)
    	throws AuthException {

		return _authenticate(
			classes, companyId, userId, password, false, headerMap,
			parameterMap);
	}

	public static void onFailureByEmailAddress(
			String[] classes, String companyId, String emailAddress,
			Map headerMap, Map parameterMap)
		throws AuthException {

		_onFailure(
			classes, companyId, emailAddress, true, headerMap, parameterMap);
	}

	public static void onFailureByUserId(
			String[] classes, String companyId, String userId, Map headerMap,
			Map parameterMap)
		throws AuthException {

		_onFailure(classes, companyId, userId, false, headerMap, parameterMap);
	}

	public static void onMaxFailuresByEmailAddress(
			String[] classes, String companyId, String emailAddress,
			Map headerMap, Map parameterMap)
		throws AuthException {

		onFailureByEmailAddress(
			classes, companyId, emailAddress, headerMap, parameterMap);
	}

	public static void onMaxFailuresByUserId(
			String[] classes, String companyId, String userId, Map headerMap,
			Map parameterMap)
		throws AuthException {

		onFailureByUserId(classes, companyId, userId, headerMap, parameterMap);
	}

    private static int _authenticate(
			String[] classes, String companyId, String login, String password,
			boolean byEmailAddress, Map headerMap, Map parameterMap)
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

					if (byEmailAddress) {
						authResult = auth.authenticateByEmailAddress(
							companyId, login, password, headerMap,
							parameterMap);
					}
					else {
						authResult = auth.authenticateByUserId(
							companyId, login, password, headerMap,
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
			String[] classes, String companyId, String login,
			boolean byEmailAddress, Map headerMap, Map parameterMap)
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
					if (byEmailAddress) {
						authFailure.onFailureByEmailAddress(
							companyId, login, headerMap, parameterMap);
					}
					else {
						authFailure.onFailureByUserId(
							companyId, login, headerMap, parameterMap);
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