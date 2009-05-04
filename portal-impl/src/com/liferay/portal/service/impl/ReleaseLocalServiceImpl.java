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

package com.liferay.portal.service.impl;

import com.liferay.portal.NoSuchReleaseException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.model.Release;
import com.liferay.portal.model.impl.ReleaseImpl;
import com.liferay.portal.service.base.ReleaseLocalServiceBaseImpl;
import com.liferay.portal.tools.sql.DBUtil;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Date;

/**
 * <a href="ReleaseLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ReleaseLocalServiceImpl extends ReleaseLocalServiceBaseImpl {

	public Release addRelease() throws SystemException {
		Release release = releasePersistence.create(ReleaseImpl.DEFAULT_ID);

		Date now = new Date();

		release.setCreateDate(now);
		release.setModifiedDate(now);
		release.setTestString(ReleaseImpl.TEST_STRING);

		releasePersistence.update(release, false);

		return release;
	}

	public void createTablesAndPopulate() throws SystemException {
		try {
			DBUtil dbUtil = DBUtil.getInstance();

			dbUtil.runSQLTemplate("portal-tables.sql", false);
			dbUtil.runSQLTemplate("portal-data-common.sql", false);
			dbUtil.runSQLTemplate("portal-data-counter.sql", false);

			if (!GetterUtil.getBoolean(
					PropsUtil.get(PropsKeys.SCHEMA_RUN_MINIMAL))) {

				dbUtil.runSQLTemplate("portal-data-sample.vm", false);
			}

			dbUtil.runSQLTemplate("portal-data-release.sql", false);
			dbUtil.runSQLTemplate("indexes.sql", false);
			dbUtil.runSQLTemplate("sequences.sql", false);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new SystemException(e);
		}
	}

	public int getBuildNumberOrCreate()
		throws PortalException, SystemException {

		// Get release build number

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_GET_BUILD_NUMBER);

			rs = ps.executeQuery();

			if (rs.next()) {
				int buildNumber = rs.getInt("buildNumber");

				if (_log.isDebugEnabled()) {
					_log.debug("Build number " + buildNumber);
				}

				testSupportsStringCaseSensitiveQuery();

				return buildNumber;
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage());
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		// Create tables and populate with default data

		if (GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.SCHEMA_RUN_ENABLED))) {

			if (_log.isInfoEnabled()) {
				_log.info("Create tables and populate with default data");
			}

			releaseLocalService.createTablesAndPopulate();

			testSupportsStringCaseSensitiveQuery();

			return getRelease().getBuildNumber();
		}
		else {
			throw new NoSuchReleaseException(
				"The database needs to be populated");
		}
	}

	public Release getRelease() throws SystemException {
		Release release = releasePersistence.fetchByPrimaryKey(
			ReleaseImpl.DEFAULT_ID);

		if (release == null) {
			release = releaseLocalService.addRelease();
		}

		return release;
	}

	public Release updateRelease(boolean verified) throws SystemException {
		Release release = getRelease();

		release.setModifiedDate(new Date());
		release.setBuildNumber(ReleaseInfo.getBuildNumber());
		release.setBuildDate(ReleaseInfo.getBuildDate());
		release.setVerified(verified);

		releasePersistence.update(release, false);

		return release;
	}

	protected void testSupportsStringCaseSensitiveQuery()
		throws SystemException {

		DBUtil dbUtil = DBUtil.getInstance();

		int count = testSupportsStringCaseSensitiveQuery(
			ReleaseImpl.TEST_STRING);

		if (count == 0) {
			try {
				dbUtil.runSQL(
					"alter table Release_ add testString VARCHAR(1024) null");
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e.getMessage());
				}
			}

			try {
				dbUtil.runSQL(
					"update Release_ set testString = '" +
						ReleaseImpl.TEST_STRING + "'");
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e.getMessage());
				}
			}

			count = testSupportsStringCaseSensitiveQuery(
				ReleaseImpl.TEST_STRING);
		}

		if (count == 0) {
			throw new SystemException(
				"Release_ table was not initialized properly");
		}

		count = testSupportsStringCaseSensitiveQuery(
			ReleaseImpl.TEST_STRING.toUpperCase());

		if (count == 0) {
			dbUtil.setSupportsStringCaseSensitiveQuery(true);
		}
		else {
			dbUtil.setSupportsStringCaseSensitiveQuery(false);
		}
	}

	protected int testSupportsStringCaseSensitiveQuery(String testString) {
		int count = 0;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_TEST_DATABASE_STRING_CASE_SENSITIVITY);

			ps.setString(1, testString);

			rs = ps.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage());
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return count;
	}

	private static final String _GET_BUILD_NUMBER =
		"select buildNumber from Release_";

	private static final String _TEST_DATABASE_STRING_CASE_SENSITIVITY =
		"select count(*) from Release_ where testString = ?";

	private static Log _log =
		LogFactoryUtil.getLog(ReleaseLocalServiceImpl.class);

}