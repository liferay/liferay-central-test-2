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

package com.liferay.portlet.journal.service.persistence;

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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.journal.NoSuchArticleImageException;
import com.liferay.portlet.journal.model.JournalArticleImage;
import com.liferay.portlet.journal.model.impl.JournalArticleImageImpl;
import com.liferay.portlet.journal.model.impl.JournalArticleImageModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="JournalArticleImagePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalArticleImagePersistence
 * @see       JournalArticleImageUtil
 * @generated
 */
public class JournalArticleImagePersistenceImpl extends BasePersistenceImpl<JournalArticleImage>
	implements JournalArticleImagePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = JournalArticleImageImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_TEMPIMAGE = new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByTempImage",
			new String[] { Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_TEMPIMAGE = new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByTempImage",
			new String[] {
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_TEMPIMAGE = new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByTempImage",
			new String[] { Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_A_V = new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_A_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Double.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_A_V = new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_A_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Double.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A_V = new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_A_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Double.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_G_A_V_E_E_L = new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_A_V_E_E_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Double.class.getName(), String.class.getName(),
				String.class.getName(), String.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A_V_E_E_L = new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_A_V_E_E_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Double.class.getName(), String.class.getName(),
				String.class.getName(), String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(JournalArticleImage journalArticleImage) {
		EntityCacheUtil.putResult(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageImpl.class, journalArticleImage.getPrimaryKey(),
			journalArticleImage);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A_V_E_E_L,
			new Object[] {
				new Long(journalArticleImage.getGroupId()),
				
			journalArticleImage.getArticleId(),
				new Double(journalArticleImage.getVersion()),
				
			journalArticleImage.getElInstanceId(),
				
			journalArticleImage.getElName(),
				
			journalArticleImage.getLanguageId()
			}, journalArticleImage);
	}

	public void cacheResult(List<JournalArticleImage> journalArticleImages) {
		for (JournalArticleImage journalArticleImage : journalArticleImages) {
			if (EntityCacheUtil.getResult(
						JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
						JournalArticleImageImpl.class,
						journalArticleImage.getPrimaryKey(), this) == null) {
				cacheResult(journalArticleImage);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(JournalArticleImageImpl.class.getName());
		EntityCacheUtil.clearCache(JournalArticleImageImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public JournalArticleImage create(long articleImageId) {
		JournalArticleImage journalArticleImage = new JournalArticleImageImpl();

		journalArticleImage.setNew(true);
		journalArticleImage.setPrimaryKey(articleImageId);

		return journalArticleImage;
	}

	public JournalArticleImage remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public JournalArticleImage remove(long articleImageId)
		throws NoSuchArticleImageException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalArticleImage journalArticleImage = (JournalArticleImage)session.get(JournalArticleImageImpl.class,
					new Long(articleImageId));

			if (journalArticleImage == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No JournalArticleImage exists with the primary key " +
						articleImageId);
				}

				throw new NoSuchArticleImageException(
					"No JournalArticleImage exists with the primary key " +
					articleImageId);
			}

			return remove(journalArticleImage);
		}
		catch (NoSuchArticleImageException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalArticleImage remove(JournalArticleImage journalArticleImage)
		throws SystemException {
		for (ModelListener<JournalArticleImage> listener : listeners) {
			listener.onBeforeRemove(journalArticleImage);
		}

		journalArticleImage = removeImpl(journalArticleImage);

		for (ModelListener<JournalArticleImage> listener : listeners) {
			listener.onAfterRemove(journalArticleImage);
		}

		return journalArticleImage;
	}

	protected JournalArticleImage removeImpl(
		JournalArticleImage journalArticleImage) throws SystemException {
		journalArticleImage = toUnwrappedModel(journalArticleImage);

		Session session = null;

		try {
			session = openSession();

			if (journalArticleImage.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(JournalArticleImageImpl.class,
						journalArticleImage.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(journalArticleImage);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		JournalArticleImageModelImpl journalArticleImageModelImpl = (JournalArticleImageModelImpl)journalArticleImage;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_A_V_E_E_L,
			new Object[] {
				new Long(journalArticleImageModelImpl.getOriginalGroupId()),
				
			journalArticleImageModelImpl.getOriginalArticleId(),
				new Double(journalArticleImageModelImpl.getOriginalVersion()),
				
			journalArticleImageModelImpl.getOriginalElInstanceId(),
				
			journalArticleImageModelImpl.getOriginalElName(),
				
			journalArticleImageModelImpl.getOriginalLanguageId()
			});

		EntityCacheUtil.removeResult(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageImpl.class, journalArticleImage.getPrimaryKey());

		return journalArticleImage;
	}

	public JournalArticleImage updateImpl(
		com.liferay.portlet.journal.model.JournalArticleImage journalArticleImage,
		boolean merge) throws SystemException {
		journalArticleImage = toUnwrappedModel(journalArticleImage);

		boolean isNew = journalArticleImage.isNew();

		JournalArticleImageModelImpl journalArticleImageModelImpl = (JournalArticleImageModelImpl)journalArticleImage;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, journalArticleImage, merge);

			journalArticleImage.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImageImpl.class, journalArticleImage.getPrimaryKey(),
			journalArticleImage);

		if (!isNew &&
				((journalArticleImage.getGroupId() != journalArticleImageModelImpl.getOriginalGroupId()) ||
				!Validator.equals(journalArticleImage.getArticleId(),
					journalArticleImageModelImpl.getOriginalArticleId()) ||
				(journalArticleImage.getVersion() != journalArticleImageModelImpl.getOriginalVersion()) ||
				!Validator.equals(journalArticleImage.getElInstanceId(),
					journalArticleImageModelImpl.getOriginalElInstanceId()) ||
				!Validator.equals(journalArticleImage.getElName(),
					journalArticleImageModelImpl.getOriginalElName()) ||
				!Validator.equals(journalArticleImage.getLanguageId(),
					journalArticleImageModelImpl.getOriginalLanguageId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_A_V_E_E_L,
				new Object[] {
					new Long(journalArticleImageModelImpl.getOriginalGroupId()),
					
				journalArticleImageModelImpl.getOriginalArticleId(),
					new Double(journalArticleImageModelImpl.getOriginalVersion()),
					
				journalArticleImageModelImpl.getOriginalElInstanceId(),
					
				journalArticleImageModelImpl.getOriginalElName(),
					
				journalArticleImageModelImpl.getOriginalLanguageId()
				});
		}

		if (isNew ||
				((journalArticleImage.getGroupId() != journalArticleImageModelImpl.getOriginalGroupId()) ||
				!Validator.equals(journalArticleImage.getArticleId(),
					journalArticleImageModelImpl.getOriginalArticleId()) ||
				(journalArticleImage.getVersion() != journalArticleImageModelImpl.getOriginalVersion()) ||
				!Validator.equals(journalArticleImage.getElInstanceId(),
					journalArticleImageModelImpl.getOriginalElInstanceId()) ||
				!Validator.equals(journalArticleImage.getElName(),
					journalArticleImageModelImpl.getOriginalElName()) ||
				!Validator.equals(journalArticleImage.getLanguageId(),
					journalArticleImageModelImpl.getOriginalLanguageId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A_V_E_E_L,
				new Object[] {
					new Long(journalArticleImage.getGroupId()),
					
				journalArticleImage.getArticleId(),
					new Double(journalArticleImage.getVersion()),
					
				journalArticleImage.getElInstanceId(),
					
				journalArticleImage.getElName(),
					
				journalArticleImage.getLanguageId()
				}, journalArticleImage);
		}

		return journalArticleImage;
	}

	protected JournalArticleImage toUnwrappedModel(
		JournalArticleImage journalArticleImage) {
		if (journalArticleImage instanceof JournalArticleImageImpl) {
			return journalArticleImage;
		}

		JournalArticleImageImpl journalArticleImageImpl = new JournalArticleImageImpl();

		journalArticleImageImpl.setNew(journalArticleImage.isNew());
		journalArticleImageImpl.setPrimaryKey(journalArticleImage.getPrimaryKey());

		journalArticleImageImpl.setArticleImageId(journalArticleImage.getArticleImageId());
		journalArticleImageImpl.setGroupId(journalArticleImage.getGroupId());
		journalArticleImageImpl.setArticleId(journalArticleImage.getArticleId());
		journalArticleImageImpl.setVersion(journalArticleImage.getVersion());
		journalArticleImageImpl.setElInstanceId(journalArticleImage.getElInstanceId());
		journalArticleImageImpl.setElName(journalArticleImage.getElName());
		journalArticleImageImpl.setLanguageId(journalArticleImage.getLanguageId());
		journalArticleImageImpl.setTempImage(journalArticleImage.isTempImage());

		return journalArticleImageImpl;
	}

	public JournalArticleImage findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public JournalArticleImage findByPrimaryKey(long articleImageId)
		throws NoSuchArticleImageException, SystemException {
		JournalArticleImage journalArticleImage = fetchByPrimaryKey(articleImageId);

		if (journalArticleImage == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No JournalArticleImage exists with the primary key " +
					articleImageId);
			}

			throw new NoSuchArticleImageException(
				"No JournalArticleImage exists with the primary key " +
				articleImageId);
		}

		return journalArticleImage;
	}

	public JournalArticleImage fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public JournalArticleImage fetchByPrimaryKey(long articleImageId)
		throws SystemException {
		JournalArticleImage journalArticleImage = (JournalArticleImage)EntityCacheUtil.getResult(JournalArticleImageModelImpl.ENTITY_CACHE_ENABLED,
				JournalArticleImageImpl.class, articleImageId, this);

		if (journalArticleImage == null) {
			Session session = null;

			try {
				session = openSession();

				journalArticleImage = (JournalArticleImage)session.get(JournalArticleImageImpl.class,
						new Long(articleImageId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (journalArticleImage != null) {
					cacheResult(journalArticleImage);
				}

				closeSession(session);
			}
		}

		return journalArticleImage;
	}

	public List<JournalArticleImage> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<JournalArticleImage> list = (List<JournalArticleImage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticleImage FROM JournalArticleImage journalArticleImage WHERE ");

				query.append("journalArticleImage.groupId = ?");

				query.append(" ");

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
					list = new ArrayList<JournalArticleImage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticleImage> findByGroupId(long groupId, int start,
		int end) throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<JournalArticleImage> findByGroupId(long groupId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalArticleImage> list = (List<JournalArticleImage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticleImage FROM JournalArticleImage journalArticleImage WHERE ");

				query.append("journalArticleImage.groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalArticleImage.");
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

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<JournalArticleImage>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticleImage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalArticleImage findByGroupId_First(long groupId,
		OrderByComparator obc)
		throws NoSuchArticleImageException, SystemException {
		List<JournalArticleImage> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticleImage exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticleImage findByGroupId_Last(long groupId,
		OrderByComparator obc)
		throws NoSuchArticleImageException, SystemException {
		int count = countByGroupId(groupId);

		List<JournalArticleImage> list = findByGroupId(groupId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticleImage exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticleImage[] findByGroupId_PrevAndNext(
		long articleImageId, long groupId, OrderByComparator obc)
		throws NoSuchArticleImageException, SystemException {
		JournalArticleImage journalArticleImage = findByPrimaryKey(articleImageId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT journalArticleImage FROM JournalArticleImage journalArticleImage WHERE ");

			query.append("journalArticleImage.groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("journalArticleImage.");
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

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalArticleImage);

			JournalArticleImage[] array = new JournalArticleImageImpl[3];

			array[0] = (JournalArticleImage)objArray[0];
			array[1] = (JournalArticleImage)objArray[1];
			array[2] = (JournalArticleImage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalArticleImage> findByTempImage(boolean tempImage)
		throws SystemException {
		Object[] finderArgs = new Object[] { Boolean.valueOf(tempImage) };

		List<JournalArticleImage> list = (List<JournalArticleImage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_TEMPIMAGE,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticleImage FROM JournalArticleImage journalArticleImage WHERE ");

				query.append("journalArticleImage.tempImage = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tempImage);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticleImage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_TEMPIMAGE,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticleImage> findByTempImage(boolean tempImage,
		int start, int end) throws SystemException {
		return findByTempImage(tempImage, start, end, null);
	}

	public List<JournalArticleImage> findByTempImage(boolean tempImage,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				Boolean.valueOf(tempImage),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalArticleImage> list = (List<JournalArticleImage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_TEMPIMAGE,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticleImage FROM JournalArticleImage journalArticleImage WHERE ");

				query.append("journalArticleImage.tempImage = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalArticleImage.");
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

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tempImage);

				list = (List<JournalArticleImage>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticleImage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_TEMPIMAGE,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalArticleImage findByTempImage_First(boolean tempImage,
		OrderByComparator obc)
		throws NoSuchArticleImageException, SystemException {
		List<JournalArticleImage> list = findByTempImage(tempImage, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticleImage exists with the key {");

			msg.append("tempImage=" + tempImage);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticleImage findByTempImage_Last(boolean tempImage,
		OrderByComparator obc)
		throws NoSuchArticleImageException, SystemException {
		int count = countByTempImage(tempImage);

		List<JournalArticleImage> list = findByTempImage(tempImage, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticleImage exists with the key {");

			msg.append("tempImage=" + tempImage);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticleImage[] findByTempImage_PrevAndNext(
		long articleImageId, boolean tempImage, OrderByComparator obc)
		throws NoSuchArticleImageException, SystemException {
		JournalArticleImage journalArticleImage = findByPrimaryKey(articleImageId);

		int count = countByTempImage(tempImage);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT journalArticleImage FROM JournalArticleImage journalArticleImage WHERE ");

			query.append("journalArticleImage.tempImage = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("journalArticleImage.");
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

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(tempImage);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalArticleImage);

			JournalArticleImage[] array = new JournalArticleImageImpl[3];

			array[0] = (JournalArticleImage)objArray[0];
			array[1] = (JournalArticleImage)objArray[1];
			array[2] = (JournalArticleImage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalArticleImage> findByG_A_V(long groupId,
		String articleId, double version) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				articleId, new Double(version)
			};

		List<JournalArticleImage> list = (List<JournalArticleImage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_A_V,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticleImage FROM JournalArticleImage journalArticleImage WHERE ");

				query.append("journalArticleImage.groupId = ?");

				query.append(" AND ");

				if (articleId == null) {
					query.append("journalArticleImage.articleId IS NULL");
				}
				else {
					if (articleId.equals(StringPool.BLANK)) {
						query.append(
							"(journalArticleImage.articleId IS NULL OR ");
					}

					query.append("journalArticleImage.articleId = ?");

					if (articleId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("journalArticleImage.version = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
				}

				qPos.add(version);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticleImage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_A_V,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticleImage> findByG_A_V(long groupId,
		String articleId, double version, int start, int end)
		throws SystemException {
		return findByG_A_V(groupId, articleId, version, start, end, null);
	}

	public List<JournalArticleImage> findByG_A_V(long groupId,
		String articleId, double version, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				articleId, new Double(version),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalArticleImage> list = (List<JournalArticleImage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_A_V,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticleImage FROM JournalArticleImage journalArticleImage WHERE ");

				query.append("journalArticleImage.groupId = ?");

				query.append(" AND ");

				if (articleId == null) {
					query.append("journalArticleImage.articleId IS NULL");
				}
				else {
					if (articleId.equals(StringPool.BLANK)) {
						query.append(
							"(journalArticleImage.articleId IS NULL OR ");
					}

					query.append("journalArticleImage.articleId = ?");

					if (articleId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("journalArticleImage.version = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalArticleImage.");
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

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
				}

				qPos.add(version);

				list = (List<JournalArticleImage>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticleImage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_A_V,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalArticleImage findByG_A_V_First(long groupId,
		String articleId, double version, OrderByComparator obc)
		throws NoSuchArticleImageException, SystemException {
		List<JournalArticleImage> list = findByG_A_V(groupId, articleId,
				version, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticleImage exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("articleId=" + articleId);

			msg.append(", ");
			msg.append("version=" + version);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticleImage findByG_A_V_Last(long groupId, String articleId,
		double version, OrderByComparator obc)
		throws NoSuchArticleImageException, SystemException {
		int count = countByG_A_V(groupId, articleId, version);

		List<JournalArticleImage> list = findByG_A_V(groupId, articleId,
				version, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticleImage exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("articleId=" + articleId);

			msg.append(", ");
			msg.append("version=" + version);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleImageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticleImage[] findByG_A_V_PrevAndNext(long articleImageId,
		long groupId, String articleId, double version, OrderByComparator obc)
		throws NoSuchArticleImageException, SystemException {
		JournalArticleImage journalArticleImage = findByPrimaryKey(articleImageId);

		int count = countByG_A_V(groupId, articleId, version);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT journalArticleImage FROM JournalArticleImage journalArticleImage WHERE ");

			query.append("journalArticleImage.groupId = ?");

			query.append(" AND ");

			if (articleId == null) {
				query.append("journalArticleImage.articleId IS NULL");
			}
			else {
				if (articleId.equals(StringPool.BLANK)) {
					query.append("(journalArticleImage.articleId IS NULL OR ");
				}

				query.append("journalArticleImage.articleId = ?");

				if (articleId.equals(StringPool.BLANK)) {
					query.append(")");
				}
			}

			query.append(" AND ");

			query.append("journalArticleImage.version = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("journalArticleImage.");
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

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (articleId != null) {
				qPos.add(articleId);
			}

			qPos.add(version);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalArticleImage);

			JournalArticleImage[] array = new JournalArticleImageImpl[3];

			array[0] = (JournalArticleImage)objArray[0];
			array[1] = (JournalArticleImage)objArray[1];
			array[2] = (JournalArticleImage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalArticleImage findByG_A_V_E_E_L(long groupId,
		String articleId, double version, String elInstanceId, String elName,
		String languageId) throws NoSuchArticleImageException, SystemException {
		JournalArticleImage journalArticleImage = fetchByG_A_V_E_E_L(groupId,
				articleId, version, elInstanceId, elName, languageId);

		if (journalArticleImage == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticleImage exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("articleId=" + articleId);

			msg.append(", ");
			msg.append("version=" + version);

			msg.append(", ");
			msg.append("elInstanceId=" + elInstanceId);

			msg.append(", ");
			msg.append("elName=" + elName);

			msg.append(", ");
			msg.append("languageId=" + languageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchArticleImageException(msg.toString());
		}

		return journalArticleImage;
	}

	public JournalArticleImage fetchByG_A_V_E_E_L(long groupId,
		String articleId, double version, String elInstanceId, String elName,
		String languageId) throws SystemException {
		return fetchByG_A_V_E_E_L(groupId, articleId, version, elInstanceId,
			elName, languageId, true);
	}

	public JournalArticleImage fetchByG_A_V_E_E_L(long groupId,
		String articleId, double version, String elInstanceId, String elName,
		String languageId, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				articleId, new Double(version),
				
				elInstanceId,
				
				elName,
				
				languageId
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_A_V_E_E_L,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticleImage FROM JournalArticleImage journalArticleImage WHERE ");

				query.append("journalArticleImage.groupId = ?");

				query.append(" AND ");

				if (articleId == null) {
					query.append("journalArticleImage.articleId IS NULL");
				}
				else {
					if (articleId.equals(StringPool.BLANK)) {
						query.append(
							"(journalArticleImage.articleId IS NULL OR ");
					}

					query.append("journalArticleImage.articleId = ?");

					if (articleId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("journalArticleImage.version = ?");

				query.append(" AND ");

				if (elInstanceId == null) {
					query.append("journalArticleImage.elInstanceId IS NULL");
				}
				else {
					if (elInstanceId.equals(StringPool.BLANK)) {
						query.append(
							"(journalArticleImage.elInstanceId IS NULL OR ");
					}

					query.append("journalArticleImage.elInstanceId = ?");

					if (elInstanceId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				if (elName == null) {
					query.append("journalArticleImage.elName IS NULL");
				}
				else {
					if (elName.equals(StringPool.BLANK)) {
						query.append("(journalArticleImage.elName IS NULL OR ");
					}

					query.append("journalArticleImage.elName = ?");

					if (elName.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				if (languageId == null) {
					query.append("journalArticleImage.languageId IS NULL");
				}
				else {
					if (languageId.equals(StringPool.BLANK)) {
						query.append(
							"(journalArticleImage.languageId IS NULL OR ");
					}

					query.append("journalArticleImage.languageId = ?");

					if (languageId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
				}

				qPos.add(version);

				if (elInstanceId != null) {
					qPos.add(elInstanceId);
				}

				if (elName != null) {
					qPos.add(elName);
				}

				if (languageId != null) {
					qPos.add(languageId);
				}

				List<JournalArticleImage> list = q.list();

				result = list;

				JournalArticleImage journalArticleImage = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A_V_E_E_L,
						finderArgs, list);
				}
				else {
					journalArticleImage = list.get(0);

					cacheResult(journalArticleImage);

					if ((journalArticleImage.getGroupId() != groupId) ||
							(journalArticleImage.getArticleId() == null) ||
							!journalArticleImage.getArticleId().equals(articleId) ||
							(journalArticleImage.getVersion() != version) ||
							(journalArticleImage.getElInstanceId() == null) ||
							!journalArticleImage.getElInstanceId()
													.equals(elInstanceId) ||
							(journalArticleImage.getElName() == null) ||
							!journalArticleImage.getElName().equals(elName) ||
							(journalArticleImage.getLanguageId() == null) ||
							!journalArticleImage.getLanguageId()
													.equals(languageId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A_V_E_E_L,
							finderArgs, journalArticleImage);
					}
				}

				return journalArticleImage;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A_V_E_E_L,
						finderArgs, new ArrayList<JournalArticleImage>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (JournalArticleImage)result;
			}
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

	public List<JournalArticleImage> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<JournalArticleImage> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<JournalArticleImage> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalArticleImage> list = (List<JournalArticleImage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticleImage FROM JournalArticleImage journalArticleImage ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalArticleImage.");
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

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<JournalArticleImage>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<JournalArticleImage>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticleImage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (JournalArticleImage journalArticleImage : findByGroupId(groupId)) {
			remove(journalArticleImage);
		}
	}

	public void removeByTempImage(boolean tempImage) throws SystemException {
		for (JournalArticleImage journalArticleImage : findByTempImage(
				tempImage)) {
			remove(journalArticleImage);
		}
	}

	public void removeByG_A_V(long groupId, String articleId, double version)
		throws SystemException {
		for (JournalArticleImage journalArticleImage : findByG_A_V(groupId,
				articleId, version)) {
			remove(journalArticleImage);
		}
	}

	public void removeByG_A_V_E_E_L(long groupId, String articleId,
		double version, String elInstanceId, String elName, String languageId)
		throws NoSuchArticleImageException, SystemException {
		JournalArticleImage journalArticleImage = findByG_A_V_E_E_L(groupId,
				articleId, version, elInstanceId, elName, languageId);

		remove(journalArticleImage);
	}

	public void removeAll() throws SystemException {
		for (JournalArticleImage journalArticleImage : findAll()) {
			remove(journalArticleImage);
		}
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

				query.append("SELECT COUNT(journalArticleImage) ");
				query.append(
					"FROM JournalArticleImage journalArticleImage WHERE ");

				query.append("journalArticleImage.groupId = ?");

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

	public int countByTempImage(boolean tempImage) throws SystemException {
		Object[] finderArgs = new Object[] { Boolean.valueOf(tempImage) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_TEMPIMAGE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(journalArticleImage) ");
				query.append(
					"FROM JournalArticleImage journalArticleImage WHERE ");

				query.append("journalArticleImage.tempImage = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tempImage);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_TEMPIMAGE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_A_V(long groupId, String articleId, double version)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				articleId, new Double(version)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_A_V,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(journalArticleImage) ");
				query.append(
					"FROM JournalArticleImage journalArticleImage WHERE ");

				query.append("journalArticleImage.groupId = ?");

				query.append(" AND ");

				if (articleId == null) {
					query.append("journalArticleImage.articleId IS NULL");
				}
				else {
					if (articleId.equals(StringPool.BLANK)) {
						query.append(
							"(journalArticleImage.articleId IS NULL OR ");
					}

					query.append("journalArticleImage.articleId = ?");

					if (articleId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("journalArticleImage.version = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
				}

				qPos.add(version);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_A_V,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_A_V_E_E_L(long groupId, String articleId,
		double version, String elInstanceId, String elName, String languageId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				articleId, new Double(version),
				
				elInstanceId,
				
				elName,
				
				languageId
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_A_V_E_E_L,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(journalArticleImage) ");
				query.append(
					"FROM JournalArticleImage journalArticleImage WHERE ");

				query.append("journalArticleImage.groupId = ?");

				query.append(" AND ");

				if (articleId == null) {
					query.append("journalArticleImage.articleId IS NULL");
				}
				else {
					if (articleId.equals(StringPool.BLANK)) {
						query.append(
							"(journalArticleImage.articleId IS NULL OR ");
					}

					query.append("journalArticleImage.articleId = ?");

					if (articleId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("journalArticleImage.version = ?");

				query.append(" AND ");

				if (elInstanceId == null) {
					query.append("journalArticleImage.elInstanceId IS NULL");
				}
				else {
					if (elInstanceId.equals(StringPool.BLANK)) {
						query.append(
							"(journalArticleImage.elInstanceId IS NULL OR ");
					}

					query.append("journalArticleImage.elInstanceId = ?");

					if (elInstanceId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				if (elName == null) {
					query.append("journalArticleImage.elName IS NULL");
				}
				else {
					if (elName.equals(StringPool.BLANK)) {
						query.append("(journalArticleImage.elName IS NULL OR ");
					}

					query.append("journalArticleImage.elName = ?");

					if (elName.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				if (languageId == null) {
					query.append("journalArticleImage.languageId IS NULL");
				}
				else {
					if (languageId.equals(StringPool.BLANK)) {
						query.append(
							"(journalArticleImage.languageId IS NULL OR ");
					}

					query.append("journalArticleImage.languageId = ?");

					if (languageId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
				}

				qPos.add(version);

				if (elInstanceId != null) {
					qPos.add(elInstanceId);
				}

				if (elName != null) {
					qPos.add(elName);
				}

				if (languageId != null) {
					qPos.add(languageId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_A_V_E_E_L,
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
						"SELECT COUNT(journalArticleImage) FROM JournalArticleImage journalArticleImage");

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
						"value.object.listener.com.liferay.portlet.journal.model.JournalArticleImage")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<JournalArticleImage>> listenersList = new ArrayList<ModelListener<JournalArticleImage>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<JournalArticleImage>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticlePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalArticlePersistence journalArticlePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticleImagePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalArticleImagePersistence journalArticleImagePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence journalArticleResourcePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalContentSearchPersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalContentSearchPersistence journalContentSearchPersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalFeedPersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalFeedPersistence journalFeedPersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalStructurePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalStructurePersistence journalStructurePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalTemplatePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalTemplatePersistence journalTemplatePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static Log _log = LogFactoryUtil.getLog(JournalArticleImagePersistenceImpl.class);
}