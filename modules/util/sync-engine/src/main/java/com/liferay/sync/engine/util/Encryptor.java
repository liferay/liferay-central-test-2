/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.sync.engine.util;

import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @author Dennis Ju
 */
public class Encryptor {

	public static String decrypt(String value) throws Exception {
		if (value == null) {
			return "";
		}

		SecretKey secretKey = new SecretKeySpec(_PASSWORD, _ALGORITHM);

		Cipher cipher = Cipher.getInstance(_ALGORITHM);

		cipher.init(Cipher.DECRYPT_MODE, secretKey);

		for (int i = 0; i < _ITERATIONS; i++) {
			byte[] decodedBytes = Base64.decodeBase64(value);

			byte[] decryptedBytes = cipher.doFinal(decodedBytes);

			value = new String(decryptedBytes, _UTF8_CHARSET);
		}

		return value;
	}

	public static String encrypt(String value) throws Exception {
		if (value == null) {
			return "";
		}

		SecretKey secretKey = new SecretKeySpec(_PASSWORD, _ALGORITHM);

		Cipher cipher = Cipher.getInstance(_ALGORITHM);

		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		for (int i = 0; i < _ITERATIONS; i++) {
			byte[] encryptedBytes = cipher.doFinal(
				value.getBytes(_UTF8_CHARSET));

			value = Base64.encodeBase64String(encryptedBytes);
		}

		return value;
	}

	private static final String _ALGORITHM = "AES";

	private static final int _ITERATIONS = 8;

	private static final byte[] _PASSWORD = {
		(byte)0x56, (byte)0x78, (byte)0x7e, (byte)0x36, (byte)0x50, (byte)0x64,
		(byte)0x7a, (byte)0x2e, (byte)0x2b, (byte)0x68, (byte)0x25, (byte)0x58,
		(byte)0x45, (byte)0x39, (byte)0x4a, (byte)0x6f
	};

	private static final Charset _UTF8_CHARSET = Charset.forName("UTF-8");

}