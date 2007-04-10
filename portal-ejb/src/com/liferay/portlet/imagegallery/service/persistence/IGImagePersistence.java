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

package com.liferay.portlet.imagegallery.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.HibernateUtil;

import com.liferay.portlet.imagegallery.NoSuchImageException;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.model.impl.IGImageImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="IGImagePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class IGImagePersistence extends BasePersistence {
	public IGImage create(IGImagePK igImagePK) {
		IGImage igImage = new IGImageImpl();
		igImage.setNew(true);
		igImage.setPrimaryKey(igImagePK);

		return igImage;
	}

	public IGImage remove(IGImagePK igImagePK)
		throws NoSuchImageException, SystemException {
		Session session = null;

		try {
			session = openSession();

			IGImage igImage = (IGImage)session.get(IGImageImpl.class, igImagePK);

			if (igImage == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No IGImage exists with the primary key " +
						igImagePK);
				}

				throw new NoSuchImageException(
					"No IGImage exists with the primary key " + igImagePK);
			}

			return remove(igImage);
		}
		catch (NoSuchImageException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public IGImage remove(IGImage igImage) throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(igImage);
			session.flush();

			return igImage;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.imagegallery.model.IGImage update(
		com.liferay.portlet.imagegallery.model.IGImage igImage)
		throws SystemException {
		return update(igImage, false);
	}

	public com.liferay.portlet.imagegallery.model.IGImage update(
		com.liferay.portlet.imagegallery.model.IGImage igImage,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(igImage);
			}
			else {
				if (igImage.isNew()) {
					session.save(igImage);
				}
			}

			session.flush();
			igImage.setNew(false);

			return igImage;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public IGImage findByPrimaryKey(IGImagePK igImagePK)
		throws NoSuchImageException, SystemException {
		IGImage igImage = fetchByPrimaryKey(igImagePK);

		if (igImage == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No IGImage exists with the primary key " +
					igImagePK);
			}

			throw new NoSuchImageException(
				"No IGImage exists with the primary key " + igImagePK);
		}

		return igImage;
	}

	public IGImage fetchByPrimaryKey(IGImagePK igImagePK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (IGImage)session.get(IGImageImpl.class, igImagePK);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByFolderId(String folderId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.imagegallery.model.IGImage WHERE ");

			if (folderId == null) {
				query.append("folderId IS NULL");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("imageId ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (folderId != null) {
				q.setString(queryPos++, folderId);
			}

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByFolderId(String folderId, int begin, int end)
		throws SystemException {
		return findByFolderId(folderId, begin, end, null);
	}

	public List findByFolderId(String folderId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.imagegallery.model.IGImage WHERE ");

			if (folderId == null) {
				query.append("folderId IS NULL");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("imageId ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (folderId != null) {
				q.setString(queryPos++, folderId);
			}

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public IGImage findByFolderId_First(String folderId, OrderByComparator obc)
		throws NoSuchImageException, SystemException {
		List list = findByFolderId(folderId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No IGImage exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("folderId=");
			msg.append(folderId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchImageException(msg.toString());
		}
		else {
			return (IGImage)list.get(0);
		}
	}

	public IGImage findByFolderId_Last(String folderId, OrderByComparator obc)
		throws NoSuchImageException, SystemException {
		int count = countByFolderId(folderId);
		List list = findByFolderId(folderId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No IGImage exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("folderId=");
			msg.append(folderId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchImageException(msg.toString());
		}
		else {
			return (IGImage)list.get(0);
		}
	}

	public IGImage[] findByFolderId_PrevAndNext(IGImagePK igImagePK,
		String folderId, OrderByComparator obc)
		throws NoSuchImageException, SystemException {
		IGImage igImage = findByPrimaryKey(igImagePK);
		int count = countByFolderId(folderId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.imagegallery.model.IGImage WHERE ");

			if (folderId == null) {
				query.append("folderId IS NULL");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("imageId ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (folderId != null) {
				q.setString(queryPos++, folderId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, igImage);
			IGImage[] array = new IGImageImpl[3];
			array[0] = (IGImage)objArray[0];
			array[1] = (IGImage)objArray[1];
			array[2] = (IGImage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portlet.imagegallery.model.IGImage ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("imageId ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByFolderId(String folderId) throws SystemException {
		Iterator itr = findByFolderId(folderId).iterator();

		while (itr.hasNext()) {
			IGImage igImage = (IGImage)itr.next();
			remove(igImage);
		}
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((IGImage)itr.next());
		}
	}

	public int countByFolderId(String folderId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.imagegallery.model.IGImage WHERE ");

			if (folderId == null) {
				query.append("folderId IS NULL");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (folderId != null) {
				q.setString(queryPos++, folderId);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portlet.imagegallery.model.IGImage");

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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(IGImagePersistence.class);
}