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

import com.liferay.portal.NoSuchOrgGroupRoleException;
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
 * <a href="OrgGroupRolePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class OrgGroupRolePersistence extends BasePersistence {
	public com.liferay.portal.model.OrgGroupRole create(
		OrgGroupRolePK orgGroupRolePK) {
		OrgGroupRoleHBM orgGroupRoleHBM = new OrgGroupRoleHBM();
		orgGroupRoleHBM.setNew(true);
		orgGroupRoleHBM.setPrimaryKey(orgGroupRolePK);

		return OrgGroupRoleHBMUtil.model(orgGroupRoleHBM);
	}

	public com.liferay.portal.model.OrgGroupRole remove(
		OrgGroupRolePK orgGroupRolePK)
		throws NoSuchOrgGroupRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrgGroupRoleHBM orgGroupRoleHBM = (OrgGroupRoleHBM)session.get(OrgGroupRoleHBM.class,
					orgGroupRolePK);

			if (orgGroupRoleHBM == null) {
				_log.warn("No OrgGroupRole exists with the primary key " +
					orgGroupRolePK.toString());
				throw new NoSuchOrgGroupRoleException(
					"No OrgGroupRole exists with the primary key " +
					orgGroupRolePK.toString());
			}

			session.delete(orgGroupRoleHBM);
			session.flush();

			return OrgGroupRoleHBMUtil.model(orgGroupRoleHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.OrgGroupRole update(
		com.liferay.portal.model.OrgGroupRole orgGroupRole)
		throws SystemException {
		Session session = null;

		try {
			if (orgGroupRole.isNew() || orgGroupRole.isModified()) {
				session = openSession();

				if (orgGroupRole.isNew()) {
					OrgGroupRoleHBM orgGroupRoleHBM = new OrgGroupRoleHBM();
					orgGroupRoleHBM.setOrganizationId(orgGroupRole.getOrganizationId());
					orgGroupRoleHBM.setGroupId(orgGroupRole.getGroupId());
					orgGroupRoleHBM.setRoleId(orgGroupRole.getRoleId());
					session.save(orgGroupRoleHBM);
					session.flush();
				}
				else {
					OrgGroupRoleHBM orgGroupRoleHBM = (OrgGroupRoleHBM)session.get(OrgGroupRoleHBM.class,
							orgGroupRole.getPrimaryKey());

					if (orgGroupRoleHBM != null) {
						session.flush();
					}
					else {
						orgGroupRoleHBM = new OrgGroupRoleHBM();
						orgGroupRoleHBM.setOrganizationId(orgGroupRole.getOrganizationId());
						orgGroupRoleHBM.setGroupId(orgGroupRole.getGroupId());
						orgGroupRoleHBM.setRoleId(orgGroupRole.getRoleId());
						session.save(orgGroupRoleHBM);
						session.flush();
					}
				}

				orgGroupRole.setNew(false);
				orgGroupRole.setModified(false);
			}

			return orgGroupRole;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.OrgGroupRole findByPrimaryKey(
		OrgGroupRolePK orgGroupRolePK)
		throws NoSuchOrgGroupRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrgGroupRoleHBM orgGroupRoleHBM = (OrgGroupRoleHBM)session.get(OrgGroupRoleHBM.class,
					orgGroupRolePK);

			if (orgGroupRoleHBM == null) {
				_log.warn("No OrgGroupRole exists with the primary key " +
					orgGroupRolePK.toString());
				throw new NoSuchOrgGroupRoleException(
					"No OrgGroupRole exists with the primary key " +
					orgGroupRolePK.toString());
			}

			return OrgGroupRoleHBMUtil.model(orgGroupRoleHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByRoleId(String roleId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM OrgGroupRole IN CLASS com.liferay.portal.service.persistence.OrgGroupRoleHBM WHERE ");
			query.append("roleId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, roleId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				OrgGroupRoleHBM orgGroupRoleHBM = (OrgGroupRoleHBM)itr.next();
				list.add(OrgGroupRoleHBMUtil.model(orgGroupRoleHBM));
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

	public List findByRoleId(String roleId, int begin, int end)
		throws SystemException {
		return findByRoleId(roleId, begin, end, null);
	}

	public List findByRoleId(String roleId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM OrgGroupRole IN CLASS com.liferay.portal.service.persistence.OrgGroupRoleHBM WHERE ");
			query.append("roleId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, roleId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				OrgGroupRoleHBM orgGroupRoleHBM = (OrgGroupRoleHBM)itr.next();
				list.add(OrgGroupRoleHBMUtil.model(orgGroupRoleHBM));
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

	public com.liferay.portal.model.OrgGroupRole findByRoleId_First(
		String roleId, OrderByComparator obc)
		throws NoSuchOrgGroupRoleException, SystemException {
		List list = findByRoleId(roleId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No OrgGroupRole exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "roleId=";
			msg += roleId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchOrgGroupRoleException(msg);
		}
		else {
			return (com.liferay.portal.model.OrgGroupRole)list.get(0);
		}
	}

	public com.liferay.portal.model.OrgGroupRole findByRoleId_Last(
		String roleId, OrderByComparator obc)
		throws NoSuchOrgGroupRoleException, SystemException {
		int count = countByRoleId(roleId);
		List list = findByRoleId(roleId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No OrgGroupRole exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "roleId=";
			msg += roleId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchOrgGroupRoleException(msg);
		}
		else {
			return (com.liferay.portal.model.OrgGroupRole)list.get(0);
		}
	}

	public com.liferay.portal.model.OrgGroupRole[] findByRoleId_PrevAndNext(
		OrgGroupRolePK orgGroupRolePK, String roleId, OrderByComparator obc)
		throws NoSuchOrgGroupRoleException, SystemException {
		com.liferay.portal.model.OrgGroupRole orgGroupRole = findByPrimaryKey(orgGroupRolePK);
		int count = countByRoleId(roleId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM OrgGroupRole IN CLASS com.liferay.portal.service.persistence.OrgGroupRoleHBM WHERE ");
			query.append("roleId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, roleId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					orgGroupRole, OrgGroupRoleHBMUtil.getInstance());
			com.liferay.portal.model.OrgGroupRole[] array = new com.liferay.portal.model.OrgGroupRole[3];
			array[0] = (com.liferay.portal.model.OrgGroupRole)objArray[0];
			array[1] = (com.liferay.portal.model.OrgGroupRole)objArray[1];
			array[2] = (com.liferay.portal.model.OrgGroupRole)objArray[2];

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
				"FROM OrgGroupRole IN CLASS com.liferay.portal.service.persistence.OrgGroupRoleHBM ");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				OrgGroupRoleHBM orgGroupRoleHBM = (OrgGroupRoleHBM)itr.next();
				list.add(OrgGroupRoleHBMUtil.model(orgGroupRoleHBM));
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

	public void removeByRoleId(String roleId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM OrgGroupRole IN CLASS com.liferay.portal.service.persistence.OrgGroupRoleHBM WHERE ");
			query.append("roleId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, roleId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				OrgGroupRoleHBM orgGroupRoleHBM = (OrgGroupRoleHBM)itr.next();
				session.delete(orgGroupRoleHBM);
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

	public int countByRoleId(String roleId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM OrgGroupRole IN CLASS com.liferay.portal.service.persistence.OrgGroupRoleHBM WHERE ");
			query.append("roleId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, roleId);

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

	private static Log _log = LogFactory.getLog(OrgGroupRolePersistence.class);
}