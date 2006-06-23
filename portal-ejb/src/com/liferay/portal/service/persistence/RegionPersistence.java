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

import com.liferay.portal.NoSuchRegionException;
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
 * <a href="RegionPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class RegionPersistence extends BasePersistence {
	public com.liferay.portal.model.Region create(String regionId) {
		RegionHBM regionHBM = new RegionHBM();
		regionHBM.setNew(true);
		regionHBM.setPrimaryKey(regionId);

		return RegionHBMUtil.model(regionHBM);
	}

	public com.liferay.portal.model.Region remove(String regionId)
		throws NoSuchRegionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RegionHBM regionHBM = (RegionHBM)session.get(RegionHBM.class,
					regionId);

			if (regionHBM == null) {
				_log.warn("No Region exists with the primary key " +
					regionId.toString());
				throw new NoSuchRegionException(
					"No Region exists with the primary key " +
					regionId.toString());
			}

			session.delete(regionHBM);
			session.flush();

			return RegionHBMUtil.model(regionHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Region update(
		com.liferay.portal.model.Region region) throws SystemException {
		Session session = null;

		try {
			if (region.isNew() || region.isModified()) {
				session = openSession();

				if (region.isNew()) {
					RegionHBM regionHBM = new RegionHBM();
					regionHBM.setRegionId(region.getRegionId());
					regionHBM.setCountryId(region.getCountryId());
					regionHBM.setRegionCode(region.getRegionCode());
					regionHBM.setName(region.getName());
					regionHBM.setActive(region.getActive());
					session.save(regionHBM);
					session.flush();
				}
				else {
					RegionHBM regionHBM = (RegionHBM)session.get(RegionHBM.class,
							region.getPrimaryKey());

					if (regionHBM != null) {
						regionHBM.setCountryId(region.getCountryId());
						regionHBM.setRegionCode(region.getRegionCode());
						regionHBM.setName(region.getName());
						regionHBM.setActive(region.getActive());
						session.flush();
					}
					else {
						regionHBM = new RegionHBM();
						regionHBM.setRegionId(region.getRegionId());
						regionHBM.setCountryId(region.getCountryId());
						regionHBM.setRegionCode(region.getRegionCode());
						regionHBM.setName(region.getName());
						regionHBM.setActive(region.getActive());
						session.save(regionHBM);
						session.flush();
					}
				}

				region.setNew(false);
				region.setModified(false);
			}

			return region;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Region findByPrimaryKey(String regionId)
		throws NoSuchRegionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RegionHBM regionHBM = (RegionHBM)session.get(RegionHBM.class,
					regionId);

			if (regionHBM == null) {
				_log.warn("No Region exists with the primary key " +
					regionId.toString());
				throw new NoSuchRegionException(
					"No Region exists with the primary key " +
					regionId.toString());
			}

			return RegionHBMUtil.model(regionHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCountryId(String countryId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Region IN CLASS com.liferay.portal.service.persistence.RegionHBM WHERE ");

			if (countryId == null) {
				query.append("countryId is null");
			}
			else {
				query.append("countryId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (countryId != null) {
				q.setString(queryPos++, countryId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				RegionHBM regionHBM = (RegionHBM)itr.next();
				list.add(RegionHBMUtil.model(regionHBM));
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

	public List findByCountryId(String countryId, int begin, int end)
		throws SystemException {
		return findByCountryId(countryId, begin, end, null);
	}

	public List findByCountryId(String countryId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Region IN CLASS com.liferay.portal.service.persistence.RegionHBM WHERE ");

			if (countryId == null) {
				query.append("countryId is null");
			}
			else {
				query.append("countryId = ?");
			}

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

			if (countryId != null) {
				q.setString(queryPos++, countryId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				RegionHBM regionHBM = (RegionHBM)itr.next();
				list.add(RegionHBMUtil.model(regionHBM));
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

	public com.liferay.portal.model.Region findByCountryId_First(
		String countryId, OrderByComparator obc)
		throws NoSuchRegionException, SystemException {
		List list = findByCountryId(countryId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Region exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "countryId=";
			msg += countryId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchRegionException(msg);
		}
		else {
			return (com.liferay.portal.model.Region)list.get(0);
		}
	}

	public com.liferay.portal.model.Region findByCountryId_Last(
		String countryId, OrderByComparator obc)
		throws NoSuchRegionException, SystemException {
		int count = countByCountryId(countryId);
		List list = findByCountryId(countryId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Region exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "countryId=";
			msg += countryId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchRegionException(msg);
		}
		else {
			return (com.liferay.portal.model.Region)list.get(0);
		}
	}

	public com.liferay.portal.model.Region[] findByCountryId_PrevAndNext(
		String regionId, String countryId, OrderByComparator obc)
		throws NoSuchRegionException, SystemException {
		com.liferay.portal.model.Region region = findByPrimaryKey(regionId);
		int count = countByCountryId(countryId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Region IN CLASS com.liferay.portal.service.persistence.RegionHBM WHERE ");

			if (countryId == null) {
				query.append("countryId is null");
			}
			else {
				query.append("countryId = ?");
			}

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

			if (countryId != null) {
				q.setString(queryPos++, countryId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, region,
					RegionHBMUtil.getInstance());
			com.liferay.portal.model.Region[] array = new com.liferay.portal.model.Region[3];
			array[0] = (com.liferay.portal.model.Region)objArray[0];
			array[1] = (com.liferay.portal.model.Region)objArray[1];
			array[2] = (com.liferay.portal.model.Region)objArray[2];

			return array;
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
				"FROM Region IN CLASS com.liferay.portal.service.persistence.RegionHBM WHERE ");
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
				RegionHBM regionHBM = (RegionHBM)itr.next();
				list.add(RegionHBMUtil.model(regionHBM));
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
				"FROM Region IN CLASS com.liferay.portal.service.persistence.RegionHBM WHERE ");
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
				RegionHBM regionHBM = (RegionHBM)itr.next();
				list.add(RegionHBMUtil.model(regionHBM));
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

	public com.liferay.portal.model.Region findByActive_First(boolean active,
		OrderByComparator obc) throws NoSuchRegionException, SystemException {
		List list = findByActive(active, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Region exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "active=";
			msg += active;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchRegionException(msg);
		}
		else {
			return (com.liferay.portal.model.Region)list.get(0);
		}
	}

	public com.liferay.portal.model.Region findByActive_Last(boolean active,
		OrderByComparator obc) throws NoSuchRegionException, SystemException {
		int count = countByActive(active);
		List list = findByActive(active, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Region exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "active=";
			msg += active;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchRegionException(msg);
		}
		else {
			return (com.liferay.portal.model.Region)list.get(0);
		}
	}

	public com.liferay.portal.model.Region[] findByActive_PrevAndNext(
		String regionId, boolean active, OrderByComparator obc)
		throws NoSuchRegionException, SystemException {
		com.liferay.portal.model.Region region = findByPrimaryKey(regionId);
		int count = countByActive(active);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Region IN CLASS com.liferay.portal.service.persistence.RegionHBM WHERE ");
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

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, region,
					RegionHBMUtil.getInstance());
			com.liferay.portal.model.Region[] array = new com.liferay.portal.model.Region[3];
			array[0] = (com.liferay.portal.model.Region)objArray[0];
			array[1] = (com.liferay.portal.model.Region)objArray[1];
			array[2] = (com.liferay.portal.model.Region)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_A(String countryId, boolean active)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Region IN CLASS com.liferay.portal.service.persistence.RegionHBM WHERE ");

			if (countryId == null) {
				query.append("countryId is null");
			}
			else {
				query.append("countryId = ?");
			}

			query.append(" AND ");
			query.append("active_ = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (countryId != null) {
				q.setString(queryPos++, countryId);
			}

			q.setBoolean(queryPos++, active);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				RegionHBM regionHBM = (RegionHBM)itr.next();
				list.add(RegionHBMUtil.model(regionHBM));
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

	public List findByC_A(String countryId, boolean active, int begin, int end)
		throws SystemException {
		return findByC_A(countryId, active, begin, end, null);
	}

	public List findByC_A(String countryId, boolean active, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Region IN CLASS com.liferay.portal.service.persistence.RegionHBM WHERE ");

			if (countryId == null) {
				query.append("countryId is null");
			}
			else {
				query.append("countryId = ?");
			}

			query.append(" AND ");
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

			if (countryId != null) {
				q.setString(queryPos++, countryId);
			}

			q.setBoolean(queryPos++, active);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				RegionHBM regionHBM = (RegionHBM)itr.next();
				list.add(RegionHBMUtil.model(regionHBM));
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

	public com.liferay.portal.model.Region findByC_A_First(String countryId,
		boolean active, OrderByComparator obc)
		throws NoSuchRegionException, SystemException {
		List list = findByC_A(countryId, active, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Region exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "countryId=";
			msg += countryId;
			msg += ", ";
			msg += "active=";
			msg += active;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchRegionException(msg);
		}
		else {
			return (com.liferay.portal.model.Region)list.get(0);
		}
	}

	public com.liferay.portal.model.Region findByC_A_Last(String countryId,
		boolean active, OrderByComparator obc)
		throws NoSuchRegionException, SystemException {
		int count = countByC_A(countryId, active);
		List list = findByC_A(countryId, active, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Region exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "countryId=";
			msg += countryId;
			msg += ", ";
			msg += "active=";
			msg += active;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchRegionException(msg);
		}
		else {
			return (com.liferay.portal.model.Region)list.get(0);
		}
	}

	public com.liferay.portal.model.Region[] findByC_A_PrevAndNext(
		String regionId, String countryId, boolean active, OrderByComparator obc)
		throws NoSuchRegionException, SystemException {
		com.liferay.portal.model.Region region = findByPrimaryKey(regionId);
		int count = countByC_A(countryId, active);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Region IN CLASS com.liferay.portal.service.persistence.RegionHBM WHERE ");

			if (countryId == null) {
				query.append("countryId is null");
			}
			else {
				query.append("countryId = ?");
			}

			query.append(" AND ");
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

			if (countryId != null) {
				q.setString(queryPos++, countryId);
			}

			q.setBoolean(queryPos++, active);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, region,
					RegionHBMUtil.getInstance());
			com.liferay.portal.model.Region[] array = new com.liferay.portal.model.Region[3];
			array[0] = (com.liferay.portal.model.Region)objArray[0];
			array[1] = (com.liferay.portal.model.Region)objArray[1];
			array[2] = (com.liferay.portal.model.Region)objArray[2];

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
				"FROM Region IN CLASS com.liferay.portal.service.persistence.RegionHBM ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				RegionHBM regionHBM = (RegionHBM)itr.next();
				list.add(RegionHBMUtil.model(regionHBM));
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

	public void removeByCountryId(String countryId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Region IN CLASS com.liferay.portal.service.persistence.RegionHBM WHERE ");

			if (countryId == null) {
				query.append("countryId is null");
			}
			else {
				query.append("countryId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (countryId != null) {
				q.setString(queryPos++, countryId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				RegionHBM regionHBM = (RegionHBM)itr.next();
				session.delete(regionHBM);
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

	public void removeByActive(boolean active) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Region IN CLASS com.liferay.portal.service.persistence.RegionHBM WHERE ");
			query.append("active_ = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setBoolean(queryPos++, active);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				RegionHBM regionHBM = (RegionHBM)itr.next();
				session.delete(regionHBM);
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

	public void removeByC_A(String countryId, boolean active)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Region IN CLASS com.liferay.portal.service.persistence.RegionHBM WHERE ");

			if (countryId == null) {
				query.append("countryId is null");
			}
			else {
				query.append("countryId = ?");
			}

			query.append(" AND ");
			query.append("active_ = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (countryId != null) {
				q.setString(queryPos++, countryId);
			}

			q.setBoolean(queryPos++, active);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				RegionHBM regionHBM = (RegionHBM)itr.next();
				session.delete(regionHBM);
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

	public int countByCountryId(String countryId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM Region IN CLASS com.liferay.portal.service.persistence.RegionHBM WHERE ");

			if (countryId == null) {
				query.append("countryId is null");
			}
			else {
				query.append("countryId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (countryId != null) {
				q.setString(queryPos++, countryId);
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

	public int countByActive(boolean active) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM Region IN CLASS com.liferay.portal.service.persistence.RegionHBM WHERE ");
			query.append("active_ = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setBoolean(queryPos++, active);

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

	public int countByC_A(String countryId, boolean active)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM Region IN CLASS com.liferay.portal.service.persistence.RegionHBM WHERE ");

			if (countryId == null) {
				query.append("countryId is null");
			}
			else {
				query.append("countryId = ?");
			}

			query.append(" AND ");
			query.append("active_ = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (countryId != null) {
				q.setString(queryPos++, countryId);
			}

			q.setBoolean(queryPos++, active);

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

	private static Log _log = LogFactory.getLog(RegionPersistence.class);
}