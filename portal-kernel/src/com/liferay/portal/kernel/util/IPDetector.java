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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <a href="IPDetector.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class IPDetector {

	public static boolean isPrefersV4() {
		if (_prefersV4 == null) {
			_prefersV4 = Boolean.valueOf(
				System.getProperty("java.net.preferIPv4Stack"));
		}

		return _prefersV4.booleanValue();
	}

	public static boolean isPrefersV6() {
		if (_prefersV6 == null) {
			_prefersV6 = Boolean.valueOf(
				System.getProperty("java.net.preferIPv6Stack"));
		}

		return _prefersV6.booleanValue();
	}

	public static boolean isSupportsV6() {
		if (_suppportsV6 == null) {
			_suppportsV6 = Boolean.FALSE;

			try {
				InetAddress[] inetAddresses = InetAddress.getAllByName(
					"localhost");

				for (InetAddress inetAddress : inetAddresses) {
					if (inetAddress.getHostAddress().contains(":")) {
						_suppportsV6 = Boolean.TRUE;

						break;
					}
				}
			}
			catch (UnknownHostException uhe) {
				_log.error(uhe, uhe);
			}
		}

		return _suppportsV6.booleanValue();
	}

	private static Log _log = LogFactoryUtil.getLog(IPDetector.class);

	private static Boolean _prefersV4;
	private static Boolean _prefersV6;
	private static Boolean _suppportsV6;

}