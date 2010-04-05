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

package com.liferay.portal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.Digester;

import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

/**
 * <a href="DigesterImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DigesterImpl implements Digester {

	public String digest(String text) {
		return digest(Digester.DIGEST_ALGORITHM, text);
	}

	public String digest(String algorithm, String text) {
		MessageDigest messageDigest = null;

		try{
			messageDigest = MessageDigest.getInstance(algorithm);

			messageDigest.update(text.getBytes(Digester.ENCODING));
		}
		catch (NoSuchAlgorithmException nsae) {
			_log.error(nsae, nsae);
		}
		catch (UnsupportedEncodingException uee) {
			_log.error(uee, uee);
		}

		byte[] bytes = messageDigest.digest();

		if (_BASE_64) {
			return Base64.encode(bytes);
		}
		else {
			return new String(Hex.encodeHex(bytes));
		}
	}

	private static final boolean _BASE_64 =
		PropsValues.PASSWORDS_DIGEST_ENCODING.equals("base64");

	private static Log _log = LogFactoryUtil.getLog(Digester.class);

}