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

package com.liferay.portal.webdav;

import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.webdav.methods.Method;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * <a href="WebDAVLitmusBasicTest.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * Based on <a href="http://www.webdav.org/neon/litmus/">litmus</a> 0.12.1
 * "basic" test.
 * </p>
 *
 * @author Alexander Chow
 */
public class WebDAVLitmusBasicTest extends BaseWebDAVTestCase {

	public void test10Mkcol() {
		Tuple tuple = service(Method.MKCOL, "col", null, null);

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));
	}

	public void test11MkcolAgain() {
		Tuple tuple = service(Method.MKCOL, "col", null, null);

		assertEquals(
			HttpServletResponse.SC_METHOD_NOT_ALLOWED, getStatusCode(tuple));
	}

	public void test12DeleteColl() {
		Tuple tuple = service(Method.DELETE, "col", null, null);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));
	}

	public void test13MkcolNoParent() {
		Tuple tuple = service(Method.MKCOL, "409me/col", null, null);

		assertEquals(HttpServletResponse.SC_CONFLICT, getStatusCode(tuple));
	}

	public void test14MkcolWithBody() {
		Map<String, String> headers = new HashMap<String, String>();

		headers.put(HttpHeaders.CONTENT_TYPE, "xyz-foo/bar-512");

		Tuple tuple = service(
			Method.MKCOL, "mkcolbody", headers, _TEST_CONTENT.getBytes());

		assertEquals(
			HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
			getStatusCode(tuple));
	}

	public void test2Options() {
		Tuple tuple = service(Method.OPTIONS, StringPool.BLANK, null, null);

		assertEquals(HttpServletResponse.SC_OK, getStatusCode(tuple));

		Map<String, String> headers = getHeaders(tuple);

		String allowMethods = headers.get("Allow");

		for (String method : Method.SUPPORTED_METHODS_ARRAY) {
			assertTrue(
				"Does not allow " + method, allowMethods.contains(method));
		}
	}

	public void test3PutGet() {
		putGet("res");
	}

	public void test4PutGetUTF8() {
		putGet("res-\u20AC");
	}

	public void test5PutNoParent() {
		Tuple tuple = service(Method.MKCOL, "409me/noparent", null, null);

		assertEquals(HttpServletResponse.SC_CONFLICT, getStatusCode(tuple));

		tuple = service(
			Method.PUT, "409me/noparent.txt", null, _TEST_CONTENT.getBytes());

		assertEquals(HttpServletResponse.SC_CONFLICT, getStatusCode(tuple));
	}

	public void test6MkcolOverPlain() {
		Tuple tuple = service(Method.MKCOL, "res-\u20AC", null, null);

		assertEquals(
			HttpServletResponse.SC_METHOD_NOT_ALLOWED, getStatusCode(tuple));
	}

	public void test7Delete() {
		Tuple tuple = service(Method.DELETE, "res-\u20AC", null, null);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));
	}

	public void test8DeleteNull() {
		Tuple tuple = service(Method.DELETE, "404me", null, null);

		assertEquals(HttpServletResponse.SC_NOT_FOUND, getStatusCode(tuple));
	}

	public void test9DeleteFragment() {
		Tuple tuple = service(Method.MKCOL, "frag", null, null);

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));

		tuple = service(Method.DELETE, "frag/#ment", null, null);

		assertEquals(HttpServletResponse.SC_NOT_FOUND, getStatusCode(tuple));

		tuple = service(Method.DELETE, "frag", null, null);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));
	}

	protected void putGet(String fileName) {
		Tuple tuple = service(
			Method.PUT, fileName, null, _TEST_CONTENT.getBytes());

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));

		tuple = service(Method.GET, fileName, null, null);

		assertEquals(HttpServletResponse.SC_OK, getStatusCode(tuple));

		assertEquals(_TEST_CONTENT, getResponseBodyString(tuple));
	}

	private static final String _TEST_CONTENT =
		"LIFERAY\n" + "Enterprise.  Open Source.  For Life.";

}