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

	public void test02Options() {
		Tuple tuple = service(Method.OPTIONS, StringPool.BLANK, null, null);

		assertEquals(HttpServletResponse.SC_OK, getStatusCode(tuple));

		Map<String, String> headers = getHeaders(tuple);

		String allowMethods = headers.get("Allow");

		for (String method : Method.SUPPORTED_METHODS_ARRAY) {
			assertTrue(
				"Does not allow " + method, allowMethods.contains(method));
		}
	}

	public void test03PutGet() {
		putGet("res");
	}

	public void test04PutGetUTF8() {
		putGet("res-\u20AC");
	}

	public void test05PutNoParent() {
		Tuple tuple = service(Method.MKCOL, "409me/noparent", null, null);

		assertEquals(HttpServletResponse.SC_CONFLICT, getStatusCode(tuple));

		tuple = service(
			Method.PUT, "409me/noparent.txt", null, _TEST_CONTENT.getBytes());

		assertEquals(HttpServletResponse.SC_CONFLICT, getStatusCode(tuple));
	}

	public void test06MkcolOverPlain() {
		Tuple tuple = service(Method.MKCOL, "res-\u20AC", null, null);

		assertEquals(
			HttpServletResponse.SC_METHOD_NOT_ALLOWED, getStatusCode(tuple));
	}

	public void test07Delete() {
		Tuple tuple = service(Method.DELETE, "res-\u20AC", null, null);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));
	}

	public void test08DeleteNull() {
		Tuple tuple = service(Method.DELETE, "404me", null, null);

		assertEquals(HttpServletResponse.SC_NOT_FOUND, getStatusCode(tuple));
	}

	public void test09DeleteFragment() {
		Tuple tuple = service(Method.MKCOL, "frag", null, null);

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));

		tuple = service(Method.DELETE, "frag/#ment", null, null);

		assertEquals(HttpServletResponse.SC_NOT_FOUND, getStatusCode(tuple));

		tuple = service(Method.DELETE, "frag", null, null);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));
	}

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