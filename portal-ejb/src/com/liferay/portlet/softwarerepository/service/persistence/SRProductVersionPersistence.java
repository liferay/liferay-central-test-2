/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarerepository.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.HibernateUtil;

import com.liferay.portlet.softwarerepository.NoSuchProductVersionException;
import com.liferay.portlet.softwarerepository.model.SRProductVersion;
import com.liferay.portlet.softwarerepository.model.impl.SRProductVersionImpl;

import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import org.springframework.dao.DataAccessException;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="SRProductVersionPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SRProductVersionPersistence extends BasePersistence {
	public SRProductVersion create(long productVersionId) {
		SRProductVersion srProductVersion = new SRProductVersionImpl();
		srProductVersion.setNew(true);
		srProductVersion.setPrimaryKey(productVersionId);

		return srProductVersion;
	}

	public SRProductVersion remove(long productVersionId)
		throws NoSuchProductVersionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SRProductVersion srProductVersion = (SRProductVersion)session.get(SRProductVersionImpl.class,
					new Long(productVersionId));

			if (srProductVersion == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No SRProductVersion exists with the primary key " +
						productVersionId);
				}

				throw new NoSuchProductVersionException(
					"No SRProductVersion exists with the primary key " +
					productVersionId);
			}

			return remove(srProductVersion);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public SRProductVersion remove(SRProductVersion srProductVersion)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(srProductVersion);
			session.flush();
			clearSRFrameworkVersions.clear(srProductVersion.getPrimaryKey());

			return srProductVersion;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.softwarerepository.model.SRProductVersion update(
		com.liferay.portlet.softwarerepository.model.SRProductVersion srProductVersion)
		throws SystemException {
		return update(srProductVersion, false);
	}

	public com.liferay.portlet.softwarerepository.model.SRProductVersion update(
		com.liferay.portlet.softwarerepository.model.SRProductVersion srProductVersion,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(srProductVersion);
			}
			else {
				if (srProductVersion.isNew()) {
					session.save(srProductVersion);
				}
			}

			session.flush();
			srProductVersion.setNew(false);

			return srProductVersion;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public SRProductVersion findByPrimaryKey(long productVersionId)
		throws NoSuchProductVersionException, SystemException {
		SRProductVersion srProductVersion = fetchByPrimaryKey(productVersionId);

		if (srProductVersion == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No SRProductVersion exists with the primary key " +
					productVersionId);
			}

			throw new NoSuchProductVersionException(
				"No SRProductVersion exists with the primary key " +
				productVersionId);
		}

		return srProductVersion;
	}

	public SRProductVersion fetchByPrimaryKey(long productVersionId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (SRProductVersion)session.get(SRProductVersionImpl.class,
				new Long(productVersionId));
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByProductEntryId(long productEntryId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRProductVersion WHERE ");
			query.append("productEntryId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate DESC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, productEntryId);

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByProductEntryId(long productEntryId, int begin, int end)
		throws SystemException {
		return findByProductEntryId(productEntryId, begin, end, null);
	}

	public List findByProductEntryId(long productEntryId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRProductVersion WHERE ");
			query.append("productEntryId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, productEntryId);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public SRProductVersion findByProductEntryId_First(long productEntryId,
		OrderByComparator obc)
		throws NoSuchProductVersionException, SystemException {
		List list = findByProductEntryId(productEntryId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No SRProductVersion exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "productEntryId=";
			msg += productEntryId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchProductVersionException(msg);
		}
		else {
			return (SRProductVersion)list.get(0);
		}
	}

	public SRProductVersion findByProductEntryId_Last(long productEntryId,
		OrderByComparator obc)
		throws NoSuchProductVersionException, SystemException {
		int count = countByProductEntryId(productEntryId);
		List list = findByProductEntryId(productEntryId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No SRProductVersion exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "productEntryId=";
			msg += productEntryId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchProductVersionException(msg);
		}
		else {
			return (SRProductVersion)list.get(0);
		}
	}

	public SRProductVersion[] findByProductEntryId_PrevAndNext(
		long productVersionId, long productEntryId, OrderByComparator obc)
		throws NoSuchProductVersionException, SystemException {
		SRProductVersion srProductVersion = findByPrimaryKey(productVersionId);
		int count = countByProductEntryId(productEntryId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRProductVersion WHERE ");
			query.append("productEntryId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, productEntryId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					srProductVersion);
			SRProductVersion[] array = new SRProductVersionImpl[3];
			array[0] = (SRProductVersion)objArray[0];
			array[1] = (SRProductVersion)objArray[1];
			array[2] = (SRProductVersion)objArray[2];

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
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List findAll(int begin, int end) throws SystemException {
		return findAll(begin, end, null);
	}

	public List findAll(int begin, int end, OrderByComparator obc)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRProductVersion ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByProductEntryId(long productEntryId)
		throws SystemException {
		Iterator itr = findByProductEntryId(productEntryId).iterator();

		while (itr.hasNext()) {
			SRProductVersion srProductVersion = (SRProductVersion)itr.next();
			remove(srProductVersion);
		}
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((SRProductVersion)itr.next());
		}
	}

	public int countByProductEntryId(long productEntryId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRProductVersion WHERE ");
			query.append("productEntryId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, productEntryId);

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

	public int countAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRProductVersion");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

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

	public List getSRFrameworkVersions(long pk)
		throws NoSuchProductVersionException, SystemException {
		return getSRFrameworkVersions(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List getSRFrameworkVersions(long pk, int begin, int end)
		throws NoSuchProductVersionException, SystemException {
		return getSRFrameworkVersions(pk, begin, end, null);
	}

	public List getSRFrameworkVersions(long pk, int begin, int end,
		OrderByComparator obc)
		throws NoSuchProductVersionException, SystemException {
		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = _SQL_GETSRFRAMEWORKVERSIONS;

			if (obc != null) {
				sql += ("ORDER BY " + obc.getOrderBy());
			}
			else {
				sql += "ORDER BY ";
				sql += "SRFrameworkVersion.priority ASC";
				sql += ", ";
				sql += "SRFrameworkVersion.name ASC";
			}

			SQLQuery q = session.createSQLQuery(sql);
			q.setCacheable(false);
			q.addEntity("SRFrameworkVersion",
				com.liferay.portlet.softwarerepository.model.impl.SRFrameworkVersionImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);

			return QueryUtil.list(q, HibernateUtil.getDialect(), begin, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public int getSRFrameworkVersionsSize(long pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(_SQL_GETSRFRAMEWORKVERSIONSSIZE);
			q.setCacheable(false);
			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);

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

	public boolean containsSRFrameworkVersion(long pk, long srFrameworkVersionPK)
		throws SystemException {
		try {
			return containsSRFrameworkVersion.contains(pk, srFrameworkVersionPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public boolean containsSRFrameworkVersions(long pk)
		throws SystemException {
		if (getSRFrameworkVersionsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addSRFrameworkVersion(long pk, long srFrameworkVersionPK)
		throws NoSuchProductVersionException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException, 
			SystemException {
		try {
			addSRFrameworkVersion.add(pk, srFrameworkVersionPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addSRFrameworkVersion(long pk,
		com.liferay.portlet.softwarerepository.model.SRFrameworkVersion srFrameworkVersion)
		throws NoSuchProductVersionException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException, 
			SystemException {
		try {
			addSRFrameworkVersion.add(pk, srFrameworkVersion.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addSRFrameworkVersions(long pk, long[] srFrameworkVersionPKs)
		throws NoSuchProductVersionException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException, 
			SystemException {
		try {
			for (int i = 0; i < srFrameworkVersionPKs.length; i++) {
				addSRFrameworkVersion.add(pk, srFrameworkVersionPKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addSRFrameworkVersions(long pk, List srFrameworkVersions)
		throws NoSuchProductVersionException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException, 
			SystemException {
		try {
			for (int i = 0; i < srFrameworkVersions.size(); i++) {
				com.liferay.portlet.softwarerepository.model.SRFrameworkVersion srFrameworkVersion =
					(com.liferay.portlet.softwarerepository.model.SRFrameworkVersion)srFrameworkVersions.get(i);
				addSRFrameworkVersion.add(pk, srFrameworkVersion.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void clearSRFrameworkVersions(long pk)
		throws NoSuchProductVersionException, SystemException {
		try {
			clearSRFrameworkVersions.clear(pk);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeSRFrameworkVersion(long pk, long srFrameworkVersionPK)
		throws NoSuchProductVersionException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException, 
			SystemException {
		try {
			removeSRFrameworkVersion.remove(pk, srFrameworkVersionPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeSRFrameworkVersion(long pk,
		com.liferay.portlet.softwarerepository.model.SRFrameworkVersion srFrameworkVersion)
		throws NoSuchProductVersionException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException, 
			SystemException {
		try {
			removeSRFrameworkVersion.remove(pk,
				srFrameworkVersion.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeSRFrameworkVersions(long pk, long[] srFrameworkVersionPKs)
		throws NoSuchProductVersionException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException, 
			SystemException {
		try {
			for (int i = 0; i < srFrameworkVersionPKs.length; i++) {
				removeSRFrameworkVersion.remove(pk, srFrameworkVersionPKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeSRFrameworkVersions(long pk, List srFrameworkVersions)
		throws NoSuchProductVersionException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException, 
			SystemException {
		try {
			for (int i = 0; i < srFrameworkVersions.size(); i++) {
				com.liferay.portlet.softwarerepository.model.SRFrameworkVersion srFrameworkVersion =
					(com.liferay.portlet.softwarerepository.model.SRFrameworkVersion)srFrameworkVersions.get(i);
				removeSRFrameworkVersion.remove(pk,
					srFrameworkVersion.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void setSRFrameworkVersions(long pk, long[] srFrameworkVersionPKs)
		throws NoSuchProductVersionException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException, 
			SystemException {
		try {
			clearSRFrameworkVersions.clear(pk);

			for (int i = 0; i < srFrameworkVersionPKs.length; i++) {
				addSRFrameworkVersion.add(pk, srFrameworkVersionPKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void setSRFrameworkVersions(long pk, List srFrameworkVersions)
		throws NoSuchProductVersionException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException, 
			SystemException {
		try {
			clearSRFrameworkVersions.clear(pk);

			for (int i = 0; i < srFrameworkVersions.size(); i++) {
				com.liferay.portlet.softwarerepository.model.SRFrameworkVersion srFrameworkVersion =
					(com.liferay.portlet.softwarerepository.model.SRFrameworkVersion)srFrameworkVersions.get(i);
				addSRFrameworkVersion.add(pk, srFrameworkVersion.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	protected void initDao() {
		containsSRFrameworkVersion = new ContainsSRFrameworkVersion(this);
		addSRFrameworkVersion = new AddSRFrameworkVersion(this);
		clearSRFrameworkVersions = new ClearSRFrameworkVersions(this);
		removeSRFrameworkVersion = new RemoveSRFrameworkVersion(this);
	}

	protected ContainsSRFrameworkVersion containsSRFrameworkVersion;
	protected AddSRFrameworkVersion addSRFrameworkVersion;
	protected ClearSRFrameworkVersions clearSRFrameworkVersions;
	protected RemoveSRFrameworkVersion removeSRFrameworkVersion;

	protected class ContainsSRFrameworkVersion extends MappingSqlQuery {
		protected ContainsSRFrameworkVersion(
			SRProductVersionPersistence persistence) {
			super(persistence.getDataSource(), _SQL_CONTAINSSRFRAMEWORKVERSION);
			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNumber)
			throws SQLException {
			return new Integer(rs.getInt("COUNT_VALUE"));
		}

		protected boolean contains(long productVersionId,
			long frameworkVersionId) {
			List results = execute(new Object[] {
						new Long(productVersionId), new Long(frameworkVersionId)
					});

			if (results.size() > 0) {
				Integer count = (Integer)results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}
	}

	protected class AddSRFrameworkVersion extends SqlUpdate {
		protected AddSRFrameworkVersion(SRProductVersionPersistence persistence) {
			super(persistence.getDataSource(),
				"INSERT INTO SRFrameworkVersions_SRProductVersions (productVersionId, frameworkVersionId) VALUES (?, ?)");
			_persistence = persistence;
			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));
			compile();
		}

		protected void add(long productVersionId, long frameworkVersionId) {
			if (!_persistence.containsSRFrameworkVersion.contains(
						productVersionId, frameworkVersionId)) {
				update(new Object[] {
						new Long(productVersionId), new Long(frameworkVersionId)
					});
			}
		}

		private SRProductVersionPersistence _persistence;
	}

	protected class ClearSRFrameworkVersions extends SqlUpdate {
		protected ClearSRFrameworkVersions(
			SRProductVersionPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM SRFrameworkVersions_SRProductVersions WHERE productVersionId = ?");
			declareParameter(new SqlParameter(Types.BIGINT));
			compile();
		}

		protected void clear(long productVersionId) {
			update(new Object[] { new Long(productVersionId) });
		}
	}

	protected class RemoveSRFrameworkVersion extends SqlUpdate {
		protected RemoveSRFrameworkVersion(
			SRProductVersionPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM SRFrameworkVersions_SRProductVersions WHERE productVersionId = ? AND frameworkVersionId = ?");
			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));
			compile();
		}

		protected void remove(long productVersionId, long frameworkVersionId) {
			update(new Object[] {
					new Long(productVersionId), new Long(frameworkVersionId)
				});
		}
	}

	private static final String _SQL_GETSRFRAMEWORKVERSIONS = "SELECT {SRFrameworkVersion.*} FROM SRFrameworkVersion INNER JOIN SRFrameworkVersions_SRProductVersions ON (SRFrameworkVersions_SRProductVersions.frameworkVersionId = SRFrameworkVersion.frameworkVersionId) WHERE (SRFrameworkVersions_SRProductVersions.productVersionId = ?)";
	private static final String _SQL_GETSRFRAMEWORKVERSIONSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM SRFrameworkVersions_SRProductVersions WHERE productVersionId = ?";
	private static final String _SQL_CONTAINSSRFRAMEWORKVERSION = "SELECT COUNT(*) AS COUNT_VALUE FROM SRFrameworkVersions_SRProductVersions WHERE productVersionId = ? AND frameworkVersionId = ?";
	private static Log _log = LogFactory.getLog(SRProductVersionPersistence.class);
}