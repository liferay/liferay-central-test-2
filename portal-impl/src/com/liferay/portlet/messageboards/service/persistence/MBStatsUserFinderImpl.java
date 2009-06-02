/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

/**
 * <a href="MBStatsUserFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Edward Han
 *
 */
public class MBStatsUserFinderImpl
	extends BasePersistenceImpl implements MBStatsUserFinder{
    public int getPostCountByUserId(long userId)
        throws SystemException {

        int sum = 0;
        Session session = null;

        try {
            session = openSession();

            StringBuilder query = new StringBuilder();

            query.append("SELECT SUM(mbStatsUser.messageCount) ");
            query.append(
                "FROM com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser WHERE ");

            query.append("userId = ?");

            Query q = session.createQuery(query.toString());

            QueryPos qPos = QueryPos.getInstance(q);

            qPos.add(userId);

            sum = (Integer)q.uniqueResult();
        }
        catch (Exception e) {
            throw processException(e);
        }
        finally {
            closeSession(session);
        }

		return sum;
    }
}
