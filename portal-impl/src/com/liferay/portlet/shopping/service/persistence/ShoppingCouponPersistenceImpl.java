/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;

import com.liferay.portlet.shopping.NoSuchCouponException;
import com.liferay.portlet.shopping.model.ShoppingCoupon;
import com.liferay.portlet.shopping.model.impl.ShoppingCouponImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="ShoppingCouponPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ShoppingCouponPersistenceImpl extends BasePersistence
	implements ShoppingCouponPersistence {
	public ShoppingCoupon create(long couponId) {
		ShoppingCoupon shoppingCoupon = new ShoppingCouponImpl();
		shoppingCoupon.setNew(true);
		shoppingCoupon.setPrimaryKey(couponId);

		return shoppingCoupon;
	}

	public ShoppingCoupon remove(long couponId)
		throws NoSuchCouponException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingCoupon shoppingCoupon = (ShoppingCoupon)session.get(ShoppingCouponImpl.class,
					new Long(couponId));

			if (shoppingCoupon == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No ShoppingCoupon exists with the primary key " +
						couponId);
				}

				throw new NoSuchCouponException(
					"No ShoppingCoupon exists with the primary key " +
					couponId);
			}

			return remove(shoppingCoupon);
		}
		catch (NoSuchCouponException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingCoupon remove(ShoppingCoupon shoppingCoupon)
		throws SystemException {
		FinderCache.clearCache(ShoppingCoupon.class.getName());

		Session session = null;

		try {
			session = openSession();
			session.delete(shoppingCoupon);
			session.flush();

			return shoppingCoupon;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingCoupon update(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon)
		throws SystemException {
		return update(shoppingCoupon, false);
	}

	public ShoppingCoupon update(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon,
		boolean saveOrUpdate) throws SystemException {
		FinderCache.clearCache(ShoppingCoupon.class.getName());

		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(shoppingCoupon);
			}
			else {
				if (shoppingCoupon.isNew()) {
					session.save(shoppingCoupon);
				}
			}

			session.flush();
			shoppingCoupon.setNew(false);

			return shoppingCoupon;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingCoupon findByPrimaryKey(long couponId)
		throws NoSuchCouponException, SystemException {
		ShoppingCoupon shoppingCoupon = fetchByPrimaryKey(couponId);

		if (shoppingCoupon == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ShoppingCoupon exists with the primary key " +
					couponId);
			}

			throw new NoSuchCouponException(
				"No ShoppingCoupon exists with the primary key " + couponId);
		}

		return shoppingCoupon;
	}

	public ShoppingCoupon fetchByPrimaryKey(long couponId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (ShoppingCoupon)session.get(ShoppingCouponImpl.class,
				new Long(couponId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByGroupId(long groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingCoupon WHERE ");
			query.append("groupId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByGroupId(long groupId, int begin, int end)
		throws SystemException {
		return findByGroupId(groupId, begin, end, null);
	}

	public List findByGroupId(long groupId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingCoupon WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingCoupon findByGroupId_First(long groupId,
		OrderByComparator obc) throws NoSuchCouponException, SystemException {
		List list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No ShoppingCoupon exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchCouponException(msg.toString());
		}
		else {
			return (ShoppingCoupon)list.get(0);
		}
	}

	public ShoppingCoupon findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchCouponException, SystemException {
		int count = countByGroupId(groupId);
		List list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No ShoppingCoupon exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchCouponException(msg.toString());
		}
		else {
			return (ShoppingCoupon)list.get(0);
		}
	}

	public ShoppingCoupon[] findByGroupId_PrevAndNext(long couponId,
		long groupId, OrderByComparator obc)
		throws NoSuchCouponException, SystemException {
		ShoppingCoupon shoppingCoupon = findByPrimaryKey(couponId);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingCoupon WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingCoupon);
			ShoppingCoupon[] array = new ShoppingCouponImpl[3];
			array[0] = (ShoppingCoupon)objArray[0];
			array[1] = (ShoppingCoupon)objArray[1];
			array[2] = (ShoppingCoupon)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingCoupon findByCode(String code)
		throws NoSuchCouponException, SystemException {
		ShoppingCoupon shoppingCoupon = fetchByCode(code);

		if (shoppingCoupon == null) {
			StringMaker msg = new StringMaker();
			msg.append("No ShoppingCoupon exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("code=");
			msg.append(code);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCouponException(msg.toString());
		}

		return shoppingCoupon;
	}

	public ShoppingCoupon fetchByCode(String code) throws SystemException {
		String finderClassName = ShoppingCoupon.class.getName();
		String finderMethodName = "fetchByCode";
		Object[] finderArgs = new Object[] { code };
		Object result = FinderCache.getResult(finderClassName,
				finderMethodName, finderArgs);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();
				query.append(
					"FROM com.liferay.portlet.shopping.model.ShoppingCoupon WHERE ");

				if (code == null) {
					query.append("code_ IS NULL");
				}
				else {
					query.append("code_ = ?");
				}

				query.append(" ");
				query.append("ORDER BY ");
				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());
				int queryPos = 0;

				if (code != null) {
					q.setString(queryPos++, code);
				}

				List list = q.list();

				if (list.size() == 0) {
					return null;
				}

				ShoppingCoupon shoppingCoupon = (ShoppingCoupon)list.get(0);
				FinderCache.putResult(finderClassName, finderMethodName,
					finderArgs, shoppingCoupon);

				return shoppingCoupon;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (ShoppingCoupon)result;
		}
	}

	public List findWithDynamicQuery(DynamicQueryInitializer queryInitializer)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			return query.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findWithDynamicQuery(DynamicQueryInitializer queryInitializer,
		int begin, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);
			query.setLimit(begin, end);

			return query.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List findAll(int begin, int end) throws SystemException {
		return findAll(begin, end, null);
	}

	public List findAll(int begin, int end, OrderByComparator obc)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingCoupon ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByGroupId(long groupId) throws SystemException {
		Iterator itr = findByGroupId(groupId).iterator();

		while (itr.hasNext()) {
			ShoppingCoupon shoppingCoupon = (ShoppingCoupon)itr.next();
			remove(shoppingCoupon);
		}
	}

	public void removeByCode(String code)
		throws NoSuchCouponException, SystemException {
		ShoppingCoupon shoppingCoupon = findByCode(code);
		remove(shoppingCoupon);
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((ShoppingCoupon)itr.next());
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingCoupon WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByCode(String code) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingCoupon WHERE ");

			if (code == null) {
				query.append("code_ IS NULL");
			}
			else {
				query.append("code_ = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (code != null) {
				q.setString(queryPos++, code);
			}

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingCoupon");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(ShoppingCouponPersistenceImpl.class);
}