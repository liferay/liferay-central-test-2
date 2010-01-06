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

package com.liferay.util;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="ThreadLocalRandomWordGenerator.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Shuyang Zhou
 */
public class ThreadLocalRandomWordGenerator {

	public static String getRandomWord(String key, int length) {

		if (key == null) {
			throw new IllegalArgumentException("Key is null");
		}
		Map<String, Integer> keySeedMap = _THREADLOCAL_KEY_SEED_MAP.get();
		Integer lastSeed = keySeedMap.get(key);

		int seed;
		if (lastSeed == null) {
			// first time call, use key hashcode as initial seed
			seed = key.hashCode();
		}
		else {
			seed = lastSeed;
		}

		StringBuilder sb = new StringBuilder(length);
		for(int i = 0; i< length; i++) {
			int index = seed > 0 ? seed % 26 : -seed % 26;
			sb.append(_CHARACTERS[index]);
			seed = nextRandom(seed);
		}

		keySeedMap.put(key, seed);
		return sb.toString();
	}

	public static void reset() {
		_THREADLOCAL_KEY_SEED_MAP.get().clear();
	}

	public static void reset(String key) {
		_THREADLOCAL_KEY_SEED_MAP.get().remove(key);
	}

	private static int nextRandom(int seed) {
		return (seed % 127773) * 16807 - (seed / 127773) * 2836;
	}

	private static ThreadLocal<Map<String, Integer>> _THREADLOCAL_KEY_SEED_MAP =
		new ThreadLocal<Map<String, Integer>>() {

		protected Map<String, Integer> initialValue() {
			return new HashMap<String, Integer>();
		}

	};
	private static char[] _CHARACTERS =
		"abcdefghijklmnopqrstuvwxyz".toCharArray();

}