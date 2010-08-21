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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.nio.charset.CharsetDecoderUtil;
import com.liferay.portal.kernel.nio.charset.CharsetEncoderUtil;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import java.util.BitSet;

/**
 * @author Shuyang Zhou
 */
public class URLCodec {

	public static String decodeURL(String encodedURLString) {
		return decodeURL(encodedURLString, StringPool.UTF8);
	}

	public static String decodeURL(
		String encodedURLString, String charsetName) {
		if (encodedURLString == null) {
			return null;
		}

		int encodedLength = encodedURLString.length();

		if (encodedLength == 0) {
			return StringPool.BLANK;
		}

		StringBuilder sb = new StringBuilder(encodedLength);

		CharsetDecoder charsetDecoder = null;

		boolean changed = false;
		for(int i = 0; i < encodedLength; i++) {
			char c = encodedURLString.charAt(i);
			switch(c) {
				case '+':
					sb.append(CharPool.SPACE);
					changed = true;
					break;
				case '%':
					ByteBuffer byteBuffer = getEncodedByteBuffer(
						encodedURLString, i);
					if (charsetDecoder == null) {
						charsetDecoder = CharsetDecoderUtil.getCharsetDecoder(
							charsetName);
					}

					CharBuffer charBuffer = null;

					try {
						charBuffer = charsetDecoder.decode(byteBuffer);
					}
					catch(CharacterCodingException cce) {
						_log.error(cce, cce);
						return StringPool.BLANK;
					}

					sb.append(charBuffer);
					i += byteBuffer.capacity() * 3;
					break;
				default:
					sb.append(c);
					break;
			}
		}

		if (sb.length() == encodedLength && !changed) {
			return encodedURLString;
		}
		else {
			return sb.toString();
		}

	}

	public static String encodeURL(String rawURLString) {
		return encodeURL(rawURLString, StringPool.UTF8, false);
	}

	public static String encodeURL(String rawURLString, boolean escapeSpaces) {
		return encodeURL(rawURLString, StringPool.UTF8, escapeSpaces);
	}

	public static String encodeURL(
		String rawURLString, String charsetName, boolean escapeSpaces) {
		if (rawURLString == null) {
			return null;
		}

		int rawLength = rawURLString.length();

		if (rawLength == 0) {
			return StringPool.BLANK;
		}

		StringBuilder sb = new StringBuilder(rawLength);

		CharsetEncoder charsetEncoder = null;
		char[] hexes = new char[2];
		boolean changed = false;
		for(int i = 0; i < rawLength; i++) {
			char c = rawURLString.charAt(i);
			if (_remainChars.get(c)) {
				sb.append(c);
			}
			else if (c == CharPool.SPACE) {
				if (escapeSpaces) {
					sb.append("%20");
				}
				else {
					sb.append(CharPool.PLUS);
				}
				changed = true;
			}
			else {
				CharBuffer charBuffer = getRawCharBuffer(rawURLString, i);
				if (charsetEncoder == null) {
					charsetEncoder = CharsetEncoderUtil.getCharsetEncoder(
						charsetName);
				}

				ByteBuffer byteBuffer = null;

				try {
					byteBuffer = charsetEncoder.encode(charBuffer);
				}
				catch(CharacterCodingException cce) {
					_log.error(cce, cce);
					return StringPool.BLANK;
				}

				for(int j = byteBuffer.position(); j < byteBuffer.limit();
					j++) {
					sb.append(CharPool.PERCENT);
					sb.append(UnicodeFormatter.byteToHex(
						byteBuffer.get(), hexes));
				}
				i += charBuffer.capacity();
			}
		}

		if (sb.length() == rawLength && !changed) {
			return rawURLString;
		}
		else {
			return sb.toString();
		}
	}

	private static int charToHexNumber(char c) {

		// Fast fail comparison order for performance, don't change.
		// CharPool.LOWER_CASE_A > CharPool.UPPER_CASE_A > CharPool.NUMBER_0

		if (c >= CharPool.LOWER_CASE_A && c <= CharPool.LOWER_CASE_Z) {
			return c -CharPool.LOWER_CASE_A + 10;
		}

		if (c >= CharPool.UPPER_CASE_A && c <= CharPool.UPPER_CASE_Z) {
			return c -CharPool.UPPER_CASE_A + 10;
		}

		if (c >= CharPool.NUMBER_0 && c <= CharPool.NUMBER_9) {
			return c - CharPool.NUMBER_0;
		}

		throw new IllegalArgumentException(
			"char c={" + c + "} is not a hex char");
	}

	private static ByteBuffer getEncodedByteBuffer(
		String encodedString, int startIndex) {

		int count = 1;

		for(int index = startIndex + 3; index < encodedString.length();
			index += 3) {
			if (encodedString.charAt(index) == CharPool.PERCENT) {
				count++;
			}
			else {
				break;
			}
		}

		ByteBuffer byteBuffer = ByteBuffer.allocate(count);

		for(int index = startIndex; index < startIndex + count * 3;
			index += 3) {
			int high = charToHexNumber(encodedString.charAt(index + 1));
			int low = charToHexNumber(encodedString.charAt(index + 2));
			byteBuffer.put((byte)((high << 4) + low));
		}

		byteBuffer.flip();
		return byteBuffer;
	}

	private static CharBuffer getRawCharBuffer(
		String rawString, int startIndex) {

		int count = 0;
		for(int index = startIndex; index < rawString.length(); index++) {
			char rawChar = rawString.charAt(index);
			if (!_remainChars.get(rawChar)) {
				count++;
				if (Character.isHighSurrogate(rawChar)) {
					if (index + 1 < rawString.length() &&
						Character.isLowSurrogate(rawString.charAt(index + 1))) {
						count++;
					}
				}
			}
		}

		return CharBuffer.wrap(rawString, startIndex, startIndex + count);
	}

	private static final Log _log = LogFactoryUtil.getLog(URLCodec.class);

	private static final BitSet _remainChars = new BitSet(256);

	static {
		for (int i = 'a'; i <= 'z'; i++) {
			_remainChars.set(i);
		}
		for (int i = 'A'; i <= 'Z'; i++) {
			_remainChars.set(i);
		}
		for (int i = '0'; i <= '9'; i++) {
			_remainChars.set(i);
		}
		_remainChars.set('-');
		_remainChars.set('_');
		_remainChars.set('.');
		_remainChars.set('*');
	}

}