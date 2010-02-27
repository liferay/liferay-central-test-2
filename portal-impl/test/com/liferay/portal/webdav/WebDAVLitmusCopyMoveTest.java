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

import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.webdav.methods.Method;

import javax.servlet.http.HttpServletResponse;

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
public class WebDAVLitmusCopyMoveTest extends BaseWebDAVTestCase {

	public void test02CopyInit() {
		Tuple tuple = service(
			Method.PUT, "copysrc", null, _TEST_CONTENT.getBytes());

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));

		tuple = service(Method.MKCOL, "copycoll", null, null);

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));
	}

	public void test03CopySimple() {
		Tuple tuple = serviceCopyOrMove(
			Method.COPY, "copysrc", null, "copydest", 0, false);

		assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));
	}

	public void test04CopyOverwrite() {
		Tuple tuple = serviceCopyOrMove(
			Method.COPY, "copysrc", null, "copydest", 0, false);

		assertEquals(
			HttpServletResponse.SC_PRECONDITION_FAILED, getStatusCode(tuple));

		tuple = serviceCopyOrMove(
			Method.COPY, "copysrc", null, "copydest", 0, true);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));

		tuple = serviceCopyOrMove(
			Method.COPY, "copysrc", null, "copycoll", 0, true);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));
	}

	public void test05NoDestColl() {
		Tuple tuple = serviceCopyOrMove(
			Method.COPY, "copysrc", null, "nonesuch/foo", 0, false);

		assertEquals(HttpServletResponse.SC_CONFLICT, getStatusCode(tuple));
	}

	public void test06CopyCleanup() {
		Tuple tuple = service(Method.DELETE, "copysrc", null, null);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));

		tuple = service(Method.DELETE, "copydest", null, null);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));

		tuple = service(Method.DELETE, "copycoll", null, null);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));
	}

	public void test07CopyColl() {
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
			tuple = service(Method.DELETE, "ccdest/foo." + i, null, null);

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

	public void test08CopyShallow() {
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

	public void test09Move() {
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
			tuple = service(Method.DELETE, "mvdest/foo." + i, null, null);

			assertEquals(
				HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));
		}

		tuple = service(Method.DELETE, "mvdest/subcoll", null, null);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));

		tuple = serviceCopyOrMove(
			Method.MOVE, "mvdest2", null, "mvnoncoll", -1, true);

		assertEquals(HttpServletResponse.SC_NO_CONTENT, getStatusCode(tuple));
	}

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