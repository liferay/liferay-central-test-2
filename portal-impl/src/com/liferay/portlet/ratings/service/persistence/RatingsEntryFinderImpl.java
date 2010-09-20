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
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.model.impl.RatingsEntryImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class RatingsEntryFinderImpl extends BasePersistenceImpl<RatingsEntry>
		implements RatingsEntryFinder {

	public static String FIND_BY_U_C_C =
		RatingsEntryFinder.class.getName() + ".findByU_C_C";

	public List<RatingsEntry> findByU_C_C(
			long userId, long classNameId, List<Long> classPKs)
		throws SystemException {

		List<RatingsEntry> ratingsEntries = new ArrayList<RatingsEntry>();

		List<Long> missedClassPKs = new ArrayList<Long>();

		Object[] finderArgs = new Object[] { userId, classNameId, 0 };

		for(Long classPk : classPKs) {
			finderArgs[2] = classPk;
			Object result = FinderCacheUtil.getResult(
				RatingsEntryPersistenceImpl.FINDER_PATH_FETCH_BY_U_C_C,
					finderArgs, this);
			if (result == null) {
				missedClassPKs.add(classPk);
			}
			else if (result instanceof RatingsEntry) {
				ratingsEntries.add((RatingsEntry) result);
			}
		}

		if (missedClassPKs.isEmpty()) {
			return ratingsEntries;
		}

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_U_C_C);

			sql = StringUtil.replace(
				sql, "[$CLASS_PKS$]", StringUtil.merge(missedClassPKs));

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("RatingsEntry", RatingsEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);
			qPos.add(classNameId);

			List<RatingsEntry> list = q.list();

			if (list.isEmpty()) {
				return ratingsEntries;
			}

			for(RatingsEntry ratingsEntry : list) {
				RatingsEntryUtil.cacheResult(ratingsEntry);
				missedClassPKs.remove(ratingsEntry.getClassPK());
			}

			for(Long missedClassPk : missedClassPKs) {
				finderArgs[2] = missedClassPk;
				FinderCacheUtil.putResult(
					RatingsEntryPersistenceImpl.FINDER_PATH_FETCH_BY_U_C_C,
						finderArgs, Collections.EMPTY_LIST);
			}

			ratingsEntries.addAll(list);

			return ratingsEntries;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}