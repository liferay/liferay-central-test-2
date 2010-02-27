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

package com.liferay.portlet.polls.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.polls.NoSuchChoiceException;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.impl.PollsChoiceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.List;

/**
 * <a href="PollsChoiceFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public class PollsChoiceFinderImpl
	extends BasePersistenceImpl<PollsChoice> implements PollsChoiceFinder {

	public static String FIND_BY_UUID_G =
		PollsChoiceFinder.class.getName() + ".findByUuid_G";

	public PollsChoice findByUuid_G(String uuid, long groupId)
		throws NoSuchChoiceException, SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_UUID_G);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("PollsChoice", PollsChoiceImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(uuid);
			qPos.add(groupId);

			List<PollsChoice> list = q.list();

			if (list.size() == 0) {
				StringBundler sb = new StringBundler(5);

				sb.append("No PollsChoice exists with the key {uuid=");
				sb.append(uuid);
				sb.append(", groupId=");
				sb.append(groupId);
				sb.append("}");

				throw new NoSuchChoiceException(sb.toString());
			}
			else {
				return list.get(0);
			}
		}
		catch (NoSuchChoiceException nsce) {
			throw nsce;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}