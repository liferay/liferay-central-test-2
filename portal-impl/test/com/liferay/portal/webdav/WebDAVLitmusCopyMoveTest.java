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

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import com.liferay.portal.kernel.util.Tuple;

/**
 * <a href="WebDAVLitmusCopyMoveTest.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * Based on <a href="http://www.webdav.org/neon/litmus/">litmus</a> 0.12.1
 * "copymove" test.
 * </p>
 *
 * @author Alexander Chow
 */
public class WebDAVLitmusCopyMoveTest extends BaseWebDAVTest {

	@Test
	public void test2CopyInit() {
		Tuple tuple = service(
			Method.PUT, "copysrc", null, _TEST_CONTENT.getBytes());

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));

		tuple = service(
			Method.MKCOL, "copycoll", null, null);

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));
	}

	@Test
	public void test3CopySimple() {
		Tuple tuple = serviceCopyOrMove(
			Method.COPY, "copysrc", null, "copydest", 0, false);

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));
	}

	@Test
	public void test4CopyOverwrite() {
		Tuple tuple = serviceCopyOrMove(
			Method.COPY, "copysrc", null, "copydest", 0, false);

		assertEquals(
			HttpServletResponse.SC_PRECONDITION_FAILED, getStatusCode(tuple));

		tuple = serviceCopyOrMove(
			Method.COPY, "copysrc", null, "copydest", 0, true);

		assertEquals(
			HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));

		tuple = serviceCopyOrMove(
			Method.COPY, "copysrc", null, "copycoll", 0, true);

		assertEquals(
			HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));
	}

	@Test
	public void test5NoDestColl() {
		Tuple tuple = serviceCopyOrMove(
			Method.COPY, "copysrc", null, "nonesuch/foo", 0, false);

		assertEquals(HttpServletResponse.SC_CONFLICT, getStatusCode(tuple));
	}

	@Test
	public void test6CopyCleanup() {
		Tuple tuple = service(Method.DELETE, "copysrc", null, null);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));

		tuple = service(Method.DELETE, "copydest", null, null);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));

		tuple = service(Method.DELETE, "copycoll", null, null);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));
	}

	@Test
	public void test7CopyColl() {
		Tuple tuple = service(Method.MKCOL, "ccsrc", null, null);

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));

		for (int i = 0; i < 10; i++) {
			tuple = service(
				Method.PUT, "ccsrc/foo." + i, null, _TEST_CONTENT.getBytes());

			assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));
		}

		tuple = service(Method.MKCOL, "ccsrc/subcoll", null, null);

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));

		tuple = serviceCopyOrMove(
			Method.COPY, "ccsrc", null, "ccdest", -1, false);

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));

		tuple = serviceCopyOrMove(
			Method.COPY, "ccsrc", null, "ccdest2", -1, false);

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));

		tuple = serviceCopyOrMove(
			Method.COPY, "ccsrc", null, "ccdest2", -1, false);

		assertEquals(
			HttpServletResponse.SC_PRECONDITION_FAILED, getStatusCode(tuple));

		tuple = serviceCopyOrMove(
			Method.COPY, "ccsrc", null, "ccdest", -1, true);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));

		tuple = service(Method.DELETE, "ccsrc", null, null);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));

		for (int i = 0; i < 10; i++) {
			tuple = service(
				Method.DELETE, "ccdest/foo." + i, null, null);

			assertEquals(
				HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));
		}

		tuple = service(Method.DELETE, "ccdest/subcoll", null, null);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));

		tuple = service(Method.DELETE, "ccdest", null, null);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));

		tuple = service(Method.DELETE, "ccdest2", null, null);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));
	}

	@Test
	public void test8CopyShallow() {
		Tuple tuple = service(Method.MKCOL, "ccsrc", null, null);

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));

		tuple = service(
			Method.PUT, "ccsrc/foo", null, _TEST_CONTENT.getBytes());

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));

		tuple = serviceCopyOrMove(
			Method.COPY, "ccsrc", null, "ccdest", 0, false);

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));

		tuple = service(Method.DELETE, "ccsrc", null, null);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));

		tuple = service(Method.DELETE, "foo", null, null);

		assertEquals(HttpServletResponse.SC_NOT_FOUND, getStatusCode(tuple));

		tuple = service(Method.DELETE, "ccdest", null, null);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));
	}

	@Test
	public void test9Move() {
		Tuple tuple = service(
			Method.PUT, "move", null, _TEST_CONTENT.getBytes());

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));

		tuple = service(Method.PUT, "move2", null, _TEST_CONTENT.getBytes());

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));

		tuple = service(Method.MKCOL, "movecoll", null, null);

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));
		
		tuple = serviceCopyOrMove(
			Method.MOVE, "move", null, "movedest", 0, false);

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));

		tuple = serviceCopyOrMove(
			Method.MOVE, "move2", null, "movedest", 0, false);

		assertEquals(
			HttpServletResponse.SC_PRECONDITION_FAILED, getStatusCode(tuple));
		
		tuple = serviceCopyOrMove(
			Method.MOVE, "move2", null, "movedest", 0, true);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));

		tuple = serviceCopyOrMove(
			Method.MOVE, "movedest", null, "movecoll", 0, true);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));

		tuple = service(Method.DELETE, "movecoll", null, null);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));
	}

	@Test
	public void test10MoveColl() {
		Tuple tuple = service(Method.MKCOL, "mvsrc", null, null);

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));

		for (int i = 0; i < 10; i++) {
			tuple = service(
				Method.PUT, "mvsrc/foo." + i, null, _TEST_CONTENT.getBytes());

			assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));
		}

		tuple = service(
			Method.PUT, "mvnoncoll", null, _TEST_CONTENT.getBytes());

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));

		tuple = service(Method.MKCOL, "mvsrc/subcoll", null, null);

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));

		tuple = serviceCopyOrMove(
			Method.COPY, "mvsrc", null, "mvdest2", -1, false);

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));

		tuple = serviceCopyOrMove(
			Method.MOVE, "mvsrc", null, "mvdest", -1, false);

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));

		tuple = serviceCopyOrMove(
			Method.MOVE, "mvdest", null, "mvdest2", -1, false);

		assertEquals(
			HttpServletResponse.SC_PRECONDITION_FAILED, getStatusCode(tuple));

		tuple = serviceCopyOrMove(
			Method.MOVE, "mvdest2", null, "mvdest", -1, true);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));

		tuple = serviceCopyOrMove(
			Method.COPY, "mvdest", null, "mvdest2", -1, false);

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));

		for (int i = 0; i < 10; i++) {
			tuple = service(
				Method.DELETE, "mvdest/foo." + i, null, null);

			assertEquals(
				HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));
		}

		tuple = service(Method.DELETE, "mvdest/subcoll", null, null);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));

		tuple = serviceCopyOrMove(
			Method.MOVE, "mvdest2", null, "mvnoncoll", -1, true);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));
	}

	@Test
	public void test11MoveCleanup() {
		Tuple tuple = service(Method.DELETE, "mvdest", null, null);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));

		tuple = service(Method.DELETE, "mvdest2", null, null);

		assertEquals(HttpServletResponse.SC_NOT_FOUND, getStatusCode(tuple));

		tuple = service(Method.DELETE, "mvnoncoll", null, null);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));
	}

	private static final String _TEST_CONTENT =
		"LIFERAY\n" + "Enterprise.  Open Source.  For Life.";

}