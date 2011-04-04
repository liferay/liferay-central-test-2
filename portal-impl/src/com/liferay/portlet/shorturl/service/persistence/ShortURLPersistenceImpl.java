/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.shorturl.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.shorturl.NoSuchShortURLException;
import com.liferay.portlet.shorturl.model.ShortURL;
import com.liferay.portlet.shorturl.model.impl.ShortURLImpl;
import com.liferay.portlet.shorturl.model.impl.ShortURLModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the short u r l service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShortURLPersistence
 * @see ShortURLUtil
 * @generated
 */
public class ShortURLPersistenceImpl extends BasePersistenceImpl<ShortURL>
	implements ShortURLPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ShortURLUtil} to access the short u r l persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ShortURLImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FETCH_BY_ORIGINALURL = new FinderPath(ShortURLModelImpl.ENTITY_CACHE_ENABLED,
			ShortURLModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByOriginalURL", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_ORIGINALURL = new FinderPath(ShortURLModelImpl.ENTITY_CACHE_ENABLED,
			ShortURLModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByOriginalURL", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_HASH = new FinderPath(ShortURLModelImpl.ENTITY_CACHE_ENABLED,
			ShortURLModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByHash", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_HASH = new FinderPath(ShortURLModelImpl.ENTITY_CACHE_ENABLED,
			ShortURLModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByHash", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ShortURLModelImpl.ENTITY_CACHE_ENABLED,
			ShortURLModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ShortURLModelImpl.ENTITY_CACHE_ENABLED,
			ShortURLModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	/**
	 * Caches the short u r l in the entity cache if it is enabled.
	 *
	 * @param shortURL the short u r l to cache
	 */
	public void cacheResult(ShortURL shortURL) {
		EntityCacheUtil.putResult(ShortURLModelImpl.ENTITY_CACHE_ENABLED,
			ShortURLImpl.class, shortURL.getPrimaryKey(), shortURL);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ORIGINALURL,
			new Object[] { shortURL.getOriginalURL() }, shortURL);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_HASH,
			new Object[] { shortURL.getHash() }, shortURL);

		shortURL.resetOriginalValues();
	}

	/**
	 * Caches the short u r ls in the entity cache if it is enabled.
	 *
	 * @param shortURLs the short u r ls to cache
	 */
	public void cacheResult(List<ShortURL> shortURLs) {
		for (ShortURL shortURL : shortURLs) {
			if (EntityCacheUtil.getResult(
						ShortURLModelImpl.ENTITY_CACHE_ENABLED,
						ShortURLImpl.class, shortURL.getPrimaryKey(), this) == null) {
				cacheResult(shortURL);
			}
		}
	}

	/**
	 * Clears the cache for all short u r ls.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ShortURLImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ShortURLImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the short u r l.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(ShortURL shortURL) {
		EntityCacheUtil.removeResult(ShortURLModelImpl.ENTITY_CACHE_ENABLED,
			ShortURLImpl.class, shortURL.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_ORIGINALURL,
			new Object[] { shortURL.getOriginalURL() });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_HASH,
			new Object[] { shortURL.getHash() });
	}

	/**
	 * Creates a new short u r l with the primary key. Does not add the short u r l to the database.
	 *
	 * @param shortURLId the primary key for the new short u r l
	 * @return the new short u r l
	 */
	public ShortURL create(long shortURLId) {
		ShortURL shortURL = new ShortURLImpl();

		shortURL.setNew(true);
		shortURL.setPrimaryKey(shortURLId);

		return shortURL;
	}

	/**
	 * Removes the short u r l with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the short u r l to remove
	 * @return the short u r l that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a short u r l with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShortURL remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the short u r l with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param shortURLId the primary key of the short u r l to remove
	 * @return the short u r l that was removed
	 * @throws com.liferay.portlet.shorturl.NoSuchShortURLException if a short u r l with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShortURL remove(long shortURLId)
		throws NoSuchShortURLException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShortURL shortURL = (ShortURL)session.get(ShortURLImpl.class,
					Long.valueOf(shortURLId));

			if (shortURL == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + shortURLId);
				}

				throw new NoSuchShortURLException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					shortURLId);
			}

			return shortURLPersistence.remove(shortURL);
		}
		catch (NoSuchShortURLException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes the short u r l from the database. Also notifies the appropriate model listeners.
	 *
	 * @param shortURL the short u r l to remove
	 * @return the short u r l that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public ShortURL remove(ShortURL shortURL) throws SystemException {
		return super.remove(shortURL);
	}

	protected ShortURL removeImpl(ShortURL shortURL) throws SystemException {
		shortURL = toUnwrappedModel(shortURL);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, shortURL);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		ShortURLModelImpl shortURLModelImpl = (ShortURLModelImpl)shortURL;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_ORIGINALURL,
			new Object[] { shortURLModelImpl.getOriginalURL() });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_HASH,
			new Object[] { shortURLModelImpl.getHash() });

		EntityCacheUtil.removeResult(ShortURLModelImpl.ENTITY_CACHE_ENABLED,
			ShortURLImpl.class, shortURL.getPrimaryKey());

		return shortURL;
	}

	public ShortURL updateImpl(
		com.liferay.portlet.shorturl.model.ShortURL shortURL, boolean merge)
		throws SystemException {
		shortURL = toUnwrappedModel(shortURL);

		boolean isNew = shortURL.isNew();

		ShortURLModelImpl shortURLModelImpl = (ShortURLModelImpl)shortURL;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, shortURL, merge);

			shortURL.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ShortURLModelImpl.ENTITY_CACHE_ENABLED,
			ShortURLImpl.class, shortURL.getPrimaryKey(), shortURL);

		if (!isNew &&
				(!Validator.equals(shortURL.getOriginalURL(),
					shortURLModelImpl.getOriginalOriginalURL()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_ORIGINALURL,
				new Object[] { shortURLModelImpl.getOriginalOriginalURL() });
		}

		if (isNew ||
				(!Validator.equals(shortURL.getOriginalURL(),
					shortURLModelImpl.getOriginalOriginalURL()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ORIGINALURL,
				new Object[] { shortURL.getOriginalURL() }, shortURL);
		}

		if (!isNew &&
				(!Validator.equals(shortURL.getHash(),
					shortURLModelImpl.getOriginalHash()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_HASH,
				new Object[] { shortURLModelImpl.getOriginalHash() });
		}

		if (isNew ||
				(!Validator.equals(shortURL.getHash(),
					shortURLModelImpl.getOriginalHash()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_HASH,
				new Object[] { shortURL.getHash() }, shortURL);
		}

		return shortURL;
	}

	protected ShortURL toUnwrappedModel(ShortURL shortURL) {
		if (shortURL instanceof ShortURLImpl) {
			return shortURL;
		}

		ShortURLImpl shortURLImpl = new ShortURLImpl();

		shortURLImpl.setNew(shortURL.isNew());
		shortURLImpl.setPrimaryKey(shortURL.getPrimaryKey());

		shortURLImpl.setShortURLId(shortURL.getShortURLId());
		shortURLImpl.setCreateDate(shortURL.getCreateDate());
		shortURLImpl.setModifiedDate(shortURL.getModifiedDate());
		shortURLImpl.setOriginalURL(shortURL.getOriginalURL());
		shortURLImpl.setHash(shortURL.getHash());
		shortURLImpl.setDescriptor(shortURL.getDescriptor());

		return shortURLImpl;
	}

	/**
	 * Finds the short u r l with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the short u r l to find
	 * @return the short u r l
	 * @throws com.liferay.portal.NoSuchModelException if a short u r l with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShortURL findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the short u r l with the primary key or throws a {@link com.liferay.portlet.shorturl.NoSuchShortURLException} if it could not be found.
	 *
	 * @param shortURLId the primary key of the short u r l to find
	 * @return the short u r l
	 * @throws com.liferay.portlet.shorturl.NoSuchShortURLException if a short u r l with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShortURL findByPrimaryKey(long shortURLId)
		throws NoSuchShortURLException, SystemException {
		ShortURL shortURL = fetchByPrimaryKey(shortURLId);

		if (shortURL == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + shortURLId);
			}

			throw new NoSuchShortURLException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				shortURLId);
		}

		return shortURL;
	}

	/**
	 * Finds the short u r l with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the short u r l to find
	 * @return the short u r l, or <code>null</code> if a short u r l with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShortURL fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the short u r l with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param shortURLId the primary key of the short u r l to find
	 * @return the short u r l, or <code>null</code> if a short u r l with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShortURL fetchByPrimaryKey(long shortURLId)
		throws SystemException {
		ShortURL shortURL = (ShortURL)EntityCacheUtil.getResult(ShortURLModelImpl.ENTITY_CACHE_ENABLED,
				ShortURLImpl.class, shortURLId, this);

		if (shortURL == null) {
			Session session = null;

			try {
				session = openSession();

				shortURL = (ShortURL)session.get(ShortURLImpl.class,
						Long.valueOf(shortURLId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (shortURL != null) {
					cacheResult(shortURL);
				}

				closeSession(session);
			}
		}

		return shortURL;
	}

	/**
	 * Finds the short u r l where originalURL = &#63; or throws a {@link com.liferay.portlet.shorturl.NoSuchShortURLException} if it could not be found.
	 *
	 * @param originalURL the original u r l to search with
	 * @return the matching short u r l
	 * @throws com.liferay.portlet.shorturl.NoSuchShortURLException if a matching short u r l could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShortURL findByOriginalURL(String originalURL)
		throws NoSuchShortURLException, SystemException {
		ShortURL shortURL = fetchByOriginalURL(originalURL);

		if (shortURL == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("originalURL=");
			msg.append(originalURL);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchShortURLException(msg.toString());
		}

		return shortURL;
	}

	/**
	 * Finds the short u r l where originalURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param originalURL the original u r l to search with
	 * @return the matching short u r l, or <code>null</code> if a matching short u r l could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShortURL fetchByOriginalURL(String originalURL)
		throws SystemException {
		return fetchByOriginalURL(originalURL, true);
	}

	/**
	 * Finds the short u r l where originalURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param originalURL the original u r l to search with
	 * @return the matching short u r l, or <code>null</code> if a matching short u r l could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShortURL fetchByOriginalURL(String originalURL,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { originalURL };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_ORIGINALURL,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_SELECT_SHORTURL_WHERE);

			if (originalURL == null) {
				query.append(_FINDER_COLUMN_ORIGINALURL_ORIGINALURL_1);
			}
			else {
				if (originalURL.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_ORIGINALURL_ORIGINALURL_3);
				}
				else {
					query.append(_FINDER_COLUMN_ORIGINALURL_ORIGINALURL_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (originalURL != null) {
					qPos.add(originalURL);
				}

				List<ShortURL> list = q.list();

				result = list;

				ShortURL shortURL = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ORIGINALURL,
						finderArgs, list);
				}
				else {
					shortURL = list.get(0);

					cacheResult(shortURL);

					if ((shortURL.getOriginalURL() == null) ||
							!shortURL.getOriginalURL().equals(originalURL)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ORIGINALURL,
							finderArgs, shortURL);
					}
				}

				return shortURL;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_ORIGINALURL,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (ShortURL)result;
			}
		}
	}

	/**
	 * Finds the short u r l where hash = &#63; or throws a {@link com.liferay.portlet.shorturl.NoSuchShortURLException} if it could not be found.
	 *
	 * @param hash the hash to search with
	 * @return the matching short u r l
	 * @throws com.liferay.portlet.shorturl.NoSuchShortURLException if a matching short u r l could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShortURL findByHash(String hash)
		throws NoSuchShortURLException, SystemException {
		ShortURL shortURL = fetchByHash(hash);

		if (shortURL == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("hash=");
			msg.append(hash);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchShortURLException(msg.toString());
		}

		return shortURL;
	}

	/**
	 * Finds the short u r l where hash = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param hash the hash to search with
	 * @return the matching short u r l, or <code>null</code> if a matching short u r l could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShortURL fetchByHash(String hash) throws SystemException {
		return fetchByHash(hash, true);
	}

	/**
	 * Finds the short u r l where hash = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param hash the hash to search with
	 * @return the matching short u r l, or <code>null</code> if a matching short u r l could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShortURL fetchByHash(String hash, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { hash };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_HASH,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_SELECT_SHORTURL_WHERE);

			if (hash == null) {
				query.append(_FINDER_COLUMN_HASH_HASH_1);
			}
			else {
				if (hash.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_HASH_HASH_3);
				}
				else {
					query.append(_FINDER_COLUMN_HASH_HASH_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (hash != null) {
					qPos.add(hash);
				}

				List<ShortURL> list = q.list();

				result = list;

				ShortURL shortURL = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_HASH,
						finderArgs, list);
				}
				else {
					shortURL = list.get(0);

					cacheResult(shortURL);

					if ((shortURL.getHash() == null) ||
							!shortURL.getHash().equals(hash)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_HASH,
							finderArgs, shortURL);
					}
				}

				return shortURL;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_HASH,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (ShortURL)result;
			}
		}
	}

	/**
	 * Finds all the short u r ls.
	 *
	 * @return the short u r ls
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShortURL> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the short u r ls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of short u r ls to return
	 * @param end the upper bound of the range of short u r ls to return (not inclusive)
	 * @return the range of short u r ls
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShortURL> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the short u r ls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of short u r ls to return
	 * @param end the upper bound of the range of short u r ls to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of short u r ls
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShortURL> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<ShortURL> list = (List<ShortURL>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SHORTURL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SHORTURL;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<ShortURL>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ShortURL>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs,
						list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes the short u r l where originalURL = &#63; from the database.
	 *
	 * @param originalURL the original u r l to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByOriginalURL(String originalURL)
		throws NoSuchShortURLException, SystemException {
		ShortURL shortURL = findByOriginalURL(originalURL);

		shortURLPersistence.remove(shortURL);
	}

	/**
	 * Removes the short u r l where hash = &#63; from the database.
	 *
	 * @param hash the hash to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByHash(String hash)
		throws NoSuchShortURLException, SystemException {
		ShortURL shortURL = findByHash(hash);

		shortURLPersistence.remove(shortURL);
	}

	/**
	 * Removes all the short u r ls from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (ShortURL shortURL : findAll()) {
			shortURLPersistence.remove(shortURL);
		}
	}

	/**
	 * Counts all the short u r ls where originalURL = &#63;.
	 *
	 * @param originalURL the original u r l to search with
	 * @return the number of matching short u r ls
	 * @throws SystemException if a system exception occurred
	 */
	public int countByOriginalURL(String originalURL) throws SystemException {
		Object[] finderArgs = new Object[] { originalURL };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ORIGINALURL,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SHORTURL_WHERE);

			if (originalURL == null) {
				query.append(_FINDER_COLUMN_ORIGINALURL_ORIGINALURL_1);
			}
			else {
				if (originalURL.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_ORIGINALURL_ORIGINALURL_3);
				}
				else {
					query.append(_FINDER_COLUMN_ORIGINALURL_ORIGINALURL_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (originalURL != null) {
					qPos.add(originalURL);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ORIGINALURL,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the short u r ls where hash = &#63;.
	 *
	 * @param hash the hash to search with
	 * @return the number of matching short u r ls
	 * @throws SystemException if a system exception occurred
	 */
	public int countByHash(String hash) throws SystemException {
		Object[] finderArgs = new Object[] { hash };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_HASH,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SHORTURL_WHERE);

			if (hash == null) {
				query.append(_FINDER_COLUMN_HASH_HASH_1);
			}
			else {
				if (hash.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_HASH_HASH_3);
				}
				else {
					query.append(_FINDER_COLUMN_HASH_HASH_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (hash != null) {
					qPos.add(hash);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_HASH,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the short u r ls.
	 *
	 * @return the number of short u r ls
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SHORTURL);

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

	/**
	 * Initializes the short u r l persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.shorturl.model.ShortURL")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ShortURL>> listenersList = new ArrayList<ModelListener<ShortURL>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ShortURL>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ShortURLImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = ShortURLPersistence.class)
	protected ShortURLPersistence shortURLPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_SHORTURL = "SELECT shortURL FROM ShortURL shortURL";
	private static final String _SQL_SELECT_SHORTURL_WHERE = "SELECT shortURL FROM ShortURL shortURL WHERE ";
	private static final String _SQL_COUNT_SHORTURL = "SELECT COUNT(shortURL) FROM ShortURL shortURL";
	private static final String _SQL_COUNT_SHORTURL_WHERE = "SELECT COUNT(shortURL) FROM ShortURL shortURL WHERE ";
	private static final String _FINDER_COLUMN_ORIGINALURL_ORIGINALURL_1 = "shortURL.originalURL IS NULL";
	private static final String _FINDER_COLUMN_ORIGINALURL_ORIGINALURL_2 = "shortURL.originalURL = ?";
	private static final String _FINDER_COLUMN_ORIGINALURL_ORIGINALURL_3 = "(shortURL.originalURL IS NULL OR shortURL.originalURL = ?)";
	private static final String _FINDER_COLUMN_HASH_HASH_1 = "shortURL.hash IS NULL";
	private static final String _FINDER_COLUMN_HASH_HASH_2 = "shortURL.hash = ?";
	private static final String _FINDER_COLUMN_HASH_HASH_3 = "(shortURL.hash IS NULL OR shortURL.hash = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "shortURL.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ShortURL exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ShortURL exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(ShortURLPersistenceImpl.class);
}