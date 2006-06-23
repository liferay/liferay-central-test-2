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

import com.liferay.portal.NoSuchPropertiesException;
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
 * <a href="PropertiesPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PropertiesPersistence extends BasePersistence {
	public com.liferay.portal.model.Properties create(PropertiesPK propertiesPK) {
		PropertiesHBM propertiesHBM = new PropertiesHBM();
		propertiesHBM.setNew(true);
		propertiesHBM.setPrimaryKey(propertiesPK);

		return PropertiesHBMUtil.model(propertiesHBM);
	}

	public com.liferay.portal.model.Properties remove(PropertiesPK propertiesPK)
		throws NoSuchPropertiesException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PropertiesHBM propertiesHBM = (PropertiesHBM)session.get(PropertiesHBM.class,
					propertiesPK);

			if (propertiesHBM == null) {
				_log.warn("No Properties exists with the primary key " +
					propertiesPK.toString());
				throw new NoSuchPropertiesException(
					"No Properties exists with the primary key " +
					propertiesPK.toString());
			}

			session.delete(propertiesHBM);
			session.flush();

			return PropertiesHBMUtil.model(propertiesHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Properties update(
		com.liferay.portal.model.Properties properties)
		throws SystemException {
		Session session = null;

		try {
			if (properties.isNew() || properties.isModified()) {
				session = openSession();

				if (properties.isNew()) {
					PropertiesHBM propertiesHBM = new PropertiesHBM();
					propertiesHBM.setCompanyId(properties.getCompanyId());
					propertiesHBM.setType(properties.getType());
					propertiesHBM.setProperties(properties.getProperties());
					session.save(propertiesHBM);
					session.flush();
				}
				else {
					PropertiesHBM propertiesHBM = (PropertiesHBM)session.get(PropertiesHBM.class,
							properties.getPrimaryKey());

					if (propertiesHBM != null) {
						propertiesHBM.setProperties(properties.getProperties());
						session.flush();
					}
					else {
						propertiesHBM = new PropertiesHBM();
						propertiesHBM.setCompanyId(properties.getCompanyId());
						propertiesHBM.setType(properties.getType());
						propertiesHBM.setProperties(properties.getProperties());
						session.save(propertiesHBM);
						session.flush();
					}
				}

				properties.setNew(false);
				properties.setModified(false);
			}

			return properties;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Properties findByPrimaryKey(
		PropertiesPK propertiesPK)
		throws NoSuchPropertiesException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PropertiesHBM propertiesHBM = (PropertiesHBM)session.get(PropertiesHBM.class,
					propertiesPK);

			if (propertiesHBM == null) {
				_log.warn("No Properties exists with the primary key " +
					propertiesPK.toString());
				throw new NoSuchPropertiesException(
					"No Properties exists with the primary key " +
					propertiesPK.toString());
			}

			return PropertiesHBMUtil.model(propertiesHBM);
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
			query.append(
				"FROM Properties IN CLASS com.liferay.portal.service.persistence.PropertiesHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
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
			List list = new ArrayList();

			while (itr.hasNext()) {
				PropertiesHBM propertiesHBM = (PropertiesHBM)itr.next();
				list.add(PropertiesHBMUtil.model(propertiesHBM));
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
			query.append(
				"FROM Properties IN CLASS com.liferay.portal.service.persistence.PropertiesHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
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

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				PropertiesHBM propertiesHBM = (PropertiesHBM)itr.next();
				list.add(PropertiesHBMUtil.model(propertiesHBM));
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

	public com.liferay.portal.model.Properties findByCompanyId_First(
		String companyId, OrderByComparator obc)
		throws NoSuchPropertiesException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Properties exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPropertiesException(msg);
		}
		else {
			return (com.liferay.portal.model.Properties)list.get(0);
		}
	}

	public com.liferay.portal.model.Properties findByCompanyId_Last(
		String companyId, OrderByComparator obc)
		throws NoSuchPropertiesException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Properties exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPropertiesException(msg);
		}
		else {
			return (com.liferay.portal.model.Properties)list.get(0);
		}
	}

	public com.liferay.portal.model.Properties[] findByCompanyId_PrevAndNext(
		PropertiesPK propertiesPK, String companyId, OrderByComparator obc)
		throws NoSuchPropertiesException, SystemException {
		com.liferay.portal.model.Properties properties = findByPrimaryKey(propertiesPK);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Properties IN CLASS com.liferay.portal.service.persistence.PropertiesHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
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

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					properties, PropertiesHBMUtil.getInstance());
			com.liferay.portal.model.Properties[] array = new com.liferay.portal.model.Properties[3];
			array[0] = (com.liferay.portal.model.Properties)objArray[0];
			array[1] = (com.liferay.portal.model.Properties)objArray[1];
			array[2] = (com.liferay.portal.model.Properties)objArray[2];

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
				"FROM Properties IN CLASS com.liferay.portal.service.persistence.PropertiesHBM ");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				PropertiesHBM propertiesHBM = (PropertiesHBM)itr.next();
				list.add(PropertiesHBMUtil.model(propertiesHBM));
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

	public void removeByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Properties IN CLASS com.liferay.portal.service.persistence.PropertiesHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
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
				PropertiesHBM propertiesHBM = (PropertiesHBM)itr.next();
				session.delete(propertiesHBM);
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
			query.append(
				"FROM Properties IN CLASS com.liferay.portal.service.persistence.PropertiesHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
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

	private static Log _log = LogFactory.getLog(PropertiesPersistence.class);
}