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

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.documentlibrary.model.DLContent;
import com.liferay.portlet.documentlibrary.model.impl.DLContentImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;
import java.util.ArrayList;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLContentFinderImpl
	extends BasePersistenceImpl<DLContent> implements DLContentFinder {

	public static String FIND_BY_C_R_P =
		DLContentFinder.class.getName() + ".findByC_R_P";

	public List<DLContent> findByC_R_P(
			long companyId, long repositoryId, String path)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_R_P);

			if (path.contains(StringPool.PERCENT)) {
				sql = StringUtil.replace(sql, "(path_ = ?)", "(path_ LIKE ?)");
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar("contentId", Type.LONG);
			q.addScalar("groupId", Type.LONG);
			q.addScalar("companyId", Type.LONG);
			q.addScalar("portletId", Type.STRING);
			q.addScalar("repositoryId", Type.LONG);
			q.addScalar("path_", Type.STRING);
			q.addScalar("version", Type.STRING);
			q.addScalar("size_", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(repositoryId);
			qPos.add(path);

			List<Object[]> queryResults = q.list();

			List<DLContent> dummyDLContents = new ArrayList<DLContent>(
				queryResults.size());

			for (Object[] queryResult : queryResults) {
				DLContent dummyDLContent = new DLContentImpl();

				dummyDLContent.setContentId(
					((Number)queryResult[0]).longValue());
				dummyDLContent.setGroupId(((Number)queryResult[1]).longValue());
				dummyDLContent.setCompanyId(
					((Number)queryResult[2]).longValue());
				dummyDLContent.setPortletId((String)queryResult[3]);
				dummyDLContent.setRepositoryId(
					((Number)queryResult[4]).longValue());
				dummyDLContent.setPath((String)queryResult[5]);
				dummyDLContent.setVersion((String)queryResult[6]);
				dummyDLContent.setSize(((Number)queryResult[7]).longValue());
				
				dummyDLContents.add(dummyDLContent);
			}

			return dummyDLContents;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}