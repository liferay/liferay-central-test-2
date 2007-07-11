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

package com.liferay.util;

import junit.framework.TestCase;

/**
 * <a href="VersionTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class VersionTest extends TestCase {

	public void testPlus() {
		_assertNotIncludes("1+", "0");
		_assertIncludes("1+", "1");
		_assertIncludes("1+", "2");
		_assertNotIncludes("1.1+", "1.0");
		_assertIncludes("1.1+", "1.1");
		_assertIncludes("1.1+", "1.10");
		_assertIncludes("1.1+", "1.2");
		_assertNotIncludes("1.1+", "2");
		_assertNotIncludes("1.1.2+", "1.1.1");
		_assertIncludes("1.1.2+", "1.1.2");
		_assertIncludes("1.1.2+", "1.1.13");
		_assertNotIncludes("1.1.2+", "1.2");
	}

	public void testStar() {
		_assertIncludes("1.1.*", "1.1.0");
		_assertIncludes("1.*", "1.1");
		_assertIncludes("*", "1");
		_assertIncludes("*", "1.2");
		_assertIncludes("*", "1.2.3");
	}

	public void testBugFixNumber() {
		_assertPrevious("1.1.0", "1.1.1");
		_assertLater("1.1.1", "1.1.0");
		_assertLater("1.2.0", "1.1.1");
	}

	public void testMinorNumber() {
		_assertPrevious("1.1", "1.1.1");
		_assertLater("1.2", "1.1.1");
		_assertLater("1.2", "1.1");
	}

	public void testMajorNumber() {
		_assertPrevious("1.1", "1.1.1");
		_assertLater("2", "1.1.1");
		_assertLater("2", "1");
	}

	private void _assertPrevious(String first, String second) {
		Version firstVersion = Version.getInstance(first);

		assertTrue(
			first + " is not previous than " + second,
			firstVersion.isPreviousVersionThan(second));
	}

	private void _assertLater(String first, String second) {
		Version firstVersion = Version.getInstance(first);

		assertTrue(
			first + " is not later than " + second,
			firstVersion.isLaterVersionThan(second.toString()));
	}

	private void _assertIncludes(String first, String second) {
		Version firstVersion = Version.getInstance(first);
		Version secondVersion = Version.getInstance(second);

		assertTrue(
			first + " does not include " + second,
			firstVersion.includes(secondVersion));
	}

	private void _assertNotIncludes(String first, String second) {
		Version firstVersion = Version.getInstance(first);
		Version secondVersion = Version.getInstance(second);

		assertFalse(
			first + " includes " + second,
			firstVersion.includes(secondVersion));
	}

}