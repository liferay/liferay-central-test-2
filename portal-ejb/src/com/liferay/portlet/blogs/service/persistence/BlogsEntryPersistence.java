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

package com.liferay.portlet.blogs.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.blogs.NoSuchEntryException;

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
 * <a href="BlogsEntryPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class BlogsEntryPersistence extends BasePersistence {
	public com.liferay.portlet.blogs.model.BlogsEntry create(String entryId) {
		BlogsEntryHBM blogsEntryHBM = new BlogsEntryHBM();
		blogsEntryHBM.setNew(true);
		blogsEntryHBM.setPrimaryKey(entryId);

		return BlogsEntryHBMUtil.model(blogsEntryHBM);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry remove(String entryId)
		throws NoSuchEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			BlogsEntryHBM blogsEntryHBM = (BlogsEntryHBM)session.get(BlogsEntryHBM.class,
					entryId);

			if (blogsEntryHBM == null) {
				_log.warn("No BlogsEntry exists with the primary key " +
					entryId.toString());
				throw new NoSuchEntryException(
					"No BlogsEntry exists with the primary key " +
					entryId.toString());
			}

			session.delete(blogsEntryHBM);
			session.flush();

			return BlogsEntryHBMUtil.model(blogsEntryHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.blogs.model.BlogsEntry update(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry)
		throws SystemException {
		Session session = null;

		try {
			if (blogsEntry.isNew() || blogsEntry.isModified()) {
				session = openSession();

				if (blogsEntry.isNew()) {
					BlogsEntryHBM blogsEntryHBM = new BlogsEntryHBM();
					blogsEntryHBM.setEntryId(blogsEntry.getEntryId());
					blogsEntryHBM.setGroupId(blogsEntry.getGroupId());
					blogsEntryHBM.setCompanyId(blogsEntry.getCompanyId());
					blogsEntryHBM.setUserId(blogsEntry.getUserId());
					blogsEntryHBM.setUserName(blogsEntry.getUserName());
					blogsEntryHBM.setCreateDate(blogsEntry.getCreateDate());
					blogsEntryHBM.setModifiedDate(blogsEntry.getModifiedDate());
					blogsEntryHBM.setCategoryId(blogsEntry.getCategoryId());
					blogsEntryHBM.setTitle(blogsEntry.getTitle());
					blogsEntryHBM.setContent(blogsEntry.getContent());
					blogsEntryHBM.setDisplayDate(blogsEntry.getDisplayDate());
					session.save(blogsEntryHBM);
					session.flush();
				}
				else {
					BlogsEntryHBM blogsEntryHBM = (BlogsEntryHBM)session.get(BlogsEntryHBM.class,
							blogsEntry.getPrimaryKey());

					if (blogsEntryHBM != null) {
						blogsEntryHBM.setGroupId(blogsEntry.getGroupId());
						blogsEntryHBM.setCompanyId(blogsEntry.getCompanyId());
						blogsEntryHBM.setUserId(blogsEntry.getUserId());
						blogsEntryHBM.setUserName(blogsEntry.getUserName());
						blogsEntryHBM.setCreateDate(blogsEntry.getCreateDate());
						blogsEntryHBM.setModifiedDate(blogsEntry.getModifiedDate());
						blogsEntryHBM.setCategoryId(blogsEntry.getCategoryId());
						blogsEntryHBM.setTitle(blogsEntry.getTitle());
						blogsEntryHBM.setContent(blogsEntry.getContent());
						blogsEntryHBM.setDisplayDate(blogsEntry.getDisplayDate());
						session.flush();
					}
					else {
						blogsEntryHBM = new BlogsEntryHBM();
						blogsEntryHBM.setEntryId(blogsEntry.getEntryId());
						blogsEntryHBM.setGroupId(blogsEntry.getGroupId());
						blogsEntryHBM.setCompanyId(blogsEntry.getCompanyId());
						blogsEntryHBM.setUserId(blogsEntry.getUserId());
						blogsEntryHBM.setUserName(blogsEntry.getUserName());
						blogsEntryHBM.setCreateDate(blogsEntry.getCreateDate());
						blogsEntryHBM.setModifiedDate(blogsEntry.getModifiedDate());
						blogsEntryHBM.setCategoryId(blogsEntry.getCategoryId());
						blogsEntryHBM.setTitle(blogsEntry.getTitle());
						blogsEntryHBM.setContent(blogsEntry.getContent());
						blogsEntryHBM.setDisplayDate(blogsEntry.getDisplayDate());
						session.save(blogsEntryHBM);
						session.flush();
					}
				}

				blogsEntry.setNew(false);
				blogsEntry.setModified(false);
			}

			return blogsEntry;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.blogs.model.BlogsEntry findByPrimaryKey(
		String entryId) throws NoSuchEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			BlogsEntryHBM blogsEntryHBM = (BlogsEntryHBM)session.get(BlogsEntryHBM.class,
					entryId);

			if (blogsEntryHBM == null) {
				_log.warn("No BlogsEntry exists with the primary key " +
					entryId.toString());
				throw new NoSuchEntryException(
					"No BlogsEntry exists with the primary key " +
					entryId.toString());
			}

			return BlogsEntryHBMUtil.model(blogsEntryHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM BlogsEntry IN CLASS com.liferay.portlet.blogs.service.persistence.BlogsEntryHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("displayDate DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				BlogsEntryHBM blogsEntryHBM = (BlogsEntryHBM)itr.next();
				list.add(BlogsEntryHBMUtil.model(blogsEntryHBM));
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

	public List findByGroupId(String groupId, int begin, int end)
		throws SystemException {
		return findByGroupId(groupId, begin, end, null);
	}

	public List findByGroupId(String groupId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM BlogsEntry IN CLASS com.liferay.portlet.blogs.service.persistence.BlogsEntryHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("displayDate DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				BlogsEntryHBM blogsEntryHBM = (BlogsEntryHBM)itr.next();
				list.add(BlogsEntryHBMUtil.model(blogsEntryHBM));
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

	public com.liferay.portlet.blogs.model.BlogsEntry findByGroupId_First(
		String groupId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		List list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No BlogsEntry exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchEntryException(msg);
		}
		else {
			return (com.liferay.portlet.blogs.model.BlogsEntry)list.get(0);
		}
	}

	public com.liferay.portlet.blogs.model.BlogsEntry findByGroupId_Last(
		String groupId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		int count = countByGroupId(groupId);
		List list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No BlogsEntry exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchEntryException(msg);
		}
		else {
			return (com.liferay.portlet.blogs.model.BlogsEntry)list.get(0);
		}
	}

	public com.liferay.portlet.blogs.model.BlogsEntry[] findByGroupId_PrevAndNext(
		String entryId, String groupId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry = findByPrimaryKey(entryId);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM BlogsEntry IN CLASS com.liferay.portlet.blogs.service.persistence.BlogsEntryHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("displayDate DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					blogsEntry, BlogsEntryHBMUtil.getInstance());
			com.liferay.portlet.blogs.model.BlogsEntry[] array = new com.liferay.portlet.blogs.model.BlogsEntry[3];
			array[0] = (com.liferay.portlet.blogs.model.BlogsEntry)objArray[0];
			array[1] = (com.liferay.portlet.blogs.model.BlogsEntry)objArray[1];
			array[2] = (com.liferay.portlet.blogs.model.BlogsEntry)objArray[2];

			return array;
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
				"FROM BlogsEntry IN CLASS com.liferay.portlet.blogs.service.persistence.BlogsEntryHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("displayDate DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				BlogsEntryHBM blogsEntryHBM = (BlogsEntryHBM)itr.next();
				list.add(BlogsEntryHBMUtil.model(blogsEntryHBM));
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
				"FROM BlogsEntry IN CLASS com.liferay.portlet.blogs.service.persistence.BlogsEntryHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
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
				query.append("displayDate DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				BlogsEntryHBM blogsEntryHBM = (BlogsEntryHBM)itr.next();
				list.add(BlogsEntryHBMUtil.model(blogsEntryHBM));
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

	public com.liferay.portlet.blogs.model.BlogsEntry findByCompanyId_First(
		String companyId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No BlogsEntry exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchEntryException(msg);
		}
		else {
			return (com.liferay.portlet.blogs.model.BlogsEntry)list.get(0);
		}
	}

	public com.liferay.portlet.blogs.model.BlogsEntry findByCompanyId_Last(
		String companyId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No BlogsEntry exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchEntryException(msg);
		}
		else {
			return (com.liferay.portlet.blogs.model.BlogsEntry)list.get(0);
		}
	}

	public com.liferay.portlet.blogs.model.BlogsEntry[] findByCompanyId_PrevAndNext(
		String entryId, String companyId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry = findByPrimaryKey(entryId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM BlogsEntry IN CLASS com.liferay.portlet.blogs.service.persistence.BlogsEntryHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
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
				query.append("displayDate DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					blogsEntry, BlogsEntryHBMUtil.getInstance());
			com.liferay.portlet.blogs.model.BlogsEntry[] array = new com.liferay.portlet.blogs.model.BlogsEntry[3];
			array[0] = (com.liferay.portlet.blogs.model.BlogsEntry)objArray[0];
			array[1] = (com.liferay.portlet.blogs.model.BlogsEntry)objArray[1];
			array[2] = (com.liferay.portlet.blogs.model.BlogsEntry)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCategoryId(String categoryId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM BlogsEntry IN CLASS com.liferay.portlet.blogs.service.persistence.BlogsEntryHBM WHERE ");

			if (categoryId == null) {
				query.append("categoryId is null");
			}
			else {
				query.append("categoryId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("displayDate DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (categoryId != null) {
				q.setString(queryPos++, categoryId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				BlogsEntryHBM blogsEntryHBM = (BlogsEntryHBM)itr.next();
				list.add(BlogsEntryHBMUtil.model(blogsEntryHBM));
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

	public List findByCategoryId(String categoryId, int begin, int end)
		throws SystemException {
		return findByCategoryId(categoryId, begin, end, null);
	}

	public List findByCategoryId(String categoryId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM BlogsEntry IN CLASS com.liferay.portlet.blogs.service.persistence.BlogsEntryHBM WHERE ");

			if (categoryId == null) {
				query.append("categoryId is null");
			}
			else {
				query.append("categoryId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("displayDate DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (categoryId != null) {
				q.setString(queryPos++, categoryId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				BlogsEntryHBM blogsEntryHBM = (BlogsEntryHBM)itr.next();
				list.add(BlogsEntryHBMUtil.model(blogsEntryHBM));
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

	public com.liferay.portlet.blogs.model.BlogsEntry findByCategoryId_First(
		String categoryId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		List list = findByCategoryId(categoryId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No BlogsEntry exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "categoryId=";
			msg += categoryId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchEntryException(msg);
		}
		else {
			return (com.liferay.portlet.blogs.model.BlogsEntry)list.get(0);
		}
	}

	public com.liferay.portlet.blogs.model.BlogsEntry findByCategoryId_Last(
		String categoryId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		int count = countByCategoryId(categoryId);
		List list = findByCategoryId(categoryId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No BlogsEntry exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "categoryId=";
			msg += categoryId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchEntryException(msg);
		}
		else {
			return (com.liferay.portlet.blogs.model.BlogsEntry)list.get(0);
		}
	}

	public com.liferay.portlet.blogs.model.BlogsEntry[] findByCategoryId_PrevAndNext(
		String entryId, String categoryId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry = findByPrimaryKey(entryId);
		int count = countByCategoryId(categoryId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM BlogsEntry IN CLASS com.liferay.portlet.blogs.service.persistence.BlogsEntryHBM WHERE ");

			if (categoryId == null) {
				query.append("categoryId is null");
			}
			else {
				query.append("categoryId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("displayDate DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (categoryId != null) {
				q.setString(queryPos++, categoryId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					blogsEntry, BlogsEntryHBMUtil.getInstance());
			com.liferay.portlet.blogs.model.BlogsEntry[] array = new com.liferay.portlet.blogs.model.BlogsEntry[3];
			array[0] = (com.liferay.portlet.blogs.model.BlogsEntry)objArray[0];
			array[1] = (com.liferay.portlet.blogs.model.BlogsEntry)objArray[1];
			array[2] = (com.liferay.portlet.blogs.model.BlogsEntry)objArray[2];

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
				"FROM BlogsEntry IN CLASS com.liferay.portlet.blogs.service.persistence.BlogsEntryHBM ");
			query.append("ORDER BY ");
			query.append("displayDate DESC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				BlogsEntryHBM blogsEntryHBM = (BlogsEntryHBM)itr.next();
				list.add(BlogsEntryHBMUtil.model(blogsEntryHBM));
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

	public void removeByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM BlogsEntry IN CLASS com.liferay.portlet.blogs.service.persistence.BlogsEntryHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("displayDate DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				BlogsEntryHBM blogsEntryHBM = (BlogsEntryHBM)itr.next();
				session.delete(blogsEntryHBM);
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

	public void removeByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM BlogsEntry IN CLASS com.liferay.portlet.blogs.service.persistence.BlogsEntryHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("displayDate DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				BlogsEntryHBM blogsEntryHBM = (BlogsEntryHBM)itr.next();
				session.delete(blogsEntryHBM);
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

	public void removeByCategoryId(String categoryId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM BlogsEntry IN CLASS com.liferay.portlet.blogs.service.persistence.BlogsEntryHBM WHERE ");

			if (categoryId == null) {
				query.append("categoryId is null");
			}
			else {
				query.append("categoryId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("displayDate DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (categoryId != null) {
				q.setString(queryPos++, categoryId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				BlogsEntryHBM blogsEntryHBM = (BlogsEntryHBM)itr.next();
				session.delete(blogsEntryHBM);
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

	public int countByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM BlogsEntry IN CLASS com.liferay.portlet.blogs.service.persistence.BlogsEntryHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
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

	public int countByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM BlogsEntry IN CLASS com.liferay.portlet.blogs.service.persistence.BlogsEntryHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
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

	public int countByCategoryId(String categoryId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM BlogsEntry IN CLASS com.liferay.portlet.blogs.service.persistence.BlogsEntryHBM WHERE ");

			if (categoryId == null) {
				query.append("categoryId is null");
			}
			else {
				query.append("categoryId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (categoryId != null) {
				q.setString(queryPos++, categoryId);
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

	private static Log _log = LogFactory.getLog(BlogsEntryPersistence.class);
}