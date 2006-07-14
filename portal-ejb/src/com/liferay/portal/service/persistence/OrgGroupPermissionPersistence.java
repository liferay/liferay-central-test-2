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

import com.liferay.portal.NoSuchOrgGroupPermissionException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.OrgGroupPermission;
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
 * <a href="OrgGroupPermissionPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class OrgGroupPermissionPersistence extends BasePersistence {
	public OrgGroupPermission create(OrgGroupPermissionPK orgGroupPermissionPK) {
		OrgGroupPermission orgGroupPermission = new OrgGroupPermission();
		orgGroupPermission.setNew(true);
		orgGroupPermission.setPrimaryKey(orgGroupPermissionPK);

		return orgGroupPermission;
	}

	public OrgGroupPermission remove(OrgGroupPermissionPK orgGroupPermissionPK)
		throws NoSuchOrgGroupPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrgGroupPermission orgGroupPermission = (OrgGroupPermission)session.get(OrgGroupPermission.class,
					orgGroupPermissionPK);

			if (orgGroupPermission == null) {
				_log.warn("No OrgGroupPermission exists with the primary key " +
					orgGroupPermissionPK.toString());
				throw new NoSuchOrgGroupPermissionException(
					"No OrgGroupPermission exists with the primary key " +
					orgGroupPermissionPK.toString());
			}

			session.delete(orgGroupPermission);
			session.flush();

			return orgGroupPermission;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.OrgGroupPermission update(
		com.liferay.portal.model.OrgGroupPermission orgGroupPermission)
		throws SystemException {
		Session session = null;

		try {
			if (orgGroupPermission.isNew() || orgGroupPermission.isModified()) {
				session = openSession();

				if (orgGroupPermission.isNew()) {
					OrgGroupPermission orgGroupPermissionModel = new OrgGroupPermission();
					orgGroupPermissionModel.setOrganizationId(orgGroupPermission.getOrganizationId());
					orgGroupPermissionModel.setGroupId(orgGroupPermission.getGroupId());
					orgGroupPermissionModel.setPermissionId(orgGroupPermission.getPermissionId());
					session.save(orgGroupPermissionModel);
					session.flush();
				}
				else {
					OrgGroupPermission orgGroupPermissionModel = (OrgGroupPermission)session.get(OrgGroupPermission.class,
							orgGroupPermission.getPrimaryKey());

					if (orgGroupPermissionModel != null) {
						session.flush();
					}
					else {
						orgGroupPermissionModel = new OrgGroupPermission();
						orgGroupPermissionModel.setOrganizationId(orgGroupPermission.getOrganizationId());
						orgGroupPermissionModel.setGroupId(orgGroupPermission.getGroupId());
						orgGroupPermissionModel.setPermissionId(orgGroupPermission.getPermissionId());
						session.save(orgGroupPermissionModel);
						session.flush();
					}
				}

				orgGroupPermission.setNew(false);
				orgGroupPermission.setModified(false);
			}

			return orgGroupPermission;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public OrgGroupPermission findByPrimaryKey(
		OrgGroupPermissionPK orgGroupPermissionPK)
		throws NoSuchOrgGroupPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrgGroupPermission orgGroupPermission = (OrgGroupPermission)session.get(OrgGroupPermission.class,
					orgGroupPermissionPK);

			if (orgGroupPermission == null) {
				_log.warn("No OrgGroupPermission exists with the primary key " +
					orgGroupPermissionPK.toString());
				throw new NoSuchOrgGroupPermissionException(
					"No OrgGroupPermission exists with the primary key " +
					orgGroupPermissionPK.toString());
			}

			return orgGroupPermission;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByPermissionId(String permissionId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portal.model.OrgGroupPermission WHERE ");

			if (permissionId == null) {
				query.append("permissionId IS NULL");
			}
			else {
				query.append("permissionId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (permissionId != null) {
				q.setString(queryPos++, permissionId);
			}

			List list = q.list();

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByPermissionId(String permissionId, int begin, int end)
		throws SystemException {
		return findByPermissionId(permissionId, begin, end, null);
	}

	public List findByPermissionId(String permissionId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portal.model.OrgGroupPermission WHERE ");

			if (permissionId == null) {
				query.append("permissionId IS NULL");
			}
			else {
				query.append("permissionId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (permissionId != null) {
				q.setString(queryPos++, permissionId);
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

	public OrgGroupPermission findByPermissionId_First(String permissionId,
		OrderByComparator obc)
		throws NoSuchOrgGroupPermissionException, SystemException {
		List list = findByPermissionId(permissionId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No OrgGroupPermission exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "permissionId=";
			msg += permissionId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchOrgGroupPermissionException(msg);
		}
		else {
			return (OrgGroupPermission)list.get(0);
		}
	}

	public OrgGroupPermission findByPermissionId_Last(String permissionId,
		OrderByComparator obc)
		throws NoSuchOrgGroupPermissionException, SystemException {
		int count = countByPermissionId(permissionId);
		List list = findByPermissionId(permissionId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No OrgGroupPermission exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "permissionId=";
			msg += permissionId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchOrgGroupPermissionException(msg);
		}
		else {
			return (OrgGroupPermission)list.get(0);
		}
	}

	public OrgGroupPermission[] findByPermissionId_PrevAndNext(
		OrgGroupPermissionPK orgGroupPermissionPK, String permissionId,
		OrderByComparator obc)
		throws NoSuchOrgGroupPermissionException, SystemException {
		OrgGroupPermission orgGroupPermission = findByPrimaryKey(orgGroupPermissionPK);
		int count = countByPermissionId(permissionId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portal.model.OrgGroupPermission WHERE ");

			if (permissionId == null) {
				query.append("permissionId IS NULL");
			}
			else {
				query.append("permissionId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (permissionId != null) {
				q.setString(queryPos++, permissionId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					orgGroupPermission);
			OrgGroupPermission[] array = new OrgGroupPermission[3];
			array[0] = (OrgGroupPermission)objArray[0];
			array[1] = (OrgGroupPermission)objArray[1];
			array[2] = (OrgGroupPermission)objArray[2];

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
			query.append("FROM com.liferay.portal.model.OrgGroupPermission ");

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

	public void removeByPermissionId(String permissionId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portal.model.OrgGroupPermission WHERE ");

			if (permissionId == null) {
				query.append("permissionId IS NULL");
			}
			else {
				query.append("permissionId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (permissionId != null) {
				q.setString(queryPos++, permissionId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				OrgGroupPermission orgGroupPermission = (OrgGroupPermission)itr.next();
				session.delete(orgGroupPermission);
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

	public int countByPermissionId(String permissionId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portal.model.OrgGroupPermission WHERE ");

			if (permissionId == null) {
				query.append("permissionId IS NULL");
			}
			else {
				query.append("permissionId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (permissionId != null) {
				q.setString(queryPos++, permissionId);
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

	private static Log _log = LogFactory.getLog(OrgGroupPermissionPersistence.class);
}