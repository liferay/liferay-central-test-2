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

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="IGImagePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       IGImagePersistence
 * @see       IGImageUtil
 * @generated
 */
public class IGImagePersistenceImpl extends BasePersistenceImpl<IGImage>
	implements IGImagePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = IGImageImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_SMALLIMAGEID = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchBySmallImageId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_SMALLIMAGEID = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countBySmallImageId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_LARGEIMAGEID = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByLargeImageId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_LARGEIMAGEID = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByLargeImageId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_CUSTOM1IMAGEID = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByCustom1ImageId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_CUSTOM1IMAGEID = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCustom1ImageId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_CUSTOM2IMAGEID = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByCustom2ImageId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_CUSTOM2IMAGEID = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCustom2ImageId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_U = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_U = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_F = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_F",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_F = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_F",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_F_N = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_F_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_F_N = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_F_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_N = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_F_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(IGImage igImage) {
		EntityCacheUtil.putResult(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageImpl.class, igImage.getPrimaryKey(), igImage);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { igImage.getUuid(), new Long(igImage.getGroupId()) },
			igImage);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
			new Object[] { new Long(igImage.getSmallImageId()) }, igImage);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LARGEIMAGEID,
			new Object[] { new Long(igImage.getLargeImageId()) }, igImage);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CUSTOM1IMAGEID,
			new Object[] { new Long(igImage.getCustom1ImageId()) }, igImage);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CUSTOM2IMAGEID,
			new Object[] { new Long(igImage.getCustom2ImageId()) }, igImage);
	}

	public void cacheResult(List<IGImage> igImages) {
		for (IGImage igImage : igImages) {
			if (EntityCacheUtil.getResult(
						IGImageModelImpl.ENTITY_CACHE_ENABLED,
						IGImageImpl.class, igImage.getPrimaryKey(), this) == null) {
				cacheResult(igImage);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(IGImageImpl.class.getName());
		EntityCacheUtil.clearCache(IGImageImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public IGImage create(long imageId) {
		IGImage igImage = new IGImageImpl();

		igImage.setNew(true);
		igImage.setPrimaryKey(imageId);

		String uuid = PortalUUIDUtil.generate();

		igImage.setUuid(uuid);

		return igImage;
	}

	public IGImage remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
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
		igImage = toUnwrappedModel(igImage);

		Session session = null;

		try {
			session = openSession();

			if (igImage.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(IGImageImpl.class,
						igImage.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(igImage);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		IGImageModelImpl igImageModelImpl = (IGImageModelImpl)igImage;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				igImageModelImpl.getOriginalUuid(),
				new Long(igImageModelImpl.getOriginalGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
			new Object[] { new Long(igImageModelImpl.getOriginalSmallImageId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_LARGEIMAGEID,
			new Object[] { new Long(igImageModelImpl.getOriginalLargeImageId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CUSTOM1IMAGEID,
			new Object[] { new Long(igImageModelImpl.getOriginalCustom1ImageId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CUSTOM2IMAGEID,
			new Object[] { new Long(igImageModelImpl.getOriginalCustom2ImageId()) });

		EntityCacheUtil.removeResult(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageImpl.class, igImage.getPrimaryKey());

		return igImage;
	}

	public IGImage updateImpl(
		com.liferay.portlet.imagegallery.model.IGImage igImage, boolean merge)
		throws SystemException {
		igImage = toUnwrappedModel(igImage);

		boolean isNew = igImage.isNew();

		IGImageModelImpl igImageModelImpl = (IGImageModelImpl)igImage;

		if (Validator.isNull(igImage.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			igImage.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, igImage, merge);

			igImage.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(IGImageModelImpl.ENTITY_CACHE_ENABLED,
			IGImageImpl.class, igImage.getPrimaryKey(), igImage);

		if (!isNew &&
				(!Validator.equals(igImage.getUuid(),
					igImageModelImpl.getOriginalUuid()) ||
				(igImage.getGroupId() != igImageModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					igImageModelImpl.getOriginalUuid(),
					new Long(igImageModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(igImage.getUuid(),
					igImageModelImpl.getOriginalUuid()) ||
				(igImage.getGroupId() != igImageModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] { igImage.getUuid(), new Long(igImage.getGroupId()) },
				igImage);
		}

		if (!isNew &&
				(igImage.getSmallImageId() != igImageModelImpl.getOriginalSmallImageId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
				new Object[] {
					new Long(igImageModelImpl.getOriginalSmallImageId())
				});
		}

		if (isNew ||
				(igImage.getSmallImageId() != igImageModelImpl.getOriginalSmallImageId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
				new Object[] { new Long(igImage.getSmallImageId()) }, igImage);
		}

		if (!isNew &&
				(igImage.getLargeImageId() != igImageModelImpl.getOriginalLargeImageId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_LARGEIMAGEID,
				new Object[] {
					new Long(igImageModelImpl.getOriginalLargeImageId())
				});
		}

		if (isNew ||
				(igImage.getLargeImageId() != igImageModelImpl.getOriginalLargeImageId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LARGEIMAGEID,
				new Object[] { new Long(igImage.getLargeImageId()) }, igImage);
		}

		if (!isNew &&
				(igImage.getCustom1ImageId() != igImageModelImpl.getOriginalCustom1ImageId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CUSTOM1IMAGEID,
				new Object[] {
					new Long(igImageModelImpl.getOriginalCustom1ImageId())
				});
		}

		if (isNew ||
				(igImage.getCustom1ImageId() != igImageModelImpl.getOriginalCustom1ImageId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CUSTOM1IMAGEID,
				new Object[] { new Long(igImage.getCustom1ImageId()) }, igImage);
		}

		if (!isNew &&
				(igImage.getCustom2ImageId() != igImageModelImpl.getOriginalCustom2ImageId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CUSTOM2IMAGEID,
				new Object[] {
					new Long(igImageModelImpl.getOriginalCustom2ImageId())
				});
		}

		if (isNew ||
				(igImage.getCustom2ImageId() != igImageModelImpl.getOriginalCustom2ImageId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CUSTOM2IMAGEID,
				new Object[] { new Long(igImage.getCustom2ImageId()) }, igImage);
		}

		return igImage;
	}

	protected IGImage toUnwrappedModel(IGImage igImage) {
		if (igImage instanceof IGImageImpl) {
			return igImage;
		}

		IGImageImpl igImageImpl = new IGImageImpl();

		igImageImpl.setNew(igImage.isNew());
		igImageImpl.setPrimaryKey(igImage.getPrimaryKey());

		igImageImpl.setUuid(igImage.getUuid());
		igImageImpl.setImageId(igImage.getImageId());
		igImageImpl.setGroupId(igImage.getGroupId());
		igImageImpl.setCompanyId(igImage.getCompanyId());
		igImageImpl.setUserId(igImage.getUserId());
		igImageImpl.setCreateDate(igImage.getCreateDate());
		igImageImpl.setModifiedDate(igImage.getModifiedDate());
		igImageImpl.setFolderId(igImage.getFolderId());
		igImageImpl.setName(igImage.getName());
		igImageImpl.setDescription(igImage.getDescription());
		igImageImpl.setSmallImageId(igImage.getSmallImageId());
		igImageImpl.setLargeImageId(igImage.getLargeImageId());
		igImageImpl.setCustom1ImageId(igImage.getCustom1ImageId());
		igImageImpl.setCustom2ImageId(igImage.getCustom2ImageId());

		return igImageImpl;
	}

	public IGImage findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
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

	public IGImage fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public IGImage fetchByPrimaryKey(long imageId) throws SystemException {
		IGImage igImage = (IGImage)EntityCacheUtil.getResult(IGImageModelImpl.ENTITY_CACHE_ENABLED,
				IGImageImpl.class, imageId, this);

		if (igImage == null) {
			Session session = null;

			try {
				session = openSession();

				igImage = (IGImage)session.get(IGImageImpl.class,
						new Long(imageId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (igImage != null) {
					cacheResult(igImage);
				}

				closeSession(session);
			}
		}

		return igImage;
	}

	public List<IGImage> findByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<IGImage> list = (List<IGImage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT igImage FROM IGImage igImage WHERE ");

				if (uuid == null) {
					query.append("igImage.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(igImage.uuid IS NULL OR ");
					}

					query.append("igImage.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("igImage.imageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<IGImage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<IGImage> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<IGImage> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<IGImage> list = (List<IGImage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT igImage FROM IGImage igImage WHERE ");

				if (uuid == null) {
					query.append("igImage.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(igImage.uuid IS NULL OR ");
					}

					query.append("igImage.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("igImage.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append("ORDER BY ");

					query.append("igImage.imageId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<IGImage>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<IGImage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public IGImage findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchImageException, SystemException {
		List<IGImage> list = findByUuid(uuid, 0, 1, obc);

		if (list.isEmpty()) {
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

		if (list.isEmpty()) {
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

			query.append("SELECT igImage FROM IGImage igImage WHERE ");

			if (uuid == null) {
				query.append("igImage.uuid IS NULL");
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append("(igImage.uuid IS NULL OR ");
				}

				query.append("igImage.uuid = ?");

				if (uuid.equals(StringPool.BLANK)) {
					query.append(")");
				}
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("igImage.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			else {
				query.append("ORDER BY ");

				query.append("igImage.imageId ASC");
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

	public IGImage findByUUID_G(String uuid, long groupId)
		throws NoSuchImageException, SystemException {
		IGImage igImage = fetchByUUID_G(uuid, groupId);

		if (igImage == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No IGImage exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(", ");
			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchImageException(msg.toString());
		}

		return igImage;
	}

	public IGImage fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	public IGImage fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT igImage FROM IGImage igImage WHERE ");

				if (uuid == null) {
					query.append("igImage.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(igImage.uuid IS NULL OR ");
					}

					query.append("igImage.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("igImage.groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("igImage.imageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<IGImage> list = q.list();

				result = list;

				IGImage igImage = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					igImage = list.get(0);

					cacheResult(igImage);

					if ((igImage.getUuid() == null) ||
							!igImage.getUuid().equals(uuid) ||
							(igImage.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, igImage);
					}
				}

				return igImage;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, new ArrayList<IGImage>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (IGImage)result;
			}
		}
	}

	public List<IGImage> findByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<IGImage> list = (List<IGImage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT igImage FROM IGImage igImage WHERE ");

				query.append("igImage.groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("igImage.imageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<IGImage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<IGImage> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<IGImage> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<IGImage> list = (List<IGImage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT igImage FROM IGImage igImage WHERE ");

				query.append("igImage.groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("igImage.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append("ORDER BY ");

					query.append("igImage.imageId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<IGImage>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<IGImage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public IGImage findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchImageException, SystemException {
		List<IGImage> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No IGImage exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public IGImage findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchImageException, SystemException {
		int count = countByGroupId(groupId);

		List<IGImage> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No IGImage exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public IGImage[] findByGroupId_PrevAndNext(long imageId, long groupId,
		OrderByComparator obc) throws NoSuchImageException, SystemException {
		IGImage igImage = findByPrimaryKey(imageId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT igImage FROM IGImage igImage WHERE ");

			query.append("igImage.groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("igImage.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			else {
				query.append("ORDER BY ");

				query.append("igImage.imageId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

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
		return fetchBySmallImageId(smallImageId, true);
	}

	public IGImage fetchBySmallImageId(long smallImageId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(smallImageId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT igImage FROM IGImage igImage WHERE ");

				query.append("igImage.smallImageId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("igImage.imageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

				List<IGImage> list = q.list();

				result = list;

				IGImage igImage = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
						finderArgs, list);
				}
				else {
					igImage = list.get(0);

					cacheResult(igImage);

					if ((igImage.getSmallImageId() != smallImageId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
							finderArgs, igImage);
					}
				}

				return igImage;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
						finderArgs, new ArrayList<IGImage>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (IGImage)result;
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
		return fetchByLargeImageId(largeImageId, true);
	}

	public IGImage fetchByLargeImageId(long largeImageId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(largeImageId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_LARGEIMAGEID,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT igImage FROM IGImage igImage WHERE ");

				query.append("igImage.largeImageId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("igImage.imageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(largeImageId);

				List<IGImage> list = q.list();

				result = list;

				IGImage igImage = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LARGEIMAGEID,
						finderArgs, list);
				}
				else {
					igImage = list.get(0);

					cacheResult(igImage);

					if ((igImage.getLargeImageId() != largeImageId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LARGEIMAGEID,
							finderArgs, igImage);
					}
				}

				return igImage;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LARGEIMAGEID,
						finderArgs, new ArrayList<IGImage>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (IGImage)result;
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
		return fetchByCustom1ImageId(custom1ImageId, true);
	}

	public IGImage fetchByCustom1ImageId(long custom1ImageId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(custom1ImageId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_CUSTOM1IMAGEID,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT igImage FROM IGImage igImage WHERE ");

				query.append("igImage.custom1ImageId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("igImage.imageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(custom1ImageId);

				List<IGImage> list = q.list();

				result = list;

				IGImage igImage = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CUSTOM1IMAGEID,
						finderArgs, list);
				}
				else {
					igImage = list.get(0);

					cacheResult(igImage);

					if ((igImage.getCustom1ImageId() != custom1ImageId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CUSTOM1IMAGEID,
							finderArgs, igImage);
					}
				}

				return igImage;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CUSTOM1IMAGEID,
						finderArgs, new ArrayList<IGImage>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (IGImage)result;
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
		return fetchByCustom2ImageId(custom2ImageId, true);
	}

	public IGImage fetchByCustom2ImageId(long custom2ImageId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(custom2ImageId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_CUSTOM2IMAGEID,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT igImage FROM IGImage igImage WHERE ");

				query.append("igImage.custom2ImageId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("igImage.imageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(custom2ImageId);

				List<IGImage> list = q.list();

				result = list;

				IGImage igImage = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CUSTOM2IMAGEID,
						finderArgs, list);
				}
				else {
					igImage = list.get(0);

					cacheResult(igImage);

					if ((igImage.getCustom2ImageId() != custom2ImageId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CUSTOM2IMAGEID,
							finderArgs, igImage);
					}
				}

				return igImage;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CUSTOM2IMAGEID,
						finderArgs, new ArrayList<IGImage>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (IGImage)result;
			}
		}
	}

	public List<IGImage> findByG_U(long groupId, long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

		List<IGImage> list = (List<IGImage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT igImage FROM IGImage igImage WHERE ");

				query.append("igImage.groupId = ?");

				query.append(" AND ");

				query.append("igImage.userId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("igImage.imageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<IGImage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_U, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<IGImage> findByG_U(long groupId, long userId, int start, int end)
		throws SystemException {
		return findByG_U(groupId, userId, start, end, null);
	}

	public List<IGImage> findByG_U(long groupId, long userId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<IGImage> list = (List<IGImage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_U,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT igImage FROM IGImage igImage WHERE ");

				query.append("igImage.groupId = ?");

				query.append(" AND ");

				query.append("igImage.userId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("igImage.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append("ORDER BY ");

					query.append("igImage.imageId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				list = (List<IGImage>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<IGImage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_U,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public IGImage findByG_U_First(long groupId, long userId,
		OrderByComparator obc) throws NoSuchImageException, SystemException {
		List<IGImage> list = findByG_U(groupId, userId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No IGImage exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public IGImage findByG_U_Last(long groupId, long userId,
		OrderByComparator obc) throws NoSuchImageException, SystemException {
		int count = countByG_U(groupId, userId);

		List<IGImage> list = findByG_U(groupId, userId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No IGImage exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public IGImage[] findByG_U_PrevAndNext(long imageId, long groupId,
		long userId, OrderByComparator obc)
		throws NoSuchImageException, SystemException {
		IGImage igImage = findByPrimaryKey(imageId);

		int count = countByG_U(groupId, userId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT igImage FROM IGImage igImage WHERE ");

			query.append("igImage.groupId = ?");

			query.append(" AND ");

			query.append("igImage.userId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("igImage.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			else {
				query.append("ORDER BY ");

				query.append("igImage.imageId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

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

	public List<IGImage> findByG_F(long groupId, long folderId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(folderId) };

		List<IGImage> list = (List<IGImage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_F,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT igImage FROM IGImage igImage WHERE ");

				query.append("igImage.groupId = ?");

				query.append(" AND ");

				query.append("igImage.folderId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("igImage.imageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<IGImage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_F, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<IGImage> findByG_F(long groupId, long folderId, int start,
		int end) throws SystemException {
		return findByG_F(groupId, folderId, start, end, null);
	}

	public List<IGImage> findByG_F(long groupId, long folderId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(folderId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<IGImage> list = (List<IGImage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_F,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT igImage FROM IGImage igImage WHERE ");

				query.append("igImage.groupId = ?");

				query.append(" AND ");

				query.append("igImage.folderId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("igImage.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append("ORDER BY ");

					query.append("igImage.imageId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				list = (List<IGImage>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<IGImage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_F,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public IGImage findByG_F_First(long groupId, long folderId,
		OrderByComparator obc) throws NoSuchImageException, SystemException {
		List<IGImage> list = findByG_F(groupId, folderId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No IGImage exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("folderId=" + folderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public IGImage findByG_F_Last(long groupId, long folderId,
		OrderByComparator obc) throws NoSuchImageException, SystemException {
		int count = countByG_F(groupId, folderId);

		List<IGImage> list = findByG_F(groupId, folderId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No IGImage exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("folderId=" + folderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public IGImage[] findByG_F_PrevAndNext(long imageId, long groupId,
		long folderId, OrderByComparator obc)
		throws NoSuchImageException, SystemException {
		IGImage igImage = findByPrimaryKey(imageId);

		int count = countByG_F(groupId, folderId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT igImage FROM IGImage igImage WHERE ");

			query.append("igImage.groupId = ?");

			query.append(" AND ");

			query.append("igImage.folderId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("igImage.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			else {
				query.append("ORDER BY ");

				query.append("igImage.imageId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

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

	public List<IGImage> findByG_F_N(long groupId, long folderId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(folderId),
				
				name
			};

		List<IGImage> list = (List<IGImage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_F_N,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT igImage FROM IGImage igImage WHERE ");

				query.append("igImage.groupId = ?");

				query.append(" AND ");

				query.append("igImage.folderId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("igImage.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(igImage.name IS NULL OR ");
					}

					query.append("igImage.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("igImage.imageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<IGImage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_F_N,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<IGImage> findByG_F_N(long groupId, long folderId, String name,
		int start, int end) throws SystemException {
		return findByG_F_N(groupId, folderId, name, start, end, null);
	}

	public List<IGImage> findByG_F_N(long groupId, long folderId, String name,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(folderId),
				
				name,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<IGImage> list = (List<IGImage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_F_N,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT igImage FROM IGImage igImage WHERE ");

				query.append("igImage.groupId = ?");

				query.append(" AND ");

				query.append("igImage.folderId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("igImage.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(igImage.name IS NULL OR ");
					}

					query.append("igImage.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("igImage.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append("ORDER BY ");

					query.append("igImage.imageId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				list = (List<IGImage>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<IGImage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_F_N,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public IGImage findByG_F_N_First(long groupId, long folderId, String name,
		OrderByComparator obc) throws NoSuchImageException, SystemException {
		List<IGImage> list = findByG_F_N(groupId, folderId, name, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No IGImage exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
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

	public IGImage findByG_F_N_Last(long groupId, long folderId, String name,
		OrderByComparator obc) throws NoSuchImageException, SystemException {
		int count = countByG_F_N(groupId, folderId, name);

		List<IGImage> list = findByG_F_N(groupId, folderId, name, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No IGImage exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
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

	public IGImage[] findByG_F_N_PrevAndNext(long imageId, long groupId,
		long folderId, String name, OrderByComparator obc)
		throws NoSuchImageException, SystemException {
		IGImage igImage = findByPrimaryKey(imageId);

		int count = countByG_F_N(groupId, folderId, name);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT igImage FROM IGImage igImage WHERE ");

			query.append("igImage.groupId = ?");

			query.append(" AND ");

			query.append("igImage.folderId = ?");

			query.append(" AND ");

			if (name == null) {
				query.append("igImage.name IS NULL");
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append("(igImage.name IS NULL OR ");
				}

				query.append("igImage.name = ?");

				if (name.equals(StringPool.BLANK)) {
					query.append(")");
				}
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("igImage.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			else {
				query.append("ORDER BY ");

				query.append("igImage.imageId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

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
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<IGImage> list = (List<IGImage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT igImage FROM IGImage igImage ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("igImage.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append("ORDER BY ");

					query.append("igImage.imageId ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<IGImage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<IGImage>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<IGImage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (IGImage igImage : findByUuid(uuid)) {
			remove(igImage);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchImageException, SystemException {
		IGImage igImage = findByUUID_G(uuid, groupId);

		remove(igImage);
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (IGImage igImage : findByGroupId(groupId)) {
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

	public void removeByG_U(long groupId, long userId)
		throws SystemException {
		for (IGImage igImage : findByG_U(groupId, userId)) {
			remove(igImage);
		}
	}

	public void removeByG_F(long groupId, long folderId)
		throws SystemException {
		for (IGImage igImage : findByG_F(groupId, folderId)) {
			remove(igImage);
		}
	}

	public void removeByG_F_N(long groupId, long folderId, String name)
		throws SystemException {
		for (IGImage igImage : findByG_F_N(groupId, folderId, name)) {
			remove(igImage);
		}
	}

	public void removeAll() throws SystemException {
		for (IGImage igImage : findAll()) {
			remove(igImage);
		}
	}

	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(igImage) ");
				query.append("FROM IGImage igImage WHERE ");

				if (uuid == null) {
					query.append("igImage.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(igImage.uuid IS NULL OR ");
					}

					query.append("igImage.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(igImage) ");
				query.append("FROM IGImage igImage WHERE ");

				if (uuid == null) {
					query.append("igImage.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(igImage.uuid IS NULL OR ");
					}

					query.append("igImage.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("igImage.groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(igImage) ");
				query.append("FROM IGImage igImage WHERE ");

				query.append("igImage.groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countBySmallImageId(long smallImageId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(smallImageId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(igImage) ");
				query.append("FROM IGImage igImage WHERE ");

				query.append("igImage.smallImageId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByLargeImageId(long largeImageId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(largeImageId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_LARGEIMAGEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(igImage) ");
				query.append("FROM IGImage igImage WHERE ");

				query.append("igImage.largeImageId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(largeImageId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_LARGEIMAGEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByCustom1ImageId(long custom1ImageId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(custom1ImageId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CUSTOM1IMAGEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(igImage) ");
				query.append("FROM IGImage igImage WHERE ");

				query.append("igImage.custom1ImageId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(custom1ImageId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CUSTOM1IMAGEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByCustom2ImageId(long custom2ImageId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(custom2ImageId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CUSTOM2IMAGEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(igImage) ");
				query.append("FROM IGImage igImage WHERE ");

				query.append("igImage.custom2ImageId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(custom2ImageId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CUSTOM2IMAGEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_U(long groupId, long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(igImage) ");
				query.append("FROM IGImage igImage WHERE ");

				query.append("igImage.groupId = ?");

				query.append(" AND ");

				query.append("igImage.userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_F(long groupId, long folderId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(folderId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(igImage) ");
				query.append("FROM IGImage igImage WHERE ");

				query.append("igImage.groupId = ?");

				query.append(" AND ");

				query.append("igImage.folderId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_F_N(long groupId, long folderId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(folderId),
				
				name
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F_N,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(igImage) ");
				query.append("FROM IGImage igImage WHERE ");

				query.append("igImage.groupId = ?");

				query.append(" AND ");

				query.append("igImage.folderId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("igImage.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(igImage.name IS NULL OR ");
					}

					query.append("igImage.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F_N,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(igImage) FROM IGImage igImage");

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
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

	@BeanReference(name = "com.liferay.portlet.imagegallery.service.persistence.IGFolderPersistence")
	protected com.liferay.portlet.imagegallery.service.persistence.IGFolderPersistence igFolderPersistence;
	@BeanReference(name = "com.liferay.portlet.imagegallery.service.persistence.IGImagePersistence")
	protected com.liferay.portlet.imagegallery.service.persistence.IGImagePersistence igImagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetEntryPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetEntryPersistence assetEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetTagPersistence assetTagPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence")
	protected com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialActivityPersistence")
	protected com.liferay.portlet.social.service.persistence.SocialActivityPersistence socialActivityPersistence;
	private static Log _log = LogFactoryUtil.getLog(IGImagePersistenceImpl.class);
}