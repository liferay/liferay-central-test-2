/*
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
 *
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.kernel.cache.key;

import com.liferay.portal.kernel.util.StringBundler;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <a href="JavaMD5CacheKeyGenerator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class JavaMD5CacheKeyGenerator implements CacheKeyGenerator {

	public String getCacheKey(String proposedKey) {

		if ((_maxLengthTrigger > -1) &&
			(proposedKey.length() < _maxLengthTrigger)) {
			
			return proposedKey;
		}

		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(proposedKey.getBytes());

			byte[] hashBytes = digest.digest();

			StringBundler keyBuilder = new StringBundler(2 * hashBytes.length);

			for (int i = 0; i < hashBytes.length; i++) {

				int value = hashBytes[i] & 0xff;

				keyBuilder.append((char) _HEX_CHARACTERS[value >> 4]);
				keyBuilder.append((char) _HEX_CHARACTERS[value & 0xf]);
			}

			return keyBuilder.toString();
		}
		catch (NoSuchAlgorithmException e) {
			return proposedKey;
		}
	}

	public void setMaxLengthTrigger(int maxLengthTrigger) {
		_maxLengthTrigger = maxLengthTrigger;
	}

	private static final byte[] _HEX_CHARACTERS = {
		'0', '1', '2', '3', '4', '5',
		'6', '7', '8', '9', 'a', 'b',
		'c', 'd', 'e', 'f'
	};

	private int _maxLengthTrigger = -1;
}
