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

package com.liferay.portlet.ratings.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.model.impl.RatingsStatsImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.List;

/**
 * <a href="RatingsStatsFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class RatingsStatsFinderImpl extends BasePersistenceImpl<RatingsStats>
	implements RatingsStatsFinder{

	public static String FIND_BY_C_CS =
			RatingsStatsFinder.class.getName() + ".findByC_CS";

	public List<RatingsStats> findByC_CS(String className, List<Long> classPKs)
		throws SystemException {
		long classNameId = PortalUtil.getClassNameId(className);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_CS);
			sql = getClassPKs(sql, classPKs);
			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("RatingsStats", RatingsStatsImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			return q.list();
		} catch (Exception e) {
			throw new SystemException(e);
		} finally {
			closeSession(session);
		}
	}

	protected String getClassPKs(String sql, List<Long> classPKs) {
		int index=sql.indexOf(_CLASSPKS);
		int size = classPKs.size();

		StringBundler sb=new StringBundler(size * 2 + 1);
		sb.append(sql.substring(0, index));
		for(int i = 0; i < size - 1; i++) {
			sb.append(classPKs.get(i));
			sb.append(", ");
		}
		sb.append(classPKs.get(size - 1));
		sb.append(sql.substring(index+_CLASSPKS.length()));
		return sb.toString();
	}

	private static String _CLASSPKS = "[$CLASSPKS$]";

}