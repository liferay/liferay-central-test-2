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

import com.liferay.portal.NoSuchDataTrackerException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.DataTracker;
import com.liferay.portal.service.persistence.BasePersistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * <a href="DataTrackerPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DataTrackerPersistence extends BasePersistence {
	public DataTracker create(String dataTrackerId) {
		DataTracker dataTracker = new DataTracker();
		dataTracker.setNew(true);
		dataTracker.setPrimaryKey(dataTrackerId);

		return dataTracker;
	}

	public DataTracker remove(String dataTrackerId)
		throws NoSuchDataTrackerException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DataTracker dataTracker = (DataTracker)session.get(DataTracker.class,
					dataTrackerId);

			if (dataTracker == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No DataTracker exists with the primary key " +
						dataTrackerId.toString());
				}

				throw new NoSuchDataTrackerException(
					"No DataTracker exists with the primary key " +
					dataTrackerId.toString());
			}

			session.delete(dataTracker);
			session.flush();

			return dataTracker;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.DataTracker update(
		com.liferay.portal.model.DataTracker dataTracker)
		throws SystemException {
		Session session = null;

		try {
			if (dataTracker.isNew() || dataTracker.isModified()) {
				session = openSession();

				if (dataTracker.isNew()) {
					DataTracker dataTrackerModel = new DataTracker();
					dataTrackerModel.setDataTrackerId(dataTracker.getDataTrackerId());
					dataTrackerModel.setCompanyId(dataTracker.getCompanyId());
					dataTrackerModel.setCreatedOn(dataTracker.getCreatedOn());
					dataTrackerModel.setCreatedByUserId(dataTracker.getCreatedByUserId());
					dataTrackerModel.setCreatedByUserName(dataTracker.getCreatedByUserName());
					dataTrackerModel.setUpdatedOn(dataTracker.getUpdatedOn());
					dataTrackerModel.setUpdatedBy(dataTracker.getUpdatedBy());
					dataTrackerModel.setClassName(dataTracker.getClassName());
					dataTrackerModel.setClassPK(dataTracker.getClassPK());
					dataTrackerModel.setActive(dataTracker.getActive());
					session.save(dataTrackerModel);
					session.flush();
				}
				else {
					DataTracker dataTrackerModel = (DataTracker)session.get(DataTracker.class,
							dataTracker.getPrimaryKey());

					if (dataTrackerModel != null) {
						dataTrackerModel.setCompanyId(dataTracker.getCompanyId());
						dataTrackerModel.setCreatedOn(dataTracker.getCreatedOn());
						dataTrackerModel.setCreatedByUserId(dataTracker.getCreatedByUserId());
						dataTrackerModel.setCreatedByUserName(dataTracker.getCreatedByUserName());
						dataTrackerModel.setUpdatedOn(dataTracker.getUpdatedOn());
						dataTrackerModel.setUpdatedBy(dataTracker.getUpdatedBy());
						dataTrackerModel.setClassName(dataTracker.getClassName());
						dataTrackerModel.setClassPK(dataTracker.getClassPK());
						dataTrackerModel.setActive(dataTracker.getActive());
						session.flush();
					}
					else {
						dataTrackerModel = new DataTracker();
						dataTrackerModel.setDataTrackerId(dataTracker.getDataTrackerId());
						dataTrackerModel.setCompanyId(dataTracker.getCompanyId());
						dataTrackerModel.setCreatedOn(dataTracker.getCreatedOn());
						dataTrackerModel.setCreatedByUserId(dataTracker.getCreatedByUserId());
						dataTrackerModel.setCreatedByUserName(dataTracker.getCreatedByUserName());
						dataTrackerModel.setUpdatedOn(dataTracker.getUpdatedOn());
						dataTrackerModel.setUpdatedBy(dataTracker.getUpdatedBy());
						dataTrackerModel.setClassName(dataTracker.getClassName());
						dataTrackerModel.setClassPK(dataTracker.getClassPK());
						dataTrackerModel.setActive(dataTracker.getActive());
						session.save(dataTrackerModel);
						session.flush();
					}
				}

				dataTracker.setNew(false);
				dataTracker.setModified(false);
			}

			return dataTracker;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public DataTracker findByPrimaryKey(String dataTrackerId)
		throws NoSuchDataTrackerException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DataTracker dataTracker = (DataTracker)session.get(DataTracker.class,
					dataTrackerId);

			if (dataTracker == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No DataTracker exists with the primary key " +
						dataTrackerId.toString());
				}

				throw new NoSuchDataTrackerException(
					"No DataTracker exists with the primary key " +
					dataTrackerId.toString());
			}

			return dataTracker;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public DataTracker fetchByPrimaryKey(String dataTrackerId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (DataTracker)session.get(DataTracker.class, dataTrackerId);
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
			query.append("FROM com.liferay.portal.model.DataTracker ");

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

	private static Log _log = LogFactory.getLog(DataTrackerPersistence.class);
}