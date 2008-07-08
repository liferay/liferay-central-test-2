/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarecatalog.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.softwarecatalog.NoSuchLicenseException;
import com.liferay.portlet.softwarecatalog.model.SCLicense;
import com.liferay.portlet.softwarecatalog.model.impl.SCLicenseImpl;
import com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.dao.DataAccessException;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="SCLicensePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SCLicensePersistenceImpl extends BasePersistenceImpl
	implements SCLicensePersistence {
	public SCLicense create(long licenseId) {
		SCLicense scLicense = new SCLicenseImpl();

		scLicense.setNew(true);
		scLicense.setPrimaryKey(licenseId);

		return scLicense;
	}

	public SCLicense remove(long licenseId)
		throws NoSuchLicenseException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SCLicense scLicense = (SCLicense)session.get(SCLicenseImpl.class,
					new Long(licenseId));

			if (scLicense == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No SCLicense exists with the primary key " +
						licenseId);
				}

				throw new NoSuchLicenseException(
					"No SCLicense exists with the primary key " + licenseId);
			}

			return remove(scLicense);
		}
		catch (NoSuchLicenseException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCLicense remove(SCLicense scLicense) throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(scLicense);
			}
		}

		scLicense = removeImpl(scLicense);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(scLicense);
			}
		}

		return scLicense;
	}

	protected SCLicense removeImpl(SCLicense scLicense)
		throws SystemException {
		try {
			clearSCProductEntries.clear(scLicense.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCLicenses_SCProductEntries");
		}

		Session session = null;

		try {
			session = openSession();

			session.delete(scLicense);

			session.flush();

			return scLicense;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(SCLicense.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(SCLicense scLicense, boolean merge)</code>.
	 */
	public SCLicense update(SCLicense scLicense) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(SCLicense scLicense) method. Use update(SCLicense scLicense, boolean merge) instead.");
		}

		return update(scLicense, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        scLicense the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when scLicense is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public SCLicense update(SCLicense scLicense, boolean merge)
		throws SystemException {
		boolean isNew = scLicense.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(scLicense);
				}
				else {
					listener.onBeforeUpdate(scLicense);
				}
			}
		}

		scLicense = updateImpl(scLicense, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(scLicense);
				}
				else {
					listener.onAfterUpdate(scLicense);
				}
			}
		}

		return scLicense;
	}

	public SCLicense updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense,
		boolean merge) throws SystemException {
		FinderCacheUtil.clearCache("SCLicenses_SCProductEntries");

		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(scLicense);
			}
			else {
				if (scLicense.isNew()) {
					session.save(scLicense);
				}
			}

			session.flush();

			scLicense.setNew(false);

			return scLicense;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(SCLicense.class.getName());
		}
	}

	public SCLicense findByPrimaryKey(long licenseId)
		throws NoSuchLicenseException, SystemException {
		SCLicense scLicense = fetchByPrimaryKey(licenseId);

		if (scLicense == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No SCLicense exists with the primary key " +
					licenseId);
			}

			throw new NoSuchLicenseException(
				"No SCLicense exists with the primary key " + licenseId);
		}

		return scLicense;
	}

	public SCLicense fetchByPrimaryKey(long licenseId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (SCLicense)session.get(SCLicenseImpl.class,
				new Long(licenseId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SCLicense> findByActive(boolean active)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SCLicenseModelImpl.CACHE_ENABLED;
		String finderClassName = SCLicense.class.getName();
		String finderMethodName = "findByActive";
		String[] finderParams = new String[] { Boolean.class.getName() };
		Object[] finderArgs = new Object[] { Boolean.valueOf(active) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCLicense WHERE ");

				query.append("active_ = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				List<SCLicense> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<SCLicense>)result;
		}
	}

	public List<SCLicense> findByActive(boolean active, int start, int end)
		throws SystemException {
		return findByActive(active, start, end, null);
	}

	public List<SCLicense> findByActive(boolean active, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SCLicenseModelImpl.CACHE_ENABLED;
		String finderClassName = SCLicense.class.getName();
		String finderMethodName = "findByActive";
		String[] finderParams = new String[] {
				Boolean.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				Boolean.valueOf(active),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCLicense WHERE ");

				query.append("active_ = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				List<SCLicense> list = (List<SCLicense>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<SCLicense>)result;
		}
	}

	public SCLicense findByActive_First(boolean active, OrderByComparator obc)
		throws NoSuchLicenseException, SystemException {
		List<SCLicense> list = findByActive(active, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCLicense exists with the key {");

			msg.append("active=" + active);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLicenseException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCLicense findByActive_Last(boolean active, OrderByComparator obc)
		throws NoSuchLicenseException, SystemException {
		int count = countByActive(active);

		List<SCLicense> list = findByActive(active, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCLicense exists with the key {");

			msg.append("active=" + active);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLicenseException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCLicense[] findByActive_PrevAndNext(long licenseId, boolean active,
		OrderByComparator obc) throws NoSuchLicenseException, SystemException {
		SCLicense scLicense = findByPrimaryKey(licenseId);

		int count = countByActive(active);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCLicense WHERE ");

			query.append("active_ = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(active);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					scLicense);

			SCLicense[] array = new SCLicenseImpl[3];

			array[0] = (SCLicense)objArray[0];
			array[1] = (SCLicense)objArray[1];
			array[2] = (SCLicense)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SCLicense> findByA_R(boolean active, boolean recommended)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SCLicenseModelImpl.CACHE_ENABLED;
		String finderClassName = SCLicense.class.getName();
		String finderMethodName = "findByA_R";
		String[] finderParams = new String[] {
				Boolean.class.getName(), Boolean.class.getName()
			};
		Object[] finderArgs = new Object[] {
				Boolean.valueOf(active), Boolean.valueOf(recommended)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCLicense WHERE ");

				query.append("active_ = ?");

				query.append(" AND ");

				query.append("recommended = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				qPos.add(recommended);

				List<SCLicense> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<SCLicense>)result;
		}
	}

	public List<SCLicense> findByA_R(boolean active, boolean recommended,
		int start, int end) throws SystemException {
		return findByA_R(active, recommended, start, end, null);
	}

	public List<SCLicense> findByA_R(boolean active, boolean recommended,
		int start, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SCLicenseModelImpl.CACHE_ENABLED;
		String finderClassName = SCLicense.class.getName();
		String finderMethodName = "findByA_R";
		String[] finderParams = new String[] {
				Boolean.class.getName(), Boolean.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				Boolean.valueOf(active), Boolean.valueOf(recommended),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCLicense WHERE ");

				query.append("active_ = ?");

				query.append(" AND ");

				query.append("recommended = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				qPos.add(recommended);

				List<SCLicense> list = (List<SCLicense>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<SCLicense>)result;
		}
	}

	public SCLicense findByA_R_First(boolean active, boolean recommended,
		OrderByComparator obc) throws NoSuchLicenseException, SystemException {
		List<SCLicense> list = findByA_R(active, recommended, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCLicense exists with the key {");

			msg.append("active=" + active);

			msg.append(", ");
			msg.append("recommended=" + recommended);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLicenseException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCLicense findByA_R_Last(boolean active, boolean recommended,
		OrderByComparator obc) throws NoSuchLicenseException, SystemException {
		int count = countByA_R(active, recommended);

		List<SCLicense> list = findByA_R(active, recommended, count - 1, count,
				obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCLicense exists with the key {");

			msg.append("active=" + active);

			msg.append(", ");
			msg.append("recommended=" + recommended);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLicenseException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCLicense[] findByA_R_PrevAndNext(long licenseId, boolean active,
		boolean recommended, OrderByComparator obc)
		throws NoSuchLicenseException, SystemException {
		SCLicense scLicense = findByPrimaryKey(licenseId);

		int count = countByA_R(active, recommended);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCLicense WHERE ");

			query.append("active_ = ?");

			query.append(" AND ");

			query.append("recommended = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(active);

			qPos.add(recommended);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					scLicense);

			SCLicense[] array = new SCLicenseImpl[3];

			array[0] = (SCLicense)objArray[0];
			array[1] = (SCLicense)objArray[1];
			array[2] = (SCLicense)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SCLicense> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SCLicense> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

			dynamicQuery.setLimit(start, end);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SCLicense> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SCLicense> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<SCLicense> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SCLicenseModelImpl.CACHE_ENABLED;
		String finderClassName = SCLicense.class.getName();
		String finderMethodName = "findAll";
		String[] finderParams = new String[] {
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCLicense ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("name ASC");
				}

				Query q = session.createQuery(query.toString());

				List<SCLicense> list = (List<SCLicense>)QueryUtil.list(q,
						getDialect(), start, end);

				if (obc == null) {
					Collections.sort(list);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<SCLicense>)result;
		}
	}

	public void removeByActive(boolean active) throws SystemException {
		for (SCLicense scLicense : findByActive(active)) {
			remove(scLicense);
		}
	}

	public void removeByA_R(boolean active, boolean recommended)
		throws SystemException {
		for (SCLicense scLicense : findByA_R(active, recommended)) {
			remove(scLicense);
		}
	}

	public void removeAll() throws SystemException {
		for (SCLicense scLicense : findAll()) {
			remove(scLicense);
		}
	}

	public int countByActive(boolean active) throws SystemException {
		boolean finderClassNameCacheEnabled = SCLicenseModelImpl.CACHE_ENABLED;
		String finderClassName = SCLicense.class.getName();
		String finderMethodName = "countByActive";
		String[] finderParams = new String[] { Boolean.class.getName() };
		Object[] finderArgs = new Object[] { Boolean.valueOf(active) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCLicense WHERE ");

				query.append("active_ = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByA_R(boolean active, boolean recommended)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SCLicenseModelImpl.CACHE_ENABLED;
		String finderClassName = SCLicense.class.getName();
		String finderMethodName = "countByA_R";
		String[] finderParams = new String[] {
				Boolean.class.getName(), Boolean.class.getName()
			};
		Object[] finderArgs = new Object[] {
				Boolean.valueOf(active), Boolean.valueOf(recommended)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCLicense WHERE ");

				query.append("active_ = ?");

				query.append(" AND ");

				query.append("recommended = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				qPos.add(recommended);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countAll() throws SystemException {
		boolean finderClassNameCacheEnabled = SCLicenseModelImpl.CACHE_ENABLED;
		String finderClassName = SCLicense.class.getName();
		String finderMethodName = "countAll";
		String[] finderParams = new String[] {  };
		Object[] finderArgs = new Object[] {  };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portlet.softwarecatalog.model.SCLicense");

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		long pk) throws SystemException {
		return getSCProductEntries(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		long pk, int start, int end) throws SystemException {
		return getSCProductEntries(pk, start, end, null);
	}

	public List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		long pk, int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SCLicenseModelImpl.CACHE_ENABLED_SCLICENSES_SCPRODUCTENTRIES;

		String finderClassName = "SCLicenses_SCProductEntries";

		String finderMethodName = "getSCProductEntries";
		String[] finderParams = new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder sb = new StringBuilder();

				sb.append(_SQL_GETSCPRODUCTENTRIES);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				else {
					sb.append("ORDER BY ");

					sb.append("SCProductEntry.modifiedDate DESC, ");
					sb.append("SCProductEntry.name DESC");
				}

				String sql = sb.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("SCProductEntry",
					com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> list =
					(List<com.liferay.portlet.softwarecatalog.model.SCProductEntry>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<com.liferay.portlet.softwarecatalog.model.SCProductEntry>)result;
		}
	}

	public int getSCProductEntriesSize(long pk) throws SystemException {
		boolean finderClassNameCacheEnabled = SCLicenseModelImpl.CACHE_ENABLED_SCLICENSES_SCPRODUCTENTRIES;

		String finderClassName = "SCLicenses_SCProductEntries";

		String finderMethodName = "getSCProductEntriesSize";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(pk) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETSCPRODUCTENTRIESSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public boolean containsSCProductEntry(long pk, long scProductEntryPK) {
		boolean finderClassNameCacheEnabled = SCLicenseModelImpl.CACHE_ENABLED_SCLICENSES_SCPRODUCTENTRIES;

		String finderClassName = "SCLicenses_SCProductEntries";

		String finderMethodName = "containsSCProductEntries";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(pk),
				
				new Long(scProductEntryPK)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Boolean value = Boolean.valueOf(containsSCProductEntry.contains(
						pk, scProductEntryPK));

			FinderCacheUtil.putResult(finderClassNameCacheEnabled,
				finderClassName, finderMethodName, finderParams, finderArgs,
				value);

			return value.booleanValue();
		}
		else {
			return ((Boolean)result).booleanValue();
		}
	}

	public boolean containsSCProductEntries(long pk) throws SystemException {
		if (getSCProductEntriesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addSCProductEntry(long pk, long scProductEntryPK)
		throws SystemException {
		try {
			addSCProductEntry.add(pk, scProductEntryPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCacheUtil.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void addSCProductEntry(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws SystemException {
		try {
			addSCProductEntry.add(pk, scProductEntry.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCacheUtil.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void addSCProductEntries(long pk, long[] scProductEntryPKs)
		throws SystemException {
		try {
			for (long scProductEntryPK : scProductEntryPKs) {
				addSCProductEntry.add(pk, scProductEntryPK);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCacheUtil.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void addSCProductEntries(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries)
		throws SystemException {
		try {
			for (com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry : scProductEntries) {
				addSCProductEntry.add(pk, scProductEntry.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCacheUtil.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void clearSCProductEntries(long pk) throws SystemException {
		try {
			clearSCProductEntries.clear(pk);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCacheUtil.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void removeSCProductEntry(long pk, long scProductEntryPK)
		throws SystemException {
		try {
			removeSCProductEntry.remove(pk, scProductEntryPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCacheUtil.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void removeSCProductEntry(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws SystemException {
		try {
			removeSCProductEntry.remove(pk, scProductEntry.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCacheUtil.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void removeSCProductEntries(long pk, long[] scProductEntryPKs)
		throws SystemException {
		try {
			for (long scProductEntryPK : scProductEntryPKs) {
				removeSCProductEntry.remove(pk, scProductEntryPK);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCacheUtil.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void removeSCProductEntries(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries)
		throws SystemException {
		try {
			for (com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry : scProductEntries) {
				removeSCProductEntry.remove(pk, scProductEntry.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCacheUtil.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void setSCProductEntries(long pk, long[] scProductEntryPKs)
		throws SystemException {
		try {
			clearSCProductEntries.clear(pk);

			for (long scProductEntryPK : scProductEntryPKs) {
				addSCProductEntry.add(pk, scProductEntryPK);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCacheUtil.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void setSCProductEntries(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries)
		throws SystemException {
		try {
			clearSCProductEntries.clear(pk);

			for (com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry : scProductEntries) {
				addSCProductEntry.add(pk, scProductEntry.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCacheUtil.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void registerListener(ModelListener listener) {
		List<ModelListener> listeners = ListUtil.fromArray(_listeners);

		listeners.add(listener);

		_listeners = listeners.toArray(new ModelListener[listeners.size()]);
	}

	public void unregisterListener(ModelListener listener) {
		List<ModelListener> listeners = ListUtil.fromArray(_listeners);

		listeners.remove(listener);

		_listeners = listeners.toArray(new ModelListener[listeners.size()]);
	}

	protected void init() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.softwarecatalog.model.SCLicense")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener> listeners = new ArrayList<ModelListener>();

				for (String listenerClassName : listenerClassNames) {
					listeners.add((ModelListener)Class.forName(
							listenerClassName).newInstance());
				}

				_listeners = listeners.toArray(new ModelListener[listeners.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsSCProductEntry = new ContainsSCProductEntry(this);

		addSCProductEntry = new AddSCProductEntry(this);
		clearSCProductEntries = new ClearSCProductEntries(this);
		removeSCProductEntry = new RemoveSCProductEntry(this);
	}

	protected ContainsSCProductEntry containsSCProductEntry;
	protected AddSCProductEntry addSCProductEntry;
	protected ClearSCProductEntries clearSCProductEntries;
	protected RemoveSCProductEntry removeSCProductEntry;

	protected class ContainsSCProductEntry extends MappingSqlQuery {
		protected ContainsSCProductEntry(
			SCLicensePersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(), _SQL_CONTAINSSCPRODUCTENTRY);

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNumber)
			throws SQLException {
			return new Integer(rs.getInt("COUNT_VALUE"));
		}

		protected boolean contains(long licenseId, long productEntryId) {
			List<Integer> results = execute(new Object[] {
						new Long(licenseId), new Long(productEntryId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}
	}

	protected class AddSCProductEntry extends SqlUpdate {
		protected AddSCProductEntry(SCLicensePersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"INSERT INTO SCLicenses_SCProductEntries (licenseId, productEntryId) VALUES (?, ?)");

			_persistenceImpl = persistenceImpl;

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void add(long licenseId, long productEntryId) {
			if (!_persistenceImpl.containsSCProductEntry.contains(licenseId,
						productEntryId)) {
				update(new Object[] {
						new Long(licenseId), new Long(productEntryId)
					});
			}
		}

		private SCLicensePersistenceImpl _persistenceImpl;
	}

	protected class ClearSCProductEntries extends SqlUpdate {
		protected ClearSCProductEntries(
			SCLicensePersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"DELETE FROM SCLicenses_SCProductEntries WHERE licenseId = ?");

			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void clear(long licenseId) {
			update(new Object[] { new Long(licenseId) });
		}
	}

	protected class RemoveSCProductEntry extends SqlUpdate {
		protected RemoveSCProductEntry(SCLicensePersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"DELETE FROM SCLicenses_SCProductEntries WHERE licenseId = ? AND productEntryId = ?");

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void remove(long licenseId, long productEntryId) {
			update(new Object[] { new Long(licenseId), new Long(productEntryId) });
		}
	}

	private static final String _SQL_GETSCPRODUCTENTRIES = "SELECT {SCProductEntry.*} FROM SCProductEntry INNER JOIN SCLicenses_SCProductEntries ON (SCLicenses_SCProductEntries.productEntryId = SCProductEntry.productEntryId) WHERE (SCLicenses_SCProductEntries.licenseId = ?)";
	private static final String _SQL_GETSCPRODUCTENTRIESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM SCLicenses_SCProductEntries WHERE licenseId = ?";
	private static final String _SQL_CONTAINSSCPRODUCTENTRY = "SELECT COUNT(*) AS COUNT_VALUE FROM SCLicenses_SCProductEntries WHERE licenseId = ? AND productEntryId = ?";
	private static Log _log = LogFactory.getLog(SCLicensePersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}