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

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.util.StringPool;
import com.liferay.util.dao.hibernate.OrderByComparator;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="LayoutPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class LayoutPersistence extends BasePersistence {
	public com.liferay.portal.model.Layout create(LayoutPK layoutPK) {
		LayoutHBM layoutHBM = new LayoutHBM();
		layoutHBM.setNew(true);
		layoutHBM.setPrimaryKey(layoutPK);

		return LayoutHBMUtil.model(layoutHBM);
	}

	public com.liferay.portal.model.Layout remove(LayoutPK layoutPK)
		throws NoSuchLayoutException, SystemException {
		Session session = null;

		try {
			session = openSession();

			LayoutHBM layoutHBM = (LayoutHBM)session.get(LayoutHBM.class,
					layoutPK);

			if (layoutHBM == null) {
				_log.warn("No Layout exists with the primary key " +
					layoutPK.toString());
				throw new NoSuchLayoutException(
					"No Layout exists with the primary key " +
					layoutPK.toString());
			}

			session.delete(layoutHBM);
			session.flush();

			return LayoutHBMUtil.model(layoutHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Layout update(
		com.liferay.portal.model.Layout layout) throws SystemException {
		Session session = null;

		try {
			if (layout.isNew() || layout.isModified()) {
				session = openSession();

				if (layout.isNew()) {
					LayoutHBM layoutHBM = new LayoutHBM();
					layoutHBM.setLayoutId(layout.getLayoutId());
					layoutHBM.setOwnerId(layout.getOwnerId());
					layoutHBM.setCompanyId(layout.getCompanyId());
					layoutHBM.setParentLayoutId(layout.getParentLayoutId());
					layoutHBM.setName(layout.getName());
					layoutHBM.setType(layout.getType());
					layoutHBM.setTypeSettings(layout.getTypeSettings());
					layoutHBM.setHidden(layout.getHidden());
					layoutHBM.setFriendlyURL(layout.getFriendlyURL());
					layoutHBM.setThemeId(layout.getThemeId());
					layoutHBM.setColorSchemeId(layout.getColorSchemeId());
					layoutHBM.setPriority(layout.getPriority());
					session.save(layoutHBM);
					session.flush();
				}
				else {
					LayoutHBM layoutHBM = (LayoutHBM)session.get(LayoutHBM.class,
							layout.getPrimaryKey());

					if (layoutHBM != null) {
						layoutHBM.setCompanyId(layout.getCompanyId());
						layoutHBM.setParentLayoutId(layout.getParentLayoutId());
						layoutHBM.setName(layout.getName());
						layoutHBM.setType(layout.getType());
						layoutHBM.setTypeSettings(layout.getTypeSettings());
						layoutHBM.setHidden(layout.getHidden());
						layoutHBM.setFriendlyURL(layout.getFriendlyURL());
						layoutHBM.setThemeId(layout.getThemeId());
						layoutHBM.setColorSchemeId(layout.getColorSchemeId());
						layoutHBM.setPriority(layout.getPriority());
						session.flush();
					}
					else {
						layoutHBM = new LayoutHBM();
						layoutHBM.setLayoutId(layout.getLayoutId());
						layoutHBM.setOwnerId(layout.getOwnerId());
						layoutHBM.setCompanyId(layout.getCompanyId());
						layoutHBM.setParentLayoutId(layout.getParentLayoutId());
						layoutHBM.setName(layout.getName());
						layoutHBM.setType(layout.getType());
						layoutHBM.setTypeSettings(layout.getTypeSettings());
						layoutHBM.setHidden(layout.getHidden());
						layoutHBM.setFriendlyURL(layout.getFriendlyURL());
						layoutHBM.setThemeId(layout.getThemeId());
						layoutHBM.setColorSchemeId(layout.getColorSchemeId());
						layoutHBM.setPriority(layout.getPriority());
						session.save(layoutHBM);
						session.flush();
					}
				}

				layout.setNew(false);
				layout.setModified(false);
			}

			return layout;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Layout findByPrimaryKey(LayoutPK layoutPK)
		throws NoSuchLayoutException, SystemException {
		Session session = null;

		try {
			session = openSession();

			LayoutHBM layoutHBM = (LayoutHBM)session.get(LayoutHBM.class,
					layoutPK);

			if (layoutHBM == null) {
				_log.warn("No Layout exists with the primary key " +
					layoutPK.toString());
				throw new NoSuchLayoutException(
					"No Layout exists with the primary key " +
					layoutPK.toString());
			}

			return LayoutHBMUtil.model(layoutHBM);
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
				"FROM Layout IN CLASS com.liferay.portal.service.persistence.LayoutHBM WHERE ");
			query.append("ownerId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentLayoutId ASC").append(", ");
			query.append("priority ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, ownerId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				LayoutHBM layoutHBM = (LayoutHBM)itr.next();
				list.add(LayoutHBMUtil.model(layoutHBM));
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
				"FROM Layout IN CLASS com.liferay.portal.service.persistence.LayoutHBM WHERE ");
			query.append("ownerId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("parentLayoutId ASC").append(", ");
				query.append("priority ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, ownerId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				LayoutHBM layoutHBM = (LayoutHBM)itr.next();
				list.add(LayoutHBMUtil.model(layoutHBM));
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

	public com.liferay.portal.model.Layout findByOwnerId_First(String ownerId,
		OrderByComparator obc) throws NoSuchLayoutException, SystemException {
		List list = findByOwnerId(ownerId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Layout exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "ownerId=";
			msg += ownerId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchLayoutException(msg);
		}
		else {
			return (com.liferay.portal.model.Layout)list.get(0);
		}
	}

	public com.liferay.portal.model.Layout findByOwnerId_Last(String ownerId,
		OrderByComparator obc) throws NoSuchLayoutException, SystemException {
		int count = countByOwnerId(ownerId);
		List list = findByOwnerId(ownerId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Layout exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "ownerId=";
			msg += ownerId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchLayoutException(msg);
		}
		else {
			return (com.liferay.portal.model.Layout)list.get(0);
		}
	}

	public com.liferay.portal.model.Layout[] findByOwnerId_PrevAndNext(
		LayoutPK layoutPK, String ownerId, OrderByComparator obc)
		throws NoSuchLayoutException, SystemException {
		com.liferay.portal.model.Layout layout = findByPrimaryKey(layoutPK);
		int count = countByOwnerId(ownerId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Layout IN CLASS com.liferay.portal.service.persistence.LayoutHBM WHERE ");
			query.append("ownerId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("parentLayoutId ASC").append(", ");
				query.append("priority ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, ownerId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, layout,
					LayoutHBMUtil.getInstance());
			com.liferay.portal.model.Layout[] array = new com.liferay.portal.model.Layout[3];
			array[0] = (com.liferay.portal.model.Layout)objArray[0];
			array[1] = (com.liferay.portal.model.Layout)objArray[1];
			array[2] = (com.liferay.portal.model.Layout)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByO_P(String ownerId, String parentLayoutId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Layout IN CLASS com.liferay.portal.service.persistence.LayoutHBM WHERE ");
			query.append("ownerId = ?");
			query.append(" AND ");
			query.append("parentLayoutId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentLayoutId ASC").append(", ");
			query.append("priority ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, ownerId);
			q.setString(queryPos++, parentLayoutId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				LayoutHBM layoutHBM = (LayoutHBM)itr.next();
				list.add(LayoutHBMUtil.model(layoutHBM));
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

	public List findByO_P(String ownerId, String parentLayoutId, int begin,
		int end) throws SystemException {
		return findByO_P(ownerId, parentLayoutId, begin, end, null);
	}

	public List findByO_P(String ownerId, String parentLayoutId, int begin,
		int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Layout IN CLASS com.liferay.portal.service.persistence.LayoutHBM WHERE ");
			query.append("ownerId = ?");
			query.append(" AND ");
			query.append("parentLayoutId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("parentLayoutId ASC").append(", ");
				query.append("priority ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, ownerId);
			q.setString(queryPos++, parentLayoutId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				LayoutHBM layoutHBM = (LayoutHBM)itr.next();
				list.add(LayoutHBMUtil.model(layoutHBM));
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

	public com.liferay.portal.model.Layout findByO_P_First(String ownerId,
		String parentLayoutId, OrderByComparator obc)
		throws NoSuchLayoutException, SystemException {
		List list = findByO_P(ownerId, parentLayoutId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Layout exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "ownerId=";
			msg += ownerId;
			msg += ", ";
			msg += "parentLayoutId=";
			msg += parentLayoutId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchLayoutException(msg);
		}
		else {
			return (com.liferay.portal.model.Layout)list.get(0);
		}
	}

	public com.liferay.portal.model.Layout findByO_P_Last(String ownerId,
		String parentLayoutId, OrderByComparator obc)
		throws NoSuchLayoutException, SystemException {
		int count = countByO_P(ownerId, parentLayoutId);
		List list = findByO_P(ownerId, parentLayoutId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Layout exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "ownerId=";
			msg += ownerId;
			msg += ", ";
			msg += "parentLayoutId=";
			msg += parentLayoutId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchLayoutException(msg);
		}
		else {
			return (com.liferay.portal.model.Layout)list.get(0);
		}
	}

	public com.liferay.portal.model.Layout[] findByO_P_PrevAndNext(
		LayoutPK layoutPK, String ownerId, String parentLayoutId,
		OrderByComparator obc) throws NoSuchLayoutException, SystemException {
		com.liferay.portal.model.Layout layout = findByPrimaryKey(layoutPK);
		int count = countByO_P(ownerId, parentLayoutId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Layout IN CLASS com.liferay.portal.service.persistence.LayoutHBM WHERE ");
			query.append("ownerId = ?");
			query.append(" AND ");
			query.append("parentLayoutId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("parentLayoutId ASC").append(", ");
				query.append("priority ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, ownerId);
			q.setString(queryPos++, parentLayoutId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, layout,
					LayoutHBMUtil.getInstance());
			com.liferay.portal.model.Layout[] array = new com.liferay.portal.model.Layout[3];
			array[0] = (com.liferay.portal.model.Layout)objArray[0];
			array[1] = (com.liferay.portal.model.Layout)objArray[1];
			array[2] = (com.liferay.portal.model.Layout)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Layout findByO_F(String ownerId,
		String friendlyURL) throws NoSuchLayoutException, SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Layout IN CLASS com.liferay.portal.service.persistence.LayoutHBM WHERE ");
			query.append("ownerId = ?");
			query.append(" AND ");
			query.append("friendlyURL = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentLayoutId ASC").append(", ");
			query.append("priority ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, ownerId);
			q.setString(queryPos++, friendlyURL);

			Iterator itr = q.list().iterator();

			if (!itr.hasNext()) {
				String msg = "No Layout exists with the key ";
				msg += StringPool.OPEN_CURLY_BRACE;
				msg += "ownerId=";
				msg += ownerId;
				msg += ", ";
				msg += "friendlyURL=";
				msg += friendlyURL;
				msg += StringPool.CLOSE_CURLY_BRACE;
				throw new NoSuchLayoutException(msg);
			}

			LayoutHBM layoutHBM = (LayoutHBM)itr.next();

			return LayoutHBMUtil.model(layoutHBM);
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
				"FROM Layout IN CLASS com.liferay.portal.service.persistence.LayoutHBM ");
			query.append("ORDER BY ");
			query.append("parentLayoutId ASC").append(", ");
			query.append("priority ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				LayoutHBM layoutHBM = (LayoutHBM)itr.next();
				list.add(LayoutHBMUtil.model(layoutHBM));
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

	public void removeByOwnerId(String ownerId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Layout IN CLASS com.liferay.portal.service.persistence.LayoutHBM WHERE ");
			query.append("ownerId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentLayoutId ASC").append(", ");
			query.append("priority ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, ownerId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				LayoutHBM layoutHBM = (LayoutHBM)itr.next();
				session.delete(layoutHBM);
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

	public void removeByO_P(String ownerId, String parentLayoutId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Layout IN CLASS com.liferay.portal.service.persistence.LayoutHBM WHERE ");
			query.append("ownerId = ?");
			query.append(" AND ");
			query.append("parentLayoutId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentLayoutId ASC").append(", ");
			query.append("priority ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, ownerId);
			q.setString(queryPos++, parentLayoutId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				LayoutHBM layoutHBM = (LayoutHBM)itr.next();
				session.delete(layoutHBM);
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

	public void removeByO_F(String ownerId, String friendlyURL)
		throws NoSuchLayoutException, SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Layout IN CLASS com.liferay.portal.service.persistence.LayoutHBM WHERE ");
			query.append("ownerId = ?");
			query.append(" AND ");
			query.append("friendlyURL = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentLayoutId ASC").append(", ");
			query.append("priority ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, ownerId);
			q.setString(queryPos++, friendlyURL);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				LayoutHBM layoutHBM = (LayoutHBM)itr.next();
				session.delete(layoutHBM);
			}

			session.flush();
		}
		catch (HibernateException he) {
			if (he instanceof ObjectNotFoundException) {
				String msg = "No Layout exists with the key ";
				msg += StringPool.OPEN_CURLY_BRACE;
				msg += "ownerId=";
				msg += ownerId;
				msg += ", ";
				msg += "friendlyURL=";
				msg += friendlyURL;
				msg += StringPool.CLOSE_CURLY_BRACE;
				throw new NoSuchLayoutException(msg);
			}
			else {
				throw new SystemException(he);
			}
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
				"FROM Layout IN CLASS com.liferay.portal.service.persistence.LayoutHBM WHERE ");
			query.append("ownerId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, ownerId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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

	public int countByO_P(String ownerId, String parentLayoutId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM Layout IN CLASS com.liferay.portal.service.persistence.LayoutHBM WHERE ");
			query.append("ownerId = ?");
			query.append(" AND ");
			query.append("parentLayoutId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, ownerId);
			q.setString(queryPos++, parentLayoutId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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

	public int countByO_F(String ownerId, String friendlyURL)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM Layout IN CLASS com.liferay.portal.service.persistence.LayoutHBM WHERE ");
			query.append("ownerId = ?");
			query.append(" AND ");
			query.append("friendlyURL = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, ownerId);
			q.setString(queryPos++, friendlyURL);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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

	private static Log _log = LogFactory.getLog(LayoutPersistence.class);
}