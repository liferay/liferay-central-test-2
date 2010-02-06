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

package com.liferay.portal.util;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.InMemoryConsumerAssociationStore;
import org.openid4java.consumer.InMemoryNonceVerifier;

/**
 * <a href="OpenIdUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class OpenIdUtil {

	public static ConsumerManager getConsumerManager() {
		return _instance._manager;
	}

	public static String getScreenName(String openId) {
		String result = normalize(openId);

		if (result.startsWith(Http.HTTP_WITH_SLASH)) {
			result = result.substring(Http.HTTP_WITH_SLASH.length());
		}

		if (result.startsWith(Http.HTTPS_WITH_SLASH)) {
			result = result.substring(Http.HTTPS_WITH_SLASH.length());
		}

		result = StringUtil.replace(
			result,
			new String[] {StringPool.SLASH, StringPool.UNDERLINE},
			new String[] {StringPool.PERIOD, StringPool.PERIOD});

		return result;
	}

	public static boolean isEnabled(long companyId) throws SystemException {
		return PrefsPropsUtil.getBoolean(
			companyId, PropsKeys.OPEN_ID_AUTH_ENABLED,
			PropsValues.OPEN_ID_AUTH_ENABLED);
	}

	public static String normalize(String identity) {
		String result = identity;

		if (result.endsWith(StringPool.SLASH)) {
			result = result.substring(0, result.length() - 1);
		}

		return result;
	}

	private OpenIdUtil() {
		try {
			_manager = new ConsumerManager();

			_manager.setAssociations(new InMemoryConsumerAssociationStore());
			_manager.setNonceVerifier(new InMemoryNonceVerifier(5000));
		}
		catch (ConsumerException ce) {
			_log.error(ce.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(OpenIdUtil.class);

	private static OpenIdUtil _instance = new OpenIdUtil();

	private ConsumerManager _manager;

}