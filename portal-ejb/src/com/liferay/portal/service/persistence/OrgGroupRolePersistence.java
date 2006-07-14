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
import com.liferay.portal.model.OrgGroupRole;
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
	public OrgGroupRole create(OrgGroupRolePK orgGroupRolePK) {
		OrgGroupRole orgGroupRole = new OrgGroupRole();
		orgGroupRole.setNew(true);
		orgGroupRole.setPrimaryKey(orgGroupRolePK);

		return orgGroupRole;
	}

	public OrgGroupRole remove(OrgGroupRolePK orgGroupRolePK)
		throws NoSuchOrgGroupRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrgGroupRole orgGroupRole = (OrgGroupRole)session.get(OrgGroupRole.class,
					orgGroupRolePK);

			if (orgGroupRole == null) {
				_log.warn("No OrgGroupRole exists with the primary key " +
					orgGroupRolePK.toString());
				throw new NoSuchOrgGroupRoleException(
					"No OrgGroupRole exists with the primary key " +
					orgGroupRolePK.toString());
			}

			session.delete(orgGroupRole);
			session.flush();

			return orgGroupRole;
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
					OrgGroupRole orgGroupRoleModel = new OrgGroupRole();
					orgGroupRoleModel.setOrganizationId(orgGroupRole.getOrganizationId());
					orgGroupRoleModel.setGroupId(orgGroupRole.getGroupId());
					orgGroupRoleModel.setRoleId(orgGroupRole.getRoleId());
					session.save(orgGroupRoleModel);
					session.flush();
				}
				else {
					OrgGroupRole orgGroupRoleModel = (OrgGroupRole)session.get(OrgGroupRole.class,
							orgGroupRole.getPrimaryKey());

					if (orgGroupRoleModel != null) {
						session.flush();
					}
					else {
						orgGroupRoleModel = new OrgGroupRole();
						orgGroupRoleModel.setOrganizationId(orgGroupRole.getOrganizationId());
						orgGroupRoleModel.setGroupId(orgGroupRole.getGroupId());
						orgGroupRoleModel.setRoleId(orgGroupRole.getRoleId());
						session.save(orgGroupRoleModel);
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

	public OrgGroupRole findByPrimaryKey(OrgGroupRolePK orgGroupRolePK)
		throws NoSuchOrgGroupRoleException, SystemException {
		return findByPrimaryKey(orgGroupRolePK, true);
	}

	public OrgGroupRole findByPrimaryKey(OrgGroupRolePK orgGroupRolePK,
		boolean throwNoSuchObjectException)
		throws NoSuchOrgGroupRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrgGroupRole orgGroupRole = (OrgGroupRole)session.get(OrgGroupRole.class,
					orgGroupRolePK);

			if (orgGroupRole == null) {
				_log.warn("No OrgGroupRole exists with the primary key " +
					orgGroupRolePK.toString());

				if (throwNoSuchObjectException) {
					throw new NoSuchOrgGroupRoleException(
						"No OrgGroupRole exists with the primary key " +
						orgGroupRolePK.toString());
				}
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

	public List findByRoleId(String roleId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.OrgGroupRole WHERE ");

			if (roleId == null) {
				query.append("roleId IS NULL");
			}
			else {
				query.append("roleId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (roleId != null) {
				q.setString(queryPos++, roleId);
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
			query.append("FROM com.liferay.portal.model.OrgGroupRole WHERE ");

			if (roleId == null) {
				query.append("roleId IS NULL");
			}
			else {
				query.append("roleId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (roleId != null) {
				q.setString(queryPos++, roleId);
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

	public OrgGroupRole findByRoleId_First(String roleId, OrderByComparator obc)
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
			return (OrgGroupRole)list.get(0);
		}
	}

	public OrgGroupRole findByRoleId_Last(String roleId, OrderByComparator obc)
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
			return (OrgGroupRole)list.get(0);
		}
	}

	public OrgGroupRole[] findByRoleId_PrevAndNext(
		OrgGroupRolePK orgGroupRolePK, String roleId, OrderByComparator obc)
		throws NoSuchOrgGroupRoleException, SystemException {
		OrgGroupRole orgGroupRole = findByPrimaryKey(orgGroupRolePK);
		int count = countByRoleId(roleId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.OrgGroupRole WHERE ");

			if (roleId == null) {
				query.append("roleId IS NULL");
			}
			else {
				query.append("roleId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (roleId != null) {
				q.setString(queryPos++, roleId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					orgGroupRole);
			OrgGroupRole[] array = new OrgGroupRole[3];
			array[0] = (OrgGroupRole)objArray[0];
			array[1] = (OrgGroupRole)objArray[1];
			array[2] = (OrgGroupRole)objArray[2];

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
			query.append("FROM com.liferay.portal.model.OrgGroupRole ");

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

	public void removeByRoleId(String roleId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.OrgGroupRole WHERE ");

			if (roleId == null) {
				query.append("roleId IS NULL");
			}
			else {
				query.append("roleId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (roleId != null) {
				q.setString(queryPos++, roleId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				OrgGroupRole orgGroupRole = (OrgGroupRole)itr.next();
				session.delete(orgGroupRole);
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
			query.append("FROM com.liferay.portal.model.OrgGroupRole WHERE ");

			if (roleId == null) {
				query.append("roleId IS NULL");
			}
			else {
				query.append("roleId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (roleId != null) {
				q.setString(queryPos++, roleId);
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

	private static Log _log = LogFactory.getLog(OrgGroupRolePersistence.class);
}