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

/**
 * <a href="KMPSearch.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * See http://en.wikipedia.org/wiki/Knuth-Morris-Pratt_algorithm.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class KMPSearch {

	public static int[] generateNexts(byte[] pattern) {
		int length = pattern.length;

		int[] nexts = new int[length];

		nexts[0] = -1;

		int i = 0;
		int j = -1;

		while (i < length - 1) {
			if ((j == -1) || (pattern[i] == pattern[j])) {
				i++;
				j++;

				nexts[i] = j;
			}
			else {
				j = nexts[j];
			}
		}

		return nexts;
	}

	public static int[] generateNexts(char[] pattern) {
		int length = pattern.length;

		int[] nexts = new int[length];

		nexts[0] = -1;

		int i = 0;
		int j = -1;

		while (i < length - 1) {
			if ((j == -1) || (pattern[i] == pattern[j])) {
				i++;
				j++;

				nexts[i] = j;
			}
			else {
				j = nexts[j];
			}
		}

		return nexts;
	}

	public static int search(byte[] text, byte[] pattern) {
		int[] nexts = generateNexts(pattern);

		return search(text, 0, text.length, pattern, nexts);
	}

	public static int search(byte[] text, byte[] pattern, int[] nexts) {
		return search(text, 0, text.length, pattern, nexts);
	}

	public static int search(
		byte[] text, int offset, byte[] pattern, int[] nexts) {

		return search(text, offset, text.length - offset, pattern, nexts);
	}

	public static int search(
		byte[] text, int offset, int length, byte[] pattern, int[] nexts) {

		int patternLength = pattern.length;

		int i = 0;
		int j = 0;

		while (i < length && j < patternLength) {
			if ((j == -1) || (text[i + offset] == pattern[j])) {
				i++;
				j++;
			}
			else {
				j = nexts[j];
			}
		}

		if (j >= patternLength) {
			return i - patternLength + offset;
		}
		else {
			return -1;
		}
	}

	public static int search(char[] text, char[] pattern) {
		int[] nexts = generateNexts(pattern);

		return search(text, 0, text.length, pattern, nexts);
	}

	public static int search(char[] text, char[] pattern, int[] nexts) {
		return search(text, 0, text.length, pattern, nexts);
	}

	public static int search(
		char[] text, int offset, char[] pattern, int[] nexts) {

		return search(text, offset, text.length - offset, pattern, nexts);
	}

	public static int search(
		char[] text, int offset, int length, char[] pattern, int[] nexts) {

		int patternLength = pattern.length;

		int i = 0;
		int j = 0;

		while (i < length && j < patternLength) {
			if ((j == -1) || (text[i + offset] == pattern[j])) {
				i++;
				j++;
			}
			else {
				j = nexts[j];
			}
		}

		if (j >= patternLength) {
			return i - patternLength + offset;
		}
		else {
			return -1;
		}
	}

}