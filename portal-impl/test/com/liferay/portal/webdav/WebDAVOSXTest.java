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

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.webdav.methods.Method;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * <a href="WebDAVOSXTest.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * Based on Microsoft Office 2008 for OS X.
 * </p>
 *
 * @author Alexander Chow
 */
public class WebDAVOSXTest extends BaseWebDAVTestCase {

	public void testMSOffice0Setup() throws Exception {
		_testFileBytes = FileUtil.getBytes(new File(_OFFICE_TEST_DOCX));
		_testMetaBytes = FileUtil.getBytes(new File(_OFFICE_TEST_META_DOCX));
		_testDeltaBytes = FileUtil.getBytes(new File(_OFFICE_TEST_DELTA_DOCX));
	}

	public void testMSOffice1Create() throws Exception {
		Tuple tuple = null;

		assertCode(
			HttpServletResponse.SC_NOT_FOUND, servicePropFind(_testFileName));
		assertCode(
			HttpServletResponse.SC_CREATED,
			servicePut(_testFileName, _testFileBytes, getLock(_testFileName)));

		for (int i = 0; i < 3; i++) {
			lock(_testFileName);
			unlock(_testFileName);
		}

		lock(_testFileName);
		assertCode(
			HttpServletResponse.SC_CREATED,
			servicePut(_testFileName, _testFileBytes, getLock(_testFileName)));
		unlock(_testFileName);

		for (int i = 0 ; i < 3; i++) {
			lock(_testFileName);
			tuple = serviceGet(_testFileName);
			assertCode(HttpServletResponse.SC_OK, tuple);
			assertBytes(_testFileBytes, getResponseBody(tuple));
			unlock(_testFileName);
		}

		for (int i = 0; i < 2; i++) {
			assertCode(
				HttpServletResponse.SC_NOT_FOUND,
				servicePropFind(_testMetaName));
			assertCode(
				HttpServletResponse.SC_CREATED,
				servicePut(_testMetaName, _testMetaBytes));
			lock(_testMetaName);
			assertCode(
				HttpServletResponse.SC_CREATED,
				servicePut(
					_testMetaName, _testMetaBytes, getLock(_testMetaName)));
			assertCode(
				WebDAVUtil.SC_MULTI_STATUS, servicePropFind(_testMetaName));
			unlock(_testMetaName);
			lock(_testMetaName);

			if (i == 0) {
				unlock(_testMetaName);
				assertCode(
					HttpServletResponse.SC_NO_CONTENT,
					serviceDelete(_testMetaName));
			}
			else {
				tuple = serviceGet(_testMetaName);
				assertCode(HttpServletResponse.SC_OK, tuple);
				assertBytes(_testMetaBytes, getResponseBody(tuple));
				assertCode(
					HttpServletResponse.SC_CREATED,
					servicePut(
						_testMetaName, _testMetaBytes, getLock(_testMetaName)));
				assertCode(
					WebDAVUtil.SC_MULTI_STATUS, servicePropFind(_testMetaName));
				unlock(_testMetaName);
				lock(_testMetaName);
				tuple = serviceGet(_testMetaName);
				assertCode(HttpServletResponse.SC_OK, tuple);
				assertBytes(_testMetaBytes, getResponseBody(tuple));
				unlock(_testMetaName);
			}
		}
	}

