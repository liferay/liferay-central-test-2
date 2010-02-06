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

package com.liferay.portal.service.impl;

import com.liferay.portal.NoSuchReleaseException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.dao.shard.ShardUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Release;
import com.liferay.portal.model.ReleaseConstants;
import com.liferay.portal.service.base.ReleaseLocalServiceBaseImpl;
import com.liferay.portal.util.PropsUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Date;

/**
 * <a href="ReleaseLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ReleaseLocalServiceImpl extends ReleaseLocalServiceBaseImpl {

	public Release addRelease(String servletContextName, int buildNumber)
		throws SystemException {

		Release release = null;

		if (servletContextName.equals(
				ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME)) {

			release = releasePersistence.create(
				ReleaseConstants.DEFAULT_ID);
		}
		else {
			long releaseId = counterLocalService.increment();

			release = releasePersistence.create(releaseId);
		}

		Date now = new Date();

		release.setCreateDate(now);
		release.setModifiedDate(now);
		release.setServletContextName(servletContextName);
		release.setBuildNumber(buildNumber);

		if (servletContextName.equals(
				ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME)) {

			release.setTestString(ReleaseConstants.TEST_STRING);
		}

		releasePersistence.update(release, false);

		return release;
	}

	public void createTablesAndPopulate() throws SystemException {
		try {
			if (_log.isInfoEnabled()) {
				_log.info("Create tables and populate with default data");
			}

			DB db = DBFactoryUtil.getDB();

			db.runSQLTemplate("portal-tables.sql", false);
			db.runSQLTemplate("portal-data-common.sql", false);
			db.runSQLTemplate("portal-data-counter.sql", false);

			if (!GetterUtil.getBoolean(
					PropsUtil.get(PropsKeys.SCHEMA_RUN_MINIMAL)) &&
				!ShardUtil.isEnabled()) {

				db.runSQLTemplate("portal-data-sample.vm", false);
			}

			db.runSQLTemplate("portal-data-release.sql", false);
			db.runSQLTemplate("indexes.sql", false);
			db.runSQLTemplate("sequences.sql", false);
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

			ps.setLong(1, ReleaseConstants.DEFAULT_ID);

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

			releaseLocalService.createTablesAndPopulate();

			testSupportsStringCaseSensitiveQuery();

			Release release = getRelease(
				ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME,
				ReleaseInfo.getBuildNumber());

			return release.getBuildNumber();
		}
		else {
			throw new NoSuchReleaseException(
				"The database needs to be populated");
		}
	}

	public Release getRelease(String servletContextName, int buildNumber)
		throws PortalException, SystemException {

		if (Validator.isNull(servletContextName)) {
			throw new IllegalArgumentException(
				"Servlet context name cannot be null");
		}

		servletContextName = servletContextName.toLowerCase();

		Release release = null;

		if (servletContextName.equals(
				ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME)) {

			release = releasePersistence.findByPrimaryKey(
				ReleaseConstants.DEFAULT_ID);
		}
		else {
			release = releasePersistence.findByServletContextName(
				servletContextName);
		}

		return release;
	}

	public Release updateRelease(
			long releaseId, int buildNumber, Date buildDate, boolean verified)
		throws PortalException, SystemException {

		Release release = releasePersistence.findByPrimaryKey(releaseId);

		release.setModifiedDate(new Date());
		release.setBuildNumber(buildNumber);
		release.setBuildDate(buildDate);
		release.setVerified(verified);

		releasePersistence.update(release, false);

		return release;
	}

	protected void testSupportsStringCaseSensitiveQuery()
		throws SystemException {

		DB db = DBFactoryUtil.getDB();

		int count = testSupportsStringCaseSensitiveQuery(
			ReleaseConstants.TEST_STRING);

		if (count == 0) {
			try {
				db.runSQL(
					"alter table Release_ add testString VARCHAR(1024) null");
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e.getMessage());
				}
			}

			try {
				db.runSQL(
					"update Release_ set testString = '" +
						ReleaseConstants.TEST_STRING + "'");
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e.getMessage());
				}
			}

			count = testSupportsStringCaseSensitiveQuery(
				ReleaseConstants.TEST_STRING);
		}

		if (count == 0) {
			throw new SystemException(
				"Release_ table was not initialized properly");
		}

		count = testSupportsStringCaseSensitiveQuery(
			ReleaseConstants.TEST_STRING.toUpperCase());

		if (count == 0) {
			db.setSupportsStringCaseSensitiveQuery(true);
		}
		else {
			db.setSupportsStringCaseSensitiveQuery(false);
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

			ps.setLong(1, ReleaseConstants.DEFAULT_ID);
			ps.setString(2, testString);

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
		"select buildNumber from Release_ where releaseId = ?";

	private static final String _TEST_DATABASE_STRING_CASE_SENSITIVITY =
		"select count(*) from Release_ where releaseId = ? and testString = ?";

	private static Log _log =
		LogFactoryUtil.getLog(ReleaseLocalServiceImpl.class);

}