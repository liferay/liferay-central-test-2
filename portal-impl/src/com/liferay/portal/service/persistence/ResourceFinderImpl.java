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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.List;

/**
 * <a href="ResourceFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class ResourceFinderImpl
	extends BasePersistenceImpl<Resource> implements ResourceFinder {

	public static String FIND_BY_NAME =
		ResourceFinder.class.getName() + ".findByName";

	public static String FIND_BY_C_P =
		ResourceFinder.class.getName() + ".findByC_P";

	public List<Resource> findByName(String name) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NAME);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Resource_", ResourceImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(name);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Resource> findByC_P(long companyId, String primKey)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_P);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Resource_", ResourceImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(primKey);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}