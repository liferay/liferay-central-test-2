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
import com.liferay.portlet.asset.model.AssetCategoryProperty;
import com.liferay.portlet.asset.model.impl.AssetCategoryPropertyImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="AssetCategoryPropertyFinderImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class AssetCategoryPropertyFinderImpl
	extends BasePersistenceImpl<AssetCategoryProperty>
	implements AssetCategoryPropertyFinder {

	public static String COUNT_BY_G_K =
		AssetCategoryPropertyFinder.class.getName() + ".countByG_K";

	public static String FIND_BY_G_K =
		AssetCategoryPropertyFinder.class.getName() + ".findByG_K";

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

	public List<AssetCategoryProperty> findByG_K(long groupId, String key)
		throws SystemException {

		return findByG_K(groupId, key, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<AssetCategoryProperty> findByG_K(
			long groupId, String key, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_K);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar("categoryPropertyValue", Type.STRING);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(key);

			List<AssetCategoryProperty> categoryProperties =
				new ArrayList<AssetCategoryProperty>();

			Iterator<String> itr = (Iterator<String>)QueryUtil.iterate(
				q, getDialect(), start, end);

			while (itr.hasNext()) {
				String value = itr.next();

				AssetCategoryProperty categoryProperty =
					new AssetCategoryPropertyImpl();

				categoryProperty.setKey(key);
				categoryProperty.setValue(value);

				categoryProperties.add(categoryProperty);
			}

			return categoryProperties;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}