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
import com.liferay.portal.service.persistence.BasePersistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="DataTrackerPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DataTrackerPersistence extends BasePersistence {
	public com.liferay.portal.model.DataTracker create(String dataTrackerId) {
		DataTrackerHBM dataTrackerHBM = new DataTrackerHBM();
		dataTrackerHBM.setNew(true);
		dataTrackerHBM.setPrimaryKey(dataTrackerId);

		return DataTrackerHBMUtil.model(dataTrackerHBM);
	}

	public com.liferay.portal.model.DataTracker remove(String dataTrackerId)
		throws NoSuchDataTrackerException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DataTrackerHBM dataTrackerHBM = (DataTrackerHBM)session.get(DataTrackerHBM.class,
					dataTrackerId);

			if (dataTrackerHBM == null) {
				_log.warn("No DataTracker exists with the primary key " +
					dataTrackerId.toString());
				throw new NoSuchDataTrackerException(
					"No DataTracker exists with the primary key " +
					dataTrackerId.toString());
			}

			session.delete(dataTrackerHBM);
			session.flush();

			return DataTrackerHBMUtil.model(dataTrackerHBM);
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
					DataTrackerHBM dataTrackerHBM = new DataTrackerHBM();
					dataTrackerHBM.setDataTrackerId(dataTracker.getDataTrackerId());
					dataTrackerHBM.setCompanyId(dataTracker.getCompanyId());
					dataTrackerHBM.setCreatedOn(dataTracker.getCreatedOn());
					dataTrackerHBM.setCreatedByUserId(dataTracker.getCreatedByUserId());
					dataTrackerHBM.setCreatedByUserName(dataTracker.getCreatedByUserName());
					dataTrackerHBM.setUpdatedOn(dataTracker.getUpdatedOn());
					dataTrackerHBM.setUpdatedBy(dataTracker.getUpdatedBy());
					dataTrackerHBM.setClassName(dataTracker.getClassName());
					dataTrackerHBM.setClassPK(dataTracker.getClassPK());
					dataTrackerHBM.setActive(dataTracker.getActive());
					session.save(dataTrackerHBM);
					session.flush();
				}
				else {
					DataTrackerHBM dataTrackerHBM = (DataTrackerHBM)session.get(DataTrackerHBM.class,
							dataTracker.getPrimaryKey());

					if (dataTrackerHBM != null) {
						dataTrackerHBM.setCompanyId(dataTracker.getCompanyId());
						dataTrackerHBM.setCreatedOn(dataTracker.getCreatedOn());
						dataTrackerHBM.setCreatedByUserId(dataTracker.getCreatedByUserId());
						dataTrackerHBM.setCreatedByUserName(dataTracker.getCreatedByUserName());
						dataTrackerHBM.setUpdatedOn(dataTracker.getUpdatedOn());
						dataTrackerHBM.setUpdatedBy(dataTracker.getUpdatedBy());
						dataTrackerHBM.setClassName(dataTracker.getClassName());
						dataTrackerHBM.setClassPK(dataTracker.getClassPK());
						dataTrackerHBM.setActive(dataTracker.getActive());
						session.flush();
					}
					else {
						dataTrackerHBM = new DataTrackerHBM();
						dataTrackerHBM.setDataTrackerId(dataTracker.getDataTrackerId());
						dataTrackerHBM.setCompanyId(dataTracker.getCompanyId());
						dataTrackerHBM.setCreatedOn(dataTracker.getCreatedOn());
						dataTrackerHBM.setCreatedByUserId(dataTracker.getCreatedByUserId());
						dataTrackerHBM.setCreatedByUserName(dataTracker.getCreatedByUserName());
						dataTrackerHBM.setUpdatedOn(dataTracker.getUpdatedOn());
						dataTrackerHBM.setUpdatedBy(dataTracker.getUpdatedBy());
						dataTrackerHBM.setClassName(dataTracker.getClassName());
						dataTrackerHBM.setClassPK(dataTracker.getClassPK());
						dataTrackerHBM.setActive(dataTracker.getActive());
						session.save(dataTrackerHBM);
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

	public com.liferay.portal.model.DataTracker findByPrimaryKey(
		String dataTrackerId)
		throws NoSuchDataTrackerException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DataTrackerHBM dataTrackerHBM = (DataTrackerHBM)session.get(DataTrackerHBM.class,
					dataTrackerId);

			if (dataTrackerHBM == null) {
				_log.warn("No DataTracker exists with the primary key " +
					dataTrackerId.toString());
				throw new NoSuchDataTrackerException(
					"No DataTracker exists with the primary key " +
					dataTrackerId.toString());
			}

			return DataTrackerHBMUtil.model(dataTrackerHBM);
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
				"FROM DataTracker IN CLASS com.liferay.portal.service.persistence.DataTrackerHBM ");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				DataTrackerHBM dataTrackerHBM = (DataTrackerHBM)itr.next();
				list.add(DataTrackerHBMUtil.model(dataTrackerHBM));
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

	private static Log _log = LogFactory.getLog(DataTrackerPersistence.class);
}