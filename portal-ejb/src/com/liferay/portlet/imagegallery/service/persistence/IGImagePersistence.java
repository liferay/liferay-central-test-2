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

package com.liferay.portlet.imagegallery.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.imagegallery.NoSuchImageException;

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
 * <a href="IGImagePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class IGImagePersistence extends BasePersistence {
	public com.liferay.portlet.imagegallery.model.IGImage create(
		IGImagePK igImagePK) {
		IGImageHBM igImageHBM = new IGImageHBM();
		igImageHBM.setNew(true);
		igImageHBM.setPrimaryKey(igImagePK);

		return IGImageHBMUtil.model(igImageHBM);
	}

	public com.liferay.portlet.imagegallery.model.IGImage remove(
		IGImagePK igImagePK) throws NoSuchImageException, SystemException {
		Session session = null;

		try {
			session = openSession();

			IGImageHBM igImageHBM = (IGImageHBM)session.get(IGImageHBM.class,
					igImagePK);

			if (igImageHBM == null) {
				_log.warn("No IGImage exists with the primary key " +
					igImagePK.toString());
				throw new NoSuchImageException(
					"No IGImage exists with the primary key " +
					igImagePK.toString());
			}

			session.delete(igImageHBM);
			session.flush();

			return IGImageHBMUtil.model(igImageHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.imagegallery.model.IGImage update(
		com.liferay.portlet.imagegallery.model.IGImage igImage)
		throws SystemException {
		Session session = null;

		try {
			if (igImage.isNew() || igImage.isModified()) {
				session = openSession();

				if (igImage.isNew()) {
					IGImageHBM igImageHBM = new IGImageHBM();
					igImageHBM.setCompanyId(igImage.getCompanyId());
					igImageHBM.setImageId(igImage.getImageId());
					igImageHBM.setUserId(igImage.getUserId());
					igImageHBM.setCreateDate(igImage.getCreateDate());
					igImageHBM.setModifiedDate(igImage.getModifiedDate());
					igImageHBM.setFolderId(igImage.getFolderId());
					igImageHBM.setDescription(igImage.getDescription());
					igImageHBM.setHeight(igImage.getHeight());
					igImageHBM.setWidth(igImage.getWidth());
					igImageHBM.setSize(igImage.getSize());
					session.save(igImageHBM);
					session.flush();
				}
				else {
					IGImageHBM igImageHBM = (IGImageHBM)session.get(IGImageHBM.class,
							igImage.getPrimaryKey());

					if (igImageHBM != null) {
						igImageHBM.setUserId(igImage.getUserId());
						igImageHBM.setCreateDate(igImage.getCreateDate());
						igImageHBM.setModifiedDate(igImage.getModifiedDate());
						igImageHBM.setFolderId(igImage.getFolderId());
						igImageHBM.setDescription(igImage.getDescription());
						igImageHBM.setHeight(igImage.getHeight());
						igImageHBM.setWidth(igImage.getWidth());
						igImageHBM.setSize(igImage.getSize());
						session.flush();
					}
					else {
						igImageHBM = new IGImageHBM();
						igImageHBM.setCompanyId(igImage.getCompanyId());
						igImageHBM.setImageId(igImage.getImageId());
						igImageHBM.setUserId(igImage.getUserId());
						igImageHBM.setCreateDate(igImage.getCreateDate());
						igImageHBM.setModifiedDate(igImage.getModifiedDate());
						igImageHBM.setFolderId(igImage.getFolderId());
						igImageHBM.setDescription(igImage.getDescription());
						igImageHBM.setHeight(igImage.getHeight());
						igImageHBM.setWidth(igImage.getWidth());
						igImageHBM.setSize(igImage.getSize());
						session.save(igImageHBM);
						session.flush();
					}
				}

				igImage.setNew(false);
				igImage.setModified(false);
			}

			return igImage;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.imagegallery.model.IGImage findByPrimaryKey(
		IGImagePK igImagePK) throws NoSuchImageException, SystemException {
		Session session = null;

		try {
			session = openSession();

			IGImageHBM igImageHBM = (IGImageHBM)session.get(IGImageHBM.class,
					igImagePK);

			if (igImageHBM == null) {
				_log.warn("No IGImage exists with the primary key " +
					igImagePK.toString());
				throw new NoSuchImageException(
					"No IGImage exists with the primary key " +
					igImagePK.toString());
			}

			return IGImageHBMUtil.model(igImageHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByFolderId(String folderId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM IGImage IN CLASS com.liferay.portlet.imagegallery.service.persistence.IGImageHBM WHERE ");

			if (folderId == null) {
				query.append("folderId is null");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("imageId ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (folderId != null) {
				q.setString(queryPos++, folderId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				IGImageHBM igImageHBM = (IGImageHBM)itr.next();
				list.add(IGImageHBMUtil.model(igImageHBM));
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

	public List findByFolderId(String folderId, int begin, int end)
		throws SystemException {
		return findByFolderId(folderId, begin, end, null);
	}

	public List findByFolderId(String folderId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM IGImage IN CLASS com.liferay.portlet.imagegallery.service.persistence.IGImageHBM WHERE ");

			if (folderId == null) {
				query.append("folderId is null");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("imageId ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (folderId != null) {
				q.setString(queryPos++, folderId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				IGImageHBM igImageHBM = (IGImageHBM)itr.next();
				list.add(IGImageHBMUtil.model(igImageHBM));
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

	public com.liferay.portlet.imagegallery.model.IGImage findByFolderId_First(
		String folderId, OrderByComparator obc)
		throws NoSuchImageException, SystemException {
		List list = findByFolderId(folderId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No IGImage exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "folderId=";
			msg += folderId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchImageException(msg);
		}
		else {
			return (com.liferay.portlet.imagegallery.model.IGImage)list.get(0);
		}
	}

	public com.liferay.portlet.imagegallery.model.IGImage findByFolderId_Last(
		String folderId, OrderByComparator obc)
		throws NoSuchImageException, SystemException {
		int count = countByFolderId(folderId);
		List list = findByFolderId(folderId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No IGImage exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "folderId=";
			msg += folderId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchImageException(msg);
		}
		else {
			return (com.liferay.portlet.imagegallery.model.IGImage)list.get(0);
		}
	}

	public com.liferay.portlet.imagegallery.model.IGImage[] findByFolderId_PrevAndNext(
		IGImagePK igImagePK, String folderId, OrderByComparator obc)
		throws NoSuchImageException, SystemException {
		com.liferay.portlet.imagegallery.model.IGImage igImage = findByPrimaryKey(igImagePK);
		int count = countByFolderId(folderId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM IGImage IN CLASS com.liferay.portlet.imagegallery.service.persistence.IGImageHBM WHERE ");

			if (folderId == null) {
				query.append("folderId is null");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("imageId ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (folderId != null) {
				q.setString(queryPos++, folderId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					igImage, IGImageHBMUtil.getInstance());
			com.liferay.portlet.imagegallery.model.IGImage[] array = new com.liferay.portlet.imagegallery.model.IGImage[3];
			array[0] = (com.liferay.portlet.imagegallery.model.IGImage)objArray[0];
			array[1] = (com.liferay.portlet.imagegallery.model.IGImage)objArray[1];
			array[2] = (com.liferay.portlet.imagegallery.model.IGImage)objArray[2];

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
				"FROM IGImage IN CLASS com.liferay.portlet.imagegallery.service.persistence.IGImageHBM ");
			query.append("ORDER BY ");
			query.append("imageId ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				IGImageHBM igImageHBM = (IGImageHBM)itr.next();
				list.add(IGImageHBMUtil.model(igImageHBM));
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

	public void removeByFolderId(String folderId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM IGImage IN CLASS com.liferay.portlet.imagegallery.service.persistence.IGImageHBM WHERE ");

			if (folderId == null) {
				query.append("folderId is null");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("imageId ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (folderId != null) {
				q.setString(queryPos++, folderId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				IGImageHBM igImageHBM = (IGImageHBM)itr.next();
				session.delete(igImageHBM);
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

	public int countByFolderId(String folderId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM IGImage IN CLASS com.liferay.portlet.imagegallery.service.persistence.IGImageHBM WHERE ");

			if (folderId == null) {
				query.append("folderId is null");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
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
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	private static Log _log = LogFactory.getLog(IGImagePersistence.class);
}