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

import com.liferay.portal.NoSuchCountryException;
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
 * <a href="CountryPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class CountryPersistence extends BasePersistence {
	public com.liferay.portal.model.Country create(String countryId) {
		CountryHBM countryHBM = new CountryHBM();
		countryHBM.setNew(true);
		countryHBM.setPrimaryKey(countryId);

		return CountryHBMUtil.model(countryHBM);
	}

	public com.liferay.portal.model.Country remove(String countryId)
		throws NoSuchCountryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			CountryHBM countryHBM = (CountryHBM)session.get(CountryHBM.class,
					countryId);

			if (countryHBM == null) {
				_log.warn("No Country exists with the primary key " +
					countryId.toString());
				throw new NoSuchCountryException(
					"No Country exists with the primary key " +
					countryId.toString());
			}

			session.delete(countryHBM);
			session.flush();

			return CountryHBMUtil.model(countryHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Country update(
		com.liferay.portal.model.Country country) throws SystemException {
		Session session = null;

		try {
			if (country.isNew() || country.isModified()) {
				session = openSession();

				if (country.isNew()) {
					CountryHBM countryHBM = new CountryHBM();
					countryHBM.setCountryId(country.getCountryId());
					countryHBM.setCountryCode(country.getCountryCode());
					countryHBM.setName(country.getName());
					countryHBM.setActive(country.getActive());
					session.save(countryHBM);
					session.flush();
				}
				else {
					CountryHBM countryHBM = (CountryHBM)session.get(CountryHBM.class,
							country.getPrimaryKey());

					if (countryHBM != null) {
						countryHBM.setCountryCode(country.getCountryCode());
						countryHBM.setName(country.getName());
						countryHBM.setActive(country.getActive());
						session.flush();
					}
					else {
						countryHBM = new CountryHBM();
						countryHBM.setCountryId(country.getCountryId());
						countryHBM.setCountryCode(country.getCountryCode());
						countryHBM.setName(country.getName());
						countryHBM.setActive(country.getActive());
						session.save(countryHBM);
						session.flush();
					}
				}

				country.setNew(false);
				country.setModified(false);
			}

			return country;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Country findByPrimaryKey(String countryId)
		throws NoSuchCountryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			CountryHBM countryHBM = (CountryHBM)session.get(CountryHBM.class,
					countryId);

			if (countryHBM == null) {
				_log.warn("No Country exists with the primary key " +
					countryId.toString());
				throw new NoSuchCountryException(
					"No Country exists with the primary key " +
					countryId.toString());
			}

			return CountryHBMUtil.model(countryHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByActive(boolean active) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Country IN CLASS com.liferay.portal.service.persistence.CountryHBM WHERE ");
			query.append("active_ = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setBoolean(queryPos++, active);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				CountryHBM countryHBM = (CountryHBM)itr.next();
				list.add(CountryHBMUtil.model(countryHBM));
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

	public List findByActive(boolean active, int begin, int end)
		throws SystemException {
		return findByActive(active, begin, end, null);
	}

	public List findByActive(boolean active, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Country IN CLASS com.liferay.portal.service.persistence.CountryHBM WHERE ");
			query.append("active_ = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setBoolean(queryPos++, active);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				CountryHBM countryHBM = (CountryHBM)itr.next();
				list.add(CountryHBMUtil.model(countryHBM));
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

	public com.liferay.portal.model.Country findByActive_First(boolean active,
		OrderByComparator obc) throws NoSuchCountryException, SystemException {
		List list = findByActive(active, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Country exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "active=";
			msg += active;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchCountryException(msg);
		}
		else {
			return (com.liferay.portal.model.Country)list.get(0);
		}
	}

	public com.liferay.portal.model.Country findByActive_Last(boolean active,
		OrderByComparator obc) throws NoSuchCountryException, SystemException {
		int count = countByActive(active);
		List list = findByActive(active, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Country exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "active=";
			msg += active;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchCountryException(msg);
		}
		else {
			return (com.liferay.portal.model.Country)list.get(0);
		}
	}

	public com.liferay.portal.model.Country[] findByActive_PrevAndNext(
		String countryId, boolean active, OrderByComparator obc)
		throws NoSuchCountryException, SystemException {
		com.liferay.portal.model.Country country = findByPrimaryKey(countryId);
		int count = countByActive(active);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Country IN CLASS com.liferay.portal.service.persistence.CountryHBM WHERE ");
			query.append("active_ = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setBoolean(queryPos++, active);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					country, CountryHBMUtil.getInstance());
			com.liferay.portal.model.Country[] array = new com.liferay.portal.model.Country[3];
			array[0] = (com.liferay.portal.model.Country)objArray[0];
			array[1] = (com.liferay.portal.model.Country)objArray[1];
			array[2] = (com.liferay.portal.model.Country)objArray[2];

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
				"FROM Country IN CLASS com.liferay.portal.service.persistence.CountryHBM ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				CountryHBM countryHBM = (CountryHBM)itr.next();
				list.add(CountryHBMUtil.model(countryHBM));
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

	public void removeByActive(boolean active) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Country IN CLASS com.liferay.portal.service.persistence.CountryHBM WHERE ");
			query.append("active_ = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setBoolean(queryPos++, active);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				CountryHBM countryHBM = (CountryHBM)itr.next();
				session.delete(countryHBM);
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

	public int countByActive(boolean active) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM Country IN CLASS com.liferay.portal.service.persistence.CountryHBM WHERE ");
			query.append("active_ = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setBoolean(queryPos++, active);

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

	private static Log _log = LogFactory.getLog(CountryPersistence.class);
}