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

import com.liferay.portal.NoSuchPortletException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Portlet;
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
 * <a href="PortletPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PortletPersistence extends BasePersistence {
	public Portlet create(PortletPK portletPK) {
		Portlet portlet = new Portlet();
		portlet.setNew(true);
		portlet.setPrimaryKey(portletPK);

		return portlet;
	}

	public Portlet remove(PortletPK portletPK)
		throws NoSuchPortletException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Portlet portlet = (Portlet)session.get(Portlet.class, portletPK);

			if (portlet == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Portlet exists with the primary key " +
						portletPK.toString());
				}

				throw new NoSuchPortletException(
					"No Portlet exists with the primary key " +
					portletPK.toString());
			}

			session.delete(portlet);
			session.flush();

			return portlet;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Portlet update(
		com.liferay.portal.model.Portlet portlet) throws SystemException {
		Session session = null;

		try {
			if (portlet.isNew() || portlet.isModified()) {
				session = openSession();

				if (portlet.isNew()) {
					Portlet portletModel = new Portlet();
					portletModel.setPortletId(portlet.getPortletId());
					portletModel.setCompanyId(portlet.getCompanyId());
					portletModel.setNarrow(portlet.getNarrow());
					portletModel.setRoles(portlet.getRoles());
					portletModel.setActive(portlet.getActive());
					session.save(portletModel);
					session.flush();
				}
				else {
					Portlet portletModel = (Portlet)session.get(Portlet.class,
							portlet.getPrimaryKey());

					if (portletModel != null) {
						portletModel.setNarrow(portlet.getNarrow());
						portletModel.setRoles(portlet.getRoles());
						portletModel.setActive(portlet.getActive());
						session.flush();
					}
					else {
						portletModel = new Portlet();
						portletModel.setPortletId(portlet.getPortletId());
						portletModel.setCompanyId(portlet.getCompanyId());
						portletModel.setNarrow(portlet.getNarrow());
						portletModel.setRoles(portlet.getRoles());
						portletModel.setActive(portlet.getActive());
						session.save(portletModel);
						session.flush();
					}
				}

				portlet.setNew(false);
				portlet.setModified(false);
			}

			return portlet;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Portlet findByPrimaryKey(PortletPK portletPK)
		throws NoSuchPortletException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Portlet portlet = (Portlet)session.get(Portlet.class, portletPK);

			if (portlet == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Portlet exists with the primary key " +
						portletPK.toString());
				}

				throw new NoSuchPortletException(
					"No Portlet exists with the primary key " +
					portletPK.toString());
			}

			return portlet;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Portlet fetchByPrimaryKey(PortletPK portletPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (Portlet)session.get(Portlet.class, portletPK);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Portlet WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
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

	public List findByCompanyId(String companyId, int begin, int end)
		throws SystemException {
		return findByCompanyId(companyId, begin, end, null);
	}

	public List findByCompanyId(String companyId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Portlet WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
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

	public Portlet findByCompanyId_First(String companyId, OrderByComparator obc)
		throws NoSuchPortletException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Portlet exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPortletException(msg);
		}
		else {
			return (Portlet)list.get(0);
		}
	}

	public Portlet findByCompanyId_Last(String companyId, OrderByComparator obc)
		throws NoSuchPortletException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Portlet exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPortletException(msg);
		}
		else {
			return (Portlet)list.get(0);
		}
	}

	public Portlet[] findByCompanyId_PrevAndNext(PortletPK portletPK,
		String companyId, OrderByComparator obc)
		throws NoSuchPortletException, SystemException {
		Portlet portlet = findByPrimaryKey(portletPK);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Portlet WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, portlet);
			Portlet[] array = new Portlet[3];
			array[0] = (Portlet)objArray[0];
			array[1] = (Portlet)objArray[1];
			array[2] = (Portlet)objArray[2];

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
			query.append("FROM com.liferay.portal.model.Portlet ");

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

	public void removeByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Portlet WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				Portlet portlet = (Portlet)itr.next();
				session.delete(portlet);
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

	public int countByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Portlet WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
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

	private static Log _log = LogFactory.getLog(PortletPersistence.class);
}