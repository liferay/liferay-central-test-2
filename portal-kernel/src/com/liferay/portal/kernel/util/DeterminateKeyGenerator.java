/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="DeterminateKeyGenerator.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * See http://issues.liferay.com/browse/LPS-6872.
 * </p>
 *
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class DeterminateKeyGenerator {

	public static String generate(String input) {
		return generate(input, _DEFAULT_LENGTH);
	}

	public static String generate(String input, int length) {
		if (input == null) {
			throw new IllegalArgumentException("Input is null");
		}

		if (length <= 0) {
			throw new IllegalArgumentException(
				"Length is less than or equal to 0");
		}

		Map<String, Integer> seedMap = _seedMap.get();

		Integer previousSeed = seedMap.get(input);

		int seed = 0;

		if (previousSeed == null) {
			seed = input.hashCode();
		}
		else {
			seed = previousSeed;
		}

		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			int index = 0;

			if (seed > 0) {
				index = seed % 26;
			}
			else {
				index = -seed % 26;
			}

			sb.append(_CHARACTERS[index]);

			seed = _nextRandom(seed);
		}

		seedMap.put(input, seed);

		return sb.toString();
	}

	public static void reset() {
		Map<String, Integer> seedMap = _seedMap.get();

		seedMap.clear();
	}

	public static void reset(String key) {
		Map<String, Integer> seedMap = _seedMap.get();

		seedMap.remove(key);
	}

	private static int _nextRandom(int seed) {
		return (seed % 127773) * 16807 - (seed / 127773) * 2836;
	}

	private static char[] _CHARACTERS =
		"abcdefghijklmnopqrstuvwxyz".toCharArray();

	private static final int _DEFAULT_LENGTH = 4;

	private static ThreadLocal<Map<String, Integer>> _seedMap =
		new AutoResetThreadLocal<Map<String, Integer>>(
			new HashMap<String, Integer>());

}