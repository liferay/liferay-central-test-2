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
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.util.StringPool;
import com.liferay.util.dao.hibernate.OrderByComparator;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="PortletPreferencesPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PortletPreferencesPersistence extends BasePersistence {
	public com.liferay.portal.model.PortletPreferences create(
		PortletPreferencesPK portletPreferencesPK) {
		PortletPreferencesHBM portletPreferencesHBM = new PortletPreferencesHBM();
		portletPreferencesHBM.setNew(true);
		portletPreferencesHBM.setPrimaryKey(portletPreferencesPK);

		return PortletPreferencesHBMUtil.model(portletPreferencesHBM);
	}

	public com.liferay.portal.model.PortletPreferences remove(
		PortletPreferencesPK portletPreferencesPK)
		throws NoSuchPortletPreferencesException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PortletPreferencesHBM portletPreferencesHBM = (PortletPreferencesHBM)session.get(PortletPreferencesHBM.class,
					portletPreferencesPK);

			if (portletPreferencesHBM == null) {
				_log.warn("No PortletPreferences exists with the primary key " +
					portletPreferencesPK.toString());
				throw new NoSuchPortletPreferencesException(
					"No PortletPreferences exists with the primary key " +
					portletPreferencesPK.toString());
			}

			session.delete(portletPreferencesHBM);
			session.flush();

			return PortletPreferencesHBMUtil.model(portletPreferencesHBM);
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
					PortletPreferencesHBM portletPreferencesHBM = new PortletPreferencesHBM();
					portletPreferencesHBM.setPortletId(portletPreferences.getPortletId());
					portletPreferencesHBM.setLayoutId(portletPreferences.getLayoutId());
					portletPreferencesHBM.setOwnerId(portletPreferences.getOwnerId());
					portletPreferencesHBM.setPreferences(portletPreferences.getPreferences());
					session.save(portletPreferencesHBM);
					session.flush();
				}
				else {
					PortletPreferencesHBM portletPreferencesHBM = (PortletPreferencesHBM)session.get(PortletPreferencesHBM.class,
							portletPreferences.getPrimaryKey());

					if (portletPreferencesHBM != null) {
						portletPreferencesHBM.setPreferences(portletPreferences.getPreferences());
						session.flush();
					}
					else {
						portletPreferencesHBM = new PortletPreferencesHBM();
						portletPreferencesHBM.setPortletId(portletPreferences.getPortletId());
						portletPreferencesHBM.setLayoutId(portletPreferences.getLayoutId());
						portletPreferencesHBM.setOwnerId(portletPreferences.getOwnerId());
						portletPreferencesHBM.setPreferences(portletPreferences.getPreferences());
						session.save(portletPreferencesHBM);
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

	public com.liferay.portal.model.PortletPreferences findByPrimaryKey(
		PortletPreferencesPK portletPreferencesPK)
		throws NoSuchPortletPreferencesException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PortletPreferencesHBM portletPreferencesHBM = (PortletPreferencesHBM)session.get(PortletPreferencesHBM.class,
					portletPreferencesPK);

			if (portletPreferencesHBM == null) {
				_log.warn("No PortletPreferences exists with the primary key " +
					portletPreferencesPK.toString());
				throw new NoSuchPortletPreferencesException(
					"No PortletPreferences exists with the primary key " +
					portletPreferencesPK.toString());
			}

			return PortletPreferencesHBMUtil.model(portletPreferencesHBM);
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
				"FROM PortletPreferences IN CLASS com.liferay.portal.service.persistence.PortletPreferencesHBM WHERE ");

			if (layoutId == null) {
				query.append("layoutId is null");
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
			List list = new ArrayList();

			while (itr.hasNext()) {
				PortletPreferencesHBM portletPreferencesHBM = (PortletPreferencesHBM)itr.next();
				list.add(PortletPreferencesHBMUtil.model(portletPreferencesHBM));
			}

			return list;
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
				"FROM PortletPreferences IN CLASS com.liferay.portal.service.persistence.PortletPreferencesHBM WHERE ");

			if (layoutId == null) {
				query.append("layoutId is null");
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

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				PortletPreferencesHBM portletPreferencesHBM = (PortletPreferencesHBM)itr.next();
				list.add(PortletPreferencesHBMUtil.model(portletPreferencesHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.PortletPreferences findByLayoutId_First(
		String layoutId, OrderByComparator obc)
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
			return (com.liferay.portal.model.PortletPreferences)list.get(0);
		}
	}

	public com.liferay.portal.model.PortletPreferences findByLayoutId_Last(
		String layoutId, OrderByComparator obc)
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
			return (com.liferay.portal.model.PortletPreferences)list.get(0);
		}
	}

	public com.liferay.portal.model.PortletPreferences[] findByLayoutId_PrevAndNext(
		PortletPreferencesPK portletPreferencesPK, String layoutId,
		OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		com.liferay.portal.model.PortletPreferences portletPreferences = findByPrimaryKey(portletPreferencesPK);
		int count = countByLayoutId(layoutId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM PortletPreferences IN CLASS com.liferay.portal.service.persistence.PortletPreferencesHBM WHERE ");

			if (layoutId == null) {
				query.append("layoutId is null");
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
					portletPreferences, PortletPreferencesHBMUtil.getInstance());
			com.liferay.portal.model.PortletPreferences[] array = new com.liferay.portal.model.PortletPreferences[3];
			array[0] = (com.liferay.portal.model.PortletPreferences)objArray[0];
			array[1] = (com.liferay.portal.model.PortletPreferences)objArray[1];
			array[2] = (com.liferay.portal.model.PortletPreferences)objArray[2];

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
				"FROM PortletPreferences IN CLASS com.liferay.portal.service.persistence.PortletPreferencesHBM WHERE ");

			if (ownerId == null) {
				query.append("ownerId is null");
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
			List list = new ArrayList();

			while (itr.hasNext()) {
				PortletPreferencesHBM portletPreferencesHBM = (PortletPreferencesHBM)itr.next();
				list.add(PortletPreferencesHBMUtil.model(portletPreferencesHBM));
			}

			return list;
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
				"FROM PortletPreferences IN CLASS com.liferay.portal.service.persistence.PortletPreferencesHBM WHERE ");

			if (ownerId == null) {
				query.append("ownerId is null");
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

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				PortletPreferencesHBM portletPreferencesHBM = (PortletPreferencesHBM)itr.next();
				list.add(PortletPreferencesHBMUtil.model(portletPreferencesHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.PortletPreferences findByOwnerId_First(
		String ownerId, OrderByComparator obc)
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
			return (com.liferay.portal.model.PortletPreferences)list.get(0);
		}
	}

	public com.liferay.portal.model.PortletPreferences findByOwnerId_Last(
		String ownerId, OrderByComparator obc)
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
			return (com.liferay.portal.model.PortletPreferences)list.get(0);
		}
	}

	public com.liferay.portal.model.PortletPreferences[] findByOwnerId_PrevAndNext(
		PortletPreferencesPK portletPreferencesPK, String ownerId,
		OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		com.liferay.portal.model.PortletPreferences portletPreferences = findByPrimaryKey(portletPreferencesPK);
		int count = countByOwnerId(ownerId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM PortletPreferences IN CLASS com.liferay.portal.service.persistence.PortletPreferencesHBM WHERE ");

			if (ownerId == null) {
				query.append("ownerId is null");
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
					portletPreferences, PortletPreferencesHBMUtil.getInstance());
			com.liferay.portal.model.PortletPreferences[] array = new com.liferay.portal.model.PortletPreferences[3];
			array[0] = (com.liferay.portal.model.PortletPreferences)objArray[0];
			array[1] = (com.liferay.portal.model.PortletPreferences)objArray[1];
			array[2] = (com.liferay.portal.model.PortletPreferences)objArray[2];

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
				"FROM PortletPreferences IN CLASS com.liferay.portal.service.persistence.PortletPreferencesHBM WHERE ");

			if (layoutId == null) {
				query.append("layoutId is null");
			}
			else {
				query.append("layoutId = ?");
			}

			query.append(" AND ");

			if (ownerId == null) {
				query.append("ownerId is null");
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
			List list = new ArrayList();

			while (itr.hasNext()) {
				PortletPreferencesHBM portletPreferencesHBM = (PortletPreferencesHBM)itr.next();
				list.add(PortletPreferencesHBMUtil.model(portletPreferencesHBM));
			}

			return list;
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
				"FROM PortletPreferences IN CLASS com.liferay.portal.service.persistence.PortletPreferencesHBM WHERE ");

			if (layoutId == null) {
				query.append("layoutId is null");
			}
			else {
				query.append("layoutId = ?");
			}

			query.append(" AND ");

			if (ownerId == null) {
				query.append("ownerId is null");
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

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				PortletPreferencesHBM portletPreferencesHBM = (PortletPreferencesHBM)itr.next();
				list.add(PortletPreferencesHBMUtil.model(portletPreferencesHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.PortletPreferences findByL_O_First(
		String layoutId, String ownerId, OrderByComparator obc)
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
			return (com.liferay.portal.model.PortletPreferences)list.get(0);
		}
	}

	public com.liferay.portal.model.PortletPreferences findByL_O_Last(
		String layoutId, String ownerId, OrderByComparator obc)
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
			return (com.liferay.portal.model.PortletPreferences)list.get(0);
		}
	}

	public com.liferay.portal.model.PortletPreferences[] findByL_O_PrevAndNext(
		PortletPreferencesPK portletPreferencesPK, String layoutId,
		String ownerId, OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		com.liferay.portal.model.PortletPreferences portletPreferences = findByPrimaryKey(portletPreferencesPK);
		int count = countByL_O(layoutId, ownerId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM PortletPreferences IN CLASS com.liferay.portal.service.persistence.PortletPreferencesHBM WHERE ");

			if (layoutId == null) {
				query.append("layoutId is null");
			}
			else {
				query.append("layoutId = ?");
			}

			query.append(" AND ");

			if (ownerId == null) {
				query.append("ownerId is null");
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
					portletPreferences, PortletPreferencesHBMUtil.getInstance());
			com.liferay.portal.model.PortletPreferences[] array = new com.liferay.portal.model.PortletPreferences[3];
			array[0] = (com.liferay.portal.model.PortletPreferences)objArray[0];
			array[1] = (com.liferay.portal.model.PortletPreferences)objArray[1];
			array[2] = (com.liferay.portal.model.PortletPreferences)objArray[2];

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
			query.append(
				"FROM PortletPreferences IN CLASS com.liferay.portal.service.persistence.PortletPreferencesHBM ");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				PortletPreferencesHBM portletPreferencesHBM = (PortletPreferencesHBM)itr.next();
				list.add(PortletPreferencesHBMUtil.model(portletPreferencesHBM));
			}

			return list;
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
				"FROM PortletPreferences IN CLASS com.liferay.portal.service.persistence.PortletPreferencesHBM WHERE ");

			if (layoutId == null) {
				query.append("layoutId is null");
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
				PortletPreferencesHBM portletPreferencesHBM = (PortletPreferencesHBM)itr.next();
				session.delete(portletPreferencesHBM);
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
				"FROM PortletPreferences IN CLASS com.liferay.portal.service.persistence.PortletPreferencesHBM WHERE ");

			if (ownerId == null) {
				query.append("ownerId is null");
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
				PortletPreferencesHBM portletPreferencesHBM = (PortletPreferencesHBM)itr.next();
				session.delete(portletPreferencesHBM);
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
				"FROM PortletPreferences IN CLASS com.liferay.portal.service.persistence.PortletPreferencesHBM WHERE ");

			if (layoutId == null) {
				query.append("layoutId is null");
			}
			else {
				query.append("layoutId = ?");
			}

			query.append(" AND ");

			if (ownerId == null) {
				query.append("ownerId is null");
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
				PortletPreferencesHBM portletPreferencesHBM = (PortletPreferencesHBM)itr.next();
				session.delete(portletPreferencesHBM);
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
				"FROM PortletPreferences IN CLASS com.liferay.portal.service.persistence.PortletPreferencesHBM WHERE ");

			if (layoutId == null) {
				query.append("layoutId is null");
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
				"FROM PortletPreferences IN CLASS com.liferay.portal.service.persistence.PortletPreferencesHBM WHERE ");

			if (ownerId == null) {
				query.append("ownerId is null");
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
				"FROM PortletPreferences IN CLASS com.liferay.portal.service.persistence.PortletPreferencesHBM WHERE ");

			if (layoutId == null) {
				query.append("layoutId is null");
			}
			else {
				query.append("layoutId = ?");
			}

			query.append(" AND ");

			if (ownerId == null) {
				query.append("ownerId is null");
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

	private static Log _log = LogFactory.getLog(PortletPreferencesPersistence.class);
}