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

package com.liferay.portlet.shopping.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.shopping.model.ShoppingCoupon;
import com.liferay.portlet.shopping.model.impl.ShoppingCouponImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="ShoppingCouponFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ShoppingCouponFinderImpl
	extends BasePersistenceImpl<ShoppingCoupon>
	implements ShoppingCouponFinder {

	public static String COUNT_BY_G_C_C_A_DT =
		ShoppingCouponFinder.class.getName() + ".countByG_C_C_A_DT";

	public static String FIND_BY_G_C_C_A_DT =
		ShoppingCouponFinder.class.getName() + ".findByG_C_C_A_DT";

	public int countByG_C_C_A_DT(
			long groupId, long companyId, String code, boolean active,
			String discountType, boolean andOperator)
		throws SystemException {

		code = StringUtil.upperCase(code);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_C_C_A_DT);

			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(companyId);
			qPos.add(code);
			qPos.add(code);
			qPos.add(active);
			qPos.add(discountType);
			qPos.add(discountType);

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

	public List<ShoppingCoupon> findByG_C_C_A_DT(
			long groupId, long companyId, String code, boolean active,
			String discountType, boolean andOperator, int start, int end)
		throws SystemException {

		code = StringUtil.upperCase(code);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_C_C_A_DT);

			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("ShoppingCoupon", ShoppingCouponImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(companyId);
			qPos.add(code);
			qPos.add(code);
			qPos.add(active);
			qPos.add(discountType);
			qPos.add(discountType);

			return (List<ShoppingCoupon>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}