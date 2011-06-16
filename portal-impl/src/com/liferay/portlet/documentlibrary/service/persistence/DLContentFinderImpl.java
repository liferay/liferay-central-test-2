/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.NoSuchDLContentException;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.documentlibrary.model.DLContent;
import com.liferay.util.dao.orm.CustomSQLUtil;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLContentFinderImpl
	extends BasePersistenceImpl<DLContent> implements DLContentFinder {

	public static String FIND_NAMES_BY_C_R_P =
		DLContentFinder.class.getName() + ".findNamesByC_R_P";

	public static String FIND_SIZE_BY_C_R_P =
		DLContentFinder.class.getName() + ".findSizeByC_R_P";

	public static String UPDATE_BY_C_R_P_N_1 =
		DLContentFinder.class.getName() + ".updateByC_R_P_N_1";

	public static String UPDATE_BY_C_R_P_N_2 =
		DLContentFinder.class.getName() + ".updateByC_R_P_N_2";

	public List<String> findNamesByC_R_P(
			long companyId, long repositoryId, String path) 
		throws SystemException {

		if (!path.endsWith(StringPool.SLASH)) {
			path = path.concat(StringPool.SLASH);
		}

		path = path.concat(StringPool.PERCENT);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_NAMES_BY_C_R_P);

			Query q = session.createSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(repositoryId);
			qPos.add(path);

			return q.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public long findSizeByC_R_P(long companyId, long repositoryId, String path)
		throws SystemException, NoSuchDLContentException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_SIZE_BY_C_R_P);

			Query q = session.createSQLQuery(sql);

			q.setFirstResult(0);
			q.setMaxResults(1);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(repositoryId);
			qPos.add(path);

			List<Object[]> list = q.list();

			if (!list.isEmpty()) {
				return ((Number)list.get(0)[0]).longValue();
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
		
		StringBundler sb = new StringBundler(7);

		sb.append("No DLContent exists with the key {companyId=");
		sb.append(companyId);
		sb.append(", repositoryId=");
		sb.append(repositoryId);
		sb.append(", path=");
		sb.append(path);
		sb.append("}");

		throw new NoSuchDLContentException(sb.toString());
	}

	public void updateByC_R_P_N_1(
			long companyId, long repositoryId, String path, 
			long newRepositoryId)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(UPDATE_BY_C_R_P_N_1);

			Query q = session.createSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(newRepositoryId);
			qPos.add(companyId);
			qPos.add(repositoryId);
			qPos.add(path);

			q.executeUpdate();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public void updateByC_R_P_N_2(
			long companyId, long repositoryId, String path, String newPath)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(UPDATE_BY_C_R_P_N_2);

			Query q = session.createSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(newPath);
			qPos.add(companyId);
			qPos.add(repositoryId);
			qPos.add(path);

			q.executeUpdate();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

}