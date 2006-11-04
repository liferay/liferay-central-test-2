/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchLayoutSetException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.spring.hibernate.CustomSQLUtil;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.dao.hibernate.QueryPos;

import java.util.Iterator;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * <a href="LayoutSetFinder.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class LayoutSetFinder {

	public static String FIND_BY_C_V =
		LayoutSetFinder.class.getName() + ".findByC_V";

	public static LayoutSet findByC_V(String companyId, String virtualHost)
		throws NoSuchLayoutSetException, SystemException {

		virtualHost = StringUtil.lowerCase(virtualHost);

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_V);

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(true);

			q.addEntity("LayoutSet", LayoutSet.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(virtualHost);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				return (LayoutSet)itr.next();
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}

		throw new NoSuchLayoutSetException(
			"No LayoutSet exists with the key {companyId=" + companyId + ", " +
				"virtualHost=" + virtualHost + "}");
	}

}