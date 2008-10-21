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

package com.liferay.portal.service.impl;

import com.liferay.portal.NoSuchReleaseException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ReleaseLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ReleaseLocalServiceImpl extends ReleaseLocalServiceBaseImpl {
	public static final int NO_BUILD_NUMBER = -1;

	public int bootstrapDatabase() throws PortalException, SystemException {

		// Create tables and populate with default data

		int buildNumber = NO_BUILD_NUMBER;

		if (GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.SCHEMA_RUN_ENABLED))) {

			if (_log.isInfoEnabled()) {
				_log.info("Create tables and populate with default data");
			}

			createTablesAndPopulate();

			Release release = getRelease();

			if (release == null) {
				release = addRelease();
			}

			buildNumber = release.getBuildNumber();
		}
		else {
			throw new NoSuchReleaseException(
				"The database needs to be populated");
		}

		return buildNumber;
	}

	public void deleteTemporaryImages() throws SystemException {
		DBUtil dbUtil = DBUtil.getInstance();

		try {
			dbUtil.runSQL(_DELETE_TEMP_IMAGES_1);
			dbUtil.runSQL(_DELETE_TEMP_IMAGES_2);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public int getBuildNumber()
		throws PortalException, SystemException {

		// Get release build number

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int buildNumber = NO_BUILD_NUMBER;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_GET_BUILD_NUMBER);

			rs = ps.executeQuery();

			if (rs.next()) {
				buildNumber = rs.getInt("buildNumber");

				if (_log.isDebugEnabled()) {
					_log.debug("Build number " + buildNumber);
				}
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return buildNumber;
	}

	public Release getRelease() throws SystemException {
		return releasePersistence.fetchByPrimaryKey(ReleaseImpl.DEFAULT_ID);
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

	protected Release addRelease() throws SystemException {
		Release release = releasePersistence.create(ReleaseImpl.DEFAULT_ID);

		Date now = new Date();

		release.setCreateDate(now);
		release.setModifiedDate(now);

		releasePersistence.update(release, false);

		return release;
	}

	protected void createTablesAndPopulate() throws SystemException {
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
			dbUtil.runSQLTemplate("quartz-tables.sql", false);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new SystemException(e);
		}
	}

	private static final String _DELETE_TEMP_IMAGES_1 =
		"DELETE FROM Image WHERE imageId IN (SELECT articleImageId FROM " +
			"JournalArticleImage WHERE tempImage = TRUE)";

	private static final String _DELETE_TEMP_IMAGES_2 =
		"DELETE FROM JournalArticleImage where tempImage = TRUE";

	private static final String _GET_BUILD_NUMBER =
		"select buildNumber from Release_";

	private static Log _log = LogFactory.getLog(ReleaseLocalServiceImpl.class);

}