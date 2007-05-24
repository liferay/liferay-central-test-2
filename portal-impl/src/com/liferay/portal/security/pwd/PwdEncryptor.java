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

package com.liferay.portal.security.pwd;

import com.liferay.portal.PwdEncryptorException;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import java.util.Random;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * <a href="PwdEncryptor.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 *
 */
public class PwdEncryptor {

	public static final String PASSWORDS_ENCRYPTION_ALGORITHM =
		GetterUtil.getString(PropsUtil.get(
			PropsUtil.PASSWORDS_ENCRYPTION_ALGORITHM)).toUpperCase();

	public static final String TYPE_MD5 = "MD5";

	public static final String TYPE_NONE = "NONE";

	public static final String TYPE_SHA = "SHA";

	public static final String TYPE_SSHA = "SSHA";

	public static String encrypt(String clearTextPwd)
		throws PwdEncryptorException {

		return encrypt(PASSWORDS_ENCRYPTION_ALGORITHM, clearTextPwd, null);
	}

	public static String encrypt(String clearTextPwd, String currentEncPwd)
		throws PwdEncryptorException {

		return encrypt(
			PASSWORDS_ENCRYPTION_ALGORITHM, clearTextPwd, currentEncPwd);
	}

	public static String encrypt(
			String algorithm, String clearTextPwd, String currentEncPwd)
		throws PwdEncryptorException {

		if (algorithm.equals(TYPE_NONE)) {
			return clearTextPwd;
		}
		else if (algorithm.equals(TYPE_SSHA)) {
			byte[] saltBytes = _getSaltFromSSHA(currentEncPwd);

			return encodePassword(algorithm, clearTextPwd, saltBytes);
		}
		else {
			return encodePassword(algorithm, clearTextPwd, null);
		}
	}

	protected static String encodePassword(
			String algorithm, String clearTextPwd, byte[] saltBytes)
		throws PwdEncryptorException {

		if (algorithm.equals(TYPE_SSHA)) {
			try {
				byte[] clearTextPwdBytes =
					clearTextPwd.getBytes(Digester.ENCODING);

			    // Create a byte array of salt bytes appeneded to password bytes

			    byte[] pwdPlusSalt =
			         new byte[clearTextPwdBytes.length + saltBytes.length];

			    System.arraycopy(
					clearTextPwdBytes, 0, pwdPlusSalt, 0,
					clearTextPwdBytes.length);

			    System.arraycopy(
			    	saltBytes, 0, pwdPlusSalt, clearTextPwdBytes.length,
			        saltBytes.length);

			    // Digest byte array

			    MessageDigest sha1Digest = MessageDigest.getInstance("SHA-1");

				byte[] pwdPlusSaltHash = sha1Digest.digest(pwdPlusSalt);

			    // Appends salt bytes to the SHA-1 digest.

			    byte[] digestPlusSalt =
			         new byte[pwdPlusSaltHash.length + saltBytes.length];

			    System.arraycopy(
			    	pwdPlusSaltHash, 0, digestPlusSalt, 0,
			    	pwdPlusSaltHash.length);

			    System.arraycopy(
			    	saltBytes, 0, digestPlusSalt, pwdPlusSaltHash.length,
			        saltBytes.length);

			    // Base64 encode and format string

				BASE64Encoder encoder = new BASE64Encoder();

				return encoder.encode(digestPlusSalt);
			}
			catch (NoSuchAlgorithmException nsae) {
				throw new PwdEncryptorException(nsae.getMessage());
			}
			catch (UnsupportedEncodingException uee) {
				throw new PwdEncryptorException(uee.getMessage());
			}
		}
		else {
			return Digester.digest(algorithm, clearTextPwd);
		}
	}

	private static byte[] _getSaltFromSSHA(String sshaString)
		throws PwdEncryptorException {

		byte[] saltBytes = new byte[8];

		if (Validator.isNull(sshaString)) {

			// Generate random salt

			Random random = new SecureRandom();

			random.nextBytes(saltBytes);
		}
		else {

			// Extract salt from encrypted password

		    BASE64Decoder decoder = new BASE64Decoder();

			try {
			    byte[] digestPlusSalt = decoder.decodeBuffer(sshaString);
			    byte[] digestBytes = new byte[digestPlusSalt.length - 8];

			    System.arraycopy(
			    	digestPlusSalt, 0, digestBytes, 0, digestBytes.length);

			    System.arraycopy(
			    	digestPlusSalt, digestBytes.length, saltBytes, 0,
			    	saltBytes.length);
			}
			catch (IOException ioe) {
				throw new PwdEncryptorException(
					"Unable to extract salt from encrypted password: " +
						ioe.getMessage());
			}
		}

		return saltBytes;
	}

}