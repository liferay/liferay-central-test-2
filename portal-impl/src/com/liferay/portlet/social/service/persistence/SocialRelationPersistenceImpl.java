/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.social.NoSuchRelationException;
import com.liferay.portlet.social.model.SocialRelation;
import com.liferay.portlet.social.model.impl.SocialRelationImpl;
import com.liferay.portlet.social.model.impl.SocialRelationModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="SocialRelationPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SocialRelationPersistenceImpl extends BasePersistenceImpl
	implements SocialRelationPersistence {
	public SocialRelation create(long relationId) {
		SocialRelation socialRelation = new SocialRelationImpl();

		socialRelation.setNew(true);
		socialRelation.setPrimaryKey(relationId);

		String uuid = PortalUUIDUtil.generate();

		socialRelation.setUuid(uuid);

		return socialRelation;
	}

	public SocialRelation remove(long relationId)
		throws NoSuchRelationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialRelation socialRelation = (SocialRelation)session.get(SocialRelationImpl.class,
					new Long(relationId));

			if (socialRelation == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No SocialRelation exists with the primary key " +
						relationId);
				}

				throw new NoSuchRelationException(
					"No SocialRelation exists with the primary key " +
					relationId);
			}

			return remove(socialRelation);
		}
		catch (NoSuchRelationException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SocialRelation remove(SocialRelation socialRelation)
		throws SystemException {
		for (ModelListener<SocialRelation> listener : listeners) {
			listener.onBeforeRemove(socialRelation);
		}

		socialRelation = removeImpl(socialRelation);

		for (ModelListener<SocialRelation> listener : listeners) {
			listener.onAfterRemove(socialRelation);
		}

		return socialRelation;
	}

	protected SocialRelation removeImpl(SocialRelation socialRelation)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(SocialRelationImpl.class,
						socialRelation.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(socialRelation);

			session.flush();

			return socialRelation;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(SocialRelation.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(SocialRelation socialRelation, boolean merge)</code>.
	 */
	public SocialRelation update(SocialRelation socialRelation)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(SocialRelation socialRelation) method. Use update(SocialRelation socialRelation, boolean merge) instead.");
		}

		return update(socialRelation, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        socialRelation the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when socialRelation is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public SocialRelation update(SocialRelation socialRelation, boolean merge)
		throws SystemException {
		boolean isNew = socialRelation.isNew();

		for (ModelListener<SocialRelation> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(socialRelation);
			}
			else {
				listener.onBeforeUpdate(socialRelation);
			}
		}

		socialRelation = updateImpl(socialRelation, merge);

		for (ModelListener<SocialRelation> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(socialRelation);
			}
			else {
				listener.onAfterUpdate(socialRelation);
			}
		}

		return socialRelation;
	}

	public SocialRelation updateImpl(
		com.liferay.portlet.social.model.SocialRelation socialRelation,
		boolean merge) throws SystemException {
		if (Validator.isNull(socialRelation.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			socialRelation.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, socialRelation, merge);

			socialRelation.setNew(false);

			return socialRelation;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(SocialRelation.class.getName());
		}
	}

	public SocialRelation findByPrimaryKey(long relationId)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByPrimaryKey(relationId);

		if (socialRelation == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No SocialRelation exists with the primary key " +
					relationId);
			}

			throw new NoSuchRelationException(
				"No SocialRelation exists with the primary key " + relationId);
		}

		return socialRelation;
	}

	public SocialRelation fetchByPrimaryKey(long relationId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (SocialRelation)session.get(SocialRelationImpl.class,
				new Long(relationId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRelation> findByUuid(String uuid)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
		String finderMethodName = "findByUuid";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { uuid };

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
					"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				List<SocialRelation> list = q.list();

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
			return (List<SocialRelation>)result;
		}
	}

	public List<SocialRelation> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<SocialRelation> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
		String finderMethodName = "findByUuid";
		String[] finderParams = new String[] {
				String.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				uuid,
				
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
					"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				List<SocialRelation> list = (List<SocialRelation>)QueryUtil.list(q,
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
			return (List<SocialRelation>)result;
		}
	}

	public SocialRelation findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchRelationException, SystemException {
		List<SocialRelation> list = findByUuid(uuid, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchRelationException, SystemException {
		int count = countByUuid(uuid);

		List<SocialRelation> list = findByUuid(uuid, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation[] findByUuid_PrevAndNext(long relationId,
		String uuid, OrderByComparator obc)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

			if (uuid == null) {
				query.append("uuid_ IS NULL");
			}
			else {
				query.append("uuid_ = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRelation);

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = (SocialRelation)objArray[0];
			array[1] = (SocialRelation)objArray[1];
			array[2] = (SocialRelation)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRelation> findByCompanyId(long companyId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
		String finderMethodName = "findByCompanyId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(companyId) };

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
					"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				List<SocialRelation> list = q.list();

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
			return (List<SocialRelation>)result;
		}
	}

	public List<SocialRelation> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<SocialRelation> findByCompanyId(long companyId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
		String finderMethodName = "findByCompanyId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
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
					"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				List<SocialRelation> list = (List<SocialRelation>)QueryUtil.list(q,
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
			return (List<SocialRelation>)result;
		}
	}

	public SocialRelation findByCompanyId_First(long companyId,
		OrderByComparator obc) throws NoSuchRelationException, SystemException {
		List<SocialRelation> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation findByCompanyId_Last(long companyId,
		OrderByComparator obc) throws NoSuchRelationException, SystemException {
		int count = countByCompanyId(companyId);

		List<SocialRelation> list = findByCompanyId(companyId, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation[] findByCompanyId_PrevAndNext(long relationId,
		long companyId, OrderByComparator obc)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

			query.append("companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRelation);

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = (SocialRelation)objArray[0];
			array[1] = (SocialRelation)objArray[1];
			array[2] = (SocialRelation)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRelation> findByUserId1(long userId1)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
		String finderMethodName = "findByUserId1";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId1) };

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
					"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

				query.append("userId1 = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

				List<SocialRelation> list = q.list();

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
			return (List<SocialRelation>)result;
		}
	}

	public List<SocialRelation> findByUserId1(long userId1, int start, int end)
		throws SystemException {
		return findByUserId1(userId1, start, end, null);
	}

	public List<SocialRelation> findByUserId1(long userId1, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
		String finderMethodName = "findByUserId1";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(userId1),
				
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
					"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

				query.append("userId1 = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

				List<SocialRelation> list = (List<SocialRelation>)QueryUtil.list(q,
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
			return (List<SocialRelation>)result;
		}
	}

	public SocialRelation findByUserId1_First(long userId1,
		OrderByComparator obc) throws NoSuchRelationException, SystemException {
		List<SocialRelation> list = findByUserId1(userId1, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("userId1=" + userId1);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation findByUserId1_Last(long userId1, OrderByComparator obc)
		throws NoSuchRelationException, SystemException {
		int count = countByUserId1(userId1);

		List<SocialRelation> list = findByUserId1(userId1, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("userId1=" + userId1);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation[] findByUserId1_PrevAndNext(long relationId,
		long userId1, OrderByComparator obc)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		int count = countByUserId1(userId1);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

			query.append("userId1 = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId1);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRelation);

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = (SocialRelation)objArray[0];
			array[1] = (SocialRelation)objArray[1];
			array[2] = (SocialRelation)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRelation> findByUserId2(long userId2)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
		String finderMethodName = "findByUserId2";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId2) };

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
					"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

				query.append("userId2 = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId2);

				List<SocialRelation> list = q.list();

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
			return (List<SocialRelation>)result;
		}
	}

	public List<SocialRelation> findByUserId2(long userId2, int start, int end)
		throws SystemException {
		return findByUserId2(userId2, start, end, null);
	}

	public List<SocialRelation> findByUserId2(long userId2, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
		String finderMethodName = "findByUserId2";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(userId2),
				
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
					"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

				query.append("userId2 = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId2);

				List<SocialRelation> list = (List<SocialRelation>)QueryUtil.list(q,
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
			return (List<SocialRelation>)result;
		}
	}

	public SocialRelation findByUserId2_First(long userId2,
		OrderByComparator obc) throws NoSuchRelationException, SystemException {
		List<SocialRelation> list = findByUserId2(userId2, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("userId2=" + userId2);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation findByUserId2_Last(long userId2, OrderByComparator obc)
		throws NoSuchRelationException, SystemException {
		int count = countByUserId2(userId2);

		List<SocialRelation> list = findByUserId2(userId2, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("userId2=" + userId2);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation[] findByUserId2_PrevAndNext(long relationId,
		long userId2, OrderByComparator obc)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		int count = countByUserId2(userId2);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

			query.append("userId2 = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId2);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRelation);

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = (SocialRelation)objArray[0];
			array[1] = (SocialRelation)objArray[1];
			array[2] = (SocialRelation)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRelation> findByType(int type) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
		String finderMethodName = "findByType";
		String[] finderParams = new String[] { Integer.class.getName() };
		Object[] finderArgs = new Object[] { new Integer(type) };

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
					"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

				query.append("type_ = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

				List<SocialRelation> list = q.list();

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
			return (List<SocialRelation>)result;
		}
	}

	public List<SocialRelation> findByType(int type, int start, int end)
		throws SystemException {
		return findByType(type, start, end, null);
	}

	public List<SocialRelation> findByType(int type, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
		String finderMethodName = "findByType";
		String[] finderParams = new String[] {
				Integer.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Integer(type),
				
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
					"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

				query.append("type_ = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

				List<SocialRelation> list = (List<SocialRelation>)QueryUtil.list(q,
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
			return (List<SocialRelation>)result;
		}
	}

	public SocialRelation findByType_First(int type, OrderByComparator obc)
		throws NoSuchRelationException, SystemException {
		List<SocialRelation> list = findByType(type, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("type=" + type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation findByType_Last(int type, OrderByComparator obc)
		throws NoSuchRelationException, SystemException {
		int count = countByType(type);

		List<SocialRelation> list = findByType(type, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("type=" + type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation[] findByType_PrevAndNext(long relationId, int type,
		OrderByComparator obc) throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		int count = countByType(type);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

			query.append("type_ = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(type);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRelation);

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = (SocialRelation)objArray[0];
			array[1] = (SocialRelation)objArray[1];
			array[2] = (SocialRelation)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRelation> findByC_T(long companyId, int type)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
		String finderMethodName = "findByC_T";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Integer(type)
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
					"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("type_ = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(type);

				List<SocialRelation> list = q.list();

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
			return (List<SocialRelation>)result;
		}
	}

	public List<SocialRelation> findByC_T(long companyId, int type, int start,
		int end) throws SystemException {
		return findByC_T(companyId, type, start, end, null);
	}

	public List<SocialRelation> findByC_T(long companyId, int type, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
		String finderMethodName = "findByC_T";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Integer(type),
				
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
					"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("type_ = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(type);

				List<SocialRelation> list = (List<SocialRelation>)QueryUtil.list(q,
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
			return (List<SocialRelation>)result;
		}
	}

	public SocialRelation findByC_T_First(long companyId, int type,
		OrderByComparator obc) throws NoSuchRelationException, SystemException {
		List<SocialRelation> list = findByC_T(companyId, type, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("type=" + type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation findByC_T_Last(long companyId, int type,
		OrderByComparator obc) throws NoSuchRelationException, SystemException {
		int count = countByC_T(companyId, type);

		List<SocialRelation> list = findByC_T(companyId, type, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("type=" + type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation[] findByC_T_PrevAndNext(long relationId,
		long companyId, int type, OrderByComparator obc)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		int count = countByC_T(companyId, type);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

			query.append("companyId = ?");

			query.append(" AND ");

			query.append("type_ = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			qPos.add(type);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRelation);

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = (SocialRelation)objArray[0];
			array[1] = (SocialRelation)objArray[1];
			array[2] = (SocialRelation)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRelation> findByU1_T(long userId1, int type)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
		String finderMethodName = "findByU1_T";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(userId1), new Integer(type) };

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
					"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

				query.append("userId1 = ?");

				query.append(" AND ");

				query.append("type_ = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

				qPos.add(type);

				List<SocialRelation> list = q.list();

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
			return (List<SocialRelation>)result;
		}
	}

	public List<SocialRelation> findByU1_T(long userId1, int type, int start,
		int end) throws SystemException {
		return findByU1_T(userId1, type, start, end, null);
	}

	public List<SocialRelation> findByU1_T(long userId1, int type, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
		String finderMethodName = "findByU1_T";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(userId1), new Integer(type),
				
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
					"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

				query.append("userId1 = ?");

				query.append(" AND ");

				query.append("type_ = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

				qPos.add(type);

				List<SocialRelation> list = (List<SocialRelation>)QueryUtil.list(q,
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
			return (List<SocialRelation>)result;
		}
	}

	public SocialRelation findByU1_T_First(long userId1, int type,
		OrderByComparator obc) throws NoSuchRelationException, SystemException {
		List<SocialRelation> list = findByU1_T(userId1, type, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("userId1=" + userId1);

			msg.append(", ");
			msg.append("type=" + type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation findByU1_T_Last(long userId1, int type,
		OrderByComparator obc) throws NoSuchRelationException, SystemException {
		int count = countByU1_T(userId1, type);

		List<SocialRelation> list = findByU1_T(userId1, type, count - 1, count,
				obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("userId1=" + userId1);

			msg.append(", ");
			msg.append("type=" + type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation[] findByU1_T_PrevAndNext(long relationId,
		long userId1, int type, OrderByComparator obc)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		int count = countByU1_T(userId1, type);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

			query.append("userId1 = ?");

			query.append(" AND ");

			query.append("type_ = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId1);

			qPos.add(type);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRelation);

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = (SocialRelation)objArray[0];
			array[1] = (SocialRelation)objArray[1];
			array[2] = (SocialRelation)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRelation> findByU2_T(long userId2, int type)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
		String finderMethodName = "findByU2_T";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(userId2), new Integer(type) };

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
					"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

				query.append("userId2 = ?");

				query.append(" AND ");

				query.append("type_ = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId2);

				qPos.add(type);

				List<SocialRelation> list = q.list();

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
			return (List<SocialRelation>)result;
		}
	}

	public List<SocialRelation> findByU2_T(long userId2, int type, int start,
		int end) throws SystemException {
		return findByU2_T(userId2, type, start, end, null);
	}

	public List<SocialRelation> findByU2_T(long userId2, int type, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
		String finderMethodName = "findByU2_T";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(userId2), new Integer(type),
				
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
					"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

				query.append("userId2 = ?");

				query.append(" AND ");

				query.append("type_ = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId2);

				qPos.add(type);

				List<SocialRelation> list = (List<SocialRelation>)QueryUtil.list(q,
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
			return (List<SocialRelation>)result;
		}
	}

	public SocialRelation findByU2_T_First(long userId2, int type,
		OrderByComparator obc) throws NoSuchRelationException, SystemException {
		List<SocialRelation> list = findByU2_T(userId2, type, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("userId2=" + userId2);

			msg.append(", ");
			msg.append("type=" + type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation findByU2_T_Last(long userId2, int type,
		OrderByComparator obc) throws NoSuchRelationException, SystemException {
		int count = countByU2_T(userId2, type);

		List<SocialRelation> list = findByU2_T(userId2, type, count - 1, count,
				obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("userId2=" + userId2);

			msg.append(", ");
			msg.append("type=" + type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation[] findByU2_T_PrevAndNext(long relationId,
		long userId2, int type, OrderByComparator obc)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		int count = countByU2_T(userId2, type);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

			query.append("userId2 = ?");

			query.append(" AND ");

			query.append("type_ = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId2);

			qPos.add(type);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRelation);

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = (SocialRelation)objArray[0];
			array[1] = (SocialRelation)objArray[1];
			array[2] = (SocialRelation)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SocialRelation findByU1_U2_T(long userId1, long userId2, int type)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByU1_U2_T(userId1, userId2, type);

		if (socialRelation == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("userId1=" + userId1);

			msg.append(", ");
			msg.append("userId2=" + userId2);

			msg.append(", ");
			msg.append("type=" + type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchRelationException(msg.toString());
		}

		return socialRelation;
	}

	public SocialRelation fetchByU1_U2_T(long userId1, long userId2, int type)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
		String finderMethodName = "fetchByU1_U2_T";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(userId1), new Long(userId2), new Integer(type)
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
					"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

				query.append("userId1 = ?");

				query.append(" AND ");

				query.append("userId2 = ?");

				query.append(" AND ");

				query.append("type_ = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

				qPos.add(userId2);

				qPos.add(type);

				List<SocialRelation> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<SocialRelation> list = (List<SocialRelation>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
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

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.setLimit(start, end);

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

	public List<SocialRelation> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SocialRelation> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<SocialRelation> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
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
					"FROM com.liferay.portlet.social.model.SocialRelation ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<SocialRelation> list = null;

				if (obc == null) {
					list = (List<SocialRelation>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SocialRelation>)QueryUtil.list(q,
							getDialect(), start, end);
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
			return (List<SocialRelation>)result;
		}
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (SocialRelation socialRelation : findByUuid(uuid)) {
			remove(socialRelation);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (SocialRelation socialRelation : findByCompanyId(companyId)) {
			remove(socialRelation);
		}
	}

	public void removeByUserId1(long userId1) throws SystemException {
		for (SocialRelation socialRelation : findByUserId1(userId1)) {
			remove(socialRelation);
		}
	}

	public void removeByUserId2(long userId2) throws SystemException {
		for (SocialRelation socialRelation : findByUserId2(userId2)) {
			remove(socialRelation);
		}
	}

	public void removeByType(int type) throws SystemException {
		for (SocialRelation socialRelation : findByType(type)) {
			remove(socialRelation);
		}
	}

	public void removeByC_T(long companyId, int type) throws SystemException {
		for (SocialRelation socialRelation : findByC_T(companyId, type)) {
			remove(socialRelation);
		}
	}

	public void removeByU1_T(long userId1, int type) throws SystemException {
		for (SocialRelation socialRelation : findByU1_T(userId1, type)) {
			remove(socialRelation);
		}
	}

	public void removeByU2_T(long userId2, int type) throws SystemException {
		for (SocialRelation socialRelation : findByU2_T(userId2, type)) {
			remove(socialRelation);
		}
	}

	public void removeByU1_U2_T(long userId1, long userId2, int type)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByU1_U2_T(userId1, userId2, type);

		remove(socialRelation);
	}

	public void removeAll() throws SystemException {
		for (SocialRelation socialRelation : findAll()) {
			remove(socialRelation);
		}
	}

	public int countByUuid(String uuid) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
		String finderMethodName = "countByUuid";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { uuid };

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
					"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

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

	public int countByCompanyId(long companyId) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
		String finderMethodName = "countByCompanyId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(companyId) };

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
					"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

	public int countByUserId1(long userId1) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
		String finderMethodName = "countByUserId1";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId1) };

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
					"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

				query.append("userId1 = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

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

	public int countByUserId2(long userId2) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
		String finderMethodName = "countByUserId2";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId2) };

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
					"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

				query.append("userId2 = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId2);

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

	public int countByType(int type) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
		String finderMethodName = "countByType";
		String[] finderParams = new String[] { Integer.class.getName() };
		Object[] finderArgs = new Object[] { new Integer(type) };

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
					"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

				query.append("type_ = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

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

	public int countByC_T(long companyId, int type) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
		String finderMethodName = "countByC_T";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Integer(type)
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
					"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("type_ = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(type);

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

	public int countByU1_T(long userId1, int type) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
		String finderMethodName = "countByU1_T";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(userId1), new Integer(type) };

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
					"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

				query.append("userId1 = ?");

				query.append(" AND ");

				query.append("type_ = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

				qPos.add(type);

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

	public int countByU2_T(long userId2, int type) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
		String finderMethodName = "countByU2_T";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(userId2), new Integer(type) };

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
					"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

				query.append("userId2 = ?");

				query.append(" AND ");

				query.append("type_ = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId2);

				qPos.add(type);

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

	public int countByU1_U2_T(long userId1, long userId2, int type)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
		String finderMethodName = "countByU1_U2_T";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(userId1), new Long(userId2), new Integer(type)
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
					"FROM com.liferay.portlet.social.model.SocialRelation WHERE ");

				query.append("userId1 = ?");

				query.append(" AND ");

				query.append("userId2 = ?");

				query.append(" AND ");

				query.append("type_ = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

				qPos.add(userId2);

				qPos.add(type);

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
		boolean finderClassNameCacheEnabled = SocialRelationModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRelation.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portlet.social.model.SocialRelation");

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

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.social.model.SocialRelation")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SocialRelation>> listenersList = new ArrayList<ModelListener<SocialRelation>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SocialRelation>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialActivityPersistence.impl")
	protected com.liferay.portlet.social.service.persistence.SocialActivityPersistence socialActivityPersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialRelationPersistence.impl")
	protected com.liferay.portlet.social.service.persistence.SocialRelationPersistence socialRelationPersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialRequestPersistence.impl")
	protected com.liferay.portlet.social.service.persistence.SocialRequestPersistence socialRequestPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static Log _log = LogFactoryUtil.getLog(SocialRelationPersistenceImpl.class);
}