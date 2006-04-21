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

import com.liferay.portal.NoSuchWebsiteException;
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
 * <a href="WebsitePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class WebsitePersistence extends BasePersistence {
	public com.liferay.portal.model.Website create(String websiteId) {
		WebsiteHBM websiteHBM = new WebsiteHBM();
		websiteHBM.setNew(true);
		websiteHBM.setPrimaryKey(websiteId);

		return WebsiteHBMUtil.model(websiteHBM);
	}

	public com.liferay.portal.model.Website remove(String websiteId)
		throws NoSuchWebsiteException, SystemException {
		Session session = null;

		try {
			session = openSession();

			WebsiteHBM websiteHBM = (WebsiteHBM)session.get(WebsiteHBM.class,
					websiteId);

			if (websiteHBM == null) {
				_log.warn("No Website exists with the primary key " +
					websiteId.toString());
				throw new NoSuchWebsiteException(
					"No Website exists with the primary key " +
					websiteId.toString());
			}

			session.delete(websiteHBM);
			session.flush();

			return WebsiteHBMUtil.model(websiteHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Website update(
		com.liferay.portal.model.Website website) throws SystemException {
		Session session = null;

		try {
			if (website.isNew() || website.isModified()) {
				session = openSession();

				if (website.isNew()) {
					WebsiteHBM websiteHBM = new WebsiteHBM();
					websiteHBM.setWebsiteId(website.getWebsiteId());
					websiteHBM.setCompanyId(website.getCompanyId());
					websiteHBM.setUserId(website.getUserId());
					websiteHBM.setUserName(website.getUserName());
					websiteHBM.setCreateDate(website.getCreateDate());
					websiteHBM.setModifiedDate(website.getModifiedDate());
					websiteHBM.setClassName(website.getClassName());
					websiteHBM.setClassPK(website.getClassPK());
					websiteHBM.setUrl(website.getUrl());
					websiteHBM.setTypeId(website.getTypeId());
					websiteHBM.setPrimary(website.getPrimary());
					session.save(websiteHBM);
					session.flush();
				}
				else {
					WebsiteHBM websiteHBM = (WebsiteHBM)session.get(WebsiteHBM.class,
							website.getPrimaryKey());

					if (websiteHBM != null) {
						websiteHBM.setCompanyId(website.getCompanyId());
						websiteHBM.setUserId(website.getUserId());
						websiteHBM.setUserName(website.getUserName());
						websiteHBM.setCreateDate(website.getCreateDate());
						websiteHBM.setModifiedDate(website.getModifiedDate());
						websiteHBM.setClassName(website.getClassName());
						websiteHBM.setClassPK(website.getClassPK());
						websiteHBM.setUrl(website.getUrl());
						websiteHBM.setTypeId(website.getTypeId());
						websiteHBM.setPrimary(website.getPrimary());
						session.flush();
					}
					else {
						websiteHBM = new WebsiteHBM();
						websiteHBM.setWebsiteId(website.getWebsiteId());
						websiteHBM.setCompanyId(website.getCompanyId());
						websiteHBM.setUserId(website.getUserId());
						websiteHBM.setUserName(website.getUserName());
						websiteHBM.setCreateDate(website.getCreateDate());
						websiteHBM.setModifiedDate(website.getModifiedDate());
						websiteHBM.setClassName(website.getClassName());
						websiteHBM.setClassPK(website.getClassPK());
						websiteHBM.setUrl(website.getUrl());
						websiteHBM.setTypeId(website.getTypeId());
						websiteHBM.setPrimary(website.getPrimary());
						session.save(websiteHBM);
						session.flush();
					}
				}

				website.setNew(false);
				website.setModified(false);
			}

			return website;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Website findByPrimaryKey(String websiteId)
		throws NoSuchWebsiteException, SystemException {
		Session session = null;

		try {
			session = openSession();

			WebsiteHBM websiteHBM = (WebsiteHBM)session.get(WebsiteHBM.class,
					websiteId);

			if (websiteHBM == null) {
				_log.warn("No Website exists with the primary key " +
					websiteId.toString());
				throw new NoSuchWebsiteException(
					"No Website exists with the primary key " +
					websiteId.toString());
			}

			return WebsiteHBMUtil.model(websiteHBM);
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
				"FROM Website IN CLASS com.liferay.portal.service.persistence.WebsiteHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				WebsiteHBM websiteHBM = (WebsiteHBM)itr.next();
				list.add(WebsiteHBMUtil.model(websiteHBM));
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
				"FROM Website IN CLASS com.liferay.portal.service.persistence.WebsiteHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				WebsiteHBM websiteHBM = (WebsiteHBM)itr.next();
				list.add(WebsiteHBMUtil.model(websiteHBM));
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

	public com.liferay.portal.model.Website findByCompanyId_First(
		String companyId, OrderByComparator obc)
		throws NoSuchWebsiteException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Website exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchWebsiteException(msg);
		}
		else {
			return (com.liferay.portal.model.Website)list.get(0);
		}
	}

	public com.liferay.portal.model.Website findByCompanyId_Last(
		String companyId, OrderByComparator obc)
		throws NoSuchWebsiteException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Website exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchWebsiteException(msg);
		}
		else {
			return (com.liferay.portal.model.Website)list.get(0);
		}
	}

	public com.liferay.portal.model.Website[] findByCompanyId_PrevAndNext(
		String websiteId, String companyId, OrderByComparator obc)
		throws NoSuchWebsiteException, SystemException {
		com.liferay.portal.model.Website website = findByPrimaryKey(websiteId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Website IN CLASS com.liferay.portal.service.persistence.WebsiteHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					website, WebsiteHBMUtil.getInstance());
			com.liferay.portal.model.Website[] array = new com.liferay.portal.model.Website[3];
			array[0] = (com.liferay.portal.model.Website)objArray[0];
			array[1] = (com.liferay.portal.model.Website)objArray[1];
			array[2] = (com.liferay.portal.model.Website)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByUserId(String userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Website IN CLASS com.liferay.portal.service.persistence.WebsiteHBM WHERE ");
			query.append("userId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				WebsiteHBM websiteHBM = (WebsiteHBM)itr.next();
				list.add(WebsiteHBMUtil.model(websiteHBM));
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

	public List findByUserId(String userId, int begin, int end)
		throws SystemException {
		return findByUserId(userId, begin, end, null);
	}

	public List findByUserId(String userId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Website IN CLASS com.liferay.portal.service.persistence.WebsiteHBM WHERE ");
			query.append("userId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				WebsiteHBM websiteHBM = (WebsiteHBM)itr.next();
				list.add(WebsiteHBMUtil.model(websiteHBM));
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

	public com.liferay.portal.model.Website findByUserId_First(String userId,
		OrderByComparator obc) throws NoSuchWebsiteException, SystemException {
		List list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Website exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchWebsiteException(msg);
		}
		else {
			return (com.liferay.portal.model.Website)list.get(0);
		}
	}

	public com.liferay.portal.model.Website findByUserId_Last(String userId,
		OrderByComparator obc) throws NoSuchWebsiteException, SystemException {
		int count = countByUserId(userId);
		List list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Website exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchWebsiteException(msg);
		}
		else {
			return (com.liferay.portal.model.Website)list.get(0);
		}
	}

	public com.liferay.portal.model.Website[] findByUserId_PrevAndNext(
		String websiteId, String userId, OrderByComparator obc)
		throws NoSuchWebsiteException, SystemException {
		com.liferay.portal.model.Website website = findByPrimaryKey(websiteId);
		int count = countByUserId(userId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Website IN CLASS com.liferay.portal.service.persistence.WebsiteHBM WHERE ");
			query.append("userId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					website, WebsiteHBMUtil.getInstance());
			com.liferay.portal.model.Website[] array = new com.liferay.portal.model.Website[3];
			array[0] = (com.liferay.portal.model.Website)objArray[0];
			array[1] = (com.liferay.portal.model.Website)objArray[1];
			array[2] = (com.liferay.portal.model.Website)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_C(String companyId, String className)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Website IN CLASS com.liferay.portal.service.persistence.WebsiteHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				WebsiteHBM websiteHBM = (WebsiteHBM)itr.next();
				list.add(WebsiteHBMUtil.model(websiteHBM));
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

	public List findByC_C(String companyId, String className, int begin, int end)
		throws SystemException {
		return findByC_C(companyId, className, begin, end, null);
	}

	public List findByC_C(String companyId, String className, int begin,
		int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Website IN CLASS com.liferay.portal.service.persistence.WebsiteHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				WebsiteHBM websiteHBM = (WebsiteHBM)itr.next();
				list.add(WebsiteHBMUtil.model(websiteHBM));
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

	public com.liferay.portal.model.Website findByC_C_First(String companyId,
		String className, OrderByComparator obc)
		throws NoSuchWebsiteException, SystemException {
		List list = findByC_C(companyId, className, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Website exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "className=";
			msg += className;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchWebsiteException(msg);
		}
		else {
			return (com.liferay.portal.model.Website)list.get(0);
		}
	}

	public com.liferay.portal.model.Website findByC_C_Last(String companyId,
		String className, OrderByComparator obc)
		throws NoSuchWebsiteException, SystemException {
		int count = countByC_C(companyId, className);
		List list = findByC_C(companyId, className, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Website exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "className=";
			msg += className;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchWebsiteException(msg);
		}
		else {
			return (com.liferay.portal.model.Website)list.get(0);
		}
	}

	public com.liferay.portal.model.Website[] findByC_C_PrevAndNext(
		String websiteId, String companyId, String className,
		OrderByComparator obc) throws NoSuchWebsiteException, SystemException {
		com.liferay.portal.model.Website website = findByPrimaryKey(websiteId);
		int count = countByC_C(companyId, className);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Website IN CLASS com.liferay.portal.service.persistence.WebsiteHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					website, WebsiteHBMUtil.getInstance());
			com.liferay.portal.model.Website[] array = new com.liferay.portal.model.Website[3];
			array[0] = (com.liferay.portal.model.Website)objArray[0];
			array[1] = (com.liferay.portal.model.Website)objArray[1];
			array[2] = (com.liferay.portal.model.Website)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_C_C(String companyId, String className, String classPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Website IN CLASS com.liferay.portal.service.persistence.WebsiteHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				WebsiteHBM websiteHBM = (WebsiteHBM)itr.next();
				list.add(WebsiteHBMUtil.model(websiteHBM));
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

	public List findByC_C_C(String companyId, String className, String classPK,
		int begin, int end) throws SystemException {
		return findByC_C_C(companyId, className, classPK, begin, end, null);
	}

	public List findByC_C_C(String companyId, String className, String classPK,
		int begin, int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Website IN CLASS com.liferay.portal.service.persistence.WebsiteHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				WebsiteHBM websiteHBM = (WebsiteHBM)itr.next();
				list.add(WebsiteHBMUtil.model(websiteHBM));
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

	public com.liferay.portal.model.Website findByC_C_C_First(
		String companyId, String className, String classPK,
		OrderByComparator obc) throws NoSuchWebsiteException, SystemException {
		List list = findByC_C_C(companyId, className, classPK, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Website exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "className=";
			msg += className;
			msg += ", ";
			msg += "classPK=";
			msg += classPK;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchWebsiteException(msg);
		}
		else {
			return (com.liferay.portal.model.Website)list.get(0);
		}
	}

	public com.liferay.portal.model.Website findByC_C_C_Last(String companyId,
		String className, String classPK, OrderByComparator obc)
		throws NoSuchWebsiteException, SystemException {
		int count = countByC_C_C(companyId, className, classPK);
		List list = findByC_C_C(companyId, className, classPK, count - 1,
				count, obc);

		if (list.size() == 0) {
			String msg = "No Website exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "className=";
			msg += className;
			msg += ", ";
			msg += "classPK=";
			msg += classPK;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchWebsiteException(msg);
		}
		else {
			return (com.liferay.portal.model.Website)list.get(0);
		}
	}

	public com.liferay.portal.model.Website[] findByC_C_C_PrevAndNext(
		String websiteId, String companyId, String className, String classPK,
		OrderByComparator obc) throws NoSuchWebsiteException, SystemException {
		com.liferay.portal.model.Website website = findByPrimaryKey(websiteId);
		int count = countByC_C_C(companyId, className, classPK);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Website IN CLASS com.liferay.portal.service.persistence.WebsiteHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					website, WebsiteHBMUtil.getInstance());
			com.liferay.portal.model.Website[] array = new com.liferay.portal.model.Website[3];
			array[0] = (com.liferay.portal.model.Website)objArray[0];
			array[1] = (com.liferay.portal.model.Website)objArray[1];
			array[2] = (com.liferay.portal.model.Website)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_C_C_P(String companyId, String className,
		String classPK, boolean primary) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Website IN CLASS com.liferay.portal.service.persistence.WebsiteHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" AND ");
			query.append("primary_ = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);
			q.setBoolean(queryPos++, primary);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				WebsiteHBM websiteHBM = (WebsiteHBM)itr.next();
				list.add(WebsiteHBMUtil.model(websiteHBM));
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

	public List findByC_C_C_P(String companyId, String className,
		String classPK, boolean primary, int begin, int end)
		throws SystemException {
		return findByC_C_C_P(companyId, className, classPK, primary, begin,
			end, null);
	}

	public List findByC_C_C_P(String companyId, String className,
		String classPK, boolean primary, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Website IN CLASS com.liferay.portal.service.persistence.WebsiteHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" AND ");
			query.append("primary_ = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);
			q.setBoolean(queryPos++, primary);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				WebsiteHBM websiteHBM = (WebsiteHBM)itr.next();
				list.add(WebsiteHBMUtil.model(websiteHBM));
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

	public com.liferay.portal.model.Website findByC_C_C_P_First(
		String companyId, String className, String classPK, boolean primary,
		OrderByComparator obc) throws NoSuchWebsiteException, SystemException {
		List list = findByC_C_C_P(companyId, className, classPK, primary, 0, 1,
				obc);

		if (list.size() == 0) {
			String msg = "No Website exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "className=";
			msg += className;
			msg += ", ";
			msg += "classPK=";
			msg += classPK;
			msg += ", ";
			msg += "primary=";
			msg += primary;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchWebsiteException(msg);
		}
		else {
			return (com.liferay.portal.model.Website)list.get(0);
		}
	}

	public com.liferay.portal.model.Website findByC_C_C_P_Last(
		String companyId, String className, String classPK, boolean primary,
		OrderByComparator obc) throws NoSuchWebsiteException, SystemException {
		int count = countByC_C_C_P(companyId, className, classPK, primary);
		List list = findByC_C_C_P(companyId, className, classPK, primary,
				count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Website exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "className=";
			msg += className;
			msg += ", ";
			msg += "classPK=";
			msg += classPK;
			msg += ", ";
			msg += "primary=";
			msg += primary;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchWebsiteException(msg);
		}
		else {
			return (com.liferay.portal.model.Website)list.get(0);
		}
	}

	public com.liferay.portal.model.Website[] findByC_C_C_P_PrevAndNext(
		String websiteId, String companyId, String className, String classPK,
		boolean primary, OrderByComparator obc)
		throws NoSuchWebsiteException, SystemException {
		com.liferay.portal.model.Website website = findByPrimaryKey(websiteId);
		int count = countByC_C_C_P(companyId, className, classPK, primary);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Website IN CLASS com.liferay.portal.service.persistence.WebsiteHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" AND ");
			query.append("primary_ = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);
			q.setBoolean(queryPos++, primary);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					website, WebsiteHBMUtil.getInstance());
			com.liferay.portal.model.Website[] array = new com.liferay.portal.model.Website[3];
			array[0] = (com.liferay.portal.model.Website)objArray[0];
			array[1] = (com.liferay.portal.model.Website)objArray[1];
			array[2] = (com.liferay.portal.model.Website)objArray[2];

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
				"FROM Website IN CLASS com.liferay.portal.service.persistence.WebsiteHBM ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				WebsiteHBM websiteHBM = (WebsiteHBM)itr.next();
				list.add(WebsiteHBMUtil.model(websiteHBM));
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

	public void removeByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Website IN CLASS com.liferay.portal.service.persistence.WebsiteHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				WebsiteHBM websiteHBM = (WebsiteHBM)itr.next();
				session.delete(websiteHBM);
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

	public void removeByUserId(String userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Website IN CLASS com.liferay.portal.service.persistence.WebsiteHBM WHERE ");
			query.append("userId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				WebsiteHBM websiteHBM = (WebsiteHBM)itr.next();
				session.delete(websiteHBM);
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

	public void removeByC_C(String companyId, String className)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Website IN CLASS com.liferay.portal.service.persistence.WebsiteHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				WebsiteHBM websiteHBM = (WebsiteHBM)itr.next();
				session.delete(websiteHBM);
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

	public void removeByC_C_C(String companyId, String className, String classPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Website IN CLASS com.liferay.portal.service.persistence.WebsiteHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				WebsiteHBM websiteHBM = (WebsiteHBM)itr.next();
				session.delete(websiteHBM);
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

	public void removeByC_C_C_P(String companyId, String className,
		String classPK, boolean primary) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Website IN CLASS com.liferay.portal.service.persistence.WebsiteHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" AND ");
			query.append("primary_ = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);
			q.setBoolean(queryPos++, primary);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				WebsiteHBM websiteHBM = (WebsiteHBM)itr.next();
				session.delete(websiteHBM);
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

	public int countByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM Website IN CLASS com.liferay.portal.service.persistence.WebsiteHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

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

	public int countByUserId(String userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM Website IN CLASS com.liferay.portal.service.persistence.WebsiteHBM WHERE ");
			query.append("userId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userId);

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

	public int countByC_C(String companyId, String className)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM Website IN CLASS com.liferay.portal.service.persistence.WebsiteHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);

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

	public int countByC_C_C(String companyId, String className, String classPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM Website IN CLASS com.liferay.portal.service.persistence.WebsiteHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);

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

	public int countByC_C_C_P(String companyId, String className,
		String classPK, boolean primary) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM Website IN CLASS com.liferay.portal.service.persistence.WebsiteHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" AND ");
			query.append("primary_ = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);
			q.setBoolean(queryPos++, primary);

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

	private static Log _log = LogFactory.getLog(WebsitePersistence.class);
}