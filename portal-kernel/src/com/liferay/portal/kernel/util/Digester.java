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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

/**
 * <a href="Digester.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class Digester {

	public static final String ENCODING = "UTF-8";

	public static final String DIGEST_ALGORITHM = "SHA";

	public static String digest(String text) {
		return digest(DIGEST_ALGORITHM, text);
	}

	public static String digest(String algorithm, String text) {
		MessageDigest mDigest = null;

		try{
			mDigest = MessageDigest.getInstance(algorithm);

			mDigest.update(text.getBytes(ENCODING));
		}
		catch (NoSuchAlgorithmException nsae) {
			_log.error(nsae, nsae);
		}
		catch (UnsupportedEncodingException uee) {
			_log.error(uee, uee);
		}

		byte[] raw = mDigest.digest();

		BASE64Encoder encoder = new BASE64Encoder();

		return encoder.encode(raw);
	}

	private static Log _log = LogFactoryUtil.getLog(Digester.class);

}