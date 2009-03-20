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

package com.liferay.portlet.imagegallery.service.persistence;

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

import com.liferay.portlet.imagegallery.NoSuchImageException;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.model.impl.IGImageImpl;
import com.liferay.portlet.imagegallery.model.impl.IGImageModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="IGImagePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class IGImagePersistenceImpl extends BasePersistenceImpl
	implements IGImagePersistence {
	public IGImage create(long imageId) {
		IGImage igImage = new IGImageImpl();

		igImage.setNew(true);
		igImage.setPrimaryKey(imageId);

		String uuid = PortalUUIDUtil.generate();

		igImage.setUuid(uuid);

		return igImage;
	}

	public IGImage remove(long imageId)
		throws NoSuchImageException, SystemException {
		Session session = null;

		try {
			session = openSession();

			IGImage igImage = (IGImage)session.get(IGImageImpl.class,
					new Long(imageId));

			if (igImage == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No IGImage exists with the primary key " +
						imageId);
				}

				throw new NoSuchImageException(
					"No IGImage exists with the primary key " + imageId);
			}

			return remove(igImage);
		}
		catch (NoSuchImageException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public IGImage remove(IGImage igImage) throws SystemException {
		for (ModelListener<IGImage> listener : listeners) {
			listener.onBeforeRemove(igImage);
		}

		igImage = removeImpl(igImage);

		for (ModelListener<IGImage> listener : listeners) {
			listener.onAfterRemove(igImage);
		}

		return igImage;
	}

	protected IGImage removeImpl(IGImage igImage) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(IGImageImpl.class,
						igImage.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(igImage);

			session.flush();

			return igImage;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(IGImage.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(IGImage igImage, boolean merge)</code>.
	 */
	public IGImage update(IGImage igImage) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(IGImage igImage) method. Use update(IGImage igImage, boolean merge) instead.");
		}

		return update(igImage, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        igImage the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when igImage is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public IGImage update(IGImage igImage, boolean merge)
		throws SystemException {
		boolean isNew = igImage.isNew();

		for (ModelListener<IGImage> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(igImage);
			}
			else {
				listener.onBeforeUpdate(igImage);
			}
		}

		igImage = updateImpl(igImage, merge);

		for (ModelListener<IGImage> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(igImage);
			}
			else {
				listener.onAfterUpdate(igImage);
			}
		}

		return igImage;
	}

	public IGImage updateImpl(
		com.liferay.portlet.imagegallery.model.IGImage igImage, boolean merge)
		throws SystemException {
		if (Validator.isNull(igImage.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			igImage.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, igImage, merge);

			igImage.setNew(false);

			return igImage;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(IGImage.class.getName());
		}
	}

	public IGImage findByPrimaryKey(long imageId)
		throws NoSuchImageException, SystemException {
		IGImage igImage = fetchByPrimaryKey(imageId);

		if (igImage == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No IGImage exists with the primary key " + imageId);
			}

			throw new NoSuchImageException(
				"No IGImage exists with the primary key " + imageId);
		}

		return igImage;
	}

	public IGImage fetchByPrimaryKey(long imageId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (IGImage)session.get(IGImageImpl.class, new Long(imageId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<IGImage> findByUuid(String uuid) throws SystemException {
		boolean finderClassNameCacheEnabled = IGImageModelImpl.CACHE_ENABLED;
		String finderClassName = IGImage.class.getName();
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
					"FROM com.liferay.portlet.imagegallery.model.IGImage WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("imageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				List<IGImage> list = q.list();

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
			return (List<IGImage>)result;
		}
	}

	public List<IGImage> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<IGImage> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = IGImageModelImpl.CACHE_ENABLED;
		String finderClassName = IGImage.class.getName();
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
					"FROM com.liferay.portlet.imagegallery.model.IGImage WHERE ");

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

				else {
					query.append("ORDER BY ");

					query.append("imageId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				List<IGImage> list = (List<IGImage>)QueryUtil.list(q,
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
			return (List<IGImage>)result;
		}
	}

	public IGImage findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchImageException, SystemException {
		List<IGImage> list = findByUuid(uuid, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No IGImage exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public IGImage findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchImageException, SystemException {
		int count = countByUuid(uuid);

		List<IGImage> list = findByUuid(uuid, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No IGImage exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public IGImage[] findByUuid_PrevAndNext(long imageId, String uuid,
		OrderByComparator obc) throws NoSuchImageException, SystemException {
		IGImage igImage = findByPrimaryKey(imageId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.imagegallery.model.IGImage WHERE ");

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

			else {
				query.append("ORDER BY ");

				query.append("imageId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, igImage);

			IGImage[] array = new IGImageImpl[3];

			array[0] = (IGImage)objArray[0];
			array[1] = (IGImage)objArray[1];
			array[2] = (IGImage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<IGImage> findByFolderId(long folderId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = IGImageModelImpl.CACHE_ENABLED;
		String finderClassName = IGImage.class.getName();
		String finderMethodName = "findByFolderId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(folderId) };

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
					"FROM com.liferay.portlet.imagegallery.model.IGImage WHERE ");

				query.append("folderId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("imageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

				List<IGImage> list = q.list();

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
			return (List<IGImage>)result;
		}
	}

	public List<IGImage> findByFolderId(long folderId, int start, int end)
		throws SystemException {
		return findByFolderId(folderId, start, end, null);
	}

	public List<IGImage> findByFolderId(long folderId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = IGImageModelImpl.CACHE_ENABLED;
		String finderClassName = IGImage.class.getName();
		String finderMethodName = "findByFolderId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(folderId),
				
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
					"FROM com.liferay.portlet.imagegallery.model.IGImage WHERE ");

				query.append("folderId = ?");

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

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

				List<IGImage> list = (List<IGImage>)QueryUtil.list(q,
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
			return (List<IGImage>)result;
		}
	}

	public IGImage findByFolderId_First(long folderId, OrderByComparator obc)
		throws NoSuchImageException, SystemException {
		List<IGImage> list = findByFolderId(folderId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No IGImage exists with the key {");

			msg.append("folderId=" + folderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public IGImage findByFolderId_Last(long folderId, OrderByComparator obc)
		throws NoSuchImageException, SystemException {
		int count = countByFolderId(folderId);

		List<IGImage> list = findByFolderId(folderId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No IGImage exists with the key {");

			msg.append("folderId=" + folderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public IGImage[] findByFolderId_PrevAndNext(long imageId, long folderId,
		OrderByComparator obc) throws NoSuchImageException, SystemException {
		IGImage igImage = findByPrimaryKey(imageId);

		int count = countByFolderId(folderId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.imagegallery.model.IGImage WHERE ");

			query.append("folderId = ?");

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

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(folderId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, igImage);

			IGImage[] array = new IGImageImpl[3];

			array[0] = (IGImage)objArray[0];
			array[1] = (IGImage)objArray[1];
			array[2] = (IGImage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public IGImage findBySmallImageId(long smallImageId)
		throws NoSuchImageException, SystemException {
		IGImage igImage = fetchBySmallImageId(smallImageId);

		if (igImage == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No IGImage exists with the key {");

			msg.append("smallImageId=" + smallImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchImageException(msg.toString());
		}

		return igImage;
	}

	public IGImage fetchBySmallImageId(long smallImageId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = IGImageModelImpl.CACHE_ENABLED;
		String finderClassName = IGImage.class.getName();
		String finderMethodName = "fetchBySmallImageId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(smallImageId) };

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
					"FROM com.liferay.portlet.imagegallery.model.IGImage WHERE ");

				query.append("smallImageId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("imageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

				List<IGImage> list = q.list();

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
			List<IGImage> list = (List<IGImage>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public IGImage findByLargeImageId(long largeImageId)
		throws NoSuchImageException, SystemException {
		IGImage igImage = fetchByLargeImageId(largeImageId);

		if (igImage == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No IGImage exists with the key {");

			msg.append("largeImageId=" + largeImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchImageException(msg.toString());
		}

		return igImage;
	}

	public IGImage fetchByLargeImageId(long largeImageId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = IGImageModelImpl.CACHE_ENABLED;
		String finderClassName = IGImage.class.getName();
		String finderMethodName = "fetchByLargeImageId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(largeImageId) };

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
					"FROM com.liferay.portlet.imagegallery.model.IGImage WHERE ");

				query.append("largeImageId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("imageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(largeImageId);

				List<IGImage> list = q.list();

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
			List<IGImage> list = (List<IGImage>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public IGImage findByCustom1ImageId(long custom1ImageId)
		throws NoSuchImageException, SystemException {
		IGImage igImage = fetchByCustom1ImageId(custom1ImageId);

		if (igImage == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No IGImage exists with the key {");

			msg.append("custom1ImageId=" + custom1ImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchImageException(msg.toString());
		}

		return igImage;
	}

	public IGImage fetchByCustom1ImageId(long custom1ImageId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = IGImageModelImpl.CACHE_ENABLED;
		String finderClassName = IGImage.class.getName();
		String finderMethodName = "fetchByCustom1ImageId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(custom1ImageId) };

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
					"FROM com.liferay.portlet.imagegallery.model.IGImage WHERE ");

				query.append("custom1ImageId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("imageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(custom1ImageId);

				List<IGImage> list = q.list();

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
			List<IGImage> list = (List<IGImage>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public IGImage findByCustom2ImageId(long custom2ImageId)
		throws NoSuchImageException, SystemException {
		IGImage igImage = fetchByCustom2ImageId(custom2ImageId);

		if (igImage == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No IGImage exists with the key {");

			msg.append("custom2ImageId=" + custom2ImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchImageException(msg.toString());
		}

		return igImage;
	}

	public IGImage fetchByCustom2ImageId(long custom2ImageId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = IGImageModelImpl.CACHE_ENABLED;
		String finderClassName = IGImage.class.getName();
		String finderMethodName = "fetchByCustom2ImageId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(custom2ImageId) };

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
					"FROM com.liferay.portlet.imagegallery.model.IGImage WHERE ");

				query.append("custom2ImageId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("imageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(custom2ImageId);

				List<IGImage> list = q.list();

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
			List<IGImage> list = (List<IGImage>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<IGImage> findByF_N(long folderId, String name)
		throws SystemException {
		boolean finderClassNameCacheEnabled = IGImageModelImpl.CACHE_ENABLED;
		String finderClassName = IGImage.class.getName();
		String finderMethodName = "findByF_N";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(folderId), name };

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
					"FROM com.liferay.portlet.imagegallery.model.IGImage WHERE ");

				query.append("folderId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("imageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				List<IGImage> list = q.list();

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
			return (List<IGImage>)result;
		}
	}

	public List<IGImage> findByF_N(long folderId, String name, int start,
		int end) throws SystemException {
		return findByF_N(folderId, name, start, end, null);
	}

	public List<IGImage> findByF_N(long folderId, String name, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = IGImageModelImpl.CACHE_ENABLED;
		String finderClassName = IGImage.class.getName();
		String finderMethodName = "findByF_N";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(folderId),
				
				name,
				
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
					"FROM com.liferay.portlet.imagegallery.model.IGImage WHERE ");

				query.append("folderId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
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

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				List<IGImage> list = (List<IGImage>)QueryUtil.list(q,
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
			return (List<IGImage>)result;
		}
	}

	public IGImage findByF_N_First(long folderId, String name,
		OrderByComparator obc) throws NoSuchImageException, SystemException {
		List<IGImage> list = findByF_N(folderId, name, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No IGImage exists with the key {");

			msg.append("folderId=" + folderId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public IGImage findByF_N_Last(long folderId, String name,
		OrderByComparator obc) throws NoSuchImageException, SystemException {
		int count = countByF_N(folderId, name);

		List<IGImage> list = findByF_N(folderId, name, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No IGImage exists with the key {");

			msg.append("folderId=" + folderId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public IGImage[] findByF_N_PrevAndNext(long imageId, long folderId,
		String name, OrderByComparator obc)
		throws NoSuchImageException, SystemException {
		IGImage igImage = findByPrimaryKey(imageId);

		int count = countByF_N(folderId, name);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.imagegallery.model.IGImage WHERE ");

			query.append("folderId = ?");

			query.append(" AND ");

			if (name == null) {
				query.append("name IS NULL");
			}
			else {
				query.append("name = ?");
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

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(folderId);

			if (name != null) {
				qPos.add(name);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, igImage);

			IGImage[] array = new IGImageImpl[3];

			array[0] = (IGImage)objArray[0];
			array[1] = (IGImage)objArray[1];
			array[2] = (IGImage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
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

	public List<IGImage> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<IGImage> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<IGImage> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = IGImageModelImpl.CACHE_ENABLED;
		String finderClassName = IGImage.class.getName();
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
					"FROM com.liferay.portlet.imagegallery.model.IGImage ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("imageId ASC");
				}

				Query q = session.createQuery(query.toString());

				List<IGImage> list = null;

				if (obc == null) {
					list = (List<IGImage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<IGImage>)QueryUtil.list(q, getDialect(),
							start, end);
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
			return (List<IGImage>)result;
		}
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (IGImage igImage : findByUuid(uuid)) {
			remove(igImage);
		}
	}

	public void removeByFolderId(long folderId) throws SystemException {
		for (IGImage igImage : findByFolderId(folderId)) {
			remove(igImage);
		}
	}

	public void removeBySmallImageId(long smallImageId)
		throws NoSuchImageException, SystemException {
		IGImage igImage = findBySmallImageId(smallImageId);

		remove(igImage);
	}

	public void removeByLargeImageId(long largeImageId)
		throws NoSuchImageException, SystemException {
		IGImage igImage = findByLargeImageId(largeImageId);

		remove(igImage);
	}

	public void removeByCustom1ImageId(long custom1ImageId)
		throws NoSuchImageException, SystemException {
		IGImage igImage = findByCustom1ImageId(custom1ImageId);

		remove(igImage);
	}

	public void removeByCustom2ImageId(long custom2ImageId)
		throws NoSuchImageException, SystemException {
		IGImage igImage = findByCustom2ImageId(custom2ImageId);

		remove(igImage);
	}

	public void removeByF_N(long folderId, String name)
		throws SystemException {
		for (IGImage igImage : findByF_N(folderId, name)) {
			remove(igImage);
		}
	}

	public void removeAll() throws SystemException {
		for (IGImage igImage : findAll()) {
			remove(igImage);
		}
	}

	public int countByUuid(String uuid) throws SystemException {
		boolean finderClassNameCacheEnabled = IGImageModelImpl.CACHE_ENABLED;
		String finderClassName = IGImage.class.getName();
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
					"FROM com.liferay.portlet.imagegallery.model.IGImage WHERE ");

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

	public int countByFolderId(long folderId) throws SystemException {
		boolean finderClassNameCacheEnabled = IGImageModelImpl.CACHE_ENABLED;
		String finderClassName = IGImage.class.getName();
		String finderMethodName = "countByFolderId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(folderId) };

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
					"FROM com.liferay.portlet.imagegallery.model.IGImage WHERE ");

				query.append("folderId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

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

	public int countBySmallImageId(long smallImageId) throws SystemException {
		boolean finderClassNameCacheEnabled = IGImageModelImpl.CACHE_ENABLED;
		String finderClassName = IGImage.class.getName();
		String finderMethodName = "countBySmallImageId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(smallImageId) };

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
					"FROM com.liferay.portlet.imagegallery.model.IGImage WHERE ");

				query.append("smallImageId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

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

	public int countByLargeImageId(long largeImageId) throws SystemException {
		boolean finderClassNameCacheEnabled = IGImageModelImpl.CACHE_ENABLED;
		String finderClassName = IGImage.class.getName();
		String finderMethodName = "countByLargeImageId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(largeImageId) };

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
					"FROM com.liferay.portlet.imagegallery.model.IGImage WHERE ");

				query.append("largeImageId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(largeImageId);

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

	public int countByCustom1ImageId(long custom1ImageId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = IGImageModelImpl.CACHE_ENABLED;
		String finderClassName = IGImage.class.getName();
		String finderMethodName = "countByCustom1ImageId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(custom1ImageId) };

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
					"FROM com.liferay.portlet.imagegallery.model.IGImage WHERE ");

				query.append("custom1ImageId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(custom1ImageId);

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

	public int countByCustom2ImageId(long custom2ImageId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = IGImageModelImpl.CACHE_ENABLED;
		String finderClassName = IGImage.class.getName();
		String finderMethodName = "countByCustom2ImageId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(custom2ImageId) };

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
					"FROM com.liferay.portlet.imagegallery.model.IGImage WHERE ");

				query.append("custom2ImageId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(custom2ImageId);

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

	public int countByF_N(long folderId, String name) throws SystemException {
		boolean finderClassNameCacheEnabled = IGImageModelImpl.CACHE_ENABLED;
		String finderClassName = IGImage.class.getName();
		String finderMethodName = "countByF_N";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(folderId), name };

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
					"FROM com.liferay.portlet.imagegallery.model.IGImage WHERE ");

				query.append("folderId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
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

	public int countAll() throws SystemException {
		boolean finderClassNameCacheEnabled = IGImageModelImpl.CACHE_ENABLED;
		String finderClassName = IGImage.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portlet.imagegallery.model.IGImage");

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
						"value.object.listener.com.liferay.portlet.imagegallery.model.IGImage")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<IGImage>> listenersList = new ArrayList<ModelListener<IGImage>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<IGImage>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.imagegallery.service.persistence.IGFolderPersistence.impl")
	protected com.liferay.portlet.imagegallery.service.persistence.IGFolderPersistence igFolderPersistence;
	@BeanReference(name = "com.liferay.portlet.imagegallery.service.persistence.IGImagePersistence.impl")
	protected com.liferay.portlet.imagegallery.service.persistence.IGImagePersistence igImagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence.impl")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence.impl")
	protected com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsAssetPersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsAssetPersistence tagsAssetPersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsEntryPersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsEntryPersistence tagsEntryPersistence;
	private static Log _log = LogFactoryUtil.getLog(IGImagePersistenceImpl.class);
}