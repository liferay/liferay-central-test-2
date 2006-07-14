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

import com.liferay.portal.NoSuchLayoutSetException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.service.persistence.BasePersistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * <a href="LayoutSetPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class LayoutSetPersistence extends BasePersistence {
	public LayoutSet create(String ownerId) {
		LayoutSet layoutSet = new LayoutSet();
		layoutSet.setNew(true);
		layoutSet.setPrimaryKey(ownerId);

		return layoutSet;
	}

	public LayoutSet remove(String ownerId)
		throws NoSuchLayoutSetException, SystemException {
		Session session = null;

		try {
			session = openSession();

			LayoutSet layoutSet = (LayoutSet)session.get(LayoutSet.class,
					ownerId);

			if (layoutSet == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No LayoutSet exists with the primary key " +
						ownerId.toString());
				}

				throw new NoSuchLayoutSetException(
					"No LayoutSet exists with the primary key " +
					ownerId.toString());
			}

			session.delete(layoutSet);
			session.flush();

			return layoutSet;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.LayoutSet update(
		com.liferay.portal.model.LayoutSet layoutSet) throws SystemException {
		Session session = null;

		try {
			if (layoutSet.isNew() || layoutSet.isModified()) {
				session = openSession();

				if (layoutSet.isNew()) {
					LayoutSet layoutSetModel = new LayoutSet();
					layoutSetModel.setOwnerId(layoutSet.getOwnerId());
					layoutSetModel.setCompanyId(layoutSet.getCompanyId());
					layoutSetModel.setGroupId(layoutSet.getGroupId());
					layoutSetModel.setUserId(layoutSet.getUserId());
					layoutSetModel.setPrivateLayout(layoutSet.getPrivateLayout());
					layoutSetModel.setThemeId(layoutSet.getThemeId());
					layoutSetModel.setColorSchemeId(layoutSet.getColorSchemeId());
					layoutSetModel.setPageCount(layoutSet.getPageCount());
					session.save(layoutSetModel);
					session.flush();
				}
				else {
					LayoutSet layoutSetModel = (LayoutSet)session.get(LayoutSet.class,
							layoutSet.getPrimaryKey());

					if (layoutSetModel != null) {
						layoutSetModel.setCompanyId(layoutSet.getCompanyId());
						layoutSetModel.setGroupId(layoutSet.getGroupId());
						layoutSetModel.setUserId(layoutSet.getUserId());
						layoutSetModel.setPrivateLayout(layoutSet.getPrivateLayout());
						layoutSetModel.setThemeId(layoutSet.getThemeId());
						layoutSetModel.setColorSchemeId(layoutSet.getColorSchemeId());
						layoutSetModel.setPageCount(layoutSet.getPageCount());
						session.flush();
					}
					else {
						layoutSetModel = new LayoutSet();
						layoutSetModel.setOwnerId(layoutSet.getOwnerId());
						layoutSetModel.setCompanyId(layoutSet.getCompanyId());
						layoutSetModel.setGroupId(layoutSet.getGroupId());
						layoutSetModel.setUserId(layoutSet.getUserId());
						layoutSetModel.setPrivateLayout(layoutSet.getPrivateLayout());
						layoutSetModel.setThemeId(layoutSet.getThemeId());
						layoutSetModel.setColorSchemeId(layoutSet.getColorSchemeId());
						layoutSetModel.setPageCount(layoutSet.getPageCount());
						session.save(layoutSetModel);
						session.flush();
					}
				}

				layoutSet.setNew(false);
				layoutSet.setModified(false);
			}

			return layoutSet;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public LayoutSet findByPrimaryKey(String ownerId)
		throws NoSuchLayoutSetException, SystemException {
		Session session = null;

		try {
			session = openSession();

			LayoutSet layoutSet = (LayoutSet)session.get(LayoutSet.class,
					ownerId);

			if (layoutSet == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No LayoutSet exists with the primary key " +
						ownerId.toString());
					throw new NoSuchLayoutSetException(
						"No LayoutSet exists with the primary key " +
						ownerId.toString());
				}
			}

			return layoutSet;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public LayoutSet fetchByPrimaryKey(String ownerId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (LayoutSet)session.get(LayoutSet.class, ownerId);
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
			query.append("FROM com.liferay.portal.model.LayoutSet ");

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

	private static Log _log = LogFactory.getLog(LayoutSetPersistence.class);
}