	public void testMSOffice2Open() throws Exception {
		Tuple tuple = null;

		assertCode(WebDAVUtil.SC_MULTI_STATUS, servicePropFind(_testFileName));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND, servicePropFind("MCF-Test.docx"));
		lock(_testFileName);
		tuple = serviceGet(_testFileName);
		assertCode(HttpServletResponse.SC_OK, tuple);
		assertBytes(_testFileBytes, getResponseBody(tuple));
		unlock(_testFileName);
		lock(_testFileName);
		tuple = serviceGet(_testFileName);
		assertCode(HttpServletResponse.SC_OK, tuple);
		assertBytes(_testFileBytes, getResponseBody(tuple));
	}

	public void testMSOffice3Modify() throws Exception {
		Tuple tuple = null;

		assertCode(
			HttpServletResponse.SC_NOT_FOUND, servicePropFind(_tempFileName1));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND,
			servicePropFind("MCF-Word Work File D_1.tmp"));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND, servicePropFind(_tempFileName1));
		assertCode(
			HttpServletResponse.SC_CREATED,
			servicePut(_tempFileName1, _testDeltaBytes));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND, servicePropFind(_tempMetaName1));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND, servicePropFind(_tempMetaName1));
		assertCode(
			HttpServletResponse.SC_CREATED,
			servicePut(_tempMetaName1, _testMetaBytes));
		lock(_tempMetaName1);
		assertCode(
			HttpServletResponse.SC_CREATED,
			servicePut(
				_tempMetaName1, _testMetaBytes, getLock(_tempMetaName1)));
		assertCode(
			WebDAVUtil.SC_MULTI_STATUS, servicePropFind(_tempFileName1));
		unlock(_tempMetaName1);

		lock(_tempFileName1);
		unlock(_tempFileName1);
		lock(_tempFileName1);
		assertCode(
			HttpServletResponse.SC_CREATED,
			servicePut(
				_tempFileName1, _testDeltaBytes, getLock(_tempFileName1)));
		assertCode(
			WebDAVUtil.SC_MULTI_STATUS, servicePropFind(_tempFileName1));

		unlock(_testFileName);
		lock(_testFileName);
		tuple = serviceGet(_testFileName);
		assertCode(HttpServletResponse.SC_OK, tuple);
		assertBytes(_testFileBytes, getResponseBody(tuple));

		assertCode(
			HttpServletResponse.SC_NOT_FOUND,
			servicePropFind("Backup of Test.docx"));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND, servicePropFind(_tempFileName2));
		unlock(_testFileName);
		assertCode(
			HttpServletResponse.SC_NOT_FOUND, servicePropFind(_tempFileName2));
		assertCode(
			HttpServletResponse.SC_CREATED,
			serviceCopyOrMove(Method.MOVE, _testFileName, _tempFileName2));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND, servicePropFind(_tempMetaName2));
		assertCode(
			HttpServletResponse.SC_CREATED,
			serviceCopyOrMove(Method.MOVE, _testMetaName, _tempMetaName2));

		for (int i = 0; i < 2; i++) {
			lock(_tempFileName2);
			tuple = serviceGet(_tempFileName2);
			assertCode(HttpServletResponse.SC_OK, tuple);
			assertBytes(_testFileBytes, getResponseBody(tuple));
			unlock(_tempFileName2);
		}

		for (int i = 0; i < 2; i++) {
			String orig = _tempFileName1;
			String dest = _testFileName;

			if (i == 1) {
				orig = _tempMetaName1;
				dest = _testMetaName;
			}

			assertCode(HttpServletResponse.SC_NOT_FOUND, servicePropFind(dest));
			assertCode(HttpServletResponse.SC_NOT_FOUND, servicePropFind(dest));
			assertCode(
				HttpServletResponse.SC_CREATED,
				serviceCopyOrMove(Method.MOVE, orig, dest, getLock(orig)));
			moveLock(orig, dest);
		}

		for (int i = 0; i < 2; i++) {
			lock(_testFileName);
			tuple = serviceGet(_testFileName);
			assertCode(HttpServletResponse.SC_OK, tuple);
			assertBytes(_testDeltaBytes, getResponseBody(tuple));
			unlock(_testFileName);
		}

		lock(_testMetaName);
		tuple = serviceGet(_testMetaName);
		assertCode(HttpServletResponse.SC_OK, tuple);
		assertBytes(_testMetaBytes, getResponseBody(tuple));
		assertCode(
			HttpServletResponse.SC_CREATED,
			servicePut(_testMetaName, _testMetaBytes, getLock(_testMetaName)));
		assertCode(
			WebDAVUtil.SC_MULTI_STATUS, servicePropFind(_testMetaName));
		unlock(_testMetaName);

		unlock(_tempFileName2);
		assertCode(
			HttpServletResponse.SC_NO_CONTENT,
			serviceDelete(_tempFileName2));
		assertCode(
			HttpServletResponse.SC_NO_CONTENT,
			serviceDelete(_tempMetaName2));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND, servicePropFind(_tempMetaName2));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND, servicePropFind(_tempFileName2));
	}

	protected String getLock(String fileName) {
		return _lockMap.get(fileName);
	}

	protected void lock(String fileName) {
		Tuple tuple = serviceLock(fileName, null, 0);

		assertCode(HttpServletResponse.SC_OK, tuple);

		_lockMap.put(fileName, getLock(tuple));
	}

	protected void moveLock(String orig, String dest) {
		String lock = _lockMap.remove(orig);

		if (lock != null) {
			_lockMap.put(dest, lock);
		}
	}

	protected void unlock(String fileName) {
		String lock = _lockMap.remove(fileName);

		assertCode(
			HttpServletResponse.SC_NO_CONTENT,
			serviceUnlock(fileName, lock));
	}

	protected String getUserAgent() {
		return _USER_AGENT;
	}

	private Map<String, String> _lockMap = new HashMap<String, String>();

	private static byte[] _testDeltaBytes;

	private static byte[] _testFileBytes;

	private static byte[] _testMetaBytes;

	private static final String _tempFileName1 = "Word Work File D_1.tmp";

	private static final String _tempFileName2 = "Word Work File L_2.tmp";

	private static final String _tempMetaName1 = "._Word Work File D_1.tmp";

	private static final String _tempMetaName2 = "._Word Work File L_2.tmp";

	private static final String _testFileName = "Test.docx";

	private static final String _testMetaName = "._Test.docx";

	private static final String _OFFICE_TEST_FILE_PREFIX =
		"portal-impl/test/com/liferay/portal/webdav/dependencies/";

	private static final String _OFFICE_TEST_DELTA_DOCX =
		_OFFICE_TEST_FILE_PREFIX + "OSX_Test_Delta.docx";

	private static final String _OFFICE_TEST_DOCX =
		_OFFICE_TEST_FILE_PREFIX + "OSX_Test.docx";

	private static final String _OFFICE_TEST_META_DOCX =
		_OFFICE_TEST_FILE_PREFIX + "OSX_Test_Meta.docx";

	private static final String _USER_AGENT =
		"WebDAVFS/1.8 (01808000) Darwin/10.3.0 (i386)";

}