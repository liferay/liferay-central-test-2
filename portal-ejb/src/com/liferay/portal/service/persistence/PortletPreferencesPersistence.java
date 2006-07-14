/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.portal.NoSuchPortletPreferencesException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.util.StringPool;
import com.liferay.util.dao.hibernate.OrderByComparator;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="PortletPreferencesPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PortletPreferencesPersistence extends BasePersistence {
	public PortletPreferences create(PortletPreferencesPK portletPreferencesPK) {
		PortletPreferences portletPreferences = new PortletPreferences();
		portletPreferences.setNew(true);
		portletPreferences.setPrimaryKey(portletPreferencesPK);

		return portletPreferences;
	}

	public PortletPreferences remove(PortletPreferencesPK portletPreferencesPK)
		throws NoSuchPortletPreferencesException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PortletPreferences portletPreferences = (PortletPreferences)session.get(PortletPreferences.class,
					portletPreferencesPK);

			if (portletPreferences == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No PortletPreferences exists with the primary key " +
						portletPreferencesPK.toString());
				}

				throw new NoSuchPortletPreferencesException(
					"No PortletPreferences exists with the primary key " +
					portletPreferencesPK.toString());
			}

			session.delete(portletPreferences);
			session.flush();

			return portletPreferences;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.PortletPreferences update(
		com.liferay.portal.model.PortletPreferences portletPreferences)
		throws SystemException {
		Session session = null;

		try {
			if (portletPreferences.isNew() || portletPreferences.isModified()) {
				session = openSession();

				if (portletPreferences.isNew()) {
					PortletPreferences portletPreferencesModel = new PortletPreferences();
					portletPreferencesModel.setPortletId(portletPreferences.getPortletId());
					portletPreferencesModel.setLayoutId(portletPreferences.getLayoutId());
					portletPreferencesModel.setOwnerId(portletPreferences.getOwnerId());
					portletPreferencesModel.setPreferences(portletPreferences.getPreferences());
					session.save(portletPreferencesModel);
					session.flush();
				}
				else {
					PortletPreferences portletPreferencesModel = (PortletPreferences)session.get(PortletPreferences.class,
							portletPreferences.getPrimaryKey());

					if (portletPreferencesModel != null) {
						portletPreferencesModel.setPreferences(portletPreferences.getPreferences());
						session.flush();
					}
					else {
						portletPreferencesModel = new PortletPreferences();
						portletPreferencesModel.setPortletId(portletPreferences.getPortletId());
						portletPreferencesModel.setLayoutId(portletPreferences.getLayoutId());
						portletPreferencesModel.setOwnerId(portletPreferences.getOwnerId());
						portletPreferencesModel.setPreferences(portletPreferences.getPreferences());
						session.save(portletPreferencesModel);
						session.flush();
					}
				}

				portletPreferences.setNew(false);
				portletPreferences.setModified(false);
			}

			return portletPreferences;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public PortletPreferences findByPrimaryKey(
		PortletPreferencesPK portletPreferencesPK)
		throws NoSuchPortletPreferencesException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PortletPreferences portletPreferences = (PortletPreferences)session.get(PortletPreferences.class,
					portletPreferencesPK);

			if (portletPreferences == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No PortletPreferences exists with the primary key " +
						portletPreferencesPK.toString());
					throw new NoSuchPortletPreferencesException(
						"No PortletPreferences exists with the primary key " +
						portletPreferencesPK.toString());
				}
			}

			return portletPreferences;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public PortletPreferences fetchByPrimaryKey(
		PortletPreferencesPK portletPreferencesPK) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (PortletPreferences)session.get(PortletPreferences.class,
				portletPreferencesPK);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByLayoutId(String layoutId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portal.model.PortletPreferences WHERE ");

			if (layoutId == null) {
				query.append("layoutId IS NULL");
			}
			else {
				query.append("layoutId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (layoutId != null) {
				q.setString(queryPos++, layoutId);
			}

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByLayoutId(String layoutId, int begin, int end)
		throws SystemException {
		return findByLayoutId(layoutId, begin, end, null);
	}

	public List findByLayoutId(String layoutId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portal.model.PortletPreferences WHERE ");

			if (layoutId == null) {
				query.append("layoutId IS NULL");
			}
			else {
				query.append("layoutId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (layoutId != null) {
				q.setString(queryPos++, layoutId);
			}

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public PortletPreferences findByLayoutId_First(String layoutId,
		OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		List list = findByLayoutId(layoutId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No PortletPreferences exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "layoutId=";
			msg += layoutId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPortletPreferencesException(msg);
		}
		else {
			return (PortletPreferences)list.get(0);
		}
	}

	public PortletPreferences findByLayoutId_Last(String layoutId,
		OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		int count = countByLayoutId(layoutId);
		List list = findByLayoutId(layoutId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No PortletPreferences exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "layoutId=";
			msg += layoutId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPortletPreferencesException(msg);
		}
		else {
			return (PortletPreferences)list.get(0);
		}
	}

	public PortletPreferences[] findByLayoutId_PrevAndNext(
		PortletPreferencesPK portletPreferencesPK, String layoutId,
		OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		PortletPreferences portletPreferences = findByPrimaryKey(portletPreferencesPK);
		int count = countByLayoutId(layoutId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portal.model.PortletPreferences WHERE ");

			if (layoutId == null) {
				query.append("layoutId IS NULL");
			}
			else {
				query.append("layoutId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (layoutId != null) {
				q.setString(queryPos++, layoutId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					portletPreferences);
			PortletPreferences[] array = new PortletPreferences[3];
			array[0] = (PortletPreferences)objArray[0];
			array[1] = (PortletPreferences)objArray[1];
			array[2] = (PortletPreferences)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByOwnerId(String ownerId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portal.model.PortletPreferences WHERE ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByOwnerId(String ownerId, int begin, int end)
		throws SystemException {
		return findByOwnerId(ownerId, begin, end, null);
	}

	public List findByOwnerId(String ownerId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portal.model.PortletPreferences WHERE ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public PortletPreferences findByOwnerId_First(String ownerId,
		OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		List list = findByOwnerId(ownerId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No PortletPreferences exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "ownerId=";
			msg += ownerId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPortletPreferencesException(msg);
		}
		else {
			return (PortletPreferences)list.get(0);
		}
	}

	public PortletPreferences findByOwnerId_Last(String ownerId,
		OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		int count = countByOwnerId(ownerId);
		List list = findByOwnerId(ownerId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No PortletPreferences exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "ownerId=";
			msg += ownerId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPortletPreferencesException(msg);
		}
		else {
			return (PortletPreferences)list.get(0);
		}
	}

	public PortletPreferences[] findByOwnerId_PrevAndNext(
		PortletPreferencesPK portletPreferencesPK, String ownerId,
		OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		PortletPreferences portletPreferences = findByPrimaryKey(portletPreferencesPK);
		int count = countByOwnerId(ownerId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portal.model.PortletPreferences WHERE ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					portletPreferences);
			PortletPreferences[] array = new PortletPreferences[3];
			array[0] = (PortletPreferences)objArray[0];
			array[1] = (PortletPreferences)objArray[1];
			array[2] = (PortletPreferences)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByL_O(String layoutId, String ownerId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portal.model.PortletPreferences WHERE ");

			if (layoutId == null) {
				query.append("layoutId IS NULL");
			}
			else {
				query.append("layoutId = ?");
			}

			query.append(" AND ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (layoutId != null) {
				q.setString(queryPos++, layoutId);
			}

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByL_O(String layoutId, String ownerId, int begin, int end)
		throws SystemException {
		return findByL_O(layoutId, ownerId, begin, end, null);
	}

	public List findByL_O(String layoutId, String ownerId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portal.model.PortletPreferences WHERE ");

			if (layoutId == null) {
				query.append("layoutId IS NULL");
			}
			else {
				query.append("layoutId = ?");
			}

			query.append(" AND ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (layoutId != null) {
				q.setString(queryPos++, layoutId);
			}

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public PortletPreferences findByL_O_First(String layoutId, String ownerId,
		OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		List list = findByL_O(layoutId, ownerId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No PortletPreferences exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "layoutId=";
			msg += layoutId;
			msg += ", ";
			msg += "ownerId=";
			msg += ownerId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPortletPreferencesException(msg);
		}
		else {
			return (PortletPreferences)list.get(0);
		}
	}

	public PortletPreferences findByL_O_Last(String layoutId, String ownerId,
		OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		int count = countByL_O(layoutId, ownerId);
		List list = findByL_O(layoutId, ownerId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No PortletPreferences exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "layoutId=";
			msg += layoutId;
			msg += ", ";
			msg += "ownerId=";
			msg += ownerId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPortletPreferencesException(msg);
		}
		else {
			return (PortletPreferences)list.get(0);
		}
	}

	public PortletPreferences[] findByL_O_PrevAndNext(
		PortletPreferencesPK portletPreferencesPK, String layoutId,
		String ownerId, OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		PortletPreferences portletPreferences = findByPrimaryKey(portletPreferencesPK);
		int count = countByL_O(layoutId, ownerId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portal.model.PortletPreferences WHERE ");

			if (layoutId == null) {
				query.append("layoutId IS NULL");
			}
			else {
				query.append("layoutId = ?");
			}

			query.append(" AND ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (layoutId != null) {
				q.setString(queryPos++, layoutId);
			}

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					portletPreferences);
			PortletPreferences[] array = new PortletPreferences[3];
			array[0] = (PortletPreferences)objArray[0];
			array[1] = (PortletPreferences)objArray[1];
			array[2] = (PortletPreferences)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.PortletPreferences ");

			Query q = session.createQuery(query.toString());

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByLayoutId(String layoutId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portal.model.PortletPreferences WHERE ");

			if (layoutId == null) {
				query.append("layoutId IS NULL");
			}
			else {
				query.append("layoutId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (layoutId != null) {
				q.setString(queryPos++, layoutId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				PortletPreferences portletPreferences = (PortletPreferences)itr.next();
				session.delete(portletPreferences);
			}

			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByOwnerId(String ownerId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portal.model.PortletPreferences WHERE ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				PortletPreferences portletPreferences = (PortletPreferences)itr.next();
				session.delete(portletPreferences);
			}

			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByL_O(String layoutId, String ownerId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portal.model.PortletPreferences WHERE ");

			if (layoutId == null) {
				query.append("layoutId IS NULL");
			}
			else {
				query.append("layoutId = ?");
			}

			query.append(" AND ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (layoutId != null) {
				q.setString(queryPos++, layoutId);
			}

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				PortletPreferences portletPreferences = (PortletPreferences)itr.next();
				session.delete(portletPreferences);
			}

			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByLayoutId(String layoutId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portal.model.PortletPreferences WHERE ");

			if (layoutId == null) {
				query.append("layoutId IS NULL");
			}
			else {
				query.append("layoutId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (layoutId != null) {
				q.setString(queryPos++, layoutId);
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
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByOwnerId(String ownerId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portal.model.PortletPreferences WHERE ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
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
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByL_O(String layoutId, String ownerId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portal.model.PortletPreferences WHERE ");

			if (layoutId == null) {
				query.append("layoutId IS NULL");
			}
			else {
				query.append("layoutId = ?");
			}

			query.append(" AND ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (layoutId != null) {
				q.setString(queryPos++, layoutId);
			}

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
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
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(PortletPreferencesPersistence.class);
}