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
import java.util.Iterator;
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

			List<DLContent> dlContents = new ArrayList<DLContent>();

			Iterator<Object[]> itr = q.list().iterator();

			while (itr.hasNext()) {
				Object[] array = itr.next();

				DLContent dlContent = new DLContentImpl();

				dlContent.setContentId(
					((Number)array[0]).longValue());
				dlContent.setGroupId(((Number)array[1]).longValue());
				dlContent.setCompanyId(
					((Number)array[2]).longValue());
				dlContent.setPortletId((String)array[3]);
				dlContent.setRepositoryId(
					((Number)array[4]).longValue());
				dlContent.setPath((String)array[5]);
				dlContent.setVersion((String)array[6]);
				dlContent.setSize(((Number)array[7]).longValue());

				dlContents.add(dlContent);
			}

			return dlContents;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}