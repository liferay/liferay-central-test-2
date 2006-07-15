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

import com.liferay.portal.NoSuchImageException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.persistence.BasePersistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * <a href="ImagePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ImagePersistence extends BasePersistence {
	public Image create(String imageId) {
		Image image = new Image();
		image.setNew(true);
		image.setPrimaryKey(imageId);

		return image;
	}

	public Image remove(String imageId)
		throws NoSuchImageException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Image image = (Image)session.get(Image.class, imageId);

			if (image == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Image exists with the primary key " +
						imageId.toString());
				}

				throw new NoSuchImageException(
					"No Image exists with the primary key " +
					imageId.toString());
			}

			session.delete(image);
			session.flush();

			return image;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Image update(
		com.liferay.portal.model.Image image) throws SystemException {
		Session session = null;

		try {
			if (image.isNew() || image.isModified()) {
				session = openSession();

				if (image.isNew()) {
					Image imageModel = new Image();
					imageModel.setImageId(image.getImageId());
					imageModel.setModifiedDate(image.getModifiedDate());
					imageModel.setText(image.getText());
					session.save(imageModel);
					session.flush();
				}
				else {
					Image imageModel = (Image)session.get(Image.class,
							image.getPrimaryKey());

					if (imageModel != null) {
						imageModel.setModifiedDate(image.getModifiedDate());
						imageModel.setText(image.getText());
						session.flush();
					}
					else {
						imageModel = new Image();
						imageModel.setImageId(image.getImageId());
						imageModel.setModifiedDate(image.getModifiedDate());
						imageModel.setText(image.getText());
						session.save(imageModel);
						session.flush();
					}
				}

				image.setNew(false);
				image.setModified(false);
			}

			return image;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Image findByPrimaryKey(String imageId)
		throws NoSuchImageException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Image image = (Image)session.get(Image.class, imageId);

			if (image == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Image exists with the primary key " +
						imageId.toString());
				}

				throw new NoSuchImageException(
					"No Image exists with the primary key " +
					imageId.toString());
			}

			return image;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Image fetchByPrimaryKey(String imageId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (Image)session.get(Image.class, imageId);
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
			query.append("FROM com.liferay.portal.model.Image ");
			query.append("ORDER BY ");
			query.append("imageId ASC");

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

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(ImagePersistence.class);
}