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

package com.liferay.portal.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portal.spring.hibernate.CustomSQLUtil;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.util.dao.hibernate.QueryPos;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * <a href="CompanyFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class CompanyFinderImpl implements CompanyFinder {

	public static String FIND_BY_V_A =
		CompanyFinder.class.getName() + ".findByV_A";

	public Company findByV_A(String virtualHost) throws SystemException {
		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_V_A);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Company", CompanyImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(virtualHost);
			qPos.add(virtualHost);

			String vostDomain = virtualHost;
			String[] virtualHostParts = StringUtil.split(
				virtualHost, StringPool.PERIOD);

			if (virtualHostParts.length >= 2) {
				vostDomain = virtualHostParts[virtualHostParts.length - 2];
				vostDomain += StringPool.PERIOD;
				vostDomain += virtualHostParts[virtualHostParts.length - 1];
			}

			qPos.add(Boolean.TRUE);
			qPos.add(vostDomain);
			qPos.add(vostDomain);

			return (Company)q.uniqueResult();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

}