/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import junit.framework.TestCase;

/**
 * <a href="HttpImplTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class HttpImplTest extends TestCase {

	public void testAddParameterToNoParams1() {
		assertAddParameter("", "?param=p");
	}

	public void testAddParameterToNoParams2() {
		assertAddParameter("?", "?param=p");
	}

	public void testAddParameterDuplicate1() {
		assertAddParameter("?param=1", "?param=1&param=p");
	}

	public void testAddParameterDuplicate2() {
		assertAddParameter("?param=1&z=w", "?param=1&z=w&param=p");
	}

	public void testAddParameterToAnchor1() {
		assertAddParameter("#", "?param=p#");
	}

	public void testAddParameterToAnchor2() {
		assertAddParameter("#a", "?param=p#a");
	}

	public void testAddParameterToAnchorExisting() {
		assertAddParameter("?param=1#a", "?param=1&param=p#a");
	}

	public void testRemoveParameterToNoParams1() {
		assertRemoveParameter("", "");
	}

	public void testRemoveParameterToNoParams2() {
		assertRemoveParameter("?", "");
	}

	public void testRemoveParameterExisting1() {
		assertRemoveParameter("?param=1", "");
	}

	public void testRemoveParameterExisting2() {
		assertRemoveParameter("?param=1&z=w", "?z=w");
	}

	public void testRemoveParameterToAnchor1() {
		assertRemoveParameter("#", "#");
	}

	public void testRemoveParameterToAnchor2() {
		assertRemoveParameter("#a", "#a");
	}

	public void testRemoveParameterToAnchorAndOtherParam() {
		assertRemoveParameter("?z=w#", "?z=w#");
	}

	public void testRemoveParameterToAnchorExisting() {
		assertRemoveParameter("?param=1#a", "#a");
	}

	public void testSetParameterToNoParams1() {
		assertSetParameter("", "?param=p");
	}

	public void testSetParameterToNoParams2() {
		assertSetParameter("?", "?param=p");
	}

	public void testSetParameterDuplicate1() {
		assertSetParameter("?param=1", "?param=p");
	}

	public void testSetParameterDuplicate2() {
		assertSetParameter("?param=1&z=w", "?z=w&param=p");
	}

	public void testSetParameterToAnchor1() {
		assertSetParameter("#", "?param=p#");
	}

	public void testSetParameterToAnchor2() {
		assertSetParameter("#a", "?param=p#a");
	}

	public void testSetParameterToAnchorAndOtherParam() {
		assertRemoveParameter("?z=w#", "?z=w#");
	}

	public void testSetParameterToAnchorExisting() {
		assertSetParameter("?param=1#a", "?param=p#a");
	}

	protected void assertAddParameter(String original, String expected) {
		String originalURL = _baseURL + original;
		String expectedURL = _baseURL + expected;

		String resultURL = _http.addParameter(originalURL, "param", "p");

		assertEquals(expectedURL, resultURL);
	}

	protected void assertRemoveParameter(String original, String expected) {
		String originalURL = _baseURL + original;
		String expectedURL = _baseURL + expected;

		String resultURL = _http.removeParameter(originalURL, "param");

		assertEquals(expectedURL, resultURL);
	}

	protected void assertSetParameter(String original, String expected) {
		String originalURL = _baseURL + original;
		String expectedURL = _baseURL + expected;

		String resultURL = _http.setParameter(originalURL, "param", "p");

		assertEquals(expectedURL, resultURL);
	}

	private HttpImpl _http = new HttpImpl();
	private String _baseURL = "http://a.com/foo";;

}