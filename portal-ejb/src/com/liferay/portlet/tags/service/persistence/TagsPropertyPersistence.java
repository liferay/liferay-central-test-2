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

package com.liferay.portlet.tags.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.tags.NoSuchPropertyException;
import com.liferay.portlet.tags.model.TagsProperty;
import com.liferay.portlet.tags.model.impl.TagsPropertyImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="TagsPropertyPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TagsPropertyPersistence extends BasePersistence {
	public TagsProperty create(long propertyId) {
		TagsProperty tagsProperty = new TagsPropertyImpl();
		tagsProperty.setNew(true);
		tagsProperty.setPrimaryKey(propertyId);

		return tagsProperty;
	}

	public TagsProperty remove(long propertyId)
		throws NoSuchPropertyException, SystemException {
		Session session = null;

		try {
			session = openSession();

			TagsProperty tagsProperty = (TagsProperty)session.get(TagsPropertyImpl.class,
					new Long(propertyId));

			if (tagsProperty == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No TagsProperty exists with the primary key " +
						propertyId);
				}

				throw new NoSuchPropertyException(
					"No TagsProperty exists with the primary key " +
					propertyId);
			}

			return remove(tagsProperty);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public TagsProperty remove(TagsProperty tagsProperty)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(tagsProperty);
			session.flush();

			return tagsProperty;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.tags.model.TagsProperty update(
		com.liferay.portlet.tags.model.TagsProperty tagsProperty)
		throws SystemException {
		return update(tagsProperty, false);
	}

	public com.liferay.portlet.tags.model.TagsProperty update(
		com.liferay.portlet.tags.model.TagsProperty tagsProperty,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(tagsProperty);
			}
			else {
				if (tagsProperty.isNew()) {
					session.save(tagsProperty);
				}
			}

			session.flush();
			tagsProperty.setNew(false);

			return tagsProperty;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public TagsProperty findByPrimaryKey(long propertyId)
		throws NoSuchPropertyException, SystemException {
		TagsProperty tagsProperty = fetchByPrimaryKey(propertyId);

		if (tagsProperty == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No TagsProperty exists with the primary key " +
					propertyId);
			}

			throw new NoSuchPropertyException(
				"No TagsProperty exists with the primary key " + propertyId);
		}

		return tagsProperty;
	}

	public TagsProperty fetchByPrimaryKey(long propertyId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (TagsProperty)session.get(TagsPropertyImpl.class,
				new Long(propertyId));
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
				"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("key_ ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
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
				"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("key_ ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
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

	public TagsProperty findByCompanyId_First(String companyId,
		OrderByComparator obc) throws NoSuchPropertyException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No TagsProperty exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPropertyException(msg);
		}
		else {
			return (TagsProperty)list.get(0);
		}
	}

	public TagsProperty findByCompanyId_Last(String companyId,
		OrderByComparator obc) throws NoSuchPropertyException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No TagsProperty exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPropertyException(msg);
		}
		else {
			return (TagsProperty)list.get(0);
		}
	}

	public TagsProperty[] findByCompanyId_PrevAndNext(long propertyId,
		String companyId, OrderByComparator obc)
		throws NoSuchPropertyException, SystemException {
		TagsProperty tagsProperty = findByPrimaryKey(propertyId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("key_ ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					tagsProperty);
			TagsProperty[] array = new TagsPropertyImpl[3];
			array[0] = (TagsProperty)objArray[0];
			array[1] = (TagsProperty)objArray[1];
			array[2] = (TagsProperty)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByEntryId(long entryId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");
			query.append("entryId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("key_ ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, entryId);

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByEntryId(long entryId, int begin, int end)
		throws SystemException {
		return findByEntryId(entryId, begin, end, null);
	}

	public List findByEntryId(long entryId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");
			query.append("entryId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("key_ ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, entryId);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public TagsProperty findByEntryId_First(long entryId, OrderByComparator obc)
		throws NoSuchPropertyException, SystemException {
		List list = findByEntryId(entryId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No TagsProperty exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "entryId=";
			msg += entryId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPropertyException(msg);
		}
		else {
			return (TagsProperty)list.get(0);
		}
	}

	public TagsProperty findByEntryId_Last(long entryId, OrderByComparator obc)
		throws NoSuchPropertyException, SystemException {
		int count = countByEntryId(entryId);
		List list = findByEntryId(entryId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No TagsProperty exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "entryId=";
			msg += entryId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPropertyException(msg);
		}
		else {
			return (TagsProperty)list.get(0);
		}
	}

	public TagsProperty[] findByEntryId_PrevAndNext(long propertyId,
		long entryId, OrderByComparator obc)
		throws NoSuchPropertyException, SystemException {
		TagsProperty tagsProperty = findByPrimaryKey(propertyId);
		int count = countByEntryId(entryId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");
			query.append("entryId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("key_ ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, entryId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					tagsProperty);
			TagsProperty[] array = new TagsPropertyImpl[3];
			array[0] = (TagsProperty)objArray[0];
			array[1] = (TagsProperty)objArray[1];
			array[2] = (TagsProperty)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_K(String companyId, String key)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (key == null) {
				query.append("key_ IS NULL");
			}
			else {
				query.append("key_ = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("key_ ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (key != null) {
				q.setString(queryPos++, key);
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

	public List findByC_K(String companyId, String key, int begin, int end)
		throws SystemException {
		return findByC_K(companyId, key, begin, end, null);
	}

	public List findByC_K(String companyId, String key, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (key == null) {
				query.append("key_ IS NULL");
			}
			else {
				query.append("key_ = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("key_ ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (key != null) {
				q.setString(queryPos++, key);
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

	public TagsProperty findByC_K_First(String companyId, String key,
		OrderByComparator obc) throws NoSuchPropertyException, SystemException {
		List list = findByC_K(companyId, key, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No TagsProperty exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "key=";
			msg += key;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPropertyException(msg);
		}
		else {
			return (TagsProperty)list.get(0);
		}
	}

	public TagsProperty findByC_K_Last(String companyId, String key,
		OrderByComparator obc) throws NoSuchPropertyException, SystemException {
		int count = countByC_K(companyId, key);
		List list = findByC_K(companyId, key, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No TagsProperty exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "key=";
			msg += key;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPropertyException(msg);
		}
		else {
			return (TagsProperty)list.get(0);
		}
	}

	public TagsProperty[] findByC_K_PrevAndNext(long propertyId,
		String companyId, String key, OrderByComparator obc)
		throws NoSuchPropertyException, SystemException {
		TagsProperty tagsProperty = findByPrimaryKey(propertyId);
		int count = countByC_K(companyId, key);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (key == null) {
				query.append("key_ IS NULL");
			}
			else {
				query.append("key_ = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("key_ ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (key != null) {
				q.setString(queryPos++, key);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					tagsProperty);
			TagsProperty[] array = new TagsPropertyImpl[3];
			array[0] = (TagsProperty)objArray[0];
			array[1] = (TagsProperty)objArray[1];
			array[2] = (TagsProperty)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public TagsProperty findByE_K(long entryId, String key)
		throws NoSuchPropertyException, SystemException {
		TagsProperty tagsProperty = fetchByE_K(entryId, key);

		if (tagsProperty == null) {
			String msg = "No TagsProperty exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "entryId=";
			msg += entryId;
			msg += ", ";
			msg += "key=";
			msg += key;
			msg += StringPool.CLOSE_CURLY_BRACE;

			if (_log.isWarnEnabled()) {
				_log.warn(msg);
			}

			throw new NoSuchPropertyException(msg);
		}

		return tagsProperty;
	}

	public TagsProperty fetchByE_K(long entryId, String key)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");
			query.append("entryId = ?");
			query.append(" AND ");

			if (key == null) {
				query.append("key_ IS NULL");
			}
			else {
				query.append("key_ = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("key_ ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, entryId);

			if (key != null) {
				q.setString(queryPos++, key);
			}

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			TagsProperty tagsProperty = (TagsProperty)list.get(0);

			return tagsProperty;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findWithDynamicQuery(DynamicQueryInitializer queryInitializer)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			return query.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findWithDynamicQuery(DynamicQueryInitializer queryInitializer,
		int begin, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);
			query.setLimit(begin, end);

			return query.list();
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
			query.append("FROM com.liferay.portlet.tags.model.TagsProperty ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("key_ ASC");
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

	public void removeByCompanyId(String companyId) throws SystemException {
		Iterator itr = findByCompanyId(companyId).iterator();

		while (itr.hasNext()) {
			TagsProperty tagsProperty = (TagsProperty)itr.next();
			remove(tagsProperty);
		}
	}

	public void removeByEntryId(long entryId) throws SystemException {
		Iterator itr = findByEntryId(entryId).iterator();

		while (itr.hasNext()) {
			TagsProperty tagsProperty = (TagsProperty)itr.next();
			remove(tagsProperty);
		}
	}

	public void removeByC_K(String companyId, String key)
		throws SystemException {
		Iterator itr = findByC_K(companyId, key).iterator();

		while (itr.hasNext()) {
			TagsProperty tagsProperty = (TagsProperty)itr.next();
			remove(tagsProperty);
		}
	}

	public void removeByE_K(long entryId, String key)
		throws NoSuchPropertyException, SystemException {
		TagsProperty tagsProperty = findByE_K(entryId, key);
		remove(tagsProperty);
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((TagsProperty)itr.next());
		}
	}

	public int countByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

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

	public int countByEntryId(long entryId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");
			query.append("entryId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, entryId);

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

	public int countByC_K(String companyId, String key)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (key == null) {
				query.append("key_ IS NULL");
			}
			else {
				query.append("key_ = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (key != null) {
				q.setString(queryPos++, key);
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

	public int countByE_K(long entryId, String key) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.tags.model.TagsProperty WHERE ");
			query.append("entryId = ?");
			query.append(" AND ");

			if (key == null) {
				query.append("key_ IS NULL");
			}
			else {
				query.append("key_ = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, entryId);

			if (key != null) {
				q.setString(queryPos++, key);
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

	public int countAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portlet.tags.model.TagsProperty");

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

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(TagsPropertyPersistence.class);
}