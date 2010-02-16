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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.asset.model.AssetTagProperty;
import com.liferay.portlet.asset.model.impl.AssetTagPropertyImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="AssetTagPropertyFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AssetTagPropertyFinderImpl
	extends BasePersistenceImpl<AssetTagProperty>
	implements AssetTagPropertyFinder {

	public static String COUNT_BY_G_K =
		AssetTagPropertyFinder.class.getName() + ".countByG_K";

	public static String FIND_BY_G_K =
		AssetTagPropertyFinder.class.getName() + ".findByG_K";

	public int countByG_K(long groupId, String key) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_K);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(key);

			Iterator<Long> itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AssetTagProperty> findByG_K(long groupId, String key)
		throws SystemException {

		return findByG_K(groupId, key, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<AssetTagProperty> findByG_K(
			long groupId, String key, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_K);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar("tagPropertyValue", Type.STRING);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(key);

			List<AssetTagProperty> tagProperties =
				new ArrayList<AssetTagProperty>();

			Iterator<String> itr = (Iterator<String>)QueryUtil.iterate(
				q, getDialect(), start, end);

			while (itr.hasNext()) {
				String value = itr.next();

				AssetTagProperty tagProperty = new AssetTagPropertyImpl();

				tagProperty.setKey(key);
				tagProperty.setValue(value);

				tagProperties.add(tagProperty);
			}

			return tagProperties;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}