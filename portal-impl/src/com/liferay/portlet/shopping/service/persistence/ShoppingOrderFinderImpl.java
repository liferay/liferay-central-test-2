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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.shopping.model.ShoppingOrder;
import com.liferay.portlet.shopping.model.ShoppingOrderConstants;
import com.liferay.portlet.shopping.model.impl.ShoppingOrderImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="ShoppingOrderFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ShoppingOrderFinderImpl
	extends BasePersistenceImpl<ShoppingOrder> implements ShoppingOrderFinder {

	public static String COUNT_BY_G_C_U_N_PPPS =
		ShoppingOrderFinder.class.getName() + ".countByG_C_U_N_PPPS";

	public static String FIND_BY_G_C_U_N_PPPS =
		ShoppingOrderFinder.class.getName() + ".findByG_C_U_N_PPPS";

	public int countByG_C_U_N_PPPS(
			long groupId, long companyId, long userId, String number,
			String billingFirstName, String billingLastName,
			String billingEmailAddress, String shippingFirstName,
			String shippingLastName, String shippingEmailAddress,
			String ppPaymentStatus, boolean andOperator)
		throws SystemException {

		number = StringUtil.upperCase(number);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_C_U_N_PPPS);

			if (userId <= 0) {
				sql = StringUtil.replace(sql, USER_ID_SQL, StringPool.BLANK);
			}

			if (Validator.isNull(ppPaymentStatus)) {
				sql = StringUtil.replace(
					sql, "ppPaymentStatus = ?", "ppPaymentStatus != ?");

				ppPaymentStatus = ShoppingOrderConstants.STATUS_LATEST;
			}

			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(companyId);

			if (userId > 0) {
				qPos.add(userId);
			}

			qPos.add(number);
			qPos.add(number);
			qPos.add(billingFirstName);
			qPos.add(billingFirstName);
			qPos.add(billingLastName);
			qPos.add(billingLastName);
			qPos.add(billingEmailAddress);
			qPos.add(billingEmailAddress);
			qPos.add(shippingFirstName);
			qPos.add(shippingFirstName);
			qPos.add(shippingLastName);
			qPos.add(shippingLastName);
			qPos.add(shippingEmailAddress);
			qPos.add(shippingEmailAddress);
			qPos.add(ppPaymentStatus);

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

	public List<ShoppingOrder> findByG_C_U_N_PPPS(
			long groupId, long companyId, long userId, String number,
			String billingFirstName, String billingLastName,
			String billingEmailAddress, String shippingFirstName,
			String shippingLastName, String shippingEmailAddress,
			String ppPaymentStatus, boolean andOperator, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		number = StringUtil.upperCase(number);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_C_U_N_PPPS);

			if (userId <= 0) {
				sql = StringUtil.replace(sql, USER_ID_SQL, StringPool.BLANK);
			}

			if (Validator.isNull(ppPaymentStatus)) {
				sql = StringUtil.replace(
					sql, "ppPaymentStatus = ?", "ppPaymentStatus != ?");

				ppPaymentStatus = ShoppingOrderConstants.STATUS_LATEST;
			}

			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);
			sql = CustomSQLUtil.replaceOrderBy(sql, obc);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("ShoppingOrder", ShoppingOrderImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(companyId);

			if (userId > 0) {
				qPos.add(userId);
			}

			qPos.add(number);
			qPos.add(number);
			qPos.add(billingFirstName);
			qPos.add(billingFirstName);
			qPos.add(billingLastName);
			qPos.add(billingLastName);
			qPos.add(billingEmailAddress);
			qPos.add(billingEmailAddress);
			qPos.add(shippingFirstName);
			qPos.add(shippingFirstName);
			qPos.add(shippingLastName);
			qPos.add(shippingLastName);
			qPos.add(shippingEmailAddress);
			qPos.add(shippingEmailAddress);
			qPos.add(ppPaymentStatus);

			return (List<ShoppingOrder>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected static String USER_ID_SQL = "(userId = ?) AND";

}