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

import com.liferay.portal.NoSuchOrgLaborException;
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
 * <a href="OrgLaborPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class OrgLaborPersistence extends BasePersistence {
	public com.liferay.portal.model.OrgLabor create(String orgLaborId) {
		OrgLaborHBM orgLaborHBM = new OrgLaborHBM();
		orgLaborHBM.setNew(true);
		orgLaborHBM.setPrimaryKey(orgLaborId);

		return OrgLaborHBMUtil.model(orgLaborHBM);
	}

	public com.liferay.portal.model.OrgLabor remove(String orgLaborId)
		throws NoSuchOrgLaborException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrgLaborHBM orgLaborHBM = (OrgLaborHBM)session.get(OrgLaborHBM.class,
					orgLaborId);

			if (orgLaborHBM == null) {
				_log.warn("No OrgLabor exists with the primary key " +
					orgLaborId.toString());
				throw new NoSuchOrgLaborException(
					"No OrgLabor exists with the primary key " +
					orgLaborId.toString());
			}

			session.delete(orgLaborHBM);
			session.flush();

			return OrgLaborHBMUtil.model(orgLaborHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.OrgLabor update(
		com.liferay.portal.model.OrgLabor orgLabor) throws SystemException {
		Session session = null;

		try {
			if (orgLabor.isNew() || orgLabor.isModified()) {
				session = openSession();

				if (orgLabor.isNew()) {
					OrgLaborHBM orgLaborHBM = new OrgLaborHBM();
					orgLaborHBM.setOrgLaborId(orgLabor.getOrgLaborId());
					orgLaborHBM.setOrganizationId(orgLabor.getOrganizationId());
					orgLaborHBM.setTypeId(orgLabor.getTypeId());
					orgLaborHBM.setSunOpen(orgLabor.getSunOpen());
					orgLaborHBM.setSunClose(orgLabor.getSunClose());
					orgLaborHBM.setMonOpen(orgLabor.getMonOpen());
					orgLaborHBM.setMonClose(orgLabor.getMonClose());
					orgLaborHBM.setTueOpen(orgLabor.getTueOpen());
					orgLaborHBM.setTueClose(orgLabor.getTueClose());
					orgLaborHBM.setWedOpen(orgLabor.getWedOpen());
					orgLaborHBM.setWedClose(orgLabor.getWedClose());
					orgLaborHBM.setThuOpen(orgLabor.getThuOpen());
					orgLaborHBM.setThuClose(orgLabor.getThuClose());
					orgLaborHBM.setFriOpen(orgLabor.getFriOpen());
					orgLaborHBM.setFriClose(orgLabor.getFriClose());
					orgLaborHBM.setSatOpen(orgLabor.getSatOpen());
					orgLaborHBM.setSatClose(orgLabor.getSatClose());
					session.save(orgLaborHBM);
					session.flush();
				}
				else {
					OrgLaborHBM orgLaborHBM = (OrgLaborHBM)session.get(OrgLaborHBM.class,
							orgLabor.getPrimaryKey());

					if (orgLaborHBM != null) {
						orgLaborHBM.setOrganizationId(orgLabor.getOrganizationId());
						orgLaborHBM.setTypeId(orgLabor.getTypeId());
						orgLaborHBM.setSunOpen(orgLabor.getSunOpen());
						orgLaborHBM.setSunClose(orgLabor.getSunClose());
						orgLaborHBM.setMonOpen(orgLabor.getMonOpen());
						orgLaborHBM.setMonClose(orgLabor.getMonClose());
						orgLaborHBM.setTueOpen(orgLabor.getTueOpen());
						orgLaborHBM.setTueClose(orgLabor.getTueClose());
						orgLaborHBM.setWedOpen(orgLabor.getWedOpen());
						orgLaborHBM.setWedClose(orgLabor.getWedClose());
						orgLaborHBM.setThuOpen(orgLabor.getThuOpen());
						orgLaborHBM.setThuClose(orgLabor.getThuClose());
						orgLaborHBM.setFriOpen(orgLabor.getFriOpen());
						orgLaborHBM.setFriClose(orgLabor.getFriClose());
						orgLaborHBM.setSatOpen(orgLabor.getSatOpen());
						orgLaborHBM.setSatClose(orgLabor.getSatClose());
						session.flush();
					}
					else {
						orgLaborHBM = new OrgLaborHBM();
						orgLaborHBM.setOrgLaborId(orgLabor.getOrgLaborId());
						orgLaborHBM.setOrganizationId(orgLabor.getOrganizationId());
						orgLaborHBM.setTypeId(orgLabor.getTypeId());
						orgLaborHBM.setSunOpen(orgLabor.getSunOpen());
						orgLaborHBM.setSunClose(orgLabor.getSunClose());
						orgLaborHBM.setMonOpen(orgLabor.getMonOpen());
						orgLaborHBM.setMonClose(orgLabor.getMonClose());
						orgLaborHBM.setTueOpen(orgLabor.getTueOpen());
						orgLaborHBM.setTueClose(orgLabor.getTueClose());
						orgLaborHBM.setWedOpen(orgLabor.getWedOpen());
						orgLaborHBM.setWedClose(orgLabor.getWedClose());
						orgLaborHBM.setThuOpen(orgLabor.getThuOpen());
						orgLaborHBM.setThuClose(orgLabor.getThuClose());
						orgLaborHBM.setFriOpen(orgLabor.getFriOpen());
						orgLaborHBM.setFriClose(orgLabor.getFriClose());
						orgLaborHBM.setSatOpen(orgLabor.getSatOpen());
						orgLaborHBM.setSatClose(orgLabor.getSatClose());
						session.save(orgLaborHBM);
						session.flush();
					}
				}

				orgLabor.setNew(false);
				orgLabor.setModified(false);
			}

			return orgLabor;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.OrgLabor findByPrimaryKey(String orgLaborId)
		throws NoSuchOrgLaborException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrgLaborHBM orgLaborHBM = (OrgLaborHBM)session.get(OrgLaborHBM.class,
					orgLaborId);

			if (orgLaborHBM == null) {
				_log.warn("No OrgLabor exists with the primary key " +
					orgLaborId.toString());
				throw new NoSuchOrgLaborException(
					"No OrgLabor exists with the primary key " +
					orgLaborId.toString());
			}

			return OrgLaborHBMUtil.model(orgLaborHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByOrganizationId(String organizationId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM OrgLabor IN CLASS com.liferay.portal.service.persistence.OrgLaborHBM WHERE ");
			query.append("organizationId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("organizationId ASC").append(", ");
			query.append("typeId ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, organizationId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				OrgLaborHBM orgLaborHBM = (OrgLaborHBM)itr.next();
				list.add(OrgLaborHBMUtil.model(orgLaborHBM));
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

	public List findByOrganizationId(String organizationId, int begin, int end)
		throws SystemException {
		return findByOrganizationId(organizationId, begin, end, null);
	}

	public List findByOrganizationId(String organizationId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM OrgLabor IN CLASS com.liferay.portal.service.persistence.OrgLaborHBM WHERE ");
			query.append("organizationId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("organizationId ASC").append(", ");
				query.append("typeId ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, organizationId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				OrgLaborHBM orgLaborHBM = (OrgLaborHBM)itr.next();
				list.add(OrgLaborHBMUtil.model(orgLaborHBM));
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

	public com.liferay.portal.model.OrgLabor findByOrganizationId_First(
		String organizationId, OrderByComparator obc)
		throws NoSuchOrgLaborException, SystemException {
		List list = findByOrganizationId(organizationId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No OrgLabor exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "organizationId=";
			msg += organizationId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchOrgLaborException(msg);
		}
		else {
			return (com.liferay.portal.model.OrgLabor)list.get(0);
		}
	}

	public com.liferay.portal.model.OrgLabor findByOrganizationId_Last(
		String organizationId, OrderByComparator obc)
		throws NoSuchOrgLaborException, SystemException {
		int count = countByOrganizationId(organizationId);
		List list = findByOrganizationId(organizationId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No OrgLabor exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "organizationId=";
			msg += organizationId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchOrgLaborException(msg);
		}
		else {
			return (com.liferay.portal.model.OrgLabor)list.get(0);
		}
	}

	public com.liferay.portal.model.OrgLabor[] findByOrganizationId_PrevAndNext(
		String orgLaborId, String organizationId, OrderByComparator obc)
		throws NoSuchOrgLaborException, SystemException {
		com.liferay.portal.model.OrgLabor orgLabor = findByPrimaryKey(orgLaborId);
		int count = countByOrganizationId(organizationId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM OrgLabor IN CLASS com.liferay.portal.service.persistence.OrgLaborHBM WHERE ");
			query.append("organizationId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("organizationId ASC").append(", ");
				query.append("typeId ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, organizationId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					orgLabor, OrgLaborHBMUtil.getInstance());
			com.liferay.portal.model.OrgLabor[] array = new com.liferay.portal.model.OrgLabor[3];
			array[0] = (com.liferay.portal.model.OrgLabor)objArray[0];
			array[1] = (com.liferay.portal.model.OrgLabor)objArray[1];
			array[2] = (com.liferay.portal.model.OrgLabor)objArray[2];

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
				"FROM OrgLabor IN CLASS com.liferay.portal.service.persistence.OrgLaborHBM ");
			query.append("ORDER BY ");
			query.append("organizationId ASC").append(", ");
			query.append("typeId ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				OrgLaborHBM orgLaborHBM = (OrgLaborHBM)itr.next();
				list.add(OrgLaborHBMUtil.model(orgLaborHBM));
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

	public void removeByOrganizationId(String organizationId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM OrgLabor IN CLASS com.liferay.portal.service.persistence.OrgLaborHBM WHERE ");
			query.append("organizationId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("organizationId ASC").append(", ");
			query.append("typeId ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, organizationId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				OrgLaborHBM orgLaborHBM = (OrgLaborHBM)itr.next();
				session.delete(orgLaborHBM);
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

	public int countByOrganizationId(String organizationId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM OrgLabor IN CLASS com.liferay.portal.service.persistence.OrgLaborHBM WHERE ");
			query.append("organizationId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, organizationId);

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

	private static Log _log = LogFactory.getLog(OrgLaborPersistence.class);
}