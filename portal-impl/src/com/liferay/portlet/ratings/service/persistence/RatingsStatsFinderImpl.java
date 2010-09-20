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

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.model.impl.RatingsStatsImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class RatingsStatsFinderImpl extends BasePersistenceImpl<RatingsStats>
	implements RatingsStatsFinder{

	public static String FIND_BY_C_C =
		RatingsStatsFinder.class.getName() + ".findByC_C";

	public List<RatingsStats> findByC_C(long classNameId, List<Long> classPKs)
		throws SystemException {

		List<RatingsStats> ratingsStatses = new ArrayList<RatingsStats>();

		List<Long> missedClassPKs = new ArrayList<Long>();

		Object[] finderArgs = new Object[] { classNameId, 0 };

		for(Long classPk : classPKs) {
			finderArgs[1] = classPk;
			Object result = FinderCacheUtil.getResult(
				RatingsStatsPersistenceImpl.FINDER_PATH_FETCH_BY_C_C,
					finderArgs, this);
			if (result == null) {
				missedClassPKs.add(classPk);
			}
			else if (result instanceof RatingsStats) {
				ratingsStatses.add((RatingsStats) result);
			}
		}

		if (missedClassPKs.isEmpty()) {
			return ratingsStatses;
		}

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_C);

			sql = StringUtil.replace(
				sql, "[$CLASS_PKS$]", StringUtil.merge(missedClassPKs));

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("RatingsStats", RatingsStatsImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			List<RatingsStats> list = q.list();

			if (list.isEmpty()) {
				return ratingsStatses;
			}

			for(RatingsStats ratingsStats : list) {
				RatingsStatsUtil.cacheResult(ratingsStats);
				missedClassPKs.remove(ratingsStats.getClassPK());
			}

			for(Long missedClassPk : missedClassPKs) {
				finderArgs[1] = missedClassPk;
				FinderCacheUtil.putResult(
					RatingsStatsPersistenceImpl.FINDER_PATH_FETCH_BY_C_C,
						finderArgs, Collections.EMPTY_LIST);
			}

			ratingsStatses.addAll(list);

			return ratingsStatses;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}